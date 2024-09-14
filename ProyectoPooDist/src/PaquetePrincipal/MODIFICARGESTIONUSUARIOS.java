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
import java.text.ParseException;
import javax.swing.JFrame; 
import javax.swing.JOptionPane;

public class MODIFICARGESTIONUSUARIOS extends javax.swing.JFrame {


    public GESTIONUSUARIOS gestionUsuarios;
    public Vector<Usuarios> listaUsuarios = new Vector<>();
    DefaultTableModel modeloUsuarios = new DefaultTableModel();
    private int indiceActual;


    public MODIFICARGESTIONUSUARIOS() {
        initComponents();
        indiceActual = 0;


        gestionUsuarios = new GESTIONUSUARIOS();
        gestionUsuarios.cargarUsuariosDesdeExcel();


        String[] columnas = {"Usuario", "Contraseña", "Nombre", "Apellido", "Cargo", "Género", "DPI", "Fecha de Nacimiento", "Teléfono", "Correo", "Estado"};
        modeloUsuarios.setColumnIdentifiers(columnas);

   
        if (gestionUsuarios.getUsuarios() != null) {
            listaUsuarios = gestionUsuarios.getUsuarios();
        }

    
        tblRegistroUsuarios.setModel(modeloUsuarios);
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

        tblRegistroUsuarios.setVisible(false);
    }
    
        private void cargarPilotosEnTablaGeneral() {
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

        tblRegistroUsuarios.setVisible(true);
    }
    
