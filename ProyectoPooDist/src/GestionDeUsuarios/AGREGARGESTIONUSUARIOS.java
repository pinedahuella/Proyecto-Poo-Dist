package GestionDeUsuarios;

import GestionDePilotos.AGREGARGESTIONPILOTOS;
import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Clase AGREGARGESTIONUSUARIOS
 * Esta clase representa una ventana para agregar usuarios en la aplicación.
 * Hereda de JFrame y maneja la interacción del usuario para ingresar datos de nuevos usuarios.
 */
public class AGREGARGESTIONUSUARIOS extends javax.swing.JFrame {

    // Variables de instancia
    public GESTIONUSUARIOS gestionUsuarios; // Manejo de usuarios
    public Vector<Usuarios> listaUsuarios = new Vector<>(); // Lista de usuarios
    private int indiceActual; // Índice del usuario actual
    private String currentUser; // Nombre de usuario actual
    private String userRole; // Rol del usuario actual
    private LOGINPINEED loginFrame; // Marco de inicio de sesión

    /**
     * Constructor de la clase AGREGARGESTIONUSUARIOS.
     * Inicializa los componentes de la ventana y carga los usuarios desde un archivo Excel.
     *
     * @param username El nombre de usuario actual.
     * @param role El rol del usuario actual.
     * @param loginFrame La instancia del marco de inicio de sesión.
     */
    public AGREGARGESTIONUSUARIOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents(); // Inicializa los componentes gráficos
        indiceActual = 0; // Inicializa el índice del usuario actual
        setResizable(false); // Desactivar el cambio de tamaño
        // Crea una instancia de GESTIONUSUARIOS y carga los usuarios desde Excel
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();

