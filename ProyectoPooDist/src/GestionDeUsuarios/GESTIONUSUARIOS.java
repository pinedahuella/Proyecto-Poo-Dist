package GestionDeUsuarios;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * Clase que gestiona la información de usuarios a través de un archivo Excel.
 * Permite cargar, guardar, actualizar y eliminar usuarios.
 */
public class GESTIONUSUARIOS {
    private Vector<Usuarios> usuarios;
    private final String excelFilePath;

    /**
     * Constructor por defecto que inicializa la ruta del archivo Excel.
     */
    public GESTIONUSUARIOS() {
        this.usuarios = new Vector<>();
        this.excelFilePath = "excels/USUARIOS.xlsx";
    }

    /**
     * Constructor que inicializa la lista de usuarios con un vector existente.
     * 
     * @param usrs Vector de usuarios a establecer.
     */
    public GESTIONUSUARIOS(Vector<Usuarios> usrs) {
        this.usuarios = usrs;
        this.excelFilePath = "excels/USUARIOS.xlsx";
    }

    // Getters y Setters
    public void setUsuarios(Vector<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public Vector<Usuarios> getUsuarios() {
        return this.usuarios;
    }

    /**
     * Agrega un nuevo usuario al sistema
     * @param usuario Usuario a agregar
     */
    public void agregarUsuario(Usuarios usuario) {
        this.usuarios.add(usuario);
        guardarUsuariosEnExcel();
    }

    /**
     * Actualiza un usuario existente o lo agrega si no existe
     * @param usuarioActualizado Usuario con la información actualizada
     */
    public void actualizarUsuario(Usuarios usuarioActualizado) {
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNumeroDPI() == usuarioActualizado.getNumeroDPI()) {
                usuarios.set(i, usuarioActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            usuarios.add(usuarioActualizado);
        }   
        guardarUsuariosEnExcel();
        cargarUsuariosDesdeExcel();
    }

    /**
     * Elimina un usuario del sistema
     * @param dpi DPI del usuario a eliminar
     */
 public void eliminarUsuario(long dpi) {
    try {
        boolean usuarioEncontrado = false;
        Set<String> estadosValidos = new HashSet<>(Arrays.asList(
            "ACTIVO",
            "ENFERMO",
            "EN VACACIONES",
            "BLOQUEADO",
            "JUBILADO"
        ));
        
        // Buscar y marcar el usuario como inactivo en memoria
        for (Usuarios usuario : usuarios) {
            if (usuario.getNumeroDPI() == dpi && estadosValidos.contains(usuario.getEstado())) {
                usuario.setEstado("INACTIVO");
                usuarioEncontrado = true;
                break;
            }
        }
        
        if (!usuarioEncontrado) {
            throw new IllegalStateException("No se encontró un usuario válido con el DPI: " + dpi);
        }

        actualizarEstadoEnExcel(dpi, "INACTIVO", false);
        cargarUsuariosDesdeExcel();
        
    } catch (Exception e) {
        System.err.println("Error al eliminar el usuario: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage(), e);
    }
}

public void reactivarUsuario(long dpi) {
    try {
        boolean usuarioEncontrado = false;
        
        // Buscar y reactivar el usuario en memoria
        for (Usuarios usuario : usuarios) {
            if (usuario.getNumeroDPI() == dpi && "INACTIVO".equals(usuario.getEstado())) {
                usuario.setEstado("ACTIVO");
                usuarioEncontrado = true;
                break;
            }
        }
        
        if (!usuarioEncontrado) {
            throw new IllegalStateException("No se encontró un usuario inactivo con el DPI: " + dpi);
        }

        actualizarEstadoEnExcel(dpi, "ACTIVO", true);
        cargarUsuariosDesdeExcel();
        
    } catch (Exception e) {
        System.err.println("Error al reactivar el usuario: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al reactivar el usuario: " + e.getMessage(), e);
    }
}
    /**
     * Carga los usuarios desde el archivo Excel
     */
    public void cargarUsuariosDesdeExcel() {
        usuarios.clear();
        
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String nombreUsuario = getStringCellValue(row.getCell(0));
                String contrasenaUsuario = getStringCellValue(row.getCell(1));
                String nombre = getStringCellValue(row.getCell(2));
                String apellido = getStringCellValue(row.getCell(3));
                String cargo = getStringCellValue(row.getCell(4));
                String genero = getStringCellValue(row.getCell(5));
                long numeroDPI = getNumericCellValue(row.getCell(6));
                String fechaNacimiento = procesarFecha(getStringCellValue(row.getCell(7)));
                int numeroTelefono = (int)getNumericCellValue(row.getCell(8));
                String correoElectronico = getStringCellValue(row.getCell(9));
                String estado = getStringCellValue(row.getCell(10));
                
                Usuarios usuario = new Usuarios(
                    nombreUsuario, contrasenaUsuario, nombre, apellido, cargo, genero,
                    numeroDPI, fechaNacimiento, numeroTelefono, correoElectronico, estado
                );
                usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios desde Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
        private void actualizarEstadoEnExcel(long dpi, String estado, boolean activo) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            boolean excelActualizado = false;
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                
                Cell dpiCell = row.getCell(6);
                if (dpiCell != null && getNumericCellValue(dpiCell) == dpi) {
                    // Actualizar estado (columna 10)
                    Cell estadoCell = row.getCell(10);
                    if (estadoCell == null) {
                        estadoCell = row.createCell(10);
                    }
                    estadoCell.setCellValue(estado);
                    
                    // Actualizar campo activo (columna 11)
                    Cell activoCell = row.getCell(11);
                    if (activoCell == null) {
                        activoCell = row.createCell(11);
                    }
                    activoCell.setCellValue(activo);
                    
                    excelActualizado = true;
                    break;
                }
            }
            
            if (!excelActualizado) {
                throw new IllegalStateException("No se pudo actualizar el usuario en el archivo Excel");
            }
            
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error al acceder al archivo Excel: " + e.getMessage(), e);
        }
    }
        
    /**
     * Guarda los usuarios en el archivo Excel
     */
    public void guardarUsuariosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Usuarios");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Nombre Usuario", "Contraseña", "Nombre", "Apellido", "Cargo",
                "Género", "DPI", "Fecha Nacimiento", "Teléfono", "Correo", "Estado"
            };
            
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Guardar datos de usuarios
            int rowCount = 1;
            for (Usuarios usuario : usuarios) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(usuario.getNombreUsuario());
                row.createCell(1).setCellValue(usuario.getContrasenaUsuario());
                row.createCell(2).setCellValue(usuario.getNombre());
                row.createCell(3).setCellValue(usuario.getApellido());
                row.createCell(4).setCellValue(usuario.getCargo());
                row.createCell(5).setCellValue(usuario.getGenero());
                row.createCell(6).setCellValue(usuario.getNumeroDPI());
                row.createCell(7).setCellValue(usuario.getFechaNacimiento());
                row.createCell(8).setCellValue(usuario.getNumeroTelefono());
                row.createCell(9).setCellValue(usuario.getCorreoElectronico());
                row.createCell(10).setCellValue(usuario.getEstado());
            }

            // Guardar archivo
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios en Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Procesa una fecha desde el formato Excel a dd/MM/yyyy
     */
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

    /**
     * Obtiene el valor de una celda como String
     */
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            default:
                return "";
        }
    }

    /**
     * Obtiene el valor de una celda como número
     */
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
}