        private void limpiarCamposUsuario() {
    txtNombreUsuarioModificar.setText("");
    txtApellidoUsuarioModificar.setText("");
    txtNumeroDeDpiUsuarioModificar.setText("");
    txtCorreoElectronicoUsuarioModificar.setText("");
    txtNumeroTelefonicoUsuarioModificar.setText("");
    txtGeneroUsuarioModificar.setSelectedIndex(0);
    txtFechaDeNacimientoUsuarioModificar.setDate(null);
    txtEstadoUsuarioModificar.setSelectedIndex(0);
    txtContraseñaUsuarioModificar.setText("");
    txtEstadoUsuario.setSelectedIndex(0);
}
        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreUsuarioModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoUsuarioModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiUsuarioModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaUsuarioModificar = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoUsuarioModificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoUsuarioModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroUsuarioModificar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoUsuarioModificar = new javax.swing.JComboBox<>();
        btnMoficarUsuarioSistema = new javax.swing.JButton();
        txtNumeroTelefonicoUsuarioModificar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombreUsuarioBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApellidoUsuarioBuscar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNumeroDeDpiUsuariosBuscar = new javax.swing.JTextField();
        btnBuscarUsuarioSistema = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroUsuarios = new javax.swing.JTable();
        btnInsertarUsuariosSistema = new javax.swing.JButton();
        btnBuscarUsuarioSistemaTodos = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtContraseñaUsuarioModificar = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtNombreUsuarioUsuarioModificar = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtEstadoUsuario = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
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
        jTextField5.setText(" MODIFICAR USUARIO EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaUsuarioModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        txtCorreoElectronicoUsuarioModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoElectronicoUsuarioModificarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoUsuarioModificar.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroUsuarioModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoUsuarioModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USUARIO", "ADMINISTRADOR", "SECRETARIA" }));

        btnMoficarUsuarioSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnMoficarUsuarioSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoficarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnMoficarUsuarioSistema.setText("MODIFICAR");
        btnMoficarUsuarioSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMoficarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoficarUsuarioSistemaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("APELLIDO");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("NUMERO DE DPI");

        btnBuscarUsuarioSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarUsuarioSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarUsuarioSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarUsuarioSistema.setText("BUSCAR");
        btnBuscarUsuarioSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarUsuarioSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioSistemaActionPerformed(evt);
            }
        });

        jScrollPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane5MouseClicked(evt);
            }
        });

        tblRegistroUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroUsuarios);

        btnInsertarUsuariosSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnInsertarUsuariosSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInsertarUsuariosSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertarUsuariosSistema.setText("INSERTAR");
        btnInsertarUsuariosSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInsertarUsuariosSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarUsuariosSistemaActionPerformed(evt);
            }
        });

        btnBuscarUsuarioSistemaTodos.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarUsuarioSistemaTodos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarUsuarioSistemaTodos.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarUsuarioSistemaTodos.setText("TODOS");
        btnBuscarUsuarioSistemaTodos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarUsuarioSistemaTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioSistemaTodosActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel5.setText("NOMBRE USUARIO");

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel20.setText("CONTRASEÑA USUARIO");

        jLabel21.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel21.setText("ESTADO");

        txtEstadoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "BLOQUEADO", "ENFERMO", "EN VACACIONES", "JUBILADO" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaDeNacimientoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtCorreoElectronicoUsuarioModificar))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTipoDeLicenciaUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtNumeroDeDpiUsuarioModificar)))
                        .addGap(18, 18, 18)
                        .addComponent(btnMoficarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuarioUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtContraseñaUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(194, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEstadoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroTelefonicoUsuarioModificar)
                        .addGap(226, 226, 226))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumeroDeDpiUsuariosBuscar))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombreUsuarioBuscar)
                                            .addComponent(txtApellidoUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBuscarUsuarioSistema, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscarUsuarioSistemaTodos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnInsertarUsuariosSistema, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtGeneroUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoUsuarioBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeroDeDpiUsuariosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(33, 33, 33))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(8, Short.MAX_VALUE)
                        .addComponent(btnBuscarUsuarioSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarUsuarioSistemaTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsertarUsuariosSistema)
                        .addGap(13, 13, 13)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtApellidoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(txtEstadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGeneroUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombreUsuarioUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtContraseñaUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEstadoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtNumeroTelefonicoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTipoDeLicenciaUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)
                                .addComponent(jLabel12))
                            .addComponent(txtNumeroDeDpiUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCorreoElectronicoUsuarioModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))
                            .addComponent(txtFechaDeNacimientoUsuarioModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMoficarUsuarioSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(56, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtCorreoElectronicoUsuarioModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoElectronicoUsuarioModificarActionPerformed

    }//GEN-LAST:event_txtCorreoElectronicoUsuarioModificarActionPerformed

    private void btnMoficarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoficarUsuarioSistemaActionPerformed
    
    int filaSeleccionada = tblRegistroUsuarios.getSelectedRow();

    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario de la tabla para modificar.");
        return;
    }

 
    String nuevoNombre = txtNombreUsuarioModificar.getText().trim();
    String nuevoApellido = txtApellidoUsuarioModificar.getText().trim();
    String nuevoCorreo = txtCorreoElectronicoUsuarioModificar.getText().trim();
    String nuevoGenero = txtGeneroUsuarioModificar.getSelectedItem().toString().trim();
    Date fechaNacimientoDate = txtFechaDeNacimientoUsuarioModificar.getDate();

 
    String fechaDeNacimiento = null;
    if (fechaNacimientoDate != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaDeNacimiento = sdf.format(fechaNacimientoDate);
    }

    String nuevoEstado = txtEstadoUsuarioModificar.getSelectedItem().toString().trim();
    String nuevaContraseña = txtContraseñaUsuarioModificar.getText().trim();


    long nuevoDPI;
    int nuevoTelefono;
    try {
        nuevoDPI = Long.parseLong(txtNumeroDeDpiUsuarioModificar.getText().trim());
        nuevoTelefono = Integer.parseInt(txtNumeroTelefonicoUsuarioModificar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI y el número telefónico deben ser números válidos.");
        return;
    }


    if (String.valueOf(nuevoDPI).length() != 13) {
        JOptionPane.showMessageDialog(this, "El DPI debe contener exactamente 13 dígitos.");
        return;
    }

    if (String.valueOf(nuevoTelefono).length() != 8) {
        JOptionPane.showMessageDialog(this, "El número telefónico debe contener exactamente 8 dígitos.");
        return;
    }


    if (!nuevoCorreo.endsWith("@gmail.com")) {
        JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.");
        return;
    }


    for (int i = 0; i < listaUsuarios.size(); i++) {
        if (i == filaSeleccionada) {
            continue;
        }

        Usuarios usuarioExistente = listaUsuarios.get(i);
        if (usuarioExistente.getNumeroDPI() == nuevoDPI) {
            JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número de DPI.");
            return;
        }
        if (usuarioExistente.getNumeroTelefono() == nuevoTelefono) {
            JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese número telefónico.");
            return;
        }
        if (usuarioExistente.getCorreoElectronico().equals(nuevoCorreo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese correo electrónico.");
            return;
        }
    }


    Usuarios usuarioAActualizar = listaUsuarios.get(filaSeleccionada);
    usuarioAActualizar.setNombre(nuevoNombre);
    usuarioAActualizar.setApellido(nuevoApellido);
    usuarioAActualizar.setCorreoElectronico(nuevoCorreo);
    usuarioAActualizar.setNumeroTelefono(nuevoTelefono);
    usuarioAActualizar.setGenero(nuevoGenero);
    if (fechaDeNacimiento != null) {
        usuarioAActualizar.setFechaNacimiento(fechaDeNacimiento);
    }
    usuarioAActualizar.setContrasenaUsuario(nuevaContraseña);


    cargarUsuariosEnTabla();


    try {
        gestionUsuarios.setUsuarios(listaUsuarios);
        gestionUsuarios.guardarUsuariosEnExcel();
        JOptionPane.showMessageDialog(this, "Datos del usuario modificados exitosamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar los datos en Excel: " + e.getMessage());
    }


    limpiarCamposUsuario();
    }//GEN-LAST:event_btnMoficarUsuarioSistemaActionPerformed

    private void btnBuscarUsuarioSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioSistemaActionPerformed
        if (txtNombreUsuarioBuscar.getText().trim().isEmpty() ||
        txtApellidoUsuarioBuscar.getText().trim().isEmpty() ||
        txtNumeroDeDpiUsuariosBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }


    String nombreBuscado = txtNombreUsuarioBuscar.getText().trim();
    String apellidoBuscado = txtApellidoUsuarioBuscar.getText().trim();

    
    long dpiBuscado;
    try {
        dpiBuscado = Long.parseLong(txtNumeroDeDpiUsuariosBuscar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI debe ser un número válido.");
        return;
    }


    modeloUsuarios.setRowCount(0);
    boolean hayCoincidencias = false;


    for (Usuarios usuarios : listaUsuarios) {
        boolean coincide = true;

        if (!nombreBuscado.isEmpty() && !usuarios.getNombre().equalsIgnoreCase(nombreBuscado)) {
            coincide = false;
        }

        if (!apellidoBuscado.isEmpty() && !usuarios.getApellido().equalsIgnoreCase(apellidoBuscado)) {
            coincide = false;
        }


        if (usuarios.getNumeroDPI() != dpiBuscado) {
            coincide = false;
        }

        if (coincide) {

            modeloUsuarios.addRow(new Object[]{
                usuarios.getNombreUsuario(),
                usuarios.getContrasenaUsuario(),
                usuarios.getNombre(),
               usuarios.getApellido(),
                usuarios.getCargo(),
                usuarios.getGenero(),
                usuarios.getNumeroDPI(),
                usuarios.getFechaNacimiento(),
                usuarios.getNumeroTelefono(),
                usuarios.getCorreoElectronico(),
                usuarios.getEstado()
            });
            hayCoincidencias = true;
        }
    }


    if (hayCoincidencias) {
        tblRegistroUsuarios.setVisible(true);
    } else {
        tblRegistroUsuarios.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }


    txtNombreUsuarioBuscar.setText("");
    txtApellidoUsuarioBuscar.setText("");
    txtNumeroDeDpiUsuariosBuscar.setText("");
    }//GEN-LAST:event_btnBuscarUsuarioSistemaActionPerformed

    private void jScrollPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane5MouseClicked

    }//GEN-LAST:event_jScrollPane5MouseClicked

    private void btnInsertarUsuariosSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarUsuariosSistemaActionPerformed
