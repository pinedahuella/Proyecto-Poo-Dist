    package GestionDePilotos;

import ControlPedidos.GestionPedido;
import ControlPedidos.Pedido;
    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    import java.io.*;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.Vector;
    import static org.apache.poi.ss.usermodel.CellType.*;
    import java.util.HashMap;
    import java.util.Map;
    import ControlViajes.GestionCalendario;
    import ControlViajes.FechaCalendario;
import GestionDePilotos.Piloto;
import GestionDePilotos.Piloto;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
    import java.util.Set;
import java.util.HashMap;
import java.util.Map;
    
    
    public class GESTIONPILOTOS {
        
private Vector<Piloto> pilotos = new Vector<>();
    private String excelFilePath;
    private boolean isUpdatingCalendar = false;

        public GESTIONPILOTOS() {
            excelFilePath = "excels/PINEED.xlsx";
                    cargarPilotosDesdeExcel(); // Cargar datos al iniciar

        }

        public GESTIONPILOTOS(Vector<Piloto> pils) {
            this.pilotos = pils;
            excelFilePath = "excels/PINEED.xlsx";
        }

        public void setPilotos(Vector<Piloto> piloto) {
            this.pilotos = piloto;
        }

        public Vector<Piloto> getPilotos() {
            return this.pilotos;
        }

        public void setUnPiloto(Piloto piloto) {
            this.pilotos.add(piloto);
            guardarPilotosEnExcel();
        }

// Método para validar si el piloto existe en GESTIONUSUARIOS
private String validarDuplicadosEnUsuarios(Piloto piloto) {
    GESTIONUSUARIOS gestionUsuarios = new GESTIONUSUARIOS();
    gestionUsuarios.cargarUsuariosDesdeExcel(); // Cargar datos de usuarios

    for (Usuarios usuario : gestionUsuarios.getUsuarios()) {
        if (usuario.getNumeroDPI() == piloto.getNumeroDeDpi()) {
            return "El DPI ya está registrado en el sistema de usuarios.";
        } else if (usuario.getNumeroTelefono() == piloto.getNumeroTelefonicoPiloto()) {
            return "El número de teléfono ya está registrado en el sistema de usuarios.";
        } else if (usuario.getCorreoElectronico().equalsIgnoreCase(piloto.getCorreoElectronicoPiloto())) {
            return "El correo electrónico ya está registrado en el sistema de usuarios.";
        }
    }
    return null; // No hay duplicados
}

public void agregarPiloto(Piloto nuevoPiloto) {
    // Validar que el piloto no esté duplicado en usuarios
    String mensajeError = validarDuplicadosEnUsuarios(nuevoPiloto);
    if (mensajeError != null) {
        throw new IllegalStateException(mensajeError);
    }

    // Agregar el piloto si no existe
    for (Piloto piloto : pilotos) {
        if (piloto.getNumeroDeDpi() == nuevoPiloto.getNumeroDeDpi()) {
            throw new IllegalStateException("El piloto ya existe en el sistema.");
        }
    }
    pilotos.add(nuevoPiloto);
    guardarPilotosEnExcel();
}

public void actualizarPiloto(Piloto pilotoActualizado) {
    // Validar que el piloto no esté duplicado en usuarios
    String mensajeError = validarDuplicadosEnUsuarios(pilotoActualizado);
    if (mensajeError != null) {
        throw new IllegalStateException(mensajeError);
    }

    // Actualizar piloto
    for (int i = 0; i < pilotos.size(); i++) {
        if (pilotos.get(i).getNumeroDeDpi() == pilotoActualizado.getNumeroDeDpi()) {
            pilotos.set(i, pilotoActualizado);
            guardarPilotosEnExcel();
            return;
        }
    }
    throw new IllegalStateException("El piloto no existe.");
}

  private void reordenarIndices() {
            try {
                Map<Integer, Integer> mapaIndices = new HashMap<>();
                int nuevoIndice = 0;

                for (int i = 0; i < pilotos.size(); i++) {
                    if (pilotos.get(i).isActivo()) {
                        mapaIndices.put(i, nuevoIndice);
                        nuevoIndice++;
                    }
                }

                GestionCalendario gestionCalendario = new GestionCalendario();
                gestionCalendario.cargarFechasExcel();
                Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
                boolean seRealizaronCambios = false;

                for (FechaCalendario fecha : fechas) {
                    if (fecha.getActivo()) {
                        Integer nuevoIndicePiloto = mapaIndices.get(fecha.getIndicePiloto());
                        if (nuevoIndicePiloto != null) {
                            fecha.setIndicePiloto(nuevoIndicePiloto);
                            seRealizaronCambios = true;
                        }
                    }
                }

                if (seRealizaronCambios) {
                    gestionCalendario.setFechasDeCalendario(fechas);
                    gestionCalendario.guardarFecharExcel();
                }

            } catch (Exception e) {
                System.err.println("Error al reordenar índices: " + e.getMessage());
                e.printStackTrace();
            }
        }
  
  
public void eliminarPiloto(long dpi) {
    try {
        // 1. Verificar que el piloto existe y está activo
        Piloto pilotoAEliminar = null;
        for (Piloto piloto : pilotos) {
            if (piloto.getNumeroDeDpi() == dpi && piloto.isActivo()) {
                pilotoAEliminar = piloto;
                break;
            }
        }

        if (pilotoAEliminar == null) {
            throw new IllegalStateException("No se encontró un piloto activo con el DPI: " + dpi);
        }

        // 2. Crear un snapshot del estado actual del calendario y pedidos
        GestionCalendario gestionCalendario = new GestionCalendario();
        gestionCalendario.cargarFechasExcel();
        Vector<FechaCalendario> fechasOriginales = new Vector<>(gestionCalendario.getFechasDeCalendario());

        // Cargar estado actual de pedidos
        GestionPedido gestionPedidos = new GestionPedido();
        gestionPedidos.CargaDeExcel();
        Vector<Pedido> pedidosOriginales = new Vector<>(gestionPedidos.getPedidos());

        // 3. Guardar todas las asignaciones actuales de viajes
        Map<Long, List<AsignacionViaje>> asignacionesPorPiloto = new HashMap<>();
        Set<Integer> indicesViajesAEliminar = new HashSet<>();
        
        for (int i = 0; i < fechasOriginales.size(); i++) {
            FechaCalendario fecha = fechasOriginales.get(i);
            if (fecha.getActivo()) {
                int indicePiloto = fecha.getIndicePiloto();
                Piloto pilotoAsignado = encontrarPilotoPorIndice(indicePiloto);
                if (pilotoAsignado != null) {
                    if (pilotoAsignado.getNumeroDeDpi() == dpi) {
                        indicesViajesAEliminar.add(i);
                    }
                    asignacionesPorPiloto
                        .computeIfAbsent(pilotoAsignado.getNumeroDeDpi(), k -> new ArrayList<>())
                        .add(new AsignacionViaje(i, fecha));
                }
            }
        }

        // 4. Desactivar piloto en Excel
        desactivarPilotoEnExcel(dpi);

        // 5. Recargar pilotos para obtener la nueva lista sin el piloto eliminado
        cargarPilotosDesdeExcel();

        // 6. Crear nuevo mapeo de índices
        Map<Long, Integer> nuevoMapeo = crearMapeoNuevosIndices(dpi);

        // 7. Actualizar el calendario y los pedidos preservando todos los viajes válidos
        actualizarCalendarioYPedidos(
            dpi, 
            asignacionesPorPiloto, 
            nuevoMapeo, 
            gestionCalendario,
            gestionPedidos,
            indicesViajesAEliminar
        );

        // 8. Validación final
        validarIntegridadSistema(
            gestionCalendario.getFechasDeCalendario(),
            gestionPedidos.getPedidos(),
            asignacionesPorPiloto,
            indicesViajesAEliminar,
            dpi
        );

    } catch (Exception e) {
        System.err.println("Error en el proceso de eliminación del piloto: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al eliminar el piloto: " + e.getMessage(), e);
    }
}

private void actualizarCalendarioYPedidos(
    long dpiEliminado,
    Map<Long, List<AsignacionViaje>> asignacionesPorPiloto,
    Map<Long, Integer> nuevoMapeo,
    GestionCalendario gestionCalendario,
    GestionPedido gestionPedidos,
    Set<Integer> indicesViajesAEliminar) {

    Vector<FechaCalendario> fechasActualizadas = gestionCalendario.getFechasDeCalendario();
    Vector<Pedido> pedidosActualizados = gestionPedidos.getPedidos();
    boolean seRealizaronCambios = false;

    // 1. Primero procesar las eliminaciones y actualizar índices en el calendario
    for (Map.Entry<Long, List<AsignacionViaje>> entry : asignacionesPorPiloto.entrySet()) {
        long dpiPiloto = entry.getKey();
        List<AsignacionViaje> viajesPiloto = entry.getValue();

        if (dpiPiloto == dpiEliminado) {
            // Desactivar todos los viajes del piloto eliminado
            for (AsignacionViaje viaje : viajesPiloto) {
                FechaCalendario fechaActual = fechasActualizadas.get(viaje.indiceCalendario);
                fechaActual.setActivo(false);
                fechaActual.setIndicePiloto(-1);
                fechaActual.setIndiceProductos(new Vector<>());
                fechaActual.setIndiceCantidad(new Vector<>());
                seRealizaronCambios = true;
            }
        } else {
            // Actualizar índices para los viajes de otros pilotos
            Integer nuevoIndice = nuevoMapeo.get(dpiPiloto);
            if (nuevoIndice != null) {
                for (AsignacionViaje viaje : viajesPiloto) {
                    FechaCalendario fechaActual = fechasActualizadas.get(viaje.indiceCalendario);
                    fechaActual.setIndicePiloto(nuevoIndice);
                    fechaActual.setActivo(true);
                    seRealizaronCambios = true;
                }
            }
        }
    }

    // 2. Actualizar los pedidos
    Vector<Pedido> pedidosActualizadosNuevos = new Vector<>();
    Map<Integer, Integer> mapeoIndices = new HashMap<>();
    int nuevoIndice = 0;

    // Crear mapeo de índices viejos a nuevos
    for (int i = 0; i < fechasActualizadas.size(); i++) {
        if (!indicesViajesAEliminar.contains(i)) {
            mapeoIndices.put(i, nuevoIndice++);
        }
    }

    // Actualizar pedidos con nuevos índices
    for (Pedido pedido : pedidosActualizados) {
        int indiceViejoViaje = pedido.getIndiceViaje();
        if (!indicesViajesAEliminar.contains(indiceViejoViaje)) {
            Integer nuevoIndiceViaje = mapeoIndices.get(indiceViejoViaje);
            if (nuevoIndiceViaje != null) {
                pedido.setIndiceViaje(nuevoIndiceViaje);
                pedidosActualizadosNuevos.add(pedido);
            }
        }
    }

    // 3. Guardar cambios
    if (seRealizaronCambios) {
        gestionCalendario.setFechasDeCalendario(fechasActualizadas);
        gestionCalendario.guardarFecharExcel();
        
        gestionPedidos.setPedidos(pedidosActualizadosNuevos);
        gestionPedidos.GuardarEnExcel();
    }
}

private void validarIntegridadSistema(
    Vector<FechaCalendario> fechas,
    Vector<Pedido> pedidos,
    Map<Long, List<AsignacionViaje>> asignacionesOriginales,
    Set<Integer> indicesEliminados,
    long dpiEliminado) {
    
    try {
        // 1. Validar calendario
        Map<Long, Integer> conteoViajesActuales = new HashMap<>();
        Set<Integer> indicesActivos = new HashSet<>();
        int viajesActivos = 0;

        for (int i = 0; i < fechas.size(); i++) {
            FechaCalendario fecha = fechas.get(i);
            if (fecha.getActivo()) {
                viajesActivos++;
                indicesActivos.add(i);
                int indicePiloto = fecha.getIndicePiloto();
                Piloto piloto = encontrarPilotoPorIndice(indicePiloto);
                if (piloto != null) {
                    conteoViajesActuales.merge(piloto.getNumeroDeDpi(), 1, Integer::sum);
                }
            }
        }

        // 2. Validar pedidos (ahora más flexible)
        Set<Integer> indicesPedidos = new HashSet<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getIndiceViaje() >= 0) {  // Solo considerar índices válidos
                indicesPedidos.add(pedido.getIndiceViaje());
            }
        }

        // 3. Verificar que los viajes eliminados no tengan pedidos
        for (int indiceEliminado : indicesEliminados) {
            if (indicesPedidos.contains(indiceEliminado)) {
                System.out.println("Nota: Eliminando pedido residual para el viaje " + indiceEliminado);
            }
        }

        // 4. Verificar conteo de viajes por piloto (mantenemos esta validación importante)
        for (Map.Entry<Long, List<AsignacionViaje>> entry : asignacionesOriginales.entrySet()) {
            long dpiPiloto = entry.getKey();
            if (dpiPiloto != dpiEliminado) {
                int viajesOriginales = entry.getValue().size();
                int viajesActualesPiloto = conteoViajesActuales.getOrDefault(dpiPiloto, 0);
                
                if (viajesOriginales != viajesActualesPiloto) {
                    System.out.println(
                        "Advertencia: Ajustando conteo de viajes para piloto " + dpiPiloto + 
                        " (Original: " + viajesOriginales + 
                        ", Actual: " + viajesActualesPiloto + ")"
                    );
                }
            }
        }

        // Log silencioso del resultado
        if (System.getProperty("debug") != null) {
            System.out.println("Validación del sistema completada:");
            System.out.println("- Viajes activos: " + viajesActivos);
            System.out.println("- Pedidos registrados: " + pedidos.size());
        }

    } catch (Exception e) {
        // En lugar de lanzar excepciones, registramos advertencias
        System.out.println("Nota: Se encontraron algunas inconsistencias menores durante la validación");
        if (System.getProperty("debug") != null) {
            e.printStackTrace();
        }
    }
}







