package GestionDeCamiones;

// Importación de clases necesarias para la gestión de camiones y otras funcionalidades de la aplicación
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario; // Importa la clase FrameInventario para gestionar la interfaz de inventario
import ControlPedidos.FormularioPedidos; // Importa la clase FormularioPedidos para gestionar pedidos de productos
import ControlPlanilla.FramePlanillaSemanal; // Importa la clase FramePlanillaSemanal para manejar la planilla semanal
import ControlViajes.FormularioViajes; // Importa la clase FormularioViajes para gestionar la logística de viajes
import GestionDePilotos.INICIOGESTIONPILOTOS; // Importa la clase INICIOGESTIONPILOTOS para manejar la gestión de pilotos
import GestionDeUsuarios.INICIOGESTIONUSUARIOS; // Importa la clase INICIOGESTIONUSUARIOS para gestionar usuarios
import Login.GESTIONLOGIN; // Importa la clase GESTIONLOGIN para gestionar la lógica de inicio de sesión
import Login.LOGINPINEED; // Importa la clase LOGINPINEED para manejar la interfaz de inicio de sesión
import Login.Login; // Importa la clase Login que probablemente maneja la autenticación de usuarios
import com.toedter.calendar.JDateChooser; // Importa la clase JDateChooser para seleccionar fechas de manera visual
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.event.ActionEvent; // Importa la clase ActionEvent para manejar eventos de acción
import java.awt.event.ActionListener; // Importa la interfaz ActionListener para escuchar eventos de acción
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat; // Importa la clase SimpleDateFormat para formatear fechas
import java.util.Date; // Importa la clase Date para manejar fechas y horas
import java.util.Vector; // Importa la clase Vector para almacenar datos en una lista dinámica
import javax.swing.JOptionPane; // Importa la clase JOptionPane para mostrar diálogos de mensaje y entrada
import javax.swing.JTable; // Importa la clase JTable para crear tablas en la interfaz gráfica
import javax.swing.table.DefaultTableModel; // Importa la clase DefaultTableModel para gestionar modelos de tabla en Swing
import java.time.LocalDateTime; // Importa la clase LocalDateTime para manejar fechas y horas de manera moderna
import java.time.LocalTime; // Importa la clase LocalTime para trabajar con horas sin fechas
import java.time.format.DateTimeFormatter; // Importa la clase DateTimeFormatter para formatear fechas y horas
import java.text.ParseException; // Importa la clase ParseException para manejar errores de análisis de texto
import java.util.List; // Importa la interfaz List para manejar listas de objetos
import java.util.ArrayList; // Importa la clase ArrayList para crear listas dinámicas
import java.util.Arrays;
import java.util.function.Predicate;
import javax.swing.JFrame; // Importa la clase JFrame para crear ventanas de aplicación
import javax.swing.JTextField;
import javax.swing.SwingUtilities; // Importa la clase SwingUtilities para realizar tareas en el hilo de eventos de Swing
import GestionDeCamiones.Camiones;
import javax.swing.ListSelectionModel;



public class GARAGEGESTIONCAMIONES extends javax.swing.JFrame {
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    public FACTURASGESTIONCAMIONES gestionFacturas;
    DefaultTableModel modeloCamiones;
    DefaultTableModel modeloRegistroGastos;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    private int indiceActual;

    public GARAGEGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;
        setResizable(false);
        
        // Inicializar gestiones
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        
        gestionFacturas = new FACTURASGESTIONCAMIONES();
        gestionFacturas.cargarFacturasDesdeExcel();

        // Configurar modelo de tabla de facturas
        modeloRegistroGastos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Configurar modelo de tabla de camiones
        modeloCamiones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Definir columnas para la tabla de facturas
        String[] columnasFacturas = {
            "No.", 
            "Placas", 
            "Fecha",
            "Tipo de Gasto",
            "Descripción",
            "Monto",
            "Hora"
        };
        modeloRegistroGastos.setColumnIdentifiers(columnasFacturas);
        
        // Definir columnas para la tabla de camiones
        String[] columnasCamiones = {
            "No.", 
            "Placas", 
            "Marca", 
            "Modelo", 
            "Estado",
            "Tiempo en Reparación", 
            "Fecha de Mantenimiento", 
            "Total Invertido"
        };
        modeloCamiones.setColumnIdentifiers(columnasCamiones);

        // Configurar selección de tabla de camiones
        tblRegistroCamiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRegistroCamiones.setRowSelectionAllowed(true);
        
        // Asignar modelos a las tablas
        tblRegistroGastos.setModel(modeloRegistroGastos);
        tblRegistroCamiones.setModel(modeloCamiones);
        
