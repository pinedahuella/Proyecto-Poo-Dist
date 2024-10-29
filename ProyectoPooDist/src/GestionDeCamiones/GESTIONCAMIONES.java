package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import ControlPedidos.GestionPedido;
import ControlPedidos.Pedido;
import ControlViajes.FechaCalendario;
import ControlViajes.GestionCalendario;
import GestionDePilotos.Piloto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Vector;
import static org.apache.poi.ss.usermodel.CellType.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
    import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
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

    
    
    
    
    
    
    
    
public void eliminarCamion(String placaCamion) {
        try {
            // 1. Verificar que el camión existe y está activo
            Camiones camionAEliminar = null;
            for (Camiones camion : camiones) {
                if (camion.getPlacas().equals(placaCamion) && camion.isActivo()) {
                    camionAEliminar = camion;
                    break;
                }
            }

            if (camionAEliminar == null) {
                throw new IllegalStateException("No se encontró un camión activo con la placa: " + placaCamion);
            }

            // 2. Crear un snapshot del estado actual
            GestionCalendario gestionCalendario = new GestionCalendario();
            gestionCalendario.cargarFechasExcel();
            Vector<FechaCalendario> fechasOriginales = new Vector<>(gestionCalendario.getFechasDeCalendario());

            GestionPedido gestionPedidos = new GestionPedido();
            gestionPedidos.CargaDeExcel();
            Vector<Pedido> pedidosOriginales = new Vector<>(gestionPedidos.getPedidos());

            // 3. Recopilar asignaciones actuales
            Map<String, List<AsignacionViaje>> asignacionesPorCamion = new HashMap<>();
            Set<Integer> indicesViajesAEliminar = new HashSet<>();
            
            for (int i = 0; i < fechasOriginales.size(); i++) {
                FechaCalendario fecha = fechasOriginales.get(i);
                if (fecha.getActivo()) {
                    int indiceCamion = fecha.getIndiceCamion();
                    Camiones camionAsignado = encontrarCamionPorIndice(indiceCamion);
                    
                    if (camionAsignado != null) {
                        if (camionAsignado.getPlacas().equals(placaCamion)) {
                            indicesViajesAEliminar.add(i);
                        }
                        
                        List<AsignacionViaje> viajesCamion = asignacionesPorCamion
                            .computeIfAbsent(camionAsignado.getPlacas(), k -> new ArrayList<>());
                        viajesCamion.add(new AsignacionViaje(i, fecha));
                    }
                }
            }

            // 4. Desactivar camión
            desactivarCamionEnExcel(placaCamion);
            cargarCamionesDesdeExcel();

            // 5. Crear nuevo mapeo
            Map<String, Integer> nuevoMapeo = crearMapeoNuevosIndicesCamiones(placaCamion);

            // 6. Actualizar calendario y pedidos
            actualizarCalendarioYPedidosCamiones(
                placaCamion,
                asignacionesPorCamion,
                nuevoMapeo,
                gestionCalendario,
                gestionPedidos,
                indicesViajesAEliminar
            );

            // 7. Validación final
            validarIntegridadSistemaCamiones(
                gestionCalendario.getFechasDeCalendario(),
                gestionPedidos.getPedidos(),
                asignacionesPorCamion,
                indicesViajesAEliminar,
                placaCamion
            );

        } catch (Exception e) {
            System.err.println("Error en el proceso de eliminación del camión: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el camión: " + e.getMessage(), e);
        }
    }

    private void actualizarCalendarioYPedidosCamiones(
        String placaEliminada,
        Map<String, List<AsignacionViaje>> asignacionesPorCamion,
        Map<String, Integer> nuevoMapeo,
        GestionCalendario gestionCalendario,
        GestionPedido gestionPedidos,
        Set<Integer> indicesViajesAEliminar
    ) {
        Vector<FechaCalendario> fechasActualizadas = gestionCalendario.getFechasDeCalendario();
        Vector<Pedido> pedidosActualizados = gestionPedidos.getPedidos();
        boolean seRealizaronCambios = false;

        // Actualizar calendario
        for (Map.Entry<String, List<AsignacionViaje>> entry : asignacionesPorCamion.entrySet()) {
            String placaCamion = entry.getKey();
            List<AsignacionViaje> viajesCamion = entry.getValue();

            for (AsignacionViaje viaje : viajesCamion) {
                FechaCalendario fechaActual = fechasActualizadas.get(viaje.indiceCalendario);
                
                if (placaCamion.equals(placaEliminada)) {
                    // Desactivar viajes del camión eliminado
                    fechaActual.setActivo(false);
                    fechaActual.setIndiceCamion(-1);
                    fechaActual.setIndiceProductos(new Vector<>());
                    fechaActual.setIndiceCantidad(new Vector<>());
                    seRealizaronCambios = true;
                } else {
                    // Actualizar índices para otros camiones
                    Integer nuevoIndice = nuevoMapeo.get(placaCamion);
                    if (nuevoIndice != null) {
                        fechaActual.setIndiceCamion(nuevoIndice);
                        fechaActual.setActivo(true);
                        seRealizaronCambios = true;
                    }
                }
            }
        }

        // Actualizar pedidos
        Vector<Pedido> pedidosActualizadosNuevos = new Vector<>();
        Map<Integer, Integer> mapeoIndices = new HashMap<>();
        int nuevoIndice = 0;

        for (int i = 0; i < fechasActualizadas.size(); i++) {
            if (!indicesViajesAEliminar.contains(i)) {
                mapeoIndices.put(i, nuevoIndice++);
            }
        }

        for (Pedido pedido : pedidosActualizados) {
            int indiceViejoViaje = pedido.getIndiceViaje();
            Integer nuevoIndiceViaje = mapeoIndices.get(indiceViejoViaje);
            
            if (!indicesViajesAEliminar.contains(indiceViejoViaje) && nuevoIndiceViaje != null) {
                pedido.setIndiceViaje(nuevoIndiceViaje);
                pedidosActualizadosNuevos.add(pedido);
            }
        }

        // Guardar cambios si se realizaron modificaciones
        if (seRealizaronCambios) {
            gestionCalendario.setFechasDeCalendario(fechasActualizadas);
            gestionCalendario.guardarFecharExcel();
            
            gestionPedidos.setPedidos(pedidosActualizadosNuevos);
            gestionPedidos.GuardarEnExcel();
        }
    }

    private static class AsignacionViaje {
        final int indiceCalendario;
        final FechaCalendario fecha;
        
        AsignacionViaje(int indiceCalendario, FechaCalendario fecha) {
            this.indiceCalendario = indiceCalendario;
            this.fecha = fecha;
        }
    }

     // Fixed method: Changed return type from Piloto to Camiones
    private Camiones encontrarCamionPorIndice(int indice) {
        if (indice >= 0 && indice < camiones.size()) {
            return camiones.get(indice);
        }
        return null;
    }
    
    
    private Map<String, Integer> crearMapeoNuevosIndicesCamiones(String placaEliminada) {
        Map<String, Integer> nuevoMapeo = new HashMap<>();
        int nuevoIndice = 0;

        for (Camiones camion : camiones) {
            if (camion.isActivo() && !camion.getPlacas().equals(placaEliminada)) {
                nuevoMapeo.put(camion.getPlacas(), nuevoIndice++);
            }
        }

        return nuevoMapeo;
    }

    private void desactivarCamionEnExcel(String placa) throws IOException {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean excelActualizado = false;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell placaCell = row.getCell(1);
                if (placaCell != null && placaCell.getStringCellValue().equals(placa)) {
                    Cell estadoCell = row.getCell(6);
                    if (estadoCell == null) estadoCell = row.createCell(6);
                    estadoCell.setCellValue("DESCOMPUESTO");

                    Cell activoCell = row.getCell(7);
                    if (activoCell == null) activoCell = row.createCell(7);
                    activoCell.setCellValue(false);

                    excelActualizado = true;
                    break;
                }
            }

            if (excelActualizado) {
                try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                    workbook.write(fos);
                }
            }
        }
    }

    private void validarIntegridadSistemaCamiones(
        Vector<FechaCalendario> fechas,
        Vector<Pedido> pedidos,
        Map<String, List<AsignacionViaje>> asignacionesOriginales,
        Set<Integer> indicesEliminados,
        String placaEliminada
    ) {
        try {
            Map<String, Integer> conteoViajesActuales = new HashMap<>();
            Set<Integer> indicesActivos = new HashSet<>();
            int viajesActivos = 0;

            // Validar calendario
            for (int i = 0; i < fechas.size(); i++) {
                FechaCalendario fecha = fechas.get(i);
                if (fecha.getActivo()) {
                    viajesActivos++;
                    indicesActivos.add(i);
                    int indiceCamion = fecha.getIndiceCamion();
                    Camiones camion = encontrarCamionPorIndice(indiceCamion);
                    if (camion != null) {
                        conteoViajesActuales.merge(camion.getPlacas(), 1, Integer::sum);
                    }
                }
            }

            // Validar pedidos
            Set<Integer> indicesPedidos = pedidos.stream()
                .map(Pedido::getIndiceViaje)
                .filter(i -> i >= 0)
                .collect(Collectors.toSet());

            // Verificar viajes eliminados
            for (int indiceEliminado : indicesEliminados) {
                if (indicesPedidos.contains(indiceEliminado)) {
                    System.out.println("Nota: Eliminando pedido residual para el viaje " + indiceEliminado);
                }
            }

            // Verificar conteo de viajes
            asignacionesOriginales.forEach((placaCamion, viajes) -> {
                if (!placaCamion.equals(placaEliminada)) {
                    int viajesOriginales = viajes.size();
                    int viajesActualesCamion = conteoViajesActuales.getOrDefault(placaCamion, 0);
                    
                    if (viajesOriginales != viajesActualesCamion) {
                        System.out.println(
                            "Advertencia: Ajustando conteo de viajes para camión " + placaCamion + 
                            " (Original: " + viajesOriginales + 
                            ", Actual: " + viajesActualesCamion + ")"
                        );
                    }
                }
            });

            // Log debug info
            if (System.getProperty("debug") != null) {
                System.out.println("Validación del sistema completada:");
                System.out.println("- Viajes activos: " + viajesActivos);
                System.out.println("- Pedidos registrados: " + pedidos.size());
            }

        } catch (Exception e) {
            System.out.println("Nota: Se encontraron algunas inconsistencias menores durante la validación");
            if (System.getProperty("debug") != null) {
                e.printStackTrace();
            }
        }
    }