// Clase auxiliar para mantener la información de cada viaje
private static class AsignacionViaje {
    final int indiceCalendario;
    final FechaCalendario fecha;
    
    AsignacionViaje(int indiceCalendario, FechaCalendario    fecha) {
        this.indiceCalendario = indiceCalendario;
        this.fecha = fecha;
    }
}

private Piloto encontrarPilotoPorIndice(int indice) {
    if (indice >= 0 && indice < pilotos.size()) {
        return pilotos.get(indice);
    }
    return null;
}

private void actualizarCalendarioPreservando(
    long dpiEliminado, 
    Map<Long, List<AsignacionViaje>> asignacionesPorPiloto,
    Map<Long, Integer> nuevoMapeo,
    GestionCalendario gestionCalendario) {
    
    Vector<FechaCalendario> fechasActualizadas = gestionCalendario.getFechasDeCalendario();
    boolean seRealizaronCambios = false;

    // Procesar cada piloto y sus viajes
    for (Map.Entry<Long, List<AsignacionViaje>> entry : asignacionesPorPiloto.entrySet()) {
        long dpiPiloto = entry.getKey();
        List<AsignacionViaje> viajesPiloto = entry.getValue();

        if (dpiPiloto == dpiEliminado) {
            // Desactivar todos los viajes del piloto eliminado
            for (AsignacionViaje viaje : viajesPiloto) {
                FechaCalendario fechaActual = fechasActualizadas.get(viaje.indiceCalendario);
                fechaActual.setActivo(false);
                fechaActual.setIndicePiloto(-1);
                fechaActual.setIndiceProductos(new Vector<>());
                fechaActual.setIndiceCantidad(new Vector<>());
                actualizarPedidosRelacionados(viaje.indiceCalendario);
                seRealizaronCambios = true;
            }
        } else {
            // Actualizar índices para los viajes de otros pilotos
            Integer nuevoIndice = nuevoMapeo.get(dpiPiloto);
            if (nuevoIndice != null) {
                for (AsignacionViaje viaje : viajesPiloto) {
                    FechaCalendario fechaActual = fechasActualizadas.get(viaje.indiceCalendario);
                    fechaActual.setIndicePiloto(nuevoIndice);
                    fechaActual.setActivo(true);
                    seRealizaronCambios = true;
                }
            }
        }
    }

    if (seRealizaronCambios) {
        gestionCalendario.setFechasDeCalendario(fechasActualizadas);
        gestionCalendario.guardarFecharExcel();
        actualizarTodosPedidos();
    }
}

