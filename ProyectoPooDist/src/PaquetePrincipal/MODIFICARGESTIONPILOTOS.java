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


public class MODIFICARGESTIONPILOTOS extends javax.swing.JFrame {

 public GESTIONPILOTOS gestionPilotos;
    public Vector<Piloto> listaPilotos = new Vector<>();
    DefaultTableModel modeloPilotos = new DefaultTableModel();
    private int indiceActual;


public MODIFICARGESTIONPILOTOS() {
     initComponents();
        indiceActual = 0;


        gestionPilotos = new GESTIONPILOTOS();
        gestionPilotos.cargarPilotosDesdeExcel();


        String[] columnas = {"Nombre", "Apellido", "DPI", "Licencia", "Correo", "Teléfono", "Género", "Nacimiento", "Estado"};
        modeloPilotos.setColumnIdentifiers(columnas);


        if (gestionPilotos.getPilotos() != null) {
            listaPilotos = gestionPilotos.getPilotos();
        }


        tblRegistroPilotos.setModel(modeloPilotos);

        cargarPilotosEnTabla();
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

        tblRegistroPilotos.setVisible(false);
    }

    
    
        private void cargarPilotosEnTablaGeneral() {

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

        tblRegistroPilotos.setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePilotoModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellidoPilotoModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTipoDeLicenciaPilotoModificar = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCorreoElectronicoPilotoModificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFechaDeNacimientoPilotoModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtGeneroPilotoModificar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtEstadoPilotoModificar = new javax.swing.JComboBox<>();
        btnMoficarPilotSistema = new javax.swing.JButton();
        txtNumeroTelefonicoPilotoModificar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombrePilotoBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApellidoPilotoBuscar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNumeroDeDpiPilotoBuscar = new javax.swing.JTextField();
        btnBuscarPilotoSistema = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroPilotos = new javax.swing.JTable();
        btnInsertarPilotosSistema = new javax.swing.JButton();
        btnBuscarPilotoSistemaTodos = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnModificarPiloto = new javax.swing.JButton();
        btnAgregarPiloto = new javax.swing.JButton();
        btnEliminarPiloto = new javax.swing.JButton();
        btnMostrarPiloto = new javax.swing.JButton();
        btnListaPiloto = new javax.swing.JButton();
        btnInicioPiloto = new javax.swing.JButton();
        btnSalirPiloto = new javax.swing.JButton();
        btnEstadoPiloto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" MODIFICAR PILOTO EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("NOMBRE");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("APELLIDO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("NUMERO DE DPI");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("TIPO DE LICENCIA");

        txtTipoDeLicenciaPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "M" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("CORREO ELECTRONICO");

        txtCorreoElectronicoPilotoModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoElectronicoPilotoModificarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("FECHA DE NACIMIENTO");

        txtFechaDeNacimientoPilotoModificar.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("GENERO");

        txtGeneroPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("NUMERO TELEFONICO");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO DEL PILOTO");

        txtEstadoPilotoModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NEUTRO", "ENFERMO", "EN CASA", "EN VACACIONES", "EN VIAJE" }));

        btnMoficarPilotSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnMoficarPilotSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoficarPilotSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnMoficarPilotSistema.setText("MODIFICAR");
        btnMoficarPilotSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMoficarPilotSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoficarPilotSistemaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("NOMBRE");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("APELLIDO");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("NUMERO DE DPI");

        btnBuscarPilotoSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarPilotoSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarPilotoSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPilotoSistema.setText("BUSCAR");
        btnBuscarPilotoSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarPilotoSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPilotoSistemaActionPerformed(evt);
            }
        });

        jScrollPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane5MouseClicked(evt);
            }
        });

        tblRegistroPilotos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroPilotos);

        btnInsertarPilotosSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnInsertarPilotosSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInsertarPilotosSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertarPilotosSistema.setText("INSERTAR");
        btnInsertarPilotosSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInsertarPilotosSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarPilotosSistemaActionPerformed(evt);
            }
        });

        btnBuscarPilotoSistemaTodos.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarPilotoSistemaTodos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarPilotoSistemaTodos.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPilotoSistemaTodos.setText("TODOS");
        btnBuscarPilotoSistemaTodos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarPilotoSistemaTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPilotoSistemaTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEstadoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroTelefonicoPilotoModificar)
                                .addGap(220, 220, 220))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTipoDeLicenciaPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNumeroDeDpiPilotoModificar)
                                    .addComponent(txtCorreoElectronicoPilotoModificar))
                                .addGap(44, 44, 44)
                                .addComponent(btnMoficarPilotSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombrePilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumeroDeDpiPilotoBuscar))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombrePilotoBuscar)
                                            .addComponent(txtApellidoPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addComponent(btnBuscarPilotoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBuscarPilotoSistemaTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInsertarPilotosSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombrePilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeroDeDpiPilotoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnBuscarPilotoSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarPilotoSistemaTodos)
                        .addGap(18, 18, 18)
                        .addComponent(btnInsertarPilotosSistema)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombrePilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtApellidoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txtGeneroPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMoficarPilotSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtEstadoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNumeroTelefonicoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTipoDeLicenciaPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)
                                .addComponent(jLabel12))
                            .addComponent(txtNumeroDeDpiPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtFechaDeNacimientoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCorreoElectronicoPilotoModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnModificarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPiloto.setText("MODIFICAR");
        btnModificarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPilotoActionPerformed(evt);
            }
        });

        btnAgregarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPiloto.setText("AGREGAR");
        btnAgregarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPilotoActionPerformed(evt);
            }
        });

        btnEliminarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPiloto.setText("ELIMINAR");
        btnEliminarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPilotoActionPerformed(evt);
            }
        });

        btnMostrarPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarPiloto.setText("MOSTRAR");
        btnMostrarPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarPilotoActionPerformed(evt);
            }
        });

        btnListaPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnListaPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnListaPiloto.setText("LISTA");
        btnListaPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPilotoActionPerformed(evt);
            }
        });

        btnInicioPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioPiloto.setText("INICIO");
        btnInicioPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioPilotoActionPerformed(evt);
            }
        });

        btnSalirPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirPiloto.setText("SALIR");
        btnSalirPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPilotoActionPerformed(evt);
            }
        });

        btnEstadoPiloto.setBackground(new java.awt.Color(0, 102, 102));
        btnEstadoPiloto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEstadoPiloto.setForeground(new java.awt.Color(255, 255, 255));
        btnEstadoPiloto.setText("ESTADO");
        btnEstadoPiloto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEstadoPiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoPilotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadoPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirPiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextField5)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnMoficarPilotSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoficarPilotSistemaActionPerformed
int filaSeleccionada = tblRegistroPilotos.getSelectedRow();

    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un piloto de la tabla para modificar.");
        return;
    }


    String nuevoNombre = txtNombrePilotoModificar.getText().trim();
    String nuevoApellido = txtApellidoPilotoModificar.getText().trim();
    String nuevoTipoLicencia = txtTipoDeLicenciaPilotoModificar.getSelectedItem().toString().trim();
    String nuevoCorreo = txtCorreoElectronicoPilotoModificar.getText().trim();
    String nuevoGenero = txtGeneroPilotoModificar.getSelectedItem().toString().trim();
    JDateChooser dateChooser = txtFechaDeNacimientoPilotoModificar;
    Date fechaNacimientoDate = dateChooser.getDate();


    String fechaDeNacimiento = null;
    if (fechaNacimientoDate != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaDeNacimiento = sdf.format(fechaNacimientoDate);
    }

    String nuevoEstado = txtEstadoPilotoModificar.getSelectedItem().toString().trim();


    long nuevoDPI;
    int nuevoTelefono;
    try {
        nuevoDPI = Long.parseLong(txtNumeroDeDpiPilotoModificar.getText().trim());
        nuevoTelefono = Integer.parseInt(txtNumeroTelefonicoPilotoModificar.getText().trim());
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
        
    

for (int i = 0; i < listaPilotos.size(); i++) {
    Piloto pilotoExistente = listaPilotos.get(i);


    if (i == filaSeleccionada) {
        continue;
    }

    if (pilotoExistente.getNumeroDeDpi() == nuevoDPI) {
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número de DPI.");
        return;
    }
    if (pilotoExistente.getNumeroTelefonicoPiloto() == nuevoTelefono) { // Comparar como int
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese número telefónico.");
        return;
    }
    if (pilotoExistente.getCorreoElectronicoPiloto().equals(nuevoCorreo)) {
        JOptionPane.showMessageDialog(this, "Ya existe un piloto con ese correo electrónico.");
        return;
    }
}
        
        

    Piloto pilotoAActualizar = listaPilotos.get(filaSeleccionada);
    pilotoAActualizar.setNombrePiloto(nuevoNombre);
    pilotoAActualizar.setApellidoPiloto(nuevoApellido);
    pilotoAActualizar.setTipoLicencia(nuevoTipoLicencia);
    pilotoAActualizar.setCorreoElectronicoPiloto(nuevoCorreo);
    pilotoAActualizar.setNumeroTelefonicoPiloto(nuevoTelefono);
    pilotoAActualizar.setGeneroPiloto(nuevoGenero);
    if (fechaDeNacimiento != null) {
        pilotoAActualizar.setFechaDeNacimiento(fechaDeNacimiento);
    }
    pilotoAActualizar.setEstadoPiloto(nuevoEstado);

    cargarPilotosEnTabla();


    try {
        gestionPilotos.setPilotos(listaPilotos); 
        gestionPilotos.guardarPilotosEnExcel();
        JOptionPane.showMessageDialog(this, "Datos del piloto modificados exitosamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar los datos en Excel: " + e.getMessage());
    }


    txtNombrePilotoModificar.setText("");
    txtApellidoPilotoModificar.setText("");
    txtNumeroDeDpiPilotoModificar.setText("");
    txtTipoDeLicenciaPilotoModificar.setSelectedIndex(0);
    txtCorreoElectronicoPilotoModificar.setText("");
    txtNumeroTelefonicoPilotoModificar.setText("");
    txtGeneroPilotoModificar.setSelectedIndex(0);
    txtFechaDeNacimientoPilotoModificar.setDate(null);
    txtEstadoPilotoModificar.setSelectedIndex(0);
    }//GEN-LAST:event_btnMoficarPilotSistemaActionPerformed

    private void btnBuscarPilotoSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPilotoSistemaActionPerformed
if (txtNombrePilotoBuscar.getText().trim().isEmpty() ||
        txtApellidoPilotoBuscar.getText().trim().isEmpty() ||
        txtNumeroDeDpiPilotoBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }


    String nombreBuscado = txtNombrePilotoBuscar.getText().trim();
    String apellidoBuscado = txtApellidoPilotoBuscar.getText().trim();


    long dpiBuscado;
    try {
        dpiBuscado = Long.parseLong(txtNumeroDeDpiPilotoBuscar.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El DPI debe ser un número válido.");
        return;
    }


    modeloPilotos.setRowCount(0);
    boolean hayCoincidencias = false;


    for (Piloto piloto : listaPilotos) {
        boolean coincide = true;

        if (!nombreBuscado.isEmpty() && !piloto.getNombrePiloto().equalsIgnoreCase(nombreBuscado)) {
            coincide = false;
        }

        if (!apellidoBuscado.isEmpty() && !piloto.getApellidoPiloto().equalsIgnoreCase(apellidoBuscado)) {
            coincide = false;
        }

  
        if (piloto.getNumeroDeDpi() != dpiBuscado) {
            coincide = false;
        }

        if (coincide) {

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
            hayCoincidencias = true;
        }
    }

    if (hayCoincidencias) {
        tblRegistroPilotos.setVisible(true);
    } else {
        tblRegistroPilotos.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }


    txtNombrePilotoBuscar.setText("");
    txtApellidoPilotoBuscar.setText("");
    txtNumeroDeDpiPilotoBuscar.setText("");
    }//GEN-LAST:event_btnBuscarPilotoSistemaActionPerformed

    private void btnModificarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPilotoActionPerformed
        MODIFICARGESTIONPILOTOS abrir = new   MODIFICARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarPilotoActionPerformed

    private void btnAgregarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPilotoActionPerformed
        AGREGARGESTIONPILOTOS abrir = new  AGREGARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarPilotoActionPerformed

    private void btnEliminarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPilotoActionPerformed
        ELIMINARGESTIONPILOTOS abrir = new  ELIMINARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarPilotoActionPerformed

    private void btnMostrarPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarPilotoActionPerformed
        MOSTRARGESTIONPILOTOS abrir = new   MOSTRARGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarPilotoActionPerformed

    private void btnListaPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaPilotoActionPerformed
        LISTAGESTIONPILOTOS abrir = new   LISTAGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaPilotoActionPerformed

    private void btnInicioPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioPilotoActionPerformed
        INICIOGESTIONPILOTOS abrir = new  INICIOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioPilotoActionPerformed

    private void btnSalirPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPilotoActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirPilotoActionPerformed

    private void btnEstadoPilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoPilotoActionPerformed
        ESTADOGESTIONPILOTOS abrir = new ESTADOGESTIONPILOTOS();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEstadoPilotoActionPerformed

    private void btnInsertarPilotosSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarPilotosSistemaActionPerformed
 int selectedRow = tblRegistroPilotos.getSelectedRow();
    
    if (selectedRow != -1) {

        String nombre = tblRegistroPilotos.getValueAt(selectedRow, 0).toString();
        String apellido = tblRegistroPilotos.getValueAt(selectedRow, 1).toString();
        String dpi = tblRegistroPilotos.getValueAt(selectedRow, 2).toString();
        String tipoLicencia = tblRegistroPilotos.getValueAt(selectedRow, 3).toString();
        String correo = tblRegistroPilotos.getValueAt(selectedRow, 4).toString();
        String telefono = tblRegistroPilotos.getValueAt(selectedRow, 5).toString();
        String genero = tblRegistroPilotos.getValueAt(selectedRow, 6).toString();
        String fechaNacimientoStr = tblRegistroPilotos.getValueAt(selectedRow, 7).toString();
        String estado = tblRegistroPilotos.getValueAt(selectedRow, 8).toString();
        
   
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al convertir la fecha de nacimiento. Asegúrate de que el formato sea correcto.");
            return;
        }
        
 
        txtNombrePilotoModificar.setText(nombre);
        txtApellidoPilotoModificar.setText(apellido);
        txtNumeroDeDpiPilotoModificar.setText(dpi);
        txtTipoDeLicenciaPilotoModificar.setSelectedItem(tipoLicencia);
        txtCorreoElectronicoPilotoModificar.setText(correo);
        txtNumeroTelefonicoPilotoModificar.setText(telefono);
        txtGeneroPilotoModificar.setSelectedItem(genero);
        txtFechaDeNacimientoPilotoModificar.setDate(fechaNacimiento);
        txtEstadoPilotoModificar.setSelectedItem(estado);
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione un piloto de la tabla para insertar.");
    }
    }//GEN-LAST:event_btnInsertarPilotosSistemaActionPerformed

    private void jScrollPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane5MouseClicked

    }//GEN-LAST:event_jScrollPane5MouseClicked

    private void btnBuscarPilotoSistemaTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPilotoSistemaTodosActionPerformed
    cargarPilotosEnTablaGeneral();
    }//GEN-LAST:event_btnBuscarPilotoSistemaTodosActionPerformed

    private void txtCorreoElectronicoPilotoModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoElectronicoPilotoModificarActionPerformed

    }//GEN-LAST:event_txtCorreoElectronicoPilotoModificarActionPerformed

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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONPILOTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MODIFICARGESTIONPILOTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarPiloto;
    private javax.swing.JButton btnBuscarPilotoSistema;
    private javax.swing.JButton btnBuscarPilotoSistemaTodos;
    private javax.swing.JButton btnEliminarPiloto;
    private javax.swing.JButton btnEstadoPiloto;
    private javax.swing.JButton btnInicioPiloto;
    private javax.swing.JButton btnInsertarPilotosSistema;
    private javax.swing.JButton btnListaPiloto;
    private javax.swing.JButton btnModificarPiloto;
    private javax.swing.JButton btnMoficarPilotSistema;
    private javax.swing.JButton btnMostrarPiloto;
    private javax.swing.JButton btnSalirPiloto;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblRegistroPilotos;
    private javax.swing.JTextField txtApellidoPilotoBuscar;
    private javax.swing.JTextField txtApellidoPilotoModificar;
    private javax.swing.JTextField txtCorreoElectronicoPilotoModificar;
    private javax.swing.JComboBox<String> txtEstadoPilotoModificar;
    private com.toedter.calendar.JDateChooser txtFechaDeNacimientoPilotoModificar;
    private javax.swing.JComboBox<String> txtGeneroPilotoModificar;
    private javax.swing.JTextField txtNombrePilotoBuscar;
    private javax.swing.JTextField txtNombrePilotoModificar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoBuscar;
    private javax.swing.JTextField txtNumeroDeDpiPilotoModificar;
    private javax.swing.JTextField txtNumeroTelefonicoPilotoModificar;
    private javax.swing.JComboBox<String> txtTipoDeLicenciaPilotoModificar;
    // End of variables declaration//GEN-END:variables
}
