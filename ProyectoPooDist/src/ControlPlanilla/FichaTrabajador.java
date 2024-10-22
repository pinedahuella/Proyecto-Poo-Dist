/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPlanilla;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class FichaTrabajador {
    
    private String nombre;
    private String descripcion;
    
    private float salarioSemanal;
    private int semanasDeTrabajo;
    
    public Vector<String> entrada = new Vector<>();
    public Vector<Float> valorEntrada = new Vector<>();
    
    
    
    //constructores
    FichaTrabajador(){};
    
    FichaTrabajador(String n, String d, float ss, int st){
        this.nombre = n;
        this.descripcion = d;
        
        this.salarioSemanal = ss;
        this.semanasDeTrabajo = st;
    };
    
    FichaTrabajador(String n, String d, float ss, int st, Vector<String> e, Vector<Float> v){
        this.nombre = n;
        this.descripcion = d;
        
        this.salarioSemanal = ss;
        this.semanasDeTrabajo = st;
        
        this.entrada = e;
        this.valorEntrada = v;
    };
    
    
    
    //set
    public void setNombre(String n){
        this.nombre = n;
    };
    
    public void setDescripcion(String d){
        this.descripcion = d;
    };
    
    public void setSalarioSemanal(float ss){
        this.salarioSemanal = ss;
    };
    
    public void setSemanasDeTrabajo(int st){
        this.semanasDeTrabajo = st;
    };
    
    public void setEntrada(Vector<String> e){
        this.entrada = e;
    }
    
    public void setValorEntrada(Vector<Float> v){
        this.valorEntrada = v;
    }
    
    
    
    //get
    public String getNombre(){
        return this.nombre;
    };
    
    public String getDescripcion(){
        return this.descripcion;
    };
    
    public float getSalarioSemanal(){
        return this.salarioSemanal;
    };
    
    public int getSemanasDeTrabajo(){
        return this.semanasDeTrabajo;
    };
    
    public Vector<String> getEntrada(){
        return this.entrada;
    }
    
    public Vector<Float> getValorEntrada(){
        return this.valorEntrada;
    }
    
    //creamos una funcion para sumarle semana
    public void nuevaSemanaTrabajada(){
        this.semanasDeTrabajo++;
    }
}
