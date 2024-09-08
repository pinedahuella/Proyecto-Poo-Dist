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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;


public class FRAMEGESTIONPILOTOS extends javax.swing.JFrame {
DefaultTableModel modeloPilotoListado = new DefaultTableModel();
    DefaultTableModel modeloPilotoEliminar = new DefaultTableModel();
    ArrayList<Piloto> listaPilotos = new ArrayList<>();
    
    
    public FRAMEGESTIONPILOTOS() {
        initComponents();
this.setTitle("GESTIÓN DE PILOTOS");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    String[] columnas = {"NOMBRE", "APELLIDO", "DPI", "LICENCIA", "CORREO", "TELEFONO", "GÉNERO", "AÑOS", "ESTADO"};
    for (String columna : columnas) {
        modeloPilotoListado.addColumn(columna);
        modeloPilotoEliminar.addColumn(columna);
    }

    tblRegistroPilotos3.setModel(modeloPilotoListado);
    tblRegistroPilotos1.setModel(modeloPilotoEliminar);
    tblRegistroPilotos2.setModel(modeloPilotoEliminar);
    tblRegistroPilotos5.setModel(modeloPilotoEliminar);
    tblRegistroPilotos4.setModel(modeloPilotoEliminar);
    
    cargarDatosDesdeExcel();
    
    tblRegistroPilotos3.setVisible(false);
    tblRegistroPilotos1.setVisible(false);
    tblRegistroPilotos2.setVisible(false);
    tblRegistroPilotos5.setVisible(false);
    tblRegistroPilotos4.setVisible(false);
}

public void cargarDatosDesdeExcel() {
    String filePath = "C:\\Users\\joseg\\Downloads\\PROYECTO CINDY\\Proyecto-Poo-Dist\\ProyectoPooDist\\PINEED.xlsx";
    try (FileInputStream file = new FileInputStream(new File(filePath))) {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        if (workbook.getNumberOfSheets() > 0) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Piloto piloto = new Piloto(
                    getCellValueAsString(row.getCell(0)),
                    getCellValueAsString(row.getCell(1)),
                    getCellValueAsString(row.getCell(2)),
                    getCellValueAsString(row.getCell(3)),
                    getCellValueAsString(row.getCell(4)),
                    getCellValueAsString(row.getCell(5)),
                    getCellValueAsString(row.getCell(6)),
                    getCellValueAsString(row.getCell(7)),
                    getCellValueAsString(row.getCell(8))
                );

                listaPilotos.add(piloto);

                modeloPilotoListado.addRow(new Object[]{
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getGeneroPiloto(),
                    piloto.getFechaDeNacimiento(),
                    piloto.getEstadoPiloto()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "El archivo no contiene hojas.");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos desde Excel: " + e.getMessage());
    }
}

private String getCellValueAsString(Cell cell) {
    if (cell == null) {
        return "";
    }
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:

            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(cell.getDateCellValue());
            } else {
                return String.valueOf((long) cell.getNumericCellValue());
            }
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case FORMULA:
            return cell.getCellFormula();
        default:
            return "";
    }
}

