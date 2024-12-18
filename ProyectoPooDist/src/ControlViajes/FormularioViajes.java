package ControlViajes;


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
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlVentas.FrameVentaDiaria;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import Login.GESTIONLOGIN;
import Login.Login;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.mail.util.ByteArrayDataSource;


//definimos las librerias para el vector
import java.util.Vector;

//librerias para las fechas
import java.util.Date;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ControlPedidos.*;

import java.util.Calendar;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

//libreria para hacer poups
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author USUARIO
 */
public class FormularioViajes extends javax.swing.JFrame {

    //definimos el la gestion de pilotos, fechas, caminos e inventario
    private GestionCalendario gescalendario;
    private GestionPedido gespedidos;
    private gestionProductos gesproductos;
    private GESTIONPILOTOS gespilotos;
    private GESTIONCAMIONES gescamiones;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
      private TreeMap<Integer, Boolean> numerosViaje = new TreeMap<>(); // número -> está en uso
    private int ultimoNumeroAsignado = 0;
    //crearemos los modelos de las tablas de productos a
    DefaultTableModel modeloProductosA = new DefaultTableModel();
    DefaultTableModel modeloProductosB = new DefaultTableModel();
    
    //creamos los vectores necesario
    Vector<Producto> productosTablaNew;
    Vector<FechaCalendario> FechaTablaNew;
    
    //creamos vectores de dias de cargas y descarga
    Vector<Date> fechasCarga = new Vector<>();
    Vector<Date> fechasDescarga = new Vector<>();
    
    //CREAMOS EL objeto para marcar los dias
    MarcadorDeFechas marcador = new MarcadorDeFechas(fechasCarga, fechasDescarga);    
        
    //indice que nos ayudara en el calendario
    int indice;
    
    //nos ayudara a mostrar los datos a modificar
    int indiceActual;
    
    //nos ayudara a repintar la fechas del calendario
    Date fechaActual;
    
    
    //nos ayudara para ver el indice de elementos iguales
    int indiceFechasIgualesActuales;
    
    //estas variables nos ayudaran a pintar las fechas
    Color pastelGreen = new Color(144, 238, 144); // Verde pastel
    Color pastelRed = new Color(255, 182, 193);   // Rojo pastel
    Color pastelBlue = new Color(173, 216, 255);  // Azul pastel
    
    Color defaultButtonGray = new Color(180,180,180);


