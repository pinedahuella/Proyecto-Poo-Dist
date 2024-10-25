package GestionDePilotos;

import Login.LOGINPINEED;
import GestionDePilotos.INICIOGESTIONPILOTOS;
import GestionDePilotos.GESTIONPILOTOS;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
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

        // Crear versiones sin tildes para validaciones si es necesario
        String nombrePilotoSinTildes = removeTildes(nombrePiloto);
        String apellidoPilotoSinTildes = removeTildes(apellidoPiloto);

        Date fechaNacimientoPilotoDate = txtFechaDeNacimientoPilotoModificarModificar.getDate();
        if (fechaNacimientoPilotoDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fecha de nacimiento válida.");
            return;
        }

        if (!correoElectronicoPiloto.endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDeNacimientoPiloto = sdf.format(fechaNacimientoPilotoDate);

        if (nombrePiloto.isEmpty() || apellidoPiloto.isEmpty() || tipoLicencia.isEmpty() ||
            correoElectronicoPiloto.isEmpty() || generoPiloto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
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

        boolean dpiCambiado = numeroDeDpiPiloto != pilotoActual.getNumeroDeDpi();
        boolean telefonoCambiado = numeroTelefonicoPiloto != pilotoActual.getNumeroTelefonicoPiloto();
        boolean correoCambiado = !correoElectronicoPiloto.equals(pilotoActual.getCorreoElectronicoPiloto());

        for (Piloto pilotoExistente : listaPilotos) {
            if (pilotoExistente != pilotoActual) {
                if (dpiCambiado && pilotoExistente.getNumeroDeDpi() == numeroDeDpiPiloto) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
                    return;
                }
                if (telefonoCambiado && pilotoExistente.getNumeroTelefonicoPiloto() == numeroTelefonicoPiloto) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
                    return;
                }
                if (correoCambiado && pilotoExistente.getCorreoElectronicoPiloto().equals(correoElectronicoPiloto)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
                    return;
                }
            }
        }

        // Actualizar el piloto usando los valores originales (con tildes)
        pilotoActual.setNombrePiloto(nombrePiloto);  // Usar versión con tildes
        pilotoActual.setApellidoPiloto(apellidoPiloto);  // Usar versión con tildes
        pilotoActual.setNumeroDeDpi(numeroDeDpiPiloto);
        pilotoActual.setTipoLicencia(tipoLicencia);
        pilotoActual.setCorreoElectronicoPiloto(correoElectronicoPiloto);
        pilotoActual.setNumeroTelefonicoPiloto(numeroTelefonicoPiloto);
        pilotoActual.setGeneroPiloto(generoPiloto);
        pilotoActual.setFechaDeNacimiento(fechaDeNacimientoPiloto);
        pilotoActual.setEstadoPiloto(estadoPiloto);

        gestionPilotos.actualizarPiloto(pilotoActual);

        JOptionPane.showMessageDialog(this, "Piloto modificado exitosamente.");

        ventanaPrincipal.actualizarTabla();
        ventanaPrincipal.setVisible(true);
        this.dispose();

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
