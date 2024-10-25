package ControlPlanilla;

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
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import java.util.Vector;
import java.util.Set;
import java.awt.event.ActionListener;  // Para manejar eventos de acción
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;

public class FramePlanillaSemanal extends javax.swing.JFrame {

    /**
     * Creates new form FramePlanillaSemanal
     */
    //creamos el objeto gestion de trabajdores y el vector de trabajadores
    public GestionFichaTrabajador gTrabajadores;
    public Vector<FichaTrabajador> tTrabajador = new Vector<>();
    
    //creamos un vector de intengers que contendra los indices reales de los trabajadores
    public Vector<Integer>  indicesVectores = new Vector<>();
    
    //crearemos el modelo de la tabla de trabajadores
    DefaultTableModel modeloTrabajador = new DefaultTableModel();
    
    //crearemos el modelo de de la tabla de entradas
    DefaultTableModel modeloEntradas = new DefaultTableModel();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    //crearemos un indice actual que nos ayudara a modificar los elementos
    private int indiceActual;
    private int indiceActualTabla;
    
    public FramePlanillaSemanal(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        //desactivamos el boton de guardar
        botonguardarDatos.setVisible(false);
        setResizable(false); // Desactivar el cambio de tamaño
        //crearemos el objeto gtrabajadores
        gTrabajadores = new GestionFichaTrabajador();
        
        gTrabajadores.cargarTrabajadoresExcel();
        
        //definimos el indice actual en -1 debido aque no representa ningun indice de la tabla real
        indiceActual = -1;
        indiceActualTabla = -1;
        
        //colocamos los paneles invisibles
        panelf1.setVisible(false);
        panelf2.setVisible(false);
        
        
        //tabla trabajadores creamos la tabla
        String ids [] = {"nombre del trabajador"};
        modeloTrabajador.setColumnIdentifiers(ids);
        tablaTrabajadores.setModel(modeloTrabajador);
        
        //tabla trabajadores creamos la tabla de entradas 
        String ids2 [] = {"No.", "Valor", "Razon"};
        modeloEntradas.setColumnIdentifiers(ids2);
        tablaEtnradas.setModel(modeloEntradas);
        
        //obetenemos el vector que tiene los productos
        if (gTrabajadores.getTrabajador() != null) {
            tTrabajador = gTrabajadores.getTrabajador();
        }
        
        //cargamos el invetario en la tabla
        cargarInvetrioTabla();
        
        //llamamos a un bucle infinito
        iniciarBucleEnHilo();
        configurarCamposTrabajador();
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
private void setupTextFieldTrabajador(JTextField textField, String placeholder) {
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
private void setupTextAreaTrabajador(JTextArea textArea, String placeholder) {
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

// Método para configurar todos los campos de trabajador con placeholders
private void configurarCamposTrabajador() {
    setupTextFieldTrabajador(txtNombreDelTrabajadorAgregar, "Ingrese nombre del trabajador");
    setupTextFieldTrabajador(txtNombreDelTrabajadorModificar, "Ingrese ombre del trabajador");
    setupTextAreaTrabajador(txtPequeñaDescripcionAgregar, "Ingrese una pequeña descripción");
    setupTextAreaTrabajador(txtPequeñaDescripcionModificar, "Ingrese una pequeña descripción");
    setupTextFieldTrabajador(txtRazon, "Ingrese la razón");
    setupTextFieldTrabajador(txtSalarioSemanalAgregar, "Ingrese salario semanal");
    setupTextFieldTrabajador(txtSalarioSemanalModificado, "Ingrese salario semanal");
    setupTextFieldTrabajador(txtValorDeLaEntrada, "Ingrese el valor de la entrada");
    setupTextAreaTrabajador(txtDescripcionNuevaEntrada, "Ingrese la descripción nueva entrada"); // Placeholder para la descripción nueva entrada
}

// Método para limpiar y restablecer los placeholders de los campos de trabajador
public void limpiarCamposTrabajador() {
    txtNombreDelTrabajadorAgregar.setText("Ingrese nombre del trabajador");
    txtNombreDelTrabajadorAgregar.setForeground(Color.GRAY);

    txtNombreDelTrabajadorModificar.setText("Ingrese nombre del trabajador");
    txtNombreDelTrabajadorModificar.setForeground(Color.GRAY);

    txtPequeñaDescripcionAgregar.setText("Ingrese una pequeña descripción");
    txtPequeñaDescripcionAgregar.setForeground(Color.GRAY);

    txtPequeñaDescripcionModificar.setText("Ingrese una pequeña descripción");
    txtPequeñaDescripcionModificar.setForeground(Color.GRAY);

    txtRazon.setText("Ingrese la razón");
    txtRazon.setForeground(Color.GRAY);

    txtSalarioSemanalAgregar.setText("Ingrese salario semanal");
    txtSalarioSemanalAgregar.setForeground(Color.GRAY);

    txtSalarioSemanalModificado.setText("Ingrese salario semanal");
    txtSalarioSemanalModificado.setForeground(Color.GRAY);

    txtValorDeLaEntrada.setText("Ingrese el valor de la entrada");
    txtValorDeLaEntrada.setForeground(Color.GRAY);

    txtDescripcionNuevaEntrada.setText("Ingrese la descripción nueva entrada"); // Limpiar el campo de descripción
    txtDescripcionNuevaEntrada.setForeground(Color.GRAY);
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


    //esta funcion actualizara los elemetnos existentes en la tabla
    private void cargarInvetrioTabla(){
        
        //primero vaciaremos la tabla totalmente
        modeloTrabajador.setRowCount(0);
        //vaciamos primero el vector
        indicesVectores.clear();
        
        //contador que guardara los indices reales
        int contador = 0;
        
        //con este for llenaremos la tabla con los elemetos del vector
        for (FichaTrabajador trad : tTrabajador) {
            
            if (trad.getSemanasDeTrabajo() >= 0) {
                modeloTrabajador.addRow(new Object[]{trad.getNombre()});
                
                indicesVectores.add(contador);
            }
            contador++;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTrabajadores = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelf1 = new javax.swing.JPanel();
        panelBoton1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPequeñaDescripcionAgregar = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtNombreDelTrabajadorAgregar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtSalarioSemanalAgregar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        labelSemanas = new javax.swing.JLabel();
        panelf2 = new javax.swing.JPanel();
        panelBoton2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreDelTrabajadorModificar = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtPequeñaDescripcionModificar = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtSalarioSemanalModificado = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        labelNombre = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcionNuevaEntrada = new javax.swing.JTextArea();
        panelBotonModificar = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelSemanaInfo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelSalirioSemanal = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaEtnradas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        labelSalioTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtValorDeLaEntrada = new javax.swing.JTextField();
        comboOperacionEntrada = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtRazon = new javax.swing.JTextField();
        PanelBoton3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        botonguardarDatos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        buscarPiloto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(128, 179, 220));

        tablaTrabajadores.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaTrabajadores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaTrabajadores);

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("         Agregar Trabajador");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        panelf1.setBackground(new java.awt.Color(128, 179, 220));

        panelBoton1.setBackground(new java.awt.Color(85, 111, 169));
        panelBoton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBoton1MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("AGREGAR TRABAJADOR");

        javax.swing.GroupLayout panelBoton1Layout = new javax.swing.GroupLayout(panelBoton1);
        panelBoton1.setLayout(panelBoton1Layout);
        panelBoton1Layout.setHorizontalGroup(
            panelBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBoton1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBoton1Layout.setVerticalGroup(
            panelBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoton1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(85, 111, 169));
        jPanel10.setPreferredSize(new java.awt.Dimension(79, 90));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtPequeñaDescripcionAgregar.setColumns(20);
        txtPequeñaDescripcionAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtPequeñaDescripcionAgregar.setRows(5);
        jScrollPane2.setViewportView(txtPequeñaDescripcionAgregar);

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel1.setText("Nombre Del Trabajador");

        txtNombreDelTrabajadorAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setText("Pequeña Descripción");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("Semanas de trabajo:");

        txtSalarioSemanalAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("Salario Semanal");

        labelSemanas.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        labelSemanas.setText("0");

        javax.swing.GroupLayout panelf1Layout = new javax.swing.GroupLayout(panelf1);
        panelf1.setLayout(panelf1Layout);
        panelf1Layout.setHorizontalGroup(
            panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelf1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelf1Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelf1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSalarioSemanalAgregar))
                                    .addComponent(txtNombreDelTrabajadorAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                        .addGap(0, 125, Short.MAX_VALUE)
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSemanas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        panelf1Layout.setVerticalGroup(
            panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(labelSemanas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreDelTrabajadorAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSalarioSemanalAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        panelf2.setBackground(new java.awt.Color(128, 179, 220));

        panelBoton2.setBackground(new java.awt.Color(85, 111, 169));
        panelBoton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBoton2MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("MODIFICAR INFORMACION");

        javax.swing.GroupLayout panelBoton2Layout = new javax.swing.GroupLayout(panelBoton2);
        panelBoton2.setLayout(panelBoton2Layout);
        panelBoton2Layout.setHorizontalGroup(
            panelBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBoton2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );
        panelBoton2Layout.setVerticalGroup(
            panelBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBoton2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("Nombre Del Trabajador");

        txtNombreDelTrabajadorModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtPequeñaDescripcionModificar.setColumns(20);
        txtPequeñaDescripcionModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtPequeñaDescripcionModificar.setRows(5);
        jScrollPane4.setViewportView(txtPequeñaDescripcionModificar);

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("Salario Semanal");

        txtSalarioSemanalModificado.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout panelf2Layout = new javax.swing.GroupLayout(panelf2);
        panelf2.setLayout(panelf2Layout);
        panelf2Layout.setHorizontalGroup(
            panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombreDelTrabajadorModificar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelf2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelf2Layout.createSequentialGroup()
                        .addGap(0, 76, Short.MAX_VALUE)
                        .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelf2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSalarioSemanalModificado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelBoton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelf2Layout.setVerticalGroup(
            panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreDelTrabajadorModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSalarioSemanalModificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBoton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jPanel9.setBackground(new java.awt.Color(128, 179, 220));

        jPanel13.setBackground(new java.awt.Color(85, 111, 169));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelNombre.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelNombre.setText("Nombre");

        txtDescripcionNuevaEntrada.setEditable(false);
        txtDescripcionNuevaEntrada.setColumns(20);
        txtDescripcionNuevaEntrada.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtDescripcionNuevaEntrada.setRows(5);
        jScrollPane3.setViewportView(txtDescripcionNuevaEntrada);

        panelBotonModificar.setBackground(new java.awt.Color(85, 111, 169));
        panelBotonModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBotonModificarMouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("MODIFICAR TRABAJADOR");

        javax.swing.GroupLayout panelBotonModificarLayout = new javax.swing.GroupLayout(panelBotonModificar);
        panelBotonModificar.setLayout(panelBotonModificarLayout);
        panelBotonModificarLayout.setHorizontalGroup(
            panelBotonModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonModificarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );
        panelBotonModificarLayout.setVerticalGroup(
            panelBotonModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonModificarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("Semanas De trabajo");

        labelSemanaInfo.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelSemanaInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBotonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(panelBotonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(labelNombre)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelSemanaInfo))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setText("Salario Semanal:  Q");

        labelSalirioSemanal.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        labelSalirioSemanal.setText("00.00");

        tablaEtnradas.setBackground(new java.awt.Color(128, 179, 220));
        tablaEtnradas.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        tablaEtnradas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tablaEtnradas);

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setText("Valor Total: Q");

        labelSalioTotal.setText("00.00");

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setText("Grabar Nueva Entrada");

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("Valor De La Entrada");

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel11.setText("Operación");

        txtValorDeLaEntrada.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        comboOperacionEntrada.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comboOperacionEntrada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-" }));

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("Razón");

        txtRazon.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        PanelBoton3.setBackground(new java.awt.Color(85, 111, 169));
        PanelBoton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelBoton3MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("AGREGAR NUEVA ENTRADA");

        javax.swing.GroupLayout PanelBoton3Layout = new javax.swing.GroupLayout(PanelBoton3);
        PanelBoton3.setLayout(PanelBoton3Layout);
        PanelBoton3Layout.setHorizontalGroup(
            PanelBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBoton3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        PanelBoton3Layout.setVerticalGroup(
            PanelBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBoton3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSalioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSalirioSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(comboOperacionEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(72, 72, 72))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtValorDeLaEntrada, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRazon, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(30, 30, 30))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelBoton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelSalirioSemanal)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtValorDeLaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(comboOperacionEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtRazon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(labelSalioTotal))
                    .addComponent(PanelBoton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" PLANILLA");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(32, 67, 99));
        jPanel4.setPreferredSize(new java.awt.Dimension(194, 34));

        txtMenu.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(85, 111, 169));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("INICIAR NUEVA SEMANA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addContainerGap())
        );

        botonguardarDatos.setBackground(new java.awt.Color(255, 255, 204));
        botonguardarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonguardarDatosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonguardarDatosLayout = new javax.swing.GroupLayout(botonguardarDatos);
        botonguardarDatos.setLayout(botonguardarDatosLayout);
        botonguardarDatosLayout.setHorizontalGroup(
            botonguardarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        botonguardarDatosLayout.setVerticalGroup(
            botonguardarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(85, 111, 169));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("       ELIMINAR AL TRABAJADOR");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        buscarPiloto.setBackground(new java.awt.Color(0, 153, 153));
        buscarPiloto.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        buscarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        buscarPiloto.setText("ACTIVAR TRABAJADORES ELIMINADOS");
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonguardarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panelf1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(botonguardarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelf1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(84, 84, 84))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        
        //boton 1 que ayuda a hacer visibl el panel de agregar trabajador
        if (panelf1.isVisible() == false) {
            panelf1.setVisible(true);
        }else{
            panelf1.setVisible(false);
        }
        
        panelf2.setVisible(false);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void panelBoton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBoton1MouseClicked
        // TODO add your handling code here:
        
        //funcion que nos ayuda a agregar un nuevo trabajador
        try{
            //primero obtenemos los datos ingresador por el usuario 
        
        String newnombre = txtNombreDelTrabajadorAgregar.getText();
        float newsalario = Float.parseFloat(txtSalarioSemanalAgregar.getText());
        int newsemanas = 0;
        String newDescripcion = txtPequeñaDescripcionAgregar.getText();
        
        if (!newnombre.isEmpty() && !newDescripcion.isEmpty() && newsalario > 0) {
                   //crearemos el objeto trabajador provisional
        FichaTrabajador newTrabajador = new FichaTrabajador(newnombre, newDescripcion, newsalario, newsemanas);
        
        //agregamos este trabajador al vector de gestion trabajador
        gTrabajadores.agregarTrabajador(newTrabajador);
        
        //actualizamos la tabla
        cargarInvetrioTabla();
        
        //ponemos todos los texto en blanco
        txtNombreDelTrabajadorAgregar.setText("");
        txtSalarioSemanalAgregar.setText("");
        txtPequeñaDescripcionAgregar.setText("");
        
        //ocultamos los paneles para evitar problemas
        panelf1.setVisible(false);
        panelf2.setVisible(false);
        
        if (indiceActualTabla > -1) {
            //colocamos la tabala seleccionando el indice actual
            tablaTrabajadores.setRowSelectionInterval(indiceActualTabla, indiceActualTabla);
        }
        
        //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Trabajador agregado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            //funcion provisional para guardas datos en el excel
        gTrabajadores.guardarTrabajadoresExcel();
         
                }else{
                  //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Completar los campos de nombre, descripcion y salario valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
              
                }
        
        }
        catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
        
    }//GEN-LAST:event_panelBoton1MouseClicked

    private void panelBotonModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBotonModificarMouseClicked
        // TODO add your handling code here:
        
        //esta funcion funciona para mostrar el panel de modificar
        if (panelf2.isVisible() == false) {
            panelf2.setVisible(true);
        }else{
            panelf2.setVisible(false);
        }
        
        panelf1.setVisible(false);
    }//GEN-LAST:event_panelBotonModificarMouseClicked

    private void panelBoton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBoton2MouseClicked
        // TODO add your handling code here:
        
        //funcion para actualizar los cambio del trabajador
        
        try{
            //preguntamos si el indice seleccionado de la tabla es legal
            if (indiceActual > -1) {
                
                //primeor obtenemos los valores del trabajador 
                String newnombre = txtNombreDelTrabajadorModificar.getText();
                String newDescripcion = txtPequeñaDescripcionModificar.getText();
                float newSalario = Float.parseFloat(txtSalarioSemanalModificado.getText());
                float salarioAntiguo = gTrabajadores.getSalariTotalEnNumeros(indiceActual);
                
                if (!newnombre.isEmpty() && !newDescripcion.isEmpty() && newSalario > 0 && newSalario > salarioAntiguo) {

                //indiceActual

                //crearemos el objeto trabajador provisional
                FichaTrabajador newTrabajador = tTrabajador.get(indiceActual);
                
                //asignamos este objetos los nuevos valores
                newTrabajador.setNombre(newnombre);
                newTrabajador.setDescripcion(newDescripcion);
                newTrabajador.setSalarioSemanal(newSalario);
                
                //llamamos a la funcion de modificar un valor
                gTrabajadores.modificarTRabajdor(indiceActual, newTrabajador);
                
                //ocultamos los paneles para evitar problemas
                panelf1.setVisible(false);
                panelf2.setVisible(false);
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(indiceActual).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(indiceActual).getSemanasDeTrabajo());
                txtDescripcionNuevaEntrada.setText(tTrabajador.get(indiceActual).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(indiceActual).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(indiceActual));
                
                //mostramos los mismos datos de modificado 
                txtNombreDelTrabajadorModificar.setText(tTrabajador.get(indiceActual).getNombre());
                txtPequeñaDescripcionModificar.setText(tTrabajador.get(indiceActual).getDescripcion());
                txtSalarioSemanalModificado.setText("" + tTrabajador.get(indiceActual).getSalarioSemanal());
                
                //actualizamos la tabla
                cargarInvetrioTabla();
                
                //acemos que la tabla siga seleccionando el indice actual
                tablaTrabajadores.setRowSelectionInterval(indiceActualTabla, indiceActualTabla);
                
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Trabajador modificado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
            //funcion provisional para guardas datos en el excel
            gTrabajadores.guardarTrabajadoresExcel();
            
                }else{
                  //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Completar los campos de nombre, descripcion y salario valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
              
                }
                }else{
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa un trabajador de la Tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
        }
        catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
        
    }//GEN-LAST:event_panelBoton2MouseClicked

    private void PanelBoton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelBoton3MouseClicked
        // TODO add your handling code here:
        
        //boton para grabar una nueva entrada
        try{
            //preguntamos si el indice seleccionado de la tabla es legal
            if (indiceActual > -1) {
                
                                //primero obtenemos los datos de la entrada
                String newEntrada = txtRazon.getText();
                
                if (!newEntrada.isEmpty()) {
                float newValorEntrada = Float.parseFloat(txtValorDeLaEntrada.getText()); 
                        
                String operacion = comboOperacionEntrada.getSelectedItem().toString();
                
                if (newValorEntrada > 0 && (Float.parseFloat(gTrabajadores.getSalariTotal(indiceActual)) >= newValorEntrada && operacion.equals("-") && newValorEntrada >= 1 || operacion.equals("+") && newValorEntrada >= 1)) {
                    
                //hacemo que el valor no pueda quedar negativo 
                if ( Float.parseFloat(gTrabajadores.getSalariTotal(indiceActual)) >= newValorEntrada && operacion.equals("-") && newValorEntrada >= 1) {
                    //llamamos a la funcion de agregar entrada
                    gTrabajadores.setEntrada(indiceActual, newEntrada, newValorEntrada, operacion);
                }else if(operacion.equals("+") && newValorEntrada >= 1){
                    //llamamos a la funcion de agregar entrada
                    gTrabajadores.setEntrada(indiceActual, newEntrada, newValorEntrada, operacion);
                }
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(indiceActual).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(indiceActual).getSemanasDeTrabajo());
                txtDescripcionNuevaEntrada.setText(tTrabajador.get(indiceActual).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(indiceActual).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(indiceActual));
                
                //actualizamos la tabla de entradas
                modificarTabladeEtradas(indiceActual);
                //actualizamos la tabla
                cargarInvetrioTabla();
                
                //colocamos la tabala seleccionando el indice actual
                tablaTrabajadores.setRowSelectionInterval(indiceActualTabla, indiceActualTabla);
                
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Entrada creada exitosamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            //funcion provisional para guardas datos en el excel
             gTrabajadores.guardarTrabajadoresExcel();
            
                }else{
                    //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "el salario del trabajador es inferior a la entrada, o la cantidad de la entrada no es valida", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
                }
                  
                }else{
                    //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "por favor completar la razon de la nueva entrada", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
                }
                
                }else{
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa un Trabajador de la Tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
        }
        catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
        
        
    }//GEN-LAST:event_PanelBoton3MouseClicked

    private void botonguardarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonguardarDatosMouseClicked
        // TODO add your handling code here:
        
        //funcion provisional para guardas datos en el excel
        gTrabajadores.guardarTrabajadoresExcel();
        System.out.println("se ha guardado correctamente");
    }//GEN-LAST:event_botonguardarDatosMouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        
        //esta funcion nos ayudara a iniciar una nueva semana de trabajo
        // Mostrar popup de advertencia
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

                // Si el usuario selecciona "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    gTrabajadores.nuevaSemana();
                    
                    
                    if (indiceActual > -1) {
                        //ocultamos los paneles para evitar problemas
                panelf1.setVisible(false);
                panelf2.setVisible(false);
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(indiceActual).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(indiceActual).getSemanasDeTrabajo());
                txtDescripcionNuevaEntrada.setText(tTrabajador.get(indiceActual).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(indiceActual).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(indiceActual));
                
                //mostramos los mismos datos de modificado 
                txtNombreDelTrabajadorModificar.setText(tTrabajador.get(indiceActual).getNombre());
                txtPequeñaDescripcionModificar.setText(tTrabajador.get(indiceActual).getDescripcion());
                txtSalarioSemanalModificado.setText("" + tTrabajador.get(indiceActual).getSalarioSemanal());
                
            //colocamos la tabala seleccionando el indice actual
            tablaTrabajadores.setRowSelectionInterval(indiceActualTabla, indiceActualTabla);
        }
                    
                    // vaciaremos la tabla totalmente
                modeloEntradas.setRowCount(0);
                    
                    gTrabajadores.guardarTrabajadoresExcel();
                }
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        
        //funcion para eliminar un trabajador
            
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

                // Si el usuario selecciona "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    //preguntamos si el indice seleccionado de la tabla es legal
            if (indiceActual > -1) {
                
                tTrabajador.get(indiceActual).setSemanasDeTrabajo(-1);
                
                //mostramos los datos de la tabla 
                labelNombre.setText("");
                labelSemanaInfo.setText("");
                txtDescripcionNuevaEntrada.setText("");
                
                labelSalirioSemanal.setText("");
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText("");
                
                //mostramos los mismos datos de modificado 
                txtNombreDelTrabajadorModificar.setText("");
                txtPequeñaDescripcionModificar.setText("");
                txtSalarioSemanalModificado.setText("");
                
                //actualizamos la tabla
                cargarInvetrioTabla();
                
                // vaciaremos la tabla totalmente
                modeloEntradas.setRowCount(0);
                
                //igualamos el indice actual a -1
                indiceActual = -1;
                
                
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Trabajador eliminado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
                //funcion provisional para guardas datos en el excel
                 gTrabajadores.guardarTrabajadoresExcel();

                
                }else{
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa un Trabajador de la Tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
                }
    }//GEN-LAST:event_jPanel6MouseClicked

    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
        String username = this.currentUser; // Suponiendo que currentUser contiene el nombre de usuario
        String role = this.userRole;        // Suponiendo que userRole contiene el rol
        LOGINPINEED loginFrame = this.loginFrame; // Suponiendo que loginFrame ya está disponible

