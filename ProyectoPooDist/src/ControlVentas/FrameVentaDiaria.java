/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ControlVentas;

/**
 *
 * @author USUARIO
 */
import ControlInventario.*;

//importamos de clientes aun falta
import ControlCliente.*;

//definimos las librerias de tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//clases para calendario
import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Vector;

public class FrameVentaDiaria extends javax.swing.JFrame {

    
    //creamos la gestiones pertinentes
    private gestionProductos gesproductos;
    private GestionClientes gesclientes;
    private gestionVentas gesventas;
    private gestionVentas geshistorial;
    private gestionVentas gescreditos;
    
    
    //cremos los vectores necesarios
    public Vector<Producto> vectorproductos = new Vector<>();
    public Vector<Venta> vectorventas = new Vector<>();
    
    //crearemos los modelos de las tablas de ventas 
    DefaultTableModel modeloventas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };

    //indice que nos ayudara a controlar la tabla
    int indiceGeneral;
    
    /**
     * Creates new form FrameVentaDiaria
     */
    public FrameVentaDiaria() {
        initComponents();
        
        //inidiciamos el indice general en -1
        indiceGeneral = -1;
        
        //inciamos las clases
        gesproductos = new gestionProductos();
        gesclientes = new GestionClientes();
        gesventas = new gestionVentas();
        geshistorial = new gestionVentas("excels/historial.xlsx");
        gescreditos = new  gestionVentas("excels/creditos.xlsx");
        
        //llenamos las clases necesarias del excel
        gesproductos.setCargarInvetarioExcel();
        gesclientes.cargarExcelCliente();
        gesventas.cargarExcelVentas();
        gescreditos.cargarExcelCreditos();
        
        //llenamos los vectores necesarios
        vectorproductos = gesproductos.getProductos();
        vectorventas = gesventas.getVentas();
        
        //unicamnete de pruebas
        //comboClienteA.addItem("Clientes Varios");
        //comboClienteB.addItem("Clientes Varios");
        
        //llenamos los combo box de producots y clientes
        actualizarComboxProductos();
        actualizarComboxClientes();
        
        //tabla ventas creamos la tabla
        String ids [] = {"no.", "producto", "cantidad", "precio", "ganancia", "tipo"};
        modeloventas.setColumnIdentifiers(ids);
        tablaVentasA.setModel(modeloventas);
        
        //actualizamo la tabla
        actualizarTablaVentas();
        
        //calculamos la ganancias total
        ActualizarGananciaTotal();
        
        //iniciamos el bucle infinito
       iniciarBucleEnHilo(); 
        
    }
    
    //funcion para actulizar el combo de ventas
    public void actualizarComboxProductos(){
        //removemos todos los elementos del combo box
        comboProductosA.removeAllItems();
        
        //recorremos el vector de procdutos para llenar el combo box
        for (int i = 0; i < vectorproductos.size(); i++) {
            comboProductosA.addItem(vectorproductos.get(i).getNombre());
        }
    }
    
    //funcion para actualizar el combo de clientes
    public void actualizarComboxClientes(){
        //primero vaciamos los combo box
        comboClienteA.removeAllItems();
        comboClienteB.removeAllItems();
        //contador para el combo box
        int numerocontado = 1;
        //ahora creamos un for que recorrera todos los clientes para añadirlos a los combo box
        for (int i = 0; i < gesclientes.getClientes().size(); i++) {
            
            comboClienteA.addItem(numerocontado +". " +gesclientes.getClientes().get(i).getNombre());
            comboClienteB.addItem(numerocontado +". " +gesclientes.getClientes().get(i).getNombre());
            numerocontado++;
        }
    }
    
    //funcion para actulizar tabla de ventas
    public void actualizarTablaVentas(){
       //dejamos la tabla en cero
        modeloventas.setRowCount(0);
        
        //cremos el indice de la venta y si es una venta o no
        int suma = 1;
        String siesventa = "venta";
        
        //recoremos el vector de ventas para agregar los elementos a la venta
        for (int i = 0; i < vectorventas.size(); i++) {
            
            if (vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == true) {
                siesventa = "credito";
            }else if(vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == false){
                siesventa = "credito / pagado";
            }else if(vectorventas.get(i).getCredito() == false){
                siesventa = "venta";
            }
            
            modeloventas.addRow(new Object[]{suma, vectorproductos.get(vectorventas.get(i).getIndiceProducto()).getNombre(), vectorventas.get(i).getIndiceCantidad(), vectorventas.get(i).getPrecio(), vectorventas.get(i).getGanancia(), siesventa});
            suma += 1;
        }
    }
    
    //creamos una funcion para calcular la ganancia del dia
    public void ActualizarGananciaTotal(){
        labelGanaciaGeneral.setText("Q " + gesventas.calcularGananciaGeneral());
        labelVendido.setText("Q " + gesventas.calcularVendidoGeneral());
    };
            
    
    
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
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        radioCredito = new javax.swing.JRadioButton();
        comboProductosA = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinCantidadA = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        textoPrecioA = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textoFleteA = new javax.swing.JTextField();
        labelpreciocosto = new javax.swing.JLabel();
        textoCostoA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboClienteA = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        comboClienteB = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        textoPrecioB = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        spinCantidadB = new javax.swing.JSpinner();
        textoFleteB = new javax.swing.JTextField();
        textoCostoB = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        labelGanaciaVenta = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        labelProductosB = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentasA = new javax.swing.JTable();
        labelGanaciaGeneral = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        labelVendido = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(18, 59, 87));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(51, 224, 218));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Agregar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        radioCredito.setText("Es un credito");
        radioCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCreditoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Cantidad:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Producto:");

        spinCantidadA.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Precio de venta:");

        jPanel6.setBackground(new java.awt.Color(51, 224, 218));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Presiona para Cargar Producto");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Agregar Venta/Credito");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Flete:");

        labelpreciocosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelpreciocosto.setText("Precio Costo:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Cliente:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radioCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboProductosA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinCantidadA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textoPrecioA, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(labelpreciocosto, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(textoFleteA, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textoCostoA, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboClienteA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(112, Short.MAX_VALUE))))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboProductosA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboClienteA, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(labelpreciocosto))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(radioCredito))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinCantidadA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoPrecioA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoFleteA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoCostoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Producto:");

        jPanel8.setBackground(new java.awt.Color(51, 224, 218));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Modificiar Venta");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Precio de venta:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Flete:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Precio Costo:");

        labelGanaciaVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelGanaciaVenta.setText("Q 00.00");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Ganancia de la venta:");

        jPanel9.setBackground(new java.awt.Color(51, 224, 218));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Modificar");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        labelProductosB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelProductosB.setText("N/A");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Cliente:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinCantidadB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoPrecioB, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(textoFleteB, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textoCostoB, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelGanaciaVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 154, Short.MAX_VALUE))
                            .addComponent(labelProductosB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128))
                            .addComponent(comboClienteB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelProductosB))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClienteB, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinCantidadB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPrecioB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoFleteB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoCostoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGanaciaVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel10.setBackground(new java.awt.Color(51, 224, 218));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Nuevo Día de Venta");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tablaVentasA.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaVentasA);

        labelGanaciaGeneral.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelGanaciaGeneral.setText("Q 00.00");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Ganancia del día");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Vendido del día");

        labelVendido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelVendido.setText("Q 00.00");

        jPanel3.setBackground(new java.awt.Color(51, 224, 218));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Ver historial de ventas:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGanaciaGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel22)
                .addGap(5, 5, 5)
                .addComponent(labelVendido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGanaciaGeneral)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1126, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioCreditoActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        
        //funcion para agregar 
        try {
           
            //leemos los indices del inventario y del cliente
            int newindiceproducto = comboProductosA.getSelectedIndex();
            int newindicecliente =  comboClienteA.getSelectedIndex();
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
                //leemos la cantidad del spin
                int newcantidad = (int) spinCantidadA.getValue();
                
                //leemos los precios
                float newprecio = Float.parseFloat(textoPrecioA.getText());
                float newflete = Float.parseFloat(textoFleteA.getText());
                float newcosto = Float.parseFloat(textoCostoA.getText());
                
                //vemos si es un credito o no
                boolean newcredito = radioCredito.isSelected();
                
                //preguntamos is hay suficientes existencias para completar el pedido
                if (vectorproductos.get(newindiceproducto).getExistencias() >= newcantidad) {
                   
                    //crearemos un if para indidicar que la ganancia no puede ser negativa
                    if (newprecio - (newflete + newcosto) >= 0) {
                      //cremos la nueva venta y la agregamos al vector
                Venta newventa = new Venta(newindiceproducto, newcantidad, newindicecliente, newprecio, newcosto, newflete, newcredito, true, 0);
                gesventas.addVentaVector(newventa);
                
                //preguntamos si es una credito para agregarlo al listado de creditos
                        if (newcredito == true) {
                            //guardamos en el vector y el excel
                            gescreditos.addCreditoVector(newventa);
                            gescreditos.guardarExcelCreditos();
                            
                            //cuardamos el credito en el cliente correspondiente
                            gesclientes.addCredito(newindicecliente, gescreditos.getCreditos().size()-1);
                            gesclientes.guardarExcelCliente();
                        }
                
                //calculamos la ganacia
                gesventas.calcularGanancia(vectorventas.size()-1);
                
                //guardamo la venta en gesventas;
                gesventas.guardarExcelVentas();
                
                //actualizamos la tabla de ventas
                actualizarTablaVentas(); 
                
                //cambiamos las existencias en el inventario
                gesproductos.setCantidad(newindiceproducto, newcantidad, "-");
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();
                
                //inciamos todos los valores a nulos
                textoPrecioA.setText("");
                textoFleteA.setText("");
                textoCostoA.setText("");
                
                comboProductosA.setSelectedIndex(0);
                comboClienteA.setSelectedIndex(0);
                
                radioCredito.setSelected(false);
                
                spinCantidadA.setValue(1);
                
                //llamamos a la funcion para calcular la ganancia general
                ActualizarGananciaTotal();
                
                //seleccionamos el indice actual de la tabla en la tabla
                        if (indiceGeneral > -1) {
                            tablaVentasA.setRowSelectionInterval(indiceGeneral, indiceGeneral);
                        }
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "venta realizada completamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                 
                    }else{
                        //monstra mensajje
                JOptionPane.showMessageDialog(null, "el costo y flete superan al precio", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                
                    }
               
                
                }else{
                    //monstra mensajje
                JOptionPane.showMessageDialog(null, "No hay suficientes existencias en inventario", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        
        //funcion para cargar los datos de la venta
        try {
           
            //leemos los indices del inventario y del cliente
            int newindiceproducto = comboProductosA.getSelectedIndex();
            int newindicecliente =  comboClienteA.getSelectedIndex();
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
               textoFleteA.setText("" + vectorproductos.get(newindiceproducto).getPrecioFlete());
               textoCostoA.setText("" + vectorproductos.get(newindiceproducto).getPrecioCosto());
               
               //esta variable nos ayudara a ver si el producto fue encontrado en la lista del cliente
               boolean tieneproducto = false;
               
               //cremos un for que recorrera los productos del cliente, y si encuentra similitud, entonces podra su precio especial
                for (int i = 0; i < gesclientes.getClientes().get(newindicecliente).getIndiceProducto().size(); i++) {
                    if (gesclientes.getClientes().get(newindicecliente).getIndiceProducto().get(i) == newindiceproducto) {
                        //colocamos su precio
                        textoPrecioA.setText("" + gesclientes.getClientes().get(newindicecliente).getIndiceCantidad().get(i));
                        //si se encontro lo ponemos true
                        tieneproducto = true;
                        //salimos
                        break;
                    }
                }
                
                if (tieneproducto == false) {
                    textoPrecioA.setText("");
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
        
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        
        //funcion para modificiar una venta
        try {
           
            if (indiceGeneral > -1) {
                 //leemos los indices del inventario y del cliente
            int newindiceproducto = vectorventas.get(indiceGeneral).getIndiceProducto();
            int newindicecliente =  comboClienteB.getSelectedIndex();
            
            //preguntamos si los indices son validos
            if (newindiceproducto > -1 && newindicecliente > -1) {
                
                //leemos la cantidad del spin
                int newcantidad = (int) spinCantidadB.getValue();
                
                //guardamos la cantidad antigua porque nos sera util, al sumar la cantidad y restarle la nueva
                int cantidadAntigua = vectorventas.get(indiceGeneral).getIndiceCantidad();
                
                //leemos los precios
                float newprecio = Float.parseFloat(textoPrecioB.getText());
                float newflete = Float.parseFloat(textoFleteB.getText());
                float newcosto = Float.parseFloat(textoCostoB.getText());
                
                //vemos si es un credito o no
                boolean newcredito = false;
                
                //preguntamos is hay suficientes existencias para completar el pedido
                if (vectorproductos.get(newindiceproducto).getExistencias() + cantidadAntigua >= newcantidad) {
                   
                    //crearemos un if para indidicar que la ganancia no puede ser negativa
                    if (newprecio - (newflete + newcosto) >= 0) {
                      //cremos la nueva venta y  editamos el vector
                Venta newventa = new Venta(newindiceproducto, newcantidad, newindicecliente, newprecio, newcosto, newflete, newcredito, true, 0);
                gesventas.modificarVenta(indiceGeneral, newventa);

                //calculamos la ganacia
                gesventas.calcularGanancia(indiceGeneral);
                
                //guardamo la venta en gesventas;
                gesventas.guardarExcelVentas();
                
                //actualizamos la tabla de ventas
                actualizarTablaVentas(); 
                
                //cambiamos las existencias en el inventario
                gesproductos.setCantidad(newindiceproducto, cantidadAntigua, "+");
                gesproductos.setCantidad(newindiceproducto, newcantidad, "-");
                //actualizamos el inventario
                gesproductos.getCargarInvetarioExcel();

                
                //llamamos a la funcion para calcular la ganancia general
                ActualizarGananciaTotal();
                
                //seleccionamos el indice actual de la tabla en la tabla
                        if (indiceGeneral > -1) {
                            tablaVentasA.setRowSelectionInterval(indiceGeneral, indiceGeneral);
                        }
                
                //mostramos mesaje para acetar que este bien
                JOptionPane.showMessageDialog(null, "venta modificada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                 
                    }else{
                        //monstra mensajje
                JOptionPane.showMessageDialog(null, "el costo y flete superan al precio", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                
                    }
               
                
                }else{
                    //monstra mensajje
                JOptionPane.showMessageDialog(null, "No hay suficientes existencias en inventario", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                }
              
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion producto o cliente Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            }else{
                //mostramos mesaje 
                JOptionPane.showMessageDialog(null, "Seleccion una venta de la tabla", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        } catch (NumberFormatException e) {
            //mostramos mesaje 
            JOptionPane.showMessageDialog(null, "Ingresa cantidad Valido", "Confirmación", JOptionPane.INFORMATION_MESSAGE);     
        }
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
        
        //funcion para finalizar el dia
        
        // Mostrar popup de advertencia
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar con la acción?", "Advertencia", JOptionPane.YES_NO_OPTION);

                // Si el usuario selecciona "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    

        // Obtener la fecha actual
        Date fechaActual = new Date();
        // Crear el formateador para la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");
        // Convertir la fecha a String
        String fechaFormateada = formatoFecha.format(fechaActual);
        
        
        String siesventa = "venta";    
        
        //cremos un vector des string que nos ayudara a guardar el historial
        Vector<String> his = new Vector<>();
        
        //creamos un for que obtendra toda la informacion del vector 
        
                    for (int i = 0; i < vectorventas.size(); i++) {

                String cliente = gesclientes.getClientes().get(vectorventas.get(i).getIndiceCliente()).getNombre();
                
                    if (vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == true) {
                    siesventa = "credito";
                }else if(vectorventas.get(i).getCredito() == true && vectorventas.get(i).getCreditoActivo() == false){
                    siesventa = "credito / pagado";
                }else if(vectorventas.get(i).getCredito() == false){
                    siesventa = "venta";
                }

                String producto = vectorproductos.get(vectorventas.get(i).getIndiceProducto()).getNombre();
                String cantidad = vectorventas.get(i).getIndiceCantidad() + " productos";
                String precio = "Q. " + vectorventas.get(i).getPrecio();
                double cantidadtotal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecio();
                String total = "Q. " + cantidadtotal;
                double fletetotal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecioFlete();
                String flete = "Q. " + fletetotal;
                double costototal = vectorventas.get(i).getIndiceCantidad() * vectorventas.get(i).getPrecioCosto();
                String costo = "Q. " + costototal;
                String ganancia = "Q. " + vectorventas.get(i).getGanancia();
                
                his.add(fechaFormateada);
                his.add(cliente);
                his.add(siesventa);
                his.add(producto);
                his.add(cantidad);
                his.add(precio);
                his.add(total);
                his.add(flete);
                his.add(costo);
                his.add(ganancia);
                
                
                    }
                    
                his.add(fechaFormateada);
                his.add("Resumen");
                his.add("total");
                his.add("del");
                his.add("dia");
                his.add("-");
                his.add("Q. " + gesventas.calcularVendidoGeneral());
                his.add("Q. " + gesventas.calcularFleteGeneral());
                his.add("Q. " + gesventas.calcularCostoGeneral());
                his.add("Q. " + gesventas.calcularGananciaGeneral());
                    
                    
                    //guardamos los datos en el historial
                    geshistorial.guardarExcelHistoria(his);
                    
        //vaciamos el vector de ventas            
        gesventas.getVentas().clear();
        gesventas.guardarExcelVentas();

        
        //dejamos la tabla en cero
        modeloventas.setRowCount(0);
        
        actualizarTablaVentas();
        ActualizarGananciaTotal();
                }
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        
        //funcion que nos ayuda a abrir el historial
        FrameHistorialVenta newframe = new FrameHistorialVenta();
        newframe.setVisible(true);
        
    }//GEN-LAST:event_jPanel3MouseClicked

    //creamos el bucle infinito
    private void iniciarBucleEnHilo() {
        //este es nuetro bucle infinito que nos ayudara a realizar acciones continuamente
        Thread hiloBucle = new Thread(() -> {
            
         while (true) {
             
            //leemos el indice actual que ha seleccionado la tabla 
            int indiceSeleccionado = tablaVentasA.getSelectedRow();
            
            //preguntamos si el indice seleccionado el diferente al actual
             if (indiceSeleccionado != indiceGeneral && indiceSeleccionado > -1) {
                
                 //igualamos los indices
                indiceGeneral = indiceSeleccionado;
                 
                //rellenamos las informacion correspondiente
                comboClienteB.setSelectedIndex(vectorventas.get(indiceGeneral).getIndiceCliente());               
                labelProductosB.setText(vectorproductos.get(vectorventas.get(indiceGeneral).getIndiceProducto()).getNombre());
                
                spinCantidadB.setValue(vectorventas.get(indiceGeneral).getIndiceCantidad());
                
                textoPrecioB.setText("" + vectorventas.get(indiceGeneral).getPrecio());
                textoFleteB.setText("" + vectorventas.get(indiceGeneral).getPrecioFlete());
                textoCostoB.setText("" + vectorventas.get(indiceGeneral).getPrecioCosto());
                
                labelGanaciaVenta.setText("" + vectorventas.get(indiceGeneral).getGanancia());
                
                //reguntamos si es un credito para no mostrar el boton de modificar
                 if (vectorventas.get(indiceGeneral).getCredito() == true || vectorventas.get(indiceGeneral).getCreditoActivo() == false) {
                     jPanel9.setVisible(false);
                 }else{
                     jPanel9.setVisible(true);
                 }
             }
            
            //limita los recursos del bucle
            try {
                    // Pausa el hilo por 100ms para reducir el consumo de recursos
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                 e.printStackTrace();
                }
            }
        });

        // Iniciar el hilo
        hiloBucle.start();

    }
    
    
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
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVentaDiaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVentaDiaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboClienteA;
    private javax.swing.JComboBox<String> comboClienteB;
    private javax.swing.JComboBox<String> comboProductosA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelGanaciaGeneral;
    private javax.swing.JLabel labelGanaciaVenta;
    private javax.swing.JLabel labelProductosB;
    private javax.swing.JLabel labelVendido;
    private javax.swing.JLabel labelpreciocosto;
    private javax.swing.JRadioButton radioCredito;
    private javax.swing.JSpinner spinCantidadA;
    private javax.swing.JSpinner spinCantidadB;
    private javax.swing.JTable tablaVentasA;
    private javax.swing.JTextField textoCostoA;
    private javax.swing.JTextField textoCostoB;
    private javax.swing.JTextField textoFleteA;
    private javax.swing.JTextField textoFleteB;
    private javax.swing.JTextField textoPrecioA;
    private javax.swing.JTextField textoPrecioB;
    // End of variables declaration//GEN-END:variables
}
