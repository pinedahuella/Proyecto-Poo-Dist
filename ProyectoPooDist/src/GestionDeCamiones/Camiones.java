package GestionDeCamiones;

public class Camiones {

    private String placas;
    private String estado;
    private String tipoCombustible;
    private double kilometraje;
    private double capacidadCarga;
    private String añoFabricacion;
    private String modelo;
    private String marca;

    private double costoReparacion;
    private double costoGalon;
    private double galones;
    private double costoMantenimiento;
    private double gastoNoEspecificado;
    private String descripcionDelGasto;
    private String tiempoEnReparacion;
    private String fechaDeMantenimiento; 
    private double total; 
    private double costoTotalCombustible;


    public Camiones() {}


    public Camiones(String placas, String estado, String tipoCombustible, double kilometraje,
                    double capacidadCarga, String añoFabricacion, String modelo,
                    String marca, double costoReparacion, double costoGalon,
                    double galones, double costoMantenimiento, 
                    double gastoNoEspecificado, String descripcionDelGasto,
                    String tiempoEnReparacion, String fechaDeMantenimiento, double total) {
        this.placas = placas;
        this.estado = estado;
        this.tipoCombustible = tipoCombustible;
        this.kilometraje = kilometraje;
        this.capacidadCarga = capacidadCarga;
        this.añoFabricacion = añoFabricacion;
        this.modelo = modelo;
        this.marca = marca;
        this.costoReparacion = costoReparacion;
        this.costoGalon = costoGalon;
        this.galones = galones;
        this.costoMantenimiento = costoMantenimiento;
        this.gastoNoEspecificado = gastoNoEspecificado;
        this.descripcionDelGasto = descripcionDelGasto;
        this.tiempoEnReparacion = tiempoEnReparacion;
        this.fechaDeMantenimiento = fechaDeMantenimiento;
        this.total = total;
        this.costoTotalCombustible = costoGalon * galones;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAñoFabricacion(String añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getCostoReparacion() {
        return costoReparacion;
    }

    public void setCostoReparacion(double costoReparacion) {
        this.costoReparacion = costoReparacion;
    }

    public double getCostoGalon() {
        return costoGalon;
    }

    public void setCostoGalon(double costoGalon) {
        this.costoGalon = costoGalon;
    }

    public double getGalones() {
        return galones;
    }

    public void setGalones(double galones) {
        this.galones = galones;
    }

    public double getCostoMantenimiento() {
        return costoMantenimiento;
    }

    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
    }

    public double getGastoNoEspecificado() {
        return gastoNoEspecificado;
    }

    public void setGastoNoEspecificado(double gastoNoEspecificado) {
        this.gastoNoEspecificado = gastoNoEspecificado;
    }

    public String getDescripcionDelGasto() {
        return descripcionDelGasto;
    }

    public void setDescripcionDelGasto(String descripcionDelGasto) {
        this.descripcionDelGasto = descripcionDelGasto;
    }

    public String getTiempoEnReparacion() {
        return tiempoEnReparacion;
    }

    public void setTiempoEnReparacion(String tiempoEnReparacion) {
        this.tiempoEnReparacion = tiempoEnReparacion;
    }

    public String getFechaDeMantenimiento() {
        return fechaDeMantenimiento;
    }

    public void setFechaDeMantenimiento(String fechaDeMantenimiento) {
        this.fechaDeMantenimiento = fechaDeMantenimiento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCostoTotalCombustible() {
        return costoTotalCombustible;
    }

    public void setCostoTotalCombustible(double costoTotalCombustible) {
        this.costoTotalCombustible = costoTotalCombustible;
    }

    @Override
    public String toString() {
        return "Camiones{" + "placas=" + placas + ", estado=" + estado + ", tipoCombustible=" + tipoCombustible + ", kilometraje=" + kilometraje + ", capacidadCarga=" + capacidadCarga + ", a\u00f1oFabricacion=" + añoFabricacion + ", modelo=" + modelo + ", marca=" + marca + ", costoReparacion=" + costoReparacion + ", costoGalon=" + costoGalon + ", galones=" + galones + ", costoMantenimiento=" + costoMantenimiento + ", gastoNoEspecificado=" + gastoNoEspecificado + ", descripcionDelGasto=" + descripcionDelGasto + ", tiempoEnReparacion=" + tiempoEnReparacion + ", fechaDeMantenimiento=" + fechaDeMantenimiento + ", total=" + total + ", costoTotalCombustible=" + costoTotalCombustible + '}';
    }  

}