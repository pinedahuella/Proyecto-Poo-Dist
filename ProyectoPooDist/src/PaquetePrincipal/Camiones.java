package PaquetePrincipal;

public class Camiones {

    private String placas;
    private String estado;
    private String tipoCombustible;
    private double kilometraje;
    private double capacidadCarga;
    private String añoFabricacion;
    private String modelo;
    private String marca;
    private double costos;

    // Constructor por defecto
    public Camiones() {}

    // Constructor con parámetros
    public Camiones(String placas, String estado, String tipoCombustible, double kilometraje, 
                    double capacidadCarga, String añoFabricacion, String modelo, 
                    String marca, double costos) {
        this.placas = placas;
        this.estado = estado;
        this.tipoCombustible = tipoCombustible;
        this.kilometraje = kilometraje;
        this.capacidadCarga = capacidadCarga;
        this.añoFabricacion = añoFabricacion;
        this.modelo = modelo;
        this.marca = marca;
        this.costos = costos;
    }

    // Métodos setters
    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public void setAñoFabricacion(String añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCostos(double costos) {
        this.costos = costos;
    }

    // Métodos getters
    public String getPlacas() {
        return this.placas;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getTipoCombustible() {
        return this.tipoCombustible;
    }

    public double getKilometraje() {
        return this.kilometraje;
    }

    public double getCapacidadCarga() {
        return this.capacidadCarga;
    }

    public String getAñoFabricacion() {
        return this.añoFabricacion;
    }

    public String getModelo() {
        return this.modelo;
    }

    public String getMarca() {
        return this.marca;
    }

    public double getCostos() {
        return this.costos;
    }

    @Override
    public String toString() {
        return "Camiones{" + "placas=" + placas + ", estado=" + estado + ", tipoCombustible=" + tipoCombustible + ", kilometraje=" + kilometraje + ", capacidadCarga=" + capacidadCarga + ", a\u00f1oFabricacion=" + añoFabricacion + ", modelo=" + modelo + ", marca=" + marca + ", costos=" + costos + '}';
    
    }
}