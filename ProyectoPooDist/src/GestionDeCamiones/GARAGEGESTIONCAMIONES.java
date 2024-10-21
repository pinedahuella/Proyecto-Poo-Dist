package GestionDeCamiones;

import GestionDeCamiones.FACTURASGESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeCamiones.CAMIONESFACTURA;
import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;



public class GARAGEGESTIONCAMIONES extends javax.swing.JFrame {
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    public FACTURASGESTIONCAMIONES gestionFacturas;
    DefaultTableModel modeloCamiones = new DefaultTableModel();
    DefaultTableModel modeloRegistroGastos = new DefaultTableModel();
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    private int indiceActual;


  public GARAGEGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;
        
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        
        gestionFacturas = new FACTURASGESTIONCAMIONES();
        gestionFacturas.cargarFacturasDesdeExcel();

        // Modify the table models to be non-editable
        modeloCamiones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This makes all cells non-editable
            }
        };

        modeloRegistroGastos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This makes all cells non-editable
            }
        };

        String[] columnasCamiones = {
            "Placas", "Marca", "Modelo", "Estado",
            "Tiempo en Reparación", "Fecha de Mantenimiento", "Total Invertido"
        };
        modeloCamiones.setColumnIdentifiers(columnasCamiones);
        
        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
            System.out.println("Camiones cargados correctamente: " + listaCamiones.size());
        } else {
            System.out.println("Error: No se pudieron cargar los camiones.");
        }
        
        tblRegistroCamiones.setModel(modeloCamiones);
        cargarCamionesTabla();

        String[] columnasFacturas = {
            "Placas", "Fecha", "Tipo de Gasto", "Descripción", "Monto", "Hora Registrado"
        };
        modeloRegistroGastos.setColumnIdentifiers(columnasFacturas);
        tblRegistroGastos.setModel(modeloRegistroGastos);
        
        cargarFacturasTabla();

        // Additional settings to make tables non-editable
        tblRegistroCamiones.setDefaultEditor(Object.class, null);
        tblRegistroGastos.setDefaultEditor(Object.class, null);

        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        addWindowListener();
    }


     private String obtenerHoraActual() {
        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return ahora.format(formatter);
    }

    private void cargarFacturasTabla() {
        modeloRegistroGastos.setRowCount(0);
        for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
            modeloRegistroGastos.addRow(new Object[]{
                factura.getPlacasFactura(),
                factura.getFechaFactura(),
                factura.getTipoDeGastoFactura(),
                factura.getDescripcionFactura(),
                factura.getMontoFactura(),
                factura.getHoraActual()
            });
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
    // Pass the necessary arguments when creating a new INICIOPINEED object
    LOGINPINEED nuevaLoginFrame = new LOGINPINEED();  // Assuming you need a new LOGINPINEED frame
    GARAGEGESTIONCAMIONES nuevaVentanaLogin = new GARAGEGESTIONCAMIONES(null, null, nuevaLoginFrame); // Passing nulls as placeholders for username and role
    nuevaVentanaLogin.setVisible(true);
    this.dispose();
}
private void cargarCamionesTabla() {
    modeloCamiones.setRowCount(0);
    for (Camiones camiones : listaCamiones) {
        modeloCamiones.addRow(new Object[]{
            camiones.getPlacas(),
            camiones.getModelo(),
            camiones.getMarca(),
            camiones.getEstado(),
            camiones.getTiempoEnReparacion(),
            camiones.getFechaDeMantenimiento(),
            camiones.getTotal()
        });
    }
}

  private void limpiarCampos() {
        txtCostoDeReparacionGasto.setText("");
        txtCostoGalonGasto.setText("");
        txtNumeroDeGalonesGasto.setText("");
        txtDescripcionDelGastoGasto.setText("");
        txtCostoDeMantenimiento.setText("");
        txtGastoNoEspecificadoGasto.setText("");
        txtTiempoMantenimientoGasto.setDate(null);
    }
 
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCostoGalonGasto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCostoDeReparacionGasto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtGastoNoEspecificadoGasto = new javax.swing.JTextField();
        txtDescripcionDelGastoGasto = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroGastos = new javax.swing.JTable();
        txtCostoDeMantenimiento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTiempoMantenimientoGasto = new com.toedter.calendar.JDateChooser();
        txtNumeroDeGalonesGasto = new javax.swing.JTextField();
        refrescarPagina = new javax.swing.JButton();
        agregarFactura = new javax.swing.JButton();
        eliminarFactura = new javax.swing.JButton();
        buscarCamion = new javax.swing.JButton();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        reparado = new javax.swing.JButton();
        txtActualizarEstado = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtActualizarTiempoReparacion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));
        jPanel2.setPreferredSize(new java.awt.Dimension(1110, 550));

        jPanel5.setPreferredSize(new java.awt.Dimension(1110, 550));
        jPanel5.setRequestFocusEnabled(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel6.setText("NUMERO DE GALONES");

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel7.setText("COSTO DEL GALON");

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel8.setText("COSTO MANTENIMIENTO");

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel9.setText("COSTO DE REPARACION");

        txtCostoGalonGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoGalonGastoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("GASTO NO ESPECIFICADO");

        txtCostoDeReparacionGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoDeReparacionGastoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("DESCRIPCION DEL GASTO");

        txtGastoNoEspecificadoGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGastoNoEspecificadoGastoActionPerformed(evt);
            }
        });

        tblRegistroGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroGastos);

        txtCostoDeMantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoDeMantenimientoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("TIEMPO MANTENIMIENTO");

        txtTiempoMantenimientoGasto.setDateFormatString("dd/MM/yyyy");

        txtNumeroDeGalonesGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroDeGalonesGastoActionPerformed(evt);
            }
        });

        refrescarPagina.setBackground(new java.awt.Color(255, 255, 0));
        refrescarPagina.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refrescarPagina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/regresainicio (1).png"))); // NOI18N
        refrescarPagina.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        refrescarPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPaginaActionPerformed(evt);
            }
        });

        agregarFactura.setBackground(new java.awt.Color(51, 255, 51));
        agregarFactura.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        agregarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/factagrea.png"))); // NOI18N
        agregarFactura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        agregarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarFacturaActionPerformed(evt);
            }
        });

        eliminarFactura.setBackground(new java.awt.Color(255, 0, 0));
        eliminarFactura.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eliminarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/eliminarincoo.png"))); // NOI18N
        eliminarFactura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        eliminarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarFacturaActionPerformed(evt);
            }
        });

        buscarCamion.setBackground(new java.awt.Color(0, 102, 255));
        buscarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buscarCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/redimenciona buscar.png"))); // NOI18N
        buscarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        buscarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCamionActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        tblRegistroCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblRegistroCamiones);

        reparado.setBackground(new java.awt.Color(255, 204, 0));
        reparado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        reparado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/reparado.png"))); // NOI18N
        reparado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        reparado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reparadoActionPerformed(evt);
            }
        });

        txtActualizarEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descompuesto", "Funcional" }));
        txtActualizarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualizarEstadoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("ESTADO");

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("TIEMPO EN REPARACION");

        txtActualizarTiempoReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActualizarTiempoReparacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescripcionDelGastoGasto))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(txtNumeroDeGalonesGasto))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMarcaCamionBuscar))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(txtCostoGalonGasto))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtCostoDeReparacionGasto))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGastoNoEspecificadoGasto))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(26, 26, 26)
                                        .addComponent(txtCostoDeMantenimiento)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(379, 379, 379)
                                        .addComponent(reparado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(buscarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(eliminarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(agregarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(refrescarPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtActualizarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(txtActualizarTiempoReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addGap(32, 32, 32))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(refrescarPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscarCamion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(agregarFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(eliminarFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(reparado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNumeroDeGalonesGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel7))
                            .addComponent(txtCostoGalonGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostoDeReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGastoNoEspecificadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCostoDeMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActualizarTiempoReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActualizarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcionDelGastoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(66, 66, 66))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    

    
    private void txtCostoGalonGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoGalonGastoActionPerformed
   
    }//GEN-LAST:event_txtCostoGalonGastoActionPerformed

    private void txtCostoDeMantenimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeMantenimientoActionPerformed

    }//GEN-LAST:event_txtCostoDeMantenimientoActionPerformed

    private void txtCostoDeReparacionGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeReparacionGastoActionPerformed

    }//GEN-LAST:event_txtCostoDeReparacionGastoActionPerformed

    private void txtNumeroDeGalonesGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroDeGalonesGastoActionPerformed

    }//GEN-LAST:event_txtNumeroDeGalonesGastoActionPerformed

    private void refrescarPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPaginaActionPerformed
        String username = this.currentUser; // Assuming currentUser holds the username
    String role = this.userRole;        // Assuming userRole holds the role
    LOGINPINEED loginFrame = this.loginFrame; // Assuming loginFrame is already available
    
    INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES(username, role, loginFrame);
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPaginaActionPerformed

    private void agregarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarFacturaActionPerformed
  int filaSeleccionada = tblRegistroCamiones.getSelectedRow();

    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
        return;
    }

    try {
        Camiones camiones = listaCamiones.get(filaSeleccionada);
        boolean huboModificaciones = false;
        double montoTotal = 0.0;
        String tipoDeGasto = "";
        String descripcionDelGasto = "";
        String fechaMantenimientoStr = null;

        // Variables for validation
        String costoReparacionGasto = txtCostoDeReparacionGasto.getText().trim();
        String costoGalonGasto = txtCostoGalonGasto.getText().trim();
        String galonesGasto = txtNumeroDeGalonesGasto.getText().trim();
        String costoMantenimientoGasto = txtCostoDeMantenimiento.getText().trim();
        String gastoNoEspecificadoGasto = txtGastoNoEspecificadoGasto.getText().trim();
        Date fechaMantenimiento = txtTiempoMantenimientoGasto.getDate();

        // Validate and process repair cost
        if (!costoReparacionGasto.isEmpty()) {
            double nuevoCostoReparacion = Double.parseDouble(costoReparacionGasto);
            if (nuevoCostoReparacion < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                return;
            }
            camiones.setCostoReparacion(Math.max(0, camiones.getCostoReparacion() + nuevoCostoReparacion));
            montoTotal += nuevoCostoReparacion;
            tipoDeGasto = "Reparación";
            descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
            huboModificaciones = true;
        }

        // Validate and process fuel cost
        if (!costoGalonGasto.isEmpty() || !galonesGasto.isEmpty()) {
            if (costoGalonGasto.isEmpty() || galonesGasto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar tanto el costo por galón como el número de galones.");
                return;
            }
            double nuevoCostoGalon = Double.parseDouble(costoGalonGasto);
            double nuevosGalones = Double.parseDouble(galonesGasto);
            if (nuevoCostoGalon < 10) {
                JOptionPane.showMessageDialog(this, "El costo por galón no puede ser menor a Q10.");
                return;
            }
            if (nuevoCostoGalon < 0 || nuevosGalones < 0) {
                JOptionPane.showMessageDialog(this, "Los valores no pueden ser negativos.");
                return;
            }
            double costoTotalCombustibleNuevo = formatearDecimal(nuevosGalones * nuevoCostoGalon);
            double galonesTotales = camiones.getGalones() + nuevosGalones;
            double costoTotalCombustible = formatearDecimal(Math.max(0, camiones.getCostoTotalCombustible() + costoTotalCombustibleNuevo));

            camiones.setGalones(galonesTotales);
            camiones.setCostoTotalCombustible(costoTotalCombustible);

            if (galonesTotales > 0) {
                camiones.setCostoGalon(costoTotalCombustible / galonesTotales);
            }

            montoTotal += costoTotalCombustibleNuevo;
            tipoDeGasto = "Combustible";
            huboModificaciones = true;
        }

        // Validate and process maintenance cost
        if (fechaMantenimiento != null || !costoMantenimientoGasto.isEmpty()) {
            if (fechaMantenimiento == null || costoMantenimientoGasto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar tanto la fecha como el costo de mantenimiento.");
                return;
            }
            double nuevoCostoMantenimiento = Double.parseDouble(costoMantenimientoGasto);
            if (nuevoCostoMantenimiento < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fechaMantenimientoStr = sdf.format(fechaMantenimiento);
            camiones.setFechaDeMantenimiento(fechaMantenimientoStr);
            camiones.setCostoMantenimiento(Math.max(0, camiones.getCostoMantenimiento() + nuevoCostoMantenimiento));
            montoTotal += nuevoCostoMantenimiento;
            tipoDeGasto = "Mantenimiento";
            descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
            huboModificaciones = true;
        }

        // Validate and process unspecified expense
        if (!gastoNoEspecificadoGasto.isEmpty()) {
            double nuevoGastoNoEspecificado = Double.parseDouble(gastoNoEspecificadoGasto);
            if (nuevoGastoNoEspecificado < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de gastos no pueden ser negativos.");
                return;
            }
            camiones.setGastoNoEspecificado(Math.max(0, camiones.getGastoNoEspecificado() + nuevoGastoNoEspecificado));
            montoTotal += nuevoGastoNoEspecificado;
            tipoDeGasto = "No Especificado";
            descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();
            huboModificaciones = true;
        }

        // Al agregar una factura, asegúrate de que el tiempo de reparación esté excluido
        if (huboModificaciones) {
            // Actualizar el total invertido
            double totalInvertido = formatearDecimal(Math.max(0, 
                camiones.getCostoReparacion() + 
                camiones.getCostoTotalCombustible() + 
                camiones.getCostoMantenimiento() + 
                camiones.getGastoNoEspecificado()));

            camiones.setTotal(totalInvertido);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());

            LocalTime ahora = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaExacta = ahora.format(formatter);

            CAMIONESFACTURA nuevaFactura = new CAMIONESFACTURA(
                camiones.getPlacas(),
                tipoDeGasto.equals("Mantenimiento") ? fechaMantenimientoStr : fechaActual,
                tipoDeGasto,
                descripcionDelGasto,
                formatearDecimal(montoTotal),
                camiones.getEstado(),
                "", // Aquí eliminamos el tiempo de reparación
                horaExacta
            );
            gestionFacturas.setUNCAMIONNFACTURA(nuevaFactura);

            listaCamiones.set(filaSeleccionada, camiones);
            guardarCamionesEnExcel();
            gestionFacturas.guardarFacturasEnExcel();

            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");

            cargarCamionesTabla();
            actualizarTablaGastos(); // Asegúrate de que esto actualice la tabla de gastos

            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron modificaciones.");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos para los costos y galones.");
    }
    }//GEN-LAST:event_agregarFacturaActionPerformed

    

private void actualizarTablaGastos() {
    modeloRegistroGastos.setRowCount(0);
    for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
        modeloRegistroGastos.addRow(new Object[]{
            factura.getPlacasFactura(),
            factura.getFechaFactura(),  // Esta debe ser la fecha correcta
            factura.getTipoDeGastoFactura(),
            factura.getDescripcionFactura(),
            factura.getMontoFactura(),
            factura.getTiempoDeReparacionFactura(),
            factura.getHoraActual()
        });
    }
}
    



    private void guardarCamionesEnExcel() {
        gestionCamiones.guardarCamionesEnExcel();
    }

    private void refrescarTablas() {
        cargarCamionesTabla();
        cargarFacturasTabla();
    }

    
    
    
    private void eliminarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarFacturaActionPerformed
 int filaSeleccionada = tblRegistroGastos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String placaSeleccionada = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 0);
            String fechaFactura = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 1);
            double montoFactura = Double.parseDouble(tblRegistroGastos.getValueAt(filaSeleccionada, 4).toString());
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar esta factura?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                CAMIONESFACTURA facturaAEliminar = null;
                for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
                    if (factura.getPlacasFactura().equals(placaSeleccionada) && 
                        factura.getFechaFactura().equals(fechaFactura) &&
                        Math.abs(factura.getMontoFactura() - montoFactura) < 0.01) {
                        facturaAEliminar = factura;
                        break;
                    }
                }

                if (facturaAEliminar != null) {
                    for (Camiones camion : listaCamiones) {
                        if (camion.getPlacas().equals(placaSeleccionada)) {
                            actualizarCamionTrasEliminarFactura(camion, facturaAEliminar);
                            break;
                        }
                    }

                    // Eliminar la factura
                    gestionFacturas.getCamionesfactura().remove(facturaAEliminar);
                    actualizarTablaGastos();
                    cargarCamionesTabla();
                    gestionFacturas.guardarFacturasEnExcel();
                    guardarCamionesEnExcel();
                    JOptionPane.showMessageDialog(this, "Factura eliminada correctamente y costos actualizados.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo encontrar la factura exacta para eliminar.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para eliminar.");
        }
    }//GEN-LAST:event_eliminarFacturaActionPerformed
