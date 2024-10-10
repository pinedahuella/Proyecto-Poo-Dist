package PaquetePrincipal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class GESTIONCAMIONES {

    private Vector<Camiones> camiones = new Vector<>();
    private String excelFilePath;

    public GESTIONCAMIONES() {
        excelFilePath = "excels/CAMIONES.xlsx";
    }

    public GESTIONCAMIONES(Vector<Camiones> camiones) {
        this.camiones = camiones;
        excelFilePath = "excels/CAMIONES.xlsx";
    }

    public void setCamiones(Vector<Camiones> camiones) {
        this.camiones = camiones;
    }

    public Vector<Camiones> getCamiones() {
        return this.camiones;
    }

    public void agregarCamion(Camiones camion) {
        this.camiones.add(camion);
        guardarCamionesEnExcel();
    }

    
    
public void actualizarCamion(Camiones camionActualizado) {
    boolean encontrado = false;
    
    // Buscar el camión por el atributo único, como el modelo o las placas
    for (int i = 0; i < camiones.size(); i++) {
        if (camiones.get(i).getPlacas().equals(camionActualizado.getPlacas())) {
            // Si se encuentra, actualiza el camión con la nueva información
            camiones.set(i, camionActualizado);
            encontrado = true;
            break;
        }
    }
    
    // Si no se encuentra, se puede añadir el nuevo camión a la lista
    if (!encontrado) {
        camiones.add(camionActualizado);
    }
    
    // Guardar los camiones actualizados en el archivo Excel
    guardarCamionesEnExcel();
    
    // Recargar los datos desde el archivo Excel (opcional)
    cargarCamionesDesdeExcel();
}
       
       
    public void eliminarCamion(String modelo) {
        camiones.removeIf(camion -> camion.getModelo().equals(modelo));
        guardarCamionesEnExcel();
    }
    
    
    public void cargarCamionesDesdeExcel() {
        camiones.clear();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }


                String placas = getStringCellValue(row.getCell(0));
                String modelo = getStringCellValue(row.getCell(1));
                String marca = getStringCellValue(row.getCell(2));
                String estado = getStringCellValue(row.getCell(3));
                String tipoCombustible = getStringCellValue(row.getCell(4));
                double kilometraje = getNumericCellValue(row.getCell(5));
                double capacidadCarga = getNumericCellValue(row.getCell(6));
                String nuevoAñoFabricacion = getStringCellValue(row.getCell(7));


                double costoReparacion = getNumericCellValue(row.getCell(8));
                double costoGalon = getNumericCellValue(row.getCell(9));
                double galones = getNumericCellValue(row.getCell(10));
                double costoMantenimiento = getNumericCellValue(row.getCell(11));
                double gastoNoEspecificado = getNumericCellValue(row.getCell(12));
                String descripcionDelGasto = getStringCellValue(row.getCell(13));
                String tiempoEnReparacion = getStringCellValue(row.getCell(14));
                String nuevaFechaMantenimiento = getStringCellValue(row.getCell(15));
                double total = getNumericCellValue(row.getCell(16));
                double costoTotalCombustible = getNumericCellValue(row.getCell(17));


                String añoFabricacion = procesarFecha(nuevoAñoFabricacion);

                String fechaDeMantenimiento = procesarFecha(nuevaFechaMantenimiento);

                 Camiones camiones = new Camiones(placas, estado, tipoCombustible, kilometraje, capacidadCarga, 
                                                  añoFabricacion, modelo, marca, costoReparacion, costoGalon, 
                                                  galones, costoMantenimiento, gastoNoEspecificado, 
                                                  descripcionDelGasto, tiempoEnReparacion, fechaDeMantenimiento, total);
                camiones.setCostoTotalCombustible(costoTotalCombustible);
                this.camiones.add(camiones);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String procesarFecha(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return "";
        }
        try {
            double fechaExcel = Double.parseDouble(fecha);
            LocalDate localDate = LocalDate.of(1900, 1, 1).plusDays((long) fechaExcel - 2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return localDate.format(formatter);
        } catch (NumberFormatException e) {
            return fecha;
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
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

public void guardarCamionesEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Camiones");

  
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Placas");
            headerRow.createCell(1).setCellValue("Modelo");
            headerRow.createCell(2).setCellValue("Marca");
            headerRow.createCell(3).setCellValue("Estado");
            headerRow.createCell(4).setCellValue("Tipo de Combustible");
            headerRow.createCell(5).setCellValue("Kilometraje");
            headerRow.createCell(6).setCellValue("Capacidad de Carga");
            headerRow.createCell(7).setCellValue("Año de Fabricación");
            headerRow.createCell(8).setCellValue("Costo Reparación");
            headerRow.createCell(9).setCellValue("Costo Galón");
            headerRow.createCell(10).setCellValue("Galones");
            headerRow.createCell(11).setCellValue("Costo Mantenimiento");
            headerRow.createCell(12).setCellValue("Gasto No Especificado");
            headerRow.createCell(13).setCellValue("Descripción del Gasto");
            headerRow.createCell(14).setCellValue("Tiempo en Reparación");
            headerRow.createCell(15).setCellValue("Fecha de Mantenimiento");
            headerRow.createCell(16).setCellValue("Total Invertido");

            int rowCount = 1;
            for (Camiones camiones : camiones) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(camiones.getPlacas());
                row.createCell(1).setCellValue(camiones.getModelo());
                row.createCell(2).setCellValue(camiones.getMarca());
                row.createCell(3).setCellValue(camiones.getEstado());
                row.createCell(4).setCellValue(camiones.getTipoCombustible());
                row.createCell(5).setCellValue(camiones.getKilometraje());
                row.createCell(6).setCellValue(camiones.getCapacidadCarga());
                row.createCell(7).setCellValue(camiones.getAñoFabricacion());
                row.createCell(8).setCellValue(camiones.getCostoReparacion());
                row.createCell(9).setCellValue(camiones.getCostoGalon());
                row.createCell(10).setCellValue(camiones.getGalones());
                row.createCell(11).setCellValue(camiones.getCostoMantenimiento());
                row.createCell(12).setCellValue(camiones.getGastoNoEspecificado());
                row.createCell(13).setCellValue(camiones.getDescripcionDelGasto());
                row.createCell(14).setCellValue(camiones.getTiempoEnReparacion());
                row.createCell(15).setCellValue(camiones.getFechaDeMantenimiento());
                row.createCell(16).setCellValue(camiones.getTotal());
                headerRow.createCell(17).setCellValue("Costo Total Combustible");

            }

            workbook.write(fos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}