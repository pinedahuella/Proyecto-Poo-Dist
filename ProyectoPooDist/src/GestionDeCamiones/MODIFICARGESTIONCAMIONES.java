package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import Login.LOGINPINEED;
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import GestionDeUsuarios.GESTIONUSUARIOS;
import GestionDeUsuarios.Usuarios;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
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


public class MODIFICARGESTIONCAMIONES extends javax.swing.JFrame {

    // Atributos de la clase
    public GESTIONCAMIONES gestionCamiones; // Objeto para gestionar camiones
    public Vector<Camiones> listaCamiones = new Vector<>(); // Lista de camiones
    DefaultTableModel modeloCamiones = new DefaultTableModel(); // Modelo de tabla para camiones
    private Camiones camionActual; // Camión actualmente seleccionado
    private INICIOGESTIONCAMIONES ventanaPrincipal; // Ventana principal de gestión de camiones
    private String currentUser; // Nombre del usuario actual
    private String userRole; // Rol del usuario actual
    private LOGINPINEED loginFrame; // Objeto de la clase LOGINPINEED
    private GESTIONUSUARIOS gestionUsuarios;

    /**
     * Constructor de la clase MODIFICARGESTIONCAMIONES.
     *
     * @param camion Camión a modificar
     * @param ventanaPrincipal Ventana principal de gestión de camiones
     * @param username Nombre del usuario actual
     * @param role Rol del usuario actual
     * @param loginFrame Objeto de la clase LOGINPINEED
     */

public MODIFICARGESTIONCAMIONES(Camiones camion, INICIOGESTIONCAMIONES ventanaPrincipal, String username, String role, LOGINPINEED loginFrame) {
    initComponents();
    setResizable(false);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    this.currentUser = username;
    this.userRole = role;
    this.loginFrame = loginFrame;

    if (ventanaPrincipal != null) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.gestionCamiones = ventanaPrincipal.gestionCamiones;
        this.listaCamiones = gestionCamiones.getCamiones();
    }
    
      gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();

    // Primero configuramos los placeholders y listeners
    configurarCamposDeTextoConPlaceholdersCamiones();

    if (camion != null) {
        this.camionActual = camion;
        cargarDatosCamion();
    }
}

private void cargarDatosCamion() {
    if (camionActual != null) {
        // Cargar datos y asegurar que estén en negro
        txtPlacasCamionesModificar.setText(camionActual.getPlacas());
        txtPlacasCamionesModificar.setForeground(Color.BLACK);

        txtModeloCamionesModificar.setText(camionActual.getModelo());
        txtModeloCamionesModificar.setForeground(Color.BLACK);

        txtMarcaCamionesModificar.setText(camionActual.getMarca());
        txtMarcaCamionesModificar.setForeground(Color.BLACK);

        txtEstadoCamionesModificar.setSelectedItem(camionActual.getEstado());
        txtTipoCombustibleCamionesModificar.setSelectedItem(camionActual.getTipoCombustible());

        txtKilometrajeCamionesModificar.setText(String.valueOf(camionActual.getKilometraje()));
        txtKilometrajeCamionesModificar.setForeground(Color.BLACK);

        txtCapacidadDeCargaCamionesModificar.setText(String.valueOf(camionActual.getCapacidadCarga()));
        txtCapacidadDeCargaCamionesModificar.setForeground(Color.BLACK);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaFabricacion = sdf.parse(camionActual.getAñoFabricacion());
            txtAñoDeFabricacionCamionesModificar.setDate(fechaFabricacion);
            ((JTextField) txtAñoDeFabricacionCamionesModificar.getDateEditor().getUiComponent()).setForeground(Color.BLACK);
        } catch (Exception e) {
            try {
                SimpleDateFormat sdfAño = new SimpleDateFormat("yyyy");
                Date fechaFabricacion = sdfAño.parse(camionActual.getAñoFabricacion());
                txtAñoDeFabricacionCamionesModificar.setDate(fechaFabricacion);
                ((JTextField) txtAñoDeFabricacionCamionesModificar.getDateEditor().getUiComponent()).setForeground(Color.BLACK);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar la fecha de fabricación: " + e.getMessage());
            }
        }
    }
}

