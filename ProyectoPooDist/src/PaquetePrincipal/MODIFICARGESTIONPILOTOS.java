package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MODIFICARGESTIONPILOTOS extends javax.swing.JFrame {


    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private Piloto pilotoActual;
    private INICIOGESTIONPILOTOS ventanaPrincipal;

    public MODIFICARGESTIONPILOTOS(Piloto piloto, INICIOGESTIONPILOTOS ventanaPrincipal) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.ventanaPrincipal = ventanaPrincipal;
        if (ventanaPrincipal != null) {
            this.gestionPilotos = ventanaPrincipal.gestionPilotos;
            this.listaPilotos = gestionPilotos.getPilotos();
        }

        this.pilotoActual = piloto;
        if (piloto != null) {
            cargarDatosPiloto();
        }
    }   
    
    public MODIFICARGESTIONPILOTOS() {
        this(null, null);
    }
    
    private void cargarDatosPiloto() {
        if (pilotoActual != null) {
            txtNombrePilotoModificarModificar.setText(pilotoActual.getNombrePiloto());
            txtApellidoPilotoModificarModificar.setText(pilotoActual.getApellidoPiloto());
            txtNumeroDeDpiPilotoModificarModificar.setText(String.valueOf(pilotoActual.getNumeroDeDpi()));
            txtTipoDeLicenciaPilotoModificarModificar.setSelectedItem(pilotoActual.getTipoLicencia());
            txtCorreoElectronicoPilotoModificarModificar.setText(pilotoActual.getCorreoElectronicoPiloto());
            txtNumeroTelefonicoPilotoModificarModificar.setText(String.valueOf(pilotoActual.getNumeroTelefonicoPiloto()));
            txtGeneroPilotoModificarModificar.setSelectedItem(pilotoActual.getGeneroPiloto());
            txtEstadoPilotoModificarModificar.setSelectedItem(pilotoActual.getEstadoPiloto());
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = sdf.parse(pilotoActual.getFechaDeNacimiento());
                txtFechaDeNacimientoPilotoModificarModificar.setDate(fechaNacimiento);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la fecha de nacimiento: " + e.getMessage());
            }
        }
    }
    
    private void limpiarCamposPiloto() {
        txtNombrePilotoModificarModificar.setText("");
        txtApellidoPilotoModificarModificar.setText("");
        txtNumeroDeDpiPilotoModificarModificar.setText("");
        txtTipoDeLicenciaPilotoModificarModificar.setSelectedIndex(0);
        txtCorreoElectronicoPilotoModificarModificar.setText("");
        txtNumeroTelefonicoPilotoModificarModificar.setText("");
        txtGeneroPilotoModificarModificar.setSelectedIndex(0);
        txtFechaDeNacimientoPilotoModificarModificar.setDate(null);
        txtEstadoPilotoModificarModificar.setSelectedIndex(0);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoModificarModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoPilotoModificarModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoModificarModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaPilotoModificarModificar = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoPilotoModificarModificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoPilotoModificarModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroPilotoModificarModificar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoPilotoModificarModificar = new javax.swing.JComboBox<>();
        btnModificarPilotoSistema = new javax.swing.JButton();
        txtNumeroTelefonicoPilotoModificarModificar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));
        jPanel2.setPreferredSize(new java.awt.Dimension(838, 495));

        jPanel5.setPreferredSize(new java.awt.Dimension(826, 483));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoPilotoModificarModificar.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        btnModificarPilotoSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnModificarPilotoSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPilotoSistema.setText("MODIFICAR");
        btnModificarPilotoSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPilotoSistemaActionPerformed(evt);
            }
        });

        txtNumeroTelefonicoPilotoModificarModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelefonicoPilotoModificarModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGeneroPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumeroDeDpiPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoDeLicenciaPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCorreoElectronicoPilotoModificarModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombrePilotoModificarModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                .addComponent(txtApellidoPilotoModificarModificar))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroTelefonicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstadoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(306, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDeLicenciaPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGeneroPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNumeroTelefonicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(btnModificarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPilotoSistemaActionPerformed
        try {
            String nombrePiloto = txtNombrePilotoModificarModificar.getText().trim();
            String apellidoPiloto = txtApellidoPilotoModificarModificar.getText().trim();
            long numeroDeDpiPiloto = Long.parseLong(txtNumeroDeDpiPilotoModificarModificar.getText().trim());
            String tipoLicencia = txtTipoDeLicenciaPilotoModificarModificar.getSelectedItem().toString().trim();
            String correoElectronicoPiloto = txtCorreoElectronicoPilotoModificarModificar.getText().trim();
            int numeroTelefonicoPiloto = Integer.parseInt(txtNumeroTelefonicoPilotoModificarModificar.getText().trim());
            String generoPiloto = txtGeneroPilotoModificarModificar.getSelectedItem().toString().trim();
            String estadoPiloto = txtEstadoPilotoModificarModificar.getSelectedItem().toString().trim();

            Date fechaNacimientoPilotoDate = txtFechaDeNacimientoPilotoModificarModificar.getDate();
            if (fechaNacimientoPilotoDate == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
                return;
            }

            if (!correoElectronicoPiloto.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaDeNacimientoPiloto = sdf.format(fechaNacimientoPilotoDate);

            if (nombrePiloto.isEmpty() || apellidoPiloto.isEmpty() || tipoLicencia.isEmpty() ||
                correoElectronicoPiloto.isEmpty() || generoPiloto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
                return;
            }

            if (String.valueOf(numeroDeDpiPiloto).length() != 13) {
                JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
                return;
            }

            if (String.valueOf(numeroTelefonicoPiloto).length() != 8) {
                JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
                return;
            }

            boolean dpiCambiado = numeroDeDpiPiloto != pilotoActual.getNumeroDeDpi();
            boolean telefonoCambiado = numeroTelefonicoPiloto != pilotoActual.getNumeroTelefonicoPiloto();
            boolean correoCambiado = !correoElectronicoPiloto.equals(pilotoActual.getCorreoElectronicoPiloto());

            for (Piloto pilotoExistente : listaPilotos) {
                if (pilotoExistente != pilotoActual) {
                    if (dpiCambiado && pilotoExistente.getNumeroDeDpi() == numeroDeDpiPiloto) {
                        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
                        return;
                    }
                    if (telefonoCambiado && pilotoExistente.getNumeroTelefonicoPiloto() == numeroTelefonicoPiloto) {
                        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
                        return;
                    }
                    if (correoCambiado && pilotoExistente.getCorreoElectronicoPiloto().equals(correoElectronicoPiloto)) {
                        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
                        return;
                    }
                }
            }

            pilotoActual.setNombrePiloto(nombrePiloto);
            pilotoActual.setApellidoPiloto(apellidoPiloto);
            pilotoActual.setNumeroDeDpi(numeroDeDpiPiloto);
            pilotoActual.setTipoLicencia(tipoLicencia);
            pilotoActual.setCorreoElectronicoPiloto(correoElectronicoPiloto);
            pilotoActual.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
            pilotoActual.setGeneroPiloto(generoPiloto);
            pilotoActual.setFechaDeNacimiento(fechaDeNacimientoPiloto);
            pilotoActual.setEstadoPiloto(estadoPiloto);

            gestionPilotos.actualizarPiloto(pilotoActual);

            JOptionPane.showMessageDialog(this, "Piloto modificado exitosamente.");

            ventanaPrincipal.actualizarTabla();

            ventanaPrincipal.setVisible(true);
            this.dispose();

} catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar piloto: " + e.getMessage());
        }
    }//GEN-LAST:event_btnModificarPilotoSistemaActionPerformed

    private void txtNumeroTelefonicoPilotoModificarModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelefonicoPilotoModificarModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTelefonicoPilotoModificarModificarActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
    private javax.swing.JButton btnModificarPilotoSistema;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtApellidoPilotoModificarModificar;
    private javax.swing.JTextField txtCorreoElectronicoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtEstadoPilotoModificarModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtGeneroPilotoModificarModificar;
    private javax.swing.JTextField txtNombrePilotoModificarModificar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoModificarModificar;
    private javax.swing.JTextField txtNumeroTelefonicoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPilotoModificarModificar;
    // End of variables declaration//GEN-END:variables
}
