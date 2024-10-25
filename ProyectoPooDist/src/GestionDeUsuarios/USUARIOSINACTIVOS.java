package GestionDeUsuarios;

import ControlCliente.FrameClientes;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import Login.GESTIONLOGIN;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Login.LOGINPINEED;
import Login.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class USUARIOSINACTIVOS extends javax.swing.JFrame {
    
    private GESTIONUSUARIOS gestionUsuarios;
    private Vector<Usuarios> listaUsuariosInactivos;
    private DefaultTableModel modeloUsuarios;
    private static final String EXCEL_PATH = "excels/USUARIOS.xlsx";
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    public USUARIOSINACTIVOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();

        this.gestionUsuarios = new GESTIONUSUARIOS();
        
        // Configuración inicial
        setResizable(false);
        inicializarTabla();
        cargarDatos();
        
        // Configuración de la interfaz
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Configuración adicional de la tabla
        tblRegistroUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroUsuarios.getTableHeader().setReorderingAllowed(false);
        tblRegistroUsuarios.getTableHeader().setResizingAllowed(false);
        tblRegistroUsuarios.setRowSelectionAllowed(true);
        tblRegistroUsuarios.setColumnSelectionAllowed(false);
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        setupComboBox();  // Añade esta línea

        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
    });
}

    private void inicializarTabla() {
        String[] columnas = {
            "Nombre Usuario", "Nombre", "Apellido", "DPI", "Cargo",
            "Correo Electrónico", "Número Telefónico", "Estado"
        };
        
        modeloUsuarios = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblRegistroUsuarios.setModel(modeloUsuarios);
    }

    private void cargarDatos() {
        try {
            gestionUsuarios.cargarUsuariosDesdeExcel();
            Vector<Usuarios> todosLosUsuarios = gestionUsuarios.getUsuarios();
            listaUsuariosInactivos = new Vector<>();
            modeloUsuarios.setRowCount(0);

            for (Usuarios usuario : todosLosUsuarios) {
                if ("INACTIVO".equals(usuario.getEstado())) {
                    Object[] fila = {
                        usuario.getNombreUsuario(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getNumeroDPI(),
                        usuario.getCargo(),
                        usuario.getCorreoElectronico(),
                        usuario.getNumeroTelefono(),
                        usuario.getEstado()
                    };
                    modeloUsuarios.addRow(fila);
                    listaUsuariosInactivos.add(usuario);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios inactivos: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error al cargar usuarios inactivos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
      

private void setupComboBox() {
    txtMenu10.removeAllItems();
    txtMenu10.addItem("Seleccione una opción");

    if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
        addAdminOptions();
    } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
        addSecretariaOptions();
    } else if (userRole.equalsIgnoreCase("PILOTO")) {
        addPilotOptions();
    }

    txtMenu10.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) txtMenu10.getSelectedItem();
            redirectToFrame(selectedOption);
        }
    });
}

private void addAdminOptions() {
    txtMenu10.addItem("Gestión de Usuarios");
    txtMenu10.addItem("Gestión de Pilotos");
    txtMenu10.addItem("Gestión de Clientes");
    txtMenu10.addItem("Gestión de Ventas");
    txtMenu10.addItem("Gestión de Pedidos");
    txtMenu10.addItem("Inventario de Quintales");
    txtMenu10.addItem("Planilla de Trabajadores");
    txtMenu10.addItem("Gestión de Camiones");
    txtMenu10.addItem("Calendario");
    txtMenu10.addItem("Cerrar Sesión");
}

private void addSecretariaOptions() {
    txtMenu10.addItem("Gestión de Ventas");
    txtMenu10.addItem("Gestión de Clientes");
    txtMenu10.addItem("Gestión de Camiones");
    txtMenu10.addItem("Gestión de Pedidos");
    txtMenu10.addItem("Gestión de Pilotos");
    txtMenu10.addItem("Calendario");
    txtMenu10.addItem("Cerrar Sesión");
}

private void addPilotOptions() {
    txtMenu10.addItem("Calendario");
    txtMenu10.addItem("Cerrar Sesión");
}
    
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


    private void btnSeleccionarUnaOpcionActionPerformed(java.awt.event.ActionEvent evt) {                                                     
    }  
    
    private void btnGestionDeUsuariosActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                    

    private void btnGestionDePilotosActionPerformed(java.awt.event.ActionEvent evt) {                                                    

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                   
                                          

    private void btnGestionDeClientesActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FrameClientes abrir = new  FrameClientes(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                    

    private void btnGestionDeVentasActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        FrameVentaDiaria abrir = new FrameVentaDiaria(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                  

    private void btnGestionDePedidosActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FormularioPedidos abrir = new  FormularioPedidos(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                   

    private void btnInventarioDeQuintalesActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FrameInventario abrir = new  FrameInventario(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                        

    private void btnPlanillaDeTrabajadoresActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FramePlanillaSemanal abrir = new FramePlanillaSemanal(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                         

    private void btnGestionDeCamionesActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
        
        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }                                                                                                  

    private void btnRegresarLoginActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        cerrarSesionYRegresarLogin();
    }                                                

    private void btnCalendarioActionPerformed(java.awt.event.ActionEvent evt) {                                              
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }     

 



    public void addWindowListener() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarSesionYSalir();
            }
        });
    }
     
