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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GestionClientes {
    
    private Vector<Cliente> clientes = new Vector<>();
    private String excelFilePath;
    
    //constructores
    public GestionClientes(){
        excelFilePath = "excels/clientes.xlsx";
    };
    
    public GestionClientes(Vector<Cliente> c){
        this.clientes = c;
        excelFilePath = "excels/clientes.xlsx";
    };
    
    //setters
    public void setClientes(Vector<Cliente> c){
        this.clientes = c;
    }
    
    //getters
    public Vector<Cliente> getClientes(){
        return this.clientes;
    }
    
    
    //funciones extras
    
    //funcion para agregar un nuevo cliente
    public void addClientesVector(Cliente c){
        clientes.add(c);
    }
            
    
    //funcion para modificar informacion general
    public void modificarInformacionGeneral(int indice, String n, String d){
        clientes.get(indice).setNombre(n);
        clientes.get(indice).setDescripcion(d);
    }
    
    //funcion para modificar la tabla de productos
    public void modificarTablaProductos(int indice, Vector<Integer> p, Vector<Float> c){
        clientes.get(indice).setIndiceProducto(p);
        clientes.get(indice).setIndiceCantidad(c);
    }
    
    //funcion para agregar un nuevo credito al cliente
    public void addCredito(int indice, int indiceCreditos){
        clientes.get(indice).addIndiceCredito(indiceCreditos);
    }
    
    
    //funciones para cargar de excel
    public void cargarExcelCliente(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la primera hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum += 2) {
                Row rowProductos = sheet.getRow(rowNum);    // Fila para productos y cantidades
                Row rowCreditos = sheet.getRow(rowNum + 1); // Fila para créditos

                if (rowProductos == null) {
                    continue;
                }

                // Leer nombre y descripción del cliente
                String nombre = rowProductos.getCell(0).getStringCellValue();
                String descripcion = rowProductos.getCell(1).getStringCellValue();

                // Crear el objeto Cliente
                Cliente cliente = new Cliente(nombre, descripcion);

                // Leer los pares de producto y cantidad
                for (int colNum = 2; colNum < rowProductos.getLastCellNum(); colNum += 2) {
                    if (rowProductos.getCell(colNum) == null || rowProductos.getCell(colNum + 1) == null) {
                        break;
                    }
                    int indiceProducto = (int) rowProductos.getCell(colNum).getNumericCellValue();
                    float indiceCantidad = (float) rowProductos.getCell(colNum + 1).getNumericCellValue();
                    cliente.getIndiceProducto().add(indiceProducto);
                    cliente.getIndiceCantidad().add(indiceCantidad);
                }

                // Verificar si hay fila de créditos (si no hay, ignorarla)
                if (rowCreditos != null) {
                    for (int colNum = 2; colNum < rowCreditos.getLastCellNum(); colNum++) {
                        if (rowCreditos.getCell(colNum) == null) {
                            break;
                        }
                        int indiceCredito = (int) rowCreditos.getCell(colNum).getNumericCellValue();
                        cliente.getIndiceCredito().add(indiceCredito);
                    }
                }

                // Añadir el cliente a la lista, independientemente de si tiene créditos o no
                clientes.add(cliente);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    //funcion para guardar de excel
    public void guardarExcelCliente(){
          try (Workbook workbook = new XSSFWorkbook()) {

            // Crear la hoja de Excel
            Sheet sheet = workbook.createSheet("Clientes");

            int rowNum = 0;

            for (Cliente cliente : clientes) {
                // Crear la fila para los productos y cantidades
                Row rowProductos = sheet.createRow(rowNum++);
                rowProductos.createCell(0).setCellValue(cliente.getNombre());
                rowProductos.createCell(1).setCellValue(cliente.getDescripcion());

                // Escribir los pares de productos y cantidades
                int cellNum = 2;
                for (int i = 0; i < cliente.getIndiceProducto().size(); i++) {
                    rowProductos.createCell(cellNum++).setCellValue(cliente.getIndiceProducto().get(i));
                    rowProductos.createCell(cellNum++).setCellValue(cliente.getIndiceCantidad().get(i));
                }

                // Crear la fila para los créditos
                Row rowCreditos = sheet.createRow(rowNum++);
                cellNum = 2; // Los créditos inician desde la columna 2
                for (int credito : cliente.getIndiceCredito()) {
                    rowCreditos.createCell(cellNum++).setCellValue(credito);
                }
            }

            // Guardar el archivo Excel
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
