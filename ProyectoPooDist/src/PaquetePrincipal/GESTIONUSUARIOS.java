package PaquetePrincipal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class GESTIONUSUARIOS {

    public Vector<Usuarios> usuarios = new Vector<>();
    
    private String excelFilePath;

    // Constructor por defecto
    public GESTIONUSUARIOS() {
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    // Constructor con Vector de Usuarios
    public GESTIONUSUARIOS(Vector<Usuarios> usrs) {
        this.usuarios = usrs;
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    // Métodos setters y getters
    public void setUsuarios(Vector<Usuarios> usuario) {
        this.usuarios = usuario;
    }

    public Vector<Usuarios> getUsuarios() {
        return this.usuarios;
    }

    // Función para agregar un usuario al vector
    public void setUnUsuario(Usuarios usuario) {
        this.usuarios.add(usuario);
    }

    // Función para actualizar los datos de un usuario
    public void actualizarUsuario(int indice, String nombreUsuario, String contrasenaUsuario, String nombre,
                                  String apellido, String cargo, String genero, long numeroDPI,
                                  String fechaNacimiento, int numeroTelefono, String correoElectronico, String estado) {
        Usuarios usuario = this.usuarios.get(indice);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenaUsuario(contrasenaUsuario);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCargo(cargo);
        usuario.setGenero(genero);
        usuario.setNumeroDPI(numeroDPI);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setNumeroTelefono(numeroTelefono);
        usuario.setEstado(estado);
    }

public void cargarUsuariosDesdeExcel() {
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Skip header row
                continue;
            }

            // Read cell values with the correct type
            String nombreUsuario = getStringCellValue(row.getCell(0));
            String contrasenaUsuario = getStringCellValue(row.getCell(1));
            String nombre = getStringCellValue(row.getCell(2));
            String apellido = getStringCellValue(row.getCell(3));
            String cargo = getStringCellValue(row.getCell(4));
            String genero = getStringCellValue(row.getCell(5));
            String numeroDPI = getStringCellValue(row.getCell(6)); // Use getStringCellValue for large numbers
            String newnewfechaNacimiento = getStringCellValue(row.getCell(7)); // New helper method for date handling
            String numeroTelefono = getStringCellValue(row.getCell(8)); // Use getStringCellValue for phone numbers
            String correoElectronico = getStringCellValue(row.getCell(9));
            String estado = getStringCellValue(row.getCell(10)); // Read the "Estado" cell
            
          

            // Date handling
            String fechaNacimiento;
            if (newnewfechaNacimiento.length() > 7) {
                fechaNacimiento = newnewfechaNacimiento;
            } else {
                int newfechaNacimiento = Integer.parseInt(newnewfechaNacimiento.split("\\.")[0]);
                
                // La fecha base de Excel es el 1 de enero de 1900
                LocalDate excelStartDate = LocalDate.of(1900, 1, 1);

                // Excel tiene un error, cuenta el 29 de febrero de 1900, que no existió (año no bisiesto)
                if (newfechaNacimiento > 59) {
                    newfechaNacimiento--; // Ajuste para corregir este error
                }
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = excelStartDate.plusDays(newfechaNacimiento - 1);
                fechaNacimiento = date.format(formatter);
            }
            
            // Create the user and add to the list
            Usuarios usuario = new Usuarios(
                nombreUsuario, contrasenaUsuario, nombre, apellido, cargo, genero,
                Long.parseLong(numeroDPI), fechaNacimiento, Integer.parseInt(numeroTelefono), correoElectronico, estado
            );
            usuarios.add(usuario);
        }

    } catch (IOException e) {
        e.printStackTrace();
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
            // Convert numeric values to string without scientific notation
            return String.format("%.0f", cell.getNumericCellValue());
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

    // Función para sobrescribir el archivo Excel con los datos actuales
    public void guardarUsuariosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Usuarios");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre de Usuario");
            headerRow.createCell(1).setCellValue("Contraseña");
            headerRow.createCell(2).setCellValue("Nombre");
            headerRow.createCell(3).setCellValue("Apellido");
            headerRow.createCell(4).setCellValue("Cargo");;
            headerRow.createCell(5).setCellValue("Género");
            headerRow.createCell(6).setCellValue("Número de DPI");
            headerRow.createCell(7).setCellValue("Fecha de Nacimiento");
            headerRow.createCell(8).setCellValue("Número Telefónico");
            headerRow.createCell(9).setCellValue("Correo Electrónico");
            headerRow.createCell(10).setCellValue("Estado");

            // Rellenar filas con datos del vector
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

            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}