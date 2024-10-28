package GestionDeCamiones;

import ControlCliente.FrameClientes;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTable; // Importa la clase JTable para crear tablas en la interfaz gráfica
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CAMIONESINACTIVOS extends javax.swing.JFrame {
    
    private GESTIONCAMIONES gestionCamiones;
    private Vector<Camiones> listaCamionesInactivos;
    private DefaultTableModel modeloCamiones;
    private static final String EXCEL_PATH = "excels/CAMIONES.xlsx";
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    private GESTIONUSUARIOS gestionUsuarios;

    public CAMIONESINACTIVOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        // Inicialización de variables de clase
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        this.gestionCamiones = new GESTIONCAMIONES();
        
        // Configuración inicial
        setResizable(false);
        inicializarTabla();
        cargarDatos(); // Añadimos esta llamada que faltaba
                setupTextField(txtMarcaCamionBuscar, "Ingresa Marca del Camión a buscar");

        // Configuración de la interfaz
        addWindowListener();
        setupComboBox();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Configuración adicional de la tabla
        tblRegistroCamiones1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroCamiones1.getTableHeader().setReorderingAllowed(false);
        tblRegistroCamiones1.getTableHeader().setResizingAllowed(false);
        tblRegistroCamiones1.setRowSelectionAllowed(true);
        tblRegistroCamiones1.setColumnSelectionAllowed(false);
              gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        // Solicitar el foco de la ventana
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
        txtMarcaCamionBuscar.setText("Ingresa Marca del Camión a buscar");
        txtMarcaCamionBuscar.setForeground(Color.GRAY);
    }

  // Modificar inicializarTabla para incluir la columna "No."
private void inicializarTabla() {
    String[] columnas = {
        "No.", "Marca", "Modelo", "Placas", "Estado", 
        "Tipo de Combustible", "Kilometraje"
    };
    
    modeloCamiones = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    tblRegistroCamiones1.setModel(modeloCamiones);
    
    // Configurar el ancho de la columna "No."
    tblRegistroCamiones1.getColumnModel().getColumn(0).setPreferredWidth(30);
}

  // Método cargarDatos corregido
private void cargarDatos() {
    try {
        System.out.println("Iniciando carga de datos de camiones inactivos...");
        listaCamionesInactivos = new Vector<>();
        int totalCamiones = 0;
        int camionesInactivos = 0;
        
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int activoColumnIndex = -1;
            
            // Encontrar el índice de la columna "Activo"
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                if ("Activo".equals(headerRow.getCell(i).getStringCellValue())) {
                    activoColumnIndex = i;
                    System.out.println("Columna 'Activo' encontrada en índice: " + i);
                    break;
                }
            }
            
            if (activoColumnIndex == -1) {
                throw new RuntimeException("No se encontró la columna 'Activo' en el Excel");
            }
            
            // Limpiar la tabla antes de cargar nuevos datos
            modeloCamiones.setRowCount(0);
            
            int rowIndex = 1;
            
            // Iterar sobre todas las filas excepto el encabezado
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    totalCamiones++;
                    Cell activoCell = row.getCell(activoColumnIndex);
                    boolean activo = true;
                    
                    if (activoCell != null) {
                        if (activoCell.getCellType() == CellType.BOOLEAN) {
                            activo = activoCell.getBooleanCellValue();
                        } else if (activoCell.getCellType() == CellType.STRING) {
                            activo = Boolean.parseBoolean(activoCell.getStringCellValue());
                        }
                    }
                    
                    // Si el camión está marcado como inactivo (falso)
                    if (!activo) {
                        camionesInactivos++;
                        String marca = getCellValueAsString(row.getCell(2));  // Marca
                        String modelo = getCellValueAsString(row.getCell(1)); // Modelo
                        String placas = getCellValueAsString(row.getCell(0)); // Placas
                        String estado = getCellValueAsString(row.getCell(3)); // Estado
                        String tipoCombustible = getCellValueAsString(row.getCell(4)); // Tipo Combustible
                        String kilometraje = getCellValueAsString(row.getCell(5)); // Kilometraje

                        Object[] fila = new Object[7];
                        fila[0] = rowIndex++;    // No.
                        fila[1] = marca;         // Marca
                        fila[2] = modelo;        // Modelo
                        fila[3] = placas;        // Placas
                        fila[4] = estado;        // Estado
                        fila[5] = tipoCombustible; // Tipo Combustible
                        fila[6] = kilometraje;   // Kilometraje
                        
                        modeloCamiones.addRow(fila);
                        
                        // Crear objeto Camiones con los valores correctos
                        Camiones camion = new Camiones(
                            marca, estado, tipoCombustible,
                            Double.parseDouble(kilometraje), 0.0, "",
                            modelo, placas, false,
                            0.0, 0.0, 0.0, 0.0, 0.0, "", "", "", 0.0, 0.0
                        );
                        listaCamionesInactivos.add(camion);
                        System.out.println("Camión inactivo agregado: " + camion.getMarca() + " - " + camion.getPlacas());
                    }
                }
            }
        }
        
        System.out.println("Total de camiones procesados: " + totalCamiones);
        System.out.println("Camiones inactivos encontrados: " + camionesInactivos);
        
    } catch (Exception e) {
        System.err.println("Error al cargar los datos de camiones inactivos: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error al cargar los datos de camiones inactivos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
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
                return String.valueOf(cell.getNumericCellValue());
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
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistroCamiones1 = new javax.swing.JTable();
        buscarCamion = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        ActivarCamionEliminado = new javax.swing.JButton();
        ActivosCamiones = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        ActivarCamionEliminado.setBackground(new java.awt.Color(85, 111, 169));
        ActivarCamionEliminado.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivarCamionEliminado.setForeground(new java.awt.Color(255, 255, 255));
        ActivarCamionEliminado.setText("ACTIVAR");
        ActivarCamionEliminado.setBorder(null);
        ActivarCamionEliminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivarCamionEliminadoActionPerformed(evt);
            }
        });

        ActivosCamiones.setBackground(new java.awt.Color(0, 153, 153));
        ActivosCamiones.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        ActivosCamiones.setForeground(new java.awt.Color(255, 255, 255));
        ActivosCamiones.setText("REGRESAR A CAMIONES ACTIVOS");
        ActivosCamiones.setBorder(null);
        ActivosCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivosCamionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ActivosCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ActivarCamionEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActivosCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ActivarCamionEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
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

    private void buscarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCamionActionPerformed
