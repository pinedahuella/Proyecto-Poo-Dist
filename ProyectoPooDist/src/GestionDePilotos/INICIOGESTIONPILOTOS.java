package GestionDePilotos;
import ControlCliente.FrameClientes;
import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import InicioApp.INICIOPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import javax.swing.SwingUtilities;


public class INICIOGESTIONPILOTOS extends javax.swing.JFrame {
protected GESTIONPILOTOS gestionPilotos;
    private Vector<Piloto> listaPilotos;
    private DefaultTableModel modeloPilotos = new DefaultTableModel();
    private LOGINPINEED loginFrame;
    private String currentUser;
    private String userRole;


     // Agregar un método getter para gestionPilotos
    public GESTIONPILOTOS getGestionPilotos() {
        return gestionPilotos;
    }


private void cargarPilotosActivos() {
        modeloPilotos.setRowCount(0); // Clear existing rows
        
        if (listaPilotos != null) {
            for (Piloto piloto : listaPilotos) {
                // Double check active status
                if (piloto.isActivo()) {
                    Object[] fila = new Object[6];
                    fila[0] = piloto.getNombrePiloto();
                    fila[1] = piloto.getApellidoPiloto();
                    fila[2] = piloto.getNumeroDeDpi();
                    fila[3] = piloto.getTipoLicencia();
                    fila[4] = piloto.getNumeroTelefonicoPiloto();
                    fila[5] = piloto.getEstadoPiloto();
                    modeloPilotos.addRow(fila);
                }
            }
        }
    }
    
    // Method to refresh the table (call this after any changes to pilots)
    public void refreshTable() {
        gestionPilotos.cargarPilotosDesdeExcel();
        listaPilotos = new Vector<>();
        for (Piloto piloto : gestionPilotos.getPilotos()) {
            if (piloto.isActivo()) {
                listaPilotos.add(piloto);
            }
        }
        cargarPilotosActivos();
    }
    
    
    
private void setupComboBox() {
    txtMenu.removeAllItems();
    txtMenu.addItem("Seleccione una opción");

    if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
        addAdminOptions();
    } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
        addSecretariaOptions();
    } else if (userRole.equalsIgnoreCase("PILOTO")) {
        addPilotOptions();
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
    txtMenu.addItem("Gestión de Clientes");
    txtMenu.addItem("Gestión de Ventas");
    txtMenu.addItem("Gestión de Pedidos");
    txtMenu.addItem("Inventario de Quintales");
    txtMenu.addItem("Planilla de Trabajadores");
    txtMenu.addItem("Gestión de Camiones");
    txtMenu.addItem("Calendario");
    txtMenu.addItem("Cerrar Sesión");
}

private void addSecretariaOptions() {
    txtMenu.addItem("Gestión de Ventas");
    txtMenu.addItem("Gestión de Clientes");
    txtMenu.addItem("Gestión de Camiones");
    txtMenu.addItem("Gestión de Pedidos");
    txtMenu.addItem("Gestión de Pilotos");
    txtMenu.addItem("Calendario");
    txtMenu.addItem("Cerrar Sesión");
}

