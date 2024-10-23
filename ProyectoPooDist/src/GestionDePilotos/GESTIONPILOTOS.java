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

    public class GESTIONPILOTOS {
        private Vector<Piloto> pilotos = new Vector<>();
        private String excelFilePath;
        private boolean isUpdatingCalendar = false;

        public GESTIONPILOTOS() {
            excelFilePath = "excels/PINEED.xlsx";
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

        
        public void agregarCamion(Piloto piloto) {
        this.pilotos.add(piloto);
        guardarPilotosEnExcel();
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

        public void eliminarPiloto(String dpi) {
        try {
            long dpiLong = Long.parseLong(dpi);
            boolean pilotoEncontrado = false;
            int indicePiloto = -1;

            for (int i = 0; i < pilotos.size(); i++) {
                if (pilotos.get(i).getNumeroDeDpi() == dpiLong) {
                    pilotos.get(i).setActivo(false);
                    pilotoEncontrado = true;
                    indicePiloto = i;
                    break;
                }
            }

            if (pilotoEncontrado) {
                actualizarCalendarioPorPiloto(indicePiloto);
                reordenarIndices();
                guardarPilotosEnExcel();
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: DPI inválido - " + e.getMessage());
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
        
// Método para actualizar un piloto existente en el Vector
public void actualizarPiloto(Piloto pilotoActualizado) {
    boolean encontrado = false; // Bandera para verificar si el piloto fue encontrado

    // Recorre el Vector de pilotos
    for (int i = 0; i < pilotos.size(); i++) {
        // Compara el DPI del piloto actual con el actualizado
        if (pilotos.get(i).getNumeroDeDpi() == pilotoActualizado.getNumeroDeDpi()) {
            pilotos.set(i, pilotoActualizado); // Actualiza el piloto en el Vector
            encontrado = true; // Cambia la bandera a verdadero
            break; // Sale del bucle
        }
    }

    // Si el piloto no fue encontrado, lo agrega al Vector
    if (!encontrado) {
        pilotos.add(pilotoActualizado);
    }

    // Guarda los pilotos en Excel
    guardarPilotosEnExcel(); // Asegúrate de que este método esté implementado
    // Carga la lista actualizada de pilotos desde Excel
    cargarPilotosDesdeExcel(); // Asegúrate de que este método esté implementado
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
            Vector<Piloto> todosPilotos = new Vector<>();

            try (FileInputStream fis = new FileInputStream(excelFilePath);
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                int indiceActual = 0;

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }

                    try {
                        String nombrePiloto = getStringCellValue(row.getCell(0));
                        String apellidoPiloto = getStringCellValue(row.getCell(1));
                        long numeroDeDpi = getNumericCellValue(row.getCell(2));
                        String tipoLicencia = getStringCellValue(row.getCell(3));
                        String correoElectronicoPiloto = getStringCellValue(row.getCell(4));
                        int numeroTelefonicoPiloto = (int) getNumericCellValue(row.getCell(5));
                        String generoPiloto = getStringCellValue(row.getCell(6));
                        String fechaDeNacimiento = procesarFecha(getStringCellValue(row.getCell(7)));
                        String estadoPiloto = getStringCellValue(row.getCell(8));
                        boolean activo = true;

                        if (row.getCell(9) != null) {
                            activo = row.getCell(9).getBooleanCellValue();
                        }

                        Piloto piloto = new Piloto(
                            nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia,
                            correoElectronicoPiloto, numeroTelefonicoPiloto, generoPiloto,
                            fechaDeNacimiento, estadoPiloto, activo
                        );

                        if (activo) {
                            pilotos.add(piloto);
                            indiceActual++;
                        }
                        todosPilotos.add(piloto);
                    } catch (Exception e) {
                        System.err.println("Error al procesar la fila " + row.getRowNum() + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                actualizarIndicesEnCalendario(todosPilotos);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo Excel: " + e.getMessage());
                e.printStackTrace();
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