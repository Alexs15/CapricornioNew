/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JCheckBox;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.sf.nachocalendar.components.DateField;

/**
 *
 * @author UMSA-JES
 */
public class JD_FECH_ANPE extends javax.swing.JDialog {

    /**
     * Creates new form JD_FECH_ANPE
     */
    private int cod_trans_nro, cod_transaccion,cod_alternativo,cod_rol=-1;
    boolean sw_datos = true;
    public JD_FECH_ANPE(java.awt.Frame parent, boolean modal, int cod_trans_nro, int cod_transaccion) {
        super(parent, modal);
        initComponents();
//        EstiloFechaInput();
        System.out.println("esto de aqui si entro");
        this.cod_trans_nro = cod_trans_nro;
        this.cod_transaccion = cod_transaccion;
        LlenaFormulario();
        llenapasouno();
        //this.JT_CUCE.setText(getCuce());
        this.setLocationRelativeTo(null);
        //bloquea_check();
    }
    public JD_FECH_ANPE(java.awt.Frame parent, boolean modal, int cod_trans_nro, int cod_transaccion, int cod_roln) {
        super(parent, modal);
        initComponents();
//        EstiloFechaInput();
        System.out.println("esto de aqui si entro");
        this.cod_trans_nro = cod_trans_nro;
        this.cod_transaccion = cod_transaccion;
        cod_rol=cod_roln;
        LlenaFormulario();
        //llenapasouno();
        //this.JT_CUCE.setText(getCuce());
        this.setLocationRelativeTo(null);
        //bloquea_check();
    }
    private void llenapasouno()
    {
        SimpleDateFormat form = new SimpleDateFormat("MMM/dd/yyyy");
        SimpleDateFormat formS = new SimpleDateFormat("dd/MM/yyyy");
        try{
        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
        AdquiWS_PortType puerto = servicio.getAdquiWS();
        Map[] d=puerto.getResIni(cod_transaccion);
        if(d!=null)
        {
            
            JTlugar1.setText("EDIFICIO HOY");
            Fecha1.setEnabled(false);
            JTlugar1.setEnabled(false);
            jCheckBox1.setEnabled(false);
            Date f1=new Date();
            f1=form.parse(d[0].get("FECHA").toString());
            System.err.println("fecha jeje "+f1);
            Fecha1.setValue(f1);
            jCheckBox1.setText(form.format(f1));
            System.out.println(form.format(f1));
            jLabel22.setText(form.format(f1));
        }
        else
        {
            System.out.println("debe generar la resolucion de inicio");
        }
        System.err.println("siiiiii");
        }catch(Exception e){System.err.println("errorororor");}
    }
    private void bloquea_check()
    {
        jCheckBox1.setEnabled(false);
        jCheckBox2.setEnabled(false);
        jCheckBox3.setEnabled(false);
        jCheckBox4.setEnabled(false);
        jCheckBox5.setEnabled(false);
        jCheckBox6.setEnabled(false);
        jCheckBox7.setEnabled(false);
        jCheckBox8.setEnabled(false);
        jCheckBox9.setEnabled(false);
        jCheckBox10.setEnabled(false);
        jCheckBox11.setEnabled(false);
        jLabel21.setEnabled(false);
        jLabel22.setEnabled(false);
        jLabel23.setEnabled(false);
        jLabel24.setEnabled(false);
        jLabel25.setEnabled(false);
        jLabel26.setEnabled(false);
        jLabel27.setEnabled(false);
        jLabel28.setEnabled(false);
        jLabel29.setEnabled(false);
        jLabel30.setEnabled(false);
        jLabel31.setEnabled(false);
        jLabel32.setEnabled(false);
        
        Fecha2.setEnabled(false);
        Fecha3.setEnabled(false);
        Fecha4.setEnabled(false);
        Fecha5.setEnabled(false);
        
        hora1.setEnabled(false);
        hora2.setEnabled(false);
        
        JTlugar2.setEnabled(false);
        JTlugar3.setEnabled(false);
        JTlugar4.setEnabled(false);
        JTlugar5.setEnabled(false);
    }
    private void LlenaFormulario(){
        try {
            jCheckBox2.setEnabled(false);
            jCheckBox11.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jCheckBox4.setEnabled(false);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getCod_Trans(cod_transaccion);
            int cod_T_N=Integer.parseInt(d[0].get("COD_TRANS_NRO").toString());
            cod_alternativo=cod_T_N;
            Map[] datos = puerto.getRestriccionFechas(cod_T_N);
            SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat formSalida = new SimpleDateFormat("MMM/dd/yyyy");
            if (datos != null) {
                BloqueaInputs();
                for (int f = 0; f < datos.length; f++) {
//                    System.out.println(f+" -->" + datos[f].get("COD_RES_FEC_PRO").toString());
                    int ff=Integer.parseInt(datos[f].get("COD_TIPO_RESF").toString())-1;
                    System.out.println(f+" -->"+ff);
//                    System.out.println(f+" -->" + datos[f].get("FECHA_CONCLUSION").toString());
//                    System.out.println(f+" -->" + datos[f].get("LUGAR").toString());
                        
                    switch(ff){
                        case 0:
                            this.Fecha1.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar1.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel22.setText(formSalida.format(f1));
                                this.jCheckBox1.setText(formSalida.format(f2));
                                jCheckBox1.setEnabled(false);
                                jCheckBox1.setSelected(true);
                                System.out.println("f1 "+f1+" f2"+f2);
                            }
                            
                            break;
                        case 1:
                            this.Fecha2.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar2.setText(datos[f].get("LUGAR").toString());
                            jCheckBox11.setEnabled(true);
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel23.setText(formSalida.format(f1));
                                this.jCheckBox11.setText(formSalida.format(f2));
                                jCheckBox11.setEnabled(false);
                                jCheckBox11.setSelected(true);
                                System.out.println("f1 "+f1+" f2"+f2);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 2:
                            this.Fecha3.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar3.setText(datos[f].get("LUGAR").toString());
                            jCheckBox2.setEnabled(true);
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel24.setText(formSalida.format(f1));
                                this.jCheckBox2.setText(formSalida.format(f2));
                                jCheckBox2.setEnabled(false);
                                jCheckBox2.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 3:
                            this.Fecha4.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar4.setText(datos[f].get("LUGAR").toString());
                            this.hora1.setText(datos[f].get("HORA_INICIO").toString());
                            jCheckBox3.setEnabled(true);
                            //
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel25.setText(formSalida.format(f1));
                                this.jCheckBox3.setText(formSalida.format(f2));
                                jCheckBox3.setEnabled(false);
                                jCheckBox3.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 4:
                            this.Fecha5.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar5.setText(datos[f].get("LUGAR").toString());
                            this.hora2.setText(datos[f].get("HORA_INICIO").toString());
                            jCheckBox4.setEnabled(true);
                            //
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel26.setText(formSalida.format(f1));
                                this.jCheckBox4.setText(formSalida.format(f2));
                                jCheckBox4.setEnabled(false);
                                jCheckBox4.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 5:
                            this.Fecha6.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar6.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel27.setText(formSalida.format(f1));
                                this.jCheckBox5.setText(formSalida.format(f2));
                                jCheckBox5.setEnabled(false);
                                jCheckBox5.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 6:
                            this.Fecha7.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar7.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel28.setText(formSalida.format(f1));
                                this.jCheckBox10.setText(formSalida.format(f2));
                                jCheckBox10.setEnabled(false);
                                jCheckBox10.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 7:
                            this.Fecha8.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar8.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel29.setText(formSalida.format(f1));
                                this.jCheckBox9.setText(formSalida.format(f2));
                                jCheckBox9.setEnabled(false);
                                jCheckBox9.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 8:
                            this.Fecha9.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar9.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel30.setText(formSalida.format(f1));
                                this.jCheckBox6.setText(formSalida.format(f2));
                                jCheckBox6.setEnabled(false);
                                jCheckBox6.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 9:
                            this.Fecha10.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar10.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel31.setText(formSalida.format(f1));
                                this.jCheckBox7.setText(formSalida.format(f2));
                                jCheckBox7.setEnabled(false);
                                jCheckBox7.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 10:
                            this.Fecha11.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            this.JTlugar11.setText(datos[f].get("LUGAR").toString());
                            if(datos[f].get("FECHA_CALIFICACION").toString().equals(""))
                            {
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                            }
                            else
                            {
                                Date f1=new Date();
                                Date f2=new Date();
                                f1=form.parse(datos[f].get("FECHA_CALIFICACION").toString());
                                f2=form.parse(datos[f].get("FECHA_ENTREGA").toString());
                                this.jLabel32.setText(formSalida.format(f1));
                                this.jCheckBox8.setText(formSalida.format(f2));
                                jCheckBox8.setEnabled(false);
                                jCheckBox8.setSelected(true);
                                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");
                            }
                            break;
                        case 11:
//                            this.Fecha_GI.setValue(new Date(datos[f].get("FECHA_INICIO").toString()));
  //                          this.Fecha_GF.setValue(new Date(datos[f].get("FECHA_CONCLUSION").toString()));
                            break;
                    }
                            
                }
               this.jButton1.setEnabled(false);
               
            }
            else{
                System.out.println("Vacio :0");
                bloquea_check();
                this.sw_datos = false;
            }
                
            
        } catch (Exception e) {
        }
    }
    private void BloqueaInputs(){
        llenapasouno();
        this.Fecha1.setEnabled(false);
        this.Fecha2.setEnabled(false);
        this.Fecha3.setEnabled(false);
        this.Fecha4.setEnabled(false);
        this.Fecha5.setEnabled(false);
        this.Fecha6.setEnabled(false);
        this.Fecha7.setEnabled(false);
        this.Fecha8.setEnabled(false);
        this.Fecha9.setEnabled(false);
        this.Fecha10.setEnabled(false);
        this.Fecha11.setEnabled(false);
//        this.Fecha_GI.setEnabled(false);
  //      this.Fecha_GF.setEnabled(false);
        /***************************/
        this.JTlugar1.setEnabled(false);
        this.JTlugar2.setEnabled(false);
        this.JTlugar3.setEnabled(false);
        this.JTlugar4.setEnabled(false);
        this.JTlugar5.setEnabled(false);
        this.JTlugar6.setEnabled(false);
        this.JTlugar7.setEnabled(false);
        this.JTlugar8.setEnabled(false);
        this.JTlugar9.setEnabled(false);
        this.JTlugar10.setEnabled(false);
        this.JTlugar11.setEnabled(false);
        /*******************************/
        this.hora1.setEnabled(false);
        this.hora2.setEnabled(false);
        //jCheckBox12.setEnabled(false);
        jCheckBox13.setEnabled(false);
        if(cod_rol==7)
        {
            jCheckBox7.setEnabled(true);
            jCheckBox8.setEnabled(true);
        }
        else
        {
            jCheckBox7.setEnabled(false);
            jCheckBox8.setEnabled(false);
        }
        
    }
    private void DesbloqueaInputs(){
        this.Fecha1.setEnabled(true);
        this.Fecha2.setEnabled(true);
        this.Fecha3.setEnabled(true);
        this.Fecha4.setEnabled(true);
        this.Fecha5.setEnabled(true);
        this.Fecha6.setEnabled(true);
        this.Fecha7.setEnabled(true);
        this.Fecha8.setEnabled(true);
        this.Fecha9.setEnabled(true);
        this.Fecha10.setEnabled(true);
        this.Fecha11.setEnabled(true);
//        this.Fecha_GI.setEnabled(true);
  //      this.Fecha_GF.setEnabled(true);
        /***************************/
        this.JTlugar1.setEnabled(true);
        this.JTlugar2.setEnabled(true);
        this.JTlugar3.setEnabled(true);
        this.JTlugar4.setEnabled(true);
        this.JTlugar5.setEnabled(true);
        this.JTlugar6.setEnabled(true);
        this.JTlugar7.setEnabled(true);
        this.JTlugar8.setEnabled(true);
        this.JTlugar9.setEnabled(true);
        this.JTlugar10.setEnabled(true);
        this.JTlugar11.setEnabled(true);
        /*******************************/
        this.hora1.setEnabled(true);
        this.hora2.setEnabled(true);
    }
    private void EstiloFechaInput(){
        Date hoy = new Date();
        Fecha1.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(hoy));
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Fecha1 = new net.sf.nachocalendar.components.DateField();
        Fecha2 = new net.sf.nachocalendar.components.DateField();
        Fecha4 = new net.sf.nachocalendar.components.DateField();
        Fecha11 = new net.sf.nachocalendar.components.DateField();
        Fecha7 = new net.sf.nachocalendar.components.DateField();
        Fecha8 = new net.sf.nachocalendar.components.DateField();
        Fecha9 = new net.sf.nachocalendar.components.DateField();
        Fecha10 = new net.sf.nachocalendar.components.DateField();
        Fecha3 = new net.sf.nachocalendar.components.DateField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Fecha5 = new net.sf.nachocalendar.components.DateField();
        Fecha6 = new net.sf.nachocalendar.components.DateField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        JTlugar1 = new javax.swing.JTextField();
        JTlugar2 = new javax.swing.JTextField();
        JTlugar3 = new javax.swing.JTextField();
        JTlugar4 = new javax.swing.JTextField();
        JTlugar5 = new javax.swing.JTextField();
        JTlugar6 = new javax.swing.JTextField();
        JTlugar7 = new javax.swing.JTextField();
        JTlugar8 = new javax.swing.JTextField();
        JTlugar9 = new javax.swing.JTextField();
        JTlugar10 = new javax.swing.JTextField();
        JTlugar11 = new javax.swing.JTextField();
        hora2 = new javax.swing.JTextField();
        hora1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jCheckBox13 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setBackground(new java.awt.Color(255, 0, 51));
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("1. Publicacion DBC SICOES y convocatoria a mesa de partes");

        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("2. Consultas escritas (*)");

        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("3. Reunion Informativa de Aclaracion (*)");

        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("6. Fecha limite de presentacion y apertura de proupuestas");

        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setText("7. Presentacion del informe de Evaluacion y recomendacion al RPA");

        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("8. Adjudicacion o Declaratoria Desierta");

        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("9. Notificacion de la adjudicacion o declaratoria desierta");

        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("10. Presentacion de documentos para la suscripcion del contrato");

        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("11. Suscripcion del contrato");

        Fecha1.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha1KeyPressed(evt);
            }
        });

        Fecha2.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha2StateChanged(evt);
            }
        });
        Fecha2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha2KeyPressed(evt);
            }
        });

        Fecha4.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha4StateChanged(evt);
            }
        });
        Fecha4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha4KeyPressed(evt);
            }
        });

        Fecha11.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha11.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha11StateChanged(evt);
            }
        });
        Fecha11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha11KeyPressed(evt);
            }
        });

        Fecha7.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha7StateChanged(evt);
            }
        });
        Fecha7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha7KeyPressed(evt);
            }
        });

        Fecha8.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha8StateChanged(evt);
            }
        });
        Fecha8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha8KeyPressed(evt);
            }
        });

        Fecha9.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha9StateChanged(evt);
            }
        });
        Fecha9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha9KeyPressed(evt);
            }
        });

        Fecha10.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha10.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha10StateChanged(evt);
            }
        });
        Fecha10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha10KeyPressed(evt);
            }
        });

        Fecha3.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha3StateChanged(evt);
            }
        });
        Fecha3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha3KeyPressed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(0, 51, 204));
        jLabel10.setText("--> 4. Aprobacion del DBC con las enmiendas si hubieran:");

        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setText("--> 5. Notificacion de aprobación del DBC:");

        Fecha5.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha5StateChanged(evt);
            }
        });
        Fecha5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha5KeyPressed(evt);
            }
        });

        Fecha6.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        Fecha6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Fecha6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Fecha6StateChanged(evt);
            }
        });
        Fecha6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Fecha6KeyPressed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("ACTIVIDAD");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("FECHA");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("LUGAR");

        JTlugar1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar9.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar10.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        JTlugar11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JTlugar11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTlugar11ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("HORA");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jCheckBox1.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox1.setText("CUMPLE");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox2.setText("CUMPLE");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox3.setText("CUMPLE");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox4.setText("CUMPLE");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox5.setText("CUMPLE");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox6.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox6.setText("CUMPLE");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        jCheckBox7.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox7.setText("CUMPLE");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        jCheckBox8.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox8.setText("CUMPLE");
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });

        jCheckBox9.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox9.setText("CUMPLE");
        jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox9ActionPerformed(evt);
            }
        });

        jCheckBox10.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox10.setText("CUMPLE");
        jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox10ActionPerformed(evt);
            }
        });

        jCheckBox11.setBackground(new java.awt.Color(204, 204, 204));
        jCheckBox11.setText("CUMPLE");
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });

        jLabel20.setText("F. CALIFICACION");

        jLabel21.setText("F. ENTREGO");

        jLabel22.setText("SIN FECHA");

        jLabel23.setText("SIN FECHA");

        jLabel24.setText("SIN FECHA");

        jLabel25.setText("SIN FECHA");

        jLabel26.setText("SIN FECHA");

        jLabel27.setText("SIN FECHA");

        jLabel28.setText("SIN FECHA");

        jLabel29.setText("SIN FECHA");

        jLabel30.setText("SIN FECHA");

        jLabel31.setText("SIN FECHA");

        jLabel32.setText("SIN FECHA");

        jCheckBox13.setText("jCheckBox13");
        jCheckBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jCheckBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jLabel12))
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)))
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Fecha4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha8, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha9, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha10, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha11, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Fecha3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(hora1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hora2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JTlugar9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                    .addComponent(JTlugar8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JTlugar1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addComponent(JTlugar11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JTlugar10, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jCheckBox7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jCheckBox11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jLabel22))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox11)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jLabel24))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox4)
                                    .addComponent(jLabel26))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox5)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox10)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox9)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JTlugar10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox7)
                                    .addComponent(jLabel31)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jLabel25)
                                    .addComponent(JTlugar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox8)
                                    .addComponent(jLabel32)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JTlugar11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addComponent(jLabel1)
                                        .addGap(7, 7, 7)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jCheckBox13))
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addGap(19, 19, 19))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel13)
                                                .addGap(19, 19, 19)
                                                .addComponent(Fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Fecha3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(11, 11, 11))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(Fecha4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(hora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(hora2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Fecha5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(16, 16, 16)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Fecha6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Fecha7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Fecha8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(Fecha9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Fecha10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Fecha11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10))
                            .addComponent(jSeparator3))))
                .addContainerGap())
        );

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 335, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(578, 578, 578))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void JTlugar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTlugar11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTlugar11ActionPerformed

    private void Fecha6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha6KeyPressed

    private void Fecha5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha5KeyPressed

    private void Fecha3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha3KeyPressed

    private void Fecha10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha10KeyPressed

    private void Fecha9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha9KeyPressed

    private void Fecha8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha8KeyPressed

    private void Fecha7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha7KeyPressed

    private void Fecha11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha11KeyPressed

    private void Fecha4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha4KeyPressed

    private void Fecha2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Fecha2KeyPressed

    private void Fecha1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Fecha1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //            TxtMemo.requestFocus();
        }
    }//GEN-LAST:event_Fecha1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("Aqui vamos a guardar las fechas si señor");
        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        //this.guarda_restriccion_fecha(cod_trans_nro, cod_trans_nro, null, null);
        
        String fecha1 = "'" + form.format(this.Fecha1.getValue()) + "'";
        String lugar1 = this.JTlugar1.getText();
        this.guarda_restriccion_fecha(1, cod_trans_nro, fecha1, lugar1);
