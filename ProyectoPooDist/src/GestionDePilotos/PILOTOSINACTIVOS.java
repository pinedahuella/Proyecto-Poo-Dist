package GestionDePilotos;

import GestionDePilotos.GESTIONPILOTOS;
import ControlCliente.FrameClientes;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import GestionDeCamiones.CAMIONESINACTIVOS;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.Piloto;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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


public class PILOTOSINACTIVOS extends javax.swing.JFrame {
    
    private GESTIONPILOTOS gestionPilotos;
    private Vector<Piloto> listaPilotosInactivos;
    private DefaultTableModel modeloPilotos;
    private static final String EXCEL_PATH = "excels/PINEED.xlsx";
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    public PILOTOSINACTIVOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        // Inicialización de variables de clase
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        this.gestionPilotos = new GESTIONPILOTOS();
        
        // Configuración inicial
        setResizable(false);
        inicializarTabla();
        cargarDatos();
        
        
        
        // Configurar el placeholder para el campo de búsqueda
        setupTextField(txtNombrePilotoBuscar, "Ingresa Nombre, Apellido o DPI del Piloto a buscar");
        
        // Configuración de la interfaz
        addWindowListener();
        setupComboBox();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Configuración adicional de la tabla
        tblRegistroPilotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroPilotos.getTableHeader().setReorderingAllowed(false);
        tblRegistroPilotos.getTableHeader().setResizingAllowed(false);
        tblRegistroPilotos.setRowSelectionAllowed(true);
        tblRegistroPilotos.setColumnSelectionAllowed(false);
        
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });
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


 private void inicializarTabla() {
    String[] columnas = {
        "No.", "Nombre", "Apellido", "DPI", "Tipo Licencia", 
        "Correo Electrónico", "Número Telefónico", "Estado"
    };
    
    modeloPilotos = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    tblRegistroPilotos.setModel(modeloPilotos);
    
    // Configurar el ancho de la columna "No."
    tblRegistroPilotos.getColumnModel().getColumn(0).setPreferredWidth(30);

 }
    
    
    private void cargarDatos() {
    try {
        System.out.println("Loading inactive pilots data...");
        listaPilotosInactivos = new Vector<>();
        
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            modeloPilotos.setRowCount(0); // Clear existing rows
            
            int rowIndex = 1; // Variable para el contador de filas
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                
                Cell activoCell = row.getCell(9); // "Activo" column
                boolean isActive = true;
                
                if (activoCell != null) {
                    if (activoCell.getCellType() == CellType.BOOLEAN) {
                        isActive = activoCell.getBooleanCellValue();
                    } else if (activoCell.getCellType() == CellType.STRING) {
                        isActive = Boolean.parseBoolean(activoCell.getStringCellValue());
                    }
                }
                
                // Only process inactive pilots
                if (!isActive) {
                    Object[] fila = new Object[8]; // Aumentado a 8 para incluir el número
                    fila[0] = rowIndex++; // Agregar el número de índice
                    fila[1] = getCellValueAsString(row.getCell(0)); // Nombre
                    fila[2] = getCellValueAsString(row.getCell(1)); // Apellido
                    fila[3] = getCellValueAsString(row.getCell(2)); // DPI
                    fila[4] = getCellValueAsString(row.getCell(3)); // Tipo Licencia
                    fila[5] = getCellValueAsString(row.getCell(4)); // Correo
                    fila[6] = getCellValueAsString(row.getCell(5)); // Teléfono
                    fila[7] = getCellValueAsString(row.getCell(8)); // Estado
                    
                    modeloPilotos.addRow(fila);
                    
                    // Create and add Piloto object to listaPilotosInactivos
                    Piloto piloto = new Piloto(
                        (String) fila[1], // Nombre (ahora en índice 1)
                        (String) fila[2], // Apellido (ahora en índice 2)
                        Long.parseLong(fila[3].toString()), // DPI (ahora en índice 3)
                        (String) fila[4], // Tipo Licencia
                        (String) fila[5], // Correo
                        Integer.parseInt(fila[6].toString()), // Teléfono
                        "", // género
                        "", // fecha nacimiento
                        (String) fila[7], // Estado
                        false // activo
                    );
                    listaPilotosInactivos.add(piloto);
                }
            }
        }
        
        System.out.println("Total inactive pilots loaded: " + listaPilotosInactivos.size());
        
    } catch (Exception e) {
        System.err.println("Error loading inactive pilots data: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error loading inactive pilots data: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void actualizarTabla() {
        cargarDatos(); // Reload all inactive pilots
    }


private String getCellValueAsString(Cell cell) {
    if (cell == null) {
        return "";
    }
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toString();
            }
            // Formatear números largos sin notación científica
            if (cell.getNumericCellValue() >= 1e10) { // Si es un número grande (como DPI)
                return String.format("%.0f", cell.getNumericCellValue());
            }
            return String.valueOf((long)cell.getNumericCellValue());
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        default:
            return "";
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

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        buscarPiloto = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        ActivarPilotoEliminado = new javax.swing.JButton();
        ActivosPilotos = new javax.swing.JButton();
        refrescarPiloto = new javax.swing.JButton();

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

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("PILOTO");

        txtNombrePilotoBuscar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNombrePilotoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePilotoBuscarActionPerformed(evt);
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
                .addContainerGap(25, Short.MAX_VALUE)
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

        ActivarPilotoEliminado.setBackground(new java.awt.Color(85, 111, 169));
        ActivarPilotoEliminado.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivarPilotoEliminado.setForeground(new java.awt.Color(255, 255, 255));
        ActivarPilotoEliminado.setText("ACTIVAR");
        ActivarPilotoEliminado.setBorder(null);
        ActivarPilotoEliminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivarPilotoEliminadoActionPerformed(evt);
            }
        });

        ActivosPilotos.setBackground(new java.awt.Color(0, 153, 153));
        ActivosPilotos.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivosPilotos.setForeground(new java.awt.Color(255, 255, 255));
        ActivosPilotos.setText("REGRESAR A PILOTOS ACTIVOS");
        ActivosPilotos.setBorder(null);
        ActivosPilotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivosPilotosActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(437, 437, 437)
                        .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ActivarPilotoEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivosPilotos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ActivarPilotoEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refrescarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
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

    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
   
     // Verificar si el campo de búsqueda está vacío o contiene el placeholder
    if (txtNombrePilotoBuscar.getText().trim().isEmpty() || 
        txtNombrePilotoBuscar.getText().equals("Ingresa Nombre, Apellido o DPI del Piloto a buscar")) {
        JOptionPane.showMessageDialog(this,
            "Por favor, ingresa un criterio de búsqueda (Nombre, Apellido o DPI).");
        return;
    }
    
    String criterioBusqueda = txtNombrePilotoBuscar.getText().trim();
    modeloPilotos.setRowCount(0);
    boolean hayCoincidencias = false;
    
    // Normalizar el texto buscado
    String criterioBusquedaNormalizado = normalizarTexto(criterioBusqueda);
    
    try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
         Workbook workbook = new XSSFWorkbook(fis)) {
        
        Sheet sheet = workbook.getSheetAt(0);
        int rowIndex = 1; // Contador para el número de fila
        
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header
            
            // Verificar si el piloto está inactivo
            Cell activoCell = row.getCell(9);
            boolean isActive = true;
            if (activoCell != null) {
                if (activoCell.getCellType() == CellType.BOOLEAN) {
                    isActive = activoCell.getBooleanCellValue();
                } else if (activoCell.getCellType() == CellType.STRING) {
                    isActive = Boolean.parseBoolean(activoCell.getStringCellValue());
                }
            }
            
            // Solo procesar pilotos inactivos
            if (!isActive) {
                String nombrePiloto = getCellValueAsString(row.getCell(0));
                String apellidoPiloto = getCellValueAsString(row.getCell(1));
                String dpiPiloto = getCellValueAsString(row.getCell(2));
                
                String nombrePilotoNormalizado = normalizarTexto(nombrePiloto);
                String apellidoPilotoNormalizado = normalizarTexto(apellidoPiloto);
                
                // Buscar por nombre, apellido o DPI
                boolean coincidencia = nombrePilotoNormalizado.contains(criterioBusquedaNormalizado) ||
                                       apellidoPilotoNormalizado.contains(criterioBusquedaNormalizado) ||
                                       dpiPiloto.contains(criterioBusqueda);
                
                if (coincidencia) {
                    Object[] fila = new Object[8]; // 8 columnas
                    fila[0] = rowIndex++; // Número de índice
                    fila[1] = nombrePiloto; // Nombre
                    fila[2] = apellidoPiloto; // Apellido
                    fila[3] = dpiPiloto; // DPI
                    fila[4] = getCellValueAsString(row.getCell(3)); // Tipo Licencia
                    fila[5] = getCellValueAsString(row.getCell(4)); // Correo
                    fila[6] = getCellValueAsString(row.getCell(5)); // Teléfono
                    fila[7] = getCellValueAsString(row.getCell(8)); // Estado
                    
                    modeloPilotos.addRow(fila);
                    hayCoincidencias = true;
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error al buscar pilotos inactivos: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error al buscar pilotos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    if (!hayCoincidencias) {
        JOptionPane.showMessageDialog(this,
            "No se encontraron pilotos inactivos que coincidan con el criterio de búsqueda.");
        cargarDatos(); // Recargar todos los pilotos inactivos
    }
    
    // Reseteamos el campo después de la búsqueda
    SwingUtilities.invokeLater(() -> {
    
    });
    }
    
    
// Método para verificar si el criterio de búsqueda parece ser un DPI
private boolean esCriterioDPI(String criterioBusqueda) {

    return criterioBusqueda.length() >= 2 && criterioBusqueda.length() <= 13 && criterioBusqueda.matches("\\d+");
}

// Método para mostrar sugerencias de DPI
private void mostrarSugerenciasDPI(String criterioBusqueda) {
    modeloPilotos.setRowCount(0); // Limpiar la tabla
    int indice = 1;
    boolean hayCoincidencias = false;

    for (Piloto piloto : listaPilotosInactivos) {
        String dpiString = String.valueOf(piloto.getNumeroDeDpi());
        if (dpiString.contains(criterioBusqueda)) {
            modeloPilotos.addRow(new Object[]{
                indice++,
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

    if (!hayCoincidencias) {
        JOptionPane.showMessageDialog(this, "No se encontraron pilotos con DPI que contengan " + criterioBusqueda);
        cargarDatos(); // Restaurar la tabla completa si no hay coincidencias
    } else {
        JOptionPane.showMessageDialog(this, "Se encontraron " + (indice - 1) + " pilotos con DPI que contienen " + criterioBusqueda);
    }                                        
    }//GEN-LAST:event_buscarPilotoActionPerformed

    // Método para normalizar el texto
private String normalizarTexto(String texto) {
    // Normalizar y eliminar tildes y caracteres especiales
    String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(textoNormalizado).replaceAll("").toLowerCase(); // Devuelve el texto sin acentos y en minúsculas
}


    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    
    
private void enviarCorreoActivacion(String destinatario, Piloto piloto) throws IOException {
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
        message.setSubject("Reactivación en el Sistema - PINEED");

        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();

      String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
    "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
    "<h2 style='color: #1e88e5; text-align: center;'><strong>¡Bienvenido nuevamente a PINEED!</strong></h2>" +
    "<p style='color: #1e88e5;'>Estimado(a) " + piloto.getNombrePiloto() + " " + piloto.getApellidoPiloto() + ",</p>" +
    "<p style='color: #1e88e5;'>Nos complace informarle que su cuenta ha sido reactivada en nuestro sistema.</p>" +
    "<div style='background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #1e88e5; margin-top: 0;'>Información del Piloto:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Nombre:</strong></td><td style='color: #ffffff; width: 70%;'>" + piloto.getNombrePiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Apellido:</strong></td><td style='color: #ffffff; width: 70%;'>" + piloto.getApellidoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>DPI:</strong></td><td style='color: #ffffff; width: 70%;'>" + piloto.getNumeroDeDpi() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; color: #1e88e5; width: 30%; vertical-align: top;'><strong>Correo Electrónico:</strong></td><td style='color: #ffffff; width: 70%;'>" + piloto.getCorreoElectronicoPiloto() + "</td></tr>" +
    "</table></div>" +
    "<div style='background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #1e88e5; margin-top: 0;'>Sus Credenciales de Acceso:</h3>" +
    "<p style='color: #1e88e5;'><strong>Usuario:</strong> <span style='color: #ffffff;'>" + piloto.getNombrePiloto().toLowerCase() + "." + piloto.getApellidoPiloto().toLowerCase() + "&pineed</span></p>" +
    "<p style='color: #1e88e5;'><strong>Contraseña:</strong> <span style='color: #ffffff;'>" + piloto.getNumeroDeDpi() + "</span></p>" +
    "</div>" +
    "<p style='color: #1e88e5;'>Su cuenta está nuevamente activa y puede acceder al sistema con sus credenciales habituales.</p>" +
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
        System.err.println("Error detallado al enviar correo: ");
        e.printStackTrace();
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}

    
    private void ActivarPilotoEliminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarPilotoEliminadoActionPerformed
   int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            // Obtener el DPI y correo de la fila seleccionada
            String dpiSeleccionado = tblRegistroPilotos.getValueAt(filaSeleccionada, 3).toString();
            String correoSeleccionado = tblRegistroPilotos.getValueAt(filaSeleccionada, 5).toString(); // Correo está en la columna 5
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas reactivar el piloto con DPI: " + dpiSeleccionado + "?",
                "Confirmar reactivación",
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
                
                JLabel mensajeLabel = new JLabel("Reactivando piloto y enviando notificación...");
                mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
                
                contenidoPanel.add(mensajeLabel, gbc);
                contenidoPanel.add(progressBar, gbc);
                panel.add(contenidoPanel, BorderLayout.CENTER);
                dialogoProceso.add(panel);
                dialogoProceso.setSize(400, 150);
                dialogoProceso.setLocationRelativeTo(this);
                
                Thread processingThread = new Thread(() -> {
                    try {
                        Piloto pilotoActivado = null;
                        
                        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
                             Workbook workbook = new XSSFWorkbook(fis)) {
                            
                            Sheet sheet = workbook.getSheetAt(0);
                            boolean pilotoEncontrado = false;
                            
                            for (Row row : sheet) {
                                Cell dpiCell = row.getCell(2); // DPI está en la columna 2
                                if (dpiCell != null) {
                                    String currentDpi = getCellValueAsString(dpiCell);
                                    if (currentDpi.equals(dpiSeleccionado)) {
                                        // Actualizar estado
                                        Cell estadoCell = row.getCell(8);
                                        if (estadoCell == null) {
                                            estadoCell = row.createCell(8);
                                        }
                                        estadoCell.setCellValue("ACTIVO");
                                        
                                        Cell activoCell = row.getCell(9);
                                        if (activoCell == null) {
                                            activoCell = row.createCell(9);
                                        }
                                        activoCell.setCellValue(true);
                                        
                                        // Crear objeto Piloto con la información correcta
                                        pilotoActivado = new Piloto();
                                        pilotoActivado.setNombrePiloto(getCellValueAsString(row.getCell(0)));
                                        pilotoActivado.setApellidoPiloto(getCellValueAsString(row.getCell(1)));
                                        pilotoActivado.setNumeroDeDpi(Long.parseLong(currentDpi));
                                        pilotoActivado.setCorreoElectronicoPiloto(correoSeleccionado); // Usar el correo de la tabla
                                        
                                        pilotoEncontrado = true;
                                        break;
                                    }
                                }
                            }
                            
                            if (pilotoEncontrado) {
                                try (FileOutputStream fos = new FileOutputStream(EXCEL_PATH)) {
                                    workbook.write(fos);
                                }
                            }
                        }
                        
                        if (pilotoActivado != null) {
                            try {
                                enviarCorreoActivacion(correoSeleccionado, pilotoActivado);
                                SwingUtilities.invokeLater(() -> {
                                    dialogoProceso.dispose();
                                    cargarDatos();
                                    JOptionPane.showMessageDialog(this,
                                        "Piloto reactivado correctamente y correo enviado.",
                                        "Éxito",
                                        JOptionPane.INFORMATION_MESSAGE);
                                });
                            } catch (IOException e) {
                                SwingUtilities.invokeLater(() -> {
                                    dialogoProceso.dispose();
                                    cargarDatos();
                                    JOptionPane.showMessageDialog(this,
                                        "Piloto reactivado pero hubo un error al enviar el correo: " + e.getMessage(),
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
            System.err.println("Error al reactivar el piloto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al reactivar el piloto: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this,
            "Por favor, selecciona un piloto para reactivar.");
    }
    }//GEN-LAST:event_ActivarPilotoEliminadoActionPerformed

    private void ActivosPilotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivosPilotosActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        INICIOGESTIONPILOTOS abrir = new INICIOGESTIONPILOTOS(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActivosPilotosActionPerformed

    private void txtNombrePilotoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePilotoBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombrePilotoBuscarActionPerformed

    private void refrescarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPilotoActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        PILOTOSINACTIVOS abrir = new  PILOTOSINACTIVOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPilotoActionPerformed

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
            java.util.logging.Logger.getLogger(PILOTOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PILOTOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PILOTOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PILOTOSINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                                String username = "defaultUser";  // Reemplaza con el nombre de usuario real o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea la instancia de INICIOGESTIONCAMIONES con los parámetros requeridos
                new PILOTOSINACTIVOS(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActivarPilotoEliminado;
    private javax.swing.JButton ActivosPilotos;
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton refrescarPiloto;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    // End of variables declaration//GEN-END:variables
}