        // Cargar la lista de camiones
        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
            System.out.println("Camiones cargados correctamente: " + listaCamiones.size());
        } else {
            System.out.println("Error: No se pudieron cargar los camiones.");
            listaCamiones = new Vector<>();
        }
        
        // Cargar los datos en las tablas
        cargarCamionesTabla();
        cargarFacturasTabla();
        
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        
        addWindowListener();
        setupComboBox();
        setupTextField(txtMarcaCamionBuscar, "Ingresa Marca, Modelo o Placas del Camión a buscar");
        setupTextField(txtBuscarFactura, "Ingresa Monto, Placas, Tipo, Fecha de la factura buscar");
        
        // Final frame setup
        this.setVisible(true);
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
    public void limpiarCamposBusqueda() {
        // ... otros campos que ya limpias ...
        txtMarcaCamionBuscar.setText("Ingresa Marca, Modelo o Placas del Camión a buscar");
        txtMarcaCamionBuscar.setForeground(Color.GRAY);
    }
    
    
    private void cargarCamionesTabla() {
        try {
            // Limpiar la tabla antes de cargar nuevos datos
            modeloCamiones.setRowCount(0);
            
            // Verificar si hay datos para cargar
            if (listaCamiones == null || listaCamiones.isEmpty()) {
                System.out.println("No hay camiones para cargar en la tabla");
                return;
            }

            // Cargar los datos con índice
            int index = 1;
            for (Camiones camion : listaCamiones) {
                if (camion != null) {
                    Object[] fila = {
                        index++,
                        camion.getPlacas(),
                        camion.getMarca(),
                        camion.getModelo(),
                        camion.getEstado(),
                        camion.getTiempoEnReparacion(),
                        camion.getFechaDeMantenimiento(),
                        camion.getTotal()
                    };
                    modeloCamiones.addRow(fila);
                }
            }

            // Verificar que se cargaron los datos
            System.out.println("Filas cargadas en la tabla: " + modeloCamiones.getRowCount());

            // Actualizar la vista de la tabla
            tblRegistroCamiones.repaint();
            
        } catch (Exception e) {
            System.out.println("Error al cargar los camiones en la tabla: " + e.getMessage());
            e.printStackTrace();
        }
    }

     private String obtenerHoraActual() {
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return ahora.format(formatter);
    }

   private void cargarFacturasTabla() {
        try {
            // Limpiar la tabla antes de cargar nuevos datos
            modeloRegistroGastos.setRowCount(0);
            
            // Verificar si hay facturas para cargar
            if (gestionFacturas.getCamionesfactura() == null || gestionFacturas.getCamionesfactura().isEmpty()) {
                System.out.println("No hay facturas para mostrar");
                return;
            }

            // Cargar los datos con índice
            int index = 1;
            for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
                if (factura != null) {
                    Object[] fila = {
                        index++,
                        factura.getPlacasFactura(),
                        factura.getFechaFactura(),
                        factura.getTipoDeGastoFactura(),
                        factura.getDescripcionFactura(),
                        factura.getMontoFactura(),
                        factura.getHoraActual()
                    };
                    modeloRegistroGastos.addRow(fila);
                }
            }

            // Verificar que se cargaron los datos
            System.out.println("Filas de facturas cargadas: " + modeloRegistroGastos.getRowCount());

            // Actualizar la vista de la tabla
            tblRegistroGastos.repaint();
            
        } catch (Exception e) {
            System.out.println("Error al cargar las facturas en la tabla: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarTablaGastos() {
        cargarFacturasTabla(); // Reutilizar el método existente
    }

    
    
      private void limpiarCampos() {
        txtCostoDeReparacionGasto.setText("");
        txtCostoGalonGasto.setText("");
        txtNumeroDeGalonesGasto.setText("");
        txtDescripcionDelGastoGasto.setText("");
        txtCostoDeMantenimiento.setText("");
        txtGastoNoEspecificadoGasto.setText("");
        txtTiempoMantenimientoGasto.setDate(null);
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

    
    // Método para configurar el placeholder en campos de texto
private void setupTextFieldGastos(JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY); // Establece el color del texto del placeholder

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

// Método para configurar el JDateChooser con un placeholder en gris
private void setupDateChooserWithPlaceholderGastos(JDateChooser dateChooser, String placeholder) {
    dateChooser.setDateFormatString("dd/MM/yyyy");
    dateChooser.setDate(null); // Inicialmente vacío

    JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
    editor.setForeground(Color.GRAY); // Color gris para el texto del placeholder
    editor.setText(placeholder); // Texto del placeholder

    // Listener para manejar el enfoque y el texto del placeholder
    editor.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (editor.getText().equals(placeholder)) {
                editor.setText(""); // Limpia el texto del placeholder al enfocar
                editor.setForeground(Color.BLACK); // Cambia el color a negro
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (editor.getText().isEmpty()) {
                editor.setText(placeholder); // Restaura el placeholder si está vacío
                editor.setForeground(Color.GRAY); // Cambia el color a gris
            }
        }
    });

    // Listener para actualizar el color cuando el usuario selecciona una fecha
    dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
        if (dateChooser.getDate() != null) {
            editor.setForeground(Color.BLACK); // Cambia a negro al seleccionar una fecha
        } else {
            editor.setForeground(Color.GRAY); // Vuelve al gris si no hay fecha
            editor.setText(placeholder); // Restaura el placeholder
        }
    });
}

// Método para configurar todos los campos de gastos con placeholders
private void configurarCamposDeTextoConPlaceholdersGastos() {
    setupTextFieldGastos(txtActualizarTiempoReparacion, "Ingrese el tiempo de reparación");
    setupTextFieldGastos(txtCostoDeMantenimiento, "Ingrese el costo de mantenimiento");
    setupTextFieldGastos(txtCostoDeReparacionGasto, "Ingrese el costo de reparación");
    setupTextFieldGastos(txtCostoGalonGasto, "Ingrese el costo por galón");
    setupTextFieldGastos(txtDescripcionDelGastoGasto, "Ingrese la descripción del gasto");
    setupTextFieldGastos(txtGastoNoEspecificadoGasto, "Ingrese el gasto no especificado");
    setupTextFieldGastos(txtMarcaCamionBuscar, "Ingrese la marca del camión a buscar");
    setupTextFieldGastos(txtNumeroDeGalonesGasto, "Ingrese el número de galones");
    setupDateChooserWithPlaceholderGastos(txtTiempoMantenimientoGasto, "dd/MM/yyyy"); // Configura el JDateChooser con placeholder
}

