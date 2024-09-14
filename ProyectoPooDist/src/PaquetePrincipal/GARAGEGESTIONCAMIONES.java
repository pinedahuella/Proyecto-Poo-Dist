package PaquetePrincipal;

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


public class GARAGEGESTIONCAMIONES extends javax.swing.JFrame {
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    public FACTURASGESTIONCAMIONES gestionFacturas;
    DefaultTableModel modeloCamiones = new DefaultTableModel();
    DefaultTableModel modeloRegistroGastos = new DefaultTableModel();

    private int indiceActual;


    public GARAGEGESTIONCAMIONES() {
        initComponents();
        indiceActual = 0;
        
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        
        gestionFacturas = new FACTURASGESTIONCAMIONES();
        gestionFacturas.cargarFacturasDesdeExcel();
        
        
   
        

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
            "Placas", "Fecha", "Tipo de Gasto", "Descripción", "Monto", "Estado", "Tiempo de Reparación", "Hora Registrado"
        };
        modeloRegistroGastos.setColumnIdentifiers(columnasFacturas);
        tblRegistroGastos.setModel(modeloRegistroGastos);
        
        cargarFacturasTabla();
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
                factura.getEstadoFactura(),
                factura.getTiempoDeReparacionFactura(),
                factura.getHoraActual()
            });
        }
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
        txtTiempoEnReparacionGasto.setText("");
        txtDescripcionDelGastoGasto.setText("");
        txtCostoDeMantenimiento.setText("");
        txtGastoNoEspecificadoGasto.setText("");
        txtTiempoMantenimientoGasto.setDate(null);
    }
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnModificarCamion = new javax.swing.JButton();
        btnAgregarCamion = new javax.swing.JButton();
        btnEliminarCamion = new javax.swing.JButton();
        btnMostrarCamion = new javax.swing.JButton();
        btnListaPCamion = new javax.swing.JButton();
        btnInicioCamion = new javax.swing.JButton();
        btnSalirCamion = new javax.swing.JButton();
        btnGarageCamion = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
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
        btnIngresarGasto = new javax.swing.JButton();
        txtCostoDeMantenimiento = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        btnBuscarCamionSistema = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTiempoMantenimientoGasto = new com.toedter.calendar.JDateChooser();
        txtTiempoEnReparacionGasto = new javax.swing.JTextField();
        txtNumeroDeGalonesGasto = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoGasto = new javax.swing.JComboBox<>();
        txtPlacaCamionBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtModeloCamionBuscar = new javax.swing.JTextField();
        btnBorrarGasto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        btnModificarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCamion.setText("MODIFICAR");
        btnModificarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCamionActionPerformed(evt);
            }
        });

        btnAgregarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCamion.setText("AGREGAR");
        btnAgregarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCamionActionPerformed(evt);
            }
        });

        btnEliminarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCamion.setText("ELIMINAR");
        btnEliminarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCamionActionPerformed(evt);
            }
        });

        btnMostrarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarCamion.setText("MOSTRAR");
        btnMostrarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCamionActionPerformed(evt);
            }
        });

        btnListaPCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnListaPCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaPCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnListaPCamion.setText("LISTA");
        btnListaPCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaPCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPCamionActionPerformed(evt);
            }
        });

        btnInicioCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioCamion.setText("INICIO");
        btnInicioCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioCamionActionPerformed(evt);
            }
        });

        btnSalirCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirCamion.setText("SALIR");
        btnSalirCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCamionActionPerformed(evt);
            }
        });

        btnGarageCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnGarageCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGarageCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnGarageCamion.setText("GARAGE");
        btnGarageCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGarageCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGarageCamionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaPCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" GARAGE DEL CAMION EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

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

        btnIngresarGasto.setBackground(new java.awt.Color(0, 102, 255));
        btnIngresarGasto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIngresarGasto.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresarGasto.setText("INGRESAR GASTOS");
        btnIngresarGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnIngresarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarGastoActionPerformed(evt);
            }
        });

        txtCostoDeMantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoDeMantenimientoActionPerformed(evt);
            }
        });

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
        jScrollPane3.setViewportView(tblRegistroCamiones);

        btnBuscarCamionSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarCamionSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarCamionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCamionSistema.setText("BUSCAR PILOTO");
        btnBuscarCamionSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarCamionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCamionSistemaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("TIEMPO MANTENIMIENTO");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("TIEMPO REPARACION");

        txtTiempoMantenimientoGasto.setDateFormatString("dd/MM/yyyy");

        txtTiempoEnReparacionGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTiempoEnReparacionGastoActionPerformed(evt);
            }
        });

        txtNumeroDeGalonesGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroDeGalonesGastoActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        txtEstadoGasto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En viaje", "En el taller", "Descompuesto", "Libre" }));
        txtEstadoGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoGastoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("PLACA");

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("MARCA");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("MODELO");

        btnBorrarGasto.setBackground(new java.awt.Color(0, 102, 255));
        btnBorrarGasto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBorrarGasto.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarGasto.setText("BORRAR FACTURA");
        btnBorrarGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBorrarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarGastoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(287, 287, 287)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCostoDeMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtGastoNoEspecificadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtEstadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(159, 159, 159)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNumeroDeGalonesGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCostoGalonGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtCostoDeReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTiempoEnReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionDelGastoGasto)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(89, 89, 89)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMarcaCamionBuscar)
                                    .addComponent(txtPlacaCamionBuscar)))
                            .addComponent(btnIngresarGasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBorrarGasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(btnBuscarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnBuscarCamionSistema))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtPlacaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDescripcionDelGastoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGastoNoEspecificadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGap(40, 40, 40)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtEstadoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel18))))
                                    .addGap(21, 21, 21)
                                    .addComponent(txtTiempoMantenimientoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(123, 123, 123)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtCostoDeMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtNumeroDeGalonesGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(txtCostoGalonGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCostoDeReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(txtTiempoEnReparacionGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnIngresarGasto)
                        .addGap(24, 24, 24)
                        .addComponent(btnBorrarGasto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(568, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(178, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1185, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCamionActionPerformed
        MODIFICARGESTIONCAMIONES abrir = new   MODIFICARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_btnModificarCamionActionPerformed

    private void btnAgregarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCamionActionPerformed
        AGREGARGESTIONCAMIONES abrir = new  AGREGARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarCamionActionPerformed

    private void btnEliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCamionActionPerformed
        ELIMINARGESTIONCAMIONES abrir = new  ELIMINARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarCamionActionPerformed

    private void btnMostrarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCamionActionPerformed
        MOSTRARGESTIONCAMIONES abrir = new   MOSTRARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarCamionActionPerformed

    private void btnListaPCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaPCamionActionPerformed
        LISTAGESTIONCAMIONES abrir = new   LISTAGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaPCamionActionPerformed

    private void btnInicioCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioCamionActionPerformed
        INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioCamionActionPerformed

    private void btnSalirCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCamionActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirCamionActionPerformed

    private void btnGarageCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGarageCamionActionPerformed
        GARAGEGESTIONCAMIONES abrir = new GARAGEGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGarageCamionActionPerformed

    private void btnIngresarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarGastoActionPerformed
 int filaSeleccionada = tblRegistroCamiones.getSelectedRow();

    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
        return;
    }

    try {
        Camiones camiones = listaCamiones.get(filaSeleccionada);

        String costoReparacionGasto = txtCostoDeReparacionGasto.getText().trim();
        String costoGalonGasto = txtCostoGalonGasto.getText().trim();
        String galonesGasto = txtNumeroDeGalonesGasto.getText().trim();
        String costoMantenimientoGasto = txtCostoDeMantenimiento.getText().trim();
        String gastoNoEspecificadoGasto = txtGastoNoEspecificadoGasto.getText().trim();
        String descripcionDelGasto = txtDescripcionDelGastoGasto.getText().trim();

        String nuevoEstado = txtEstadoGasto.getSelectedItem().toString().trim();
        Date fechaMantenimiento = txtTiempoMantenimientoGasto.getDate();

        
         String tiempoEnReparacion = txtTiempoEnReparacionGasto.getText().trim();
    if (!tiempoEnReparacion.isEmpty() && !validarFormatoTiempoReparacion(tiempoEnReparacion)) {
        JOptionPane.showMessageDialog(this, "Formato de tiempo de reparación inválido. Use el selector de tiempo.");
        return;
    }
    
        boolean huboModificaciones = false;
        double montoTotal = 0.0;
        String tipoDeGasto = "";

        if (!costoReparacionGasto.isEmpty()) {
            double nuevoCostoReparacion = Double.parseDouble(costoReparacionGasto);
            if (nuevoCostoReparacion < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                return;
            }
            camiones.setCostoReparacion(camiones.getCostoReparacion() + nuevoCostoReparacion);
            camiones.setTiempoEnReparacion(tiempoEnReparacion);
            montoTotal += nuevoCostoReparacion;
            tipoDeGasto = "Reparación";
            huboModificaciones = true;
        }

        if (!costoGalonGasto.isEmpty() && !galonesGasto.isEmpty()) {
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
            
            double costoTotalCombustibleNuevo = nuevosGalones * nuevoCostoGalon;
            double galonesTotales = camiones.getGalones() + nuevosGalones;
            double costoTotalCombustible = camiones.getCostoTotalCombustible() + costoTotalCombustibleNuevo;
            
            camiones.setGalones(galonesTotales);
            camiones.setCostoTotalCombustible(costoTotalCombustible);
            
            if (galonesTotales > 0) {
                camiones.setCostoGalon(costoTotalCombustible / galonesTotales);
            }
            
            montoTotal += costoTotalCombustibleNuevo;
            tipoDeGasto = "Combustible";
            huboModificaciones = true;
        }

        if (!costoMantenimientoGasto.isEmpty()) {
            double nuevoCostoMantenimiento = Double.parseDouble(costoMantenimientoGasto);
            if (nuevoCostoMantenimiento < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de costos no pueden ser negativos.");
                return;
            }
            camiones.setCostoMantenimiento(camiones.getCostoMantenimiento() + nuevoCostoMantenimiento);
            montoTotal += nuevoCostoMantenimiento;
            tipoDeGasto = "Mantenimiento";
            huboModificaciones = true;
        }

        if (!gastoNoEspecificadoGasto.isEmpty()) {
            double nuevoGastoNoEspecificado = Double.parseDouble(gastoNoEspecificadoGasto);
            if (nuevoGastoNoEspecificado < 0) {
                JOptionPane.showMessageDialog(this, "Los valores de gastos no pueden ser negativos.");
                return;
            }
            camiones.setGastoNoEspecificado(camiones.getGastoNoEspecificado() + nuevoGastoNoEspecificado);
            camiones.setDescripcionDelGasto(descripcionDelGasto);
            montoTotal += nuevoGastoNoEspecificado;
            tipoDeGasto = "No Especificado";
            huboModificaciones = true;
        }

        if (!nuevoEstado.equals(camiones.getEstado())) {
            camiones.setEstado(nuevoEstado);
            huboModificaciones = true;
        }

        if (huboModificaciones) {
            double totalInvertido = camiones.getCostoReparacion() + 
                                    camiones.getCostoTotalCombustible() + 
                                    camiones.getCostoMantenimiento() + 
                                    camiones.getGastoNoEspecificado();
            camiones.setTotal(totalInvertido);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());
            camiones.setFechaDeMantenimiento(fechaActual);

            listaCamiones.set(filaSeleccionada, camiones);


            LocalTime ahora = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaExacta = ahora.format(formatter);

            CAMIONESFACTURA nuevaFactura = new CAMIONESFACTURA(
                camiones.getPlacas(),
                fechaActual,
                tipoDeGasto,
                descripcionDelGasto,
                montoTotal,
                nuevoEstado,
                tiempoEnReparacion,
                horaExacta
            );
            gestionFacturas.setUNCAMIONNFACTURA(nuevaFactura);

    
            guardarCamionesEnExcel();
            gestionFacturas.guardarFacturasEnExcel();

            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");

            refrescarTablas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron modificaciones.");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos para los costos y galones.");
    }
    }//GEN-LAST:event_btnIngresarGastoActionPerformed

    
    
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


private void mostrarOpcionesTiempoReparacion() {
    String[] opciones = {
        "segundos", "minutos", "horas", "media hora",
        "días", "semanas", "meses", "años"
    };
    
    String seleccion = (String) JOptionPane.showInputDialog(
        this,
        "Seleccione la unidad de tiempo para la reparación:",
        "Tiempo de Reparación",
        JOptionPane.QUESTION_MESSAGE,
        null,
        opciones,
        opciones[0]
    );

    if (seleccion != null) {
        String cantidad = JOptionPane.showInputDialog(
            this,
            "Ingrese la cantidad de " + seleccion + ":",
            "Tiempo de Reparación",
            JOptionPane.QUESTION_MESSAGE
        );

        if (cantidad != null && !cantidad.isEmpty()) {
            if (seleccion.equals("media hora")) {
                txtTiempoEnReparacionGasto.setText("0.5 horas");
            } else {
                txtTiempoEnReparacionGasto.setText(cantidad + " " + seleccion);
            }
        }
    }
}


    private void guardarCamionesEnExcel() {
        gestionCamiones.guardarCamionesEnExcel();
    }

    private void refrescarTablas() {
        cargarCamionesTabla();
        cargarFacturasTabla();
    }

    
    private void btnBuscarCamionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCamionSistemaActionPerformed
                    if (txtMarcaCamionBuscar.getText().trim().isEmpty() &&
                       txtModeloCamionBuscar.getText().trim().isEmpty() &&
                       txtPlacaCamionBuscar.getText().trim().isEmpty()) {

                       JOptionPane.showMessageDialog(this, "Por favor, completa al menos un campo de búsqueda.");
                       return;
                   }

              
                   String marcaBuscada = txtMarcaCamionBuscar.getText().trim();
                   String modeloBuscado = txtModeloCamionBuscar.getText().trim();
                   String placaBuscada = txtPlacaCamionBuscar.getText().trim();

                   modeloCamiones.setRowCount(0);
                   boolean hayCoincidencias = false;

                   for (Camiones camion : listaCamiones) {
                       boolean coincide = true;

                     
                       if (!marcaBuscada.isEmpty() && !camion.getMarca().equalsIgnoreCase(marcaBuscada)) {
                           coincide = false;
                       }

               
                       if (!modeloBuscado.isEmpty() && !camion.getModelo().equalsIgnoreCase(modeloBuscado)) {
                           coincide = false;
                       }

                   
                       if (!placaBuscada.isEmpty() && !camion.getPlacas().equalsIgnoreCase(placaBuscada)) {
                           coincide = false;
                       }

                
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

 
                   tblRegistroCamiones.setVisible(hayCoincidencias);
                   if (!hayCoincidencias) {
                       JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
                   }


                   txtMarcaCamionBuscar.setText("");
                   txtModeloCamionBuscar.setText("");
                   txtPlacaCamionBuscar.setText("");
    }//GEN-LAST:event_btnBuscarCamionSistemaActionPerformed

    private void txtTiempoEnReparacionGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTiempoEnReparacionGastoActionPerformed

    }//GEN-LAST:event_txtTiempoEnReparacionGastoActionPerformed

    private void txtCostoGalonGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoGalonGastoActionPerformed
   
    }//GEN-LAST:event_txtCostoGalonGastoActionPerformed

    private void txtCostoDeMantenimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeMantenimientoActionPerformed

    }//GEN-LAST:event_txtCostoDeMantenimientoActionPerformed

    private void txtCostoDeReparacionGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoDeReparacionGastoActionPerformed

    }//GEN-LAST:event_txtCostoDeReparacionGastoActionPerformed

    private void txtNumeroDeGalonesGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroDeGalonesGastoActionPerformed

    }//GEN-LAST:event_txtNumeroDeGalonesGastoActionPerformed

    private void txtEstadoGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoGastoActionPerformed

    }//GEN-LAST:event_txtEstadoGastoActionPerformed

    private void btnBorrarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarGastoActionPerformed
 int filaSeleccionada = tblRegistroGastos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        String placaSeleccionada = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 0);
        String fechaFactura = (String) tblRegistroGastos.getValueAt(filaSeleccionada, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas borrar esta factura?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            CAMIONESFACTURA facturaAEliminar = null;
            for (CAMIONESFACTURA factura : gestionFacturas.getCamionesfactura()) {
                if (factura.getPlacasFactura().equals(placaSeleccionada) && 
                    factura.getFechaFactura().equals(fechaFactura)) {
                    facturaAEliminar = factura;
                    break;
                }
            }
            
            if (facturaAEliminar != null) {

                for (Camiones camion : listaCamiones) {
                    if (camion.getPlacas().equals(placaSeleccionada)) {
                        double montoFactura = facturaAEliminar.getMontoFactura();
                        camion.setTotal(camion.getTotal() - montoFactura);
                        
                        // Actualizar otros campos según el tipo de gasto
                        switch (facturaAEliminar.getTipoDeGastoFactura()) {
                            case "Reparación":
                                camion.setCostoReparacion(camion.getCostoReparacion() - montoFactura);
                                break;
                            case "Combustible":
                                camion.setCostoTotalCombustible(camion.getCostoTotalCombustible() - montoFactura);
                                break;
                            case "Mantenimiento":
                                camion.setCostoMantenimiento(camion.getCostoMantenimiento() - montoFactura);
                                break;
                            case "No Especificado":
                                camion.setGastoNoEspecificado(camion.getGastoNoEspecificado() - montoFactura);
                                break;
                        }
                        break;
                    }
                }
                
                gestionFacturas.getCamionesfactura().remove(facturaAEliminar);
                cargarFacturasTabla();
                cargarCamionesTabla(); // Actualizar la tabla de camiones
                gestionFacturas.guardarFacturasEnExcel();
                guardarCamionesEnExcel(); // Guardar los cambios en los camiones
                JOptionPane.showMessageDialog(this, "Factura eliminada correctamente y costos actualizados.");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para eliminar.");
    }
    }//GEN-LAST:event_btnBorrarGastoActionPerformed

    
    
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
                new GARAGEGESTIONCAMIONES().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCamion;
    private javax.swing.JButton btnBorrarGasto;
    private javax.swing.JButton btnBuscarCamionSistema;
    private javax.swing.JButton btnEliminarCamion;
    private javax.swing.JButton btnGarageCamion;
    private javax.swing.JButton btnIngresarGasto;
    private javax.swing.JButton btnInicioCamion;
    private javax.swing.JButton btnListaPCamion;
    private javax.swing.JButton btnModificarCamion;
    private javax.swing.JButton btnMostrarCamion;
    private javax.swing.JButton btnSalirCamion;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroCamiones;
    private javax.swing.JTable tblRegistroGastos;
    private javax.swing.JTextField txtCostoDeMantenimiento;
    private javax.swing.JTextField txtCostoDeReparacionGasto;
    private javax.swing.JTextField txtCostoGalonGasto;
    private javax.swing.JTextField txtDescripcionDelGastoGasto;
    private javax.swing.JComboBox<String> txtEstadoGasto;
    private javax.swing.JTextField txtGastoNoEspecificadoGasto;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JTextField txtModeloCamionBuscar;
    private javax.swing.JTextField txtNumeroDeGalonesGasto;
    private javax.swing.JTextField txtPlacaCamionBuscar;
    private javax.swing.JTextField txtTiempoEnReparacionGasto;
    private com.toedter.calendar.JDateChooser txtTiempoMantenimientoGasto;
    // End of variables declaration//GEN-END:variables
}