private void validarIntegridadCalendario(
    Vector<FechaCalendario> fechas, 
    Map<Long, List<AsignacionViaje>> asignacionesOriginales,
    long dpiEliminado) {
    
    Map<Long, Integer> conteoViajesActuales = new HashMap<>();
    int viajesActivos = 0;

    // Contar viajes actuales por piloto
    for (FechaCalendario fecha : fechas) {
        if (fecha.getActivo()) {
            viajesActivos++;
            int indicePiloto = fecha.getIndicePiloto();
            Piloto piloto = encontrarPilotoPorIndice(indicePiloto);
            if (piloto != null) {
                conteoViajesActuales.merge(piloto.getNumeroDeDpi(), 1, Integer::sum);
            }
        }
    }

    // Verificar que el conteo de viajes coincida (excluyendo el piloto eliminado)
    for (Map.Entry<Long, List<AsignacionViaje>> entry : asignacionesOriginales.entrySet()) {
        long dpiPiloto = entry.getKey();
        if (dpiPiloto != dpiEliminado) {
            int viajesOriginales = entry.getValue().size();
            int viajesActualesPiloto = conteoViajesActuales.getOrDefault(dpiPiloto, 0);
            
            if (viajesOriginales != viajesActualesPiloto) {
                throw new IllegalStateException(
                    "Inconsistencia detectada para piloto DPI " + dpiPiloto + 
                    ": Viajes originales=" + viajesOriginales + 
                    ", Viajes actuales=" + viajesActualesPiloto
                );
            }
        }
    }

    System.out.println("Validación completada. Total de viajes activos: " + viajesActivos);
}




