/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPedidos;
import java.util.Vector;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * @author USUARIO
 */
public class GestionPedido {
    public Vector<Pedido> pedidos = new Vector<>();
        private String excelFilePath;

    
    //constructor 
    public GestionPedido(){
        excelFilePath = "excels/pedidos.xlsx";
    };
    
    public GestionPedido(Vector<Pedido> p){
        this.pedidos = p;
        excelFilePath = "excels/pedidos.xlsx";
    };
    
    //set
    public void setPedidos(Vector<Pedido> p){
        this.pedidos = p;
    };
    
    //get
    public Vector<Pedido> getPedidos(){
      return this.pedidos;  
    };
    
    //funcion para agregar un nuevo pedido
    public void agregarPedido(Pedido p){
        pedidos.add(p);
    };
    
    //funciones para cargar desde excel
    public void CargaDeExcel(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la priemra hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                // Leer el indiceViaje
                int indiceViaje = (int) row.getCell(0).getNumericCellValue();

                // Crear el objeto Pedido y añadirlo al vector
                Pedido pedido = new Pedido(indiceViaje);
                pedidos.add(pedido);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    public void GuardarEnExcel(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener o crear la primera hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                sheet = workbook.createSheet("Pedidos");
            }

            // Crear fila de encabezado si no existe
            if (sheet.getRow(0) == null) {
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Indice Viaje");
            }

            // Limpiar las filas existentes (sobreescribir)
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Escribir los pedidos en el Excel
            int rowNum = 1;
            for (Pedido pedido : pedidos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(pedido.getIndiceViaje());
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
