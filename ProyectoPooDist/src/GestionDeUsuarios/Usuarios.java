package GestionDeUsuarios;

import java.util.Date;

public class Usuarios {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String nombre;
    private String apellido;
    private String cargo;
    private String genero;
    private long numeroDPI;
    private String fechaNacimiento;
    private int numeroTelefono;
    private String correoElectronico;
    private String estado;

    public Usuarios(String nombreUsuario, String contrasenaUsuario, String nombre, String apellido, 
                    String cargo, String genero, long numeroDPI, String fechaNacimiento, 
                    int numeroTelefono, String correoElectronico, String estado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.genero = genero;
        this.numeroDPI = numeroDPI;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.estado = estado;
    }


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getNumeroDPI() {
        return numeroDPI;
    }

    public void setNumeroDPI(long numeroDPI) {
        this.numeroDPI = numeroDPI;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "nombreUsuario=" + nombreUsuario + ", contrasenaUsuario=" + contrasenaUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", cargo=" + cargo + ", genero=" + genero + ", numeroDPI=" + numeroDPI + ", fechaNacimiento=" + fechaNacimiento + ", numeroTelefono=" + numeroTelefono + ", correoElectronico=" + correoElectronico + ", estado=" + estado + '}';
    }
}