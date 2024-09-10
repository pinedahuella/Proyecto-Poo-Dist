
package PaquetePrincipal;


public class LOGINPINEED extends javax.swing.JFrame {


    public LOGINPINEED() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        txtContraseñaUsuario = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        btnIngresarPineed = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel34.setText("USUARIO");
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, -1, -1));

        jLabel35.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel35.setText("CONTRASEÑA");
        getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, -1, -1));
        getContentPane().add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, 270, -1));
        getContentPane().add(txtContraseñaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 490, 260, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/Imagen_de_WhatsApp_2024-09-09_a_las_09.35.27_baedf375-removebg-preview.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 550, 370));

        btnIngresarPineed.setBackground(new java.awt.Color(153, 255, 204));
        btnIngresarPineed.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        btnIngresarPineed.setText("INGRESAR");
        btnIngresarPineed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnIngresarPineed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarPineedActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarPineed, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 570, 110, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/1366_2000.jpeg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-3, -4, 1010, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarPineedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarPineedActionPerformed

        String nombreUsuario = txtNombreUsuario.getText();
        String contraseña = new String(txtContraseñaUsuario.getPassword());

        String usuarioCorrecto = "meme";
        String contraseñaCorrecta = "123";

        if (nombreUsuario.equals(usuarioCorrecto) && contraseña.equals(contraseñaCorrecta)) {

            INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
            abrir.setVisible(true);
            this.setVisible(false);
        } else {

            javax.swing.JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnIngresarPineedActionPerformed

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
    private javax.swing.JPasswordField txtContraseñaUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
