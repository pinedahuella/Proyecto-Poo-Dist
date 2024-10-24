package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import Login.LOGINPINEED; // Importa la clase LOGINPINEED para manejar la lógica de inicio de sesión
import com.toedter.calendar.JDateChooser; // Importa la clase JDateChooser para seleccionar fechas de manera visual
import java.text.SimpleDateFormat; // Importa la clase SimpleDateFormat para formatear fechas
import java.util.Date; // Importa la clase Date para manejar fechas y horas
import java.util.Vector; // Importa la clase Vector para almacenar datos en una lista dinámica
import javax.swing.JOptionPane; // Importa la clase JOptionPane para mostrar diálogos de mensaje y entrada
import javax.swing.table.DefaultTableModel; // Importa la clase DefaultTableModel para gestionar modelos de tabla en Swing
import java.util.regex.Pattern; // Importa la clase Pattern para trabajar con expresiones regulares
import javax.swing.JTable; // Importa la clase JTable para crear tablas en la interfaz gráfica

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

        cargarCamionesEnTabla(); // Carga los camiones en la tabla.
        this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Cierra la ventana al salir.
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

    /**
     * Limpia los campos de entrada en el formulario.
     */
    private void limpiarCampos() {
        txtPlacasCamiones.setText("");
        txtEstadoCamiones.setSelectedIndex(0);
        txtTipoCombustibleCamiones.setSelectedIndex(0);
        txtKilometrajeCamiones.setText("");
        txtCapacidadDeCargaCamiones.setText("");
        txtAñoDeFabricacionCamiones.setDate(null);
        txtModeloCamiones.setText("");
        txtMarcaCamiones.setText("");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(696, Short.MAX_VALUE)
                .addComponent(btnAgregarCamionesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlacasCamiones))
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
                            .addComponent(txtKilometrajeCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarcaCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtModeloCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPlacasCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    String placas = txtPlacasCamiones.getText().trim();
                    String modelo = txtModeloCamiones.getText().trim();
                    String marca = txtMarcaCamiones.getText().trim();
                    String estado = txtEstadoCamiones.getSelectedItem().toString().trim();
                    String tipoCombustible = txtTipoCombustibleCamiones.getSelectedItem().toString().trim();
                    double kilometraje = Double.parseDouble(txtKilometrajeCamiones.getText().trim());
                    double capacidadCarga = Double.parseDouble(txtCapacidadDeCargaCamiones.getText().trim());

                    Date añoFabricacionDate = txtAñoDeFabricacionCamiones.getDate();
                    if (añoFabricacionDate == null) {
                        JOptionPane.showMessageDialog(this, "Por favor, selecciona un año de fabricación válido.");
                        return;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    String añoFabricacion = sdf.format(añoFabricacionDate);

                    // Validación de campos
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

                    // Verifica si hay un camión con las mismas placas
                    boolean placasCambiadas = !placas.equals(camionActual.getPlacas());
                    for (Camiones camionExistente : listaCamiones) {
                        if (camionExistente != camionActual && placasCambiadas && camionExistente.getPlacas().equals(placas)) {
                            JOptionPane.showMessageDialog(this, "Ya existe un camión con esas placas.");
                            return;
                        }
                    }

                    // Actualización de datos del camión
                    camionActual.setPlacas(placas);
                    camionActual.setModelo(modelo);
                    camionActual.setMarca(marca);
                    camionActual.setEstado(estado);
                    camionActual.setTipoCombustible(tipoCombustible);
                    camionActual.setKilometraje(kilometraje);
                    camionActual.setCapacidadCarga(capacidadCarga);
                    camionActual.setAñoFabricacion(añoFabricacion);

                    gestionCamiones.actualizarCamion(camionActual); // Actualiza el camión en la gestión.

                    JOptionPane.showMessageDialog(this, "Camión modificado exitosamente.");
                    cargarCamionesEnTabla(); // Refresca la tabla con los nuevos datos

                    this.dispose(); // Cierra la ventana actual

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al modificar camión: " + e.getMessage());
                }

                // Abre la ventana de gestión de camiones después de modificar
                String username = this.currentUser;
                String role = this.userRole;
                LOGINPINEED loginFrame = this.loginFrame;

                INICIOGESTIONCAMIONES abrir = new INICIOGESTIONCAMIONES(username, role, loginFrame);
                abrir.setVisible(true);
                this.setVisible(false);
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
