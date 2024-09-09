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
public class GestionFichaTrabajador {
    
    public Vector<FichaTrabajador> trabajador = new Vector<>();
    
    
    //constructores
    GestionFichaTrabajador(){};
    
    
    GestionFichaTrabajador(Vector<FichaTrabajador> tra){
        this.trabajador = tra;
    };
    
    
    //set
    public void setTrabajador(Vector<FichaTrabajador> tra){
        this.trabajador = tra;
    };
    
    //get
    public Vector<FichaTrabajador> getTrabajador(){
        return this.trabajador;
    };
    
    
    //fuciones extras
    //agregar un trabajor
    public void agregarTrabajador(FichaTrabajador newtra){
        this.trabajador.add(newtra);
    };
    
    //funcion para cambiar la informacion de un trabajador
    public void modificarTRabajdor(int indice, FichaTrabajador newtra){
        this.trabajador.get(indice).setNombre(newtra.getNombre());
        this.trabajador.get(indice).setDescripcion(newtra.getDescripcion());
        this.trabajador.get(indice).setSalarioSemanal(newtra.getSalarioSemanal());
        this.trabajador.get(indice).setSemanasDeTrabajo(newtra.getSemanasDeTrabajo());
        this.trabajador.get(indice).setEntrada(newtra.getEntrada());
        this.trabajador.get(indice).setValorEntrada(newtra.getValorEntrada());
    };
    
    //funcion para obtener el salario total
    public String getSalariTotal(int indice){
        
        float salarioTotal = this.trabajador.get(indice).getSalarioSemanal();
        
        Vector<Float> newValorEntrada = this.trabajador.get(indice).getValorEntrada();
        
        for (float prod : newValorEntrada) {
            salarioTotal += prod;
        }
        
        return "" + salarioTotal;
    };
    
    
    //funcion para agregar una nueva entradad
    public void setEntrada(int indice, String e, float v, String operacion){
        
        this.trabajador.get(indice).getEntrada().add(e);
        
        if (operacion.equals("+")) {
            this.trabajador.get(indice).getValorEntrada().add(v);
        }else{
            this.trabajador.get(indice).getValorEntrada().add(v * -1);
        }
        
    }
    
    //cargar el invetario de excel
    public void cargarTrabajadoresExcel(){
        //falta implemetacion
    };
}