        FrameHistorialTrabajadores abrir = new FrameHistorialTrabajadores(currentUser, userRole, loginFrame);
        abrir.setVisible(true);
    }//GEN-LAST:event_buscarPilotoActionPerformed

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
            java.util.logging.Logger.getLogger(FramePlanillaSemanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePlanillaSemanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePlanillaSemanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePlanillaSemanal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                                       
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FramePlanillaSemanal(username, role, loginFrame).setVisible(true);
       
            }
        });
    }
    
    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice de seleccion de la tabla
            int indiceSeleccionado = tablaTrabajadores.getSelectedRow();
            
             if (tablaTrabajadores.getSelectedRow() > -1 ) {
                 indiceSeleccionado = indicesVectores.get(tablaTrabajadores.getSelectedRow());
             }
            
            //preguntamos si el indice genera y el seleccionado son distintos
            if (indiceSeleccionado != indiceActual && tablaTrabajadores.getSelectedRow() > -1) {
                
                //igualamos el indice general el indice seleccionado
                indiceActual = indiceSeleccionado;
                indiceActualTabla = tablaTrabajadores.getSelectedRow();
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(indiceSeleccionado).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(indiceSeleccionado).getSemanasDeTrabajo());
                txtDescripcionNuevaEntrada.setText(tTrabajador.get(indiceSeleccionado).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(indiceSeleccionado).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(indiceSeleccionado));
                
                //mostramos los mismos datos de modificado 
                txtNombreDelTrabajadorModificar.setText(tTrabajador.get(indiceSeleccionado).getNombre());
                txtPequeñaDescripcionModificar.setText(tTrabajador.get(indiceSeleccionado).getDescripcion());
                txtSalarioSemanalModificado.setText("" + tTrabajador.get(indiceSeleccionado).getSalarioSemanal());
                
                //actualizamos la tabla de entradas
                modificarTabladeEtradas(indicesVectores.get(tablaTrabajadores.getSelectedRow()));
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
    
    
    //esta funcion nos ayudara a modificar la tabla de entradas de cada trabajador
    private void modificarTabladeEtradas(int indiceEntrada){
      
        //primero vaciaremos la tabla totalmente
        modeloEntradas.setRowCount(0);
        
        //etos numeros nos ayudaras a darle numeros a las entradas y al indice del vector entradad 
        int numeroEntrada = 1;
        int indicedelValorEntrada = 0;
        
        //obtenemos los vectores de los trabajadores
        Vector<String> newentrada = tTrabajador.get(indiceEntrada).getEntrada();
        Vector<Float> newValorEntrada = tTrabajador.get(indiceEntrada).getValorEntrada();
        
        //con este for llenaremos la tabla con los elemetos del vector
        for (String razon : newentrada) {
            
            modeloEntradas.addRow(new Object[]{numeroEntrada, newValorEntrada.get(indicedelValorEntrada) , razon});
            
            numeroEntrada ++;
            indicedelValorEntrada ++;
        }
        
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBoton3;
    private javax.swing.JPanel botonguardarDatos;
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JComboBox<String> comboOperacionEntrada;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelSalioTotal;
    private javax.swing.JLabel labelSalirioSemanal;
    private javax.swing.JLabel labelSemanaInfo;
    private javax.swing.JLabel labelSemanas;
    private javax.swing.JPanel panelBoton1;
    private javax.swing.JPanel panelBoton2;
    private javax.swing.JPanel panelBotonModificar;
    private javax.swing.JPanel panelf1;
    private javax.swing.JPanel panelf2;
    private javax.swing.JTable tablaEtnradas;
    private javax.swing.JTable tablaTrabajadores;
    private javax.swing.JTextArea txtDescripcionNuevaEntrada;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JTextField txtNombreDelTrabajadorAgregar;
    private javax.swing.JTextField txtNombreDelTrabajadorModificar;
    private javax.swing.JTextArea txtPequeñaDescripcionAgregar;
    private javax.swing.JTextArea txtPequeñaDescripcionModificar;
    private javax.swing.JTextField txtRazon;
    private javax.swing.JTextField txtSalarioSemanalAgregar;
    private javax.swing.JTextField txtSalarioSemanalModificado;
    private javax.swing.JTextField txtValorDeLaEntrada;
    // End of variables declaration//GEN-END:variables
}
