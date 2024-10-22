/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlVentas;

import java.util.Vector;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author USUARIO
 */
public class gestionVentas {
    private Vector<Venta> ventas = new Vector<>();
    private Vector<Venta> creditos = new Vector<>();
    private Vector<String> historial = new Vector<>();
    private String excelFilePath;
    
    //constructor 
    public gestionVentas(){
        excelFilePath = "excels/ventas.xlsx";
    };
    
    public gestionVentas(Vector<Venta> v, Vector<Venta> c, Vector<String> h){
        this.ventas = v;
        this.creditos = c;
        this.historial = h;
        excelFilePath = "excels/ventas.xlsx";
    };

    //constructos especial para una segunda gestion
    public gestionVentas(String n){
        excelFilePath = n;
    };
    
    
    //setters
    public void setVentas(Vector<Venta> v){
        this.ventas = v;
    };
    
    public void setCreditos(Vector<Venta> c){
        this.creditos = c;
    };
    
    public void setHistorial(Vector<String> h){
        this.historial = h;
    };
    
    //getters
    public Vector<Venta> getVentas(){
        return this.ventas;
    };
    
    public Vector<Venta> getCreditos(){
        return this.creditos;
    };
    
    public Vector<String> getHistorial(){
        System.out.print("obeteniendo vector");
        return this.historial;
    };
    
    
    //funciones extras
    public void addVentaVector(Venta ven){
        ventas.add(ven);
    } 
    
    //funcion para modificar una venta
    public void modificarVenta(int indice, Venta ven){
        ventas.get(indice).setIndiceProducto(ven.getIndiceProducto());
        ventas.get(indice).setIndiceCantidad(ven.getIndiceCantidad());
        
        ventas.get(indice).setIndiceCliente(ven.getIndiceCliente());
        
        ventas.get(indice).setPrecio(ven.getPrecio());
        ventas.get(indice).setPrecioCosto(ven.getPrecioCosto());
        ventas.get(indice).setPrecioFlete(ven.getPrecioFlete());
        
        ventas.get(indice).setCredito(ven.getCredito());
        ventas.get(indice).setCreditoActivo(ven.getCreditoActivo());
        
        calcularGanancia(indice);
    }
    
    //funcion para calcular ganacia
    public void calcularGanancia(int indice){
        
        float precioDeVenta = ventas.get(indice).getPrecio() * ventas.get(indice).getIndiceCantidad();
        float precioDeCosto = ventas.get(indice).getPrecioCosto() * ventas.get(indice).getIndiceCantidad();
        float precioDeFlete = ventas.get(indice).getPrecioFlete() * ventas.get(indice).getIndiceCantidad();
        
        ventas.get(indice).setGanancia(precioDeVenta - precioDeCosto - precioDeFlete);
    }
    
