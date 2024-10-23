package GestionDePilotos;

import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDePilotos.Piloto;
import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AGREGARGESTIONPILOTOS extends javax.swing.JFrame {

    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private int indiceActual;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    public AGREGARGESTIONPILOTOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;
        setResizable(false); // Desactivar el cambio de tamaño
        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();

        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Correo", "Teléfono", "Género", "Nacimiento", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);

        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }

        cargarPilotosEnTabla();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnAgregarPilotoSistema = new javax.swing.JButton();
        txtCorreoElectronicoPiloto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaPiloto = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePiloto = new javax.swing.JTextField();
        txtApellidoPiloto = new javax.swing.JTextField();
        txtNumeroDeDpiPiloto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoPiloto = new javax.swing.JComboBox<>();
        txtNumeroTelefonicoPiloto = new javax.swing.JTextField();
        txtGeneroPiloto = new javax.swing.JComboBox<>();
        txtFechaDeNacimientoPiloto = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnAgregarPilotoSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnAgregarPilotoSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnAgregarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPilotoSistema.setText("AGREGAR");
        btnAgregarPilotoSistema.setBorder(null);
        btnAgregarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPilotoSistemaActionPerformed(evt);
            }
        });

        txtCorreoElectronicoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoDeLicenciaPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        txtNombrePiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtApellidoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtNumeroDeDpiPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "ENFERMO", "EN VACACIONES", "JUBILADO", "INACTIVO" }));

        txtNumeroTelefonicoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNumeroTelefonicoPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelefonicoPilotoActionPerformed(evt);
            }
        });

        txtGeneroPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtGeneroPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        txtFechaDeNacimientoPiloto.setDateFormatString("dd/MM/yyyy");
        txtFechaDeNacimientoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnAgregarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCorreoElectronicoPiloto))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombrePiloto)
                                .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroTelefonicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNumeroTelefonicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(93, 93, 93)
                .addComponent(btnAgregarPilotoSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPilotoSistemaActionPerformed
try {
        String nombrePiloto = txtNombrePiloto.getText().trim();
        String apellidoPiloto = txtApellidoPiloto.getText().trim();
        long numeroDeDpiPiloto = Long.parseLong(txtNumeroDeDpiPiloto.getText().trim());
        String tipoLicencia = txtTipoDeLicenciaPiloto.getSelectedItem().toString().trim();
        String correoElectronicoPiloto = txtCorreoElectronicoPiloto.getText().trim();
        int numeroTelefonicoPiloto = Integer.parseInt(txtNumeroTelefonicoPiloto.getText().trim());
        String generoPiloto = txtGeneroPiloto.getSelectedItem().toString().trim();
        String estadoPiloto = txtEstadoPiloto.getSelectedItem().toString().trim();

        Date fechaNacimientoPilotoDate = txtFechaDeNacimientoPiloto.getDate();
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

        // Verificación de existencia de piloto
        for (Piloto pilotoExistente : listaPilotos) {
            if (pilotoExistente.getNumeroDeDpi() == numeroDeDpiPiloto) {
                JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
                return;
            }
            if (pilotoExistente.getNumeroTelefonicoPiloto() == numeroTelefonicoPiloto) {
                JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
                return;
            }
            if (pilotoExistente.getCorreoElectronicoPiloto().equals(correoElectronicoPiloto)) {
                JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
                return;
            }
        }

        // Crear un nuevo piloto y agregarlo
        Piloto nuevoPiloto = new Piloto();
        nuevoPiloto.setNombrePiloto(nombrePiloto);
        nuevoPiloto.setApellidoPiloto(apellidoPiloto);
        nuevoPiloto.setNumeroDeDpi(numeroDeDpiPiloto);
        nuevoPiloto.setTipoLicencia(tipoLicencia);
        nuevoPiloto.setCorreoElectronicoPiloto(correoElectronicoPiloto);
        nuevoPiloto.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
        nuevoPiloto.setGeneroPiloto(generoPiloto);
        nuevoPiloto.setFechaDeNacimiento(fechaDeNacimientoPiloto);
        nuevoPiloto.setEstadoPiloto(estadoPiloto);
        nuevoPiloto.setActivo(true); // Establecer activo en true

        // Agregar piloto a la lista y actualizar
        listaPilotos.add(nuevoPiloto);
        gestionPilotos.actualizarPiloto(nuevoPiloto); // Llama al método de actualización para manejar el guardado

        // Recargar la tabla
        cargarPilotosEnTabla();
        limpiarCampos();

        // Mensaje de éxito
        JOptionPane.showMessageDialog(this, "Piloto agregado exitosamente.");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar piloto: " + e.getMessage());
    }

    // Cerrar la ventana actual y abrir la gestión de pilotos
    INICIOGESTIONPILOTOS abrir = new INICIOGESTIONPILOTOS(currentUser, userRole, loginFrame);
    abrir.setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnAgregarPilotoSistemaActionPerformed

    private void txtNumeroTelefonicoPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelefonicoPilotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTelefonicoPilotoActionPerformed

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
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                       String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole";      // Replace with actual role
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new AGREGARGESTIONPILOTOS(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPilotoSistema;
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
    private javax.swing.JTextField txtApellidoPiloto;
    private javax.swing.JTextField txtCorreoElectronicoPiloto;
    private javax.swing.JComboBox<String> txtEstadoPiloto;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPiloto;
    private javax.swing.JComboBox<String> txtGeneroPiloto;
    private javax.swing.JTextField txtNombrePiloto;
    private javax.swing.JTextField txtNumeroDeDpiPiloto;
    private javax.swing.JTextField txtNumeroTelefonicoPiloto;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPiloto;
    // End of variables declaration//GEN-END:variables
}
