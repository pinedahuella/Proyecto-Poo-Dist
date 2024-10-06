/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPedidos;

/**
 *
 * @author USUARIO
 */
public class Pedido {
    
    private int indiceViaje;
    
    //constructores
    public Pedido(){
        
    };
    
    public Pedido(int a){
        this.indiceViaje = a;
    };
    
    //set
    public void setIndiceViaje(int a){
        this.indiceViaje = a;
    };
    
    //get
    public int getIndiceViaje(){
      return  this.indiceViaje; 
    };
}
