package GestionDePilotos;

// Importación de bibliotecas necesarias
import org.apache.poi.ss.usermodel.*; // Clases de Apache POI para trabajar con hojas de cálculo de Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Clase para manejar archivos Excel en formato .xlsx
import java.io.FileInputStream; // Clase para leer datos desde archivos
import java.io.FileOutputStream; // Clase para escribir datos en archivos
import java.io.IOException; // Clase para manejar excepciones de entrada/salida
import java.util.Vector; // Clase que implementa una lista dinámica para almacenar objetos
import java.time.LocalDate; // Clase para manipular fechas
import java.time.format.DateTimeFormatter; // Clase para formatear fechas
import static org.apache.poi.ss.usermodel.CellType.NUMERIC; // Constante para celdas de tipo numérico
import static org.apache.poi.ss.usermodel.CellType.STRING; // Constante para celdas de tipo texto

// Clase principal para gestionar la información de pilotos
public class GESTIONPILOTOS {
    // Vector para almacenar objetos de tipo Piloto
    public Vector<Piloto> pilotos = new Vector<>();
    // Ruta del archivo de Excel donde se almacenan los datos de los pilotos
    private String excelFilePath;

    // Constructor por defecto que inicializa la ruta del archivo Excel
    public GESTIONPILOTOS() {
        excelFilePath = "excels/PINEED.xlsx"; // Define la ruta del archivo Excel
    }

    // Constructor que permite inicializar el Vector de pilotos y establece la ruta del archivo
    public GESTIONPILOTOS(Vector<Piloto> pils) {
        this.pilotos = pils; // Asigna el Vector de pilotos proporcionado
        excelFilePath = "excels/PINEED.xlsx"; // Define la ruta del archivo Excel
    }

    // Método para establecer el Vector de pilotos
    public void setPilotos(Vector<Piloto> piloto) {
        this.pilotos = piloto; // Asigna el Vector de pilotos
    }

    // Método para obtener el Vector de pilotos
    public Vector<Piloto> getPilotos() {
        return this.pilotos; // Devuelve el Vector de pilotos
    }

    // Método para agregar un nuevo piloto al Vector
    public void setUnPiloto(Piloto piloto) {
        this.pilotos.add(piloto); // Agrega un piloto al Vector
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
        // Guarda los pilotos en Excel y recarga la lista
        guardarPilotosEnExcel();
        cargarPilotosDesdeExcel();
    }

    // Método para eliminar un piloto del Vector
    public void eliminarPiloto(String nombre, String apellido) {
        // Remueve el piloto que coincide con el nombre y apellido proporcionados
        pilotos.removeIf(piloto -> piloto.getNombrePiloto().equals(nombre) && piloto.getApellidoPiloto().equals(apellido));
        // Guarda los cambios en el archivo Excel y recarga la lista de pilotos
        guardarPilotosEnExcel();
        cargarPilotosDesdeExcel();
    }

