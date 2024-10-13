// LOGINPINEED.java
package Login;

import Inicio.INICIOPINEEDINICIAL;
import Inicio.INICIOPINEEDINICIAL;
import GestionDeUsuarios.GESTIONUSUARIOS;
import Login.GESTIONLOGIN;
import Login.GESTIONLOGIN;
import Login.Login;
import GestionDeUsuarios.Usuarios;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

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

        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        txtContraseñaUsuario = new javax.swing.JPasswordField();
        btnIngresarPineed = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("CONTRASEÑA");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, -1, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("USUARIO");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 450, 60, -1));
        getContentPane().add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 210, -1));
        getContentPane().add(txtContraseñaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 490, 210, -1));

        btnIngresarPineed.setBackground(new java.awt.Color(0, 102, 255));
        btnIngresarPineed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIngresarPineed.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresarPineed.setText("Ingresar");
        btnIngresarPineed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnIngresarPineed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarPineedActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarPineed, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 500, 100, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/Imagen_de_WhatsApp_2024-09-09_a_las_09.35.27_baedf375-removebg-preview.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 640, 370));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/azul.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarPineedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarPineedActionPerformed
String nombreUsuario = txtNombreUsuario.getText();
        String contraseña = new String(txtContraseñaUsuario.getPassword());
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
            LocalDateTime tiempoEntrada = LocalDateTime.now();
            String rol = usuario.getCargo();
            GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
            gestionLogin.cargarLoginsDesdeExcel();
            
            Login nuevoLogin = new Login(
                tiempoEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), 
                "", 
                nombreUsuario, 
                rol
            );
            gestionLogin.setUnLogin(nuevoLogin);
            
            INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL(nombreUsuario, rol, this);
            abrir.addWindowListener();
            abrir.setVisible(true);
            this.setVisible(false);
            intentosFallidos.remove(nombreUsuario);
        } else {
            int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
            intentosFallidos.put(nombreUsuario, intentos);
            
            if (intentos >= 3) {
                bloquearUsuario(usuario);
                mostrarMensajeError("Usuario bloqueado por múltiples intentos fallidos.");
            } else {
                mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
            }
        }
    }//GEN-LAST:event_btnIngresarPineedActionPerformed

    public void cerrarSesion(String nombreUsuario, String rol) {
        LocalDateTime tiempoSalida = LocalDateTime.now();
        GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
        gestionLogin.cargarLoginsDesdeExcel();
        
        Vector<Login> logins = gestionLogin.getLogins();
        for (Login login : logins) {
            if (login.getPersonal().equals(nombreUsuario) && login.getTiempoSalida().isEmpty()) {
                login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                gestionLogin.actualizarLogin(login);
                break;
            }
        }
    }
    
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
            usuario.setEstado("BLOQUEADO");
            gestionUsuarios.actualizarUsuario(usuario);
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField txtContraseñaUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
