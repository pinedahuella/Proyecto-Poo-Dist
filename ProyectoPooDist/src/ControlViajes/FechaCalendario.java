/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlViajes;

//definicimos las librerias a utilizar dentro de la clase

//nos pemiten dar formato de fecha
import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class FechaCalendario {
    
    private Date fechac;
    private Date fechad;
    
    private int indicePiloto;
    private int indiceCamion;
    
    public Vector<Integer> indiceProductos;
    public Vector<Integer> indiceCantidad;
    
    public boolean activo;
    public boolean compra;
    
    SimpleDateFormat formato;
    
    public FechaCalendario(){
      formato = new SimpleDateFormat("dd/MM/yyyy");  
    };
    
    public FechaCalendario(Date fc, Date fd, int iP, int iC, Vector<Integer> iProd, Vector<Integer> iCad, boolean act, boolean com){
        this.fechac = fc;
        this.fechad = fd;
        formato = new SimpleDateFormat("dd/MM/yyyy");
        
        this.indicePiloto = iP;
        this.indiceCamion = iC;
        
        this.indiceProductos = iProd;
        this.indiceCantidad = iCad;
        
        this.activo = act;
        this.compra = com;
    };
    
    //setters
    public void setFechaC(Date f){
        this.fechac = f;
    };
    
    public void setFechaD(Date f){
        this.fechad = f;
    };
    
    
    public void setIndicePiloto(int iP){
        this.indicePiloto = iP;
    };
    
    public void setIndiceCamion(int iC){
      this.indiceCamion = iC;  
    };
    
    public void setIndiceProductos(Vector<Integer> iProd){
      this.indiceProductos = iProd;  
    };
    
    public void setIndiceCantidad(Vector<Integer> iCant){
      this.indiceCantidad = iCant;  
    };
    
    public void setActivo(boolean act){
      this.activo = act;  
    };
    
    public void setCompra(boolean com){
      this.compra = com;  
    };
    
    //getters
    public Date getFechaC(){
        return this.fechac;
    };
    
    public Date getFechaD(){
        return this.fechad;
    };
    
    public int getIndicePiloto(){
        return this.indicePiloto;
    };
    
    public int getIndiceCamion(){
      return this.indiceCamion;  
    };
    
    public Vector<Integer> getIndiceProductos(){
      return this.indiceProductos;  
    };
    
    public Vector<Integer> getIndiceCantidad(){
      return this.indiceCantidad;  
    };
    
    public boolean getActivo(){
      return this.activo;  
    };
    
    public boolean getCompra(){
      return this.compra;  
    };
}
