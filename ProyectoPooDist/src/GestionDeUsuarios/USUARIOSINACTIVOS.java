package GestionDeUsuarios;

import GestionDePilotos.INICIOGESTIONPILOTOS;
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
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

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
                setupTextField(txtNombreUsuarioBuscar, "Ingresa Nombre del Usuario a buscar");

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

    private void inicializarTabla() {
    String[] columnas = {
        "No.", "Nombre Usuario", "Nombre", "Apellido", "DPI", "Cargo",
        "Correo Electrónico", "Número Telefónico", "Estado"
    };
    
    modeloUsuarios = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    tblRegistroUsuarios.setModel(modeloUsuarios);
    
    // Establecer el ancho de la columna "No."
    tblRegistroUsuarios.getColumnModel().getColumn(0).setPreferredWidth(30);
    tblRegistroUsuarios.getColumnModel().getColumn(0).setMaxWidth(30);
    tblRegistroUsuarios.getColumnModel().getColumn(0).setMinWidth(30);
}


    private void cargarDatos() {
    try {
        gestionUsuarios.cargarUsuariosDesdeExcel();
        Vector<Usuarios> todosLosUsuarios = gestionUsuarios.getUsuarios();
        listaUsuariosInactivos = new Vector<>();
        modeloUsuarios.setRowCount(0);
        
        int numeroFila = 1; // Contador para la numeración

        for (Usuarios usuario : todosLosUsuarios) {
            if ("INACTIVO".equals(usuario.getEstado())) {
                Object[] fila = {
                    numeroFila++, // Agregar el número de fila
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

    String nombreBuscado = txtNombreUsuarioBuscar.getText().trim();
    modeloUsuarios.setRowCount(0);
    boolean encontrado = false;
    int numeroFila = 1; // Contador para la numeración

    String nombreBuscadoNormalizado = normalizarTexto(nombreBuscado);

    for (Usuarios usuario : listaUsuariosInactivos) {
        String nombreUsuarioNormalizado = normalizarTexto(usuario.getNombre());
        String nombreUsuarioLoginNormalizado = normalizarTexto(usuario.getNombreUsuario());

        if (nombreUsuarioNormalizado.contains(nombreBuscadoNormalizado) ||
            nombreUsuarioLoginNormalizado.contains(nombreBuscadoNormalizado)) {

            Object[] fila = {
                numeroFila++, // Agregar el número de fila
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

    SwingUtilities.invokeLater(() -> {
        txtNombreUsuarioBuscar.setText("Ingresa Nombre del Usuario a buscar");
        txtNombreUsuarioBuscar.setForeground(Color.GRAY);
    });
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    
    // Método para normalizar el texto
private String normalizarTexto(String texto) {
    String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(textoNormalizado).replaceAll("").toLowerCase(); // Devuelve el texto sin acentos y en minúsculas
}
private void enviarCorreoActivacionUsuario(String destinatario, Usuarios usuario) throws IOException, AddressException, MessagingException {
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
        message.setSubject("Reactivación de Usuario en el Sistema - PINEED");
        
        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        
   String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
    "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
    "<h2 style='color: #1e88e5; text-align: center;'><strong>¡Bienvenido nuevamente a PINEED!</strong></h2>" +
    "<p style='color: #1e88e5;'>Nos complace informarle que su cuenta de usuario ha sido reactivada en nuestro sistema.</p>" +
    
    "<div style='background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #1e88e5; margin-top: 0;'>Información del Usuario:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Nombre:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getNombre() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Apellido:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getApellido() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>DPI:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getNumeroDPI() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Cargo:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getCargo() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Correo Electrónico:</strong></td><td style='color: #ffffff; word-break: break-all; width: 70%;'>" + usuario.getCorreoElectronico() + "</td></tr>" +
    "</table></div>" +
    
    "<div style='background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #1e88e5; margin-top: 0;'>Sus Credenciales de Acceso:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Usuario:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getNombreUsuario() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Contraseña:</strong></td><td style='color: #ffffff; width: 70%;'>" + usuario.getContrasenaUsuario() + "</td></tr>" +
    "</table>" +
    "</div>" +
    
    "<div style='text-align: center; margin-top: 20px;'>" +
    "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
    "</div>" +
    "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
    "</div></body></html>";
            
        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Segunda parte - la imagen embebida
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



    private void ActivarUsuarioEliminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarUsuarioEliminadoActionPerformed
    int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            // Obtener DPI y correo del usuario seleccionado
            long dpi = Long.parseLong(tblRegistroUsuarios.getValueAt(filaSeleccionada, 4).toString());
            String correo = tblRegistroUsuarios.getValueAt(filaSeleccionada, 6).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea reactivar el usuario con DPI: " + dpi + "?",
                "Confirmar reactivación",
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

                JLabel mensajeLabel = new JLabel("Reactivando usuario y enviando notificación...");
                mensajeLabel.setHorizontalAlignment(JLabel.CENTER);

                contenidoPanel.add(mensajeLabel, gbc);
                contenidoPanel.add(progressBar, gbc);
                panel.add(contenidoPanel, BorderLayout.CENTER);
                dialogoProceso.add(panel);
                dialogoProceso.setSize(400, 150);
                dialogoProceso.setLocationRelativeTo(this);

                Thread processingThread = new Thread(() -> {
                    try {
                        // Obtener el usuario completo
                        Usuarios usuarioActivado = null;
                        for (Usuarios usuario : listaUsuariosInactivos) {
                            if (usuario.getNumeroDPI() == dpi) {
                                usuarioActivado = usuario;
                                break;
                            }
                        }

                        if (usuarioActivado != null) {
                            // Reactivar usuario
                            gestionUsuarios.reactivarUsuario(dpi);

                            try {
                                // Enviar correo
                                enviarCorreoActivacionUsuario(correo, usuarioActivado);
                                SwingUtilities.invokeLater(() -> {
                                    dialogoProceso.dispose();
                                    cargarDatos();
                                    JOptionPane.showMessageDialog(this,
                                        "Usuario reactivado correctamente y correo enviado.",
                                        "Éxito",
                                        JOptionPane.INFORMATION_MESSAGE);
                                });
                            } catch (IOException e) {
                                SwingUtilities.invokeLater(() -> {
                                    dialogoProceso.dispose();
                                    cargarDatos();
                                    JOptionPane.showMessageDialog(this,
                                        "Usuario reactivado pero hubo un error al enviar el correo: " + e.getMessage(),
                                        "Advertencia",
                                        JOptionPane.WARNING_MESSAGE);
                                });
                            }
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
