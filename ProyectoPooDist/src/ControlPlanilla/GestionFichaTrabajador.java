/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPlanilla;

import ControlPlanilla.FichaTrabajador;
import java.util.Vector;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.FileOutputStream;

/**
 *
 * @author USUARIO
 */
public class GestionFichaTrabajador {
    
    public Vector<FichaTrabajador> trabajador = new Vector<>();
    private String excelFilePath;
    
    //constructores
    GestionFichaTrabajador(){ excelFilePath = "excels/trabajadores.xlsx";};
    
    
    GestionFichaTrabajador(Vector<FichaTrabajador> tra){
        this.trabajador = tra;
        
        excelFilePath = "excels/trabajadores.xlsx";
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
    
    //funcion para obtener el salario total
    public float getSalariTotalEnNumeros(int indice){
        
        float salarioTotal = 0;
        
        Vector<Float> newValorEntrada = this.trabajador.get(indice).getValorEntrada();
        
        for (float prod : newValorEntrada) {
            
            if (prod > 0) {
               salarioTotal += prod;
            }else{
              salarioTotal -= prod;
            }

        }
        
        return salarioTotal;
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
    
    //funcion para inidicar una nueva semana de trabajo
    public void nuevaSemana(){
        //recorremos el vector de trabajadores
        for (int i = 0; i < trabajador.size(); i++) {
            trabajador.get(i).getEntrada().clear();
            trabajador.get(i).getValorEntrada().clear();
            trabajador.get(i).nuevaSemanaTrabajada();
        }
    }
    
    //cargar el invetario de excel
    public void cargarTrabajadoresExcel(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                String nombre = row.getCell(0).getStringCellValue();
                String descripcion = row.getCell(1).getStringCellValue();
                int semanasTrabajadas = (int) row.getCell(2).getNumericCellValue();
                float sueldoSemanal = (float) row.getCell(3).getNumericCellValue();

                Vector<String> entrada = new Vector<>();
                Vector<Float> valorEntrada = new Vector<>();

                // Leer entradas y valores de manera flexible
                for (int i = 4; i < row.getLastCellNum(); i += 2) {
                    Cell entradaCell = row.getCell(i);
                    Cell valorEntradaCell = row.getCell(i + 1);

                    if (entradaCell != null && entradaCell.getCellType() == CellType.STRING) {
                        entrada.add(entradaCell.getStringCellValue());
                    } else {
                        break; // No más entradas
                    }

                    if (valorEntradaCell != null && valorEntradaCell.getCellType() == CellType.NUMERIC) {
                        valorEntrada.add((float) valorEntradaCell.getNumericCellValue());
                    } else {
                        valorEntrada.add(0.0f); // Si el valor está vacío, agrega 0.0f
                    }
                }

                FichaTrabajador newtrabajador = new FichaTrabajador(nombre, descripcion, sueldoSemanal, semanasTrabajadas, entrada, valorEntrada);
                trabajador.add(newtrabajador);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    //guardar el invetario de excel
    public void guardarTrabajadoresExcel(){
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Trabajadores");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Descripción");
            headerRow.createCell(2).setCellValue("Semanas Trabajadas");
            headerRow.createCell(3).setCellValue("Sueldo Semanal");
            headerRow.createCell(4).setCellValue("Entrada 1");
            headerRow.createCell(5).setCellValue("Valor Entrada 1");
            // Columnas dinámicas adicionales se añadirán en el loop

            int rowNum = 1;

            for (FichaTrabajador newtrabajador : trabajador) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(newtrabajador.getNombre());
                row.createCell(1).setCellValue(newtrabajador.getDescripcion());
                row.createCell(2).setCellValue(newtrabajador.getSemanasDeTrabajo());
                row.createCell(3).setCellValue(newtrabajador.getSalarioSemanal());

                Vector<String> entradas = newtrabajador.getEntrada();
                Vector<Float> valoresEntradas = newtrabajador.getValorEntrada();

                int colNum = 4;

                for (int i = 0; i < entradas.size(); i++) {
                    row.createCell(colNum++).setCellValue(entradas.get(i));
                    row.createCell(colNum++).setCellValue(valoresEntradas.get(i));
                }
            }

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
