package GestionDeUsuarios;

import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import Login.GESTIONLOGIN;
import Login.LOGINPINEED;
import Login.Login;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class INGRESOGESTIONUSUARIOS extends javax.swing.JFrame {
    private GESTIONUSUARIOS gestionUsuarios;
    private Vector<Usuarios> listaUsuarios;
    private GESTIONLOGIN gestionLogin;  // Instance for managing login records
    private DefaultTableModel modeloUsuarios;  // Model for the user table
    private DefaultTableModel modeloLogins;    // Model for the login table
    private Usuarios usuarioActual;
    private INICIOGESTIONUSUARIOS ventanaPrincipal;

    // Constructor
    public INGRESOGESTIONUSUARIOS() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initializeData(); // Separate method to initialize data
        configurarTablaLogins();  // Set up the login table
        cargarDatosLogin();  // Load login data into the table
    }

    // Method to initialize user and login data
    private void initializeData() {
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        listaUsuarios = gestionUsuarios.getUsuarios();
        
        gestionLogin = new GESTIONLOGIN();  // Initialize GESTIONLOGIN
        gestionLogin.cargarLoginsDesdeExcel();  // Load login data from Excel
    }



    // Setup the login table
    private void configurarTablaLogins() {
        modeloLogins = new DefaultTableModel(new Object[]{"Tiempo Entrada", "Tiempo Salida", "Personal", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Disable cell editing
            }
        };

        tblRegistroLogins.setModel(modeloLogins); // Ensure this is the login table

        // Disable column reordering and cell selection
        tblRegistroLogins.getTableHeader().setReorderingAllowed(false);
        tblRegistroLogins.setColumnSelectionAllowed(false);
        tblRegistroLogins.setRowSelectionAllowed(true);
        tblRegistroLogins.setCellSelectionEnabled(false);
    }

    // Load login data into the table
    private void cargarDatosLogin() {
        modeloLogins.setRowCount(0);  // Clear the table
        Vector<Login> logins = gestionLogin.getLogins();

        for (Login login : logins) {
            modeloLogins.addRow(new Object[]{
                login.getTiempoEntrada(),
                login.getTiempoSalida(),
                login.getPersonal(),
                login.getRol()
            });
        }
    }

    // Refresh the login table when necessary
    public void refreshLoginTable() {
        cargarDatosLogin();  // Populate the login table
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroLogins = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel8.setBackground(new java.awt.Color(6, 40, 86));

        tblRegistroLogins.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblRegistroLogins);

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel3.setText("ENTRADAS / SALIDAS");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.util.logging.Logger.getLogger(INGRESOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(INGRESOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(INGRESOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(INGRESOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            // Create the INICIOPINEED instance with the required parameters
            new INGRESOGESTIONUSUARIOS().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblRegistroLogins;
    // End of variables declaration//GEN-END:variables
}
