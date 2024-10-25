package ControlCliente;

/**
 *
 * @author USUARIO
 */
//importamos paquetes externos que nos ayudaran
import ControlInventario.*;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.*;
import ControlViajes.FormularioViajes;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import Login.GESTIONLOGIN;
import Login.LOGINPINEED;
import Login.Login;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Vector;

//definimos las librerias de tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameClientes extends javax.swing.JFrame {

    /**
     * Creates new form FrameClientes
     */
    
    //creamos la gestiones pertinentes
    private gestionProductos gesproductos;
    private GestionClientes gesclientes;
    private gestionVentas gesventas;
    private gestionVentas gescreditos;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    //creamos vectores de gestion para clientes
    private Vector<Cliente> vectorclientes = new Vector<>();
    //un vector de los creditos
    private Vector<Venta> vectorcreditos = new Vector<>();
    //un vector de productos
    private Vector<Producto> vectorproductos = new Vector<>();
    
    //crearemos los modelos de las tablas necesarias
    DefaultTableModel modeloClientes = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    
    DefaultTableModel modeloCreditosActivos = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    
    DefaultTableModel modeloCreditosFinalizados = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    
    DefaultTableModel modeloProductos = new DefaultTableModel();
    
    
    //cremos un idice general para la tabla de clientes
    int indiceGeneralClientes;
    //cremos un indice general para la tabla de creditos
    int indiceGeneralCreditos;
    
    //creamos el indice de la tabla;
    int indiceTablaCliente;
   
    
    //creamos un vectores que tendra los indices reales de los clientes
    private Vector<Integer> indicesClientes = new Vector<>();
    
    public FrameClientes(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        //inicializamos el indice general a -1
        indiceGeneralClientes = -1;
        //inicializamos indice de creditos en -1;
       indiceGeneralCreditos = -1;
       //iniciamos el idice tabla a -1
       indiceTablaCliente = -1;
        
        //inciamos las clases
        gesproductos = new gestionProductos();
        gesclientes = new GestionClientes();
        gesventas = new gestionVentas();
        gescreditos = new  gestionVentas("excels/creditos.xlsx");
        
        //ponemos el planel de agregar clientes invisible
        panelAgregarCliente.setVisible(false);
        
        //llenamos las clases necesarias del excel
        gesproductos.setCargarInvetarioExcel();
        gesclientes.cargarExcelCliente();
        gesventas.cargarExcelVentas();
        gescreditos.cargarExcelCreditos();
        
        //creamos las tablas de clientes, creditos, creditos finalizads y la de productos
        String ids [] = {"Cliente"};
        modeloClientes.setColumnIdentifiers(ids);
        tablaClientes.setModel(modeloClientes);
        
        String ids2 [] = {"no.", "producto", "cantidad", "precio", "ganancia"};
        modeloCreditosActivos.setColumnIdentifiers(ids2);
        tablaCreditos.setModel(modeloCreditosActivos);
        
        String ids3 [] = {"no.", "producto", "cantidad", "precio", "ganancia"};
        modeloCreditosFinalizados.setColumnIdentifiers(ids3);
        tablaCreditosFinalizados.setModel(modeloCreditosFinalizados);
        
        String ids4 [] = {"producto", "Precio de Cliente"};
        modeloProductos.setColumnIdentifiers(ids4);
        tablaProductos.setModel(modeloProductos);
        
        //igualamos el vector de clientes al que posee gestion clientes
        vectorclientes = gesclientes.getClientes();
        //igualamos el vector de creditos con el de gestion credigo
        vectorcreditos = gescreditos.getCreditos();
        //igualamos el vector de productos al de gestion productos
        vectorproductos = gesproductos.getProductos();
        
        /*/////////////////////////////////////////////////////////////////////////////////////////
        //solo para pruebas
        Venta newventa1 = new Venta(1, 40, 0, 140, 3, 135, true, true, 2 );
        Venta newventa2 = new Venta(0, 50, 0, 150, 3, 135, true, false, 12 );
        
        gescreditos.addCreditoVector(newventa1);
        gescreditos.addCreditoVector(newventa2);
        
        //creamos el vector vacio
        Vector<Integer> vectorvacio = new Vector<>();
        Vector<Integer> vector1p = new Vector<>();
        Vector<Float> vector1c = new Vector<>();
        
        vector1p.add(0);
        vector1c.add(Float.parseFloat("150"));
        vector1p.add(1);
        vector1c.add(Float.parseFloat("140"));
        Cliente newcliente = new Cliente("juan", "pepito", vector1p, vector1c, vectorvacio);
        
        gesclientes.addClientesVector(newcliente);
        gesclientes.addCredito(0, 0);
        gesclientes.addCredito(0, 1);
        /////////////////////////////////////////////////////////////////////////////////////////// */
        
        //actualizamos la tabla de clientes
        actualizaTablaCliente();
        
        //actualizaremos la tabla de productos
        actualizarTablaProductos();
        
        
        //iniciamos el bucle infinito
       iniciarBucleEnHilo();
           configurarCamposCliente(); // Configura los campos con placeholders
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
   
    
    // Método para configurar el placeholder en campos de texto
private void setupTextFieldCliente(JTextField textField, String placeholder) {
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

// Método para configurar el placeholder en campos de texto de área
private void setupTextAreaCliente(JTextArea textArea, String placeholder) {
    textArea.setText(placeholder);
    textArea.setForeground(Color.GRAY); // Establece el color del texto del placeholder

    textArea.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            // Limpia el placeholder al enfocar
            if (textArea.getText().equals(placeholder)) {
                textArea.setText("");
                textArea.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Restablece el placeholder si el campo está vacío
            if (textArea.getText().isEmpty()) {
                textArea.setForeground(Color.GRAY);
                textArea.setText(placeholder);
            }
        }
    });
}



