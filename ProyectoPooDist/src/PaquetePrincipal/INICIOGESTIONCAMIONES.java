package PaquetePrincipal;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class INICIOGESTIONCAMIONES extends javax.swing.JFrame {
    public GESTIONCAMIONES gestionCamiones;
    public Vector<Camiones> listaCamiones = new Vector<>();
    
    DefaultTableModel modeloCamiones = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public INICIOGESTIONCAMIONES() {
        initComponents();
        
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();
        
        String[] columnas = {"Placas", "Modelo", "Marca", "Estado", "Tipo Combustible", "Kilometraje"};
        modeloCamiones.setColumnIdentifiers(columnas);
        
        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
        }
        
        tblRegistroCamiones.setModel(modeloCamiones);
        tblRegistroCamiones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRegistroCamiones.getTableHeader().setReorderingAllowed(false);
        tblRegistroCamiones.getTableHeader().setResizingAllowed(false);
        tblRegistroCamiones.setRowSelectionAllowed(true);
        tblRegistroCamiones.setColumnSelectionAllowed(false);
        
        cargarCamionesEnTabla();
    }

    private void cargarCamionesEnTabla() {
        for (Camiones camion : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camion.getPlacas(),
                camion.getModelo(),
                camion.getMarca(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje()
            });
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jTextField18 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btnGestionDeVentas2 = new javax.swing.JButton();
        btnGestionDePedidos2 = new javax.swing.JButton();
        btnPlanillaDeTrabajadores2 = new javax.swing.JButton();
        btnGestionDeClientes2 = new javax.swing.JButton();
        btnGestionDeCreditos2 = new javax.swing.JButton();
        btnInventarioDeQuintales2 = new javax.swing.JButton();
        btnCerrarSesion2 = new javax.swing.JButton();
        btnCalendario2 = new javax.swing.JButton();
        btnGestionDePilotos2 = new javax.swing.JButton();
        btnGestionDeCamiones2 = new javax.swing.JButton();
        btnGestionDeUsuarios2 = new javax.swing.JButton();
        btnRegresarLogin2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        mostrarPiloto = new javax.swing.JButton();
        modificarPiloto = new javax.swing.JButton();
        buscarPiloto = new javax.swing.JButton();
        refrescarPagina = new javax.swing.JButton();
        refrescarPagina1 = new javax.swing.JButton();
        refrescarPagina2 = new javax.swing.JButton();
        refrescarPagina3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel8.setBackground(new java.awt.Color(6, 40, 86));

        jTextField18.setEditable(false);
        jTextField18.setBackground(new java.awt.Color(0, 153, 153));
        jTextField18.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setText(" GESTION DE CAMIONES");
        jTextField18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jPanel10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnGestionDeVentas2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeVentas2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeVentas2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeVentas2.setText("GESTION DE VENTAS");
        btnGestionDeVentas2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeVentas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeVentas2ActionPerformed(evt);
            }
        });

        btnGestionDePedidos2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDePedidos2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDePedidos2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDePedidos2.setText("GESTION DE PEDIDOS");
        btnGestionDePedidos2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDePedidos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDePedidos2ActionPerformed(evt);
            }
        });

        btnPlanillaDeTrabajadores2.setBackground(new java.awt.Color(0, 102, 102));
        btnPlanillaDeTrabajadores2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPlanillaDeTrabajadores2.setForeground(new java.awt.Color(255, 255, 255));
        btnPlanillaDeTrabajadores2.setText("PLANILLA DE TRABAJADORES");
        btnPlanillaDeTrabajadores2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnPlanillaDeTrabajadores2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanillaDeTrabajadores2ActionPerformed(evt);
            }
        });

        btnGestionDeClientes2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeClientes2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeClientes2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeClientes2.setText("GESTION DE CLIENTES");
        btnGestionDeClientes2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeClientes2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeClientes2ActionPerformed(evt);
            }
        });

        btnGestionDeCreditos2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeCreditos2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeCreditos2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeCreditos2.setText("GESTION DE CREDITOS");
        btnGestionDeCreditos2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeCreditos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeCreditos2ActionPerformed(evt);
            }
        });

        btnInventarioDeQuintales2.setBackground(new java.awt.Color(0, 102, 102));
        btnInventarioDeQuintales2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInventarioDeQuintales2.setForeground(new java.awt.Color(255, 255, 255));
        btnInventarioDeQuintales2.setText("INVENTARIO QUINTALES");
        btnInventarioDeQuintales2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInventarioDeQuintales2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioDeQuintales2ActionPerformed(evt);
            }
        });

        btnCerrarSesion2.setBackground(new java.awt.Color(0, 102, 102));
        btnCerrarSesion2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCerrarSesion2.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion2.setText("CERRAR SESION");
        btnCerrarSesion2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnCerrarSesion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesion2ActionPerformed(evt);
            }
        });

        btnCalendario2.setBackground(new java.awt.Color(0, 102, 102));
        btnCalendario2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCalendario2.setForeground(new java.awt.Color(255, 255, 255));
        btnCalendario2.setText("CALENDARIO");
        btnCalendario2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnCalendario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendario2ActionPerformed(evt);
            }
        });

        btnGestionDePilotos2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDePilotos2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDePilotos2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDePilotos2.setText("GESTION DE PILOTOS");
        btnGestionDePilotos2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDePilotos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDePilotos2ActionPerformed(evt);
            }
        });

        btnGestionDeCamiones2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeCamiones2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeCamiones2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeCamiones2.setText("GESTION DE CAMIONES");
        btnGestionDeCamiones2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeCamiones2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeCamiones2ActionPerformed(evt);
            }
        });

        btnGestionDeUsuarios2.setBackground(new java.awt.Color(0, 102, 102));
        btnGestionDeUsuarios2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGestionDeUsuarios2.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionDeUsuarios2.setText("GESTION DE USUARIOS");
        btnGestionDeUsuarios2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGestionDeUsuarios2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionDeUsuarios2ActionPerformed(evt);
            }
        });

        btnRegresarLogin2.setBackground(new java.awt.Color(0, 102, 102));
        btnRegresarLogin2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegresarLogin2.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresarLogin2.setText("REGRESAR LOGIN");
        btnRegresarLogin2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnRegresarLogin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarLogin2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRegresarLogin2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionDeUsuarios2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionDeCamiones2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionDePilotos2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCalendario2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionDeCreditos2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGestionDeClientes2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnGestionDeVentas2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPlanillaDeTrabajadores2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGestionDePedidos2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInventarioDeQuintales2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnCerrarSesion2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInventarioDeQuintales2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDePedidos2)
                .addGap(12, 12, 12)
                .addComponent(btnPlanillaDeTrabajadores2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeVentas2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeClientes2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeCreditos2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCalendario2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDePilotos2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeCamiones2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGestionDeUsuarios2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegresarLogin2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrarSesion2)
                .addContainerGap())
        );

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

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        mostrarPiloto.setBackground(new java.awt.Color(204, 204, 255));
        mostrarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mostrarPiloto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/redicmonacionas mostrar.png"))); // NOI18N
        mostrarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        mostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarPilotoActionPerformed(evt);
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

        refrescarPagina1.setBackground(new java.awt.Color(153, 153, 153));
        refrescarPagina1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refrescarPagina1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/llavescamiones-removebg-preview.png"))); // NOI18N
        refrescarPagina1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        refrescarPagina1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPagina1ActionPerformed(evt);
            }
        });

        refrescarPagina2.setBackground(new java.awt.Color(51, 255, 102));
        refrescarPagina2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refrescarPagina2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/agregar camion redimencionado.png"))); // NOI18N
        refrescarPagina2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        refrescarPagina2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPagina2ActionPerformed(evt);
            }
        });

        refrescarPagina3.setBackground(new java.awt.Color(255, 0, 0));
        refrescarPagina3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refrescarPagina3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PaquetePrincipal/eliminar camion redimencionado.png"))); // NOI18N
        refrescarPagina3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        refrescarPagina3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarPagina3ActionPerformed(evt);
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
                        .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(refrescarPagina1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refrescarPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refrescarPagina3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(modificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refrescarPagina2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarPiloto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refrescarPagina2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refrescarPagina3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refrescarPagina, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refrescarPagina1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed

    }//GEN-LAST:event_jTextField18ActionPerformed

    private void btnGestionDeVentas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeVentas2ActionPerformed

    }//GEN-LAST:event_btnGestionDeVentas2ActionPerformed

    private void btnGestionDePedidos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDePedidos2ActionPerformed

    }//GEN-LAST:event_btnGestionDePedidos2ActionPerformed

    private void btnPlanillaDeTrabajadores2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanillaDeTrabajadores2ActionPerformed
        FramePlanillaSemanal abrir = new  FramePlanillaSemanal();
        abrir.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPlanillaDeTrabajadores2ActionPerformed

    private void btnGestionDeClientes2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeClientes2ActionPerformed

    }//GEN-LAST:event_btnGestionDeClientes2ActionPerformed

    private void btnGestionDeCreditos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeCreditos2ActionPerformed

    }//GEN-LAST:event_btnGestionDeCreditos2ActionPerformed

    private void btnInventarioDeQuintales2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioDeQuintales2ActionPerformed
        FrameInventario abrir = new  FrameInventario();
        abrir.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInventarioDeQuintales2ActionPerformed

    private void btnCerrarSesion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesion2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCerrarSesion2ActionPerformed

    private void btnCalendario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendario2ActionPerformed

    }//GEN-LAST:event_btnCalendario2ActionPerformed

    private void btnGestionDePilotos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDePilotos2ActionPerformed
        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDePilotos2ActionPerformed

    private void btnGestionDeCamiones2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeCamiones2ActionPerformed
        INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDeCamiones2ActionPerformed

    private void btnGestionDeUsuarios2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionDeUsuarios2ActionPerformed
        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGestionDeUsuarios2ActionPerformed

    private void btnRegresarLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarLogin2ActionPerformed
        LOGINPINEED abrir = new  LOGINPINEED();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarLogin2ActionPerformed

    private void modificarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarPilotoActionPerformed
        int filaSeleccionada = tblRegistroCamiones.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Camiones camionSeleccionado = listaCamiones.get(filaSeleccionada);
            abrirVentanaModificar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un camión para modificar.");
        }
    }//GEN-LAST:event_modificarPilotoActionPerformed

    private void mostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarPilotoActionPerformed
        int filaSeleccionada = tblRegistroCamiones.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Camiones camionSeleccionado = listaCamiones.get(filaSeleccionada);
            abrirVentanaMostrar(camionSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión para mostrar su información.");
        }
    }//GEN-LAST:event_mostrarPilotoActionPerformed

    private void buscarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarPilotoActionPerformed
        if (txtMarcaCamionBuscar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa la marca del camión para buscar.");
            return;
        }

        String marcaBuscada = txtMarcaCamionBuscar.getText().trim();
        modeloCamiones.setRowCount(0);
        boolean hayCoincidencias = false;

        for (Camiones camion : listaCamiones) {
            if (camion.getMarca().toLowerCase().contains(marcaBuscada.toLowerCase())) {
                modeloCamiones.addRow(new Object[]{
                    camion.getPlacas(),
                    camion.getModelo(),
                    camion.getMarca(),
                    camion.getEstado(),
                    camion.getTipoCombustible(),
                    camion.getKilometraje()
                });
                hayCoincidencias = true;
            }
        }

        if (!hayCoincidencias) {
            JOptionPane.showMessageDialog(this, "No se encontraron camiones de la marca especificada.");
            cargarCamionesEnTabla();
        } else {
            tblRegistroCamiones.setVisible(true);
            if (tblRegistroCamiones.getRowCount() > 0) {
                tblRegistroCamiones.setRowSelectionInterval(0, 0);
            }
        }

        txtMarcaCamionBuscar.setText("");
    }//GEN-LAST:event_buscarPilotoActionPerformed

    private void refrescarPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPaginaActionPerformed
        INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPaginaActionPerformed

    private void refrescarPagina1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPagina1ActionPerformed
        GARAGEGESTIONCAMIONES abrir = new GARAGEGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPagina1ActionPerformed

    private void refrescarPagina2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPagina2ActionPerformed
        AGREGARGESTIONCAMIONES abrir = new AGREGARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_refrescarPagina2ActionPerformed

    private void refrescarPagina3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarPagina3ActionPerformed
        int filaSeleccionada = tblRegistroCamiones.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String placasSeleccionadas = (String) tblRegistroCamiones.getValueAt(filaSeleccionada, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas borrar este camión con placas: " + placasSeleccionadas + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gestionCamiones.eliminarCamion(placasSeleccionadas);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Camión eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión para eliminar.");
        }
    }//GEN-LAST:event_refrescarPagina3ActionPerformed

    

    public void actualizarTabla() {
        gestionCamiones.cargarCamionesDesdeExcel();
        listaCamiones = gestionCamiones.getCamiones();
        modeloCamiones.setRowCount(0);
        cargarCamionesEnTabla();
    }

    private void abrirVentanaModificar(Camiones camion) {
        MODIFICARGESTIONCAMIONES ventanaModificar = new MODIFICARGESTIONCAMIONES(camion, this);
        ventanaModificar.setVisible(true);
        this.setVisible(false);
    }

    private void abrirVentanaMostrar(Camiones camion) {
        MOSTRARGESTIONCAMIONES ventanaMostrar = new MOSTRARGESTIONCAMIONES(camion, this);
        ventanaMostrar.setVisible(true);
    }
    
    
    

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
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIOGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new INICIOGESTIONCAMIONES().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalendario2;
    private javax.swing.JButton btnCerrarSesion2;
    private javax.swing.JButton btnGestionDeCamiones2;
    private javax.swing.JButton btnGestionDeClientes2;
    private javax.swing.JButton btnGestionDeCreditos2;
    private javax.swing.JButton btnGestionDePedidos2;
    private javax.swing.JButton btnGestionDePilotos2;
    private javax.swing.JButton btnGestionDeUsuarios2;
    private javax.swing.JButton btnGestionDeVentas2;
    private javax.swing.JButton btnInventarioDeQuintales2;
    private javax.swing.JButton btnPlanillaDeTrabajadores2;
    private javax.swing.JButton btnRegresarLogin2;
    private javax.swing.JButton buscarPiloto;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JButton modificarPiloto;
    private javax.swing.JButton mostrarPiloto;
    private javax.swing.JButton refrescarPagina;
    private javax.swing.JButton refrescarPagina1;
    private javax.swing.JButton refrescarPagina2;
    private javax.swing.JButton refrescarPagina3;
    private javax.swing.JTable tblRegistroCamiones;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    // End of variables declaration//GEN-END:variables
}
