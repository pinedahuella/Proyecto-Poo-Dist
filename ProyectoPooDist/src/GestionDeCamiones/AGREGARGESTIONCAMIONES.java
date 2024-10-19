package GestionDeCamiones;

import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Pattern;

public class AGREGARGESTIONCAMIONES extends javax.swing.JFrame {
    private GESTIONCAMIONES gestionCamiones;
    private Vector<Camiones> listaCamiones = new Vector<>();
    private DefaultTableModel modeloCamiones = new DefaultTableModel();
    private Camiones camionActual;
    private int indiceActual;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    public AGREGARGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;

        // Inicializa la gestión de camiones
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();

        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
        }

        cargarCamionesEnTabla();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }



    private void cargarCamionesEnTabla() {
        modeloCamiones.setRowCount(0); // Reinicia la tabla

        // Llena la tabla con los datos de los camiones
        for (Camiones camiones : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camiones.getPlacas(),
                camiones.getModelo(),
                camiones.getMarca(),
                camiones.getEstado(),
                camiones.getTipoCombustible(),
                camiones.getKilometraje(),
                camiones.getCapacidadCarga(),
                camiones.getAñoFabricacion(),
                camiones.getCostoReparacion(),
                camiones.getCostoGalon(),
                camiones.getGalones(),
                camiones.getCostoMantenimiento(),
                camiones.getGastoNoEspecificado(),
                camiones.getDescripcionDelGasto(),
                camiones.getTiempoEnReparacion(),
                camiones.getFechaDeMantenimiento(),
                camiones.getTotal()
            });
        }
    }

    private void limpiarCampos() {
        txtPlacasCamiones.setText("");
        txtEstadoCamiones.setSelectedIndex(0);
        txtTipoCombustibleCamiones.setSelectedIndex(0);
        txtKilometrajeCamiones.setText("");
        txtCapacidadDeCargaCamiones.setText("");
        txtAñoDeFabricacionCamiones.setDate(null);
        txtModeloCamiones.setText("");
        txtMarcaCamiones.setText("");
    }

    private boolean validarPlacas(String placas) {
        // Valida que las placas contengan al menos una letra y un número
        String regex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$";
        return Pattern.matches(regex, placas);
    }

    private boolean validarKilometraje(double kilometraje) {
        return (kilometraje >= 0 && kilometraje <= 1000000);
    }

    private boolean validarCapacidadCarga(double capacidadCarga) {
        return (capacidadCarga >= 100 && capacidadCarga <= 30000);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtKilometrajeCamiones = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtModeloCamiones = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCapacidadDeCargaCamiones = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtAñoDeFabricacionCamiones = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnAgregarCamionSistema = new javax.swing.JButton();
        txtEstadoCamiones = new javax.swing.JComboBox<>();
        txtPlacasCamiones = new javax.swing.JTextField();
        txtTipoCombustibleCamiones = new javax.swing.JComboBox<>();
        txtMarcaCamiones = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));
        jPanel2.setPreferredSize(new java.awt.Dimension(838, 495));

        jPanel5.setPreferredSize(new java.awt.Dimension(826, 483));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("CAPACIDAD DE CARGA");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("KILOMETRAJE");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("AÑO DE FABRICACION");

        txtAñoDeFabricacionCamiones.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("PLACA");

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("TIPO DE COMBUSTIBLE");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        btnAgregarCamionSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnAgregarCamionSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarCamionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCamionSistema.setText("AGREGAR");
        btnAgregarCamionSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarCamionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCamionSistemaActionPerformed(evt);
            }
        });

        txtEstadoCamiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En viaje", "Libre", "Descompuesto", "Funcional" }));
        txtEstadoCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoCamionesActionPerformed(evt);
            }
        });

        txtTipoCombustibleCamiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disel", "Gasolina", "Eléctrico", "Híbrido", "Hidrógeno", "Gas Licuado de Petróleo", " " }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPlacasCamiones))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMarcaCamiones)
                                    .addComponent(txtModeloCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAñoDeFabricacionCamiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCapacidadDeCargaCamiones)
                                    .addComponent(txtTipoCombustibleCamiones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtEstadoCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtKilometrajeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 470, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarcaCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtModeloCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPlacasCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAñoDeFabricacionCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCapacidadDeCargaCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTipoCombustibleCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometrajeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEstadoCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(btnAgregarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnAgregarCamionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCamionSistemaActionPerformed
       try {
            // Obtención de datos desde el formulario
            String placas = txtPlacasCamiones.getText().trim();
            String modelo = txtModeloCamiones.getText().trim();
            String marca = txtMarcaCamiones.getText().trim();
            String estado = txtEstadoCamiones.getSelectedItem().toString().trim();
            String tipoCombustible = txtTipoCombustibleCamiones.getSelectedItem().toString().trim();
            double kilometraje = Double.parseDouble(txtKilometrajeCamiones.getText().trim());
            double capacidadCarga = Double.parseDouble(txtCapacidadDeCargaCamiones.getText().trim());

            Date añoFabricacionDate = txtAñoDeFabricacionCamiones.getDate();
            if (añoFabricacionDate == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un año de fabricación válido.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String añoFabricacion = sdf.format(añoFabricacionDate);

            // Validación de campos
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

            // Verifica si hay un camión con las mismas placas
            boolean placasCambiadas = !placas.equals(camionActual.getPlacas());
            for (Camiones camionExistente : listaCamiones) {
                if (camionExistente != camionActual && placasCambiadas && camionExistente.getPlacas().equals(placas)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un camión con esas placas.");
                    return;
                }
            }

            // Actualización de datos del camión
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
            cargarCamionesEnTabla(); // Refresca la tabla con los nuevos datos

            this.dispose(); // Cierra la ventana actual

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar camión: " + e.getMessage());
        }
       
              String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
   
    
    INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarCamionSistemaActionPerformed

    private void txtEstadoCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoCamionesActionPerformed

    }//GEN-LAST:event_txtEstadoCamionesActionPerformed

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
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new AGREGARGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCamionSistema;
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
    private com.toedter.calendar.JDateChooser txtAñoDeFabricacionCamiones;
    private javax.swing.JTextField txtCapacidadDeCargaCamiones;
    private javax.swing.JComboBox<String> txtEstadoCamiones;
    private javax.swing.JTextField txtKilometrajeCamiones;
    private javax.swing.JTextField txtMarcaCamiones;
    private javax.swing.JTextField txtModeloCamiones;
    private javax.swing.JTextField txtPlacasCamiones;
    private javax.swing.JComboBox<String> txtTipoCombustibleCamiones;
    // End of variables declaration//GEN-END:variables
}
