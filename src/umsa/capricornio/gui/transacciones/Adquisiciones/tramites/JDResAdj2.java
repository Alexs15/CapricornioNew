/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.text.DecimalFormat;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static umsa.capricornio.gui.menu.FrmMenu.cod_usuario;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;
import umsa.capricornio.utilitarios.herramientas.NumerosTextuales;

/**
 *
 * @author UMSA-JES
 */
public class JDResAdj2 extends javax.swing.JDialog {

    /**
     * Creates new form JDResAdj2
     */
    String res_adm,adc,fecha_comision,inf_div_adqui,cod_proveedor,detalle,proveedor,num_resol,det_conc_prop,modo_eval,destino,cargo,actividad,prop_proveedor;
    int cod_trans_nro,cod_w,cod_transaccion,gestion;
    String fec,lug;
    String iniciales;
    JLabel j1;
    JCheckBox jc;
    JTextField jt1,jt2,jt3,jt4,jt5;
    GetResAdj genera_res_adj = new GetResAdj();
    private String cuce;
    String s1, tprov="", tbche="";
    int tadju,sw1;
    String[] vt1;
    String[] vt2;
    String []codigos=new String[100];
    int correlativo;
    int cod_resolucion;
    
    public JDResAdj2(java.awt.Frame parent, boolean modal,int cod_w,int cod_trans_nro,String cod_proveedor,String detalle,int cod_transaccion,int gestion) {
        super(parent, modal);
        initComponents();
        this.cod_w=cod_w;
        this.cod_trans_nro=cod_trans_nro;
        this.cod_proveedor=cod_proveedor;
        this.detalle=detalle;
        this.cod_transaccion=cod_transaccion;
        this.gestion=gestion;
        LlenaDatos();
        creatabla();
        controlBloqueo();
        Llenaejemplos();
    }
    
     private void verifielementos()
    {
        int c=0,c1=0,c2=0,c3=0,c4=0,c5=0;
        JTextField jtv[]=new JTextField[30];
        JCheckBox jc[]=new JCheckBox[30];
        tprov="";
        tbche="";
        //System.out.println("total adju"+tadju*5);
        for(int i=5; i<=(tadju*5)+4 ;i++)
        {
            //System.out.println(" ");
            ///System.out.println(jPanel2.getComponent(i).getName());
            if(jPanel2.getComponent(i).getName().toString().equals("texto1"+c))
            {
                jtv[c]=(JTextField) jPanel2.getComponent(i);
                //System.out.println(jtv[c].getText().toString());
                tprov=tprov+","+jtv[c].getText();
                c++;
            }
            if(jPanel2.getComponent(i).getName().toString().equals("texto2"+c2))
            {
                jtv[c2]=(JTextField) jPanel2.getComponent(i);
                //System.out.println(jtv[c].getText().toString());
                tprov=tprov+","+jtv[c2].getText();
                c2++;
            }
            if(jPanel2.getComponent(i).getName().toString().equals("texto3"+c3))
            {
                jtv[c3]=(JTextField) jPanel2.getComponent(i);
                jtv[c3].setText(jtv[c3].getText().toLowerCase());
                System.out.println("noooooooo"+jtv[c3].getText().toString());
                tprov=tprov+","+jtv[c3].getText();
                c3++;
            }
            /*if(jPanel2.getComponent(i).getName().toString().equals("texto4"+c4))
            {
                jtv[c4]=(JTextField) jPanel2.getComponent(i);
                //System.out.println(jtv[c].getText().toString());
                tprov=tprov+","+jtv[c4].getText();
                c4++;
            }
            if(jPanel2.getComponent(i).getName().toString().equals("texto5"+c5))
            {
                jtv[c5]=(JTextField) jPanel2.getComponent(i);
                //System.out.println(jtv[c].getText().toString());
                tprov=tprov+","+jtv[c5].getText();
                c5++;
            }*/
            if(jPanel2.getComponent(i).getName().toString().equals("check1"+c1))
            {
                jc[c1]=(JCheckBox) jPanel2.getComponent(i);
                //System.out.println(jc[c1].getSelectedObjects());
                //System.out.println("c1="+c1);
                if(jc[c1].getSelectedObjects()!=null)
                   tbche=tbche+",cumple";
                else
                   tbche=tbche+",no cumple";
                c1++;
            }
            System.err.println("u"+c+"u"+c1+"u"+c2+"u"+c3+"u"+c4+"u"+c5);
            System.err.println("dsf"+jPanel2.getComponent(i).getName().toString());
        }
        //System.out.println("tprov: "+tprov+", tbche"+tbche);
    }
    