private void actualizarCamionTrasEliminarFactura(Camiones camion, CAMIONESFACTURA factura) {
    double montoFactura = factura.getMontoFactura();

    switch (factura.getTipoDeGastoFactura()) {
        case "Reparación":
            camion.setCostoReparacion(formatearDecimal(Math.max(0, camion.getCostoReparacion() - montoFactura)));
            // Buscar el tiempo de reparación anterior
            String tiempoReparacionAnterior = buscarTiempoDeReparacionAnterior(camion.getPlacas(), factura.getFechaFactura());
            camion.setTiempoEnReparacion(tiempoReparacionAnterior);
            break;
        case "Combustible":
            camion.setCostoTotalCombustible(formatearDecimal(Math.max(0, camion.getCostoTotalCombustible() - montoFactura)));
            if (camion.getCostoTotalCombustible() == 0) {
                camion.setGalones(0);
                camion.setCostoGalon(0);
            }
            break;
        case "Mantenimiento":
            camion.setCostoMantenimiento(formatearDecimal(Math.max(0, camion.getCostoMantenimiento() - montoFactura)));
            String fechaMantenimientoAnterior = buscarFechaMantenimientoAnterior(camion.getPlacas(), factura.getFechaFactura());
            camion.setFechaDeMantenimiento(fechaMantenimientoAnterior);
            break;
        case "No Especificado":
            camion.setGastoNoEspecificado(formatearDecimal(Math.max(0, camion.getGastoNoEspecificado() - montoFactura)));
            break;
    }

    double nuevoTotal = formatearDecimal(Math.max(0, 
        camion.getCostoReparacion() + 
        camion.getCostoTotalCombustible() + 
        camion.getCostoMantenimiento() + 
        camion.getGastoNoEspecificado()));
    camion.setTotal(nuevoTotal);
}

