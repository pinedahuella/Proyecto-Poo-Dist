package GestionDeUsuarios;

import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.Piloto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private Map<Long, Integer> posicionesOriginalesUsuarios = new HashMap<>(); // Almacena DPI y posición original

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
// Método para validar si el usuario existe en GESTIONPILOTOS
// Método para validar si el usuario existe en GESTIONPILOTOS
private String validarDuplicadosEnPilotos(Usuarios usuario) {
    GESTIONPILOTOS gestionPilotos = new GESTIONPILOTOS();
    gestionPilotos.cargarPilotosDesdeExcel(); // Cargar datos de pilotos

    for (Piloto piloto : gestionPilotos.getPilotos()) {
        if (piloto.getNumeroDeDpi() == usuario.getNumeroDPI()) {
            return "El DPI ya está registrado en el sistema de pilotos.";
        } else if (piloto.getNumeroTelefonicoPiloto() == usuario.getNumeroTelefono()) {
            return "El número de teléfono ya está registrado en el sistema de pilotos.";
        } else if (piloto.getCorreoElectronicoPiloto().equalsIgnoreCase(usuario.getCorreoElectronico())) {
            return "El correo electrónico ya está registrado en el sistema de pilotos.";
        }
    }
    return null; // No hay duplicados
}

public void agregarUsuario(Usuarios nuevoUsuario) {
    // Validar que el usuario no esté duplicado en pilotos
    String mensajeError = validarDuplicadosEnPilotos(nuevoUsuario);
    if (mensajeError != null) {
        throw new IllegalStateException(mensajeError);
    }
    
    // Agregar el usuario si no existe
    for (Usuarios usuario : usuarios) {
        if (usuario.getNumeroDPI() == nuevoUsuario.getNumeroDPI()) {
            throw new IllegalStateException("El usuario ya existe en el sistema.");
        }
    }
    usuarios.add(nuevoUsuario);
    guardarUsuariosEnExcel();
}

public void actualizarUsuario(Usuarios usuarioActualizado) {
    // Validar que el usuario no esté duplicado en pilotos
    String mensajeError = validarDuplicadosEnPilotos(usuarioActualizado);
    if (mensajeError != null) {
        throw new IllegalStateException(mensajeError);
    }

    // Actualizar usuario
    for (int i = 0; i < usuarios.size(); i++) {
        if (usuarios.get(i).getNumeroDPI() == usuarioActualizado.getNumeroDPI()) {
            usuarios.set(i, usuarioActualizado);
            guardarUsuariosEnExcel();
            return; // Salir después de actualizar
        }
    }
    throw new IllegalStateException("El usuario no existe.");
}




public void eliminarUsuario(long dpi) {
    try {
        int usuarioIndex = -1;
        
        // Buscar el usuario y su posición
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNumeroDPI() == dpi && !"INACTIVO".equals(usuarios.get(i).getEstado())) {
                usuarioIndex = i;
                break;
            }
        }

        if (usuarioIndex == -1) {
            throw new IllegalStateException("No se encontró un usuario válido con el DPI: " + dpi);
        }

        Usuarios usuarioADesactivar = usuarios.get(usuarioIndex);
        usuarioADesactivar.setEstado("INACTIVO");
        
        // Remover el usuario de su posición actual
        usuarios.remove(usuarioIndex);
        
        // Agregar el usuario al final de la lista
        usuarios.add(usuarioADesactivar);
        
        // Guardar cambios en Excel
        guardarUsuariosEnExcel();
        
    } catch (Exception e) {
        System.err.println("Error al eliminar el usuario: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage(), e);
    }
}

public void reactivarUsuario(long dpi) {
    try {
        int usuarioIndex = -1;
        
        // Buscar el usuario inactivo
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNumeroDPI() == dpi && "INACTIVO".equals(usuarios.get(i).getEstado())) {
                usuarioIndex = i;
                break;
            }
        }

        if (usuarioIndex == -1) {
            throw new IllegalStateException("No se encontró un usuario inactivo con el DPI: " + dpi);
        }

        // Obtener el usuario y cambiar su estado
        Usuarios usuarioAReactivar = usuarios.get(usuarioIndex);
        usuarioAReactivar.setEstado("ACTIVO");
        
        // Remover de la posición actual
        usuarios.remove(usuarioIndex);
        
        // Insertar justo antes del primer usuario inactivo
        int posicionInsercion = encontrarPosicionParaInsertar();
        usuarios.add(posicionInsercion, usuarioAReactivar);
        
        // Guardar cambios en Excel
        guardarUsuariosEnExcel();
        
    } catch (Exception e) {
        System.err.println("Error al reactivar el usuario: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al reactivar el usuario: " + e.getMessage(), e);
    }
}

private int encontrarPosicionParaInsertar() {
    // Encuentra la posición del primer usuario inactivo
    for (int i = 0; i < usuarios.size(); i++) {
        if ("INACTIVO".equals(usuarios.get(i).getEstado())) {
            return i;
        }
    }
    // Si no hay usuarios inactivos, retorna el tamaño de la lista
    return usuarios.size();
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

public void cargarUsuariosDesdeExcel() {
    usuarios.clear();
    List<Usuarios> usuariosActivos = new ArrayList<>();
    List<Usuarios> usuariosInactivos = new ArrayList<>();
    
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

            // Separar usuarios activos e inactivos
            if ("INACTIVO".equals(estado)) {
                usuariosInactivos.add(usuario);
            } else {
                usuariosActivos.add(usuario);
            }
        }

        // Primero agregar todos los activos
        usuarios.addAll(usuariosActivos);
        // Luego agregar todos los inactivos
        usuarios.addAll(usuariosInactivos);

    } catch (IOException e) {
        System.err.println("Error al cargar usuarios desde Excel: " + e.getMessage());
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