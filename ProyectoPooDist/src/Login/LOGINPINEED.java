package Login;

// Importación de clases necesarias para la funcionalidad de inicio de sesión
import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.Piloto;
import InicioApp.INICIOPINEED;
import GestionDeUsuarios.GESTIONUSUARIOS;
import Login.GESTIONLOGIN;
import Login.Login;
import GestionDeUsuarios.Usuarios;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.awt.Color;


// Clase principal para el formulario de inicio de sesión
public class LOGINPINEED extends javax.swing.JFrame {
    // Atributos
    private GESTIONUSUARIOS gestionUsuarios; // Gestión de usuarios
    private GESTIONPILOTOS gestionPilotos; // Gestión de usuarios
    private Map<String, Integer> intentosFallidos; // Mapa para rastrear intentos fallidos de inicio de sesión

    // Constructor de la clase LOGINPINEED
    public LOGINPINEED() {
initComponents();
        setResizable(false);
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionPilotos = new GESTIONPILOTOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        gestionPilotos.cargarPilotosDesdeExcel();
        intentosFallidos = new HashMap<>();

        // Iniciar en pantalla completa ajustada a la resolución de la pantalla
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        // Configurar el comportamiento de los campos de texto
        setupTextField(txtNombreUsuario, "Ingrese su usuario");
        setupPasswordField(txtContraseñaUsuario, "Ingrese su contraseña");

        // Asegurarse de que ningún campo tenga el foco inicialmente
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });

        // Agregar el WindowListener para manejar el cierre de la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Verificar si hay un usuario con sesión activa
                GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
                gestionLogin.cargarLoginsDesdeExcel();
                boolean sesionActiva = false;
                String usuarioActivo = "";
                
                for (Login login : gestionLogin.getLogins()) {
                    if (login.getTiempoSalida().isEmpty()) {
                        sesionActiva = true;
                        usuarioActivo = login.getPersonal();
                        break;
                    }
                }
                
                // Si hay una sesión activa, registrar la hora de salida
                if (sesionActiva) {
                    LocalDateTime tiempoSalida = LocalDateTime.now();
                    for (Login login : gestionLogin.getLogins()) {
                        if (login.getPersonal().equals(usuarioActivo) && login.getTiempoSalida().isEmpty()) {
                            login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                            gestionLogin.actualizarLogin(login);
                            break;
                        }
                    }
                }
                
                // Cerrar la aplicación
                System.exit(0);
            }
        });
    }

    
    private void openNewFrame(JFrame newFrame) {
        newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        newFrame.setVisible(true);
        
        // Hacer invisible el frame actual
        this.setVisible(false);
        
        // Dispose del frame actual
        SwingUtilities.invokeLater(() -> {
            this.dispose();
        });
    }
    
    // Configura el campo de texto con un placeholder
    private void setupTextField(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY); // Establece el color del texto del placeholder

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder al enfocar
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restablece el placeholder si el campo está vacío
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    // Configura el campo de contraseña con un placeholder
    private void setupPasswordField(JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Muestra el texto del placeholder

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Limpia el placeholder al enfocar
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•'); // Carácter para ocultar la contraseña
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restablece el placeholder si el campo está vacío
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Muestra el texto del placeholder
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtContraseñaUsuario = new javax.swing.JPasswordField();
        btnIngresarPineed = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnMostrarContraseña = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel4.setText("USUARIO");

        txtNombreUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        txtNombreUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtNombreUsuario.setBorder(null);

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel11.setText("CONTRASEÑA");

        txtContraseñaUsuario.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        txtContraseñaUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtContraseñaUsuario.setBorder(null);
        txtContraseñaUsuario.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtContraseñaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseñaUsuarioActionPerformed(evt);
            }
        });

        btnIngresarPineed.setBackground(new java.awt.Color(10, 30, 67));
        btnIngresarPineed.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        btnIngresarPineed.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresarPineed.setText("Ingresar");
        btnIngresarPineed.setBorder(null);
        btnIngresarPineed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarPineedActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionLogo.png"))); // NOI18N

        btnMostrarContraseña.setBackground(new java.awt.Color(153, 153, 255));
        btnMostrarContraseña.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        btnMostrarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarContraseña.setText("MOSTRAR");
        btnMostrarContraseña.setBorder(null);
        btnMostrarContraseña.setPreferredSize(new java.awt.Dimension(79, 22));
        btnMostrarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(287, 287, 287))
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContraseñaUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIngresarPineed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(24, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(11, 11, 11)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(482, 482, 482)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnIngresarPineed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(279, Short.MAX_VALUE)))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fotos/ImagenDecoracionVegetales.jpeg"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(32, 67, 99));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1360, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(455, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(265, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(266, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 2705, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(2197, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1532, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Acción del botón para iniciar sesión
    private void btnIngresarPineedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarPineedActionPerformed
