package GestionDePilotos;

import Login.LOGINPINEED;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.Piloto;
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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class MODIFICARGESTIONPILOTOS extends javax.swing.JFrame {


    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private Piloto pilotoActual;
    private INICIOGESTIONPILOTOS ventanaPrincipal;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;





    public MODIFICARGESTIONPILOTOS(Piloto piloto, INICIOGESTIONPILOTOS ventanaPrincipal, String username, String role, LOGINPINEED loginFrame) {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false); // Desactivar el cambio de tamaño
    this.currentUser = username;
    this.userRole = role;
    this.loginFrame = loginFrame;
    
    if (ventanaPrincipal != null) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.gestionPilotos = ventanaPrincipal.gestionPilotos;
        this.listaPilotos = gestionPilotos.getPilotos();
    }

    // Configurar placeholders
    configurarCamposDeTextoConPlaceholders();

    if (piloto != null) {
        this.pilotoActual = piloto;
        cargarDatosPiloto();
    }
}

// Método para cargar datos del piloto actual
private void cargarDatosPiloto() {
    if (pilotoActual != null) {
        txtNombrePilotoModificarModificar.setText(pilotoActual.getNombrePiloto());
        txtNombrePilotoModificarModificar.setForeground(Color.BLACK);

        txtApellidoPilotoModificarModificar.setText(pilotoActual.getApellidoPiloto());
        txtApellidoPilotoModificarModificar.setForeground(Color.BLACK);

        txtNumeroDeDpiPilotoModificarModificar.setText(String.valueOf(pilotoActual.getNumeroDeDpi()));
        txtNumeroDeDpiPilotoModificarModificar.setForeground(Color.BLACK);

        txtCorreoElectronicoPilotoModificarModificar.setText(pilotoActual.getCorreoElectronicoPiloto());
        txtCorreoElectronicoPilotoModificarModificar.setForeground(Color.BLACK);

        txtNumeroTelefonicoPilotoModificarModificar.setText(String.valueOf(pilotoActual.getNumeroTelefonicoPiloto()));
        txtNumeroTelefonicoPilotoModificarModificar.setForeground(Color.BLACK);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse(pilotoActual.getFechaDeNacimiento());
            txtFechaDeNacimientoPilotoModificarModificar.setDate(fechaNacimiento);
            JTextField editor = (JTextField) txtFechaDeNacimientoPilotoModificarModificar.getDateEditor().getUiComponent();
            editor.setForeground(Color.BLACK);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la fecha de nacimiento: " + e.getMessage());
        }
    }
}

// Configuración de placeholder para JTextField
private void setupTextField(JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY);

    textField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });
}

// Configuración de placeholder en JDateChooser
private void setupDateChooserWithPlaceholder(JDateChooser dateChooser, String placeholder) {
    dateChooser.setDateFormatString("dd/MM/yyyy");
    dateChooser.setDate(null);

    JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
    editor.setText(placeholder);
    editor.setForeground(Color.GRAY);

    editor.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (dateChooser.getDate() == null) {
                editor.setText("");
                editor.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (dateChooser.getDate() == null) {
                editor.setText(placeholder);
                editor.setForeground(Color.GRAY);
            }
        }
    });
}

// Configuración de todos los campos con placeholders
private void configurarCamposDeTextoConPlaceholders() {
    setupTextField(txtNombrePilotoModificarModificar, "Ingrese el nombre");
    setupTextField(txtApellidoPilotoModificarModificar, "Ingrese el apellido");
    setupTextField(txtNumeroDeDpiPilotoModificarModificar, "Ingrese el número de DPI");
    setupTextField(txtCorreoElectronicoPilotoModificarModificar, "Ingrese el correo electrónico");
    setupTextField(txtNumeroTelefonicoPilotoModificarModificar, "Ingrese el número telefónico");
    setupDateChooserWithPlaceholder(txtFechaDeNacimientoPilotoModificarModificar, "dd/MM/yyyy");
}

