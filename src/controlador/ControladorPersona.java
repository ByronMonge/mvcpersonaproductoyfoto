package controlador;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
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
import modelo.ModeloPersona;
import modelo.Persona;
import vista.VistaPersonas;

public class ControladorPersona {

    private ModeloPersona modelo;
    private VistaPersonas vista;

    private JFileChooser jfc; //Objeto de tipo JFileChooser

    public ControladorPersona(ModeloPersona modelo, VistaPersonas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);//Aprovecho el constructor para hacer visible la vista 
        cargarPersonasTabla(); //Carga los datos al iniciar la ventana
    }

    public void cargarPersonasTabla() {
        vista.getTbPersona().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
        vista.getTbPersona().setRowHeight(100);

        //Enlazar el modelo de tabla con mi controlador.
        DefaultTableModel tblModel;
        tblModel = (DefaultTableModel) vista.getTbPersona().getModel();
        tblModel.setNumRows(0);//limpio filas de la tabla.

        List<Persona> listap = modelo.listaPersonasTabla();//Enlazo al Modelo y obtengo los datos
        Holder<Integer> i = new Holder<>(0);//Contador para las filas. 'i' funciona dentro de una expresion lambda

        listap.stream().forEach(pe -> {

            tblModel.addRow(new Object[9]);//Creo una fila vacia
            vista.getTbPersona().setValueAt(pe.getIdPersona(), i.value, 0);
            vista.getTbPersona().setValueAt(pe.getNombre(), i.value, 1);
            vista.getTbPersona().setValueAt(pe.getApellido(), i.value, 2);
            vista.getTbPersona().setValueAt(pe.getSexo(), i.value, 3);
            vista.getTbPersona().setValueAt(pe.getFechaDeNacimiento(), i.value, 4);
            vista.getTbPersona().setValueAt(pe.getTelefono(), i.value, 5);
            vista.getTbPersona().setValueAt(pe.getCorreo(), i.value, 6);
            vista.getTbPersona().setValueAt(pe.getSueldo(), i.value, 7);
            vista.getTbPersona().setValueAt(pe.getCupo(), i.value, 8);

            Image foto = pe.getImagen();
            if (foto != null) {

                Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon icono = new ImageIcon(nimg);
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setIcon(icono);
                vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 9);

            } else {
                vista.getTbPersona().setValueAt(null, i.value, 9);
            }

            i.value++;
        });
    }

    public void iniciarControl() {
        vista.getBtnActualizar().addActionListener(l -> cargarPersonasTabla());
        vista.getBtnCrear().addActionListener(l -> abrirDialogCrear());
        vista.getBtnEditar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        vista.getBtnAceptar().addActionListener(l -> crearEditarPersona());
        vista.getBtnExaminar().addActionListener(l -> seleccionarFoto());
        vista.getBtnEliminar().addActionListener(l -> eliminarPersona());
        buscarPersona();//Llama al metodo de "buscarPersona"
    }

    public void abrirDialogCrear() {
        vista.getDlgPersona().setName("Crear nueva persona");
        vista.getDlgPersona().setLocationRelativeTo(vista);
        vista.getDlgPersona().setSize(1100, 500);
        vista.getDlgPersona().setTitle("Crear nueva persona");
        vista.getDlgPersona().setVisible(true);

        //Limpiar los datos del jDialog
        limpiarDatos();
    }

    private void crearEditarPersona() {
        if ("Crear nueva persona".equals(vista.getDlgPersona().getName())) {

            //INSERTAR
            String cedula = vista.getTxtIdentificacion().getText();
            String nombres = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellidos().getText();

            String sexo;
            if (vista.getRbMasculino().isSelected()) {
                sexo = "Masculino";
            } else {
                if (vista.getRbFemenino().isSelected()) {
                    sexo = "Femenino";
                } else {
                    sexo = "null";
                }
            }

            String telefono = vista.getTxtTelefono().getText();
            Date fecha = vista.getjDateFecha().getDate();
            double sueldo = Double.parseDouble(vista.getSpinnerSueldo().getValue().toString());
            int cupo = Integer.parseInt(vista.getSpinnerCupo().getValue().toString());
            String correo = vista.getTxtCorreo().getText();

            ModeloPersona persona = new ModeloPersona();
            persona.setIdPersona(cedula);
            persona.setNombre(nombres);
            persona.setApellido(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechaDeNacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);
            persona.setCorreo(correo);

            if (vista.getLabelFoto().getIcon() == null) { //Verifico si el label esta vacio o no

                if (persona.crearPersonaSinFoto()) {
                    vista.getDlgPersona().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Persona Creada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear la persona");
                }

            } else {

                //Foto
                try {

                    FileInputStream foto = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();

                    persona.setFoto(foto);
                    persona.setLongitud(longitud);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorPersona.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (persona.crearPersonaFoto()) {
                    vista.getDlgPersona().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Persona Creada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo crear la persona");
                }
            }

        } else {

            //EDITAR
            String cedula = vista.getTxtIdentificacion().getText();
            String nombres = vista.getTxtNombres().getText();
            String apellidos = vista.getTxtApellidos().getText();

            String sexo;
            if (vista.getRbMasculino().isSelected()) {
                sexo = "Masculino";
            } else {
                if (vista.getRbFemenino().isSelected()) {
                    sexo = "Femenino";
                } else {
                    sexo = "null";
                }
            }

            String telefono = vista.getTxtTelefono().getText();
            Date fecha = vista.getjDateFecha().getDate();
            double sueldo = Double.parseDouble(vista.getSpinnerSueldo().getValue().toString());
            int cupo = Integer.parseInt(vista.getSpinnerCupo().getValue().toString());
            String correo = vista.getTxtCorreo().getText();

            ModeloPersona persona = new ModeloPersona();
            persona.setIdPersona(cedula);
            persona.setNombre(nombres);
            persona.setApellido(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechaDeNacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);
            persona.setCorreo(correo);

            if (vista.getLabelFoto().getIcon() == null) {
                if (persona.modificarPersonaSinFoto()) {

                    vista.getDlgPersona().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Persona Modificada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar la persona");
                }
            } else {

                //Foto
                try {

                    FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                    int longitud = (int) jfc.getSelectedFile().length();
                    persona.setFoto(img);
                    persona.setLongitud(longitud);
                } catch (FileNotFoundException | NullPointerException ex) {
                    Logger.getLogger(ControladorPersona.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (persona.modificarPersonaFoto()) {

                    vista.getDlgPersona().setVisible(false);
                    JOptionPane.showMessageDialog(vista, "Persona Modificada Satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudo modificar la persona");
                }
            }
        }

        cargarPersonasTabla(); //Actualizo la tabla con los datos
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

    public void eliminarPersona() {

        int fila = vista.getTbPersona().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            int response = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar esta información?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {

                String cedula;
                cedula = vista.getTbPersona().getValueAt(fila, 0).toString();

                if (modelo.eliminarPersona(cedula)) {
                    JOptionPane.showMessageDialog(null, "La persona fue eliminada exitosamente");
                    cargarPersonasTabla();//Actualizo la tabla con los datos
                } else {
                    JOptionPane.showMessageDialog(null, "Error: La persona no se pudo eliminar");
                }
            }
        }

    }

    public void abrirYCargarDatosEnElDialog() {

        int seleccion = vista.getTbPersona().getSelectedRow();

        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            String cedula = vista.getTbPersona().getValueAt(seleccion, 0).toString();
            modelo.listaPersonasJDialog().forEach((pe) -> {
                if (pe.getIdPersona().equals(cedula)) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getDlgPersona().setName("Editar");
                    vista.getDlgPersona().setLocationRelativeTo(vista);
                    vista.getDlgPersona().setSize(1100, 500);
                    vista.getDlgPersona().setTitle("Editar");
                    vista.getDlgPersona().setVisible(true);

                    vista.getTxtIdentificacion().setText(pe.getIdPersona());
                    vista.getTxtNombres().setText(pe.getNombre());
                    vista.getTxtApellidos().setText(pe.getApellido());

                    if (pe.getSexo().equalsIgnoreCase("Masculino")) {
                        vista.getRbMasculino().setSelected(true);
                    } else {
                        if (pe.getSexo().equalsIgnoreCase("Femenino")) {
                            vista.getRbFemenino().setSelected(true);
                        }
                    }

                    vista.getTxtTelefono().setText(pe.getTelefono());
                    vista.getTxtCorreo().setText(pe.getCorreo());
                    vista.getjDateFecha().setDate(pe.getFechaDeNacimiento());
                    vista.getSpinnerSueldo().setValue(pe.getSueldo());
                    vista.getSpinnerCupo().setValue(pe.getCupo());
                    vista.getLabelFoto().setIcon(modelo.ConsultarFoto(cedula)); //Llamo al metodo 'ConsultarFoto' del modelo
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

                vista.getTbPersona().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
                vista.getTbPersona().setRowHeight(100);

                //Enlazar el modelo de tabla con mi controlador.
                DefaultTableModel tblModel;
                tblModel = (DefaultTableModel) vista.getTbPersona().getModel();
                tblModel.setNumRows(0);//limpio filas de la tabla.

                List<Persona> listap = modelo.buscarPersonas(vista.getTxtBuscar().getText());//Enlazo al Modelo y obtengo los datos
                Holder<Integer> i = new Holder<>(0);//contador para el no. fila
                listap.stream().forEach(pe -> {

                    tblModel.addRow(new Object[9]);//Creo una fila vacia/
                    vista.getTbPersona().setValueAt(pe.getIdPersona(), i.value, 0);
                    vista.getTbPersona().setValueAt(pe.getNombre(), i.value, 1);
                    vista.getTbPersona().setValueAt(pe.getApellido(), i.value, 2);
                    vista.getTbPersona().setValueAt(pe.getSexo(), i.value, 3);
                    vista.getTbPersona().setValueAt(pe.getFechaDeNacimiento(), i.value, 4);
                    vista.getTbPersona().setValueAt(pe.getTelefono(), i.value, 5);
                    vista.getTbPersona().setValueAt(pe.getCorreo(), i.value, 6);
                    vista.getTbPersona().setValueAt(pe.getSueldo(), i.value, 7);
                    vista.getTbPersona().setValueAt(pe.getCupo(), i.value, 8);

                    Image foto = pe.getImagen();
                    if (foto != null) {

                        Image nimg = foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icono = new ImageIcon(nimg);
                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        renderer.setIcon(icono);
                        vista.getTbPersona().setValueAt(new JLabel(icono), i.value, 9);

                    } else {
                        vista.getTbPersona().setValueAt(null, i.value, 9);
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
        vista.getTxtApellidos().setText("");
        vista.getSexo().clearSelection();
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getjDateFecha().setDate(null);
        vista.getSpinnerSueldo().setValue(0);
        vista.getSpinnerCupo().setValue(0);
        vista.getLabelFoto().setIcon(null);
    }
}