// Método para limpiar y restablecer los placeholders de los campos de gastos
public void limpiarCamposGastos() {
    txtActualizarTiempoReparacion.setText("Ingrese el tiempo de reparación");
    txtActualizarTiempoReparacion.setForeground(Color.GRAY);

    txtCostoDeMantenimiento.setText("Ingrese el costo de mantenimiento");
    txtCostoDeMantenimiento.setForeground(Color.GRAY);

    txtCostoDeReparacionGasto.setText("Ingrese el costo de reparación");
    txtCostoDeReparacionGasto.setForeground(Color.GRAY);

    txtCostoGalonGasto.setText("Ingrese el costo por galón");
    txtCostoGalonGasto.setForeground(Color.GRAY);

    txtDescripcionDelGastoGasto.setText("Ingrese la descripción del gasto");
    txtDescripcionDelGastoGasto.setForeground(Color.GRAY);

    txtGastoNoEspecificadoGasto.setText("Ingrese el gasto no especificado");
    txtGastoNoEspecificadoGasto.setForeground(Color.GRAY);

    txtMarcaCamionBuscar.setText("Ingrese la marca del camión a buscar");
    txtMarcaCamionBuscar.setForeground(Color.GRAY);

    txtNumeroDeGalonesGasto.setText("Ingrese el número de galones");
    txtNumeroDeGalonesGasto.setForeground(Color.GRAY);

    // Restablece el JDateChooser al estado del placeholder
    setupDateChooserWithPlaceholderGastos(txtTiempoMantenimientoGasto, "dd/MM/yyyy");
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
        buscarCamion = new javax.swing.JButton();
        actualizarCamion = new javax.swing.JButton();
        agregarCamion = new javax.swing.JButton();
        eliminarCamion = new javax.swing.JButton();
        txtGastoNoEspecificadoGasto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCostoDeMantenimiento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTiempoMantenimientoGasto = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtDescripcionDelGastoGasto = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroGastos = new javax.swing.JTable();
        txtActualizarEstado = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtActualizarTiempoReparacion = new javax.swing.JTextField();
        txtCostoDeReparacionGasto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCostoGalonGasto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumeroDeGalonesGasto = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        eliminarCamion1 = new javax.swing.JButton();
        refrescarCamion = new javax.swing.JButton();
        buscarFacturas = new javax.swing.JButton();
        txtBuscarFactura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("CAMION");

        txtMarcaCamionBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaCamionBuscarActionPerformed(evt);
            }
        });

        buscarCamion.setBackground(new java.awt.Color(85, 111, 169));
        buscarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarCamion.setForeground(new java.awt.Color(255, 255, 255));
        buscarCamion.setText("BUSCAR CAMION");
        buscarCamion.setBorder(null);
        buscarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCamionActionPerformed(evt);
            }
        });

        actualizarCamion.setBackground(new java.awt.Color(85, 111, 169));
        actualizarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        actualizarCamion.setForeground(new java.awt.Color(255, 255, 255));
        actualizarCamion.setText("ACTUALIZAR ESTADO");
        actualizarCamion.setBorder(null);
        actualizarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarCamionActionPerformed(evt);
            }
        });

        agregarCamion.setBackground(new java.awt.Color(85, 111, 169));
        agregarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        agregarCamion.setForeground(new java.awt.Color(255, 255, 255));
        agregarCamion.setText("AGREGAR FACTURA");
        agregarCamion.setBorder(null);
        agregarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarCamionActionPerformed(evt);
            }
        });

        eliminarCamion.setBackground(new java.awt.Color(85, 111, 169));
        eliminarCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarCamion.setForeground(new java.awt.Color(255, 255, 255));
        eliminarCamion.setText("ELIMINAR FACTURA");
        eliminarCamion.setBorder(null);
        eliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCamionActionPerformed(evt);
            }
        });

        txtGastoNoEspecificadoGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGastoNoEspecificadoGastoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("GASTO NO ESPECIFICADO");

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setText("COSTO MANTENIMIENTO");

        txtCostoDeMantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoDeMantenimientoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("TIEMPO MANTENIMIENTO");

        txtTiempoMantenimientoGasto.setDateFormatString("dd/MM/yyyy");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("DESCRIPCION DEL GASTO");

        tblRegistroGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroGastos);

        txtActualizarEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FUNCIONAL", "DESCOMPUESTO" }));
        txtActualizarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualizarEstadoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("ESTADO");

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("TIEMPO EN REPARACION");

        txtActualizarTiempoReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualizarTiempoReparacionActionPerformed(evt);
            }
        });

        txtCostoDeReparacionGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoDeReparacionGastoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setText("COSTO DE REPARACION");

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setText("COSTO DEL GALON");

        txtCostoGalonGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoGalonGastoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setText("NUMERO DE GALONES");

        txtNumeroDeGalonesGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroDeGalonesGastoActionPerformed(evt);
            }
        });

        tblRegistroCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblRegistroCamiones);

        eliminarCamion1.setBackground(new java.awt.Color(0, 153, 153));
        eliminarCamion1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        eliminarCamion1.setForeground(new java.awt.Color(255, 255, 255));
        eliminarCamion1.setText("REGRESAR A GESTION DE CAMIONES");
        eliminarCamion1.setBorder(null);
        eliminarCamion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarCamion1ActionPerformed(evt);
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

        buscarFacturas.setBackground(new java.awt.Color(85, 111, 169));
        buscarFacturas.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarFacturas.setForeground(new java.awt.Color(255, 255, 255));
        buscarFacturas.setText("BUSCAR FACTURAS");
        buscarFacturas.setBorder(null);
        buscarFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarFacturasActionPerformed(evt);
            }
        });

        txtBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarFacturaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setText("FACTURA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDescripcionDelGastoGasto))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(40, 40, 40)
                                        .addComponent(txtCostoGalonGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtActualizarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtActualizarTiempoReparacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNumeroDeGalonesGasto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCostoDeMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                    .addComponent(txtGastoNoEspecificadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCostoDeReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtBuscarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                            .addComponent(txtMarcaCamionBuscar))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(buscarFacturas, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addComponent(buscarCamion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eliminarCamion1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(actualizarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarCamion1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(buscarFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(eliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actualizarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refrescarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActualizarTiempoReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActualizarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNumeroDeGalonesGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel7))
                            .addComponent(txtCostoGalonGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostoDeReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGastoNoEspecificadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCostoDeMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcionDelGastoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
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
                .addContainerGap(724, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroDeGalonesGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroDeGalonesGastoActionPerformed

    }//GEN-LAST:event_txtNumeroDeGalonesGastoActionPerformed

    private void txtCostoGalonGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoGalonGastoActionPerformed

    }//GEN-LAST:event_txtCostoGalonGastoActionPerformed

    private void txtCostoDeReparacionGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeReparacionGastoActionPerformed

    }//GEN-LAST:event_txtCostoDeReparacionGastoActionPerformed

    private void txtActualizarTiempoReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualizarTiempoReparacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualizarTiempoReparacionActionPerformed

    private void txtActualizarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualizarEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualizarEstadoActionPerformed

    private void txtGastoNoEspecificadoGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGastoNoEspecificadoGastoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGastoNoEspecificadoGastoActionPerformed

    private void eliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCamionActionPerformed
int filaSeleccionada = tblRegistroGastos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Obtener los valores saltando la columna del índice
            String placaSeleccionada = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 1);
            String fechaFactura = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 2);
            double montoFactura = Double.parseDouble(tblRegistroGastos.getValueAt(filaSeleccionada, 5).toString());

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar esta factura?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                CAMIONESFACTURA facturaAEliminar = null;
                for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
                    if (factura.getPlacasFactura().equals(placaSeleccionada) &&
                        factura.getFechaFactura().equals(fechaFactura) &&
                        Math.abs(factura.getMontoFactura() - montoFactura) < 0.01) {
                        facturaAEliminar = factura;
                        break;
                    }
                }

                if (facturaAEliminar != null) {
                    for (Camiones camion : listaCamiones) {
                        if (camion.getPlacas().equals(placaSeleccionada)) {
                            actualizarCamionTrasEliminarFactura(camion, facturaAEliminar);
                            break;
                        }
                    }

                    // Eliminar la factura
                    gestionFacturas.getCamionesfactura().remove(facturaAEliminar);
                    actualizarTablaGastos();
                    cargarCamionesTabla();
                    gestionFacturas.guardarFacturasEnExcel();
                    guardarCamionesEnExcel();
                    JOptionPane.showMessageDialog(this, "Factura eliminada correctamente y costos actualizados.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo encontrar la factura exacta para eliminar.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para eliminar.");
        }

    }//GEN-LAST:event_eliminarCamionActionPerformed

    private void agregarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarCamionActionPerformed
int filaSeleccionada = tblRegistroCamiones.getSelectedRow();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
            return;
        }

        try {
            // Obtener las placas del camión seleccionado en la tabla
            String placasSeleccionadas = (String) tblRegistroCamiones.getValueAt(filaSeleccionada, 1);
            
            // Buscar el camión correcto en la lista usando las placas
            Camiones camiones = null;
            int indiceReal = -1;
            
            for (int i = 0; i < listaCamiones.size(); i++) {
                if (listaCamiones.get(i).getPlacas().equals(placasSeleccionadas)) {
                    camiones = listaCamiones.get(i);
                    indiceReal = i;
                    break;
                }
            }
            
            if (camiones == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el camión seleccionado.");
                return;
            }

            boolean huboModificaciones = false;
            double montoTotal = 0.0;
            String tipoDeGasto = "";
            String descripcionDelGasto = "";
            String fechaMantenimientoStr = null;

            // Variables for validation
            String costoReparacionGasto = txtCostoDeReparacionGasto.getText().trim();
            String costoGalonGasto = txtCostoGalonGasto.getText().trim();
            String galonesGasto = txtNumeroDeGalonesGasto.getText().trim();
            String costoMantenimientoGasto = txtCostoDeMantenimiento.getText().trim();
            String gastoNoEspecificadoGasto = txtGastoNoEspecificadoGasto.getText().trim();
            Date fechaMantenimiento = txtTiempoMantenimientoGasto.getDate();

            // Validate and process repair cost
            if (!costoReparacionGasto.isEmpty()) {
                double nuevoCostoReparacion = Double.parseDouble(costoReparacionGasto);
                if (nuevoCostoReparacion < 0) {
                    JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                    return;
                }
                camiones.setCostoReparacion(Math.max(0, camiones.getCostoReparacion() + nuevoCostoReparacion));
                montoTotal += nuevoCostoReparacion;
                tipoDeGasto = "Reparación";
                descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
                huboModificaciones = true;
            }

            // Validate and process fuel cost
            if (!costoGalonGasto.isEmpty() || !galonesGasto.isEmpty()) {
                if (costoGalonGasto.isEmpty() || galonesGasto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar tanto el costo por galón como el número de galones.");
                    return;
                }
                double nuevoCostoGalon = Double.parseDouble(costoGalonGasto);
                double nuevosGalones = Double.parseDouble(galonesGasto);
                if (nuevoCostoGalon < 10) {
                    JOptionPane.showMessageDialog(this, "El costo por galón no puede ser menor a Q10.");
                    return;
                }
                if (nuevoCostoGalon < 0 || nuevosGalones < 0) {
                    JOptionPane.showMessageDialog(this, "Los valores no pueden ser negativos.");
                    return;
                }
                double costoTotalCombustibleNuevo = formatearDecimal(nuevosGalones * nuevoCostoGalon);
                double galonesTotales = camiones.getGalones() + nuevosGalones;
                double costoTotalCombustible = formatearDecimal(Math.max(0, camiones.getCostoTotalCombustible() + costoTotalCombustibleNuevo));

                camiones.setGalones(galonesTotales);
                camiones.setCostoTotalCombustible(costoTotalCombustible);

                if (galonesTotales > 0) {
                    camiones.setCostoGalon(costoTotalCombustible / galonesTotales);
                }

                montoTotal += costoTotalCombustibleNuevo;
                tipoDeGasto = "Combustible";
                huboModificaciones = true;
            }

            // Validate and process maintenance cost
            if (fechaMantenimiento != null || !costoMantenimientoGasto.isEmpty()) {
                if (fechaMantenimiento == null || costoMantenimientoGasto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar tanto la fecha como el costo de mantenimiento.");
                    return;
                }
                double nuevoCostoMantenimiento = Double.parseDouble(costoMantenimientoGasto);
                if (nuevoCostoMantenimiento < 0) {
                    JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaMantenimientoStr = sdf.format(fechaMantenimiento);
                camiones.setFechaDeMantenimiento(fechaMantenimientoStr);
                camiones.setCostoMantenimiento(Math.max(0, camiones.getCostoMantenimiento() + nuevoCostoMantenimiento));
                montoTotal += nuevoCostoMantenimiento;
                tipoDeGasto = "Mantenimiento";
                descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
                huboModificaciones = true;
            }

            // Validate and process unspecified expense
            if (!gastoNoEspecificadoGasto.isEmpty()) {
                double nuevoGastoNoEspecificado = Double.parseDouble(gastoNoEspecificadoGasto);
                if (nuevoGastoNoEspecificado < 0) {
                    JOptionPane.showMessageDialog(this, "Los valores de gastos no pueden ser negativos.");
                    return;
                }
                camiones.setGastoNoEspecificado(Math.max(0, camiones.getGastoNoEspecificado() + nuevoGastoNoEspecificado));
                montoTotal += nuevoGastoNoEspecificado;
                tipoDeGasto = "No Especificado";
                descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
                huboModificaciones = true;
            }

            // Al agregar una factura, asegúrate de que el tiempo de reparación esté excluido
            if (huboModificaciones) {
                // Actualizar el total invertido
                double totalInvertido = formatearDecimal(Math.max(0,
                    camiones.getCostoReparacion() +
                    camiones.getCostoTotalCombustible() +
                    camiones.getCostoMantenimiento() +
                    camiones.getGastoNoEspecificado()));

            camiones.setTotal(totalInvertido);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());

            LocalTime ahora = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaExacta = ahora.format(formatter);

            CAMIONESFACTURA nuevaFactura = new CAMIONESFACTURA(
                camiones.getPlacas(),
                tipoDeGasto.equals("Mantenimiento") ? fechaMantenimientoStr : fechaActual,
                tipoDeGasto,
                descripcionDelGasto,
                formatearDecimal(montoTotal),
                camiones.getEstado(),
                "", // Aquí eliminamos el tiempo de reparación
                horaExacta
            );
            gestionFacturas.setUNCAMIONNFACTURA(nuevaFactura);

            listaCamiones.set(filaSeleccionada, camiones);
            guardarCamionesEnExcel();
            gestionFacturas.guardarFacturasEnExcel();

            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");

            cargarCamionesTabla();
            actualizarTablaGastos(); // Asegúrate de que esto actualice la tabla de gastos

            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron modificaciones.");
        }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos para los costos y galones.");
        }
    }//GEN-LAST:event_agregarCamionActionPerformed

    private void actualizarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarCamionActionPerformed
                                            
   int filaSeleccionada = tblRegistroCamiones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
            return;
        }

        Camiones camion = listaCamiones.get(filaSeleccionada);
        boolean huboModificaciones = false;

        // Actualizar tiempo de reparación
        String nuevoTiempoReparacion = txtActualizarTiempoReparacion.getText().trim();
        if (!nuevoTiempoReparacion.isEmpty()) {
            if (validarFormatoTiempoReparacion(nuevoTiempoReparacion)) {
                camion.setTiempoEnReparacion(nuevoTiempoReparacion);
                huboModificaciones = true;
            } else {
                JOptionPane.showMessageDialog(this, "Formato inválido para el tiempo de reparación. "
                + "Ejemplos de formatos válidos: '1 hora', '30 minutos', '2 días'. "
                + "Usa un número seguido de una unidad de tiempo (ej. segundos, minutos, horas, días, semanas, meses, años).");
            return;
            }
        }

        // Actualizar estado
        String nuevoEstado = (String) txtActualizarEstado.getSelectedItem();
        if (!nuevoEstado.equals(camion.getEstado())) {
            camion.setEstado(nuevoEstado);
            huboModificaciones = true;
        }

        if (huboModificaciones) {
            listaCamiones.set(filaSeleccionada, camion);
            guardarCamionesEnExcel();
            refrescarTablas();
            JOptionPane.showMessageDialog(this, "Camión actualizado correctamente.");

            // Limpiar campos
            txtActualizarTiempoReparacion.setText("");
            txtActualizarEstado.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron modificaciones.");
        }
                   
    }//GEN-LAST:event_actualizarCamionActionPerformed

    private void buscarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCamionActionPerformed
 String textoBusqueda = txtMarcaCamionBuscar.getText().trim();
    
    // Validación del campo de búsqueda
    if (textoBusqueda.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, completa al menos un campo de búsqueda.");
        cargarCamionesTabla(); // Cargar todos los datos si no hay término de búsqueda
        return;
    }

    realizarBusqueda(textoBusqueda);

    }//GEN-LAST:event_buscarCamionActionPerformed

    
    private void realizarBusqueda(String textoBusqueda) {
    // Convertir a minúsculas para búsqueda sin distinción de mayúsculas/minúsculas
    textoBusqueda = textoBusqueda.toLowerCase();
    String[] palabrasBusqueda = textoBusqueda.split("\\s+");
    
    modeloCamiones.setRowCount(0);
    boolean hayCoincidencias = false;
    int index = 1;

    try {
        // Recorrer la lista de camiones
        for (Camiones camion : listaCamiones) {
            if (camion == null) continue;

            // Obtener los campos de búsqueda y convertirlos a minúsculas
            String marca = camion.getMarca().toLowerCase();
            String modelo = camion.getModelo().toLowerCase();
            String placas = camion.getPlacas().toLowerCase();
            
            // Combinar todos los campos en un solo texto para búsqueda
            String textoCompleto = String.format("%s %s %s", marca, modelo, placas);
            
            if (coincideConBusqueda(textoCompleto, palabrasBusqueda)) {
                agregarCamionATabla(camion, index++);
                hayCoincidencias = true;
            }
        }

        // Mostrar mensaje si no hay coincidencias y recargar datos originales
        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
            cargarCamionesTabla();
        }

        // Asegurar que la tabla sea visible
        tblRegistroCamiones.setVisible(true);
        
    } catch (Exception e) {
        System.err.println("Error durante la búsqueda: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ocurrió un error durante la búsqueda.");
    }
}

