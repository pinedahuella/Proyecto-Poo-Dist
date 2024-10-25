package ControlPedidos;
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
import javax.swing.JButton;
import ControlViajes.*;
import Login.GESTIONLOGIN;
import Login.Login;




//librerias para las fechas
import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//definimos las librerias de tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.util.Vector;
/**
 *
 * @author USUARIO
 */
public class FormularioPedidos extends javax.swing.JFrame {

    /**
     * Creates new form FormularioPedidos
     */
    
    //definimos el la gestion de pilotos, fechas, caminos e inventario
    private GestionCalendario gescalendario;
    private gestionProductos gesproductos;
    private GESTIONPILOTOS gespilotos;
    private GESTIONCAMIONES gescamiones;
    private GestionPedido gespedidos;
    
    //creamos los vectores necesario
    Vector<Producto> productosTablaNew;
    Vector<Pedido> pedidoTablaNew;
    Vector<FechaCalendario> FechaTablaNew;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    //crearemos los modelos de las tablas de productos a
    DefaultTableModel modeloProductosA = new DefaultTableModel();
    DefaultTableModel modeloProductosB = new DefaultTableModel();
    DefaultTableModel modeloProductosC = new DefaultTableModel();
    
    //crearemos los modelos de las tablas de pedidos
    DefaultTableModel modeloPedidosA = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    DefaultTableModel modeloPedidosB = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    
    //creamos el indice que se ha seleccionado
    int indiceGeneral;
    int indiceGeneralB;
    
    public FormularioPedidos(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        //inicializamos el indice actual en -1
        indiceGeneral = -1;
        indiceGeneralB = -1;
        
        //creamos los objetos de gestion
        gescalendario = new GestionCalendario();
        gesproductos = new gestionProductos();
        gespilotos = new GESTIONPILOTOS();
        gescamiones = new GESTIONCAMIONES();
        gespedidos = new GestionPedido();
        
        //cargamos los valores del excel del las gestiones
        gescalendario.cargarFechasExcel();
        gesproductos.setCargarInvetarioExcel();
        gespilotos.cargarPilotosDesdeExcel();
        gescamiones.cargarCamionesDesdeExcel();
        gespedidos.CargaDeExcel();
        
        //tabla productosA creamos la tabla
        String ids [] = {"productos", "cantidades"};
        modeloProductosA.setColumnIdentifiers(ids);
        tablaProductosA.setModel(modeloProductosA);
        
        modeloProductosB.setColumnIdentifiers(ids);
        tablaProductosB.setModel(modeloProductosB);
        
        modeloProductosC.setColumnIdentifiers(ids);
        tablaProductosC.setModel(modeloProductosC);
        
        //cremos la tabla para pedidos
        String ids2 [] = {"No. pedido", "Fecha Carga", "Fecha Descarga"};
        modeloPedidosA.setColumnIdentifiers(ids2);
        tablaPedidosA.setModel(modeloPedidosA);
        
        modeloPedidosB.setColumnIdentifiers(ids2);
        tablaPedidosB.setModel(modeloPedidosB);
        
        //actualizamos la tabla de pedidos A
        productosTablaNew = gesproductos.getProductos();
        ActualizarTablaA();
        
        //inicializamos el vector de pedidos
        pedidoTablaNew = gespedidos.getPedidos();
        
        //inicamos los vectores de fechas
        FechaTablaNew = gescalendario.getFechasDeCalendario(); 

        //e inicializamos la tabla pedidos B
        for (Producto prod : productosTablaNew) {
            modeloProductosB.addRow(new Object[]{prod.getNombre(), "0"});
            modeloProductosC.addRow(new Object[]{prod.getNombre(), "0"});
        }
        
        //llenamos el combo box de pilotos
        Vector<Piloto> pilotonew = gespilotos.getPilotos();
        Vector<Camiones> camionesnew = gescamiones.getCamiones();
        //vector para agregar los pilotos al comobobox
        for (int i = 0; i < pilotonew.size(); i++) {
            txtPilotoAgregar.addItem(pilotonew.get(i).getNombrePiloto());
            txtPilotoModificar.addItem(pilotonew.get(i).getNombrePiloto());
        }
        for (int i = 0; i < camionesnew.size(); i++) { 
            txtTransportePesadoAgregar.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
            txtTransportePesadoModificar.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
        }
        
        
        //actualizamos la tabla de pedidos
        actualizarTablaDePedidos();
             

       //iniciamos el bucle infinito
        iniciarBucleEnHilo(); 
        configurarDateChoosersConPlaceholders();
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
   
    private void setupDateChooserWithPlaceholder(JDateChooser dateChooser, String placeholder) {
    dateChooser.setDateFormatString("dd/MM/yyyy"); // Formato de fecha deseado
    dateChooser.setDate(null); // Asegúrate de que esté vacío al inicio

    // Obtener el editor de fecha como JTextField
    JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
    
    // Inicializar con el placeholder
    editor.setText(placeholder);
    editor.setForeground(Color.GRAY);
    
    // Añadir un listener para manejar el enfoque
    editor.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            // Si no hay fecha seleccionada, establecer el placeholder
            if (dateChooser.getDate() == null) {
                editor.setText(""); // Limpia el texto al enfocar
                editor.setForeground(Color.BLACK); // Cambia el color a negro
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Si no hay fecha seleccionada, restablecer el placeholder
            if (dateChooser.getDate() == null) {
                editor.setText(placeholder); // Restaura el placeholder si está vacío
                editor.setForeground(Color.GRAY); // Cambia el color a gris
            }
        }
    });
}

