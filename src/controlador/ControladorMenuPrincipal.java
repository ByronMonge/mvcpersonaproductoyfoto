package controlador;

import modelo.ModeloPersona;
import modelo.ModeloProducto;
import vista.VistaPersonas;
import vista.VistaPrincipal;
import vista.VistaProducto;

public class ControladorMenuPrincipal {

    VistaPrincipal vistaPrincipal;

    public ControladorMenuPrincipal(VistaPrincipal vistaprincipal) {
        this.vistaPrincipal = vistaprincipal;
        vistaprincipal.setVisible(true);
    }

    public void iniciaControl() {
        vistaPrincipal.getMnuPersonas().addActionListener(l -> crudPersonas());
        vistaPrincipal.getBtnPersonas().addActionListener(l -> crudPersonas());
        vistaPrincipal.getBtnproductos().addActionListener(l-> crudProductos());
    }

    private void crudPersonas() {
        //Instancio las clases del Modelo y la Vista.
        VistaPersonas vista = new VistaPersonas();
        ModeloPersona modelo = new ModeloPersona();

        //Agregar Vista Personas al Desktop Pane.
        vistaPrincipal.getEscritorio().add(vista);

        ControladorPersona control = new ControladorPersona(modelo, vista);
        control.iniciarControl();//Empezamos las escuchas a los eventos de la vista, Listeners.
    }

    private void crudProductos() {
        //Instancio las clases del Modelo y la Vista.
        VistaProducto vista = new VistaProducto();
        ModeloProducto modelo = new ModeloProducto();

        //Agregar Vista Personas al Desktop Pane.
        vistaPrincipal.getEscritorio().add(vista);

        ControladorProducto control = new ControladorProducto(modelo, vista);
        control.iniciarControl();//Empezamos las escuchas a los eventos de la vista, Listeners.
    }

    public void ControladorPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        vistaPrincipal.setVisible(true);
    }
}
