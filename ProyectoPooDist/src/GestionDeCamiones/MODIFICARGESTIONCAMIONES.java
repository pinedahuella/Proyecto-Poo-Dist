package GestionDeCamiones;

import Login.LOGINPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MODIFICARGESTIONCAMIONES extends javax.swing.JFrame {

    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    DefaultTableModel modeloCamiones = new DefaultTableModel();
    private Camiones camionActual;
    private INICIOGESTIONCAMIONES ventanaPrincipal;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;



public MODIFICARGESTIONCAMIONES(Camiones camion, INICIOGESTIONCAMIONES ventanaPrincipal, String username, String role, LOGINPINEED loginFrame) {
    initComponents();
    // Cambia esto para que solo cierre esta ventana, no toda la aplicación
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    
    this.currentUser = username;
    this.userRole = role;
    this.loginFrame = loginFrame;
    
    if (ventanaPrincipal != null) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.gestionCamiones = ventanaPrincipal.gestionCamiones;
        this.listaCamiones = gestionCamiones.getCamiones();
    }
    
    if (camion != null) {
        this.camionActual = camion;
        cargarDatosCamion();
    }
}
    
    

    
    private void cargarDatosCamion() {
        if (camionActual != null) {
            txtPlacaCamionesModificar.setText(camionActual.getPlacas());
            txtModeloCamionModificar.setText(camionActual.getModelo());
            txtMarcaCamionModificar.setText(camionActual.getMarca());
            txtEstadoCamionModificar.setSelectedItem(camionActual.getEstado());
            txtTipoCombustibleCamionModificar.setSelectedItem(camionActual.getTipoCombustible());
            txtKilometrajeCamionModificar.setText(String.valueOf(camionActual.getKilometraje()));
            txtCapacidadDeCargaCamionModificar.setText(String.valueOf(camionActual.getCapacidadCarga()));
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date añoFabricacion = sdf.parse(camionActual.getAñoFabricacion());
                txtAñoDeFabricacionCamionModificar.setDate(añoFabricacion);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar el año de fabricación: " + e.getMessage());
            }
        }
    }
    
    private void limpiarCamposCamion() {
        txtPlacaCamionesModificar.setText("");
        txtModeloCamionModificar.setText("");
        txtMarcaCamionModificar.setText("");
        txtEstadoCamionModificar.setSelectedIndex(0);
        txtTipoCombustibleCamionModificar.setSelectedIndex(0);
        txtKilometrajeCamionModificar.setText("");
        txtCapacidadDeCargaCamionModificar.setText("");
        txtAñoDeFabricacionCamionModificar.setDate(null);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnModificarCamionesSistema = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtTipoCombustibleCamionModificar = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEstadoCamionModificar = new javax.swing.JComboBox<>();
        txtAñoDeFabricacionCamionModificar = new com.toedter.calendar.JDateChooser();
        txtCapacidadDeCargaCamionModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtModeloCamionModificar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtPlacaCamionesModificar = new javax.swing.JTextField();
        txtKilometrajeCamionModificar = new javax.swing.JTextField();
        txtMarcaCamionModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnModificarCamionesSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnModificarCamionesSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnModificarCamionesSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCamionesSistema.setText("MODIFICAR");
        btnModificarCamionesSistema.setBorder(null);
        btnModificarCamionesSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCamionesSistemaActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        txtTipoCombustibleCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoCombustibleCamionModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disel", "Gasolina", "Eléctrico", "Híbrido", "Hidrógeno", "Gas Licuado de Petróleo", " " }));

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("KILOMETRAJE");

        txtEstadoCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoCamionModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En viaje", "Libre", "Descompuesto", "Funcional" }));
        txtEstadoCamionModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoCamionModificarActionPerformed(evt);
            }
        });

        txtAñoDeFabricacionCamionModificar.setDateFormatString("dd/MM/yyyy");
        txtAñoDeFabricacionCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtCapacidadDeCargaCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("CAPACIDAD DE CARGA");

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("AÑO DE FABRICACION");

        txtModeloCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("PLACA");

        txtPlacaCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtKilometrajeCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtMarcaCamionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("TIPO DE COMBUSTIBLE");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnModificarCamionesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlacaCamionesModificar))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarcaCamionModificar)
                            .addComponent(txtModeloCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAñoDeFabricacionCamionModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCapacidadDeCargaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoCombustibleCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtEstadoCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtKilometrajeCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarcaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtModeloCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPlacaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAñoDeFabricacionCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCapacidadDeCargaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTipoCombustibleCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometrajeCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEstadoCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128)
                .addComponent(btnModificarCamionesSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarCamionesSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCamionesSistemaActionPerformed
        try {
            String placas = txtPlacaCamionesModificar.getText().trim();
            String modelo = txtModeloCamionModificar.getText().trim();
            String marca = txtMarcaCamionModificar.getText().trim();
            String estado = txtEstadoCamionModificar.getSelectedItem().toString().trim();
            String tipoCombustible = txtTipoCombustibleCamionModificar.getSelectedItem().toString().trim();
            double kilometraje = Double.parseDouble(txtKilometrajeCamionModificar.getText().trim());
            double capacidadCarga = Double.parseDouble(txtCapacidadDeCargaCamionModificar.getText().trim());

            Date añoFabricacionDate = txtAñoDeFabricacionCamionModificar.getDate();
            if (añoFabricacionDate == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un año de fabricación válido.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String añoFabricacion = sdf.format(añoFabricacionDate);

            if (placas.isEmpty() || modelo.isEmpty() || marca.isEmpty() || estado.isEmpty() || tipoCombustible.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
                return;
            }

            if (!validarPlacas(placas)) {
                JOptionPane.showMessageDialog(this, "Las placas deben contener al menos una letra y un número.");
                return;
            }

            if (!validarKilometraje(kilometraje)) {
                JOptionPane.showMessageDialog(this, "El kilometraje debe ser un valor positivo y realista (0 - 1,000,000 km).");
                return;
            }

            if (!validarCapacidadCarga(capacidadCarga)) {
                JOptionPane.showMessageDialog(this, "La capacidad de carga debe ser un valor positivo y realista (100 - 30,000 kg).");
                return;
            }

            boolean placasCambiadas = !placas.equals(camionActual.getPlacas());

            for (Camiones camionExistente : listaCamiones) {
                if (camionExistente != camionActual && placasCambiadas && camionExistente.getPlacas().equals(placas)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un camión con esas placas.");
                    return;
                }
            }

            camionActual.setPlacas(placas);
            camionActual.setModelo(modelo);
            camionActual.setMarca(marca);
            camionActual.setEstado(estado);
            camionActual.setTipoCombustible(tipoCombustible);
            camionActual.setKilometraje(kilometraje);
            camionActual.setCapacidadCarga(capacidadCarga);
            camionActual.setAñoFabricacion(añoFabricacion);

            gestionCamiones.actualizarCamion(camionActual);

            JOptionPane.showMessageDialog(this, "Camión modificado exitosamente.");

            ventanaPrincipal.actualizarTabla();

            ventanaPrincipal.setVisible(true);
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar camión: " + e.getMessage());
        }
    }//GEN-LAST:event_btnModificarCamionesSistemaActionPerformed

    private void txtEstadoCamionModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoCamionModificarActionPerformed

    }//GEN-LAST:event_txtEstadoCamionModificarActionPerformed

    
    
    private boolean validarPlacas(String placas) {
        String regex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$";
        return placas.matches(regex);
    }

    private boolean validarKilometraje(double kilometraje) {
        return (kilometraje >= 0 && kilometraje <= 1000000);
    }

    private boolean validarCapacidadCarga(double capacidadCarga) {
        return (capacidadCarga >= 100 && capacidadCarga <= 30000);
    }
    
    
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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                 String username = "defaultUser";  // Replace with actual username or logic
        String role = "defaultRole"; 
        
        LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object
        
        // Create the MODIFICARGESTIONCAMIONES instance with the required parameters
        new MODIFICARGESTIONCAMIONES(null, null, username, role, loginFrame).setVisible(true);
    
            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificarCamionesSistema;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private com.toedter.calendar.JDateChooser txtAñoDeFabricacionCamionModificar;
    private javax.swing.JTextField txtCapacidadDeCargaCamionModificar;
    private javax.swing.JComboBox<String> txtEstadoCamionModificar;
    private javax.swing.JTextField txtKilometrajeCamionModificar;
    private javax.swing.JTextField txtMarcaCamionModificar;
    private javax.swing.JTextField txtModeloCamionModificar;
    private javax.swing.JTextField txtPlacaCamionesModificar;
    private javax.swing.JComboBox<String> txtTipoCombustibleCamionModificar;
    // End of variables declaration//GEN-END:variables
}
