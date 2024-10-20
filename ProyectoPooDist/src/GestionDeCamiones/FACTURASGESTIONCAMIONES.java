package GestionDeCamiones;

// Importación de clases necesarias para la gestión de camiones y facturas
import org.apache.poi.ss.usermodel.*; // Importa las clases necesarias de Apache POI para trabajar con archivos Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Importa la clase XSSFWorkbook para manejar archivos Excel en formato .xlsx
import java.io.FileInputStream; // Importa la clase FileInputStream para leer archivos desde el sistema de archivos
import java.io.FileOutputStream; // Importa la clase FileOutputStream para escribir archivos en el sistema de archivos
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida
import java.util.Vector; // Importa la clase Vector para almacenar datos en una lista dinámica
import javax.swing.JOptionPane; // Importa la clase JOptionPane para mostrar diálogos de mensaje y entrada


/**
 * La clase FACTURASGESTIONCAMIONES gestiona una colección de objetos CAMIONESFACTURA
 * y proporciona funcionalidades para cargar y guardar estas facturas en un archivo Excel.
 */
public class FACTURASGESTIONCAMIONES {

    // Vector que almacena las facturas de camiones
    private Vector<CAMIONESFACTURA> camionesfactura = new Vector<>();
    // Ruta del archivo Excel donde se almacenan las facturas
    private String excelFilePath;

    /**
     * Constructor por defecto que inicializa la ruta del archivo Excel.
     */
    public FACTURASGESTIONCAMIONES() {
        excelFilePath = "excels/CAMIONESFACTURA.xlsx";
    }

    /**
     * Constructor que permite establecer la lista de facturas.
     * 
     * @param camionesfactura Vector de CAMIONESFACTURA a inicializar.
     */
    public FACTURASGESTIONCAMIONES(Vector<CAMIONESFACTURA> camionesfactura) {
        this.camionesfactura = camionesfactura;
        excelFilePath = "excels/CAMIONESFACTURA.xlsx";
    }

    /**
     * Establece la lista de facturas.
     * 
     * @param camionesfactura Vector de CAMIONESFACTURA a establecer.
     */
    public void setCamiones(Vector<CAMIONESFACTURA> camionesfactura) {
        this.camionesfactura = camionesfactura;
    }

    /**
     * Obtiene la lista de facturas.
     * 
     * @return Vector de CAMIONESFACTURA.
     */
    public Vector<CAMIONESFACTURA> getCamionesFactura() {
        return this.camionesfactura;
    }

    /**
     * Obtiene la lista de facturas (sin encapsulamiento).
     * 
     * @return Vector de CAMIONESFACTURA.
     */
    public Vector<CAMIONESFACTURA> getCamionesfactura() {
        return camionesfactura;
    }

    /**
     * Obtiene la ruta del archivo Excel.
     * 
     * @return Ruta del archivo Excel.
     */
    public String getExcelFilePath() {
        return excelFilePath;
    }

    /**
     * Agrega una nueva factura al vector de facturas.
     * 
     * @param camionfactura Objeto CAMIONESFACTURA a agregar.
     */
    public void setUNCAMIONNFACTURA(CAMIONESFACTURA camionfactura) {
        this.camionesfactura.add(camionfactura);
    }

    /**
     * Actualiza los detalles de una factura existente.
     * 
     * @param indice                    Índice de la factura a actualizar.
     * @param placasFactura             Placas del camión.
     * @param fechaFactura              Fecha de la factura.
     * @param tipoDeGastoFactura        Tipo de gasto.
     * @param descripcionFactura         Descripción de la factura.
     * @param montoFactura              Monto de la factura.
     * @param estadoFactura             Estado de la factura.
     * @param tiempoDeReparacionFactura Tiempo de reparación.
     * @param horaActual                Hora actual.
     */
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

    /**
     * Carga las facturas desde un archivo Excel y las agrega al vector de facturas.
     */
    public void cargarFacturasDesdeExcel() {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Saltar la fila de encabezados
                }

                String placasFactura = getStringCellValue(row.getCell(0));
                String fechaFactura = getStringCellValue(row.getCell(1));
                String tipoDeGastoFactura = getStringCellValue(row.getCell(2));
                String descripcionFactura = getStringCellValue(row.getCell(3));
                double montoFactura = getNumericCellValue(row.getCell(4));
                String estadoFactura = getStringCellValue(row.getCell(5));
                String tiempoDeReparacionFactura = getStringCellValue(row.getCell(6));
                String horaAgregado = getStringCellValue(row.getCell(7)); // Añadir esta línea

                // Crear un nuevo objeto CAMIONESFACTURA y agregarlo al vector
                CAMIONESFACTURA camionfactura = new CAMIONESFACTURA(placasFactura, fechaFactura, tipoDeGastoFactura, 
                                                                   descripcionFactura, montoFactura, estadoFactura, 
                                                                   tiempoDeReparacionFactura, horaAgregado);
                this.camionesfactura.add(camionfactura);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar las facturas desde el archivo Excel: " + e.getMessage());
        }
    }

    /**
     * Obtiene el valor de una celda como una cadena.
     * 
     * @param cell Celda de la cual obtener el valor.
     * @return Valor de la celda como cadena.
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
     * Obtiene el valor de una celda como un número.
     * 
     * @param cell Celda de la cual obtener el valor.
     * @return Valor de la celda como número.
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
                    JOptionPane.showMessageDialog(null, "Error al convertir el valor numérico: " + e.getMessage());
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

    /**
     * Guarda las facturas actuales en el archivo Excel.
     */
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

            // Guardar el archivo Excel
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar las facturas en el archivo Excel: " + e.getMessage());
        }
    }
}
