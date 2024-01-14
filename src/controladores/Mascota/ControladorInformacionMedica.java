package controladores.Mascota;

import modulos.Mascota.InformacionMedica;
import modulos.Mascota.Mascota;
import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;
import vistas.Mascota.InformacionMedicaGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControladorInformacionMedica implements ActionListener, MouseListener, Observer{
    private int rowVacuna;
    private int rowCirugia;
    private Mascota mascota;
    private JFrame vistaAnterior;
    private InformacionMedicaGUI informacionMedicaGUI;

    public ControladorInformacionMedica(Mascota mascota, JFrame vistaAnterior){
        this.mascota = mascota;
        this.vistaAnterior = vistaAnterior;

        mascota.registrarObservador(this);

        setVentana();
        setListeners();
    }


    //Comienzo funciones y metodos
    public void borrarCirugia(){

        mascota.removeInformacionMedica(mascota.getInformacionMedica((String) informacionMedicaGUI.TablaCirugias.getValueAt(rowCirugia, 0), (String) informacionMedicaGUI.TablaCirugias.getValueAt(rowCirugia, 1)));
        informacionMedicaGUI.TablaModeloCirugias.removeRow(rowCirugia);

    }
    public void borrarVacuna(){

        mascota.removeInformacionMedica(mascota.getInformacionMedica((String) informacionMedicaGUI.TablaVacunas.getValueAt(rowVacuna, 0), (String) informacionMedicaGUI.TablaVacunas.getValueAt(rowVacuna, 1)));
        informacionMedicaGUI.TablaModeloVacunas.removeRow(rowVacuna);
    }
    public void setVentana(){
        informacionMedicaGUI = new InformacionMedicaGUI();
        setTablas();
    }

    public void setTablas(){
        informacionMedicaGUI.TablaModeloVacunas.setRowCount(0);
        informacionMedicaGUI.TablaModeloCirugias.setRowCount(0);

        if(mascota.getInformacionMedicas() != null){

            for(InformacionMedica informacionMedica : mascota.getInformacionMedicas()){
                if(informacionMedica.getTipo().equals("Vacuna")) informacionMedicaGUI.TablaModeloVacunas.addRow(new Object[]{informacionMedica.getFecha().toString(), informacionMedica.getDescripcion()});

                else informacionMedicaGUI.TablaModeloCirugias.addRow(new Object[]{informacionMedica.getFecha().toString(), informacionMedica.getDescripcion()});
            }
        }

    }


    public void setListeners(){

        informacionMedicaGUI.atrasBoton.setToolTipText("Volver");
        informacionMedicaGUI.borrarVacunaBoton.setToolTipText("Eliminar Vacuna");
        informacionMedicaGUI.borrarCirugiaBoton.setToolTipText("Eliminar Cirugia");
        informacionMedicaGUI.editarVacunaBoton.setToolTipText("Editar Vacuna");
        informacionMedicaGUI.editarCirugiaBoton.setToolTipText("Editar Cirugia");
        informacionMedicaGUI.agregarVacunaBoton.setToolTipText("Agregar Vacuna");
        informacionMedicaGUI.agregarCirugiaBoton.setToolTipText("Agregar Cirugia");

        informacionMedicaGUI.atrasBoton.addActionListener(this);
        informacionMedicaGUI.editarVacunaBoton.addActionListener(this);
        informacionMedicaGUI.borrarCirugiaBoton.addActionListener(this);
        informacionMedicaGUI.agregarVacunaBoton.addActionListener(this);
        informacionMedicaGUI.editarCirugiaBoton.addActionListener(this);
        informacionMedicaGUI.agregarCirugiaBoton.addActionListener(this);
        informacionMedicaGUI.borrarVacunaBoton.addActionListener(this);

        informacionMedicaGUI.TablaVacunas.addMouseListener(this);
        informacionMedicaGUI.TablaCirugias.addMouseListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == informacionMedicaGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == informacionMedicaGUI.agregarVacunaBoton) ingresarDatos("Vacuna");

        else if(e.getSource() == informacionMedicaGUI.borrarVacunaBoton && informacionMedicaGUI.TablaModeloVacunas.getRowCount() > 0){
            borrarVacuna();
            setTablas();
        }

        else if(e.getSource() == informacionMedicaGUI.agregarCirugiaBoton) ingresarDatos("Cirugia");

        else if(e.getSource() == informacionMedicaGUI.borrarCirugiaBoton && informacionMedicaGUI.TablaModeloCirugias.getRowCount() > 0){
            borrarCirugia();
            setTablas();
        }

        else if(e.getSource() == informacionMedicaGUI.editarVacunaBoton && informacionMedicaGUI.TablaModeloVacunas.getRowCount() > 0) editar("Vacuna", rowVacuna);

        else if(e.getSource() == informacionMedicaGUI.editarCirugiaBoton && informacionMedicaGUI.TablaModeloCirugias.getRowCount() > 0) editar("Cirugia", rowCirugia);

    }

    public void editar(String tipoInformacion, int rowNumber){

        new ControladorIngresoInformacionMedica(mascota, mascota.getInformacionMedica(informacionMedicaGUI.TablaModeloVacunas.getValueAt(rowNumber, 0).toString(), informacionMedicaGUI.TablaModeloVacunas.getValueAt(rowNumber, 1).toString()), tipoInformacion, informacionMedicaGUI);
    }


    public void cerrarControlador(){
        informacionMedicaGUI.cerrarVentana();
        vistaAnterior.setVisible(true);
    }

    public void ingresarDatos(String tipoInformacion){
        informacionMedicaGUI.setVisible(false);
        new ControladorIngresoInformacionMedica(mascota, new InformacionMedica(), tipoInformacion, informacionMedicaGUI);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == informacionMedicaGUI.TablaVacunas){
            rowVacuna = 0;
            rowVacuna = informacionMedicaGUI.TablaVacunas.rowAtPoint(e.getPoint());
        }


        else if(e.getSource() == informacionMedicaGUI.TablaCirugias){
            rowCirugia = 0;
            rowCirugia = informacionMedicaGUI.TablaCirugias.rowAtPoint(e.getPoint());
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
        setTablas();
    }
}
