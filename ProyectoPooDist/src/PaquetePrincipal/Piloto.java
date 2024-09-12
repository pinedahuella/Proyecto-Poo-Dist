package PaquetePrincipal;






public class Piloto {

    private String nombrePiloto;
    private String apellidoPiloto;
    private long numeroDeDpi;
    private String tipoLicencia;
    private String correoElectronicoPiloto;
    private int numeroTelefonicoPiloto;
    private String generoPiloto;
    private String fechaDeNacimiento;
    private String estadoPiloto;

    // Constructor por defecto
    public Piloto() {}

    // Constructor con parámetros
    public Piloto(String nombrePiloto, String apellidoPiloto, long numeroDeDpi, String tipoLicencia,
                  String correoElectronicoPiloto, int numeroTelefonicoPiloto, String generoPiloto,
                  String fechaDeNacimiento, String estadoPiloto) {
        this.nombrePiloto = nombrePiloto;
        this.apellidoPiloto = apellidoPiloto;
        this.numeroDeDpi = numeroDeDpi;
        this.tipoLicencia = tipoLicencia;
        this.correoElectronicoPiloto = correoElectronicoPiloto;
        this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
        this.generoPiloto = generoPiloto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.estadoPiloto = estadoPiloto;
    }

    // Métodos setters
    public void setNombrePiloto(String nombrePiloto) {
        this.nombrePiloto = nombrePiloto;
    }

    public void setApellidoPiloto(String apellidoPiloto) {
        this.apellidoPiloto = apellidoPiloto;
    }

    public void setNumeroDeDpi(long numeroDeDpi) {
        this.numeroDeDpi = numeroDeDpi;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public void setCorreoElectronicoPiloto(String correoElectronicoPiloto) {
        this.correoElectronicoPiloto = correoElectronicoPiloto;
    }

    public void setNumeroTelefonicoPiloto(int numeroTelefonicoPiloto) {
        this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
    }

    public void setGeneroPiloto(String generoPiloto) {
        this.generoPiloto = generoPiloto;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setEstadoPiloto(String estadoPiloto) {
        this.estadoPiloto = estadoPiloto;
    }

    // Métodos getters
    public String getNombrePiloto() {
        return this.nombrePiloto;
    }

    public String getApellidoPiloto() {
        return this.apellidoPiloto;
    }

    public long getNumeroDeDpi() {
        return this.numeroDeDpi;
    }

    public String getTipoLicencia() {
        return this.tipoLicencia;
    }

    public String getCorreoElectronicoPiloto() {
        return this.correoElectronicoPiloto;
    }

    public int getNumeroTelefonicoPiloto() {
        return this.numeroTelefonicoPiloto;
    }

    public String getGeneroPiloto() {
        return this.generoPiloto;
    }

    public String getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }

    public String getEstadoPiloto() {
        return this.estadoPiloto;
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "nombrePiloto='" + nombrePiloto + '\'' +
                ", apellidoPiloto='" + apellidoPiloto + '\'' +
                ", numeroDeDpi=" + numeroDeDpi +
                ", tipoLicencia='" + tipoLicencia + '\'' +
                ", correoElectronicoPiloto='" + correoElectronicoPiloto + '\'' +
                ", numeroTelefonicoPiloto=" + numeroTelefonicoPiloto +
                ", generoPiloto='" + generoPiloto + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", estadoPiloto='" + estadoPiloto + '\'' +
                '}';
    
    }
}