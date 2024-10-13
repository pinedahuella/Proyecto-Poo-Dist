package GestionDeCamiones;

import GestionDeCamiones.CAMIONESFACTURA;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Vector;
import javax.swing.JOptionPane;

public class FACTURASGESTIONCAMIONES {

    private Vector<CAMIONESFACTURA> camionesfactura = new Vector<>();
    private String excelFilePath;


    public FACTURASGESTIONCAMIONES() {
        excelFilePath = "excels/CAMIONESFACTURA.xlsx";
    }

 
    public FACTURASGESTIONCAMIONES(Vector<CAMIONESFACTURA> camionesfactura) {
        this.camionesfactura = camionesfactura;
        excelFilePath = "excels/CAMIONESFACTURA.xlsx";
    }


    public void setCamiones(Vector<CAMIONESFACTURA> camionesfactura) {
        this.camionesfactura = camionesfactura;
    }

    public Vector<CAMIONESFACTURA> getCamionesFactura() {
        return this.camionesfactura;
    }

    public Vector<CAMIONESFACTURA> getCamionesfactura() {
        return camionesfactura;
    }

    public String getExcelFilePath() {
        return excelFilePath;
    }


    public void setUNCAMIONNFACTURA(CAMIONESFACTURA camionfactura) {
        this.camionesfactura.add(camionfactura);
    }


    public void actualizarCamion(int indice, String placasFactura, String fechaFactura, String tipoDeGastoFactura, 
                                  String descripcionFactura, double montoFactura, String estadoFactura, 
                                  String tiempoDeReparacionFactura, String horaActual) {
        if (indice >= 0 && indice < camionesfactura.size()) {
            CAMIONESFACTURA camionfactura = this.camionesfactura.get(indice);
            camionfactura.setPlacasFactura(placasFactura);
            camionfactura.setFechaFactura(fechaFactura);
            camionfactura.setTipoDeGastoFactura(tipoDeGastoFactura);
            camionfactura.setDescripcionFactura(descripcionFactura);
            camionfactura.setMontoFactura(montoFactura);
            camionfactura.setEstadoFactura(estadoFactura);
            camionfactura.setTiempoDeReparacionFactura(tiempoDeReparacionFactura);
            camionfactura.setHoraActual(horaActual);
        } else {
            JOptionPane.showMessageDialog(null, "Índice fuera de rango.");
        }
    }

    public void cargarFacturasDesdeExcel() {
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String placasFactura = getStringCellValue(row.getCell(0));
            String fechaFactura = getStringCellValue(row.getCell(1));
            String tipoDeGastoFactura = getStringCellValue(row.getCell(2));
            String descripcionFactura = getStringCellValue(row.getCell(3));
            double montoFactura = getNumericCellValue(row.getCell(4));
            String estadoFactura = getStringCellValue(row.getCell(5));
            String tiempoDeReparacionFactura = getStringCellValue(row.getCell(6));
            String horaAgregado = getStringCellValue(row.getCell(7)); // Añadir esta línea

            CAMIONESFACTURA camionfactura = new CAMIONESFACTURA(placasFactura, fechaFactura, tipoDeGastoFactura, 
                                                               descripcionFactura, montoFactura, estadoFactura, 
                                                               tiempoDeReparacionFactura, horaAgregado);
            this.camionesfactura.add(camionfactura);
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar las facturas desde el archivo Excel: " + e.getMessage());
    }
}
    
    

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error al convertir el valor numérico: " + e.getMessage());
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

public void guardarFacturasEnExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Facturas");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Placas", "Fecha", "Tipo de Gasto", "Descripción", "Monto", "Estado", "Tiempo de Reparación", "Hora de Agregado" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Agregar datos
            int rowIndex = 1;
            for (CAMIONESFACTURA factura : camionesfactura) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(factura.getPlacasFactura());
                row.createCell(1).setCellValue(factura.getFechaFactura());
                row.createCell(2).setCellValue(factura.getTipoDeGastoFactura());
                row.createCell(3).setCellValue(factura.getDescripcionFactura());
                row.createCell(4).setCellValue(factura.getMontoFactura());
                row.createCell(5).setCellValue(factura.getEstadoFactura());
                row.createCell(6).setCellValue(factura.getTiempoDeReparacionFactura());
                row.createCell(7).setCellValue(factura.getHoraActual());
            }

        
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar las facturas en el archivo Excel: " + e.getMessage());
        }
    }
}