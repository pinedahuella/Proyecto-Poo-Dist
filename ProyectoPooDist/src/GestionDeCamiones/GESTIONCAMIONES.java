package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import ControlViajes.FechaCalendario;
import ControlViajes.GestionCalendario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import static org.apache.poi.ss.usermodel.CellType.*;
import java.util.HashMap;
import java.util.Map;
/**
 * La clase GESTIONCAMIONES gestiona una colección de camiones (Camiones).
 * Proporciona funcionalidad para agregar, actualizar, eliminar y cargar datos 
 * de camiones desde un archivo Excel. La clase utiliza Apache POI para manejar 
 * las operaciones con archivos Excel.
 */
public class GESTIONCAMIONES {

    private Vector<Camiones> camiones = new Vector<>();
    private String excelFilePath;
    private boolean isUpdatingCalendar = false; // Add flag to prevent recursive calls

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


    /**
     * Reordena los índices de los camiones activos y actualiza las referencias en el calendario
     */
    private void reordenarIndices() {
        try {
            // 1. Crear un mapa del índice viejo al nuevo
            Map<Integer, Integer> mapaIndices = new HashMap<>();
            int nuevoIndice = 0;
            
            // Primero crear el mapeo de índices viejos a nuevos
            for (int i = 0; i < camiones.size(); i++) {
                if (camiones.get(i).isActivo()) {
                    mapaIndices.put(i, nuevoIndice);
                    nuevoIndice++;
                }
            }

            // 2. Actualizar las fechas en el calendario con los nuevos índices
            GestionCalendario gestionCalendario = new GestionCalendario();
            gestionCalendario.cargarFechasExcel();
            Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
            boolean seRealizaronCambios = false;

            for (FechaCalendario fecha : fechas) {
                if (fecha.getActivo()) {
                    Integer nuevoIndiceCamion = mapaIndices.get(fecha.getIndiceCamion());
                    if (nuevoIndiceCamion != null) {
                        fecha.setIndiceCamion(nuevoIndiceCamion);
                        seRealizaronCambios = true;
                    }
                }
            }

            // Si se realizaron cambios, guardar el calendario actualizado
            if (seRealizaronCambios) {
                gestionCalendario.setFechasDeCalendario(fechas);
                gestionCalendario.guardarFecharExcel();
            }

        } catch (Exception e) {
            System.err.println("Error al reordenar índices: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método modificado de eliminarCamion para incluir reordenamiento de índices
     */
    public void eliminarCamion(String placas) {
        boolean camionEncontrado = false;
        int indiceCamion = -1;
        
        // Encontrar el camión y marcarlo como inactivo
        for (int i = 0; i < camiones.size(); i++) {
            if (camiones.get(i).getPlacas().equals(placas)) {
                camiones.get(i).setActivo(false);
                camionEncontrado = true;
                indiceCamion = i;
                break;
            }
        }
        
        if (camionEncontrado) {
            // Primero desactivar las fechas asociadas al camión
            actualizarCalendarioPorCamion(indiceCamion);
            
            // Luego reordenar los índices
            reordenarIndices();
            
            // Finalmente guardar los cambios en el archivo de camiones
            guardarCamionesEnExcel();
        }
    }


private void actualizarCalendarioPorCamion(int indiceCamionAEliminar) {
    try {
        System.out.println("Iniciando actualización del calendario para camión índice: " + indiceCamionAEliminar);
        
        // Creamos una instancia de GestionCalendario
        GestionCalendario gestionCalendario = new GestionCalendario();
        
        // Cargamos las fechas actuales
        gestionCalendario.cargarFechasExcel();
        Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
        
        System.out.println("Total de fechas en calendario: " + fechas.size());
        
        // Contador para verificar cambios
        int cambiosRealizados = 0;
        
        // Recorremos todas las fechas y desactivamos las que usan este camión
        for (int i = 0; i < fechas.size(); i++) {
            FechaCalendario fecha = fechas.get(i);
            if (fecha.getIndiceCamion() == indiceCamionAEliminar) {
                System.out.println("Encontrada fecha con camión a eliminar: Índice " + i + 
                                 ", Fecha C: " + fecha.getFechaC() +
                                 ", Estado actual activo: " + fecha.getActivo());
                
                fecha.setActivo(false);
                fechas.set(i, fecha); // Aseguramos que el cambio se guarda en el vector
                cambiosRealizados++;
            }
        }
        
        System.out.println("Cambios realizados: " + cambiosRealizados);
        
        if (cambiosRealizados > 0) {
            // Actualizamos el vector de fechas
            gestionCalendario.setFechasDeCalendario(fechas);
            
            // Guardamos los cambios en el Excel
            gestionCalendario.guardarFecharExcel();
            System.out.println("Cambios guardados en el archivo Excel");
        }
        
    } catch (Exception e) {
        System.err.println("Error al actualizar el calendario: " + e.getMessage());
        e.printStackTrace();
    }
}

// Método auxiliar para imprimir el estado actual del calendario
public void imprimirEstadoCalendario() {
    try {
        GestionCalendario gestionCalendario = new GestionCalendario();
        gestionCalendario.cargarFechasExcel();
        Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
        
        System.out.println("\n=== Estado actual del calendario ===");
        for (int i = 0; i < fechas.size(); i++) {
            FechaCalendario fecha = fechas.get(i);
            System.out.println(String.format(
                "Índice: %d, Camión: %d, Fecha: %s, Activo: %b",
                i,
                fecha.getIndiceCamion(),
                fecha.getFechaC(),
                fecha.getActivo()
            ));
        }
        System.out.println("===================================\n");
    } catch (Exception e) {
        System.err.println("Error al imprimir estado del calendario: " + e.getMessage());
    }
}

private void actualizarIndicesEnCalendario(Vector<Camiones> todosLosCamiones) {
        // Prevent recursive calls
        if (isUpdatingCalendar) {
            return;
        }
        
        try {
            isUpdatingCalendar = true;
            
            // Crear mapa de placas a nuevo índice
            Map<String, Integer> mapaPlacasIndice = new HashMap<>();
            int nuevoIndice = 0;
            
            // Primero mapear las placas de camiones activos a sus nuevos índices
            for (Camiones camion : todosLosCamiones) {
                if (camion.isActivo()) {
                    mapaPlacasIndice.put(camion.getPlacas(), nuevoIndice);
                    nuevoIndice++;
                }
            }
            
            // Cargar y actualizar el calendario
            GestionCalendario gestionCalendario = new GestionCalendario();
            Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
            boolean seRealizaronCambios = false;
            
            // Crear mapa de índice viejo a nuevo
            Map<Integer, Integer> mapaIndices = new HashMap<>();
            for (int i = 0; i < todosLosCamiones.size(); i++) {
                Camiones camion = todosLosCamiones.get(i);
                Integer nuevoIndiceCamion = mapaPlacasIndice.get(camion.getPlacas());
                if (nuevoIndiceCamion != null) {
                    mapaIndices.put(i, nuevoIndiceCamion);
                }
            }
            
            // Actualizar los índices en las fechas
            for (FechaCalendario fecha : fechas) {
                if (fecha.getActivo()) {
                    Integer nuevoIndiceCamion = mapaIndices.get(fecha.getIndiceCamion());
                    if (nuevoIndiceCamion != null) {
                        fecha.setIndiceCamion(nuevoIndiceCamion);
                        seRealizaronCambios = true;
                    }
                }
            }
            
            // Guardar los cambios si se realizaron modificaciones
            if (seRealizaronCambios) {
                gestionCalendario.setFechasDeCalendario(fechas);
                gestionCalendario.guardarFecharExcel();
            }
            
        } catch (Exception e) {
            System.err.println("Error al actualizar índices en calendario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isUpdatingCalendar = false;
        }
    }

public void cargarCamionesDesdeExcel() {
    camiones.clear();
    Vector<Camiones> todosLosCamiones = new Vector<>();
    
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);
        int indiceActual = 0; // Índice para camiones activos

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Saltar la fila de encabezados
                continue;
            }

            try {
                // Leer todos los campos del Excel
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

                // Verificar si existe la columna de activo
                if (row.getCell(18) != null) {
                    activo = row.getCell(18).getBooleanCellValue();
                }

                // Crear el objeto Camiones
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

                // Agregar a la lista correspondiente
                if (activo) {
                    camiones.add(camion); // Agregar a la lista de activos
                    indiceActual++; // Incrementar el índice solo para camiones activos
                }
                todosLosCamiones.add(camion); // Agregar a la lista completa
            } catch (Exception e) {
                System.err.println("Error al procesar la fila " + row.getRowNum() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Actualizar el calendario con los nuevos índices
        actualizarIndicesEnCalendario(todosLosCamiones);

    } catch (IOException e) {
        System.err.println("Error al leer el archivo Excel: " + e.getMessage());
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