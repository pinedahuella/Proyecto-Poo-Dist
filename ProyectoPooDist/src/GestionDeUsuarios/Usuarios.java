package GestionDeUsuarios;

/**
 * Clase que representa a un usuario del sistema.
 * Almacena información relevante como credenciales, datos personales y estado.
 */
public class Usuarios {
    // Atributos de la clase
    private String nombreUsuario;      // Nombre de usuario para el acceso
    private String contrasenaUsuario;   // Contraseña asociada al usuario
    private String nombre;              // Nombre del usuario
    private String apellido;            // Apellido del usuario
    private String cargo;               // Cargo o puesto del usuario
    private String genero;              // Género del usuario
    private long numeroDPI;             // Número de DPI del usuario
    private String fechaNacimiento;     // Fecha de nacimiento del usuario
    private int numeroTelefono;          // Número telefónico del usuario
    private String correoElectronico;   // Correo electrónico del usuario
    private String estado;              // Estado del usuario (activo, inactivo, etc.)

    /**
     * Constructor que inicializa todos los atributos de la clase.
     *
     * @param nombreUsuario Nombre de usuario.
     * @param contrasenaUsuario Contraseña del usuario.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param cargo Cargo del usuario.
     * @param genero Género del usuario.
     * @param numeroDPI Número de DPI del usuario.
     * @param fechaNacimiento Fecha de nacimiento del usuario.
     * @param numeroTelefono Número telefónico del usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param estado Estado del usuario.
     */
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

    // Métodos de acceso (getters y setters) para los atributos

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

    /**
     * Devuelve una representación en cadena del objeto Usuarios.
     * 
     * @return String que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuarios{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenaUsuario='" + contrasenaUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cargo='" + cargo + '\'' +
                ", genero='" + genero + '\'' +
                ", numeroDPI=" + numeroDPI +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", numeroTelefono=" + numeroTelefono +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
