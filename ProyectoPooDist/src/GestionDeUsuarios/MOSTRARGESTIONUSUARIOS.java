package GestionDeUsuarios;

import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class MOSTRARGESTIONUSUARIOS extends javax.swing.JFrame {
    private GESTIONUSUARIOS gestionUsuarios;
    private Vector<Usuarios> listaUsuarios;
    private DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private Usuarios usuarioActual;
    private INICIOGESTIONUSUARIOS ventanaPrincipal;
    

public MOSTRARGESTIONUSUARIOS() {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    gestionUsuarios = new GESTIONUSUARIOS();
    gestionUsuarios.cargarUsuariosDesdeExcel();
    listaUsuarios = gestionUsuarios.getUsuarios();
    configurarTabla();
}

    

    public MOSTRARGESTIONUSUARIOS(Usuarios usuario, INICIOGESTIONUSUARIOS ventanaPrincipal) {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    this.ventanaPrincipal = ventanaPrincipal;
    
    if (ventanaPrincipal != null) {
        this.gestionUsuarios = ventanaPrincipal.gestionUsuarios;
        this.listaUsuarios = gestionUsuarios.getUsuarios();
    } else {
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        listaUsuarios = gestionUsuarios.getUsuarios();
    }
    
    this.usuarioActual = usuario;
    configurarTabla();
    if (usuario != null) {
        cargarDatosUsuario();
    }
}

private void configurarTabla() {

    modeloUsuarios = new DefaultTableModel(new Object[]{"Identificadores", "Datos"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Todas las celdas no son editables
            return false;
        }
    };
    
    tblRegistroUsuarios.setModel(modeloUsuarios);
    tblRegistroUsuarios.setCellSelectionEnabled(true);
    tblRegistroUsuarios.setFocusable(true);
    tblRegistroUsuarios.setRowSelectionAllowed(true);
    tblRegistroUsuarios.setColumnSelectionAllowed(false);
    
    // Permitir que el usuario copie datos seleccionados
    tblRegistroUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
}

private void cargarDatosUsuario() {
    if (usuarioActual != null) {
        modeloUsuarios.setRowCount(0);
        
        // Show "*****" for the password by default
        String maskedPassword = "*****";

        modeloUsuarios.addRow(new Object[]{"Usuario", usuarioActual.getNombreUsuario()});
        modeloUsuarios.addRow(new Object[]{"Contraseña", maskedPassword});
        modeloUsuarios.addRow(new Object[]{"Nombre", usuarioActual.getNombre()});
        modeloUsuarios.addRow(new Object[]{"Apellido", usuarioActual.getApellido()});
        modeloUsuarios.addRow(new Object[]{"Cargo", usuarioActual.getCargo()});
        modeloUsuarios.addRow(new Object[]{"Género", usuarioActual.getGenero()});
        modeloUsuarios.addRow(new Object[]{"Número de DPI", usuarioActual.getNumeroDPI()});
        modeloUsuarios.addRow(new Object[]{"Fecha de Nacimiento", usuarioActual.getFechaNacimiento()});
        modeloUsuarios.addRow(new Object[]{"Número Telefónico", usuarioActual.getNumeroTelefono()});
        modeloUsuarios.addRow(new Object[]{"Correo Electrónico", usuarioActual.getCorreoElectronico()});
        modeloUsuarios.addRow(new Object[]{"Estado", usuarioActual.getEstado()});
    }
}
    
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnMostrarContraseña = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel14.setBackground(new java.awt.Color(32, 67, 99));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        tblRegistroUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblRegistroUsuarios);

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel6.setText("INFORMACIÓN DEL USUARIO");

        btnMostrarContraseña.setBackground(new java.awt.Color(204, 153, 255));
        btnMostrarContraseña.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        btnMostrarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarContraseña.setText("MOSTRAR CONTRASEÑA");
        btnMostrarContraseña.setBorder(null);
        btnMostrarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 48, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean isPasswordVisible = false;

    private void btnMostrarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarContraseñaActionPerformed
int passwordRowIndex = 1; // Assuming "Contraseña" is at index 1 in the table
    
    if (!isPasswordVisible) {
        // Show the actual password
        modeloUsuarios.setValueAt(usuarioActual.getContrasenaUsuario(), passwordRowIndex, 1);
        btnMostrarContraseña.setText("OCULTAR CONTRASEÑA");
    } else {
        // Hide the password by showing "*****"
        modeloUsuarios.setValueAt("*****", passwordRowIndex, 1);
        btnMostrarContraseña.setText("MOSTRAR CONTRASEÑA");
    }
    
    isPasswordVisible = !isPasswordVisible;
    }//GEN-LAST:event_btnMostrarContraseñaActionPerformed

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
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MOSTRARGESTIONUSUARIOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMostrarContraseña;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblRegistroUsuarios;
    // End of variables declaration//GEN-END:variables
}
