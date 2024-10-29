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
import java.util.Arrays;


// Clase principal para el formulario de inicio de sesión
public class LOGINPINEED extends javax.swing.JFrame {
    // Atributos
    private GESTIONUSUARIOS gestionUsuarios; // Gestión de usuarios
    private GESTIONPILOTOS gestionPilotos; // Gestión de usuarios
    private Map<String, Integer> intentosFallidos; // Mapa para rastrear intentos fallidos de inicio de sesión
 private Map<String, Long> tiempoBloqueoAdmin;
    private static final long TIEMPO_BLOQUEO = 180000; // 3 minutos en milisegundos
    
    
    // Constructor de la clase LOGINPINEED
    public LOGINPINEED() {
initComponents();
        setResizable(false);
        gestionUsuarios = new GESTIONUSUARIOS();
        gestionPilotos = new GESTIONPILOTOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
        gestionPilotos.cargarPilotosDesdeExcel();
        intentosFallidos = new HashMap<>();
  intentosFallidos = new HashMap<>();
        tiempoBloqueoAdmin = new HashMap<>(); // Inicializar el nuevo mapa
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

        // Verificar formato básico
        if (!esNombreUsuarioValido(nombreUsuario)) {
            mostrarMensajeError("Formato de usuario incorrecto. Por favor verifique su entrada.");
            return;
        }

        // Verificar administrador especial
        if (nombreUsuario.equals("1")) {
            // Verificar si el admin está temporalmente bloqueado
            if (estaAdminBloqueado()) {
                long tiempoRestante = getTiempoRestanteBloqueo();
                mostrarMensajeError("Demasiados intentos fallidos. Por favor, espere " + 
                    (tiempoRestante / 1000) + " segundos antes de intentar nuevamente.");
                return;
            }

            if (contraseña.equals("1")) {
                iniciarSesion(nombreUsuario, "ADMINISTRADOR");
                // Resetear intentos fallidos al lograr un inicio de sesión exitoso
                intentosFallidos.remove(nombreUsuario);
                tiempoBloqueoAdmin.remove(nombreUsuario);
                return;
            } else {
                manejarIntentoFallidoAdmin(nombreUsuario);
                return;
            }
        }
    // Normalizar el nombre de usuario
    String nombreNormalizado = normalizarNombreUsuario(nombreUsuario);

    // Verificar si es un piloto
    Piloto piloto = buscarPiloto(nombreNormalizado);
    if (piloto != null) {
        if (!piloto.getEstadoPiloto().equalsIgnoreCase("ACTIVO")) {
            mostrarMensajeEstadoPiloto(piloto.getEstadoPiloto());
            return;
        }
        validarLoginPiloto(piloto, nombreNormalizado, contraseña);
        return;
    }

    // Validación de usuario normal
    Usuarios usuario = buscarUsuario(nombreNormalizado);
    if (usuario == null) {
        mostrarMensajeError("Usuario no encontrado.");
        return;
    }

    // Verificar estado del usuario
    if (!usuario.getEstado().equalsIgnoreCase("ACTIVO")) {
        // Caso especial para administradores
        if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR") && 
            usuario.getEstado().equalsIgnoreCase("EN VACACIONES")) {
            JOptionPane.showMessageDialog(
                this,
                "Advertencia: Está ingresando durante su período de vacaciones.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            // Continuar con la validación de contraseña
        } else {
            mostrarMensajeEstadoUsuario(usuario.getEstado());
            return;
        }
    }

    // Validación de contraseña
    if (contraseña.equals(usuario.getContrasenaUsuario())) {
        iniciarSesion(nombreUsuario, usuario.getCargo());
        intentosFallidos.remove(nombreUsuario);
    } else {
        manejarIntentoFallido(usuario, nombreUsuario);
    }
    }//GEN-LAST:event_btnIngresarPineedActionPerformed

    
    // Métodos auxiliares para mejor organización
private void iniciarSesion(String nombreUsuario, String rol) {
    LocalDateTime tiempoEntrada = LocalDateTime.now();
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
}

private void mostrarMensajeEstadoUsuario(String estado) {
    String mensaje = "Acceso denegado. ";
    switch (estado.toUpperCase()) {
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
}


private void mostrarMensajeEstadoPiloto(String estado) {
    String mensaje = "Acceso denegado. ";
    switch (estado.toUpperCase()) {
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
}



private void manejarIntentoFallido(Usuarios usuario, String nombreUsuario) {
    // Los administradores nunca se bloquean
    if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
        mostrarMensajeError("Contraseña incorrecta.");
        return;
    }
    
    int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
    intentosFallidos.put(nombreUsuario, intentos);

    if (intentos >= 3) {
        // En lugar de eliminar, cambiamos el estado a BLOQUEADO
        usuario.setEstado("BLOQUEADO");
        gestionUsuarios.actualizarUsuario(usuario);
        mostrarMensajeError("Usuario bloqueado por múltiples intentos fallidos. Contacte al administrador: +502 5754-5388");
    } else {
        mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
    }
}

// También actualizamos el método bloquearUsuario para mantener consistencia
private void bloquearUsuario(Usuarios usuario) {
    // Verificación adicional para nunca bloquear administradores
    if (usuario.getCargo().equalsIgnoreCase("ADMINISTRADOR")) {
        return;
    }
    
    usuario.setEstado("BLOQUEADO");
    gestionUsuarios.actualizarUsuario(usuario);
    mostrarMensajeError("Usuario bloqueado por múltiples intentos fallidos. Contacte al administrador: +502 5754-5388");
}

// Método actualizado para buscar piloto
    private Piloto buscarPiloto(String nombreUsuario) {
        String nombreNormalizado = normalizarNombreUsuario(nombreUsuario);
        
        if (!nombreNormalizado.endsWith("&pineed")) {
            return null;
        }

        String nombreSinSufijo = nombreNormalizado.replace("&pineed", "");
        String[] partes = nombreSinSufijo.split("\\.");
        
        // Si no hay suficientes partes para nombre y apellido
        if (partes.length < 2) {
            return null;
        }

        // Obtener nombre y apellido normalizados
        String nombre = partes[0];
        // Unir el resto de las partes como apellido si hay más de dos partes
        String apellido = String.join(".", Arrays.copyOfRange(partes, 1, partes.length));

        for (Piloto p : gestionPilotos.getPilotos()) {
            String nombrePilotoNorm = normalizarNombreUsuario(p.getNombrePiloto());
            String apellidoPilotoNorm = normalizarNombreUsuario(p.getApellidoPiloto());

            // Comparar ignorando espacios extras y case-sensitive
            if (nombrePilotoNorm.replaceAll("\\s+", "").equals(nombre) &&
                apellidoPilotoNorm.replaceAll("\\s+", "").equals(apellido)) {
                return p;
            }
        }
        return null;
    }

    // Método actualizado para buscar usuario
    private Usuarios buscarUsuario(String nombreUsuario) {
        String nombreNormalizado = normalizarNombreUsuario(nombreUsuario);

        if (!esNombreUsuarioValido(nombreUsuario)) {
            return null;
        }

        for (Usuarios u : gestionUsuarios.getUsuarios()) {
            String nombreUsuarioNorm = normalizarNombreUsuario(u.getNombreUsuario());
            
            // Comparar ignorando espacios extras
            if (nombreUsuarioNorm.replaceAll("\\s+", "")
                    .equals(nombreNormalizado.replaceAll("\\s+", ""))) {
                return u;
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
                piloto.setEstadoPiloto("BLOQUEADO");
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

// Método mejorado para normalizar el nombre de usuario
    private String normalizarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return "";
        }

        // Si es el admin especial, no aplicar normalización
        if (nombreUsuario.equals("1")) {
            return nombreUsuario;
        }

        // Convertir a minúsculas y eliminar espacios extras
        String normalizado = nombreUsuario.toLowerCase().trim();
        
        // Eliminar espacios múltiples entre palabras
        normalizado = normalizado.replaceAll("\\s+", " ");
        
        // Quitar tildes y caracteres especiales
        normalizado = normalizado
            .replace('á', 'a')
            .replace('é', 'e')
            .replace('í', 'i')
            .replace('ó', 'o')
            .replace('ú', 'u')
            .replace('ü', 'u')
            .replace('ñ', 'n');

        // Si es un formato de piloto (contiene &pineed)
        if (normalizado.contains("&pineed")) {
            // Separar la parte del nombre de la parte &pineed
            String[] partes = normalizado.split("&pineed");
            if (partes.length > 0) {
                // Normalizar la parte del nombre
                String nombreParte = partes[0].trim();
                // Reemplazar espacios con puntos en el nombre
                nombreParte = nombreParte.replaceAll("\\s+", ".");
                // Reconstruir el nombre con el formato correcto
                normalizado = nombreParte + "&pineed";
            }
        }

        return normalizado;
    }

    // Método mejorado para verificar si el nombre de usuario es válido
    private boolean esNombreUsuarioValido(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return false;
        }

        // Si es el admin especial
        if (nombreUsuario.equals("1")) {
            return true;
        }

        // Verificar formato básico
        String normalizado = normalizarNombreUsuario(nombreUsuario);
        
        // Para usuarios piloto
        if (normalizado.endsWith("&pineed")) {
            String nombreSinSufijo = normalizado.replace("&pineed", "");
            String[] partes = nombreSinSufijo.split("\\.");
            
            // Debe tener al menos nombre y apellido
            if (partes.length < 2) {
                return false;
            }
            
            // Verificar que cada parte tenga contenido válido
            for (String parte : partes) {
                if (parte.trim().isEmpty() || !parte.matches("[a-z]+")) {
                    return false;
                }
            }
            return true;
        }
        
        // Para usuarios normales
        return normalizado.matches("[a-z]+(\\.[a-z]+)*");
    }



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

    
     // Nuevo método para verificar si el admin está bloqueado
    private boolean estaAdminBloqueado() {
        Long tiempoBloqueo = tiempoBloqueoAdmin.get("1");
        if (tiempoBloqueo == null) return false;

        long tiempoTranscurrido = System.currentTimeMillis() - tiempoBloqueo;
        if (tiempoTranscurrido >= TIEMPO_BLOQUEO) {
            // Si ya pasó el tiempo de bloqueo, eliminar el bloqueo y resetear intentos
            tiempoBloqueoAdmin.remove("1");
            intentosFallidos.remove("1");
            return false;
        }
        return true;
    }

    
    // Nuevo método para manejar intentos fallidos del admin
    private void manejarIntentoFallidoAdmin(String nombreUsuario) {
        int intentos = intentosFallidos.getOrDefault(nombreUsuario, 0) + 1;
        intentosFallidos.put(nombreUsuario, intentos);

        if (intentos >= 3) {
            // Establecer el tiempo de bloqueo
            tiempoBloqueoAdmin.put(nombreUsuario, System.currentTimeMillis());
            mostrarMensajeError("Demasiados intentos fallidos. Por favor, espere 3 minutos antes de intentar nuevamente.");
        } else {
            mostrarMensajeError("Contraseña incorrecta. Intento " + intentos + " de 3.");
        }
    }

    
    
    // Nuevo método para obtener el tiempo restante de bloqueo
    private long getTiempoRestanteBloqueo() {
        Long tiempoBloqueo = tiempoBloqueoAdmin.get("1");
        if (tiempoBloqueo == null) return 0;

        long tiempoTranscurrido = System.currentTimeMillis() - tiempoBloqueo;
        return Math.max(0, TIEMPO_BLOQUEO - tiempoTranscurrido);
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

 


 // Método mejorado para mostrar mensajes de error
    private void mostrarMensajeError(String mensaje) {
        // Para errores de formato, dar más información
        if (mensaje.contains("Formato de usuario incorrecto")) {
            JOptionPane.showMessageDialog(this,
                mensaje + "\n\nFormatos válidos:\n" +
                "- Usuario normal: nombre.apellido\n" +
                "- Piloto: nombre.apellido&pineed\n" +
                "- Los espacios serán convertidos automáticamente",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
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