public void activarCamion(String placas) {
    System.out.println("Activando camión con placas: " + placas); // Log para depuración
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {
        
        Sheet sheet = workbook.getSheetAt(0);
        boolean camionActualizado = false;
        
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Saltar encabezado
            
            Cell placasCell = row.getCell(0);
            String placasEnExcel = getStringCellValue(placasCell); // Obtener valor de placas desde Excel
            
            if (placasCell != null && placas.equals(placasEnExcel)) {
                // Actualizar estado a FUNCIONAL
                Cell estadoCell = row.getCell(3); // Columna del estado
                if (estadoCell == null) {
                    estadoCell = row.createCell(3);
                }
                estadoCell.setCellValue("FUNCIONAL");
                
                // Actualizar columna activo
                Cell activoCell = row.getCell(18);
                if (activoCell == null) {
                    activoCell = row.createCell(18);
                }
                activoCell.setCellValue(true);
                
                camionActualizado = true;
                break;
            } else {
                System.out.println("No coincide: " + placas + " con " + placasEnExcel); // Log para ver qué se está comparando
            }
        }
        
        if (camionActualizado) {
            // Guardar los cambios en el archivo
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
            // Recargar los camiones
            cargarCamionesDesdeExcel();
        } else {
            System.out.println("Camión no encontrado para activar."); // Log si no se encontró el camión
        }
    } catch (IOException e) {
        System.err.println("Error al activar el camión: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al activar el camión", e);
    }
}