private void cerrarSesionYRegresarLogin() {
        cerrarSesionManualmente();
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true);
        this.dispose();
    }

    private void cerrarSesionManualmente() {
        LocalDateTime tiempoSalida = LocalDateTime.now();
        GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
        gestionLogin.cargarLoginsDesdeExcel();
        
        boolean sesionCerrada = false;
        for (Login login : gestionLogin.getLogins()) {
            if (login.getPersonal().equals(currentUser) && login.getTiempoSalida().isEmpty()) {
                login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                gestionLogin.actualizarLogin(login);
                sesionCerrada = true;
                System.out.println("Sesión cerrada para el usuario: " + currentUser);
                break;
            }
        }
        
        if (!sesionCerrada) {
            System.out.println("No se encontró una sesión abierta para cerrar para el usuario: " + currentUser);
        }
    }

    private void cerrarSesionYSalir() {
        cerrarSesionManualmente();
        System.exit(0);
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        txtNombreUsuarioBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        buscarUsuario = new javax.swing.JButton();
        ActivarUsuarioEliminado = new javax.swing.JButton();
        ActivosUsuarios = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtMenu10 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" GESTION USUARIOS");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        tblRegistroUsuarios.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
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
        jScrollPane4.setViewportView(tblRegistroUsuarios);

        txtNombreUsuarioBuscar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        buscarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        buscarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        buscarUsuario.setText("BUSCAR");
        buscarUsuario.setBorder(null);
        buscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarUsuarioActionPerformed(evt);
            }
        });

        ActivarUsuarioEliminado.setBackground(new java.awt.Color(85, 111, 169));
        ActivarUsuarioEliminado.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivarUsuarioEliminado.setForeground(new java.awt.Color(255, 255, 255));
        ActivarUsuarioEliminado.setText("ACTIVAR");
        ActivarUsuarioEliminado.setBorder(null);
        ActivarUsuarioEliminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivarUsuarioEliminadoActionPerformed(evt);
            }
        });

        ActivosUsuarios.setBackground(new java.awt.Color(0, 153, 153));
        ActivosUsuarios.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivosUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        ActivosUsuarios.setText("REGRESAR A USUARIOS ACTIVOS");
        ActivosUsuarios.setBorder(null);
        ActivosUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivosUsuariosActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(32, 67, 99));
        jPanel8.setPreferredSize(new java.awt.Dimension(194, 34));

        txtMenu10.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenu10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(txtMenu10, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ActivarUsuarioEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ActivosUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivosUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivarUsuarioEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void txtMenu10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenu10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenu10ActionPerformed

    private void ActivosUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivosUsuariosActionPerformed
String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        INICIOGESTIONUSUARIOS abrir = new INICIOGESTIONUSUARIOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActivosUsuariosActionPerformed

    private void buscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioActionPerformed
        if (txtNombreUsuarioBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese el nombre del usuario para buscar.");
            return;
        }

        String nombreBuscado = txtNombreUsuarioBuscar.getText().trim().toLowerCase();
        modeloUsuarios.setRowCount(0);
        boolean encontrado = false;

        for (Usuarios usuario : listaUsuariosInactivos) {
            if (usuario.getNombre().toLowerCase().contains(nombreBuscado) ||
                usuario.getNombreUsuario().toLowerCase().contains(nombreBuscado)) {

                Object[] fila = {
                    usuario.getNombreUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getNumeroDPI(),
                    usuario.getCargo(),
                    usuario.getCorreoElectronico(),
                    usuario.getNumeroTelefono(),
                    usuario.getEstado()
                };
                modeloUsuarios.addRow(fila);
                encontrado = true;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron usuarios inactivos con el nombre especificado.");
            cargarDatos();
        }

        txtNombreUsuarioBuscar.setText("");
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    private void ActivarUsuarioEliminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarUsuarioEliminadoActionPerformed
        int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                long dpi = Long.parseLong(tblRegistroUsuarios.getValueAt(filaSeleccionada, 3).toString());

                int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea reactivar el usuario con DPI: " + dpi + "?",
                    "Confirmar reactivación",
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    gestionUsuarios.reactivarUsuario(dpi);
                    cargarDatos();
                    JOptionPane.showMessageDialog(this, "Usuario reactivado correctamente.");
                }
            } catch (Exception e) {
                System.err.println("Error al reactivar el usuario: " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                    "Error al reactivar el usuario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un usuario para reactivar.");
        }
    }//GEN-LAST:event_ActivarUsuarioEliminadoActionPerformed

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
            java.util.logging.Logger.getLogger(USUARIOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(USUARIOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(USUARIOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(USUARIOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                                String username = "defaultUser";  // Reemplaza con el nombre de usuario real o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea la instancia de INICIOGESTIONCAMIONES con los parámetros requeridos
                new USUARIOSINACTIVOS(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActivarUsuarioEliminado;
    private javax.swing.JButton ActivosUsuarios;
    private javax.swing.JButton buscarUsuario;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTable tblRegistroUsuarios;
    private javax.swing.JComboBox<String> txtMenu10;
    private javax.swing.JTextField txtNombreUsuarioBuscar;
    // End of variables declaration//GEN-END:variables
}
