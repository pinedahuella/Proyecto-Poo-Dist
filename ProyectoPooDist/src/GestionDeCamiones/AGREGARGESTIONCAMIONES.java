package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import GestionDePilotos.Piloto;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import Login.LOGINPINEED; // Importa la clase LOGINPINEED para manejar la lógica de inicio de sesión
import com.toedter.calendar.JDateChooser; // Importa la clase JDateChooser para seleccionar fechas de manera visual
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat; // Importa la clase SimpleDateFormat para formatear fechas
import java.util.ArrayList;
import java.util.Date; // Importa la clase Date para manejar fechas y horas
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Vector; // Importa la clase Vector para almacenar datos en una lista dinámica
import javax.swing.JOptionPane; // Importa la clase JOptionPane para mostrar diálogos de mensaje y entrada
import javax.swing.table.DefaultTableModel; // Importa la clase DefaultTableModel para gestionar modelos de tabla en Swing
import java.util.regex.Pattern; // Importa la clase Pattern para trabajar con expresiones regulares
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTable; // Importa la clase JTable para crear tablas en la interfaz gráfica
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Clase AGREGARGESTIONCAMIONES.
 * Esta clase representa una ventana para gestionar la información de los camiones,
 * permitiendo agregar y actualizar datos de camiones en un sistema.
 */
public class AGREGARGESTIONCAMIONES extends javax.swing.JFrame {
    private GESTIONCAMIONES gestionCamiones; // Maneja la lógica de gestión de camiones.
    private Vector<Camiones> listaCamiones = new Vector<>(); // Lista de camiones.
    private DefaultTableModel modeloCamiones = new DefaultTableModel(); // Modelo para la tabla de camiones.
    private Camiones camionActual; // Camión actualmente seleccionado.
    private int indiceActual; // Índice del camión actual en la lista.
    private String currentUser; // Nombre del usuario actual.
    private String userRole; // Rol del usuario actual.
    private LOGINPINEED loginFrame; // Referencia al frame de inicio de sesión.
private List<Usuarios> usuarios = new ArrayList<>();
private List<Piloto> pilotos = new ArrayList<>();
    private GESTIONUSUARIOS gestionUsuarios;


    /**
     * Constructor de la clase AGREGARGESTIONCAMIONES.
     * Inicializa los componentes de la ventana y carga los camiones desde un archivo Excel.
     *
     * @param username Nombre de usuario del usuario actual.
     * @param role Rol del usuario actual.
     * @param loginFrame Referencia al frame de inicio de sesión.
     */
    public AGREGARGESTIONCAMIONES(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
        indiceActual = 0;
        setResizable(false); // Desactivar el cambio de tamaño
        // Inicializa la gestión de camiones
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel();

        if (gestionCamiones.getCamiones() != null) {
            listaCamiones = gestionCamiones.getCamiones();
        }
    configurarCamposDeTextoConPlaceholdersCamiones(); // Configura los placeholders en los campos de texto

        cargarCamionesEnTabla(); // Carga los camiones en la tabla.
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Cierra la ventana al salir.
           gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();
    }

    /**
     * Carga los camiones en la tabla.
     * Reinicia la tabla y la llena con los datos de los camiones.
     */
    private void cargarCamionesEnTabla() {
        modeloCamiones.setRowCount(0); // Reinicia la tabla

        // Llena la tabla con los datos de los camiones
        for (Camiones camiones : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camiones.getPlacas(),
                camiones.getModelo(),
                camiones.getMarca(),
                camiones.getEstado(),
                camiones.getTipoCombustible(),
                camiones.getKilometraje(),
                camiones.getCapacidadCarga(),
                camiones.getAñoFabricacion(),
                camiones.getCostoReparacion(),
                camiones.getCostoGalon(),
                camiones.getGalones(),
                camiones.getCostoMantenimiento(),
                camiones.getGastoNoEspecificado(),
                camiones.getDescripcionDelGasto(),
                camiones.getTiempoEnReparacion(),
                camiones.getFechaDeMantenimiento(),
                camiones.getTotal()
            });
        }
    }

