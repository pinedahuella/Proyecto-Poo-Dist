package GestionDePilotos;

import GestionDePilotos.GESTIONPILOTOS;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDePilotos.Piloto;
import GestionDePilotos.Piloto;
import Login.LOGINPINEED;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
import javax.swing.SwingUtilities;



public class AGREGARGESTIONPILOTOS extends javax.swing.JFrame {

    public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private int indiceActual;
    private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;

    public AGREGARGESTIONPILOTOS(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;
        setResizable(false); // Desactivar el cambio de tamaño
        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();

        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Correo", "Teléfono", "Género", "Nacimiento", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);

        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }
        
            configurarCamposDeTextoConPlaceholders(); // Configura los placeholders en los campos de texto


        cargarPilotosEnTabla();
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void cargarPilotosEnTabla() {
        modeloPilotos.setRowCount(0);
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
    }

    // Método para configurar el placeholder en campos de texto
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

// Método para configurar el placeholder en campos de contraseña
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



// Método para configurar todos los campos con placeholders
private void configurarCamposDeTextoConPlaceholders() {
    setupTextField(txtNombrePiloto, "Ingrese el nombre");
    setupTextField(txtApellidoPiloto, "Ingrese el apellido");
    setupTextField(txtNumeroDeDpiPiloto, "Ingrese el número de DPI");
    setupTextField(txtCorreoElectronicoPiloto, "Ingrese el correo electrónico");
    setupTextField(txtNumeroTelefonicoPiloto, "Ingrese el número telefónico");
    setupDateChooserWithPlaceholder(txtFechaDeNacimientoPiloto, "dd/MM/yyyy"); // Configura el JDateChooser con placeholder
}



    
    // Método para configurar el JDateChooser con un placeholder en gris
private void setupDateChooserWithPlaceholder(JDateChooser dateChooser, String placeholder) {
  dateChooser.setDateFormatString("dd/MM/yyyy"); // Formato de fecha deseado
    dateChooser.setDate(null); // Asegúrate de que esté vacío al inicio

    // Obtener el editor de fecha como JTextField
    JTextField editor = (JTextField) dateChooser.getDateEditor().getUiComponent();
    
    // Inicializar con el placeholder
    editor.setText(placeholder);
    editor.setForeground(Color.GRAY);
    
    // Añadir un listener para manejar el enfoque
    editor.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            // Si no hay fecha seleccionada, establecer el placeholder
            if (dateChooser.getDate() == null) {
                editor.setText(""); // Limpia el texto al enfocar
                editor.setForeground(Color.BLACK); // Cambia el color a negro
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Si no hay fecha seleccionada, restablecer el placeholder
            if (dateChooser.getDate() == null) {
                editor.setText(placeholder); // Restaura el placeholder si está vacío
                editor.setForeground(Color.GRAY); // Cambia el color a gris
            }
        }
    });
}