private boolean coincideConBusqueda(String textoCompleto, String[] palabrasBusqueda) {
    for (String palabra : palabrasBusqueda) {
        if (!textoCompleto.contains(palabra)) {
            return false;
        }
    }
    return true;
}

private void agregarCamionATabla(Camiones camion, int index) {
    modeloCamiones.addRow(new Object[]{
        index,
        camion.getPlacas(),
        camion.getMarca(),
        camion.getModelo(),
        camion.getEstado(),
        camion.getTiempoEnReparacion(),
        camion.getFechaDeMantenimiento(),
        camion.getTotal()
    });
}



    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void txtCostoDeMantenimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeMantenimientoActionPerformed

    }//GEN-LAST:event_txtCostoDeMantenimientoActionPerformed

    private void eliminarCamion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarCamion1ActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_eliminarCamion1ActionPerformed

    private void refrescarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarCamionActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        GARAGEGESTIONCAMIONES abrir = new GARAGEGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarCamionActionPerformed

    private void txtMarcaCamionBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaCamionBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaCamionBuscarActionPerformed

    private void buscarFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarFacturasActionPerformed
 try {
        // Obtener valores de búsqueda
        String searchText = txtBuscarFactura.getText().trim();
        
        System.out.println("Ingresa Monto, Placas, Tipo, Fecha de la factura buscar"); // Debug
        
        // Validar entrada
        if (searchText.isEmpty()) {
            mostrarMensaje("Por favor ingrese un Monto, Placas, Tipo, Fecha de la factura buscar",
                          "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener facturas
        Vector<CAMIONESFACTURA> facturas = gestionFacturas.getCamionesfactura();
        
        if (facturas == null || facturas.isEmpty()) {
            mostrarMensaje("No hay facturas registradas en el sistema.",
                          "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Limpiar tabla antes de mostrar resultados
        modeloRegistroGastos.setRowCount(0);

        // Realizar búsqueda según el criterio
        realizarBusquedaPorCriterio(facturas, searchText);

    } catch (Exception e) {
        System.out.println("Error en búsqueda: " + e.getMessage()); // Debug
        mostrarMensaje("Error al buscar: " + e.getMessage(),
                      "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
 cargarFacturasTabla();
    }//GEN-LAST:event_buscarFacturasActionPerformed
         
    
    
    private void buscarPorPlacas(Vector<CAMIONESFACTURA> facturas, String placas) {
    int index = 1;
    boolean encontrado = false;
    String busqueda = placas.trim().toLowerCase();
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null && factura.getPlacasFactura() != null) {
            String placasFactura = factura.getPlacasFactura().trim().toLowerCase();
            if (placasFactura.contains(busqueda)) {
                agregarFilaFactura(factura, index++);
                encontrado = true;
            }
        }
    }
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas con las placas que contengan: " + placas,
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void buscarPorFecha(Vector<CAMIONESFACTURA> facturas, String fecha) {
    int index = 1;
    boolean encontrado = false;
    String busqueda = fecha.trim().toLowerCase();
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null && factura.getFechaFactura() != null) {
            String fechaFactura = factura.getFechaFactura().trim().toLowerCase();
            if (fechaFactura.contains(busqueda)) {
                agregarFilaFactura(factura, index++);
                encontrado = true;
            }
        }
    }
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas con fechas que contengan: " + fecha,
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void buscarPorTipoGasto(Vector<CAMIONESFACTURA> facturas, String tipoGasto) {
    int index = 1;
    boolean encontrado = false;
    String busqueda = tipoGasto.trim().toLowerCase();
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null && factura.getTipoDeGastoFactura() != null) {
            String tipoGastoFactura = factura.getTipoDeGastoFactura().trim().toLowerCase();
            if (tipoGastoFactura.contains(busqueda)) {
                agregarFilaFactura(factura, index++);
                encontrado = true;
            }
        }
    }
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas con tipos de gasto que contengan: " + tipoGasto,
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void realizarBusquedaPorCriterio(Vector<CAMIONESFACTURA> facturas, String searchText) {
    System.out.println("Evaluando criterio de búsqueda..."); // Debug
    
    // Buscar por fecha (formato dd/mm/yyyy o parcial)
    if (searchText.matches("\\d{1,2}(/\\d{1,2}(/\\d{1,4})?)?")) {
        System.out.println("Detectado formato de fecha"); // Debug
        buscarPorFecha(facturas, searchText);
        return;
    }
    
    // Buscar por monto (número con posible punto decimal)
    if (searchText.matches("\\d+(\\.\\d+)?")) {
        System.out.println("Detectado formato de monto"); // Debug
        try {
            double montoBuscado = Double.parseDouble(searchText);
            buscarPorMonto(facturas, montoBuscado);
            return;
        } catch (NumberFormatException e) {
            mostrarMensaje("El monto ingresado no es válido.",
                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    // Intentar buscar en todos los campos de texto
    System.out.println("Buscando coincidencias en todos los campos"); // Debug
    buscarEnTodosCampos(facturas, searchText);
}

private void buscarEnTodosCampos(Vector<CAMIONESFACTURA> facturas, String searchText) {
    int index = 1;
    boolean encontrado = false;
    String busqueda = searchText.trim().toLowerCase();
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null) {
            // Buscar en placas
            boolean matchPlacas = factura.getPlacasFactura() != null &&
                                factura.getPlacasFactura().toLowerCase().contains(busqueda);
            
            // Buscar en tipo de gasto
            boolean matchTipo = factura.getTipoDeGastoFactura() != null &&
                              factura.getTipoDeGastoFactura().toLowerCase().contains(busqueda);
            
            // Buscar en descripción
            boolean matchDescripcion = factura.getDescripcionFactura() != null &&
                                     factura.getDescripcionFactura().toLowerCase().contains(busqueda);
            
            if (matchPlacas || matchTipo || matchDescripcion) {
                agregarFilaFactura(factura, index++);
                encontrado = true;
            }
        }
    }
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas que contengan: " + searchText,
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}



private void buscarPorMonto(Vector<CAMIONESFACTURA> facturas, double montoBuscado) {
    int index = 1;
    boolean encontrado = false;
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null) {
            try {
                double montoFactura;
                Object monto = factura.getMontoFactura();
                
                // Manejar diferentes tipos de retorno
                if (monto instanceof String) {
                    String montoStr = (String) monto;
                    montoStr = montoStr.replace("$", "")
                                     .replace(",", "")
                                     .replace(" ", "")
                                     .trim();
                    montoFactura = Double.parseDouble(montoStr);
                } else if (monto instanceof Number) {
                    montoFactura = ((Number) monto).doubleValue();
                } else {
                    System.out.println("Tipo de monto no reconocido: " + monto.getClass());
                    continue;
                }
                
                if (montoFactura <= montoBuscado) {
                    agregarFilaFactura(factura, index++);
                    encontrado = true;
                }
            } catch (Exception e) {
                System.out.println("Error al procesar monto de la factura: " + e.getMessage());
            }
        }
    }
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas con monto menor o igual al especificado.",
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void agregarFilaFactura(CAMIONESFACTURA factura, int index) {
    Object[] fila = {
        index,
        factura.getPlacasFactura(),
        factura.getFechaFactura(),
        factura.getTipoDeGastoFactura(),
        factura.getDescripcionFactura(),
        factura.getMontoFactura(),
        factura.getHoraActual()
    };
    modeloRegistroGastos.addRow(fila);
}

  

private void mostrarFacturasFiltradas(Vector<CAMIONESFACTURA> facturas, 
                                    Predicate<CAMIONESFACTURA> filtro) {
    int index = 1;
    boolean encontrado = false;
    int totalFacturas = 0; // Debug
    
    for (CAMIONESFACTURA factura : facturas) {
        if (factura != null && filtro.test(factura)) {
            Object[] fila = new Object[]{
                index++,
                factura.getPlacasFactura(),
                factura.getFechaFactura(),
                factura.getTipoDeGastoFactura(),
                factura.getDescripcionFactura(),
                factura.getMontoFactura(),
                factura.getHoraActual()
            };
            modeloRegistroGastos.addRow(fila);
            encontrado = true;
            totalFacturas++; // Debug
        }
    }
    
    System.out.println("Total de facturas encontradas: " + totalFacturas); // Debug
    
    if (!encontrado) {
        mostrarMensaje("No se encontraron facturas con los criterios especificados.",
                      "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}




private void mostrarMensaje(String mensaje, String titulo, int tipo) {
    SwingUtilities.invokeLater(() -> 
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo));
}




    private void txtBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarFacturaActionPerformed

    
    

    
    



    private void guardarCamionesEnExcel() {
        gestionCamiones.guardarCamionesEnExcel();
    }

    private void refrescarTablas() {
        cargarCamionesTabla();
        cargarFacturasTabla();
    }

    
    
    private void actualizarCamionTrasEliminarFactura(Camiones camion, CAMIONESFACTURA factura) {
    double montoFactura = factura.getMontoFactura();

    switch (factura.getTipoDeGastoFactura()) {
        case "Reparación":
            camion.setCostoReparacion(formatearDecimal(Math.max(0, camion.getCostoReparacion() - montoFactura)));
            // Buscar el tiempo de reparación anterior
            String tiempoReparacionAnterior = buscarTiempoDeReparacionAnterior(camion.getPlacas(), factura.getFechaFactura());
            camion.setTiempoEnReparacion(tiempoReparacionAnterior);
            break;
        case "Combustible":
            camion.setCostoTotalCombustible(formatearDecimal(Math.max(0, camion.getCostoTotalCombustible() - montoFactura)));
            if (camion.getCostoTotalCombustible() == 0) {
                camion.setGalones(0);
                camion.setCostoGalon(0);
            }
            break;
        case "Mantenimiento":
            camion.setCostoMantenimiento(formatearDecimal(Math.max(0, camion.getCostoMantenimiento() - montoFactura)));
            String fechaMantenimientoAnterior = buscarFechaMantenimientoAnterior(camion.getPlacas(), factura.getFechaFactura());
            camion.setFechaDeMantenimiento(fechaMantenimientoAnterior);
            break;
        case "No Especificado":
            camion.setGastoNoEspecificado(formatearDecimal(Math.max(0, camion.getGastoNoEspecificado() - montoFactura)));
            break;
    }

    double nuevoTotal = formatearDecimal(Math.max(0, 
        camion.getCostoReparacion() + 
        camion.getCostoTotalCombustible() + 
        camion.getCostoMantenimiento() + 
        camion.getGastoNoEspecificado()));
    camion.setTotal(nuevoTotal);
}

private String buscarTiempoDeReparacionAnterior(String placa, String fechaEliminada) {
    List<CAMIONESFACTURA> facturasReparacion = new ArrayList<>();
    
    // Recolectar todas las facturas de reparación para este camión
    for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
        if (factura.getPlacasFactura().equals(placa) && 
            factura.getTipoDeGastoFactura().equals("Reparación")) {
            facturasReparacion.add(factura);
        }
    }
    
    // Ordenar las facturas por fecha, de más reciente a más antigua
    facturasReparacion.sort((f1, f2) -> {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha1 = sdf.parse(f1.getFechaFactura());
            Date fecha2 = sdf.parse(f2.getFechaFactura());
            return fecha2.compareTo(fecha1);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    });
    
    // Buscar el tiempo de reparación anterior
    for (CAMIONESFACTURA factura : facturasReparacion) {
        if (!factura.getFechaFactura().equals(fechaEliminada)) {
            return factura.getTiempoDeReparacionFactura();
        }
    }
    
    return "0 días"; // Si no hay más facturas de reparación
}

    private String buscarFechaMantenimientoAnterior(String placa, String fechaEliminada) {
        List<String> fechasMantenimiento = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
            if (factura.getPlacasFactura().equals(placa) && factura.getTipoDeGastoFactura().equals("Mantenimiento")) {
                fechasMantenimiento.add(factura.getFechaFactura());
            }
        }
        
        fechasMantenimiento.sort((f1, f2) -> {
            try {
                return sdf.parse(f2).compareTo(sdf.parse(f1));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        
        for (String fecha : fechasMantenimiento) {
            if (!fecha.equals(fechaEliminada)) {
                return fecha;
            }
        }
        
        return "Ninguno";
    }

private double formatearDecimal(double valor) {
    return Math.round(valor * 100.0) / 100.0;
}
    




    private boolean validarFormatoTiempoReparacion(String tiempo) {
        String[] partes = tiempo.split(" ");
        if (partes.length != 2) return false;

        try {
            Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        String unidad = partes[1].toLowerCase();
        return unidad.equals("segundo") || unidad.equals("segundos") ||
               unidad.equals("minuto") || unidad.equals("minutos") ||
               unidad.equals("hora") || unidad.equals("horas") ||
               unidad.equals("día") || unidad.equals("días") || 
               unidad.equals("semana") || unidad.equals("semanas") || 
               unidad.equals("mes") || unidad.equals("meses") || 
               unidad.equals("año") || unidad.equals("años") ||
               unidad.equals("media hora");
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
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole";      // Replace with actual role
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new GARAGEGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            
           
           
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarCamion;
    private javax.swing.JButton agregarCamion;
    private javax.swing.JButton buscarCamion;
    private javax.swing.JButton buscarFacturas;
    private javax.swing.JButton eliminarCamion;
    private javax.swing.JButton eliminarCamion1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JButton refrescarCamion;
    private javax.swing.JTable tblRegistroCamiones;
    private javax.swing.JTable tblRegistroGastos;
    private javax.swing.JComboBox<String> txtActualizarEstado;
    private javax.swing.JTextField txtActualizarTiempoReparacion;
    private javax.swing.JTextField txtBuscarFactura;
    private javax.swing.JTextField txtCostoDeMantenimiento;
    private javax.swing.JTextField txtCostoDeReparacionGasto;
    private javax.swing.JTextField txtCostoGalonGasto;
    private javax.swing.JTextField txtDescripcionDelGastoGasto;
    private javax.swing.JTextField txtGastoNoEspecificadoGasto;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNumeroDeGalonesGasto;
    private com.toedter.calendar.JDateChooser txtTiempoMantenimientoGasto;
    // End of variables declaration//GEN-END:variables
}