String placeholder = "Ingresa Marca del Camión a buscar";
    String textoActual = txtMarcaCamionBuscar.getText().trim();
    
    // Verificar si el texto es el placeholder o está vacío
    if (textoActual.isEmpty() || textoActual.equals(placeholder)) {
        JOptionPane.showMessageDialog(this,
            "Por favor, ingresa la marca del camión para buscar.");
        return;
    }

    // Primero recargar todos los datos para asegurar que tenemos la lista completa
    cargarDatos();

    // Obtener el texto de búsqueda y convertirlo a minúsculas
    String marcaBuscada = textoActual.toLowerCase();
    
    // Filtrar en la tabla completa
    TableModel modelo = tblRegistroCamiones1.getModel();
    int rowCount = modelo.getRowCount();
    Vector<Object[]> filasFiltradas = new Vector<>();
    boolean encontrado = false;
    
    // Recorrer todas las filas de la tabla
    for (int i = 0; i < rowCount; i++) {
        // La marca está en la columna 1
        String marcaEnTabla = String.valueOf(modelo.getValueAt(i, 1)).toLowerCase();
        if (marcaEnTabla.contains(marcaBuscada)) {
            encontrado = true;
            // Guardar toda la fila
            Object[] fila = new Object[7];
            for (int j = 0; j < 7; j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filasFiltradas.add(fila);
        }
    }
    
    // Si se encontraron coincidencias, mostrarlas
    if (encontrado) {
        modeloCamiones.setRowCount(0);
        int nuevoIndice = 1;
        for (Object[] fila : filasFiltradas) {
            // Actualizar el número de índice
            fila[0] = nuevoIndice++;
            modeloCamiones.addRow(fila);
        }
    } else {
        JOptionPane.showMessageDialog(this,
            "No se encontraron camiones con la marca especificada.");
        cargarDatos(); // Volver a mostrar todos los camiones
    }
    
    // Resetear el campo de búsqueda
    SwingUtilities.invokeLater(() -> {
        txtMarcaCamionBuscar.setText(placeholder);
        txtMarcaCamionBuscar.setForeground(Color.GRAY);
    });
    }//GEN-LAST:event_buscarCamionActionPerformed

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getLocalDateTimeCellValue().toString();
                    }
                    // Para evitar números en formato científico
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.format("%.0f", numericValue);
                    }
                    return String.valueOf(numericValue);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            System.err.println("Error al obtener valor de celda: " + e.getMessage());
            return "";
        }
    }
    
    
