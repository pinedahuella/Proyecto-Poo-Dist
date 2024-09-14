package PaquetePrincipal;

public class CAMIONESFACTURA {
    private String placasFactura;
    private String fechaFactura;
    private String tipoDeGastoFactura;
    private String descripcionFactura;
    private double montoFactura;
    private String estadoFactura;
    private String tiempoDeReparacionFactura;
    private String horaActual;


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

    @Override
    public String toString() {
        return "CAMIONESFACTURA{" + "placasFactura=" + placasFactura + ", fechaFactura=" + fechaFactura + ", tipoDeGastoFactura=" + tipoDeGastoFactura + ", descripcionFactura=" + descripcionFactura + ", montoFactura=" + montoFactura + ", estadoFactura=" + estadoFactura + ", tiempoDeReparacionFactura=" + tiempoDeReparacionFactura + ", horaActual=" + horaActual + '}';
    }
    
    
}