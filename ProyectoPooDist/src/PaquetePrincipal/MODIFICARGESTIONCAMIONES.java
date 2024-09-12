package PaquetePrincipal;

import com.toedter.calendar.JDateChooser;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.regex.Pattern;

public class MODIFICARGESTIONCAMIONES extends javax.swing.JFrame {
private GESTIONCAMIONES gestionCamiones;
    private Vector<Camiones> listaCamiones;
    private DefaultTableModel modeloCamiones;
    private int indiceActual;
    
    
    public MODIFICARGESTIONCAMIONES() {
initComponents(); // Inicializa la interfaz gráfica
        indiceActual = 0;

        // Iniciamos la gestión de camiones
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel(); // Carga los datos desde el archivo Excel

        // Definimos las columnas de la tabla de camiones
        String[] columnas = {"Placas", "Estado", "Tipo de Combustible", "Kilometraje", "Capacidad de Carga", "Año de Fabricación", "Modelo", "Marca", "Costos"};
        modeloCamiones = new DefaultTableModel();
        modeloCamiones.setColumnIdentifiers(columnas);

        // Verificamos si hay camiones y los cargamos
        listaCamiones = gestionCamiones.getCamiones();
        if (listaCamiones != null) {
            // Cargamos los camiones en la tabla
            cargarCamionesEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron camiones en la lista.");
        }
    }
    
    

