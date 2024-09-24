/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlViajes;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class GestionCalendario {
    
    public Vector<FechaCalendario> fechasDeCalendario = new Vector<>();
    
    GestionCalendario(){
        
    };
    
    GestionCalendario(Vector<FechaCalendario> pi){
        this.fechasDeCalendario = pi;
    };
    
    //setters
    public void setFechasDeCalendario(Vector<FechaCalendario> pi){
        this.fechasDeCalendario = pi;
    };
    
    //getters
    public Vector<FechaCalendario> getFechasDeCalendario(){
        return this.fechasDeCalendario;
    };
    
    
    //funciones de gestion 
    
    //modificamos un fecha existente
    public void modificarFecha(int indice, FechaCalendario newfec){
        //pasamos los parametros
        fechasDeCalendario.get(indice).setFechaC(newfec.getFechaC());
        fechasDeCalendario.get(indice).setFechaD(newfec.getFechaD());
        
        fechasDeCalendario.get(indice).setIndicePiloto(newfec.getIndicePiloto());
        fechasDeCalendario.get(indice).setIndiceCamion(newfec.getIndiceCamion());
        
        fechasDeCalendario.get(indice).setIndiceProductos(newfec.getIndiceProductos());
        fechasDeCalendario.get(indice).setIndiceCantidad(newfec.getIndiceCantidad());
        
        fechasDeCalendario.get(indice).setActivo(newfec.getActivo());
        fechasDeCalendario.get(indice).setCompra(newfec.getCompra());
    };
    
    //ingresar una fehc nueva
    public void agregarFecha(FechaCalendario newfec){
        fechasDeCalendario.add(newfec);
    };
    
    
    
    
    
    
    //funciones relacionadas con el excel
    public void cargarFechasExcel(){
        
    };
    
    public void guardarFecharExcel(){
        
    };
}
