package ControlInventario;

import GestionDePilotos.INICIOGESTIONPILOTOS;
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
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import Login.GESTIONLOGIN;
import Login.LOGINPINEED;
import Login.Login;
import javax.swing.JButton;
import java.util.Vector;
import java.util.Set;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author USUARIO
 */
public class FrameInventario extends javax.swing.JFrame {

    
    //creamos el objeto gestion de productos y un vector que guardara sus elementos
    public gestionProductos Tproductos;
    public Vector<Producto> productosdeinvetario = new Vector<>();
    
    //colores que nos ayudaran a modificar los paneles que funcionan como botones
    private Color colorazulclaro = new Color(85, 150, 202);
    private Color colorazuloscurlo = new Color(60, 100, 132);
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    //creamos el modelo de la tabla
    DefaultTableModel modeloproductos = new DefaultTableModel();
    
    //crearemos un indice actual que nos ayudara a modificar los elementos
    private int indiceActual;
    
    /**
     * Creates new form FrameInventario
     */
    public FrameInventario(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        setResizable(false); // Desactivar el cambio de tamaño
        //iniciamos el indicie actual en 0, el primer elemnto del combo box
        indiceActual = 0;
        
        //inciamos el gestion productos
        Tproductos = new gestionProductos();
        Tproductos.setCargarInvetarioExcel();
       
        
        //colocamos invisibles todos los paneles
        panelp1.setVisible(true);
        panelp2.setVisible(false);
        panelp3.setVisible(false);
        panelp4.setVisible(true);
        
        botonParaActualizar.setVisible(false);
        
        //tabla productos creamos la tabla
        String ids [] = {"Producto", "cantidad", "Proveedor", "precio costo", "Flete"};
        modeloproductos.setColumnIdentifiers(ids);
        tablaInventario.setModel(modeloproductos);
        
        
        //obetenemos el vector que tiene los productos
        if (Tproductos.getProductos() != null) {
            productosdeinvetario = Tproductos.getProductos();
        }
        
        //llenamos los combo box con los quintales
        
        //con este for llenaremos la tabla con los elemetos del vector
        for (Producto prod : productosdeinvetario) {
            txtProductoModificar.addItem(prod.getNombre());
            comoboProductoC2.addItem(prod.getNombre());

        }
        
        //cargamos el invetario en la tabla
        cargarInvetrioTabla();
        
        
        //llamamos a un bucle infinito
        iniciarBucleEnHilo();
        configurarCamposProducto();
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
    
// Método para limpiar y restablecer los placeholders de los campos de producto
public void limpiarCamposProducto() {
    txtNombreDelProductoAgregar.setText("Ingrese el nombre del producto");
    txtNombreDelProductoAgregar.setForeground(Color.GRAY);

    txtNombreDelProveedorAgregar.setText("Ingrese el nombre del proveedor");
    txtNombreDelProveedorAgregar.setForeground(Color.GRAY);

    txtPrecioCostoAgregar.setText("Ingrese precio");
    txtPrecioCostoAgregar.setForeground(Color.GRAY);

    txtPrecioCostoModificar.setText("Ingrese precio");
    txtPrecioCostoModificar.setForeground(Color.GRAY);

    txtNombreDelProveedorModificar.setText("Ingrese el nombre del producto");
    txtNombreDelProveedorModificar.setForeground(Color.GRAY);

    txtPrecioFleteAgregar.setText("Ingrese precio");
    txtPrecioFleteAgregar.setForeground(Color.GRAY);

    txtPrecioFleteModificar.setText("Ingrese precio");
    txtPrecioFleteModificar.setForeground(Color.GRAY);

    txtNombreProductoModificar.setText("Ingrese el nombre del producto");
    txtNombreProductoModificar.setForeground(Color.GRAY);
}

// Método para configurar todos los campos de producto con placeholders
private void configurarCamposProducto() {
    setupTextField(txtNombreDelProductoAgregar, "Ingrese el nombre del producto");
    setupTextField(txtNombreDelProveedorAgregar, "Ingrese el nombre del proveedor");
    setupTextField(txtPrecioCostoAgregar, "Ingrese precio");
    setupTextField(txtPrecioCostoModificar, "Ingrese precio");
    setupTextField(txtNombreDelProveedorModificar, "Ingrese el nombre del producto");
    setupTextField(txtPrecioFleteAgregar, "Ingrese precio");
    setupTextField(txtPrecioFleteModificar, "Ingrese precio");
    setupTextField(txtNombreProductoModificar, "Ingrese el nombre del producto");
}



// Método para configurar el placeholder en campos de texto
private void setupTextField(JTextField textField, String placeholder) {
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


private void setupComboBox() {
    txtMenu3.removeAllItems();
    txtMenu3.addItem("Seleccione una opción");

    if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
        addAdminOptions();
    } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
        addSecretariaOptions();
    } else if (userRole.equalsIgnoreCase("PILOTO")) {
        addPilotOptions();
    }

    txtMenu3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) txtMenu3.getSelectedItem();
            redirectToFrame(selectedOption);
        }
    });
}