// Método para configurar todos los campos de cliente con placeholders
private void configurarCamposCliente() {
    setupTextFieldCliente(txtNombreDelClienteAñadir, "Ingrese el nombre del cliente");
    setupTextFieldCliente(txtNombreDelClienteModificar, "Ingrese el nombre del cliente");
    setupTextAreaCliente(txtDescripcionDelClienteAñadir, "Ingrese la descripción del cliente");
    setupTextAreaCliente(txtDescripcionDelClienteModificar, "Ingrese la descripción del cliente");
    setupTextFieldCliente(txtFleteModificarCredito, "Ingrese precio");
    setupTextFieldCliente(txtPrecioCostoModificarCredito, "Ingrese precio");
    setupTextFieldCliente(txtPrecioModificarCredito, "Ingrese precio");
}




// Método para limpiar y restablecer los placeholders de los campos de cliente
public void limpiarCamposCliente() {
    txtNombreDelClienteAñadir.setText("Ingrese el nombre del cliente");
    txtNombreDelClienteAñadir.setForeground(Color.GRAY);

    txtNombreDelClienteModificar.setText("Ingrese el nombre del cliente");
    txtNombreDelClienteModificar.setForeground(Color.GRAY);

    txtDescripcionDelClienteAñadir.setText("Ingrese la descripción del cliente");
    txtDescripcionDelClienteAñadir.setForeground(Color.GRAY);

    txtDescripcionDelClienteModificar.setText("Ingrese la descripción del cliente");
    txtDescripcionDelClienteModificar.setForeground(Color.GRAY);

    txtFleteModificarCredito.setText("Ingrese precio");
    txtFleteModificarCredito.setForeground(Color.GRAY);

    txtPrecioCostoModificarCredito.setText("Ingrese precio");
    txtPrecioCostoModificarCredito.setForeground(Color.GRAY);

    txtPrecioModificarCredito.setText("Ingrese precio");
    txtPrecioModificarCredito.setForeground(Color.GRAY);
}



    private void setupComboBox() {
    txtMenu1.removeAllItems();
    txtMenu1.addItem("Seleccione una opción");

    if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
        addAdminOptions();
    } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
        addSecretariaOptions();
    } else if (userRole.equalsIgnoreCase("PILOTO")) {
        addPilotOptions();
    }

    txtMenu1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) txtMenu1.getSelectedItem();
            redirectToFrame(selectedOption);
        }
    });
}

