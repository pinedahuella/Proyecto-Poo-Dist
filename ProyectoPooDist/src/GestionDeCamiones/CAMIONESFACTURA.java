package GestionDeCamiones;

/**
 * La clase CAMIONESFACTURA representa una factura asociada a un camión.
 * Contiene información sobre el camión, detalles de la factura y gastos relacionados.
 */
public class CAMIONESFACTURA {
    private String placasFactura; // Placas del camión asociado a la factura
    private String fechaFactura; // Fecha en que se generó la factura
    private String tipoDeGastoFactura; // Tipo de gasto de la factura (reparación, mantenimiento, etc.)
    private String descripcionFactura; // Descripción detallada del gasto
    private double montoFactura; // Monto total de la factura
    private String estadoFactura; // Estado de la factura (pagada, pendiente, etc.)
    private String tiempoDeReparacionFactura; // Tiempo de reparación asociado a la factura
    private String horaActual; // Hora actual al momento de registrar la factura

    /**
     * Constructor para inicializar una instancia de CAMIONESFACTURA.
     * 
     * @param placasFactura Placas del camión
     * @param fechaFactura Fecha de la factura
     * @param tipoDeGastoFactura Tipo de gasto asociado
     * @param descripcionFactura Descripción de la factura
     * @param montoFactura Monto total de la factura
     * @param estadoFactura Estado actual de la factura
     * @param tiempoDeReparacionFactura Tiempo de reparación
     * @param horaActual Hora de registro de la factura
     */
    public CAMIONESFACTURA(String placasFactura, String fechaFactura, String tipoDeGastoFactura,
                           String descripcionFactura, double montoFactura, String estadoFactura,
                           String tiempoDeReparacionFactura, String horaActual) {
        this.placasFactura = placasFactura;
        this.fechaFactura = fechaFactura;
        this.tipoDeGastoFactura = tipoDeGastoFactura;
        this.descripcionFactura = descripcionFactura;
        this.montoFactura = montoFactura;
        this.estadoFactura = estadoFactura;
        this.tiempoDeReparacionFactura = tiempoDeReparacionFactura;
        this.horaActual = horaActual; 
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public String getPlacasFactura() {
        return placasFactura;
    }

    public void setPlacasFactura(String placasFactura) {
        this.placasFactura = placasFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getTipoDeGastoFactura() {
        return tipoDeGastoFactura;
    }

    public void setTipoDeGastoFactura(String tipoDeGastoFactura) {
        this.tipoDeGastoFactura = tipoDeGastoFactura;
    }

    public String getDescripcionFactura() {
        return descripcionFactura;
    }

    public void setDescripcionFactura(String descripcionFactura) {
        this.descripcionFactura = descripcionFactura;
    }

    public double getMontoFactura() {
        return montoFactura;
    }

    public void setMontoFactura(double montoFactura) {
        this.montoFactura = montoFactura;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public String getTiempoDeReparacionFactura() {
        return tiempoDeReparacionFactura;
    }

    public void setTiempoDeReparacionFactura(String tiempoDeReparacionFactura) {
        this.tiempoDeReparacionFactura = tiempoDeReparacionFactura;
    }

    public String getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(String horaActual) {
        this.horaActual = horaActual;
    }

    /**
     * Método toString que devuelve una representación en forma de cadena de la factura.
     * 
     * @return Cadena que representa la información de la factura.
     */
    @Override
    public String toString() {
        return "CAMIONESFACTURA{" + 
                "placasFactura='" + placasFactura + '\'' + 
                ", fechaFactura='" + fechaFactura + '\'' + 
                ", tipoDeGastoFactura='" + tipoDeGastoFactura + '\'' + 
                ", descripcionFactura='" + descripcionFactura + '\'' + 
                ", montoFactura=" + montoFactura + 
                ", estadoFactura='" + estadoFactura + '\'' + 
                ", tiempoDeReparacionFactura='" + tiempoDeReparacionFactura + '\'' + 
                ", horaActual='" + horaActual + '\'' + 
                '}';
    }
}
