    package GestionDePilotos;

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
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
    import java.util.Set;

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
        boolean pilotoEncontrado = false;
        int indicePiloto = -1;
        
        Set<String> estadosValidos = new HashSet<>(Arrays.asList(
            "ACTIVO",
            "ENFERMO",
            "EN VACACIONES",
            "BLOQUEADO",
            "JUBILADO"
        ));
        
        // Buscar el piloto y su índice actual
        for (int i = 0; i < pilotos.size(); i++) {
            Piloto piloto = pilotos.get(i);
            if (piloto.getNumeroDeDpi() == dpi && 
                estadosValidos.contains(piloto.getEstadoPiloto())) {
                piloto.setActivo(false);
                piloto.setEstadoPiloto("INACTIVO");
                pilotoEncontrado = true;
                indicePiloto = i;
                break;
            }
        }
        
        if (!pilotoEncontrado) {
            throw new IllegalStateException("No se encontró un piloto con estado válido con el DPI: " + dpi);
        }
        
        // Actualizar el archivo Excel
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            boolean excelActualizado = false;
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar encabezado
                
                Cell dpiCell = row.getCell(2);
                if (dpiCell != null) {
                    long dpiFila = (long) dpiCell.getNumericCellValue();
                    if (dpiFila == dpi) {
                        // Actualizar estado a INACTIVO
                        Cell estadoCell = row.getCell(8);
                        if (estadoCell == null) {
                            estadoCell = row.createCell(8);
                        }
                        estadoCell.setCellValue("INACTIVO");
                        
                        // Actualizar columna activo a false
                        Cell activoCell = row.getCell(9);
                        if (activoCell == null) {
                            activoCell = row.createCell(9);
                        }
                        activoCell.setCellValue(false);
                        
                        excelActualizado = true;
                        break;
                    }
                }
            }
            
            if (excelActualizado) {
                try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                    workbook.write(fos);
                }
                
                // Actualizar calendario para el piloto eliminado
                actualizarCalendarioPorPiloto(indicePiloto);
                
                // Recargar pilotos para actualizar índices
                cargarPilotosDesdeExcel();
            } else {
                throw new IllegalStateException("No se pudo actualizar el piloto en el archivo Excel");
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo Excel: " + e.getMessage(), e);
        }
        
    } catch (Exception e) {
        System.err.println("Error al eliminar el piloto: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al eliminar el piloto: " + e.getMessage(), e);
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



        private void actualizarCalendarioPorPiloto(int indicePilotoAEliminar) {
            try {
                System.out.println("Iniciando actualización del calendario para piloto índice: " + indicePilotoAEliminar);

                GestionCalendario gestionCalendario = new GestionCalendario();
                gestionCalendario.cargarFechasExcel();
                Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();

                System.out.println("Total de fechas en calendario: " + fechas.size());

                int cambiosRealizados = 0;

                for (int i = 0; i < fechas.size(); i++) {
                    FechaCalendario fecha = fechas.get(i);
                    if (fecha.getIndicePiloto() == indicePilotoAEliminar) {
                        System.out.println("Encontrada fecha con piloto a eliminar: Índice " + i + 
                                         ", Fecha C: " + fecha.getFechaC() +
                                         ", Estado actual activo: " + fecha.getActivo());

                        fecha.setActivo(false);
                        fechas.set(i, fecha);
                        cambiosRealizados++;
                    }
                }

                System.out.println("Cambios realizados: " + cambiosRealizados);

                if (cambiosRealizados > 0) {
                    gestionCalendario.setFechasDeCalendario(fechas);
                    gestionCalendario.guardarFecharExcel();
                    System.out.println("Cambios guardados en el archivo Excel");
                }

            } catch (Exception e) {
                System.err.println("Error al actualizar el calendario: " + e.getMessage());
                e.printStackTrace();
            }
        }
        

    
        private void actualizarIndicesEnCalendario(Vector<Piloto> todosPilotos) {
            if (isUpdatingCalendar) {
                return;
            }

            try {
                isUpdatingCalendar = true;

                Map<Long, Integer> mapaDPIIndice = new HashMap<>();
                int nuevoIndice = 0;

                for (Piloto piloto : todosPilotos) {
                    if (piloto.isActivo()) {
                        mapaDPIIndice.put(piloto.getNumeroDeDpi(), nuevoIndice);
                        nuevoIndice++;
                    }
                }

                GestionCalendario gestionCalendario = new GestionCalendario();
                Vector<FechaCalendario> fechas = gestionCalendario.getFechasDeCalendario();
                boolean seRealizaronCambios = false;

                Map<Integer, Integer> mapaIndices = new HashMap<>();
                for (int i = 0; i < todosPilotos.size(); i++) {
                    Piloto piloto = todosPilotos.get(i);
                    Integer nuevoIndicePiloto = mapaDPIIndice.get(piloto.getNumeroDeDpi());
                    if (nuevoIndicePiloto != null) {
                        mapaIndices.put(i, nuevoIndicePiloto);
                    }
                }

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
                System.err.println("Error al actualizar índices en calendario: " + e.getMessage());
                e.printStackTrace();
            } finally {
                isUpdatingCalendar = false;
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
                
                Piloto piloto = new Piloto(
                    nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia,
                    correoElectronicoPiloto, numeroTelefonicoPiloto, generoPiloto,
                    fechaDeNacimiento, estadoPiloto, activo
                );

                pilotos.add(piloto);
                
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