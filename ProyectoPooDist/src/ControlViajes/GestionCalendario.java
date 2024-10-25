package ControlViajes;

import ControlViajes.FechaCalendario;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.Piloto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ControlPedidos.*;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * Clase que gestiona el calendario de viajes y fechas
 * Maneja la persistencia en Excel y la lógica de negocio relacionada con las fechas
 */
public class GestionCalendario {
    
    private Vector<FechaCalendario> fechasDeCalendario;
    private String excelFilePath;
    private SimpleDateFormat dateFormat;
    
    /**
     * Constructor por defecto
     */
    public GestionCalendario() {
        fechasDeCalendario = new Vector<>();
        excelFilePath = "excels/pedidos_calendario.xlsx";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    /**
     * Constructor con parámetros
     * @param fechasDeCalendario Vector inicial de fechas
     */
    public GestionCalendario(Vector<FechaCalendario> fechasDeCalendario) {
        this.fechasDeCalendario = fechasDeCalendario;
        excelFilePath = "excels/pedidos_calendario.xlsx";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    // Getters y Setters
    
    public Vector<FechaCalendario> getFechasDeCalendario() {
        return this.fechasDeCalendario;
    }
    
    public void setFechasDeCalendario(Vector<FechaCalendario> fechasDeCalendario) {
        this.fechasDeCalendario = fechasDeCalendario;
    }
    
    /**
     * Modifica una fecha existente en el calendario
     * @param indice Índice de la fecha a modificar
     * @param nuevaFecha Nueva información de la fecha
     */
    public void modificarFecha(int indice, FechaCalendario nuevaFecha) {
        if (indice >= 0 && indice < fechasDeCalendario.size()) {
            FechaCalendario fecha = fechasDeCalendario.get(indice);
            fecha.setFechaC(nuevaFecha.getFechaC());
            fecha.setFechaD(nuevaFecha.getFechaD());
            fecha.setIndicePiloto(nuevaFecha.getIndicePiloto());
            fecha.setIndiceCamion(nuevaFecha.getIndiceCamion());
            fecha.setIndiceProductos(nuevaFecha.getIndiceProductos());
            fecha.setIndiceCantidad(nuevaFecha.getIndiceCantidad());
            fecha.setActivo(nuevaFecha.getActivo());
            fecha.setCompra(nuevaFecha.getCompra());
            
            guardarFecharExcel();
        }
    }
    
    /**
     * Agrega una nueva fecha al calendario
     * @param nuevaFecha Fecha a agregar
     */
    public void agregarFecha(FechaCalendario nuevaFecha) {
        fechasDeCalendario.add(nuevaFecha);
        guardarFecharExcel();
    }
    
    /**
     * Carga las fechas desde el archivo Excel
     * Solo carga fechas activas con camiones activos
     */
    public void cargarFechasExcel() {
        
        int indiceactual = 0;
 
    fechasDeCalendario.clear();
    
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);
          //cargamos los pedidos
        GestionPedido gespedidos = new GestionPedido();
        gespedidos.CargaDeExcel();
        
        // Cargar camiones activos y sus índices
        GESTIONCAMIONES gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        Vector<Camiones> camionesActivos = gestionCamiones.getCamiones();
        
        // Crear mapa de índices válidos de camiones
        Map<Integer, Boolean> indicesCamionesValidos = new HashMap<>();
        for (int i = 0; i < camionesActivos.size(); i++) {
            indicesCamionesValidos.put(i, true);
        }
        
        // Procesar cada fila del calendario
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header
            
            try {
                Date fechac = leerFechaCelda(row.getCell(0));
                Date fechad = leerFechaCelda(row.getCell(1));
                int indicePiloto = (int) row.getCell(2).getNumericCellValue();
                int indiceCamion = (int) row.getCell(3).getNumericCellValue();
                boolean activo = row.getCell(4).getBooleanCellValue();
                boolean compra = row.getCell(5).getBooleanCellValue();
                
                // Solo cargar la fecha si:
                // 1. Está activa
                // 2. El índice del camión es válido
                // 3. El camión referenciado está activo
                if (indicesCamionesValidos.containsKey(indiceCamion)) {
                    Vector<Integer> indiceProductos = new Vector<>();
                    Vector<Integer> indiceCantidad = new Vector<>();
                    
                    // Leer productos y cantidades
                    for (int i = 6; i < row.getLastCellNum(); i += 2) {
                        Cell productoCell = row.getCell(i);
                        Cell cantidadCell = row.getCell(i + 1);
                        
                        if (productoCell != null && productoCell.getCellType() == CellType.NUMERIC) {
                            indiceProductos.add((int) productoCell.getNumericCellValue());
                            indiceCantidad.add(cantidadCell != null ? 
                                (int) cantidadCell.getNumericCellValue() : 0);
                        }
                    }
                    
                    FechaCalendario fechaCalendario = new FechaCalendario(
                        fechac, fechad, indicePiloto, indiceCamion,
                        indiceProductos, indiceCantidad, activo, compra
                    );
                    fechasDeCalendario.add(fechaCalendario);
                }else{
                    gespedidos.actualizarIndiceCalendario(indiceactual);
                }
                
                indiceactual++;
            } catch (Exception e) {
                System.err.println("Error al procesar la fila " + row.getRowNum() + ": " + e.getMessage());
            }
        }
        
        gespedidos.GuardarEnExcel();

    } catch (IOException e) {
        System.err.println("Error al cargar el archivo Excel: " + e.getMessage());
        e.printStackTrace();
    }
}
    /**
     * Lee una fecha desde una celda de Excel
     * @param cell Celda a leer
     * @return Date objeto fecha, null si no es válida
     */
    private Date leerFechaCelda(Cell cell) {
        if (cell == null) return null;
        
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    }
                    break;
                case STRING:
                    try {
                        return dateFormat.parse(cell.getStringCellValue());
                    } catch (Exception e) {
                        System.err.println("Error al parsear fecha string: " + e.getMessage());
                    }
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error al leer fecha de celda: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Guarda todas las fechas en el archivo Excel
     */
    public void guardarFecharExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Calendario");
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Fecha C", "Fecha D", "Índice Piloto", "Índice Camión",
                "Activo", "Compra", "Producto 1", "Cantidad 1"
            };
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                
                // Dar formato al encabezado
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(headerStyle);
            }

            // Escribir datos
            int rowNum = 1;
            for (FechaCalendario fecha : fechasDeCalendario) {
                Row row = sheet.createRow(rowNum++);
                
                // Crear estilo para fechas
                CellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));
                
                // Escribir fechas con formato
                Cell cellFechaC = row.createCell(0);
                Cell cellFechaD = row.createCell(1);
                cellFechaC.setCellValue(fecha.getFechaC());
                cellFechaD.setCellValue(fecha.getFechaD());
                cellFechaC.setCellStyle(dateStyle);
                cellFechaD.setCellStyle(dateStyle);
                
                // Escribir resto de datos
                row.createCell(2).setCellValue(fecha.getIndicePiloto());
                row.createCell(3).setCellValue(fecha.getIndiceCamion());
                row.createCell(4).setCellValue(fecha.getActivo());
                row.createCell(5).setCellValue(fecha.getCompra());
                
                // Escribir productos y cantidades
                Vector<Integer> productos = fecha.getIndiceProductos();
                Vector<Integer> cantidades = fecha.getIndiceCantidad();
                
                int colNum = 6;
                for (int i = 0; i < productos.size(); i++) {
                    row.createCell(colNum++).setCellValue(productos.get(i));
                    row.createCell(colNum++).setCellValue(cantidades.get(i));
                }
            }

            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Guardar archivo
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
            
        } catch (IOException e) {
            System.err.println("Error al guardar en Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    /**
     * Verifica si existe alguna fecha activa para un camión específico
     * @param indiceCamion Índice del camión a verificar
     * @return true si existen fechas activas para el camión
     */
    public boolean existenFechasActivasPorCamion(int indiceCamion) {
        for (FechaCalendario fecha : fechasDeCalendario) {
            if (fecha.getIndiceCamion() == indiceCamion && fecha.getActivo()) {
                return true;
            }
        }
        return false;
    }


    public boolean existenFechasActivasPorPiloto(int indicePiloto) {
        for (FechaCalendario fecha : fechasDeCalendario) {
            if (fecha.getIndicePiloto() == indicePiloto && fecha.getActivo()) {
                return true;
            }
        }
        return false;
    }
}