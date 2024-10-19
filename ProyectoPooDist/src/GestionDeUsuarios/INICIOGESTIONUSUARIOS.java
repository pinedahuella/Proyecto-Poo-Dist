package GestionDeUsuarios;

import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import Inicio.INICIOPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlViajes.FormularioViajes;
import GestionDeUsuarios.AGREGARGESTIONUSUARIOS;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INGRESOGESTIONUSUARIOS;
import GestionDeUsuarios.MODIFICARGESTIONUSUARIOS;
import GestionDeUsuarios.MOSTRARGESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import Login.LOGINPINEED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import java.awt.event.ActionEvent;  // Para representar eventos de acción
import javax.swing.SwingUtilities;



public class INICIOGESTIONUSUARIOS extends javax.swing.JFrame {
    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    DefaultTableModel modeloUsuarios = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
private void setupComboBox() {
        txtMenu.removeAllItems();
        txtMenu.addItem("Seleccione una opción");

        if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
            addAdminOptions();
        } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
            addSecretariaOptions();
        } else if (userRole.equalsIgnoreCase("USUARIO")) {
            addUsuarioOptions();
        }

        txtMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) txtMenu.getSelectedItem();
                redirectToFrame(selectedOption);
            }
        });
    }






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
        txtMenu.addItem("Login");
        txtMenu.addItem("Cerrar Sesión");
    }

    private void addSecretariaOptions() {
        txtMenu.addItem("Gestión de Ventas");
        txtMenu.addItem("Planilla de Trabajadores");
        txtMenu.addItem("Login");
        txtMenu.addItem("Cerrar Sesión");
    }

    private void addUsuarioOptions() {
        txtMenu.addItem("Calendario");
        txtMenu.addItem("Login");
        txtMenu.addItem("Cerrar Sesión");
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
        case "Login":
            btnRegresarLoginActionPerformed(null);
            break;
        case "Cerrar Sesión":
            btnCerrarSesionActionPerformed(null);
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

    private void btnGestionDeCreditosActionPerformed(java.awt.event.ActionEvent evt) {                                                     

    }                                                    

    private void btnGestionDeClientesActionPerformed(java.awt.event.ActionEvent evt) {                                                     

    }                                                    

    private void btnGestionDeVentasActionPerformed(java.awt.event.ActionEvent evt) {                                                   

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

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                
        System.exit(0);
    }                                               

    private void btnRegresarLoginActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        LOGINPINEED abrir = new  LOGINPINEED();
        abrir.setVisible(true);
        this.setVisible(false);
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
     

    private void cerrarSesionYSalir() {
        if (loginFrame != null) {
            loginFrame.cerrarSesion(currentUser, userRole);
        }
        // Crear una nueva instancia de LOGINPINEED sin pasar argumentos nulos
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true);
        this.dispose();
    }
    
    
    

    
    public INICIOGESTIONUSUARIOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        
        String[] columnas = {"Nombre", "Apellido", "DPI", "Cargo", "Teléfono", "Estado"};
        modeloUsuarios.setColumnIdentifiers(columnas);
        
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }
        
        tblRegistroUsuarios.setModel(modeloUsuarios);
        tblRegistroUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroUsuarios.getTableHeader().setReorderingAllowed(false);
        tblRegistroUsuarios.getTableHeader().setResizingAllowed(false);
        tblRegistroUsuarios.setRowSelectionAllowed(true);
        tblRegistroUsuarios.setColumnSelectionAllowed(false);
        
        cargarUsuariosEnTabla();
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
    
   
       
       
private void cargarUsuariosEnTabla() {
    for (Usuarios usuario : listaUsuarios) {
        modeloUsuarios.addRow(new Object[]{
            
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getNumeroDPI(),
            usuario.getCargo(),
            usuario.getNumeroTelefono(),
            usuario.getEstado(),
            usuario.getCorreoElectronico(),
            usuario.getGenero(),
            usuario.getFechaNacimiento(),
            usuario.getNombreUsuario(),
            usuario.getContrasenaUsuario(),
            

        });
    }
}


private void abrirVentanaModificar(Usuarios usuario) {
    
    String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

    MODIFICARGESTIONUSUARIOS ventanaModificar = new MODIFICARGESTIONUSUARIOS(usuario, this, username, role, loginFrame);
    ventanaModificar.setVisible(true);
}


private void abrirVentanaMostrar(Usuarios usuario) {
        MOSTRARGESTIONUSUARIOS ventanaMostrar = new MOSTRARGESTIONUSUARIOS(usuario, this);
        ventanaMostrar.setVisible(true);
    }
    