private void enviarCorreoReactivacion(String destinatario, Camiones camion) throws IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    final String username = "distribuidorapine@gmail.com";
    final String password = "aura hcol bzmt plzf";

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject("Camión Reactivado - PINEED");

        Multipart multipart = new MimeMultipart("related");

        // Primera parte - contenido HTML
        BodyPart messageBodyPart = new MimeBodyPart();
        String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
            "<h2 style='color: #2c3e50; text-align: center;'><strong>¡Camión Reactivado en PINEED!</strong></h2>" +
            "<p style='color: #34495e;'>Se ha reactivado un camión en el sistema.</p>" +
            "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2c3e50; margin-top: 0;'>Detalles del Camión:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0;'><strong>Marca:</strong></td><td>" + camion.getMarca() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Modelo:</strong></td><td>" + camion.getModelo() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Placas:</strong></td><td>" + camion.getPlacas() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Estado:</strong></td><td>FUNCIONAL</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Tipo de Combustible:</strong></td><td>" + camion.getTipoCombustible() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Capacidad de Carga:</strong></td><td>" + camion.getCapacidadCarga() + " kg</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Año de Fabricación:</strong></td><td>" + camion.getAñoFabricacion() + "</td></tr>" +
            "</table></div>" +
            "<div style='margin-top: 20px; text-align: center;'>" +
            "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
            "</div>" +
            "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
            "</div></body></html>";
        
        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Segunda parte - la imagen
        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);

        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);  // Mostrar la imagen en línea
            multipart.addBodyPart(messageBodyPart);
        } else {
            System.out.println("Imagen no encontrada en el classpath.");
        }

        message.setContent(multipart);
        Transport.send(message);

    } catch (MessagingException e) {
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}


    
    private void ActivarCamionEliminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarCamionEliminadoActionPerformed
 int filaSeleccionada = tblRegistroCamiones1.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            String placasSeleccionadas = (String) tblRegistroCamiones1.getValueAt(filaSeleccionada, 3);
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas reactivar el camión con placas: " +
                placasSeleccionadas + "?",
                "Confirmar reactivación",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Llama al método para activar el camión
                gestionCamiones.activarCamion(placasSeleccionadas);
                
                // Cargar los datos para actualizar la vista
                cargarDatos();

                // Obtener el camión reactivado
                Camiones camion = gestionCamiones.obtenerCamionPorPlacas(placasSeleccionadas);
                if (camion != null) {
                    // Crear el diálogo de progreso
                    JDialog dialogoProceso = new JDialog(this, "Procesando", true);
                    dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    
                    JPanel panel = new JPanel(new BorderLayout(10, 10));
                    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    JProgressBar progressBar = new JProgressBar();
                    progressBar.setIndeterminate(true);
                    progressBar.setStringPainted(true);
                    progressBar.setString("Enviando notificaciones...");
                    
                    JLabel mensajeLabel = new JLabel("Enviando correos al personal administrativo...");
                    mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
                    
                    panel.add(mensajeLabel, BorderLayout.NORTH);
                    panel.add(progressBar, BorderLayout.CENTER);
                    
                    dialogoProceso.add(panel);
                    dialogoProceso.setSize(400, 150);
                    dialogoProceso.setLocationRelativeTo(this);

                    // Crear un hilo separado para realizar el envío de correos
                    // Crear un hilo separado para realizar el envío de correos
                    // Crear un hilo separado para realizar el envío de correos
Thread processingThread = new Thread(() -> {
    boolean correosEnviados = false;
    try {
        // Obtener la lista de usuarios
        Vector<Usuarios> usuarios = gestionUsuarios.getUsuarios();

        for (Usuarios usuario : usuarios) {
            if (("ADMINISTRADOR".equalsIgnoreCase(usuario.getCargo()) || 
                 "SECRETARIA".equalsIgnoreCase(usuario.getCargo())) &&
                usuario.getCorreoElectronico() != null &&
                !usuario.getCorreoElectronico().isEmpty()) {
                
                // Enviar correo a cada administrador o secretaria
                enviarCorreoReactivacion(usuario.getCorreoElectronico(), camion);
                correosEnviados = true;
            }
        }
    } catch (Exception e) {
        correosEnviados = false;
    } finally {
        final boolean exito = correosEnviados;
                            // Cerrar el diálogo y mostrar los resultados en el hilo de la interfaz
                            // Cerrar el diálogo y mostrar los resultados en el hilo de la interfaz
                            SwingUtilities.invokeLater(() -> {
                                dialogoProceso.dispose(); // Cerrar el diálogo de progreso
                                
                                if (exito) {
                                    JOptionPane.showMessageDialog(
                                        this,
                                        "Camión reactivado exitosamente y se han enviado las notificaciones al personal administrativo.",
                                        "Operación exitosa",
                                        JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                        this,
                                        "Camión reactivado exitosamente pero no se pudieron enviar las notificaciones.",
                                        "Advertencia",
                                        JOptionPane.WARNING_MESSAGE
                                    );
                                }

                                // Cargar los datos de nuevo para actualizar la vista
                                cargarDatos();
                            });
                        }
                    });

                    // Iniciar el proceso en un hilo separado
                    processingThread.start(); 
                    
                    // Mostrar el diálogo mientras se realiza el proceso
                    dialogoProceso.setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error inesperado: " + e.getMessage() + "\nPor favor, contacte al administrador del sistema.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }                          
    }//GEN-LAST:event_ActivarCamionEliminadoActionPerformed

    private void ActivosCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivosCamionesActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ActivosCamionesActionPerformed

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
            java.util.logging.Logger.getLogger(CAMIONESINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CAMIONESINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CAMIONESINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CAMIONESINACTIVOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                                String username = "defaultUser";  // Reemplaza con el nombre de usuario real o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea la instancia de INICIOGESTIONCAMIONES con los parámetros requeridos
                new CAMIONESINACTIVOS(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActivarCamionEliminado;
    private javax.swing.JButton ActivosCamiones;
    private javax.swing.JButton buscarCamion;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTable tblRegistroCamiones1;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JComboBox<String> txtMenu;
    // End of variables declaration//GEN-END:variables
}
