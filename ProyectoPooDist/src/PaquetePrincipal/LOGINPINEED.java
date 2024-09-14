package PaquetePrincipal;

import java.util.HashMap;
import java.util.Map;

public class LOGINPINEED extends javax.swing.JFrame {
    private GESTIONUSUARIOS gestionUsuarios;
    private Map<String, Integer> intentosFallidos;

    public LOGINPINEED() {
        initComponents();
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        intentosFallidos = new HashMap<>();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel35 = new javax.swing.JLabel();
        btnIngresarPineed = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtContrasenaUsuario = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel35.setText("CONTRASEÑA");
        getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 100, -1));

        btnIngresarPineed.setBackground(new java.awt.Color(153, 255, 204));
        btnIngresarPineed.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        btnIngresarPineed.setText("INGRESAR");
        btnIngresarPineed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2));
        btnIngresarPineed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarPineedActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarPineed, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 520, 170, 70));

        jLabel34.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jLabel34.setText("USUARIO");
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, 70, -1));
        getContentPane().add(txtContrasenaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 310, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/Imagen_de_WhatsApp_2024-09-09_a_las_09.35.27_baedf375-removebg-preview.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, -30, 690, 510));
        getContentPane().add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 470, 310, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/azul.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarPineedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarPineedActionPerformed
<<<<<<< HEAD
String nombreUsuario = txtNombreUsuario.getText();
        String contraseña = new String(txtContraseñaUsuario.getPassword());
=======

        String nombreUsuario = txtNombreUsuario.getText();
        String contraseña = new String(txtContrasenaUsuario.getPassword());
>>>>>>> 8f45bced62433f4e62290a05f01e433d2cac7582

        Usuarios usuario = buscarUsuario(nombreUsuario);

        if (usuario == null) {
            mostrarMensajeError("Usuario no encontrado.");
            return;
        }

        if (usuario.getEstado().equalsIgnoreCase("bloqueado") && !usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
            mostrarMensajeError("Usuario bloqueado. Contacte al administrador.");
            return;
        }

        if (contraseña.equals(usuario.getContrasenaUsuario())) {
      
            INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
            abrir.setVisible(true);
            this.setVisible(false);
    
            intentosFallidos.remove(nombreUsuario);
        } else {
      
            int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
            intentosFallidos.put(nombreUsuario, intentos);

            if (intentos >= 3) {
                if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
                    mostrarMensajeError("Ha superado los intentos. El programa se cerrará.");
                    System.exit(0);
                } else {
                    bloquearUsuario(usuario);
                    mostrarMensajeError("Usuario bloqueado después de 3 intentos fallidos.");
                }
            } else {
                mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
            }
        }
    }//GEN-LAST:event_btnIngresarPineedActionPerformed

     private Usuarios buscarUsuario(String nombreUsuario) {
        for (Usuarios u : gestionUsuarios.getUsuarios()) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    private void bloquearUsuario(Usuarios usuario) {
        if (!usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
            usuario.setEstado("BLOQUEADOuser");
            gestionUsuarios.guardarUsuariosEnExcel();
        }
    }

    private void mostrarMensajeError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LOGINPINEED().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresarPineed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JPasswordField txtContrasenaUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
