package PaquetePrincipal;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GESTIONPILOTOS {

    public Vector<Piloto> pilotos = new Vector<>();
    
    private String excelFilePath;


    public GESTIONPILOTOS() {
        excelFilePath = "excels/PINEED.xlsx";
    }


    public GESTIONPILOTOS(Vector<Piloto> pil) {
        this.pilotos = pil;
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
    }


    public void actualizarPiloto(int indice, String nombrePiloto, String apellidoPiloto, long numeroDeDpi,
                                 String tipoLicencia, String correoElectronicoPiloto, int numeroTelefonicoPiloto,
                                 String generoPiloto, String fechaDeNacimiento, String estadoPiloto) {
        Piloto piloto = this.pilotos.get(indice);
        piloto.setNombrePiloto(nombrePiloto);
        piloto.setApellidoPiloto(apellidoPiloto);
        piloto.setNumeroDeDpi(numeroDeDpi);
        piloto.setTipoLicencia(tipoLicencia);
        piloto.setCorreoElectronicoPiloto(correoElectronicoPiloto);
        piloto.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
        piloto.setGeneroPiloto(generoPiloto);
        piloto.setFechaDeNacimiento(fechaDeNacimiento);
        piloto.setEstadoPiloto(estadoPiloto);
    }

public void cargarPilotosDesdeExcel() {
    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

 
            String nombrePiloto = getStringCellValue(row.getCell(0));
            String apellidoPiloto = getStringCellValue(row.getCell(1));
            long numeroDeDpi = getNumericCellValue(row.getCell(2));
            String tipoLicencia = getStringCellValue(row.getCell(3));
            String correoElectronicoPiloto = getStringCellValue(row.getCell(4));
            int numeroTelefonicoPiloto = (int) getNumericCellValue(row.getCell(5));
            String generoPiloto = getStringCellValue(row.getCell(6));
            String newnewfechaDeNacimiento = getStringCellValue(row.getCell(7));
            String estadoPiloto = getStringCellValue(row.getCell(8));
            
            String fechaDeNacimiento;
            
            if (newnewfechaDeNacimiento.length() > 7) {
                
                fechaDeNacimiento = newnewfechaDeNacimiento;
                System.out.println(fechaDeNacimiento);
                
            }else{
                
                int newfechaDeNacimiento = Integer.parseInt(newnewfechaDeNacimiento.split("\\.")[0]);

                LocalDate excelStartDate = LocalDate.of(1900, 1, 1);


                if (newfechaDeNacimiento > 59) {
         
                        newfechaDeNacimiento--;
                }
 

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


                LocalDate date = excelStartDate.plusDays(newfechaDeNacimiento - 1);

                fechaDeNacimiento = "" + date.format(formatter);
                System.out.println(fechaDeNacimiento);
            }

            Piloto piloto = new Piloto(nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia, correoElectronicoPiloto,
                                       numeroTelefonicoPiloto, generoPiloto, fechaDeNacimiento, estadoPiloto);
            pilotos.add(piloto);
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
            headerRow.createCell(0).setCellValue("Nombre Piloto");
            headerRow.createCell(1).setCellValue("Apellido Piloto");
            headerRow.createCell(2).setCellValue("Número de DPI");
            headerRow.createCell(3).setCellValue("Tipo Licencia");
            headerRow.createCell(4).setCellValue("Correo Electrónico");
            headerRow.createCell(5).setCellValue("Número Telefónico");
            headerRow.createCell(6).setCellValue("Género");
            headerRow.createCell(7).setCellValue("Fecha de Nacimiento");
            headerRow.createCell(8).setCellValue("Estado");

            int rowCount = 1;
            for (Piloto piloto : pilotos) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(piloto.getNombrePiloto());
                row.createCell(1).setCellValue(piloto.getApellidoPiloto());
                row.createCell(2).setCellValue(piloto.getNumeroDeDpi());
                row.createCell(3).setCellValue(piloto.getTipoLicencia());
                row.createCell(4).setCellValue(piloto.getCorreoElectronicoPiloto());
                row.createCell(5).setCellValue(piloto.getNumeroTelefonicoPiloto());
                row.createCell(6).setCellValue(piloto.getGeneroPiloto());
                row.createCell(7).setCellValue(piloto.getFechaDeNacimiento());
                row.createCell(8).setCellValue(piloto.getEstadoPiloto());
            }

            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}