//        System.out.println("La fecha1 es : "+fecha1+", El lugar es: "+lugar1);
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            String fecha2 = "'" + form.format(this.Fecha2.getValue()) + "'";
            String lugar2 = this.JTlugar2.getText();
            this.guarda_restriccion_fecha(2, cod_trans_nro, fecha2, lugar2);
        }
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            String fecha3 = "'" + form.format(this.Fecha3.getValue()) + "'";
            String lugar3 = this.JTlugar3.getText();
            this.guarda_restriccion_fecha(3, cod_trans_nro, fecha3, lugar3);
        }
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            String fecha4 = "'" + form.format(this.Fecha4.getValue()) + "'";
            String lugar4 = this.JTlugar4.getText();
            String hora1 = this.hora1.getText();
            this.guarda_restriccion_fecha3(4, cod_trans_nro,fecha4, lugar4, hora1);
        }
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            String fecha5 = "'" + form.format(this.Fecha5.getValue()) + "'";
            String lugar5 = this.JTlugar5.getText();
            String hora2 = this.hora2.getText();
            this.guarda_restriccion_fecha3(5, cod_trans_nro,fecha5, lugar5, hora2);
        }
        
        String fecha6 = "'" + form.format(this.Fecha6.getValue()) + "'";
        String lugar6 = this.JTlugar6.getText();
        this.guarda_restriccion_fecha(6, cod_trans_nro, fecha6, lugar6);
        
        String fecha7 = "'" + form.format(this.Fecha7.getValue()) + "'";
        String lugar7 = this.JTlugar7.getText();
        this.guarda_restriccion_fecha(7, cod_trans_nro, fecha7, lugar7);
        
        String fecha8 = "'" + form.format(this.Fecha8.getValue()) + "'";
        String lugar8 = this.JTlugar8.getText();
        this.guarda_restriccion_fecha(8, cod_trans_nro, fecha8, lugar8);
        
        String fecha9 = "'" + form.format(this.Fecha9.getValue()) + "'";
        String lugar9 = this.JTlugar9.getText();
        this.guarda_restriccion_fecha(9, cod_trans_nro, fecha9, lugar9);
        
        String fecha10 = "'" + form.format(this.Fecha10.getValue()) + "'";
        String lugar10 = this.JTlugar10.getText();
        this.guarda_restriccion_fecha(10, cod_trans_nro, fecha10, lugar10);
        
        String fecha11 = "'" + form.format(this.Fecha11.getValue()) + "'";
        String lugar11 = this.JTlugar11.getText();
        this.guarda_restriccion_fecha(11, cod_trans_nro, fecha11, lugar11);
        
        /*
        String fecha_GI = "'" + form.format(this.Fecha_GI.getValue()) + "'";
        String fecha_GF = "'" + form.format(this.Fecha_GF.getValue()) + "'";
        this.guarda_restriccion_fecha2(12, cod_trans_nro, fecha_GI,fecha_GF, "","");*/
        
        javax.swing.JOptionPane.showMessageDialog(this, "Las restricciones de tiempo se adicionaron correctamente", 
               "SYSTEM CAPRICORN",javax.swing.JOptionPane.INFORMATION_MESSAGE);
