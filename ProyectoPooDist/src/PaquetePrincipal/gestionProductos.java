/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaquetePrincipal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.FileOutputStream;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class gestionProductos {

    public Vector<Producto> productos = new Vector<>();
    
    private String excelFilePath;
    
    
    gestionProductos(){excelFilePath = "excels/inventarios.xlsx";};
    
    gestionProductos(Vector<Producto> prod){
        this.productos = prod;
        excelFilePath = "excels/inventarios.xlsx";
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
    
    
    //funcion para cargar del excel
    public void setCargarInvetarioExcel(){
         try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                String nombre = row.getCell(0).getStringCellValue();
                String proveedor = row.getCell(1).getStringCellValue();
                float precioCosto = (float) row.getCell(2).getNumericCellValue();
                float precioFlete = (float) row.getCell(3).getNumericCellValue();
                int existencias = (int) row.getCell(4).getNumericCellValue();

                Producto quintal = new Producto(nombre, proveedor, existencias, precioCosto, precioFlete);
                productos.add(quintal);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //funcion par asobreescribir el excel
    public void getCargarInvetarioExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Inventario Quintales");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Proveedor");
            headerRow.createCell(2).setCellValue("Precio Costo");
            headerRow.createCell(3).setCellValue("Precio Flete");
            headerRow.createCell(4).setCellValue("Existencias");

            // Rellenar filas con datos del vector
            int rowCount = 1;
            for (Producto quintal : productos) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(quintal.getNombre());
                row.createCell(1).setCellValue(quintal.getProveedor());
                row.createCell(2).setCellValue(quintal.getPrecioCosto());
                row.createCell(3).setCellValue(quintal.getPrecioFlete());
                row.createCell(4).setCellValue(quintal.getExistencias());
            }

            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
