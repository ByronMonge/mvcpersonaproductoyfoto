package modelo;

import java.awt.Image;
import java.io.FileInputStream;
import java.sql.Date;

public class Persona {

    private String idPersona;
    private String nombre;
    private String apellido;
    private String sexo;
    private Date fechaDeNacimiento;
    private String telefono;
    private double sueldo;
    private int cupo;
    private String correo;

    //Guardar foto
    private FileInputStream foto;
    private int longitud;

    //Recuperar imagen
    private Image imagen;

    public Persona() {
    }

    public Persona(String idPersona, String nombre, String apellido, String sexo, Date fechaDeNacimiento, String telefono, double sueldo, int cupo, String correo) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.cupo = cupo;
        this.correo = correo;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
}
