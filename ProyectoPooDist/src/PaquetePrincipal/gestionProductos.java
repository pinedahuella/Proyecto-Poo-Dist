/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaquetePrincipal;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class gestionProductos {

    public Vector<Producto> productos = new Vector<>();
    
    
    gestionProductos(){};
    
    gestionProductos(Vector<Producto> prod){
        this.productos = prod;
    };
    
    public void setProductos(Vector<Producto> prod){
        this.productos = prod;
    }
    
    public Vector<Producto> getProductos(){
        return this.productos;
    }
    
    
    
    //funciones extras
    public void setUnProducto(Producto produc){
        this.productos.add(produc);
    }
    
    public void setCantidad(int indice, int nuevacantidad, String operacion){
        if(operacion.equals("+")){
            this.productos.get(indice).setExistencias(productos.get(indice).getExistencias() + nuevacantidad);
        }else if(operacion.equals("-")){
            this.productos.get(indice).setExistencias(productos.get(indice).getExistencias() - nuevacantidad);
        }else{
            this.productos.get(indice).setExistencias(nuevacantidad);
        }
    }
    
    public void actualizarProducto(int indice, String n, String pro, int exis, float pC, float pF){
        this.productos.get(indice).setNombre(n);
        this.productos.get(indice).setProveedor(pro);
        this.productos.get(indice).setExistencias(exis);
        this.productos.get(indice).setPrecioCosto(pC);
        this.productos.get(indice).setPrecioFlete(pF);        
    }
    
    public void setCargarInvetarioExcel(){
        
       //falta implementación 
    }
    
}
