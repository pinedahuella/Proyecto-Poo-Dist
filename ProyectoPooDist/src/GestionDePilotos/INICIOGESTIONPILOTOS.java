package GestionDePilotos;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;


public class INICIOGESTIONPILOTOS extends javax.swing.JFrame {
    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
        private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    DefaultTableModel modeloPilotos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    
    
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




    public INICIOGESTIONPILOTOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        
        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();
        
        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Teléfono", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);
        
        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }
        
        tblRegistroPilotos.setModel(modeloPilotos);
        tblRegistroPilotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroPilotos.getTableHeader().setReorderingAllowed(false);
        tblRegistroPilotos.getTableHeader().setResizingAllowed(false);
        tblRegistroPilotos.setRowSelectionAllowed(true);
        tblRegistroPilotos.setColumnSelectionAllowed(false);
        cargarPilotosEnTabla();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
        configureButtonsByRole();
    }
    
    
        

    private void cargarPilotosEnTabla() {
        for (Piloto piloto : listaPilotos) {
            modeloPilotos.addRow(new Object[]{
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getEstadoPiloto(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jTextField18 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        mostrarPiloto = new javax.swing.JButton();
        eliminarPiloto = new javax.swing.JButton();
        agregarPiloto = new javax.swing.JButton();
        modificarPiloto = new javax.swing.JButton();
        buscarPiloto = new javax.swing.JButton();
        refrescarPagina = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
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
        btnRegresarLogin4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel8.setBackground(new java.awt.Color(6, 40, 86));

        jTextField18.setEditable(false);
        jTextField18.setBackground(new java.awt.Color(0, 153, 153));
        jTextField18.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setText(" GESTION DE PILOTOS");
        jTextField18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        tblRegistroPilotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblRegistroPilotos);

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        mostrarPiloto.setBackground(new java.awt.Color(204, 204, 255));
        mostrarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mostrarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/redicmonacionas mostrar.png"))); // NOI18N
        mostrarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        mostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarPilotoActionPerformed(evt);
            }
        });

        eliminarPiloto.setBackground(new java.awt.Color(255, 0, 0));
        eliminarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eliminarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/eliminausuare (1).png"))); // NOI18N
        eliminarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        eliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPilotoActionPerformed(evt);
            }
        });

        agregarPiloto.setBackground(new java.awt.Color(51, 255, 51));
        agregarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        agregarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/agregarusers (1).png"))); // NOI18N
        agregarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        agregarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPilotoActionPerformed(evt);
            }
        });

        modificarPiloto.setBackground(new java.awt.Color(153, 0, 204));
        modificarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        modificarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/lapis (1).png"))); // NOI18N
        modificarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        modificarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarPilotoActionPerformed(evt);
            }
        });

        buscarPiloto.setBackground(new java.awt.Color(0, 102, 255));
        buscarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buscarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/redimenciona buscar.png"))); // NOI18N
        buscarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        buscarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarPilotoActionPerformed(evt);
            }
        });

        refrescarPagina.setBackground(new java.awt.Color(255, 255, 0));
        refrescarPagina.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refrescarPagina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/regresarredimensionado.png"))); // NOI18N
        refrescarPagina.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        refrescarPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPaginaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refrescarPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(modificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(agregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(eliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarPiloto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(refrescarPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        jPanel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

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

        btnRegresarLogin4.setBackground(new java.awt.Color(0, 102, 102));
        btnRegresarLogin4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegresarLogin4.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresarLogin4.setText("REGRESAR LOGIN");
        btnRegresarLogin4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnRegresarLogin4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarLogin4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnGestionDeUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePilotos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDeVentas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionDePedidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionDeClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnRegresarLogin4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGestionDeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlanillaDeTrabajadores, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInventarioDeQuintales, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(btnCalendario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegresarLogin4)
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed

    }//GEN-LAST:event_jTextField18ActionPerformed

    private void mostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarPilotoActionPerformed
  int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);
            abrirVentanaMostrar(pilotoSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarPilotoActionPerformed

    private void eliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPilotoActionPerformed
        int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreSeleccionado = (String) tblRegistroPilotos.getValueAt(filaSeleccionada, 0);
            String apellidoSeleccionado = (String) tblRegistroPilotos.getValueAt(filaSeleccionada, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar este piloto: " + nombreSeleccionado + " " + apellidoSeleccionado + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gestionPilotos.eliminarPiloto(nombreSeleccionado, apellidoSeleccionado);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Piloto eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para eliminar.");
        }
    }//GEN-LAST:event_eliminarPilotoActionPerformed

    private void agregarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPilotoActionPerformed
         String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
    // Create the INICIOPINEED instance with the correct arguments
    AGREGARGESTIONPILOTOS abrir = new AGREGARGESTIONPILOTOS(username, role, loginFrame);
    abrir.setVisible(true);
    
    }//GEN-LAST:event_agregarPilotoActionPerformed

    private void modificarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarPilotoActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        Piloto pilotoSeleccionado = listaPilotos.get(filaSeleccionada);
        abrirVentanaModificar(pilotoSeleccionado);
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un piloto para modificar.");
    }
    }//GEN-LAST:event_modificarPilotoActionPerformed

    
    
    
    
    private void abrirVentanaModificar(Piloto piloto) {
    String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
        MODIFICARGESTIONPILOTOS ventanaModificar = new MODIFICARGESTIONPILOTOS(piloto, this, username, role, loginFrame);
    ventanaModificar.setVisible(true);
    this.setVisible(false);
    }

  
    
    
    
    private void abrirVentanaMostrar(Piloto piloto) {
        MOSTRARGESTIONPILOTOS ventanaMostrar = new MOSTRARGESTIONPILOTOS(piloto, this);
        ventanaMostrar.setVisible(true);
    }
    
    public void actualizarTabla() {
        gestionPilotos.cargarPilotosDesdeExcel();
        listaPilotos = gestionPilotos.getPilotos();
        modeloPilotos.setRowCount(0);
        for (Piloto piloto : listaPilotos) {
            modeloPilotos.addRow(new Object[]{
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getEstadoPiloto(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento()
            });
        }
    }
    
    
    
    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
        if (txtNombrePilotoBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
            return;
        }

        String nombreBuscado = txtNombrePilotoBuscar.getText().trim();
        modeloPilotos.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Piloto piloto : listaPilotos) {
            boolean coincide = true;

            if (!nombreBuscado.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado)) {
                coincide = false;
            }

            if (coincide) {
                modeloPilotos.addRow(new Object[]{
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getGeneroPiloto(),
                    piloto.getFechaDeNacimiento()
                });
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
            for (Piloto piloto : listaPilotos) {
                modeloPilotos.addRow(new Object[]{
                    piloto.getNombrePiloto(),
                    piloto.getApellidoPiloto(),
                    piloto.getNumeroDeDpi(),
                    piloto.getTipoLicencia(),
                    piloto.getNumeroTelefonicoPiloto(),
                    piloto.getEstadoPiloto(),
                    piloto.getCorreoElectronicoPiloto(),
                    piloto.getGeneroPiloto(),
                    piloto.getFechaDeNacimiento()
                });
            }
        } else {
            tblRegistroPilotos.setVisible(true);
            if (tblRegistroPilotos.getRowCount() > 0) {
                tblRegistroPilotos.setRowSelectionInterval(0, 0);
            }
        }

        txtNombrePilotoBuscar.setText("");
    }//GEN-LAST:event_buscarPilotoActionPerformed

    private void refrescarPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPaginaActionPerformed
          
           String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
    INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPaginaActionPerformed

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

    private void btnRegresarLogin4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarLogin4ActionPerformed
        LOGINPINEED abrir = new  LOGINPINEED();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarLogin4ActionPerformed

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
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
     
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole"; 
            
            
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new INICIOGESTIONPILOTOS(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarPiloto;
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
    private javax.swing.JButton btnRegresarLogin4;
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JButton eliminarPiloto;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JButton modificarPiloto;
    private javax.swing.JButton mostrarPiloto;
    private javax.swing.JButton refrescarPagina;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    // End of variables declaration//GEN-END:variables
}
