/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmTransacciones.java
 *
 * Created on 24-jun-2011, 17:12:59
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JLabel;
//import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import net.sf.nachocalendar.components.DateField;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.ItemCellEditor;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.TablaTransaccionBandeja;
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;
import umsa.capricornio.gui.transacciones.reporte.RepTransaccion;
import umsa.capricornio.gui.transacciones.reporte.Reportes;
import umsa.capricornio.gui.transacciones.tablas.TablaTransaccionEstados;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
import umsa.capricornio.utilitarios.herramientas.NumerosTextuales;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author henrry
 */
public class FrmAdquisiciones extends javax.swing.JInternalFrame {

    TablaTransaccionBandeja bandeja;
    TablaTransaccionEstados estados;
    int pasa=0,swdias=0,dia;
    FrmMenu menu;
    private Reportes reportes;
    int cod_usuario,cod_rol,gestion,x,tope,ini,fin,cod_almacen;
    private Runtime r;
    private int cod_tramite;
//    DiagOrdenesDetalle ordenes;
    /** Creates new form FrmTransacciones **/
    public FrmAdquisiciones(FrmMenu menu,int cod_usuario,int cod_rol,int gestion,int cod_almacen) {
        this.menu=menu;
        this.cod_usuario=cod_usuario;
        this.cod_rol=cod_rol;
        this.gestion=gestion;
        this.cod_almacen=cod_almacen;
        reportes = new Reportes(cod_almacen);
        initComponents();
        ConstruyeTablaTransacciones();
        ConstruyeTablaEstados();
    }
    
    private void ConstruyeTablaTransacciones(){
        bandeja = new TablaTransaccionBandeja();
        TblTransaccionBandeja.setAutoCreateColumnsFromModel(false);
        TblTransaccionBandeja.setModel(bandeja);

        for (int k = 0; k < TablaTransaccionBandeja.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaTransaccionBandeja.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            //TableCellEditor edit =new ItemCellEditor(null);
            TableColumn column = new TableColumn(k, TablaTransaccionBandeja.m_columns[k].m_width, renderer, null);
            //column.setMaxWidth(TablaTransaccionBandeja.m_columns[k].m_width);
            column.setMinWidth(TablaTransaccionBandeja.m_columns[k].m_width);
            //column.setResizable(true);
            
            TblTransaccionBandeja.addColumn(column);
        }
        JTableHeader header = TblTransaccionBandeja.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlTransaccionBandeja.getViewport().add(TblTransaccionBandeja);
    }
    
