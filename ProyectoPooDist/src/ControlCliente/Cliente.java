/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlCliente;

/**
 *
 * @author USUARIO
 */

import java.util.Vector;
        

public class Cliente {
    
    private String nombre;
    private String descripcion;
    
    private Vector<Integer> indiceProducto = new Vector<>();
    private Vector<Float> indiceCantidad = new Vector<>();
    
    private Vector<Integer> indiceCredito = new Vector<>();
    
    //contructores
    public Cliente(){
        
    };
    
    public Cliente(String nom, String des, Vector<Integer> ip, Vector<Float> ic, Vector<Integer> icre){
        this.nombre = nom;
        this.descripcion = des;
        
        this.indiceProducto = ip;
        this.indiceCantidad = ic;
        
        this.indiceCredito = icre;
    };
    
    public Cliente(String nom, String des){
        this.nombre = nom;
        this.descripcion = des;
    };
    
    //setters
    public void setNombre(String n){
        this.nombre = n;
    };
    
    public void setDescripcion(String d){
        this.descripcion = d; 
    }
    
    public void setIndiceProducto(Vector<Integer> ip){
        this.indiceProducto = ip;
    }
    
    public void setIndiceCantidad(Vector<Float> ic){
        this.indiceCantidad = ic;
    }
    
    public void setIndiceCredito(Vector<Integer>  ice){
        this.indiceCredito = ice;
    }
    
    
    //gett
    public String getNombre(){
        return this.nombre;
    };
    
    public String getDescripcion(){
        return this.descripcion; 
    }
    
    public Vector<Integer> getIndiceProducto(){
        return this.indiceProducto;
    }
    
    public Vector<Float> getIndiceCantidad(){
        return this.indiceCantidad;
    }
    
    public Vector<Integer> getIndiceCredito(){
        return this.indiceCredito;
    }
    
    //funcion extra para agregar un credito a la lista
    public void addIndiceCredito(int indice){
        indiceCredito.add(indice);
    }
}
