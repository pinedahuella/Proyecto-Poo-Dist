package GestionDeUsuarios;

import Login.LOGINPINEED;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.INICIOGESTIONUSUARIOS;
import com.sun.jdi.connect.spi.Connection;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;



public class MODIFICARGESTIONUSUARIOS extends javax.swing.JFrame {

    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuariosActivos = new Vector<>();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private Usuarios usuarioActual;
    private INICIOGESTIONUSUARIOS ventanaPrincipal;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;


        
        
        public MODIFICARGESTIONUSUARIOS(Usuarios usuario, INICIOGESTIONUSUARIOS ventanaPrincipal, String username, String role, LOGINPINEED loginFrame) {
            initComponents();
            setResizable(false); // Desactivar el cambio de tamaño
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                this.currentUser = username;
    this.userRole = role;
    this.loginFrame = loginFrame;
            this.ventanaPrincipal = ventanaPrincipal;
            if (ventanaPrincipal != null) {
                this.gestionUsuarios = ventanaPrincipal.gestionUsuarios;
                this.listaUsuariosActivos = gestionUsuarios.getUsuarios();
            }
configurarCamposDeTextoConPlaceholdersUsuarios();
            this.usuarioActual = usuario;
            if (usuario != null) {
                cargarDatosUsuario();
            }
        }   
    
    
    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            // Cargar datos y asegurar que estén en negro
            txtNombreUsuarioModificarModificar.setText(usuarioActual.getNombre());
            txtNombreUsuarioModificarModificar.setForeground(Color.BLACK);

            txtApellidoUsuarioModificarModificar.setText(usuarioActual.getApellido());
            txtApellidoUsuarioModificarModificar.setForeground(Color.BLACK);

            txtNumeroDeDpiUsuarioModificarModificar.setText(String.valueOf(usuarioActual.getNumeroDPI()));
            txtNumeroDeDpiUsuarioModificarModificar.setForeground(Color.BLACK);

            txtCorreoElectronicoUsuarioModificarModificar.setText(usuarioActual.getCorreoElectronico());
            txtCorreoElectronicoUsuarioModificarModificar.setForeground(Color.BLACK);

            txtNumeroTelefonicoUsuarioModificarModificar.setText(String.valueOf(usuarioActual.getNumeroTelefono()));
            txtNumeroTelefonicoUsuarioModificarModificar.setForeground(Color.BLACK);

            txtNombreDeUsuarioUsuarioModificarModificar.setText(usuarioActual.getNombreUsuario());
            txtNombreDeUsuarioUsuarioModificarModificar.setForeground(Color.BLACK);

            txtContraseñaUsuarioModificarModificar.setText(usuarioActual.getContrasenaUsuario());
            txtContraseñaUsuarioModificarModificar.setForeground(Color.BLACK);
            txtContraseñaUsuarioModificarModificar.setEchoChar('•');

            // Configurar ComboBoxes
            txtGeneroUsuarioModificarModificar.setSelectedItem(usuarioActual.getGenero());
            txtCargoUsuarioModificarModificar.setSelectedItem(usuarioActual.getCargo());
            txtEstadoUsuarioModificarModificar.setSelectedItem(usuarioActual.getEstado());

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = sdf.parse(usuarioActual.getFechaNacimiento());
                txtFechaDeNacimientoUsuarioModificarModificar.setDate(fechaNacimiento);
                ((JTextField) txtFechaDeNacimientoUsuarioModificarModificar.getDateEditor().getUiComponent()).setForeground(Color.BLACK);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la fecha de nacimiento: " + e.getMessage());
            }
        }
    }
    
 private void configurarCamposDeTextoConPlaceholdersUsuarios() {
    setupTextField(txtNombreUsuarioModificarModificar, "Ingrese el nombre");
    setupTextField(txtApellidoUsuarioModificarModificar, "Ingrese el apellido");
    setupTextField(txtNumeroDeDpiUsuarioModificarModificar, "Ingrese el DPI");
    setupTextField(txtCorreoElectronicoUsuarioModificarModificar, "Ingrese el correo electrónico");
    setupTextField(txtNumeroTelefonicoUsuarioModificarModificar, "Ingrese el teléfono");
    setupTextField(txtNombreDeUsuarioUsuarioModificarModificar, "Ingrese el nombre de usuario");
    setupPasswordField(txtContraseñaUsuarioModificarModificar, "Ingrese la contraseña");
    setupDateChooser(txtFechaDeNacimientoUsuarioModificarModificar, "dd/MM/yyyy");
}