String nombreUsuario = txtNombreUsuario.getText();
    String contraseña = new String(txtContraseñaUsuario.getPassword());

    // Verificar primero si es el administrador especial
    if (nombreUsuario.equals("administrador.admin&pineed")) {
        if (contraseña.equals("1110101000001")) {
            LocalDateTime tiempoEntrada = LocalDateTime.now();
            GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
            gestionLogin.cargarLoginsDesdeExcel();
            
            Login nuevoLogin = new Login(
                tiempoEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "",
                nombreUsuario,
                "ADMINISTRADOR"
            );
            gestionLogin.setUnLogin(nuevoLogin);
            
            INICIOPINEED abrir = new INICIOPINEED(nombreUsuario, "ADMINISTRADOR", this);
            abrir.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    LOGINPINEED.this.setVisible(true);
                }
            });
            abrir.setVisible(true);
            this.setVisible(false);
            return;
        } else {
            mostrarMensajeError("Contraseña incorrecta.");
            return;
        }
    }

    // Validación de formato minúsculas
    if (!nombreUsuario.equals(nombreUsuario.toLowerCase())) {
        mostrarMensajeError("El nombre de usuario debe estar en minúsculas."
                + "Usando el formato: nombre.apellido&pineed "
                + "Primer Nombre y Primer Apellido"
                + "Por favor, corríjalo e intente de nuevo.");
        return;
    }

    // Verificar si es un piloto
    Piloto piloto = buscarPiloto(nombreUsuario);
    if (piloto != null) {
        // Verificar estado del piloto
        if (!piloto.getEstadoPiloto().equalsIgnoreCase("ACTIVO")) {
            String mensaje = "Acceso denegado. ";
            switch (piloto.getEstadoPiloto().toUpperCase()) {
                case "BLOQUEADO":
                    mensaje += "Su cuenta está bloqueada. Contacte al administrador: +502 5754-5388";
                    break;
                case "ENFERMO":
                    mensaje += "Su cuenta está suspendida por enfermedad.";
                    break;
                case "EN VACACIONES":
                    mensaje += "Su cuenta está suspendida por vacaciones.";
                    break;
                case "JUBILADO":
                    mensaje += "Su cuenta está inactiva por jubilación.";
                    break;
                default:
                    mensaje += "Su cuenta no está activa.";
            }
            mostrarMensajeError(mensaje);
            return;
        }
        validarLoginPiloto(piloto, nombreUsuario, contraseña);
        return;
    }

    // Validación de usuario normal
    Usuarios usuario = buscarUsuario(nombreUsuario);
    if (usuario == null) {
        mostrarMensajeError("Usuario no encontrado.");
        return;
    }

   // Verificar estado del usuario
