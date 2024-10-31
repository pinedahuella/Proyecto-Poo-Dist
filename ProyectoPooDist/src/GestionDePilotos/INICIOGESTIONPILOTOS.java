package GestionDePilotos;

////package GestionDePilotos;
import GestionDePilotos.PILOTOSINACTIVOS;
import GestionDePilotos.GESTIONPILOTOS;
import ControlCliente.FrameClientes;
import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
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
import GestionDePilotos.Piloto;
import GestionDeUsuarios.Usuarios;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class INICIOGESTIONPILOTOS extends javax.swing.JFrame {
    protected GESTIONPILOTOS gestionPilotos;
    private Vector<Piloto> listaPilotos;
    private Vector<Piloto> pilotosFiltrados; // Lista para mantener los resultados actuales de la tabla
    private DefaultTableModel modeloPilotos = new DefaultTableModel();
    private LOGINPINEED loginFrame;
    private String currentUser;
    private String userRole;

    public INICIOGESTIONPILOTOS(String username, String role, LOGINPINEED loginFrame) {
        // Initialize base frame components first 
        initComponents();
        gestionPilotos = new GESTIONPILOTOS();

        // Set frame properties 
        setResizable(false);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setupTextField(txtNombrePilotoBuscar, "Ingresa Nombre/Apellido/DPI del Piloto a buscar");

        // Initialize instance variables 
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;

        // Initialize table model and set columns 
        modeloPilotos = new DefaultTableModel();
        String[] columnas = {"No.", "Nombre", "Apellido", "DPI", "Licencia", "Teléfono", "Estado"}; 
        modeloPilotos.setColumnIdentifiers(columnas); 

        // Configure table properties 
        tblRegistroPilotos.setModel(modeloPilotos);
        tblRegistroPilotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroPilotos.getTableHeader().setReorderingAllowed(false);
        tblRegistroPilotos.getTableHeader().setResizingAllowed(false);
        tblRegistroPilotos.setRowSelectionAllowed(true);
        tblRegistroPilotos.setColumnSelectionAllowed(false);
        
        // Ajustar anchos de columnas
        tblRegistroPilotos.getColumnModel().getColumn(0).setPreferredWidth(30); // Ancho para columna "No."
        tblRegistroPilotos.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho para columna "Nombre"
        tblRegistroPilotos.getColumnModel().getColumn(2).setPreferredWidth(150); // Ancho para columna "Apellido"
        tblRegistroPilotos.getColumnModel().getColumn(3).setPreferredWidth(100); // Ancho para columna "DPI"
        tblRegistroPilotos.getColumnModel().getColumn(4).setPreferredWidth(100); // Ancho para columna "Licencia"
        tblRegistroPilotos.getColumnModel().getColumn(5).setPreferredWidth(100); // Ancho para columna "Teléfono"
        tblRegistroPilotos.getColumnModel().getColumn(6).setPreferredWidth(100); // Ancho para columna "Estado"
        
        // Load pilots without filtering
        gestionPilotos.cargarPilotosDesdeExcel(); 
        listaPilotos = gestionPilotos.getPilotos(); 
        
        cargarPilotosEnTabla(); 
        // Additional setup 
        addWindowListener(); 
        setupComboBox(); 
        
        // Final frame setup 
        this.setVisible(true); 
        SwingUtilities.invokeLater(() -> { 
            this.requestFocusInWindow(); 
        }); 
    }

    // Agregar un método getter para gestionPilotos
    public GESTIONPILOTOS getGestionPilotos() {
        return gestionPilotos;
    }

    private void cargarPilotosEnTabla() {
        modeloPilotos.setRowCount(0);
        pilotosFiltrados = new Vector<>(); // Inicializar la lista filtrada
        
        Set<String> estadosValidos = new HashSet<>(Arrays.asList(
            "ACTIVO",
            "ENFERMO",
            "EN VACACIONES",
            "BLOQUEADO",
            "JUBILADO"
        ));
        
        int indice = 1; // Inicializar el índice
        for (Piloto piloto : listaPilotos) {
            if (estadosValidos.contains(piloto.getEstadoPiloto())) {
                pilotosFiltrados.add(piloto); // Agregar a la lista filtrada
                modeloPilotos.addRow(new Object[]{
                    indice++, // Añadir el índice
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto()
                });
            }
        }
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
    txtMenu.addItem("Gestión de Clientes");
    txtMenu.addItem("Gestión de Ventas");
    txtMenu.addItem("Gestión de Pedidos");
    txtMenu.addItem("Inventario de Quintales");
    txtMenu.addItem("Planilla de Trabajadores");
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



 // Método para configurar el campo de texto con placeholder
    private void setupTextField(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder al enfocar
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restablece el placeholder si el campo está vacío
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    // Método para limpiar los campos incluyendo el campo de búsqueda
    public void limpiarCampos() {
        // ... otros campos que ya limpias ...
        txtNombrePilotoBuscar.setText("Ingresa Nombre, Apellido o DPI del Piloto a buscar");
        txtNombrePilotoBuscar.setForeground(Color.GRAY);
    }
    
            public void actualizarTabla() {
        gestionPilotos.cargarPilotosDesdeExcel();
        listaPilotos = gestionPilotos.getPilotos();
        cargarPilotosEnTabla();
        }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jPanel1 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jLabel4.setText("PILOTO");

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

        jPanel1.setBackground(new java.awt.Color(32, 67, 99));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(437, 437, 437)
                            .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(169, 169, 169)
                            .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(editarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void editarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarPilotoActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Usar la lista filtrada en lugar de la lista completa
            Piloto pilotoSeleccionado = pilotosFiltrados.get(filaSeleccionada);
            abrirVentanaModificar(pilotoSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un piloto para modificar.");
        }
    }//GEN-LAST:event_editarPilotoActionPerformed

    private void mostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarPilotoActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0 && filaSeleccionada < pilotosFiltrados.size()) {
            // Usar la lista filtrada para obtener el piloto correcto
            Piloto pilotoSeleccionado = pilotosFiltrados.get(filaSeleccionada);
            abrirVentanaMostrar(pilotoSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un piloto válido para mostrar su información.");
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
   String criterioBusqueda = txtNombrePilotoBuscar.getText().trim();
    // Validar si el campo está vacío o es el placeholder
    if (criterioBusqueda.isEmpty() || criterioBusqueda.equals("Ingresa Nombre, Apellido o DPI del Piloto a buscar")) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda (Nombre, Apellido o DPI).");
        return;
    }
    
    modeloPilotos.setRowCount(0); // Limpiar la tabla antes de cargar los resultados
    pilotosFiltrados = new Vector<>(); // Reset lista filtrada
    boolean hayCoincidencias = false;
    String criterioBusquedaNormalizado = normalizarTexto(criterioBusqueda);
    int indice = 1; // Inicializamos el índice para la numeración
    
    for (Piloto piloto : listaPilotos) {
        String nombrePilotoNormalizado = normalizarTexto(piloto.getNombrePiloto());
        String apellidoPilotoNormalizado = normalizarTexto(piloto.getApellidoPiloto());
        
        // Comparación de DPI como long
        boolean coincidenciaDPI = false;
        try {
            long criterioDPI = Long.parseLong(criterioBusqueda);
            coincidenciaDPI = piloto.getNumeroDeDpi() == criterioDPI;
        } catch (NumberFormatException e) {
            // Si no es un número válido, no hace nada
            coincidenciaDPI = false;
        }
        
        // Comprobar si coincide con nombre, apellido o DPI
        boolean coincidencia = nombrePilotoNormalizado.contains(criterioBusquedaNormalizado) ||
                                apellidoPilotoNormalizado.contains(criterioBusquedaNormalizado) ||
                                coincidenciaDPI;
        
        if (coincidencia) {
            pilotosFiltrados.add(piloto); // Agregar a la lista filtrada
            modeloPilotos.addRow(new Object[]{
                indice++, // Añadimos el índice
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getEstadoPiloto()
            });
            hayCoincidencias = true;
        }
    }
    
    // Si no hay coincidencias, mostrar un mensaje y restaurar la tabla completa
    if (!hayCoincidencias) {
        JOptionPane.showMessageDialog(this, "No se encontraron pilotos que coincidan con el criterio de búsqueda.");
        cargarPilotosEnTabla(); // Restaurar la tabla completa
    } else {
        // Si hay resultados, seleccionar el primer resultado
        if (tblRegistroPilotos.getRowCount() > 0) {
            tblRegistroPilotos.setRowSelectionInterval(0, 0);
        }
    }
    
    // No restaurar el placeholder si hay resultados
    if (hayCoincidencias) {
        txtNombrePilotoBuscar.setForeground(Color.BLACK); // Cambiar el color del texto
    } else {
        // Restaurar el placeholder
        SwingUtilities.invokeLater(() -> {
            txtNombrePilotoBuscar.setText("Ingresa Nombre, Apellido o DPI del Piloto a buscar");
            txtNombrePilotoBuscar.setForeground(Color.GRAY);
        });
    }
    }//GEN-LAST:event_buscarPilotoActionPerformed

    
    // Método para normalizar el texto
private String normalizarTexto(String texto) {
    // Normalizar y eliminar tildes y caracteres especiales
    String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(textoNormalizado).replaceAll("").toLowerCase(); // Devuelve el texto sin acentos y en minúsculas
}



private void enviarCorreoDesactivacion(String destinatario, Piloto piloto) throws IOException {
    // Validar el correo electrónico
    if (destinatario == null || destinatario.trim().isEmpty() || !destinatario.contains("@")) {
        throw new IOException("Correo electrónico inválido: " + destinatario);
    }

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    
    final String username = "distribuidorapine@gmail.com";
    final String password = "aura hcol bzmt plzf";
    
    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    
    System.out.println("Intentando enviar correo a: " + destinatario);
        
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Notificación de Desactivación - PINEED");
        
        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        
     String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
    "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
    "<h2 style='color: #c41e3a; text-align: center;'><strong>Notificación de Desactivación en PINEED</strong></h2>" +
    "<p style='color: #c41e3a;'>Estimado(a) " + piloto.getNombrePiloto() + " " + piloto.getApellidoPiloto() + ",</p>" +
    "<p style='color: #c41e3a;'>Lamentamos informarle que su cuenta ha sido desactivada en nuestro sistema.</p>" +
    
    "<div style='background-color: #ffebee; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #c41e3a; margin-top: 0;'>Información del Piloto:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Nombre:</strong></td><td style='color: #ffffff;'>" + piloto.getNombrePiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Apellido:</strong></td><td style='color: #ffffff;'>" + piloto.getApellidoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>DPI:</strong></td><td style='color: #ffffff;'>" + piloto.getNumeroDeDpi() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Correo Electrónico:</strong></td><td style='color: #ffffff;'>" + piloto.getCorreoElectronicoPiloto() + "</td></tr>" +
    "</table></div>" +
    
    "<p style='color: #c41e3a;'>Si tiene preguntas o desea más información, no dude en contactarnos.</p>" +
    "<p style='color: #c41e3a;'>Atentamente,</p>" +
    "<p style='color: #c41e3a;'>El equipo de PINEED</p>" +
    "<div style='text-align: center; margin-top: 20px;'>" +
    "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
    "</div>" +
    "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
    "</div></body></html>";
     
            
        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);
        
        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
        } else {
            System.out.println("Imagen no encontrada en el classpath.");
        }
        
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Correo enviado exitosamente a: " + destinatario);
        
    } catch (MessagingException e) {
        System.err.println("Error detallado al enviar correo: ");
        e.printStackTrace();
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}



    private void eliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPilotoActionPerformed
        int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            // Obtener el DPI
            Object dpiObject = tblRegistroPilotos.getValueAt(filaSeleccionada, 3);
            String dpiString = dpiObject.toString().trim();
            long numeroDeDpiSeleccionado = Long.parseLong(dpiString);