// Método para limpiar y restablecer los placeholders
public void limpiarCampos() {
    txtNombrePiloto.setText("Ingrese el nombre");
    txtNombrePiloto.setForeground(Color.GRAY);

    txtApellidoPiloto.setText("Ingrese el apellido");
    txtApellidoPiloto.setForeground(Color.GRAY);

    txtNumeroDeDpiPiloto.setText("Ingrese el número de DPI");
    txtNumeroDeDpiPiloto.setForeground(Color.GRAY);



    txtCorreoElectronicoPiloto.setText("Ingrese el correo electrónico");
    txtCorreoElectronicoPiloto.setForeground(Color.GRAY);

    txtNumeroTelefonicoPiloto.setText("Ingrese el número telefónico");
    txtNumeroTelefonicoPiloto.setForeground(Color.GRAY);


    // Restablece el JDateChooser al estado del placeholder
        setupDateChooserWithPlaceholder(txtFechaDeNacimientoPiloto, "dd/MM/yyyy"); // Configura el JDateChooser con placeholder
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
private void enviarCorreoActualizacion(String destinatario, Piloto piloto) throws IOException {
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
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject("Bienvenido a PINEED - Información de Registro");

        // Crear la estructura de mensaje multipart
        Multipart multipart = new MimeMultipart("related");

        // Primera parte - contenido HTML
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
    "<h2 style='color: #155724; text-align: center;'><strong>¡Bienvenido a PINEED!</strong></h2>" +
    "<p style='color: #155724;'>Sus datos han sido registrados exitosamente en nuestro sistema.</p>" +
    
    // Sección de Información del Registro
    "<div style='background-color: #d4edda; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #155724; margin-top: 0;'>Información del Registro:</h3>" +
    "<table style='width: 100%; border-collapse: collapse;'>" +
    "<tr><td style='padding: 8px 0;'><strong>Nombre:</strong></td><td>" + piloto.getNombrePiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Apellido:</strong></td><td>" + piloto.getApellidoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>DPI:</strong></td><td>" + piloto.getNumeroDeDpi() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Tipo de Licencia:</strong></td><td>" + piloto.getTipoLicencia() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Correo Electrónico:</strong></td><td>" + piloto.getCorreoElectronicoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Teléfono:</strong></td><td>" + piloto.getNumeroTelefonicoPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Género:</strong></td><td>" + piloto.getGeneroPiloto() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Fecha de Nacimiento:</strong></td><td>" + piloto.getFechaDeNacimiento() + "</td></tr>" +
    "<tr><td style='padding: 8px 0;'><strong>Estado:</strong></td><td>" + piloto.getEstadoPiloto() + "</td></tr>" +
    "</table></div>" +

    // Sección de Información de Acceso al Sistema con fondo verde claro
    "<div style='background-color: #e0f7fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
    "<h3 style='color: #155724; margin-top: 0;'>Sus Credenciales de Acceso:</h3>" +
    "<p><strong>Nombre de Usuario:</strong> " + nombreUsuario + "</p>" +
    "<p><strong>Contraseña:</strong> " + piloto.getNumeroDeDpi() + "</p>" +
    "</div>" +

    "<div style='text-align: center; margin-top: 20px;'>" +
    "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +
    "</div>" +
    "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
    "</div></body></html>";

        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Segunda parte - la imagen embebida
        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);
        
        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE); // Mostrar la imagen embebida
            multipart.addBodyPart(messageBodyPart);
        } else {
            System.out.println("Imagen no encontrada en el classpath.");
        }

        // Configurar el contenido final del correo
        message.setContent(multipart);

        // Enviar el correo
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
        btnAgregarPilotoSistema = new javax.swing.JButton();
        txtCorreoElectronicoPiloto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaPiloto = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePiloto = new javax.swing.JTextField();
        txtApellidoPiloto = new javax.swing.JTextField();
        txtNumeroDeDpiPiloto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoPiloto = new javax.swing.JComboBox<>();
        txtNumeroTelefonicoPiloto = new javax.swing.JTextField();
        txtGeneroPiloto = new javax.swing.JComboBox<>();
        txtFechaDeNacimientoPiloto = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnAgregarPilotoSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnAgregarPilotoSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnAgregarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPilotoSistema.setText("AGREGAR");
        btnAgregarPilotoSistema.setBorder(null);
        btnAgregarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPilotoSistemaActionPerformed(evt);
            }
        });

        txtCorreoElectronicoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoDeLicenciaPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        txtNombrePiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtApellidoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtNumeroDeDpiPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        txtNumeroTelefonicoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtNumeroTelefonicoPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelefonicoPilotoActionPerformed(evt);
            }
        });

        txtGeneroPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtGeneroPiloto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        txtFechaDeNacimientoPiloto.setDateFormatString("dd/MM/yyyy");
        txtFechaDeNacimientoPiloto.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnAgregarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCorreoElectronicoPiloto))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombrePiloto)
                                .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroTelefonicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombrePiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDeDpiPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCorreoElectronicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDeLicenciaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaDeNacimientoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGeneroPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNumeroTelefonicoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(93, 93, 93)
                .addComponent(btnAgregarPilotoSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPilotoSistemaActionPerformed
      try {
        // Primero validar que ningún campo tenga su placeholder o esté vacío
        if (txtNombrePiloto.getText().equals("Ingrese el nombre") || txtNombrePiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del piloto.");
            return;
        }
        if (txtApellidoPiloto.getText().equals("Ingrese el apellido") || txtApellidoPiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el apellido del piloto.");
            return;
        }
        if (txtNumeroDeDpiPiloto.getText().equals("Ingrese el número de DPI") || txtNumeroDeDpiPiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el número de DPI del piloto.");
            return;
        }
        
        if (!esSoloLetras(txtNombrePiloto.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.");
            return;
        }
        
        if (txtApellidoPiloto.getText().equals("Ingrese el apellido") || txtApellidoPiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el apellido del piloto.");
            return;
        }
        if (!esSoloLetras(txtApellidoPiloto.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El apellido solo debe contener letras.");
            return;
        }
        
        if (txtCorreoElectronicoPiloto.getText().equals("Ingrese el correo electrónico") || txtCorreoElectronicoPiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el correo electrónico del piloto.");
            return;
        }
        if (txtNumeroTelefonicoPiloto.getText().equals("Ingrese el número telefónico") || txtNumeroTelefonicoPiloto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el número telefónico del piloto.");
            return;
        }
        if (txtFechaDeNacimientoPiloto.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione la fecha de nacimiento del piloto.");
            return;
        }

        // Si pasa todas las validaciones, entonces proceder con la obtención de valores
        String nombrePiloto = txtNombrePiloto.getText().trim();
        String apellidoPiloto = txtApellidoPiloto.getText().trim();
        long numeroDeDpiPiloto;
        int numeroTelefonicoPiloto;
        
        try {
            numeroDeDpiPiloto = Long.parseLong(txtNumeroDeDpiPiloto.getText().trim());
            numeroTelefonicoPiloto = Integer.parseInt(txtNumeroTelefonicoPiloto.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DPI y el número telefónico deben contener solo números.");
            return;
        }
        
        String tipoLicencia = txtTipoDeLicenciaPiloto.getSelectedItem().toString().trim();
        String correoElectronicoPiloto = txtCorreoElectronicoPiloto.getText().trim();
        String generoPiloto = txtGeneroPiloto.getSelectedItem().toString().trim();
        String estadoPiloto = txtEstadoPiloto.getSelectedItem().toString().trim();

        // Validaciones de formato
        if (!correoElectronicoPiloto.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
            return;
        }

        if (String.valueOf(numeroDeDpiPiloto).length() != 13) {
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }

        if (String.valueOf(numeroTelefonicoPiloto).length() != 8) {
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }

        Date fechaNacimientoPilotoDate = txtFechaDeNacimientoPiloto.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDeNacimientoPiloto = sdf.format(fechaNacimientoPilotoDate);

        
                    // Validar longitud del nombre y apellido
        if (nombrePiloto.length() < 2 || nombrePiloto.length() > 50) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener entre 2 y 50 caracteres.");
            return;
        }
        if (nombrePiloto.length() < 2 || nombrePiloto.length() > 50) {
            JOptionPane.showMessageDialog(this, "El apellido debe tener entre 2 y 50 caracteres.");
            return;
        }

        
        
 // Verificar duplicados y mostrar mensajes específicos
for (Piloto pilotoExistente : listaPilotos) {
    if (pilotoExistente.getNumeroDeDpi() == numeroDeDpiPiloto) {
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
        return;
    }
    
    if (pilotoExistente.getNumeroTelefonicoPiloto() == numeroTelefonicoPiloto) {
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
        return;
    }
    
    if (pilotoExistente.getCorreoElectronicoPiloto().equals(correoElectronicoPiloto)) {
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
        return;
    }
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
            // Crear el nuevo piloto
            Piloto nuevoPiloto = new Piloto();
            nuevoPiloto.setNombrePiloto(nombrePiloto);
            nuevoPiloto.setApellidoPiloto(apellidoPiloto);
            nuevoPiloto.setNumeroDeDpi(numeroDeDpiPiloto);
            nuevoPiloto.setTipoLicencia(tipoLicencia);
            nuevoPiloto.setCorreoElectronicoPiloto(correoElectronicoPiloto);
            nuevoPiloto.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
            nuevoPiloto.setGeneroPiloto(generoPiloto);
            nuevoPiloto.setFechaDeNacimiento(fechaDeNacimientoPiloto);
            nuevoPiloto.setEstadoPiloto(estadoPiloto);
            nuevoPiloto.setActivo(true);
        // Mostrar diálogo de confirmación con los datos
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea agregar al piloto con los siguientes datos?\n\n" +
            "Nombre completo: " + nombrePiloto + " " + apellidoPiloto + "\n" +
            "DPI: " + numeroDeDpiPiloto + "\n" +
            "Tipo de licencia: " + tipoLicencia + "\n" +
            "Correo electrónico: " + correoElectronicoPiloto + "\n" +
            "Teléfono: " + numeroTelefonicoPiloto + "\n" +
            "Género: " + generoPiloto + "\n" +
            "Fecha de nacimiento: " + fechaDeNacimientoPiloto + "\n" +
            "Estado: " + estadoPiloto,
            "Confirmar agregación de piloto",
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
        progressBar.setString("Procesando...");
        
        // Etiqueta de mensaje
        JLabel mensajeLabel = new JLabel("Agregando piloto y enviando correo electrónico...");
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
                // Intentar agregar el nuevo piloto
                gestionPilotos.agregarPiloto(nuevoPiloto);
                
                // Actualizar UI y enviar correo
                SwingUtilities.invokeLater(() -> {
                    cargarPilotosEnTabla();
                    limpiarCampos();
                });
                
                // Enviar correo
                enviarCorreoActualizacion(correoElectronicoPiloto, nuevoPiloto);
                
                // Cerrar diálogo de progreso y mostrar éxito
                SwingUtilities.invokeLater(() -> {
                    dialogoProceso.dispose();
                    
                    JOptionPane.showMessageDialog(
                        this,
                        "¡Piloto agregado exitosamente!\n\n" +
                        "Se ha enviado un correo electrónico a:\n" + 
                        correoElectronicoPiloto + "\n" +
                        "con los datos de acceso al sistema.",
                        "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Abrir nueva ventana de gestión
                    INICIOGESTIONPILOTOS abrir = new INICIOGESTIONPILOTOS(currentUser, userRole, loginFrame);
                    abrir.setVisible(true);
                    this.setVisible(false);
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
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Error inesperado: " + e.getMessage() +
            "\nPor favor, contacte al administrador del sistema.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnAgregarPilotoSistemaActionPerformed

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

    private void txtNumeroTelefonicoPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelefonicoPilotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTelefonicoPilotoActionPerformed

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
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                       String username = "defaultUser";  // Replace with actual username or logic
            String role = "defaultRole";      // Replace with actual role
            LOGINPINEED loginFrame = new LOGINPINEED();  // Instantiate the LOGINPINEED object

            // Create the INICIOPINEED instance with the required parameters
            new AGREGARGESTIONPILOTOS(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPilotoSistema;
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
    private javax.swing.JTextField txtApellidoPiloto;
    private javax.swing.JTextField txtCorreoElectronicoPiloto;
    private javax.swing.JComboBox<String> txtEstadoPiloto;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPiloto;
    private javax.swing.JComboBox<String> txtGeneroPiloto;
    private javax.swing.JTextField txtNombrePiloto;
    private javax.swing.JTextField txtNumeroDeDpiPiloto;
    private javax.swing.JTextField txtNumeroTelefonicoPiloto;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPiloto;
    // End of variables declaration//GEN-END:variables
}
