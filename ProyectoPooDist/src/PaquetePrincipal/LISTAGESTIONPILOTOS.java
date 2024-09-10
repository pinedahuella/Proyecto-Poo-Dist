
package PaquetePrincipal;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import PaquetePrincipal.GESTIONPILOTOS;
import PaquetePrincipal.GESTIONPILOTOS.Piloto;
import com.toedter.calendar.JDateChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;



public class LISTAGESTIONPILOTOS extends javax.swing.JFrame {

DefaultTableModel modeloPilotoListado = new DefaultTableModel();
    ArrayList<Piloto> listaPilotos = new ArrayList<>();
    
    
    public LISTAGESTIONPILOTOS() {

        initComponents();
    this.setTitle("GESTIÓN DE PILOTOS");

    String[] columnas = {"NOMBRE", "APELLIDO", "DPI", "LICENCIA", "CORREO", "TELEFONO", "GÉNERO", "AÑOS", "ESTADO"};
    for (String columna : columnas) {
        modeloPilotoListado.addColumn(columna);
    }

    tblRegistroPilotos.setModel(modeloPilotoListado);

    cargarDatosDesdeExcel();  // Cargar datos desde Excel
    refrescarTabla();          // Refrescar la tabla para mostrar los datos
    
    tblRegistroPilotos.setVisible(true); // Asegúrate de que la tabla sea visible
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


 
 
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btnModificarPiloto4 = new javax.swing.JButton();
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
        jTextField5.setText(" LISTA DE PILOTOS EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

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
        jScrollPane2.setViewportView(tblRegistroPilotos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnModificarPiloto4.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarPiloto4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPiloto4.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPiloto4.setText("MODIFICAR");
        btnModificarPiloto4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPiloto4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPiloto4ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoPiloto3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarPiloto4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarPiloto1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarPiloto4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnModificarPiloto4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPiloto4ActionPerformed
        MODIFICARGESTIONPILOTOS abrir = new   MODIFICARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarPiloto4ActionPerformed

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
            java.util.logging.Logger.getLogger(LISTAGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LISTAGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LISTAGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LISTAGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LISTAGESTIONPILOTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPiloto1;
    private javax.swing.JButton btnEliminarPiloto1;
    private javax.swing.JButton btnEstadoPiloto3;
    private javax.swing.JButton btnInicioPiloto1;
    private javax.swing.JButton btnListaPiloto1;
    private javax.swing.JButton btnModificarPiloto;
    private javax.swing.JButton btnModificarPiloto1;
    private javax.swing.JButton btnModificarPiloto3;
    private javax.swing.JButton btnModificarPiloto4;
    private javax.swing.JButton btnMostrarPiloto1;
    private javax.swing.JButton btnSalirPiloto1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroPilotos;
    // End of variables declaration//GEN-END:variables
}
