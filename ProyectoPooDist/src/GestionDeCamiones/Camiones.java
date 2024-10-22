package GestionDeCamiones;

/**
 * La clase Camiones representa un camión y contiene información sobre su estado,
 * características, costos de operación y mantenimiento.
 */
public class Camiones {

    private String placas; // Placas del camión
    private String estado; // Estado actual del camión (disponible, en reparación, etc.)
    private String tipoCombustible; // Tipo de combustible utilizado por el camión
    private double kilometraje; // Kilometraje actual del camión
    private double capacidadCarga; // Capacidad de carga del camión en toneladas
    private String añoFabricacion; // Año de fabricación del camión
    private String modelo; // Modelo del camión
    private String marca; // Marca del camión
    private boolean activo;

    // Atributos relacionados con los costos
    private double costoReparacion; // Costo de reparación del camión
    private double costoGalon; // Costo por galón de combustible
    private double galones; // Cantidad de galones consumidos
    private double costoMantenimiento; // Costo de mantenimiento del camión
    private double gastoNoEspecificado; // Gastos que no han sido especificados
    private String descripcionDelGasto; // Descripción de los gastos
    private String tiempoEnReparacion; // Tiempo que el camión ha estado en reparación
    private String fechaDeMantenimiento; // Fecha del último mantenimiento realizado
    private double total; // Total de gastos asociados al camión
    private double costoTotalCombustible; // Costo total del combustible consumido

    // Constructor por defecto
    public Camiones() {}

    public Camiones(String placas, String estado, String tipoCombustible, double kilometraje, double capacidadCarga, String añoFabricacion, String modelo, String marca, boolean activo, double costoReparacion, double costoGalon, double galones, double costoMantenimiento, double gastoNoEspecificado, String descripcionDelGasto, String tiempoEnReparacion, String fechaDeMantenimiento, double total, double costoTotalCombustible) {
        this.placas = placas;
        this.estado = estado;
        this.tipoCombustible = tipoCombustible;
        this.kilometraje = kilometraje;
        this.capacidadCarga = capacidadCarga;
        this.añoFabricacion = añoFabricacion;
        this.modelo = modelo;
        this.marca = marca;
        this.activo = activo;
        this.costoReparacion = costoReparacion;
        this.costoGalon = costoGalon;
        this.galones = galones;
        this.costoMantenimiento = costoMantenimiento;
        this.gastoNoEspecificado = gastoNoEspecificado;
        this.descripcionDelGasto = descripcionDelGasto;
        this.tiempoEnReparacion = tiempoEnReparacion;
        this.fechaDeMantenimiento = fechaDeMantenimiento;
        this.total = total;
        this.costoTotalCombustible = costoTotalCombustible;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        return "Camiones{" + "placas=" + placas + ", estado=" + estado + ", tipoCombustible=" + tipoCombustible + ", kilometraje=" + kilometraje + ", capacidadCarga=" + capacidadCarga + ", a\u00f1oFabricacion=" + añoFabricacion + ", modelo=" + modelo + ", marca=" + marca + ", activo=" + activo + ", costoReparacion=" + costoReparacion + ", costoGalon=" + costoGalon + ", galones=" + galones + ", costoMantenimiento=" + costoMantenimiento + ", gastoNoEspecificado=" + gastoNoEspecificado + ", descripcionDelGasto=" + descripcionDelGasto + ", tiempoEnReparacion=" + tiempoEnReparacion + ", fechaDeMantenimiento=" + fechaDeMantenimiento + ", total=" + total + ", costoTotalCombustible=" + costoTotalCombustible + '}';
    }
}