// Método para configurar todos los JDateChooser con placeholders
private void configurarDateChoosersConPlaceholders() {
    setupDateChooserWithPlaceholder(txtFechaDeCargaAgregar, "dd/MM/yyyy");
    setupDateChooserWithPlaceholder(txtFechaDeCargaModificar, "dd/MM/yyyy");
    setupDateChooserWithPlaceholder(txtFechaDeDescargaAgregar, "dd/MM/yyyy");
    setupDateChooserWithPlaceholder(txtFechaDeDescargaModificar, "dd/MM/yyyy");
}

// Método para limpiar y restablecer los placeholders de los JDateChooser
public void limpiarDateChoosers() {
    
        ((JTextField) txtFechaDeCargaAgregar.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
        ((JTextField) txtFechaDeCargaModificar.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
        ((JTextField) txtFechaDeDescargaAgregar.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
        ((JTextField) txtFechaDeDescargaModificar.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
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
    
    
    
    //funcion para actualizar la tabla de productos necesarias para cada producto;
    public void ActualizarTablaA(){
        modeloProductosA.setRowCount(0);
        
        for (Producto prod : productosTablaNew) {
            modeloProductosA.addRow(new Object[]{prod.getNombre(), "0"});
         }
    };
    
    //funcion para poder actualizar la tabla de productos b
    private void ActualizarTablaB(){
        
        if (indiceGeneral > -1) {
            //primero vaciaremos la tabla totalmente
        modeloProductosB.setRowCount(0);
        
        int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosA.getValueAt(indiceGeneral, 0) -1).getIndiceViaje();
        
        //creamos un for para crear la tabla
        for (int i = 0; i < productosTablaNew.size(); i++) {
            
            //creamos un int para el numero de cantidades del pedido 
            int numeroDeCantidades = 0;
            
            //un for para para recorrer el vector de indices y modificar las existencias
            for (int j = 0; j < FechaTablaNew.get(indiceDelPedido).getIndiceProductos().size(); j++) {
                if (i == FechaTablaNew.get(indiceDelPedido).getIndiceProductos().get(j)) {
                    numeroDeCantidades = FechaTablaNew.get(indiceDelPedido).getIndiceCantidad().get(j);
                }
            }
                        
            //actualizamos la tabla y sus cantidades
            modeloProductosB.addRow(new Object[]{productosTablaNew.get(i).getNombre(), Integer.toString(numeroDeCantidades)});            
        }
        
        }
    };
    
    //funcion para poder actualizar la tabla de productos b
    private void ActualizarTablaC(){
        
        if (indiceGeneralB > -1) {
            //primero vaciaremos la tabla totalmente
        modeloProductosC.setRowCount(0);
        
        int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosB.getValueAt(indiceGeneralB, 0) -1).getIndiceViaje();
        
        //creamos un for para crear la tabla
        for (int i = 0; i < productosTablaNew.size(); i++) {
            
            //creamos un int para el numero de cantidades del pedido 
            int numeroDeCantidades = 0;
            
            //un for para para recorrer el vector de indices y modificar las existencias
            for (int j = 0; j < FechaTablaNew.get(indiceDelPedido).getIndiceProductos().size(); j++) {
                if (i == FechaTablaNew.get(indiceDelPedido).getIndiceProductos().get(j)) {
                    numeroDeCantidades = FechaTablaNew.get(indiceDelPedido).getIndiceCantidad().get(j);
                }
            }
                        
            //actualizamos la tabla y sus cantidades
            modeloProductosC.addRow(new Object[]{productosTablaNew.get(i).getNombre(), numeroDeCantidades});            
        }
        
        }
    };
    
    //funcion que nos permite modificar la tabla de pedidos activos y cancelados
    public void actualizarTablaDePedidos(){
        
        //reiniciamos las dos tablas
        modeloPedidosA.setRowCount(0);
        modeloPedidosB.setRowCount(0);
        
        //creamos un formato para escribir la fecha
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        
        //creamos un for para recorrer todos los pedidos
        for (int i = 0; i < pedidoTablaNew.size(); i++) {
            
            //definimos el indice de la fecha a utilizar
            int indiceAUsar = pedidoTablaNew.get(i).getIndiceViaje();
            
            //madamos la fecha a la tabla de 
            if (FechaTablaNew.get(indiceAUsar).getActivo() == true) {
                modeloPedidosA.addRow(new Object[]{i+1, formato.format(FechaTablaNew.get(indiceAUsar).getFechaC()), formato.format(FechaTablaNew.get(indiceAUsar).getFechaD())});   

            }else{
                modeloPedidosB.addRow(new Object[]{i+1, formato.format(FechaTablaNew.get(indiceAUsar).getFechaC()), formato.format(FechaTablaNew.get(indiceAUsar).getFechaD())});
            }
            
        }
        
        
        indiceGeneral = -1;
        indiceGeneralB = -1;
        

        
       
            //igualamos las fechas
             txtFechaDeCargaModificar.setDate(null);    
             txtFechaDeDescargaModificar.setDate(null);
                
            //igualamos el combo box de pilotos y camiones
            txtPilotoModificar.setSelectedIndex(0);
            txtTransportePesadoModificar.setSelectedIndex(0);
                
            modeloProductosB.setRowCount(0);
            modeloProductosC.setRowCount(0);
            
             //igualamos las fechas
            labelFechaC.setText("Fecha de Carga");    
            labelFechaD.setText("Fecha de Descarga");

            //igualamos el combo box de pilotos y camiones
            labelPiloto.setText("piloto");
            labelCamion.setText("Transporte pesado");
            
           //e inicializamos la tabla pedidos B
            for (Producto prod : productosTablaNew) {
                modeloProductosB.addRow(new Object[]{prod.getNombre(), "0"});
                modeloProductosC.addRow(new Object[]{prod.getNombre(), "0"});
            }

    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProductosB = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        radioFinalizadoA = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFechaDeDescargaModificar = new com.toedter.calendar.JDateChooser();
        txtFechaDeCargaModificar = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtPilotoModificar = new javax.swing.JComboBox<>();
        txtTransportePesadoModificar = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        labeltPedido = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaProductosC = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        labelFechaC = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        labelFechaD = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        labelPiloto = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        labelCamion = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductosA = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFechaDeCargaAgregar = new com.toedter.calendar.JDateChooser();
        txtFechaDeDescargaAgregar = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPilotoAgregar = new javax.swing.JComboBox<>();
        txtTransportePesadoAgregar = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        formato1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidosA = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPedidosB = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(32, 67, 99));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(102, 102, 255));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        tablaProductosB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaProductosB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tablaProductosB);

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setText("Productos Del Pedido");

        radioFinalizadoA.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        radioFinalizadoA.setText("Finalizar Pedido");
        radioFinalizadoA.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setText("Fecha De Carga");

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setText("Fecha De Descarga");

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setText("Piloto");

        txtPilotoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtTransportePesadoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("Transporte Pesado");

        jPanel10.setBackground(new java.awt.Color(102, 153, 255));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("MODIFICAR PEDIDO");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        labeltPedido.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labeltPedido.setText("Tipo de Pedido:");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("Para la Distribuidora");

        jPanel5.setBackground(new java.awt.Color(102, 153, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("ELIMINAR PEDIDO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radioFinalizadoA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtPilotoModificar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFechaDeCargaModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFechaDeDescargaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(txtTransportePesadoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labeltPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 82, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(radioFinalizadoA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeCargaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeDescargaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTransportePesadoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeltPedido)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("MODIFICAR PEDIDO", jPanel4);

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("Productos Del Pedido");

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("Piloto");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("Transporte Pesado");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("Fecha De Descarga");

        tablaProductosC.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaProductosC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablaProductosC);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        labelFechaC.setBackground(new java.awt.Color(255, 255, 255));
        labelFechaC.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelFechaC.setText("Fecha De Carga");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFechaC, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFechaC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("Fecha De Carga");

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        labelFechaD.setBackground(new java.awt.Color(255, 255, 255));
        labelFechaD.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelFechaD.setText("Fecha De Descarga");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFechaD, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFechaD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        labelPiloto.setBackground(new java.awt.Color(255, 255, 255));
        labelPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelPiloto.setText("Piloto");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPiloto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPiloto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        labelCamion.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelCamion.setText("Transporte Pesado");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCamion, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCamion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(2, 2, 2)))
                                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel16)))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PEDIDOS FINALIZADOS", jPanel11);

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        tablaProductosA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaProductosA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablaProductosA);

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel1.setText("Productos Del Pedido");

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("Fecha De Carga");

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("Fecha De Descarga");

        txtFechaDeCargaAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtFechaDeDescargaAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setText("Piloto");

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setText("Transporte Pesado");

        txtPilotoAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtTransportePesadoAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(102, 153, 255));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("AGREGAR PEDIDO");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtPilotoAgregar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFechaDeCargaAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTransportePesadoAgregar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtFechaDeDescargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaDeCargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaDeDescargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPilotoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTransportePesadoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("AGREGAR PEDIDO", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(24, 24, 24)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" PEDIDOS");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        formato1.setBackground(new java.awt.Color(60, 100, 132));
        formato1.setForeground(new java.awt.Color(255, 255, 255));
        formato1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        tablaPedidosA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaPedidosA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPedidosA);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );

        formato1.addTab("PEDIDOS EN PROCESO", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        tablaPedidosB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaPedidosB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaPedidosB);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
        );

        formato1.addTab("PEDIDOS FINALIZADOS", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(formato1))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(formato1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    
    // Método auxiliar para verificar si un piloto está disponible
private boolean isPilotoDisponible(int indicePiloto) {
    // Obtener el piloto del sistema de gestión
    Vector<Piloto> pilotos = gespilotos.getPilotos();
    
    if (indicePiloto >= 0 && indicePiloto < pilotos.size()) {
        Piloto piloto = pilotos.get(indicePiloto);
        String estado = piloto.getEstadoPiloto();
        
        if (!estado.equals("ACTIVO")) {
            String mensaje = "No se puede asignar este piloto porque está " + 
                (estado.equals("INACTIVO") ? "INACTIVO" :
                estado.equals("ENFERMO") ? "ENFERMO" :
                estado.equals("EN VACACIONES") ? "EN VACACIONES" :
                estado.equals("JUBILADO") ? "JUBILADO" : "en estado no disponible");
                
            JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    return false;
}



// Método auxiliar para verificar si un camión está disponible
private boolean isCamionDisponible(int indiceCamion) {
    // Obtener la lista de camiones del sistema de gestión
    Vector<Camiones> camiones = gescamiones.getCamiones();
    
    // Verificar si el índice es válido
    if (indiceCamion >= 0 && indiceCamion < camiones.size()) {
        // Obtener el camión en el índice dado
        Camiones camion = camiones.get(indiceCamion);
        
        // Obtener el estado del camión
        String estado = camion.getEstado();
        
        // Verificar si el camión está disponible (es funcional)
        if (!estado.equals("FUNCIONAL")) {
            // Crear el mensaje según el estado del camión
            String mensaje = "No se puede asignar este camión porque está " + 
                (estado.equals("DESCOMPUESTO") ? "DESCOMPUESTO" :
                estado.equals("EN MANTENIMIENTO") ? "EN MANTENIMIENTO" :
                estado.equals("INACTIVO") ? "INACTIVO" : "en estado no disponible");
                
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Si el índice no es válido, se retorna false
    return false;
}


    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        
        //funcion para hacer un nuevo pedido
        
        //leemos las fechas primero primero:  
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        Date newFechaCarga = txtFechaDeCargaAgregar.getDate();
        Date newFechaDescarga = txtFechaDeDescargaAgregar.getDate();
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // Restar un día a la fecha actual
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaActualMenosUnDia = calendar.getTime();
        
   try {
        // Primero verificamos si el piloto está disponible
        int newIndicePiloto = txtPilotoAgregar.getSelectedIndex();
        if (!isPilotoDisponible(newIndicePiloto)) {
            return; // Si el piloto no está disponible, terminamos la ejecución
        }
        
        int newIndiceCamion = txtTransportePesadoAgregar.getSelectedIndex();
        if (!isCamionDisponible(newIndiceCamion)) {
        return;
        }
          //verifica que las fechas sean validas
        if (newFechaCarga != null && newFechaDescarga != null && !newFechaCarga.before(fechaActualMenosUnDia) && !newFechaDescarga.before(fechaActualMenosUnDia) && !newFechaDescarga.before(newFechaCarga)) {
            

            //miramos si el viaje es una compra o una venta
            boolean newcompra;
            newcompra = true;

            
            //creamos los vectores que tengas los indices de los productos
            Vector<Integer> newIndiceProducto = new Vector<>();
            Vector<Integer> newIndiceCantidad = new Vector<>();
            
            //recoremos las tabla B para ver cuantos productos forman parte del viaje
            for (int i = 0; i < tablaProductosA.getRowCount(); i++) {
                
                //definimos las variables a utilizar
                int cantidadDeProducto = 0;     
                Object value = tablaProductosA.getValueAt(i, 1);
                
                //asignamos el valor a las variables
                cantidadDeProducto = Integer.parseInt((String) tablaProductosA.getValueAt(i, 1));
               
                
                //verificamos si la cantidad es superior a cero
                if (cantidadDeProducto > 0) {
                    newIndiceProducto.add(i);
                    newIndiceCantidad.add(cantidadDeProducto);
                }
                
            }
            
                            
            //verificamos que los vectores no esten vacios
            if (!newIndiceProducto.isEmpty()) {
                //creamos la fecha nueva
                FechaCalendario newFecha = new FechaCalendario(newFechaCarga, newFechaDescarga, newIndicePiloto, newIndiceCamion, newIndiceProducto, newIndiceCantidad, true, newcompra);
                
                //la agregamos a la lista de fechas
                gescalendario.agregarFecha(newFecha);
                
                //creamos el nuevo pedido
                Pedido newPedido = new Pedido(FechaTablaNew.size()-1);
                
                System.out.println(FechaTablaNew.size()-1);
                gespedidos.agregarPedido(newPedido);
        
                //imprimos que la fecha se haya hecho correctamente
                System.out.println("se ha creado la fecha correctamente");
                
                //reiniciamos todos los elemento necesario
                ActualizarTablaA();
                
                txtPilotoAgregar.setSelectedIndex(0);
                txtTransportePesadoAgregar.setSelectedIndex(0);

                //actualizamos la tabla general de pedidos
                actualizarTablaDePedidos();
                
                //cargamos los datos en el excel
                gescalendario.guardarFecharExcel();
                
                //cargamos el pedido al excel
                gespedidos.GuardarEnExcel();
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "Datos agregados de forma correcta", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                                    limpiarDateChoosers();


            }else{
               //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "Ingresa al menos un producto ", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
            }
            
        }else{
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa fecha Valida", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }  
            
        } catch (NumberFormatException e) {
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
        
        //funcion para modificar pedido
        //leemos las fechas primero primero:  
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        Date newFechaCarga = txtFechaDeCargaModificar.getDate();
        Date newFechaDescarga = txtFechaDeDescargaModificar.getDate();
        
        try {
            if (indiceGeneral > -1) {
                
                //seleccionamos el indice a utiliza para la fecha necesaria
                int indiceDelPedidoAntiguo = pedidoTablaNew.get((int) tablaPedidosA.getValueAt(indiceGeneral, 0) -1).getIndiceViaje();

                //fecha antigua
                Date fechaAntigua = FechaTablaNew.get(indiceDelPedidoAntiguo).getFechaC();

                
                // Crear una instancia de Calendar y establecer la fechaAntigua
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaAntigua);
        // Restar un día
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        // Obtener la nueva fecha
        Date nuevaFecha = calendar.getTime();
                        
        // Primero verificamos si el piloto está disponible
        int newIndicePiloto = txtPilotoModificar.getSelectedIndex();
        if (!isPilotoDisponible(newIndicePiloto)) {
            return; // Si el piloto no está disponible, terminamos la ejecución
        }
        
        
        int newIndiceCamion = txtTransportePesadoModificar.getSelectedIndex();
        if (!isCamionDisponible(newIndiceCamion)) {
        return;
        }
                       //verifica que las fechas sean validas
                if (newFechaCarga != null && newFechaDescarga != null && !newFechaDescarga.before(newFechaCarga) && nuevaFecha.before(newFechaCarga) && nuevaFecha.before(newFechaDescarga)) {

                    //miramos si el viaje es una compra o una venta
                    boolean newcompra;
                    newcompra = true;

                    boolean PedidoCancelado = !radioFinalizadoA.isSelected();


                    //creamos los vectores que tengas los indices de los productos
                    Vector<Integer> newIndiceProducto = new Vector<>();
                    Vector<Integer> newIndiceCantidad = new Vector<>();

                    //recoremos las tabla B para ver cuantos productos forman parte del viaje
                    for (int i = 0; i < tablaProductosB.getRowCount(); i++) {

                        //definimos las variables a utilizar
                        int cantidadDeProducto = 0;   
                        Object value =  tablaProductosB.getValueAt(i, 1);

                        //asignamos el valor a las variables
                        cantidadDeProducto = Integer.parseInt((String) tablaProductosB.getValueAt(i, 1));


                        //verificamos si la cantidad es superior a cero
                        if (cantidadDeProducto > 0) {
                            newIndiceProducto.add(i);
                            newIndiceCantidad.add(cantidadDeProducto);
                        }

                    }


                    //verificamos que los vectores no esten vacios
                    if (!newIndiceProducto.isEmpty()) {
                        //creamos la fecha nueva
                        FechaCalendario newFecha = new FechaCalendario(newFechaCarga, newFechaDescarga, newIndicePiloto, newIndiceCamion, newIndiceProducto, newIndiceCantidad, PedidoCancelado, newcompra);

                        //seleccionamos el indice a utiliza para la fecha necesaria
                        int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosA.getValueAt(indiceGeneral, 0) -1).getIndiceViaje();


                        //modificamos la lista de fechas
                        gescalendario.modificarFecha(indiceDelPedido, newFecha);


                        //imprimos que la fecha se haya hecho correctamente
                        System.out.println("se ha creado la modificado correctamente");
                        
                        //ahora vamos a preguntar si el pedido fue cancelado para sumarle las cantidades al inventario de quintales
                        if (PedidoCancelado == false) {
                            //recoremos las tabla B para ver cuantos productos forman parte del viaje
                            for (int i = 0; i < tablaProductosB.getRowCount(); i++) {

                                //definimos las variables a utilizar
                                int cantidadDeProducto = 0;   
                                Object value =  tablaProductosB.getValueAt(i, 1);

                                //asignamos el valor a las variables
                                cantidadDeProducto = Integer.parseInt((String) tablaProductosB.getValueAt(i, 1));

                                gesproductos.setCantidad(i, cantidadDeProducto, "+");                               

                            }
                            
                            gesproductos.getCargarInvetarioExcel();
                        }

                        //actualizamos la tabla general de pedidos
                        actualizarTablaDePedidos();

                        //cargamos los datos en el excel
                        gescalendario.guardarFecharExcel();
                        

                        //mostramos mesaje para acetar que este bien
                        JOptionPane.showMessageDialog(null, "Datos agregados se modificaron correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                                            limpiarDateChoosers();

                    }else{
                       //mostramos mesaje para acetar que este bien
                        JOptionPane.showMessageDialog(null, "Ingresa al menos un producto ", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
                    }

                }else{
                    //mostramos mesaje para acetar que este bien
                    JOptionPane.showMessageDialog(null, "Ingresa fecha Valida", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }  
            }else{
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "seleccione un pedido de la tabla valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE); 
            }
            
        } catch (NumberFormatException e) {
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jPanel10MouseClicked

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        
        //funcion para eliminar un pedido
        
        // Mostrar popup de advertencia        
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí"
        if (respuesta == JOptionPane.YES_OPTION) {
               if (indiceGeneral > -1) {
                
                   //obtenemos el indice que se utilizara para el pedido
                    int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosA.getValueAt(indiceGeneral, 0) -1).getIndiceViaje();
                 
                   gescalendario.getFechasDeCalendario().remove(indiceDelPedido);
                   gescalendario.guardarFecharExcel();
                   
                   gespedidos.actualizarIndiceCalendario(indiceDelPedido);
                   
                   gespedidos.GuardarEnExcel();
                   
                   //actualizamos la tabla general de pedidos
                   actualizarTablaDePedidos();
                   
                    //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "pedido eliminado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE); 
           
            }else{
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "seleccione un pedido de la tabla valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE); 
            }

        }
    }//GEN-LAST:event_jPanel5MouseClicked

    
    
    
    //creamos el bucle infinito
    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice actual que ha seleccionado la tabla 
            int indiceSeleccionado = tablaPedidosA.getSelectedRow();
            
            
            //preguntamos si el indice seleccionado es diferente al actual y mayor que -1
             if (indiceSeleccionado != indiceGeneral && indiceSeleccionado > -1) {
                
                 //igualamos el indice seleccionado al actual
                 indiceGeneral = indiceSeleccionado;
                 
                 //obtenemos el indice que se utilizara para el pedido
                 int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosA.getValueAt(indiceGeneral, 0) -1).getIndiceViaje();
                 
                 
                 //agregamos todos los valores necesarios
                 
                 //igualamos las fechas
                txtFechaDeCargaModificar.setDate(FechaTablaNew.get(indiceDelPedido).getFechaC());    
                txtFechaDeDescargaModificar.setDate(FechaTablaNew.get(indiceDelPedido).getFechaD());
                
                //igualamos el combo box de pilotos y camiones
                txtPilotoModificar.setSelectedIndex(FechaTablaNew.get(indiceDelPedido).getIndicePiloto());
                txtTransportePesadoModificar.setSelectedIndex(FechaTablaNew.get(indiceDelPedido).getIndiceCamion());
                
                //actualizamos la tabla de productos 
                ActualizarTablaB();
                 
             }
             
            //leemos el indice actual de la tabla de pedidos finalizados
            int indiceSelecionadoB = tablaPedidosB.getSelectedRow();
            
            
            //preguntamos si el indice general es diferente al indice seleccionado
            if (indiceSelecionadoB != indiceGeneralB && indiceSelecionadoB > -1) {
            
                //creamos un formato para escribir la fecha
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                
                //igualamos el indice seleccionado al actual
                 indiceGeneralB = indiceSelecionadoB;
                 
                 //obtenemos el indice que se utilizara para el pedido
                 int indiceDelPedido = pedidoTablaNew.get((int) tablaPedidosB.getValueAt(indiceGeneralB, 0) -1).getIndiceViaje();
                 
                 
                 //agregamos todos los valores necesarios
                 
                 //igualamos las fechas
                labelFechaC.setText(formato.format(FechaTablaNew.get(indiceDelPedido).getFechaC()));    
                labelFechaD.setText(formato.format(FechaTablaNew.get(indiceDelPedido).getFechaD()));
                
                //utilizamos los vectores para llenar el label
                Vector<Piloto> pilotonew = gespilotos.getPilotos();
                Vector<Camiones> camionesnew = gescamiones.getCamiones();
                
                //igualamos el combo box de pilotos y camiones
                labelPiloto.setText(pilotonew.get(FechaTablaNew.get(indiceDelPedido).getIndicePiloto()).getNombrePiloto());
                labelCamion.setText(camionesnew.get(FechaTablaNew.get(indiceDelPedido).getIndiceCamion()).getMarca()
                + " " + camionesnew.get(FechaTablaNew.get(indiceDelPedido).getIndiceCamion()).getModelo());
                
                //actualizamos la tabla de productos 
                ActualizarTablaC();
                
            }
             
            
            //limita los recursos del bucle
            try {
                    // Pausa el hilo por 100ms para reducir el consumo de recursos
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                 e.printStackTrace();
                }
            }
        });

        // Iniciar el hilo
        hiloBucle.start();

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
            java.util.logging.Logger.getLogger(FormularioPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                             
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FormularioPedidos(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane formato1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JLabel labelCamion;
    private javax.swing.JLabel labelFechaC;
    private javax.swing.JLabel labelFechaD;
    private javax.swing.JLabel labelPiloto;
    private javax.swing.JLabel labeltPedido;
    private javax.swing.JRadioButton radioFinalizadoA;
    private javax.swing.JTable tablaPedidosA;
    private javax.swing.JTable tablaPedidosB;
    private javax.swing.JTable tablaProductosA;
    private javax.swing.JTable tablaProductosB;
    private javax.swing.JTable tablaProductosC;
    private com.toedter.calendar.JDateChooser txtFechaDeCargaAgregar;
    private com.toedter.calendar.JDateChooser txtFechaDeCargaModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeDescargaAgregar;
    private com.toedter.calendar.JDateChooser txtFechaDeDescargaModificar;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JComboBox<String> txtPilotoAgregar;
    private javax.swing.JComboBox<String> txtPilotoModificar;
    private javax.swing.JComboBox<String> txtTransportePesadoAgregar;
    private javax.swing.JComboBox<String> txtTransportePesadoModificar;
    // End of variables declaration//GEN-END:variables
}
