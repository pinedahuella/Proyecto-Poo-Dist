package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ELIMINARGESTIONPILOTOS extends javax.swing.JFrame {

    // Variables globales
    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel(); // Nombre corregido
    private int indiceActual;

    // Constructor
    public ELIMINARGESTIONPILOTOS() {
        initComponents();
        indiceActual = 0;

        // Iniciamos la gestión de pilotos
        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();

        // Definimos las columnas de la tabla de pilotos
        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Correo", "Teléfono", "Género", "Nacimiento", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);

        // Obtenemos la lista de pilotos
        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }

        // Cargamos los pilotos en la tabla
        tblRegistroPilotos.setModel(modeloPilotos);

        cargarPilotosEnTabla(); // Llamada al método para cargar pilotos
    }

    // Método para cargar los pilotos en la tabla
    private void cargarPilotosEnTabla() {
        // Vaciamos la tabla completamente
        modeloPilotos.setRowCount(0);

        // Llenamos la tabla con los elementos de la lista
        for (Piloto piloto : listaPilotos) {
            modeloPilotos.addRow(new Object[]{
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento(),
                piloto.getEstadoPiloto()
            });
        }

        tblRegistroPilotos.setVisible(true); // Hacer visible la tabla
    }

    
  
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoPilotoBuscar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoBuscar = new javax.swing.JTextField();
        btnEliminarPilotoSistema = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        btnBuscarPilotoSistema = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnModificarPiloto = new javax.swing.JButton();
        btnAgregarPiloto = new javax.swing.JButton();
        btnEliminarPiloto = new javax.swing.JButton();
        btnMostrarPiloto = new javax.swing.JButton();
        btnListaPiloto = new javax.swing.JButton();
        btnInicioPiloto = new javax.swing.JButton();
        btnSalirPiloto = new javax.swing.JButton();
        btnEstadoPiloto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" ELIMINAR PILOTO AL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        btnEliminarPilotoSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnEliminarPilotoSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPilotoSistema.setText("ELIMINAR");
        btnEliminarPilotoSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPilotoSistemaActionPerformed(evt);
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

        btnBuscarPilotoSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarPilotoSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPilotoSistema.setText("BUSCAR PILOTO");
        btnBuscarPilotoSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPilotoSistemaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(6, 629, Short.MAX_VALUE)
                        .addComponent(btnEliminarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroDeDpiPilotoBuscar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtApellidoPilotoBuscar)
                                    .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnBuscarPilotoSistema)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnEliminarPilotoSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        btnModificarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPiloto.setText("MODIFICAR");
        btnModificarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPilotoActionPerformed(evt);
            }
        });

        btnAgregarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPiloto.setText("AGREGAR");
        btnAgregarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPilotoActionPerformed(evt);
            }
        });

        btnEliminarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPiloto.setText("ELIMINAR");
        btnEliminarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPilotoActionPerformed(evt);
            }
        });

        btnMostrarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPiloto.setText("MOSTRAR");
        btnMostrarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotoActionPerformed(evt);
            }
        });

        btnListaPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnListaPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnListaPiloto.setText("LISTA");
        btnListaPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPilotoActionPerformed(evt);
            }
        });

        btnInicioPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioPiloto.setText("INICIO");
        btnInicioPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioPilotoActionPerformed(evt);
            }
        });

        btnSalirPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirPiloto.setText("SALIR");
        btnSalirPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPilotoActionPerformed(evt);
            }
        });

        btnEstadoPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoPiloto.setText("ESTADO");
        btnEstadoPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoPilotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnEliminarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPilotoSistemaActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();

    // Verificar si hay una fila seleccionada
    if (filaSeleccionada >= 0) {
        // Obtener el número de DPI del piloto seleccionado en la tabla como long
        long dpiSeleccionado = (long) tblRegistroPilotos.getValueAt(filaSeleccionada, 2); // El índice 2 corresponde a la columna DPI en tu tabla

        // Mostrar un cuadro de confirmación antes de eliminar
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que deseas borrar este piloto?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);

        // Si el usuario confirma la eliminación
        if (confirm == JOptionPane.YES_OPTION) {
            // Buscar y eliminar el piloto de la lista basado en el DPI
            Piloto pilotoAEliminar = null;
            for (Piloto piloto : listaPilotos) {
                if (piloto.getNumeroDeDpi() == dpiSeleccionado) { // Comparación de long
                    pilotoAEliminar = piloto;
                    break;
                }
            }

            // Eliminar el piloto encontrado
            if (pilotoAEliminar != null) {
                listaPilotos.remove(pilotoAEliminar);

                // Refrescar la tabla para reflejar los cambios
                cargarPilotosEnTabla();

                // Guardar los cambios en el archivo Excel después de eliminar el piloto
                gestionPilotos.setPilotos(listaPilotos); // Actualizar la lista en la clase de gestión
                gestionPilotos.guardarPilotosEnExcel();  // Guardar los datos actualizados en el archivo Excel

                // Mostrar un mensaje de confirmación
                JOptionPane.showMessageDialog(this, "Piloto eliminado correctamente.");
            }
        }
    } else {
        // Si no se ha seleccionado ninguna fila, mostrar un mensaje de advertencia
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto para eliminar.");
    }
    }//GEN-LAST:event_btnEliminarPilotoSistemaActionPerformed

    private void btnBuscarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPilotoSistemaActionPerformed
if (txtNombrePilotoBuscar.getText().trim().isEmpty() ||
        txtApellidoPilotoBuscar.getText().trim().isEmpty() ||
        txtNumeroDeDpiPilotoBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }

    // Obtener los valores ingresados
    String nombreBuscado = txtNombrePilotoBuscar.getText().trim();
    String apellidoBuscado = txtApellidoPilotoBuscar.getText().trim();

    // Convertir el DPI ingresado a long
    long dpiBuscado;
    try {
        dpiBuscado = Long.parseLong(txtNumeroDeDpiPilotoBuscar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI debe ser un número válido.");
        return;
    }

    // Reiniciar el modelo de la tabla
    modeloPilotos.setRowCount(0);
    boolean hayCoincidencias = false;

    // Buscar coincidencias en la lista de pilotos
    for (Piloto piloto : listaPilotos) {
        boolean coincide = true;

        if (!nombreBuscado.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado)) {
            coincide = false;
        }

        if (!apellidoBuscado.isEmpty() && !piloto.getApellidoPiloto().equalsIgnoreCase(apellidoBuscado)) {
            coincide = false;
        }

        // Comparar DPI como long
        if (piloto.getNumeroDeDpi() != dpiBuscado) {
            coincide = false;
        }

        if (coincide) {
            // Si hay coincidencias, agregar las filas al modelo de la tabla
            modeloPilotos.addRow(new Object[]{
                piloto.getNombrePiloto(),
                piloto.getApellidoPiloto(),
                piloto.getNumeroDeDpi(),
                piloto.getTipoLicencia(),
                piloto.getCorreoElectronicoPiloto(),
                piloto.getNumeroTelefonicoPiloto(),
                piloto.getGeneroPiloto(),
                piloto.getFechaDeNacimiento(),
                piloto.getEstadoPiloto()
            });
            hayCoincidencias = true;
        }
    }

    // Mostrar u ocultar la tabla según si hay coincidencias
    if (hayCoincidencias) {
        tblRegistroPilotos.setVisible(true);
    } else {
        tblRegistroPilotos.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }

    // Limpiar los campos de búsqueda
    txtNombrePilotoBuscar.setText("");
    txtApellidoPilotoBuscar.setText("");
    txtNumeroDeDpiPilotoBuscar.setText("");
    }//GEN-LAST:event_btnBuscarPilotoSistemaActionPerformed

    private void btnModificarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPilotoActionPerformed
        MODIFICARGESTIONPILOTOS abrir = new   MODIFICARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarPilotoActionPerformed

    private void btnAgregarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPilotoActionPerformed
        AGREGARGESTIONPILOTOS abrir = new  AGREGARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarPilotoActionPerformed

    private void btnEliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPilotoActionPerformed
        ELIMINARGESTIONPILOTOS abrir = new  ELIMINARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarPilotoActionPerformed

    private void btnMostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotoActionPerformed
        MOSTRARGESTIONPILOTOS abrir = new   MOSTRARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarPilotoActionPerformed

    private void btnListaPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaPilotoActionPerformed
        LISTAGESTIONPILOTOS abrir = new   LISTAGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaPilotoActionPerformed

    private void btnInicioPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioPilotoActionPerformed
        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioPilotoActionPerformed

    private void btnSalirPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPilotoActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirPilotoActionPerformed

    private void btnEstadoPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoPilotoActionPerformed
        ESTADOGESTIONPILOTOS abrir = new ESTADOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoPilotoActionPerformed

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
            java.util.logging.Logger.getLogger(ELIMINARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ELIMINARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ELIMINARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ELIMINARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ELIMINARGESTIONPILOTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPiloto;
    private javax.swing.JButton btnBuscarPilotoSistema;
    private javax.swing.JButton btnEliminarPiloto;
    private javax.swing.JButton btnEliminarPilotoSistema;
    private javax.swing.JButton btnEstadoPiloto;
    private javax.swing.JButton btnInicioPiloto;
    private javax.swing.JButton btnListaPiloto;
    private javax.swing.JButton btnModificarPiloto;
    private javax.swing.JButton btnMostrarPiloto;
    private javax.swing.JButton btnSalirPiloto;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JTextField txtApellidoPilotoBuscar;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoBuscar;
    // End of variables declaration//GEN-END:variables
}
