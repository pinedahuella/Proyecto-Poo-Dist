/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ControlVentas;

/**
 *
 * @author USUARIO
 */
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.*;

//importamos de clientes aun falta
import ControlCliente.*;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
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

//definimos las librerias de tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//clases para calendario
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Vector;

public class FrameVentaDiaria extends javax.swing.JFrame {

    
    //creamos la gestiones pertinentes
    private gestionProductos gesproductos;
    private GestionClientes gesclientes;
    private gestionVentas gesventas;
    private gestionVentas geshistorial;
    private gestionVentas gescreditos;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    //cremos los vectores necesarios
    public Vector<Producto> vectorproductos = new Vector<>();
    public Vector<Venta> vectorventas = new Vector<>();
    
    //crearemos los modelos de las tablas de ventas 
    DefaultTableModel modeloventas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };

    //indice que nos ayudara a controlar la tabla
    int indiceGeneral;
    
    /**
     * Creates new form FrameVentaDiaria
     */
    
    //creamos un vector que contenga los indices reales de los clientes
    public Vector<Integer> vectorIndices = new Vector<>();
    
    public FrameVentaDiaria(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        // Show or hide the button based on user role
if (role.equalsIgnoreCase("ADMINISTRADOR")) {
    txtHistorialVentas.setVisible(true); // Show the button for ADMINISTRADOR
} else if (role.equalsIgnoreCase("SECRETARIA")) {
    txtHistorialVentas.setVisible(true); // Hide the button for SECRETARIA
} else {
    // Optionally, you can set the default visibility for other roles
    txtHistorialVentas.setVisible(false); // Hide for all other roles
}
        //inidiciamos el indice general en -1
        indiceGeneral = -1;
        
        //inciamos las clases
        gesproductos = new gestionProductos();
        gesclientes = new GestionClientes();
        gesventas = new gestionVentas();
        geshistorial = new gestionVentas("excels/historial.xlsx");
        gescreditos = new  gestionVentas("excels/creditos.xlsx");
        
        //llenamos las clases necesarias del excel
        gesproductos.setCargarInvetarioExcel();
        gesclientes.cargarExcelCliente();
        gesventas.cargarExcelVentas();
        gescreditos.cargarExcelCreditos();
        
        //llenamos los vectores necesarios
        vectorproductos = gesproductos.getProductos();
        vectorventas = gesventas.getVentas();
        
        //unicamnete de pruebas
        //comboClienteA.addItem("Clientes Varios");
        //comboClienteB.addItem("Clientes Varios");
        
        //llenamos los combo box de producots y clientes
        actualizarComboxProductos();
        actualizarComboxClientes();
        
        //tabla ventas creamos la tabla
        String ids [] = {"no.", "producto", "cantidad", "precio", "ganancia", "tipo"};
        modeloventas.setColumnIdentifiers(ids);
        tablaVentasA.setModel(modeloventas);
        
        //actualizamo la tabla
        actualizarTablaVentas();
        
        //calculamos la ganancias total
        ActualizarGananciaTotal();
        configurarCamposPrecioCosto();
        //iniciamos el bucle infinito
       iniciarBucleEnHilo(); 
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
    
// Método para configurar el placeholder en campos de texto de precio y costo
private void setupTextFieldPrecioCosto(JTextField textField, String placeholder) {
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
   textField.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        // Solo restablece el placeholder si el campo está vacío
        if (textField.getText().isEmpty()) {
            textField.setForeground(Color.GRAY);
            textField.setText(placeholder);
        }
    }
});
    
}
// Método para configurar todos los campos de precio y costo con placeholders
private void configurarCamposPrecioCosto() {
    setupTextFieldPrecioCosto(textoPrecioCostoAgregar, "Ingrese precio");
    setupTextFieldPrecioCosto(textoPrecioVentaAgregar, "Ingrese precio");
    setupTextFieldPrecioCosto(txtPrecioAgregar, "Ingrese precio");
    setupTextFieldPrecioCosto(txtPrecioCostoModificar, "Ingrese precio");
    setupTextFieldPrecioCosto(txtPrecioFleteModificar, "Ingrese precio");
    setupTextFieldPrecioCosto(txtPrecioVentarModificar, "Ingrese precio");
}

