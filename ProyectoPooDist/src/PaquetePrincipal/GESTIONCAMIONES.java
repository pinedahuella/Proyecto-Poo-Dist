package PaquetePrincipal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class GESTIONCAMIONES {
    private Vector<Camiones> camiones;
    private final String excelFilePath;

    public GESTIONCAMIONES() {
        this.camiones = new Vector<>();
        this.excelFilePath = "excels/CAMIONES.xlsx";
        cargarCamionesDesdeExcel(); // Cargar camiones al inicializar
    }

    public GESTIONCAMIONES(Vector<Camiones> camiones) {
        this.camiones = (camiones != null) ? camiones : new Vector<>();
        this.excelFilePath = "excels/CAMIONES.xlsx";
        cargarCamionesDesdeExcel(); // Cargar camiones al inicializar
    }

    public void setCamiones(Vector<Camiones> camiones) {
        this.camiones = (camiones != null) ? camiones : new Vector<>();
    }

    public Vector<Camiones> getCamiones() {
        return this.camiones;
    }

    public void setUnCamion(Camiones camion) {
        if (camion != null) {
            this.camiones.add(camion);
        }
    }

    public void actualizarCamion(int indice, String placas, String estado, String tipoCombustible,
                                  double kilometraje, double capacidadCarga, String añoFabricacion,
                                  String modelo, String marca, double costos) {
        if (indice >= 0 && indice < camiones.size()) {
            Camiones camion = this.camiones.get(indice);
            camion.setPlacas(placas);
            camion.setEstado(estado);
            camion.setTipoCombustible(tipoCombustible);
            camion.setKilometraje(kilometraje);
            camion.setCapacidadCarga(capacidadCarga);
            camion.setAñoFabricacion(añoFabricacion);
            camion.setModelo(modelo);
            camion.setMarca(marca);
            camion.setCostos(costos);
        } else {
            JOptionPane.showMessageDialog(null, "Índice de camión inválido.");
        }
    }

    public void cargarCamionesDesdeExcel() {
        try {
            FileInputStream file = new FileInputStream(excelFilePath); // Usa excelFilePath
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Selecciona la hoja correcta

            camiones = new Vector<>(); // Reinicia la lista

            for (Row row : sheet) {
                Camiones camion = new Camiones();
                // Asigna los valores a los atributos del objeto Camiones
                camion.setPlacas(getStringCellValue(row.getCell(0)));
                camion.setEstado(getStringCellValue(row.getCell(1)));
                camion.setTipoCombustible(getStringCellValue(row.getCell(2)));
                camion.setKilometraje(getNumericCellValue(row.getCell(3)));
                camion.setCapacidadCarga(getNumericCellValue(row.getCell(4)));
                camion.setAñoFabricacion(getStringCellValue(row.getCell(5)));
                camion.setModelo(getStringCellValue(row.getCell(6)));
                camion.setMarca(getStringCellValue(row.getCell(7)));
                camion.setCostos(getNumericCellValue(row.getCell(8)));

                camiones.add(camion); // Añade el camion a la lista
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error en la consola para depuración
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }
        return cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue() : 0;
    }

    public void guardarCamionesEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Camiones");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Placas");
            headerRow.createCell(1).setCellValue("Estado");
            headerRow.createCell(2).setCellValue("Tipo de Combustible");
            headerRow.createCell(3).setCellValue("Kilometraje");
            headerRow.createCell(4).setCellValue("Capacidad de Carga");
            headerRow.createCell(5).setCellValue("Año de Fabricación");
            headerRow.createCell(6).setCellValue("Modelo");
            headerRow.createCell(7).setCellValue("Marca");
            headerRow.createCell(8).setCellValue("Costos");

            int rowCount = 1;
            for (Camiones camion : camiones) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(camion.getPlacas());
                row.createCell(1).setCellValue(camion.getEstado());
                row.createCell(2).setCellValue(camion.getTipoCombustible());
                row.createCell(3).setCellValue(camion.getKilometraje());
                row.createCell(4).setCellValue(camion.getCapacidadCarga());
                row.createCell(5).setCellValue(camion.getAñoFabricacion());
                row.createCell(6).setCellValue(camion.getModelo());
                row.createCell(7).setCellValue(camion.getMarca());
                row.createCell(8).setCellValue(camion.getCostos());
            }

            workbook.write(fos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar camiones en Excel: " + e.getMessage());
        }
    }
}