private String buscarTiempoDeReparacionAnterior(String placa, String fechaEliminada) {
    List<CAMIONESFACTURA> facturasReparacion = new ArrayList<>();
    
    // Recolectar todas las facturas de reparación para este camión
    for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
        if (factura.getPlacasFactura().equals(placa) && 
            factura.getTipoDeGastoFactura().equals("Reparación")) {
            facturasReparacion.add(factura);
        }
    }
    
    // Ordenar las facturas por fecha, de más reciente a más antigua
    facturasReparacion.sort((f1, f2) -> {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha1 = sdf.parse(f1.getFechaFactura());
            Date fecha2 = sdf.parse(f2.getFechaFactura());
            return fecha2.compareTo(fecha1);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    });
    
    // Buscar el tiempo de reparación anterior
    for (CAMIONESFACTURA factura : facturasReparacion) {
        if (!factura.getFechaFactura().equals(fechaEliminada)) {
            return factura.getTiempoDeReparacionFactura();
        }
    }
    
    return "0 días"; // Si no hay más facturas de reparación
}

    private String buscarFechaMantenimientoAnterior(String placa, String fechaEliminada) {
        List<String> fechasMantenimiento = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
            if (factura.getPlacasFactura().equals(placa) && factura.getTipoDeGastoFactura().equals("Mantenimiento")) {
                fechasMantenimiento.add(factura.getFechaFactura());
            }
        }
        
        fechasMantenimiento.sort((f1, f2) -> {
            try {
                return sdf.parse(f2).compareTo(sdf.parse(f1));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        
        for (String fecha : fechasMantenimiento) {
            if (!fecha.equals(fechaEliminada)) {
                return fecha;
            }
        }
        
        return "Ninguno";
    }

private double formatearDecimal(double valor) {
    return Math.round(valor * 100.0) / 100.0;
}
    

    
    private void txtGastoNoEspecificadoGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGastoNoEspecificadoGastoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGastoNoEspecificadoGastoActionPerformed

    private void buscarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCamionActionPerformed
// Verifica si el campo de búsqueda está vacío
    if (txtMarcaCamionBuscar.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, completa al menos un campo de búsqueda.");
        return;
    }

    // Obtiene la marca buscada
    String marcaBuscada = txtMarcaCamionBuscar.getText().trim();

    // Limpia el modelo de la tabla
    modeloCamiones.setRowCount(0);
    boolean hayCoincidencias = false;

    // Itera sobre la lista de camiones
    for (Camiones camion : listaCamiones) {
        boolean coincide = true;

        // Verifica si la marca coincide
        if (!marcaBuscada.isEmpty() && !camion.getMarca().equalsIgnoreCase(marcaBuscada)) {
            coincide = false;
        }

        // Si hay coincidencias, añade el camión a la tabla
        if (coincide) {
            modeloCamiones.addRow(new Object[]{
                camion.getPlacas(),
                camion.getMarca(),
                camion.getModelo(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje(),
                camion.getCapacidadCarga(),
                camion.getAñoFabricacion(),
                camion.getCostoReparacion(),
                camion.getCostoGalon(),
                camion.getGalones(),
                camion.getCostoMantenimiento(),
                camion.getGastoNoEspecificado(),
                camion.getDescripcionDelGasto(),
                camion.getTiempoEnReparacion(),
                camion.getFechaDeMantenimiento()
            });
            hayCoincidencias = true;
        }
    }

    // Muestra la tabla solo si hay coincidencias
    tblRegistroCamiones.setVisible(hayCoincidencias);

    // Muestra un mensaje si no se encontraron coincidencias
    if (!hayCoincidencias) {
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }

    // Limpia el campo de texto de búsqueda
    txtMarcaCamionBuscar.setText("");
    }//GEN-LAST:event_buscarCamionActionPerformed

    
    
private boolean validarFormatoTiempoReparacion(String tiempo) {
    String[] partes = tiempo.split(" ");
    if (partes.length != 2) return false;
    
    try {
        Integer.parseInt(partes[0]);
    } catch (NumberFormatException e) {
        return false;
    }
    
    String unidad = partes[1].toLowerCase();
    return unidad.equals("segundo") || unidad.equals("segundos") ||
           unidad.equals("minuto") || unidad.equals("minutos") ||
           unidad.equals("hora") || unidad.equals("horas") ||
           unidad.equals("día") || unidad.equals("días") || 
           unidad.equals("semana") || unidad.equals("semanas") || 
           unidad.equals("mes") || unidad.equals("meses") || 
           unidad.equals("año") || unidad.equals("años") ||
           unidad.equals("media hora");
}



    private void reparadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reparadoActionPerformed
int filaSeleccionada = tblRegistroCamiones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
            return;
        }

        Camiones camion = listaCamiones.get(filaSeleccionada);
        boolean huboModificaciones = false;

        // Actualizar tiempo de reparación
        String nuevoTiempoReparacion = txtActualizarTiempoReparacion.getText().trim();
        if (!nuevoTiempoReparacion.isEmpty()) {
            if (validarFormatoTiempoReparacion(nuevoTiempoReparacion)) {
                camion.setTiempoEnReparacion(nuevoTiempoReparacion);
                huboModificaciones = true;
            } else {
                JOptionPane.showMessageDialog(this, "Formato invalido. Número seguido de una unidad de tiempo válida.");
                return;
            }
        }

        // Actualizar estado
        String nuevoEstado = (String) txtActualizarEstado.getSelectedItem();
        if (!nuevoEstado.equals(camion.getEstado())) {
            camion.setEstado(nuevoEstado);
            huboModificaciones = true;
        }

        if (huboModificaciones) {
            listaCamiones.set(filaSeleccionada, camion);
            guardarCamionesEnExcel();
            refrescarTablas();
            JOptionPane.showMessageDialog(this, "Camión actualizado correctamente.");
            
            // Limpiar campos
            txtActualizarTiempoReparacion.setText("");
            txtActualizarEstado.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron modificaciones.");
        }
    }//GEN-LAST:event_reparadoActionPerformed

    private void txtActualizarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualizarEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualizarEstadoActionPerformed

    private void txtActualizarTiempoReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActualizarTiempoReparacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActualizarTiempoReparacionActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GARAGEGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                 String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole";      // Replace with actual role
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new GARAGEGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            
           
           
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarFactura;
    private javax.swing.JButton buscarCamion;
    private javax.swing.JButton eliminarFactura;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton refrescarPagina;
    private javax.swing.JButton reparado;
    private javax.swing.JTable tblRegistroCamiones;
    private javax.swing.JTable tblRegistroGastos;
    private javax.swing.JComboBox<String> txtActualizarEstado;
    private javax.swing.JTextField txtActualizarTiempoReparacion;
    private javax.swing.JTextField txtCostoDeMantenimiento;
    private javax.swing.JTextField txtCostoDeReparacionGasto;
    private javax.swing.JTextField txtCostoGalonGasto;
    private javax.swing.JTextField txtDescripcionDelGastoGasto;
    private javax.swing.JTextField txtGastoNoEspecificadoGasto;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JTextField txtNumeroDeGalonesGasto;
    private com.toedter.calendar.JDateChooser txtTiempoMantenimientoGasto;
    // End of variables declaration//GEN-END:variables
}