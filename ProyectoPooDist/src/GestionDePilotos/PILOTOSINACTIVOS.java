package GestionDePilotos;

import ControlCliente.FrameClientes;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import GestionDeCamiones.CAMIONESINACTIVOS;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private void inicializarTabla() {
        String[] columnas = {
            "Nombre", "Apellido", "DPI", "Tipo Licencia", 
            "Correo Electrónico", "Número Telefónico", "Estado"
        };
        
        modeloPilotos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblRegistroPilotos.setModel(modeloPilotos);
    }

    private void cargarDatos() {
        try {
            System.out.println("Loading inactive pilots data...");
            listaPilotosInactivos = new Vector<>();
            
            try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                
                Sheet sheet = workbook.getSheetAt(0);
                modeloPilotos.setRowCount(0); // Clear existing rows
                
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
                        Object[] fila = new Object[7];
                        fila[0] = getCellValueAsString(row.getCell(0)); // Nombre
                        fila[1] = getCellValueAsString(row.getCell(1)); // Apellido
                        fila[2] = getCellValueAsString(row.getCell(2)); // DPI
                        fila[3] = getCellValueAsString(row.getCell(3)); // Tipo Licencia
                        fila[4] = getCellValueAsString(row.getCell(4)); // Correo
                        fila[5] = getCellValueAsString(row.getCell(5)); // Teléfono
                        fila[6] = getCellValueAsString(row.getCell(8)); // Estado
                        
                        modeloPilotos.addRow(fila);
                        
                        // Create and add Piloto object to listaPilotosInactivos
                        Piloto piloto = new Piloto(
                            (String) fila[0],
                            (String) fila[1],
                            Long.parseLong(fila[2].toString()),
                            (String) fila[3],
                            (String) fila[4],
                            Integer.parseInt(fila[5].toString()),
                            "", // género
                            "", // fecha nacimiento
                            (String) fila[6],
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
        jLabel4.setText("NOMBRE");

        txtNombrePilotoBuscar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

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
                        .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ActivarPilotoEliminado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
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
        if (txtNombrePilotoBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingresa el nombre del piloto para buscar.");
            return;
        }

        String nombreBuscado = txtNombrePilotoBuscar.getText().trim().toLowerCase();
        modeloPilotos.setRowCount(0);
        boolean hayCoincidencias = false;

        Vector<Piloto> todosLosPilotos = gestionPilotos.getPilotos();

        for (Piloto piloto : todosLosPilotos) {
            if (!piloto.isActivo() &&
                piloto.getNombrePiloto().toLowerCase().contains(nombreBuscado)) {

                Object[] fila = {
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto()
                };
                modeloPilotos.addRow(fila);
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron pilotos inactivos con el nombre especificado.");
            cargarDatos();
        }

        txtNombrePilotoBuscar.setText("");
    }//GEN-LAST:event_buscarPilotoActionPerformed

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    private void ActivarPilotoEliminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarPilotoEliminadoActionPerformed
        int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        try {
            String dpiSeleccionado = tblRegistroPilotos.getValueAt(filaSeleccionada, 2).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas reactivar el piloto con DPI: " + dpiSeleccionado + "?",
                "Confirmar reactivación",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
                     Workbook workbook = new XSSFWorkbook(fis)) {
                    
                    Sheet sheet = workbook.getSheetAt(0);
                    boolean pilotoEncontrado = false;
                    
                    for (Row row : sheet) {
                        Cell dpiCell = row.getCell(2);
                        if (dpiCell != null) {
                            String currentDpi = getCellValueAsString(dpiCell);
                            if (currentDpi.equals(dpiSeleccionado)) {
                                // Actualizar estado a "ACTIVO"
                                Cell estadoCell = row.getCell(8);
                                if (estadoCell == null) {
                                    estadoCell = row.createCell(8);
                                }
                                estadoCell.setCellValue("ACTIVO");
                                
                                // Actualizar columna "Activo" a true
                                Cell activoCell = row.getCell(9);
                                if (activoCell == null) {
                                    activoCell = row.createCell(9);
                                }
                                activoCell.setCellValue(true);
                                
                                pilotoEncontrado = true;
                                break;
                            }
                        }
                    }
                    
                    if (pilotoEncontrado) {
                        // Guardar cambios en el archivo Excel
                        try (FileOutputStream fos = new FileOutputStream(EXCEL_PATH)) {
                            workbook.write(fos);
                        }
                        
                        // Actualizar la tabla
                        cargarDatos();
                        JOptionPane.showMessageDialog(this, "Piloto reactivado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "No se encontró el piloto con el DPI especificado.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
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
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    // End of variables declaration//GEN-END:variables
}