// Método para limpiar y restablecer los placeholders de los campos de precio y costo
public void limpiarCamposPrecioCosto() {
    textoPrecioCostoAgregar.setText("Ingrese precio");
    textoPrecioCostoAgregar.setForeground(Color.GRAY);

    textoPrecioVentaAgregar.setText("Ingrese precio");
    textoPrecioVentaAgregar.setForeground(Color.GRAY);

    txtPrecioAgregar.setText("Ingrese precio");
    txtPrecioAgregar.setForeground(Color.GRAY);

    txtPrecioCostoModificar.setText("Ingrese precio");
    txtPrecioCostoModificar.setForeground(Color.GRAY);

    txtPrecioFleteModificar.setText("Ingrese precio");
    txtPrecioFleteModificar.setForeground(Color.GRAY);

    txtPrecioVentarModificar.setText("Ingrese precio");
    txtPrecioVentarModificar.setForeground(Color.GRAY);
}


    /**
     * Añade opciones al ComboBox para el rol de ADMINISTRADOR.
     */
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
    
    //funcion para actulizar el combo de ventas
    public void actualizarComboxProductos(){
        //removemos todos los elementos del combo box
        comboProductosA.removeAllItems();
        
        //recorremos el vector de procdutos para llenar el combo box
        for (int i = 0; i < vectorproductos.size(); i++) {
            comboProductosA.addItem(vectorproductos.get(i).getNombre());
        }
    }
    
    //funcion para actualizar el combo de clientes
    public void actualizarComboxClientes(){
        //primero vaciamos los combo box
        comboClienteA.removeAllItems();
        comboClienteB.removeAllItems();
        
        //vaciamos el vector de clientes
        vectorIndices.clear();
        
        //contador para el combo box
        int numerocontado = 1;
        
        //creamos una variable de si el cliente esta activo o no
        boolean activoCliente = true;
        //creamos una variable que tendra la cantidad del vector de creditos
        int logitudCreditos = 0;
        
        //ahora creamos un for que recorrera todos los clientes para añadirlos a los combo box
        for (int i = 0; i < gesclientes.getClientes().size(); i++) {
                    
            activoCliente = true;
            
            //leemos la logitus del vector credito
            logitudCreditos = gesclientes.getClientes().get(i).getIndiceCredito().size();
            
            if (logitudCreditos > 0) {
                if (gesclientes.getClientes().get(i).getIndiceCredito().get(logitudCreditos-1) == -100) {
                    activoCliente = false;
                }
            }else{
                activoCliente = true;
            }
            
            if (activoCliente == true) {
                comboClienteA.addItem(numerocontado +". " +gesclientes.getClientes().get(i).getNombre());
                comboClienteB.addItem(numerocontado +". " +gesclientes.getClientes().get(i).getNombre());
                
                vectorIndices.add(i);
                
                numerocontado++;
            }

        }
    }
    
    //funcion para actulizar tabla de ventas
    public void actualizarTablaVentas(){
       //dejamos la tabla en cero
        modeloventas.setRowCount(0);
        
        //cremos el indice de la venta y si es una venta o no
        int suma = 1;
        String siesventa = "venta";
        
        //recoremos el vector de ventas para agregar los elementos a la venta
        for (int i = 0; i < vectorventas.size(); i++) {
            
            if (vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == true) {
                siesventa = "credito";
            }else if(vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == false){
                siesventa = "credito / pagado";
            }else if(vectorventas.get(i).getCredito() == false){
                siesventa = "venta";
            }
            
            modeloventas.addRow(new Object[]{suma, vectorproductos.get(vectorventas.get(i).getIndiceProducto()).getNombre(), vectorventas.get(i).getIndiceCantidad(), vectorventas.get(i).getPrecio(), vectorventas.get(i).getGanancia(), siesventa});
            suma += 1;
        }
    }
    
    //creamos una funcion para calcular la ganancia del dia
    public void ActualizarGananciaTotal(){
        labelGanaciaGeneral.setText("Q " + gesventas.calcularGananciaGeneral());
        labelVendido.setText("Q " + gesventas.calcularVendidoGeneral());
    };
            
    
    
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
        tablaVentasA = new javax.swing.JTable();
        labelGanaciaGeneral = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        labelVendido = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        radioCredito = new javax.swing.JRadioButton();
        comboProductosA = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinCantidadA = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        textoPrecioVentaAgregar = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPrecioAgregar = new javax.swing.JTextField();
        labelpreciocosto = new javax.swing.JLabel();
        textoPrecioCostoAgregar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboClienteA = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        comboClienteB = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPrecioVentarModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        spinCantidadB = new javax.swing.JSpinner();
        txtPrecioFleteModificar = new javax.swing.JTextField();
        txtPrecioCostoModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        labelGanaciaVenta = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        labelProductosB = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        jTextField19 = new javax.swing.JTextField();
        txtHistorialVentas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablaVentasA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaVentasA.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaVentasA);

        labelGanaciaGeneral.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelGanaciaGeneral.setText("Q 00.00");

        jLabel20.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel20.setText("Ganancia del día");

        jLabel22.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel22.setText("Vendido del día");

        labelVendido.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelVendido.setText("Q 00.00");

        jPanel4.setBackground(new java.awt.Color(128, 179, 220));

        jPanel5.setBackground(new java.awt.Color(85, 111, 169));
        jPanel5.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(125, 32));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("AGREGAR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        radioCredito.setText("Es un credito");
        radioCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCreditoActionPerformed(evt);
            }
        });

        comboProductosA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("Cantidad");

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("Producto");

        spinCantidadA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        spinCantidadA.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinCantidadA.setBorder(null);

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setText("Precio de venta");

        textoPrecioVentaAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(85, 111, 169));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jPanel11.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("PRESIONAR PARA CARGAR NUEVO PRODUCTO");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel1.setText("Agregar Venta/Credito");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setText("Flete");

        txtPrecioAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        labelpreciocosto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelpreciocosto.setText("Precio Costo");

        textoPrecioCostoAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setText("Cliente");

        comboClienteA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboProductosA, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(radioCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinCantidadA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoPrecioVentaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelpreciocosto, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoPrecioCostoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboClienteA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39))))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboProductosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboClienteA, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(labelpreciocosto))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinCantidadA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPrecioVentaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPrecioCostoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(radioCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel7.setBackground(new java.awt.Color(128, 179, 220));
        jPanel7.setPreferredSize(new java.awt.Dimension(623, 273));

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setText("Producto");

        comboClienteB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comboClienteB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClienteBActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(85, 111, 169));

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel7.setText("Modificiar Venta");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad");

        txtPrecioVentarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("Precio de venta");

        spinCantidadB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        spinCantidadB.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinCantidadB.setBorder(null);

        txtPrecioFleteModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtPrecioCostoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("Flete");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("Precio Costo");

        labelGanaciaVenta.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelGanaciaVenta.setText("Q 00.00");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("Ganancia de la venta");

        jPanel9.setBackground(new java.awt.Color(85, 111, 169));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("MODIFICAR");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel17)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        labelProductosB.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelProductosB.setText("N/A");

        jLabel21.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel21.setText("Cliente");

        jPanel12.setBackground(new java.awt.Color(85, 111, 169));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ELIMINAR");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spinCantidadB)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioVentarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioFleteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioCostoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(labelProductosB, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelGanaciaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboClienteB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(44, 44, 44))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClienteB, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelProductosB)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelGanaciaVenta)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinCantidadB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(28, 28, 28))
                    .addComponent(txtPrecioVentarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioFleteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioCostoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        jPanel10.setBackground(new java.awt.Color(102, 102, 255));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("NUEVO DIA DE VENTA");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(30, 30, 30))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(18, 59, 87));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(727, Short.MAX_VALUE))
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" GESTION VENTAS");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        txtHistorialVentas.setBackground(new java.awt.Color(0, 153, 153));
        txtHistorialVentas.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        txtHistorialVentas.setForeground(new java.awt.Color(255, 255, 255));
        txtHistorialVentas.setText("HISTORIAL DE VENTAS");
        txtHistorialVentas.setBorder(null);
        txtHistorialVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHistorialVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelGanaciaGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtHistorialVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel22)
                        .addGap(5, 5, 5)
                        .addComponent(labelVendido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelGanaciaGeneral))
                            .addComponent(txtHistorialVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 752, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioCreditoActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        
        //funcion para agregar 
        try {
           
            //leemos los indices del inventario y del cliente
            int newindiceproducto = comboProductosA.getSelectedIndex();
            int newindicecliente =  -1;
            
            if (comboClienteA.getSelectedIndex() > -1) {
                newindicecliente = vectorIndices.get(comboClienteA.getSelectedIndex());
            }
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
                //leemos la cantidad del spin
                int newcantidad = (int) spinCantidadA.getValue();
                
                //leemos los precios
                float newprecio = Float.parseFloat(textoPrecioVentaAgregar.getText());
                float newflete = Float.parseFloat(txtPrecioAgregar.getText());
                float newcosto = Float.parseFloat(textoPrecioCostoAgregar.getText());
                
                //vemos si es un credito o no
                boolean newcredito = radioCredito.isSelected();
                
                //preguntamos is hay suficientes existencias para completar el pedido
                if (vectorproductos.get(newindiceproducto).getExistencias() >= newcantidad) {
                   
                    //crearemos un if para indidicar que la ganancia no puede ser negativa
                    if (newprecio - (newflete + newcosto) >= 0 && newprecio > 0 && newflete > 0 && newcosto > 0) {
                      //cremos la nueva venta y la agregamos al vector
                Venta newventa = new Venta(newindiceproducto, newcantidad, newindicecliente, newprecio, newcosto, newflete, newcredito, true, 0);
                gesventas.addVentaVector(newventa);
                
                //preguntamos si es una credito para agregarlo al listado de creditos
                        if (newcredito == true) {
                            //guardamos en el vector y el excel
                            gescreditos.addCreditoVector(newventa);
                            gescreditos.calcularGananciaCredito(gescreditos.getCreditos().size()-1);
                            gescreditos.guardarExcelCreditos();
                            
                            //cuardamos el credito en el cliente correspondiente
                            gesclientes.addCredito(newindicecliente, gescreditos.getCreditos().size()-1);
                            gesclientes.guardarExcelCliente();
                        }
                
                //calculamos la ganacia
                gesventas.calcularGanancia(vectorventas.size()-1);
                
                //guardamo la venta en gesventas;
                gesventas.guardarExcelVentas();
                
                //actualizamos la tabla de ventas
                actualizarTablaVentas(); 
                
                limpiarCamposPrecioCosto();
                //cambiamos las existencias en el inventario
                gesproductos.setCantidad(newindiceproducto, newcantidad, "-");
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();
                
                //inciamos todos los valores a nulos
                textoPrecioVentaAgregar.setText("");
                txtPrecioAgregar.setText("");
                textoPrecioCostoAgregar.setText("");
                
                comboProductosA.setSelectedIndex(0);
                comboClienteA.setSelectedIndex(0);
                
                radioCredito.setSelected(false);
                
                spinCantidadA.setValue(1);
                
                //llamamos a la funcion para calcular la ganancia general
                ActualizarGananciaTotal();
                
                //seleccionamos el indice actual de la tabla en la tabla
                        if (indiceGeneral > -1) {
                            tablaVentasA.setRowSelectionInterval(indiceGeneral, indiceGeneral);
                        }
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "venta realizada completamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                 
                    }else{
                        //monstra mensajje
                JOptionPane.showMessageDialog(null, "el costo y flete superan al precio, o existe algun precio invalido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                
                    }
               
                
                }else{
                    //monstra mensajje
                JOptionPane.showMessageDialog(null, "No hay suficientes existencias en inventario", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        
        //funcion para cargar los datos de la venta
        try {
           
            //leemos los indices del inventario y del cliente
            int newindiceproducto = comboProductosA.getSelectedIndex();
            int newindicecliente =  -1;
            
            if (comboClienteA.getSelectedIndex() > -1) {
                newindicecliente = vectorIndices.get(comboClienteA.getSelectedIndex());
            }
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
               txtPrecioAgregar.setText("" + vectorproductos.get(newindiceproducto).getPrecioFlete());
               textoPrecioCostoAgregar.setText("" + vectorproductos.get(newindiceproducto).getPrecioCosto());
                               limpiarCamposPrecioCosto();

               //esta variable nos ayudara a ver si el producto fue encontrado en la lista del cliente
               boolean tieneproducto = false;
               
               //cremos un for que recorrera los productos del cliente, y si encuentra similitud, entonces podra su precio especial
                for (int i = 0; i < gesclientes.getClientes().get(newindicecliente).getIndiceProducto().size(); i++) {
                    if (gesclientes.getClientes().get(newindicecliente).getIndiceProducto().get(i) == newindiceproducto) {
                        //colocamos su precio
                        textoPrecioVentaAgregar.setText("" + gesclientes.getClientes().get(newindicecliente).getIndiceCantidad().get(i));
                        //si se encontro lo ponemos true
                        tieneproducto = true;
                        //salimos
                        break;
                    }
                }
                
                if (tieneproducto == false) {
                    textoPrecioVentaAgregar.setText("");
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        
        //funcion para modificiar una venta
        try {
           
            if (indiceGeneral > -1) {
                 //leemos los indices del inventario y del cliente
            int newindiceproducto = vectorventas.get(indiceGeneral).getIndiceProducto();
            int newindicecliente =  -1;
            
            if (comboClienteA.getSelectedIndex() > -1) {
                newindicecliente = vectorIndices.get(comboClienteA.getSelectedIndex());
            }
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
                //leemos la cantidad del spin
                int newcantidad = (int) spinCantidadB.getValue();
                
                //guardamos la cantidad antigua porque nos sera util, al sumar la cantidad y restarle la nueva
                int cantidadAntigua = vectorventas.get(indiceGeneral).getIndiceCantidad();
                
                //leemos los precios
                float newprecio = Float.parseFloat(txtPrecioVentarModificar.getText());
                float newflete = Float.parseFloat(txtPrecioFleteModificar.getText());
                float newcosto = Float.parseFloat(txtPrecioCostoModificar.getText());
                
                //vemos si es un credito o no
                boolean newcredito = false;
                                limpiarCamposPrecioCosto();

                //preguntamos is hay suficientes existencias para completar el pedido
                if (vectorproductos.get(newindiceproducto).getExistencias() + cantidadAntigua >= newcantidad ) {
                   
                    //crearemos un if para indidicar que la ganancia no puede ser negativa
                    if (newprecio - (newflete + newcosto) >= 0 && newprecio > 0 && newflete > 0 && newcosto > 0) {
                      //cremos la nueva venta y  editamos el vector
                Venta newventa = new Venta(newindiceproducto, newcantidad, newindicecliente, newprecio, newcosto, newflete, newcredito, true, 0);
                gesventas.modificarVenta(indiceGeneral, newventa);

                //calculamos la ganacia
                gesventas.calcularGanancia(indiceGeneral);
                
                //guardamo la venta en gesventas;
                gesventas.guardarExcelVentas();
                
                //actualizamos la tabla de ventas
                actualizarTablaVentas(); 
                
                //cambiamos las existencias en el inventario
                gesproductos.setCantidad(newindiceproducto, cantidadAntigua, "+");
                gesproductos.setCantidad(newindiceproducto, newcantidad, "-");
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();

                
                //llamamos a la funcion para calcular la ganancia general
                ActualizarGananciaTotal();
                
                //seleccionamos el indice actual de la tabla en la tabla
                        if (indiceGeneral > -1) {
                            tablaVentasA.setRowSelectionInterval(indiceGeneral, indiceGeneral);
                        }
                
                labelGanaciaVenta.setText("" + vectorventas.get(indiceGeneral).getGanancia());

                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "venta modificada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                 
                    }else{
                        //monstra mensajje
                JOptionPane.showMessageDialog(null, "el costo y flete superan al precio, o existe algun precio invalido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                
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
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
        
        //funcion para finalizar el dia
        
        // Mostrar popup de advertencia
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

                // Si el usuario selecciona "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    
                limpiarCamposPrecioCosto();

        // Obtener la fecha actual
        Date fechaActual = new Date();
        // Crear el formateador para la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");
        // Convertir la fecha a String
        String fechaFormateada = formatoFecha.format(fechaActual);
        
        
        String siesventa = "venta";    
        
        //cremos un vector des string que nos ayudara a guardar el historial
        Vector<String> his = new Vector<>();
        
        //creamos un for que obtendra toda la informacion del vector 
        
                    for (int i = 0; i < vectorventas.size(); i++) {

                String cliente = gesclientes.getClientes().get(vectorventas.get(i).getIndiceCliente()).getNombre();
                
                    if (vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == true) {
                    siesventa = "credito";
                }else if(vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == false){
                    siesventa = "credito / pagado";
                }else if(vectorventas.get(i).getCredito() == false){
                    siesventa = "venta";
                }

                String producto = vectorproductos.get(vectorventas.get(i).getIndiceProducto()).getNombre();
                String cantidad = vectorventas.get(i).getIndiceCantidad() + " productos";
                String precio = "Q. " + vectorventas.get(i).getPrecio();
                double cantidadtotal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecio();
                String total = "Q. " + cantidadtotal;
                double fletetotal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecioFlete();
                String flete = "Q. " + fletetotal;
                double costototal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecioCosto();
                String costo = "Q. " + costototal;
                String ganancia = "Q. " + vectorventas.get(i).getGanancia();
                
                his.add(fechaFormateada);
                his.add(cliente);
                his.add(siesventa);
                his.add(producto);
                his.add(cantidad);
                his.add(precio);
                his.add(total);
                his.add(flete);
                his.add(costo);
                his.add(ganancia);
                
                
                    }
                    
                his.add(fechaFormateada);
                his.add("Resumen");
                his.add("total");
                his.add("del");
                his.add("dia");
                his.add("-");
                his.add("Q. " + gesventas.calcularVendidoGeneral());
                his.add("Q. " + gesventas.calcularFleteGeneral());
                his.add("Q. " + gesventas.calcularCostoGeneral());
                his.add("Q. " + gesventas.calcularGananciaGeneral());
                    
                    
                    //guardamos los datos en el historial
                    geshistorial.guardarExcelHistoria(his);
                    
        //vaciamos el vector de ventas            
        gesventas.getVentas().clear();
        gesventas.guardarExcelVentas();

        
        //dejamos la tabla en cero
        modeloventas.setRowCount(0);
        
        actualizarTablaVentas();
        ActualizarGananciaTotal();
                }
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void comboClienteBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClienteBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboClienteBActionPerformed

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        // TODO add your handling code here:
        
        //funcion para eliminar una venta
         // Mostrar popup de advertencia        
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí"
        if (respuesta == JOptionPane.YES_OPTION) {
           if (indiceGeneral > -1) {
                   //rellenamos las informacion correspondiente
                comboClienteB.setSelectedIndex(0);               
                labelProductosB.setText("N/A");
                
                spinCantidadB.setValue(0);
                
                txtPrecioVentarModificar.setText("");
                txtPrecioFleteModificar.setText("");
                txtPrecioCostoModificar.setText("");
                
                labelGanaciaVenta.setText("Q 0.00");
                
                //guardamos la cantidad antigua porque nos sera util, al sumar la cantidad 
                int cantidadAntigua = vectorventas.get(indiceGeneral).getIndiceCantidad();
                
                //leemos los indices del inventario 
                int newindiceproducto = vectorventas.get(indiceGeneral).getIndiceProducto();
                
                //resumamos las ventas
                gesproductos.setCantidad(newindiceproducto, cantidadAntigua, "+");
                
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();
                        
                //eliminamos la venta del vector
                gesventas.getVentas().remove(indiceGeneral);
                
                //actualizamos las tablas correspondientes               
                actualizarTablaVentas();
                ActualizarGananciaTotal();
                                limpiarCamposPrecioCosto();

                gesventas.guardarExcelVentas();
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Venta eliminada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion una venta de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }       
        }
    }//GEN-LAST:event_jPanel12MouseClicked

    private void txtHistorialVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHistorialVentasActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible
                limpiarCamposPrecioCosto();

        FrameHistorialVenta abrir = new FrameHistorialVenta(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_txtHistorialVentasActionPerformed

    //creamos el bucle infinito
    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice actual que ha seleccionado la tabla 
            int indiceSeleccionado = tablaVentasA.getSelectedRow();
            
            //preguntamos si el indice seleccionado el diferente al actual
             if (indiceSeleccionado != indiceGeneral && indiceSeleccionado > -1) {
                
                 //igualamos los indices
                indiceGeneral = indiceSeleccionado;
                 
                //rellenamos las informacion correspondiente
                
                //creamos una variable que cree representar al cliente de dicha venta
                int indiceClienteVenta = 0;
                
                //hacemos un for que recorra el vector de indices para ver si los indices coindice
                
                 for (int i = 0; i < vectorIndices.size(); i++) {
                     //si los indices coincides los igualamos
                     if (vectorventas.get(indiceGeneral).getIndiceCliente() == vectorIndices.get(i)) {
                         indiceClienteVenta = i;
                     }
                 }
                comboClienteB.setSelectedIndex(indiceClienteVenta);               
                labelProductosB.setText(vectorproductos.get(vectorventas.get(indiceGeneral).getIndiceProducto()).getNombre());
                
                spinCantidadB.setValue(vectorventas.get(indiceGeneral).getIndiceCantidad());
                
                txtPrecioVentarModificar.setText("" + vectorventas.get(indiceGeneral).getPrecio());
                txtPrecioFleteModificar.setText("" + vectorventas.get(indiceGeneral).getPrecioFlete());
                txtPrecioCostoModificar.setText("" + vectorventas.get(indiceGeneral).getPrecioCosto());
                                limpiarCamposPrecioCosto();

                labelGanaciaVenta.setText("" + vectorventas.get(indiceGeneral).getGanancia());
                
                //reguntamos si es un credito para no mostrar el boton de modificar
                 if (vectorventas.get(indiceGeneral).getCredito() == true || vectorventas.get(indiceGeneral).getCreditoActivo() == false) {
                     jPanel9.setVisible(false);
                     jPanel12.setVisible(false);
                 }else{
                     jPanel9.setVisible(true);
                     jPanel12.setVisible(true);
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
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
                            
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FrameVentaDiaria(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboClienteA;
    private javax.swing.JComboBox<String> comboClienteB;
    private javax.swing.JComboBox<String> comboProductosA;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JLabel labelGanaciaGeneral;
    private javax.swing.JLabel labelGanaciaVenta;
    private javax.swing.JLabel labelProductosB;
    private javax.swing.JLabel labelVendido;
    private javax.swing.JLabel labelpreciocosto;
    private javax.swing.JRadioButton radioCredito;
    private javax.swing.JSpinner spinCantidadA;
    private javax.swing.JSpinner spinCantidadB;
    private javax.swing.JTable tablaVentasA;
    private javax.swing.JTextField textoPrecioCostoAgregar;
    private javax.swing.JTextField textoPrecioVentaAgregar;
    private javax.swing.JButton txtHistorialVentas;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtPrecioAgregar;
    private javax.swing.JTextField txtPrecioCostoModificar;
    private javax.swing.JTextField txtPrecioFleteModificar;
    private javax.swing.JTextField txtPrecioVentarModificar;
    // End of variables declaration//GEN-END:variables
}
