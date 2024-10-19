package GestionDeUsuarios;

import GestionDePilotos.AGREGARGESTIONPILOTOS;
import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;


public class AGREGARGESTIONUSUARIOS  extends javax.swing.JFrame {

   
    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    private int indiceActual;
       private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    
    public AGREGARGESTIONUSUARIOS (String username, String role, LOGINPINEED loginFrame) {
      initComponents();
        indiceActual = 0;

       gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();


 
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }
    this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
              setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    
   

    private void limpiarCampos() {
        txtNombreUsuario.setText("");
        txtApellidoUsuario.setText("");
        txtNumeroDeDpiUsuario.setText("");
        txtCargoUsuario.setSelectedIndex(0);
        txtContraseñaUsuario.setText("");
        txtCorreoElectronicoUsuario.setText("");
        txtNumeroTelefonicoUsuario.setText("");
        txtGeneroUsuario.setSelectedIndex(0);
        txtFechaDeNacimientoUsuario.setDate(null);
        txtNombreDeUsuarioUsuario.setText("");
        txtEstadoUsuario.setSelectedIndex(0);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCargoUsuario = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoUsuario = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoUsuario = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroUsuario = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnAgregarUsuarioSistema = new javax.swing.JButton();
        txtNumeroDeDpiUsuario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtApellidoUsuario = new javax.swing.JTextField();
        txtContraseñaUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreDeUsuarioUsuario = new javax.swing.JTextField();
        txtNumeroTelefonicoUsuario = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtEstadoUsuario = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NOMBRE");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("CARGO");

        txtCargoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "ADMINISTRADOR", "SECRETARIA" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoUsuario.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("NUMERO DE DPI");

        btnAgregarUsuarioSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnAgregarUsuarioSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUsuarioSistema.setText("AGREGAR");
        btnAgregarUsuarioSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioSistemaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("APELLIDO");

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE DE USUARIO");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("CONTRASEÑA");

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel20.setText("ESTADO ");

        txtEstadoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "BLOQUEADO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel10))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombreDeUsuarioUsuario)
                                .addComponent(txtContraseñaUsuario))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(52, 52, 52)
                        .addComponent(txtNumeroDeDpiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreDeUsuarioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNumeroDeDpiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnAgregarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioSistemaActionPerformed
    try {

        String nombreUsuario = txtNombreUsuario.getText().trim();
        String apellidoUsuario = txtApellidoUsuario.getText().trim();
        long numeroDeDpiUsuario = Long.parseLong(txtNumeroDeDpiUsuario.getText().trim());
        String cargoUsuario = txtCargoUsuario.getSelectedItem().toString().trim();
        String correoElectronicoUsuario = txtCorreoElectronicoUsuario.getText().trim();
        int numeroTelefonicoUsuario = Integer.parseInt(txtNumeroTelefonicoUsuario.getText().trim()); 
        String generoUsuario = txtGeneroUsuario.getSelectedItem().toString().trim();
        String nombreDeUsuario = txtNombreDeUsuarioUsuario.getText().trim();
        String estadoUsuario = txtEstadoUsuario.getSelectedItem().toString().trim();

        Date fechaNacimientoUsuarioDate = txtFechaDeNacimientoUsuario.getDate();
        if (fechaNacimientoUsuarioDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
            return;
        }

        
        String contrasenaUsuario = txtContraseñaUsuario.getText().trim();

        // Validate password
            if (!validarContrasena(contrasenaUsuario)) {
                JOptionPane.showMessageDialog(this, "La contraseña no cumple con los requisitos:\n" +
                    "- Debe tener al menos 8 caracteres\n" +
                    "- Debe contener 'pineed'\n" +
                    "- Debe incluir al menos una letra, un número y un carácter especial");
                return;
            }
        
        
   
        if (!correoElectronicoUsuario.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDeNacimientoUsuario = sdf.format(fechaNacimientoUsuarioDate);


        if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || cargoUsuario.isEmpty() ||
            correoElectronicoUsuario.isEmpty() || generoUsuario.isEmpty() || nombreDeUsuario.isEmpty() ||
            contrasenaUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
            return;
        }

   
        if (String.valueOf(numeroDeDpiUsuario).length() != 13) { 
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }

        if (String.valueOf(numeroTelefonicoUsuario).length() != 8) { 
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }


        for (Usuarios usuarioExistente : listaUsuarios) {
            if (usuarioExistente.getNumeroDPI() == numeroDeDpiUsuario) { 
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número de DPI.");
                return;
            }
            if (usuarioExistente.getNumeroTelefono() == numeroTelefonicoUsuario) { 
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número telefónico.");
                return;
            }
            if (usuarioExistente.getCorreoElectronico().equals(correoElectronicoUsuario)) {
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo electrónico.");
                return;
            }
        }


        Usuarios usuario = new Usuarios(nombreDeUsuario, contrasenaUsuario, nombreUsuario, apellidoUsuario, cargoUsuario, 
                                        generoUsuario, numeroDeDpiUsuario, fechaDeNacimientoUsuario, numeroTelefonicoUsuario, 
                                        correoElectronicoUsuario, estadoUsuario);
        
        listaUsuarios.add(usuario);
        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");


        limpiarCampos();



      
        gestionUsuarios.setUsuarios(listaUsuarios); 
        gestionUsuarios.guardarUsuariosEnExcel();
        
        
} catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + e.getMessage());
    }


    

                 String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
    INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarUsuarioSistemaActionPerformed
private boolean validarContrasena(String contrasena) {
    // Verificar longitud mínima
    if (contrasena.length() < 8) {
        return false;
    }

    // Verificar si contiene 'pineed'
    if (!contrasena.toLowerCase().contains("pineed")) {
        return false;
    }

    // Verificar si contiene al menos una letra, un número y un carácter especial
    boolean tieneLetra = false;
    boolean tieneNumero = false;
    boolean tieneEspecial = false;

    for (char c : contrasena.toCharArray()) {
        if (Character.isLetter(c)) {
            tieneLetra = true;
        } else if (Character.isDigit(c)) {
            tieneNumero = true;
        } else if (!Character.isWhitespace(c)) {
            tieneEspecial = true;
        }
    }

    return tieneLetra && tieneNumero && tieneEspecial;
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
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole";      // Replace with actual role
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new AGREGARGESTIONUSUARIOS(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuarioSistema;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtApellidoUsuario;
    private javax.swing.JComboBox<String> txtCargoUsuario;
    private javax.swing.JTextField txtContraseñaUsuario;
    private javax.swing.JTextField txtCorreoElectronicoUsuario;
    private javax.swing.JComboBox<String> txtEstadoUsuario;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoUsuario;
    private javax.swing.JComboBox<String> txtGeneroUsuario;
    private javax.swing.JTextField txtNombreDeUsuarioUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtNumeroDeDpiUsuario;
    private javax.swing.JTextField txtNumeroTelefonicoUsuario;
    // End of variables declaration//GEN-END:variables
}
