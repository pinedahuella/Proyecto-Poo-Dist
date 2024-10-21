package Login;

/**
 * Clase Login que representa un registro de inicio de sesión.
 * Contiene información sobre el tiempo de entrada, tiempo de salida,
 * el personal que inicia sesión y su rol.
 */
public class Login {
    private String tiempoEntrada; // Hora de entrada del personal
    private String tiempoSalida; // Hora de salida del personal
    private String personal; // Nombre del personal
    private String rol; // Rol del personal

    // Constructor que inicializa los atributos de la clase
    public Login(String tiempoEntrada, String tiempoSalida, String personal, String rol) {
        this.tiempoEntrada = tiempoEntrada; // Asigna el tiempo de entrada
        this.tiempoSalida = tiempoSalida; // Asigna el tiempo de salida
        this.personal = personal; // Asigna el nombre del personal
        this.rol = rol; // Asigna el rol del personal
    }

    // Getter para obtener el tiempo de entrada
    public String getTiempoEntrada() {
        return tiempoEntrada;
    }

    // Setter para establecer el tiempo de entrada
    public void setTiempoEntrada(String tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }

    // Getter para obtener el tiempo de salida
    public String getTiempoSalida() {
        return tiempoSalida;
    }

    // Setter para establecer el tiempo de salida
    public void setTiempoSalida(String tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    // Getter para obtener el nombre del personal
    public String getPersonal() {
        return personal;
    }

    // Setter para establecer el nombre del personal
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    // Getter para obtener el rol del personal
    public String getRol() {
        return rol;
    }

    // Setter para establecer el rol del personal
    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método toString que devuelve una representación en forma de cadena del objeto Login
    @Override
    public String toString() {
        return "Login{" + 
               "tiempoEntrada=" + tiempoEntrada + 
               ", tiempoSalida=" + tiempoSalida + 
               ", personal=" + personal + 
               ", rol=" + rol + 
               '}'; // Devuelve la representación del objeto en formato de texto
    }
}
