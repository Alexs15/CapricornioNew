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
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;

/**
 *
 * @author Usuario
 */
public class JD_GeneraContrato2 extends javax.swing.JDialog {

    /**
     * Creates new form JD_GeneraContrato2
     */
    double monto;
    int cod_transaccion,cod_trans_nro;
    String cuantia;
    int cod_w;
    
    GetResAdj genera_res_15 = new GetResAdj();
    public JD_GeneraContrato2(java.awt.Frame parent, boolean modal, int cod_transaccion,String cuantia, int cod_w,int cod_trans_nro) {
        super(parent, modal);
        initComponents();
         this.setLocationRelativeTo(null);
//         this.cod_transaccion = cod_transaccion;
         System.err.println("el cod_transaccion es: "+cod_transaccion);
         System.err.println("la cuantia es: "+cuantia);
         System.err.println("el cod_w es: "+cod_w);
//         this.cod_transaccion = 2913;
         this.cod_transaccion = cod_transaccion;
         this.cod_trans_nro=cod_trans_nro;
         this.cod_w = cod_w;
         this.cuantia = cuantia;
        this.inicia(this.cod_transaccion);
        JalaDatos(this.cod_transaccion,this.cod_trans_nro);
        bloquea();
        this.JL_TITULO.setText("AJA!!!");
        setTitulo();
    }
    private void setTitulo(){
        System.err.println("hola palusa"+cod_w);
        switch (cod_w) {
            
            case 1: 
                this.JL_TITULO.setText("BIENES - "+this.cuantia);
                break;
            case 2: 
                this.JL_TITULO.setText("PEDIDO MATERIALES - "+this.cuantia);
                break;
            case 3: 
                this.JL_TITULO.setText("CONSULTORIAS - "+this.cuantia);
                break;
            case 4: 
                this.JL_TITULO.setText("OBRAS - "+this.cuantia);
                break;
            case 5: 
                this.JL_TITULO.setText("COMPRAS MAYORES - "+this.cuantia);
                break;
            case 6: 
                this.JL_TITULO.setText("SERVICIOS - "+this.cuantia);
                break;
            default: 
                System.err.println("a caray");
                break;
                  
        }
    }
    private void bloquea(){
        if(cod_w!=3){
            //this.JC_TipoConsult.setVisible(false);
            this.JC_TipoConsult.setEnabled(false);
        }
//        if(cod_w!=1){
//            this.JC_FunGarRet.setVisible(false);
//        }
        if(cod_w!=6){
            //this.JC_TipoServ.setVisible(false);
            this.JC_TipoServ.setEditable(false);
        }
        
        
        if(cuantia.equals("COMPRA MENOR")){
            this.JTF_CUCE.setEditable(false);
            this.JTF_Convocatoria.setEditable(false);
            this.FECHA_CONVOCATORIA.setEnabled(false);
        }
         
    }
    
