package ControlPlanilla;

import ControlInventario.gestionProductos;
import ControlInventario.Producto;
import GestionDePilotos.Piloto;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import Inicio.INICIOPINEED;
import Inicio.INICIOPINEEDINICIAL;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import ControlInventario.FrameInventario;
import ControlPedidos.FormularioPedidos;
import ControlPlanilla.FramePlanillaSemanal;
import ControlViajes.FormularioViajes;
import Login.LOGINPINEED;
import javax.swing.JButton;
import java.util.Vector;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FramePlanillaSemanal extends javax.swing.JFrame {

    /**
     * Creates new form FramePlanillaSemanal
     */
    //creamos el objeto gestion de trabajdores y el vector de trabajadores
    public GestionFichaTrabajador gTrabajadores;
    public Vector<FichaTrabajador> tTrabajador = new Vector<>();
    
    //crearemos el modelo de la tabla de trabajadores
    DefaultTableModel modeloTrabajador = new DefaultTableModel();
    
    //crearemos el modelo de de la tabla de entradas
    DefaultTableModel modeloEntradas = new DefaultTableModel();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    //crearemos un indice actual que nos ayudara a modificar los elementos
    private int indiceActual;
    
    public FramePlanillaSemanal(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        //crearemos el objeto gtrabajadores
        gTrabajadores = new GestionFichaTrabajador();
        
        gTrabajadores.cargarTrabajadoresExcel();
        
        //definimos el indice actual en -1 debido aque no representa ningun indice de la tabla real
        indiceActual = -1;
        
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
          this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        configureButtonsByRole();
    }
    
    
 



private void configureButtonsByRole() {
    JButton[] allButtons = {
        btnGestionDeVentas1, btnGestionDePedidos, btnPlanillaDeTrabajadores,
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


    //esta funcion actualizara los elemetnos existentes en la tabla
    private void cargarInvetrioTabla(){
        
        //primero vaciaremos la tabla totalmente
        modeloTrabajador.setRowCount(0);
        
        //con este for llenaremos la tabla con los elemetos del vector
        for (FichaTrabajador trad : tTrabajador) {
            
            modeloTrabajador.addRow(new Object[]{trad.getNombre()});

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
        botonguardarDatos = new javax.swing.JPanel();
        panelf1 = new javax.swing.JPanel();
        panelBoton1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaDescripcionT = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        textoNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        textoSalario = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        labelSemanas = new javax.swing.JLabel();
        panelf2 = new javax.swing.JPanel();
        panelBoton2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        textoNombreModificado = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaDescripcionModificada = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        textoSalarioModificado = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        labelNombre = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        areaDescripcionNo = new javax.swing.JTextArea();
        panelBotonModificar = new javax.swing.JPanel();
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
        textoValorEntrada = new javax.swing.JTextField();
        comboOperacionEntrada = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        textoRazonEntrada = new javax.swing.JTextField();
        PanelBoton3 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btnGestionDeVentas1 = new javax.swing.JButton();
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

        jPanel1.setBackground(new java.awt.Color(18, 59, 87));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tablaTrabajadores.setBackground(new java.awt.Color(128, 179, 220));
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

        jPanel3.setBackground(new java.awt.Color(85, 150, 202));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 102, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        botonguardarDatos.setBackground(new java.awt.Color(153, 153, 0));
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
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(botonguardarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonguardarDatos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelf1.setBackground(new java.awt.Color(128, 179, 220));

        panelBoton1.setBackground(new java.awt.Color(85, 111, 169));
        panelBoton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBoton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBoton1Layout = new javax.swing.GroupLayout(panelBoton1);
        panelBoton1.setLayout(panelBoton1Layout);
        panelBoton1Layout.setHorizontalGroup(
            panelBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 117, Short.MAX_VALUE)
        );
        panelBoton1Layout.setVerticalGroup(
            panelBoton1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(85, 111, 169));

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
            .addGap(0, 47, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
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

        areaDescripcionT.setColumns(20);
        areaDescripcionT.setRows(5);
        jScrollPane2.setViewportView(areaDescripcionT);

        jLabel1.setText("Nombre del Trabajador:");

        jLabel2.setText("pequeña descripcion:");

        jLabel14.setText("semanas de trabajo:");

        jLabel15.setText("Salario Semanal:");

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
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelf1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textoSalario))
                                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(panelBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSemanas, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelf1Layout.setVerticalGroup(
            panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf1Layout.createSequentialGroup()
                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(labelSemanas))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(6, 6, 6)))
                .addGroup(panelf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelf1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelf2.setBackground(new java.awt.Color(128, 179, 220));

        panelBoton2.setBackground(new java.awt.Color(85, 111, 169));
        panelBoton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBoton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBoton2Layout = new javax.swing.GroupLayout(panelBoton2);
        panelBoton2.setLayout(panelBoton2Layout);
        panelBoton2Layout.setHorizontalGroup(
            panelBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );
        panelBoton2Layout.setVerticalGroup(
            panelBoton2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel4.setText("Nombre del Trabajador:");

        areaDescripcionModificada.setColumns(20);
        areaDescripcionModificada.setRows(5);
        jScrollPane4.setViewportView(areaDescripcionModificada);

        jLabel13.setText("Salario Semanal:");

        javax.swing.GroupLayout panelf2Layout = new javax.swing.GroupLayout(panelf2);
        panelf2.setLayout(panelf2Layout);
        panelf2Layout.setHorizontalGroup(
            panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelf2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoNombreModificado)
                    .addComponent(jScrollPane4)
                    .addGroup(panelf2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBoton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textoSalarioModificado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelf2Layout.setVerticalGroup(
            panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelf2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoNombreModificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(textoSalarioModificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBoton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
            .addGap(0, 37, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
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
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelNombre.setText("Nombre");

        areaDescripcionNo.setEditable(false);
        areaDescripcionNo.setColumns(20);
        areaDescripcionNo.setRows(5);
        jScrollPane3.setViewportView(areaDescripcionNo);

        panelBotonModificar.setBackground(new java.awt.Color(255, 255, 153));
        panelBotonModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBotonModificarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBotonModificarLayout = new javax.swing.GroupLayout(panelBotonModificar);
        panelBotonModificar.setLayout(panelBotonModificarLayout);
        panelBotonModificarLayout.setHorizontalGroup(
            panelBotonModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
        );
        panelBotonModificarLayout.setVerticalGroup(
            panelBotonModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        jLabel16.setText("semanas de trabajo:");

        labelSemanaInfo.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelSemanaInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBotonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(labelNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSemanaInfo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(13, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(panelBotonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jLabel5.setText("Salario Semanal:  Q");

        labelSalirioSemanal.setText("00.00");

        tablaEtnradas.setBackground(new java.awt.Color(128, 179, 220));
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

        jLabel7.setText("Valor Total: Q");

        labelSalioTotal.setText("00.00");

        jLabel9.setText("Grabar nueva entrada:");

        jLabel10.setText("Valor de la entrada:");

        jLabel11.setText("operacion:");

        comboOperacionEntrada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "+", "-" }));

        jLabel12.setText("Razon:");

        PanelBoton3.setBackground(new java.awt.Color(85, 111, 169));
        PanelBoton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PanelBoton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelBoton3Layout = new javax.swing.GroupLayout(PanelBoton3);
        PanelBoton3.setLayout(PanelBoton3Layout);
        PanelBoton3Layout.setHorizontalGroup(
            PanelBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );
        PanelBoton3Layout.setVerticalGroup(
            PanelBoton3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 86, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoRazonEntrada, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PanelBoton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(comboOperacionEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(textoValorEntrada)
                        .addContainerGap())))
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
                            .addComponent(textoValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(comboOperacionEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(textoRazonEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelBoton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelSalioTotal))
                .addGap(18, 18, 18))
        );

        jPanel20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnGestionDeVentas1.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeVentas1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeVentas1.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeVentas1.setText("GESTION DE VENTAS");
        btnGestionDeVentas1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeVentas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeVentas1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnGestionDeUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePilotos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDeVentas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePedidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionDeClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnRegresarLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlanillaDeTrabajadores, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInventarioDeQuintales, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
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
                .addComponent(btnGestionDeVentas1)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(168, 168, 168))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1276, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        
        //primero obtenemos los datos ingresador por el usuario 
        
        String newnombre = textoNombre.getText();
        float newsalario = Float.parseFloat(textoSalario.getText());
        int newsemanas = 0;
        String newDescripcion = areaDescripcionT.getText();
        
        //crearemos el objeto trabajador provisional
        FichaTrabajador newTrabajador = new FichaTrabajador(newnombre, newDescripcion, newsalario, newsemanas);
        
        //agregamos este trabajador al vector de gestion trabajador
        gTrabajadores.agregarTrabajador(newTrabajador);
        
        //actualizamos la tabla
        cargarInvetrioTabla();
        
        //ponemos todos los texto en blanco
        textoNombre.setText("");
        textoSalario.setText("");
        areaDescripcionT.setText("");
        
        //ocultamos los paneles para evitar problemas
        panelf1.setVisible(false);
        panelf2.setVisible(false);
        
        if (indiceActual > -1) {
            //colocamos la tabala seleccionando el indice actual
            tablaTrabajadores.setRowSelectionInterval(indiceActual, indiceActual);
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
        //preguntamos si el indice seleccionado de la tabla es legal
            if (tablaTrabajadores.getSelectedRow() > -1) {
                
                //primeor obtenemos los valores del trabajador 
                String newnombre = textoNombreModificado.getText();
                String newDescripcion = areaDescripcionModificada.getText();
                float newSalario = Float.parseFloat(textoSalarioModificado.getText());

                //indiceActual

                //crearemos el objeto trabajador provisional
                FichaTrabajador newTrabajador = tTrabajador.get(tablaTrabajadores.getSelectedRow());
                
                //asignamos este objetos los nuevos valores
                newTrabajador.setNombre(newnombre);
                newTrabajador.setDescripcion(newDescripcion);
                newTrabajador.setSalarioSemanal(newSalario);
                
                //llamamos a la funcion de modificar un valor
                gTrabajadores.modificarTRabajdor(tablaTrabajadores.getSelectedRow(), newTrabajador);
                
                //ocultamos los paneles para evitar problemas
                panelf1.setVisible(false);
                panelf2.setVisible(false);
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(tablaTrabajadores.getSelectedRow()).getSemanasDeTrabajo());
                areaDescripcionNo.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(tablaTrabajadores.getSelectedRow()).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(tablaTrabajadores.getSelectedRow()));
                
                //mostramos los mismos datos de modificado 
                textoNombreModificado.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getNombre());
                areaDescripcionModificada.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getDescripcion());
                textoSalarioModificado.setText("" + tTrabajador.get(tablaTrabajadores.getSelectedRow()).getSalarioSemanal());
                
                //actualizamos la tabla
                cargarInvetrioTabla();
                
                //acemos que la tabla siga seleccionando el indice actual
                tablaTrabajadores.setRowSelectionInterval(indiceActual, indiceActual);
            }
        
    }//GEN-LAST:event_panelBoton2MouseClicked

    private void PanelBoton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelBoton3MouseClicked
        // TODO add your handling code here:
        
        //boton para grabar una nueva entrada
        
        //preguntamos si el indice seleccionado de la tabla es legal
            if (tablaTrabajadores.getSelectedRow() > -1) {
                
                //primero obtenemos los datos de la entrada
                String newEntrada = textoRazonEntrada.getText();
                float newValorEntrada = Float.parseFloat(textoValorEntrada.getText()); 
                        
                String operacion = comboOperacionEntrada.getSelectedItem().toString();
                
                //hacemo que el valor no pueda quedar negativo 
                if ( Float.parseFloat(gTrabajadores.getSalariTotal(tablaTrabajadores.getSelectedRow())) >= newValorEntrada && operacion.equals("-") && newValorEntrada >= 1) {
                    //llamamos a la funcion de agregar entrada
                    gTrabajadores.setEntrada(tablaTrabajadores.getSelectedRow(), newEntrada, newValorEntrada, operacion);
                }else if(operacion.equals("+") && newValorEntrada >= 1){
                    //llamamos a la funcion de agregar entrada
                    gTrabajadores.setEntrada(tablaTrabajadores.getSelectedRow(), newEntrada, newValorEntrada, operacion);
                }
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(tablaTrabajadores.getSelectedRow()).getSemanasDeTrabajo());
                areaDescripcionNo.setText(tTrabajador.get(tablaTrabajadores.getSelectedRow()).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(tablaTrabajadores.getSelectedRow()).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(tablaTrabajadores.getSelectedRow()));
                
                //actualizamos la tabla de entradas
                modificarTabladeEtradas(tablaTrabajadores.getSelectedRow());
                //actualizamos la tabla
                cargarInvetrioTabla();
                
                //colocamos la tabala seleccionando el indice actual
                tablaTrabajadores.setRowSelectionInterval(indiceActual, indiceActual);
            }
        
        
    }//GEN-LAST:event_PanelBoton3MouseClicked

    private void botonguardarDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonguardarDatosMouseClicked
        // TODO add your handling code here:
        
        //funcion provisional para guardas datos en el excel
        gTrabajadores.guardarTrabajadoresExcel();
        System.out.println("se ha guardado correctamente");
    }//GEN-LAST:event_botonguardarDatosMouseClicked

    private void btnGestionDeVentas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeVentas1ActionPerformed

    }//GEN-LAST:event_btnGestionDeVentas1ActionPerformed

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
            
            //preguntamos si el indice genera y el seleccionado son distintos
            if (indiceSeleccionado != indiceActual && tablaTrabajadores.getSelectedRow() > -1) {
                
                //igualamos el indice general el indice seleccionado
                indiceActual = indiceSeleccionado;
                
                //mostramos los datos de la tabla 
                labelNombre.setText(tTrabajador.get(indiceSeleccionado).getNombre());
                labelSemanaInfo.setText("" + tTrabajador.get(indiceSeleccionado).getSemanasDeTrabajo());
                areaDescripcionNo.setText(tTrabajador.get(indiceSeleccionado).getDescripcion());
                
                labelSalirioSemanal.setText("" + tTrabajador.get(indiceSeleccionado).getSalarioSemanal());
                
                //ahora vamos a llamar a la funcion de calcular el sueldo total
                labelSalioTotal.setText(gTrabajadores.getSalariTotal(indiceSeleccionado));
                
                //mostramos los mismos datos de modificado 
                textoNombreModificado.setText(tTrabajador.get(indiceSeleccionado).getNombre());
                areaDescripcionModificada.setText(tTrabajador.get(indiceSeleccionado).getDescripcion());
                textoSalarioModificado.setText("" + tTrabajador.get(indiceSeleccionado).getSalarioSemanal());
                
                //actualizamos la tabla de entradas
                modificarTabladeEtradas(tablaTrabajadores.getSelectedRow());
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
    private javax.swing.JTextArea areaDescripcionModificada;
    private javax.swing.JTextArea areaDescripcionNo;
    private javax.swing.JTextArea areaDescripcionT;
    private javax.swing.JPanel botonguardarDatos;
    private javax.swing.JButton btnCalendario;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGestionDeCamiones;
    private javax.swing.JButton btnGestionDeClientes;
    private javax.swing.JButton btnGestionDeCreditos;
    private javax.swing.JButton btnGestionDePedidos;
    private javax.swing.JButton btnGestionDePilotos;
    private javax.swing.JButton btnGestionDeUsuarios;
    private javax.swing.JButton btnGestionDeVentas1;
    private javax.swing.JButton btnInventarioDeQuintales;
    private javax.swing.JButton btnPlanillaDeTrabajadores;
    private javax.swing.JButton btnRegresarLogin;
    private javax.swing.JComboBox<String> comboOperacionEntrada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
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
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoNombreModificado;
    private javax.swing.JTextField textoRazonEntrada;
    private javax.swing.JTextField textoSalario;
    private javax.swing.JTextField textoSalarioModificado;
    private javax.swing.JTextField textoValorEntrada;
    // End of variables declaration//GEN-END:variables
}