    private void cargarCamionesEnTabla() {
        // Vaciamos la tabla completamente
        modeloCamiones.setRowCount(0);

        // Llenamos la tabla con los elementos del vector
        for (Camiones camiones : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camiones.getPlacas(),
                camiones.getEstado(),
                camiones.getTipoCombustible(),
                camiones.getKilometraje(),
                camiones.getCapacidadCarga(),
                camiones.getAñoFabricacion(),
                camiones.getModelo(),
                camiones.getMarca(),
                camiones.getCostos()
            });
        }
        tblRegistroCamiones.setVisible(false);
    }

    
     private void  cargarPilotosEnTablaGeneral() {
        // Vaciamos la tabla completamente
        modeloCamiones.setRowCount(0);

        // Llenamos la tabla con los elementos del vector
        for (Camiones camiones : listaCamiones) {
            modeloCamiones.addRow(new Object[]{
                camiones.getPlacas(),
                camiones.getEstado(),
                camiones.getTipoCombustible(),
                camiones.getKilometraje(),
                camiones.getCapacidadCarga(),
                camiones.getAñoFabricacion(),
                camiones.getModelo(),
                camiones.getMarca(),
                camiones.getCostos()
            });
        }
        tblRegistroCamiones  .setVisible(true);
    }
     
     
     
    private void limpiarCampos() {
        txtPlacaCamionesModificar.setText("");
        txtEstadoCamionModificar.setSelectedIndex(0); // Restablecer al primer valor
        txtTipoCombustibleCamionModificar.setSelectedIndex(0); // Restablecer al primer valor
        txtKilometrajeCamionModificar.setText("");
        txtCapacidadDeCargaCamionModificar.setText("");
        txtAñoDeFabricacionCamionModificar.setDate(null); // Limpiar la fecha seleccionada
        txtModeloCamionModificar.setText("");
        txtMarcaCamionModificar.setText("");
        txtCostoCamionModificar.setText("");
    }

    private boolean validarPlacas(String placas) {
        // Validar que la placa contenga al menos una letra y un número
        String regex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+$";
        return Pattern.matches(regex, placas);
    }

    private boolean validarKilometraje(double kilometraje) {
        // Validar que el kilometraje sea positivo y realista (0 - 1,000,000 km)
        return (kilometraje >= 0 && kilometraje <= 1000000);
    }

    private boolean validarCapacidadCarga(double capacidadCarga) {
        // Validar que la capacidad de carga sea positiva y realista (100 - 30000 kg)
        return (capacidadCarga >= 100 && capacidadCarga <= 30000);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtKilometrajeCamionModificar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtModeloCamionModificar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCapacidadDeCargaCamionModificar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCostoCamionModificar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtAñoDeFabricacionCamionModificar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnMoficarCamionSistema = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtMarcaCamionBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtModeloCamionBuscar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtPlacaCamionBuscar = new javax.swing.JTextField();
        btnBuscarCamionSistema = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        btnInsertarCamionesSistema = new javax.swing.JButton();
        btnBuscarCamionSistemaTodos = new javax.swing.JButton();
        txtEstadoCamionModificar = new javax.swing.JComboBox<>();
        txtPlacaCamionesModificar = new javax.swing.JTextField();
        txtTipoCombustibleCamionModificar = new javax.swing.JComboBox<>();
        txtMarcaCamionModificar = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnModificarCamion = new javax.swing.JButton();
        btnAgregarCamion = new javax.swing.JButton();
        btnEliminarCamion = new javax.swing.JButton();
        btnMostrarCamion = new javax.swing.JButton();
        btnListaCamion = new javax.swing.JButton();
        btnInicioCamion = new javax.swing.JButton();
        btnSalirCamion = new javax.swing.JButton();
        btnGarageCamion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(6, 40, 86));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(0, 153, 153));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText(" MODIFICAR CAMIONES EN EL SISTEMA");
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel3.setText("MARCA");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel12.setText("CAPACIDAD DE CARGA");

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel13.setText("KILOMETRAJE");

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel14.setText("COSTO");

        txtCostoCamionModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoCamionModificarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel15.setText("AÑO DE FABRICACION");

        txtAñoDeFabricacionCamionModificar.setDateFormatString("dd/MM/yyyy");

        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel16.setText("PLACA");

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel17.setText("TIPO DE COMBUSTIBLE");

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel18.setText("ESTADO");

        btnMoficarCamionSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnMoficarCamionSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoficarCamionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnMoficarCamionSistema.setText("MODIFICAR");
        btnMoficarCamionSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMoficarCamionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoficarCamionSistemaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel4.setText("MARCA");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel11.setText("MODELO");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabel19.setText("PLACA");

        btnBuscarCamionSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarCamionSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarCamionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCamionSistema.setText("BUSCAR");
        btnBuscarCamionSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarCamionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCamionSistemaActionPerformed(evt);
            }
        });

        jScrollPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane5MouseClicked(evt);
            }
        });

        tblRegistroCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblRegistroCamiones);

        btnInsertarCamionesSistema.setBackground(new java.awt.Color(0, 102, 255));
        btnInsertarCamionesSistema.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInsertarCamionesSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertarCamionesSistema.setText("INSERTAR");
        btnInsertarCamionesSistema.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInsertarCamionesSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarCamionesSistemaActionPerformed(evt);
            }
        });

        btnBuscarCamionSistemaTodos.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarCamionSistemaTodos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscarCamionSistemaTodos.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarCamionSistemaTodos.setText("TODOS");
        btnBuscarCamionSistemaTodos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnBuscarCamionSistemaTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCamionSistemaTodosActionPerformed(evt);
            }
        });

        txtEstadoCamionModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En viaje", "En el taller", "Descompuesto", "Libre" }));
        txtEstadoCamionModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoCamionModificarActionPerformed(evt);
            }
        });

        txtTipoCombustibleCamionModificar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disel", "Gasolina", "Eléctrico", "Híbrido", "Hidrógeno", "Gas Licuado de Petróleo", " " }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKilometrajeCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPlacaCamionBuscar))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMarcaCamionBuscar)
                                        .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMarcaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstadoCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCapacidadDeCargaCamionModificar))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtTipoCombustibleCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtModeloCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnMoficarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCostoCamionModificar))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPlacaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAñoDeFabricacionCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInsertarCamionesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCamionSistemaTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMarcaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtModeloCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPlacaCamionBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnBuscarCamionSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCamionSistemaTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInsertarCamionesSistema)
                        .addGap(24, 24, 24)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtModeloCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txtPlacaCamionesModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMarcaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel17)
                        .addComponent(txtEstadoCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTipoCombustibleCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtAñoDeFabricacionCamionModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKilometrajeCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtCapacidadDeCargaCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtCostoCamionModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btnMoficarCamionSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnModificarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnModificarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCamion.setText("MODIFICAR");
        btnModificarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnModificarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCamionActionPerformed(evt);
            }
        });

        btnAgregarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnAgregarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCamion.setText("AGREGAR");
        btnAgregarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnAgregarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCamionActionPerformed(evt);
            }
        });

        btnEliminarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEliminarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCamion.setText("ELIMINAR");
        btnEliminarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnEliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCamionActionPerformed(evt);
            }
        });

        btnMostrarCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnMostrarCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMostrarCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarCamion.setText("MOSTRAR");
        btnMostrarCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnMostrarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCamionActionPerformed(evt);
            }
        });

        btnListaCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnListaCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnListaCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnListaCamion.setText("LISTA");
        btnListaCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnListaCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaCamionActionPerformed(evt);
            }
        });

        btnInicioCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnInicioCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInicioCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioCamion.setText("INICIO");
        btnInicioCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnInicioCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioCamionActionPerformed(evt);
            }
        });

        btnSalirCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnSalirCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalirCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnSalirCamion.setText("SALIR");
        btnSalirCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnSalirCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirCamionActionPerformed(evt);
            }
        });

        btnGarageCamion.setBackground(new java.awt.Color(0, 102, 102));
        btnGarageCamion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGarageCamion.setForeground(new java.awt.Color(255, 255, 255));
        btnGarageCamion.setText("GARAGE");
        btnGarageCamion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        btnGarageCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGarageCamionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInicioCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListaCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnInicioCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListaCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGarageCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalirCamion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtCostoCamionModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoCamionModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoCamionModificarActionPerformed

    private void btnMoficarCamionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoficarCamionSistemaActionPerformed
