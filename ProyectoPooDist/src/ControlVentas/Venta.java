/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlVentas;

/**
 *
 * @author USUARIO
 */
public class Venta {
    
    private int indiceProducto;
    private int indiceCantidad;
    
    private int indiceCliente;
    
    private float precio;
    private float precioCosto;
    private float precioFlete;
    
    private boolean credito;
    private boolean creditoActivo;
    
    private float ganancia;
    
    //constructores
    public Venta(){
        
    };
    
    public Venta(int ip, int ic, int icli, float pre, float prec, float fle, boolean credi, boolean accredi, float gan){
        this.indiceProducto = ip;
        this.indiceCantidad = ic;
        this.indiceCliente = icli;
        this.precio = pre;
        this.precioCosto = prec;
        this.precioFlete = fle;
        this.credito = credi;
        this.creditoActivo = accredi;
        this.ganancia = gan;
    };
    
    //setters
    public void setIndiceProducto(int ip){
        this.indiceProducto = ip;
    };
    
    public void setIndiceCantidad(int ic){
        this.indiceCantidad = ic;
    };
    
    public void setIndiceCliente(int icli){
        this.indiceCliente = icli;
    };
    
    public void setPrecio(float pre){
        this.precio = pre;
    };
    
    public void setPrecioCosto(float prec){
        this.precioCosto = prec;
    };
    
    public void setPrecioFlete(float fle){
        this.precioFlete = fle;
    };
    
    public void setCredito(boolean credi){
        this.credito =  credi;
    };
    
    public void setCreditoActivo(boolean crediA){
        this.creditoActivo = crediA;
    };
            
    public void setGanancia(float gan){
        this.ganancia = gan;
    };
    
    //getters 
    public int getIndiceProducto(){
        return this.indiceProducto;
    };
    
    public int getIndiceCantidad(){
        return this.indiceCantidad;
    };
    
    public int getIndiceCliente(){
        return this.indiceCliente;
    };
    
    public float getPrecio(){
        return this.precio;
    };
    
    public float getPrecioCosto(){
        return this.precioCosto;
    };
    
    public float getPrecioFlete(){
        return this.precioFlete;
    };
    
    public boolean getCredito(){
        return this.credito;
    };
    
    public boolean getCreditoActivo(){
        return this.creditoActivo;
    };
    
    public float getGanancia(){
        return this.ganancia;
    };
}
