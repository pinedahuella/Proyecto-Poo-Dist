package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import org.apache.poi.ss.usermodel.*; // Importa las clases necesarias de Apache POI para trabajar con hojas de cálculo de Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Importa la clase XSSFWorkbook para trabajar con archivos de Excel en formato .xlsx
import java.io.FileInputStream; // Importa la clase FileInputStream para leer archivos
import java.io.FileOutputStream; // Importa la clase FileOutputStream para escribir en archivos
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida
import java.text.SimpleDateFormat;
import java.time.LocalDate; // Importa la clase LocalDate para trabajar con fechas
import java.time.format.DateTimeFormatter; // Importa la clase DateTimeFormatter para formatear fechas
import java.util.Vector; // Importa la clase Vector para almacenar colecciones de objetos de manera dinámica
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

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
     * Elimina un Camion por su modelo y guarda la lista actualizada en Excel.
     * 
     * @param modelo El modelo del Camiones a eliminar.
     */
    public void eliminarCamion(String modelo) {
        camiones.removeIf(camion -> camion.getModelo().equals(modelo));
        guardarCamionesEnExcel();
    }

    /**
     * Carga los datos de Camiones desde el archivo Excel y llena el vector de camiones.
     */
    public void cargarCamionesDesdeExcel() {
        camiones.clear();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Lee las filas de la hoja de Excel
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Omite la fila de encabezado
                }

                // Extrae los valores de las celdas
                String placas = getStringCellValue(row.getCell(0));
                String modelo = getStringCellValue(row.getCell(1));
                String marca = getStringCellValue(row.getCell(2));
                String estado = getStringCellValue(row.getCell(3));
                String tipoCombustible = getStringCellValue(row.getCell(4));
                double kilometraje = getNumericCellValue(row.getCell(5));
                double capacidadCarga = getNumericCellValue(row.getCell(6));
                String newnewnuevoAñoFabricacion = getStringCellValue(row.getCell(7));
                
                double costoReparacion = getNumericCellValue(row.getCell(8));
                double costoGalon = getNumericCellValue(row.getCell(9));
                double galones = getNumericCellValue(row.getCell(10));
                double costoMantenimiento = getNumericCellValue(row.getCell(11));
                double gastoNoEspecificado = getNumericCellValue(row.getCell(12));
                String descripcionDelGasto = getStringCellValue(row.getCell(13));
                String tiempoEnReparacion = getStringCellValue(row.getCell(14));
                String newnewnuevaFechaMantenimiento = getStringCellValue(row.getCell(15));
                double total = getNumericCellValue(row.getCell(16));
                double costoTotalCombustible = getNumericCellValue(row.getCell(17));

                
                // Procesa la fecha de nacimiento desde el formato Excel.
                String nuevoAñoFabricacion;
                if (newnewnuevoAñoFabricacion.length() > 7) {
                    nuevoAñoFabricacion = newnewnuevoAñoFabricacion; // Si ya es un String válido.
                } else {
                    int newnuevoAñoFabricacion = Integer.parseInt(newnewnuevoAñoFabricacion.split("\\.")[0]);
                    
                    LocalDate excelStartDate = LocalDate.of(1900, 1, 1);
                    if (newnuevoAñoFabricacion > 59) {
                        newnuevoAñoFabricacion--; 
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = excelStartDate.plusDays(newnuevoAñoFabricacion - 1);
                    nuevoAñoFabricacion = date.format(formatter); // Formato final de la fecha.
                }
                
                
                // Procesa la fecha de nacimiento desde el formato Excel.
                String nuevaFechaMantenimiento;
                if (newnewnuevaFechaMantenimiento.length() > 7) {
                    nuevaFechaMantenimiento = newnewnuevaFechaMantenimiento; // Si ya es un String válido.
                } else {
                    int newnuevaFechaMantenimiento = Integer.parseInt(newnewnuevaFechaMantenimiento.split("\\.")[0]);
                    
                    LocalDate excelStartDate = LocalDate.of(1900, 1, 1);
                    if (newnuevaFechaMantenimiento > 59) {
                        newnuevaFechaMantenimiento--; 
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = excelStartDate.plusDays(newnuevaFechaMantenimiento - 1);
                    nuevaFechaMantenimiento = date.format(formatter); // Formato final de la fecha.
                }
                

                Camiones camiones = new Camiones(placas, estado, tipoCombustible, kilometraje, capacidadCarga, 
                                                  nuevoAñoFabricacion, modelo, marca, costoReparacion, costoGalon, 
                                                  galones, costoMantenimiento, gastoNoEspecificado, 
                                                  descripcionDelGasto, tiempoEnReparacion, nuevaFechaMantenimiento, total);
                camiones.setCostoTotalCombustible(costoTotalCombustible);
                this.camiones.add(camiones);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private double getNumericCellValue(Cell cell) {
    if (cell == null) {
        return 0;
    }
    switch (cell.getCellType()) {
        case NUMERIC:
            return cell.getNumericCellValue();
        case STRING:
            String stringValue = cell.getStringCellValue().trim();
            if (stringValue.equalsIgnoreCase("Ninguno") || stringValue.isEmpty()) {
                return 0;
            }
            try {
                return Double.parseDouble(stringValue);
            } catch (NumberFormatException e) {
                System.err.println("No se pudo convertir a número: " + stringValue);
                return 0;
            }
        case BOOLEAN:
            return cell.getBooleanCellValue() ? 1 : 0;
        default:
            return 0;
    }
}
    

// Método para obtener el valor de una celda de tipo String
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                }
                return String.format("%.0f", cell.getNumericCellValue());
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

  

    /**
     * Guarda la lista de camiones en el archivo Excel.
     */
    public void guardarCamionesEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Camiones");

            // Crear la fila de encabezado
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
            headerRow.createCell(16).setCellValue("Total");
            headerRow.createCell(17).setCellValue("Costo Total Combustible");

            // Llenar los datos de los camiones
            int rowIndex = 1;
            for (Camiones camion : camiones) {
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
            }

            // Escribir el libro en el archivo
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