// Método para configurar el campo de texto con placeholder
private void setupTextField(JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(java.awt.Color.GRAY);
    textField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(java.awt.Color.BLACK);
            }
        }
        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setForeground(java.awt.Color.GRAY);
                textField.setText(placeholder);
            }
        }
    });
}

// Método para configurar el campo de contraseña con placeholder
private void setupPasswordField(JPasswordField passwordField, String placeholder) {
    passwordField.setText(placeholder);
    passwordField.setForeground(java.awt.Color.GRAY);
    passwordField.setEchoChar((char) 0); // Para mostrar el placeholder

    passwordField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                passwordField.setText("");
                passwordField.setForeground(java.awt.Color.BLACK);
                passwordField.setEchoChar('•'); // Mostrar puntos cuando se escribe
            }
        }
        @Override
        public void focusLost(FocusEvent e) {
            if (passwordField.getPassword().length == 0) {
                passwordField.setForeground(java.awt.Color.GRAY);
                passwordField.setText(placeholder);
                passwordField.setEchoChar((char) 0); // Mostrar el placeholder
            }
        }
    });
}

// Método para configurar el DateChooser con placeholder
private void setupDateChooser(JDateChooser dateChooser, String placeholder) {
    JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
    editor.setText(placeholder);
    editor.setForeground(java.awt.Color.GRAY);
    
    editor.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (dateChooser.getDate() == null) {
                editor.setText("");
                editor.setForeground(java.awt.Color.BLACK);
            }
        }
        @Override
        public void focusLost(FocusEvent e) {
            if (dateChooser.getDate() == null) {
                editor.setText(placeholder);
                editor.setForeground(java.awt.Color.GRAY);
            }
        }
    });
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
// Método para enviar correo de actualización
private void enviarCorreoActualizacion(String destinatario, Usuarios usuario) throws IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    
    final String username = "distribuidorapine@gmail.com";
    final String password = "aura hcol bzmt plzf";
    
    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("PINEED - Actualización de Información");
        
        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        
        
           String nombreUsuario = usuario.getNombreUsuario().toLowerCase() + "." + 
                               usuario.getApellido().toLowerCase() + "&pineed";
           
        String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
            "<h2 style='color: #2c3e50; text-align: center;'><strong>Actualización de Datos en PINEED</strong></h2>" +
            "<p style='color: #34495e;'>Sus datos han sido actualizados exitosamente en nuestro sistema.</p>" +
            
            "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2c3e50; margin-top: 0;'>Información Actualizada:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Nombre:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getNombre() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Apellido:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getApellido() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>DPI:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getNumeroDPI() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Cargo:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getCargo() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Correo Electrónico:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getCorreoElectronico() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Teléfono:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getNumeroTelefono() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Género:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getGenero() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Fecha de Nacimiento:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getFechaNacimiento() + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Estado:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getEstado() + "</td></tr>" +
            "</table></div>" +

            "<div style='background-color: #e0f7fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2c3e50; margin-top: 0;'>Información de Acceso al Sistema:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Nombre de Usuario:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + nombreUsuario + "</td></tr>" +
            "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;'><strong>Contraseña:</strong></td>" +
            "<td style='padding: 8px 0; word-break: break-word;'>" + usuario.getContrasenaUsuario() + "</td></tr>" +
            "</table></div>" +

            "<div style='text-align: center; margin-top: 20px;'>" +
            "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
            "</div>" +
            "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
            "</div></body></html>";
            
        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Segunda parte - la imagen
        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);
        
        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
            multipart.addBodyPart(messageBodyPart);
        } else {
            System.out.println("Imagen no encontrada en el classpath.");
        }
        
        message.setContent(multipart);
        Transport.send(message);
        
        System.out.println("Correo enviado exitosamente a: " + destinatario);
        
    } catch (MessagingException e) {
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
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
    
    
    private boolean esNombreValido(String nombre) {
    // Expresión regular para validar que el nombre solo contenga letras y espacios
    return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
}
    
    
  // En la clase donde tienes el método de modificación:
private boolean existeContrasena(String contrasena, Usuarios usuarioActual) {
    // Recorrer la lista de usuarios y verificar si la contraseña ya existe
    for (Usuarios usuario : listaUsuariosActivos) {
        // Si la contraseña existe en otro usuario (no en el usuario actual)
        if (usuario != usuarioActual && 
            usuario.getContrasenaUsuario() != null && 
            usuario.getContrasenaUsuario().equals(contrasena)) {
            return true;
        }
    }
    return false;
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
        txtCargoUsuarioModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SECRETARIA", "ADMINISTRADOR" }));

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                .addComponent(txtEstadoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFechaDeNacimientoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCorreoElectronicoUsuarioModificarModificar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(52, 52, 52)
                                .addComponent(txtNumeroDeDpiUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(285, 296, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellidoUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreDeUsuarioUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(54, 54, 54)
                                .addComponent(txtContraseñaUsuarioModificarModificar)))
                        .addGap(295, 295, 295))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificarUsuariosSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreDeUsuarioUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContraseñaUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEstadoUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCargoUsuarioModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoUsuarioModificarModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        // Recuperar y limpiar los datos de entrada
        String nombreUsuario = txtNombreUsuarioModificarModificar.getText().trim();
        String apellidoUsuario = txtApellidoUsuarioModificarModificar.getText().trim();
        
        // Validar que nombre y apellido no estén vacíos
        if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido son obligatorios.");
            return;
        }

        // Validar que nombre y apellido no estén vacíos
        if (nombreUsuario.isEmpty() || apellidoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido son obligatorios.");
            return;
        }
        
        // Validar que el nombre y apellido no contengan números
        if (!esNombreValido(nombreUsuario)) {
            JOptionPane.showMessageDialog(this, "El nombre no puede contener números o caracteres especiales.");
            return;
        }

        if (!esNombreValido(apellidoUsuario)) {
            JOptionPane.showMessageDialog(this, "El apellido no puede contener números o caracteres especiales.");
            return;
        }
        
        // Obtener el nombre de usuario ingresado
        String nombreDeUsuario = txtNombreDeUsuarioUsuarioModificarModificar.getText().trim().toLowerCase();
        
        // Generar las dos versiones posibles del nombre de usuario (con y sin tildes)
        String nombreUsuarioSinTildes = removeTildes(nombreUsuario.toLowerCase()) + "." + 
                                      removeTildes(apellidoUsuario.toLowerCase()) + "&pineed";
                                      
        String nombreUsuarioConTildes = nombreUsuario.toLowerCase() + "." + 
                                      apellidoUsuario.toLowerCase() + "&pineed";

        // Remover espacios extras que pudieran haberse colado
        nombreDeUsuario = nombreDeUsuario.replaceAll("\\s+", "");
        nombreUsuarioSinTildes = nombreUsuarioSinTildes.replaceAll("\\s+", "");
        nombreUsuarioConTildes = nombreUsuarioConTildes.replaceAll("\\s+", "");
        
        // Validar que el nombre de usuario coincida con alguno de los formatos esperados
        if (!nombreDeUsuario.equals(nombreUsuarioSinTildes) && !nombreDeUsuario.equals(nombreUsuarioConTildes)) {
            String mensaje = "El nombre de usuario debe seguir el formato: nombre.apellido&pineed\n" +
                           "Para sus datos, puede ser:\n" +
                           "- Sin tildes: " + nombreUsuarioSinTildes + "\n" +
                           "- Con tildes: " + nombreUsuarioConTildes + "\n\n" +
                           "Nota: Aunque ambas formas son válidas, se recomienda usar la versión sin tildes " +
                           "para mayor compatibilidad.";
            JOptionPane.showMessageDialog(this, mensaje);
            return;
        }

        // Obtener y validar el número de DPI
        String dpiText = txtNumeroDeDpiUsuarioModificarModificar.getText().trim();
        if (dpiText.length() != 13) {
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }
        long numeroDeDpiUsuario = Long.parseLong(dpiText);

// Obtener la contraseña
       // Obtener la contraseña
        String contrasenaUsuario = txtContraseñaUsuarioModificarModificar.getText().trim();
        
        // Validar longitud de la contraseña
        if (contrasenaUsuario.length() != 13) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe contener exactamente 13 caracteres.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la contraseña solo contenga dígitos
        if (!contrasenaUsuario.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(this,
                "La contraseña debe contener solo números.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si la contraseña ya existe en otro usuario
        if (existeContrasena(contrasenaUsuario, usuarioActual)) {
            JOptionPane.showMessageDialog(this,
                "La contraseña ya ha sido utilizada por otro usuario. Por favor, elija una contraseña diferente.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
 


        // Continuar con el resto de las validaciones
        String cargoUsuario = txtCargoUsuarioModificarModificar.getSelectedItem().toString().trim();
        String correoElectronicoUsuario = txtCorreoElectronicoUsuarioModificarModificar.getText().trim();
        int numeroTelefonicoUsuario = Integer.parseInt(txtNumeroTelefonicoUsuarioModificarModificar.getText().trim());
        String generoUsuario = txtGeneroUsuarioModificarModificar.getSelectedItem().toString().trim();
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

        if (String.valueOf(numeroTelefonicoUsuario).length() != 8) {
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }

        // Verificar si los datos únicos han cambiado
        boolean dpiCambiado = numeroDeDpiUsuario != usuarioActual.getNumeroDPI();
        boolean telefonoCambiado = numeroTelefonicoUsuario != usuarioActual.getNumeroTelefono();
        boolean correoCambiado = !correoElectronicoUsuario.equals(usuarioActual.getCorreoElectronico());

        // Verificar duplicados solo si los datos únicos han cambiado
        for (Usuarios usuarioExistente : listaUsuariosActivos) {
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

        
        
        
            // Validar longitud del nombre y apellido
        if (nombreUsuario.length() < 2 || nombreUsuario.length() > 50) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener entre 2 y 50 caracteres.");
            return;
        }
        if (apellidoUsuario.length() < 2 || apellidoUsuario.length() > 50) {
            JOptionPane.showMessageDialog(this, "El apellido debe tener entre 2 y 50 caracteres.");
            return;
        }

        
        
        
// Validar que el usuario tenga al menos 18 años
Calendar calHoy = Calendar.getInstance();
Calendar calNacimiento = Calendar.getInstance();
calNacimiento.setTime(fechaNacimientoUsuarioDate);

int edad = calHoy.get(Calendar.YEAR) - calNacimiento.get(Calendar.YEAR);
if (calHoy.get(Calendar.DAY_OF_YEAR) < calNacimiento.get(Calendar.DAY_OF_YEAR)) {
    edad--;
}

if (edad < 18) {
    JOptionPane.showMessageDialog(this, "El usuario debe ser mayor de 18 años.");
    return;
}


               // Mostrar diálogo de confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea modificar al usuario con los siguientes datos?\n\n" +
            "Nombre completo: " + nombreUsuario + " " + apellidoUsuario + "\n" +
            "DPI: " + numeroDeDpiUsuario + "\n" +
            "Cargo: " + cargoUsuario + "\n" +
            "Correo electrónico: " + correoElectronicoUsuario + "\n" +
            "Teléfono: " + numeroTelefonicoUsuario + "\n" +
            "Género: " + generoUsuario + "\n" +
            "Fecha de nacimiento: " + fechaDeNacimientoUsuario + "\n" +
            "Estado: " + estadoUsuario,
            "Confirmar modificación de usuario",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        
        
        // Almacenar los datos validados en variables finales para usar en el hilo
        final String finalNombreDeUsuario = nombreDeUsuario;
        final String finalContrasenaUsuario = contrasenaUsuario;
        final String finalNombreUsuario = nombreUsuario;
        final String finalApellidoUsuario = apellidoUsuario;
        final String finalCargoUsuario = cargoUsuario;
        final String finalGeneroUsuario = generoUsuario;
        final long finalNumeroDeDpiUsuario = numeroDeDpiUsuario;
        final String finalFechaDeNacimientoUsuario = fechaDeNacimientoUsuario;
        final int finalNumeroTelefonicoUsuario = numeroTelefonicoUsuario;
        final String finalCorreoElectronicoUsuario = correoElectronicoUsuario;
        final String finalEstadoUsuario = estadoUsuario;

        // Crear y mostrar el diálogo de progreso
        JDialog dialogoProceso = new JDialog(this, "Procesando", true);
        dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel contenidoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Modificando usuario...");
        
        JLabel mensajeLabel = new JLabel("Actualizando información y enviando correo electrónico...");
        mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        contenidoPanel.add(mensajeLabel, gbc);
        contenidoPanel.add(progressBar, gbc);
        
        panel.add(contenidoPanel, BorderLayout.CENTER);
        dialogoProceso.add(panel);
        dialogoProceso.setSize(400, 150);
        dialogoProceso.setLocationRelativeTo(this);

        // Crear e iniciar el hilo de procesamiento
        Thread processingThread = new Thread(() -> {
            try {
                // Actualizar el usuario actual con los datos validados
                synchronized(usuarioActual) {
                    usuarioActual.setNombreUsuario(finalNombreDeUsuario);
                    usuarioActual.setContrasenaUsuario(finalContrasenaUsuario);
                    usuarioActual.setNombre(finalNombreUsuario);
                    usuarioActual.setApellido(finalApellidoUsuario);
                    usuarioActual.setCargo(finalCargoUsuario);
                    usuarioActual.setGenero(finalGeneroUsuario);
                    usuarioActual.setNumeroDPI(finalNumeroDeDpiUsuario);
                    usuarioActual.setFechaNacimiento(finalFechaDeNacimientoUsuario);
                    usuarioActual.setNumeroTelefono(finalNumeroTelefonicoUsuario);
                    usuarioActual.setCorreoElectronico(finalCorreoElectronicoUsuario);
                    usuarioActual.setEstado(finalEstadoUsuario);
                }

                // Actualizar en la base de datos
                gestionUsuarios.actualizarUsuario(usuarioActual);

                // Enviar correo
                enviarCorreoActualizacion(finalCorreoElectronicoUsuario, usuarioActual);

                // Actualizar UI en el EDT
                SwingUtilities.invokeLater(() -> {
                    try {
                        // Actualizar la tabla principal
                        ventanaPrincipal.actualizarTabla();
                        
                        // Cerrar el diálogo de progreso
                        dialogoProceso.dispose();
                        
                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(
                            this,
                            "¡Usuario modificado exitosamente!\n\n" +
                            "Se ha enviado un correo electrónico a:\n" + 
                            finalCorreoElectronicoUsuario + "\n" +
                            "con los datos actualizados.",
                            "Modificación exitosa",
                            JOptionPane.INFORMATION_MESSAGE
                        );

                        // Cerrar la ventana actual y mostrar la principal
                        ventanaPrincipal.setVisible(true);
                        this.dispose();
                    } catch (Exception e) {
                        manejarError("Error al actualizar la interfaz", e);
                    }
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    manejarError("Error durante la actualización", e);
                });
            }
        });

        // Iniciar el proceso y mostrar el diálogo
        processingThread.start();
        dialogoProceso.setVisible(true);

    } catch (NumberFormatException e) {
        manejarError("Error en el formato de número", e);
    } catch (Exception e) {
        manejarError("Error al modificar usuario", e);
    }
    }//GEN-LAST:event_btnModificarUsuariosSistemaActionPerformed
   
// Método auxiliar para manejar errores
private void manejarError(String mensaje, Exception e) {
    SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(
            this,
            mensaje + ": " + e.getMessage() +
            "\nPor favor, intente nuevamente.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    });
}

// Agregar este método fuera del btnAgregarUsuarioSistemaActionPerformed, pero dentro de la clase
private String removeTildes(String input) {
    return input.replace('á', 'a')
               .replace('é', 'e')
               .replace('í', 'i')
               .replace('ó', 'o')
               .replace('ú', 'u')
               .replace('Á', 'a')
               .replace('É', 'e')
               .replace('Í', 'i')
               .replace('Ó', 'o')
               .replace('Ú', 'u')
               .replace('ñ', 'n')
               .replace('Ñ', 'n');
}

    
    
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
