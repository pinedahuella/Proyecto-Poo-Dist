/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ControlCliente;

/**
 *
 * @author USUARIO
 */

import GestionDeCamiones.CAMIONESINACTIVOS;
import Login.GESTIONLOGIN;
import Login.LOGINPINEED;
import Login.Login;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

//definimos las librerias de tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrameHistorialClientes extends javax.swing.JFrame {

    /**
     * Creates new form FrameHistorialClientes
     */
    
    //creamos la gestion 
    private GestionClientes gesclientes;
    
    //creamos vectores de gestion para clientes
    private Vector<Cliente> vectorclientes = new Vector<>();
    
    //crearemos los modelos de las tablas necesarias
    DefaultTableModel modeloClientes = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
    
        private String currentUser;
    private String userRole;
    private LOGINPINEED loginFrame;
    
    
    public FrameHistorialClientes(String username, String role, LOGINPINEED loginFrame) {
        initComponents();
          this.currentUser = username;
        this.userRole = role;
        this.loginFrame = loginFrame;
                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false); // Desactivar el cambio de tamaño
    configurarListenerCierre();

        
        //inciamos las clases
        gesclientes = new GestionClientes();
        
        //cargamos de excel
        gesclientes.cargarExcelCliente();
        
        //igualamos el vector de clientes al que posee gestion clientes
        vectorclientes = gesclientes.getClientes();
        
        String ids [] = {"Cliente", "Activo"};
        modeloClientes.setColumnIdentifiers(ids);
        tablaclientes1.setModel(modeloClientes);
        
        //creamos una variable de si el cliente esta activo o no
        boolean activoCliente = true;
        //creamos una variable que tendra la cantidad del vector de creditos
        int logitudCreditos = 0;
        
        for (int i = 0; i < vectorclientes.size(); i++) {
            activoCliente = true;
            
            //leemos la logitus del vector credito
            logitudCreditos = vectorclientes.get(i).getIndiceCredito().size();
            
            if (logitudCreditos > 0) {
                if (vectorclientes.get(i).getIndiceCredito().get(logitudCreditos-1) == -100) {
                    activoCliente = false;
                }
            }else{
                activoCliente = true;
            }
            
            if (activoCliente == true) {
                modeloClientes.addRow(new Object[]{vectorclientes.get(i).getNombre(), "Activo"});

            }else{
                modeloClientes.addRow(new Object[]{vectorclientes.get(i).getNombre(), "Desactivo"});
            }
        }
    }
    
    //funcion que nos ayudara a actualizar la tabla
    public void voidactualizartabla(){
        //limpiamos la tabla
        modeloClientes.setRowCount(0);
        
        //creamos una variable de si el cliente esta activo o no
        boolean activoCliente = true;
        //creamos una variable que tendra la cantidad del vector de creditos
        int logitudCreditos = 0;
        
        for (int i = 0; i < vectorclientes.size(); i++) {
            activoCliente = true;
            
            //leemos la logitus del vector credito
            logitudCreditos = vectorclientes.get(i).getIndiceCredito().size();
            
            if (logitudCreditos > 0) {
                if (vectorclientes.get(i).getIndiceCredito().get(logitudCreditos-1) == -100) {
                    activoCliente = false;
                }
            }else{
                activoCliente = true;
            }
            
            if (activoCliente == true) {
                modeloClientes.addRow(new Object[]{vectorclientes.get(i).getNombre(), "Activo"});

            }else{
                modeloClientes.addRow(new Object[]{vectorclientes.get(i).getNombre(), "Desactivo"});
            }
        }
    }

     
public void addWindowListener() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Show message about changes being reflected
                JOptionPane.showMessageDialog(null, 
                    "Los cambios realizados se verán reflejados al cerrar el historial de clientes eliminados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                cerrarYActualizarFrame();
            }
        });
    }
     
private void configurarListenerCierre() {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            cerrarYActualizarFrame();
        }
    });
}

