package GestionDePilotos;

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
    private boolean activo;



    public Piloto() {}

    public Piloto(String nombrePiloto, String apellidoPiloto, long numeroDeDpi, String tipoLicencia, String correoElectronicoPiloto, int numeroTelefonicoPiloto, String generoPiloto, String fechaDeNacimiento, String estadoPiloto, boolean activo) {
        this.nombrePiloto = nombrePiloto;
        this.apellidoPiloto = apellidoPiloto;
        this.numeroDeDpi = numeroDeDpi;
        this.tipoLicencia = tipoLicencia;
        this.correoElectronicoPiloto = correoElectronicoPiloto;
        this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
        this.generoPiloto = generoPiloto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.estadoPiloto = estadoPiloto;
        this.activo = true;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public void setNombrePiloto(String nombrePiloto) {
        this.nombrePiloto = nombrePiloto;
    }

    public String getApellidoPiloto() {
        return apellidoPiloto;
    }

    public void setApellidoPiloto(String apellidoPiloto) {
        this.apellidoPiloto = apellidoPiloto;
    }

    public long getNumeroDeDpi() {
        return numeroDeDpi;
    }

    public void setNumeroDeDpi(long numeroDeDpi) {
        this.numeroDeDpi = numeroDeDpi;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getCorreoElectronicoPiloto() {
        return correoElectronicoPiloto;
    }

    public void setCorreoElectronicoPiloto(String correoElectronicoPiloto) {
        this.correoElectronicoPiloto = correoElectronicoPiloto;
    }

    public int getNumeroTelefonicoPiloto() {
        return numeroTelefonicoPiloto;
    }

    public void setNumeroTelefonicoPiloto(int numeroTelefonicoPiloto) {
        this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
    }

    public String getGeneroPiloto() {
        return generoPiloto;
    }

    public void setGeneroPiloto(String generoPiloto) {
        this.generoPiloto = generoPiloto;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getEstadoPiloto() {
        return estadoPiloto;
    }

    public void setEstadoPiloto(String estadoPiloto) {
        this.estadoPiloto = estadoPiloto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Piloto{" + "nombrePiloto=" + nombrePiloto + ", apellidoPiloto=" + apellidoPiloto + ", numeroDeDpi=" + numeroDeDpi + ", tipoLicencia=" + tipoLicencia + ", correoElectronicoPiloto=" + correoElectronicoPiloto + ", numeroTelefonicoPiloto=" + numeroTelefonicoPiloto + ", generoPiloto=" + generoPiloto + ", fechaDeNacimiento=" + fechaDeNacimiento + ", estadoPiloto=" + estadoPiloto + ", activo=" + activo + '}';
    }
}