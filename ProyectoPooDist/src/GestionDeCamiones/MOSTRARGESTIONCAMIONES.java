package GestionDeCamiones;

// Importación de clases necesarias para el funcionamiento de la aplicación
import GestionDeCamiones.INICIOGESTIONCAMIONES;
import GestionDeCamiones.GESTIONCAMIONES;
import GestionDeCamiones.Camiones;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Clase MOSTRARGESTIONCAMIONES
 * Esta clase representa una ventana JFrame para mostrar y gestionar la información de los camiones.
 * Permite visualizar detalles específicos de un camión seleccionado y gestionar la lista de camiones.
 */
public class MOSTRARGESTIONCAMIONES extends javax.swing.JFrame {

    // Atributos de la clase
    private GESTIONCAMIONES gestionCamiones;  // Gestión de camiones
    private Vector<Camiones> listaCamiones;    // Lista de camiones
    private DefaultTableModel modeloCamiones;  // Modelo de la tabla para mostrar camiones
    private Camiones camionActual;              // Camión actualmente seleccionado
    private INICIOGESTIONCAMIONES ventanaPrincipal; // Ventana principal de gestión

    /**
     * Constructor por defecto de la clase.
     * Inicializa la ventana y carga los camiones desde el archivo Excel.
     */
    public MOSTRARGESTIONCAMIONES() {
        initComponents();
        setResizable(false); // Desactivar el cambio de tamaño
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        gestionCamiones = new GESTIONCAMIONES();
        gestionCamiones.cargarCamionesDesdeExcel(); // Cargar camiones
        listaCamiones = gestionCamiones.getCamiones(); // Obtener lista de camiones
        configurarTabla(); // Configurar la tabla
    }

    /**
     * Constructor que permite cargar un camión específico.
     * @param camion Camión a cargar
     * @param ventanaPrincipal Ventana principal de gestión
     */
    public MOSTRARGESTIONCAMIONES(Camiones camion, INICIOGESTIONCAMIONES ventanaPrincipal) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.ventanaPrincipal = ventanaPrincipal;
        setResizable(false); // Desactivar el cambio de tamaño
        if (ventanaPrincipal != null) {
            this.gestionCamiones = ventanaPrincipal.gestionCamiones; // Obtener gestión de camiones de la ventana principal
            this.listaCamiones = gestionCamiones.getCamiones(); // Obtener lista de camiones
        } else {
            gestionCamiones = new GESTIONCAMIONES();
            gestionCamiones.cargarCamionesDesdeExcel(); // Cargar camiones
            listaCamiones = gestionCamiones.getCamiones(); // Obtener lista de camiones
        }

        this.camionActual = camion; // Asignar camión actual
        configurarTabla(); // Configurar la tabla
        if (camion != null) {
            cargarDatosCamion(); // Cargar datos del camión actual
        }
    }

    /**
     * Configura la tabla para mostrar los datos de los camiones.
     * Define el modelo de la tabla y desactiva la edición de celdas.
     */
    private void configurarTabla() {
        modeloCamiones = new DefaultTableModel(new Object[]{
            "Identificadores", "Datos" // Nombres de las columnas
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };

        tblRegistroCamiones.setModel(modeloCamiones); // Asignar modelo a la tabla
        tblRegistroCamiones.setCellSelectionEnabled(true);
        tblRegistroCamiones.setFocusable(true);
        tblRegistroCamiones.setRowSelectionAllowed(true);
        tblRegistroCamiones.setColumnSelectionAllowed(false);
        tblRegistroCamiones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Selección de una sola fila
    }

    /**
     * Carga los datos del camión actual en la tabla.
     * Actualiza la tabla con la información del camión seleccionado.
     */
    private void cargarDatosCamion() {
        if (camionActual != null) {
            modeloCamiones.setRowCount(0); // Limpiar la tabla

            // Agregar filas a la tabla con los datos del camión
            modeloCamiones.addRow(new Object[]{"Placas", camionActual.getPlacas()});
            modeloCamiones.addRow(new Object[]{"Marca", camionActual.getMarca()});
            modeloCamiones.addRow(new Object[]{"Modelo", camionActual.getModelo()});
            modeloCamiones.addRow(new Object[]{"Estado", camionActual.getEstado()});
            modeloCamiones.addRow(new Object[]{"Tipo de Combustible", camionActual.getTipoCombustible()});
            modeloCamiones.addRow(new Object[]{"Kilometraje", camionActual.getKilometraje()});
            modeloCamiones.addRow(new Object[]{"Capacidad de Carga", camionActual.getCapacidadCarga()});
            modeloCamiones.addRow(new Object[]{"Año de Fabricación", camionActual.getAñoFabricacion()});
            modeloCamiones.addRow(new Object[]{"Tiempo en Reparación", camionActual.getTiempoEnReparacion()});
            modeloCamiones.addRow(new Object[]{"Fecha de Mantenimiento", camionActual.getFechaDeMantenimiento()});
            modeloCamiones.addRow(new Object[]{"Total Invertido", camionActual.getTotal()});
        }
    }

        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRegistroCamiones = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel14.setBackground(new java.awt.Color(32, 67, 99));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

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
        jScrollPane6.setViewportView(tblRegistroCamiones);

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        jLabel6.setText("INFORMACIÓN DEL CAMIÓN");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOSTRARGESTIONCAMIONES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Crea y muestra la ventana */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new MOSTRARGESTIONCAMIONES().setVisible(true); // Crear una instancia de MOSTRARGESTIONCAMIONES y hacerla visible
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblRegistroCamiones;
    // End of variables declaration//GEN-END:variables
}
