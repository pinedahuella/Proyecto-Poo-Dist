package ControlPedidos;
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
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import javax.swing.JButton;
import ControlViajes.*;




//librerias para las fechas
import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.IDateEvaluator;

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
            comboPilotosA.addItem(pilotonew.get(i).getNombrePiloto());
            comboPilotoB.addItem(pilotonew.get(i).getNombrePiloto());
        }
        for (int i = 0; i < camionesnew.size(); i++) { 
            comboCamionA.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
            comboCamionB.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
        }
        
        
        //actualizamos la tabla de pedidos
        actualizarTablaDePedidos();
             

       //iniciamos el bucle infinito
       iniciarBucleEnHilo(); 
                 this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        configureButtonsByRole();

    }
    
   
            
private void configureButtonsByRole() {
    JButton[] allButtons = {
        btnGestionDeVentas, btnGestionDePedidos, btnPlanillaDeTrabajadores,
        btnGestionDeClientes, btnGestionDeCreditos, btnInventarioDeQuintales,
        btnCalendario, btnGestionDePilotos, btnGestionDeCamiones,
        btnGestionDeUsuarios
    };
    for (JButton button : allButtons) {
        if (button != null) {
            button.setVisible(false);
            button.setEnabled(false);
        } else {
            System.out.println("Warning: A button is null");
        }
    }

     if (userRole != null) {
        switch (userRole.toUpperCase()) {
            case "ADMINISTRADOR":
                // Mostrar todos los botones para el administrador
                for (JButton button : allButtons) {
                    if (button != null) {
                        button.setVisible(true);
                        button.setEnabled(true);
                    }
                }
                break;
                case "USUARIO":
                    // Solo mostrar y habilitar el botón de Calendario para usuarios
                    btnCalendario.setVisible(true);
                    btnCalendario.setEnabled(true);
                    break;
                case "SECRETARIA":
                // Solo mostrar botones específicos para la secretaria
                btnGestionDeCamiones.setVisible(true);
                btnGestionDeCamiones.setEnabled(true);
                btnPlanillaDeTrabajadores.setVisible(true);
                btnPlanillaDeTrabajadores.setEnabled(true);
                // Asegúrate de que btnGestionDeVentas no esté visible aquí
                break;
                default:
                    // Para cualquier otro rol, no mostrar ningún botón
                    break;
           
        
        }
        }
    }

    public void addWindowListener() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarSesionYSalir();
            }
        });
    }
     
    private void cerrarSesionYSalir() {
        if (loginFrame != null) {
            loginFrame.cerrarSesion(currentUser, userRole);
        }
        // Crear una nueva instancia de LOGINPINEED sin pasar argumentos nulos
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true);
        this.dispose();
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
             fechaCargaB.setDate(null);    
             fechaDescargaB.setDate(null);
                
            //igualamos el combo box de pilotos y camiones
            comboPilotoB.setSelectedIndex(0);
            comboCamionB.setSelectedIndex(0);
                
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductosA = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaCargaA = new com.toedter.calendar.JDateChooser();
        fechaDescargaA = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboPilotosA = new javax.swing.JComboBox<>();
        comboCamionA = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProductosB = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        radioFinalizadoA = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fechaDescargaB = new com.toedter.calendar.JDateChooser();
        fechaCargaB = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        comboPilotoB = new javax.swing.JComboBox<>();
        comboCamionB = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        labeltPedido = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
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
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        formato1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidosA = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPedidosB = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        btnGestionDeVentas = new javax.swing.JButton();
        btnGestionDePedidos = new javax.swing.JButton();
        btnPlanillaDeTrabajadores = new javax.swing.JButton();
        btnGestionDeClientes = new javax.swing.JButton();
        btnGestionDeCreditos = new javax.swing.JButton();
        btnInventarioDeQuintales = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnCalendario = new javax.swing.JButton();
        btnGestionDePilotos = new javax.swing.JButton();
        btnGestionDeCamiones = new javax.swing.JButton();
        btnGestionDeUsuarios = new javax.swing.JButton();
        btnRegresarLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(6, 40, 86));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(78, 64, 2));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(219, 217, 92));

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Productos del Pedido:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Fecha de Carga:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Fecha de Descarga:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Piloto:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Transporte Pesado:");

        jPanel9.setBackground(new java.awt.Color(78, 64, 2));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Agregar Pedido");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(84, 84, 84))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(84, 84, 84))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboPilotosA, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fechaCargaA, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fechaDescargaA, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(comboCamionA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(45, 45, 45))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaCargaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaDescargaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPilotosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCamionA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Agregar Pedido", jPanel3);

        jPanel4.setBackground(new java.awt.Color(219, 217, 92));

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Productos del Pedido:");

        radioFinalizadoA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        radioFinalizadoA.setText("Finalizar Pedido");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Fecha de Carga:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Fecha de Descarga:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Piloto:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Transporte Pesado:");

        jPanel10.setBackground(new java.awt.Color(78, 64, 2));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Modificar Pedido");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18))
        );

        labeltPedido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labeltPedido.setText("Tipo de Pedido:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Para la Distribuidora");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboPilotoB, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fechaCargaB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(117, 117, 117)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboCamionB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaDescargaB, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(186, 186, 186)
                                .addComponent(radioFinalizadoA, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labeltPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(radioFinalizadoA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaCargaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaDescargaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPilotoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCamionB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeltPedido)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Modificar Pedido", jPanel4);

        jPanel11.setBackground(new java.awt.Color(219, 217, 92));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Productos del Pedido:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Piloto:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Transporte Pesado:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Fecha de Descarga:");

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

        labelFechaC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelFechaC.setText("Fecha de Carga");

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

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Fecha de Carga:");

        labelFechaD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelFechaD.setText("Fecha de Descarga");

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

        labelPiloto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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

        labelCamion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(101, 101, 101))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(103, 103, 103))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("pedidosFinalizados", jPanel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(154, 82, 12));

        jPanel6.setBackground(new java.awt.Color(78, 64, 2));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        formato1.setBackground(new java.awt.Color(78, 64, 2));
        formato1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(154, 82, 12));

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );

        formato1.addTab("Pedidos En proceso", jPanel7);

        jPanel8.setBackground(new java.awt.Color(154, 82, 12));

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
        );

        formato1.addTab("Pedidos Finalizados ", jPanel8);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formato1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formato1))
        );

        jPanel19.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnGestionDeVentas.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeVentas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeVentas.setText("GESTION DE VENTAS");
        btnGestionDeVentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeVentasActionPerformed(evt);
            }
        });

        btnGestionDePedidos.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDePedidos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDePedidos.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDePedidos.setText("GESTION DE PEDIDOS");
        btnGestionDePedidos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDePedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDePedidosActionPerformed(evt);
            }
        });

        btnPlanillaDeTrabajadores.setBackground(new java.awt.Color(0, 102, 102));
        btnPlanillaDeTrabajadores.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPlanillaDeTrabajadores.setForeground(new java.awt.Color(255, 255, 255));
        btnPlanillaDeTrabajadores.setText("PLANILLA DE TRABAJADORES");
        btnPlanillaDeTrabajadores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnPlanillaDeTrabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanillaDeTrabajadoresActionPerformed(evt);
            }
        });

        btnGestionDeClientes.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeClientes.setText("GESTION DE CLIENTES");
        btnGestionDeClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeClientesActionPerformed(evt);
            }
        });

        btnGestionDeCreditos.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeCreditos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeCreditos.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeCreditos.setText("GESTION DE CREDITOS");
        btnGestionDeCreditos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeCreditosActionPerformed(evt);
            }
        });

        btnInventarioDeQuintales.setBackground(new java.awt.Color(0, 102, 102));
        btnInventarioDeQuintales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInventarioDeQuintales.setForeground(new java.awt.Color(255, 255, 255));
        btnInventarioDeQuintales.setText("INVENTARIO QUINTALES");
        btnInventarioDeQuintales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInventarioDeQuintales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioDeQuintalesActionPerformed(evt);
            }
        });

        btnCerrarSesion.setBackground(new java.awt.Color(0, 102, 102));
        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("CERRAR SESION");
        btnCerrarSesion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        btnCalendario.setBackground(new java.awt.Color(0, 102, 102));
        btnCalendario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCalendario.setForeground(new java.awt.Color(255, 255, 255));
        btnCalendario.setText("CALENDARIO");
        btnCalendario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnCalendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarioActionPerformed(evt);
            }
        });

        btnGestionDePilotos.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDePilotos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDePilotos.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDePilotos.setText("GESTION DE PILOTOS");
        btnGestionDePilotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDePilotos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDePilotosActionPerformed(evt);
            }
        });

        btnGestionDeCamiones.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeCamiones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeCamiones.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeCamiones.setText("GESTION DE CAMIONES");
        btnGestionDeCamiones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeCamionesActionPerformed(evt);
            }
        });

        btnGestionDeUsuarios.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeUsuarios.setText("GESTION DE USUARIOS");
        btnGestionDeUsuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeUsuariosActionPerformed(evt);
            }
        });

        btnRegresarLogin.setBackground(new java.awt.Color(0, 102, 102));
        btnRegresarLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegresarLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresarLogin.setText("REGRESAR LOGIN");
        btnRegresarLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnRegresarLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnGestionDeUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePilotos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDeVentas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePedidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionDeClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnRegresarLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlanillaDeTrabajadores, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInventarioDeQuintales, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCalendario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegresarLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrarSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeCamiones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPlanillaDeTrabajadores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInventarioDeQuintales)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDePedidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeCreditos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDePilotos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeUsuarios)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)))
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

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        
        //funcion para hacer un nuevo pedido
        
        //leemos las fechas primero primero:  
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        Date newFechaCarga = fechaCargaA.getDate();
        Date newFechaDescarga = fechaDescargaA.getDate();
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // Restar un día a la fecha actual
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaActualMenosUnDia = calendar.getTime();
        
         try {
          //verifica que las fechas sean validas
        if (newFechaCarga != null && newFechaDescarga != null && !newFechaCarga.before(fechaActualMenosUnDia) && !newFechaDescarga.before(fechaActualMenosUnDia) && !newFechaDescarga.before(newFechaCarga)) {
            
            //creamos los indices de pilotos y camiones
            int newIndicePiloto = comboPilotosA.getSelectedIndex();
            int newIndiceCamion = comboCamionA.getSelectedIndex();
            
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
                
                comboPilotosA.setSelectedIndex(0);
                comboCamionA.setSelectedIndex(0);

                //actualizamos la tabla general de pedidos
                actualizarTablaDePedidos();
                
                //cargamos los datos en el excel
                gescalendario.guardarFecharExcel();
                
                //cargamos el pedido al excel
                gespedidos.GuardarEnExcel();
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "Datos agregados de forma correcta", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
  

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
        
        Date newFechaCarga = fechaCargaB.getDate();
        Date newFechaDescarga = fechaDescargaB.getDate();
        
        try {
            if (indiceGeneral > -1) {
                       //verifica que las fechas sean validas
                if (newFechaCarga != null && newFechaDescarga != null && !newFechaDescarga.before(newFechaCarga)) {

                    //creamos los indices de pilotos y camiones
                    int newIndicePiloto = comboPilotoB.getSelectedIndex();
                    int newIndiceCamion = comboCamionB.getSelectedIndex();

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

    private void btnGestionDeVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeVentasActionPerformed

    }//GEN-LAST:event_btnGestionDeVentasActionPerformed

    private void btnGestionDePedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDePedidosActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FormularioPedidos abrir = new  FormularioPedidos(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDePedidosActionPerformed

    private void btnPlanillaDeTrabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanillaDeTrabajadoresActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

    if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("SECRETARIA")) {
        FramePlanillaSemanal abrir = new FramePlanillaSemanal(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    } else {
        JOptionPane.showMessageDialog(this, "No tienes permiso para acceder a este módulo.");
    }
    }//GEN-LAST:event_btnPlanillaDeTrabajadoresActionPerformed

    private void btnGestionDeClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeClientesActionPerformed

    }//GEN-LAST:event_btnGestionDeClientesActionPerformed

    private void btnGestionDeCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeCreditosActionPerformed

    }//GEN-LAST:event_btnGestionDeCreditosActionPerformed

    private void btnInventarioDeQuintalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioDeQuintalesActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        FrameInventario abrir = new  FrameInventario(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInventarioDeQuintalesActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnCalendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarioActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

       if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("USUARIO")) {
        FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    } else {
        JOptionPane.showMessageDialog(this, "No tienes permiso para acceder a este módulo.");
    }
    }//GEN-LAST:event_btnCalendarioActionPerformed

    private void btnGestionDePilotosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDePilotosActionPerformed

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDePilotosActionPerformed

    private void btnGestionDeCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeCamionesActionPerformed

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        if (userRole.equalsIgnoreCase("ADMINISTRADOR") || userRole.equalsIgnoreCase("SECRETARIA")) {
            INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
            abrir.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnGestionDeCamionesActionPerformed

    private void btnGestionDeUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeUsuariosActionPerformed

        String username = this.currentUser; // Assuming currentUser holds the username
        String role = this.userRole;        // Assuming userRole holds the role
        LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDeUsuariosActionPerformed

    private void btnRegresarLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarLoginActionPerformed
        LOGINPINEED abrir = new  LOGINPINEED();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarLoginActionPerformed

    
    
    
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
                fechaCargaB.setDate(FechaTablaNew.get(indiceDelPedido).getFechaC());    
                fechaDescargaB.setDate(FechaTablaNew.get(indiceDelPedido).getFechaD());
                
                //igualamos el combo box de pilotos y camiones
                comboPilotoB.setSelectedIndex(FechaTablaNew.get(indiceDelPedido).getIndicePiloto());
                comboCamionB.setSelectedIndex(FechaTablaNew.get(indiceDelPedido).getIndiceCamion());
                
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
    private javax.swing.JButton btnCalendario;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGestionDeCamiones;
    private javax.swing.JButton btnGestionDeClientes;
    private javax.swing.JButton btnGestionDeCreditos;
    private javax.swing.JButton btnGestionDePedidos;
    private javax.swing.JButton btnGestionDePilotos;
    private javax.swing.JButton btnGestionDeUsuarios;
    private javax.swing.JButton btnGestionDeVentas;
    private javax.swing.JButton btnInventarioDeQuintales;
    private javax.swing.JButton btnPlanillaDeTrabajadores;
    private javax.swing.JButton btnRegresarLogin;
    private javax.swing.JComboBox<String> comboCamionA;
    private javax.swing.JComboBox<String> comboCamionB;
    private javax.swing.JComboBox<String> comboPilotoB;
    private javax.swing.JComboBox<String> comboPilotosA;
    private com.toedter.calendar.JDateChooser fechaCargaA;
    private com.toedter.calendar.JDateChooser fechaCargaB;
    private com.toedter.calendar.JDateChooser fechaDescargaA;
    private com.toedter.calendar.JDateChooser fechaDescargaB;
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
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
    // End of variables declaration//GEN-END:variables
}