    private void ConstruyeTablaEstados(){
        estados = new TablaTransaccionEstados();
        TblTransaccionEstado.setAutoCreateColumnsFromModel(false);
        TblTransaccionEstado.setModel(estados);

        for (int k = 0; k < TablaTransaccionEstados.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaTransaccionEstados.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaTransaccionEstados.m_columns[k].m_width, renderer, null);
            TblTransaccionEstado.addColumn(column);
        }
        JTableHeader header = TblTransaccionEstado.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlTransaccionEstado.getViewport().add(TblTransaccionEstado);
    }
        
    private void LlenaBandeja(){        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("DENTRO DEL POPULAR LLENA BANDEJA --> cod_rol: "+cod_rol+"cod_almacen: "+cod_almacen+" cod_tramite: "+cod_tramite+"gestion: "+gestion+" ini: "+ini+" fin: "+fin);
            System.out.println("Lo que se le envia al query --> gestion: "+gestion+" cod_almacen:"+cod_almacen+" cod_usuario: "+cod_usuario+" cod_tramite: "+cod_tramite);
            Map[] datos=puerto.getTransaccionBandejaAdqui(gestion,cod_almacen,cod_usuario,cod_tramite);
            CerearTablaBandeja();
            
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    bandeja.insert(c);
                    TblTransaccionBandeja.tableChanged(new TableModelEvent(bandeja, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_W"),c,2);
                    TblTransaccionBandeja.setValueAt(datos[c].get("NRO_TRAMITE"),c,3);
                    TblTransaccionBandeja.setValueAt(datos[c].get("TIPO_TRAMITE"),c,4);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DETALLE"),c,5);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    TblTransaccionBandeja.setValueAt(datos[c].get("ESTADO"),c,8);
                    TblTransaccionBandeja.setValueAt(datos[c].get("CUANTIA"),c,9);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DEL"),c,10);
                    TblTransaccionBandeja.setValueAt(datos[c].get("HASTA"),c,11);
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
            
    void CerearTablaBandeja(){
        int f = TblTransaccionBandeja.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (bandeja.delete(i)) {
                TblTransaccionBandeja.tableChanged(new TableModelEvent(
                bandeja, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }    
    
    private void LlenaEstados(){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("DENTRO DEL POPULAR LLENA Estados --> cod_rol: "+cod_rol+"cod_almacen: "+cod_almacen+" cod_tramite: "+cod_tramite+"gestion: "+gestion+" ini: "+ini+" fin: "+fin);
            Map[] datos=puerto.getTransaccionEstado(cod_almacen,cod_tramite,gestion,ini,fin);
            CerearTablaEstados();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    estados.insert(c);
                    TblTransaccionEstado.tableChanged(new TableModelEvent(estados, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRAMITE"),c,2);
                    TblTransaccionEstado.setValueAt(datos[c].get("NRO"),c,3);
                    TblTransaccionEstado.setValueAt(datos[c].get("TRAMITE"),c,4);
                    TblTransaccionEstado.setValueAt(datos[c].get("DETALLE"),c,5);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    TblTransaccionEstado.setValueAt(datos[c].get("ESTADO"),c,8);
                    TblTransaccionEstado.setValueAt(datos[c].get("CUANTIA"),c,10);
                    TblTransaccionEstado.setValueAt(datos[c].get("DEL"),c,11);
                    TblTransaccionEstado.setValueAt(datos[c].get("HASTA"),c,12);
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }
    
    void CerearTablaEstados(){
        int f = TblTransaccionEstado.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (estados.delete(i)) {
                TblTransaccionEstado.tableChanged(new TableModelEvent(
                estados, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }
    
    private void CalculaElementosListar(){
       int nro_reg=5;
       try{ AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();                  
            Map[] datos= puerto.getNroTransacciones(gestion,cod_tramite,cod_almacen);            
            if (datos!=null){
                System.out.println("con datos");
                try{
                    nro_reg= Integer.parseInt(datos[0].get("LINENUM").toString());
                } catch (Exception e) {
                    System.out.println("Error al generar R.A.B :(");
                    nro_reg=5;  
                }
                
                /*for (int c=0;c<datos.length;c++){
                    nro_reg= Integer.parseInt(datos[c].get("LINENUM").toString());
                    System.out.println("nro_reg: "+nro_reg);
                    //nro_reg=5;
                }*/
            }
            else
                nro_reg=5;
            
            tope=nro_reg;
            ini=1;
            x=100;
            fin=ini+x-1;            
            //String ultin=cant_sol.substring(n_dig-2, n_dig);
            if (nro_reg<= x){
                BtnInicio.setEnabled(false);
                BtnAtras.setEnabled(false);
                BtnAdelante.setEnabled(false);
                BtnFinal.setEnabled(false);
            }
            else  {BtnInicio.setEnabled(false);
                   BtnAtras.setEnabled(false);
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }
    
    private String fecharestringida(int cod_trans_nro)
    {
      //JTextField xField = new JTextField(5);
      JTextField texto = new JTextField(60);
      JTextField field1 = new JTextField(5);
      Date xx=new Date();
      Date yy=new Date();
      DateField xfield=new DateField();
      DateField yfield=new DateField();
      xfield.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
      yfield.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
      xfield.setValue(xx);
      yfield.setValue(yy);
      JPanel myPanel = new JPanel();
      myPanel.add(new JLabel("Fecha de Inicio:"));
      myPanel.add(xfield);
      myPanel.add(new JLabel("Fecha de Final:"));
      myPanel.add(yfield);
      myPanel.add(new JLabel("Ingrese los dias:"));
      myPanel.add(field1);
      myPanel.add(new JLabel("INGRESE LA FORMA DE PAGO:"));
      myPanel.add(texto);
      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
      /*myPanel.add(new JLabel("y:"));
      myPanel.add(yField);*/
      String convertido,convertido1;
      int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "POR FAVOR INSERTE LAS FECHAS O LOS DIAS EN CASO DE TENER UN ESTIMADO", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        System.out.println("x value: " + field1.getText());
        if(field1.getText().equals(""))
        {
            System.out.println("siiiiiiiiiiiiiiiiiiiii");
        }
        if(!field1.getText().equals(""))
        {
            System.out.println("aqui aja");
            convertido=field1.getText();
        }
        else
        {
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            convertido = fecha.format(xfield.getValue());
            convertido1= fecha.format(yfield.getValue());
            System.out.println(convertido+" - "+convertido1);  
            try{
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                System.out.println("la fi="+xfield.getValue()+" la ff="+yfield.getValue());
                Map[] datos=puerto.fechasTramite(convertido,convertido1,cod_trans_nro);
                System.out.println("y value: " +datos[0].get("DIAS").toString());
                System.out.println("esto de aqui esta entrando correctamente");
                convertido=datos[0].get("DIAS").toString();
            }
            catch(Exception e)
            {
                System.out.println("esto es un error: "+e);
            }
        }
        
      }
      else
      {
        convertido=null;
      }
      if(!texto.getText().equals(""))
      {
          convertido=convertido+"#"+texto.getText();
      }
      //int dias=yfield.getValue()-xfield.getValue();
      return convertido;
    }
    
     private void verificadias(FrmMenu menu, int cod_almacen, int cod_trans_nro, int cod_rol, String tramite, int gestion, int cod_w, String origen, String detalle, String unidad_sol, String unidad_des, String nro, String cuantia, String del, String hasta)
    {
        //if(tab_habil==0)
           // {
                int t=15;
                String vf[]=null;
                try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getDias(cod_trans_nro);
            t=15;
            if(!datos[0].get("DIAS").toString().equals(""))
            { 
                System.out.println("ttttt ya tiene dias");
            }
            else
            {
                boolean sw12=true;
                do{
                    String resp=fecharestringida(cod_trans_nro);
                    vf=resp.split("#");
                    System.out.println("la fecha reconocida es: "+vf[0]);
                    //System.out.println("la fecha reconocida es: "+vf[1]);
                    resp=vf[0];
                    //String resp=null;
                //String resp=JOptionPane.showInputDialog("escribe el tiempo de entrega en caso de ser mayor a 15 dias\nsi es menor deje en blanco y click en aceptar");
                if(resp==null || vf[0].equals(""))
                {
                    //this.setVisible(false);
                    this.setVisible(true);
                    //BtnSalir1.doClick();
                    System.err.println("jajaja si detecto el cancel");
                    pasa=-1;
                    return;
                    
                }
                int num=Integer.parseInt(resp);
                if(num<=15)
                {
                    System.out.println("s 15");
                    t=num;
                }
                else{
                    try{
                    t=Integer.parseInt(resp);
                    System.out.println("ttttt "+t);
                    sw12=true;
                    }
                    catch(Exception e){JOptionPane.showMessageDialog (null, "El dato digitado no es un número", "Error", JOptionPane.ERROR_MESSAGE);
                    sw12=false;}
                }
                }while(sw12==false);
                int res1;
                if(t>15)
                {
                    res1 = javax.swing.JOptionPane.showConfirmDialog(this, "Esta seguro de que el tiempo de duracion se "+t+" dias",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
                }
                else
                {
                    res1 = javax.swing.JOptionPane.showConfirmDialog(this, "Esta seguro de que el tiempo de duracion se "+t+" dias",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
                    /*res1 = javax.swing.JOptionPane.showConfirmDialog(this, "si los dias entre las fechas es menor a 15 dias se colocara automaticamente 15 \nEsta seguro de que el tiempo de duracion se "+t+" dias",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);*/
                }
                
                if (res1 != javax.swing.JOptionPane.YES_OPTION) {
                    pasa=-1;
                    return;
                }
                if(vf.length==1)
                {
                    JOptionPane.showMessageDialog (null, "No Ingreso la Forma de Pago es Necesario Ingresar el Dato", "Error", JOptionPane.ERROR_MESSAGE);
                    //Map[] datos1=puerto.setDias(cod_trans_nro,t,null);
                }
                else
                {
                    Map[] datos1=puerto.setDias(cod_trans_nro,t,vf[1]);
                }
            }
        }
        catch(Exception e)
        { System.err.println("no rayoa!!!!!! "+e);}
              try{
              if(cod_w==6 && cuantia.equals("COMPRA MENOR") && t<=15)
              {
                  int cod_alternativo=cod_w;
              }
              }catch(Exception e)
              {}
        //}
    }
        
    void AbreItems(){
        int fila = TblTransaccionBandeja.getSelectedRow();
        int cod_trans_nro = Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 0).toString());        
        //menu.AbrirOtroFrame(this, new FrmOrdenesDetalle(menu, this,cod_almacen, cod_trans_nro,cod_rol,  TblTransaccionBandeja.getValueAt(fila, 4).toString(), gestion, Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString()),TblTransaccionBandeja.getValueAt(fila, 1).toString(),TblTransaccionBandeja.getValueAt(fila, 5).toString(),TblTransaccionBandeja.getValueAt(fila, 6).toString(),TblTransaccionBandeja.getValueAt(fila, 7).toString()));
        String cuantia=TblTransaccionBandeja.getValueAt(fila, 9).toString();
        /*if(!cuantia.equals("ANPE"))
        {*/
            verificadias(menu,cod_almacen, cod_trans_nro,cod_rol,  TblTransaccionBandeja.getValueAt(fila, 4).toString(), gestion, Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString()),TblTransaccionBandeja.getValueAt(fila, 1).toString(),TblTransaccionBandeja.getValueAt(fila, 5).toString(),TblTransaccionBandeja.getValueAt(fila, 6).toString(),TblTransaccionBandeja.getValueAt(fila, 7).toString(),TblTransaccionBandeja.getValueAt(fila, 3).toString(),TblTransaccionBandeja.getValueAt(fila, 9).toString(),TblTransaccionBandeja.getValueAt(fila, 10).toString(),TblTransaccionBandeja.getValueAt(fila, 11).toString());
        //}
        System.out.println("la cuantia: "+cuantia);
        if(pasa==-1 && !cuantia.equals("ANPE"))
        {
            System.out.println("no tiene que abrir");
            pasa=0;
        }else
        {
            DiagOrdenesDetalle ordenes= new DiagOrdenesDetalle(this,menu,cod_almacen, cod_trans_nro,cod_rol,  TblTransaccionBandeja.getValueAt(fila, 4).toString(), gestion, Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString()),TblTransaccionBandeja.getValueAt(fila, 1).toString(),TblTransaccionBandeja.getValueAt(fila, 5).toString(),TblTransaccionBandeja.getValueAt(fila, 6).toString(),TblTransaccionBandeja.getValueAt(fila, 7).toString(),TblTransaccionBandeja.getValueAt(fila, 3).toString(),TblTransaccionBandeja.getValueAt(fila, 9).toString(),TblTransaccionBandeja.getValueAt(fila, 10).toString(),TblTransaccionBandeja.getValueAt(fila, 11).toString(),this.cod_usuario,false);
            ordenes.AbreDialogo();
            pasa=0;
        }
        
//        ordenes.setVisible(true);
        System.out.println("Se cerro la ventanita");
        BtnActualizar.doClick();
    }
    
    void CerrarFrame(){
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
    public int verificaresolucion(int cod_trans_nro)
    {
        GetResAdj genera_res_15 = new GetResAdj();
        String hoja_ruta = null;
        String enviado_por = null;
        String cargo;
        String detalle = null;
        String destino;
        String objetivo;
        String preventivo = null;
        String monto = null;
        String partida = null;
        String sol_compra = null;
        String cotizacion;
        String proveedor = null;
        String dias = null;
        String cite;
        String nro_res;
        String fec_ini;
        String fec_hr = null;
        String fec_nota = null;
        String documentos;
        int cod_w = 0;
        int x=0;
        String t="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getTransaccionNro(cod_trans_nro);
            int cod_transaccion=Integer.parseInt(d[0].get("COD_TRANSACCION").toString());
            Map[] datos=puerto.getPartidas1(cod_transaccion);
            Map[] dd=puerto.getDias(cod_trans_nro);
            Map[] dat = puerto.getProponenteADJ(cod_trans_nro);
            dias=dd[0].get("DIAS").toString();
            proveedor=(dat[0].get("NOMBRE_COMERCIAL").toString());
            System.out.println("datos son :"+datos);
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
            System.out.println("errorers que no esperaba "+e);
        }
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getTransaccionNro(cod_trans_nro);
            System.out.println("cod_tn"+cod_trans_nro);
            int cod_transaccion=Integer.parseInt(d[0].get("COD_TRANSACCION").toString());
            System.out.println("cod_tn"+cod_trans_nro+" cod_T"+cod_transaccion);
            Map[] datos=puerto.getresmayN(cod_transaccion, cod_trans_nro);
            System.out.println("parece que esta aqui el error "+datos);
            if(datos!=null)
            {
                /*cite=datos[0].get("CITE").toString();
                destino=datos[0].get("DESTINO").toString();
                cargo=datos[0].get("CARGO").toString();
                objetivo=datos[0].get("OBJETIVO").toString();
                cotizacion=datos[0].get("COTIZACION").toString();
                nro_res=datos[0].get("NRO").toString();
                documentos=datos[0].get("DOCUMENTOS").toString();
                fec_ini=datos[0].get("FECHA_INICIO_CON").toString();*/
                
                System.out.println("uno");
                cite=(datos[0].get("CITE").toString());
                System.out.println("dos");
                cargo=(datos[0].get("CARGO").toString());
                System.out.println("tres");
                fec_hr=(datos[0].get("DETALLE_NOTA_SOLICITUD").toString());
                System.out.println("cuatro");
                fec_nota=(datos[0].get("DETALLE_NOTA_PRESUPUESTO").toString());
                System.out.println("cinco");
                /*SimpleDateFormat formatoDeFecha = new SimpleDateFormat("MM/dd/yyyy");
                String strf=(datos[0].get("FECHA_INICIO_CON").toString());
                System.out.println("esto es str "+strf);
                fec_ini=(formatoDeFecha.parse(strf));*/
                fec_ini=datos[0].get("FECHA_INICIO_CON").toString();
                destino=(datos[0].get("DESTINO").toString());
                objetivo=(datos[0].get("OBJETIVO").toString());
                cotizacion=(datos[0].get("COTIZACION").toString());
                nro_res=datos[0].get("NRO").toString();
                documentos=datos[0].get("DOCUMENTOS").toString();
                System.out.println("hasta aqui si pasa");
                
                partida=t;
                //*********
                Map[] datos1=puerto.getdatosres15(cod_transaccion);
                if(datos1!=null)
                {
                    hoja_ruta=datos1[0].get("HOJA_RUTA").toString();
                    enviado_por=datos1[0].get("USUARIO_SOL").toString();
                    detalle=datos1[0].get("DET").toString();
                    preventivo=datos1[0].get("COD_PREVENTIVO").toString();
                    monto=datos1[0].get("TOTAL").toString();
                    /*fec_hr=datos1[0].get("DETALLE_NOTA_SOLICITUD").toString();
                    fec_nota=datos1[0].get("DETALLE_NOTA_PRESUPUESTO").toString();*/
                    //cargo=datos[0].get("CARGO").toString();
                    sol_compra=datos1[0].get("SOL").toString();
                }
                else
                    System.out.println("nookokokok");
                //monto=monto+" ("+TotalTexto(monto).toLowerCase()+" Bolivianos)";
                String doc=obtiene_doc_imp(documentos);
                try{
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
                    //genera_res_15.reporte15(hoja_ruta,enviado_por,cargo,detalle,destino,objetivo,preventivo,monto,partida,sol_compra,cotizacion,proveedor,dias,cite,nro_res,cod_w,doc,fec_ini,fec_hr,fec_nota);
                    reportes.MostrarOrdenx(cod_trans_nro, "ADQ", 2, "FORM. DE ADJUDICACION",doc,nro_res);
                    x=1;
                }catch(Exception e)
                {
            
                }
            }
            else
            {
                System.out.println("esto");
                Map[] datos1=puerto.getDias(cod_trans_nro);
                if(!datos1[0].get("DIAS").toString().equals(""))
                { 
                    System.out.println("ttttt ya tiene dias "+datos1[0].get("DIAS").toString());
                    String nh=datos1[0].get("DIAS").toString();
                    dia=Integer.parseInt(nh);
                    swdias=1;
                }
            }
        }catch(Exception e)
        {
            System.out.println("aqui error"+e);
        }
        return x;
    }
    public String obtiene_doc_imp(String d)
    {
        d=d.replaceAll("#", "<br/>");
        return d;
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
    void MostrarReporte(int cod_trans_nro,String cod_estado,int cod_tramite,String titulo){
        
                System.out.println("asdasdasdasdasdddddddddddd");
                System.out.println("cod_trans_nro: "+cod_trans_nro);
                System.out.println("cod_estado: "+cod_estado);
                System.out.println("cod_tramite: "+cod_tramite);
                System.out.println("titulo: "+titulo);
                List list=new ArrayList();
                try{
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    Map[] datos=puerto.getReporteOrden(cod_trans_nro,cod_estado,cod_tramite);

                    if (datos!=null){                 
                        Map map = new HashMap();
                        int i=0;
                        String cod_trans_detalle,aux = null;
                        for (int f=0;f<datos.length;f++){
                            Transaccion trans = new Transaccion();
                            trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                            trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                            trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                            trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                            trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                            trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                            trans.setDetalle(datos[f].get("DETALLE").toString());
                            trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                            trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                            trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                            trans.setArticulo(datos[f].get("ARTICULO").toString());
                            trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());
                            //hoja_ruta,cbte,casa_comercial,direccion,telefono,nit,precio_unit
                            trans.setHoja_ruta(datos[f].get("HOJA_RUTA").toString());
                            trans.setCbte(datos[f].get("CBTE").toString());
                            trans.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());
                            trans.setDireccion(datos[f].get("DIRECCION").toString());
                            trans.setTelefono(datos[f].get("TELEFONO").toString());
                            trans.setNit(datos[f].get("NIT").toString());
                            trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                            trans.setPartida(datos[f].get("PARTIDA").toString());
                            trans.setObs(datos[f].get("OBS").toString());
                            //trans.setSubtotal(Double.parseDouble(datos[f].get("SUBTOTAL").toString()));

                            /*trans.setNro_orden_compra(Integer.parseInt(datos[f].get("NRO_ORDEN_COMPRA").toString()));
                            if (!(datos[f].get("FEC_ORDEN_COMPRA")==null || "".equals(datos[f].get("FEC_ORDEN_COMPRA"))))
                                trans.setFec_orden_compra(i_formatterDate.i_conveterStandardToDate(datos[f].get("FEC_ORDEN_COMPRA").toString()));
                            trans.setFactura(datos[f].get("FACTURA").toString());
                            if (!(datos[f].get("FECHA_FACT")==null || "".equals(datos[f].get("FECHA_FACT"))))
                                trans.setFecha_fact(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_FACT").toString()));
                            trans.setMemo(datos[f].get("MEMO").toString());*/
                            trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));
                            //trans.setCod_trans_detalle(datos[f].get("COD_TRANS_DETALLE").toString());
                            cod_trans_detalle=datos[f].get("COD_TRANS_DETALLE").toString();
                            if(!cod_trans_detalle.equals(aux)){
                                i++;
                                System.out.println("El indice es wujuuu: "+i);
                                trans.setIndice(String.valueOf(i));
                            }
                            aux=cod_trans_detalle;
                            trans.setCod_trans_detalle(cod_trans_detalle);
                            list.add(trans);
                        } 
                        RepTransaccion rep = new RepTransaccion();
                        System.out.println("titulo: "+titulo);
                        System.out.println("cod_tramite: "+cod_tramite);
                        System.out.println("cod_trans_nro: "+cod_trans_nro);

                        rep.Reporte(list,titulo,cod_tramite,cod_trans_nro,cod_almacen);
                    }                         
                    /*for (int i = 0; i < list.size(); i++) {
                        Transaccion aux = (Transaccion) list.get(i);t           
                        System.out.println(aux.getNro_gestion()+" "+aux.getFecha_creacion()+" "+ aux.getFecha_envio()+" "+aux.getUnidad_sol()+" "+aux.getUnidad_des()+" "+aux.getUsuario_sol()+" "+aux.getUnidad_medida()+" "+aux.getCantidad_pedido()+" "+aux.getTipo_item()+" "+aux.getArticulo()+" "+aux.getDetalle_solicitud());
                    }  */      
                }
                catch (RemoteException e){
                    javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                catch (ServiceException e){ System.out.println("error 1:"+e);} 
                catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(this,"LA ORDEN NO FUE APROBADA O \n NO ELIJIO UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                }  
        
    }
    
    void MostrarReporte(){
        int fila=TblTransaccionEstado.getSelectedRow();
        int cod_trans_nro=Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 0).toString()); 
        String cod_estado=TblTransaccionEstado.getValueAt(fila, 1).toString();
        //String cod_estado="ALM1";        
        String titulo=TblTransaccionEstado.getValueAt(fila, 4).toString();
        //if ((cod_rol==2 && cod_tramite==3)|| (cod_rol==5 && cod_tramite==2))
            System.out.println(":D :D :D cod_trans: "+cod_trans_nro+" cod_estado: "+cod_estado+" cod_tramite: "+cod_tramite+" titulo: "+titulo);
            int xxxx=verificaresolucion(cod_trans_nro);
            //xxxx=2;
        if(xxxx==1)
        {
            
        }
        else{
            if(swdias==1 && dia>15)
            {
                System.out.println("su resolucion aun no a sido generada por sistema");
            JOptionPane.showMessageDialog(null, "su resolucion aun no ha sido generada por sistema debido a que no es necesario imprimir la orden de compra");
                //swdias=0;
            }
            else
            {
                reportes.MostrarOrden(cod_trans_nro, "ADQ", 2, "ORDEN DE COMPRA Y/U ORDEN DE SERVICIO");
            }
            
        }
            //MostrarReporte(cod_trans_nro, cod_estado, cod_tramite, titulo);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnSalir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        PnlTransaccionBandeja = new javax.swing.JScrollPane();
        TblTransaccionBandeja = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        BtnActualizar = new javax.swing.JButton();
        BtnOrdenes = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        PnlTransaccionEstado = new javax.swing.JScrollPane();
        TblTransaccionEstado = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BtnInicio = new javax.swing.JButton();
        BtnAtras = new javax.swing.JButton();
        BtnAdelante = new javax.swing.JButton();
        BtnFinal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        JTF_nroOrden = new javax.swing.JTextField();
        JTF_hojaRuta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(104, 129, 156));
        setClosable(true);
        setTitle("ESTADO DE TRANSACCIONES Y BANDEJA DE ENTRADA");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(null);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir);
        BtnSalir.setBounds(510, 640, 150, 28);

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setLayout(null);

        TblTransaccionBandeja.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblTransaccionBandeja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblTransaccionBandejaMousePressed(evt);
            }
        });
        TblTransaccionBandeja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TblTransaccionBandejaKeyPressed(evt);
            }
        });
        PnlTransaccionBandeja.setViewportView(TblTransaccionBandeja);

        jPanel4.add(PnlTransaccionBandeja);
        PnlTransaccionBandeja.setBounds(20, 60, 1100, 480);

        jPanel1.setBackground(new java.awt.Color(185, 203, 221));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        BtnActualizar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnActualizar.setForeground(new java.awt.Color(42, 78, 42));
        BtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/action_refresh_blue.gif"))); // NOI18N
        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnActualizar);
        BtnActualizar.setBounds(30, 10, 120, 28);

        BtnOrdenes.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnOrdenes.setForeground(new java.awt.Color(0, 102, 153));
        BtnOrdenes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnOrdenes.setText("Crear Ordenes");
        BtnOrdenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOrdenesActionPerformed(evt);
            }
        });
        jPanel1.add(BtnOrdenes);
        BtnOrdenes.setBounds(170, 10, 140, 28);

        jPanel4.add(jPanel1);
        jPanel1.setBounds(20, 10, 1100, 50);

        jTabbedPane1.addTab("Borrador", jPanel4);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(null);

        TblTransaccionEstado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblTransaccionEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblTransaccionEstadoMousePressed(evt);
            }
        });
        TblTransaccionEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TblTransaccionEstadoKeyPressed(evt);
            }
        });
        PnlTransaccionEstado.setViewportView(TblTransaccionEstado);

        jPanel3.add(PnlTransaccionEstado);
        PnlTransaccionEstado.setBounds(10, 80, 1120, 440);

        jPanel2.setBackground(new java.awt.Color(185, 203, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_first.png"))); // NOI18N
        BtnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInicioActionPerformed(evt);
            }
        });
        jPanel2.add(BtnInicio);
        BtnInicio.setBounds(40, 30, 30, 28);

        BtnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_previous.png"))); // NOI18N
        BtnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAtrasActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAtras);
        BtnAtras.setBounds(70, 30, 30, 28);

        BtnAdelante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_next.png"))); // NOI18N
        BtnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAdelanteActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAdelante);
        BtnAdelante.setBounds(100, 30, 30, 28);

        BtnFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_last.png"))); // NOI18N
        BtnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFinalActionPerformed(evt);
            }
        });
        jPanel2.add(BtnFinal);
        BtnFinal.setBounds(130, 30, 30, 28);

        jLabel1.setText("Nro. Orden de Compra:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(230, 24, 140, 16);

        jLabel2.setText("Nro. Hoja de Ruta: ");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(540, 24, 120, 16);
        jPanel2.add(JTF_nroOrden);
        JTF_nroOrden.setBounds(370, 20, 110, 28);
        jPanel2.add(JTF_hojaRuta);
        JTF_hojaRuta.setBounds(650, 20, 100, 28);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/search_16.png"))); // NOI18N
        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(800, 5, 140, 28);

        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("MOSTRAR TODO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(960, 10, 140, 40);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/info_16.png"))); // NOI18N
        jButton3.setText("LIMPIAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(800, 30, 140, 28);

        jPanel3.add(jPanel2);
        jPanel2.setBounds(10, 20, 1120, 60);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/action_refresh_blue.gif"))); // NOI18N
        jButton4.setText("Modificar Tramite");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);
        jButton4.setBounds(400, 530, 190, 28);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/desktop.png"))); // NOI18N
        jButton5.setText("Cierre y observaciones");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);
        jButton5.setBounds(603, 528, 200, 30);

        jTabbedPane1.addTab("Enviados", jPanel3);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 50, 1150, 590);

        setBounds(0, 0, 1193, 706);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CerrarFrame();
    }//GEN-LAST:event_formInternalFrameClosed

    private void BtnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInicioActionPerformed
        ini=1;fin=ini+x-1;
        LlenaEstados();
        BtnAdelante.setEnabled(true);
        BtnFinal.setEnabled(true);
        BtnInicio.setEnabled(false);
        BtnAtras.setEnabled(false);
    }//GEN-LAST:event_BtnInicioActionPerformed

    private void BtnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAtrasActionPerformed
        ini=ini-x;
        fin=ini+x-1;
        BtnAdelante.setEnabled(true);
        BtnFinal.setEnabled(true);
        LlenaEstados();
        if (ini==1){
            BtnInicio.setEnabled(false);
            BtnAtras.setEnabled(false);
        }
    }//GEN-LAST:event_BtnAtrasActionPerformed

    private void BtnAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAdelanteActionPerformed
        ini=fin+1;
        fin=ini+x-1;
        BtnInicio.setEnabled(true);
        BtnAtras.setEnabled(true);
        LlenaEstados();
        if (fin>=tope){
            BtnAdelante.setEnabled(false);
            BtnFinal.setEnabled(false);
        }
    }//GEN-LAST:event_BtnAdelanteActionPerformed

    private void BtnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFinalActionPerformed
        ini=((tope/x)*x)+1;
        if (ini>tope)
            ini=tope-x+1;
        fin=ini+x-1;
        LlenaEstados();
        BtnInicio.setEnabled(true);
        BtnAtras.setEnabled(true);
        BtnFinal.setEnabled(false);
        BtnAdelante.setEnabled(false);
    }//GEN-LAST:event_BtnFinalActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if (cod_rol==4) cod_tramite=1;
        else if(cod_rol==5) cod_tramite=2;
        else if(cod_rol==2) cod_tramite=3;
        System.out.println("rol es: "+cod_rol+" y cod_tramite es:"+cod_tramite);
        CalculaElementosListar();       
    }//GEN-LAST:event_formInternalFrameOpened

    private void TblTransaccionBandejaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaMousePressed
        if (evt.getClickCount() == 2)
            AbreItems();
    }//GEN-LAST:event_TblTransaccionBandejaMousePressed

    private void TblTransaccionBandejaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            AbreItems();
        }
    }//GEN-LAST:event_TblTransaccionBandejaKeyPressed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        LlenaBandeja();
        LlenaEstados();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnOrdenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOrdenesActionPerformed
        //menu.AbrirOtroFrame(this, new FrmOrdenesGenera(menu, this, gestion,cod_almacen,cod_usuario ));
        
        DiagOrdenesGenera ordenes= new DiagOrdenesGenera(this,menu,gestion,cod_almacen,cod_usuario);
        this.setVisible(false);
        ordenes.AbreDialogo(); 
        LlenaBandeja();
    }//GEN-LAST:event_BtnOrdenesActionPerformed

    private void TblTransaccionEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblTransaccionEstadoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            MostrarReporte();
        }
    }//GEN-LAST:event_TblTransaccionEstadoKeyPressed

    private void TblTransaccionEstadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTransaccionEstadoMousePressed
        if (evt.getClickCount() == 2)
            MostrarReporte();
    }//GEN-LAST:event_TblTransaccionEstadoMousePressed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        LlenaBandeja();
        LlenaEstados();
    }//GEN-LAST:event_formComponentShown

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("Antes de intentar buscar .... :P");
            String nro = "%"+this.JTF_nroOrden.getText().trim()+"%";
            String hoja_ruta = "%"+this.JTF_hojaRuta.getText().trim()+"%";
            
            Map[] datos = puerto.busquedaAdquiOrdenC(gestion, cod_almacen, cod_tramite, nro, hoja_ruta,"","");
            CerearTablaEstados();
            
            if(datos !=null){
                System.out.println("Con datos :D :D :D ..... datos-length: "+datos.length);
                for (int c=0;c<datos.length;c++){
                    estados.insert(c);
                    TblTransaccionEstado.tableChanged(new TableModelEvent(estados, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRAMITE"),c,2);
                    TblTransaccionEstado.setValueAt(datos[c].get("NRO"),c,3);
                    TblTransaccionEstado.setValueAt(datos[c].get("TRAMITE"),c,4);
                    TblTransaccionEstado.setValueAt(datos[c].get("DETALLE"),c,5);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    TblTransaccionEstado.setValueAt(datos[c].get("ESTADO"),c,8);                    
                }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        LlenaEstados();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.JTF_hojaRuta.setText("");
        this.JTF_nroOrden.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Esta seguro de Corregir el tramite?\ntenga en cuenta que afecatara a :"
                + "\n - Solicitud de Compra\n"
                + "- Orden de Compra\n"
                + "- Pedido de Material\n"
                + "- Ingreso de Material",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        
        
        try {
            int fila = TblTransaccionEstado.getSelectedRow();
        int cod_trans_nro = Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 0).toString());
        //String tramite = TblTransaccionBandeja.getValueAt(fila, 4).toString();
        System.err.println("El cod_trans_nro: "+cod_trans_nro);
        String tramite = "Correccion Tramite";
        int cod_w = Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 2).toString());
        System.err.println("El cod_w: "+cod_w);
        String origen=TblTransaccionEstado.getValueAt(fila, 1).toString();
        System.err.println("El origen: "+origen);
        String detalle=TblTransaccionEstado.getValueAt(fila, 5).toString();
        System.err.println("El detalle: "+detalle);
        String unidad_sol=TblTransaccionEstado.getValueAt(fila, 6).toString();
        System.err.println("unidad_sol: "+unidad_sol);
        String unidad_des=TblTransaccionEstado.getValueAt(fila, 7).toString();
        System.err.println("unidad_des: "+unidad_des);
        String estadox=TblTransaccionEstado.getValueAt(fila, 8).toString();
        System.err.println("El estadox: "+estadox);
        String nro=TblTransaccionEstado.getValueAt(fila, 3).toString();
        System.err.println("El nro: "+nro);
        String cuantia=TblTransaccionEstado.getValueAt(fila, 10).toString();
        System.err.println("cuantia: "+cuantia);
        String del=TblTransaccionEstado.getValueAt(fila, 11).toString();
        System.err.println("del: "+del);
        String hasta=TblTransaccionEstado.getValueAt(fila, 12).toString();
        System.err.println("hasta: "+hasta);