if (!usuario.getEstado().equalsIgnoreCase("ACTIVO")) {
    // Para administradores, permitir acceso si están en ACTIVO o EN VACACIONES
    if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
        if (usuario.getEstado().equalsIgnoreCase("EN VACACIONES")) {
            // Permitir acceso pero mostrar advertencia
            JOptionPane.showMessageDialog(
                this,
                "Advertencia: Está ingresando durante su período de vacaciones.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            // Continuar con el proceso de login
            return;
        } else if (!usuario.getEstado().equalsIgnoreCase("ACTIVO")) {
            mostrarMensajeError("Acceso denegado. Su cuenta de administrador está " + 
                              usuario.getEstado().toLowerCase() + ".");
            return;
        }
    } else {
        // Para usuarios normales, mostrar mensaje según el estado
        String mensaje = "Acceso denegado. ";
        switch (usuario.getEstado().toUpperCase()) {
            case "BLOQUEADO":
                mensaje += "Su cuenta está bloqueada. Contacte al administrador: +502 5754-5388";
                break;
            case "ENFERMO":
                mensaje += "Su cuenta está suspendida por enfermedad.";
                break;
            case "EN VACACIONES":
                mensaje += "Su cuenta está suspendida por vacaciones.";
                break;
            case "JUBILADO":
                mensaje += "Su cuenta está inactiva por jubilación.";
                break;
            default:
                mensaje += "Su cuenta no está activa.";
        }
        mostrarMensajeError(mensaje);
        return;
    }
        
        // Para usuarios normales, mostrar mensaje según el estado
        String mensaje = "Acceso denegado. ";
        switch (usuario.getEstado().toUpperCase()) {
            case "BLOQUEADO":
                mensaje += "Su cuenta está bloqueada. Contacte al administrador: +502 5754-5388";
                break;
            case "ENFERMO":
                mensaje += "Su cuenta está suspendida por enfermedad.";
                break;
            case "EN VACACIONES":
                mensaje += "Su cuenta está suspendida por vacaciones.";
                break;
            case "JUBILADO":
                mensaje += "Su cuenta está inactiva por jubilación.";
                break;
            default:
                mensaje += "Su cuenta no está activa.";
        }
        mostrarMensajeError(mensaje);
        return;
    }

    if (contraseña.equals(usuario.getContrasenaUsuario())) {
        LocalDateTime tiempoEntrada = LocalDateTime.now();
        String rol = usuario.getCargo();
        GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
        gestionLogin.cargarLoginsDesdeExcel();

        Login nuevoLogin = new Login(
            tiempoEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            "",
            nombreUsuario,
            rol
        );
        gestionLogin.setUnLogin(nuevoLogin);

        INICIOPINEED abrir = new INICIOPINEED(nombreUsuario, rol, this);
        abrir.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                LOGINPINEED.this.setVisible(true);
            }
        });
        abrir.setVisible(true);
        this.setVisible(false);
        intentosFallidos.remove(nombreUsuario);
    } else {
        // Si es administrador, solo mostrar mensaje de error sin contar intentos
        if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
            mostrarMensajeError("Contraseña incorrecta.");
            return;
        }
        
        // Para usuarios no-administradores, aplicar la lógica de intentos
        int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
        intentosFallidos.put(nombreUsuario, intentos);

        if (intentos >= 3) {
            bloquearUsuario(usuario);
            mostrarMensajeError("Usuario bloqueado por múltiples intentos fallidos. Contacte al administrador: +502 5754-5388");
        } else {
            mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
        }
    }
    }//GEN-LAST:event_btnIngresarPineedActionPerformed

    
      // Añadir método para buscar piloto
    private Piloto buscarPiloto(String nombreUsuario) {
        // El formato esperado es "nombre.apellido&pineed"
        if (!nombreUsuario.endsWith("&pineed")) {
            return null;
        }
        
        String[] partes = nombreUsuario.replace("&pineed", "").split("\\.");
        if (partes.length != 2) {
            return null;
        }
        
        String nombre = partes[0];
        String apellido = partes[1];
        
        for (Piloto p : gestionPilotos.getPilotos()) {
            if (p.getNombrePiloto().toLowerCase().equals(nombre) &&
                p.getApellidoPiloto().toLowerCase().equals(apellido)) {
                return p;
            }
        }
        return null;
    }
    
    // Añadir método para validar login de piloto
    private void validarLoginPiloto(Piloto piloto, String nombreUsuario, String contraseña) {
        // En este ejemplo, usamos el DPI como contraseña
        String dpiComoString = String.valueOf(piloto.getNumeroDeDpi());
        
        if (contraseña.equals(dpiComoString)) {
            LocalDateTime tiempoEntrada = LocalDateTime.now();
            String rol = "PILOTO"; // Rol específico para pilotos
            
            GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
            gestionLogin.cargarLoginsDesdeExcel();

            Login nuevoLogin = new Login(
                tiempoEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "",
                nombreUsuario,
                rol
            );
            gestionLogin.setUnLogin(nuevoLogin);

            INICIOPINEED abrir = new INICIOPINEED(nombreUsuario, rol, this);
            abrir.setVisible(true);
            this.setVisible(false);
            intentosFallidos.remove(nombreUsuario);
        } else {
            int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
            intentosFallidos.put(nombreUsuario, intentos);

            if (intentos >= 3) {
                piloto.setEstadoPiloto("bloqueado");
                gestionPilotos.actualizarPiloto(piloto);
                mostrarMensajeError("Piloto bloqueado por múltiples intentos fallidos.");
            } else {
                mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
            }
        }
    }
        
    private boolean isPasswordVisible = false; // Controla la visibilidad de la contraseña

    
    // Acción del botón para mostrar/ocultar la contraseña
    private void btnMostrarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarContraseñaActionPerformed
        if (!isPasswordVisible) {
            // Mostrar contraseña
            txtContraseñaUsuario.setEchoChar((char) 0); // Muestra la contraseña
            btnMostrarContraseña.setText("OCULTAR"); // Cambia el texto del botón
        } else {
            // Ocultar contraseña
            txtContraseñaUsuario.setEchoChar('*'); // Oculta la contraseña
            btnMostrarContraseña.setText("MOSTRAR"); // Cambia el texto del botón
        }
        isPasswordVisible = !isPasswordVisible; // Alterna la visibilidad
    }//GEN-LAST:event_btnMostrarContraseñaActionPerformed

    private void txtContraseñaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseñaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseñaUsuarioActionPerformed

    // Método para cerrar sesión del usuario

    public void cerrarSesion(String nombreUsuario, String rol) {
        LocalDateTime tiempoSalida = LocalDateTime.now(); // Obtiene la hora de salida
        GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
        gestionLogin.cargarLoginsDesdeExcel();
        
        Vector<Login> logins = gestionLogin.getLogins(); // Obtiene los logins
        boolean sesionCerrada = false; // Indica si se cerró la sesión
        for (Login login : logins) {
            if (login.getPersonal().equals(nombreUsuario) && login.getTiempoSalida().isEmpty()) {
                login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))); // Establece la hora de salida
                gestionLogin.actualizarLogin(login); // Actualiza el login
                sesionCerrada = true; // Cambia el estado a cerrado
                break;
            }
        }
        
        if (!sesionCerrada) {
            System.out.println("No se encontró una sesión abierta para cerrar para el usuario: " + nombreUsuario);
        }
    }

    // Método para limpiar los campos del formulario
    public void limpiarCampos() {
        txtNombreUsuario.setText("Ingrese su usuario");
        txtNombreUsuario.setForeground(Color.GRAY);
        txtContraseñaUsuario.setText("Ingrese su contraseña");
        txtContraseñaUsuario.setForeground(Color.GRAY);
        txtContraseñaUsuario.setEchoChar((char) 0); // Muestra el texto del placeholder
    }

    // Método para gestionar la visibilidad del formulario
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            limpiarCampos(); // Limpia los campos al hacer visible
        }
        super.setVisible(visible);
    }

    // Método para buscar un usuario por su nombre
    private Usuarios buscarUsuario(String nombreUsuario) {
        for (Usuarios u : gestionUsuarios.getUsuarios()) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                return u; // Retorna el usuario encontrado
            }
        }
        return null; // Retorna nulo si no se encuentra
    }

    // Método para bloquear un usuario
    private void bloquearUsuario(Usuarios usuario) {
        usuario.setEstado("bloqueado");
        gestionUsuarios.actualizarUsuario(usuario); // Actualiza el estado del usuario
        JOptionPane.showMessageDialog(this, "El usuario ha sido bloqueado por múltiples intentos fallidos.");
    }

    // Método para mostrar mensajes de error
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    

    // Método main para ejecutar la aplicación
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LOGINPINEED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        //</editor-fold>

        // Crea e inicia la aplicación
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LOGINPINEED().setVisible(true);
            }
        });
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresarPineed;
    private javax.swing.JButton btnMostrarContraseña;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPasswordField txtContraseñaUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