private void addAdminOptions() {
    txtMenu3.addItem("Gestión de Usuarios");
    txtMenu3.addItem("Gestión de Pilotos");
    txtMenu3.addItem("Gestión de Clientes");
    txtMenu3.addItem("Gestión de Ventas");
    txtMenu3.addItem("Gestión de Pedidos");
    txtMenu3.addItem("Inventario de Quintales");
    txtMenu3.addItem("Planilla de Trabajadores");
    txtMenu3.addItem("Gestión de Camiones");
    txtMenu3.addItem("Calendario");
    txtMenu3.addItem("Cerrar Sesión");
}

private void addSecretariaOptions() {
    txtMenu3.addItem("Gestión de Clientes");
    txtMenu3.addItem("Gestión de Ventas");
    txtMenu3.addItem("Gestión de Pedidos");
    txtMenu3.addItem("Inventario de Quintales");
    txtMenu3.addItem("Planilla de Trabajadores");
    txtMenu3.addItem("Calendario");
    txtMenu3.addItem("Cerrar Sesión");
}

private void addPilotOptions() {
    txtMenu3.addItem("Calendario");
    txtMenu3.addItem("Cerrar Sesión");
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

    //esta funcion actualizara los elemetnos existentes en la tabla
    private void cargarInvetrioTabla(){
        
        //primero vaciaremos la tabla totalmente
        modeloproductos.setRowCount(0);
        
        //con este for llenaremos la tabla con los elemetos del vector
        for (Producto prod : productosdeinvetario) {
            
            modeloproductos.addRow(new Object[]{prod.getNombre(), prod.getExistencias(), prod.getProveedor(), prod.getPrecioCosto(), prod.getPrecioFlete()});

        }
        
    };
    
 
    //funcion que nos ayudara a actulizar los dos combo box
    private void actualizarCombobox(){
        
        txtProductoModificar.removeAllItems();
        comoboProductoC2.removeAllItems();
        
        txtProductoModificar.addItem("None");
        comoboProductoC2.addItem("None");
        
        //con este for llenaremos nuevamente los combo box
        for (Producto prod : productosdeinvetario) {
            txtProductoModificar.addItem(prod.getNombre());
            comoboProductoC2.addItem(prod.getNombre());
        }
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
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtMenu3 = new javax.swing.JComboBox<>();
        PanelB4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        panelp4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        PanelB2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        PanelB3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        panelB1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelp1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelAgregarProducto = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtNombreDelProductoAgregar = new javax.swing.JTextField();
        txtNombreDelProveedorAgregar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrecioCostoAgregar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioFleteAgregar = new javax.swing.JTextField();
        spinnerCantidad = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        panelp2 = new javax.swing.JPanel();
        txtNombreProductoModificar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombreDelProveedorModificar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPrecioCostoModificar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioFleteModificar = new javax.swing.JTextField();
        panelModificarProducto = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        spinnerModificadoCantidad = new javax.swing.JSpinner();
        txtProductoModificar = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        botonParaActualizar = new javax.swing.JPanel();
        panelp3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        comoboProductoC2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        comboOperacion = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        spineerCantidadOperacion = new javax.swing.JSpinner();
        panelExistenciasProductos = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 102));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1375, 752));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1353, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" INVENTARIO");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(32, 67, 99));
        jPanel7.setPreferredSize(new java.awt.Dimension(194, 34));

        txtMenu3.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenu3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelB4.setBackground(new java.awt.Color(204, 153, 255));
        PanelB4.setForeground(new java.awt.Color(255, 255, 255));
        PanelB4.setFont(new java.awt.Font("Nirmala UI", 0, 18)); // NOI18N
        PanelB4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB4MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("INVENTARIO");

        javax.swing.GroupLayout PanelB4Layout = new javax.swing.GroupLayout(PanelB4);
        PanelB4.setLayout(PanelB4Layout);
        PanelB4Layout.setHorizontalGroup(
            PanelB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelB4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        PanelB4Layout.setVerticalGroup(
            PanelB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelB4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        panelp4.setBackground(new java.awt.Color(128, 179, 220));

        tablaInventario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaInventario);

        javax.swing.GroupLayout panelp4Layout = new javax.swing.GroupLayout(panelp4);
        panelp4.setLayout(panelp4Layout);
        panelp4Layout.setHorizontalGroup(
            panelp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelp4Layout.setVerticalGroup(
            panelp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        PanelB2.setBackground(new java.awt.Color(102, 102, 255));
        PanelB2.setForeground(new java.awt.Color(255, 255, 255));
        PanelB2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB2MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("MODIFICAR PRODUCTO");

        javax.swing.GroupLayout PanelB2Layout = new javax.swing.GroupLayout(PanelB2);
        PanelB2.setLayout(PanelB2Layout);
        PanelB2Layout.setHorizontalGroup(
            PanelB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelB2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelB2Layout.setVerticalGroup(
            PanelB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelB2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        PanelB3.setBackground(new java.awt.Color(102, 153, 255));
        PanelB3.setForeground(new java.awt.Color(255, 255, 255));
        PanelB3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB3MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("EXISTENCIAS");

        javax.swing.GroupLayout PanelB3Layout = new javax.swing.GroupLayout(PanelB3);
        PanelB3.setLayout(PanelB3Layout);
        PanelB3Layout.setHorizontalGroup(
            PanelB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelB3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(14, 14, 14))
        );
        PanelB3Layout.setVerticalGroup(
            PanelB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelB3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        panelB1.setBackground(new java.awt.Color(60, 100, 132));
        panelB1.setForeground(new java.awt.Color(255, 255, 255));
        panelB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelB1MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("AGREGAR PRODUCTO");

        javax.swing.GroupLayout panelB1Layout = new javax.swing.GroupLayout(panelB1);
        panelB1.setLayout(panelB1Layout);
        panelB1Layout.setHorizontalGroup(
            panelB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelB1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelB1Layout.setVerticalGroup(
            panelB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelB1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        panelp1.setBackground(new java.awt.Color(204, 204, 255));
        panelp1.setPreferredSize(new java.awt.Dimension(487, 181));

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setText("Nombre Del provedor");

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 51));
        jLabel2.setText("Nombre Del Producto");

        panelAgregarProducto.setBackground(new java.awt.Color(102, 153, 255));
        panelAgregarProducto.setPreferredSize(new java.awt.Dimension(75, 28));
        panelAgregarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAgregarProductoMouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("AGREGAR PRODUCTO");

        javax.swing.GroupLayout panelAgregarProductoLayout = new javax.swing.GroupLayout(panelAgregarProducto);
        panelAgregarProducto.setLayout(panelAgregarProductoLayout);
        panelAgregarProductoLayout.setHorizontalGroup(
            panelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAgregarProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(39, 39, 39))
        );
        panelAgregarProductoLayout.setVerticalGroup(
            panelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        txtNombreDelProductoAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNombreDelProductoAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreDelProductoAgregarActionPerformed(evt);
            }
        });

        txtNombreDelProveedorAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 51));
        jLabel4.setText("Precio Costo");

        txtPrecioCostoAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 51));
        jLabel5.setText("Precio Flete");

        txtPrecioFleteAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        spinnerCantidad.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        spinnerCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 51));
        jLabel3.setText("Cantidad");

        javax.swing.GroupLayout panelp1Layout = new javax.swing.GroupLayout(panelp1);
        panelp1.setLayout(panelp1Layout);
        panelp1Layout.setHorizontalGroup(
            panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelp1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreDelProductoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelp1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreDelProveedorAgregar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioCostoAgregar))
                    .addGroup(panelp1Layout.createSequentialGroup()
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(9, 9, 9)
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioFleteAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        panelp1Layout.setVerticalGroup(
            panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreDelProductoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreDelProveedorAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtPrecioCostoAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioFleteAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(panelAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        panelp2.setBackground(new java.awt.Color(204, 204, 255));
        panelp2.setPreferredSize(new java.awt.Dimension(487, 181));

        txtNombreProductoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 51));
        jLabel6.setText("Nombre Del Producto");

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 51));
        jLabel7.setText("Nombre Del provedor");

        txtNombreDelProveedorModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNombreDelProveedorModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreDelProveedorModificarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 51));
        jLabel8.setText("Precio Costo");

        txtPrecioCostoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 51));
        jLabel9.setText("Precio Flete");

        txtPrecioFleteModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        panelModificarProducto.setBackground(new java.awt.Color(102, 153, 255));
        panelModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelModificarProductoMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("MODIFICAR PRODUCTO");

        javax.swing.GroupLayout panelModificarProductoLayout = new javax.swing.GroupLayout(panelModificarProducto);
        panelModificarProducto.setLayout(panelModificarProductoLayout);
        panelModificarProductoLayout.setHorizontalGroup(
            panelModificarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModificarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelModificarProductoLayout.setVerticalGroup(
            panelModificarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModificarProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 51));
        jLabel10.setText("Cantidad");

        spinnerModificadoCantidad.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        spinnerModificadoCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        txtProductoModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtProductoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 51));
        jLabel14.setText("Producto");

        javax.swing.GroupLayout panelp2Layout = new javax.swing.GroupLayout(panelp2);
        panelp2.setLayout(panelp2Layout);
        panelp2Layout.setHorizontalGroup(
            panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp2Layout.createSequentialGroup()
                        .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreDelProveedorModificar))
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreProductoModificar)))
                        .addGap(14, 14, 14))
                    .addGroup(panelp2Layout.createSequentialGroup()
                        .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProductoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrecioCostoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerModificadoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioFleteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelModificarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelp2Layout.setVerticalGroup(
            panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(28, 28, 28)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombreProductoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombreDelProveedorModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioCostoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioFleteModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(28, 28, 28)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerModificadoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(panelModificarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        botonParaActualizar.setBackground(new java.awt.Color(255, 255, 204));
        botonParaActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonParaActualizarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonParaActualizarLayout = new javax.swing.GroupLayout(botonParaActualizar);
        botonParaActualizar.setLayout(botonParaActualizarLayout);
        botonParaActualizarLayout.setHorizontalGroup(
            botonParaActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        botonParaActualizarLayout.setVerticalGroup(
            botonParaActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        panelp3.setBackground(new java.awt.Color(204, 204, 255));
        panelp3.setPreferredSize(new java.awt.Dimension(487, 181));

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 51));
        jLabel11.setText("Operación");

        comoboProductoC2.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comoboProductoC2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 51));
        jLabel12.setText("Producto");

        comboOperacion.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comboOperacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-", "=" }));

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 51));
        jLabel13.setText("Cantidad");

        spineerCantidadOperacion.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        spineerCantidadOperacion.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        panelExistenciasProductos.setBackground(new java.awt.Color(102, 153, 255));
        panelExistenciasProductos.setForeground(new java.awt.Color(102, 153, 255));
        panelExistenciasProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelExistenciasProductosMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("MODIFICAR EXISTENCIAS");

        javax.swing.GroupLayout panelExistenciasProductosLayout = new javax.swing.GroupLayout(panelExistenciasProductos);
        panelExistenciasProductos.setLayout(panelExistenciasProductosLayout);
        panelExistenciasProductosLayout.setHorizontalGroup(
            panelExistenciasProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExistenciasProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelExistenciasProductosLayout.setVerticalGroup(
            panelExistenciasProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelExistenciasProductosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelp3Layout = new javax.swing.GroupLayout(panelp3);
        panelp3.setLayout(panelp3Layout);
        panelp3Layout.setHorizontalGroup(
            panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelExistenciasProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(panelp3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comoboProductoC2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spineerCantidadOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        panelp3Layout.setVerticalGroup(
            panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(comoboProductoC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spineerCantidadOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                .addComponent(panelExistenciasProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(panelB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(PanelB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PanelB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panelp3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                .addComponent(panelp2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                .addComponent(panelp1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(PanelB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonParaActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PanelB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PanelB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(PanelB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonParaActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(panelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelp2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelp3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 513, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(panelp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 2374, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 2268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelB1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelB1MouseClicked
        // TODO add your handling code here:
        
        //primer boton
   
        panelB1.setBackground(colorazulclaro);
        PanelB2.setBackground(colorazuloscurlo);
        PanelB3.setBackground(colorazuloscurlo);
        
        panelp1.setVisible(true);
        panelp2.setVisible(false);
        panelp3.setVisible(false);
        
        limpiarCamposProducto();
    }//GEN-LAST:event_panelB1MouseClicked

    private void PanelB2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelB2MouseClicked
        // TODO add your handling code here:
        
        //segundo boton
        panelB1.setBackground(colorazuloscurlo);
        PanelB2.setBackground(colorazulclaro);
        PanelB3.setBackground(colorazuloscurlo);
        
        panelp1.setVisible(false);
        panelp2.setVisible(true);
        panelp3.setVisible(false);
        limpiarCamposProducto();

    }//GEN-LAST:event_PanelB2MouseClicked

    private void PanelB3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelB3MouseClicked
        // TODO add your handling code here:
        
        //tercer boton
        panelB1.setBackground(colorazuloscurlo);
        PanelB2.setBackground(colorazuloscurlo);
        PanelB3.setBackground(colorazulclaro);
        
        panelp1.setVisible(false);
        panelp2.setVisible(false);
        panelp3.setVisible(true);
        limpiarCamposProducto();

    }//GEN-LAST:event_PanelB3MouseClicked

    private void PanelB4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelB4MouseClicked
        // TODO add your handling code here:
panelp4.setVisible(true);
                limpiarCamposProducto();
    }//GEN-LAST:event_PanelB4MouseClicked

    private void panelAgregarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAgregarProductoMouseClicked
        // TODO add your handling code here:
        
        //boton para agregar un producto
        try{
            //obtenemos los datos del usuario
        String newnombre = txtNombreDelProductoAgregar.getText();
        String newproveedor = txtNombreDelProveedorAgregar.getText();
        float newPrecioCosto = Float.parseFloat(txtPrecioCostoAgregar.getText());
        float newPrecioFlete = Float.parseFloat(txtPrecioFleteAgregar.getText());
        
            if (!newnombre.isEmpty() && !newproveedor.isEmpty() && newPrecioCosto > 0 && newPrecioFlete > 0) {
                
        
        int newExistencias = (Integer) spinnerCantidad.getValue();
        
        //creamos el objeto con los valores
        Producto newproduc = new Producto(newnombre, newproveedor, newExistencias, newPrecioCosto, newPrecioFlete );
        
        //lo asignamos a los dos vectores       
        Tproductos.setUnProducto(newproduc);
        
        //actualizamo la tabla
        cargarInvetrioTabla();
        
        //agregamos el quintal al comobo box de modifica y de cantidades.
        txtProductoModificar.addItem(newnombre);
        comoboProductoC2.addItem(newnombre);
        
        //dejamos todos los campos en blanco 
        txtNombreDelProductoAgregar.setText("");
        txtNombreDelProveedorAgregar.setText("");
        
        txtPrecioCostoAgregar.setText("");
        txtPrecioFleteAgregar.setText("");
        
        spinnerCantidad.setValue(0);
        
        //funcion para guardar todos los quintales actuales
        Tproductos.getCargarInvetarioExcel();
        
        //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "producto agregado corretamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
       
            }else{
               //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "complete el nombre y proveedor, ingrese precios validos", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
        
        
        }
        catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_panelAgregarProductoMouseClicked

    private void panelModificarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarProductoMouseClicked
        try {
        String comboA = txtProductoModificar.getSelectedItem().toString();
            
        if (comboA.equals("None")) {
            JOptionPane.showMessageDialog(null, "seleccione un producto", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
        } else {
            String newnombre = txtNombreProductoModificar.getText();
            String newproveedor = txtNombreDelProveedorModificar.getText();
            float newPrecioCosto = Float.parseFloat(txtPrecioCostoModificar.getText());
            float newPrecioFlete = Float.parseFloat(txtPrecioFleteModificar.getText());
            
            if (!newnombre.isEmpty() && !newproveedor.isEmpty() && newPrecioCosto > 0 && newPrecioFlete > 0) {
                int newExistencias = (Integer) spinnerModificadoCantidad.getValue();

                Tproductos.actualizarProducto(txtProductoModificar.getSelectedIndex() - 1, 
                    newnombre, newproveedor, newExistencias, newPrecioCosto, newPrecioFlete);

                cargarInvetrioTabla();
                actualizarCombobox();
                Tproductos.getCargarInvetarioExcel();
                
                // Restaurar placeholders después de modificar
                configurarCamposProducto();
                
                JOptionPane.showMessageDialog(null, "producto modificado correctamente", 
                    "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
            } else {
                JOptionPane.showMessageDialog(null, "complete el nombre y proveedor, ingrese precios validos", 
                    "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", 
            "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
    }
    }//GEN-LAST:event_panelModificarProductoMouseClicked

    private void panelExistenciasProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelExistenciasProductosMouseClicked
        // TODO add your handling code here:
        
        //boton para quitar, agregar, o igual cantidad
        
        try{
            //obtenemos el valor del combo box correspondiente
        
        String comboB = comoboProductoC2.getSelectedItem().toString();
        
        //verificamos que is hay algun valor
        if (comboB.equals("None")) {
           System.out.println("no paso nada");   
           //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "seleccione un producto", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
       
        }else{
            
            //obteenmos la operaicon que desea realizar el usuario
            String newoperacion = comboOperacion.getSelectedItem().toString();
            
            //obtenemos el valor que desea cambiar
            int newcantidadC = (Integer) spineerCantidadOperacion.getValue();
            
            if (newoperacion.equals("-") && productosdeinvetario.get(comoboProductoC2.getSelectedIndex() - 1).getExistencias() >= newcantidadC || newoperacion.equals("+") || newoperacion.equals("=")) {
                
//preguntamos si la cantidad a cambiar en la resta es mayor al actual
            if (newoperacion.equals("-") && productosdeinvetario.get(comoboProductoC2.getSelectedIndex() - 1).getExistencias() >= newcantidadC) {
                //ahora llamamos a la funcion de cambiar el valor de un elemento      
                Tproductos.setCantidad(comoboProductoC2.getSelectedIndex() - 1, newcantidadC, newoperacion);
            }else if (newoperacion.equals("+") || newoperacion.equals("=")) {
                Tproductos.setCantidad(comoboProductoC2.getSelectedIndex() - 1, newcantidadC, newoperacion);
            }
            
            //actualizamo la tabla
            cargarInvetrioTabla();
            
            //funcion para guardar todos los quintales actuales
            Tproductos.getCargarInvetarioExcel();
            
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "cantidad actualizada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
       
            }else{
               //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "no existen cantidad en existencia para completar la trasaccion", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
            
        }
        }
        catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
        
    }//GEN-LAST:event_panelExistenciasProductosMouseClicked

    private void botonParaActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonParaActualizarMouseClicked
        // TODO add your handling code here:
        
        //funcion para guardar todos los quintales actuales
        Tproductos.getCargarInvetarioExcel();
        System.out.println("se ha cargado correctamente");
    }//GEN-LAST:event_botonParaActualizarMouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void txtNombreDelProveedorModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreDelProveedorModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreDelProveedorModificarActionPerformed

    private void txtMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenu3ActionPerformed

    private void txtNombreDelProductoAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreDelProductoAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreDelProductoAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                            
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FrameInventario(username, role, loginFrame).setVisible(true);
            
            }
        });
    }
    
private void iniciarBucleEnHilo() {
    Thread hiloBucle = new Thread(() -> {
        while (true) {
            String comboA = txtProductoModificar.getSelectedItem().toString();
            
            if (comboA.equals("None")) {
                if (indiceActual != 0) {
                    indiceActual = 0;
                    SwingUtilities.invokeLater(() -> {
                        // Restaurar placeholders
                        configurarCamposProducto();
                    });
                }
            } else {
                int selectedIndex = txtProductoModificar.getSelectedIndex();
                if (indiceActual != selectedIndex) {
                    indiceActual = selectedIndex;
                    final Producto productoSeleccionado = productosdeinvetario.get(selectedIndex - 1);
                    
                    SwingUtilities.invokeLater(() -> {
                        // Establecer valores con color negro
                        setTextWithBlackColor(txtNombreProductoModificar, productoSeleccionado.getNombre());
                        setTextWithBlackColor(txtNombreDelProveedorModificar, productoSeleccionado.getProveedor());
                        setTextWithBlackColor(txtPrecioCostoModificar, String.valueOf(productoSeleccionado.getPrecioCosto()));
                        setTextWithBlackColor(txtPrecioFleteModificar, String.valueOf(productoSeleccionado.getPrecioFlete()));
                        spinnerModificadoCantidad.setValue(productoSeleccionado.getExistencias());
                    });
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    hiloBucle.start();
}

private void setTextWithBlackColor(JTextField field, String text) {
    field.setForeground(Color.BLACK);
    field.setText(text);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelB2;
    private javax.swing.JPanel PanelB3;
    private javax.swing.JPanel PanelB4;
    private javax.swing.JPanel botonParaActualizar;
    private javax.swing.JComboBox<String> comboOperacion;
    private javax.swing.JComboBox<String> comoboProductoC2;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JPanel panelAgregarProducto;
    private javax.swing.JPanel panelB1;
    private javax.swing.JPanel panelExistenciasProductos;
    private javax.swing.JPanel panelModificarProducto;
    private javax.swing.JPanel panelp1;
    private javax.swing.JPanel panelp2;
    private javax.swing.JPanel panelp3;
    private javax.swing.JPanel panelp4;
    private javax.swing.JSpinner spineerCantidadOperacion;
    private javax.swing.JSpinner spinnerCantidad;
    private javax.swing.JSpinner spinnerModificadoCantidad;
    private javax.swing.JTable tablaInventario;
    private javax.swing.JComboBox<String> txtMenu3;
    private javax.swing.JTextField txtNombreDelProductoAgregar;
    private javax.swing.JTextField txtNombreDelProveedorAgregar;
    private javax.swing.JTextField txtNombreDelProveedorModificar;
    private javax.swing.JTextField txtNombreProductoModificar;
    private javax.swing.JTextField txtPrecioCostoAgregar;
    private javax.swing.JTextField txtPrecioCostoModificar;
    private javax.swing.JTextField txtPrecioFleteAgregar;
    private javax.swing.JTextField txtPrecioFleteModificar;
    private javax.swing.JComboBox<String> txtProductoModificar;
    // End of variables declaration//GEN-END:variables
}
