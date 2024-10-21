package GestionDeUsuarios;

// Importación de clases necesarias para la funcionalidad de inicio de sesión
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

/**
 * Clase que gestiona la información de usuarios a través de un archivo Excel.
 * Permite cargar, guardar, actualizar y eliminar usuarios.
 */
public class GESTIONUSUARIOS {

    // Vector que almacena los usuarios.
    public Vector<Usuarios> usuarios = new Vector<>();
    
    // Ruta del archivo Excel donde se almacenan los usuarios.
    private String excelFilePath;

    /**
     * Constructor por defecto que inicializa la ruta del archivo Excel.
     */
    public GESTIONUSUARIOS() {
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    /**
     * Constructor que inicializa la lista de usuarios con un vector existente.
     * 
     * @param usrs Vector de usuarios a establecer.
     */
    public GESTIONUSUARIOS(Vector<Usuarios> usrs) {
        this.usuarios = usrs;
        excelFilePath = "excels/USUARIOS.xlsx";
    }

    /**
     * Establece la lista de usuarios.
     * 
     * @param usuario Vector de usuarios a establecer.
     */
    public void setUsuarios(Vector<Usuarios> usuario) {
        this.usuarios = usuario;
    }

    /**
     * Devuelve la lista de usuarios.
     * 
     * @return Vector de usuarios.
     */
    public Vector<Usuarios> getUsuarios() {
        return this.usuarios;
    }

    /**
     * Agrega un nuevo usuario al vector de usuarios.
     * 
     * @param usuario Usuario a agregar.
     */
    public void setUnUsuario(Usuarios usuario) {
        this.usuarios.add(usuario);
    }

    /**
     * Actualiza un usuario existente o lo agrega si no se encuentra.
     * 
     * @param usuarioActualizado Usuario con la información actualizada.
     */
    public void actualizarUsuario(Usuarios usuarioActualizado) {
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            // Compara el número de DPI para encontrar el usuario.
            if (usuarios.get(i).getNumeroDPI() == usuarioActualizado.getNumeroDPI()) {
                usuarios.set(i, usuarioActualizado); // Actualiza el usuario.
                encontrado = true;
                break;
            }
        }
        // Si no se encontró, agrega el usuario nuevo.
        if (!encontrado) {
            usuarios.add(usuarioActualizado);
        }
        guardarUsuariosEnExcel(); // Guarda la lista de usuarios en Excel.
        cargarUsuariosDesdeExcel(); // Carga la lista de usuarios desde Excel.
    }

    /**
     * Elimina un usuario basado en su nombre y apellido.
     * 
     * @param nombre Nombre del usuario a eliminar.
     * @param apellido Apellido del usuario a eliminar.
     */
    public void eliminarUsuario(String nombre, String apellido) {
        // Elimina el usuario si coincide el nombre y apellido.
        usuarios.removeIf(usuario -> usuario.getNombre().equals(nombre) && usuario.getApellido().equals(apellido));
        guardarUsuariosEnExcel(); // Guarda la lista actualizada en Excel.
        cargarUsuariosDesdeExcel(); // Carga la lista de usuarios desde Excel.
    }

    /**
     * Carga los usuarios desde un archivo Excel y los almacena en el vector.
     */
    public void cargarUsuariosDesdeExcel() {
        usuarios.clear(); // Limpia la lista de usuarios antes de cargar.

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Obtiene la primera hoja.

            for (Row row : sheet) {
                // Salta la primera fila que contiene los encabezados.
                if (row.getRowNum() == 0) { 
                    continue;
                }

                // Obtiene los datos de las celdas y los convierte a String.
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
                
                // Procesa la fecha de nacimiento desde el formato Excel.
                String fechaNacimiento;
                if (newnewfechaNacimiento.length() > 7) {
                    fechaNacimiento = newnewfechaNacimiento; // Si ya es un String válido.
                } else {
                    int newfechaNacimiento = Integer.parseInt(newnewfechaNacimiento.split("\\.")[0]);
                    
                    LocalDate excelStartDate = LocalDate.of(1900, 1, 1);
                    if (newfechaNacimiento > 59) {
                        newfechaNacimiento--; 
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = excelStartDate.plusDays(newfechaNacimiento - 1);
                    fechaNacimiento = date.format(formatter); // Formato final de la fecha.
                }
                
                // Crea un nuevo objeto Usuarios y lo agrega al vector.
                Usuarios usuario = new Usuarios(
                    nombreUsuario, contrasenaUsuario, nombre, apellido, cargo, genero,
                    Long.parseLong(numeroDPI), fechaNacimiento, Integer.parseInt(numeroTelefono), correoElectronico, estado
                );
                usuarios.add(usuario);
            }

        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones al cargar.
        }
    }

    /**
     * Obtiene el valor de una celda como String.
     * 
     * @param cell Celda de la que se obtendrá el valor.
     * @return Valor de la celda como String.
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
     * Obtiene el valor de una celda como número (long).
     * 
     * @param cell Celda de la que se obtendrá el valor numérico.
     * @return Valor de la celda como long.
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
                    return 0; // Manejo de excepciones para conversiones fallidas.
                }
            default:
                return 0;
        }
    }

    /**
     * Guarda la lista de usuarios en un archivo Excel.
     */
    public void guardarUsuariosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Usuarios"); // Crea una nueva hoja.

            // Crea la fila de encabezados.
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

            // Escribe cada usuario en una nueva fila.
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

            // Escribe los datos en el archivo.
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones al guardar.
        }
    }
}
