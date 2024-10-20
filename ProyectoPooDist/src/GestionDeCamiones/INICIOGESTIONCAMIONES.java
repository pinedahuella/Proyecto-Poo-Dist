package GestionDeCamiones;

import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import Inicio.INICIOPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.JButton;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import java.awt.event.ActionEvent;  // Para representar eventos de acción
import javax.swing.SwingUtilities;



public class INICIOGESTIONCAMIONES extends javax.swing.JFrame {
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    DefaultTableModel modeloCamiones = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };






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

     
    public INICIOGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        
        String[] columnas = {"Placas", "Modelo", "Marca", "Estado", "Tipo Combustible", "Kilometraje"};
        modeloCamiones.setColumnIdentifiers(columnas);
        
        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
        }
        
        tblRegistroCamiones1.setModel(modeloCamiones);
        tblRegistroCamiones1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroCamiones1.getTableHeader().setReorderingAllowed(false);
        tblRegistroCamiones1.getTableHeader().setResizingAllowed(false);
        tblRegistroCamiones1.setRowSelectionAllowed(true);
        tblRegistroCamiones1.setColumnSelectionAllowed(false);
        
        cargarCamionesEnTabla();
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
    
    



    private void cargarCamionesEnTabla() {
        for (Camiones camion : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camion.getPlacas(),
                camion.getModelo(),
                camion.getMarca(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje()
            });
        }
    }


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

        if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("SECRETARIA")) {
            FramePlanillaSemanal abrir = new FramePlanillaSemanal(currentUser, userRole, loginFrame);
            abrir.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "No tienes permiso para acceder a este módulo.");
        }
    }                                                         

    private void btnGestionDeCamionesActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("SECRETARIA")) {
            INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
            abrir.setVisible(true);
            this.setVisible(false);
        }
    }                                                    

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                
    cerrarSesionYSalir();
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

        if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("USUARIO")) {
            FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
            abrir.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "No tienes permiso para acceder a este módulo.");
        }
    }     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMarcaCamionBuscar1 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroCamiones1 = new javax.swing.JTable();
        buscarCamion = new javax.swing.JButton();
        refrescarCamion = new javax.swing.JButton();
        agregarCamion = new javax.swing.JButton();
        mostrarCamion = new javax.swing.JButton();
        editarCamion = new javax.swing.JButton();
        eliminarCamion = new javax.swing.JButton();
        garageCamiones = new javax.swing.JButton();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(32, 67, 99));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" GESTION CAMIONES");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("MARCA");

        txtMarcaCamionBuscar1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        tblRegistroCamiones1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tblRegistroCamiones1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblRegistroCamiones1);

        buscarCamion.setBackground(new java.awt.Color(85, 111, 169));
        buscarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarCamion.setForeground(new java.awt.Color(255, 255, 255));
        buscarCamion.setText("BUSCAR");
        buscarCamion.setBorder(null);
        buscarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCamionActionPerformed(evt);
            }
        });

        refrescarCamion.setBackground(new java.awt.Color(85, 111, 169));
        refrescarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        refrescarCamion.setForeground(new java.awt.Color(255, 255, 255));
        refrescarCamion.setText("REFRESCAR");
        refrescarCamion.setBorder(null);
        refrescarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarCamionActionPerformed(evt);
            }
        });

        agregarCamion.setBackground(new java.awt.Color(85, 111, 169));
        agregarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        agregarCamion.setForeground(new java.awt.Color(255, 255, 255));
        agregarCamion.setText("AGREGAR");
        agregarCamion.setBorder(null);
        agregarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarCamionActionPerformed(evt);
            }
        });

        mostrarCamion.setBackground(new java.awt.Color(85, 111, 169));
        mostrarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        mostrarCamion.setForeground(new java.awt.Color(255, 255, 255));
        mostrarCamion.setText("MOSTRAR");
        mostrarCamion.setBorder(null);
        mostrarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarCamionActionPerformed(evt);
            }
        });

        editarCamion.setBackground(new java.awt.Color(85, 111, 169));
        editarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        editarCamion.setForeground(new java.awt.Color(255, 255, 255));
        editarCamion.setText("EDITAR");
        editarCamion.setBorder(null);
        editarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarCamionActionPerformed(evt);
            }
        });

        eliminarCamion.setBackground(new java.awt.Color(85, 111, 169));
        eliminarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarCamion.setForeground(new java.awt.Color(255, 255, 255));
        eliminarCamion.setText("ELIMINAR");
        eliminarCamion.setBorder(null);
        eliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCamionActionPerformed(evt);
            }
        });

        garageCamiones.setBackground(new java.awt.Color(85, 111, 169));
        garageCamiones.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        garageCamiones.setForeground(new java.awt.Color(255, 255, 255));
        garageCamiones.setText("GARAGE");
        garageCamiones.setBorder(null);
        garageCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                garageCamionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMarcaCamionBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(editarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(eliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(garageCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMarcaCamionBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(garageCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void buscarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCamionActionPerformed
                if (txtMarcaCamionBuscar1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa la marca del camión para buscar.");
            return;
        }

        String marcaBuscada = txtMarcaCamionBuscar1.getText().trim();
        modeloCamiones.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Camiones camion : listaCamiones) {
            if (camion.getMarca().toLowerCase().contains(marcaBuscada.toLowerCase())) {
                modeloCamiones.addRow(new Object[]{
                    camion.getPlacas(),
                    camion.getModelo(),
                    camion.getMarca(),
                    camion.getEstado(),
                    camion.getTipoCombustible(),
                    camion.getKilometraje()
                });
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron camiones de la marca especificada.");
            cargarCamionesEnTabla();
        } else {
            tblRegistroCamiones1.setVisible(true);
            if (tblRegistroCamiones1.getRowCount() > 0) {
                tblRegistroCamiones1.setRowSelectionInterval(0, 0);
            }
        }

        txtMarcaCamionBuscar1.setText("");
    }//GEN-LAST:event_buscarCamionActionPerformed

    private void refrescarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarCamionActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarCamionActionPerformed

    private void agregarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarCamionActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        AGREGARGESTIONCAMIONES abrir = new  AGREGARGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_agregarCamionActionPerformed

    private void mostrarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarCamionActionPerformed
        int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Camiones camionSeleccionado = listaCamiones.get(filaSeleccionada);
            abrirVentanaMostrar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarCamionActionPerformed

    private void editarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCamionActionPerformed
        int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Camiones camionSeleccionado = listaCamiones.get(filaSeleccionada);
            abrirVentanaModificar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un camión para modificar.");
        }
    }//GEN-LAST:event_editarCamionActionPerformed

    private void eliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCamionActionPerformed
int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String placasSeleccionadas = (String) tblRegistroCamiones1.getValueAt(filaSeleccionada, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar este camión con placas: " + placasSeleccionadas + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gestionCamiones.eliminarCamion(placasSeleccionadas);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Camión eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión para eliminar.");
        }
    }//GEN-LAST:event_eliminarCamionActionPerformed

    private void garageCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_garageCamionesActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        GARAGEGESTIONCAMIONES abrir = new  GARAGEGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_garageCamionesActionPerformed

    

    public void actualizarTabla() {
        gestionCamiones.cargarCamionesDesdeExcel();
        listaCamiones = gestionCamiones.getCamiones();
        modeloCamiones.setRowCount(0);
        cargarCamionesEnTabla();
    }

private void abrirVentanaModificar(Camiones camion) {
    String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

    MODIFICARGESTIONCAMIONES ventanaModificar = new MODIFICARGESTIONCAMIONES(camion, this, username, role, loginFrame);
    ventanaModificar.setVisible(true);
}

    private void abrirVentanaMostrar(Camiones camion) {
        MOSTRARGESTIONCAMIONES ventanaMostrar = new MOSTRARGESTIONCAMIONES(camion, this);
        ventanaMostrar.setVisible(true);
    }
    
    
    

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
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

               
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new INICIOGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            
            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarCamion;
    private javax.swing.JButton buscarCamion;
    private javax.swing.JButton editarCamion;
    private javax.swing.JButton eliminarCamion;
    private javax.swing.JButton garageCamiones;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton mostrarCamion;
    private javax.swing.JButton refrescarCamion;
    private javax.swing.JTable tblRegistroCamiones1;
    private javax.swing.JTextField txtMarcaCamionBuscar1;
    private javax.swing.JComboBox<String> txtMenu;
    // End of variables declaration//GEN-END:variables
}
