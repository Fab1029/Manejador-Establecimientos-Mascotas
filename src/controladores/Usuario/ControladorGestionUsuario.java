package controladores.Usuario;

import controladores.Establecimiento.ControladorEstablecimiento;
import controladores.Establecimiento.ControladorPermisos;
import controladores.Evaluacion.ControladorEAuditoriaAmbiental;
import controladores.Evaluacion.ControladorEInformeAmbientalCumplimiento;
import controladores.Evaluacion.ControladorEInformeMonitoreo;
import controladores.Mascota.ControladorInformacionMedica;
import controladores.Mascota.ControladorMascota;
import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EAuditoriaAmbiental;
import modulos.Evaluacion.EInformeAmbientalCumplimiento;
import modulos.Evaluacion.EInformeMonitoreo;
import modulos.Mascota.GestionChips;
import modulos.Mascota.Mascota;
import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;
import modulos.Usuario.*;
import vistas.Usuario.GestionUsuarioGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class ControladorGestionUsuario implements ActionListener, MouseListener, Observer {

    private int rowNumber = 0;
    private Usuario usuario;
    private String tipoGestion;
    private JFrame anteriorVentana;
    private GestionUsuarioGUI gestionGUI;
    private GestionUsuarios gestionUsuarios;

    public ControladorGestionUsuario(Usuario usuario, JFrame anteriorVentana, String tipoGestion){
        this.usuario = usuario;
        this.tipoGestion = tipoGestion;
        this.anteriorVentana = anteriorVentana;
        gestionUsuarios = GestionUsuarios.getInstance();

        gestionUsuarios.registrarObservador(this);

        setVentana();
        setListeners();
        setTablaInformacion();
        cargarTablaInformacion();
    }


    //Comienzo funciones y metodos
    public void setVentana(){
        switch (tipoGestion){

            case "GestionEstablecimientos":
                gestionGUI = new GestionUsuarioGUI(this.usuario, this.tipoGestion, true);
                gestionGUI.permisosBoton.setVisible(true);
                break;

                case "GestionMascotas":
                    gestionGUI = new GestionUsuarioGUI(this.usuario, this.tipoGestion, true);
                    gestionGUI.permisosBoton.setVisible(false);
                    break;
            default:
                gestionGUI = new GestionUsuarioGUI(this.usuario, this.tipoGestion, false);
                gestionGUI.permisosBoton.setVisible(false);
                break;

        }
    }

    public void setListeners(){

        gestionGUI.atrasBoton.setToolTipText("Volver");
        gestionGUI.buscarBoton.setToolTipText("Buscar");
        gestionGUI.editarBoton.setToolTipText("Editar");
        gestionGUI.borraBoton.setToolTipText("Eliminar");
        gestionGUI.agregarBoton.setToolTipText("Agregar");
        gestionGUI.eliminarBoton.setToolTipText("Eliminar");
        gestionGUI.permisosBoton.setToolTipText("Permisos");
        gestionGUI.opcionAdicionalBoton.setToolTipText("Evaluacion");

        gestionGUI.borraBoton.addActionListener(this);
        gestionGUI.atrasBoton.addActionListener(this);
        gestionGUI.gestionTabla.addMouseListener(this);
        gestionGUI.editarBoton.addActionListener(this);
        gestionGUI.buscarBoton.addActionListener(this);
        gestionGUI.agregarBoton.addActionListener(this);
        gestionGUI.eliminarBoton.addActionListener(this);
        gestionGUI.permisosBoton.addActionListener(this);
        gestionGUI.opcionAdicionalBoton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gestionGUI.borraBoton) gestionGUI.busqueda.setText("");

        else if(e.getSource() == gestionGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == gestionGUI.editarBoton && gestionGUI.gestionModelTabla.getRowCount() > 0) editarDato();

        else if(e.getSource() == gestionGUI.buscarBoton && !gestionGUI.busqueda.getText().isEmpty()) cargarBusqueda();

        else if(e.getSource() == gestionGUI.agregarBoton) agregarDato();

        else if(e.getSource() == gestionGUI.eliminarBoton && gestionGUI.gestionModelTabla.getRowCount() > 0){
            eliminarDato();
            cargarTablaInformacion();
        }

        else if(e.getSource() == gestionGUI.opcionAdicionalBoton && gestionGUI.gestionModelTabla.getRowCount() > 0) opcionAdional();

        else if(e.getSource() == gestionGUI.permisosBoton && gestionGUI.gestionModelTabla.getRowCount() > 0) permisosAction();

    }

    public void permisosAction(){
        gestionGUI.setPanelPrincipalVisible(false);
        PropietarioEstablecimiento propietarioEstablecimiento;

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
            propietarioEstablecimiento = (PropietarioEstablecimiento) gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString());
        else
            propietarioEstablecimiento = (PropietarioEstablecimiento) usuario;

        new ControladorPermisos(usuario, propietarioEstablecimiento, propietarioEstablecimiento.getEstablecimiento(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 0).toString()), gestionGUI);
    }
    public void opcionAdional(){


        Usuario usuario = null;

        switch (tipoGestion){
            case "GestionEstablecimientos":

                Establecimiento establecimiento = null;

                if(this.usuario instanceof Administrador || this.usuario instanceof PersonalCGA) usuario = gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString());

                else usuario = this.usuario;

                establecimiento = ((PropietarioEstablecimiento) usuario).getEstablecimiento(gestionGUI.busqueda.getText());

                if(establecimiento.getPermisos().isTodosPermisos()){
                    gestionGUI.setPanelPrincipalVisible(false);

                    if(establecimiento.getEvaluacion() instanceof EAuditoriaAmbiental) new ControladorEAuditoriaAmbiental(this.usuario, (PropietarioEstablecimiento) usuario, establecimiento, (EAuditoriaAmbiental) establecimiento.getEvaluacion(), gestionGUI);

                    else if(establecimiento.getEvaluacion() instanceof EInformeMonitoreo) new ControladorEInformeMonitoreo(this.usuario, (PropietarioEstablecimiento) usuario, establecimiento, (EInformeMonitoreo) establecimiento.getEvaluacion(), gestionGUI);

                    else new ControladorEInformeAmbientalCumplimiento(this.usuario, (PropietarioEstablecimiento) usuario, establecimiento, (EInformeAmbientalCumplimiento) establecimiento.getEvaluacion(), gestionGUI);

                }
                else
                    JOptionPane.showMessageDialog(null, "Incumplimiento de permisos");

                break;
            case "GestionMascotas":
                Mascota mascota = null;
                gestionGUI.setPanelPrincipalVisible(false);

                if(this.usuario instanceof Administrador || this.usuario instanceof PersonalCGA) usuario = gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString());

                else usuario = this.usuario;

                mascota = ((DuenoMascota) usuario).getMascota(Integer.parseInt(gestionGUI.busqueda.getText()));
                new ControladorInformacionMedica(mascota, gestionGUI);
                break;

        }
    }

    public void editarDato(){
        gestionGUI.setPanelPrincipalVisible(false);

        Usuario usuario = null;

        switch (tipoGestion){
            case "GestionPersonalCGA", "GestionDuenoEstablecimiento", "GestionDuenosMascotas":

                new ControladorDatosUsuario(1, gestionUsuarios.getUsuario(gestionGUI.busqueda.getText()), gestionGUI);
                break;
            case "GestionEstablecimientos":

                Establecimiento establecimiento = null;

                if(this.usuario instanceof Administrador || this.usuario instanceof PersonalCGA) usuario = gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString());

                else usuario = this.usuario;

                establecimiento = ((PropietarioEstablecimiento)usuario).getEstablecimiento(gestionGUI.busqueda.getText());

                new ControladorEstablecimiento((PropietarioEstablecimiento) usuario, establecimiento, gestionGUI);

                break;
            case "GestionMascotas":

                if(this.usuario instanceof Administrador || this.usuario instanceof PersonalCGA) usuario = gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString());

                else usuario = this.usuario;

                new ControladorMascota((DuenoMascota) usuario, ((DuenoMascota) usuario).getMascota(Integer.parseInt(gestionGUI.busqueda.getText())), gestionGUI);

                break;
        }

    }

    public void eliminarDato(){
        switch (tipoGestion){
            case "GestionPersonalCGA", "GestionDuenosMascotas", "GestionDuenoEstablecimiento":
                gestionUsuarios.getUsuarios().remove(gestionGUI.busqueda.getText());

                break;
            case "GestionEstablecimientos":

                if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                    ((PropietarioEstablecimiento) gestionUsuarios.getUsuario((String) gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2))).removeEstablecimiento(gestionGUI.busqueda.getText());

                else
                    ((PropietarioEstablecimiento) usuario).removeEstablecimiento(gestionGUI.busqueda.getText());
                break;
            case "GestionMascotas":
                if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                    ((DuenoMascota) gestionUsuarios.getUsuario((String) gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2))).removeMascota(Integer.parseInt(gestionGUI.busqueda.getText()));

                else
                    ((DuenoMascota) usuario).removeMascota(Integer.parseInt(gestionGUI.busqueda.getText()));

                GestionChips.getInstance().removeChip(Integer.parseInt(gestionGUI.busqueda.getText()));
                break;
        }
    }


    public void agregarDato()  {
        gestionGUI.setPanelPrincipalVisible(false);

        switch (tipoGestion){
            case "GestionPersonalCGA":

                new ControladorDatosUsuario(1, new PersonalCGA(), gestionGUI);
                break;
            case "GestionDuenoEstablecimiento":

                new ControladorDatosUsuario(1, new PropietarioEstablecimiento(), gestionGUI);
                break;
            case "GestionDuenosMascotas":

                new ControladorDatosUsuario(1, new DuenoMascota(), gestionGUI);
                break;
            case "GestionEstablecimientos":

                if(usuario instanceof Administrador || usuario instanceof PersonalCGA){

                    if(gestionGUI.busqueda.getText().isEmpty()) new ControladorDatosUsuario(1, new PropietarioEstablecimiento(), gestionGUI);

                    else new ControladorEstablecimiento((PropietarioEstablecimiento) gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber,2).toString()), null, gestionGUI);

                }

                else new ControladorEstablecimiento((PropietarioEstablecimiento) usuario, null, gestionGUI);

                break;
            case "GestionMascotas":

                if(usuario instanceof Administrador || usuario instanceof PersonalCGA){

                    if(gestionGUI.busqueda.getText().isEmpty()) new ControladorDatosUsuario(1, new DuenoMascota(), gestionGUI);

                    else new ControladorMascota((DuenoMascota) gestionUsuarios.getUsuario(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 2).toString()), null, gestionGUI);

                }

                else new ControladorMascota((DuenoMascota) usuario, null, gestionGUI);

                break;

        }
    }

    public void cerrarControlador(){
        gestionGUI.cerrarVentana();
        anteriorVentana.setVisible(true);

    }

    public void setTablaInformacion(){
        switch (tipoGestion){
            case "GestionPersonalCGA", "GestionDuenosMascotas", "GestionDuenoEstablecimiento":
                gestionGUI.gestionModelTabla = new DefaultTableModel(new String[][]{}, new String[]{"Cedula", "Nombre"});

                break;
            case "GestionEstablecimientos":
                if(usuario instanceof Administrador || usuario instanceof PersonalCGA) gestionGUI.gestionModelTabla = new DefaultTableModel(new String[][]{}, new String[]{"Ruc", "Nombre establecimiento", "Cedula propietario", "Nombre propietario"});

                else gestionGUI.gestionModelTabla = new DefaultTableModel(new String[][]{}, new String[]{"Ruc", "Nombre establecimiento"});
                break;
            case "GestionMascotas":
                if(usuario instanceof Administrador || usuario instanceof PersonalCGA) gestionGUI.gestionModelTabla = new DefaultTableModel(new String[][]{}, new String[]{"Chip", "Nombre", "Cedula dueno", "Nombre dueno"});

                else gestionGUI.gestionModelTabla = new DefaultTableModel(new String[][]{}, new String[]{"Chip", "Nombre"});

                break;

        }

        gestionGUI.gestionTabla.setModel(gestionGUI.gestionModelTabla);
    }

    public void cargarTablaInformacion(){

        gestionGUI.gestionModelTabla.setRowCount(0); //Borra datos existentes para actualizar

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA)  cargarTablaInformacionAdminCGA();

        else cargarTablaInformacionOtrosUsuarios();

    }

    public void cargarTablaInformacionOtrosUsuarios(){
        switch (tipoGestion){
            case "GestionEstablecimientos":

                for(String ruc : ((PropietarioEstablecimiento) usuario).getEstablecimientos().keySet()){
                    gestionGUI.gestionModelTabla.addRow(new Object[]{ruc, ((PropietarioEstablecimiento) usuario).getEstablecimiento(ruc).getNombreEstablecimiento()});
                }
                break;
            case "GestionMascotas":
                for(Integer numeroChip : ((DuenoMascota) usuario).getMascotas().keySet()){
                    gestionGUI.gestionModelTabla.addRow(new Object[]{numeroChip, ((DuenoMascota) usuario).getMascota(numeroChip).getNombre()});
                }
                break;
        }
    }


    public void cargarTablaInformacionAdminCGA(){

        for(String key: gestionUsuarios.getUsuarios().keySet()){
            switch (tipoGestion){
                case "GestionPersonalCGA":

                    if(gestionUsuarios.getUsuario(key) instanceof PersonalCGA) gestionGUI.gestionModelTabla.addRow(new Object[]{gestionUsuarios.getUsuario(key).getCedula(), gestionUsuarios.getUsuario(key).getNombre()});

                    break;
                case "GestionDuenosMascotas":

                    if(gestionUsuarios.getUsuario(key) instanceof DuenoMascota) gestionGUI.gestionModelTabla.addRow(new Object[]{gestionUsuarios.getUsuario(key).getCedula(), gestionUsuarios.getUsuario(key).getNombre()});

                    break;
                case "GestionDuenoEstablecimiento":

                    if(gestionUsuarios.getUsuario(key) instanceof PropietarioEstablecimiento) gestionGUI.gestionModelTabla.addRow(new Object[]{gestionUsuarios.getUsuario(key).getCedula(), gestionUsuarios.getUsuario(key).getNombre()});

                    break;
                case "GestionEstablecimientos":

                    if(gestionUsuarios.getUsuario(key) instanceof PropietarioEstablecimiento){
                        PropietarioEstablecimiento propietarioEstablecimiento = (PropietarioEstablecimiento) gestionUsuarios.getUsuario(key);

                        for(String ruc : propietarioEstablecimiento.getEstablecimientos().keySet()){
                            gestionGUI.gestionModelTabla.addRow(new Object[]{ruc, propietarioEstablecimiento.getEstablecimiento(ruc).getNombreEstablecimiento(), propietarioEstablecimiento.getCedula(), propietarioEstablecimiento.getNombre()});
                        }
                    }

                    break;
                case "GestionMascotas":

                    if(gestionUsuarios.getUsuario(key) instanceof DuenoMascota){
                        DuenoMascota duenoMascota = (DuenoMascota) gestionUsuarios.getUsuario(key);

                        for(Integer numeroChip : duenoMascota.getMascotas().keySet()){
                            gestionGUI.gestionModelTabla.addRow(new Object[]{numeroChip, duenoMascota.getMascota(numeroChip).getNombre(), duenoMascota.getCedula(), duenoMascota.getNombre()});
                        }
                    }
                    break;

            }

        }
    }

    public void cargarBusqueda(){
        gestionGUI.gestionModelTabla.setRowCount(0);

        for(Map.Entry<String, Usuario> entryUsuario: gestionUsuarios.getUsuarios().entrySet()){
            switch (tipoGestion){
                case "GestionPersonalCGA" :
                    if(gestionGUI.tipoBusqueda.getSelectedItem().equals("Cedula")){
                        if(entryUsuario.getValue() instanceof PersonalCGA && entryUsuario.getKey().equals(gestionGUI.busqueda.getText())){
                            gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                            break;
                        }
                    }
                    else{
                        if(entryUsuario.getValue() instanceof PersonalCGA && entryUsuario.getValue().getNombre().equals(gestionGUI.busqueda.getText())) gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                    }

                    break;
                case "GestionDuenosMascotas" :
                    if(gestionGUI.tipoBusqueda.getSelectedItem().equals("Cedula")){
                        if(entryUsuario.getValue() instanceof DuenoMascota && entryUsuario.getKey().equals(gestionGUI.busqueda.getText())){
                            gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                            break;
                        }
                    }
                    else{
                        if(entryUsuario.getValue() instanceof DuenoMascota && entryUsuario.getValue().getNombre().equals(gestionGUI.busqueda.getText())) gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                    }
                    break;
                case "GestionDuenoEstablecimiento" :
                    if(gestionGUI.tipoBusqueda.getSelectedItem().equals("Cedula")){
                        if(entryUsuario.getValue() instanceof PropietarioEstablecimiento && entryUsuario.getKey().equals(gestionGUI.busqueda.getText())){
                            gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                            break;
                        }
                    }
                    else{
                        if(entryUsuario.getValue() instanceof PropietarioEstablecimiento && entryUsuario.getValue().getNombre().equals(gestionGUI.busqueda.getText())) gestionGUI.gestionModelTabla.addRow(new Object[]{entryUsuario.getKey(), entryUsuario.getValue().getNombre()});
                    }
                    break;
                case "GestionEstablecimientos":
                    if(gestionGUI.tipoBusqueda.getSelectedItem().equals("Ruc")){
                        if(entryUsuario.getValue() instanceof PropietarioEstablecimiento && ((PropietarioEstablecimiento) entryUsuario.getValue()).isEstablecimiento(gestionGUI.busqueda.getText())){
                            if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                                gestionGUI.gestionModelTabla.addRow(new Object[]{gestionGUI.busqueda.getText(), ((PropietarioEstablecimiento) entryUsuario.getValue()).getEstablecimiento(gestionGUI.busqueda.getText()).getNombreEstablecimiento(), entryUsuario.getValue().getCedula(), entryUsuario.getValue().getNombre()});
                            else
                                gestionGUI.gestionModelTabla.addRow(new Object[]{gestionGUI.busqueda.getText(), ((PropietarioEstablecimiento) entryUsuario.getValue()).getEstablecimiento(gestionGUI.busqueda.getText()).getNombreEstablecimiento()});

                            break;
                        }
                    }
                    else{
                        if(entryUsuario.getValue() instanceof PropietarioEstablecimiento && gestionGUI.busqueda.equals(entryUsuario.getValue().getCedula())){
                            for(Map.Entry<String, Establecimiento> entryEstablecimiento : ((PropietarioEstablecimiento) entryUsuario.getValue()).getEstablecimientos().entrySet())
                                gestionGUI.gestionModelTabla.addRow(new Object[]{entryEstablecimiento.getKey(), entryEstablecimiento.getValue().getNombreEstablecimiento(), entryUsuario.getValue().getCedula(), entryUsuario.getValue().getNombre()});

                            break;
                        }

                    }

                    break;
                case "GestionMascotas":
                    if(gestionGUI.tipoBusqueda.getSelectedItem().equals("Numero chip")){
                        if(entryUsuario.getValue() instanceof DuenoMascota && ((DuenoMascota) entryUsuario.getValue()).isMascota(Integer.parseInt(gestionGUI.busqueda.getText()))){
                            if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                                gestionGUI.gestionModelTabla.addRow(new Object[]{gestionGUI.busqueda.getText(), ((DuenoMascota) entryUsuario.getValue()).getMascota(Integer.parseInt(gestionGUI.busqueda.getText())).getNombre(), entryUsuario.getValue().getCedula(), entryUsuario.getValue().getNombre()});
                            else
                                gestionGUI.gestionModelTabla.addRow(new Object[]{gestionGUI.busqueda.getText(), ((DuenoMascota) entryUsuario.getValue()).getMascota(Integer.parseInt(gestionGUI.busqueda.getText())).getNombre()});

                            break;
                        }
                    }
                    else{
                        if(entryUsuario.getValue() instanceof DuenoMascota && entryUsuario.getKey().equals(gestionGUI.busqueda.getText())) {
                            for(Map.Entry<Integer, Mascota> mascotaEntry : ((DuenoMascota) entryUsuario.getValue()).getMascotas().entrySet())
                                gestionGUI.gestionModelTabla.addRow(new Object[]{mascotaEntry.getKey(), mascotaEntry.getValue().getNombre(), entryUsuario.getValue().getCedula(), entryUsuario.getValue().getNombre()});
                            break;
                        }
                    }

                    break;
            }
        }


    }




    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == gestionGUI.gestionTabla){
            rowNumber = 0;
            rowNumber = gestionGUI.gestionTabla.rowAtPoint(e.getPoint());
            gestionGUI.busqueda.setText(gestionGUI.gestionModelTabla.getValueAt(rowNumber, 0).toString());

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actualizar(Subject sujeto) {
        cargarTablaInformacion();
    }
}