private void setupTextFieldCamiones(JTextField textField, String placeholder) {
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
            if (textField.getText().trim().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });
}

private void configurarCamposDeTextoConPlaceholdersCamiones() {
    setupTextFieldCamiones(txtPlacasCamionesModificar, "Ingrese las placas");
    setupTextFieldCamiones(txtMarcaCamionesModificar, "Ingrese la marca");
    setupTextFieldCamiones(txtModeloCamionesModificar, "Ingrese el modelo");
    setupTextFieldCamiones(txtKilometrajeCamionesModificar, "Ingrese el kilometraje");
    setupTextFieldCamiones(txtCapacidadDeCargaCamionesModificar, "Ingrese la capacidad de carga");
    setupDateChooser(txtAñoDeFabricacionCamionesModificar, "dd/MM/yyyy");
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
            if (dateChooser.getDate() == null) {
                editor.setText(""); // Limpia el texto al enfocar
                editor.setForeground(Color.BLACK); // Cambia el color a negro
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (dateChooser.getDate() == null) {
                editor.setText(placeholder); // Restaura el placeholder si está vacío
                editor.setForeground(Color.GRAY); // Cambia el color a gris
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
        btnModificarCamionesSistema = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtTipoCombustibleCamionesModificar = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEstadoCamionesModificar = new javax.swing.JComboBox<>();
        txtAñoDeFabricacionCamionesModificar = new com.toedter.calendar.JDateChooser();
        txtCapacidadDeCargaCamionesModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtModeloCamionesModificar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtPlacasCamionesModificar = new javax.swing.JTextField();
        txtKilometrajeCamionesModificar = new javax.swing.JTextField();
        txtMarcaCamionesModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        btnModificarCamionesSistema.setBackground(new java.awt.Color(85, 111, 169));
        btnModificarCamionesSistema.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        btnModificarCamionesSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCamionesSistema.setText("MODIFICAR");
        btnModificarCamionesSistema.setBorder(null);
        btnModificarCamionesSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCamionesSistemaActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        txtTipoCombustibleCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtTipoCombustibleCamionesModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disel", "Gasolina", "Eléctrico", "Híbrido", "Hidrógeno", "Gas Licuado de Petróleo", " " }));

        jLabel3.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        jLabel13.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel13.setText("KILOMETRAJE");

        txtEstadoCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N
        txtEstadoCamionesModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FUNCIONAL", "DESCOMPUESTO", "EN MANTENIMIENTO" }));
        txtEstadoCamionesModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoCamionesModificarActionPerformed(evt);
            }
        });

        txtAñoDeFabricacionCamionesModificar.setDateFormatString("dd/MM/yyyy");
        txtAñoDeFabricacionCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtCapacidadDeCargaCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel12.setText("CAPACIDAD DE CARGA");

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel15.setText("AÑO DE FABRICACION");

        txtModeloCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel16.setText("PLACA");

        txtPlacasCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtKilometrajeCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        txtMarcaCamionesModificar.setFont(new java.awt.Font("Nirmala UI", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel17.setFont(new java.awt.Font("Nirmala UI", 1, 12)); // NOI18N
        jLabel17.setText("TIPO DE COMBUSTIBLE");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnModificarCamionesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlacasCamionesModificar))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarcaCamionesModificar)
                            .addComponent(txtModeloCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAñoDeFabricacionCamionesModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCapacidadDeCargaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoCombustibleCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtEstadoCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtKilometrajeCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarcaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtModeloCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPlacasCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAñoDeFabricacionCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCapacidadDeCargaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTipoCombustibleCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometrajeCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEstadoCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128)
                .addComponent(btnModificarCamionesSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
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

    
    
    
            
private void enviarCorreoModificacion(String destinatario, Camiones camionNuevo, Camiones camionAnterior) throws IOException {
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
        message.setSubject("Camión Modificado - PINEED");

        Multipart multipart = new MimeMultipart("related");

        // Primera parte - contenido HTML
        BodyPart messageBodyPart = new MimeBodyPart();
        String contenido = "<html><body style='font-family: Arial, sans-serif;'>" +
            "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
            "<h2 style='color: #2c3e50; text-align: center;'><strong>¡Camión Modificado en PINEED!</strong></h2>" +
            "<p style='color: #34495e;'>Se ha modificado un camión en el sistema.</p>" +
            
            // Datos Anteriores
            "<div style='background-color: #fff3e0; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #e65100; margin-top: 0;'>Datos Anteriores:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0;'><strong>Marca:</strong></td><td>" + camionAnterior.getMarca() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Modelo:</strong></td><td>" + camionAnterior.getModelo() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Placas:</strong></td><td>" + camionAnterior.getPlacas() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Estado:</strong></td><td>" + camionAnterior.getEstado() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Tipo de Combustible:</strong></td><td>" + camionAnterior.getTipoCombustible() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Capacidad de Carga:</strong></td><td>" + camionAnterior.getCapacidadCarga() + " kg</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Kilometraje:</strong></td><td>" + camionAnterior.getKilometraje() + " km</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Año de Fabricación:</strong></td><td>" + camionAnterior.getAñoFabricacion() + "</td></tr>" +
            "</table></div>" +
            
            // Nuevos Datos
            "<div style='background-color: #e8f5e9; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2e7d32; margin-top: 0;'>Nuevos Datos:</h3>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 8px 0;'><strong>Marca:</strong></td><td>" + camionNuevo.getMarca() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Modelo:</strong></td><td>" + camionNuevo.getModelo() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Placas:</strong></td><td>" + camionNuevo.getPlacas() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Estado:</strong></td><td>" + camionNuevo.getEstado() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Tipo de Combustible:</strong></td><td>" + camionNuevo.getTipoCombustible() + "</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Capacidad de Carga:</strong></td><td>" + camionNuevo.getCapacidadCarga() + " kg</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Kilometraje:</strong></td><td>" + camionNuevo.getKilometraje() + " km</td></tr>" +
            "<tr><td style='padding: 8px 0;'><strong>Año de Fabricación:</strong></td><td>" + camionNuevo.getAñoFabricacion() + "</td></tr>" +
            "</table></div>" +
            
            // Resumen de Cambios
            "<div style='background-color: #f5f5f5; padding: 15px; border-radius: 5px; margin: 20px 0;'>" +
            "<h3 style='color: #2c3e50; margin-top: 0;'>Campos Modificados:</h3>" +
            "<ul style='list-style-type: none; padding: 0;'>" +
            ((!camionAnterior.getMarca().equals(camionNuevo.getMarca())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Marca: " + camionAnterior.getMarca() + " → " + camionNuevo.getMarca() + "</li>" : "") +
            ((!camionAnterior.getModelo().equals(camionNuevo.getModelo())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Modelo: " + camionAnterior.getModelo() + " → " + camionNuevo.getModelo() + "</li>" : "") +
            ((!camionAnterior.getPlacas().equals(camionNuevo.getPlacas())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Placas: " + camionAnterior.getPlacas() + " → " + camionNuevo.getPlacas() + "</li>" : "") +
            ((!camionAnterior.getEstado().equals(camionNuevo.getEstado())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Estado: " + camionAnterior.getEstado() + " → " + camionNuevo.getEstado() + "</li>" : "") +
            ((!camionAnterior.getTipoCombustible().equals(camionNuevo.getTipoCombustible())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Tipo de Combustible: " + camionAnterior.getTipoCombustible() + " → " + camionNuevo.getTipoCombustible() + "</li>" : "") +
            ((camionAnterior.getCapacidadCarga() != camionNuevo.getCapacidadCarga()) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Capacidad de Carga: " + camionAnterior.getCapacidadCarga() + " kg → " + camionNuevo.getCapacidadCarga() + " kg</li>" : "") +
            ((camionAnterior.getKilometraje() != camionNuevo.getKilometraje()) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Kilometraje: " + camionAnterior.getKilometraje() + " km → " + camionNuevo.getKilometraje() + " km</li>" : "") +
            ((!camionAnterior.getAñoFabricacion().equals(camionNuevo.getAñoFabricacion())) ? 
                "<li style='color: #e91e63; padding: 4px 0;'>✓ Año de Fabricación: " + camionAnterior.getAñoFabricacion() + " → " + camionNuevo.getAñoFabricacion() + "</li>" : "") +
            "</ul></div>" +
            
            "<div style='margin-top: 20px; text-align: center;'>" +
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

    } catch (MessagingException e) {
        throw new IOException("Error al enviar el correo: " + e.getMessage());
    }
}

    private void btnModificarCamionesSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCamionesSistemaActionPerformed
    try {
        // Obtener datos de los campos de entrada
        String placas = txtPlacasCamionesModificar.getText().trim();
        String modelo = txtModeloCamionesModificar.getText().trim();
        String marca = txtMarcaCamionesModificar.getText().trim();
        String estado = txtEstadoCamionesModificar.getSelectedItem().toString().trim();
        String tipoCombustible = txtTipoCombustibleCamionesModificar.getSelectedItem().toString().trim();
        double kilometraje = Double.parseDouble(txtKilometrajeCamionesModificar.getText().trim());
        double capacidadCarga = Double.parseDouble(txtCapacidadDeCargaCamionesModificar.getText().trim());

        Date añoFabricacionDate = txtAñoDeFabricacionCamionesModificar.getDate();
        if (añoFabricacionDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de fabricación válida.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String añoFabricacion = sdf.format(añoFabricacionDate);

        // Validar campos de entrada
        if (placas.isEmpty() || modelo.isEmpty() || marca.isEmpty() || estado.isEmpty() || tipoCombustible.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
            return;
        }

        if (!validarPlacas(placas)) {
            JOptionPane.showMessageDialog(this, "Las placas deben contener al menos una letra y un número.");
            return;
        }

        if (!validarKilometraje(kilometraje)) {
            JOptionPane.showMessageDialog(this, "El kilometraje debe ser un valor positivo y realista (0 - 1,000,000 km).");
            return;
        }

        if (!validarCapacidadCarga(capacidadCarga)) {
            JOptionPane.showMessageDialog(this, "La capacidad de carga debe ser un valor positivo y realista (100 - 30,000 kg).");
            return;
        }

        // Verificar si las placas han cambiado
        boolean placasCambiadas = !placas.equals(camionActual.getPlacas());

        // Validar que no exista otro camión con las mismas placas
        for (Camiones camionExistente : listaCamiones) {
            if (camionExistente != camionActual && placasCambiadas && camionExistente.getPlacas().equals(placas)) {
                JOptionPane.showMessageDialog(this, "Ya existe un camión con esas placas.");
                return;
            }
        }

        // Preguntar confirmación antes de modificar
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas modificar el camión con placas: " + placas + "?",
            "Confirmar modificación",
            JOptionPane.YES_NO_OPTION);
            
        
        
        // Antes de modificar el camión, crear una copia de los datos anteriores
Camiones camionAnterior = new Camiones();
camionAnterior.setPlacas(camionActual.getPlacas());
camionAnterior.setModelo(camionActual.getModelo());
camionAnterior.setMarca(camionActual.getMarca());
camionAnterior.setEstado(camionActual.getEstado());
camionAnterior.setTipoCombustible(camionActual.getTipoCombustible());
camionAnterior.setKilometraje(camionActual.getKilometraje());
camionAnterior.setCapacidadCarga(camionActual.getCapacidadCarga());
camionAnterior.setAñoFabricacion(camionActual.getAñoFabricacion());


        if (confirm == JOptionPane.YES_OPTION) {
            // Actualizar datos del camión
            camionActual.setPlacas(placas);
            camionActual.setModelo(modelo);
            camionActual.setMarca(marca);
            camionActual.setEstado(estado);
            camionActual.setTipoCombustible(tipoCombustible);
            camionActual.setKilometraje(kilometraje);
            camionActual.setCapacidadCarga(capacidadCarga);
            camionActual.setAñoFabricacion(añoFabricacion);

            // Actualizar camión en la gestión de camiones
            gestionCamiones.actualizarCamion(camionActual);

            // Crear el diálogo de progreso
            JDialog dialogoProceso = new JDialog(this, "Procesando", true);
            dialogoProceso.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            
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

            // Crear un hilo separado para realizar el envío de correos
            Thread processingThread = new Thread(() -> {
                boolean correosEnviados = false;
                try {
                    // Obtener la lista de usuarios
                    Vector<Usuarios> usuarios = gestionUsuarios.getUsuarios();

                    for (Usuarios usuario : usuarios) {
                        if (("ADMINISTRADOR".equalsIgnoreCase(usuario.getCargo()) || 
                             "SECRETARIA".equalsIgnoreCase(usuario.getCargo())) &&
                            usuario.getCorreoElectronico() != null &&
                            !usuario.getCorreoElectronico().isEmpty()) {
                            
                            // Enviar correo a cada administrador o secretaria
enviarCorreoModificacion(usuario.getCorreoElectronico(), camionActual, camionAnterior);
                            correosEnviados = true;
                        }
                    }
                } catch (Exception e) {
                    correosEnviados = false;
                } finally {
                    final boolean exito = correosEnviados;
                    SwingUtilities.invokeLater(() -> {
                        dialogoProceso.dispose(); // Cerrar el diálogo de progreso
                        
                        if (exito) {
                            JOptionPane.showMessageDialog(
                                this,
                                "Camión modificado exitosamente y se han enviado las notificaciones al personal administrativo.",
                                "Operación exitosa",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                this,
                                "Camión modificado exitosamente pero no se pudieron enviar las notificaciones.",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE
                            );
                        }

                        // Actualizar la tabla en la ventana principal
                        ventanaPrincipal.actualizarTabla();
                        ventanaPrincipal.setVisible(true); // Hacer visible la ventana principal
                        this.dispose(); // Cerrar esta ventana
                    });
                }
            });

            // Iniciar el proceso en un hilo separado
            processingThread.start();
            
            // Mostrar el diálogo mientras se realiza el proceso
            dialogoProceso.setVisible(true);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al modificar camión: " + e.getMessage());
    }
    }//GEN-LAST:event_btnModificarCamionesSistemaActionPerformed

    private void txtEstadoCamionesModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoCamionesModificarActionPerformed

    }//GEN-LAST:event_txtEstadoCamionesModificarActionPerformed

    
    /**
     * Método para validar el formato de las placas.
     *
     * @param placas Placas del camión
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean validarPlacas(String placas) {
        String regex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$"; // Expresión regular para validar placas
        return placas.matches(regex);
    }

    /**
     * Método para validar el kilometraje.
     *
     * @param kilometraje Kilometraje del camión
     * @return true si el kilometraje es válido, false en caso contrario
     */
    private boolean validarKilometraje(double kilometraje) {
        return kilometraje >= 0 && kilometraje <= 1000000; // Validar rango de kilometraje
    }

    /**
     * Método para validar la capacidad de carga.
     *
     * @param capacidadCarga Capacidad de carga del camión
     * @return true si la capacidad de carga es válida, false en caso contrario
     */
    private boolean validarCapacidadCarga(double capacidadCarga) {
        return capacidadCarga >= 100 && capacidadCarga <= 30000; // Validar rango de capacidad de carga
    }
    
    
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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Crear y mostrar el formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String username = "defaultUser";  // Reemplazar con el nombre de usuario actual o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instanciar el objeto LOGINPINEED

                // Crear la instancia de MODIFICARGESTIONCAMIONES con los parámetros requeridos
                new MODIFICARGESTIONCAMIONES(null, null, username, role, loginFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificarCamionesSistema;
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
    private com.toedter.calendar.JDateChooser txtAñoDeFabricacionCamionesModificar;
    private javax.swing.JTextField txtCapacidadDeCargaCamionesModificar;
    private javax.swing.JComboBox<String> txtEstadoCamionesModificar;
    private javax.swing.JTextField txtKilometrajeCamionesModificar;
    private javax.swing.JTextField txtMarcaCamionesModificar;
    private javax.swing.JTextField txtModeloCamionesModificar;
    private javax.swing.JTextField txtPlacasCamionesModificar;
    private javax.swing.JComboBox<String> txtTipoCombustibleCamionesModificar;
    // End of variables declaration//GEN-END:variables
}