    //funcion para clacular la ganancia general
    public double calcularGananciaGeneral(){
        double GananciaDelDia = 0;
        
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCredito() == false || ventas.get(i).getCreditoActivo() == false) {
                GananciaDelDia = ventas.get(i).getGanancia() + GananciaDelDia;
            }
        }
        
        return GananciaDelDia;
    };
    
    public double calcularVendidoGeneral(){
        double GananciaDelDia = 0;
        
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCredito() == false || ventas.get(i).getCreditoActivo() == false) {
                GananciaDelDia = ventas.get(i).getPrecio() * ventas.get(i).getIndiceCantidad() + GananciaDelDia;
            }
        }
        
        return GananciaDelDia;
    };
    
    public double calcularFleteGeneral(){
        double GananciaDelDia = 0;
        
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCredito() == false || ventas.get(i).getCreditoActivo() == false) {
                GananciaDelDia = ventas.get(i).getPrecioFlete() * ventas.get(i).getIndiceCantidad() + GananciaDelDia;
            }
        }
        
        return GananciaDelDia;
    };
    
    public double calcularCostoGeneral(){
        double GananciaDelDia = 0;
        
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCredito() == false || ventas.get(i).getCreditoActivo() == false) {
                GananciaDelDia = ventas.get(i).getPrecioCosto() * ventas.get(i).getIndiceCantidad() + GananciaDelDia;
            }
        }
        
        return GananciaDelDia;
    };
    
    //funcion para agregar un credito a la lista de creditos
    public void addCreditoVector(Venta ven){
        creditos.add(ven);
    }
    
    //funcion para agregar un valor al historia
    public void addHistorialVector(String his){
        historial.add(his);
    }
    
    //funcion para modificar una credito
    public void modificarCredito(int indice, Venta ven){
        creditos.get(indice).setIndiceProducto(ven.getIndiceProducto());
        creditos.get(indice).setIndiceCantidad(ven.getIndiceCantidad());
        
        creditos.get(indice).setIndiceCliente(ven.getIndiceCliente());
        
        creditos.get(indice).setPrecio(ven.getPrecio());
        creditos.get(indice).setPrecioCosto(ven.getPrecioCosto());
        creditos.get(indice).setPrecioFlete(ven.getPrecioFlete());
        
        creditos.get(indice).setCredito(ven.getCredito());
        creditos.get(indice).setCreditoActivo(ven.getCreditoActivo());
        
        calcularGananciaCredito(indice);
    }
    
    //funcion para calcular credito
    public void calcularGananciaCredito(int indice){
        
        float precioDeVenta = creditos.get(indice).getPrecio() * creditos.get(indice).getIndiceCantidad();
        float precioDeCosto = creditos.get(indice).getPrecioCosto() * creditos.get(indice).getIndiceCantidad();
        float precioDeFlete = creditos.get(indice).getPrecioFlete() * creditos.get(indice).getIndiceCantidad();
        
       creditos.get(indice).setGanancia(precioDeVenta - precioDeCosto - precioDeFlete);
    }
    

    
    
    //funciones para carga y guardar en excel
    //cargar de excel
    public void cargarExcelVentas(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la primera hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                // Leer los valores de las celdas
                int indiceProducto = (int) row.getCell(0).getNumericCellValue();
                int indiceCantidad = (int) row.getCell(1).getNumericCellValue();
                int indiceCliente = (int) row.getCell(2).getNumericCellValue();

                float precio = (float) row.getCell(3).getNumericCellValue();
                float precioCosto = (float) row.getCell(4).getNumericCellValue();
                float precioFlete = (float) row.getCell(5).getNumericCellValue();

                boolean credito = row.getCell(6).getNumericCellValue() == 1;
                boolean creditoActivo = row.getCell(7).getNumericCellValue() == 1;

                float ganancia = (float) row.getCell(8).getNumericCellValue();

                // Crear el objeto Venta y añadirlo al vector
                Venta venta = new Venta(indiceProducto, indiceCantidad, indiceCliente, precio, precioCosto, precioFlete, credito, creditoActivo, ganancia);
                ventas.add(venta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    //guardar de excel
    public void guardarExcelVentas(){
        
        System.out.println("modfificando");
        try (FileInputStream fis = new FileInputStream(excelFilePath);
                
                
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener o crear la hoja "Ventas"
            Sheet sheet = workbook.getSheet("Ventas");
            if (sheet == null) {
                sheet = workbook.createSheet("Ventas");
            }

            // Crear fila de encabezado si no existe
            if (sheet.getRow(0) == null) {
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Indice Producto");
                headerRow.createCell(1).setCellValue("Indice Cantidad");
                headerRow.createCell(2).setCellValue("Indice Cliente");
                headerRow.createCell(3).setCellValue("Precio");
                headerRow.createCell(4).setCellValue("Precio Costo");
                headerRow.createCell(5).setCellValue("Precio Flete");
                headerRow.createCell(6).setCellValue("Credito");
                headerRow.createCell(7).setCellValue("Credito Activo");
                headerRow.createCell(8).setCellValue("Ganancia");
            }

            // Limpiar las filas existentes (sobreescribir)
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Escribir los datos de las ventas en el Excel
            int rowNum = 1;
            for (Venta venta : ventas) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(venta.getIndiceProducto());
                row.createCell(1).setCellValue(venta.getIndiceCantidad());
                row.createCell(2).setCellValue(venta.getIndiceCliente());
                row.createCell(3).setCellValue(venta.getPrecio());
                row.createCell(4).setCellValue(venta.getPrecioCosto());
                row.createCell(5).setCellValue(venta.getPrecioFlete());
                row.createCell(6).setCellValue(venta.getCredito() ? 1 : 0);
                row.createCell(7).setCellValue(venta.getCreditoActivo() ? 1 : 0);
                row.createCell(8).setCellValue(venta.getGanancia());
            }

            // Guardar el archivo Excel
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    
    
    //funciones especiales para historial de excel, cargar y guardar
    public void cargarExcelHistoria(){
        System.out.print("cargando Hitorial");
        //funcion obsoleta de cargar, por rendimiento
        /*
         try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la primera hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);

            // Recorrer todas las filas del Excel
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                // Leer cada celda de la fila y convertir a String
                String fecha = row.getCell(0).getStringCellValue();
                String cliente = row.getCell(1).getStringCellValue();
                String tipo = row.getCell(2).getStringCellValue();
                String producto = row.getCell(3).getStringCellValue();
                String cantidad = row.getCell(4).getStringCellValue();
                String precio = row.getCell(5).getStringCellValue();
                String total = row.getCell(6).getStringCellValue();
                String flete = row.getCell(7).getStringCellValue();
                String costo = row.getCell(8).getStringCellValue();
                String ganancia = row.getCell(9).getStringCellValue();

                // Agregar la entrada al Vector historial
                historial.add(fecha);
                historial.add(cliente);
                historial.add(tipo);
                historial.add(producto);
                historial.add(cantidad);
                historial.add(precio);
                historial.add(total);
                historial.add(flete);
                historial.add(costo);
                historial.add(ganancia);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    };
    
    public void guardarExcelHistoria(Vector<String> his){
        /*
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Historial");

        // Crear la fila de encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha", "Cliente", "Tipo", "Producto", "Cantidad", "Precio", "Total", "Flete", "Costo", "Ganancia"};
        for (int col = 0; col < headers.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(headers[col]);
        }

        // Insertar los datos del historial en el Excel
        int rowNum = 1; // Empieza en la fila 1 porque la 0 es el encabezado
        for (int i = 0; i < historial.size(); i += 10) {
            Row row = sheet.createRow(rowNum++);

            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(historial.get(i + j)); // Tomar los 10 elementos correspondientes
            }
        }

        // Guardar el archivo Excel
        try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */ 
        
        //nueva funcion para guardar en el excel, mas optima
        
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            int startRow = lastRowNum + 1; // La próxima fila vacía
            int historiaIndex = 0;

            // Escribe historias en grupos de 10
            for (int i = startRow; historiaIndex < his.size(); i++) {
                Row row = sheet.createRow(i);
                for (int j = 0; j < 10 && historiaIndex < his.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(his.get(historiaIndex));
                    historiaIndex++;
                }
            }

            // Guardar cambios en el archivo Excel
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    //funciones especiales para creditos de excel, gargar y guardar
    public void cargarExcelCreditos(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener la primera hoja (índice 0)
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                // Leer los valores de las celdas
                int indiceProducto = (int) row.getCell(0).getNumericCellValue();
                int indiceCantidad = (int) row.getCell(1).getNumericCellValue();
                int indiceCliente = (int) row.getCell(2).getNumericCellValue();

                float precio = (float) row.getCell(3).getNumericCellValue();
                float precioCosto = (float) row.getCell(4).getNumericCellValue();
                float precioFlete = (float) row.getCell(5).getNumericCellValue();

                boolean credito = row.getCell(6).getNumericCellValue() == 1;
                boolean creditoActivo = row.getCell(7).getNumericCellValue() == 1;

                float ganancia = (float) row.getCell(8).getNumericCellValue();

                // Crear el objeto Venta y añadirlo al vector
                Venta venta = new Venta(indiceProducto, indiceCantidad, indiceCliente, precio, precioCosto, precioFlete, credito, creditoActivo, ganancia);
                creditos.add(venta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    
    public void guardarExcelCreditos(){
        
        System.out.println("modfificando");
        try (FileInputStream fis = new FileInputStream(excelFilePath);
                
                
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Obtener o crear la hoja "Ventas"
            Sheet sheet = workbook.getSheet("Ventas");
            if (sheet == null) {
                sheet = workbook.createSheet("Ventas");
            }

            // Crear fila de encabezado si no existe
            if (sheet.getRow(0) == null) {
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Indice Producto");
                headerRow.createCell(1).setCellValue("Indice Cantidad");
                headerRow.createCell(2).setCellValue("Indice Cliente");
                headerRow.createCell(3).setCellValue("Precio");
                headerRow.createCell(4).setCellValue("Precio Costo");
                headerRow.createCell(5).setCellValue("Precio Flete");
                headerRow.createCell(6).setCellValue("Credito");
                headerRow.createCell(7).setCellValue("Credito Activo");
                headerRow.createCell(8).setCellValue("Ganancia");
            }

            // Limpiar las filas existentes (sobreescribir)
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Escribir los datos de las ventas en el Excel
            int rowNum = 1;
            for (Venta venta : creditos) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(venta.getIndiceProducto());
                row.createCell(1).setCellValue(venta.getIndiceCantidad());
                row.createCell(2).setCellValue(venta.getIndiceCliente());
                row.createCell(3).setCellValue(venta.getPrecio());
                row.createCell(4).setCellValue(venta.getPrecioCosto());
                row.createCell(5).setCellValue(venta.getPrecioFlete());
                row.createCell(6).setCellValue(venta.getCredito() ? 1 : 0);
                row.createCell(7).setCellValue(venta.getCreditoActivo() ? 1 : 0);
                row.createCell(8).setCellValue(venta.getGanancia());
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