//        System.err.println("Bueno el estado es: "+estadox);
        if(estadox.trim().equals("CONCLUIDO")){
            DiagOrdenesDetalle ordenes= new DiagOrdenesDetalle
            (this,menu,cod_almacen, cod_trans_nro,cod_rol,tramite,gestion,cod_w,origen,detalle,unidad_sol,unidad_des,nro,cuantia,del,hasta,this.cod_usuario,true);
            ordenes.AbreDialogo();
        }else{
            javax.swing.JOptionPane.showMessageDialog(this,"<html> SOLO PUEDE MODIFICAR TRAMITES CONCLUIDOS<br> ","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> DEBE PINTAR UNA FILA<br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
            int fila = TblTransaccionEstado.getSelectedRow();
            int cod_trans_nro = Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 0).toString());
            //String tramite = TblTransaccionBandeja.getValueAt(fila, 4).toString();
            System.err.println("El cod_trans_nro: "+cod_trans_nro);
            
            String tramite = "Correccion Tramite";
            int cod_w = Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 2).toString());
            System.err.println("El cod_w: "+cod_w);
            String origen=TblTransaccionEstado.getValueAt(fila, 1).toString();
            System.err.println("El origen: "+origen);
            String detalle=TblTransaccionEstado.getValueAt(fila, 5).toString();
            System.err.println("El detalle: "+detalle);
            String unidad_sol=TblTransaccionEstado.getValueAt(fila, 6).toString();
            System.err.println("unidad_sol: "+unidad_sol);
            String unidad_des=TblTransaccionEstado.getValueAt(fila, 7).toString();
            System.err.println("unidad_des: "+unidad_des);
            String estadox=TblTransaccionEstado.getValueAt(fila, 8).toString();
            System.err.println("El estadox: "+estadox);
            String nro=TblTransaccionEstado.getValueAt(fila, 3).toString();
            System.err.println("El nro: "+nro);
            String cuantia=TblTransaccionEstado.getValueAt(fila, 10).toString();
            System.err.println("cuantia: "+cuantia);
            String del=TblTransaccionEstado.getValueAt(fila, 11).toString();
            System.err.println("del: "+del);
            String hasta=TblTransaccionEstado.getValueAt(fila, 12).toString();
            System.err.println("hasta: "+hasta);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getTransaccionNro(cod_trans_nro);
            int cod_transaccion = Integer.parseInt(d[0].get("COD_TRANSACCION").toString());
            Map[] datos=puerto.getresmay(cod_transaccion, cod_trans_nro);
            if(datos!=null)
            {
                System.out.println("esto si tiene datos");
                DiagOrdenesDetalleForm ordenes= new DiagOrdenesDetalleForm
                (this,menu,cod_almacen, cod_trans_nro,cod_rol,tramite,gestion,cod_w,origen,detalle,unidad_sol,unidad_des,nro,cuantia,del,hasta,this.cod_usuario,true,cod_transaccion);
                ordenes.AbreDialogo();
            }
            else
            {
                d=puerto.getResIni(cod_transaccion);
                if(d!=null)
                {
                    System.out.println("este es un anpe");
                    DiagOrdenesDetalleForm ordenes= new DiagOrdenesDetalleForm
                    (this,menu,cod_almacen, cod_trans_nro,cod_rol,tramite,gestion,cod_w,origen,detalle,unidad_sol,unidad_des,nro,cuantia,del,hasta,this.cod_usuario,true,cod_transaccion);
                    ordenes.AbreDialogo();
                }
                else
                {
                    System.out.println("no puedes ya que este tramite no necesita");
                    javax.swing.JOptionPane.showMessageDialog(this,"<html> ESTE TRAMITE NO NECESITA LA GENERACION DE ESTOS FORMULARIOS<br> ","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
        }
        catch(Exception e)
        {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> DEBE PINTAR UNA FILA<br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAdelante;
    private javax.swing.JButton BtnAtras;
    private javax.swing.JButton BtnFinal;
    private javax.swing.JButton BtnInicio;
    private javax.swing.JButton BtnOrdenes;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JTextField JTF_hojaRuta;
    private javax.swing.JTextField JTF_nroOrden;
    private javax.swing.JScrollPane PnlTransaccionBandeja;
    private javax.swing.JScrollPane PnlTransaccionEstado;
    private javax.swing.JTable TblTransaccionBandeja;
    private javax.swing.JTable TblTransaccionEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
