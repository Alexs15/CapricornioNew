/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Map;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;

/**
 *
 * @author Henrry
 */
public class JD_DOCUMENTOS_INTEGRANTES extends javax.swing.JDialog {

    /**
     * Creates new form JD_CONTRATADO
     */
    private int cod_transaccion;
    public JD_DOCUMENTOS_INTEGRANTES(java.awt.Frame parent, boolean modal, int cod_transaccion) {
        super(parent, modal);
        initComponents();
        this.cod_transaccion = cod_transaccion;
        this.setLocationRelativeTo(null);
        CargaDocumentos_Integrantes(cod_transaccion);
    }
    private void CargaDocumentos_Integrantes(int cod_transaccion){
        try {
            
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            Map[] datos = puerto.getContratoDocumentosIntegrantes(cod_transaccion);
            if (datos != null) {
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("MM/dd/yyyy");
                System.err.println("Cargando..."+datos[0].get("NOTA_SOL_ADQ").toString());
                System.err.println("Cargando..."+formatoDeFecha.parse(datos[0].get("FECHA_HOJA_RUTA").toString()));
                
                this.JTF_NOTA_ADQ.setText(datos[0].get("NOTA_SOL_ADQ").toString());
                this.FECHA_NOTA_ADQ.setValue(formatoDeFecha.parse(datos[0].get("FECHA_NOTA_SOL_ADQ").toString()));
                
                this.JTF_CERT_PPTA.setText(datos[0].get("NRO_CERT_PPTA").toString());
                this.FECHA_CERT_PPTA.setValue(formatoDeFecha.parse(datos[0].get("FECHA_CERT_PPTA").toString()));
                
                this.JTF_RESOLUCION_INICIO.setText(datos[0].get("NRO_RES_INI").toString());
                this.FECHA_RESOLUCION_INICIO.setValue(formatoDeFecha.parse(datos[0].get("FECHA_RES_INI").toString()));
                
                this.JTF_INF_COM_CALF.setText(datos[0].get("NRO_INF_COM_CALF").toString());
                this.FECHA_INF_COM_CALF.setValue(formatoDeFecha.parse(datos[0].get("FECHA_INF_COM_CALF").toString()));
                
                this.JTF_RES_ADJ.setText(datos[0].get("NRO_RES_ADJ").toString());
                this.FECHA_RES_ADJ.setValue(formatoDeFecha.parse(datos[0].get("FECHA_RES_ADJ").toString()));
                
//                this.JTF_TPGA.setText(datos[0].get("NRO_TPGA").toString());
//                this.FECHA_TPGA.setValue(formatoDeFecha.parse(datos[0].get("FECHA_TPGA").toString()));
//                
//                this.JTF_NOTFEPUB.setText(datos[0].get("NRO_NOTFEPUB").toString());
//                this.JTF_DOC_NOTFEPUB.setText(datos[0].get("DOC_NOTFEPUB").toString());
                
                this.JTF_HOJA_RUTA.setText(datos[0].get("NRO_HOJA_RUTA").toString());
                this.FECHA_HOJA_RUTA.setValue(formatoDeFecha.parse(datos[0].get("FECHA_HOJA_RUTA").toString()));
                
//                this.JTF_BOLETA_GARANTIA.setText(datos[0].get("NRO_BOLETA_GARANTIA").toString());
//                this.FECHA_BOLETA_GARANTIA.setValue(formatoDeFecha.parse(datos[0].get("FECHA_BOLETA_GARANTIA").toString()));
//                this.JTF_EMIT_BOLETA_GARANTIA.setText(datos[0].get("EMIT_BOLETA_GARANTIA").toString());
            }
            else
                System.err.println("Vacio!!!");
        } catch (Exception e) {
            System.err.println("Error :O");          
        }
    }
    private void GuardaDocumentos_Integrantes(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            
            System.err.println("vamo a darle: "+formatoDeFecha.format(this.FECHA_NOTA_ADQ.getValue()));
            System.err.println("Vamooos: "+formatoDeFecha.format(this.FECHA_HOJA_RUTA.getValue()));
            
//            Map[] datos = puerto.updateContratoDocumentosIntegrantes(cod_transaccion
//                    ,this.JTF_NOTA_ADQ.getText(),formatoDeFecha.format(this.FECHA_NOTA_ADQ.getValue())
//                    ,this.JTF_CERT_PPTA.getText(),formatoDeFecha.format(this.FECHA_CERT_PPTA.getValue())
//                    ,this.JTF_RESOLUCION_INICIO.getText(),formatoDeFecha.format(this.FECHA_RESOLUCION_INICIO.getValue())
//                    ,this.JTF_INF_COM_CALF.getText(),formatoDeFecha.format(this.FECHA_INF_COM_CALF.getValue())
//                    ,this.JTF_RES_ADJ.getText(),formatoDeFecha.format(this.FECHA_RES_ADJ.getValue())
////                    ,this.JTF_TPGA.getText(),formatoDeFecha.format(this.FECHA_TPGA.getValue())
////                    ,this.JTF_NOTFEPUB.getText(),this.JTF_DOC_NOTFEPUB.getText()
//                    ,this.JTF_HOJA_RUTA.getText(),formatoDeFecha.format(this.FECHA_HOJA_RUTA.getValue())
////                    ,this.JTF_BOLETA_GARANTIA.getText(),formatoDeFecha.format(this.FECHA_BOLETA_GARANTIA.getValue())
////                    ,this.JTF_EMIT_BOLETA_GARANTIA.getText()
//                  
//            );
            
             javax.swing.JOptionPane.showMessageDialog(this, "Se actualizo el tramite de forma exitosa!!! ", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            
//            Map[] datos = puerto.updateContratoDocumentosIntegrantes(cod_transaccion
//                    ,this.JTF_NOTA_ADQ.getText(),"16/04/1991"
//                    ,this.JTF_CERT_PPTA.getText(),"13/04/1991"
//                    ,this.JTF_RESOLUCION_INICIO.getText(),"13/04/1991"
//                    ,this.JTF_INF_COM_CALF.getText(),"13/04/1991"
//                    ,this.JTF_RES_ADJ.getText(),"13/04/1991"
//                    ,this.JTF_TPGA.getText(),"13/04/1991"
//                    ,this.JTF_NOTFEPUB.getText(),this.JTF_DOC_NOTFEPUB.getText()
//                    ,this.JTF_HOJA_RUTA.getText(),"13/04/1991"
//                    ,this.JTF_BOLETA_GARANTIA.getText(),"13/04/1991"
//                    ,this.JTF_EMIT_BOLETA_GARANTIA.getText()
//                  
//            );
            
            
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error :O <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);       
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
        JD_GUARDAR = new javax.swing.JButton();
        JD_SALIR = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        JTF_NOTA_ADQ = new javax.swing.JTextField();
        FECHA_NOTA_ADQ = new net.sf.nachocalendar.components.DateField();
        JTF_CERT_PPTA = new javax.swing.JTextField();
        FECHA_CERT_PPTA = new net.sf.nachocalendar.components.DateField();
        JTF_RESOLUCION_INICIO = new javax.swing.JTextField();
        FECHA_RESOLUCION_INICIO = new net.sf.nachocalendar.components.DateField();
        JTF_INF_COM_CALF = new javax.swing.JTextField();
        FECHA_INF_COM_CALF = new net.sf.nachocalendar.components.DateField();
        JTF_RES_ADJ = new javax.swing.JTextField();
        FECHA_RES_ADJ = new net.sf.nachocalendar.components.DateField();
        JTF_HOJA_RUTA = new javax.swing.JTextField();
        FECHA_HOJA_RUTA = new net.sf.nachocalendar.components.DateField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        PnlDocumentos = new javax.swing.JScrollPane();
        JTbl_VPreventivo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DOCUMENTOS INTEGRANTES");

        JD_GUARDAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/save_16.png"))); // NOI18N
        JD_GUARDAR.setText("Guardar");
        JD_GUARDAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JD_GUARDARActionPerformed(evt);
            }
        });

        JD_SALIR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        JD_SALIR.setText("SALIR");
        JD_SALIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JD_SALIRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(JD_GUARDAR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JD_SALIR)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JD_GUARDAR)
                    .addComponent(JD_SALIR))
                .addGap(16, 16, 16))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        JTF_NOTA_ADQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_NOTA_ADQActionPerformed(evt);
            }
        });

        FECHA_NOTA_ADQ.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_NOTA_ADQ.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_NOTA_ADQ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_NOTA_ADQKeyPressed(evt);
            }
        });

        JTF_CERT_PPTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_CERT_PPTAActionPerformed(evt);
            }
        });

        FECHA_CERT_PPTA.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_CERT_PPTA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_CERT_PPTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_CERT_PPTAKeyPressed(evt);
            }
        });

        JTF_RESOLUCION_INICIO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_RESOLUCION_INICIOActionPerformed(evt);
            }
        });

        FECHA_RESOLUCION_INICIO.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_RESOLUCION_INICIO.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_RESOLUCION_INICIO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_RESOLUCION_INICIOKeyPressed(evt);
            }
        });

        JTF_INF_COM_CALF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_INF_COM_CALFActionPerformed(evt);
            }
        });

        FECHA_INF_COM_CALF.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_INF_COM_CALF.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_INF_COM_CALF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_INF_COM_CALFKeyPressed(evt);
            }
        });

        JTF_RES_ADJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_RES_ADJActionPerformed(evt);
            }
        });

        FECHA_RES_ADJ.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_RES_ADJ.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_RES_ADJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_RES_ADJKeyPressed(evt);
            }
        });

        JTF_HOJA_RUTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_HOJA_RUTAActionPerformed(evt);
            }
        });

        FECHA_HOJA_RUTA.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_HOJA_RUTA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_HOJA_RUTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_HOJA_RUTAKeyPressed(evt);
            }
        });

        jCheckBox1.setText("No. NOTA ADQUISICIONES ");

        jCheckBox2.setText("No. CERT.PPTA.:");

        jCheckBox3.setText("No. RESOLUCION INICIO:");

        jCheckBox4.setText("No. INF.COM.CALIFICACION:");

        jCheckBox5.setText("No. RESOLUCION ADJUDICACION:");

        jCheckBox6.setText("No. HOJA DE RUTA:");

        jButton1.setText("OTRO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox4))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JTF_HOJA_RUTA)
                    .addComponent(JTF_INF_COM_CALF)
                    .addComponent(JTF_NOTA_ADQ)
                    .addComponent(JTF_CERT_PPTA)
                    .addComponent(JTF_RESOLUCION_INICIO)
                    .addComponent(JTF_RES_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FECHA_CERT_PPTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FECHA_NOTA_ADQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FECHA_RESOLUCION_INICIO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FECHA_INF_COM_CALF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FECHA_RES_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FECHA_HOJA_RUTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(185, 185, 185))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FECHA_NOTA_ADQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox1)
                        .addComponent(JTF_NOTA_ADQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox2)
                        .addComponent(JTF_CERT_PPTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_CERT_PPTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox3)
                        .addComponent(JTF_RESOLUCION_INICIO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_RESOLUCION_INICIO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox4)
                        .addComponent(JTF_INF_COM_CALF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_INF_COM_CALF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox5)
                        .addComponent(JTF_RES_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_RES_ADJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox6)
                        .addComponent(JTF_HOJA_RUTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_HOJA_RUTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        PnlDocumentos.setViewportView(JTbl_VPreventivo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(PnlDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(PnlDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTF_NOTA_ADQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_NOTA_ADQActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_NOTA_ADQActionPerformed

    private void JTF_CERT_PPTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_CERT_PPTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_CERT_PPTAActionPerformed

    private void JTF_RESOLUCION_INICIOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_RESOLUCION_INICIOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_RESOLUCION_INICIOActionPerformed

    private void JTF_INF_COM_CALFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_INF_COM_CALFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_INF_COM_CALFActionPerformed

    private void JTF_RES_ADJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_RES_ADJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_RES_ADJActionPerformed

    private void JTF_HOJA_RUTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_HOJA_RUTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTF_HOJA_RUTAActionPerformed

    private void FECHA_NOTA_ADQKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_NOTA_ADQKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_NOTA_ADQKeyPressed

    private void FECHA_CERT_PPTAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_CERT_PPTAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_CERT_PPTAKeyPressed

    private void FECHA_RESOLUCION_INICIOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_RESOLUCION_INICIOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_RESOLUCION_INICIOKeyPressed

    private void FECHA_INF_COM_CALFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_INF_COM_CALFKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_INF_COM_CALFKeyPressed

    private void FECHA_RES_ADJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_RES_ADJKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_RES_ADJKeyPressed

    private void FECHA_HOJA_RUTAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_HOJA_RUTAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_HOJA_RUTAKeyPressed

    private void JD_GUARDARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JD_GUARDARActionPerformed
        // TODO add your handling code here:
        
        GuardaDocumentos_Integrantes(this.cod_transaccion);
    }//GEN-LAST:event_JD_GUARDARActionPerformed

    private void JD_SALIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JD_SALIRActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_JD_SALIRActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JD_ADD_DOC_CONTRATO jadc = new JD_ADD_DOC_CONTRATO(null,true);
        jadc.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_CONTRATADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_DOCUMENTOS_INTEGRANTES dialog = new JD_DOCUMENTOS_INTEGRANTES(new javax.swing.JFrame(), true,0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.sf.nachocalendar.components.DateField FECHA_CERT_PPTA;
    private net.sf.nachocalendar.components.DateField FECHA_HOJA_RUTA;
    private net.sf.nachocalendar.components.DateField FECHA_INF_COM_CALF;
    private net.sf.nachocalendar.components.DateField FECHA_NOTA_ADQ;
    private net.sf.nachocalendar.components.DateField FECHA_RESOLUCION_INICIO;
    private net.sf.nachocalendar.components.DateField FECHA_RES_ADJ;
    private javax.swing.JButton JD_GUARDAR;
    private javax.swing.JButton JD_SALIR;
    private javax.swing.JTextField JTF_CERT_PPTA;
    private javax.swing.JTextField JTF_HOJA_RUTA;
    private javax.swing.JTextField JTF_INF_COM_CALF;
    private javax.swing.JTextField JTF_NOTA_ADQ;
    private javax.swing.JTextField JTF_RESOLUCION_INICIO;
    private javax.swing.JTextField JTF_RES_ADJ;
    private javax.swing.JTable JTbl_VPreventivo;
    private javax.swing.JScrollPane PnlDocumentos;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