private void guardarDatosEnExcel() {
    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Pilotos");

        String[] encabezados = {"Nombre", "Apellido", "DPI", "Tipo Licencia", "Correo", "Teléfono", "Género", "Fecha de Nacimiento", "Estado"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < encabezados.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(encabezados[i]);
        }

        int rowNum = 1;
        for (Piloto piloto : listaPilotos) {
            Row row = sheet.createRow(rowNum++);
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

        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\joseg\\Downloads\\PROYECTO CINDY\\Proyecto-Poo-Dist\\ProyectoPooDist\\PINEED.xlsx")) {
            workbook.write(fos);
        }
        JOptionPane.showMessageDialog(this, "Datos guardados en Excel exitosamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar los datos en Excel: " + e.getMessage());
    }
}

    public void refrescarTablaListado() {
        modeloPilotoListado.setRowCount(0);
        for (Piloto piloto : listaPilotos) {
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
        tblRegistroPilotos3.setVisible(true);
    }


    public void refrescarTablaEliminarMostrar() {
        modeloPilotoEliminar.setRowCount(0);
        for (Piloto piloto : listaPilotos) {
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
            modeloPilotoEliminar.addRow(fila);
        }
        tblRegistroPilotos1.setVisible(true);
    }    
    
    
        public void refrescarTablaEliminarOcultar() {
        modeloPilotoEliminar.setRowCount(0);
        for (Piloto piloto : listaPilotos) {
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
            modeloPilotoEliminar.addRow(fila);
        }
        tblRegistroPilotos1.setVisible(false);
    }
        
        
     public void refrescarTablaModificarMostrar() {
    modeloPilotoEliminar.setRowCount(0);
    for (Piloto piloto : listaPilotos) {
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
        modeloPilotoEliminar.addRow(fila);
    }
    tblRegistroPilotos2.setVisible(true);
}

public void refrescarTablaModificarOcultar() {
    modeloPilotoEliminar.setRowCount(0);
    for (Piloto piloto : listaPilotos) {
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
        modeloPilotoEliminar.addRow(fila);
    }
    tblRegistroPilotos2.setVisible(false);
}

public void refrescarTablaEstadoMostrar() {
    modeloPilotoEliminar.setRowCount(0);
    for (Piloto piloto : listaPilotos) {
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
        modeloPilotoEliminar.addRow(fila);
    }
    tblRegistroPilotos4.setVisible(true);
}


public void refrescarTablaEstadoOcultar() {
    modeloPilotoEliminar.setRowCount(0);
    for (Piloto piloto : listaPilotos) {
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
        modeloPilotoEliminar.addRow(fila);
    }
    tblRegistroPilotos4.setVisible(false);
}
    
    
    
        private void limpiarCamposModificar() {
        txtNombrePilotoModificar.setText("");
        txtApellidoPilotoModificar.setText("");
        txtDpiPilotoModificar.setText("");
        txtCorreoPilotoModificar.setText("");
        txtNumeroTelefonoPilotoModificar.setText("");
        txtGeneroPilotoModificar.setSelectedIndex(0);
        txtTipoLicenciaModificar.setSelectedIndex(0);
        txtFechaDeNacimientoPilotoModificar.setDate(null);
        txtEstadoPilotoModificar.setSelectedIndex(0);
}
    

            private void limpiarCampos() {
        txtNombrePiloto.setText("");
        txtApellidoPiloto.setText("");
        txtNumeroDeDpiPiloto.setText("");
        txtTipoDeLicenciaPiloto.setSelectedIndex(0);
        txtCorreoElectronicoPiloto.setText("");
        txtNumeroTelefonicoPiloto.setText("");
        txtGeneroPiloto.setSelectedIndex(0);
        txtFechaDeNacimientoPiloto.setDate(null);
        txtEstadoPiloto.setSelectedIndex(0);
    }
            
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestañaEliminar = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtNombrePiloto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtApellidoPiloto = new javax.swing.JTextField();
        txtNumeroDeDpiPiloto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCorreoElectronicoPiloto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNumeroTelefonicoPiloto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTipoDeLicenciaPiloto = new javax.swing.JComboBox<>();
        txtGeneroPiloto = new javax.swing.JComboBox<>();
        btnAgregarInformacionPiloto = new javax.swing.JButton();
        txtFechaDeNacimientoPiloto = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtEstadoPiloto = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        bEliminarPiloto = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroPilotos1 = new javax.swing.JTable();
        MostrarListadoEliminar = new javax.swing.JButton();
        buscarPilotosEspecifico = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtNombrePilotoBuscar1 = new javax.swing.JTextField();
        txtNumeroDeDpiPilotoBuscar1 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtApellidoPilotoBuscar1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroPilotos2 = new javax.swing.JTable();
        modificarDatosDePiloto = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtNombrePilotoModificar = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtApellidoPilotoModificar = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtDpiPilotoModificar = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtTipoLicenciaModificar = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtGeneroPilotoModificar = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        txtNumeroTelefonoPilotoModificar = new javax.swing.JTextField();
        txtFechaDeNacimientoPilotoModificar = new com.toedter.calendar.JDateChooser();
        jLabel46 = new javax.swing.JLabel();
        txtEstadoPilotoModificar = new javax.swing.JComboBox<>();
        txtCorreoPilotoModificar = new javax.swing.JTextField();
        btnMostrarPilotosModificar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroPilotos5 = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        txtNombrePilotoBuscar3 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtApellidoPilotoBuscar3 = new javax.swing.JTextField();
        buscarPilotosEspecifico3 = new javax.swing.JButton();
        txtNumeroDeDpiPilotoBuscar3 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRegistroPilotos3 = new javax.swing.JTable();
        btnMostrarPilotosLista = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRegistroPilotos4 = new javax.swing.JTable();
        btnMostrarPilotosEstado = new javax.swing.JButton();
        estadoActualDePilotos = new javax.swing.JButton();
        txtEstadoPilotoActualizado = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pestañaEliminar.setPreferredSize(new java.awt.Dimension(1339, 702));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel1.setText("NOMBRE");

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel2.setText("APELLIDO");

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("NUMERO DE DPI");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setText("CORREO ELECTRONICO");

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setText("FECHA DE NACIMIENTO");

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setText("NUMERO TELEFONICO");

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setText("GENERO");

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        txtGeneroPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        btnAgregarInformacionPiloto.setBackground(new java.awt.Color(0, 153, 153));
        btnAgregarInformacionPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarInformacionPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarInformacionPiloto.setText("AGREGAR PILOTO AL SISTEMA");
        btnAgregarInformacionPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarInformacionPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarInformacionPilotoActionPerformed(evt);
            }
        });

        txtFechaDeNacimientoPiloto.setDateFormatString("dd/MM/yyyy");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("ESTADO DEL PILOTO");

        txtEstadoPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarInformacionPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCorreoElectronicoPiloto))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEstadoPiloto, 0, 240, Short.MAX_VALUE)
                                .addComponent(txtNumeroTelefonicoPiloto))))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtNombrePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(713, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreoElectronicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroTelefonicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addComponent(btnAgregarInformacionPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );

        pestañaEliminar.addTab("AGREGAR", jPanel2);

        jPanel10.setBackground(new java.awt.Color(0, 102, 102));

        jPanel11.setBackground(new java.awt.Color(0, 204, 204));
        jPanel11.setPreferredSize(new java.awt.Dimension(1339, 702));

        bEliminarPiloto.setBackground(new java.awt.Color(0, 153, 153));
        bEliminarPiloto.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        bEliminarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        bEliminarPiloto.setText("ELIMINAR PILOTO DEL SISTEMA");
        bEliminarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        bEliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarPilotoActionPerformed(evt);
            }
        });

        tblRegistroPilotos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblRegistroPilotos1);

        MostrarListadoEliminar.setBackground(new java.awt.Color(0, 153, 153));
        MostrarListadoEliminar.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        MostrarListadoEliminar.setForeground(new java.awt.Color(255, 255, 255));
        MostrarListadoEliminar.setText("MOSTRAR LISTADO DE PILOTOS GENERAL");
        MostrarListadoEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        MostrarListadoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarListadoEliminarActionPerformed(evt);
            }
        });

        buscarPilotosEspecifico.setBackground(new java.awt.Color(0, 153, 153));
        buscarPilotosEspecifico.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        buscarPilotosEspecifico.setForeground(new java.awt.Color(255, 255, 255));
        buscarPilotosEspecifico.setText("BUSCAR PILOTO EN ESPECIFICO");
        buscarPilotosEspecifico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        buscarPilotosEspecifico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPilotosEspecificoActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel32.setText("APELLIDO");

        jLabel36.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel36.setText("NOMBRE");

        jLabel40.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel40.setText("NUMERO DE DPI");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(MostrarListadoEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNumeroDeDpiPilotoBuscar1))
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtNombrePilotoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel11Layout.createSequentialGroup()
                                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtApellidoPilotoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(692, 692, 692))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buscarPilotosEspecifico, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1277, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePilotoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtApellidoPilotoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtNumeroDeDpiPilotoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(buscarPilotosEspecifico, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MostrarListadoEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1582, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 138, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 63, Short.MAX_VALUE)))
        );

        pestañaEliminar.addTab("ELIMINAR", jPanel3);

        jPanel14.setBackground(new java.awt.Color(0, 102, 102));

        jPanel15.setBackground(new java.awt.Color(0, 204, 204));

        tblRegistroPilotos2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblRegistroPilotos2);

        modificarDatosDePiloto.setBackground(new java.awt.Color(0, 153, 153));
        modificarDatosDePiloto.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        modificarDatosDePiloto.setForeground(new java.awt.Color(255, 255, 255));
        modificarDatosDePiloto.setText("ACTUALIZAR DATOS DEL PILOTO");
        modificarDatosDePiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        modificarDatosDePiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarDatosDePilotoActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel27.setText("NOMBRE");

        jLabel28.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel28.setText("APELLIDO");

        jLabel29.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel29.setText("NUMERO DE DPI");

        jLabel37.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel37.setText("TIPO DE LICENCIA");

        txtTipoLicenciaModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel38.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel38.setText("CORREO ELECTRONICO");

        jLabel41.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel41.setText("FECHA DE NACIMIENTO");

        jLabel42.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel42.setText("GENERO");

        txtGeneroPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel43.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel43.setText("NUMERO TELEFONICO");

        txtFechaDeNacimientoPilotoModificar.setDateFormatString("dd/MM/yyyy");

        jLabel46.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel46.setText("ESTADO DEL PILOTO");

        txtEstadoPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        btnMostrarPilotosModificar.setBackground(new java.awt.Color(0, 153, 153));
        btnMostrarPilotosModificar.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        btnMostrarPilotosModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPilotosModificar.setText("MOSTRAR LISTADO DE PILOTOS ACTUAL");
        btnMostrarPilotosModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPilotosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotosModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modificarDatosDePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnMostrarPilotosModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel15Layout.createSequentialGroup()
                                                .addComponent(jLabel29)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDpiPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel15Layout.createSequentialGroup()
                                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtNombrePilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(135, 135, 135))
                                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtNumeroTelefonoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel15Layout.createSequentialGroup()
                                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTipoLicenciaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(646, 646, 646)
                                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtEstadoPilotoModificar, 0, 205, Short.MAX_VALUE)
                                        .addGap(29, 29, 29)))
                                .addGap(79, 79, 79)))
                        .addGap(35, 35, 35))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btnMostrarPilotosModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel41))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDpiPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel43)
                            .addComponent(txtNumeroTelefonoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoLicenciaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel46)
                    .addComponent(txtEstadoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modificarDatosDePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(txtCorreoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1582, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pestañaEliminar.addTab("MODIFICAR", jPanel5);

        jPanel16.setBackground(new java.awt.Color(0, 102, 102));

        jPanel17.setBackground(new java.awt.Color(0, 204, 204));

        tblRegistroPilotos5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroPilotos5);

        jLabel33.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel33.setText("NOMBRE");

        jLabel34.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel34.setText("APELLIDO");

        buscarPilotosEspecifico3.setBackground(new java.awt.Color(0, 153, 153));
        buscarPilotosEspecifico3.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        buscarPilotosEspecifico3.setForeground(new java.awt.Color(255, 255, 255));
        buscarPilotosEspecifico3.setText("MOSTRAR PILOTO EN ESPECIFICO");
        buscarPilotosEspecifico3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        buscarPilotosEspecifico3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPilotosEspecifico3ActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel45.setText("NUMERO DE DPI");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarPilotosEspecifico3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombrePilotoBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNumeroDeDpiPilotoBuscar3))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtApellidoPilotoBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePilotoBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(30, 30, 30)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtApellidoPilotoBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtNumeroDeDpiPilotoBuscar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addComponent(buscarPilotosEspecifico3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1582, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 63, Short.MAX_VALUE)))
        );

        pestañaEliminar.addTab("MOSTRAR", jPanel6);

        jPanel18.setBackground(new java.awt.Color(0, 102, 102));

        jPanel19.setBackground(new java.awt.Color(0, 204, 204));

        tblRegistroPilotos3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblRegistroPilotos3);

        btnMostrarPilotosLista.setBackground(new java.awt.Color(0, 153, 153));
        btnMostrarPilotosLista.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        btnMostrarPilotosLista.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPilotosLista.setText("MOSTRAR LISTADO ACTUALIZADO");
        btnMostrarPilotosLista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPilotosLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotosListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMostrarPilotosLista, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(btnMostrarPilotosLista, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );

        pestañaEliminar.addTab("LISTA", jPanel18);

        jPanel20.setBackground(new java.awt.Color(0, 102, 102));

        jPanel21.setBackground(new java.awt.Color(0, 204, 204));

        tblRegistroPilotos4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblRegistroPilotos4);

        btnMostrarPilotosEstado.setBackground(new java.awt.Color(0, 153, 153));
        btnMostrarPilotosEstado.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        btnMostrarPilotosEstado.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPilotosEstado.setText("ESTADO ACTUALIZADO DEL PILOTO");
        btnMostrarPilotosEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPilotosEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotosEstadoActionPerformed(evt);
            }
        });

        estadoActualDePilotos.setBackground(new java.awt.Color(0, 153, 153));
        estadoActualDePilotos.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        estadoActualDePilotos.setForeground(new java.awt.Color(255, 255, 255));
        estadoActualDePilotos.setText("ESTADO ACTUAL DE PILOTOS");
        estadoActualDePilotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        estadoActualDePilotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoActualDePilotosActionPerformed(evt);
            }
        });

        txtEstadoPilotoActualizado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        jLabel48.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel48.setText("ESTADO DEL PILOTO");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEstadoPilotoActualizado, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1312, Short.MAX_VALUE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMostrarPilotosEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(estadoActualDePilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(45, 45, 45))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(estadoActualDePilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtEstadoPilotoActualizado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(btnMostrarPilotosEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1582, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pestañaEliminar.addTab("ESTADO", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestañaEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestañaEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarInformacionPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarInformacionPilotoActionPerformed
        try {
            String nombrePiloto = txtNombrePiloto.getText().trim();
            String apellidoPiloto = txtApellidoPiloto.getText().trim();
            String numeroDeDpi = txtNumeroDeDpiPiloto.getText().trim();
            String tipoLicencia = txtTipoDeLicenciaPiloto.getSelectedItem().toString().trim();
            String correoElectronicoPiloto = txtCorreoElectronicoPiloto.getText().trim();
            String numeroTelefonico = txtNumeroTelefonicoPiloto.getText().trim();
            String generoPiloto = txtGeneroPiloto.getSelectedItem().toString().trim();

            JDateChooser dateChooser = txtFechaDeNacimientoPiloto;
            Date fechaNacimientoDate = dateChooser.getDate();

            if (fechaNacimientoDate == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String fechaDeNacimiento = sdf.format(fechaNacimientoDate);
            String estadoPiloto = txtEstadoPiloto.getSelectedItem().toString().trim();

            if (nombrePiloto.isEmpty() || apellidoPiloto.isEmpty() || numeroDeDpi.isEmpty() || tipoLicencia.isEmpty() ||
                correoElectronicoPiloto.isEmpty() || generoPiloto.isEmpty() || numeroTelefonico.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
                return;
            }

            if (!numeroDeDpi.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, llena correctamente el campo de DPI con números.");
                return;
            }

            if (!numeroTelefonico.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Por favor, llena correctamente el campo de número telefónico con números.");
                return;
            }

            for (Piloto pilotoExistente : listaPilotos) {
                if (pilotoExistente.getNumeroDeDpi().equals(numeroDeDpi)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
                    return;
                }
            }

            Piloto piloto = new Piloto(nombrePiloto, apellidoPiloto, numeroDeDpi, tipoLicencia, correoElectronicoPiloto,
                numeroTelefonico, generoPiloto, fechaDeNacimiento, estadoPiloto);
            listaPilotos.add(piloto);
            guardarDatosEnExcel();

            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Piloto agregado exitosamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AGREGAR PILOTO: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAgregarInformacionPilotoActionPerformed

    private void bEliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarPilotoActionPerformed
        int filaSeleccionada1 = tblRegistroPilotos3.getSelectedRow();
        int filaSeleccionada2 = tblRegistroPilotos1.getSelectedRow();

        if (filaSeleccionada1 >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas borrar este piloto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listaPilotos.remove(filaSeleccionada1);
                guardarDatosEnExcel();
                refrescarTablaListado();
            }
        }
        else if (filaSeleccionada2 >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas borrar este piloto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listaPilotos.remove(filaSeleccionada2);
                guardarDatosEnExcel();
                refrescarTablaEliminarOcultar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para eliminar.");
        }
    }//GEN-LAST:event_bEliminarPilotoActionPerformed

    private void MostrarListadoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarListadoEliminarActionPerformed
        refrescarTablaEliminarMostrar();
    }//GEN-LAST:event_MostrarListadoEliminarActionPerformed

    private void buscarPilotosEspecificoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotosEspecificoActionPerformed
        if (txtNombrePilotoBuscar1.getText().trim().isEmpty() ||
            txtApellidoPilotoBuscar1.getText().trim().isEmpty() ||
            txtNumeroDeDpiPilotoBuscar1.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
            return;
        }

        String nombreBuscado1 = txtNombrePilotoBuscar1.getText().trim();
        String apellidoBuscado1 = txtApellidoPilotoBuscar1.getText().trim();
        String dpiBuscado1 = txtNumeroDeDpiPilotoBuscar1.getText().trim();

        modeloPilotoEliminar.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Piloto piloto : listaPilotos) {
            boolean coincide = true;

            if (!nombreBuscado1.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado1)) {
                coincide = false;
            }

            if (!apellidoBuscado1.isEmpty() && !piloto.getApellidoPiloto().equalsIgnoreCase(apellidoBuscado1)) {
                coincide = false;
            }

            if (!dpiBuscado1.isEmpty() && !piloto.getNumeroDeDpi().equals(dpiBuscado1)) {
                coincide = false;
            }

            if (coincide) {
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
                modeloPilotoEliminar.addRow(fila);
                hayCoincidencias = true;
            }
        }

        if (hayCoincidencias) {
            tblRegistroPilotos1.setVisible(true);
        } else {
            tblRegistroPilotos1.setVisible(false);
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
        }

        txtNombrePilotoBuscar1.setText("");
        txtApellidoPilotoBuscar1.setText("");
        txtNumeroDeDpiPilotoBuscar1.setText("");
    }//GEN-LAST:event_buscarPilotosEspecificoActionPerformed

    private void modificarDatosDePilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarDatosDePilotoActionPerformed
        int filaSeleccionada = tblRegistroPilotos2.getSelectedRow();

        if (filaSeleccionada >= 0) {
            // Obtener el piloto correspondiente de la lista utilizando el índice de la fila seleccionada
            Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);

            // Obtener los nuevos datos del formulario
            String nuevoNombre = txtNombrePilotoModificar.getText().trim();
            String nuevoApellido = txtApellidoPilotoModificar.getText().trim();
            String nuevoDPI = txtDpiPilotoModificar.getText().trim();
            String nuevoTipoLicencia = txtTipoLicenciaModificar.getSelectedItem().toString().trim();
            String nuevoCorreo = txtCorreoPilotoModificar.getText().trim();
            String nuevoTelefono = txtNumeroTelefonoPilotoModificar.getText().trim();
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

            if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoDPI.isEmpty() ||
                nuevoTipoLicencia.isEmpty() || nuevoCorreo.isEmpty() || nuevoTelefono.isEmpty() ||
                nuevoGenero.isEmpty() || nuevoEstado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos antes de continuar.");
                return;
            }

            if (!nuevoDPI.matches("\\d+") || !nuevoTelefono.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "El DPI y el número telefónico deben contener solo números.");
                return;
            }

            pilotoSeleccionado.setNombrePiloto(nuevoNombre);
            pilotoSeleccionado.setApellidoPiloto(nuevoApellido);
            pilotoSeleccionado.setNumeroDeDpi(nuevoDPI);
            pilotoSeleccionado.setTipoLicencia(nuevoTipoLicencia);
            pilotoSeleccionado.setCorreoElectronicoPiloto(nuevoCorreo);
            pilotoSeleccionado.setNumeroTelefonicoPiloto(nuevoTelefono);
            pilotoSeleccionado.setGeneroPiloto(nuevoGenero);
            pilotoSeleccionado.setFechaDeNacimiento(fechaDeNacimiento);
            pilotoSeleccionado.setEstadoPiloto(nuevoEstado);

            refrescarTablaModificarOcultar();
            guardarDatosEnExcel();

            JOptionPane.showMessageDialog(this, "Datos del piloto modificados exitosamente.");

            txtNombrePilotoModificar.setText("");
            txtApellidoPilotoModificar.setText("");
            txtDpiPilotoModificar.setText("");
            txtTipoLicenciaModificar.setSelectedIndex(0);
            txtCorreoPilotoModificar.setText("");
            txtNumeroTelefonoPilotoModificar.setText("");
            txtGeneroPilotoModificar.setSelectedIndex(0);
            txtFechaDeNacimientoPilotoModificar.setDate(null);
            txtEstadoPilotoModificar.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para modificar.");
        }
    }//GEN-LAST:event_modificarDatosDePilotoActionPerformed

    private void btnMostrarPilotosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotosModificarActionPerformed
        refrescarTablaModificarMostrar();
    }//GEN-LAST:event_btnMostrarPilotosModificarActionPerformed

    private void buscarPilotosEspecifico3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotosEspecifico3ActionPerformed
        if (txtNombrePilotoBuscar3.getText().trim().isEmpty() ||
            txtApellidoPilotoBuscar3.getText().trim().isEmpty() ||
            txtNumeroDeDpiPilotoBuscar3.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
            return;
        }

        String nombreBuscado3 = txtNombrePilotoBuscar3.getText().trim();
        String apellidoBuscado3 = txtApellidoPilotoBuscar3.getText().trim();
        String dpiBuscado3 = txtNumeroDeDpiPilotoBuscar3.getText().trim();

        modeloPilotoEliminar.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Piloto piloto : listaPilotos) {
            boolean coincide = true;

            if (!nombreBuscado3.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado3)) {
                coincide = false;
            }

            if (!apellidoBuscado3.isEmpty() && !piloto.getApellidoPiloto().equalsIgnoreCase(apellidoBuscado3)) {
                coincide = false;
            }

            if (!dpiBuscado3.isEmpty() && !piloto.getNumeroDeDpi().equals(dpiBuscado3)) {
                coincide = false;
            }

            if (coincide) {
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
                modeloPilotoEliminar.addRow(fila);
                hayCoincidencias = true;
            }
        }

        if (hayCoincidencias) {
            tblRegistroPilotos5.setVisible(true);
        } else {
            tblRegistroPilotos5.setVisible(false);
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
        }

        txtNombrePilotoBuscar3.setText("");
        txtApellidoPilotoBuscar3.setText("");
        txtNumeroDeDpiPilotoBuscar3.setText("");
    }//GEN-LAST:event_buscarPilotosEspecifico3ActionPerformed

    private void btnMostrarPilotosListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotosListaActionPerformed
        refrescarTablaListado();
    }//GEN-LAST:event_btnMostrarPilotosListaActionPerformed

    private void btnMostrarPilotosEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotosEstadoActionPerformed
        int filaSeleccionada = tblRegistroPilotos4.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto de la tabla.");
            return;
        }

        String nombre = tblRegistroPilotos4.getValueAt(filaSeleccionada, 0).toString();
        String apellido = tblRegistroPilotos4.getValueAt(filaSeleccionada, 1).toString();
        String dpi = tblRegistroPilotos4.getValueAt(filaSeleccionada, 2).toString();

        String nuevoEstado = txtEstadoPilotoActualizado.getSelectedItem().toString().trim();

        Piloto pilotoEncontrado = null;
        for (Piloto piloto : listaPilotos) {
            if (piloto.getNombrePiloto().equalsIgnoreCase(nombre) &&
                piloto.getApellidoPiloto().equalsIgnoreCase(apellido) &&
                piloto.getNumeroDeDpi().equals(dpi)) {
                pilotoEncontrado = piloto;
                break;
            }
        }

        if (pilotoEncontrado != null) {
            pilotoEncontrado.setEstadoPiloto(nuevoEstado);

            refrescarTablaEstadoOcultar();
            guardarDatosEnExcel();

            JOptionPane.showMessageDialog(this, "Estado del piloto modificado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un piloto con esos datos.");
        }
    }//GEN-LAST:event_btnMostrarPilotosEstadoActionPerformed

    private void estadoActualDePilotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoActualDePilotosActionPerformed
        refrescarTablaEstadoMostrar();
    }//GEN-LAST:event_estadoActualDePilotosActionPerformed

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
            java.util.logging.Logger.getLogger(FRAMEGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRAMEGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRAMEGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRAMEGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FRAMEGESTIONPILOTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MostrarListadoEliminar;
    private javax.swing.JButton bEliminarPiloto;
    private javax.swing.JButton btnAgregarInformacionPiloto;
    private javax.swing.JButton btnMostrarPilotosEstado;
    private javax.swing.JButton btnMostrarPilotosLista;
    private javax.swing.JButton btnMostrarPilotosModificar;
    private javax.swing.JButton buscarPilotosEspecifico;
    private javax.swing.JButton buscarPilotosEspecifico3;
    private javax.swing.JButton estadoActualDePilotos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton modificarDatosDePiloto;
    private javax.swing.JTabbedPane pestañaEliminar;
    private javax.swing.JTable tblRegistroPilotos1;
    private javax.swing.JTable tblRegistroPilotos2;
    private javax.swing.JTable tblRegistroPilotos3;
    private javax.swing.JTable tblRegistroPilotos4;
    private javax.swing.JTable tblRegistroPilotos5;
    private javax.swing.JTextField txtApellidoPiloto;
    private javax.swing.JTextField txtApellidoPilotoBuscar1;
    private javax.swing.JTextField txtApellidoPilotoBuscar3;
    private javax.swing.JTextField txtApellidoPilotoModificar;
    private javax.swing.JTextField txtCorreoElectronicoPiloto;
    private javax.swing.JTextField txtCorreoPilotoModificar;
    private javax.swing.JTextField txtDpiPilotoModificar;
    private javax.swing.JComboBox<String> txtEstadoPiloto;
    private javax.swing.JComboBox<String> txtEstadoPilotoActualizado;
    private javax.swing.JComboBox<String> txtEstadoPilotoModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPiloto;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPilotoModificar;
    private javax.swing.JComboBox<String> txtGeneroPiloto;
    private javax.swing.JComboBox<String> txtGeneroPilotoModificar;
    private javax.swing.JTextField txtNombrePiloto;
    private javax.swing.JTextField txtNombrePilotoBuscar1;
    private javax.swing.JTextField txtNombrePilotoBuscar3;
    private javax.swing.JTextField txtNombrePilotoModificar;
    private javax.swing.JTextField txtNumeroDeDpiPiloto;
    private javax.swing.JTextField txtNumeroDeDpiPilotoBuscar1;
    private javax.swing.JTextField txtNumeroDeDpiPilotoBuscar3;
    private javax.swing.JTextField txtNumeroTelefonicoPiloto;
    private javax.swing.JTextField txtNumeroTelefonoPilotoModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPiloto;
    private javax.swing.JComboBox<String> txtTipoLicenciaModificar;
    // End of variables declaration//GEN-END:variables
}