    // Método para cargar los datos de los pilotos desde un archivo Excel
    public void cargarPilotosDesdeExcel() {
        pilotos.clear(); // Limpia el Vector de pilotos antes de cargar nuevos datos
        try (FileInputStream fis = new FileInputStream(excelFilePath); // Intenta abrir el archivo Excel
             Workbook workbook = new XSSFWorkbook(fis)) { // Crea un objeto Workbook para manipular el archivo

            Sheet sheet = workbook.getSheetAt(0); // Obtiene la primera hoja del archivo

            // Recorre cada fila de la hoja
            for (Row row : sheet) {
                // Omite la primera fila (encabezados)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Obtiene los valores de cada celda y los asigna a variables
                String nombrePiloto = getStringCellValue(row.getCell(0));
                String apellidoPiloto = getStringCellValue(row.getCell(1));
                long numeroDeDpi = getNumericCellValue(row.getCell(2));
                String tipoLicencia = getStringCellValue(row.getCell(3));
                String correoElectronicoPiloto = getStringCellValue(row.getCell(4));
                int numeroTelefonicoPiloto = (int) getNumericCellValue(row.getCell(5));
                String generoPiloto = getStringCellValue(row.getCell(6));
                String newnewfechaDeNacimiento = getStringCellValue(row.getCell(7));
                String estadoPiloto = getStringCellValue(row.getCell(8));

                // Variable para almacenar la fecha de nacimiento
                String fechaDeNacimiento;

                // Comprueba si la fecha de nacimiento tiene más de 7 caracteres
                if (newnewfechaDeNacimiento.length() > 7) {
                    fechaDeNacimiento = newnewfechaDeNacimiento; // Si es así, se usa directamente
                    System.out.println(fechaDeNacimiento); // Imprime la fecha de nacimiento
                } else {
                    // Convierte la fecha desde el formato de Excel (número de días)
                    int newfechaDeNacimiento = Integer.parseInt(newnewfechaDeNacimiento.split("\\.")[0]);

                    // Define la fecha de inicio de Excel
                    LocalDate excelStartDate = LocalDate.of(1900, 1, 1);

                    // Ajusta la fecha si es necesario
                    if (newfechaDeNacimiento > 59) {
                        newfechaDeNacimiento--; // Corrige el día en caso de que sea necesario
                    }

                    // Crea un formateador de fechas
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    // Calcula la fecha de nacimiento a partir de la fecha de inicio
                    LocalDate date = excelStartDate.plusDays(newfechaDeNacimiento - 1);
                    fechaDeNacimiento = "" + date.format(formatter); // Formatea la fecha
                    System.out.println(fechaDeNacimiento); // Imprime la fecha de nacimiento
                }

                // Crea un nuevo objeto Piloto y lo añade al Vector
                Piloto piloto = new Piloto(nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia, correoElectronicoPiloto,
                                           numeroTelefonicoPiloto, generoPiloto, fechaDeNacimiento, estadoPiloto);
                pilotos.add(piloto); // Añade el piloto al Vector
            }

        } catch (IOException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en caso de error
        }
    }

    // Método para obtener el valor de una celda de tipo String
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Devuelve una cadena vacía si la celda es nula
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue(); // Devuelve el valor si es una cadena
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue()); // Devuelve el valor numérico como cadena
            default:
                return ""; // Devuelve una cadena vacía para otros tipos
        }
    }

    // Método para obtener el valor de una celda de tipo numérico
    private long getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0; // Devuelve 0 si la celda es nula
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (long) cell.getNumericCellValue(); // Devuelve el valor si es numérico
            case STRING:
                try {
                    return Long.parseLong(cell.getStringCellValue()); // Intenta convertir a long si es una cadena
                } catch (NumberFormatException e) {
                    return 0; // Devuelve 0 si la conversión falla
                }
            default:
                return 0; // Devuelve 0 para otros tipos
        }
    }

    // Método para guardar los datos de los pilotos en un archivo Excel
    public void guardarPilotosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook(); // Crea un nuevo objeto Workbook para escribir
             FileOutputStream fos = new FileOutputStream(excelFilePath)) { // Abre el archivo para escribir

            Sheet sheet = workbook.createSheet("Pilotos"); // Crea una nueva hoja llamada "Pilotos"

            // Crea la fila de encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre Piloto");
            headerRow.createCell(1).setCellValue("Apellido Piloto");
            headerRow.createCell(2).setCellValue("DPI");
            headerRow.createCell(3).setCellValue("Tipo Licencia");
            headerRow.createCell(4).setCellValue("Correo Electrónico");
            headerRow.createCell(5).setCellValue("Número Telefónico");
            headerRow.createCell(6).setCellValue("Género");
            headerRow.createCell(7).setCellValue("Fecha de Nacimiento");
            headerRow.createCell(8).setCellValue("Estado");

            // Recorre el Vector de pilotos y añade cada uno a una nueva fila
            for (int i = 0; i < pilotos.size(); i++) {
                Row row = sheet.createRow(i + 1); // Crea una nueva fila en la hoja
                Piloto piloto = pilotos.get(i); // Obtiene el piloto correspondiente
                // Asigna los valores de cada piloto a las celdas
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

            workbook.write(fos); // Escribe los cambios en el archivo
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en caso de error
        }
    }
}
