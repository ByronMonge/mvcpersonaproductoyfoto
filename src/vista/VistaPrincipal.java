
package vista;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author monge
 */
public class VistaPrincipal extends javax.swing.JFrame {


    public VistaPrincipal() {
        initComponents();
    }

    public JButton getBtnproductos() {
        return btnproductos;
    }

    public void setBtnproductos(JButton btnproductos) {
        this.btnproductos = btnproductos;
    }

    public JDesktopPane getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(JDesktopPane escritorio) {
        this.escritorio = escritorio;
    }

    public JMenuItem getMnuCrear() {
        return mnItmCrearPersona;
    }

    public void setMnuCrear(JMenuItem mnuCrear) {
        this.mnItmCrearPersona = mnuCrear;
    }

    public JMenuItem getMnuEditar() {
        return mnItmEditarPersona;
    }

    public void setMnuEditar(JMenuItem mnuEditar) {
        this.mnItmEditarPersona = mnuEditar;
    }

    public JMenu getMnuPersonas() {
        return mnuPersonas;
    }

    public void setMnuPersonas(JMenu mnuPersonas) {
        this.mnuPersonas = mnuPersonas;
    }

    public JMenu getMnuProductos() {
        return mnuProductos;
    }

    public void setMnuProductos(JMenu mnuProductos) {
        this.mnuProductos = mnuProductos;
    }

    public JButton getBtnPersonas() {
        return btnPersonas;
    }

    public void setBtnPersonas(JButton btnPersonas) {
        this.btnPersonas = btnPersonas;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnPersonas = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnproductos = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        escritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuPersonas = new javax.swing.JMenu();
        mnItmCrearPersona = new javax.swing.JMenuItem();
        mnItmEditarPersona = new javax.swing.JMenuItem();
        mnuProductos = new javax.swing.JMenu();
        mnuVentas = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        btnPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/icons/agregar-usuario.png"))); // NOI18N
        btnPersonas.setToolTipText("Crear persona");
        btnPersonas.setFocusable(false);
        btnPersonas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPersonas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnPersonas);
        jToolBar1.add(jSeparator1);

        btnproductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/icons/productos-lacteos.png"))); // NOI18N
        btnproductos.setFocusable(false);
        btnproductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnproductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnproductos);

        jPanel1.setBackground(new java.awt.Color(255, 255, 102));

        jLabel1.setText("Tienda v1.1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(798, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );

        mnuPersonas.setText("Personas");

        mnItmCrearPersona.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        mnItmCrearPersona.setText("Crear persona");
        mnuPersonas.add(mnItmCrearPersona);

        mnItmEditarPersona.setText("Editar persona");
        mnuPersonas.add(mnItmEditarPersona);

        jMenuBar1.add(mnuPersonas);

        mnuProductos.setText("Productos ");
        jMenuBar1.add(mnuProductos);

        mnuVentas.setText("Ventas");
        jMenuBar1.add(mnuVentas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(escritorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPersonas;
    private javax.swing.JButton btnproductos;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mnItmCrearPersona;
    private javax.swing.JMenuItem mnItmEditarPersona;
    private javax.swing.JMenu mnuPersonas;
    private javax.swing.JMenu mnuProductos;
    private javax.swing.JMenu mnuVentas;
    // End of variables declaration//GEN-END:variables
}
