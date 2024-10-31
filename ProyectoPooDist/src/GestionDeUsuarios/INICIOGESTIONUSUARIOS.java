package GestionDeUsuarios;

import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlCliente.FrameClientes;
import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import InicioApp.INICIOPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import GestionDeUsuarios.AGREGARGESTIONUSUARIOS;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INGRESOGESTIONUSUARIOS;
import GestionDeUsuarios.MODIFICARGESTIONUSUARIOS;
import GestionDeUsuarios.MOSTRARGESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import Login.GESTIONLOGIN;
import Login.LOGINPINEED;
import Login.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import java.awt.event.ActionEvent;  // Para representar eventos de acción
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class INICIOGESTIONUSUARIOS extends javax.swing.JFrame {
    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    private Vector<Usuarios> listaUsuariosFiltrados = new Vector<>();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    DefaultTableModel modeloUsuarios = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public INICIOGESTIONUSUARIOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        setResizable(false);
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        
        // Modificado: Agregada la columna "No." al inicio del array de columnas
        String[] columnas = {"No.", "Nombre", "Apellido", "DPI", "Cargo", "Teléfono", "Estado"};
        modeloUsuarios.setColumnIdentifiers(columnas);
        
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }
        setupTextField(txtNombreUsuarioBuscar, "Ingresa Nombre del Usuario a buscar");
        tblRegistroUsuarios.setModel(modeloUsuarios);
        tblRegistroUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroUsuarios.getTableHeader().setReorderingAllowed(false);
        tblRegistroUsuarios.getTableHeader().setResizingAllowed(false);
        tblRegistroUsuarios.setRowSelectionAllowed(true);
        tblRegistroUsuarios.setColumnSelectionAllowed(false);
        
        // Modificado: Agregado el ancho para la columna "No."
        tblRegistroUsuarios.getColumnModel().getColumn(0).setPreferredWidth(30);  // Ancho para columna "No."
        tblRegistroUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho para columna "Nombre"
        tblRegistroUsuarios.getColumnModel().getColumn(2).setPreferredWidth(150); // Ancho para columna "Apellido"
        tblRegistroUsuarios.getColumnModel().getColumn(3).setPreferredWidth(100); // Ancho para columna "DPI"
        tblRegistroUsuarios.getColumnModel().getColumn(4).setPreferredWidth(100); // Ancho para columna "Cargo"
        tblRegistroUsuarios.getColumnModel().getColumn(5).setPreferredWidth(100); // Ancho para columna "Teléfono"
        tblRegistroUsuarios.getColumnModel().getColumn(6).setPreferredWidth(100); // Ancho para columna "Estado"
        
        cargarUsuariosEnTabla();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        setupComboBox();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });
    }
    
    
    
    
    
    
     private void cargarUsuariosEnTabla() {
        modeloUsuarios.setRowCount(0);
        listaUsuariosFiltrados = new Vector<>(); // Inicializar como nuevo vector
        
        Set<String> estadosValidos = new HashSet<>(Arrays.asList(
            "ACTIVO",
            "ENFERMO",
            "EN VACACIONES",
            "BLOQUEADO",
            "JUBILADO"
        ));
        
        int indice = 1;
        for (Usuarios usuario : listaUsuarios) {
            if (estadosValidos.contains(usuario.getEstado())) {
                listaUsuariosFiltrados.add(usuario);
                modeloUsuarios.addRow(new Object[]{
                    indice++,
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getNumeroDPI(),
                    usuario.getCargo(),
                    usuario.getNumeroTelefono(),
                    usuario.getEstado()
                });
            }
        }
    }

public void actualizarTabla() {
        gestionUsuarios.cargarUsuariosDesdeExcel();
        listaUsuarios = gestionUsuarios.getUsuarios();
        cargarUsuariosEnTabla();
    }


        // Método para configurar el campo de texto con placeholder
    private void setupTextField(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(java.awt.Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder al enfocar
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restablece el placeholder si el campo está vacío
                if (textField.getText().isEmpty()) {
                    textField.setForeground(java.awt.Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    // Método para limpiar los campos incluyendo el campo de búsqueda
    public void limpiarCampos() {
        // ... otros campos que ya limpias ...
        txtNombreUsuarioBuscar.setText("Ingresa Nombre del Usuario a buscar");
        txtNombreUsuarioBuscar.setForeground(java.awt.Color.GRAY);
    }
    
    
private void setupComboBox() {
    txtMenu2.removeAllItems();
    txtMenu2.addItem("Seleccione una opción");

    if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
        addAdminOptions();
    } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
        addSecretariaOptions();
    } else if (userRole.equalsIgnoreCase("PILOTO")) {
        addPilotOptions();
    }

    txtMenu2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) txtMenu2.getSelectedItem();
            redirectToFrame(selectedOption);
        }
    });
}