private void addPilotOptions() {
    txtMenu.addItem("Calendario");
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

public INICIOGESTIONPILOTOS(String username, String role, LOGINPINEED loginFrame) {
        // Initialize base frame components first
        initComponents();
            gestionPilotos = new GESTIONPILOTOS();

        // Set frame properties
        setResizable(false);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Initialize instance variables
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        this.gestionPilotos = new GESTIONPILOTOS();
        
        // Initialize table model and set columns
        modeloPilotos = new DefaultTableModel();
        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Teléfono", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);
        
        // Configure table properties
        tblRegistroPilotos.setModel(modeloPilotos);
        tblRegistroPilotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroPilotos.getTableHeader().setReorderingAllowed(false);
        tblRegistroPilotos.getTableHeader().setResizingAllowed(false);
        tblRegistroPilotos.setRowSelectionAllowed(true);
        tblRegistroPilotos.setColumnSelectionAllowed(false);
        
        // Load and filter active pilots
        gestionPilotos.cargarPilotosDesdeExcel();
        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = new Vector<>();
            for (Piloto piloto : gestionPilotos.getPilotos()) {
                if (piloto.isActivo()) {  // Only add active pilots
                    listaPilotos.add(piloto);
                }
            }
        }
        
        // Load pilots into table
        cargarPilotosActivos();
        
        // Additional setup
        addWindowListener();
        setupComboBox();
        
        // Final frame setup
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });
    }
    
        

    private void cargarPilotosEnTabla() {
        for (Piloto piloto : listaPilotos) {
            modeloPilotos.addRow(new Object[]{
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getEstadoPiloto(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento()
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
        tblRegistroPilotos = new javax.swing.JTable();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        editarPiloto = new javax.swing.JButton();
        mostrarPiloto = new javax.swing.JButton();
        agregarPiloto = new javax.swing.JButton();
        refrescarPiloto = new javax.swing.JButton();
        buscarPiloto = new javax.swing.JButton();
        eliminarPiloto = new javax.swing.JButton();
        ActivosPilotos = new javax.swing.JButton();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(32, 67, 99));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" GESTION PILOTOS");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        tblRegistroPilotos.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tblRegistroPilotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblRegistroPilotos);

        txtNombrePilotoBuscar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        editarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        editarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        editarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        editarPiloto.setText("EDITAR");
        editarPiloto.setBorder(null);
        editarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarPilotoActionPerformed(evt);
            }
        });

        mostrarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        mostrarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        mostrarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        mostrarPiloto.setText("MOSTRAR");
        mostrarPiloto.setBorder(null);
        mostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarPilotoActionPerformed(evt);
            }
        });

        agregarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        agregarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        agregarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        agregarPiloto.setText("AGREGAR");
        agregarPiloto.setBorder(null);
        agregarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPilotoActionPerformed(evt);
            }
        });

        refrescarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        refrescarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        refrescarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        refrescarPiloto.setText("REFRESCAR");
        refrescarPiloto.setBorder(null);
        refrescarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPilotoActionPerformed(evt);
            }
        });

        buscarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        buscarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        buscarPiloto.setText("BUSCAR");
        buscarPiloto.setBorder(null);
        buscarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPilotoActionPerformed(evt);
            }
        });

        eliminarPiloto.setBackground(new java.awt.Color(85, 111, 169));
        eliminarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        eliminarPiloto.setText("ELIMINAR");
        eliminarPiloto.setBorder(null);
        eliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPilotoActionPerformed(evt);
            }
        });

        ActivosPilotos.setBackground(new java.awt.Color(0, 153, 153));
        ActivosPilotos.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivosPilotos.setForeground(new java.awt.Color(255, 255, 255));
        ActivosPilotos.setText("ACTIVAR PILOTOS ELIMINADOS");
        ActivosPilotos.setBorder(null);
        ActivosPilotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivosPilotosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void editarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarPilotoActionPerformed
        int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);
            abrirVentanaModificar(pilotoSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un piloto para modificar.");
        }
    }//GEN-LAST:event_editarPilotoActionPerformed

    private void mostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarPilotoActionPerformed
        int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);
            abrirVentanaMostrar(pilotoSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarPilotoActionPerformed

    private void agregarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPilotoActionPerformed
String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        // Create the INICIOPINEED instance with the correct arguments
        AGREGARGESTIONPILOTOS abrir = new AGREGARGESTIONPILOTOS(username, role, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_agregarPilotoActionPerformed

    private void refrescarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPilotoActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPilotoActionPerformed

    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
        if (txtNombrePilotoBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
            return;
        }

        String nombreBuscado = txtNombrePilotoBuscar.getText().trim();
        modeloPilotos.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Piloto piloto : listaPilotos) {
            boolean coincide = true;

            if (!nombreBuscado.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado)) {
                coincide = false;
            }

            if (coincide) {
                modeloPilotos.addRow(new Object[]{
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getGeneroPiloto(),
                    piloto.getFechaDeNacimiento()
                });
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
            for (Piloto piloto : listaPilotos) {
                modeloPilotos.addRow(new Object[]{
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getGeneroPiloto(),
                    piloto.getFechaDeNacimiento()
                });
            }
        } else {
            tblRegistroPilotos.setVisible(true);
            if (tblRegistroPilotos.getRowCount() > 0) {
                tblRegistroPilotos.setRowSelectionInterval(0, 0);
            }
        }

        txtNombrePilotoBuscar.setText("");
    }//GEN-LAST:event_buscarPilotoActionPerformed

    private void eliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPilotoActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            // Retrieve DPI and convert it to long
            Object dpiObject = tblRegistroPilotos.getValueAt(filaSeleccionada, 2);
            long numeroDeDpiSeleccionado = Long.parseLong(dpiObject.toString());
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar este piloto con DPI: " + numeroDeDpiSeleccionado + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Now passing a long value
                gestionPilotos.eliminarPiloto(numeroDeDpiSeleccionado);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Piloto eliminado correctamente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error: El DPI no tiene un formato válido.",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar el piloto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para eliminar.");
    }
    }//GEN-LAST:event_eliminarPilotoActionPerformed

    private void ActivosPilotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivosPilotosActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        PILOTOSINACTIVOS abrir = new PILOTOSINACTIVOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActivosPilotosActionPerformed

    
    
    
    
    private void abrirVentanaModificar(Piloto piloto) {
    String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
    MODIFICARGESTIONPILOTOS ventanaModificar = new MODIFICARGESTIONPILOTOS(piloto, this, username, role, loginFrame);
    ventanaModificar.setVisible(true);
    }

  
    
    
    
    private void abrirVentanaMostrar(Piloto piloto) {
        MOSTRARGESTIONPILOTOS ventanaMostrar = new MOSTRARGESTIONPILOTOS(piloto, this);
        ventanaMostrar.setVisible(true);
    }
    
    public void actualizarTabla() {
        gestionPilotos.cargarPilotosDesdeExcel();
        listaPilotos = gestionPilotos.getPilotos();
        modeloPilotos.setRowCount(0);
        cargarPilotosEnTabla();
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
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
     
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new INICIOGESTIONPILOTOS(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActivosPilotos;
    private javax.swing.JButton agregarPiloto;
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JButton editarPiloto;
    private javax.swing.JButton eliminarPiloto;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton mostrarPiloto;
    private javax.swing.JButton refrescarPiloto;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    // End of variables declaration//GEN-END:variables
}
