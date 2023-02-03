package mvc;

import controlador.ControladorMenuPrincipal;
import vista.VistaPrincipal;

/**
 *
 * @author monge
 */
public class Mvc {

    public static void main(String[] args) {

        VistaPrincipal vistaPrincipal = new VistaPrincipal();

        ControladorMenuPrincipal control = new ControladorMenuPrincipal(vistaPrincipal);
        control.iniciaControl();

    }
}
