package ControlInventario;

import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import Inicio.INICIOPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
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
            comboProductoM.addItem(prod.getNombre());
            comoboProductoC2.addItem(prod.getNombre());

        }
        
        //cargamos el invetario en la tabla
        cargarInvetrioTabla();
        
        
        //llamamos a un bucle infinito
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
    
    private void setupComboBox() {
        txtMenu.removeAllItems();
        txtMenu.addItem("Seleccione una opción");

        if (userRole.equalsIgnoreCase("ADMINISTRADOR")) {
            addAdminOptions();
        } else if (userRole.equalsIgnoreCase("SECRETARIA")) {
            addSecretariaOptions();
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
        txtMenu.addItem("Gestión de Créditos");
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
        txtMenu.addItem("Planilla de Trabajadores");
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
        
        comboProductoM.removeAllItems();
        comoboProductoC2.removeAllItems();
        
        comboProductoM.addItem("None");
        comoboProductoC2.addItem("None");
        
        //con este for llenaremos nuevamente los combo box
        for (Producto prod : productosdeinvetario) {
            comboProductoM.addItem(prod.getNombre());
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
        jPanel2 = new javax.swing.JPanel();
        PanelB4 = new javax.swing.JPanel();
        panelp4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        jTextField19 = new javax.swing.JTextField();
        botonParaActualizar = new javax.swing.JPanel();
        PanelB3 = new javax.swing.JPanel();
        PanelB2 = new javax.swing.JPanel();
        panelB1 = new javax.swing.JPanel();
        panelp1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelAgregarProducto = new javax.swing.JPanel();
        textProductoN = new javax.swing.JTextField();
        textProductoP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textProductoPC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textProductoPF = new javax.swing.JTextField();
        spinnerCantidad = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        panelp3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        comoboProductoC2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        comboOperacion = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        spineerCantidadOperacion = new javax.swing.JSpinner();
        panelExistenciasProductos = new javax.swing.JPanel();
        panelp2 = new javax.swing.JPanel();
        textoModificadoNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textoModificadoProveedor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textoModificadoPC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        textoModificadoPF = new javax.swing.JTextField();
        panelModificarProducto = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        spinnerModificadoCantidad = new javax.swing.JSpinner();
        comboProductoM = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 102));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1375, 752));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        PanelB4.setBackground(new java.awt.Color(204, 153, 255));
        PanelB4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelB4Layout = new javax.swing.GroupLayout(PanelB4);
        PanelB4.setLayout(PanelB4Layout);
        PanelB4Layout.setHorizontalGroup(
            PanelB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );
        PanelB4Layout.setVerticalGroup(
            PanelB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
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
            .addGroup(panelp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelp4Layout.setVerticalGroup(
            panelp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
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

        PanelB3.setBackground(new java.awt.Color(102, 153, 255));
        PanelB3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelB3Layout = new javax.swing.GroupLayout(PanelB3);
        PanelB3.setLayout(PanelB3Layout);
        PanelB3Layout.setHorizontalGroup(
            PanelB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelB3Layout.setVerticalGroup(
            PanelB3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PanelB2.setBackground(new java.awt.Color(102, 102, 255));
        PanelB2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelB2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelB2Layout = new javax.swing.GroupLayout(PanelB2);
        PanelB2.setLayout(PanelB2Layout);
        PanelB2Layout.setHorizontalGroup(
            PanelB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PanelB2Layout.setVerticalGroup(
            PanelB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );

        panelB1.setBackground(new java.awt.Color(60, 100, 132));
        panelB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelB1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelB1Layout = new javax.swing.GroupLayout(panelB1);
        panelB1.setLayout(panelB1Layout);
        panelB1Layout.setHorizontalGroup(
            panelB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelB1Layout.setVerticalGroup(
            panelB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelp1.setBackground(new java.awt.Color(204, 204, 255));
        panelp1.setPreferredSize(new java.awt.Dimension(487, 181));

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setText("Nombre Del provedor");

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 51));
        jLabel2.setText("Nombre Del Producto");

        panelAgregarProducto.setBackground(new java.awt.Color(85, 111, 169));
        panelAgregarProducto.setPreferredSize(new java.awt.Dimension(75, 28));
        panelAgregarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAgregarProductoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAgregarProductoLayout = new javax.swing.GroupLayout(panelAgregarProducto);
        panelAgregarProducto.setLayout(panelAgregarProductoLayout);
        panelAgregarProductoLayout.setHorizontalGroup(
            panelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
        panelAgregarProductoLayout.setVerticalGroup(
            panelAgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        textProductoN.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        textProductoP.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 51));
        jLabel4.setText("Precio Costo");

        textProductoPC.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 51));
        jLabel5.setText("Precio Flete");

        textProductoPF.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        spinnerCantidad.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 51));
        jLabel3.setText("Cantidad");

        javax.swing.GroupLayout panelp1Layout = new javax.swing.GroupLayout(panelp1);
        panelp1.setLayout(panelp1Layout);
        panelp1Layout.setHorizontalGroup(
            panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp1Layout.createSequentialGroup()
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelp1Layout.createSequentialGroup()
                                .addComponent(textProductoPF, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelp1Layout.createSequentialGroup()
                                .addComponent(textProductoPC, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp1Layout.createSequentialGroup()
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textProductoP)
                            .addComponent(textProductoN, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerCantidad)))
                .addContainerGap())
        );
        panelp1Layout.setVerticalGroup(
            panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textProductoN, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textProductoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(textProductoPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 41, Short.MAX_VALUE)
                .addGroup(panelp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(textProductoPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(panelAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        panelExistenciasProductos.setBackground(new java.awt.Color(85, 111, 169));
        panelExistenciasProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelExistenciasProductosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelExistenciasProductosLayout = new javax.swing.GroupLayout(panelExistenciasProductos);
        panelExistenciasProductos.setLayout(panelExistenciasProductosLayout);
        panelExistenciasProductosLayout.setHorizontalGroup(
            panelExistenciasProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelExistenciasProductosLayout.setVerticalGroup(
            panelExistenciasProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelp3Layout = new javax.swing.GroupLayout(panelp3);
        panelp3.setLayout(panelp3Layout);
        panelp3Layout.setHorizontalGroup(
            panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comoboProductoC2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelExistenciasProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spineerCantidadOperacion, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelp3Layout.setVerticalGroup(
            panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp3Layout.createSequentialGroup()
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel12))
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spineerCantidadOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addComponent(comoboProductoC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(panelp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboOperacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(panelExistenciasProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelp2.setBackground(new java.awt.Color(204, 204, 255));
        panelp2.setPreferredSize(new java.awt.Dimension(487, 181));

        textoModificadoNombre.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 51));
        jLabel6.setText("Nombre Del Producto");

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 51));
        jLabel7.setText("Nombre Del provedor");

        textoModificadoProveedor.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        textoModificadoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoModificadoProveedorActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 51));
        jLabel8.setText("Precio Costo");

        textoModificadoPC.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 51));
        jLabel9.setText("Precio Flete");

        textoModificadoPF.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        panelModificarProducto.setBackground(new java.awt.Color(85, 111, 169));
        panelModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelModificarProductoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelModificarProductoLayout = new javax.swing.GroupLayout(panelModificarProducto);
        panelModificarProducto.setLayout(panelModificarProductoLayout);
        panelModificarProductoLayout.setHorizontalGroup(
            panelModificarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
        panelModificarProductoLayout.setVerticalGroup(
            panelModificarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 51));
        jLabel10.setText("Cantidad");

        spinnerModificadoCantidad.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        comboProductoM.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comboProductoM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

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
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoModificadoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerModificadoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelp2Layout.createSequentialGroup()
                        .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textoModificadoPC, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textoModificadoPF, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(192, 192, 192))
                            .addGroup(panelp2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboProductoM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(86, 86, 86)))
                        .addGap(73, 73, 73)
                        .addComponent(panelModificarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelp2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoModificadoProveedor)
                        .addGap(240, 240, 240))))
        );
        panelp2Layout.setVerticalGroup(
            panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelp2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerModificadoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(textoModificadoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textoModificadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboProductoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp2Layout.createSequentialGroup()
                        .addComponent(panelModificarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelp2Layout.createSequentialGroup()
                        .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoModificadoPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoModificadoPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(24, 24, 24))))
        );

        jPanel5.setBackground(new java.awt.Color(32, 67, 99));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelp2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addComponent(panelp1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(panelB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(PanelB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PanelB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonParaActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelp3, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(panelp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PanelB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(PanelB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(botonParaActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(panelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelp2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelp3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(153, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 222, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1597, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(197, Short.MAX_VALUE))
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
    }//GEN-LAST:event_PanelB3MouseClicked

    private void PanelB4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelB4MouseClicked
        // TODO add your handling code here:
        
        panelp4.setVisible(true);
    }//GEN-LAST:event_PanelB4MouseClicked

    private void panelAgregarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAgregarProductoMouseClicked
        // TODO add your handling code here:
        
        //boton para agregar un producto
        
        //obtenemos los datos del usuario
        String newnombre = textProductoN.getText();
        String newproveedor = textProductoP.getText();
        
        float newPrecioCosto = Float.parseFloat(textProductoPC.getText());
        float newPrecioFlete = Float.parseFloat(textProductoPF.getText());
        
        int newExistencias = (Integer) spinnerCantidad.getValue();
        
        //creamos el objeto con los valores
        Producto newproduc = new Producto(newnombre, newproveedor, newExistencias, newPrecioCosto, newPrecioFlete );
        
        //lo asignamos a los dos vectores       
        Tproductos.setUnProducto(newproduc);
        
        //actualizamo la tabla
        cargarInvetrioTabla();
        
        //agregamos el quintal al comobo box de modifica y de cantidades.
        comboProductoM.addItem(newnombre);
        comoboProductoC2.addItem(newnombre);
        
        //dejamos todos los campos en blanco 
        textProductoN.setText("");
        textProductoP.setText("");
        
        textProductoPC.setText("");
        textProductoPF.setText("");
        
        spinnerCantidad.setValue(0);
        
        //funcion para guardar todos los quintales actuales
        Tproductos.getCargarInvetarioExcel();
    }//GEN-LAST:event_panelAgregarProductoMouseClicked

    private void panelModificarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelModificarProductoMouseClicked
        // TODO add your handling code here:
        
        //boton para modificar un elemento
        
        
        //leemos el combo box de modificar producto
        String comboA = comboProductoM.getSelectedItem().toString();
            
        //aqui preguntamos si ese valor es vali o no
        if (comboA.equals("None")) {
             System.out.println("no paso nada");
        }else{
                
            //obtenemos los datos del usuario
            String newnombre = textoModificadoNombre.getText();
            String newproveedor = textoModificadoProveedor.getText();

            float newPrecioCosto = Float.parseFloat(textoModificadoPC.getText());
            float newPrecioFlete = Float.parseFloat(textoModificadoPF.getText());

            int newExistencias = (Integer) spinnerModificadoCantidad.getValue();


            //llamamos a la funcion de modificar un elemento del vector
            Tproductos.actualizarProducto( comboProductoM.getSelectedIndex() - 1, newnombre, newproveedor, newExistencias, newPrecioCosto, newPrecioFlete);

            //actualizamo la tabla
            cargarInvetrioTabla();

            //actualizamos los comobo box
            actualizarCombobox();
            
            //funcion para guardar todos los quintales actuales
            Tproductos.getCargarInvetarioExcel();
        }
    }//GEN-LAST:event_panelModificarProductoMouseClicked

    private void panelExistenciasProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelExistenciasProductosMouseClicked
        // TODO add your handling code here:
        
        //boton para quitar, agregar, o igual cantidad
        
        //obtenemos el valor del combo box correspondiente
        
        String comboB = comoboProductoC2.getSelectedItem().toString();
        
        //verificamos que is hay algun valor
        if (comboB.equals("None")) {
           System.out.println("no paso nada");     
        }else{
            
            //obteenmos la operaicon que desea realizar el usuario
            String newoperacion = comboOperacion.getSelectedItem().toString();
            
            //obtenemos el valor que desea cambiar
            int newcantidadC = (Integer) spineerCantidadOperacion.getValue();
            
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
        }
    }//GEN-LAST:event_panelExistenciasProductosMouseClicked

    private void botonParaActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonParaActualizarMouseClicked
        // TODO add your handling code here:
        
        //funcion para guardar todos los quintales actuales
        Tproductos.getCargarInvetarioExcel();
        System.out.println("se ha cargado correctamente");
    }//GEN-LAST:event_botonParaActualizarMouseClicked

    private void txtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void textoModificadoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoModificadoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoModificadoProveedorActionPerformed

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
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
             //leemos el combo box de modificar producto
            String comboA = comboProductoM.getSelectedItem().toString();
            
            //aqui preguntamos si ese valor es vali o no
            if (comboA.equals("None")) {
                indiceActual = 0;
                
                textoModificadoNombre.setText("");
                textoModificadoProveedor.setText("");
                textoModificadoPC.setText("");
                 textoModificadoPF.setText("");
                    
                spinnerModificadoCantidad.setValue(0);
                    
            }else{
                
                //ingresamo los datos del producto a modificar dentro de los field text
                if (indiceActual != comboProductoM.getSelectedIndex()) {
                    
                    textoModificadoNombre.setText(productosdeinvetario.get(comboProductoM.getSelectedIndex() - 1).getNombre());
                    textoModificadoProveedor.setText(productosdeinvetario.get(comboProductoM.getSelectedIndex() - 1).getProveedor());
                    textoModificadoPC.setText("" + productosdeinvetario.get(comboProductoM.getSelectedIndex() - 1).getPrecioCosto());
                    textoModificadoPF.setText("" + productosdeinvetario.get(comboProductoM.getSelectedIndex() - 1).getPrecioFlete());
                    
                    spinnerModificadoCantidad.setValue(productosdeinvetario.get(comboProductoM.getSelectedIndex() - 1).getExistencias());
                    
                    indiceActual = comboProductoM.getSelectedIndex();
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelB2;
    private javax.swing.JPanel PanelB3;
    private javax.swing.JPanel PanelB4;
    private javax.swing.JPanel botonParaActualizar;
    private javax.swing.JComboBox<String> comboOperacion;
    private javax.swing.JComboBox<String> comboProductoM;
    private javax.swing.JComboBox<String> comoboProductoC2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTextField textProductoN;
    private javax.swing.JTextField textProductoP;
    private javax.swing.JTextField textProductoPC;
    private javax.swing.JTextField textProductoPF;
    private javax.swing.JTextField textoModificadoNombre;
    private javax.swing.JTextField textoModificadoPC;
    private javax.swing.JTextField textoModificadoPF;
    private javax.swing.JTextField textoModificadoProveedor;
    private javax.swing.JComboBox<String> txtMenu;
    // End of variables declaration//GEN-END:variables
}