private void addAdminOptions() {
    txtMenu2.addItem("Gestión de Usuarios");
    txtMenu2.addItem("Gestión de Pilotos");
    txtMenu2.addItem("Gestión de Clientes");
    txtMenu2.addItem("Gestión de Ventas");
    txtMenu2.addItem("Gestión de Pedidos");
    txtMenu2.addItem("Inventario de Quintales");
    txtMenu2.addItem("Planilla de Trabajadores");
    txtMenu2.addItem("Gestión de Camiones");
    txtMenu2.addItem("Calendario");
    txtMenu2.addItem("Cerrar Sesión");
}

private void addSecretariaOptions() {
    txtMenu2.addItem("Gestión de Clientes");
    txtMenu2.addItem("Gestión de Ventas");
    txtMenu2.addItem("Gestión de Pedidos");
    txtMenu2.addItem("Inventario de Quintales");
    txtMenu2.addItem("Planilla de Trabajadores");
    txtMenu2.addItem("Calendario");
    txtMenu2.addItem("Cerrar Sesión");
}

private void addPilotOptions() {
    txtMenu2.addItem("Calendario");
    txtMenu2.addItem("Cerrar Sesión");
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
    
 


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        txtNombreUsuarioBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        agregarUsuario = new javax.swing.JButton();
        editarUsuario = new javax.swing.JButton();
        mostrarUsuario = new javax.swing.JButton();
        refrescarUsuario = new javax.swing.JButton();
        entradasUsuario = new javax.swing.JButton();
        buscarUsuario = new javax.swing.JButton();
        eliminarUsuario = new javax.swing.JButton();
        ActivosPilotos = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtMenu2 = new javax.swing.JComboBox<>();

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

        agregarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        agregarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        agregarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        agregarUsuario.setText("AGREGAR");
        agregarUsuario.setBorder(null);
        agregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarUsuarioActionPerformed(evt);
            }
        });

        editarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        editarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        editarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        editarUsuario.setText("EDITAR");
        editarUsuario.setBorder(null);
        editarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarUsuarioActionPerformed(evt);
            }
        });

        mostrarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        mostrarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        mostrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        mostrarUsuario.setText("MOSTRAR");
        mostrarUsuario.setBorder(null);
        mostrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarUsuarioActionPerformed(evt);
            }
        });

        refrescarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        refrescarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        refrescarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        refrescarUsuario.setText("REFRESCAR");
        refrescarUsuario.setBorder(null);
        refrescarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarUsuarioActionPerformed(evt);
            }
        });

        entradasUsuario.setBackground(new java.awt.Color(85, 111, 169));
        entradasUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        entradasUsuario.setForeground(new java.awt.Color(255, 255, 255));
        entradasUsuario.setText("ENTRADAS");
        entradasUsuario.setBorder(null);
        entradasUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradasUsuarioActionPerformed(evt);
            }
        });

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

        eliminarUsuario.setBackground(new java.awt.Color(85, 111, 169));
        eliminarUsuario.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        eliminarUsuario.setText("ELIMINAR");
        eliminarUsuario.setBorder(null);
        eliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuarioActionPerformed(evt);
            }
        });

        ActivosPilotos.setBackground(new java.awt.Color(0, 153, 153));
        ActivosPilotos.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivosPilotos.setForeground(new java.awt.Color(255, 255, 255));
        ActivosPilotos.setText("ACTIVAR USUARIOS ELIMINADOS");
        ActivosPilotos.setBorder(null);
        ActivosPilotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivosPilotosActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(32, 67, 99));
        jPanel8.setPreferredSize(new java.awt.Dimension(194, 34));

        txtMenu2.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenu2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(txtMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(448, 448, 448)
                        .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refrescarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(agregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(entradasUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(editarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(agregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entradasUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
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

    private void ActivosPilotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivosPilotosActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        USUARIOSINACTIVOS abrir = new USUARIOSINACTIVOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActivosPilotosActionPerformed

    
    // Método para enviar correo de eliminación
private void enviarCorreoEliminacionUsuario(String destinatario, Usuarios usuario) throws IOException, AddressException, MessagingException {
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
    
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Desactivación de Cuenta - PINEED");
        
        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        
 String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
    "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
    "<h2 style='color: #c41e3a; text-align: center;'><strong>Notificación de Eliminación de Cuenta - PINEED</strong></h2>" +
    "<p style='color: #c41e3a;'>Estimado(a) " + usuario.getNombre() + " " + usuario.getApellido() + ",</p>" +
    "<p style='color: #c41e3a;'>Le informamos que su cuenta ha sido eliminada del sistema de PINEED.</p>" +
    
    "<div style='background-color: #ffebee; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #c41e3a; margin-top: 0;'>Información del Usuario:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Nombre:</strong></td><td style='color: #ffffff;'>" + usuario.getNombre() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Apellido:</strong></td><td style='color: #ffffff;'>" + usuario.getApellido() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>DPI:</strong></td><td style='color: #ffffff;'>" + usuario.getNumeroDPI() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Cargo:</strong></td><td style='color: #ffffff;'>" + usuario.getCargo() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #c41e3a;'><strong>Correo Electrónico:</strong></td><td style='color: #ffffff;'>" + usuario.getCorreoElectronico() + "</td></tr>" +
    "</table></div>" +
    
    "<p style='color: #c41e3a;'>Si tiene alguna pregunta sobre esta acción, por favor contacte al administrador del sistema.</p>" +
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
        }
        
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Correo enviado exitosamente a: " + destinatario);
        
    } catch (MessagingException e) {
        System.err.println("Error detallado al enviar correo de eliminación: ");
        e.printStackTrace();
        throw new IOException("Error al enviar el correo de eliminación: " + e.getMessage());
    }
}


    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
         int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                Usuarios usuarioSeleccionado = listaUsuariosFiltrados.get(filaSeleccionada);
                String nombreCompleto = usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellido();
                String correo = usuarioSeleccionado.getCorreoElectronico();

                int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el usuario: " + nombreCompleto + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
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

                    JLabel mensajeLabel = new JLabel("Desactivando usuario y enviando notificación...");
                    mensajeLabel.setHorizontalAlignment(JLabel.CENTER);

                    contenidoPanel.add(mensajeLabel, gbc);
                    contenidoPanel.add(progressBar, gbc);
                    panel.add(contenidoPanel, BorderLayout.CENTER);
                    dialogoProceso.add(panel);
                    dialogoProceso.setSize(400, 150);
                    dialogoProceso.setLocationRelativeTo(this);

                    Thread processingThread = new Thread(() -> {
                        try {
                            // Enviar correo antes de eliminar el usuario
                            enviarCorreoEliminacionUsuario(correo, usuarioSeleccionado);
                            
                            // Eliminar usuario
                            gestionUsuarios.eliminarUsuario(usuarioSeleccionado.getNumeroDPI());
                            
                            SwingUtilities.invokeLater(() -> {
                                dialogoProceso.dispose();
                                // En lugar de solo remover la fila, actualizamos toda la tabla
                                actualizarTabla();
                                JOptionPane.showMessageDialog(this,
                                    "Usuario eliminado correctamente y notificación enviada.",
                                    "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            });
                        } catch (IOException e) {
                            SwingUtilities.invokeLater(() -> {
                                dialogoProceso.dispose();
                                JOptionPane.showMessageDialog(this,
                                    "Usuario eliminado pero hubo un error al enviar el correo: " + e.getMessage(),
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                            });
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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el usuario: " + e.getMessage(),
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un usuario para eliminar.");
        }
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void buscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioActionPerformed
  String criterioBusqueda = txtNombreUsuarioBuscar.getText().trim();
    
    // Validar si el campo está vacío o es el placeholder
    if (criterioBusqueda.isEmpty() || 
            criterioBusqueda.equals("Ingresa Nombre, Apellido o DPI del Usuario a buscar")) {
        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }
    
    String criterioBusquedaNormalizado = normalizarTexto(criterioBusqueda);
    modeloUsuarios.setRowCount(0);
    listaUsuariosFiltrados.clear(); // Limpiamos la lista filtrada
    boolean hayCoincidencias = false;
    int indice = 1; // Inicializamos el contador para el índice
    
    for (Usuarios usuario : listaUsuarios) {
        String nombreUsuarioNormalizado = normalizarTexto(usuario.getNombre());
        String apellidoUsuarioNormalizado = normalizarTexto(usuario.getApellido());
        
        // Comparar DPI como long
        boolean coincidenciaDPI = false;
        try {
            long criterioDPI = Long.parseLong(criterioBusqueda);
            coincidenciaDPI = usuario.getNumeroDPI() == criterioDPI;
        } catch (NumberFormatException e) {
            // Si no es un número válido, no se hace nada
            coincidenciaDPI = false;
        }

        // Comprobar si coincide con nombre, apellido o DPI
        boolean coincidencia = nombreUsuarioNormalizado.contains(criterioBusquedaNormalizado) ||
                               apellidoUsuarioNormalizado.contains(criterioBusquedaNormalizado) ||
                               coincidenciaDPI;

        if (coincidencia) {
            modeloUsuarios.addRow(new Object[]{
                indice++, // Añadimos el índice
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getNumeroDPI(),
                usuario.getCargo(),
                usuario.getNumeroTelefono(),
                usuario.getEstado()
            });
            listaUsuariosFiltrados.add(usuario); // Añadimos el usuario encontrado a la lista filtrada
            hayCoincidencias = true; // Hay al menos una coincidencia
        }
    }
    
    if (!hayCoincidencias) {
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
        cargarUsuariosEnTabla(); // Volver a cargar todos los usuarios
    }
    
    // Aseguramos que las columnas mantengan sus anchos preferidos
    SwingUtilities.invokeLater(() -> {
        tblRegistroUsuarios.getColumnModel().getColumn(0).setPreferredWidth(30);  // Ancho para columna "No."
        tblRegistroUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho para columna "Nombre"
        tblRegistroUsuarios.getColumnModel().getColumn(2).setPreferredWidth(150); // Ancho para columna "Apellido"
        tblRegistroUsuarios.getColumnModel().getColumn(3).setPreferredWidth(100); // Ancho para columna "DPI"
        tblRegistroUsuarios.getColumnModel().getColumn(4).setPreferredWidth(100); // Ancho para columna "Cargo"
        tblRegistroUsuarios.getColumnModel().getColumn(5).setPreferredWidth(100); // Ancho para columna "Teléfono"
        tblRegistroUsuarios.getColumnModel().getColumn(6).setPreferredWidth(100); // Ancho para columna "Estado"
        
        // Restablecer el campo de búsqueda
        txtNombreUsuarioBuscar.setText("Ingresa Nombre, Apellido o DPI del Usuario a buscar");
        txtNombreUsuarioBuscar.setForeground(Color.GRAY);
    });
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    // Método para normalizar texto eliminando tildes y convirtiendo a minúsculas
private String normalizarTexto(String texto) {
    String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
    return textoNormalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
}

    private void entradasUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradasUsuarioActionPerformed
                String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INGRESOGESTIONUSUARIOS abrir = new  INGRESOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);

    }//GEN-LAST:event_entradasUsuarioActionPerformed

    private void refrescarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarUsuarioActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarUsuarioActionPerformed

    private void mostrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarUsuarioActionPerformed
        int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Usamos la lista filtrada en lugar de la lista original
            Usuarios usuarioSeleccionado = listaUsuariosFiltrados.get(filaSeleccionada);
            abrirVentanaMostrar(usuarioSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarUsuarioActionPerformed

    private void editarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarUsuarioActionPerformed
    int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
    if (filaSeleccionada >= 0) {
        // Obtener el usuario directamente de la lista filtrada
        Usuarios usuarioSeleccionado = listaUsuariosFiltrados.get(filaSeleccionada);
        
        if (usuarioSeleccionado != null) {
            abrirVentanaModificar(usuarioSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario seleccionado.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario para modificar.");
    }
    }//GEN-LAST:event_editarUsuarioActionPerformed

    private void agregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarUsuarioActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        AGREGARGESTIONUSUARIOS abrir = new  AGREGARGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_agregarUsuarioActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void txtMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenu2ActionPerformed

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
    private javax.swing.JButton ActivosPilotos;
    private javax.swing.JButton agregarUsuario;
    private javax.swing.JButton buscarUsuario;
    private javax.swing.JButton editarUsuario;
    private javax.swing.JButton eliminarUsuario;
    private javax.swing.JButton entradasUsuario;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton mostrarUsuario;
    private javax.swing.JButton refrescarUsuario;
    private javax.swing.JTable tblRegistroUsuarios;
    private javax.swing.JComboBox<String> txtMenu2;
    private javax.swing.JTextField txtNombreUsuarioBuscar;
    // End of variables declaration//GEN-END:variables
}
