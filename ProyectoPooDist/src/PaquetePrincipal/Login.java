package PaquetePrincipal;

public class Login {
    private String tiempoEntrada;
    private String tiempoSalida;
    private String personal;
    private String rol;

    // Constructor
    public Login(String tiempoEntrada, String tiempoSalida, String personal, String rol) {
        this.tiempoEntrada = tiempoEntrada;
        this.tiempoSalida = tiempoSalida;
        this.personal = personal;
        this.rol = rol;
    }

    // Getter para tiempoEntrada
    public String getTiempoEntrada() {
        return tiempoEntrada;
    }

    // Setter para tiempoEntrada
    public void setTiempoEntrada(String tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }

    // Getter para tiempoSalida
    public String getTiempoSalida() {
        return tiempoSalida;
    }

    // Setter para tiempoSalida
    public void setTiempoSalida(String tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    // Getter para personal
    public String getPersonal() {
        return personal;
    }

    // Setter para personal
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    // Getter para rol
    public String getRol() {
        return rol;
    }

    // Setter para rol
    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Login{" + "tiempoEntrada=" + tiempoEntrada + ", tiempoSalida=" + tiempoSalida + ", personal=" + personal + ", rol=" + rol + '}';
    }
    
    
    
}