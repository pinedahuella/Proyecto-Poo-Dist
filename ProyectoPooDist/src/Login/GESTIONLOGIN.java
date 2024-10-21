package Login;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * GESTIONLOGIN es responsable de gestionar los registros de inicio de sesión.
 * Permite agregar, actualizar y guardar información de inicio de sesión en un archivo Excel.
 */
public class GESTIONLOGIN {
    private Vector<Login> logins = new Vector<>(); // Almacena una colección de registros de inicio de sesión
    private String excelFilePath; // Ruta del archivo Excel donde se guardan los registros

    // Constructor por defecto que inicializa la ruta del archivo Excel
    public GESTIONLOGIN() {
        excelFilePath = "excels/LOGGIN.xlsx";
    }

    // Constructor que recibe un vector de registros de inicio de sesión
    public GESTIONLOGIN(Vector<Login> logins) {
        this.logins = logins;
        excelFilePath = "excels/LOGGIN.xlsx";
    }

    // Método para establecer los registros de inicio de sesión
    public void setLogins(Vector<Login> logins) {
        this.logins = logins;
    }

    // Método para obtener los registros de inicio de sesión
    public Vector<Login> getLogins() {
        return this.logins;
    }

    // Método para agregar un nuevo registro de inicio de sesión
    public void setUnLogin(Login login) {
        this.logins.add(login); // Agrega el nuevo registro al vector
        guardarLoginsEnExcel(); // Guarda los registros actualizados en Excel
    }

    // Método para actualizar un registro de inicio de sesión
    public void actualizarLogin(Login loginActualizado) {
        boolean encontrado = false; // Bandera para indicar si se encontró el registro
        for (int i = 0; i < logins.size(); i++) {
            // Verifica si el personal coincide con el registro actualizado
            if (logins.get(i).getPersonal().equals(loginActualizado.getPersonal())) {
                logins.set(i, loginActualizado); // Actualiza el registro
                encontrado = true; // Marca que se encontró el registro
                break;
            }
        }
        // Si no se encontró el registro, lo agrega
        if (!encontrado) {
            logins.add(loginActualizado);
        }
        guardarLoginsEnExcel(); // Guarda los registros actualizados en Excel
    }

    // Método para cargar los registros de inicio de sesión desde el archivo Excel
    public void cargarLoginsDesdeExcel() {
        logins.clear(); // Limpia la lista de registros antes de cargar nuevos datos
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) { // Abre el archivo Excel

            Sheet sheet = workbook.getSheetAt(0); // Obtiene la primera hoja
            DataFormatter formatter = new DataFormatter(); // Formateador para datos de Excel

            // Itera sobre las filas de la hoja
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Salta la fila de encabezado

                // Obtiene los valores de las celdas
                String tiempoEntrada = formatter.formatCellValue(row.getCell(0));
                String tiempoSalida = formatter.formatCellValue(row.getCell(1));
                String personal = formatter.formatCellValue(row.getCell(2));
                String rol = formatter.formatCellValue(row.getCell(3));

                // Crea un nuevo objeto Login y lo agrega a la lista
                Login login = new Login(tiempoEntrada, tiempoSalida, personal, rol);
                logins.add(login);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Maneja excepciones de entrada/salida
        }
    }

    // Método privado para obtener el valor de una celda como String
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Retorna vacío si la celda es nula
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue(); // Retorna el valor de texto
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue()); // Retorna el valor numérico formateado
            default:
                return ""; // Retorna vacío para otros tipos
        }
    }

    // Método privado para obtener el valor de una celda como numérico
    private long getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0; // Retorna 0 si la celda es nula
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (long) cell.getNumericCellValue(); // Retorna el valor numérico
            case STRING:
                try {
                    return Long.parseLong(cell.getStringCellValue()); // Intenta convertir el texto a numérico
                } catch (NumberFormatException e) {
                    return 0; // Retorna 0 si no se puede convertir
                }
            default:
                return 0; // Retorna 0 para otros tipos
        }
    }

    // Método para guardar los registros de inicio de sesión en el archivo Excel
    public void guardarLoginsEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) { // Crea un nuevo libro de trabajo

            Sheet sheet = workbook.createSheet("Logins"); // Crea una nueva hoja
            CreationHelper createHelper = workbook.getCreationHelper(); // Ayudante para crear datos
            CellStyle dateCellStyle = workbook.createCellStyle(); // Estilo para celdas de fecha
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss")); // Formato de fecha

            // Crea la fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Tiempo Entrada");
            headerRow.createCell(1).setCellValue("Tiempo Salida");
            headerRow.createCell(2).setCellValue("Personal");
            headerRow.createCell(3).setCellValue("Rol");

            int rowCount = 1; // Contador de filas para los datos
            for (Login login : logins) {
                Row row = sheet.createRow(rowCount++); // Crea una nueva fila
                
                // Manejar Tiempo Entrada
                Cell cellEntrada = row.createCell(0);
                cellEntrada.setCellValue(login.getTiempoEntrada()); // Establece el tiempo de entrada
                cellEntrada.setCellStyle(dateCellStyle); // Aplica el estilo de fecha
                
                // Manejar Tiempo Salida
                Cell cellSalida = row.createCell(1);
                if (!login.getTiempoSalida().isEmpty()) {
                    cellSalida.setCellValue(login.getTiempoSalida()); // Establece el tiempo de salida
                    cellSalida.setCellStyle(dateCellStyle); // Aplica el estilo de fecha
                } else {
                    cellSalida.setCellValue(""); // Deja vacío si no hay tiempo de salida
                }
                
                row.createCell(2).setCellValue(login.getPersonal()); // Establece el nombre del personal
                row.createCell(3).setCellValue(login.getRol()); // Establece el rol del personal
            }

            workbook.write(fos); // Escribe los datos en el archivo Excel
        } catch (IOException e) {
            e.printStackTrace(); // Maneja excepciones de entrada/salida
        }
    }
}