private void addAdminOptions() {
    txtMenu1.addItem("Gestión de Usuarios");
    txtMenu1.addItem("Gestión de Pilotos");
    txtMenu1.addItem("Gestión de Clientes");
    txtMenu1.addItem("Gestión de Ventas");
    txtMenu1.addItem("Gestión de Pedidos");
    txtMenu1.addItem("Inventario de Quintales");
    txtMenu1.addItem("Planilla de Trabajadores");
    txtMenu1.addItem("Gestión de Camiones");
    txtMenu1.addItem("Calendario");
    txtMenu1.addItem("Cerrar Sesión");
}

private void addSecretariaOptions() {
    txtMenu1.addItem("Gestión de Ventas");
    txtMenu1.addItem("Gestión de Clientes");
    txtMenu1.addItem("Gestión de Camiones");
    txtMenu1.addItem("Gestión de Pedidos");
    txtMenu1.addItem("Gestión de Pilotos");
    txtMenu1.addItem("Calendario");
    txtMenu1.addItem("Cerrar Sesión");
}

private void addPilotOptions() {
    txtMenu1.addItem("Calendario");
    txtMenu1.addItem("Cerrar Sesión");
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

    //esta funcion nos ayudara a actualizar la tabla de clientes
    public void actualizaTablaCliente(){
        //vaciamos la tabla primero
        modeloClientes.setRowCount(0);
        
        //vaciamos el vector
        indicesClientes.clear();
        
                        
        //creamos una variable de si el cliente esta activo o no
        boolean activoCliente = true;
        //creamos una variable que tendra la cantidad del vector de creditos
        int logitudCreditos = 0;
        
        //hacemos un for que recorra el vector de clientes y añada la informacion
        for (int i = 0; i < vectorclientes.size() ;i++) {
            activoCliente = true;
            
            //leemos la logitus del vector credito
            logitudCreditos = vectorclientes.get(i).getIndiceCredito().size();
            
            if (logitudCreditos > 0) {
                if (vectorclientes.get(i).getIndiceCredito().get(logitudCreditos-1) == -100) {
                    activoCliente = false;
                }
            }else{
                activoCliente = true;
            }
            
            if (activoCliente == true) {
                modeloClientes.addRow(new Object[]{vectorclientes.get(i).getNombre()});
                
                indicesClientes.add(i);
            }
        }
    }
    
    //esta funcion nos ayudara a mostara las tablas de creditos activos y desactivos 
    public void actualizarTablaCreditos(){
        //primero vaciamos las dos tablas
        modeloCreditosActivos.setRowCount(0);
        modeloCreditosFinalizados.setRowCount(0);
        
        //preguntamos si el indice general es mayor a -1
        if (indiceGeneralClientes > -1 ) {
            
            //cremos un vector correspondiente a los indices de los creditos
            Vector<Integer> newcreditos = vectorclientes.get(indiceGeneralClientes).getIndiceCredito();

            
            //creamos un for que pondra en las tablas los creditos correspondientes
            for (int i = 0; i < newcreditos.size(); i++) {
                
                if (vectorcreditos.get(newcreditos.get(i)).getCreditoActivo() == true && vectorcreditos.get(newcreditos.get(i)).getPrecio() > -1) {
                    modeloCreditosActivos.addRow(new Object[]{newcreditos.get(i)+1, vectorproductos.get(vectorcreditos.get(newcreditos.get(i)).getIndiceProducto()).getNombre(), vectorcreditos.get(newcreditos.get(i)).getIndiceCantidad(), vectorcreditos.get(newcreditos.get(i)).getPrecio(), vectorcreditos.get(newcreditos.get(i)).getGanancia()});
                }else if(vectorcreditos.get(newcreditos.get(i)).getPrecio() > -1){
                    modeloCreditosFinalizados.addRow(new Object[]{newcreditos.get(i)+1, vectorproductos.get(vectorcreditos.get(newcreditos.get(i)).getIndiceProducto()).getNombre(), vectorcreditos.get(newcreditos.get(i)).getIndiceCantidad(), vectorcreditos.get(newcreditos.get(i)).getPrecio(), vectorcreditos.get(newcreditos.get(i)).getGanancia()});
                }
            }
        }
    }
    
    //esta funcion nos permitira actualizar la table de productos
    public void actualizarTablaProductos(){
        //primero vaciamos la tabla
        modeloProductos.setRowCount(0);
        
        //preguntamos si el indice general es mayor a -1
        if (indiceGeneralClientes > -1 ) {
            //cremos una variable para agregar la catidad
            float newcantidadP = 0;
            
            //obtenemos el vector del clientes y sus productos
            Vector<Integer> vectorClienteP = vectorclientes.get(indiceGeneralClientes).getIndiceProducto();
            
            //recorremos el vector de productos
            for (int i = 0; i < vectorproductos.size(); i++) {
                
                newcantidadP = 0;
                //cremos un for que recorra el vector cliente p y pregunte si los productos son iguales para cambiar la cantidad
                for (int j = 0; j < vectorClienteP.size(); j++) {
                    if (vectorClienteP.get(j) == i) {
                        //cambiamos la cantidad 
                        newcantidadP = vectorclientes.get(indiceGeneralClientes).getIndiceCantidad().get(j);
                    }
                }
                modeloProductos.addRow(new Object[]{vectorproductos.get(i).getNombre(), Float.toString(newcantidadP)});
            }
        }else{
            //si no lo es, llenamos la tabla genericamente
            for (int i = 0; i < vectorproductos.size(); i++) {
                //recorremos el ford para llenar la tabla 
                modeloProductos.addRow(new Object[]{vectorproductos.get(i).getNombre(), "0"});
            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        txtMenu1 = new javax.swing.JComboBox<>();
        panelAgregarCliente = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreDelClienteAñadir = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcionDelClienteAñadir = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNombreDelClienteModificar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        scrolgenerico = new javax.swing.JScrollPane();
        txtDescripcionDelClienteModificar = new javax.swing.JTextArea();
        jPanel19 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaCreditos = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCreditosFinalizados = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        labelProducto = new javax.swing.JLabel();
        radioActivo = new javax.swing.JRadioButton();
        txtPrecioModificarCredito = new javax.swing.JTextField();
        txtFleteModificarCredito = new javax.swing.JTextField();
        txtPrecioCostoModificarCredito = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        labelCatidad = new javax.swing.JLabel();
        buscarPiloto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablaClientes.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaClientes);

        jPanel3.setBackground(new java.awt.Color(85, 111, 169));
        jPanel3.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("AÑADIR CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" CLIENTES");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(32, 67, 99));

        txtMenu1.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenu1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAgregarCliente.setBackground(new java.awt.Color(102, 102, 255));

        jPanel5.setBackground(new java.awt.Color(18, 59, 87));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setText("Nombre Del Cliente");

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("Descripción Del Cliente");

        txtNombreDelClienteAñadir.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtDescripcionDelClienteAñadir.setColumns(20);
        txtDescripcionDelClienteAñadir.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtDescripcionDelClienteAñadir.setRows(5);
        jScrollPane2.setViewportView(txtDescripcionDelClienteAñadir);

        jPanel10.setBackground(new java.awt.Color(102, 153, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(131, 28));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("AÑADIR CLIENTE");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(16, 16, 16))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAgregarClienteLayout = new javax.swing.GroupLayout(panelAgregarCliente);
        panelAgregarCliente.setLayout(panelAgregarClienteLayout);
        panelAgregarClienteLayout.setHorizontalGroup(
            panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreDelClienteAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAgregarClienteLayout.setVerticalGroup(
            panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                .addGroup(panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                        .addGroup(panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreDelClienteAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAgregarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))
                            .addGroup(panelAgregarClienteLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        jPanel15.setBackground(new java.awt.Color(128, 197, 228));

        jPanel12.setBackground(new java.awt.Color(18, 59, 87));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setText("Nombre Del Cliente");

        txtNombreDelClienteModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setText("Descripción del Cliente");

        txtDescripcionDelClienteModificar.setColumns(20);
        txtDescripcionDelClienteModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtDescripcionDelClienteModificar.setRows(5);
        scrolgenerico.setViewportView(txtDescripcionDelClienteModificar);

        jPanel19.setBackground(new java.awt.Color(102, 153, 255));
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("MODIFICAR INFORMACION DEL CLIENTE");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(26, 26, 26))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ELIMINAR CLIENTE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreDelClienteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrolgenerico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombreDelClienteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrolgenerico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaCreditos.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaCreditos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tablaCreditos);

        tablaProductos.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tablaProductos);

        jPanel16.setBackground(new java.awt.Color(102, 153, 255));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("MODIFICAR LISTA DE PRODUCTOS");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("Lista De Productos Consumidos Normalmente");

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel11.setText("Lista De Creditos Activos");

        tablaCreditosFinalizados.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaCreditosFinalizados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tablaCreditosFinalizados);

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("Lista De Creditos Finalizados");

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("Cantidad:");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("Precio:");

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("Flete:");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("Precio Costo:");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("Productos:");

        labelProducto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelProducto.setText("N/a");

        radioActivo.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        radioActivo.setText("Finalizar Credito");
        radioActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioActivoActionPerformed(evt);
            }
        });

        txtPrecioModificarCredito.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtFleteModificarCredito.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtPrecioCostoModificarCredito.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jPanel20.setBackground(new java.awt.Color(102, 153, 255));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("MODIFICAR CREDITO");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelCatidad.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelCatidad.setText("0");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelCatidad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel11Layout.createSequentialGroup()
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtPrecioCostoModificarCredito))
                                        .addComponent(radioActivo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPrecioModificarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFleteModificarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelCatidad, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioModificarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFleteModificarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioCostoModificarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(radioActivo)
                                .addGap(29, 29, 29))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        buscarPiloto.setBackground(new java.awt.Color(0, 153, 153));
        buscarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        buscarPiloto.setText("HISTORIAL CLIENTE");
        buscarPiloto.setBorder(null);
        buscarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPilotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAgregarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void radioActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioActivoActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        
        //funcion que nos permitira ocultar o mostrar el panel de agregar cliente
        //preguntamos si esta activo o no
        if (panelAgregarCliente.isVisible()) {
            //si el panel el visible lo ocultamos
            panelAgregarCliente.setVisible(false);
        }else{
            //de lo contrario lo mostramos
            panelAgregarCliente.setVisible(true);
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
        
        //funcion que nos ayudara a crear un nuevo cliente
        //primero leemos la inforacion
        
        String newname = txtNombreDelClienteAñadir.getText();
        String newdescripcion = txtDescripcionDelClienteAñadir.getText();
        
        //preguntamos si la informacion no esta vacia
        if (!newname.equals("") && !newdescripcion.equals("")) {
            
            //creamos el vector vacio
            Vector<Integer> vectorvacio = new Vector<>();
            Vector<Float> vectorvacioFlotante = new Vector<>();
            
            //creamos el objeto clientes
            Cliente newCliente = new Cliente(newname, newdescripcion, vectorvacio, vectorvacioFlotante, vectorvacio);
            
            //añadimos el cliente al vector de clientes y guardanos en el excel
            gesclientes.addClientesVector(newCliente);
            gesclientes.guardarExcelCliente();
            
            //actualizamos la tabla de clientes
            actualizaTablaCliente();
            
            //resestablecemos toda la inforcion nuevamente
            txtNombreDelClienteAñadir.setText("");
            txtDescripcionDelClienteAñadir.setText("");
            
            //ocultamos el panel de agregar clientes
            panelAgregarCliente.setVisible(false);
            
            //si el indice general el mayor a menos -1, haremos que el cliente seleccionado se mantenga seleccionado
            if (indiceTablaCliente > -1) {
                tablaClientes.setRowSelectionInterval(indiceTablaCliente, indiceTablaCliente);
            }
            
            //mostramos mensaje de que fue añadido el cliente
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Cliente añadido exitosamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
       
            
        }else{
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "llene los campos correspondientes por favor", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
        // TODO add your handling code here:
        
        //esta funcion nos ayudara a modificar la informacion general del cliente
        //primero pregutnamos si el indice de la tabla es valido
        
        if (indiceGeneralClientes > -1) {
            String newname = txtNombreDelClienteModificar.getText();
        String newdescripcion = txtDescripcionDelClienteModificar.getText();
        
        //preguntamos si la informacion no esta vacia
        if (!newname.equals("") && !newdescripcion.equals("")) {

            //modificamos el vector el cliente al vector de clientes y guardanos en el excel
            gesclientes.modificarInformacionGeneral(indiceGeneralClientes, newname, newdescripcion);
            gesclientes.guardarExcelCliente();
            
            //actualizamos la tabla de clientes
            actualizaTablaCliente();
            
            //ocultamos el panel de agregar clientes
            panelAgregarCliente.setVisible(false);
            
            //si el indice general el mayor a menos -1, haremos que el cliente seleccionado se mantenga seleccionado
            if (indiceTablaCliente > -1) {
                tablaClientes.setRowSelectionInterval(indiceTablaCliente, indiceTablaCliente);
            }
            
            //mostramos mensaje de que fue añadido el cliente
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
       
            
        }else{
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "llene los campos correspondientes por favor", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
        }else{
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "seleccione un cliente de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_jPanel19MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        
        //estafuncion nos ayudara a cambiar la informacion pertenecientes a los productos y cantidades de un clientes
        //primero pregutnamos si el indice de la tabla es valido
        
        try{
            if (indiceGeneralClientes > -1) {
           
            //creamos los vectores que tengas los indices de los productos
                    Vector<Integer> newIndiceProducto = new Vector<>();
                    Vector<Float> newIndiceCantidad = new Vector<>();
                    
                    //vamos a crear un bool que nos idique si la cantidades agregadas son demasiado pequeñas
                    boolean cantidadesValidas = true;

                    //recoremos las tabla de productos para ver cuantos productos tiene un numero distintos de 0
                    for (int i = 0; i < tablaProductos.getRowCount(); i++) {

                        //definimos las variables a utilizar
                        float cantidadDeProducto = 0;   

                        //asignamos el valor a las variables
                        cantidadDeProducto = Float.parseFloat((String) tablaProductos.getValueAt(i, 1));


                        //verificamos si la cantidad es superior a cero
                        if (cantidadDeProducto > 0) {
                            newIndiceProducto.add(i);
                            newIndiceCantidad.add(cantidadDeProducto);
                            
                            //preguntamos si el precio es menor al precio con coste y flete
                            if (0 > cantidadDeProducto - vectorproductos.get(i).getPrecioCosto() - vectorproductos.get(i).getPrecioFlete()) {
                                cantidadesValidas = false;
                            }
                        }

                    }
            
            //pretungamos si la hubo algun cambio y si se ingreso almenos una cantidad
                if (!newIndiceProducto.isEmpty()) {
                    
                    //preguntamos si las cantidades son validas
                    if (cantidadesValidas == true) {
                        
                        
                        //pasamos la listas al cliente correspondiente
                        gesclientes.modificarTablaProductos(indiceGeneralClientes, newIndiceProducto, newIndiceCantidad);
                        gesclientes.guardarExcelCliente();
                        //actualizamos lista de productos
                        actualizarTablaProductos();
                        
                        //mostramos mesaje 
                    JOptionPane.showMessageDialog(null, "Lista de precios modifica con exito", "Confirmación", JOptionPane.INFORMATION_MESSAGE);         
                    }else{
                      //mostramos mesaje 
                    JOptionPane.showMessageDialog(null, "Uno o mas productos tiene precios invalidos, son demasiado pequeños", "Confirmación", JOptionPane.INFORMATION_MESSAGE);         
                    }
                    
                    
                }else{
                    //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "ingrese al menos una cantidad", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
                }
            
        }else{
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "seleccione un cliente de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        }catch (NumberFormatException e) {
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        // TODO add your handling code here:
        
        //funcion que nos permite modificar un credito
        //funcion para modificiar un credito
        try {
           
            //preguntamos si el indice es valido
            
            if (indiceGeneralCreditos > -1) {
                 //leemos los indices del inventario y del cliente
            int newindiceproducto = vectorcreditos.get(indiceGeneralCreditos).getIndiceProducto();
            int newindicecliente =  vectorcreditos.get(indiceGeneralCreditos).getIndiceCliente();
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
                //leemos la cantidad del spin
                int newcantidad = vectorcreditos.get(indiceGeneralCreditos).getIndiceCantidad();
                

                //leemos los precios
                float newprecio = Float.parseFloat(txtPrecioModificarCredito.getText());
                float newflete = Float.parseFloat(txtFleteModificarCredito.getText());
                float newcosto = Float.parseFloat(txtPrecioCostoModificarCredito.getText());
                
                //vemos si es un credito o no
                boolean newcredito = true;
                
                //vemos si el credito esta activo o no
                boolean newactivo = !radioActivo.isSelected();
                
                //preguntamos is hay suficientes existencias para completar el pedido
                if (vectorproductos.get(newindiceproducto).getExistencias() >= newcantidad) {
                   
                    //crearemos un if para indidicar que la ganancia no puede ser negativa
                    if (newprecio - (newflete + newcosto) >= 0 && newprecio > 0 && newflete > 0 && newcosto > 0) {
                        
                      //cremos la nueva venta y  editamos el vector
                Venta newventa = new Venta(newindiceproducto, newcantidad, newindicecliente, newprecio, newcosto, newflete, newcredito, newactivo, 0);
                gescreditos.modificarCredito(indiceGeneralCreditos, newventa);
                gescreditos.guardarExcelCreditos();
                 
                
                //preguntamos si el credito fue finalizado
                        if (newactivo == false) {
                          //guardamos el credito finalizado en el vector
                gesventas.addVentaVector(newventa);
                gesventas.calcularGanancia(gesventas.getVentas().size()-1);
                //guardamo la venta en gesventas;
                gesventas.guardarExcelVentas();
                //cambiamos las existencias en el inventario
                gesproductos.setCantidad(newindiceproducto, newcantidad, "-");
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();  
                        }
                
                gesclientes.guardarExcelCliente();
                
                //actualizamos la tabla de creditos
                actualizarTablaCreditos();
                
                       
                //vaciamos los campos de informacion
                labelProducto.setText("N/A");
                labelCatidad.setText("0");
                txtPrecioModificarCredito.setText("");
                txtFleteModificarCredito.setText("");
                txtPrecioCostoModificarCredito.setText("");
                radioActivo.setSelected(false);
                
                //definimos nuevament el indice general en -1 
                indiceGeneralCreditos = -1;
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "credito modificada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                 
                    }else{
                        //monstra mensajje
                JOptionPane.showMessageDialog(null, "el costo y flete superan al precio, o existe algun valor invalido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                
                    }
               
                
                }else{
                    //monstra mensajje
                JOptionPane.showMessageDialog(null, "No hay suficientes existencias en inventario", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion una venta de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void txtMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenu1ActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
        
        //esta funcion nos ayudara a eliminar un cliente
        
         // Mostrar popup de advertencia        
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí"
        if (respuesta == JOptionPane.YES_OPTION) {
           if (indiceGeneralClientes > -1) {
                   //rellenamos las informacion correspondiente            
                txtNombreDelClienteModificar.setText("");
                txtDescripcionDelClienteModificar.setText("");

                
                labelProducto.setText("N/A");
                labelCatidad.setText("0");
                
                txtPrecioModificarCredito.setText("");
                txtFleteModificarCredito.setText("");
                txtPrecioCostoModificarCredito.setText("");
                
                radioActivo.setSelected(false);
                
                //eliminamos el cliente de la tabla
                gesclientes.addCredito(indiceGeneralClientes, -100);
                
                //reiniciamos los indices
                //inicializamos el indice general a -1
                indiceGeneralClientes = -1;
                //inicializamos indice de creditos en -1;
                indiceGeneralCreditos = -1;
                //iniciamos el idice tabla a -1
                indiceTablaCliente = -1;
                
                //actualizamos las tablas correspondientes      
                actualizaTablaCliente();
                actualizarTablaProductos();
                
                gesclientes.guardarExcelCliente();
                modeloCreditosActivos.setRowCount(0);
                modeloCreditosFinalizados.setRowCount(0);
                
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "cliente eliminado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion un cliente de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }       
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
   String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        FrameHistorialClientes abrir = new FrameHistorialClientes(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_buscarPilotoActionPerformed

    //creamos el bucle infinito
    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice actual que ha seleccionado la tabla 
            int indiceSeleccionadoClientes = -1;
            
             if (tablaClientes.getSelectedRow() > -1) {
                indiceSeleccionadoClientes = indicesClientes.get(tablaClientes.getSelectedRow());
             }
            
            //preguntamos si el indice actual y el general son diferente y mayores a -1
             if (indiceSeleccionadoClientes != indiceGeneralClientes &&  indiceSeleccionadoClientes > -1) {
                 
                 //indice de la tabla
                 indiceTablaCliente = tablaClientes.getSelectedRow();

                 //igualamos el indice general al seleccionado
                 indiceGeneralClientes = indiceSeleccionadoClientes;
                 
                 //mostramos la informacion necesaria en los campos de visualizacion
                 txtNombreDelClienteModificar.setText(vectorclientes.get(indiceGeneralClientes).getNombre());
                 txtDescripcionDelClienteModificar.setText(vectorclientes.get(indiceGeneralClientes).getDescripcion());
                 
                 //actualizamos la tabla de creditos
                 actualizarTablaCreditos();
                 
                 //actualizamos la tabla de productos;
                 actualizarTablaProductos();
                 
                 //el cliente varios no puede ser eliminado
                 if (indiceSeleccionadoClientes == 0) {
                     jPanel2.setVisible(false);
                 }else{
                     jPanel2.setVisible(true);
                 }
             }
             
             //leemos el idice seleccionado de la tabla de creditos
             int filaseleccionada = tablaCreditos.getSelectedRow();
             if (filaseleccionada > -1) {
                 int indiceSeleccionadoCreditos = (int) tablaCreditos.getValueAt(filaseleccionada, 0);
                 //le restamos 1 indice
                 indiceSeleccionadoCreditos -= 1;
                 
                 //preguntamos si ese indice es distinto al general
                 if (indiceSeleccionadoCreditos != indiceGeneralCreditos && indiceSeleccionadoCreditos > -1) {
                     //igualamos los indices
                     indiceGeneralCreditos = indiceSeleccionadoCreditos;
                     
                     System.out.println("" + indiceGeneralCreditos);
                     
                     //mostramos la informacion pertinente
                     labelProducto.setText(vectorproductos.get(vectorcreditos.get(indiceGeneralCreditos).getIndiceProducto()).getNombre());
                     labelCatidad.setText("" + vectorcreditos.get(indiceGeneralCreditos).getIndiceCantidad());
                     txtPrecioModificarCredito.setText("" + vectorcreditos.get(indiceGeneralCreditos).getPrecio());
                     txtFleteModificarCredito.setText("" + vectorcreditos.get(indiceGeneralCreditos).getPrecioFlete());
                     txtPrecioCostoModificarCredito.setText("" + vectorcreditos.get(indiceGeneralCreditos).getPrecioCosto());
                 }
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
            java.util.logging.Logger.getLogger(FrameClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                                             
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FrameClientes(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JLabel labelCatidad;
    private javax.swing.JLabel labelProducto;
    private javax.swing.JPanel panelAgregarCliente;
    private javax.swing.JRadioButton radioActivo;
    private javax.swing.JScrollPane scrolgenerico;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaCreditos;
    private javax.swing.JTable tablaCreditosFinalizados;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextArea txtDescripcionDelClienteAñadir;
    private javax.swing.JTextArea txtDescripcionDelClienteModificar;
    private javax.swing.JTextField txtFleteModificarCredito;
    private javax.swing.JComboBox<String> txtMenu1;
    private javax.swing.JTextField txtNombreDelClienteAñadir;
    private javax.swing.JTextField txtNombreDelClienteModificar;
    private javax.swing.JTextField txtPrecioCostoModificarCredito;
    private javax.swing.JTextField txtPrecioModificarCredito;
    // End of variables declaration//GEN-END:variables
}