    private void inicia(int cod_transaccion){
        try {
            
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println("pasa 1 "+cod_transaccion);
            Map[] datos = puerto.getContratoInicio(cod_transaccion);
            System.out.println("pasa 2");
            if (datos != null) {
                System.err.println("Con Datos");
//                this.JTF_RazonSocialContratante.setText(datos[0].get("RAZON_SOCIAL_CONTRATANTE").toString());
//                this.JTF_NitContratante.setText(datos[0].get("NIT_CONTRATANTE").toString());
//                this.JTF_DireccionContratante.setText(datos[0].get("DIRECCION_CONTRATANTE").toString());
//                this.JTF_CiudadContratante.setText(datos[0].get("CIUDAD_CONTRATANTE").toString());
//                this.JTF_RPA_RPC.setText(datos[0].get("REP_LEG_CONTRATANTE").toString());
//                this.JTF_CI_RPA_RPC.setText(datos[0].get("CI_REP_LEG_CONTRATANTE").toString());
//                this.JTF_CARGO_RPC_RPA.setText(datos[0].get("CARGO_REP_LEG_CONTRATANTE").toString());
//                this.JTF_ProponenteAdjudicado.setText(datos[0].get("PROPONENTE_ADJUDICADO").toString());
//                this.JTF_CI_ProponenteAdjudicado.setText(datos[0].get("CI_PROPONENTE_ADJUDICADO").toString());
//                this.JTF_Direccion_ProponenteAdjudicado.setText(datos[0].get("DIR_PROPONENTE_ADJUDICADO").toString());
//                this.JTF_Denominacion_ProponenteAdjudicado.setText(datos[0].get("DENO_PROPONENTE_ADJUDICADO").toString());
//                this.JTF_ObjetoContratacion.setText(datos[0].get("OBJETO_CONTRATACION").toString());
                //this.JTF_Convocatoria.setText(datos[0].get("CONVOCATORIA_ADC_ANPE").toString());
                //System.out.println("La Fecha es:----- "+datos[0].get("FECHA_CONTRATO").toString());
//                monto = Double.parseDouble(datos[0].get("TOTAL").toString());
                /*System.err.println("---- monto: "+datos[0].get("FECHA_CONV_ANPE").toString());
                this.FECHA_CONVOCATORIA.setValue(formatoDeFecha.parse(datos[0].get("FECHA_CONV_ANPE").toString()));
                System.err.println("---- FECHA_CONV_ANPE: "+datos[0].get("FECHA_CONV_ANPE").toString());
                this.FECHA_CONTRATO.setValue(formatoDeFecha.parse(datos[0].get("FECHA_CONTRATO").toString()));*/
                System.err.println("prueba 1");
                this.JTF_Convocatoria.setText(datos[0].get("CONVOCATORIA_ADC_ANPE").toString());
                System.err.println("prueba 2");
                this.JTF_ObjetoContratacion.setText(datos[0].get("OBJETO_CONTRATACION").toString());
                System.err.println("prueba 3");
                this.JTF_CUCE.setText(datos[0].get("CUCE_CONTRATACION").toString());
                System.err.println("prueba 4");
                this.JTF_NRO.setText(datos[0].get("NRO_CONTRATO").toString());
                System.err.println("prueba 5");
                this.JTF_DESTINO.setText(datos[0].get("DESTINO_CONTRATACION").toString());
                System.err.println("prueba 6");
                this.JTF_AsesorJuridico.setText(datos[0].get("ASESOR_JURIDICO").toString());
                this.JTF_LUGAR_PRES_ENT.setText(datos[0].get("LUGAR_SERVICIO_ENTREGA").toString());
                System.err.println("prueba 7");
                System.err.println("Vamos Loco!!!SW_GARANTIA_RETENCION "+Integer.valueOf(datos[0].get("SW_GARANTIA_RETENCION").toString()));
                
//                this.JC_GarRet.setSelectedIndex(Integer.valueOf(datos[0].get("SW_GARANTIA_RETENCION").toString()));
                
                System.err.println("Vamos Loco!!!SW_GARAN_RETEN_FUNC "+Integer.valueOf(datos[0].get("SW_GARAN_RETEN_FUNC").toString()));
                
//                this.JC_FunGarRet.setSelectedIndex(Integer.valueOf(datos[0].get("SW_GARAN_RETEN_FUNC").toString()));
                
                System.err.println("Vamos Loco!!!SW_ANTICIPO "+Integer.valueOf(datos[0].get("SW_ANTICIPO").toString()));
                
                this.JC_Anticipo.setSelectedIndex(Integer.valueOf(datos[0].get("SW_ANTICIPO").toString()));
                
                System.err.println("Vamos Loco!!!SW_TIPO_CONSULTORIA "+Integer.valueOf(datos[0].get("SW_TIPO_CONSULTORIA").toString()));
                
                this.JC_TipoConsult.setSelectedIndex(Integer.valueOf(datos[0].get("SW_TIPO_CONSULTORIA").toString()));
                
                System.err.println("Vamos Loco!!!SW_TIPO_SERVICIO "+Integer.valueOf(datos[0].get("SW_TIPO_SERVICIO").toString()));
                
                this.JC_TipoServ.setSelectedIndex(Integer.valueOf(datos[0].get("SW_TIPO_SERVICIO").toString()));
                
                
                
    //            this.JTF_RazonSocialContratante.setText(datos[0].get("B").toString());
            }
            else
                System.err.println("Vacio!!!");
            
            
            
        } catch (Exception e) {
            System.err.println("Error :O "+e);
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
        jLabel13 = new javax.swing.JLabel();
        JTF_AsesorJuridico = new javax.swing.JTextField();
        JC_Anticipo = new javax.swing.JComboBox();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        JTF_CUCE = new javax.swing.JTextField();
        JL_TITULO = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        JC_TipoConsult = new javax.swing.JComboBox();
        JC_TipoServ = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        JTF_ObjetoContratacion = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        JTF_Convocatoria = new javax.swing.JTextField();
        FECHA_CONVOCATORIA = new net.sf.nachocalendar.components.DateField();
        jLabel16 = new javax.swing.JLabel();
        FECHA_CONTRATO = new net.sf.nachocalendar.components.DateField();
        jLabel2 = new javax.swing.JLabel();
        JTF_NRO = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        JTF_DESTINO = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        JTF_LUGAR_PRES_ENT = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jCheckBox9 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        JB_Guardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Nombre Asesor Juridico:");

        JTF_AsesorJuridico.setPreferredSize(new java.awt.Dimension(250, 28));

        JC_Anticipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja una Opcion de Anticipo", "Con Anticipo", "Sin Anticipo" }));

        jButton4.setText("DATOS CONTRATANTE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("DATOS CONTRATADO");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        JL_TITULO.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        JL_TITULO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JL_TITULO.setText("TIPO DE CONTRATO");
        JL_TITULO.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton6.setText("DOCUMENTOS INTEGRANTES");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        JC_TipoConsult.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja un tipo de Consultoria", "De Linea", "Por Producto" }));

        JC_TipoServ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja un tipo de Servicio", "Servicios 26990", "Servicios Generales" }));

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Objeto Contratación:");

        JTF_ObjetoContratacion.setPreferredSize(new java.awt.Dimension(250, 28));

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Convocatoria:");

        JTF_Convocatoria.setPreferredSize(new java.awt.Dimension(250, 28));

        FECHA_CONVOCATORIA.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_CONVOCATORIA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_CONVOCATORIA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_CONVOCATORIAKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Fecha Contrato:");

        FECHA_CONTRATO.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        FECHA_CONTRATO.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        FECHA_CONTRATO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FECHA_CONTRATOKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("NRO:");

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Destinado a:");

        JTF_DESTINO.setPreferredSize(new java.awt.Dimension(250, 28));

        jButton7.setText("PAGO");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("PLAZOS");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Lugar Prestacion/Entrega:");

        JTF_LUGAR_PRES_ENT.setPreferredSize(new java.awt.Dimension(250, 28));

        jButton9.setText("GARANTIA");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jCheckBox9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jCheckBox9.setText("CUCE:");

        jButton2.setText("DETALLE ITEMS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTF_AsesorJuridico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(287, 287, 287))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTF_LUGAR_PRES_ENT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(287, 287, 287))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTF_DESTINO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(287, 287, 287))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FECHA_CONTRATO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTF_Convocatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FECHA_CONVOCATORIA, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTF_ObjetoContratacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(287, 287, 287))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(JL_TITULO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(JC_TipoConsult, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JC_Anticipo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JC_TipoServ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JTF_CUCE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JTF_NRO, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(192, 192, 192))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JL_TITULO)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTF_CUCE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(JTF_NRO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox9))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(JC_Anticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(JC_TipoConsult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(JC_TipoServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(JTF_ObjetoContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(JTF_Convocatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(FECHA_CONVOCATORIA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FECHA_CONTRATO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(JTF_DESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(JTF_LUGAR_PRES_ENT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(JTF_AsesorJuridico, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/printer.png"))); // NOI18N
        jButton3.setText("Imprimir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        JB_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk (2).png"))); // NOI18N
        JB_Guardar.setText("Guardar");
        JB_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_GuardarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(68, 68, 68))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JB_Guardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(JB_Guardar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JB_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_GuardarActionPerformed
        // TODO add your handling code here:
        guardarContrato();
    }//GEN-LAST:event_JB_GuardarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //this.JB_Guardar.doClick();
        genera_res_15.ReporteContratoGenerico(this.cod_transaccion,this.cod_w,this.cuantia,this.JC_TipoServ.getSelectedIndex(),this.JC_TipoConsult.getSelectedIndex(),monto);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JD_CONTRATANTE JDC = new JD_CONTRATANTE(null,true,this.cod_transaccion);
        JDC.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JD_CONTRATADO JDC = new JD_CONTRATADO(null,true,this.cod_transaccion);
        JDC.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        JD_DOCUMENTOS_INTEGRANTES JDI = new JD_DOCUMENTOS_INTEGRANTES(null,true,this.cod_transaccion);
        JDI.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void FECHA_CONVOCATORIAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_CONVOCATORIAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_CONVOCATORIAKeyPressed

    private void FECHA_CONTRATOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FECHA_CONTRATOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FECHA_CONTRATOKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        JD_PAGO JDP = new JD_PAGO(null,true,this.cod_transaccion);
        JDP.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JD_PLAZOS JDP = new JD_PLAZOS(null,true,this.cod_transaccion);
        JDP.setVisible(true);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        JD_GARANTIA JDG = new JD_GARANTIA(null,true,this.cod_transaccion);
        JDG.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void JalaDatos(int cod_transaccion,int cod_trans_nro){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("si entra a construir");
            puerto.construyeContrato("SET-ConstruyeContrato", cod_transaccion,cod_trans_nro);
            System.out.println("si entra a construir 2");
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error :O <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    private void guardarContrato(){
//        System.err.println(this.JC_Anticipo.getSelectedIndex());
//        System.err.println(this.JC_FunGarRet.getSelectedIndex());
//        System.err.println(this.JC_GarRet.getSelectedIndex());
        
//        int sw_garantia_retencion = this.JC_GarRet.getSelectedIndex();
        int sw_anticipo = this.JC_Anticipo.getSelectedIndex();
//        int sw_garan_reten_func = this.JC_FunGarRet.getSelectedIndex();
        int sw_tipo_consultoria = this.JC_TipoConsult.getSelectedIndex();
        int sw_tipo_servicio = this.JC_TipoServ.getSelectedIndex();
        
//        if(sw_garantia_retencion ==0){
//            javax.swing.JOptionPane.showMessageDialog(this,"<html> Debe escoger una opcion<br> entre garantia o retencion ","SYSTEM CAPRICORN",
//                        javax.swing.JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if(sw_anticipo ==0){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Debe escoger una opcion<br>de anticipo ","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
//        if(sw_garan_reten_func ==0 && cod_w==1){
//            javax.swing.JOptionPane.showMessageDialog(this,"<html> Debe escoger una opcion<br>de garantia de funcionamiento ","SYSTEM CAPRICORN",
//                        javax.swing.JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if(sw_tipo_consultoria ==0 && cod_w==3){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Debe escoger una opcion<br>de tipo de consultoria","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(sw_tipo_servicio ==0 && cod_w==6){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Debe escoger una opcion<br>de tipo de servicio","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        
        try {
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //quitar el comentario
//            puerto.updateContratoInicio(cod_transaccion
//                    ,this.JTF_CUCE.getText(),this.JTF_AsesorJuridico.getText()
//                    ,String.valueOf(this.JC_GarRet.getSelectedIndex()),String.valueOf(this.JC_Anticipo.getSelectedIndex())
//                    ,String.valueOf(this.JC_FunGarRet.getSelectedIndex()),String.valueOf(this.JC_TipoConsult.getSelectedIndex())
//                    ,String.valueOf(this.JC_TipoServ.getSelectedIndex())
//                    ,this.JTF_ObjetoContratacion.getText()/*,"hola"*/
//                    ,this.JTF_Convocatoria.getText()
//                    ,formatoDeFecha.format(this.FECHA_CONVOCATORIA.getValue())
//                    ,formatoDeFecha.format(this.FECHA_CONTRATO.getValue())
//                    ,this.JTF_NRO.getText()
//                    ,this.JTF_DESTINO.getText()
//                    ,this.JTF_LUGAR_PRES_ENT.getText()
//            );
            
//            puerto.updateContrato2(cod_transaccion
//                , this.JTF_ProponenteAdjudicado.getText(), this.JTF_Direccion_ProponenteAdjudicado.getText(), this.JTF_RazonSocialContratante.getText()
//                , this.JTF_NitContratante.getText(), this.JTF_DireccionContratante.getText(), this.JTF_CiudadContratante.getText()
//                , this.JTF_RPA_RPC.getText(), this.JTF_CARGO_RPC_RPA.getText(), this.JTF_CI_RPA_RPC.getText()
//                , "", this.JTF_CI_ProponenteAdjudicado.getText(), this.JTF_Denominacion_ProponenteAdjudicado.getText()
//                , this.JTF_AsesorJuridico.getText(), this.JTF_ObjetoContratacion.getText(), null
//                , null, sw_garantia_retencion,sw_anticipo,sw_garan_reten_func
//            );
            javax.swing.JOptionPane.showMessageDialog(this, "Se actualizo el tramite de forma exitosa!!! ", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
           
                  
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error :O <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(JD_GeneraContrato2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_GeneraContrato2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_GeneraContrato2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_GeneraContrato2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_GeneraContrato2 dialog = new JD_GeneraContrato2(new javax.swing.JFrame(), true, 0,"",0,0);
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
    private net.sf.nachocalendar.components.DateField FECHA_CONTRATO;
    private net.sf.nachocalendar.components.DateField FECHA_CONVOCATORIA;
    private javax.swing.JButton JB_Guardar;
    private javax.swing.JComboBox JC_Anticipo;
    private javax.swing.JComboBox JC_TipoConsult;
    private javax.swing.JComboBox JC_TipoServ;
    private javax.swing.JLabel JL_TITULO;
    private javax.swing.JTextField JTF_AsesorJuridico;
    private javax.swing.JTextField JTF_CUCE;
    private javax.swing.JTextField JTF_Convocatoria;
    private javax.swing.JTextField JTF_DESTINO;
    private javax.swing.JTextField JTF_LUGAR_PRES_ENT;
    private javax.swing.JTextField JTF_NRO;
    private javax.swing.JTextField JTF_ObjetoContratacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