int filaSeleccionada = tblRegistroCamiones.getSelectedRow();

    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un camión de la tabla para modificar.");
        return;
    }

    // Obtener los nuevos datos del formulario
    String nuevaPlaca = txtPlacaCamionesModificar.getText().trim();
    String nuevoEstado = txtEstadoCamionModificar.getSelectedItem().toString().trim();
    String nuevoTipoCombustible = txtTipoCombustibleCamionModificar.getSelectedItem().toString().trim();
    double nuevoKilometraje;
    double nuevaCapacidadCarga;
    String nuevoAñoFabricacion; // Se mantiene como String
    String nuevoModelo = txtModeloCamionModificar.getText().trim();
    String nuevaMarca = txtMarcaCamionModificar.getText().trim();
    double nuevoCosto;

    // Validar y parsear los campos numéricos
    try {
        nuevoKilometraje = Double.parseDouble(txtKilometrajeCamionModificar.getText().trim());
        nuevaCapacidadCarga = Double.parseDouble(txtCapacidadDeCargaCamionModificar.getText().trim());
        nuevoCosto = Double.parseDouble(txtCostoCamionModificar.getText().trim());
        
        // Obtener el año de fabricación como String
        nuevoAñoFabricacion = new SimpleDateFormat("yyyy").format(txtAñoDeFabricacionCamionModificar.getDate());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, asegúrate de que el kilometraje, la capacidad de carga y el costo sean números válidos.");
        return;
    } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un año de fabricación.");
        return;
    }

    // Validar los datos
    if (!validarPlacas(nuevaPlaca)) {
        JOptionPane.showMessageDialog(this, "Las placas deben contener al menos una letra y un número.");
        return;
    }

    if (!validarKilometraje(nuevoKilometraje)) {
        JOptionPane.showMessageDialog(this, "El kilometraje debe ser un valor positivo y realista (0 - 1,000,000 km).");
        return;
    }

    if (!validarCapacidadCarga(nuevaCapacidadCarga)) {
        JOptionPane.showMessageDialog(this, "La capacidad de carga debe ser un valor positivo y realista (100 - 30,000 kg).");
        return;
    }

    // Actualizar los datos del camión seleccionado
    Camiones camionAActualizar = listaCamiones.get(filaSeleccionada);
    camionAActualizar.setPlacas(nuevaPlaca);
    camionAActualizar.setEstado(nuevoEstado);
    camionAActualizar.setTipoCombustible(nuevoTipoCombustible);
    camionAActualizar.setKilometraje(nuevoKilometraje);
    camionAActualizar.setCapacidadCarga(nuevaCapacidadCarga);
    camionAActualizar.setAñoFabricacion(nuevoAñoFabricacion); // Se establece como String
    camionAActualizar.setModelo(nuevoModelo);
    camionAActualizar.setMarca(nuevaMarca);
    camionAActualizar.setCostos(nuevoCosto);

    // Actualizar la tabla
    cargarCamionesEnTabla();

    // Guardar los cambios en el archivo Excel
    try {
        gestionCamiones.setCamiones(listaCamiones);
        gestionCamiones.guardarCamionesEnExcel();
        JOptionPane.showMessageDialog(this, "Datos del camión modificados exitosamente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar los datos en Excel: " + e.getMessage());
    }

    // Limpiar campos de entrada
    limpiarCampos();
    }//GEN-LAST:event_btnMoficarCamionSistemaActionPerformed

    private void btnBuscarCamionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCamionSistemaActionPerformed
if (txtMarcaCamionBuscar.getText().trim().isEmpty() ||
        txtModeloCamionBuscar.getText().trim().isEmpty() ||
        txtPlacaCamionBuscar.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos de búsqueda.");
        return;
    }

    // Obtener los valores ingresados
    String marcaBuscada = txtMarcaCamionBuscar.getText().trim();
    String modeloBuscado = txtModeloCamionBuscar.getText().trim();
    String placaBuscada = txtPlacaCamionBuscar.getText().trim();

    // Reiniciar el modelo de la tabla
    modeloCamiones.setRowCount(0);
    boolean hayCoincidencias = false;

    // Buscar coincidencias en la lista de camiones
    for (Camiones camion : listaCamiones) {
        boolean coincide = true;

        if (!marcaBuscada.isEmpty() && !camion.getMarca().equalsIgnoreCase(marcaBuscada)) {
            coincide = false;
        }

        if (!modeloBuscado.isEmpty() && !camion.getModelo().equalsIgnoreCase(modeloBuscado)) {
            coincide = false;
        }

        if (!placaBuscada.isEmpty() && !camion.getPlacas().equalsIgnoreCase(placaBuscada)) {
            coincide = false;
        }

        if (coincide) {
            // Si hay coincidencias, agregar las filas al modelo de la tabla
            modeloCamiones.addRow(new Object[]{
                camion.getPlacas(),
                camion.getEstado(),
                camion.getTipoCombustible(),
                camion.getKilometraje(),
                camion.getCapacidadCarga(),
                camion.getAñoFabricacion(),
                camion.getModelo(),
                camion.getMarca(),
                camion.getCostos()
            });
            hayCoincidencias = true;
        }
    }

    // Mostrar u ocultar la tabla según si hay coincidencias
    if (hayCoincidencias) {
        tblRegistroCamiones.setVisible(true);
    } else {
        tblRegistroCamiones.setVisible(false);
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias para la búsqueda.");
    }

    // Limpiar los campos de búsqueda
    txtMarcaCamionBuscar.setText("");
    txtModeloCamionBuscar.setText("");
    txtPlacaCamionBuscar.setText("");
    }//GEN-LAST:event_btnBuscarCamionSistemaActionPerformed

    private void jScrollPane5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane5MouseClicked

    }//GEN-LAST:event_jScrollPane5MouseClicked

    private void btnInsertarCamionesSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarCamionesSistemaActionPerformed
int selectedRow = tblRegistroCamiones.getSelectedRow();

    if (selectedRow != -1) {
        // Obtiene los datos de la fila seleccionada
        String placa = tblRegistroCamiones.getValueAt(selectedRow, 0).toString();
        String estado = tblRegistroCamiones.getValueAt(selectedRow, 1).toString();
        String tipoCombustible = tblRegistroCamiones.getValueAt(selectedRow, 2).toString();
        String kilometrajeStr = tblRegistroCamiones.getValueAt(selectedRow, 3).toString();
        String capacidadCargaStr = tblRegistroCamiones.getValueAt(selectedRow, 4).toString();
        String añoFabricacion = tblRegistroCamiones.getValueAt(selectedRow, 5).toString();
        String modelo = tblRegistroCamiones.getValueAt(selectedRow, 6).toString();
        String marca = tblRegistroCamiones.getValueAt(selectedRow, 7).toString();
        String costosStr = tblRegistroCamiones.getValueAt(selectedRow, 8).toString();

        // Transfiere los datos a los campos correspondientes
        txtPlacaCamionesModificar.setText(placa);
        txtEstadoCamionModificar.setSelectedItem(estado);
        txtTipoCombustibleCamionModificar.setSelectedItem(tipoCombustible);
        
        // Convertir a números
        try {
            double kilometraje = Double.parseDouble(kilometrajeStr);
            txtKilometrajeCamionModificar.setText(String.valueOf(kilometraje));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir el kilometraje. Asegúrate de que sea un número válido.");
            return;
        }

        try {
            double capacidadCarga = Double.parseDouble(capacidadCargaStr);
            txtCapacidadDeCargaCamionModificar.setText(String.valueOf(capacidadCarga));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir la capacidad de carga. Asegúrate de que sea un número válido.");
            return;
        }

        // Manejo del año de fabricación
        try {
            int año = Integer.parseInt(añoFabricacion);
            Calendar calendar = Calendar.getInstance();
            calendar.set(año, Calendar.JANUARY, 1); // Establecer el 1 de enero del año
            txtAñoDeFabricacionCamionModificar.setDate(calendar.getTime());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir el año de fabricación. Asegúrate de que sea un número válido.");
            return;
        }

        txtModeloCamionModificar.setText(modelo);
        txtMarcaCamionModificar.setText(marca);

        try {
            double costos = Double.parseDouble(costosStr);
            txtCostoCamionModificar.setText(String.valueOf(costos));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al convertir el costo. Asegúrate de que sea un número válido.");
            return;
        }

        // Limpia los campos después de la inserción
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione un camión de la tabla para insertar.");
    }
    }//GEN-LAST:event_btnInsertarCamionesSistemaActionPerformed

    private void btnBuscarCamionSistemaTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCamionSistemaTodosActionPerformed
        cargarPilotosEnTablaGeneral();
    }//GEN-LAST:event_btnBuscarCamionSistemaTodosActionPerformed

    private void btnModificarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCamionActionPerformed
        MODIFICARGESTIONCAMIONES abrir = new   MODIFICARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarCamionActionPerformed

    private void btnAgregarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCamionActionPerformed
        AGREGARGESTIONCAMIONES abrir = new  AGREGARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarCamionActionPerformed

    private void btnEliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCamionActionPerformed
        ELIMINARGESTIONCAMIONES abrir = new  ELIMINARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnEliminarCamionActionPerformed

    private void btnMostrarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCamionActionPerformed
        MOSTRARGESTIONCAMIONES abrir = new   MOSTRARGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMostrarCamionActionPerformed

    private void btnListaCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaCamionActionPerformed
        LISTAGESTIONCAMIONES abrir = new   LISTAGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnListaCamionActionPerformed

    private void btnInicioCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioCamionActionPerformed
        INICIOGESTIONCAMIONES abrir = new  INICIOGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInicioCamionActionPerformed

    private void btnSalirCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirCamionActionPerformed
        INICIOPINEEDINICIAL abrir = new INICIOPINEEDINICIAL();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSalirCamionActionPerformed

    private void btnGarageCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGarageCamionActionPerformed
        GARAGEGESTIONCAMIONES abrir = new GARAGEGESTIONCAMIONES();
        abrir.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGarageCamionActionPerformed

    private void txtEstadoCamionModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoCamionModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoCamionModificarActionPerformed

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
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MODIFICARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MODIFICARGESTIONCAMIONES().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCamion;
    private javax.swing.JButton btnBuscarCamionSistema;
    private javax.swing.JButton btnBuscarCamionSistemaTodos;
    private javax.swing.JButton btnEliminarCamion;
    private javax.swing.JButton btnGarageCamion;
    private javax.swing.JButton btnInicioCamion;
    private javax.swing.JButton btnInsertarCamionesSistema;
    private javax.swing.JButton btnListaCamion;
    private javax.swing.JButton btnModificarCamion;
    private javax.swing.JButton btnMoficarCamionSistema;
    private javax.swing.JButton btnMostrarCamion;
    private javax.swing.JButton btnSalirCamion;
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
    private javax.swing.JTable tblRegistroCamiones;
    private com.toedter.calendar.JDateChooser txtAñoDeFabricacionCamionModificar;
    private javax.swing.JTextField txtCapacidadDeCargaCamionModificar;
    private javax.swing.JTextField txtCostoCamionModificar;
    private javax.swing.JComboBox<String> txtEstadoCamionModificar;
    private javax.swing.JTextField txtKilometrajeCamionModificar;
    private javax.swing.JTextField txtMarcaCamionBuscar;
    private javax.swing.JTextField txtMarcaCamionModificar;
    private javax.swing.JTextField txtModeloCamionBuscar;
    private javax.swing.JTextField txtModeloCamionModificar;
    private javax.swing.JTextField txtPlacaCamionBuscar;
    private javax.swing.JTextField txtPlacaCamionesModificar;
    private javax.swing.JComboBox<String> txtTipoCombustibleCamionModificar;
    // End of variables declaration//GEN-END:variables
}
