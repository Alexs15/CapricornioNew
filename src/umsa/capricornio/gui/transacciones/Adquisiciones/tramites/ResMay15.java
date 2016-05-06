/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JOptionPane;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;
import umsa.capricornio.utilitarios.herramientas.NumerosTextuales;

/**
 *
 * @author alex
 */
public class ResMay15 extends javax.swing.JDialog {

    /**
     * Creates new form ResMay15
     */
    GetResAdj genera_res_15 = new GetResAdj();
    String hoja_ruta;
    String enviado_por;
    String cargo;
    String detalle;
    String destino;
    String objetivo;
    String preventivo;
    String monto;
    String partida;
    String sol_compra;
    String cotizacion;
    String proveedor;
    String dias;
    String cite;
    String nro_res;
    String fec_ini;
    String documentos,fecha_hr,fecha_nota;
    int cod_transaccion,cod_trans_nro,gestion,cod_w,cod_resolucion;
    public ResMay15(java.awt.Frame parent, boolean modal,String x, int a, int b, int c, int d) 
    {
        super(parent, modal);
        initComponents();
        proveedor=x;
        cod_transaccion=a;
        cod_trans_nro=b;
        gestion=c;
        cod_w=d;
        llenadatos();
    }
    public void bloquea()
    {
        jTextField1.setEnabled(false);
        jTextField2.setEnabled(false);
        Fecha2.setEnabled(false);
        jTextArea1.setEnabled(false);
        jTextArea2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField4.setEnabled(false);
        jTextField6.setEnabled(false);
        jButton1.setEnabled(false);
        jButton4.setVisible(true);
        jButton5.setVisible(true);
        jCheckBox9.setEnabled(false);
        jTextField5.setEnabled(false);
        
    }
    public void bloquea_botones()
    {
        jButton4.setVisible(false);
        jButton5.setVisible(false);
        if(cod_w!=1)
            bloquea3();
    }
    public void bloquea3()
    {
        jTextField6.setEnabled(false);
    }
    public void bloquea_check(Boolean x)
    {
        jCheckBox1.setEnabled(x);
        jCheckBox2.setEnabled(x);
        jCheckBox3.setEnabled(x);
        jCheckBox4.setEnabled(x);
        jCheckBox5.setEnabled(x);
        jCheckBox6.setEnabled(x);
        jCheckBox7.setEnabled(x);
        jCheckBox8.setEnabled(x);
    }
    public void llena_checks()
    {
        String v[]=documentos.split("#");
        for(int i=0;i<v.length;i++)
        {
            System.out.println(v[i]);
            if(v[i].equals("- "+jCheckBox1.getText()))
            {
                jCheckBox1.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox2.getText()))
            {
                jCheckBox2.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox3.getText()))
            {
                jCheckBox3.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox4.getText()))
            {
                jCheckBox4.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox5.getText()))
            {
                jCheckBox5.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox6.getText()))
            {
                jCheckBox6.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox7.getText()))
            {
                jCheckBox7.setSelected(true);
            }
            if(v[i].equals("- "+jCheckBox8.getText()))
            {
                jCheckBox8.setSelected(true);
            }
        }
    }
    public void llenadatos()
    {
        try
        {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println(cod_transaccion+" ggg "+cod_trans_nro);
            Map[] datos=puerto.getresmay(cod_transaccion,cod_trans_nro);
            System.out.println("asdfsfsdfsdf"+datos);
            if(datos!=null)
            {
                System.out.println("uno");
                jTextField1.setText(datos[0].get("CITE").toString());
                System.out.println("dos");
                jTextField2.setText(datos[0].get("CARGO").toString());
                System.out.println("tres");
                jTextField3.setText(datos[0].get("DETALLE_NOTA_SOLICITUD").toString());
                System.out.println("cuatro");
                jTextField4.setText(datos[0].get("DETALLE_NOTA_PRESUPUESTO").toString());
                System.out.println("cinco");
                Fecha2.setText(datos[0].get("FECHA_INICIO_CON").toString());
                jTextArea1.setText(datos[0].get("DESTINO").toString());
                jTextArea2.setText(datos[0].get("OBJETIVO").toString());
                jTextField6.setText(datos[0].get("COTIZACION").toString());
                nro_res=datos[0].get("NRO").toString();
                documentos=datos[0].get("DOCUMENTOS").toString();
                cod_resolucion=Integer.parseInt(datos[0].get("COD_RESOLUCION").toString());
                jTextField5.setText(nro_res);
                System.out.println("hasta aqui si pasa");
                llena_checks();
                bloquea_check(false);
                bloquea();
            }
            else
            {
                bloquea_botones();
            }
        }catch(Exception e)
        {
            System.err.println("noooooo "+e);
        }
    }
    String TotalTexto(String total){
        double m=Double.parseDouble(total);                          
        
        long valor =(long)m;
        double var= m-valor;
        
        DecimalFormat formato_decimal = new DecimalFormat("0.00");        
        String decimal =formato_decimal.format(var);
      
        String montoLetra=NumerosTextuales.NumTextuales(valor);
        
        if ((m>=1000 && m<2000) || (m>=1000000 && m<2000000)){ montoLetra="UN "+montoLetra;}        
        if (var ==0.0) montoLetra="("+montoLetra+" 00/100 Bolivianos)";
        else montoLetra="("+montoLetra+" "+decimal.substring(2, 4)+"/100 Bolivianos)";
       return montoLetra;
   }
    private void montocompleto()
    {
        System.out.println("monto "+monto);
        System.out.println("hr "+hoja_ruta);
        System.out.println("envia "+enviado_por);
        System.out.println("cargo "+cargo);
        System.out.println("detalle "+detalle);
        System.out.println("destino "+destino);
        System.out.println("objetivo "+objetivo);
        System.out.println("preventivo "+preventivo);
        System.out.println("partida "+partida);
        System.out.println("sol "+sol_compra);
        System.out.println("cot "+cotizacion);
        System.out.println("prov "+proveedor);
        System.out.println("dias "+dias);
        System.out.println("cite "+cite);
        monto=monto+" "+TotalTexto(monto).toLowerCase();
    }
    private void habilita()
    {
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
    }
    private void llena_campos()
    {
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println(cod_transaccion);
            Map[] datos=puerto.getdatosres15(cod_transaccion);
            if(datos!=null)
            {
                hoja_ruta=datos[0].get("HOJA_RUTA").toString();
                enviado_por=datos[0].get("USUARIO_SOL").toString();
                detalle=datos[0].get("DET").toString();
                preventivo=datos[0].get("COD_PREVENTIVO").toString();
                monto=datos[0].get("TOTAL").toString();
                //cargo=datos[0].get("CARGO").toString();
                sol_compra=datos[0].get("SOL").toString();
            }
            else
                System.out.println("nookokokok");
            
        }catch(Exception e)
        {
            
        }
    }
    private String obtenerpartidas()
    {
        String t="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getPartidas1(cod_transaccion);
            if(datos!=null)
            {
                System.out.println("nro de partidas "+datos.length);
                for(int i=0;i<datos.length;i++)
                {
                    t=t+", "+datos[i].get("PARTIDA").toString()+"-"+datos[i].get("DETALLE").toString()+"";
                }
            }
        }catch(Exception e)
        {
            
        }
        return t;
    }
    private String total_dias()
    {
        String t="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getDias(cod_trans_nro);
            t=datos[0].get("DIAS").toString();
        }catch(Exception e)
        {
            
        }
        return t;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jTextField6 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jCheckBox9 = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Fecha2 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        jLabel2.setText("CARTA Nº");

        jLabel5.setText("DESTINO:");

        jLabel6.setText("OBJETIVO:");

        jLabel7.setText("COTIZACION:");

        jLabel4.setText("CARGO:");

        jLabel10.setText("FECHA");

        jTextField1.setToolTipText("EJEMPLO: 162/2015 ");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel8.setText("DPTO. INFRAEST. N° 095/15 ");

        jLabel11.setText("mejoras del wifi");

        jLabel12.setText("obtener una mayor ancho de banda");

        jLabel13.setText("CS-038/15");

        jLabel9.setText("Reformulacion de Poa y Presupuesto");

        jLabel14.setText("10 de junio al 27 de agosto de 2015");

        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RESOLUCION MAYOR A 15 DIAS");

        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("EJEMPLOS");

        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("DOCUMENTOS A PRESENTAR");

        jCheckBox1.setText("Certificado del RUPE (Registro unico de Proveedores del Estado)");

        jCheckBox2.setText("Certificado de No adeudo a las AFPs Futuro y Prevision");

        jCheckBox3.setText("Fotocopia de Cedula de Identidad");

        jCheckBox4.setText("Poder del Representante Legal (si corresponde en fotocopia)");

        jCheckBox5.setText("NIT (fotocopia)");

        jCheckBox6.setText("Licencia de funcionamiento otorgada por la Policia Nacional (fotocopia)");

        jCheckBox7.setText("Licencia de funcionamiento otorgada por el GAMLP (Alcaldia) fotocopia");

        jCheckBox8.setText("Curriculum vitae y documento que respalde copia legalzada");

        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/accept.png"))); // NOI18N
        jButton1.setText("GENERAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        jButton2.setText("SALIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/printer.png"))); // NOI18N
        jButton3.setText("IMPRIMIR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk (2).png"))); // NOI18N
        jButton5.setText("GUARDAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/action_refresh_blue.gif"))); // NOI18N
        jButton4.setText("ACTUALIZAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jCheckBox9.setText("....");
        jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox9ActionPerformed(evt);
            }
        });

        jLabel16.setText("FECHA HOJA RUTA");

        jLabel17.setText("12 DE JUNIO DE 2015");

        jLabel18.setText("FECHA DE LA NOTA");

        jLabel19.setText("12 DE JUNIO DE 2015");

        jLabel20.setText("CORRELATIVO");

        jLabel21.setText("COLOCAR DESPUES DE GENERAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox2)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox4)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox8))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel17))))
                        .addGap(46, 46, 46))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(Fecha2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13)
                            .addComponent(jCheckBox9))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(Fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addGap(10, 10, 10)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dias=total_dias();
        partida=obtenerpartidas();
        cite=jTextField1.getText();
        cargo=jTextField2.getText();
        fecha_hr=jTextField3.getText();
        fecha_nota=jTextField4.getText();
        //SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_ini=Fecha2.getText();
        destino=jTextArea1.getText();
        objetivo=jTextArea2.getText();
        cotizacion=jTextField6.getText();
        System.out.println(fec_ini);
        llena_campos();
        montocompleto();
        String doc=obtiene_doc();
        System.out.println(doc);
//        if(cite.equals("") || destino.equals("") || objetivo.equals("") || cotizacion.equals(""))
//        {
//            JOptionPane.showMessageDialog(null, "todos los campos deben ser llenados");
//        }
        //else
        //{
            try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("aqui uno "+gestion);
            System.out.println(fec_ini);

            puerto.generaRes15(cod_transaccion,cod_trans_nro,dias,cite,destino,objetivo,cotizacion,detalle,enviado_por,cargo,gestion,doc,fec_ini,fecha_hr,fecha_nota);

            System.out.println("aqui dos");
            llenadatos();
            }catch(Exception e)
            {
                System.out.println("aqui esta el error con un demonio "+e);
            }
        //}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        dias=total_dias();
        partida=obtenerpartidas();
        cite=jTextField1.getText();
        cargo=jTextField2.getText();
        fecha_hr=jTextField3.getText();
        fecha_nota=jTextField4.getText();
        //SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_ini=Fecha2.getText();
        //objetivo=jTextField5.getText();*/
        destino=jTextArea1.getText();
        objetivo=jTextArea2.getText();
        cotizacion=jTextField6.getText();
        
        llena_campos();
        montocompleto();
        String doc=obtiene_doc();
        if(cite.equals("") || destino.equals("") || objetivo.equals(""))
        {
            JOptionPane.showMessageDialog(null, "todos los campos deben ser llenados");
        }
        else
        {
            try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("la fecha "+fec_ini);
            System.out.println("esto es un clasico error jejeje "+fecha_hr+" esto "+fecha_nota+" "+cod_transaccion+" "+cod_trans_nro+" "+cite+","+destino+","+objetivo+","+cotizacion+","+doc+","+fec_ini+","+cargo+","+fecha_hr+","+fecha_nota);
            
            int correlativo=Integer.parseInt(jTextField5.getText());
            Map[] dato=puerto.verificando_Nro(correlativo,3);
            //Map[] dato=null;
            if(dato!=null)
            {
                if(Integer.parseInt(dato[0].get("COD_RESOLUCION").toString())==cod_resolucion)
                {
                    puerto.updateRes15(cod_transaccion,cod_trans_nro,cite,destino,objetivo,cotizacion,doc,fec_ini,cargo,fecha_hr,fecha_nota,correlativo);
                    llenadatos();
                }
                else
                {
                    javax.swing.JOptionPane.showMessageDialog(this,"ESTE CORRELATIVO YA EXISTE VERIFIQUE EL NRO DE CORRELATIVO","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
            {
                puerto.updateRes15(cod_transaccion,cod_trans_nro,cite,destino,objetivo,cotizacion,doc,fec_ini,cargo,fecha_hr,fecha_nota,correlativo);
                llenadatos();
            }
            }catch(Exception e)
            {
                System.out.println("este es el error al actualizar "+e);
            }
        }
        habilita();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(cod_w==1)
            jTextField6.setEnabled(true);
        jButton1.setEnabled(false);
        Fecha2.setEnabled(true);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(true);
        jTextField4.setEnabled(true);
        jTextField3.setEnabled(true);
        jTextArea1.setEnabled(true);
        jTextArea2.setEnabled(true);
        jCheckBox9.setEnabled(true);
        jTextField5.setEnabled(true);
        bloquea_check(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        dias=total_dias();
        partida=obtenerpartidas();
        cite=jTextField1.getText();
        destino=jTextArea1.getText();
        objetivo=jTextArea2.getText();
        cotizacion=jTextField6.getText();
        cargo=jTextField2.getText();
        fecha_hr=jTextField3.getText();
        fecha_nota=jTextField4.getText();
        //SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        fec_ini=Fecha2.getText();
        llena_campos();
        montocompleto();
        String doc=obtiene_doc_imp();
        try{
            this.genera_res_15.reporte15(hoja_ruta,enviado_por,cargo,detalle,destino,objetivo,preventivo,monto,partida,sol_compra,cotizacion,proveedor,dias,cite,nro_res,cod_w,doc,fec_ini,fecha_hr,fecha_nota);
            //this.genera_res_15.reporte15(hoja_ruta,enviado_por,cargo,detalle,destino,objetivo,preventivo,monto,partida,sol_compra,cotizacion,proveedor,dias,cite,nro_res,cod_w,doc,fec_ini);
        }catch(Exception e)
        {
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private String obtiene_doc()
    {
        String x="";
        if(jCheckBox1.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox1.getText()+"#";
        }
        if(jCheckBox2.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox2.getText()+"#";
        }
        if(jCheckBox3.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox3.getText()+"#";
        }
        if(jCheckBox4.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox4.getText()+"#";
        }
        if(jCheckBox5.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox5.getText()+"#";
        }
        if(jCheckBox6.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox6.getText()+"#";
        }
        if(jCheckBox7.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox7.getText()+"#";
        }
        if(jCheckBox8.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox8.getText()+"#";
        }
        return x;
        
    }
    private String obtiene_doc_imp()
    {
        String x="";
        if(jCheckBox1.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox1.getText()+"<br/>";
        }
        if(jCheckBox2.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox2.getText()+"<br/>";
        }
        if(jCheckBox3.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox3.getText()+"<br/>";
        }
        if(jCheckBox4.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox4.getText()+"<br/>";
        }
        if(jCheckBox5.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox5.getText()+"<br/>";
        }
        if(jCheckBox6.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox6.getText()+"<br/>";
        }
        if(jCheckBox7.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox7.getText()+"<br/>";
        }
        if(jCheckBox8.getSelectedObjects()!=null)
        {
            x=x+"- "+jCheckBox8.getText()+"<br/>";
        }
        return x;
        
    }
    private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox9ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox9.getSelectedObjects()==null)
        {
            jTextField6.setEnabled(false);
        }
        else
        {
            jTextField6.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Fecha2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