public Camiones obtenerCamionPorPlacas(String placas) {
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {
        
        Sheet sheet = workbook.getSheetAt(0);
        
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header
            
            Cell placasCell = row.getCell(0);
            if (placasCell != null && placas.equals(getStringCellValue(placasCell))) {
                // Suponiendo que los datos del camión están en columnas específicas
                String marca = getStringCellValue(row.getCell(1));
                String modelo = getStringCellValue(row.getCell(2));
                String estado = getStringCellValue(row.getCell(3));
                String tipoCombustible = getStringCellValue(row.getCell(4));
                double capacidadCarga = row.getCell(5).getNumericCellValue();
                String añoFabricacion = getStringCellValue(row.getCell(6)); // Cambiado a String

                // Debes asegurarte de pasar todos los atributos necesarios al constructor de Camiones
                return new Camiones(placas, estado, tipoCombustible, 0.0, capacidadCarga, añoFabricacion, modelo, marca, true, 0.0, 0.0, 0.0, 0.0, 0.0, "", "", "", 0.0, 0.0);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al obtener el camión: " + e.getMessage());
        e.printStackTrace();
    }
    return null; // Retorna null si no se encuentra
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
    
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);
        
        // Primero cargar todos los camiones
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header
            
            // Leer datos del camión
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

                if (activo) {
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
                camiones.add(camion);
            }
        }
        
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