        // Si se cargaron usuarios, se asignan a la lista
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }
        
        this.currentUser = username; // Asigna el nombre de usuario actual
        this.userRole = role; // Asigna el rol del usuario actual
        this.loginFrame = loginFrame; // Asigna el marco de inicio de sesión
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Cierra la ventana al finalizar
    }

    /**
     * Limpia los campos de entrada en la ventana.
     */
    private void limpiarCampos() {
        txtNombreUsuario.setText(""); // Limpia el campo de nombre
        txtApellidoUsuario.setText(""); // Limpia el campo de apellido
        txtNumeroDeDpiUsuario.setText(""); // Limpia el campo de DPI
        txtCargoUsuario.setSelectedIndex(0); // Reinicia el índice de cargo
        txtContraseñaUsuario.setText(""); // Limpia el campo de contraseña
        txtCorreoElectronicoUsuario.setText(""); // Limpia el campo de correo electrónico
        txtNumeroTelefonicoUsuario.setText(""); // Limpia el campo de número telefónico
        txtGeneroUsuario.setSelectedIndex(0); // Reinicia el índice de género
        txtFechaDeNacimientoUsuario.setDate(null); // Reinicia la fecha de nacimiento
        txtNombreDeUsuarioUsuario.setText(""); // Limpia el campo de nombre de usuario
        txtEstadoUsuario.setSelectedIndex(0); // Reinicia el índice de estado
    }

    /**
     * Añade un oyente a la ventana para manejar el cierre de sesión al cerrar.
     */
    public void addWindowListener() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarSesionYSalir(); // Cierra sesión y sale
            }
        });
    }

    /**
     * Cierra la sesión del usuario actual y muestra la ventana de inicio de sesión.
     */
    private void cerrarSesionYSalir() {
        if (loginFrame != null) {
            loginFrame.cerrarSesion(currentUser, userRole); // Cierra sesión
        }
        // Crea una nueva instancia de LOGINPINEED
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true); // Muestra el marco de inicio de sesión
        this.dispose(); // Cierra la ventana actual
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCargoUsuario = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoUsuario = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoUsuario = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroUsuario = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnAgregarUsuarioSistema = new javax.swing.JButton();
        txtNumeroDeDpiUsuario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtApellidoUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreDeUsuarioUsuario = new javax.swing.JTextField();
        txtNumeroTelefonicoUsuario = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtEstadoUsuario = new javax.swing.JComboBox<>();
        txtContraseñaUsuario = new javax.swing.JPasswordField();
        btnMostrarContraseña = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));
        jPanel2.setPreferredSize(new java.awt.Dimension(839, 452));

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("NOMBRE");

        txtNombreUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("CARGO");

        txtCargoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtCargoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SECRETARIA", "ADMINISTRADOR" }));

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        txtCorreoElectronicoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoUsuario.setDateFormatString("dd/MM/yyyy");
        txtFechaDeNacimientoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtGeneroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("NUMERO DE DPI");

        btnAgregarUsuarioSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnAgregarUsuarioSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnAgregarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUsuarioSistema.setText("AGREGAR");
        btnAgregarUsuarioSistema.setBorder(null);
        btnAgregarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioSistemaActionPerformed(evt);
            }
        });

        txtNumeroDeDpiUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel19.setText("APELLIDO");

        txtApellidoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE DE USUARIO");

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("CONTRASEÑA");

        txtNombreDeUsuarioUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtNumeroTelefonicoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel20.setText("ESTADO ");

        txtEstadoUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "BLOQUEADO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        btnMostrarContraseña.setBackground(new java.awt.Color(153, 153, 255));
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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreDeUsuarioUsuario)
                                    .addComponent(txtContraseñaUsuario))))
                        .addGap(18, 18, 18)
                        .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(52, 52, 52)
                        .addComponent(txtNumeroDeDpiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreDeUsuarioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNumeroDeDpiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Maneja el evento de acción para agregar un usuario al sistema.
     * Este método valida la entrada del usuario, incluyendo el nombre, apellido, DPI, 
     * cargo, correo electrónico, número telefónico, género, nombre de usuario y 
     * contraseña. Si todos los campos son válidos, se crea un nuevo objeto `Usuarios`, 
     * se agrega a la lista de usuarios y se guarda en un archivo Excel. 
     * Se muestra un mensaje de confirmación al usuario.
     *
     * @param evt El evento de acción que desencadena este método.
     */
    private void btnAgregarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioSistemaActionPerformed
    try {
        // Recuperar y limpiar los datos de entrada
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String apellidoUsuario = txtApellidoUsuario.getText().trim();
        
        // Validar que nombre y apellido no estén vacíos
        if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido son obligatorios.");
            return;
        }

        // Generar el nombre de usuario esperado en el formato correcto
        String nombreUsuarioEsperado = nombreUsuario.toLowerCase() + "." + 
                                     apellidoUsuario.toLowerCase() + "&pineed";

        // Obtener el nombre de usuario ingresado
        String nombreDeUsuario = txtNombreDeUsuarioUsuario.getText().trim().toLowerCase();

        // Validar que el nombre de usuario coincida con el formato esperado
        if (!nombreDeUsuario.equals(nombreUsuarioEsperado)) {
            JOptionPane.showMessageDialog(this, 
                "El nombre de usuario debe seguir el formato: nombre.apellido&pineed\n" +
                "Para sus datos, debe ser: " + nombreUsuarioEsperado);
            return;
        }

        // Obtener y validar el número de DPI
        String dpiText = txtNumeroDeDpiUsuario.getText().trim();
        if (dpiText.length() != 13) {
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }
        long numeroDeDpiUsuario = Long.parseLong(dpiText);

        // Obtener la contraseña y validar que coincida con el DPI
        String contrasenaUsuario = txtContraseñaUsuario.getText().trim();
        if (!contrasenaUsuario.equals(dpiText)) {
            JOptionPane.showMessageDialog(this, "La contraseña debe ser igual al número de DPI.");
            return;
        }

        // Continuar con el resto de las validaciones
        String cargoUsuario = txtCargoUsuario.getSelectedItem().toString().trim();
        String correoElectronicoUsuario = txtCorreoElectronicoUsuario.getText().trim();
        int numeroTelefonicoUsuario = Integer.parseInt(txtNumeroTelefonicoUsuario.getText().trim());
        String generoUsuario = txtGeneroUsuario.getSelectedItem().toString().trim();
        String estadoUsuario = txtEstadoUsuario.getSelectedItem().toString().trim();

        // Validar fecha de nacimiento
        Date fechaNacimientoUsuarioDate = txtFechaDeNacimientoUsuario.getDate();
        if (fechaNacimientoUsuarioDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
            return;
        }

        // Validar correo electrónico
        if (!correoElectronicoUsuario.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
            return;
        }

        // Formatear fecha de nacimiento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDeNacimientoUsuario = sdf.format(fechaNacimientoUsuarioDate);

        // Validar longitud del número telefónico
        if (String.valueOf(numeroTelefonicoUsuario).length() != 8) { 
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }

        // Verificar si el usuario ya existe
        for (Usuarios usuarioExistente : listaUsuarios) {
            if (usuarioExistente.getNumeroDPI() == numeroDeDpiUsuario) { 
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número de DPI.");
                return;
            }
            if (usuarioExistente.getNumeroTelefono() == numeroTelefonicoUsuario) { 
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número telefónico.");
                return;
            }
            if (usuarioExistente.getCorreoElectronico().equals(correoElectronicoUsuario)) {
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo electrónico.");
                return;
            }
        }

        // Crear un nuevo usuario
        Usuarios usuario = new Usuarios(nombreDeUsuario, contrasenaUsuario, nombreUsuario, apellidoUsuario, 
                                        cargoUsuario, generoUsuario, numeroDeDpiUsuario, 
                                        fechaDeNacimientoUsuario, numeroTelefonicoUsuario, 
                                        correoElectronicoUsuario, estadoUsuario);

        // Agregar usuario a la lista
        listaUsuarios.add(usuario);
        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");

        // Limpiar los campos de entrada
        limpiarCampos();

        // Guardar usuarios en Excel
        gestionUsuarios.setUsuarios(listaUsuarios); 
        gestionUsuarios.guardarUsuariosEnExcel();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + e.getMessage());
    }

    // Navegar a la ventana de gestión de usuarios
    String username = this.currentUser;
    String role = this.userRole;
    LOGINPINEED loginFrame = this.loginFrame;

    INICIOGESTIONUSUARIOS abrir = new INICIOGESTIONUSUARIOS(username, role, loginFrame);
    abrir.setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnAgregarUsuarioSistemaActionPerformed

    // Indica si la contraseña es visible o no. Inicialmente, está oculta.
    private boolean isPasswordVisible = false;

    /**
     * Maneja el evento de acción para mostrar u ocultar la contraseña.
     * Cambia el carácter de eco del campo de texto de la contraseña
     * y actualiza el texto del botón en consecuencia.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void btnMostrarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarContraseñaActionPerformed
        if (!isPasswordVisible) {
            // Mostrar la contraseña configurando el carácter de eco a 0
            txtContraseñaUsuario.setEchoChar((char)0);
            btnMostrarContraseña.setText("OCULTAR"); // Cambiar el texto del botón a "OCULTAR"
        } else {
            // Ocultar la contraseña configurando el carácter de eco a '*'
            txtContraseñaUsuario.setEchoChar('*');
            btnMostrarContraseña.setText("MOSTRAR"); // Cambiar el texto del botón a "MOSTRAR"
        }
        // Alternar el estado de visibilidad de la contraseña
        isPasswordVisible = !isPasswordVisible;
    }//GEN-LAST:event_btnMostrarContraseñaActionPerformed

    
     /**
     * Valida si la contraseña cumple con ciertos criterios de seguridad.
     *
     * @param contrasena La contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    private boolean validarContrasena(String contrasena) {
        // Verificar longitud mínima de la contraseña
        if (contrasena.length() < 8) {
            return false; // La contraseña es demasiado corta
        }

        // Verificar si la contraseña contiene 'pineed'
        if (!contrasena.toLowerCase().contains("pineed")) {
            return false; // La contraseña no contiene la palabra requerida
        }

        // Verificar si contiene al menos una letra, un número y un carácter especial
        boolean tieneLetra = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasena.toCharArray()) {
            if (Character.isLetter(c)) {
                tieneLetra = true; // Hay al menos una letra
            } else if (Character.isDigit(c)) {
                tieneNumero = true; // Hay al menos un número
            } else if (!Character.isWhitespace(c)) {
                tieneEspecial = true; // Hay al menos un carácter especial
            }
        }

        // Retorna true solo si se cumplen todas las condiciones
        return tieneLetra && tieneNumero && tieneEspecial;
    }
    

    // Método main para inicializar la interfaz gráfica
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
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

            /* Crear y mostrar el formulario */
       java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
               String username = "defaultUser";  // Reemplazar con el nombre de usuario real o lógica
               String role = "defaultRole";      // Reemplazar con el rol real
               LOGINPINEED loginFrame = new LOGINPINEED();  // Instanciar el objeto LOGINPINEED

               // Crear la instancia de AGREGARGESTIONUSUARIOS con los parámetros requeridos
               new AGREGARGESTIONUSUARIOS(username, role, loginFrame).setVisible(true);
           }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuarioSistema;
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
    private javax.swing.JTextField txtApellidoUsuario;
    private javax.swing.JComboBox<String> txtCargoUsuario;
    private javax.swing.JPasswordField txtContraseñaUsuario;
    private javax.swing.JTextField txtCorreoElectronicoUsuario;
    private javax.swing.JComboBox<String> txtEstadoUsuario;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoUsuario;
    private javax.swing.JComboBox<String> txtGeneroUsuario;
    private javax.swing.JTextField txtNombreDeUsuarioUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtNumeroDeDpiUsuario;
    private javax.swing.JTextField txtNumeroTelefonicoUsuario;
    // End of variables declaration//GEN-END:variables
}
