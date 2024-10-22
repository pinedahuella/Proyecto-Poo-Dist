package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * La clase GESTIONCAMIONES gestiona una colección de camiones (Camiones).
 * Proporciona funcionalidad para agregar, actualizar, eliminar y cargar datos 
 * de camiones desde un archivo Excel. La clase utiliza Apache POI para manejar 
 * las operaciones con archivos Excel.
 */
public class GESTIONCAMIONES {

    private Vector<Camiones> camiones = new Vector<>();
    private String excelFilePath;

    /**
     * Constructor por defecto que inicializa la ruta del archivo Excel.
     */
    public GESTIONCAMIONES() {
        excelFilePath = "excels/CAMIONES.xlsx";
    }

    /**
     * Constructor que inicializa el vector de camiones con una lista proporcionada de Camiones.
     * 
     * @param camiones Un Vector de Camiones para inicializar la clase.
     */
    public GESTIONCAMIONES(Vector<Camiones> camiones) {
        this.camiones = camiones;
        excelFilePath = "excels/CAMIONES.xlsx";
    }

    /**
     * Establece el Vector de Camiones.
     * 
     * @param camiones Un Vector de Camiones para establecer.
     */
    public void setCamiones(Vector<Camiones> camiones) {
        this.camiones = camiones;
    }

    /**
     * Obtiene el Vector actual de Camiones.
     * 
     * @return Un Vector de Camiones.
     */
    public Vector<Camiones> getCamiones() {
        return this.camiones;
    }

    /**
     * Agrega un nuevo Camion a la colección y lo guarda en el archivo Excel.
     * 
     * @param camion El objeto Camiones a agregar.
     */
    public void agregarCamion(Camiones camion) {
        this.camiones.add(camion);
        guardarCamionesEnExcel();
    }