//        BloqueaInputs();
//        String fec_ing = "'" + form.format(CalFechaIng.getValue()) + "'";
//        System.out.println("La fecha1 es : "+fecha1+", El lugar es: "+lugar1);
//        System.out.println("La fecha2 es : "+fecha2+", El lugar es: "+lugar2);
//        System.out.println("La fecha3 es : "+fecha3+", El lugar es: "+lugar3);
//        System.out.println("La fecha4 es : "+fecha4+", El lugar es: "+lugar4);
//        System.out.println("La fecha5 es : "+fecha5+", El lugar es: "+lugar5);
//        System.out.println("La fecha6 es : "+fecha6+", El lugar es: "+lugar6);
//        System.out.println("La fecha7 es : "+fecha7+", El lugar es: "+lugar7);
//        System.out.println("La fecha8 es : "+fecha8+", El lugar es: "+lugar8);
//        System.out.println("La fecha9 es : "+fecha9+", El lugar es: "+lugar9);
//        System.out.println("La fecha10 es : "+fecha10+", El lugar es: "+lugar10);
//        System.out.println("La fecha11 es : "+fecha11+", El lugar es: "+lugar11);
       
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void guardaCuceSicoes(String cuce_sicoes){
        System.out.println("El cuce seria: "+cuce_sicoes);
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addCuceSicoes("SET-addCuceSicoes", cod_transaccion, cuce_sicoes);
        } catch (Exception e) {
        }
    }
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox1,jLabel22,1);
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox11,jLabel23,2);
    }//GEN-LAST:event_jCheckBox11ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox2,jLabel24,3);
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox3,jLabel25,4);
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox4,jLabel26,5);
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox5,jLabel27,6);
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox10ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox10,jLabel28,7);
    }//GEN-LAST:event_jCheckBox10ActionPerformed

    private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox9ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox9,jLabel29,8);
    }//GEN-LAST:event_jCheckBox9ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox6,jLabel30,9);
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox7,jLabel31,10);
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        // TODO add your handling code here:
        Verificacion_de_fechas(jCheckBox8,jLabel32,11);
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jCheckBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox13ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            Fecha2.setEnabled(true);
            JTlugar2.setEnabled(true);
            Fecha3.setEnabled(true);
            Fecha4.setEnabled(true);
            Fecha5.setEnabled(true);
            hora1.setEnabled(true);
            hora2.setEnabled(true);
            JTlugar3.setEnabled(true);
            JTlugar4.setEnabled(true);
            JTlugar5.setEnabled(true);
        }
        else
        {
            Fecha2.setEnabled(false);
            JTlugar2.setEnabled(false);
            Fecha3.setEnabled(false);
            Fecha4.setEnabled(false);
            Fecha5.setEnabled(false);
            hora1.setEnabled(false);
            hora2.setEnabled(false);
            JTlugar3.setEnabled(false);
            JTlugar4.setEnabled(false);
            JTlugar5.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox13ActionPerformed

    private void Fecha6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha6StateChanged
        // TODO add your handling code here:
        if(jCheckBox13.getSelectedObjects()!=null)
        {
            comp_fec(Fecha5, Fecha6);
        }
        else
        {
            comp_fec(Fecha1, Fecha6);
        }
    }//GEN-LAST:event_Fecha6StateChanged

    private void Fecha7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha7StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha6, Fecha7);
    }//GEN-LAST:event_Fecha7StateChanged

    private void Fecha8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha8StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha7, Fecha8);
    }//GEN-LAST:event_Fecha8StateChanged

    private void Fecha9StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha9StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha8, Fecha9);
    }//GEN-LAST:event_Fecha9StateChanged

    private void Fecha10StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha10StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha9, Fecha10);
    }//GEN-LAST:event_Fecha10StateChanged

    private void Fecha11StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha11StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha10, Fecha11);
    }//GEN-LAST:event_Fecha11StateChanged

    private void Fecha2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha2StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha1, Fecha2);
    }//GEN-LAST:event_Fecha2StateChanged

    private void Fecha3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha3StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha2, Fecha3);
    }//GEN-LAST:event_Fecha3StateChanged

    private void Fecha4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha4StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha3, Fecha4);
    }//GEN-LAST:event_Fecha4StateChanged

    private void Fecha5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Fecha5StateChanged
        // TODO add your handling code here:
        comp_fec(Fecha4, Fecha5);
    }//GEN-LAST:event_Fecha5StateChanged
    public void comp_fec(DateField Fec1,DateField Fec2)
    {
        DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
        String s =  df1.format(Fec1.getValue());
        String s1 =  df1.format(Fec2.getValue());
        int comp=compara_fechas(s, s1);
        if(comp!=0)
        {
            JOptionPane.showMessageDialog(null, "Debe introducir una fecha que no sea menor a la anterior");
            Fec2.setValue(Fec1.getValue());
        }
    }
    
    public int compara_fechas(String f1,String f2)
    {
        int res=-1;
        try {
            System.out.println(Fecha1.getValue().toString());
            System.out.println(Fecha2.getValue().toString());
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate1 = formateador.parse(f1);
            Date fechaDate2 = formateador.parse(f2);
            if(fechaDate1.before(fechaDate2))
            {
                res=0;
                System.out.println("Fecha1 es menor");
            }
            else
            {
                if(fechaDate2.before(fechaDate1))
                {
                    res=1;
                }
                else
                {
                    res=0;
                }
                
            }
        } catch (ParseException ex) {
            Logger.getLogger(JD_FECH_ANPE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    private void Verificacion_de_fechas(JCheckBox jc, JLabel jl,int num)
    {
        if(jc.getSelectedObjects()!=null){
            //String resp=JOptionPane.showInputDialog("Ingrsa la fecha que se ejecuto esta accion de la siguiente forma 24/04/2015 ");
            String resp=fecharestringida();
            System.out.println("es el resp: "+resp);
            if(resp==null)
            {
                jc.setSelected(false);
                return;
            }
            String [] date = resp.split("/");
            System.out.println("la cantidad "+date.length);
            if(date.length==3)
            {
                java.util.Date fecha = new Date();
                Date d=new Date();
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat salida = new SimpleDateFormat("MMM/dd/yyyy");
                String formattedDate = inputFormat.format(fecha);
                try{
                    d=salida.parse(resp);
                    jl.setText(resp);
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Usted no ingreso un formato correcto de fecha");
                    jc.setSelected(false);
                    return;
                }
                System.out.println (fecha);
                System.err.println(d);
                int xd=JOptionPane.showConfirmDialog(null, "esta seguro de colocar la fecha: "+resp);
                System.out.println(xd);
                if(xd==0)
                {
                    System.out.println(formattedDate+" hola");
                    Date xxxx=null;
                    try {
                        xxxx=inputFormat.parse(formattedDate);
                    } catch (ParseException ex) {
                        System.out.println("sjfhksdjfhkjdsfhksdjhfkjdshfkjds");
                        Logger.getLogger(JD_FECH_ANPE.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    jc.setText(salida.format(xxxx));
                    jc.setEnabled(false);
                    try {
                        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                        AdquiWS_PortType puerto = servicio.getAdquiWS();
                        System.out.println("resp"+resp+" formaterdate"+formattedDate+" codalter"+cod_alternativo+" num"+num);
                        puerto.fechasExtra(inputFormat.format(d),formattedDate,cod_alternativo,num);
                    } catch (Exception e) {
                        System.out.println("noooooooooooooooo :(");
                    }
                }
                else
                {
                    jc.setSelected(false);
                    return;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Usted no ingreso un formato correcto de fecha");
                jc.setSelected(false);
                return;
            }
        }
    }
    private String fecharestringida()
    {
        /*JTextField xField = new JTextField(5);
      JTextField yField = new JTextField(5);*/
      Date xx=new Date();
      DateField xfield=new DateField();
      xfield.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
      xfield.setValue(xx);
      JPanel myPanel = new JPanel();
      myPanel.add(new JLabel("Fecha de Entrega:"));
      myPanel.add(xfield);
      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
      /*myPanel.add(new JLabel("y:"));
      myPanel.add(yField);*/
      String convertido;
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "por favor inserte la fecha de entrega", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        System.out.println("x value: " + xfield.getValue());
        DateFormat fecha = new SimpleDateFormat("MMM/dd/yyyy");
        convertido = fecha.format(xfield.getValue());
        System.out.println(convertido);
         //System.out.println("y value: " + yField.getText());
      }else
      {
        convertido=null;
      }
    
        return convertido;
    }
    private String getCuce(){
        String cuce_sicoes = "";
//        System.out.println("el cod_transaccion: "+cod_transaccion);
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getCuceSicoes(cod_transaccion);
            if(datos != null){
//                System.out.println("--> "+datos[0].get("CUCE_SICOES").toString());
                cuce_sicoes = datos[0].get("CUCE_SICOES").toString();
            }else{
//                System.out.println("Vacio ...");
            }
          
        } catch (Exception e) {
        }
        return cuce_sicoes;
    }    
    /**
     * @param args the command line arguments
     */
    private void guarda_restriccion_fecha(int cod_tipo_resf,int cod_trans_nro , String fecha_final, String lugar){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addFechaRestriccion("SET-addFechaRestriccion", cod_tipo_resf, cod_trans_nro, fecha_final, lugar);
            puerto.fechasExtra(this.jCheckBox1.getText(), this.jLabel22.getText(), cod_trans_nro, 1);
            System.out.println("Exito Wujuuuuuuuuu");
        } catch (Exception e) {
            System.out.println("Error en el servidor ..... :(");
        }
    }
    private void guarda_restriccion_fecha3(int cod_tipo_resf,int cod_trans_nro , String fecha_final, String lugar,String hora_inicio){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addFechaRestriccion3("SET-addFechaRestriccion", cod_tipo_resf, cod_trans_nro, fecha_final, lugar, hora_inicio);
            System.out.println("Exito Wujuuuuuuuuu");
        } catch (Exception e) {
            System.out.println("Error en el servidor ..... :(");
        }
    }
     private void guarda_restriccion_fecha2(int cod_tipo_resf,int cod_trans_nro , String fecha_inicio ,String fecha_final, String lugar, String hora){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addFechaRestriccion2("SET-addFechaRestriccion", cod_tipo_resf, cod_trans_nro, fecha_inicio,fecha_final, lugar,hora);
            System.out.println("Exito Wujuuuuuuuuu");
        } catch (Exception e) {
            System.out.println("Error en el servidor ..... :(");
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
            java.util.logging.Logger.getLogger(JD_FECH_ANPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_FECH_ANPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_FECH_ANPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_FECH_ANPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_FECH_ANPE dialog = new JD_FECH_ANPE(new javax.swing.JFrame(), true,0,0);
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
    private net.sf.nachocalendar.components.DateField Fecha1;
    private net.sf.nachocalendar.components.DateField Fecha10;
    private net.sf.nachocalendar.components.DateField Fecha11;
    private net.sf.nachocalendar.components.DateField Fecha2;
    private net.sf.nachocalendar.components.DateField Fecha3;
    private net.sf.nachocalendar.components.DateField Fecha4;
    private net.sf.nachocalendar.components.DateField Fecha5;
    private net.sf.nachocalendar.components.DateField Fecha6;
    private net.sf.nachocalendar.components.DateField Fecha7;
    private net.sf.nachocalendar.components.DateField Fecha8;
    private net.sf.nachocalendar.components.DateField Fecha9;
    private javax.swing.JTextField JTlugar1;
    private javax.swing.JTextField JTlugar10;
    private javax.swing.JTextField JTlugar11;
    private javax.swing.JTextField JTlugar2;
    private javax.swing.JTextField JTlugar3;
    private javax.swing.JTextField JTlugar4;
    private javax.swing.JTextField JTlugar5;
    private javax.swing.JTextField JTlugar6;
    private javax.swing.JTextField JTlugar7;
    private javax.swing.JTextField JTlugar8;
    private javax.swing.JTextField JTlugar9;
    private javax.swing.JTextField hora1;
    private javax.swing.JTextField hora2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox13;
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