    private void creatabla()
    {
        String conc="";
        //codigos[]=new String[10];
        System.out.println("entra acreartbla");
        int k=30,kk=10,kkk=572,p=1,p1=1;
        jLabel24.setText("MONTO AJUSTADO");
        /*jPanel2.add(jLabel26);
        jLabel27.setBounds(910,10,60,20);
        jPanel2.add(jLabel27);*/
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getConcursantes(cod_trans_nro);
            if(datos!=null){
                tadju=datos.length;
                System.out.println("el total es: "+tadju);
                kk=kk+(datos.length-1)*50;
                kkk=kkk+(datos.length-1)*50;
                for (int c=0;c<datos.length;c++){
                    System.out.println("Concursantes con Datos...");
                    if(datos[c].get("NOMBRE_COMERCIAL").equals("Sin Nombre Comercial"))
                    {
                        codigos[c]=datos[c].get("COD_PROVEEDOR").toString();
                        System.out.println("jjjjj "+codigos[c]);
                        j1=new JLabel(datos[c].get("NOMBRE").toString());
                        j1.setBounds(5, k, 300, 30);
                        j1.setText(datos[c].get("NOMBRE").toString());
                        j1.setName("label1"+c);
                        jc=new JCheckBox("si cumple");
                        jc.setBounds(300, k, 100, 30);
                        //jc.setText(datos[c].get("NOMBRE").toString());
                        jc.setVisible(true);
                        jc.setEnabled(true);
                        jc.setName("check1"+c);
                        jt1=new JTextField();
                        jt1.setBounds(410, k, 100, 30);
                        jt1.setName("texto1"+c);
                        jt2=new JTextField();
                        jt2.setBounds(520, k, 150, 30);
                        jt2.setName("texto2"+c);
                        jt3=new JTextField();
                        jt3.setBounds(680, k, 150, 30);
                        jt3.setName("texto3"+c);
                        /*jt4=new JTextField();
                        jt4.setBounds(840, k, 60, 30);
                        jt4.setName("texto4"+c);
                        jt5=new JTextField();
                        jt5.setBounds(910, k, 60, 30);
                        jt5.setName("texto5"+c);*/
                        jPanel2.add(j1);
                        jPanel2.add(jc);
                        jPanel2.add(jt1);
                        jPanel2.add(jt2);
                        jPanel2.add(jt3);
                        /*jPanel2.add(jt4);
                        jPanel2.add(jt5);*/
                        /*jPanel2.setSize(970, kk);
                        jPanel2.setPreferredSize(jPanel2.getSize());
                        jPanel1.setSize(997, kkk);
                       // System.out.println("WIDTH "+jPanel2.getSize());
                        jPanel1.setPreferredSize(jPanel1.getSize());*/
                        k=k+30;
                        conc = conc+", "+datos[c].get("NOMBRE").toString();
                        System.out.println("uuuuuuuuu "+conc+" esto es el sw"+sw1);
                        if(sw1==1)
                        {
                            /*System.out.println((datos[c].get("MONTO").toString()));
                            System.out.println((datos[c].get("PLAZO").toString()));*/
                            System.out.println((datos[c].get("OBSERVACION").toString()));
                            jt1.setText((datos[c].get("OBSERVACION").toString()));
                            jt2.setText((datos[c].get("PTG_ECONOMICO").toString()));
                            jt3.setText((datos[c].get("PTG_TECNICO").toString()));
                            /*jt4.setText(datos[c].get("PTG_ECONOMICO").toString());
                            jt5.setText(datos[c].get("PTG_TECNICO").toString());*/
                            p=p+1;
                            
                            jt1.setEnabled(false);
                            jt2.setEnabled(false);
                            jt3.setEnabled(false);
                            /*jt4.setEnabled(false);
                            jt5.setEnabled(false);*/
                            if((datos[c].get("CUMPLE").toString()).equals("cumple"))
                                jc.setSelected(true);
                            jc.setEnabled(false);
                        }
                    }
                    else
                    {
                        codigos[c]=datos[c].get("COD_PROVEEDOR").toString();
                        System.out.println("jjjjj8 "+codigos[c]);
                        j1=new JLabel(datos[c].get("NOMBRE_COMERCIAL").toString());
                        j1.setBounds(5, k, 300, 30);
                        j1.setVisible(true);
                        j1.setEnabled(true);
                        j1.setName("label1"+c);
                        jc=new JCheckBox("si cumple");
                        jc.setBounds(300, k, 100, 30);
                        jc.setName("check1"+c);
                        //jc.setText(datos[c].get("NOMBRE").toString());
                        jc.setVisible(true);
                        jc.setEnabled(true);
                        jt1=new JTextField();
                        jt1.setBounds(410, k, 100, 30);
                        jt1.setName("texto1"+c);
                        jt2=new JTextField();
                        jt2.setBounds(520, k, 150, 30);
                        jt2.setName("texto2"+c);
                        jt3=new JTextField();
                        jt3.setBounds(680, k, 150, 30);
                        jt3.setName("texto3"+c);
                        /*jt4=new JTextField();
                        jt4.setBounds(840, k, 60, 30);
                        jt4.setName("texto4"+c);
                        jt5=new JTextField();
                        jt5.setBounds(910, k, 60, 30);
                        jt5.setName("texto5"+c);*/
                        jPanel2.add(j1);
                        jPanel2.add(jc);
                        jPanel2.add(jt1);
                        jPanel2.add(jt2);
                        jPanel2.add(jt3);
                        /*jPanel2.add(jt4);
                        jPanel2.add(jt5);
                        jPanel2.setSize(970, kk);
                        System.out.println("WIDTH "+jPanel2.getSize());
                        jPanel2.setPreferredSize(jPanel2.getSize());
                        jPanel1.setSize(997, kkk);
                       // System.out.println("WIDTH "+jPanel2.getSize());
                        jPanel1.setPreferredSize(jPanel1.getSize());*/
                        k=k+30;
                        conc = conc+", "+datos[c].get("NOMBRE_COMERCIAL").toString();
                        System.out.println("aaaaaaa "+conc);
                        if(sw1==1)
                        {
                            jt1.setText((datos[c].get("OBSERVACION").toString()));
                            jt2.setText((datos[c].get("PTG_ECONOMICO").toString()));
                            jt3.setText((datos[c].get("PTG_TECNICO").toString()));
                            /*jt4.setText(datos[c].get("PTG_ECONOMICO").toString());
                            jt5.setText(datos[c].get("PTG_TECNICO").toString());*/
                            p=p+1;
                            
                            jt1.setEnabled(false);
                            jt2.setEnabled(false);
                            jt3.setEnabled(false);
                            /*jt4.setEnabled(false);
                            jt5.setEnabled(false);*/
                            if((datos[c].get("CUMPLE").toString()).equals("cumple"))
                                jc.setSelected(true);
                            jc.setEnabled(false);
                        }
                    }
                    System.out.println("adsfsdf "+ c);
                }
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
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
        jLabel1 = new javax.swing.JLabel();
        JB_Salir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        JTF_Res_Admi = new javax.swing.JTextField();
        JTF_ADC = new javax.swing.JTextField();
        JTF_FechaCC = new javax.swing.JTextField();
        JTF_INFDIVADQUI = new javax.swing.JTextField();
        JB_Generar = new javax.swing.JButton();
        JB_Imprimir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        JTF_DET_CONC_PROP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        JTF_MEVAL = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        JTF_DESTINO = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        JTF_CARGO = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        JTF_ACTIVIDAD = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        JB_Actualizar = new javax.swing.JButton();
        JB_Guardar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(209, 224, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("GENERACIÓN DE RESOLUCIÓN DE ADJUDICACIÓN");

        JB_Salir.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        JB_Salir.setForeground(new java.awt.Color(255, 0, 0));
        JB_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        JB_Salir.setText("SALIR");
        JB_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_SalirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel2.setText("RESOLUCION ADMINISTRATIVA:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel3.setText("INFORME TECNICO");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setText("FECHA COMISION CALIFICACION:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel5.setText("INF.DIV.ADQUI :");

        JTF_Res_Admi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_Res_AdmiActionPerformed(evt);
            }
        });

        JTF_ADC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_ADCActionPerformed(evt);
            }
        });

        JTF_FechaCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_FechaCCActionPerformed(evt);
            }
        });

        JTF_INFDIVADQUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_INFDIVADQUIActionPerformed(evt);
            }
        });

        JB_Generar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        JB_Generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/arrow_redo.png"))); // NOI18N
        JB_Generar.setText("GENERAR");
        JB_Generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_GenerarActionPerformed(evt);
            }
        });

        JB_Imprimir.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        JB_Imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/printer.png"))); // NOI18N
        JB_Imprimir.setText("IMPRIMIR");
        JB_Imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_ImprimirActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel6.setText("DETALLE CONCURSO PROPUESTAS:");

        JTF_DET_CONC_PROP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_DET_CONC_PROPActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel7.setText("MODO DE EVALUACION:");

        JTF_MEVAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_MEVALActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel8.setText("DESTINO:");

        JTF_DESTINO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_DESTINOActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel9.setText("CARGO:");

        JTF_CARGO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_CARGOActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel10.setText("ACTIVIDAD:");

        JTF_ACTIVIDAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTF_ACTIVIDADActionPerformed(evt);
            }
        });

        JB_Actualizar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        JB_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/action_refresh_blue.gif"))); // NOI18N
        JB_Actualizar.setText("Actualizar");
        JB_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_ActualizarActionPerformed(evt);
            }
        });

        JB_Guardar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        JB_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk.png"))); // NOI18N
        JB_Guardar.setText("Guardar");
        JB_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_GuardarActionPerformed(evt);
            }
        });

        jLabel11.setText("jLabel11");

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel13");

        jLabel14.setText("jLabel14");

        jLabel15.setText("jLabel15");

        jLabel16.setText("jLabel16");

        jLabel17.setText("jLabel17");

        jLabel18.setText("jLabel18");

        jLabel19.setText("jLabel19");

        jLabel20.setText("PROPONENTE");

        jLabel21.setText("CUMPLE");

        jLabel22.setText("OBSERVACIONES");

        jLabel23.setText("VALOR PROPUESTA");

        jLabel24.setText("jLabel24");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel20)
                .addGap(160, 160, 160)
                .addComponent(jLabel21)
                .addGap(65, 65, 65)
                .addComponent(jLabel22)
                .addGap(30, 30, 30)
                .addComponent(jLabel23)
                .addGap(34, 34, 34)
                .addComponent(jLabel24)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        jLabel25.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel25.setText("LUGAR DEL ACTO DE APERTURA:");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel26.setText("FECHA DEL ACTO DE APERTURA:");

        jLabel27.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel27.setText("CORRELATIVO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_ACTIVIDAD, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_CARGO, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_DESTINO, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_MEVAL, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_DET_CONC_PROP, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_INFDIVADQUI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                            .addComponent(JTF_FechaCC, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_ADC, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTF_Res_Admi)
                                            .addComponent(jTextField3))
                                        .addGap(62, 62, 62)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19)))))
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(JB_Generar)
                        .addGap(35, 35, 35)
                        .addComponent(JB_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(JB_Imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(JB_Actualizar)
                        .addGap(56, 56, 56)
                        .addComponent(JB_Guardar)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JTF_Res_Admi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(JTF_ADC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(JTF_FechaCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(JTF_INFDIVADQUI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(JTF_DET_CONC_PROP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(JTF_MEVAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(JTF_DESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JTF_CARGO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(JTF_ACTIVIDAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JB_Actualizar)
                            .addComponent(JB_Guardar))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JB_Generar)
                            .addComponent(JB_Salir)
                            .addComponent(JB_Imprimir))
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void controlBloqueo(){
        System.out.println("================ el cod_w: "+cod_w+" =============");
        if(this.cod_w==6)
            this.bloqueaServicio();
        if(this.cod_w==7 || cod_w == 1)
            this.bloqueaBien();
        if(this.cod_w==3)
            this.bloqueaConsultoria();
    }
    private void Llenaejemplos(){
        System.out.println("================ el cod_w: "+cod_w+" =============");
        if(this.cod_w==6)
            this.ejServicio();
        if(this.cod_w==7 || cod_w == 1)
            this.ejBien();
        if(this.cod_w==3)
            this.ejConsultoria();
    }
    private void bloqueaConsultoria(){
        this.JTF_ADC.setEnabled(false);
        //this.JTF_ACTIVIDAD.setEnabled(false);
    }
    private void bloqueaServicio(){
        /*this.JTF_DET_CONC_PROP.setEnabled(false);
        this.JTF_MEVAL.setEnabled(false);
        this.JTF_DESTINO.setEnabled(false);
        this.JTF_CARGO.setEnabled(false);
        this.JTF_ACTIVIDAD.setEnabled(false);*/
    }
    private void bloqueaBien(){
        //this.JTF_ADC.setEnabled(false);
        
    }
    private void ejServicio()
    {
        jLabel11.setText("12/05");
        jLabel12.setText("INF.PP.DIV.Nº18/02 emitido por DTIC");
        jLabel13.setText("14 de abril");
        jLabel14.setText("18/03");
        jLabel15.setText("07/25(primera convocatoria)");
        jLabel16.setText("calidad");
        jLabel17.setText("DPTO. DE REDES o (actividad especifica)");
        jLabel18.setText("REFORMULACION DE POA");
        jLabel19.setText("FORTALECIMIENTO DE WIFI");
    }
    private void ejBien()
    {
        jLabel11.setText("12/05");
        jLabel12.setText("INF.PP.DIV.Nº18/02");
        jLabel13.setText("14 de abril");
        jLabel14.setText("18/03");
        jLabel15.setText("07/25(primera convocatoria)");
        jLabel16.setText("calidad");
        jLabel17.setText("DPTO. DE REDES");
        jLabel18.setText("REFORMULACION DE POA");
        jLabel19.setText("FORTALECIMIENTO DE WIFI");
    }
    private void ejConsultoria()
    {
        jLabel11.setText("12/05");
        jLabel12.setText("NO ES NECESARIO");
        jLabel13.setText("14 de abril");
        jLabel14.setText("18/03");
        jLabel15.setText("07/25(primera convocatoria)");
        jLabel16.setText("INF.PP.DIV.Nº18/02");
        jLabel17.setText("DPTO. DE REDES");
        jLabel18.setText("REFORMULACION DE POA");
        jLabel19.setText("184,185,186");
    }
    private void JB_GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_GenerarActionPerformed
        // TODO add your handling code here:
        verifielementos();
        System.out.println("--***>> el cod_w es: "+cod_w);
        if(this.cod_w==6){
            this.GeneraResAdjServ(this.JTF_ADC.getText(), this.JTF_Res_Admi.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(),this.JTF_DET_CONC_PROP.getText(),this.JTF_MEVAL.getText(),this.JTF_DESTINO.getText(),this.JTF_CARGO.getText(),this.JTF_ACTIVIDAD.getText());
        }
        if(this.cod_w==7||this.cod_w==1){
            this.GeneraResAdjBien(this.JTF_Res_Admi.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(),this.JTF_DET_CONC_PROP.getText(),this.JTF_MEVAL.getText(),this.JTF_DESTINO.getText(),this.JTF_CARGO.getText(),this.JTF_ACTIVIDAD.getText(),this.JTF_ADC.getText(),jTextField1.getText(),jTextField2.getText());
        }
        if(this.cod_w==3){
            this.GeneraResAdjConsu(this.JTF_Res_Admi.getText(), this.JTF_ADC.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(),this.JTF_DET_CONC_PROP.getText(),this.JTF_MEVAL.getText(),this.JTF_DESTINO.getText(),this.JTF_CARGO.getText(),this.JTF_ACTIVIDAD.getText());
        }
    
    }//GEN-LAST:event_JB_GenerarActionPerformed

    private void JB_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_SalirActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_JB_SalirActionPerformed

    private String Concursantes(){
        String conc="";
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getConcursantes(cod_transaccion);
            if(datos!=null){
                for (int c=0;c<datos.length;c++){
                    System.out.println("Concursantes con Datos...");
                    if(datos[c].get("NOMBRE_COMERCIAL").equals("Sin Nombre Comercial"))
                    {
                        conc = conc+", "+datos[c].get("NOMBRE").toString();
                        System.out.println("uuuuuuuuu "+conc);
                    }
                    else
                        conc = conc+", "+datos[c].get("NOMBRE_COMERCIAL").toString();
                    //System.out.println(".. "+this.num_resol);
                }
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return conc;
    }
    private String CUCE(){
        String conc="";
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getCuceSicoes(cod_transaccion);
            System.out.println("el nro de trans "+cod_transaccion);
            if(datos!=null){
                for (int c=0;c<datos.length;c++){
                    //System.out.println("Concursantes con Datos...");
                    conc = datos[c].get("CUCE_SICOES").toString();
                    //System.out.println(".. "+this.num_resol);
                }
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return conc;
    }
    String formato(String t)
    {
        System.out.println("aejrhaeusdhuiasdfhiudshf "+t);
        try{
           double m=Double.parseDouble(t);  
        DecimalFormat formato_decimal = new DecimalFormat("###,###.##");
        System.out.println("swdasda"+formato_decimal.format(m));
        //String d=formato_decimal.format(t).toString();
        return (String.valueOf(formato_decimal.format(m)).toString());
        }
        catch(Exception e)
        {
            System.out.println("este mensaje jejeje"+e.getMessage());
            return "hola";
        }
        
    }
    private String MONTO(){
        String conc="";
        Double total=0.0;
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getPreventivos(cod_transaccion);
            if(datos!=null){
                for (int c=0;c<datos.length;c++){
                    total=total+Double.parseDouble(datos[c].get("TOTAL").toString().trim().replace(",", "."));
                }
                conc=String.valueOf(total);
                conc=this.formato(conc)+" ("+this.TotalTexto(conc)+" BOLIVIANOS)";
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return conc;
    }
    void imprime (){
        this.JB_Imprimir.doClick();
    }
    private void JB_ImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_ImprimirActionPerformed
        // TODO add your handling code here:
        //this.genera_res_adj.Reporte(res_adm, res_adm, res_adm, adc, detalle, res_adm, fecha_comision, adc, adc, res_adm, res_adm, res_adm, cod_trans_nro, cod_w);
        if(this.cod_w==6)
            this.genera_res_adj.ReporteAdjServ(adc, res_adm, fecha_comision, inf_div_adqui, proveedor, detalle, num_resol, det_conc_prop, modo_eval, destino, cargo, actividad,prop_proveedor,Concursantes(),CUCE(),MONTO());
        if(this.cod_w==1)
            this.genera_res_adj.ReporteAdjBien(res_adm, fecha_comision, inf_div_adqui, proveedor, detalle, num_resol, det_conc_prop, modo_eval, destino, cargo, actividad,prop_proveedor,Concursantes(),CUCE(),MONTO(),adc,cod_transaccion,cod_trans_nro);
//        this.setVisible(false);
        if(this.cod_w==3)
            this.genera_res_adj.ReporteAdjConsul(res_adm, fecha_comision, inf_div_adqui, proveedor, detalle, num_resol, det_conc_prop, modo_eval, destino, cargo, actividad,prop_proveedor,Concursantes(),CUCE(),MONTO());
    }//GEN-LAST:event_JB_ImprimirActionPerformed

    private void unlockAdjServ(){
        this.JTF_Res_Admi.setEnabled(true);
        this.JTF_ADC.setEnabled(true);
        this.JTF_FechaCC.setEnabled(true);
        this.JTF_INFDIVADQUI.setEnabled(true);
        this.JTF_DET_CONC_PROP.setEnabled(true);
        this.JTF_MEVAL.setEnabled(true);
        this.JTF_DESTINO.setEnabled(true);
        this.JTF_CARGO.setEnabled(true);
        this.JTF_ACTIVIDAD.setEnabled(true);
        
        this.jTextField3.setEnabled(true);
        this.JB_Generar.setEnabled(false);
        this.JB_Imprimir.setEnabled(false);
        this.JB_Actualizar.setEnabled(false);
    }
    private void unlockAdjBien(){
        this.JTF_Res_Admi.setEnabled(true);
        this.JTF_ADC.setEnabled(true);
        this.JTF_FechaCC.setEnabled(true);
        this.JTF_INFDIVADQUI.setEnabled(true);
        this.JTF_DET_CONC_PROP.setEnabled(true);
        this.JTF_MEVAL.setEnabled(true);
        this.JTF_DESTINO.setEnabled(true);
        this.JTF_CARGO.setEnabled(true);
        this.JTF_ACTIVIDAD.setEnabled(true);
        this.jTextField1.setEnabled(true);
        this.jTextField2.setEnabled(true);
        
        this.jTextField3.setEnabled(true);
        
        for(int i=5; i<=(tadju*5)+4 ;i++)
        {
            jPanel2.getComponent(i).setEnabled(true);
        }
        
        this.JB_Generar.setEnabled(false);
        this.JB_Imprimir.setEnabled(false);
        this.JB_Actualizar.setEnabled(false);
    }
    private void unlockAdjCons(){
        this.JTF_Res_Admi.setEnabled(true);
        //this.JTF_ADC.setEnabled(true);
        this.JTF_FechaCC.setEnabled(true);
        this.JTF_INFDIVADQUI.setEnabled(true);
        this.JTF_DET_CONC_PROP.setEnabled(true);
        this.JTF_MEVAL.setEnabled(true);
        this.JTF_DESTINO.setEnabled(true);
        this.JTF_CARGO.setEnabled(true);
        this.JTF_ACTIVIDAD.setEnabled(true);
        
        this.jTextField3.setEnabled(true);
        
        this.JB_Generar.setEnabled(false);
        this.JB_Imprimir.setEnabled(false);
        this.JB_Actualizar.setEnabled(false);
    }
    private void JB_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_ActualizarActionPerformed
        // TODO add your handling code here:
        
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea actualizar la Resolución de Adjudicación?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.YES_OPTION) {
            if(this.cod_w==6)
                this.unlockAdjServ();
            if(this.cod_w==7 || cod_w==1)
                this.unlockAdjBien();
            if(this.cod_w==3)
                this.unlockAdjCons();
        }
    }//GEN-LAST:event_JB_ActualizarActionPerformed

    private void updateAdjServ(String adc,String res_adm,String fecha_cc,String inf_div_adq,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();

            correlativo=Integer.parseInt(jTextField3.getText());
            Map[] dato=puerto.verificando_Nro(correlativo,2);
            //Map[] dato=null;
            if(dato!=null)
            {
                if(Integer.parseInt(dato[0].get("COD_RESOLUCION").toString())==cod_resolucion)
                {
                    puerto.updateResAdjServ(adc,this.cod_trans_nro, this.cod_w, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,cod_usuario,this.correlativo);
                    System.out.println("ResAdjServ actualizado correctamente :D");
                    javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido actualizada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE); 
                }
                else
                {
                    javax.swing.JOptionPane.showMessageDialog(this,"ESTE CORRELATIVO YA EXISTE VERIFIQUE EL NRO DE CORRELATIVO","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
            {
                puerto.updateResAdjServ(adc,this.cod_trans_nro, this.cod_w, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,cod_usuario,this.correlativo);
                System.out.println("ResAdjServ actualizado correctamente :D");
                javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido actualizada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(Exception e){
            System.out.println("ResAdjServ actualizado erroneamente :D");
        }
    }
    private void updateAdjBien(String res_adm,String fecha_cc,String inf_div_adq,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String adc,String aaa,String bbb){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
                        
            //puerto.updateResAdjServ(cod_trans_nro, cod_w, res_adm, adc, fecha_cc, inf_div_adq);
            correlativo=Integer.parseInt(jTextField3.getText());
            Map[] dato=puerto.verificando_Nro(correlativo,2);
            //Map[] dato=null;
            if(dato!=null)
            {
                if(Integer.parseInt(dato[0].get("COD_RESOLUCION").toString())==cod_resolucion)
                {
                    puerto.updateResAdjBien(this.cod_trans_nro, 2, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,adc,aaa,bbb,cod_usuario,this.correlativo);
                    int cc=1,hh=1,nh=0;
                    String []n1;
                    String []n2;
                    n1=tprov.split(",");
                    n2=tbche.split(",");
                    System.out.println(tprov+" aqui "+tbche);
                    System.out.println();
                    while(cc<n1.length)
                    {
                        puerto.gentabla(null, null, n1[cc], n2[hh],cod_trans_nro,codigos[nh],n1[cc+1],n1[cc+2]);
                        cc=cc+3;
                        /*puerto.gentabla(n1[cc], n1[cc+1], n1[cc+2], n2[hh],cod_trans_nro,codigos[nh]);
                        cc=cc+3;*/
                        hh++;
                        nh++;
                    }
                    System.out.println("ResAdjServ actualizado correctamente :D");
                }
                else
                {
                    javax.swing.JOptionPane.showMessageDialog(this,"ESTE CORRELATIVO YA EXISTE VERIFIQUE EL NRO DE CORRELATIVO","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
            {
                puerto.updateResAdjBien(this.cod_trans_nro, 2, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,adc,aaa,bbb,cod_usuario,this.correlativo);
                    int cc=1,hh=1,nh=0;
                    String []n1;
                    String []n2;
                    n1=tprov.split(",");
                    n2=tbche.split(",");
                    System.out.println(tprov+" aqui "+tbche);
                    System.out.println();
                    while(cc<n1.length)
                    {
                        puerto.gentabla(null, null, n1[cc], n2[hh],cod_trans_nro,codigos[nh],n1[cc+1],n1[cc+2]);
                        cc=cc+3;
                        /*puerto.gentabla(n1[cc], n1[cc+1], n1[cc+2], n2[hh],cod_trans_nro,codigos[nh]);
                        cc=cc+3;*/
                        hh++;
                        nh++;
                    }
                    System.out.println("ResAdjServ actualizado correctamente :D");
            }
            
            javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido actualizada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println("ResAdjServ actualizado erroneamente :D");
        }
    }
    private void updateAdjCons(String res_adm,String fecha_cc,String inf_div_adq,String det_conc_prop,String modo_eval,String destino,String cargo,String actividad){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
                        
            //puerto.updateResAdjServ(cod_trans_nro, cod_w, res_adm, adc, fecha_cc, inf_div_adq);
            correlativo=Integer.parseInt(jTextField3.getText());
            Map[] dato=puerto.verificando_Nro(correlativo,2);
            //Map[] dato=null;
            if(dato!=null)
            {
                if(Integer.parseInt(dato[0].get("COD_RESOLUCION").toString())==cod_resolucion)
                {
                    puerto.updateResAdjCons(this.cod_trans_nro, this.cod_w, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,cod_usuario,this.correlativo);
                    System.out.println("ResAdjServ actualizado correctamente :D");
                        javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido actualizada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    javax.swing.JOptionPane.showMessageDialog(this,"ESTE CORRELATIVO YA EXISTE VERIFIQUE EL NRO DE CORRELATIVO","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
            {
                puerto.updateResAdjCons(this.cod_trans_nro, this.cod_w, res_adm, fecha_cc, inf_div_adq, det_conc_prop, modo_eval, destino, cargo, actividad,cod_usuario,this.correlativo);
                System.out.println("ResAdjServ actualizado correctamente :D");
                javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido actualizada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(Exception e){
            System.out.println("ResAdjServ actualizado erroneamente :D");
        }
    }
    private void JB_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_GuardarActionPerformed
        // TODO add your handling code here:
        verifielementos();
        if(this.cod_w==6)
            this.updateAdjServ(this.JTF_ADC.getText(), this.JTF_Res_Admi.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(), this.JTF_DET_CONC_PROP.getText(), this.JTF_MEVAL.getText(), this.JTF_DESTINO.getText(), this.JTF_CARGO.getText(), this.JTF_ACTIVIDAD.getText());
        if(this.cod_w==7 || cod_w==1)
            this.updateAdjBien(this.JTF_Res_Admi.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(), this.JTF_DET_CONC_PROP.getText(), this.JTF_MEVAL.getText(), this.JTF_DESTINO.getText(), this.JTF_CARGO.getText(), this.JTF_ACTIVIDAD.getText(),this.JTF_ADC.getText(),jTextField1.getText(),jTextField2.getText());
        if(this.cod_w==3)
            this.updateAdjCons(this.JTF_Res_Admi.getText(), this.JTF_FechaCC.getText(), this.JTF_INFDIVADQUI.getText(), this.JTF_DET_CONC_PROP.getText(), this.JTF_MEVAL.getText(), this.JTF_DESTINO.getText(), this.JTF_CARGO.getText(), this.JTF_ACTIVIDAD.getText());
        this.setVisible(false);
    }//GEN-LAST:event_JB_GuardarActionPerformed

    private void JTF_Res_AdmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_Res_AdmiActionPerformed
        // TODO add your handling code here:
        //System.out.println("Changos.... cod_w: "+cod_w);
        if(cod_w==6)
            this.JTF_ADC.requestFocus();
        if(cod_w==7 || cod_w==1)
            this.JTF_FechaCC.requestFocus();
    }//GEN-LAST:event_JTF_Res_AdmiActionPerformed

    private void JTF_ADCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_ADCActionPerformed
        // TODO add your handling code here
        this.JTF_FechaCC.requestFocus();
    }//GEN-LAST:event_JTF_ADCActionPerformed

    private void JTF_FechaCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_FechaCCActionPerformed
        // TODO add your handling code here:
        this.JTF_INFDIVADQUI.requestFocus();
    }//GEN-LAST:event_JTF_FechaCCActionPerformed

    private void JTF_INFDIVADQUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_INFDIVADQUIActionPerformed
        // TODO add your handling code here:
        if(cod_w==6)
            this.JB_Generar.requestFocus();
        if(cod_w==7 || cod_w==1)
            this.JTF_DET_CONC_PROP.requestFocus();
    }//GEN-LAST:event_JTF_INFDIVADQUIActionPerformed

    private void JTF_DET_CONC_PROPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_DET_CONC_PROPActionPerformed
        // TODO add your handling code here:
        this.JTF_MEVAL.requestFocus();
    }//GEN-LAST:event_JTF_DET_CONC_PROPActionPerformed

    private void JTF_MEVALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_MEVALActionPerformed
        // TODO add your handling code here:
        this.JTF_DESTINO.requestFocus();
    }//GEN-LAST:event_JTF_MEVALActionPerformed

    private void JTF_DESTINOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_DESTINOActionPerformed
        // TODO add your handling code here:
        this.JTF_CARGO.requestFocus();
    }//GEN-LAST:event_JTF_DESTINOActionPerformed

    private void JTF_CARGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_CARGOActionPerformed
        // TODO add your handling code here:
        this.JTF_ACTIVIDAD.requestFocus();
    }//GEN-LAST:event_JTF_CARGOActionPerformed

    private void JTF_ACTIVIDADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTF_ACTIVIDADActionPerformed
        // TODO add your handling code here:
        this.JB_Generar.requestFocus();
    }//GEN-LAST:event_JTF_ACTIVIDADActionPerformed

    /**
     * @param args the command line arguments
     */
    private void bloqueaTodo(){
        this.JTF_ACTIVIDAD.setEnabled(false);
        this.JTF_ADC.setEnabled(false);
        this.JTF_CARGO.setEnabled(false);
        this.JTF_DESTINO.setEnabled(false);
        this.JTF_DET_CONC_PROP.setEnabled(false);
        this.JTF_FechaCC.setEnabled(false);
        this.JTF_INFDIVADQUI.setEnabled(false);
        this.JTF_MEVAL.setEnabled(false);
        this.JTF_Res_Admi.setEnabled(false);
        this.jTextField1.setEnabled(false);
        this.jTextField2.setEnabled(false);
        this.jTextField3.setEnabled(false);
    }
    private void LlenaServicio(){
        System.out.println("En llena servicio");
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getResAdjServ(cod_trans_nro);
            if(datos!=null){
                this.adc = datos[0].get("DET_ADC").toString();
                System.out.println(".. "+this.adc);
                this.res_adm = datos[0].get("DET_RES_ADM").toString();
               
                this.fecha_comision = datos[0].get("FECHA_CC").toString(); 
                this.inf_div_adqui = datos[0].get("DET_INF_DIV_ADQ").toString();
                this.proveedor = datos[0].get("PROVEEDOR").toString();
                this.num_resol = datos[0].get("NUM_RESOLUCION").toString();
                
                this.det_conc_prop = datos[0].get("DET_CONC_PROP").toString();
                this.modo_eval = datos[0].get("MODO_EVAL").toString();
                this.destino = datos[0].get("DESTINO").toString();
                this.cargo = datos[0].get("CARGO").toString();
                this.actividad = datos[0].get("ACTIVIDAD").toString();
                this.prop_proveedor =datos[0].get("ADH_NOMBRE").toString();
                this.cod_resolucion = Integer.parseInt(datos[0].get("COD_RESOLUCION").toString());
                this.JTF_Res_Admi.setText(res_adm);
                
                this.JTF_FechaCC.setText(fecha_comision);
                this.JTF_INFDIVADQUI.setText(inf_div_adqui);
                
                this.JTF_DET_CONC_PROP.setText(det_conc_prop);
                this.JTF_MEVAL.setText(modo_eval);
                this.JTF_DESTINO.setText(destino);
                this.JTF_CARGO.setText(cargo);
                this.JTF_ACTIVIDAD.setText(actividad);
                
                this.JTF_ADC.setText(adc);
                this.jTextField3.setText(datos[0].get("NRO").toString());
                
                this.bloqueaTodo();
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }
    private void LlenaBien(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getResAdjBien(cod_trans_nro);
            if(datos!=null){
                sw1=1;
                System.out.println("Con datos :D");
                this.res_adm = datos[0].get("DET_RES_ADM").toString();
                this.adc = datos[0].get("DET_ADC").toString();
                this.fecha_comision = datos[0].get("FECHA_CC").toString(); 
                this.inf_div_adqui = datos[0].get("DET_INF_DIV_ADQ").toString();
                this.proveedor = datos[0].get("PROVEEDOR").toString();
                this.num_resol = datos[0].get("NUM_RESOLUCION").toString();
                
                this.det_conc_prop = datos[0].get("DET_CONC_PROP").toString();
                this.modo_eval = datos[0].get("MODO_EVAL").toString();
                this.destino = datos[0].get("DESTINO").toString();
                this.cargo = datos[0].get("CARGO").toString();
                this.actividad = datos[0].get("ACTIVIDAD").toString();
                this.prop_proveedor =datos[0].get("ADH_NOMBRE").toString();
                fec=datos[0].get("DET_UNI_SOL").toString();
                lug=datos[0].get("DET_AUT").toString();
                
                this.cod_resolucion = Integer.parseInt(datos[0].get("COD_RESOLUCION").toString());
                this.JTF_Res_Admi.setText(res_adm);
                this.JTF_ADC.setText(adc);
                this.JTF_FechaCC.setText(fecha_comision);
                this.JTF_INFDIVADQUI.setText(inf_div_adqui);
                
                this.JTF_DET_CONC_PROP.setText(det_conc_prop);
                this.JTF_MEVAL.setText(modo_eval);
                this.JTF_DESTINO.setText(destino);
                this.JTF_CARGO.setText(cargo);
                this.JTF_ACTIVIDAD.setText(actividad);
                this.jTextField3.setText(datos[0].get("NRO").toString());
                jTextField1.setText(fec);
                jTextField2.setText(lug);
                this.bloqueaTodo();
            }
            else
                System.out.println("Vacio :(");
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }
    
    private void LlenaCons(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            System.out.println("- El cod_w es: "+cod_w+" y el cod_trans_nro: "+cod_trans_nro);
            Map[] datos =puerto.getResAdjConsul(cod_trans_nro);
            this.jLabel10.setText("Notas de DIV.ADQ.Nº");
            if(datos!=null){
                System.out.println("Con datos :D");
                this.res_adm = datos[0].get("DET_RES_ADM").toString();
               
                this.fecha_comision = datos[0].get("FECHA_CC").toString(); 
                this.inf_div_adqui = datos[0].get("DET_INF_DIV_ADQ").toString();
                this.proveedor = datos[0].get("PROVEEDOR").toString();
                this.num_resol = datos[0].get("NUM_RESOLUCION").toString();
                
                this.det_conc_prop = datos[0].get("DET_CONC_PROP").toString();
                this.modo_eval = datos[0].get("MODO_EVAL").toString();
                this.destino = datos[0].get("DESTINO").toString();
                this.cargo = datos[0].get("CARGO").toString();
                this.actividad = datos[0].get("ACTIVIDAD").toString();
                this.prop_proveedor =datos[0].get("ADH_NOMBRE").toString();
                this.jLabel7.setText("INFORME TECNICO");
                this.jLabel3.setText("A.D.C");
                this.cod_resolucion = Integer.parseInt(datos[0].get("COD_RESOLUCION").toString());
                this.JTF_Res_Admi.setText(res_adm);
                
                this.JTF_FechaCC.setText(fecha_comision);
                this.JTF_INFDIVADQUI.setText(inf_div_adqui);
                
                this.JTF_DET_CONC_PROP.setText(det_conc_prop);
                this.JTF_MEVAL.setText(modo_eval);
                this.JTF_DESTINO.setText(destino);
                this.JTF_CARGO.setText(cargo);
                this.JTF_ACTIVIDAD.setText(actividad);
                this.jTextField3.setText(datos[0].get("NRO").toString());
                this.bloqueaTodo();
            }
            else{
                System.out.println("Vacio :(");
                //this.JB_Actualizar.setVisible(false);
                //this.JB_Guardar.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }
    private void LlenaDatos(){
        System.out.println("esto es el cod_w asd"+cod_w);
        if(this.cod_w==6)
            this.LlenaServicio();
        if(this.cod_w==7||cod_w==1)
            this.LlenaBien();
        if(this.cod_w==3)
            this.LlenaCons();
        
    }
    private void GeneraResAdjConsu(String res_adm, String fecha_comision, String adc,String inf_div_adqui, String det_conc_prop,String modo_eval,String destino,String cargo,String actividad){
          try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //puerto.genResAdjServ2(this.cod_trans_nro,this.cod_w, this.detalle,res_adm, adc, fecha_comision, inf_div_adqui);
            puerto.genResAdjConsu(this.cod_transaccion,this.cod_trans_nro, 2,this.detalle, res_adm, fecha_comision, inf_div_adqui, det_conc_prop, modo_eval, destino,cargo, actividad,gestion,cod_usuario);
            javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de consultoria ha sido generada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
//            this.setVisible(false);
            this.LlenaDatos();
            System.out.println("Se genero la R.A.B con exito :D");
            
        } catch (Exception e) {
            System.out.println("Error al generar R.A.B :(");
        }
    }
    private void GeneraResAdjBien(String res_adm, String fecha_comision, String inf_div_adqui, String det_conc_prop,String modo_eval,String destino,String cargo,String actividad,String adc,String aaa,String bbb){
          try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            //puerto.genResAdjServ2(this.cod_trans_nro,this.cod_w, this.detalle,res_adm, adc, fecha_comision, inf_div_adqui);
            System.out.println("cod_transaccion: "+this.cod_transaccion);
            System.out.println("cod_trans_nro: "+this.cod_trans_nro);
            System.out.println("cod_w: "+this.cod_w);
            System.out.println("detalle: "+this.detalle);
            System.out.println("res_adm: "+res_adm);
            System.out.println("fecha_comision: "+fecha_comision);
            System.out.println("inf_div_adqui: "+inf_div_adqui);
            System.out.println("det_conc_prop: "+det_conc_prop);
            System.out.println("modo_eval: "+modo_eval);
            System.out.println("destino: "+destino);
            System.out.println("cargo: "+cargo);
            System.out.println("actividad: "+actividad);
            try{
                System.out.println("uno");
                puerto.genResAdjBien(this.cod_transaccion,this.cod_trans_nro, 2,this.detalle, res_adm, fecha_comision, inf_div_adqui, det_conc_prop, modo_eval, destino,cargo, actividad,adc,aaa,bbb,gestion,cod_usuario);
                System.out.println("dos");
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            int cc=1,hh=1,nh=0;
            String []n1;
            String []n2;
            n1=tprov.split(",");
            n2=tbche.split(",");
            while(cc<n1.length)
            {
                puerto.gentabla(null, null, n1[cc], n2[hh],cod_trans_nro,codigos[nh],n1[cc+1],n1[cc+2]);
                cc=cc+3;
                System.out.println("entro");
                /*puerto.gentabla(n1[cc], n1[cc+1], n1[cc+2], n2[hh],cod_trans_nro,codigos[nh]);
                cc=cc+3;*/
                hh++;
                nh++;
            }
            javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de bien ha sido generada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
//            this.setVisible(false);
            this.LlenaDatos();
            System.out.println("Se genero la R.A.B con exito :D");
            
        } catch (Exception e) {
            System.out.println("Error al generar R.A.B :(");
        }
    }
    private void GeneraResAdjServ(String adc,String res_adm, String fecha_comision, String inf_div_adqui, String det_conc_prop,String modo_eval,String destino,String cargo,String actividad){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            /*
            System.out.println("cod_trans_nro: "+this.cod_trans_nro);
            System.out.println("cod_w: "+this.cod_w);
            System.out.println("detalle: "+this.detalle);
            System.out.println("res_adm: "+res_adm);
            System.out.println("adc: "+adc);
            System.out.println("fecha_comision: "+fecha_comision);
            System.out.println("inf_div_adqui: "+inf_div_adqui);
            System.out.println("cod_proveedor: "+this.cod_proveedor);
            */
            //puerto.genResAdjServ(this.cod_trans_nro,this.cod_w, this.detalle,res_adm, adc, fecha_comision, inf_div_adqui);
            //puerto.genResAdjServ2(this.cod_trans_nro,this.cod_w, this.detalle,res_adm, adc, fecha_comision, inf_div_adqui);
            //puerto.genResAdjServ2(15,1, "ups","a", "b", "c", "d");
            puerto.genResAdjServ2(this.cod_transaccion,this.cod_trans_nro, 2,this.detalle, res_adm, fecha_comision, inf_div_adqui, det_conc_prop, modo_eval, destino,cargo, actividad,adc,gestion,cod_usuario);
            javax.swing.JOptionPane.showMessageDialog(this,"La Resolución de Adjudicación de servicio ha sido generada de forma correcta","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
//            this.setVisible(false);
            this.LlenaDatos();
            System.out.println("Se genero la R.A.S con exito :D");
            
        } catch (Exception e) {
            System.out.println("Error al generar R.A.S :(");
        }
    }
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
            java.util.logging.Logger.getLogger(JDResAdj2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDResAdj2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDResAdj2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDResAdj2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDResAdj2 dialog = new JDResAdj2(new javax.swing.JFrame(), false,6,2,"xxx","qqq",0,2016);
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
    String TotalTexto(String total){
        double m=Double.parseDouble(total);                          
        
        long valor =(long)m;
        double var= m-valor;
        
        DecimalFormat formato_decimal = new DecimalFormat("0.00");        
        String decimal =formato_decimal.format(var);
      
        String montoLetra=NumerosTextuales.NumTextuales(valor);
        
        if ((m>=1000 && m<2000) || (m>=1000000 && m<2000000)){ montoLetra="UN "+montoLetra;}        
        if (var ==0.0) montoLetra=montoLetra+" 00/100";
        else montoLetra=montoLetra+" "+decimal.substring(2, 4)+"/100";
       return montoLetra;
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JB_Actualizar;
    private javax.swing.JButton JB_Generar;
    private javax.swing.JButton JB_Guardar;
    private javax.swing.JButton JB_Imprimir;
    private javax.swing.JButton JB_Salir;
    private javax.swing.JTextField JTF_ACTIVIDAD;
    private javax.swing.JTextField JTF_ADC;
    private javax.swing.JTextField JTF_CARGO;
    private javax.swing.JTextField JTF_DESTINO;
    private javax.swing.JTextField JTF_DET_CONC_PROP;
    private javax.swing.JTextField JTF_FechaCC;
    private javax.swing.JTextField JTF_INFDIVADQUI;
    private javax.swing.JTextField JTF_MEVAL;
    private javax.swing.JTextField JTF_Res_Admi;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
