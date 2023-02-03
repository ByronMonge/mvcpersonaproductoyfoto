package controlador;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import modelo.ModeloProducto;
import modelo.Producto;
import vista.VistaProducto;

public class ControladorProducto {
    
    ModeloProducto modelo;
    VistaProducto vista;
    
    private JFileChooser jfc; //Objeto de tipo JFileChooser

    public ControladorProducto(ModeloProducto modelo, VistaProducto vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);//Aprovecho el constructor para hacer visible la vista 
        cargarProductosTabla(); //Carga los datos al iniciar la ventana
    }
    
    public void iniciarControl() {
        vista.getBtnActualizar().addActionListener(l -> cargarProductosTabla());
        vista.getBtnCrear().addActionListener(l -> abrirDialogCrear());
        vista.getBtnEditar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        vista.getBtnAceptar().addActionListener(l -> crearEditarProducto());
        vista.getBtnExaminar().addActionListener(l -> seleccionarFoto());
        vista.getBtnEliminar().addActionListener(l -> eliminarProducto());
        buscarPersona();//Llama al metodo de "buscarPersona"
    }
    
    public void cargarProductosTabla() {
        vista.getTbProducto().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
        vista.getTbProducto().setRowHeight(100);

        //Enlazar el modelo de tabla con mi controlador.
        DefaultTableModel tblModel;
        tblModel = (DefaultTableModel) vista.getTbPersona().getModel();
        tblModel.setNumRows(0);//limpio filas de la tabla.

        List<Producto> listap = modelo.listaProductosTabla();//Enlazo al Modelo y obtengo los datos
        Holder<Integer> i = new Holder<>(0);//Contador para las filas. 'i' funciona dentro de una expresion lambda

        listap.stream().forEach(pe -> {
            
            tblModel.addRow(new Object[9]);//Creo una fila vacia
            vista.getTbProducto().setValueAt(pe.getIdProducto(), i.value, 0);
            vista.getTbProducto().setValueAt(pe.getNombre(), i.value, 1);
            vista.getTbProducto().setValueAt(pe.getPrecio(), i.value, 2);
            vista.getTbProducto().setValueAt(pe.getCantidad(), i.value, 3);
            vista.getTbProducto().setValueAt(pe.getDescripcion(), i.value, 4);
            
            Image foto = pe.getImagen();
            if (foto != null) {
                
                Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon icono = new ImageIcon(nimg);
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setIcon(icono);
                vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 5);
                
            } else {
                vista.getTbPersona().setValueAt(null, i.value, 5);
            }
            
            i.value++;
        });
    }
    
    public void abrirDialogCrear() {
        vista.getjDlgProductos().setName("Crear nuevo producto");
        vista.getjDlgProductos().setLocationRelativeTo(vista);
        vista.getjDlgProductos().setSize(546, 486);
        vista.getjDlgProductos().setTitle("Crear nuevo producto");
        vista.getjDlgProductos().setVisible(true);

        //Limpiar los datos del jDialog
        limpiarDatos();
    }
    
    private void crearEditarProducto() {
        if ("Crear nuevo producto".equals(vista.getjDlgProductos().getName())) {

            //INSERTAR
            String nombre = vista.getTxtNombres().getText();
            double precio = (Double) vista.getSpinnerPrecio().getValue();
            int cantidad = (Integer) vista.getSpinnerCantidad().getValue();
            String descripcion = vista.getTxtarea().getText();
            
            ModeloProducto producto = new ModeloProducto();
            
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setDescripcion(descripcion);
            
            if (vista.getLabelFoto().getIcon() == null) { //Verifico si el label esta vacio o no

                if (producto.crearProductoSinFoto()) {
                    vista.getjDlgProductos().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto Creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el producto");
                }
                
            } else {

                //Foto
                try {
                    
                    FileInputStream foto = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();
                    
                    producto.setFoto(foto);
                    producto.setLongitud(longitud);
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorPersona.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (producto.crearProductoFoto()) {
                    vista.getjDlgProductos().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto Creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear el producto");
                }
            }
            
        } else {

            //EDITAR
            int codigo = Integer.parseInt(vista.getTxtIdentificacion().getText());
            String nombre = vista.getTxtNombres().getText();
            double precio = (Double) vista.getSpinnerPrecio().getValue();
            int cantidad = (Integer) vista.getSpinnerCantidad().getValue();
            String descripcion = vista.getTxtarea().getText();
            
            ModeloProducto producto = new ModeloProducto();
            
            producto.setIdProducto(codigo);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setDescripcion(descripcion);
            
            if (vista.getLabelFoto().getIcon() == null) {
                if (producto.modificarProductoSinFoto()) {
                    
                    vista.getjDlgProductos().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar el producto");
                }
            } else {

                //Foto
                try {
                    
                    FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();
                    producto.setFoto(img);
                    producto.setLongitud(longitud);
                } catch (FileNotFoundException | NullPointerException ex) {
                    Logger.getLogger(ControladorPersona.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (producto.modificarPersonaFoto()) {
                    
                    vista.getjDlgProductos().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Producto creado Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar el producto");
                }
            }
        }
        
        cargarProductosTabla(); //Actualizo la tabla con los datos
    }
    
    public void seleccionarFoto() {
        
        vista.getLabelFoto().setIcon(null);
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = jfc.showOpenDialog(null);
        
        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                Image imagen = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(vista.getLabelFoto().getWidth(), vista.getLabelFoto().getHeight(), Image.SCALE_DEFAULT);
                vista.getLabelFoto().setIcon(new ImageIcon(imagen));
                vista.getLabelFoto().updateUI();
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex);
            }
        }
    }
    
    public void eliminarProducto() {
        
        int fila = vista.getTbPersona().getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {
            
            int response = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar esta información?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                
                int codigo;
                codigo = (Integer) vista.getTbPersona().getValueAt(fila, 0);
                
                if (modelo.eliminarProducto(codigo)) {
                    JOptionPane.showMessageDialog(null, "El producto fue eliminado exitosamente");
                    cargarProductosTabla();//Actualizo la tabla con los datos
                } else {
                    JOptionPane.showMessageDialog(null, "Error: El producto no se pudo eliminar");
                }
            }
        }
        
    }
    
    public void abrirYCargarDatosEnElDialog() {
        
        int seleccion = vista.getTbPersona().getSelectedRow();
        
        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {
            
            int codigo = (Integer) vista.getTbPersona().getValueAt(seleccion, 0);
            modelo.listaProductoJDialog().forEach((pe) -> {
                if (pe.getIdProducto() == codigo) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getjDlgProductos().setName("Editar");
                    vista.getjDlgProductos().setLocationRelativeTo(vista);
                    vista.getjDlgProductos().setSize(546, 486);
                    vista.getjDlgProductos().setTitle("Editar");
                    vista.getjDlgProductos().setVisible(true);
                    
                    vista.getTxtIdentificacion().setText(String.valueOf(pe.getIdProducto()));
                    vista.getTxtNombres().setText(pe.getNombre());
                    vista.getSpinnerPrecio().setValue(pe.getPrecio());
                    vista.getSpinnerCantidad().setValue(pe.getCantidad());
                    vista.getTxtarea().setText(pe.getDescripcion());
                    
                    vista.getLabelFoto().setIcon(modelo.ConsultarFotoJDialog(codigo)); //Llamo al metodo 'ConsultarFoto' del modelo
                }
            });
        }
    }
    
    public void buscarPersona() {
        
        KeyListener eventoTeclado = new KeyListener() {//Crear un objeto de tipo keyListener(Es una interface) por lo tanto se debe implementar sus metodos abstractos

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                
                vista.getTbProducto().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
                vista.getTbProducto().setRowHeight(100);

                //Enlazar el modelo de tabla con mi controlador.
                DefaultTableModel tblModel;
                tblModel = (DefaultTableModel) vista.getTbPersona().getModel();
                tblModel.setNumRows(0);//limpio filas de la tabla.

                List<Producto> listap = modelo.buscarProducto(vista.getTxtBuscar().getText());//Enlazo al Modelo y obtengo los datos
                Holder<Integer> i = new Holder<>(0);//Contador para las filas. 'i' funciona dentro de una expresion lambda

                listap.stream().forEach(pe -> {
                    
                    tblModel.addRow(new Object[9]);//Creo una fila vacia
                    vista.getTbProducto().setValueAt(pe.getIdProducto(), i.value, 0);
                    vista.getTbProducto().setValueAt(pe.getNombre(), i.value, 1);
                    vista.getTbProducto().setValueAt(pe.getPrecio(), i.value, 2);
                    vista.getTbProducto().setValueAt(pe.getCantidad(), i.value, 3);
                    vista.getTbProducto().setValueAt(pe.getDescripcion(), i.value, 4);
                    
                    Image foto = pe.getImagen();
                    if (foto != null) {
                        
                        Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icono = new ImageIcon(nimg);
                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        renderer.setIcon(icono);
                        vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 5);
                        
                    } else {
                        vista.getTbPersona().setValueAt(null, i.value, 5);
                    }
                    
                    i.value++;
                });
            }
        };
        
        vista.getTxtBuscar().addKeyListener(eventoTeclado); //"addKeyListener" es un metodo que se le tiene que pasar como argumento un objeto de tipo keyListener 
    }
    
    public void limpiarDatos() {
        vista.getTxtIdentificacion().setText("");
        vista.getTxtNombres().setText("");
        vista.getSpinnerPrecio().setValue(0);
        vista.getSpinnerCantidad().setValue(0);
        vista.getTxtarea().setText("");
        vista.getLabelFoto().setIcon(null);
        
        vista.getTxtIdentificacion().setVisible(false);
        vista.getLblCodigo().setVisible(false);
    }
    
}
