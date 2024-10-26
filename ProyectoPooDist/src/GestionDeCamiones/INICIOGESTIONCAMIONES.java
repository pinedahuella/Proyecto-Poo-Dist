package GestionDeCamiones;
// Importación de clases necesarias para el funcionamiento de la aplicación
import ControlCliente.FrameClientes;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Clase principal para la gestión de camiones
public class INICIOGESTIONCAMIONES extends javax.swing.JFrame {
    // Atributos de la clase
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    private Vector<Camiones> camionesEnTabla = new Vector<>(); // Nueva variable para trackear los camiones mostrados

    // Modelo de tabla para mostrar los camiones
    DefaultTableModel modeloCamiones = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hace que las celdas no sean editables
        }
    };

    // Constructor de la clase
    public INICIOGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        setResizable(false); // Desactivar el cambio de tamaño
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();

        // Configuración de las columnas de la tabla
        String[] columnas = {"No.", "Marca", "Modelo", "Placas", "Estado", "Tipo Combustible", "Kilometraje"};
        modeloCamiones.setColumnIdentifiers(columnas);

        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
        }

        setupTextField(txtMarcaCamionBuscar, "Ingresa Marca del Camión a buscar");

        // Configuración de la tabla de camiones
        tblRegistroCamiones1.setModel(modeloCamiones);
        tblRegistroCamiones1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroCamiones1.getTableHeader().setReorderingAllowed(false);
        tblRegistroCamiones1.getTableHeader().setResizingAllowed(false);
        tblRegistroCamiones1.setRowSelectionAllowed(true);
        tblRegistroCamiones1.setColumnSelectionAllowed(false);

        // Ajustar el ancho de las columnas
        tblRegistroCamiones1.getColumnModel().getColumn(0).setPreferredWidth(30); // Ancho para la columna "No."
        tblRegistroCamiones1.getColumnModel().getColumn(1).setPreferredWidth(100); // Ancho para la columna "Marca"
        tblRegistroCamiones1.getColumnModel().getColumn(2).setPreferredWidth(100); // Ancho para la columna "Modelo"
        tblRegistroCamiones1.getColumnModel().getColumn(3).setPreferredWidth(100); // Ancho para la columna "Placas"
        tblRegistroCamiones1.getColumnModel().getColumn(4).setPreferredWidth(100); // Ancho para la columna "Estado"
        tblRegistroCamiones1.getColumnModel().getColumn(5).setPreferredWidth(100); // Ancho para la columna "Tipo Combustible"
        tblRegistroCamiones1.getColumnModel().getColumn(6).setPreferredWidth(100); // Ancho para la columna "Kilometraje"

        cargarCamionesEnTabla();
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

    private void cargarCamionesEnTabla() {
        modeloCamiones.setRowCount(0);
        camionesEnTabla.clear(); // Limpiamos el vector de seguimiento

        int indice = 1; // Inicializamos el índice
        for (Camiones camion : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                indice++,                          // No. (se incrementa automáticamente)
                camion.getMarca(),                 // Marca
                camion.getModelo(),                // Modelo
                camion.getPlacas(),                // Placas
                camion.getEstado(),                // Estado
                camion.getTipoCombustible(),       // Tipo Combustible
                camion.getKilometraje()            // Kilometraje
            });
            camionesEnTabla.add(camion); // Agregamos el camión al vector de seguimiento
        }
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
        txtMarcaCamionBuscar.setText("Ingresa Marca del Camión a buscar");
        txtMarcaCamionBuscar.setForeground(Color.GRAY);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroCamiones1 = new javax.swing.JTable();
        buscarCamion = new javax.swing.JButton();
        refrescarCamion = new javax.swing.JButton();
        agregarCamion = new javax.swing.JButton();
        mostrarCamion = new javax.swing.JButton();
        editarCamion = new javax.swing.JButton();
        garageCamiones = new javax.swing.JButton();
        eliminarUsuario = new javax.swing.JButton();
        ActivarCamiones = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        txtMarcaCamionBuscar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

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

        ActivarCamiones.setBackground(new java.awt.Color(0, 153, 153));
        ActivarCamiones.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivarCamiones.setForeground(new java.awt.Color(255, 255, 255));
        ActivarCamiones.setText("ACTIVAR CAMIONES ELIMINADOS");
        ActivarCamiones.setBorder(null);
        ActivarCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivarCamionesActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(32, 67, 99));
        jPanel7.setPreferredSize(new java.awt.Dimension(194, 34));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(garageCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ActivarCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivarCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(garageCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    
        /**
     * Maneja la acción de buscar un camión según su marca.
     * Si el campo de entrada está vacío, muestra un mensaje solicitando al usuario que ingrese la marca.
     * Si se encuentran coincidencias, se llena la tabla con los camiones que coinciden con los criterios de búsqueda.
     * Si no se encuentran coincidencias, se muestra un mensaje indicando que no se encontraron camiones.
     * 
     * @param evt el evento que activó esta acción
     */
    private void buscarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCamionActionPerformed
     if (txtMarcaCamionBuscar.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa la marca del camión para buscar.");
        return;
    }

    String marcaBuscada = txtMarcaCamionBuscar.getText().trim();
    modeloCamiones.setRowCount(0);
    camionesEnTabla.clear(); // Limpiamos el vector de seguimiento
    boolean hayCoincidencias = false;

    int indice = 1; // Inicializamos el índice

    for (Camiones camion : listaCamiones) {
        if (camion.getMarca().toLowerCase().contains(marcaBuscada.toLowerCase())) {
            modeloCamiones.addRow(new Object[]{
                indice++, // Añadimos el índice
                camion.getMarca(), // Primero la marca
                camion.getModelo(),
                camion.getPlacas(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje()
            });
            camionesEnTabla.add(camion); // Agregamos solo los camiones filtrados
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

    SwingUtilities.invokeLater(() -> {
        txtMarcaCamionBuscar.setText("Ingresa Marca del Camión a buscar");
        txtMarcaCamionBuscar.setForeground(Color.GRAY);
    });
    }//GEN-LAST:event_buscarCamionActionPerformed

     /**
     * Refresca la pantalla de gestión de camiones creando una nueva instancia de 
     * INICIOGESTIONCAMIONES y cerrando la ventana actual.
     *
     * @param evt el evento que activó esta acción
     */
    private void refrescarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarCamionActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarCamionActionPerformed

     /**
     * Abre la ventana para agregar un nuevo camión. 
     * Pasa los detalles del usuario actual a la nueva ventana.
     *
     * @param evt el evento que activó esta acción
     */
    private void agregarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarCamionActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        AGREGARGESTIONCAMIONES abrir = new AGREGARGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_agregarCamionActionPerformed

     /**
     * Muestra la información del camión seleccionado en una nueva ventana.
     * Si no hay un camión seleccionado, muestra un mensaje solicitando que se seleccione uno.
     *
     * @param evt el evento que activó esta acción
     */
    private void mostrarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarCamionActionPerformed
    int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Usamos el vector de seguimiento en lugar del listaCamiones original
            Camiones camionSeleccionado = camionesEnTabla.get(filaSeleccionada);
            abrirVentanaMostrar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarCamionActionPerformed

     /**
     * Abre la ventana para editar la información del camión seleccionado.
     * Si no hay un camión seleccionado, muestra un mensaje solicitando que se seleccione uno.
     *
     * @param evt el evento que activó esta acción
     */
    private void editarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCamionActionPerformed
int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Usamos el vector de seguimiento en lugar del listaCamiones original
            Camiones camionSeleccionado = camionesEnTabla.get(filaSeleccionada);
            abrirVentanaModificar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un camión para modificar.");
        }
    }//GEN-LAST:event_editarCamionActionPerformed

     /**
     * Abre la ventana de gestión del garaje de camiones.
     * Pasa los detalles del usuario actual a la nueva ventana.
     *
     * @param evt el evento que activó esta acción
     */
    private void garageCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_garageCamionesActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        GARAGEGESTIONCAMIONES abrir = new GARAGEGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_garageCamionesActionPerformed

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
        int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Usamos el vector de seguimiento en lugar del listaCamiones original
            Camiones camionSeleccionado = camionesEnTabla.get(filaSeleccionada);
            String placasSeleccionadas = camionSeleccionado.getPlacas();

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
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void ActivarCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarCamionesActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        CAMIONESINACTIVOS abrir = new CAMIONESINACTIVOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_ActivarCamionesActionPerformed

     /**
     * Actualiza la tabla de camiones recargando los datos desde Excel.
     */
    public void actualizarTabla() {
        gestionCamiones.cargarCamionesDesdeExcel();
        listaCamiones = gestionCamiones.getCamiones();
        modeloCamiones.setRowCount(0);
        cargarCamionesEnTabla();
    }

     /**
     * Abre la ventana para modificar la información de un camión.
     * 
     * @param camion el camión cuya información se va a modificar
     */
    private void abrirVentanaModificar(Camiones camion) {
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        MODIFICARGESTIONCAMIONES ventanaModificar = new MODIFICARGESTIONCAMIONES(camion, this, username, role, loginFrame);
        ventanaModificar.setVisible(true);
    }

     /**
     * Abre la ventana para mostrar la información de un camión.
     * 
     * @param camion el camión cuya información se va a mostrar
     */
    private void abrirVentanaMostrar(Camiones camion) {
        MOSTRARGESTIONCAMIONES ventanaMostrar = new MOSTRARGESTIONCAMIONES(camion, this);
        ventanaMostrar.setVisible(true);
    }

    
     /**
     * Método principal que establece el aspecto y la sensación de Nimbus,
     * y crea y muestra la interfaz de gestión de camiones.
     * 
     * @param args los argumentos de la línea de comandos
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
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    /* Crea y muestra el formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
                String username = "defaultUser";  // Reemplaza con el nombre de usuario real o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea la instancia de INICIOGESTIONCAMIONES con los parámetros requeridos
                new INICIOGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActivarCamiones;
    private javax.swing.JButton agregarCamion;
    private javax.swing.JButton buscarCamion;
    private javax.swing.JButton editarCamion;
    private javax.swing.JButton eliminarUsuario;
    private javax.swing.JButton garageCamiones;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton mostrarCamion;
    private javax.swing.JButton refrescarCamion;
    private javax.swing.JTable tblRegistroCamiones1;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JComboBox<String> txtMenu;
    // End of variables declaration//GEN-END:variables
}