String nombrePiloto = tblRegistroPilotos.getValueAt(filaSeleccionada, 1).toString().trim(); // Suponiendo que la columna 1 contiene el nombre
            String apellidoPiloto = tblRegistroPilotos.getValueAt(filaSeleccionada, 2).toString().trim(); // Suponiendo que la columna 2 contiene el apellido
            String nombreCompleto = nombrePiloto + " " + apellidoPiloto;


            // Buscar el piloto completo basado en el DPI
            Piloto pilotoParaEliminar = null;
            for (Piloto piloto : listaPilotos) {
                if (piloto.getNumeroDeDpi() == numeroDeDpiSeleccionado) {
                    pilotoParaEliminar = piloto;
                    break;
                }
            }
            
            if (pilotoParaEliminar == null) {
                throw new IllegalStateException("No se pudo encontrar la información completa del piloto.");
            }

            // Validar DPI
            if (dpiString.length() != 13) {
                throw new NumberFormatException("El DPI debe tener 13 dígitos.");
            }
            
            // Diálogo de confirmación
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar el piloto: " + nombreCompleto + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Crear y mostrar el diálogo de progreso
                JDialog dialogoProceso = new JDialog(this, "Procesando", true);
                dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JPanel contenidoPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);
                
                JProgressBar progressBar = new JProgressBar();
                progressBar.setIndeterminate(true);
                progressBar.setStringPainted(true);
                progressBar.setString("Procesando...");
                
                JLabel mensajeLabel = new JLabel("Desactivando piloto y enviando notificación...");
                mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
                
                contenidoPanel.add(mensajeLabel, gbc);
                contenidoPanel.add(progressBar, gbc);
                panel.add(contenidoPanel, BorderLayout.CENTER);
                dialogoProceso.add(panel);
                dialogoProceso.setSize(400, 150);
                dialogoProceso.setLocationRelativeTo(this);
                
                // Crear una copia final del piloto para usar en el thread
                final Piloto pilotoFinal = pilotoParaEliminar;
                
                Thread processingThread = new Thread(() -> {
                    try {
                        String correoSeleccionado = pilotoFinal.getCorreoElectronicoPiloto();
                        
                        if (correoSeleccionado == null || correoSeleccionado.trim().isEmpty()) {
                            throw new IOException("El piloto no tiene un correo electrónico registrado.");
                        }
                        
                        // Enviar correo antes de eliminar
                        try {
                            enviarCorreoDesactivacion(correoSeleccionado, pilotoFinal);
                            // Proceder con la eliminación
                            gestionPilotos.eliminarPiloto(numeroDeDpiSeleccionado);
                            
                            SwingUtilities.invokeLater(() -> {
                                dialogoProceso.dispose();
                                actualizarTabla();
                                JOptionPane.showMessageDialog(this,
                                    "Piloto eliminado correctamente y notificación enviada.",
                                    "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            });
                        } catch (IOException e) {
                            SwingUtilities.invokeLater(() -> {
                                dialogoProceso.dispose();
                                JOptionPane.showMessageDialog(this,
                                    "Error al enviar la notificación: " + e.getMessage() + "\nEl piloto no ha sido eliminado.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            dialogoProceso.dispose();
                            JOptionPane.showMessageDialog(this,
                                "Error durante el proceso: " + e.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                processingThread.start();
                dialogoProceso.setVisible(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Error: El DPI no tiene un formato válido. " + e.getMessage(),
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

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    
    
    
    
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