public void actualizarTabla() {
        gestionUsuarios.cargarUsuariosDesdeExcel();
        listaUsuarios = gestionUsuarios.getUsuarios();
        modeloUsuarios.setRowCount(0); // Limpiar la tabla
        for (Usuarios usuario : listaUsuarios) {
            modeloUsuarios.addRow(new Object[]{
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getNumeroDPI(),
            usuario.getCargo(),
            usuario.getNumeroTelefono(),
            usuario.getEstado(),
            usuario.getCorreoElectronico(),
            usuario.getGenero(),
            usuario.getFechaNacimiento(),
            usuario.getNombreUsuario(),
            usuario.getContrasenaUsuario(),
            });
        }
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        txtNombreUsuarioBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        agregarUsuario = new javax.swing.JButton();
        eliminarUsuario = new javax.swing.JButton();
        editarUsuario = new javax.swing.JButton();
        mostrarUsuario = new javax.swing.JButton();
        refrescarUsuario = new javax.swing.JButton();
        entradasUsuario = new javax.swing.JButton();
        buscarUsuario = new javax.swing.JButton();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(32, 67, 99));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

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

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        agregarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        agregarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        agregarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        agregarUsuario.setText("AGREGAR");
        agregarUsuario.setBorder(null);
        agregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarUsuarioActionPerformed(evt);
            }
        });

        eliminarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        eliminarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        eliminarUsuario.setText("ELIMINAR");
        eliminarUsuario.setBorder(null);
        eliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuarioActionPerformed(evt);
            }
        });

        editarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        editarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        editarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        editarUsuario.setText("EDITAR");
        editarUsuario.setBorder(null);
        editarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarUsuarioActionPerformed(evt);
            }
        });

        mostrarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        mostrarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        mostrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        mostrarUsuario.setText("MOSTRAR");
        mostrarUsuario.setBorder(null);
        mostrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarUsuarioActionPerformed(evt);
            }
        });

        refrescarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        refrescarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        refrescarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        refrescarUsuario.setText("REFRESCAR");
        refrescarUsuario.setBorder(null);
        refrescarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarUsuarioActionPerformed(evt);
            }
        });

        entradasUsuario.setBackground(new java.awt.Color(102, 102, 255));
        entradasUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        entradasUsuario.setForeground(new java.awt.Color(255, 255, 255));
        entradasUsuario.setText("ENTRADAS");
        entradasUsuario.setBorder(null);
        entradasUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradasUsuarioActionPerformed(evt);
            }
        });

        buscarUsuario.setBackground(new java.awt.Color(102, 102, 255));
        buscarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        buscarUsuario.setText("BUSCAR");
        buscarUsuario.setBorder(null);
        buscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refrescarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entradasUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entradasUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void mostrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarUsuarioActionPerformed
        int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Usuarios usuarioSeleccionado = listaUsuarios.get(filaSeleccionada);
            abrirVentanaMostrar(usuarioSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarUsuarioActionPerformed

    private void agregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarUsuarioActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        AGREGARGESTIONUSUARIOS abrir = new  AGREGARGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_agregarUsuarioActionPerformed

    private void buscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioActionPerformed
                if (txtNombreUsuarioBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
            return;
        }

        String nombreBuscado = txtNombreUsuarioBuscar.getText().trim();
        modeloUsuarios.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Usuarios usuarios : listaUsuarios) {
            boolean coincide = true;

            if (!nombreBuscado.isEmpty() && !usuarios.getNombre().equalsIgnoreCase(nombreBuscado)) {
                coincide = false;
            }

            if (coincide) {
                modeloUsuarios.addRow(new Object[]{
                    usuarios.getNombre(),
                    usuarios.getApellido(),
                    usuarios.getNumeroDPI(),
                    usuarios.getCargo(),
                    usuarios.getNumeroTelefono(),
                    usuarios.getEstado(),
                    usuarios.getCorreoElectronico(),
                    usuarios.getGenero(),
                    usuarios.getFechaNacimiento(),
                    usuarios.getNombreUsuario(),
                    usuarios.getContrasenaUsuario(),
                });
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
            for (Usuarios usuario : listaUsuarios) {
                modeloUsuarios.addRow(new Object[]{
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getNumeroDPI(),
                    usuario.getCargo(),
                    usuario.getNumeroTelefono(),
                    usuario.getEstado(),
                    usuario.getCorreoElectronico(),
                    usuario.getGenero(),
                    usuario.getFechaNacimiento(),
                    usuario.getNombreUsuario(),
                    usuario.getContrasenaUsuario(),
                });
            }
        } else {
            tblRegistroUsuarios.setVisible(true);
            if (tblRegistroUsuarios.getRowCount() > 0) {
                tblRegistroUsuarios.setRowSelectionInterval(0, 0);
            }
        }

        txtNombreUsuarioBuscar.setText("");
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    private void entradasUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradasUsuarioActionPerformed
               INGRESOGESTIONUSUARIOS abrir = new  INGRESOGESTIONUSUARIOS();
        abrir.setVisible(true);
    }//GEN-LAST:event_entradasUsuarioActionPerformed

    private void editarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarUsuarioActionPerformed
                        int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Usuarios usuarioSeleccionado = listaUsuarios.get(filaSeleccionada);
            abrirVentanaModificar(usuarioSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario para modificar.");
        }
    }//GEN-LAST:event_editarUsuarioActionPerformed

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
                        int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreSeleccionado = (String) tblRegistroUsuarios.getValueAt(filaSeleccionada, 0);
            String apellidoSeleccionado = (String) tblRegistroUsuarios.getValueAt(filaSeleccionada, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar este usuario: " + nombreSeleccionado + " " + apellidoSeleccionado + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gestionUsuarios.eliminarUsuario(nombreSeleccionado, apellidoSeleccionado);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para eliminar.");
        }
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void refrescarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarUsuarioActionPerformed
                String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarUsuarioActionPerformed

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
            java.util.logging.Logger.getLogger(INICIOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                             
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new INICIOGESTIONUSUARIOS(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarUsuario;
    private javax.swing.JButton buscarUsuario;
    private javax.swing.JButton editarUsuario;
    private javax.swing.JButton eliminarUsuario;
    private javax.swing.JButton entradasUsuario;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton mostrarUsuario;
    private javax.swing.JButton refrescarUsuario;
    private javax.swing.JTable tblRegistroUsuarios;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNombreUsuarioBuscar;
    // End of variables declaration//GEN-END:variables
}