// Método para limpiar y restablecer los placeholders
public void limpiarCampos() {
    txtNombrePilotoModificarModificar.setText("Ingrese el nombre");
    txtNombrePilotoModificarModificar.setForeground(Color.GRAY);

    txtApellidoPilotoModificarModificar.setText("Ingrese el apellido");
    txtApellidoPilotoModificarModificar.setForeground(Color.GRAY);

    txtNumeroDeDpiPilotoModificarModificar.setText("Ingrese el número de DPI");
    txtNumeroDeDpiPilotoModificarModificar.setForeground(Color.GRAY);

    txtCorreoElectronicoPilotoModificarModificar.setText("Ingrese el correo electrónico");
    txtCorreoElectronicoPilotoModificarModificar.setForeground(Color.GRAY);

    txtNumeroTelefonicoPilotoModificarModificar.setText("Ingrese el número telefónico");
    txtNumeroTelefonicoPilotoModificarModificar.setForeground(Color.GRAY);

    setupDateChooserWithPlaceholder(txtFechaDeNacimientoPilotoModificarModificar, "dd/MM/yyyy");
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
    
    

  
    // Método para eliminar tildes y espacios adicionales
    public static String eliminarTildes(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern patron = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return patron.matcher(normalizado).replaceAll("").trim().replaceAll("\\s+", ""); // Elimina espacios
    
    }
    
private void enviarCorreoActualizacionPiloto(String destinatario, Piloto piloto) throws IOException {
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
        message.setSubject("PINEED - Actualización de Información de Piloto");
        
        Multipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        
                String[] nombres = eliminarTildes(piloto.getNombrePiloto()).toLowerCase().split(" ");
            String[] apellidos = eliminarTildes(piloto.getApellidoPiloto()).toLowerCase().split(" ");

            // Concatenar los nombres y apellidos sin espacios
            String nombreCompleto = String.join("", nombres); // Unir los nombres sin espacios
            String apellidoCompleto = String.join("", apellidos); // Unir los apellidos sin espacios

            // Formar el nombre de usuario
            String nombreUsuario = nombreCompleto + "." + apellidoCompleto + "&pineed";
        
        String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
    "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
    "<h2 style='color: #6a0dad; text-align: center;'><strong>Modificación de Datos en PINEED</strong></h2>" +
    "<p style='color: #ffffff; background-color: #9370db; padding: 10px; border-radius: 5px;'>Sus datos han sido modificados exitosamente en nuestro sistema.</p>" +

    "<div style='background-color: #e6e6fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #6a0dad; margin-top: 0;'>Información del Registro:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Nombre:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getNombrePiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Apellido:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getApellidoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>DPI:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getNumeroDeDpi() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Tipo de Licencia:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getTipoLicencia() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;max-width: 200px; white-space: normal; word-wrap: break-word; overflow-wrap: break-word;'><strong style='color: #6a0dad;'>Correo Electrónico:</strong></td>" +
    "<td style='color: #ffffff;max-width: 250px; overflow: hidden; text-overflow: ellipsis;'>" + piloto.getCorreoElectronicoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Teléfono:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getNumeroTelefonicoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Género:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getGeneroPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Fecha de Nacimiento:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getFechaDeNacimiento() + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Estado:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getEstadoPiloto() + "</td></tr>" +
    "</table></div>" +

    "<div style='background-color: #e6e6fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #6a0dad; margin-top: 0;'>Sus Credenciales de Acceso:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Nombre de Usuario:</strong></td>" +
    "<td style='color: #ffffff;'>" + nombreUsuario + "</td></tr>" +
    "<tr><td style='padding: 8px 0; width: 30%; vertical-align: top;white-space: normal; word-wrap: break-word;'><strong style='color: #6a0dad;'>Contraseña:</strong></td>" +
    "<td style='color: #ffffff;'>" + piloto.getNumeroDeDpi() + "</td></tr>" +
    "</table></div>" +

    "<div style='text-align: center; margin-top: 20px;'>" +
    "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
    "</div>" +
    "<p style='color: #6a0dad; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
    "</div></body></html>";
            
        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

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
        
        // Confirmación de envío exitoso
        System.out.println("El correo fue enviado correctamente a: " + destinatario);
        
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
    
    private boolean esSoloLetras(String texto) {
    // Expresión regular para letras solamente (incluyendo letras con acentos y ñ)
    return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
}

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnModificarPilotosSistema = new javax.swing.JButton();
        txtEstadoPilotoModificarModificar = new javax.swing.JComboBox<>();
        txtNumeroTelefonicoPilotoModificarModificar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoModificarModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtFechaDeNacimientoPilotoModificarModificar = new com.toedter.calendar.JDateChooser();
        txtCorreoElectronicoPilotoModificarModificar = new javax.swing.JTextField();
        txtNumeroDeDpiPilotoModificarModificar = new javax.swing.JTextField();
        txtTipoDeLicenciaPilotoModificarModificar = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroPilotoModificarModificar = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtApellidoPilotoModificarModificar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnModificarPilotosSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnModificarPilotosSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnModificarPilotosSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPilotosSistema.setText("MODIFICAR");
        btnModificarPilotosSistema.setBorder(null);
        btnModificarPilotosSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPilotosSistemaActionPerformed(evt);
            }
        });

        txtEstadoPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));
        txtEstadoPilotoModificarModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoPilotoModificarModificarActionPerformed(evt);
            }
        });

        txtNumeroTelefonicoPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNumeroTelefonicoPilotoModificarModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelefonicoPilotoModificarModificarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        txtNombrePilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        txtFechaDeNacimientoPilotoModificarModificar.setDateFormatString("dd/MM/yyyy");
        txtFechaDeNacimientoPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtCorreoElectronicoPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtNumeroDeDpiPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtTipoDeLicenciaPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoDeLicenciaPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtGeneroPilotoModificarModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        txtApellidoPilotoModificarModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnModificarPilotosSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGeneroPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumeroDeDpiPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoDeLicenciaPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCorreoElectronicoPilotoModificarModificar))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombrePilotoModificarModificar)
                                .addComponent(txtApellidoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroTelefonicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstadoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDeLicenciaPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGeneroPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNumeroTelefonicoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoPilotoModificarModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(89, 89, 89)
                .addComponent(btnModificarPilotosSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
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

    private void btnModificarPilotosSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPilotosSistemaActionPerformed
    try {
        // Obtener valores originales (con tildes)
        String nombrePiloto = txtNombrePilotoModificarModificar.getText().trim();
        String apellidoPiloto = txtApellidoPilotoModificarModificar.getText().trim();
        long numeroDeDpiPiloto = Long.parseLong(txtNumeroDeDpiPilotoModificarModificar.getText().trim());
        String tipoLicencia = txtTipoDeLicenciaPilotoModificarModificar.getSelectedItem().toString().trim();
        String correoElectronicoPiloto = txtCorreoElectronicoPilotoModificarModificar.getText().trim();
        int numeroTelefonicoPiloto = Integer.parseInt(txtNumeroTelefonicoPilotoModificarModificar.getText().trim());
        String generoPiloto = txtGeneroPilotoModificarModificar.getSelectedItem().toString().trim();
        String estadoPiloto = txtEstadoPilotoModificarModificar.getSelectedItem().toString().trim();

        // Validar fecha de nacimiento
        Date fechaNacimientoPilotoDate = txtFechaDeNacimientoPilotoModificarModificar.getDate();
        if (fechaNacimientoPilotoDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
            return;
        }

        // Validaciones básicas
        if (!correoElectronicoPiloto.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
            return;
        }
        
        if (!esSoloLetras(nombrePiloto)) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.");
            return;
        }
        
        if (apellidoPiloto.equals("Ingrese el apellido") || apellidoPiloto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el apellido del piloto.");
            return;
        }
        
        if (!esSoloLetras(apellidoPiloto)) {
            JOptionPane.showMessageDialog(this, "El apellido solo debe contener letras.");
            return;
        }

        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDeNacimientoPiloto = sdf.format(fechaNacimientoPilotoDate);

        // Validar campos vacíos
        if (nombrePiloto.isEmpty() || apellidoPiloto.isEmpty() || tipoLicencia.isEmpty() ||
            correoElectronicoPiloto.isEmpty() || generoPiloto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
            return;
        }

        // Validar longitud de DPI y teléfono
        if (String.valueOf(numeroDeDpiPiloto).length() != 13) {
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }

        if (String.valueOf(numeroTelefonicoPiloto).length() != 8) {
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }

        // Verificar duplicados SOLO si los valores han cambiado
        for (Piloto pilotoExistente : listaPilotos) {
            // Ignorar el piloto actual en la comparación
            if (!pilotoExistente.equals(pilotoActual)) {
                // Verificar DPI solo si ha cambiado
                if (numeroDeDpiPiloto != pilotoActual.getNumeroDeDpi() && 
                    pilotoExistente.getNumeroDeDpi() == numeroDeDpiPiloto) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
                    return;
                }
                
                // Verificar teléfono solo si ha cambiado
                if (numeroTelefonicoPiloto != pilotoActual.getNumeroTelefonicoPiloto() && 
                    pilotoExistente.getNumeroTelefonicoPiloto() == numeroTelefonicoPiloto) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
                    return;
                }
                
                // Verificar correo solo si ha cambiado
                if (!correoElectronicoPiloto.equals(pilotoActual.getCorreoElectronicoPiloto()) && 
                    pilotoExistente.getCorreoElectronicoPiloto().equals(correoElectronicoPiloto)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
                    return;
                }
            }
        }

        
         
            // Validar longitud del nombre y apellido
        if (nombrePiloto.length() < 2 || nombrePiloto.length() > 50) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener entre 2 y 50 caracteres.");
            return;
        }
        if (nombrePiloto.length() < 2 || nombrePiloto.length() > 50) {
            JOptionPane.showMessageDialog(this, "El apellido debe tener entre 2 y 50 caracteres.");
            return;
        }

        
        
        
