/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlInventario;

/**
 *
 * @author USUARIO
 */
public class Producto {
    
    private String nombre;
    private String proveedor;
    
    private int existencias;
    
    private float precioCosto;
    private float precioFlete;
    
    Producto(){};
    
    Producto(String n, String pro, int exis, float pC, float pF){  
    
        this.nombre = n;
        this.proveedor = pro;
        
        this.existencias = exis;
        
        this.precioCosto = pC;
        this.precioFlete = pF;
    };
    
    //setters
    public void setNombre(String n){
        this.nombre = n;
    };
    
    public void setProveedor(String pro){
        this.proveedor = pro;
    };
    
    public void setExistencias(int exis){
        this.existencias = exis;
    };
    
    public void setPrecioCosto(float pC){
        this.precioCosto = pC;
    };
    
    public void setPrecioFlete(float pF){
        this.precioFlete = pF;
    };
    
    //getters
    public String getNombre(){
        return this.nombre;
    };
    
    public String getProveedor(){
        return this.proveedor;
    };
    
    public int getExistencias(){
        return this.existencias;
    };
    
    public float getPrecioCosto(){
        return this.precioCosto;
    };
    
    public float getPrecioFlete(){
        return this.precioFlete;
    };
    
}
