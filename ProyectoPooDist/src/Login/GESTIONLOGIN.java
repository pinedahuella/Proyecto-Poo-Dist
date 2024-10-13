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

public class GESTIONLOGIN {
    private Vector<Login> logins = new Vector<>();
    private String excelFilePath;

    public GESTIONLOGIN() {
        excelFilePath = "excels/LOGGIN.xlsx";
    }

    public GESTIONLOGIN(Vector<Login> logins) {
        this.logins = logins;
        excelFilePath = "excels/LOGGIN.xlsx";
    }

    public void setLogins(Vector<Login> logins) {
        this.logins = logins;
    }

    public Vector<Login> getLogins() {
        return this.logins;
    }

    public void setUnLogin(Login login) {
        this.logins.add(login);
        guardarLoginsEnExcel();
    }

    public void actualizarLogin(Login loginActualizado) {
        boolean encontrado = false;
        for (int i = 0; i < logins.size(); i++) {
            if (logins.get(i).getPersonal().equals(loginActualizado.getPersonal())) {
                logins.set(i, loginActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            logins.add(loginActualizado);
        }
        guardarLoginsEnExcel();
    }

     // Actualizar el método cargarLoginsDesdeExcel también
    public void cargarLoginsDesdeExcel() {
        logins.clear();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String tiempoEntrada = formatter.formatCellValue(row.getCell(0));
                String tiempoSalida = formatter.formatCellValue(row.getCell(1));
                String personal = formatter.formatCellValue(row.getCell(2));
                String rol = formatter.formatCellValue(row.getCell(3));

                Login login = new Login(tiempoEntrada, tiempoSalida, personal, rol);
                logins.add(login);
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

 public void guardarLoginsEnExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("Logins");
            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Tiempo Entrada");
            headerRow.createCell(1).setCellValue("Tiempo Salida");
            headerRow.createCell(2).setCellValue("Personal");
            headerRow.createCell(3).setCellValue("Rol");

            int rowCount = 1;
            for (Login login : logins) {
                Row row = sheet.createRow(rowCount++);
                
                // Manejar Tiempo Entrada
                Cell cellEntrada = row.createCell(0);
                cellEntrada.setCellValue(login.getTiempoEntrada());
                cellEntrada.setCellStyle(dateCellStyle);
                
                // Manejar Tiempo Salida
                Cell cellSalida = row.createCell(1);
                if (!login.getTiempoSalida().isEmpty()) {
                    cellSalida.setCellValue(login.getTiempoSalida());
                    cellSalida.setCellStyle(dateCellStyle);
                } else {
                    cellSalida.setCellValue("");
                }
                
                row.createCell(2).setCellValue(login.getPersonal());
                row.createCell(3).setCellValue(login.getRol());
            }

            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
