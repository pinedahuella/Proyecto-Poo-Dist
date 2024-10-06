/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPedidos;
import java.util.Vector;
/**
 *
 * @author USUARIO
 */
public class GestionPedido {
    public Vector<Pedido> pedidos = new Vector<>();
    
    //constructor 
    public GestionPedido(){
        
    };
    
    public GestionPedido(Vector<Pedido> p){
        this.pedidos = p;
    };
    
    //set
    public void setPedidos(Vector<Pedido> p){
        this.pedidos = p;
    };
    
    //get
    public Vector<Pedido> getPedidos(){
      return this.pedidos;  
    };
}