// Validar que el usuario tenga al menos 18 años
Calendar calHoy = Calendar.getInstance();
Calendar calNacimiento = Calendar.getInstance();
calNacimiento.setTime(fechaNacimientoPilotoDate);

int edad = calHoy.get(Calendar.YEAR) - calNacimiento.get(Calendar.YEAR);
if (calHoy.get(Calendar.DAY_OF_YEAR) < calNacimiento.get(Calendar.DAY_OF_YEAR)) {
    edad--;
}

if (edad < 18) {
    JOptionPane.showMessageDialog(this, "El usuario debe ser mayor de 18 años.");
    return;
}

        // Mostrar diálogo de confirmación con los cambios
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea modificar al piloto con los siguientes datos?\n\n" +
            "Nombre completo: " + nombrePiloto + " " + apellidoPiloto + "\n" +
            "DPI: " + numeroDeDpiPiloto + "\n" +
            "Tipo de licencia: " + tipoLicencia + "\n" +
            "Correo electrónico: " + correoElectronicoPiloto + "\n" +
            "Teléfono: " + numeroTelefonicoPiloto + "\n" +
            "Género: " + generoPiloto + "\n" +
            "Fecha de nacimiento: " + fechaDeNacimientoPiloto + "\n" +
            "Estado: " + estadoPiloto,
            "Confirmar modificación de piloto",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        

        // Crear y mostrar el diálogo de progreso
        JDialog dialogoProceso = new JDialog(this, "Procesando", true);
        dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel para el contenido
        JPanel contenidoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Crear y configurar la barra de progreso
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Modificando datos...");
        
        // Etiqueta de mensaje
        JLabel mensajeLabel = new JLabel("Actualizando información y enviando correo electrónico...");
        mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Agregar componentes al panel
        contenidoPanel.add(mensajeLabel, gbc);
        contenidoPanel.add(progressBar, gbc);
        
        panel.add(contenidoPanel, BorderLayout.CENTER);
        dialogoProceso.add(panel);
        dialogoProceso.setSize(400, 150);
        dialogoProceso.setLocationRelativeTo(this);

        // Crear thread para procesar en segundo plano
        Thread processingThread = new Thread(() -> {
            try {
                // Actualizar el piloto
                pilotoActual.setNombrePiloto(nombrePiloto);
                pilotoActual.setApellidoPiloto(apellidoPiloto);
                pilotoActual.setNumeroDeDpi(numeroDeDpiPiloto);
                pilotoActual.setTipoLicencia(tipoLicencia);
                pilotoActual.setCorreoElectronicoPiloto(correoElectronicoPiloto);
                pilotoActual.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
                pilotoActual.setGeneroPiloto(generoPiloto);
                pilotoActual.setFechaDeNacimiento(fechaDeNacimientoPiloto);
                pilotoActual.setEstadoPiloto(estadoPiloto);

                // Actualizar en la base de datos
                gestionPilotos.actualizarPiloto(pilotoActual);

                // Enviar correo
                enviarCorreoActualizacionPiloto(correoElectronicoPiloto, pilotoActual);

                // Actualizar UI en el EDT
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    
                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(
                        this,
                        "¡Piloto modificado exitosamente!\n\n" +
                        "Se ha enviado un correo electrónico a:\n" + 
                        correoElectronicoPiloto + "\n" +
                        "con los datos actualizados.",
                        "Modificación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    // Actualizar la tabla principal y cerrar ventana
                    ventanaPrincipal.actualizarTabla();
                    ventanaPrincipal.setVisible(true);
                    this.dispose();
                });

            } catch (IllegalStateException | IllegalArgumentException e) {
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    JOptionPane.showMessageDialog(
                        this,
                        "Error de validación: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                });
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al enviar el correo electrónico: " + e.getMessage() +
                        "\nPor favor, intente nuevamente.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    JOptionPane.showMessageDialog(
                        this,
                        "Error inesperado: " + e.getMessage() +
                        "\nPor favor, contacte al administrador del sistema.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                });
            }
        });

        // Iniciar el procesamiento en segundo plano
        processingThread.start();
        
        // Mostrar el diálogo de progreso
        dialogoProceso.setVisible(true);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al modificar piloto: " + e.getMessage());
    }
    }//GEN-LAST:event_btnModificarPilotosSistemaActionPerformed

// Método para remover tildes que se usará en ambas funciones
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



    private void txtNumeroTelefonicoPilotoModificarModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelefonicoPilotoModificarModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTelefonicoPilotoModificarModificarActionPerformed

    private void txtEstadoPilotoModificarModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoPilotoModificarModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoPilotoModificarModificarActionPerformed

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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            
                 String username = "defaultUser";  // Replace with actual username or logic
        String role = "defaultRole"; 
        
        LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object
        
        // Create the MODIFICARGESTIONCAMIONES instance with the required parameters
        new MODIFICARGESTIONPILOTOS(null, null, username, role, loginFrame).setVisible(true);
    
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificarPilotosSistema;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtApellidoPilotoModificarModificar;
    private javax.swing.JTextField txtCorreoElectronicoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtEstadoPilotoModificarModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtGeneroPilotoModificarModificar;
    private javax.swing.JTextField txtNombrePilotoModificarModificar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoModificarModificar;
    private javax.swing.JTextField txtNumeroTelefonicoPilotoModificarModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPilotoModificarModificar;
    // End of variables declaration//GEN-END:variables
}
