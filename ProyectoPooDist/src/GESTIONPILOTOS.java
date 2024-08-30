import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GESTIONPILOTOS implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class Piloto implements Serializable {
        private static final long serialVersionUID = 1L;

        private String nombrePiloto;
        private String apellidoPiloto;
        private String numeroDeDpi;
        private String tipoLicencia;
        private String correoElectronicoPiloto;
        private String numeroTelefonicoPiloto;
        private String generoPiloto;
        private String fechaDeNacimiento;
        private String estadoPiloto;

        public Piloto() {
            this.nombrePiloto = "";
            this.apellidoPiloto = "";
            this.numeroDeDpi = "";
            this.tipoLicencia = "";
            this.correoElectronicoPiloto = "";
            this.numeroTelefonicoPiloto = "";
            this.generoPiloto = "";
            this.fechaDeNacimiento = "";
            this.estadoPiloto = "";
        }

        public Piloto(String nombrePiloto, String apellidoPiloto, String numeroDeDpi, String tipoLicencia, String correoElectronicoPiloto, String numeroTelefonicoPiloto, String generoPiloto, String fechaDeNacimiento, String estadoPiloto) {
            this.nombrePiloto = nombrePiloto;
            this.apellidoPiloto = apellidoPiloto;
            this.numeroDeDpi = numeroDeDpi;
            this.tipoLicencia = tipoLicencia;
            this.correoElectronicoPiloto = correoElectronicoPiloto;
            this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
            this.generoPiloto = generoPiloto;
            this.fechaDeNacimiento = fechaDeNacimiento;
            this.estadoPiloto = estadoPiloto;
        }

        // Getters y Setters

        public String getNombrePiloto() {
            return nombrePiloto;
        }

        public void setNombrePiloto(String nombrePiloto) {
            this.nombrePiloto = nombrePiloto;
        }

        public String getApellidoPiloto() {
            return apellidoPiloto;
        }

        public void setApellidoPiloto(String apellidoPiloto) {
            this.apellidoPiloto = apellidoPiloto;
        }

        public String getNumeroDeDpi() {
            return numeroDeDpi;
        }

        public void setNumeroDeDpi(String numeroDeDpi) {
            this.numeroDeDpi = numeroDeDpi;
        }

        public String getTipoLicencia() {
            return tipoLicencia;
        }

        public void setTipoLicencia(String tipoLicencia) {
            this.tipoLicencia = tipoLicencia;
        }

        public String getCorreoElectronicoPiloto() {
            return correoElectronicoPiloto;
        }

        public void setCorreoElectronicoPiloto(String correoElectronicoPiloto) {
            this.correoElectronicoPiloto = correoElectronicoPiloto;
        }

        public String getNumeroTelefonicoPiloto() {
            return numeroTelefonicoPiloto;
        }

        public void setNumeroTelefonicoPiloto(String numeroTelefonicoPiloto) {
            this.numeroTelefonicoPiloto = numeroTelefonicoPiloto;
        }

        public String getGeneroPiloto() {
            return generoPiloto;
        }

        public void setGeneroPiloto(String generoPiloto) {
            this.generoPiloto = generoPiloto;
        }

        public String getFechaDeNacimiento() {
            return fechaDeNacimiento;
        }

        public void setFechaDeNacimiento(String fechaDeNacimiento) {
            this.fechaDeNacimiento = fechaDeNacimiento;
        }

        public String getEstadoPiloto() {
            return estadoPiloto;
        }

        public void setEstadoPiloto(String estadoPiloto) {
            this.estadoPiloto = estadoPiloto;
        }

        @Override
        public String toString() {
            return "Piloto{" + "nombrePiloto='" + nombrePiloto + '\'' +
                    ", apellidoPiloto='" + apellidoPiloto + '\'' +
                    ", numeroDeDpi='" + numeroDeDpi + '\'' +
                    ", tipoLicencia='" + tipoLicencia + '\'' +
                    ", correoElectronicoPiloto='" + correoElectronicoPiloto + '\'' +
                    ", numeroTelefonicoPiloto='" + numeroTelefonicoPiloto + '\'' +
                    ", generoPiloto='" + generoPiloto + '\'' +
                    ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                    ", estadoPiloto='" + estadoPiloto + '\'' +
                    '}';
        }
    }

    public List<Piloto> leerPilotosDesdeExcel(String rutaArchivo) {
        List<Piloto> listaPilotos = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Salta el encabezado
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Piloto piloto = new Piloto();
                piloto.setNombrePiloto(row.getCell(0).getStringCellValue());
                piloto.setApellidoPiloto(row.getCell(1).getStringCellValue());
                piloto.setNumeroDeDpi(row.getCell(2).getStringCellValue());
                piloto.setTipoLicencia(row.getCell(3).getStringCellValue());
                piloto.setCorreoElectronicoPiloto(row.getCell(4).getStringCellValue());
                piloto.setNumeroTelefonicoPiloto(row.getCell(5).getStringCellValue());
                piloto.setGeneroPiloto(row.getCell(6).getStringCellValue());
                piloto.setFechaDeNacimiento(row.getCell(7).getStringCellValue());
                piloto.setEstadoPiloto(row.getCell(8).getStringCellValue());

                listaPilotos.add(piloto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaPilotos;
    }

    public static void main(String[] args) {
        GESTIONPILOTOS gestionPilotos = new GESTIONPILOTOS();

        String rutaArchivo = "C:\\Users\\joseg\\Downloads\\GestionPilotos.xlsx";
        List<Piloto> listaPilotos = gestionPilotos.leerPilotosDesdeExcel(rutaArchivo);

        for (Piloto piloto : listaPilotos) {
            System.out.println(piloto);
        }
    }
}