    /**
     * Creates new form FormularioViajes
     */
public FormularioViajes(String username, String role, LOGINPINEED loginFrame) {
    initComponents();
    setResizable(false); // Desactivar el cambio de tamaño
    
   // Ocultar componentes si el rol del usuario es PILOTO
    if (role.equalsIgnoreCase("PILOTO")) {
        ocultarComponentesParaPiloto();
    }

    //iniciamos el indice a 0;
    indice = 0;
    
    //ocultamos el boton de modificar un viaje
    txtModificarViajes.setVisible(false);
        txtEliminarViaje.setVisible(false);
    txtModificarViajes.setVisible(false);
    jLabel16.setVisible(false);

        
    //ocultamos el radio button de finalizar un pedido
    radioFinalizarViaje.setVisible(false);
    
    //iniciamos el indiceActual a -1
    indiceActual = -1;
    
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
    
    //inicamos los vectores
    FechaTablaNew = gescalendario.getFechasDeCalendario(); 
    System.out.println("se ha cargado ");
    
    //inicializamos las tablas con los elementos del inventario
    if (gesproductos.getProductos() != null) {
        productosTablaNew = gesproductos.getProductos();
        
        for (Producto prod : productosTablaNew) {
            modeloProductosA.addRow(new Object[]{prod.getNombre(), "0"});
            modeloProductosB.addRow(new Object[]{prod.getNombre(), "0"});
        }
    }
    
    //actualizamos los combo box de pilotos y camiones
    if (gespilotos.getPilotos() != null) {
        Vector<Piloto> pilotonew = gespilotos.getPilotos();
        
        //vector para agregar los pilotos al comobobox
        for (int i = 0; i < pilotonew.size(); i++) {
            comboPilotosB.addItem(pilotonew.get(i).getNombrePiloto());
            comboPilotosA.addItem(pilotonew.get(i).getNombrePiloto());
        }
    }
    
    if (gescamiones.getCamiones() != null) {
        Vector<Camiones> camionesnew = gescamiones.getCamiones();
        
        //vector para agregar los pilotos al comobobox
        for (int i = 0; i < camionesnew.size(); i++) {
            comboCamionesB.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
            comoboCamionesA.addItem(camionesnew.get(i).getMarca() + " " +camionesnew.get(i).getModelo());
        }
    }
    
    //hacemos que el calendario se cargue
    CalendarioGeneral.getDayChooser().addDateEvaluator(marcador);
    ActualizarCalendario();
    
    //llenamos la lista de fechas
    ActualizarComboListaPedidos();
            
     numerosViaje.clear();
        ultimoNumeroAsignado = 0;
        
         try {
        // Cargar estado guardado
        Properties props = new Properties();
        File configFile = new File("viajes_config.properties");
        
        if (configFile.exists()) {
            try (FileInputStream in = new FileInputStream(configFile)) {
                props.load(in);
                ultimoNumeroAsignado = Integer.parseInt(
                    props.getProperty("ultimoNumeroAsignado", "0"));
                
                String numerosStr = props.getProperty("numerosViaje", "");
                if (!numerosStr.isEmpty()) {
                    String[] entries = numerosStr.split(";");
                    for (String entry : entries) {
                        String[] parts = entry.split(",");
                        if (parts.length == 2) {
                            numerosViaje.put(
                                Integer.parseInt(parts[0]),
                                Boolean.parseBoolean(parts[1])
                            );
                        }
                    }
                }
            }
        }
        
        // Registrar números de viajes existentes
        for (int i = 0; i < gescalendario.getFechasDeCalendario().size(); i++) {
            FechaCalendario fecha = gescalendario.getFechasDeCalendario().get(i);
            int numeroViaje = i + 1;
            numerosViaje.put(numeroViaje, true);
            if (numeroViaje > ultimoNumeroAsignado) {
                ultimoNumeroAsignado = numeroViaje;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    iniciarBucleEnHilo(); 
    configurarCamposConPlaceholders();
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
    
    
    if (comboPedidosLista.getSelectedIndex() > -1) {
        //igualamos las fechas
                txtFechaDeCargaViaje.setDate(FechaTablaNew.get(0).getFechaC());    
                txtFechaDeDescargaViaje.setDate(FechaTablaNew.get(0).getFechaD());
    }
}
    

private void ocultarComponentesParaPiloto() {
    // Ocultar paneles principales y campos de fecha
    jPanel6.setVisible(false);
    jLabel2.setVisible(false);
    txtFechaDeCargaAgregar.setVisible(false);
    jLabel3.setVisible(false);
    txtFechaDeDescargaAgregar.setVisible(false);
    jLabel4.setVisible(false);
    comboPilotosB.setVisible(false);
    jLabel5.setVisible(false);
    
    // Ocultar botones y otros paneles
    txtModificarViajes.setVisible(false);
    txtEliminarViaje.setVisible(false);
    comboCamionesB.setVisible(false);
    jLabel11.setVisible(false);
    comboTViajeB.setVisible(false);
    jLabel10.setVisible(false);
    
    // Paneles adicionales
    txtAgregarFecha.setVisible(false);
    jPanel7.setVisible(false);
    jPanel9.setVisible(false);
    jPanel10.setVisible(false);
    jPanel8.setVisible(false);
    jPanel11.setVisible(false);
    
    // Ocultar tabla y su contenedor (JScrollPane)
    tablaProductosB.setVisible(false);
    Container parentContainer = tablaProductosB.getParent();
    while (parentContainer != null) {
        if (parentContainer instanceof JScrollPane) {
            parentContainer.setVisible(false);
            break;
        }
        parentContainer = parentContainer.getParent();
    }
}
// Método para configurar el placeholder en JDateChooser
private void setupDateChooser(JDateChooser dateChooser, String placeholder) {
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
private void configurarCamposConPlaceholders() {
    setupDateChooser(txtFechaDeCargaAgregar, "dd/MM/yyyy");
    setupDateChooser(txtFechaDeCargaViaje, "dd/MM/yyyy");
    setupDateChooser(txtFechaDeDescargaAgregar, "dd/MM/yyyy");
    setupDateChooser(txtFechaDeDescargaViaje, "dd/MM/yyyy");
}

// Método auxiliar para resetear un JDateChooser individual
private void resetDateChooser(JDateChooser dateChooser) {
    dateChooser.setDate(null);
    JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
    editor.setText("dd/MM/yyyy");
    editor.setForeground(Color.GRAY);
}

// Método mejorado para limpiar y restablecer los placeholders de los JDateChooser
public void limpiarDateChoosers() {
    resetDateChooser(txtFechaDeCargaAgregar);
    resetDateChooser(txtFechaDeCargaViaje);
    resetDateChooser(txtFechaDeDescargaAgregar);
    resetDateChooser(txtFechaDeDescargaViaje);
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

    //creamos la funcion para señalar los dias de viajes en el calendario 
    private void ActualizarCalendario(){
       
        for (int i = 0; i < FechaTablaNew.size(); i++) {
            Date fC = FechaTablaNew.get(i).getFechaC();
            Date fD = FechaTablaNew.get(i).getFechaD();
            
            fechasCarga.add(fC);
            fechasDescarga.add(fD);
                 
            colorearFecha(fC, pastelGreen);
            colorearFecha(fD, pastelRed );
        }
        
        CalendarioGeneral.getDayChooser().repaint();
    };
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void colorearFecha(Date fecha, Color color) {
        aplicarColorAFecha(fecha, color);
    }
    
    private void aplicarColorAFecha(Date fecha, Color color) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        Calendar calActual = CalendarioGeneral.getCalendar();
        if (calActual.get(Calendar.MONTH) == month && calActual.get(Calendar.YEAR) == year) {
            Calendar tempCal = (Calendar) calActual.clone();
            tempCal.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);

            // Corregimos el cálculo del índice
            int index = (day+6) + (firstDayOfWeek - Calendar.SUNDAY);

            if (index >= 0 && index < CalendarioGeneral.getDayChooser().getDayPanel().getComponents().length) {
                JComponent component = (JComponent) CalendarioGeneral.getDayChooser().getDayPanel().getComponents()[index];
                component.setBackground(color);
                component.setOpaque(true);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public void ActualizarComboListaPedidos(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        comboPedidosLista.removeAllItems();
        
        
        for (int i = 0; i < FechaTablaNew.size(); i++) {
            Date fC = FechaTablaNew.get(i).getFechaC();
            Date fD = FechaTablaNew.get(i).getFechaD();
            int conteo = i + 1;
            comboPedidosLista.addItem( conteo + ". " + formato.format(fC) + " - " + formato.format(fD) );
        }
        
    };
    
    //esta funcion sirve para actualizar las cantidades de la tabla B a 0, despues de darle al boton agregar
    public void ActualizarTablaB(){
        modeloProductosB.setRowCount(0);
        
        for (Producto prod : productosTablaNew) {
            modeloProductosB.addRow(new Object[]{prod.getNombre(), "0"});
         }
    };
    
    //funcion para poder actualizar la tabla de productos A
    private void ActualizarTablaA(){
        
        if (indiceActual > -1) {
            //primero vaciaremos la tabla totalmente
        modeloProductosA.setRowCount(0);
        
        //creamos un for para crear la tabla
        for (int i = 0; i < productosTablaNew.size(); i++) {
            
            //creamos un int para el numero de cantidades del pedido 
            int numeroDeCantidades = 0;
            
            //un for para para recorrer el vector de indices y modificar las existencias
            for (int j = 0; j < FechaTablaNew.get(indiceActual).getIndiceProductos().size(); j++) {
                if (i == FechaTablaNew.get(indiceActual).getIndiceProductos().get(j)) {
                    numeroDeCantidades = FechaTablaNew.get(indiceActual).getIndiceCantidad().get(j);
                }
            }
                        
            //actualizamos la tabla y sus cantidades
            modeloProductosA.addRow(new Object[]{productosTablaNew.get(i).getNombre(), Integer.toString(numeroDeCantidades)});            
        }
        
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
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboPedidosLista = new javax.swing.JComboBox<>();
        textoFechasIguales = new javax.swing.JLabel();
        comboMasDeFecha = new javax.swing.JComboBox<>();
        radioFinalizarViaje = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comoboCamionesA = new javax.swing.JComboBox<>();
        comboPilotosA = new javax.swing.JComboBox<>();
        txtFechaDeCargaViaje = new com.toedter.calendar.JDateChooser();
        txtFechaDeDescargaViaje = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductosA = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        ComboTViajeA = new javax.swing.JComboBox<>();
        txtModificarViajes = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        CalendarioGeneral = new com.toedter.calendar.JCalendar();
        jTextField19 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFechaDeCargaAgregar = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtFechaDeDescargaAgregar = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        comboPilotosB = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboCamionesB = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductosB = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        comboTViajeB = new javax.swing.JComboBox<>();
        txtAgregarFecha = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtEliminarViaje = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtMenu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1375, 752));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(128, 179, 220));

        jPanel4.setBackground(new java.awt.Color(85, 111, 169));

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detalles del Viaje");

        comboPedidosLista.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        textoFechasIguales.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        textoFechasIguales.setText("Fechas Iguales");

        comboMasDeFecha.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        radioFinalizarViaje.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        radioFinalizarViaje.setText("Finalizar Viaje");
        radioFinalizarViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFinalizarViajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(textoFechasIguales, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMasDeFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPedidosLista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(radioFinalizarViaje)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(radioFinalizarViaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPedidosLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoFechasIguales)
                    .addComponent(comboMasDeFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel9.setText("Piloto");

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel6.setText("Fecha de Carga");

        jLabel8.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel8.setText("Transporte pesado");

        jLabel7.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel7.setText("Fecha de Descarga");

        comoboCamionesA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        comboPilotosA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtFechaDeCargaViaje.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtFechaDeDescargaViaje.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

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
        jScrollPane1.setViewportView(tablaProductosA);

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("Tipo de Viaje");

        ComboTViajeA.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        ComboTViajeA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pedido para Distribuidora", "Pedido Para Cliente " }));

        txtModificarViajes.setBackground(new java.awt.Color(85, 111, 169));
        txtModificarViajes.setForeground(new java.awt.Color(255, 255, 255));
        txtModificarViajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtModificarViajesMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText(" MODIFICAR VIAJES");

        javax.swing.GroupLayout txtModificarViajesLayout = new javax.swing.GroupLayout(txtModificarViajes);
        txtModificarViajes.setLayout(txtModificarViajesLayout);
        txtModificarViajesLayout.setHorizontalGroup(
            txtModificarViajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtModificarViajesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        txtModificarViajesLayout.setVerticalGroup(
            txtModificarViajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtModificarViajesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtFechaDeCargaViaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(comboPilotosA, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ComboTViajeA, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtModificarViajes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtFechaDeDescargaViaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(comoboCamionesA, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPilotosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaDeCargaViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comoboCamionesA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaDeDescargaViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboTViajeA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtModificarViajes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(128, 179, 220));

        CalendarioGeneral.setBackground(new java.awt.Color(102, 102, 255));
        CalendarioGeneral.setDecorationBackgroundColor(new java.awt.Color(204, 204, 255));
        CalendarioGeneral.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        CalendarioGeneral.setSundayForeground(new java.awt.Color(0, 0, 0));
        CalendarioGeneral.setWeekdayForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CalendarioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CalendarioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(0, 51, 102));
        jTextField19.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText(" CALENDARIO");
        jTextField19.setBorder(null);
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(102, 102, 255));
        jPanel6.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel2.setText("Fecha de Carga");

        txtFechaDeCargaAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("Fecha de Descarga");

        txtFechaDeDescargaAgregar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel4.setText("Piloto");

        comboPilotosB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel5.setText("Transporte pesado");

        comboCamionesB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

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
        jScrollPane2.setViewportView(tablaProductosB);

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel11.setText("Tipo de Viaje");

        comboTViajeB.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        comboTViajeB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pedido para Distribuidora", "Pedido Para Cliente " }));
        comboTViajeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTViajeBActionPerformed(evt);
            }
        });

        txtAgregarFecha.setBackground(new java.awt.Color(85, 111, 169));
        txtAgregarFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAgregarFechaMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("   AGREGAR FECHA");

        javax.swing.GroupLayout txtAgregarFechaLayout = new javax.swing.GroupLayout(txtAgregarFecha);
        txtAgregarFecha.setLayout(txtAgregarFechaLayout);
        txtAgregarFechaLayout.setHorizontalGroup(
            txtAgregarFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtAgregarFechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        txtAgregarFechaLayout.setVerticalGroup(
            txtAgregarFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtAgregarFechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(0, 51, 255));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("MODO DALTONICO");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        txtEliminarViaje.setBackground(new java.awt.Color(51, 51, 255));
        txtEliminarViaje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEliminarViajeMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ELIMINAR VIAJE");

        javax.swing.GroupLayout txtEliminarViajeLayout = new javax.swing.GroupLayout(txtEliminarViaje);
        txtEliminarViaje.setLayout(txtEliminarViajeLayout);
        txtEliminarViajeLayout.setHorizontalGroup(
            txtEliminarViajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtEliminarViajeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        txtEliminarViajeLayout.setVerticalGroup(
            txtEliminarViajeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtEliminarViajeLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Pedido Finalizado");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtFechaDeCargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboPilotosB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtFechaDeDescargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(comboCamionesB, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTViajeB, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 209, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAgregarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(278, 278, 278)
                                .addComponent(txtEliminarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(txtEliminarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(7, 7, 7)
                                            .addComponent(txtFechaDeCargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(comboPilotosB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtFechaDeDescargaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(comboCamionesB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboTViajeB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(13, 13, 13))
                    .addComponent(txtAgregarFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel13.setBackground(new java.awt.Color(32, 67, 99));
        jPanel13.setPreferredSize(new java.awt.Dimension(183, 0));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
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
                estado.equals("EN VACACIONES") ? "DE VACACIONES" :
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


// Variables para rastrear los números de pedido
private static Vector<Integer> numerosUsados = new Vector<>(); // Números de pedido en uso
private static Vector<Integer> numerosEliminados = new Vector<>(); // Números disponibles para reutilización
private static Vector<Integer> pedidoPorIndice = new Vector<>(); // Rastrea el número de pedido asignado a cada viaje

// Método para inicializar el sistema de numeración
private void inicializarSistemaNumeracion() {
    numerosUsados.clear();
    numerosEliminados.clear();
    pedidoPorIndice.clear();
    
    // Reconstruir la numeración basada en los viajes existentes
    for (int i = 0; i < gescalendario.getFechasDeCalendario().size(); i++) {
        int numero = i + 1; // Empezamos desde 1
        numerosUsados.add(numero);
        pedidoPorIndice.add(numero);
    }
}

// Método para obtener el siguiente número de pedido disponible
private int obtenerSiguienteNumeroPedido() {
    int numero;
    if (!numerosEliminados.isEmpty()) {
        // Reutilizar el primer número disponible de la lista de eliminados
        numero = numerosEliminados.firstElement();
        numerosEliminados.remove(0);
    } else {
        // Asignar el siguiente número consecutivo
        numero = numerosUsados.isEmpty() ? 1 : Collections.max(numerosUsados) + 1;
    }
    numerosUsados.add(numero);
    pedidoPorIndice.add(numero);
    return numero;
}

// Método para eliminar un número de pedido
private void eliminarNumeroPedido(int indice) {
    if (indice >= 0 && indice < pedidoPorIndice.size()) {
        int numeroPedido = pedidoPorIndice.get(indice);
        numerosUsados.remove(Integer.valueOf(numeroPedido));
        if (!numerosEliminados.contains(numeroPedido)) {
            numerosEliminados.add(numeroPedido);
        }
        pedidoPorIndice.remove(indice);
        Collections.sort(numerosEliminados);
    }
}

// Método para validar fechas
private boolean validarFechas(Date fechaCarga, Date fechaDescarga) {
    if (fechaCarga == null || fechaDescarga == null) {
        return false;
    }
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date fechaActualMenosUnDia = calendar.getTime();
    
    return !fechaCarga.before(fechaActualMenosUnDia) && 
           !fechaDescarga.before(fechaActualMenosUnDia) && 
           !fechaDescarga.before(fechaCarga);
}




// Método para guardar el estado actual
private void guardarContadorViajes() {
    try {
        Properties props = new Properties();
        props.setProperty("contadorViajes", String.valueOf(gescalendario.getFechasDeCalendario().size()));
        
        // Guardar números usados y eliminados
        StringBuilder usados = new StringBuilder();
        for (Integer num : numerosUsados) {
            usados.append(num).append(",");
        }
        props.setProperty("numerosUsados", usados.toString());

        StringBuilder eliminados = new StringBuilder();
        for (Integer num : numerosEliminados) {
            eliminados.append(num).append(",");
        }
        props.setProperty("numerosEliminados", eliminados.toString());

        File configFile = new File("config.properties");
        try (FileOutputStream out = new FileOutputStream(configFile)) {
            props.store(out, "Configuración de viajes");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


// Método para obtener siguiente número de viaje
private int obtenerSiguienteNumeroViaje() {
    // Buscar el número más bajo disponible
    for (Map.Entry<Integer, Boolean> entry : numerosViaje.entrySet()) {
        if (!entry.getValue()) {
            int numero = entry.getKey();
            numerosViaje.put(numero, true);
            return numero;
        }
    }
    
    // Si no hay números disponibles, crear uno nuevo
    ultimoNumeroAsignado++;
    numerosViaje.put(ultimoNumeroAsignado, true);
    guardarEstadoNumeracion();
    return ultimoNumeroAsignado;
}

// Método para liberar un número de viaje
private void liberarNumeroViaje(int numero) {
    numerosViaje.put(numero, false);
    guardarEstadoNumeracion();
}

// Método para guardar el estado de la numeración
private void guardarEstadoNumeracion() {
    try {
        Properties props = new Properties();
        props.setProperty("ultimoNumeroAsignado", String.valueOf(ultimoNumeroAsignado));
        
        StringBuilder numeros = new StringBuilder();
        for (Map.Entry<Integer, Boolean> entry : numerosViaje.entrySet()) {
            numeros.append(entry.getKey())
                   .append(",")
                   .append(entry.getValue())
                   .append(";");
        }
        props.setProperty("numerosViaje", numeros.toString());
        
        File configFile = new File("viajes_config.properties");
        try (FileOutputStream out = new FileOutputStream(configFile)) {
            props.store(out, "Configuración de números de viaje");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Método modificado para agregar viaje
private void agregarViaje() {
    int confirmacion = JOptionPane.showConfirmDialog(null, 
        "¿Está seguro de que desea agregar este viaje?\nEl envío del correo puede tomar unos segundos.", 
        "Confirmar agregar viaje", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        Date newFechaCarga = txtFechaDeCargaAgregar.getDate();
        Date newFechaDescarga = txtFechaDeDescargaAgregar.getDate();
        
        if (!validarFechas(newFechaCarga, newFechaDescarga)) {
            JOptionPane.showMessageDialog(null, 
                "Las fechas ingresadas no son válidas", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newIndicePiloto = comboPilotosB.getSelectedIndex();
        int newIndiceCamion = comboCamionesB.getSelectedIndex();
        
        if (!isPilotoDisponible(newIndicePiloto) || !isCamionDisponible(newIndiceCamion)) {
            return;
        }

        // Obtener el nuevo número de viaje
        int numeroViaje = obtenerSiguienteNumeroViaje();
        
        boolean newcompra = comboTViajeB.getSelectedItem().equals("Pedido para Distribuidora");
        Vector<Integer> newIndiceProducto = new Vector<>();
        Vector<Integer> newIndiceCantidad = new Vector<>();
        
        // Recolectar productos
        for (int i = 0; i < tablaProductosB.getRowCount(); i++) {
            try {
                int cantidadDeProducto = Integer.parseInt((String) tablaProductosB.getValueAt(i, 1));
                if (cantidadDeProducto > 0) {
                    newIndiceProducto.add(i);
                    newIndiceCantidad.add(cantidadDeProducto);
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        
        if (newIndiceProducto.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Ingrese al menos un producto", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        FechaCalendario newFecha = new FechaCalendario(newFechaCarga, newFechaDescarga, 
            newIndicePiloto, newIndiceCamion, newIndiceProducto, newIndiceCantidad, true, newcompra);
        
        gescalendario.agregarFecha(newFecha);

        // Enviar correo si hay piloto seleccionado
        Piloto pilotoSeleccionado = gespilotos.getPilotos().get(newIndicePiloto);
        if (pilotoSeleccionado.getCorreoElectronicoPiloto() != null && 
            !pilotoSeleccionado.getCorreoElectronicoPiloto().isEmpty()) {
            
            Camiones camionSeleccionado = gescamiones.getCamiones().get(newIndiceCamion);
            String tripDetails = buildTripEmailContent(newFechaCarga, newFechaDescarga, 
                newcompra, tablaProductosB, "agregado", camionSeleccionado);
            
            enviarCorreoViaje(pilotoSeleccionado.getCorreoElectronicoPiloto(), 
                pilotoSeleccionado, tripDetails);
        }

        actualizarDespuesDeModificacion();
        
        JOptionPane.showMessageDialog(null, 
            "Viaje No. " + numeroViaje + " agregado correctamente", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, 
            "Error al agregar el viaje: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

// Método modificado para eliminar viaje
private void eliminarViaje() {
    if (indiceActual < 0 || indiceActual >= gescalendario.getFechasDeCalendario().size()) {
        JOptionPane.showMessageDialog(null, 
            "Seleccione un viaje válido de la tabla", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Obtener el número del viaje basado en el índice actual
    int numeroViaje = indiceActual + 1;
    
    int respuesta = JOptionPane.showConfirmDialog(null, 
        "¿Está seguro que desea eliminar el viaje No. " + numeroViaje + "?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION);
        
    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        FechaCalendario fechaAEliminar = gescalendario.getFechasDeCalendario().get(indiceActual);
        Piloto pilotoSeleccionado = gespilotos.getPilotos().get(fechaAEliminar.getIndicePiloto());
        Camiones camionSeleccionado = gescamiones.getCamiones().get(fechaAEliminar.getIndiceCamion());

        // Enviar correo de cancelación
        if (pilotoSeleccionado.getCorreoElectronicoPiloto() != null && 
            !pilotoSeleccionado.getCorreoElectronicoPiloto().isEmpty()) {
            String tripDetails = buildCancelledTripEmailContent(
                fechaAEliminar.getFechaC(),
                fechaAEliminar.getFechaD(),
                camionSeleccionado
            );
            enviarCorreoViaje(pilotoSeleccionado.getCorreoElectronicoPiloto(), 
                pilotoSeleccionado, tripDetails);
        }

        // Eliminar el viaje y liberar su número
        gescalendario.getFechasDeCalendario().remove(indiceActual);
        liberarNumeroViaje(numeroViaje);

        actualizarDespuesDeModificacion();

        JOptionPane.showMessageDialog(null, 
            "Viaje No. " + numeroViaje + " eliminado correctamente", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        
        

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, 
            "Error al eliminar el viaje: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

// Método actualizado para actualizar después de modificaciones
private void actualizarDespuesDeModificacion() {
    gescalendario.guardarFecharExcel();
    guardarEstadoNumeracion();
    ActualizarCalendario();
    ActualizarComboListaPedidos();
    ActualizarTablaB();
}











private static int contadorViajes = 0;
private void enviarCorreoViaje(String destinatario, Piloto piloto, String tripDetails) throws IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    final String username = "distribuidorapine@gmail.com";
    final String password = "aura hcol bzmt plzf";

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("PINEED - Notificación de Viaje");

        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();

        String contenido = "<html><body>" +
            "<h1 style='text-align: center; font-size: 24px; color: #2E86C1;'><strong>Notificación de Viaje</strong></h1>" +
            "<h2><strong>Notificación de Viaje en PINEED</strong></h2>" +
            "<p>Estimado/a " + piloto.getNombrePiloto() + " " + piloto.getApellidoPiloto() + ",</p>" +
            "<p>Se le informa que se ha " + tripDetails + "</p>" +
            "<h3>Piloto Encargado:</h3>" +
            "<p><strong>Nombre:</strong> " + piloto.getNombrePiloto() + " " + piloto.getApellidoPiloto() + "</p>" +
            "<div style='margin-top: 20px; text-align: center;'>" +
            "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
            "</div>" +
            "</body></html>";

        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Agregar la imagen
        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);

        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
        }

        message.setContent(multipart);
        Transport.send(message);
    } catch (MessagingException e) {
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}

private String buildTripEmailContent(Date fechaCarga, Date fechaDescarga, 
    boolean esCompra, JTable tablaProductos, String accion, Camiones camion) {
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder details = new StringBuilder();
    
    details.append(accion).append(" un nuevo viaje con los siguientes detalles:<br><br>");
    details.append("<strong>Tipo de Viaje:</strong> ")
          .append(esCompra ? "Pedido para Distribuidora" : "Pedido de Cliente")
          .append("<br>");
    details.append("<strong>Fecha de Carga:</strong> ")
          .append(dateFormat.format(fechaCarga))
          .append("<br>");
    details.append("<strong>Fecha de Descarga:</strong> ")
          .append(dateFormat.format(fechaDescarga))
          .append("<br><br>");
    
    details.append("<strong>Detalles del Camión:</strong><br>")
           .append("Marca: ").append(camion.getMarca()).append("<br>")
           .append("Modelo: ").append(camion.getModelo()).append("<br>")
           .append("Placa: ").append(camion.getPlacas()).append("<br><br>");
    
    details.append("<strong>Productos:</strong><br>");
    for (int i = 0; i < tablaProductos.getRowCount(); i++) {
        String cantidad = (String) tablaProductos.getValueAt(i, 1);
        if (cantidad != null && !cantidad.equals("0")) {
            details.append("- ")
                  .append(tablaProductos.getValueAt(i, 0))
                  .append(": ")
                  .append(cantidad)
                  .append("<br>");
        }
    }
    
    return details.toString();
}




private String buildTripEmailContent(Date oldFechaCarga, Date oldFechaDescarga, JTable oldTablaProductos,
                                     Date newFechaCarga, Date newFechaDescarga, boolean esCompra, 
                                     JTable newTablaProductos, String accion, Camiones camion) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder details = new StringBuilder();

    details.append("El viaje ha sido ").append(accion).append(". Aquí están los detalles anteriores y nuevos del viaje:<br><br>");

    // Detalles anteriores del viaje
    details.append("<strong>Detalles Anteriores:</strong><br>");
    details.append("<strong>Tipo de Viaje:</strong> ")
           .append(esCompra ? "Pedido para Distribuidora" : "Pedido de Cliente")
           .append("<br>");
    details.append("<strong>Fecha de Carga:</strong> ")
           .append(dateFormat.format(oldFechaCarga))
           .append("<br>");
    details.append("<strong>Fecha de Descarga:</strong> ")
           .append(dateFormat.format(oldFechaDescarga))
           .append("<br>");
    details.append("<strong>Productos:</strong><br>");
    for (int i = 0; i < oldTablaProductos.getRowCount(); i++) {
        String cantidad = (String) oldTablaProductos.getValueAt(i, 1);
        if (cantidad != null && !cantidad.equals("0")) {
            details.append("- ").append(oldTablaProductos.getValueAt(i, 0)).append(": ").append(cantidad).append("<br>");
        }
    }
    details.append("<br>");

    // Nuevos detalles del viaje
    details.append("<strong>Nuevos Detalles:</strong><br>");
    details.append("<strong>Fecha de Carga:</strong> ")
           .append(dateFormat.format(newFechaCarga))
           .append("<br>");
    details.append("<strong>Fecha de Descarga:</strong> ")
           .append(dateFormat.format(newFechaDescarga))
           .append("<br>");
    details.append("<strong>Detalles del Camión:</strong><br>")
           .append("Marca: ").append(camion.getMarca()).append("<br>")
           .append("Modelo: ").append(camion.getModelo()).append("<br>")
           .append("Placa: ").append(camion.getPlacas()).append("<br><br>");
    details.append("<strong>Productos:</strong><br>");
    for (int i = 0; i < newTablaProductos.getRowCount(); i++) {
        String cantidad = (String) newTablaProductos.getValueAt(i, 1);
        if (cantidad != null && !cantidad.equals("0")) {
            details.append("- ").append(newTablaProductos.getValueAt(i, 0)).append(": ").append(cantidad).append("<br>");
        }
    }

    return details.toString();
}


private String buildCancelledTripEmailContent(Date oldFechaCarga, Date oldFechaDescarga, boolean esCompra, Camiones camion) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder details = new StringBuilder();

    details.append("<h1 style='text-align: center; font-size: 24px; color: #E74C3C;'>")
           .append("Viaje Cancelado")
           .append("</h1>");

    details.append("<p>El número de pedido que se le había asignado anteriormente ha sido cancelado.</p><br><br>");
    details.append(esCompra ? "Pedido para Distribuidora" : "Pedido de Cliente").append("<br>");

    if (oldFechaCarga != null) {
        details.append("<strong>Fecha de Carga:</strong> ").append(dateFormat.format(oldFechaCarga)).append("<br>");
    } else {
        details.append("<strong>Fecha de Carga:</strong> No disponible<br>");
    }

    if (oldFechaDescarga != null) {
        details.append("<strong>Fecha de Descarga:</strong> ").append(dateFormat.format(oldFechaDescarga)).append("<br>");
    } else {
        details.append("<strong>Fecha de Descarga:</strong> No disponible<br>");
    }

    // Detalles del camión
    details.append("<strong>Detalles del Camión:</strong><br>")
           .append("Marca: ").append(camion.getMarca()).append("<br>")
           .append("Modelo: ").append(camion.getModelo()).append("<br>")
           .append("Placa: ").append(camion.getPlacas()).append("<br><br>");

    return details.toString();
}

private String buildCancelledTripEmailContent(Date oldFechaCarga, Date oldFechaDescarga, Camiones camion) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder details = new StringBuilder();

    details.append("<h1 style='text-align: center; font-size: 24px; color: #E74C3C;'>")
           .append("Viaje Cancelado")
           .append("</h1>");

    details.append("<p>El viaje que se le había asignado anteriormente ha sido cancelado.</p><br><br>");
    
    if (oldFechaCarga != null) {
        details.append("<strong>Fecha de Carga:</strong> ")
               .append(dateFormat.format(oldFechaCarga))
               .append("<br>");
    }
    
    if (oldFechaDescarga != null) {
        details.append("<strong>Fecha de Descarga:</strong> ")
               .append(dateFormat.format(oldFechaDescarga))
               .append("<br>");
    }

    details.append("<br><strong>Detalles del Camión:</strong><br>")
           .append("Marca: ").append(camion.getMarca()).append("<br>")
           .append("Modelo: ").append(camion.getModelo()).append("<br>")
           .append("Placa: ").append(camion.getPlacas());

    return details.toString();
}



    private void txtAgregarFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAgregarFechaMouseClicked
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
        int newIndicePiloto = comboPilotosB.getSelectedIndex();
        if (!isPilotoDisponible(newIndicePiloto)) {
            return; // Si el piloto no está disponible, terminamos la ejecución
        }
        
            int newIndiceCamion = comboCamionesB.getSelectedIndex();
        if (!isCamionDisponible(newIndiceCamion)) {
        return;
        }
        
        //verifica que las fechas sean validas
        if (newFechaCarga != null && newFechaDescarga != null 
    && !newFechaCarga.before(fechaActualMenosUnDia) 
    && !newFechaDescarga.before(fechaActualMenosUnDia) 
    && !newFechaDescarga.before(newFechaCarga)) {

            //miramos si el viaje es una compra o una venta
            boolean newcompra;
            
            if (comboTViajeB.getSelectedItem().equals("Pedido para Distribuidora")) {
                newcompra = true;
            }else{
                newcompra = false;
            }
            
            //creamos los vectores que tengas los indices de los productos
            Vector<Integer> newIndiceProducto = new Vector<>();
            Vector<Integer> newIndiceCantidad = new Vector<>();
            
            //recoremos las tabla B para ver cuantos productos forman parte del viaje
            for (int i = 0; i < tablaProductosB.getRowCount(); i++) {
                
                //definimos las variables a utilizar
                int cantidadDeProducto = 0;     
                Object value = tablaProductosB.getValueAt(i, 1);
                
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
                FechaCalendario newFecha = new FechaCalendario(newFechaCarga, newFechaDescarga, newIndicePiloto, newIndiceCamion, newIndiceProducto, newIndiceCantidad, true, newcompra);
                
                //la agregamos a la lista de fechas
                gescalendario.agregarFecha(newFecha);
                
                System.out.println("se ha creado la fecha correctamente");
                
                //actualizamos el calendario 
                ActualizarCalendario();
                
                //actualizamos el combo box
                ActualizarComboListaPedidos();
                
                // Limpiamos las fechas
                limpiarDateChoosers();
                //reiniciamos todos los elemento necesario
                ActualizarTablaB();
                
                //preguntamos si el indice anterior es superior de -1
                 if (indiceActual > -1) {
                    
                    //pintamos la fecha seleccionada en azul
                    colorearFecha(FechaTablaNew.get(0).getFechaC(), pastelBlue);
                    colorearFecha(FechaTablaNew.get(0).getFechaD(), pastelBlue);
                 }
                
                comboPilotosB.setSelectedIndex(0);
                comboCamionesB.setSelectedIndex(0);
                
                comboTViajeB.setSelectedIndex(0);
                
                //cargamos los datos en el excel
                gescalendario.guardarFecharExcel();
                
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
    
    }//GEN-LAST:event_txtAgregarFechaMouseClicked

    private void radioFinalizarViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFinalizarViajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioFinalizarViajeActionPerformed

    private void txtModificarViajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtModificarViajesMouseClicked
            
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    Date newFechaCarga = txtFechaDeCargaViaje.getDate();
    Date newFechaDescarga = txtFechaDeDescargaViaje.getDate();
    
    try {
        // Primero verificamos si el piloto está disponible
        int newIndicePiloto = comboPilotosA.getSelectedIndex();
        if (!isPilotoDisponible(newIndicePiloto)) {
            return; // Si el piloto no está disponible, terminamos la ejecución
        }
        
        
        int newIndiceCamion = comoboCamionesA.getSelectedIndex();
        if (!isCamionDisponible(newIndiceCamion)) {
        return;
        }
        
        Date fechaAntigua = new Date();
        Date nuevaFecha = new Date();
        
        if (indiceActual > -1) {
            //fecha antigua
        fechaAntigua = FechaTablaNew.get(indiceActual).getFechaC();

                
                // Crear una instancia de Calendar y establecer la fechaAntigua
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaAntigua);
        // Restar un día
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        // Obtener la nueva fecha
        nuevaFecha = calendar.getTime();
        }
 
        Date nuevaFechaB = new Date();
        
        // Limpiamos las fechas
                limpiarDateChoosers();
                
        // Crear una instancia de Calendar y establecer la fechaAntigua
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newFechaCarga);
        // Restar un día
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        // Obtener la nueva fecha
        nuevaFechaB = calendar.getTime();
        
        //verifica que las fechas sean validas
        if (newFechaCarga != null && newFechaDescarga != null && 
            indiceActual > -1 
    && !newFechaCarga.before(nuevaFecha) 
    && !newFechaDescarga.before(nuevaFecha) 
    && !newFechaDescarga.before(nuevaFechaB)) {
            
  
            
            //leemos las fechas antiguas
            Date oldFechaCarga = FechaTablaNew.get(indiceActual).getFechaC();
            Date oldFechaDescarga = FechaTablaNew.get(indiceActual).getFechaD();
            
            
            //miramos si el viaje es una compra o una venta
            boolean newcompra;
            
            //preguntamo si el pedido estara activo pedido activo
            boolean pedidoActivo = !radioFinalizarViaje.isSelected();
            
            if (ComboTViajeA.getSelectedItem().equals("Pedido para Distribuidora")) {
                newcompra = true;
            }else{
                newcompra = false;
            }
            
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
            
            
            //vamos a hacer un bucle y una variable que nos ayude a identificar si hay suficientes existencias para completar un viaje de cliente
            boolean ViajeDeCliente = true;
            
            if (pedidoActivo == false && newcompra == false) {
                //recoremos las tabla B para ver cuantos productos forman parte del viaje
                for (int i = 0; i < tablaProductosA.getRowCount(); i++) {

                    //definimos las variables a utilizar
                    int cantidadDeProducto = 0;

                    //asignamos el valor a las variables
                   cantidadDeProducto = Integer.parseInt((String) tablaProductosA.getValueAt(i, 1));
                   
                    if (cantidadDeProducto > productosTablaNew.get(i).getExistencias() ) {
                        ViajeDeCliente = false;
                    }
                }
           }
            
            if (ViajeDeCliente == true) {
                //verificamos que los vectores no esten vacios
               if (!newIndiceProducto.isEmpty()) {
                   //creamos la fecha nueva
                   FechaCalendario newFecha = new FechaCalendario(newFechaCarga, newFechaDescarga, newIndicePiloto, newIndiceCamion, newIndiceProducto, newIndiceCantidad, pedidoActivo, newcompra);

                   //la agregamos a la lista de fechas
                   gescalendario.modificarFecha(indiceActual, newFecha);

                   System.out.println("se ha creado la fecha correctamente");

                   //pintamos la fecha seleccionada otra vez en gris
                   colorearFecha(oldFechaCarga, defaultButtonGray);
                   colorearFecha(oldFechaDescarga, defaultButtonGray);        

                   //actualizamos el calendario 
                   ActualizarCalendario();

                   //pintamos la fecha seleccionada en azul
                   colorearFecha(newFechaCarga, pastelBlue);
                   colorearFecha(newFechaDescarga, pastelBlue);


                   //preguntamos si el viaje fue cancelado para ocualtar los botones d editar
                   if (pedidoActivo == false) {
                       //ocultamos el boton de modificar un viaje
                       txtModificarViajes.setVisible(false);
                       //ocultamos el radio button de finalizar un pedido
                       radioFinalizarViaje.setVisible(false);
                       //ocultamos el boton de eliminar
                       txtEliminarViaje.setVisible(false);
                   }


                    //ahora vamos a preguntar si el pedido fue cancelado para sumarle las cantidades al inventario de quintales
                    if (pedidoActivo == false && newcompra == true) {
                           //recoremos las tabla B para ver cuantos productos forman parte del viaje
                           for (int i = 0; i < tablaProductosA.getRowCount(); i++) {

                               //definimos las variables a utilizar
                               int cantidadDeProducto = 0;   

                               //asignamos el valor a las variables
                               cantidadDeProducto = Integer.parseInt((String) tablaProductosA.getValueAt(i, 1));

                               gesproductos.setCantidad(i, cantidadDeProducto, "+");                               

                           }

                           gesproductos.getCargarInvetarioExcel();
                    }
                    
                    //ahora vamos a preguntar si el pedido fue cancelado para sumarle las cantidades al inventario de quintales
                    if (pedidoActivo == false && newcompra == false) {
                           //recoremos las tabla B para ver cuantos productos forman parte del viaje
                           for (int i = 0; i < tablaProductosA.getRowCount(); i++) {

                               //definimos las variables a utilizar
                               int cantidadDeProducto = 0;   

                               //asignamos el valor a las variables
                               cantidadDeProducto = Integer.parseInt((String) tablaProductosA.getValueAt(i, 1));

                               gesproductos.setCantidad(i, cantidadDeProducto, "-");                               

                           }

                           gesproductos.getCargarInvetarioExcel();
                    }


                   //cargamos los datos en el excel
                   gescalendario.guardarFecharExcel();

                   //mostramos mesaje para acetar que este bien
                   JOptionPane.showMessageDialog(null, "Datos fueron modificados de forma correcta", "Confirmación", JOptionPane.INFORMATION_MESSAGE);


               }else{
                  //mostramos mesaje para acetar que este bien
                   JOptionPane.showMessageDialog(null, "Ingresa al menos un producto ", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
               }   
            }else{
               //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "no hay suficientes cantidades para completar el viaje", "Confirmación", JOptionPane.INFORMATION_MESSAGE);      
            }              
            
        }else{
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa fecha Valida", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }  
            
        } catch (NumberFormatException e) {
            //mostramos mesaje para acetar que este bien
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_txtModificarViajesMouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed

    }//GEN-LAST:event_jTextField19ActionPerformed

    private void comboTViajeBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTViajeBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTViajeBActionPerformed

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        // TODO add your handling code here:
        
        //funcion para el modo daltonico
        if (pastelGreen.equals(new Color(144, 238, 144))) {
        // Cambiar a modo daltónico
        pastelGreen = new Color(255, 255, 0); 
        pastelRed = new Color(255, 0, 0);      // Rojo
        pastelBlue = new Color(128, 0, 128); // Morado
        } else {
        // Cambiar a colores originales
        pastelGreen = new Color(144, 238, 144); // Verde pastel
        pastelRed = new Color(255, 182, 193);   // Rojo pastel
        pastelBlue = new Color(173, 216, 255);  // Azul pastel
        }
        
        
        // Limpiamos las fechas
                limpiarDateChoosers();
        ActualizarCalendario();
        
        //preguntamos si el indice anterior es superior de -1
                 if (comboPedidosLista.getSelectedIndex() > -1) {
  
                    //volvemos a colores las fechas antiguas al color correspondiente 
                    colorearFecha(FechaTablaNew.get(comboPedidosLista.getSelectedIndex()).getFechaC(), pastelBlue);
                    colorearFecha(FechaTablaNew.get(comboPedidosLista.getSelectedIndex()).getFechaD(), pastelBlue );
                 }
        
    }//GEN-LAST:event_jPanel14MouseClicked

    private Vector<Integer> deletedTripIndices = new Vector<>();

    private void txtEliminarViajeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEliminarViajeMouseClicked
       
     //este formulario nos ayudara a elimar una fecha
          // Mostrar popup de advertencia        
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí"
        if (respuesta == JOptionPane.YES_OPTION) {
               if (indiceActual > -1) {
                
                   gescalendario.getFechasDeCalendario().remove(indiceActual);
                   gescalendario.guardarFecharExcel();
                   
                   gespedidos.actualizarIndiceCalendario(indiceActual);              
                   gespedidos.GuardarEnExcel();
                   
                   // Limpiamos las fechas
                limpiarDateChoosers();
                   
                    //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "viaje eliminado correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE); 
           
                String username = this.currentUser; // Assuming currentUser holds the username
                String role = this.userRole;        // Assuming userRole holds the role
                LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

                FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
                abrir.setVisible(true); 
                this.dispose();
            }else{
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "seleccione un viaje de la tabla valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE); 
            }

        } 
                
    }//GEN-LAST:event_txtEliminarViajeMouseClicked


    public void cargarformulario(){
        String username = this.currentUser; // Assuming currentUser holds the username
                String role = this.userRole;        // Assuming userRole holds the role
                LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available

                FormularioViajes abrir = new FormularioViajes(currentUser, userRole, loginFrame);
                abrir.setVisible(true);
                this.dispose();
    }

    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice seleccionado por el combo box
            
            int indiceSeleccionado = comboPedidosLista.getSelectedIndex();
            
            //pregutamos si el indice seleccionado es el actual
            if (indiceSeleccionado != indiceActual && indiceSeleccionado > -1) {
                
                //preguntamos si el indice anterior es superior de -1
                 if (indiceActual > -1) {
                    //creamos un indice antiguo que nos ahorrara una cuantas iteraciones a la hora de pintar la fechas
                    int indiceAntiguo = indiceActual;
                    
                    //volvemos a colores las fechas antiguas al color correspondiente 
                    colorearFecha(FechaTablaNew.get(indiceAntiguo).getFechaC(), pastelGreen);
                    colorearFecha(FechaTablaNew.get(indiceAntiguo).getFechaD(), pastelRed );
                 }
                 
                 System.out.println(indiceSeleccionado);
                
                //hacemos que el indice actual sea igual al seleccionado 
                indiceActual = indiceSeleccionado;
                
                //ahora hacemos que cada cada elemento se iguale a la fecha seleccionada
                
                //igualamos las fechas
                txtFechaDeCargaViaje.setDate(FechaTablaNew.get(indiceSeleccionado).getFechaC());    
                txtFechaDeDescargaViaje.setDate(FechaTablaNew.get(indiceSeleccionado).getFechaD());
                
                //igualamos las fechas
                txtFechaDeCargaViaje.setDate(FechaTablaNew.get(indiceSeleccionado).getFechaC());    
                txtFechaDeDescargaViaje.setDate(FechaTablaNew.get(indiceSeleccionado).getFechaD());
                
                //igualamos el combo box de pilotos y camiones
                comboPilotosA.setSelectedIndex(FechaTablaNew.get(indiceSeleccionado).getIndicePiloto());
                comoboCamionesA.setSelectedIndex(FechaTablaNew.get(indiceSeleccionado).getIndiceCamion());
                
                //actualizamos el combo si es un venta o compra
                if (FechaTablaNew.get(indiceSeleccionado).getCompra() ==  true) {
                    ComboTViajeA.setSelectedIndex(0);
                }else{
                    ComboTViajeA.setSelectedIndex(1);
                }
                
                
                //colocamos las fechas seleccionadas en azul, para ver que son la fecha seleccionada
                colorearFecha(FechaTablaNew.get(indiceSeleccionado).getFechaC(), pastelBlue);
                colorearFecha(FechaTablaNew.get(indiceSeleccionado).getFechaD(), pastelBlue);
                
                //verificamos si el pedido aun esta activo para poder ponerlo activo o no el boton de modificar y el radio button
                if (FechaTablaNew.get(indiceSeleccionado).getActivo() == true) {
                    txtModificarViajes.setVisible(true);
                    radioFinalizarViaje.setVisible(true);
                    txtEliminarViaje.setVisible(true);
                }else{
                    txtModificarViajes.setVisible(false);
                    radioFinalizarViaje.setVisible(false);
                    txtEliminarViaje.setVisible(false);
                }
                
                String role = this.userRole;   
                
                // Ocultar componentes si el rol del usuario es PILOTO
                if (role != null && role.equalsIgnoreCase("PILOTO")) {
                    ocultarComponentesParaPiloto();
                    
                    radioFinalizarViaje.setVisible(false);
                    
                    if (FechaTablaNew.get(indiceSeleccionado).getActivo() == true) {
                        jLabel16.setVisible(false);
                    }else{
                        jLabel16.setVisible(true);
                    }
                }
               
                //actualizamos la tabla
                 ActualizarTablaA();
            }
            
            //nos ayudara a repintar todas las fechas encesarias
            Date fechaSeleccionada = CalendarioGeneral.getDate();
            
            //creamos un bool que nos permita permita saber si existen más elementos
            boolean masfechas = false;
            
            //preguntamos si hemos seleccionado una nueva fecha
             if (!fechaSeleccionada.equals(fechaActual)) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                 fechaActual = fechaSeleccionada;
                 System.out.println("" + formato.format(fechaActual));
                 
                 
                 //repintamos el calendario ora vez
                 ActualizarCalendario();
                 
                 
                 //creamos un bool para ver las fechas similares
                 boolean primeraFechaEncontrada = false;
                 
                 
                 //vaciamos todo el combo box
                 comboMasDeFecha.removeAllItems();
                 
                 //recoremos el vector de fecha tabla para encontrar la fecha selecionada
                 for (int i = 0; i < FechaTablaNew.size(); i++) {
                     
                     
                     Date fC = FechaTablaNew.get(i).getFechaC();
                     Date fD = FechaTablaNew.get(i).getFechaD();
                     
                    //preguntamos si ambas fechas son iguales
                     if (formato.format(fechaActual).equals(formato.format(fC)) || formato.format(fechaActual).equals(formato.format(fD))) {
                         
                         int numeroDeIndiceAescribri = i+1;
                         
                        if (primeraFechaEncontrada == false) {
                            //igualamos el indice a la fecha
                            comboMasDeFecha.addItem(""  + numeroDeIndiceAescribri);
                            comboPedidosLista.setSelectedIndex(i);
                            primeraFechaEncontrada = true;
                        }else if (primeraFechaEncontrada == true) {
                            masfechas = true;
                            comboMasDeFecha.setVisible(true);
                            textoFechasIguales.setVisible(true);
                            comboMasDeFecha.addItem(""  + numeroDeIndiceAescribri);
                        }
                     }       
                     
                 }
                 
                 if (primeraFechaEncontrada == false) {
                     
                     int indiceMasFechasSeleccionado = indiceSeleccionado + 1;                    
                     comboMasDeFecha.addItem(""  + indiceMasFechasSeleccionado);
                 }
                 
                 if (masfechas == false) {
                     comboMasDeFecha.setVisible(false);
                     textoFechasIguales.setVisible(false);
                 }
                 
                 //pintamos la fecha seleccionada
                 if (indiceSeleccionado == indiceActual && indiceActual > -1) {
                    //colocamos las fechas seleccionadas en azul, para ver que son la fecha seleccionada
                    colorearFecha(FechaTablaNew.get(indiceSeleccionado).getFechaC(), pastelBlue);
                    colorearFecha(FechaTablaNew.get(indiceSeleccionado).getFechaD(), pastelBlue); 
                 }
                             
                 
             }
             
             //cremos un if que nos ayudara a ver si hay fechas iguales 
                int indiceFechas = -1; 
                //este if guardara el numero del combo de fechas iguales en la variable anteriormente creaeda
                if (comboMasDeFecha.getItemCount() > 1) {
                    //agregamos el valor del item del combo a la variable de indice fechas
                    String selectedItemFechasRepetidas = (String)comboMasDeFecha.getSelectedItem();
                    indiceFechas = Integer.parseInt(selectedItemFechasRepetidas);
                    indiceFechas -= 1;                
                }
                
                
                //cremos un if para ver las fechas iguales actuales 
                if (indiceFechas != indiceFechasIgualesActuales && comboMasDeFecha.isVisible() && indiceFechas > -1) {
                    System.out.println("hay mas fechas iguales"); 
                    
                    indiceFechasIgualesActuales = indiceFechas;
                     
                     int indiceFechaIgualACambiar;
                     
                     
                     String selectedItemFechasRepetidas = (String)comboMasDeFecha.getSelectedItem();
                     indiceFechaIgualACambiar = Integer.parseInt(selectedItemFechasRepetidas);
                     indiceFechaIgualACambiar -= 1 ;
                     
                     comboPedidosLista.setSelectedIndex(indiceFechaIgualACambiar);
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
            java.util.logging.Logger.getLogger(FormularioViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                       
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new FormularioViajes(username, role, loginFrame).setVisible(true);
            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar CalendarioGeneral;
    private javax.swing.JComboBox<String> ComboTViajeA;
    private javax.swing.JComboBox<String> comboCamionesB;
    private javax.swing.JComboBox<String> comboMasDeFecha;
    private javax.swing.JComboBox<String> comboPedidosLista;
    private javax.swing.JComboBox<String> comboPilotosA;
    private javax.swing.JComboBox<String> comboPilotosB;
    private javax.swing.JComboBox<String> comboTViajeB;
    private javax.swing.JComboBox<String> comoboCamionesA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
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
    private javax.swing.JTextField jTextField19;
    private javax.swing.JRadioButton radioFinalizarViaje;
    private javax.swing.JTable tablaProductosA;
    private javax.swing.JTable tablaProductosB;
    private javax.swing.JLabel textoFechasIguales;
    private javax.swing.JPanel txtAgregarFecha;
    private javax.swing.JPanel txtEliminarViaje;
    private com.toedter.calendar.JDateChooser txtFechaDeCargaAgregar;
    private com.toedter.calendar.JDateChooser txtFechaDeCargaViaje;
    private com.toedter.calendar.JDateChooser txtFechaDeDescargaAgregar;
    private com.toedter.calendar.JDateChooser txtFechaDeDescargaViaje;
    private javax.swing.JComboBox<String> txtMenu;
    private javax.swing.JPanel txtModificarViajes;
    // End of variables declaration//GEN-END:variables
}
