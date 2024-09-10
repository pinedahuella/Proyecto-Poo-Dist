package PaquetePrincipal;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import PaquetePrincipal.GESTIONPILOTOS.Piloto;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;



public class MODIFICARGESTIONPILOTOS extends javax.swing.JFrame {

   DefaultTableModel modeloPilotoListado = new DefaultTableModel();
    ArrayList<Piloto> listaPilotos = new ArrayList<>();
    
    

public MODIFICARGESTIONPILOTOS() {
    initComponents();
    this.setTitle("GESTIÓN DE PILOTOS");

    String[] columnas = {"NOMBRE", "APELLIDO", "DPI", "LICENCIA", "CORREO", "TELEFONO", "GÉNERO", "AÑOS", "ESTADO"};
    for (String columna : columnas) {
        modeloPilotoListado.addColumn(columna);
    }

    tblRegistroPilotos.setModel(modeloPilotoListado);

    cargarDatosDesdeExcel();  // Cargar datos desde Excel
    refrescarTabla();          // Refrescar la tabla para mostrar los datos
    
    tblRegistroPilotos.setVisible(false); // Asegúrate de que la tabla sea visible
}


public void cargarDatosDesdeExcel() {
    String filePath = "C:\\Users\\joseg\\Downloads\\PROYECTO CINDY\\Proyecto-Poo-Dist\\ProyectoPooDist\\PINEED.xlsx";
    try (FileInputStream file = new FileInputStream(new File(filePath))) {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        listaPilotos.clear(); // Limpiar la lista antes de cargar nuevos datos

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Saltar la primera fila (encabezados)

            GESTIONPILOTOS.Piloto piloto = new GESTIONPILOTOS.Piloto(
                getCellValueAsString(row.getCell(0)), // Nombre
                getCellValueAsString(row.getCell(1)), // Apellido
                getCellValueAsString(row.getCell(2)), // **DPI** ahora es String
                getCellValueAsString(row.getCell(3)), // Tipo de licencia
                getCellValueAsString(row.getCell(4)), // Correo electrónico
                getCellValueAsInt(row.getCell(5)),    // Número telefónico
                getCellValueAsString(row.getCell(6)), // Género
                getCellValueAsString(row.getCell(7)), // Fecha de nacimiento
                getCellValueAsString(row.getCell(8))  // Estado
            );

            listaPilotos.add(piloto); // Añadir piloto a la lista
        }

        workbook.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    // Refrescar la tabla después de cargar datos
    refrescarTabla();
}
 private String getCellValueAsString(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue()); // Convierte a string
            }
        }
        return ""; // Devuelve una cadena vacía si la celda es nula
    }

    private int getCellValueAsInt(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return (int) Math.round(cell.getNumericCellValue());
        } else {
            System.err.println("Error: La celda no es válida o está vacía.");
            return 0; // Maneja el error como prefieras
        }
    }

   private void guardarDatosEnExcel() {
    String filePath = "C:\\Users\\joseg\\Downloads\\PROYECTO CINDY\\Proyecto-Poo-Dist\\ProyectoPooDist\\PINEED.xlsx";
    try {
        FileInputStream fileIn = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = workbook.getSheet("Pilotos");

        if (sheet == null) {
            sheet = workbook.createSheet("Pilotos");
            // Crear encabezados
            Row header = sheet.createRow(0);
            String[] columnas = {"NOMBRE", "APELLIDO", "DPI", "LICENCIA", "CORREO", "TELÉFONO", "GÉNERO", "AÑOS", "ESTADO"};
            for (int i = 0; i < columnas.length; i++) {
                header.createCell(i).setCellValue(columnas[i]);
            }
        }

        // Elimina la hoja anterior y crea una nueva para evitar duplicados
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > 0) {
            for (int i = 1; i <= lastRowNum; i++) {
                sheet.removeRow(sheet.getRow(i));
            }
        }

        // Guardar los datos de los pilotos
        int rowIndex = 1; // Empieza después del encabezado
        for (GESTIONPILOTOS.Piloto piloto : listaPilotos) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(piloto.getNombrePiloto());
            row.createCell(1).setCellValue(piloto.getApellidoPiloto());
            row.createCell(2).setCellValue(piloto.getNumeroDeDpi()); // **DPI** ahora es String
            row.createCell(3).setCellValue(piloto.getTipoLicencia());
            row.createCell(4).setCellValue(piloto.getCorreoElectronicoPiloto());
            row.createCell(5).setCellValue(piloto.getNumeroTelefonicoPiloto());
            row.createCell(6).setCellValue(piloto.getGeneroPiloto());
            row.createCell(7).setCellValue(piloto.getFechaDeNacimiento());
            row.createCell(8).setCellValue(piloto.getEstadoPiloto());
        }

        // Guardar archivo Excel
        try (FileOutputStream fileOut = new FileOutputStream(new File(filePath))) {
            workbook.write(fileOut);
        }

        workbook.close();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar datos en Excel: " + e.getMessage());
    }
}
   
   
   
   public void refrescarTabla() {
    modeloPilotoListado.setRowCount(0); // Limpiar la tabla actual
    for (GESTIONPILOTOS.Piloto piloto : listaPilotos) {
        Object[] fila = {
            piloto.getNombrePiloto(),
            piloto.getApellidoPiloto(),
            piloto.getNumeroDeDpi(),
            piloto.getTipoLicencia(),
            piloto.getCorreoElectronicoPiloto(),
            piloto.getNumeroTelefonicoPiloto(),
            piloto.getGeneroPiloto(),
            piloto.getFechaDeNacimiento(),
            piloto.getEstadoPiloto()
        };
        modeloPilotoListado.addRow(fila); // Añadir cada fila al modelo
    }
    tblRegistroPilotos.setVisible(true); // Hacer visible la tabla
}
    
    
        public void refrescarTablaOcultar() {
        modeloPilotoListado.setRowCount(0);
        for (GESTIONPILOTOS.Piloto piloto : listaPilotos) {
            Object[] fila = {
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento(),
                piloto.getEstadoPiloto()
            };
            modeloPilotoListado.addRow(fila);
        }
        tblRegistroPilotos.setVisible(false);
    }
        
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoPilotoModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaPilotoModificar = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoPilotoModificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoPilotoModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroPilotoModificar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoPilotoModificar = new javax.swing.JComboBox<>();
        btnMoficarPilotSistema = new javax.swing.JButton();
        txtNumeroTelefonicoPilotoModificar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApellidoPilotoBuscar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoBuscar = new javax.swing.JTextField();
        btnBuscarPilotoSistema = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnModificarPiloto1 = new javax.swing.JButton();
        btnAgregarPiloto1 = new javax.swing.JButton();
        btnEliminarPiloto1 = new javax.swing.JButton();
        btnMostrarPiloto1 = new javax.swing.JButton();
        btnListaPiloto1 = new javax.swing.JButton();
        btnInicioPiloto1 = new javax.swing.JButton();
        btnSalirPiloto1 = new javax.swing.JButton();
        btnEstadoPiloto3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" MODIFICAR PILOTO EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoPilotoModificar.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        btnMoficarPilotSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnMoficarPilotSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoficarPilotSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnMoficarPilotSistema.setText("MODIFICAR");
        btnMoficarPilotSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMoficarPilotSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoficarPilotSistemaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("APELLIDO");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("NUMERO DE DPI");

        btnBuscarPilotoSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarPilotoSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPilotoSistema.setText("BUSCAR");
        btnBuscarPilotoSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPilotoSistemaActionPerformed(evt);
            }
        });

        tblRegistroPilotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroPilotos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCorreoElectronicoPilotoModificar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNumeroDeDpiPilotoModificar))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNombrePilotoModificar)
                                        .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTipoDeLicenciaPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtEstadoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtNumeroTelefonicoPilotoModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMoficarPilotSistema, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroDeDpiPilotoBuscar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombrePilotoBuscar)
                                    .addComponent(txtApellidoPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(btnBuscarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtNumeroTelefonicoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstadoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMoficarPilotSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeroDeDpiPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCorreoElectronicoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoDeLicenciaPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(129, 129, 129)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(226, Short.MAX_VALUE)))
        );

        btnModificarPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPiloto1.setText("MODIFICAR");
        btnModificarPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPiloto1ActionPerformed(evt);
            }
        });

        btnAgregarPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPiloto1.setText("AGREGAR");
        btnAgregarPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPiloto1ActionPerformed(evt);
            }
        });

        btnEliminarPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPiloto1.setText("ELIMINAR");
        btnEliminarPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPiloto1ActionPerformed(evt);
            }
        });

        btnMostrarPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPiloto1.setText("MOSTRAR");
        btnMostrarPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPiloto1ActionPerformed(evt);
            }
        });

        btnListaPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnListaPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnListaPiloto1.setText("LISTA");
        btnListaPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPiloto1ActionPerformed(evt);
            }
        });

        btnInicioPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioPiloto1.setText("INICIO");
        btnInicioPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioPiloto1ActionPerformed(evt);
            }
        });

        btnSalirPiloto1.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirPiloto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirPiloto1.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirPiloto1.setText("SALIR");
        btnSalirPiloto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirPiloto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPiloto1ActionPerformed(evt);
            }
        });

        btnEstadoPiloto3.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoPiloto3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoPiloto3.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoPiloto3.setText("ESTADO");
        btnEstadoPiloto3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoPiloto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoPiloto3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoPiloto3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoPiloto3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1163, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoficarPilotSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoficarPilotSistemaActionPerformed
    int filaSeleccionada = tblRegistroPilotos.getSelectedRow();

    if (filaSeleccionada < 0) {
        // Si no hay fila seleccionada, mostrar mensaje
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para modificar.");
        return; // Salir del método
    }

    // Obtener el piloto correspondiente de la lista utilizando el índice de la fila seleccionada
    Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);

    // Obtener los nuevos datos del formulario
    String nuevoNombre = txtNombrePilotoModificar.getText().trim();
    String nuevoApellido = txtApellidoPilotoModificar.getText().trim();
    String nuevoTipoLicencia = txtTipoDeLicenciaPilotoModificar.getSelectedItem().toString().trim();
    String nuevoCorreo = txtCorreoElectronicoPilotoModificar.getText().trim();
    String nuevoGenero = txtGeneroPilotoModificar.getSelectedItem().toString().trim();
    JDateChooser dateChooser = txtFechaDeNacimientoPilotoModificar;
    Date fechaNacimientoDate = dateChooser.getDate();

    if (fechaNacimientoDate == null) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    String fechaDeNacimiento = sdf.format(fechaNacimientoDate);
    String nuevoEstado = txtEstadoPilotoModificar.getSelectedItem().toString().trim();

    // Validación de campos
    if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || 
        nuevoTipoLicencia.isEmpty() || nuevoCorreo.isEmpty() ||
        nuevoGenero.isEmpty() || nuevoEstado.isEmpty() ||
        txtNumeroDeDpiPilotoModificar.getText().trim().isEmpty() || 
        txtNumeroTelefonicoPilotoModificar.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos antes de continuar.");
        return;
    }

    // Validar DPI como String y número telefónico como int
    String nuevoDPI = txtNumeroDeDpiPilotoModificar.getText().trim();
    int nuevoTelefono;
    
    try {
        nuevoTelefono = Integer.parseInt(txtNumeroTelefonicoPilotoModificar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El número telefónico debe ser un valor numérico.");
        return;
    }

    // Verificar si el nuevo DPI ya existe (exceptuando el piloto que se está modificando)
    for (Piloto pilotoExistente : listaPilotos) {
        if (pilotoExistente.getNumeroDeDpi().equals(nuevoDPI) && pilotoExistente != pilotoSeleccionado) {
            JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
            return;
        }
    }

    // Actualizar los datos del piloto
    pilotoSeleccionado.setNombrePiloto(nuevoNombre);
    pilotoSeleccionado.setApellidoPiloto(nuevoApellido);
    pilotoSeleccionado.setNumeroDeDpi(nuevoDPI); // Se mantiene como String
    pilotoSeleccionado.setTipoLicencia(nuevoTipoLicencia);
    pilotoSeleccionado.setCorreoElectronicoPiloto(nuevoCorreo);
    pilotoSeleccionado.setNumeroTelefonicoPiloto(nuevoTelefono); // Se mantiene como int
    pilotoSeleccionado.setGeneroPiloto(nuevoGenero);
    pilotoSeleccionado.setFechaDeNacimiento(fechaDeNacimiento);
    pilotoSeleccionado.setEstadoPiloto(nuevoEstado);

    // Guardar los cambios en Excel
    guardarDatosEnExcel();

    // Refrescar y ocultar la tabla
    refrescarTablaOcultar();

    JOptionPane.showMessageDialog(this, "Datos del piloto modificados exitosamente.");

    // Limpiar campos de entrada
    txtNombrePilotoModificar.setText("");
    txtApellidoPilotoModificar.setText("");
    txtNumeroDeDpiPilotoModificar.setText("");
    txtTipoDeLicenciaPilotoModificar.setSelectedIndex(0);
    txtCorreoElectronicoPilotoModificar.setText("");
    txtNumeroTelefonicoPilotoModificar.setText("");
    txtGeneroPilotoModificar.setSelectedIndex(0);
    txtFechaDeNacimientoPilotoModificar.setDate(null);
    txtEstadoPilotoModificar.setSelectedIndex(0);
    }//GEN-LAST:event_btnMoficarPilotSistemaActionPerformed

    private void btnBuscarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPilotoSistemaActionPerformed
   if (txtNombrePilotoBuscar.getText().trim().isEmpty() ||
        txtApellidoPilotoBuscar.getText().trim().isEmpty() ||
        txtNumeroDeDpiPilotoBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }

    // Obtener los valores ingresados
    String nombreBuscado = txtNombrePilotoBuscar.getText().trim();
    String apellidoBuscado = txtApellidoPilotoBuscar.getText().trim();
    String dpiBuscado = txtNumeroDeDpiPilotoBuscar.getText().trim(); // Cambiado a String

    // Reiniciar el modelo de la tabla
    modeloPilotoListado.setRowCount(0);
    boolean hayCoincidencias = false;

    // Buscar coincidencias en la lista de pilotos
    for (GESTIONPILOTOS.Piloto piloto : listaPilotos) {
        boolean coincide = true;

        if (!nombreBuscado.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado)) {
            coincide = false;
        }

        if (!apellidoBuscado.isEmpty() && !piloto.getApellidoPiloto().equalsIgnoreCase(apellidoBuscado)) {
            coincide = false;
        }

        if (!dpiBuscado.isEmpty() && !piloto.getNumeroDeDpi().equals(dpiBuscado)) { // Comparar como String
            coincide = false;
        }

        if (coincide) {
            // Si hay coincidencias, agregarlas al modelo de la tabla
            Object[] fila = {
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento(),
                piloto.getEstadoPiloto()
            };
            modeloPilotoListado.addRow(fila);
            hayCoincidencias = true;
        }
    }

    // Mostrar u ocultar la tabla según si hay coincidencias
    if (hayCoincidencias) {
        tblRegistroPilotos.setVisible(true);
    } else {
        tblRegistroPilotos.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }

    // Limpiar los campos de búsqueda
    txtNombrePilotoBuscar.setText("");
    txtApellidoPilotoBuscar.setText("");
    txtNumeroDeDpiPilotoBuscar.setText("");
    }//GEN-LAST:event_btnBuscarPilotoSistemaActionPerformed

    private void btnModificarPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPiloto1ActionPerformed
        MODIFICARGESTIONPILOTOS abrir = new   MODIFICARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarPiloto1ActionPerformed

    private void btnAgregarPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPiloto1ActionPerformed
        AGREGARGESTIONPILOTOS abrir = new  AGREGARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarPiloto1ActionPerformed

    private void btnEliminarPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPiloto1ActionPerformed
        ELIMINARGESTIONPILOTOS abrir = new  ELIMINARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarPiloto1ActionPerformed

    private void btnMostrarPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPiloto1ActionPerformed
        MOSTRARGESTIONPILOTOS abrir = new   MOSTRARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarPiloto1ActionPerformed

    private void btnListaPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaPiloto1ActionPerformed
        LISTAGESTIONPILOTOS abrir = new   LISTAGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaPiloto1ActionPerformed

    private void btnInicioPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioPiloto1ActionPerformed
        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioPiloto1ActionPerformed

    private void btnSalirPiloto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPiloto1ActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirPiloto1ActionPerformed

    private void btnEstadoPiloto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoPiloto3ActionPerformed
        ESTADOGESTIONPILOTOS abrir = new ESTADOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoPiloto3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MODIFICARGESTIONPILOTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPiloto1;
    private javax.swing.JButton btnBuscarPilotoSistema;
    private javax.swing.JButton btnEliminarPiloto1;
    private javax.swing.JButton btnEstadoPiloto3;
    private javax.swing.JButton btnInicioPiloto1;
    private javax.swing.JButton btnListaPiloto1;
    private javax.swing.JButton btnModificarPiloto1;
    private javax.swing.JButton btnMoficarPilotSistema;
    private javax.swing.JButton btnMostrarPiloto1;
    private javax.swing.JButton btnSalirPiloto1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JTextField txtApellidoPilotoBuscar;
    private javax.swing.JTextField txtApellidoPilotoModificar;
    private javax.swing.JTextField txtCorreoElectronicoPilotoModificar;
    private javax.swing.JComboBox<String> txtEstadoPilotoModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPilotoModificar;
    private javax.swing.JComboBox<String> txtGeneroPilotoModificar;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    private javax.swing.JTextField txtNombrePilotoModificar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoBuscar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoModificar;
    private javax.swing.JTextField txtNumeroTelefonicoPilotoModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPilotoModificar;
    // End of variables declaration//GEN-END:variables
}
