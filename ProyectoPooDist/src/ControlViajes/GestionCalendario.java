/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlViajes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileOutputStream;

import java.util.Vector;

/**
 *
 * @author USUARIO
 */
public class GestionCalendario {
    
    public Vector<FechaCalendario> fechasDeCalendario = new Vector<>();
    private String excelFilePath;
        private SimpleDateFormat dateFormat;

    
    GestionCalendario(){
        excelFilePath = "excels/pedidos_calendario.xlsx";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    };
    
    GestionCalendario(Vector<FechaCalendario> pi){
        this.fechasDeCalendario = pi;
        excelFilePath = "excels/pedidos_calendario.xlsx";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    };
    
    //setters
    public void setFechasDeCalendario(Vector<FechaCalendario> pi){
        this.fechasDeCalendario = pi;
    };
    
    //getters
    public Vector<FechaCalendario> getFechasDeCalendario(){
        return this.fechasDeCalendario;
    };
    
    
    //funciones de gestion 
    
    //modificamos un fecha existente
    public void modificarFecha(int indice, FechaCalendario newfec){
        //pasamos los parametros
        fechasDeCalendario.get(indice).setFechaC(newfec.getFechaC());
        fechasDeCalendario.get(indice).setFechaD(newfec.getFechaD());
        
        fechasDeCalendario.get(indice).setIndicePiloto(newfec.getIndicePiloto());
        fechasDeCalendario.get(indice).setIndiceCamion(newfec.getIndiceCamion());
        
        fechasDeCalendario.get(indice).setIndiceProductos(newfec.getIndiceProductos());
        fechasDeCalendario.get(indice).setIndiceCantidad(newfec.getIndiceCantidad());
        
        fechasDeCalendario.get(indice).setActivo(newfec.getActivo());
        fechasDeCalendario.get(indice).setCompra(newfec.getCompra());
    };
    
    //ingresar una fehc nueva
    public void agregarFecha(FechaCalendario newfec){
        fechasDeCalendario.add(newfec);
    }; 
    
    //funciones relacionadas con el excel
    public void cargarFechasExcel(){
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                // Manejo de la fecha con verificación de tipo de celda
                Date fechac = leerFechaCelda(row.getCell(0));
                Date fechad = leerFechaCelda(row.getCell(1));
                
                int indicePiloto = (int) row.getCell(2).getNumericCellValue();
                int indiceCamion = (int) row.getCell(3).getNumericCellValue();
                boolean activo = row.getCell(4).getBooleanCellValue();
                boolean compra = row.getCell(5).getBooleanCellValue();

                Vector<Integer> indiceProductos = new Vector<>();
                Vector<Integer> indiceCantidad = new Vector<>();

                // Leer productos y cantidades de manera flexible
                for (int i = 6; i < row.getLastCellNum(); i += 2) {
                    Cell productoCell = row.getCell(i);
                    Cell cantidadCell = row.getCell(i + 1);

                    if (productoCell != null && productoCell.getCellType() == CellType.NUMERIC) {
                        indiceProductos.add((int) productoCell.getNumericCellValue());
                    } else {
                        break; // No más productos
                    }

                    if (cantidadCell != null && cantidadCell.getCellType() == CellType.NUMERIC) {
                        indiceCantidad.add((int) cantidadCell.getNumericCellValue());
                    } else {
                        indiceCantidad.add(0); // Si la cantidad está vacía, agregar 0
                    }
                }

                FechaCalendario fechaCalendario = new FechaCalendario(fechac, fechad, indicePiloto, indiceCamion, indiceProductos, indiceCantidad, activo, compra);
                fechasDeCalendario.add(fechaCalendario);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    };
    
    // Método para manejar correctamente las fechas
    private Date leerFechaCelda(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            }
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return dateFormat.parse(cell.getStringCellValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null; // Si no es una fecha válida, devuelve null
    }
    
    public void guardarFecharExcel(){
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Calendario");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Fecha C");
            headerRow.createCell(1).setCellValue("Fecha D");
            headerRow.createCell(2).setCellValue("Índice Piloto");
            headerRow.createCell(3).setCellValue("Índice Camión");
            headerRow.createCell(4).setCellValue("Activo");
            headerRow.createCell(5).setCellValue("Compra");
            headerRow.createCell(6).setCellValue("Producto 1");
            headerRow.createCell(7).setCellValue("Cantidad 1");
            // Las columnas adicionales de productos y cantidades se añadirán dinámicamente.

            int rowNum = 1;

            for (FechaCalendario fechaCalendario : fechasDeCalendario) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(dateFormat.format(fechaCalendario.getFechaC()));
                row.createCell(1).setCellValue(dateFormat.format(fechaCalendario.getFechaD()));
                row.createCell(2).setCellValue(fechaCalendario.getIndicePiloto());
                row.createCell(3).setCellValue(fechaCalendario.getIndiceCamion());
                row.createCell(4).setCellValue(fechaCalendario.getActivo());
                row.createCell(5).setCellValue(fechaCalendario.getCompra());

                Vector<Integer> productos = fechaCalendario.getIndiceProductos();
                Vector<Integer> cantidades = fechaCalendario.getIndiceCantidad();

                int colNum = 6;

                // Guardar productos y cantidades en pares
                for (int i = 0; i < productos.size(); i++) {
                    row.createCell(colNum++).setCellValue(productos.get(i));
                    row.createCell(colNum++).setCellValue(cantidades.get(i));
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
