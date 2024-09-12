package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.regex.Pattern;


public class MOSTRARGESTIONCAMIONES extends javax.swing.JFrame {
private GESTIONCAMIONES gestionCamiones;
    private Vector<Camiones> listaCamiones;
    private DefaultTableModel modeloCamiones;
    private int indiceActual;
    
    
    public MOSTRARGESTIONCAMIONES() {
        initComponents(); // Inicializa la interfaz gráfica
        indiceActual = 0;

        // Iniciamos la gestión de camiones
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel(); // Carga los datos desde el archivo Excel

        // Definimos las columnas de la tabla de camiones
        String[] columnas = {"Placas", "Estado", "Tipo de Combustible", "Kilometraje", "Capacidad de Carga", "Año de Fabricación", "Modelo", "Marca", "Costos"};
        modeloCamiones = new DefaultTableModel();
        modeloCamiones.setColumnIdentifiers(columnas);

        // Verificamos si hay camiones y los cargamos
        listaCamiones = gestionCamiones.getCamiones();
        if (listaCamiones != null) {
            // Cargamos los camiones en la tabla
            cargarCamionesEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron camiones en la lista.");
        }
    }
    
    
    private void cargarCamionesEnTabla() {
        // Vaciamos la tabla completamente
        modeloCamiones.setRowCount(0);

        // Llenamos la tabla con los elementos del vector
        for (Camiones camiones : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camiones.getPlacas(),
                camiones.getEstado(),
                camiones.getTipoCombustible(),
                camiones.getKilometraje(),
                camiones.getCapacidadCarga(),
                camiones.getAñoFabricacion(),
                camiones.getModelo(),
                camiones.getMarca(),
                camiones.getCostos()
            });
        }
        tblRegistroCamiones.setVisible(false);
    }
    
    
    
        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtModeloCamionBuscar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPlacaCamionBuscar = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        btnBuscarCamionSistema = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnModificarCamion = new javax.swing.JButton();
        btnAgregarCamion = new javax.swing.JButton();
        btnEliminarCamion = new javax.swing.JButton();
        btnMostrarCamion = new javax.swing.JButton();
        btnListaCamion = new javax.swing.JButton();
        btnInicioCamion = new javax.swing.JButton();
        btnSalirCamion = new javax.swing.JButton();
        btnGarageCamion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" MOSTRAR CAMIONES AL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("PLACA");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPlacaCamionBuscar))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMarcaCamionBuscar)
                                        .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(182, 182, 182)))
                    .addComponent(btnBuscarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlacaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(60, 60, 60)
                .addComponent(btnBuscarCamionSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

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

        btnListaCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnListaCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnListaCamion.setText("LISTA");
        btnListaCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaCamionActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
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
                .addComponent(btnListaCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1163, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarCamionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCamionSistemaActionPerformed
       if (txtMarcaCamionBuscar.getText().trim().isEmpty() ||
        txtModeloCamionBuscar.getText().trim().isEmpty() ||
        txtPlacaCamionBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }

    // Obtener los valores ingresados
    String marcaBuscada = txtMarcaCamionBuscar.getText().trim();
    String modeloBuscado = txtModeloCamionBuscar.getText().trim();
    String placaBuscada = txtPlacaCamionBuscar.getText().trim();

    // Reiniciar el modelo de la tabla
    modeloCamiones.setRowCount(0);
    boolean hayCoincidencias = false;

    // Buscar coincidencias en la lista de camiones
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
            // Si hay coincidencias, agregar las filas al modelo de la tabla
            modeloCamiones.addRow(new Object[]{
                camion.getPlacas(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje(),
                camion.getCapacidadCarga(),
                camion.getAñoFabricacion(),
                camion.getModelo(),
                camion.getMarca(),
                camion.getCostos()
            });
            hayCoincidencias = true;
        }
    }

    // Mostrar u ocultar la tabla según si hay coincidencias
    if (hayCoincidencias) {
        tblRegistroCamiones.setVisible(true);
    } else {
        tblRegistroCamiones.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }

    // Limpiar los campos de búsqueda
    txtMarcaCamionBuscar.setText("");
    txtModeloCamionBuscar.setText("");
    txtPlacaCamionBuscar.setText("");
    }//GEN-LAST:event_btnBuscarCamionSistemaActionPerformed

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

    private void btnListaCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaCamionActionPerformed
        LISTAGESTIONCAMIONES abrir = new   LISTAGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaCamionActionPerformed

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
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MOSTRARGESTIONCAMIONES().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCamion;
    private javax.swing.JButton btnBuscarCamionSistema;
    private javax.swing.JButton btnEliminarCamion;
    private javax.swing.JButton btnGarageCamion;
    private javax.swing.JButton btnInicioCamion;
    private javax.swing.JButton btnListaCamion;
    private javax.swing.JButton btnModificarCamion;
    private javax.swing.JButton btnMostrarCamion;
    private javax.swing.JButton btnSalirCamion;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroCamiones;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JTextField txtModeloCamionBuscar;
    private javax.swing.JTextField txtPlacaCamionBuscar;
    // End of variables declaration//GEN-END:variables
}