    /**
     * Actualiza un Camion existente o lo agrega si no se encuentra, luego guarda los cambios en Excel.
     * 
     * @param camionActualizado El objeto Camiones actualizado.
     */
    public void actualizarCamion(Camiones camionActualizado) {
        boolean encontrado = false;

        // Busca el camión por un atributo único, como el modelo o las placas
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

    
    /**
     * Carga los datos de Camiones desde el archivo Excel y llena el vector de camiones.
     */
private String procesarFecha(String fechaExcel) {
    if (fechaExcel == null || fechaExcel.isEmpty()) {
        return "";
    }

    try {
        // Intentar parsear directamente si ya está en formato dd/MM/yyyy
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(fechaExcel, formateador);
            return fechaExcel;
        } catch (DateTimeParseException e) {
            // Si no se puede parsear, continuar con el proceso para número
        }

            // Intentar procesar como número de Excel
            double numeroExcel;
            if (fechaExcel.contains(".")) {
                numeroExcel = Double.parseDouble(fechaExcel);
            } else {
                numeroExcel = Integer.parseInt(fechaExcel);
            }

            // Convertir el número de Excel a fecha
            int dias = (int) numeroExcel;

            // Definir la fecha de inicio de Excel (1900-01-01)
            LocalDate fechaInicioExcel = LocalDate.of(1900, 1, 1);

            // Ajustar la fecha si es necesario (corrección del día 60 en Excel)
            if (dias > 59) {
                dias--;
            }

            // Calcular la fecha sumando los días a la fecha de inicio
            LocalDate fecha = fechaInicioExcel.plusDays(dias - 1);
            
            // Formatear la fecha al formato deseado
            return fecha.format(formateador);

   } catch (NumberFormatException e) {
        return fechaExcel;
    }
}

public void eliminarCamion(String placas) {
    for (Camiones camion : camiones) {
        if (camion.getPlacas().equals(placas)) {
            camion.setActivo(false);  // Solo marca como inactivo
            break;
        }
    }
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
            String añoFabricacion = procesarFecha(getStringCellValue(row.getCell(7)));
            double costoReparacion = getNumericCellValue(row.getCell(8));
            double costoGalon = getNumericCellValue(row.getCell(9));
            double galones = getNumericCellValue(row.getCell(10));
            double costoMantenimiento = getNumericCellValue(row.getCell(11));
            double gastoNoEspecificado = getNumericCellValue(row.getCell(12));
            String descripcionDelGasto = getStringCellValue(row.getCell(13));
            String tiempoEnReparacion = getStringCellValue(row.getCell(14));
            String fechaDeMantenimiento = procesarFecha(getStringCellValue(row.getCell(15)));
            double total = getNumericCellValue(row.getCell(16));
            double costoTotalCombustible = getNumericCellValue(row.getCell(17));
            boolean activo = true;
            
            // Si existe la columna de activo, leerla
            if (row.getCell(18) != null) {
                activo = row.getCell(18).getBooleanCellValue();
            }

            Camiones camion = new Camiones(
                placas, 
                estado, 
                tipoCombustible, 
                kilometraje, 
                capacidadCarga, 
                añoFabricacion, 
                modelo, 
                marca, 
                activo, 
                costoReparacion, 
                costoGalon, 
                galones, 
                costoMantenimiento, 
                gastoNoEspecificado, 
                descripcionDelGasto, 
                tiempoEnReparacion, 
                fechaDeMantenimiento, 
                total,
                costoTotalCombustible
            );
            
            // Solo agregar al vector si está activo
            if (activo) {
                this.camiones.add(camion);
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}


    /**
     * Obtiene el valor de una celda como cadena.
     * 
     * @param cell La celda a procesar.
     * @return El valor de la celda como cadena.
     */
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

    /**
     * Obtiene el valor de una celda como número.
     * 
     * @param cell La celda a procesar.
     * @return El valor de la celda como número.
     */
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

    /**
     * Guarda la lista de camiones en el archivo Excel.
     */
    public void guardarCamionesEnExcel() {
    try {
        // Primero, cargar todos los registros existentes (incluyendo inactivos)
        Vector<Camiones> todosLosCamiones = new Vector<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbookRead = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbookRead.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                
                // Leer todos los campos del Excel existente
                String placas = getStringCellValue(row.getCell(0));
                String modelo = getStringCellValue(row.getCell(1));
                String marca = getStringCellValue(row.getCell(2));
                String estado = getStringCellValue(row.getCell(3));
                String tipoCombustible = getStringCellValue(row.getCell(4));
                double kilometraje = getNumericCellValue(row.getCell(5));
                double capacidadCarga = getNumericCellValue(row.getCell(6));
                String añoFabricacion = getStringCellValue(row.getCell(7));
                double costoReparacion = getNumericCellValue(row.getCell(8));
                double costoGalon = getNumericCellValue(row.getCell(9));
                double galones = getNumericCellValue(row.getCell(10));
                double costoMantenimiento = getNumericCellValue(row.getCell(11));
                double gastoNoEspecificado = getNumericCellValue(row.getCell(12));
                String descripcionDelGasto = getStringCellValue(row.getCell(13));
                String tiempoEnReparacion = getStringCellValue(row.getCell(14));
                String fechaDeMantenimiento = getStringCellValue(row.getCell(15));
                double total = getNumericCellValue(row.getCell(16));
                double costoTotalCombustible = getNumericCellValue(row.getCell(17));
                boolean activo = true;
                if (row.getCell(18) != null) {
                    activo = row.getCell(18).getBooleanCellValue();
                }

                // Verificar si el camión ya existe en la lista actual de camiones activos
                boolean existeEnActivos = false;
                for (Camiones camionActivo : camiones) {
                    if (camionActivo.getPlacas().equals(placas)) {
                        existeEnActivos = true;
                        break;
                    }
                }

                // Si el camión no está en la lista de activos, agregarlo a todosLosCamiones
                if (!existeEnActivos) {
                    Camiones camion = new Camiones(
                        placas, estado, tipoCombustible, kilometraje, capacidadCarga,
                        añoFabricacion, modelo, marca, activo, costoReparacion,
                        costoGalon, galones, costoMantenimiento, gastoNoEspecificado,
                        descripcionDelGasto, tiempoEnReparacion, fechaDeMantenimiento,
                        total, costoTotalCombustible
                    );
                    todosLosCamiones.add(camion);
                }
            }
        }
        
        // Agregar los camiones activos actuales
        todosLosCamiones.addAll(camiones);
        
        // Crear nuevo archivo Excel con todos los registros
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Camiones");
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Placas", "Modelo", "Marca", "Estado", "Tipo de Combustible", 
                "Kilometraje", "Capacidad de Carga", "Año de Fabricación", 
                "Costo Reparación", "Costo Galón", "Galones", "Costo Mantenimiento",
                "Gasto No Especificado", "Descripción del Gasto", "Tiempo en Reparación",
                "Fecha de Mantenimiento", "Total", "Costo Total Combustible", "Activo"
            };
            
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Guardar todos los camiones (activos e inactivos)
            int rowIndex = 1;
            for (Camiones camion : todosLosCamiones) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(camion.getPlacas());
                row.createCell(1).setCellValue(camion.getModelo());
                row.createCell(2).setCellValue(camion.getMarca());
                row.createCell(3).setCellValue(camion.getEstado());
                row.createCell(4).setCellValue(camion.getTipoCombustible());
                row.createCell(5).setCellValue(camion.getKilometraje());
                row.createCell(6).setCellValue(camion.getCapacidadCarga());
                row.createCell(7).setCellValue(camion.getAñoFabricacion());
                row.createCell(8).setCellValue(camion.getCostoReparacion());
                row.createCell(9).setCellValue(camion.getCostoGalon());
                row.createCell(10).setCellValue(camion.getGalones());
                row.createCell(11).setCellValue(camion.getCostoMantenimiento());
                row.createCell(12).setCellValue(camion.getGastoNoEspecificado());
                row.createCell(13).setCellValue(camion.getDescripcionDelGasto());
                row.createCell(14).setCellValue(camion.getTiempoEnReparacion());
                row.createCell(15).setCellValue(camion.getFechaDeMantenimiento());
                row.createCell(16).setCellValue(camion.getTotal());
                row.createCell(17).setCellValue(camion.getCostoTotalCombustible());
                row.createCell(18).setCellValue(camion.isActivo());
            }

            workbook.write(fos);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}