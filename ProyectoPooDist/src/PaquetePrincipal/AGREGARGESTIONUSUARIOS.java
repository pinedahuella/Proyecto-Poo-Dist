package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;


import javax.swing.JFrame; 
import javax.swing.JOptionPane;


public class AGREGARGESTIONUSUARIOS  extends javax.swing.JFrame {

   
    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private int indiceActual;
    
    
    
    public AGREGARGESTIONUSUARIOS () {
      initComponents();
        indiceActual = 0;

       gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();


 
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }


        
        cargarUsuariosEnTabla();
    }

 
    private void cargarUsuariosEnTabla() {
 
        modeloUsuarios.setRowCount(0);


        for (Usuarios usuario : listaUsuarios) {
            modeloUsuarios.addRow(new Object[]{
                usuario.getNombreUsuario(),
                usuario.getContrasenaUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCargo(),
                usuario.getGenero(),
                usuario.getNumeroDPI(),
                usuario.getFechaNacimiento(),
                usuario.getNumeroTelefono(),
                usuario.getCorreoElectronico(),
                usuario.getEstado()
            });
        }
    }

  private void limpiarCampos() {
    txtNombreUsuario.setText("");
    txtApellidoUsuario.setText("");
    txtNumeroDeDpiUsuario.setText("");
    txtCargoUsuario.setSelectedIndex(0);
    txtContraseñaUsuario.setText("");
    txtCorreoElectronicoUsuario.setText("");
    txtNumeroTelefonicoUsuario.setText("");
    txtGeneroUsuario.setSelectedIndex(0);
    txtFechaDeNacimientoUsuario.setDate(null);
    txtNombreDeUsuarioUsuario.setText("");
    txtEstadoUsuario.setSelectedIndex(0);
}
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
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
        txtContraseñaUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreDeUsuarioUsuario = new javax.swing.JTextField();
        txtNumeroTelefonicoUsuario = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtEstadoUsuario = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btnModificarUsuario = new javax.swing.JButton();
        btnAgregarUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        btnMostrarUsuario = new javax.swing.JButton();
        btnListaUsuario = new javax.swing.JButton();
        btnInicioUsuario = new javax.swing.JButton();
        btnSalirUsuario = new javax.swing.JButton();
        btnEstadoUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" AGREGAR USUARIO AL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NOMBRE");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("CARGO");

        txtCargoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "ADMINISTRADOR", "SECRETARIA" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoUsuario.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("NUMERO DE DPI");

        btnAgregarUsuarioSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnAgregarUsuarioSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUsuarioSistema.setText("AGREGAR");
        btnAgregarUsuarioSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioSistemaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("APELLIDO");

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE DE USUARIO");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("CONTRASEÑA");

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel20.setText("ESTADO ");

        txtEstadoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "BLOQUEADO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                            .addComponent(txtNumeroDeDpiUsuario))
                        .addGap(178, 178, 178)
                        .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreUsuario))
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
                                    .addComponent(txtContraseñaUsuario)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreDeUsuarioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCargoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(txtFechaDeNacimientoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroTelefonicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtNumeroDeDpiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCorreoElectronicoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAgregarUsuarioSistema, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        btnModificarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarUsuario.setText("MODIFICAR");
        btnModificarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUsuarioActionPerformed(evt);
            }
        });

        btnAgregarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarUsuario.setText("AGREGAR");
        btnAgregarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarUsuarioActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarUsuario.setText("ELIMINAR");
        btnEliminarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        btnMostrarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarUsuario.setText("MOSTRAR");
        btnMostrarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarUsuarioActionPerformed(evt);
            }
        });

        btnListaUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnListaUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnListaUsuario.setText("LISTA");
        btnListaUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaUsuarioActionPerformed(evt);
            }
        });

        btnInicioUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioUsuario.setText("INICIO");
        btnInicioUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioUsuarioActionPerformed(evt);
            }
        });

        btnSalirUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirUsuario.setText("SALIR");
        btnSalirUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirUsuarioActionPerformed(evt);
            }
        });

        btnEstadoUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoUsuario.setText("ESTADO");
        btnEstadoUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 848, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1163, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioSistemaActionPerformed
    try {

        String nombreUsuario = txtNombreUsuario.getText().trim();
        String apellidoUsuario = txtApellidoUsuario.getText().trim();
        long numeroDeDpiUsuario = Long.parseLong(txtNumeroDeDpiUsuario.getText().trim());
        String cargoUsuario = txtCargoUsuario.getSelectedItem().toString().trim();
        String correoElectronicoUsuario = txtCorreoElectronicoUsuario.getText().trim();
        int numeroTelefonicoUsuario = Integer.parseInt(txtNumeroTelefonicoUsuario.getText().trim()); 
        String generoUsuario = txtGeneroUsuario.getSelectedItem().toString().trim();
        String nombreDeUsuario = txtNombreDeUsuarioUsuario.getText().trim();
        String contrasenaUsuario = txtContraseñaUsuario.getText().trim();
        String estadoUsuario = txtEstadoUsuario.getSelectedItem().toString().trim();

        Date fechaNacimientoUsuarioDate = txtFechaDeNacimientoUsuario.getDate();
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

   
        if (String.valueOf(numeroDeDpiUsuario).length() != 13) { 
            JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
            return;
        }

        if (String.valueOf(numeroTelefonicoUsuario).length() != 8) { 
            JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
            return;
        }


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


        Usuarios usuario = new Usuarios(nombreDeUsuario, contrasenaUsuario, nombreUsuario, apellidoUsuario, cargoUsuario, 
                                        generoUsuario, numeroDeDpiUsuario, fechaDeNacimientoUsuario, numeroTelefonicoUsuario, 
                                        correoElectronicoUsuario, estadoUsuario);
        
        listaUsuarios.add(usuario);
        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");


        limpiarCampos();


        cargarUsuariosEnTabla();

      
        gestionUsuarios.setUsuarios(listaUsuarios); 
        gestionUsuarios.guardarUsuariosEnExcel();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + e.getMessage());
    }
    }//GEN-LAST:event_btnAgregarUsuarioSistemaActionPerformed

    private void btnModificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuarioActionPerformed
        MODIFICARGESTIONUSUARIOS abrir = new   MODIFICARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarUsuarioActionPerformed

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        AGREGARGESTIONUSUARIOS abrir = new  AGREGARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        ELIMINARGESTIONUSUARIOS  abrir = new  ELIMINARGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnMostrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUsuarioActionPerformed
        MOSTRARGESTIONUSUARIOS  abrir = new   MOSTRARGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarUsuarioActionPerformed

    private void btnListaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaUsuarioActionPerformed
        LISTAGESTIONUSUARIOS abrir = new   LISTAGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaUsuarioActionPerformed

    private void btnInicioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioUsuarioActionPerformed
        INICIOGESTIONUSUARIOS  abrir = new  INICIOGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioUsuarioActionPerformed

    private void btnSalirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirUsuarioActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirUsuarioActionPerformed

    private void btnEstadoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoUsuarioActionPerformed
        CONTRASEÑAUSUARIOGESTION abrir = new CONTRASEÑAUSUARIOGESTION ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoUsuarioActionPerformed

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
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AGREGARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AGREGARGESTIONUSUARIOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuario;
    private javax.swing.JButton btnAgregarUsuarioSistema;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnEstadoUsuario;
    private javax.swing.JButton btnInicioUsuario;
    private javax.swing.JButton btnListaUsuario;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JButton btnMostrarUsuario;
    private javax.swing.JButton btnSalirUsuario;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField txtApellidoUsuario;
    private javax.swing.JComboBox<String> txtCargoUsuario;
    private javax.swing.JTextField txtContraseñaUsuario;
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