int selectedRow = tblRegistroUsuarios.getSelectedRow();

    if (selectedRow != -1) {

        String nombreUsuario = tblRegistroUsuarios.getValueAt(selectedRow, 0).toString();
        String ContrasenaUsuario = tblRegistroUsuarios.getValueAt(selectedRow, 1).toString();
        String Nombre = tblRegistroUsuarios.getValueAt(selectedRow, 2).toString();
        String apellido = tblRegistroUsuarios.getValueAt(selectedRow, 3).toString();
        String cargo = tblRegistroUsuarios.getValueAt(selectedRow, 4).toString();
        String genero = tblRegistroUsuarios.getValueAt(selectedRow, 5).toString();
        String numeroDPI = tblRegistroUsuarios.getValueAt(selectedRow, 6).toString();
        String fechaNacimientoStr = tblRegistroUsuarios.getValueAt(selectedRow, 7).toString();
        String numeroTelefono = tblRegistroUsuarios.getValueAt(selectedRow, 8).toString();
        String correoElectronico = tblRegistroUsuarios.getValueAt(selectedRow, 9).toString();
        String estado = tblRegistroUsuarios.getValueAt(selectedRow, 10).toString();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al convertir la fecha de nacimiento. Asegúrate de que el formato sea correcto.");
            return;
        }


    txtNombreUsuarioModificar.setText("");
    txtApellidoUsuarioModificar.setText("");
    txtNumeroDeDpiUsuarioModificar.setText("");
    txtCorreoElectronicoUsuarioModificar.setText("");
    txtNumeroTelefonicoUsuarioModificar.setText("");
    txtGeneroUsuarioModificar.setSelectedIndex(0);
    txtFechaDeNacimientoUsuarioModificar.setDate(null);
    txtEstadoUsuarioModificar.setSelectedIndex(0);
    txtContraseñaUsuarioModificar.setText("");
    txtEstadoUsuario.setSelectedIndex(0);
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para insertar.");
    }
    }//GEN-LAST:event_btnInsertarUsuariosSistemaActionPerformed

    private void btnBuscarUsuarioSistemaTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioSistemaTodosActionPerformed
        cargarPilotosEnTablaGeneral();
    }//GEN-LAST:event_btnBuscarUsuarioSistemaTodosActionPerformed

    private void btnModificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUsuarioActionPerformed
        MODIFICARGESTIONUSUARIOS abrir = new   MODIFICARGESTIONUSUARIOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarUsuarioActionPerformed

    private void btnAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarUsuarioActionPerformed
        AGREGARGESTIONUSUARIOS  abrir = new  AGREGARGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarUsuarioActionPerformed

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        ELIMINARGESTIONUSUARIOS  abrir = new  ELIMINARGESTIONUSUARIOS ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnMostrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUsuarioActionPerformed
        MOSTRARGESTIONUSUARIOS   abrir = new   MOSTRARGESTIONUSUARIOS  ();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarUsuarioActionPerformed

    private void btnListaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaUsuarioActionPerformed
        LISTAGESTIONUSUARIOS  abrir = new   LISTAGESTIONUSUARIOS ();
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
        CONTRASEÑAUSUARIOGESTION abrir = new CONTRASEÑAUSUARIOGESTION();
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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONUSUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MODIFICARGESTIONUSUARIOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarUsuario;
    private javax.swing.JButton btnBuscarUsuarioSistema;
    private javax.swing.JButton btnBuscarUsuarioSistemaTodos;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnEstadoUsuario;
    private javax.swing.JButton btnInicioUsuario;
    private javax.swing.JButton btnInsertarUsuariosSistema;
    private javax.swing.JButton btnListaUsuario;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JButton btnMoficarUsuarioSistema;
    private javax.swing.JButton btnMostrarUsuario;
    private javax.swing.JButton btnSalirUsuario;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroUsuarios;
    private javax.swing.JTextField txtApellidoUsuarioBuscar;
    private javax.swing.JTextField txtApellidoUsuarioModificar;
    private javax.swing.JTextField txtContraseñaUsuarioModificar;
    private javax.swing.JTextField txtCorreoElectronicoUsuarioModificar;
    private javax.swing.JComboBox<String> txtEstadoUsuario;
    private javax.swing.JComboBox<String> txtEstadoUsuarioModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoUsuarioModificar;
    private javax.swing.JComboBox<String> txtGeneroUsuarioModificar;
    private javax.swing.JTextField txtNombreUsuarioBuscar;
    private javax.swing.JTextField txtNombreUsuarioModificar;
    private javax.swing.JTextField txtNombreUsuarioUsuarioModificar;
    private javax.swing.JTextField txtNumeroDeDpiUsuarioModificar;
    private javax.swing.JTextField txtNumeroDeDpiUsuariosBuscar;
    private javax.swing.JTextField txtNumeroTelefonicoUsuarioModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaUsuarioModificar;
    // End of variables declaration//GEN-END:variables
}
