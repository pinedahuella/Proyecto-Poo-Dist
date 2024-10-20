package InicioApp;

// Importación de clases necesarias para la funcionalidad de inicio de sesión
import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComboBox; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 * La clase INICIOPINEED representa la ventana principal de la aplicación,
 * donde los usuarios pueden acceder a diferentes funcionalidades
 * dependiendo de su rol (ADMINISTRADOR o SECRETARIA).
 */
public class INICIOPINEED extends javax.swing.JFrame {
    private String currentUser;  // Nombre de usuario actual
    private String userRole;      // Rol del usuario actual
    private LOGINPINEED loginFrame;  // Referencia al marco de inicio de sesión
    private JComboBox<String> comboBoxBotones; // ComboBox para seleccionar opciones del menú
    
    /**
     * Constructor de la clase INICIOPINEED.
     * 
     * @param username El nombre de usuario del usuario actual.
     * @param role El rol del usuario actual (ADMINISTRADOR o SECRETARIA).
     * @param loginFrame Referencia al marco de inicio de sesión.
     */
public INICIOPINEED(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        setupComboBox();
        setResizable(false);
        
        // Configurar para pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });
    }
    
   

    /**
     * Configura el ComboBox de opciones según el rol del usuario.
     */
    private void setupComboBox() {
        txtMenu.removeAllItems();
        txtMenu.addItem("Seleccione una opción");

        // Agrega opciones dependiendo del rol
        if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
            addAdminOptions();
        } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
            addSecretariaOptions();
        }

        // Añade un listener para redirigir a la opción seleccionada
        txtMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) txtMenu.getSelectedItem();
                redirectToFrame(selectedOption);
            }
        });
    }

    /**
     * Añade opciones al ComboBox para el rol de ADMINISTRADOR.
     */
    private void addAdminOptions() {
        txtMenu.addItem("Gestión de Usuarios");
        txtMenu.addItem("Gestión de Pilotos");
        txtMenu.addItem("Gestión de Créditos");
        txtMenu.addItem("Gestión de Clientes");
        txtMenu.addItem("Gestión de Ventas");
        txtMenu.addItem("Gestión de Pedidos");
        txtMenu.addItem("Inventario de Quintales");
        txtMenu.addItem("Planilla de Trabajadores");
        txtMenu.addItem("Gestión de Camiones");
        txtMenu.addItem("Calendario");
        txtMenu.addItem("Cerrar Sesión");
    }

    /**
     * Añade opciones al ComboBox para el rol de SECRETARIA.
     */
    private void addSecretariaOptions() {
        txtMenu.addItem("Gestión de Ventas");
        txtMenu.addItem("Planilla de Trabajadores");
        txtMenu.addItem("Cerrar Sesión");
    }

    /**
     * Redirige al marco correspondiente según la opción seleccionada.
     * 
     * @param option La opción seleccionada en el ComboBox.
     */
    private void redirectToFrame(String option) {
        switch (option) {
            case "Seleccione una opción":
                btnSeleccionarUnaOpcionActionPerformed(null);
                break;
            case "Gestión de Usuarios":
                btnGestionDeUsuariosActionPerformed(null);
                break;
            case "Gestión de Pilotos":
                btnGestionDePilotosActionPerformed(null);
                break;
            case "Gestión de Créditos":
                btnGestionDeCreditosActionPerformed(null);
                break;
            case "Gestión de Clientes":
                btnGestionDeClientesActionPerformed(null);
                break;
            case "Gestión de Ventas":
                btnGestionDeVentasActionPerformed(null);
                break;
            case "Gestión de Pedidos":
                btnGestionDePedidosActionPerformed(null);
                break;
            case "Inventario de Quintales":
                btnInventarioDeQuintalesActionPerformed(null);
                break;
            case "Planilla de Trabajadores":
                btnPlanillaDeTrabajadoresActionPerformed(null);
                break;
            case "Gestión de Camiones":
                btnGestionDeCamionesActionPerformed(null);
                break;
            case "Calendario":
                btnCalendarioActionPerformed(null);
                break;
            case "Cerrar Sesión":
                btnRegresarLoginActionPerformed(null);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Opción no válida");
                break;
        }
    }

    // Métodos de acción para cada opción del menú
    private void btnSeleccionarUnaOpcionActionPerformed(java.awt.event.ActionEvent evt) {                                                     
    }  
   
    private void btnGestionDeUsuariosActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        INICIOGESTIONUSUARIOS abrir = new INICIOGESTIONUSUARIOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }                                                    

    private void btnGestionDePilotosActionPerformed(java.awt.event.ActionEvent evt) {                                                    

        INICIOGESTIONPILOTOS abrir = new INICIOGESTIONPILOTOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
        
    }                                                   

    private void btnGestionDeCreditosActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // Lógica para gestión de créditos
    }                                                    

    private void btnGestionDeClientesActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // Lógica para gestión de clientes
    }                                                    

    private void btnGestionDeVentasActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // Lógica para gestión de ventas
    }                                                  

    private void btnGestionDePedidosActionPerformed(java.awt.event.ActionEvent evt) {                                                    

        FormularioPedidos abrir = new FormularioPedidos(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }                                                   

    private void btnInventarioDeQuintalesActionPerformed(java.awt.event.ActionEvent evt) {                                                         

        FrameInventario abrir = new FrameInventario(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }                                                        

    private void btnPlanillaDeTrabajadoresActionPerformed(java.awt.event.ActionEvent evt) {                                                          

        FramePlanillaSemanal abrir = new FramePlanillaSemanal(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }                                                         

    private void btnGestionDeCamionesActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }                                                                                                  


    private void btnRegresarLoginActionPerformed(java.awt.event.ActionEvent evt) {                                                 

        cerrarSesionYRegresarLogin();
        
    }                                                

    private void btnCalendarioActionPerformed(java.awt.event.ActionEvent evt) {                                              

        FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }     

    /**
     * Añade un listener para cerrar la ventana correctamente.
     */
    public void addWindowListener() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarSesionYSalir();
            }
        });
    }

    /**
     * Cierra la sesión y regresa al marco de inicio de sesión.
     */
    private void cerrarSesionYRegresarLogin() {
        cerrarSesionManualmente();
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true);
        this.dispose();
    }

    /**
     * Cierra la sesión del usuario manualmente y actualiza los registros de inicio de sesión.
     */
    private void cerrarSesionManualmente() {
        LocalDateTime tiempoSalida = LocalDateTime.now();
        GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
        gestionLogin.cargarLoginsDesdeExcel();
        
        // Actualiza el tiempo de salida en los registros
        for (Login login : gestionLogin.getLogins()) {
            if (login.getPersonal().equals(currentUser)) {
                login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                break;
            }
        }
        gestionLogin.guardarLoginsEnExcel();  // Guarda los registros actualizados
    }

    /**
     * Cierra la aplicación correctamente.
     */
    private void cerrarSesionYSalir() {
        cerrarSesionManualmente();
        System.exit(0);
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(1375, 752));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionMascota.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionCalendario.png"))); // NOI18N

        jTextField21.setEditable(false);
        jTextField21.setBackground(new java.awt.Color(0, 51, 102));
        jTextField21.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField21.setForeground(new java.awt.Color(255, 255, 255));
        jTextField21.setText("                PINEED");
        jTextField21.setBorder(null);

        jPanel8.setBackground(new java.awt.Color(32, 67, 99));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionCostales.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionLibreta.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addGap(83, 83, 83)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(529, 529, 529)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(INICIOPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Proporcionar valores reales para el nombre de usuario, rol y marco de inicio de sesión
                String username = "defaultUser";  // Reemplazar con el nombre de usuario real o lógica
                String role = "defaultRole";      // Reemplazar con el rol real
                LOGINPINEED loginFrame = new LOGINPINEED();  // Instanciar el objeto LOGINPINEED

                // Crear la instancia de INICIOPINEED con los parámetros requeridos
                new INICIOPINEED(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JComboBox<String> txtMenu;
    // End of variables declaration//GEN-END:variables
}
