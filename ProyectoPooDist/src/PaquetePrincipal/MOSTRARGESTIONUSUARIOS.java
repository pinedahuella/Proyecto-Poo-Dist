package PaquetePrincipal;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MOSTRARGESTIONUSUARIOS extends javax.swing.JFrame {


    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private int indiceActual;


    public MOSTRARGESTIONUSUARIOS() {
        initComponents();
        indiceActual = 0;

 
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();

  
        String[] columnas = {"Usuario", "Contraseña", "Nombre", "Apellido", "Cargo", "Género", "DPI", "Fecha de Nacimiento", "Teléfono", "Correo", "Estado"};
        modeloUsuarios.setColumnIdentifiers(columnas);


        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }


        tblRegistroUsuarios.setModel(modeloUsuarios);
        cargarUsuariosEnTabla();
    }


    private void cargarUsuariosEnTabla() {

        modeloUsuarios.setRowCount(0);


        for (Usuarios usuario : listaUsuarios) {
            modeloUsuarios.addRow(new Object[]{
                usuario.getNombreUsuario(),
                usuario.getContrasenaUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCargo(),
                usuario.getGenero(),
                usuario.getNumeroDPI(),
                usuario.getFechaNacimiento(),
                usuario.getNumeroTelefono(),
                usuario.getCorreoElectronico(),
                usuario.getEstado()
            });
        }

        tblRegistroUsuarios.setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreUsuarioBuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoUsuarioBuscar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiUsuariosBuscar = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        btnBuscarUsuarioSistema = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnModificarUsuario = new javax.swing.JButton();
        btnAgregarUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        btnMostrarUsuario = new javax.swing.JButton();
        btnListaUsuario = new javax.swing.JButton();
        btnInicioUsuario = new javax.swing.JButton();
        btnSalirUsuario = new javax.swing.JButton();
        btnEstadoUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" MOSTRAR USUARIO AL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        tblRegistroUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblRegistroUsuarios);

        btnBuscarUsuarioSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarUsuarioSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarUsuarioSistema.setText("BUSCAR USUARIO");
        btnBuscarUsuarioSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioSistemaActionPerformed(evt);
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
                                    .addComponent(txtNumeroDeDpiUsuariosBuscar))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNombreUsuarioBuscar)
                                        .addComponent(txtApellidoUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(182, 182, 182)))
                    .addComponent(btnBuscarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiUsuariosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(60, 60, 60)
                .addComponent(btnBuscarUsuarioSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        btnModificarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarUsuario.setText("MODIFICAR");
        btnModificarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUsuarioActionPerformed(evt);
            }
        });

        btnAgregarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUsuario.setText("AGREGAR");
        btnAgregarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarUsuario.setText("ELIMINAR");
        btnEliminarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        btnMostrarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarUsuario.setText("MOSTRAR");
        btnMostrarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarUsuarioActionPerformed(evt);
            }
        });

        btnListaUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnListaUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnListaUsuario.setText("LISTA");
        btnListaUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaUsuarioActionPerformed(evt);
            }
        });

        btnInicioUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioUsuario.setText("INICIO");
        btnInicioUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioUsuarioActionPerformed(evt);
            }
        });

        btnSalirUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirUsuario.setText("SALIR");
        btnSalirUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirUsuarioActionPerformed(evt);
            }
        });

        btnEstadoUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoUsuario.setText("ESTADO");
        btnEstadoUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnBuscarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioSistemaActionPerformed
    if (txtNombreUsuarioBuscar.getText().trim().isEmpty() ||
        txtApellidoUsuarioBuscar.getText().trim().isEmpty() ||
        txtNumeroDeDpiUsuariosBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }


    String nombreBuscado = txtNombreUsuarioBuscar.getText().trim();
    String apellidoBuscado = txtApellidoUsuarioBuscar.getText().trim();


    long dpiBuscado;
    try {
        dpiBuscado = Long.parseLong(txtNumeroDeDpiUsuariosBuscar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI debe ser un número válido.");
        return;
    }


    modeloUsuarios.setRowCount(0);
    boolean hayCoincidencias = false;


    for (Usuarios usuarios : listaUsuarios) {
        boolean coincide = true;

        if (!nombreBuscado.isEmpty() && !usuarios.getNombre().equalsIgnoreCase(nombreBuscado)) {
            coincide = false;
        }

        if (!apellidoBuscado.isEmpty() && !usuarios.getApellido().equalsIgnoreCase(apellidoBuscado)) {
            coincide = false;
        }

        if (usuarios.getNumeroDPI() != dpiBuscado) {
            coincide = false;
        }

        if (coincide) {

            modeloUsuarios.addRow(new Object[]{
                usuarios.getNombreUsuario(),
                usuarios.getContrasenaUsuario(),
                usuarios.getNombre(),
                usuarios.getApellido(),
                usuarios.getCargo(),
                usuarios.getGenero(),
                usuarios.getNumeroDPI(),
                usuarios.getFechaNacimiento(),
                usuarios.getNumeroTelefono(),
                usuarios.getCorreoElectronico(),
                usuarios.getEstado()
            });
            hayCoincidencias = true;
        }
    }


    if (hayCoincidencias) {
        tblRegistroUsuarios.setVisible(true);
    } else {
        tblRegistroUsuarios.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }


    txtNombreUsuarioBuscar.setText("");
    txtApellidoUsuarioBuscar.setText("");
    txtNumeroDeDpiUsuariosBuscar.setText("");
    }//GEN-LAST:event_btnBuscarUsuarioSistemaActionPerformed

    private void btnModificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuarioActionPerformed
        MODIFICARGESTIONUSUARIOS abrir = new   MODIFICARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarUsuarioActionPerformed

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        AGREGARGESTIONUSUARIOS abrir = new  AGREGARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        ELIMINARGESTIONUSUARIOS abrir = new  ELIMINARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnMostrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUsuarioActionPerformed
        MOSTRARGESTIONUSUARIOS abrir = new   MOSTRARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarUsuarioActionPerformed

    private void btnListaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaUsuarioActionPerformed
        LISTAGESTIONUSUARIOS abrir = new   LISTAGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaUsuarioActionPerformed

    private void btnInicioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioUsuarioActionPerformed
        INICIOGESTIONUSUARIOS abrir = new  INICIOGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioUsuarioActionPerformed

    private void btnSalirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirUsuarioActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirUsuarioActionPerformed

    private void btnEstadoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoUsuarioActionPerformed
        CONTRASEÑAUSUARIOGESTION abrir = new CONTRASEÑAUSUARIOGESTION();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoUsuarioActionPerformed

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
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MOSTRARGESTIONUSUARIOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuario;
    private javax.swing.JButton btnBuscarUsuarioSistema;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnEstadoUsuario;
    private javax.swing.JButton btnInicioUsuario;
    private javax.swing.JButton btnListaUsuario;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JButton btnMostrarUsuario;
    private javax.swing.JButton btnSalirUsuario;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroUsuarios;
    private javax.swing.JTextField txtApellidoUsuarioBuscar;
    private javax.swing.JTextField txtNombreUsuarioBuscar;
    private javax.swing.JTextField txtNumeroDeDpiUsuariosBuscar;
    // End of variables declaration//GEN-END:variables
}