// Función para actualizar la tabla de clientes
public void actualizarTablaClientes() {
    modeloClientes.setRowCount(0);
    boolean activoCliente;
    int longitudCreditos;

    for (Cliente cliente : vectorclientes) {
        activoCliente = true;
        longitudCreditos = cliente.getIndiceCredito().size();

        if (longitudCreditos > 0 && cliente.getIndiceCredito().get(longitudCreditos - 1) == -100) {
            activoCliente = false;
        }

        String estadoCliente = activoCliente ? "Activo" : "Desactivo";
        modeloClientes.addRow(new Object[]{cliente.getNombre(), estadoCliente});
    }
}

// Cerrar y actualizar el frame de clientes
private void cerrarYActualizarFrame() {
    cerrarSesionManualmente();
    this.dispose();

    // Crear y abrir una nueva instancia de FrameClientes
    FrameClientes nuevoFrame = new FrameClientes(currentUser, userRole, loginFrame);
    nuevoFrame.actualizaTablaCliente();
    nuevoFrame.setVisible(true);
}

// Otros métodos de cierre de sesión
private void cerrarSesionManualmente() {
    LocalDateTime tiempoSalida = LocalDateTime.now();
    GESTIONLOGIN gestionLogin = new GESTIONLOGIN();
    gestionLogin.cargarLoginsDesdeExcel();
    
    boolean sesionCerrada = false;
    for (Login login : gestionLogin.getLogins()) {
        if (login.getPersonal().equals(currentUser) && login.getTiempoSalida().isEmpty()) {
            login.setTiempoSalida(tiempoSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            gestionLogin.actualizarLogin(login);
            sesionCerrada = true;
            System.out.println("Sesión cerrada para el usuario: " + currentUser);
            break;
        }
    }
    
    if (!sesionCerrada) {
        System.out.println("No se encontró una sesión abierta para cerrar para el usuario: " + currentUser);
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabla1 = new javax.swing.JScrollPane();
        tablaclientes1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(32, 67, 99));

        jLabel1.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Historial de Clientes");

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ACTIVAR CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tablaclientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla1.setViewportView(tablaclientes1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabla1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabla1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        
        //funcion para activar un trabajador
            
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

                // Si el usuario selecciona "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    //preguntamos si el indice seleccionado de la tabla es legal

            int indiceActual = tablaclientes1.getSelectedRow();
                    
            if (indiceActual > -1) {
                
                //creamos una variable de si el cliente esta activo o no
                boolean activoCliente = true;
                //creamos una variable que tendra la cantidad del vector de creditos
                int logitudCreditos = vectorclientes.get(indiceActual).getIndiceCredito().size();

                if (logitudCreditos > 0) {
                        if (vectorclientes.get(indiceActual).getIndiceCredito().get(logitudCreditos-1) == -100) {
                            activoCliente = false;
                        }
                    }
        
                if (activoCliente == false) {
                    gesclientes.getClientes().get(indiceActual).getIndiceCredito().remove(gesclientes.getClientes().get(indiceActual).getIndiceCredito().size()-1);
                
                //mostramos mesaje 
    JOptionPane.showMessageDialog(null, "Los cambios realizados se verán reflejados al cerrar el historial de clientes eliminados.", "Información", JOptionPane.INFORMATION_MESSAGE);

                //funcion provisional para guardas datos en el excel
                 gesclientes.guardarExcelCliente();
                 
                 //actualizamos la tabla
                 voidactualizartabla();
        
                }else{
                   //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "El Cliente ya esta activo", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
         
                }
                

                
                }else{
                //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa un Trabajador de la Tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        
            }
                }
    }//GEN-LAST:event_jPanel3MouseClicked

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
            java.util.logging.Logger.getLogger(FrameHistorialClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameHistorialClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameHistorialClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameHistorialClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                                
                                String username = "defaultUser";  // Reemplaza con el nombre de usuario real o lógica
                String role = "defaultRole"; 

                LOGINPINEED loginFrame = new LOGINPINEED();  // Instancia el objeto LOGINPINEED

                // Crea la instancia de INICIOGESTIONCAMIONES con los parámetros requeridos
                new FrameHistorialClientes(username, role, loginFrame).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane tabla1;
    private javax.swing.JTable tablaclientes1;
    // End of variables declaration//GEN-END:variables
}
