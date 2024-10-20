package GestionDeUsuarios;

import Login.LOGINPINEED;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MODIFICARGESTIONUSUARIOS extends javax.swing.JFrame {

    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private Usuarios usuarioActual;
    private INICIOGESTIONUSUARIOS ventanaPrincipal;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    

        
        
        public MODIFICARGESTIONUSUARIOS(Usuarios usuario, INICIOGESTIONUSUARIOS ventanaPrincipal, String username, String role, LOGINPINEED loginFrame) {
            initComponents();
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                this.currentUser = username;
    this.userRole = role;
    this.loginFrame = loginFrame;
            this.ventanaPrincipal = ventanaPrincipal;
            if (ventanaPrincipal != null) {
                this.gestionUsuarios = ventanaPrincipal.gestionUsuarios;
                this.listaUsuarios = gestionUsuarios.getUsuarios();
            }

            this.usuarioActual = usuario;
            if (usuario != null) {
                cargarDatosUsuario();
            }
        }   
    
    
    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            txtNombreUsuarioModificarModificar.setText(usuarioActual.getNombre());
            txtApellidoUsuarioModificarModificar.setText(usuarioActual.getApellido());
            txtNumeroDeDpiUsuarioModificarModificar.setText(String.valueOf(usuarioActual.getNumeroDPI()));
            txtCorreoElectronicoUsuarioModificarModificar.setText(usuarioActual.getCorreoElectronico());
            txtNumeroTelefonicoUsuarioModificarModificar.setText(String.valueOf(usuarioActual.getNumeroTelefono()));
            txtGeneroUsuarioModificarModificar.setSelectedItem(usuarioActual.getGenero());
            txtCargoUsuarioModificarModificar.setSelectedItem(usuarioActual.getCargo());
            txtNombreDeUsuarioUsuarioModificarModificar.setText(usuarioActual.getNombreUsuario());
            txtContraseñaUsuarioModificarModificar.setText(usuarioActual.getContrasenaUsuario());
            txtEstadoUsuarioModificarModificar.setSelectedItem(usuarioActual.getEstado());
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = sdf.parse(usuarioActual.getFechaNacimiento());
                txtFechaDeNacimientoUsuarioModificarModificar.setDate(fechaNacimiento);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la fecha de nacimiento: " + e.getMessage());
            }
        }
    }
    
    
    private void limpiarCamposUsuario() {
    txtNombreUsuarioModificarModificar.setText("");
    txtApellidoUsuarioModificarModificar.setText("");
    txtNumeroDeDpiUsuarioModificarModificar.setText("");
    txtCorreoElectronicoUsuarioModificarModificar.setText("");
    txtNumeroTelefonicoUsuarioModificarModificar.setText("");
    txtGeneroUsuarioModificarModificar.setSelectedIndex(0);
    txtFechaDeNacimientoUsuarioModificarModificar.setDate(null);
    txtEstadoUsuarioModificarModificar.setSelectedIndex(0);
    txtContraseñaUsuarioModificarModificar.setText("");
    txtEstadoUsuarioModificarModificar.setSelectedIndex(0);
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
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnModificarUsuariosSistema = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtNombreUsuarioModificarModificar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtApellidoUsuarioModificarModificar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreDeUsuarioUsuarioModificarModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtEstadoUsuarioModificarModificar = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtCargoUsuarioModificarModificar = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoUsuarioModificarModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroUsuarioModificarModificar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtNumeroTelefonicoUsuarioModificarModificar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNumeroDeDpiUsuarioModificarModificar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoUsuarioModificarModificar = new javax.swing.JTextField();
        btnMostrarContraseña = new javax.swing.JButton();
        txtContraseñaUsuarioModificarModificar = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        jPanel5.setPreferredSize(new java.awt.Dimension(827, 440));

        btnModificarUsuariosSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnModificarUsuariosSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnModificarUsuariosSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarUsuariosSistema.setText("MODIFICAR");
        btnModificarUsuariosSistema.setBorder(null);
        btnModificarUsuariosSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUsuariosSistemaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("NOMBRE");

        txtNombreUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel19.setText("APELLIDO");

        txtApellidoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE DE USUARIO");

        txtNombreDeUsuarioUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("CONTRASEÑA");

        jLabel20.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel20.setText("ESTADO ");

        txtEstadoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoUsuarioModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "BLOQUEADO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("CARGO");

        txtCargoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtCargoUsuarioModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "ADMINISTRADOR", "SECRETARIA" }));

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoUsuarioModificarModificar.setDateFormatString("dd/MM/yyyy");
        txtFechaDeNacimientoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtGeneroUsuarioModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        txtNumeroTelefonicoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("NUMERO DE DPI");

        txtNumeroDeDpiUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        txtCorreoElectronicoUsuarioModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        btnMostrarContraseña.setBackground(new java.awt.Color(204, 153, 255));
        btnMostrarContraseña.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        btnMostrarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarContraseña.setText("MOSTRAR");
        btnMostrarContraseña.setBorder(null);
        btnMostrarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificarUsuariosSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCargoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGeneroUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(txtNumeroTelefonicoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEstadoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCorreoElectronicoUsuarioModificarModificar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(txtNombreDeUsuarioUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtApellidoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(txtContraseñaUsuarioModificarModificar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFechaDeNacimientoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(52, 52, 52)
                                        .addComponent(txtNumeroDeDpiUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(14, 14, 14)
                        .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtApellidoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreDeUsuarioUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtContraseñaUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEstadoUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCargoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGeneroUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroTelefonicoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNumeroDeDpiUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnModificarUsuariosSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarUsuariosSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuariosSistemaActionPerformed
        try {
            String nombreUsuario = txtNombreUsuarioModificarModificar.getText().trim();
            String apellidoUsuario = txtApellidoUsuarioModificarModificar.getText().trim();
            long numeroDeDpiUsuario = Long.parseLong(txtNumeroDeDpiUsuarioModificarModificar.getText().trim());
            String cargoUsuario = txtCargoUsuarioModificarModificar.getSelectedItem().toString().trim();
            String correoElectronicoUsuario = txtCorreoElectronicoUsuarioModificarModificar.getText().trim();
            int numeroTelefonicoUsuario = Integer.parseInt(txtNumeroTelefonicoUsuarioModificarModificar.getText().trim());
            String generoUsuario = txtGeneroUsuarioModificarModificar.getSelectedItem().toString().trim();
            String nombreDeUsuario = txtNombreDeUsuarioUsuarioModificarModificar.getText().trim();
            String contrasenaUsuario = txtContraseñaUsuarioModificarModificar.getText().trim();
            String estadoUsuario = txtEstadoUsuarioModificarModificar.getSelectedItem().toString().trim();

            Date fechaNacimientoUsuarioDate = txtFechaDeNacimientoUsuarioModificarModificar.getDate();
            if (fechaNacimientoUsuarioDate == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
                return;
            }

            if (!correoElectronicoUsuario.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaDeNacimientoUsuario = sdf.format(fechaNacimientoUsuarioDate);

            if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() || cargoUsuario.isEmpty() ||
                correoElectronicoUsuario.isEmpty() || generoUsuario.isEmpty() || nombreDeUsuario.isEmpty() ||
                contrasenaUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
                return;
            }

            if (String.valueOf(numeroDeDpiUsuario).length() != 13) {
                JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
                return;
            }

            if (String.valueOf(numeroTelefonicoUsuario).length() != 8) {
                JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
                return;
            }

            // Validate password
            if (!validarContrasena(contrasenaUsuario)) {
                JOptionPane.showMessageDialog(this, "La contraseña no cumple con los requisitos:\n" +
                    "- Debe tener al menos 8 caracteres\n" +
                    "- Debe contener 'pineed'\n" +
                    "- Debe incluir al menos una letra, un número y un carácter especial");
                return;
            }

            boolean dpiCambiado = numeroDeDpiUsuario != usuarioActual.getNumeroDPI();
            boolean telefonoCambiado = numeroTelefonicoUsuario != usuarioActual.getNumeroTelefono();
            boolean correoCambiado = !correoElectronicoUsuario.equals(usuarioActual.getCorreoElectronico());

            for (Usuarios usuarioExistente : listaUsuarios) {
                if (usuarioExistente != usuarioActual) {
                    if (dpiCambiado && usuarioExistente.getNumeroDPI() == numeroDeDpiUsuario) {
                        JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número de DPI.");
                        return;
                    }
                    if (telefonoCambiado && usuarioExistente.getNumeroTelefono() == numeroTelefonicoUsuario) {
                        JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número telefónico.");
                        return;
                    }
                    if (correoCambiado && usuarioExistente.getCorreoElectronico().equals(correoElectronicoUsuario)) {
                        JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo electrónico.");
                        return;
                    }
                }
            }

            usuarioActual.setNombreUsuario(nombreDeUsuario);
            usuarioActual.setContrasenaUsuario(contrasenaUsuario);
            usuarioActual.setNombre(nombreUsuario);
            usuarioActual.setApellido(apellidoUsuario);
            usuarioActual.setCargo(cargoUsuario);
            usuarioActual.setGenero(generoUsuario);
            usuarioActual.setNumeroDPI(numeroDeDpiUsuario);
            usuarioActual.setFechaNacimiento(fechaDeNacimientoUsuario);
            usuarioActual.setNumeroTelefono(numeroTelefonicoUsuario);
            usuarioActual.setCorreoElectronico(correoElectronicoUsuario);
            usuarioActual.setEstado(estadoUsuario);

            gestionUsuarios.actualizarUsuario(usuarioActual);

            JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente.");

            ventanaPrincipal.actualizarTabla();

            ventanaPrincipal.setVisible(true);
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btnModificarUsuariosSistemaActionPerformed


    private boolean isPasswordVisible = false;

    
    private void btnMostrarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarContraseñaActionPerformed
    if (!isPasswordVisible) {
        // Mostrar contraseña
        txtContraseñaUsuarioModificarModificar.setEchoChar((char)0);
        btnMostrarContraseña.setText("OCULTAR");
    } else {
        // Ocultar contraseña
        txtContraseñaUsuarioModificarModificar.setEchoChar('*');
        btnMostrarContraseña.setText("MOSTRAR");
    }
    isPasswordVisible = !isPasswordVisible;
    }//GEN-LAST:event_btnMostrarContraseñaActionPerformed

    
    private boolean validarContrasena(String contrasena) {
    // Verificar longitud mínima
    if (contrasena.length() < 8) {
        return false;
    }

    // Verificar si contiene 'pineed'
    if (!contrasena.toLowerCase().contains("pineed")) {
        return false;
    }

    // Verificar si contiene al menos una letra, un número y un carácter especial
    boolean tieneLetra = false;
    boolean tieneNumero = false;
    boolean tieneEspecial = false;

    for (char c : contrasena.toCharArray()) {
        if (Character.isLetter(c)) {
            tieneLetra = true;
        } else if (Character.isDigit(c)) {
            tieneNumero = true;
        } else if (!Character.isWhitespace(c)) {
            tieneEspecial = true;
        }
    }

    return tieneLetra && tieneNumero && tieneEspecial;
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
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(MODIFICARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
       
                 String username = "defaultUser";  // Replace with actual username or logic
        String role = "defaultRole"; 
        
        LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object
        
        // Create the MODIFICARGESTIONCAMIONES instance with the required parameters
        new MODIFICARGESTIONUSUARIOS(null, null, username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificarUsuariosSistema;
    private javax.swing.JButton btnMostrarContraseña;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtApellidoUsuarioModificarModificar;
    private javax.swing.JComboBox<String> txtCargoUsuarioModificarModificar;
    private javax.swing.JPasswordField txtContraseñaUsuarioModificarModificar;
    private javax.swing.JTextField txtCorreoElectronicoUsuarioModificarModificar;
    private javax.swing.JComboBox<String> txtEstadoUsuarioModificarModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoUsuarioModificarModificar;
    private javax.swing.JComboBox<String> txtGeneroUsuarioModificarModificar;
    private javax.swing.JTextField txtNombreDeUsuarioUsuarioModificarModificar;
    private javax.swing.JTextField txtNombreUsuarioModificarModificar;
    private javax.swing.JTextField txtNumeroDeDpiUsuarioModificarModificar;
    private javax.swing.JTextField txtNumeroTelefonicoUsuarioModificarModificar;
    // End of variables declaration//GEN-END:variables
}
