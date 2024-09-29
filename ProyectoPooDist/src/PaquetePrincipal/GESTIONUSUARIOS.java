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

    public GESTIONUSUARIOS() {
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    public GESTIONUSUARIOS(Vector<Usuarios> usrs) {
        this.usuarios = usrs;
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    public void setUsuarios(Vector<Usuarios> usuario) {
        this.usuarios = usuario;
    }

    public Vector<Usuarios> getUsuarios() {
        return this.usuarios;
    }

    public void setUnUsuario(Usuarios usuario) {
        this.usuarios.add(usuario);
    }

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

public void eliminarUsuario(String nombre, String apellido) {
    usuarios.removeIf(usuario -> usuario.getNombre().equals(nombre) && usuario.getApellido().equals(apellido));
    guardarUsuariosEnExcel();
    cargarUsuariosDesdeExcel();
}

    public void cargarUsuariosDesdeExcel() {
                usuarios.clear();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { 
                    continue;
                }

                String nombreUsuario = getStringCellValue(row.getCell(0));
                String contrasenaUsuario = getStringCellValue(row.getCell(1));
                String nombre = getStringCellValue(row.getCell(2));
                String apellido = getStringCellValue(row.getCell(3));
                String cargo = getStringCellValue(row.getCell(4));
                String genero = getStringCellValue(row.getCell(5));
                String numeroDPI = getStringCellValue(row.getCell(6)); 
                String newnewfechaNacimiento = getStringCellValue(row.getCell(7)); 
                String numeroTelefono = getStringCellValue(row.getCell(8));
                String correoElectronico = getStringCellValue(row.getCell(9));
                String estado = getStringCellValue(row.getCell(10)); 
                
                String fechaNacimiento;
                if (newnewfechaNacimiento.length() > 7) {
                    fechaNacimiento = newnewfechaNacimiento;
                } else {
                    int newfechaNacimiento = Integer.parseInt(newnewfechaNacimiento.split("\\.")[0]);
                    
                    LocalDate excelStartDate = LocalDate.of(1900, 1, 1);

                    if (newfechaNacimiento > 59) {
                        newfechaNacimiento--; 
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = excelStartDate.plusDays(newfechaNacimiento - 1);
                    fechaNacimiento = date.format(formatter);
                }
                
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

    public void guardarUsuariosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Usuarios");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre de Usuario");
            headerRow.createCell(1).setCellValue("Contraseña");
            headerRow.createCell(2).setCellValue("Nombre");
            headerRow.createCell(3).setCellValue("Apellido");
            headerRow.createCell(4).setCellValue("Cargo");
            headerRow.createCell(5).setCellValue("Género");
            headerRow.createCell(6).setCellValue("Número de DPI");
            headerRow.createCell(7).setCellValue("Fecha de Nacimiento");
            headerRow.createCell(8).setCellValue("Número Telefónico");
            headerRow.createCell(9).setCellValue("Correo Electrónico");
            headerRow.createCell(10).setCellValue("Estado");

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