// Método para configurar el placeholder en campos de texto
private void setupTextFieldCamiones(JTextField textField, String placeholder) {
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

// Método para configurar el placeholder en JDateChooser
private void setupDateChooser(JDateChooser dateChooser, String placeholder) {
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


// Método para configurar todos los campos de camiones con placeholders
private void configurarCamposDeTextoConPlaceholdersCamiones() {
    setupTextFieldCamiones(txtPlacasCamiones, "Ingrese las placas");
    setupTextFieldCamiones(txtMarcaCamiones, "Ingrese la marca");
    setupTextFieldCamiones(txtModeloCamiones, "Ingrese el modelo");
    setupTextFieldCamiones(txtKilometrajeCamiones, "Ingrese el kilometraje");
    setupTextFieldCamiones(txtCapacidadDeCargaCamiones, "Ingrese la capacidad de carga");
    setupDateChooser(txtAñoDeFabricacionCamiones, "dd/MM/yyyy"); // Configura el JDateChooser con placeholder
}

// Método para limpiar y restablecer los placeholders de los campos de camiones
public void limpiarCamposCamiones() {
    txtPlacasCamiones.setText("Ingrese las placas");
    txtPlacasCamiones.setForeground(Color.GRAY);

    txtMarcaCamiones.setText("Ingrese la marca");
    txtMarcaCamiones.setForeground(Color.GRAY);

    txtModeloCamiones.setText("Ingrese el modelo");
    txtModeloCamiones.setForeground(Color.GRAY);

    txtKilometrajeCamiones.setText("Ingrese el kilometraje");
    txtKilometrajeCamiones.setForeground(Color.GRAY);

    txtCapacidadDeCargaCamiones.setText("Ingrese la capacidad de carga");
    txtCapacidadDeCargaCamiones.setForeground(Color.GRAY);

    // Restablece el JDateChooser al estado del placeholder
    
        ((JTextField) txtAñoDeFabricacionCamiones.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
}


    /**
     * Valida que las placas contengan al menos una letra y un número.
     *
     * @param placas Las placas a validar.
     * @return true si las placas son válidas, false en caso contrario.
     */
    private boolean validarPlacas(String placas) {
        // Valida que las placas contengan al menos una letra y un número
        String regex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$";
        return Pattern.matches(regex, placas);
    }

    /**
     * Valida el kilometraje del camión.
     *
     * @param kilometraje Kilometraje a validar.
     * @return true si el kilometraje es válido, false en caso contrario.
     */
    private boolean validarKilometraje(double kilometraje) {
        return (kilometraje >= 0 && kilometraje <= 1000000);
    }

    /**
     * Valida la capacidad de carga del camión.
     *
     * @param capacidadCarga Capacidad a validar.
     * @return true si la capacidad de carga es válida, false en caso contrario.
     */
    private boolean validarCapacidadCarga(double capacidadCarga) {
        return (capacidadCarga >= 100 && capacidadCarga <= 30000);
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
    


    private void cerrarSesionYSalir() {
        if (loginFrame != null) {
            loginFrame.cerrarSesion(currentUser, userRole);
        }
        // Crear una nueva instancia de LOGINPINEED sin pasar argumentos nulos
        LOGINPINEED nuevaLoginFrame = new LOGINPINEED();
        nuevaLoginFrame.setVisible(true);
        this.dispose();
    }
    
    

private void enviarCorreoActualizacion(String destinatario, Camiones camion) throws IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    final String username = "distribuidorapine@gmail.com";
    final String password = "aura hcol bzmt plzf";

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject("Nuevo Camión Agregado - PINEED");

        Multipart multipart = new MimeMultipart("related");

        // Primera parte - contenido HTML
        BodyPart messageBodyPart = new MimeBodyPart();
        String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
            "<h2 style='color: #2c3e50; text-align: center;'><strong>¡Nuevo Camión Agregado en PINEED!</strong></h2>" +
            "<p style='color: #34495e;'>Se ha agregado un nuevo camión al sistema.</p>" +
            "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2c3e50; margin-top: 0;'>Detalles del Camión:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0;'><strong>Marca:</strong></td><td>" + camion.getMarca() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Modelo:</strong></td><td>" + camion.getModelo() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Placas:</strong></td><td>" + camion.getPlacas() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Estado:</strong></td><td>" + camion.getEstado() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Tipo de Combustible:</strong></td><td>" + camion.getTipoCombustible() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Capacidad de Carga:</strong></td><td>" + camion.getCapacidadCarga() + " kg</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Año de Fabricación:</strong></td><td>" + camion.getAñoFabricacion() + "</td></tr>" +
            "</table></div>" +
            "<div style='margin-top: 20px; text-align: center;'>" +
            "<img src='cid:imagen' style='max-width: 100%; height: auto;'/>" +  // Referencia a la imagen
            "</div>" +
            "<p style='color: #7f8c8d; font-size: 0.9em; text-align: center;'>Este es un mensaje automático, por favor no responder.</p>" +
            "</div></body></html>";

        messageBodyPart.setContent(contenido, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        // Segunda parte - la imagen
        messageBodyPart = new MimeBodyPart();
        String rutaImagen = "/Fotos/ImagenTarjetaDePresentacionPine.png";  // Ruta de la imagen
        InputStream imageStream = getClass().getResourceAsStream(rutaImagen);

        if (imageStream != null) {
            DataSource source = new ByteArrayDataSource(imageStream, "image/png");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<imagen>");
            messageBodyPart.setDisposition(MimeBodyPart.INLINE);  // Importante para mostrar la imagen en línea
            multipart.addBodyPart(messageBodyPart);
        } else {
            System.out.println("Imagen no encontrada en el classpath.");
        }

        message.setContent(multipart);
        Transport.send(message);

    } catch (MessagingException e) {
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnAgregarCamionesSistema = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtMarcaCamiones = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtModeloCamiones = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtPlacasCamiones = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtAñoDeFabricacionCamiones = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtCapacidadDeCargaCamiones = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTipoCombustibleCamiones = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtKilometrajeCamiones = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoCamiones = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnAgregarCamionesSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnAgregarCamionesSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnAgregarCamionesSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCamionesSistema.setText("AGREGAR");
        btnAgregarCamionesSistema.setBorder(null);
        btnAgregarCamionesSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCamionesSistemaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        txtMarcaCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        txtModeloCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("PLACA");

        txtPlacasCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("AÑO DE FABRICACION");

        txtAñoDeFabricacionCamiones.setDateFormatString("dd/MM/yyyy");
        txtAñoDeFabricacionCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("CAPACIDAD DE CARGA");

        txtCapacidadDeCargaCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("TIPO DE COMBUSTIBLE");

        txtTipoCombustibleCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoCombustibleCamiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disel", "Gasolina", "Eléctrico", "Híbrido", "Hidrógeno", "Gas Licuado de Petróleo", " " }));

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("KILOMETRAJE");

        txtKilometrajeCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        txtEstadoCamiones.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoCamiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FUNCIONAL", "DESCOMPUESTO", "EN MANTENIMIENTO" }));
        txtEstadoCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoCamionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarcaCamiones)
                            .addComponent(txtModeloCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAñoDeFabricacionCamiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCapacidadDeCargaCamiones)
                            .addComponent(txtTipoCombustibleCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtEstadoCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtKilometrajeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlacasCamiones)))
                .addContainerGap(474, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarCamionesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPlacasCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarcaCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtModeloCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAñoDeFabricacionCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCapacidadDeCargaCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTipoCombustibleCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometrajeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEstadoCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(127, 127, 127)
                .addComponent(btnAgregarCamionesSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Acción a realizar al hacer clic en el botón para agregar o actualizar camiones.
     *
     * @param evt Evento del botón.
     */
    private void btnAgregarCamionesSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCamionesSistemaActionPerformed
    try {
        // Obtención de datos desde el formulario
        String marca = txtMarcaCamiones.getText().trim();
        String placas = txtPlacasCamiones.getText().trim();
        String modelo = txtModeloCamiones.getText().trim();
        String estado = txtEstadoCamiones.getSelectedItem().toString().trim();
        String tipoCombustible = txtTipoCombustibleCamiones.getSelectedItem().toString().trim();

        // Validación de campos (mantener las validaciones existentes...)
        if (marca.isEmpty() || modelo.isEmpty() || placas.isEmpty() || estado.isEmpty() || tipoCombustible.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
            return;
        }

        // Validar la marca primero
        if (marca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La marca no puede estar vacía.");
            return;
        }

        // Validar placas
        if (!validarPlacas(placas)) {
            JOptionPane.showMessageDialog(this, "Las placas deben contener al menos una letra y un número.");
            return;
        }

        // Validar kilometraje
        String kilometrajeStr = txtKilometrajeCamiones.getText().trim();
        if (kilometrajeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el kilometraje del camión.");
            return;
        }
        double kilometraje;
        try {
            kilometraje = Double.parseDouble(kilometrajeStr);
            if (!validarKilometraje(kilometraje)) {
                JOptionPane.showMessageDialog(this, "El kilometraje debe ser un valor positivo y realista (0 - 1,000,000 km).");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número para el kilometraje: " + e.getMessage());
            return;
        }

        // Validar capacidad de carga
        String capacidadCargaStr = txtCapacidadDeCargaCamiones.getText().trim();
        if (capacidadCargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese la capacidad de carga del camión.");
            return;
        }
        double capacidadCarga;
        try {
            capacidadCarga = Double.parseDouble(capacidadCargaStr);
            if (!validarCapacidadCarga(capacidadCarga)) {
                JOptionPane.showMessageDialog(this, "La capacidad de carga debe ser un valor positivo y realista (100 - 30,000 kg).");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de número para la capacidad de carga: " + e.getMessage());
            return;
        }

        // Validar año de fabricación
        Date añoFabricacionDate = txtAñoDeFabricacionCamiones.getDate();
        if (añoFabricacionDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un año de fabricación válido.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String añoFabricacion = sdf.format(añoFabricacionDate);

        // Verifica si hay un camión con las mismas placas en la lista existente
        for (Camiones camionExistente : listaCamiones) {
            if (camionExistente.getPlacas().equals(placas)) {
                JOptionPane.showMessageDialog(this, "Ya existe un camión con esas placas.");
                return;
            }
        }

        // Crear nuevo camión
            Camiones nuevoCamion = new Camiones(
                placas,
                estado,
                tipoCombustible,
                kilometraje,
                capacidadCarga,
                añoFabricacion,
                modelo,
                marca,
                true,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                "",
                "",
                "",
                0.0,
                0.0
            );

            // Agregar el nuevo camión
            gestionCamiones.agregarCamion(nuevoCamion);

            // Crear y mostrar el diálogo de progreso
            JDialog dialogoProceso = new JDialog(this, "Procesando", true);
            dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            
            // Configurar el panel de progreso
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            progressBar.setStringPainted(true);
            progressBar.setString("Enviando notificaciones...");
            
            JLabel mensajeLabel = new JLabel("Enviando correos al personal administrativo...");
            mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
            
            panel.add(mensajeLabel, BorderLayout.NORTH);
            panel.add(progressBar, BorderLayout.CENTER);
            
            dialogoProceso.add(panel);
            dialogoProceso.setSize(400, 150);
            dialogoProceso.setLocationRelativeTo(this);

            // Crear y ejecutar el hilo para enviar correos
            Thread processingThread = new Thread(() -> {
                try {
      Vector<Usuarios> usuarios = gestionUsuarios.getUsuarios();
                boolean correosEnviados = false;
                
                // Enviar correos tanto a administradores como a secretarias
                for (Usuarios usuario : usuarios) {
                    // Verificar si el usuario es ADMINISTRADOR o SECRETARIA
                    if (("ADMINISTRADOR".equalsIgnoreCase(usuario.getCargo()) || 
                         "SECRETARIA".equalsIgnoreCase(usuario.getCargo())) && 
                        usuario.getCorreoElectronico() != null && 
                        !usuario.getCorreoElectronico().isEmpty()) {
                        
                        enviarCorreoActualizacion(usuario.getCorreoElectronico(), nuevoCamion);
                        correosEnviados = true;
                    }
                }   

                    final boolean exito = correosEnviados;
                    SwingUtilities.invokeLater(() -> {
                        dialogoProceso.dispose();
                        if (exito) {
                            JOptionPane.showMessageDialog(
                                this,
                                "Camión agregado exitosamente y se han enviado las notificaciones al personal administrativo.",
                                "Operación exitosa",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            
                            
                        } else {
                            JOptionPane.showMessageDialog(
                                this,
                                "Camión agregado exitosamente pero no se el personal administrativo para notificar.",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE
                            );
                        }
 cargarCamionesEnTabla();
            
            // Abrir nueva ventana de gestión
            INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(currentUser, userRole, loginFrame);
            abrir.setVisible(true);
            this.setVisible(false); // Ocultar la ventana actual
        });

    } catch (Exception e) {
        SwingUtilities.invokeLater(() -> {
            dialogoProceso.dispose(); // Cerrar diálogo en caso de error
            JOptionPane.showMessageDialog(
                this,
                "Error al enviar las notificaciones: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
                    });
                }
            });

            processingThread.start();
            dialogoProceso.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error inesperado: " + e.getMessage() + "\nPor favor, contacte al administrador del sistema.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnAgregarCamionesSistemaActionPerformed

    private void txtEstadoCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoCamionesActionPerformed

    }//GEN-LAST:event_txtEstadoCamionesActionPerformed

    /**
     * Método principal para ejecutar la aplicación.
     *
     * @param args Argumentos de línea de comando.
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
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Crea y muestra la ventana */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Inicializa el nombre de usuario y rol por defecto.
                // Estos valores deben ser reemplazados con la lógica real de autenticación.
                String username = "defaultUser";  // Reemplazar con el nombre de usuario real o lógica de obtención de usuario
                String role = "defaultRole"; // Reemplazar con el rol real del usuario

                // Crea una instancia del frame de inicio de sesión.
                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea y muestra la ventana AGREGARGESTIONCAMIONES con los parámetros necesarios.
                // Se pasa el nombre de usuario, rol y el frame de inicio de sesión a la nueva ventana.
                new AGREGARGESTIONCAMIONES(username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCamionesSistema;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private com.toedter.calendar.JDateChooser txtAñoDeFabricacionCamiones;
    private javax.swing.JTextField txtCapacidadDeCargaCamiones;
    private javax.swing.JComboBox<String> txtEstadoCamiones;
    private javax.swing.JTextField txtKilometrajeCamiones;
    private javax.swing.JTextField txtMarcaCamiones;
    private javax.swing.JTextField txtModeloCamiones;
    private javax.swing.JTextField txtPlacasCamiones;
    private javax.swing.JComboBox<String> txtTipoCombustibleCamiones;
    // End of variables declaration//GEN-END:variables
}
