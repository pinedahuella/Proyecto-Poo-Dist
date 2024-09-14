package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;


import javax.swing.JFrame; 
import javax.swing.JOptionPane;



public class LISTAGESTIONPILOTOS extends javax.swing.JFrame {


    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private int indiceActual;

    
    
    public LISTAGESTIONPILOTOS() {

        initComponents();
    indiceActual = 0;

   
        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();

   
        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Correo", "Teléfono", "Género", "Nacimiento", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);

    
        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }

      
          tblRegistroPilotos.setModel(modeloPilotos);

        cargarPilotosEnTabla();
    }

    private void cargarPilotosEnTabla() {
     
        modeloPilotos.setRowCount(0);


        for (Piloto piloto : listaPilotos) {
            modeloPilotos.addRow(new Object[]{
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
        btnModificarPiloto = new javax.swing.JButton();
        btnAgregarPiloto = new javax.swing.JButton();
        btnEliminarPiloto = new javax.swing.JButton();
        btnMostrarPiloto = new javax.swing.JButton();
        btnListaPiloto = new javax.swing.JButton();
        btnInicioPiloto = new javax.swing.JButton();
        btnSalirPiloto = new javax.swing.JButton();
        btnEstadoPiloto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
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

        btnModificarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPiloto.setText("MODIFICAR");
        btnModificarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPilotoActionPerformed(evt);
            }
        });

        btnAgregarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPiloto.setText("AGREGAR");
        btnAgregarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPilotoActionPerformed(evt);
            }
        });

        btnEliminarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPiloto.setText("ELIMINAR");
        btnEliminarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPilotoActionPerformed(evt);
            }
        });

        btnMostrarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPiloto.setText("MOSTRAR");
        btnMostrarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotoActionPerformed(evt);
            }
        });

        btnListaPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnListaPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnListaPiloto.setText("LISTA");
        btnListaPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPilotoActionPerformed(evt);
            }
        });

        btnInicioPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioPiloto.setText("INICIO");
        btnInicioPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioPilotoActionPerformed(evt);
            }
        });

        btnSalirPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirPiloto.setText("SALIR");
        btnSalirPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPilotoActionPerformed(evt);
            }
        });

        btnEstadoPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoPiloto.setText("ESTADO");
        btnEstadoPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoPilotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnModificarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPilotoActionPerformed
        MODIFICARGESTIONPILOTOS abrir = new   MODIFICARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarPilotoActionPerformed

    private void btnAgregarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPilotoActionPerformed
        AGREGARGESTIONPILOTOS abrir = new  AGREGARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarPilotoActionPerformed

    private void btnEliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPilotoActionPerformed
        ELIMINARGESTIONPILOTOS abrir = new  ELIMINARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarPilotoActionPerformed

    private void btnMostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotoActionPerformed
        MOSTRARGESTIONPILOTOS abrir = new   MOSTRARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarPilotoActionPerformed

    private void btnListaPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaPilotoActionPerformed
        LISTAGESTIONPILOTOS abrir = new   LISTAGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaPilotoActionPerformed

    private void btnInicioPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioPilotoActionPerformed
        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioPilotoActionPerformed

    private void btnSalirPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPilotoActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirPilotoActionPerformed

    private void btnEstadoPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoPilotoActionPerformed
        ESTADOGESTIONPILOTOS abrir = new ESTADOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoPilotoActionPerformed

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
    private javax.swing.JButton btnAgregarPiloto;
    private javax.swing.JButton btnEliminarPiloto;
    private javax.swing.JButton btnEstadoPiloto;
    private javax.swing.JButton btnInicioPiloto;
    private javax.swing.JButton btnListaPiloto;
    private javax.swing.JButton btnModificarPiloto;
    private javax.swing.JButton btnMostrarPiloto;
    private javax.swing.JButton btnSalirPiloto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroPilotos;
    // End of variables declaration//GEN-END:variables
}
