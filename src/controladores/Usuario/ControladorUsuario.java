package controladores.Usuario;
import controladores.Evaluacion.ControladorEAuditoriaAmbiental;
import controladores.Evaluacion.ControladorEInformeAmbientalCumplimiento;
import controladores.Evaluacion.ControladorEInformeMonitoreo;
import modulos.Alertas.GestionNotificaciones;
import modulos.Alertas.Notificacion;
import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EAuditoriaAmbiental;
import modulos.Evaluacion.EInformeAmbientalCumplimiento;
import modulos.Evaluacion.EInformeMonitoreo;
import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;
import modulos.Usuario.*;
import vistas.Usuario.UsuarioGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ControladorUsuario implements ActionListener, Observer, MouseListener {

    private Timer timer;
    private int rowCount;
    private Usuario usuario;
    private JFrame vistaAnterior;
    private UsuarioGUI usuarioGUI;
    private Notificacion notificacion;
    private TimerTask enviarNotificaciones;

    public ControladorUsuario(Usuario usuario, JFrame vistaAnterior){
        timer = new Timer();
        this.usuario = usuario;
        notificacion = new Notificacion();
        this.vistaAnterior = vistaAnterior;
        usuarioGUI = new UsuarioGUI(this.usuario);

        GestionUsuarios.getInstance().registrarObservador(this);

        setListeners();
        setNotificaciones();

        notificar();
        timer.schedule(enviarNotificaciones, 2 * 60 * 1000, 2 * 60 * 1000);

    }

    public void setListeners(){

        usuarioGUI.evaluacionBoton.setToolTipText("Evaluación");

        usuarioGUI.gestionCGA.addActionListener(this);
        usuarioGUI.cerrarSesion.addActionListener(this);

        usuarioGUI.notificaciones.addMouseListener(this);
        usuarioGUI.gestionMascota.addActionListener(this);
        usuarioGUI.gestionarCuenta.addActionListener(this);
        usuarioGUI.evaluacionBoton.addActionListener(this);
        usuarioGUI.gestionDuenoMascota.addActionListener(this);
        usuarioGUI.gestionEstablecimiento.addActionListener(this);
        usuarioGUI.gestionDuenoEstablecimiento.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == usuarioGUI.gestionCGA){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorGestionUsuario(usuario, usuarioGUI,"GestionPersonalCGA");
        }

        else if(e.getSource() == usuarioGUI.cerrarSesion) cerrarControlador();

        else if(e.getSource() == usuarioGUI.gestionMascota){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorGestionUsuario(usuario, usuarioGUI,"GestionMascotas");
        }

        else if(e.getSource() == usuarioGUI.gestionarCuenta){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorDatosUsuario(1, usuario ,usuarioGUI);
        }

        else if(e.getSource() == usuarioGUI.gestionDuenoMascota){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorGestionUsuario(usuario, usuarioGUI, "GestionDuenosMascotas");
        }

        else if(e.getSource() == usuarioGUI.gestionEstablecimiento){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorGestionUsuario(usuario, usuarioGUI, "GestionEstablecimientos");
        }

        else if(e.getSource() == usuarioGUI.gestionDuenoEstablecimiento){
            usuarioGUI.setPanelPrincipalVisible(false);
            new ControladorGestionUsuario(usuario, usuarioGUI, "GestionDuenoEstablecimiento");
        }

        else if(e.getSource() == usuarioGUI.evaluacionBoton && usuarioGUI.notificacionesModel.getRowCount() > 0){
            usuarioGUI.setPanelPrincipalVisible(false);
            getEvaluacion();
        }
    }

    public void setNotificaciones(){
        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
            usuarioGUI.notificacionesModel = new DefaultTableModel(new String[][]{}, new String[]{"Ruc", "Nombre establecimiento", "Cedula propietario", "Nombre propietario", "Descripcion"});

        else if(usuario instanceof PropietarioEstablecimiento)
            usuarioGUI.notificacionesModel = new DefaultTableModel(new String[][]{}, new String[]{"Ruc", "Nombre establecimiento", "Descripcion"});

        usuarioGUI.notificaciones.setModel(usuarioGUI.notificacionesModel);
    }


    public void actualizar(){
        usuarioGUI.setPanelPrincipalVisible(true);
        //Ademas actualizar ususario
        actualizarDatosUsuario();

    }

    public void actualizarNotificaciones(){

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
            actualizarNotificacionesAdministrador();

        else if(usuario instanceof PropietarioEstablecimiento)
            actualizarNotificacionesPropietario();

    }

    public void actualizarNotificacionesAdministrador(){

        for(Map.Entry<String, Usuario> usuarioNotificacion : GestionUsuarios.getInstance().getUsuarios().entrySet()){
            if(usuarioNotificacion.getValue() instanceof PropietarioEstablecimiento){
                for(Map.Entry<String, Establecimiento> establecimiento : ((PropietarioEstablecimiento) usuarioNotificacion.getValue()).getEstablecimientos().entrySet()){
                    notificacionEstablecimiento((PropietarioEstablecimiento) usuarioNotificacion.getValue(), establecimiento.getValue());
                }
            }
        }

    }

    public void actualizarNotificacionesPropietario(){
        for(Map.Entry<String, Establecimiento> establecimiento : ((PropietarioEstablecimiento) usuario).getEstablecimientos().entrySet()){
            notificacionEstablecimiento((PropietarioEstablecimiento) usuario, establecimiento.getValue());
        }
    }

    public void notificacionEstablecimiento(PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento){

        if(establecimiento.getEvaluacion() instanceof EAuditoriaAmbiental && (((EAuditoriaAmbiental) establecimiento.getEvaluacion()).getPeriodoAuditado() != null || ((EAuditoriaAmbiental) establecimiento.getEvaluacion()).getFechaEmisionLicencia() != null))
            notificacionEAuditoriaAmbiental(propietarioEstablecimiento, establecimiento, (EAuditoriaAmbiental) establecimiento.getEvaluacion());

        else if(establecimiento.getEvaluacion() instanceof EInformeMonitoreo)
            notificacionEInformeMonitoreo(propietarioEstablecimiento, establecimiento, (EInformeMonitoreo) establecimiento.getEvaluacion());

        else if(establecimiento.getEvaluacion() instanceof EInformeAmbientalCumplimiento && (((EInformeAmbientalCumplimiento) establecimiento.getEvaluacion()).getPeriodoEvaluado() != null || ((EInformeAmbientalCumplimiento) establecimiento.getEvaluacion()).getFechaResolucion() != null))
            notificacionEInformeAmbientalCumplimiento(propietarioEstablecimiento, establecimiento, (EInformeAmbientalCumplimiento) establecimiento.getEvaluacion());

    }

    public void notificacionEInformeAmbientalCumplimiento(PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento, EInformeAmbientalCumplimiento eInformeAmbientalCumplimiento){
        String content  = "";
        Calendar fechaVerificacion;
        fechaVerificacion = Calendar.getInstance();


        if(eInformeAmbientalCumplimiento.getPeriodoEvaluado() == null){
            fechaVerificacion.setTime(eInformeAmbientalCumplimiento.getFechaResolucion());
            fechaVerificacion.add(Calendar.YEAR, 1);
        }

        else{
            fechaVerificacion.setTime(eInformeAmbientalCumplimiento.getPeriodoEvaluado());
            fechaVerificacion.add(Calendar.YEAR, 2);
        }


        System.out.println(Calendar.getInstance().get(Calendar.YEAR) + " " + fechaVerificacion.get(Calendar.YEAR) +  " " + Calendar.getInstance().get(Calendar.MONTH) + " " + fechaVerificacion.get(Calendar.MONTH));

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) == 3){
            System.out.println("1");
            content = "Proxima evaluacion del establecimiento\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eInformeAmbientalCumplimiento.getFechaResolucion() + eInformeAmbientalCumplimiento.getPeriodoEvaluado());

        }

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) == 2){
            System.out.println("2");
            content = "Actual evaluacion\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eInformeAmbientalCumplimiento.getFechaResolucion() + eInformeAmbientalCumplimiento.getPeriodoEvaluado());

        }

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) < 2){
            System.out.println("3");
            content = "Evaluacion tardia\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eInformeAmbientalCumplimiento.getFechaResolucion() + eInformeAmbientalCumplimiento.getPeriodoEvaluado());

        }

    }

    public void notificacionEInformeMonitoreo(PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento, EInformeMonitoreo eInformeMonitoreo){
        //Desarrollar;
    }

    public void notificacionEAuditoriaAmbiental(PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento, EAuditoriaAmbiental eAuditoriaAmbiental){
        int periodo;
        String content = "";
        Calendar fechaVerificacion;

        fechaVerificacion = Calendar.getInstance();

        if(eAuditoriaAmbiental.getPeriodoAuditado() == null){
            fechaVerificacion.setTime(eAuditoriaAmbiental.getFechaEmisionLicencia());
            fechaVerificacion.add(Calendar.YEAR, 1);
        }

        else{
            if(Calendar.getInstance().get(Calendar.YEAR) <= 2019 && Calendar.getInstance().get(Calendar.MONTH) + 1 <= 6 && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 12)
                periodo = 2;
            else
                periodo = 3;

            fechaVerificacion.setTime(eAuditoriaAmbiental.getPeriodoAuditado());
            fechaVerificacion.add(Calendar.YEAR, periodo);
        }

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) == 4){
            content = "Proxima presentacion TDR\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eAuditoriaAmbiental.getFechaEmisionLicencia() + eAuditoriaAmbiental.getPeriodoAuditado());
        }

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) == 3){
            content = "Presentacion actual TDR\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eAuditoriaAmbiental.getFechaEmisionLicencia() + eAuditoriaAmbiental.getPeriodoAuditado());

        }

        if(fechaVerificacion.get(Calendar.MONTH) > Calendar.getInstance().get(Calendar.MONTH)){
            content = "Presentacion tardia TDR\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eAuditoriaAmbiental.getFechaEmisionLicencia() + eAuditoriaAmbiental.getPeriodoAuditado());

        }

        fechaVerificacion.setTime(eAuditoriaAmbiental.getFechaVencimientoPoliza());

        if(Calendar.getInstance().get(Calendar.MONTH) - fechaVerificacion.get(Calendar.MONTH) == 1){
            content = "Proximo vencimiento poliza\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eAuditoriaAmbiental.getFechaEmisionLicencia() + eAuditoriaAmbiental.getPeriodoAuditado());
        }
        else if(Calendar.getInstance().get(Calendar.MONTH) > fechaVerificacion.get(Calendar.MONTH)){
            content = "Vencio poliza\n" + establecimiento.getRuc() + " " + establecimiento.getNombreEstablecimiento() + "\n" + propietarioEstablecimiento.getCedula() + " " + propietarioEstablecimiento.getNombre();
            sendNotificacion(establecimiento, propietarioEstablecimiento, content, content + eAuditoriaAmbiental.getFechaEmisionLicencia() + eAuditoriaAmbiental.getPeriodoAuditado());
        }

    }

    public void sendNotificacion(Establecimiento establecimiento, PropietarioEstablecimiento propietarioEstablecimiento, String content, String verificador){

        if(!GestionNotificaciones.getInstance().isNotificacionExistente(propietarioEstablecimiento.getCorreo(), verificador)){
            GestionNotificaciones.getInstance().agregarNotificacion(propietarioEstablecimiento.getCorreo(), verificador);
            notificacion.createEmail(notificacion.getDestinatariosNotificacion(propietarioEstablecimiento.getCorreo()), "Notificacion gestion de establecimientos y mascotas", content);
            notificacion.sendEmail();

        }

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
            usuarioGUI.notificacionesModel.addRow(new Object[]{establecimiento.getRuc(), establecimiento.getNombreEstablecimiento(), propietarioEstablecimiento.getCedula(), propietarioEstablecimiento.getNombre(), content});

        else if(usuario instanceof PropietarioEstablecimiento)
            usuarioGUI.notificacionesModel.addRow(new Object[]{establecimiento.getRuc(), establecimiento.getNombreEstablecimiento(), content});

    }

    public void notificar(){

        enviarNotificaciones = new TimerTask() {
            @Override
            public void run() {
                usuarioGUI.notificacionesModel.setRowCount(0);
                actualizarNotificaciones();
            }
        };
    }

    public void actualizarDatosUsuario(){

        usuarioGUI.setFotoUsuario(usuario.getPathFoto());
        usuarioGUI.setInformacionUsuario(usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono());
    }

    public void cerrarControlador(){
        usuarioGUI.cerrarVentana();
        vistaAnterior.setVisible(true);
    }

    public void getEvaluacion(){
        PropietarioEstablecimiento busquedaUsuario = null;

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
            busquedaUsuario = (PropietarioEstablecimiento) GestionUsuarios.getInstance().getUsuario(usuarioGUI.notificacionesModel.getValueAt(rowCount, 2).toString());

        else if(usuario instanceof PropietarioEstablecimiento)
            busquedaUsuario = (PropietarioEstablecimiento) usuario;


        if(busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion() instanceof EAuditoriaAmbiental)
            new ControladorEAuditoriaAmbiental(usuario, busquedaUsuario, busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()), (EAuditoriaAmbiental) busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion(), usuarioGUI);

        else if(busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion() instanceof EInformeAmbientalCumplimiento)
            new ControladorEInformeAmbientalCumplimiento(usuario, busquedaUsuario, busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()), (EInformeAmbientalCumplimiento) busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion(), usuarioGUI);

        else if(busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion() instanceof EInformeMonitoreo)
            new ControladorEInformeMonitoreo(usuario, busquedaUsuario, busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()), (EInformeMonitoreo) busquedaUsuario.getEstablecimiento(usuarioGUI.notificacionesModel.getValueAt(rowCount, 0).toString()).getEvaluacion(), usuarioGUI);

    }


    @Override
    public void actualizar(Subject sujeto) {
        actualizar();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource() == usuarioGUI.notificaciones){
            rowCount = 0;
            rowCount = usuarioGUI.notificaciones.rowAtPoint(e.getPoint());
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