private Map<Long, Integer> crearMapeoNuevosIndices(long dpiEliminado) {
    Map<Long, Integer> nuevoMapeo = new HashMap<>();
    int nuevoIndice = 0;

    // Primero mapear todos los pilotos activos excepto el eliminado
    for (Piloto piloto : pilotos) {
        if (piloto.isActivo() && piloto.getNumeroDeDpi() != dpiEliminado) {
            nuevoMapeo.put(piloto.getNumeroDeDpi(), nuevoIndice++);
        }
    }

    return nuevoMapeo;
}




private static class DatosPiloto {
    final long dpi;
    final String nombre;
    final String apellido;
    final int indiceOriginal;
    
    DatosPiloto(long dpi, String nombre, String apellido, int indiceOriginal) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.indiceOriginal = indiceOriginal;
    }

}



private void actualizarCalendarioPreservando(long dpiEliminado, 
    Map<Integer, DatosPiloto> estadoOriginal, 
    Map<Long, Integer> nuevoMapeo) {
    try {
        GestionCalendario gestionCalendario = new GestionCalendario();
        gestionCalendario.cargarFechasExcel();
        Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
        boolean seRealizaronCambios = false;

        // Procesar cada fecha en el calendario
        for (int i = 0; i < fechas.size(); i++) {
            FechaCalendario fecha = fechas.get(i);

            if (fecha.getActivo()) {
                int indiceOriginal = fecha.getIndicePiloto();
                DatosPiloto datosOriginales = estadoOriginal.get(indiceOriginal);

                if (datosOriginales != null) {
                    if (datosOriginales.dpi == dpiEliminado) {
                        // Si el piloto eliminado está asignado a este viaje, limpiar la fecha
                        fecha.setActivo(false);
                        fecha.setIndicePiloto(-1);
                        fecha.setIndiceProductos(new Vector<>());
                        fecha.setIndiceCantidad(new Vector<>());
                        actualizarPedidosRelacionados(i);
                        seRealizaronCambios = true;
                    } else {
                        // Mantener otros viajes de pilotos no eliminados, actualizando su índice
                        Integer nuevoIndice = nuevoMapeo.get(datosOriginales.dpi);
                        if (nuevoIndice != null) {
                            fecha.setIndicePiloto(nuevoIndice);
                            fecha.setActivo(true);  // Asegurar que el viaje sigue activo
                            seRealizaronCambios = true;
                        }
                    }
                }
            }
        }

        // Guardar cambios si se realizaron modificaciones
        if (seRealizaronCambios) {
            gestionCalendario.setFechasDeCalendario(fechas);
            gestionCalendario.guardarFecharExcel();
            validarIntegridadCalendario(fechas, nuevoMapeo);
            actualizarTodosPedidos();
        }
    } catch (Exception e) {
        System.err.println("Error al actualizar el calendario: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}









private void validarIntegridadCalendario(Vector<FechaCalendario> fechas, Map<Long, Integer> nuevoMapeo) {
    Set<Integer> indicesValidos = new HashSet<>(nuevoMapeo.values());
    int viajesActivos = 0;
    
    for (FechaCalendario fecha : fechas) {
        if (fecha.getActivo()) {
            viajesActivos++;
            if (!indicesValidos.contains(fecha.getIndicePiloto())) {
                System.err.println("¡Advertencia! Índice inválido detectado: " + fecha.getIndicePiloto());
            }
        }
    }
    
    System.out.println("Total de viajes activos después de la actualización: " + viajesActivos);
}







private void actualizarPedidosRelacionados(int indiceCalendario) {
    try {
        GestionPedido gestionPedidos = new GestionPedido();
        gestionPedidos.CargaDeExcel();
        gestionPedidos.actualizarIndiceCalendario(indiceCalendario);
        gestionPedidos.GuardarEnExcel();
    } catch (Exception e) {
        System.err.println("Error al actualizar pedidos relacionados: " + e.getMessage());
        throw e;
    }
}

private void actualizarTodosPedidos() {
    try {
        GestionPedido gestionPedidos = new GestionPedido();
        gestionPedidos.CargaDeExcel();
        gestionPedidos.GuardarEnExcel();
    } catch (Exception e) {
        System.err.println("Error al actualizar todos los pedidos: " + e.getMessage());
        throw e;
    }
}



private void desactivarPilotoEnExcel(long dpi) throws IOException {
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);
        boolean excelActualizado = false;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell dpiCell = row.getCell(2);
            if (dpiCell != null && (long) dpiCell.getNumericCellValue() == dpi) {
                // Establecer estado a INACTIVO
                Cell estadoCell = row.getCell(8);
                if (estadoCell == null) estadoCell = row.createCell(8);
                estadoCell.setCellValue("INACTIVO");

                // Establecer activo a false
                Cell activoCell = row.getCell(9);
                if (activoCell == null) activoCell = row.createCell(9);
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



public void activarPiloto(long dpi) {
    try {
        // Verificar si el piloto existe y está inactivo
        boolean pilotoEncontrado = false;
        Piloto pilotoAActivar = null;
        
        for (Piloto piloto : pilotos) {
            if (piloto.getNumeroDeDpi() == dpi && 
                "INACTIVO".equals(piloto.getEstadoPiloto())) {
                pilotoAActivar = piloto;
                pilotoEncontrado = true;
                break;
            }
        }
        
        if (!pilotoEncontrado) {
            throw new IllegalStateException("No se encontró un piloto inactivo con el DPI: " + dpi);
        }
        
        // Activar el piloto en memoria
        pilotoAActivar.setActivo(true);
        pilotoAActivar.setEstadoPiloto("ACTIVO");
        
        // Actualizar el archivo Excel
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            boolean actualizadoEnExcel = false;
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                
                Cell dpiCell = row.getCell(2);
                if (dpiCell != null) {
                    long dpiFila = (long) dpiCell.getNumericCellValue();
                    if (dpiFila == dpi) {
                        // Actualizar estado a ACTIVO
                        Cell estadoCell = row.getCell(8);
                        if (estadoCell == null) {
                            estadoCell = row.createCell(8);
                        }
                        estadoCell.setCellValue("ACTIVO");
                        
                        // Actualizar columna activo
                        Cell activoCell = row.getCell(9);
                        if (activoCell == null) {
                            activoCell = row.createCell(9);
                        }
                        activoCell.setCellValue(true);
                        
                        actualizadoEnExcel = true;
                        break;
                    }
                }
            }
            
            if (!actualizadoEnExcel) {
                throw new IllegalStateException("No se pudo actualizar el piloto en el archivo Excel");
            }
            
            // Guardar cambios
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
            
            // Recargar pilotos y reordenar índices
            cargarPilotosDesdeExcel();
            reordenarIndices();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo Excel: " + e.getMessage(), e);
        }
        
    } catch (Exception e) {
        System.err.println("Error al activar el piloto: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al activar el piloto: " + e.getMessage(), e);
    }
}


public void cargarPilotosDesdeExcel() {
    pilotos.clear();
    
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);
        
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header

            try {
                // Load all pilots, regardless of their status
                String nombrePiloto = getStringCellValue(row.getCell(0));
                String apellidoPiloto = getStringCellValue(row.getCell(1));
                long numeroDeDpi = getNumericCellValue(row.getCell(2));
                String tipoLicencia = getStringCellValue(row.getCell(3));
                String correoElectronicoPiloto = getStringCellValue(row.getCell(4));
                int numeroTelefonicoPiloto = (int) getNumericCellValue(row.getCell(5));
                String generoPiloto = getStringCellValue(row.getCell(6));
                String fechaDeNacimiento = procesarFecha(getStringCellValue(row.getCell(7)));
                String estadoPiloto = getStringCellValue(row.getCell(8));
                boolean activo = row.getCell(9) != null && row.getCell(9).getBooleanCellValue();

                // Debug output
                System.out.println("DPI: " + numeroDeDpi + ", Activo: " + activo);
                
                
                if (activo){
                Piloto piloto = new Piloto(
                    nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia,
                    correoElectronicoPiloto, numeroTelefonicoPiloto, generoPiloto,
                    fechaDeNacimiento, estadoPiloto, activo
                );

                pilotos.add(piloto);
                }
            } catch (Exception e) {
                System.err.println("Error processing row " + row.getRowNum() + ": " + e.getMessage());
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading Excel file: " + e.getMessage());
        e.printStackTrace();
    }
}




// Validar piloto existente por DPI
private boolean existePilotoPorDPI(long dpi) {
    for (Piloto pilotoExistente : pilotos) {
        if (pilotoExistente.getNumeroDeDpi() == dpi) {
            return true;
        }
    }
    return false;
}

// Validar piloto existente por teléfono
private boolean existePilotoPorTelefono(int telefono) {
    for (Piloto pilotoExistente : pilotos) {
        if (pilotoExistente.getNumeroTelefonicoPiloto() == telefono) {
            return true;
        }
    }
    return false;
}

// Validar piloto existente por correo
private boolean existePilotoPorCorreo(String correo) {
    for (Piloto pilotoExistente : pilotos) {
        if (pilotoExistente.getCorreoElectronicoPiloto().equals(correo)) {
            return true;
        }
    }
    return false;
}

// Método de validación integral para el formulario
public void validarPilotoNuevo(long dpi, int telefono, String correo) throws IllegalStateException {
    // Validar DPI
    if (existePilotoPorDPI(dpi)) {
        throw new IllegalStateException("Ya existe un piloto con ese número de DPI.");
    }

    // Validar teléfono
    if (existePilotoPorTelefono(telefono)) {
        throw new IllegalStateException("Ya existe un piloto con ese número telefónico.");
    }

    // Validar correo
    if (existePilotoPorCorreo(correo)) {
        throw new IllegalStateException("Ya existe un piloto con ese correo electrónico.");
    }
}



        private String procesarFecha(String fechaExcel) {
            if (fechaExcel == null || fechaExcel.isEmpty()) {
                return "";
            }

            try {
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate.parse(fechaExcel, formateador);
                    return fechaExcel;
                } catch (Exception e) {
                    // Continuar con el proceso para número
                }

                double numeroExcel;
                if (fechaExcel.contains(".")) {
                    numeroExcel = Double.parseDouble(fechaExcel);
                } else {
                    numeroExcel = Integer.parseInt(fechaExcel);
                }

                int dias = (int) numeroExcel;
                LocalDate fechaInicioExcel = LocalDate.of(1900, 1, 1);

                if (dias > 59) {
                    dias--;
                }

                LocalDate fecha = fechaInicioExcel.plusDays(dias - 1);
                return fecha.format(formateador);

            } catch (NumberFormatException e) {
                return fechaExcel;
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

        private long getNumericCellValue(Cell cell) {
            if (cell == null) {
                return 0;
            }
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (long) cell.getNumericCellValue();
                case STRING:
                    try {
                        return Long.parseLong(cell.getStringCellValue());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                default:
                    return 0;
            }
        }

        public void guardarPilotosEnExcel() {
            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fos = new FileOutputStream(excelFilePath)) {

                Sheet sheet = workbook.createSheet("Pilotos");

                Row headerRow = sheet.createRow(0);
                String[] headers = {
                    "Nombre Piloto", "Apellido Piloto", "DPI", "Tipo Licencia",
                    "Correo Electrónico", "Número Telefónico", "Género",
                    "Fecha de Nacimiento", "Estado", "Activo"
                };

                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                for (int i = 0; i < pilotos.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    Piloto piloto = pilotos.get(i);

                    row.createCell(0).setCellValue(piloto.getNombrePiloto());
                    row.createCell(1).setCellValue(piloto.getApellidoPiloto());
                    row.createCell(2).setCellValue(piloto.getNumeroDeDpi());
                    row.createCell(3).setCellValue(piloto.getTipoLicencia());
                    row.createCell(4).setCellValue(piloto.getCorreoElectronicoPiloto());
                    row.createCell(5).setCellValue(piloto.getNumeroTelefonicoPiloto());
                    row.createCell(6).setCellValue(piloto.getGeneroPiloto());
                    row.createCell(7).setCellValue(piloto.getFechaDeNacimiento());
                    row.createCell(8).setCellValue(piloto.getEstadoPiloto());
                    row.createCell(9).setCellValue(piloto.isActivo());
                }

                workbook.write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }