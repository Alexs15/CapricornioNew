/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmTransacciones.java
 *
 * Created on 24-jun-2011, 17:12:59
 */
package umsa.capricornio.gui.transacciones;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.DiagOrdenesDetalle;
import umsa.capricornio.gui.transacciones.reporte.RepTransaccion;
import umsa.capricornio.gui.transacciones.reporte.Reportes;
import umsa.capricornio.gui.transacciones.tablas.TablaTransaccionBandejaGral;
import umsa.capricornio.gui.transacciones.tablas.TablaTransaccionEstados;
import umsa.capricornio.gui.transacciones.tablas.TablaTransaccionEstados1;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author henrryciño
 */
public class FrmTransacciones extends javax.swing.JInternalFrame {

    TablaTransaccionBandejaGral bandeja;
    TablaTransaccionEstados estados;
    TablaTransaccionEstados1 enviados;
    
    FrmMenu menu;
    Reportes reportes;
    //String cod_transaccion;
    int cod_usuario,cod_rol,gestion,x,tope,ini,fin,cod_almacen;
    private Runtime r;
    private int cod_tramite;
    /** Creates new form FrmTransacciones */
    public FrmTransacciones(FrmMenu menu,int cod_usuario,int cod_rol,int gestion,int cod_almacen) {
        System.out.println("Entrando");
        this.menu=menu;
        this.cod_usuario=cod_usuario;
        this.cod_rol=cod_rol;
        this.gestion=gestion;
        this.cod_almacen=cod_almacen;
        reportes = new Reportes(cod_almacen);
        initComponents();
        ConstruyeTablaTransacciones();
        ConstruyeTablaEstados();
        ConstruyeTablaEnviados();
    }
    
    private void ConstruyeTablaTransacciones(){
        bandeja = new TablaTransaccionBandejaGral();
        TblTransaccionBandeja.setAutoCreateColumnsFromModel(false);
        TblTransaccionBandeja.setModel(bandeja);

        for (int k = 0; k < TablaTransaccionBandejaGral.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaTransaccionBandejaGral.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaTransaccionBandejaGral.m_columns[k].m_width, renderer, null);
            System.out.println("Waooo");
            //column.setMinWidth(TablaTransaccionBandejaGral.m_columns[k].m_width);
            column.setMaxWidth(TablaTransaccionBandejaGral.m_columns[k].m_width);
            TblTransaccionBandeja.addColumn(column);
        }
        JTableHeader header = TblTransaccionBandeja.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        System.out.println("aqui lo coloca en el panel234 "+TablaTransaccionBandejaGral.m_columns.length);
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
    
    private void ConstruyeTablaEnviados()
    {
        enviados = new TablaTransaccionEstados1();
        TblEnviados.setAutoCreateColumnsFromModel(false);
        TblEnviados.setModel(enviados);

        for (int k = 0; k < TablaTransaccionEstados1.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaTransaccionEstados1.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaTransaccionEstados1.m_columns[k].m_width, renderer, null);
            //column.setMaxWidth(TablaTransaccionBandejaGral.m_columns[k].m_width);
            TblEnviados.addColumn(column);
        }
        JTableHeader header = TblEnviados.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        System.out.println("aqui lo coloca en el panel "+TablaTransaccionEstados1.m_columns.length);
        PnlEniviados.getViewport().add(TblEnviados);
    }
        
    private void LlenaBandeja(){
        String nro = null,Palabra = null,tipo_obligacion= null,cbte=null;
        /*if (!"".equals(TxtNroFFPL.getText())){
            try { int n = new Integer(TxtNroFFPL.getText());
                  nro="'"+TxtNroFFPL.getText()+"'";
            }
            catch (NumberFormatException e) {}
        }
        if (!"".equals(TxtDet.getText()))
                Palabra="'"+TxtDet.getText()+"'";

        if (!"".equals(TxtCb.getText()))
                cbte="'"+TxtCb.getText()+"'";

        if (!"- ELIJA UNA OPCION -".equals(CmbTO.getSelectedItem()))
                tipo_obligacion="'"+CmbTO.getSelectedItem().toString()+"'";*/
        try{
            //System.out.println("Aquí esta en presupuestos :D y los datos son: "+gestion+", "+cod_almacen+", "+cod_usuario+", "+cod_tramite);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.err.println("1).............. gestion: "+gestion+" cod_almacen: "+cod_almacen+" cod_usuario: "+cod_usuario+" cod_tramite"+cod_tramite);
            Map[] datos=puerto.getTransaccionBandeja(gestion,cod_almacen,cod_usuario,0,"alm",cod_tramite);            
            CerearTablaBandeja();
            if (datos!=null)
                for (int c=0;c<datos.length;c++){
                    bandeja.insert(c);
                    //System.out.println("******El cod"+datos[c].get("NRO_TRAMITE"));
                    TblTransaccionBandeja.tableChanged(new TableModelEvent(bandeja, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANS_NRO"),c,2);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_W"),c,3);
                    TblTransaccionBandeja.setValueAt(datos[c].get("NRO_TRAMITE"),c,4);
                    TblTransaccionBandeja.setValueAt(datos[c].get("TIPO_TRAMITE"),c,5);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DETALLE"),c,6);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_SOL"),c,7);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_DES"),c,8);
                    TblTransaccionBandeja.setValueAt(datos[c].get("ESTADO"),c,9);
                    TblTransaccionBandeja.setValueAt(datos[c].get("CUANTIA"),c,10);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DEL"),c,11);
                    TblTransaccionBandeja.setValueAt(datos[c].get("HASTA"),c,12);
                }            
          
            if (cod_rol==2){
                int n=TblTransaccionBandeja.getRowCount();
                /*===============================================================================================================
                 * cod_Tramite=2 porque el usuario de almacenes puede ver ordenes de compra al ingresar a almacen
                 ================================================================================================================*/
                System.out.println("2)............. gestion: "+gestion+" cod_almacen: "+cod_almacen+" cod_usuario: "+cod_usuario);
                datos=puerto.getTransaccionBandejaUnion(gestion,cod_almacen,3,cod_usuario);            
                if (datos!=null){
                    System.out.println("Siii"+datos.length);
                    for (int c=0;c<datos.length;c++){
                        bandeja.insert(n);
                        TblTransaccionBandeja.tableChanged(new TableModelEvent(bandeja, n, n, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                        TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANSACCION"),n,0);
                        TblTransaccionBandeja.setValueAt(datos[c].get("COD_ESTADO"),n,1);
                        TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANS_NRO"),n,2);
                        TblTransaccionBandeja.setValueAt(datos[c].get("COD_W"),n,3);
                        TblTransaccionBandeja.setValueAt(datos[c].get("NRO"),n,4);
                        TblTransaccionBandeja.setValueAt(datos[c].get("TIPO_TRAMITE"),n,5);
                        TblTransaccionBandeja.setValueAt(datos[c].get("DETALLE"),n,6);
                        TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_SOL"),n,7);
                        TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_DES"),n,8);
                        TblTransaccionBandeja.setValueAt(datos[c].get("ESTADO"),n,9);                        
                        TblTransaccionBandeja.setValueAt(datos[c].get("CUANTIA"),n,10);                        
                        TblTransaccionBandeja.setValueAt(datos[c].get("DEL"),n,11);                        
                        TblTransaccionBandeja.setValueAt(datos[c].get("HASTA"),n,12);                        
                    }          
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
    
    void CerearTablaEnviados(){
        int f = TblEnviados.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (enviados.delete(i)) {
                TblEnviados.tableChanged(new TableModelEvent(
                enviados, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }
    
    
    public void LlenaEnviados()
    {
        if(this.cod_rol!=2)
        {
            System.out.println("esto es ppto");
            try{
            //String []tram=CmbTramite.getSelectedItem().toString().split(" - ");
            //int cod_tram=Integer.parseInt(tram[0]);
            System.err.println("esta entrando en el rol 0");
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("Wujuuuu --->>>>   Ini: "+ini+", Fin: "+fin);
            Map[] datos=puerto.getTransaccionEstado(cod_almacen,1,gestion,0,100000);
            CerearTablaEnviados();
            if (datos!=null){
                //System.err.println("esta entrando en el rol 0");
                for (int c=0;c<datos.length;c++){
                    
                    enviados.insert(c);
                    TblEnviados.tableChanged(new TableModelEvent(enviados, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    //this.cod_transaccion = datos[c].get("COD_TRANSACCION").toString();
                    TblEnviados.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 0));
                    TblEnviados.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 1));
                    TblEnviados.setValueAt(datos[c].get("COD_TRAMITE"),c,2);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 2));
                    TblEnviados.setValueAt(datos[c].get("NRO"),c,3);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 3));
                    TblEnviados.setValueAt(datos[c].get("TRAMITE"),c,4);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 4));
                    TblEnviados.setValueAt(datos[c].get("DETALLE"),c,5);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 5));
                    TblEnviados.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 6));
                    TblEnviados.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 7));
                    TblEnviados.setValueAt(datos[c].get("ESTADO"),c,8);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 8));
                    TblEnviados.setValueAt(datos[c].get("COD_TRANSACCION"),c,9); 
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 9));
                    TblEnviados.setValueAt(datos[c].get("CUANTIA"),c,10);
                    TblEnviados.setValueAt(datos[c].get("DEL"),c,11);
                    TblEnviados.setValueAt(datos[c].get("HASTA"),c,12);
                    //TblEnviados.setVisible(true);
                    //PnlEniviados.add(TblEnviados);
                    
                    /*TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
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
                    TblTransaccionEstado.setValueAt(datos[c].get("HASTA"),c,12);*/
                }
            }
            System.out.println("esto son sus datos de la tabla "+TblEnviados.getColumnCount()+" y la segunda parte es "+TblEnviados.getRowCount());
            }
            catch (RemoteException e){
                javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            catch (ServiceException e){ System.out.println(e);}
        }
        else
        {
            System.out.println("esto es almacenes");
            try{
            //String []tram=CmbTramite.getSelectedItem().toString().split(" - ");
            //int cod_tram=Integer.parseInt(tram[0]);
            System.err.println("esta entrando en el rol 0");
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("Wujuuuu --->>>>   Ini: "+ini+", Fin: "+fin);
            Map[] datos=puerto.getTransaccionEstado1(cod_almacen,3,gestion,0,100000);
            CerearTablaEnviados();
            if (datos!=null){
                //System.err.println("esta entrando en el rol 0");
                for (int c=0;c<datos.length;c++){
                    
                    enviados.insert(c);
                    TblEnviados.tableChanged(new TableModelEvent(enviados, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    //this.cod_transaccion = datos[c].get("COD_TRANSACCION").toString();
                    TblEnviados.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 0));
                    TblEnviados.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 1));
                    TblEnviados.setValueAt(datos[c].get("COD_TRAMITE"),c,2);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 2));
                    TblEnviados.setValueAt(datos[c].get("NRO"),c,3);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 3));
                    TblEnviados.setValueAt(datos[c].get("TRAMITE"),c,4);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 4));
                    TblEnviados.setValueAt(datos[c].get("DETALLE"),c,5);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 5));
                    TblEnviados.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 6));
                    TblEnviados.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 7));
                    TblEnviados.setValueAt(datos[c].get("ESTADO"),c,8);
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 8));
                    TblEnviados.setValueAt(datos[c].get("COD_TRANSACCION"),c,9); 
                    System.err.println("esta entrando en el rol 0 "+TblEnviados.getValueAt(c, 9));
                    TblEnviados.setValueAt(datos[c].get("CUANTIA"),c,10);
                    TblEnviados.setValueAt(datos[c].get("DEL"),c,11);
                    TblEnviados.setValueAt(datos[c].get("HASTA"),c,12);
                    //TblEnviados.setVisible(true);
                    //PnlEniviados.add(TblEnviados);
                    
                    /*TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
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
                    TblTransaccionEstado.setValueAt(datos[c].get("HASTA"),c,12);*/
                }
            }
            System.out.println("esto son sus datos de la tabla "+TblEnviados.getColumnCount()+" y la segunda parte es "+TblEnviados.getRowCount());
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        }
    }
     
    private void LlenaEstados(){
        try{
            String []tram=CmbTramite.getSelectedItem().toString().split(" - ");
            int cod_tram=Integer.parseInt(tram[0]);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("Wujuuuu --->>>>   Ini: "+ini+", Fin: "+fin);
            Map[] datos=puerto.getTransaccionEstado(cod_almacen,cod_tram,gestion,ini,fin);
            CerearTablaEstados();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    estados.insert(c);
                    TblTransaccionEstado.tableChanged(new TableModelEvent(estados, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,0);
                    //this.cod_transaccion = datos[c].get("COD_TRANSACCION").toString();
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANS_NRO"),c,0);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_ESTADO"),c,1);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRAMITE"),c,2);
                    TblTransaccionEstado.setValueAt(datos[c].get("NRO"),c,3);
                    TblTransaccionEstado.setValueAt(datos[c].get("TRAMITE"),c,4);
                    TblTransaccionEstado.setValueAt(datos[c].get("DETALLE"),c,5);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_SOL"),c,6);
                    TblTransaccionEstado.setValueAt(datos[c].get("UNIDAD_DES"),c,7);
                    TblTransaccionEstado.setValueAt(datos[c].get("ESTADO"),c,8);
                    TblTransaccionEstado.setValueAt(datos[c].get("COD_TRANSACCION"),c,9); 
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
       int nro_reg=0;
       try{ AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS(); 
            System.out.println("!gestion: "+gestion);
            System.out.println("!cod_tramite: "+cod_tramite);
            System.out.println("!cod_almacen: "+cod_almacen);
            
            Map[] datos= puerto.getNroTransacciones(gestion,cod_tramite,cod_almacen);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    try{
                        nro_reg= Integer.parseInt(datos[0].get("LINENUM").toString());
                        } 
                    catch (Exception e) {
                        System.out.println("Error al generar R.A.B :(");
                        nro_reg=5;  
                    }
                }
                 //nro_reg=5;
            }
           
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
        
    void AbreItems(){
        int fila = TblTransaccionBandeja.getSelectedRow();
        int cod_transaccion=Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 0).toString());
        if ("ALM1".equals(TblTransaccionBandeja.getValueAt(fila, 1).toString()) || "JUR".equals(TblTransaccionBandeja.getValueAt(fila, 1).toString())){           
            this.setVisible(false);
            System.err.println("cod_almacen: "+cod_almacen);
            System.err.println("cod_almacen: "+Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString()));
            System.err.println("cod_almacen: "+cod_rol);
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 5).toString());
            System.err.println("cod_almacen: "+gestion);
            System.err.println("cod_almacen: "+Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 3).toString()));
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 1).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 6).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 7).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 8).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 4).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 10).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 11).toString());
            System.err.println("cod_almacen: "+TblTransaccionBandeja.getValueAt(fila, 12).toString());
            System.err.println("cod_almacen: "+cod_usuario);
            DiagOrdenesDetalle ordenes= new DiagOrdenesDetalle(this,menu,cod_almacen, Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString()),cod_rol,  TblTransaccionBandeja.getValueAt(fila, 5).toString(), gestion, Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 3).toString()),TblTransaccionBandeja.getValueAt(fila, 1).toString(),TblTransaccionBandeja.getValueAt(fila, 6).toString(),TblTransaccionBandeja.getValueAt(fila, 7).toString(),TblTransaccionBandeja.getValueAt(fila, 8).toString(),TblTransaccionBandeja.getValueAt(fila, 4).toString(),TblTransaccionBandeja.getValueAt(fila, 10).toString(),TblTransaccionBandeja.getValueAt(fila, 11).toString(),TblTransaccionBandeja.getValueAt(fila, 12).toString(),this.cod_usuario,false);
            ordenes.AbreDialogo(); 
            //LlenaBandeja();
            //LlenaEstados();
            BtnActualizar.doClick();
        }
        else
            menu.AbrirOtroFrame(this, new FrmItems(menu,this,cod_transaccion,cod_rol,TblTransaccionBandeja.getValueAt(fila, 5).toString(),gestion,Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 3).toString()),TblTransaccionBandeja.getValueAt(fila, 1).toString(),TblTransaccionBandeja.getValueAt(fila, 6).toString(),TblTransaccionBandeja.getValueAt(fila, 7).toString(),TblTransaccionBandeja.getValueAt(fila, 8).toString(),TblTransaccionBandeja.getValueAt(fila, 4).toString(),TblTransaccionBandeja.getValueAt(fila, 10).toString(),TblTransaccionBandeja.getValueAt(fila, 11).toString(),TblTransaccionBandeja.getValueAt(fila, 12).toString(),cod_almacen,Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString())));
    }
    
    void CerrarFrame(){
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
    
    
    void MostrarIngreso(int cod_trans_nro,String cod_estado,int cod_tramite,String titulo){
        List list=new ArrayList();
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("_>_>_>_>El cod_trans_nro es :"+cod_trans_nro+" cod_estado: "+cod_estado+" cod_tramite: "+cod_tramite);
            Map[] datos=puerto.getReporteIngreso(cod_trans_nro,cod_estado,cod_tramite);            
            int i=0;
            String cod_trans_detalle,aux = null;
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Transaccion trans = new Transaccion();
                    trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                    trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                    trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                    //trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate("13-04-91"));
                    trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                    trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                    trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                    trans.setDetalle(datos[f].get("DETALLE").toString());
                    trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                    trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                    trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                    trans.setArticulo(datos[f].get("ARTICULO").toString());
                    trans.setPartida(datos[f].get("PARTIDA").toString());
//                    System.out.println("El origen esta en: "+datos[f].get("ORIGEN").toString());
                    trans.setUnidad_sol(datos[f].get("ORIGEN").toString());
                    trans.setObs(datos[f].get("OBS").toString());
                    //trans.setCod_trans_detalle(datos[f].get("COD_TRANS_DETALLE").toString());
                    //System.out.println("________ Cod_trans_detalle: "+trans.getCod_trans_detalle());
                    cod_trans_detalle=datos[f].get("COD_TRANS_DETALLE").toString();
                    if(!cod_trans_detalle.equals(aux)){
                        i++;
                        System.out.println("El indice es wujuuu: "+i);
                        trans.setIndice(String.valueOf(i));
                    }
                    aux=cod_trans_detalle;
                    trans.setCod_trans_detalle(cod_trans_detalle);
                    
                    trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());
                    //hoja_ruta,cbte,casa_comercial,direccion,telefono,nit,precio_unit
                    trans.setHoja_ruta(datos[f].get("HOJA_RUTA").toString());
                    trans.setCbte(datos[f].get("CBTE").toString());
                    trans.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());
                    //trans.setDireccion(datos[f].get("DIRECCION").toString());
                    //trans.setTelefono(datos[f].get("TELEFONO").toString());
                    trans.setNit(datos[f].get("NIT").toString());
                    trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                    //trans.setSubtotal(Double.parseDouble(datos[f].get("SUBTOTAL").toString()));
                    trans.setSubtotal(0.0);
                    
                    trans.setNro_orden_compra(Integer.parseInt(datos[f].get("NRO_ORDEN_COMPRA").toString()));
                    if (!(datos[f].get("FEC_ORDEN_COMPRA")==null || "".equals(datos[f].get("FEC_ORDEN_COMPRA"))))
                        trans.setFec_orden_compra(i_formatterDate.i_conveterStandardToDate(datos[f].get("FEC_ORDEN_COMPRA").toString()));
                    trans.setFactura(datos[f].get("FACTURA").toString());
                    if (!(datos[f].get("FECHA_FACT")==null || "".equals(datos[f].get("FECHA_FACT"))))
                        trans.setFecha_fact(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_FACT").toString()));
                    trans.setMemo(datos[f].get("MEMO").toString());
                    trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));
                    list.add(trans);
                } 
                RepTransaccion rep = new RepTransaccion();
               // rep.Reporte(list,titulo, cod_tramite,cod_trans_nro);
                reportes.MostrarIngreso(cod_trans_nro, cod_estado, cod_tramite);
            }
            System.out.println("error:P ");
            /*for (int i = 0; i < list.size(); i++) {
                Transaccion aux = (Transaccion) list.get(i);t           
                System.out.println(aux.getNro_gestion()+" "+aux.getFecha_creacion()+" "+ aux.getFecha_envio()+" "+aux.getUnidad_sol()+" "+aux.getUnidad_des()+" "+aux.getUsuario_sol()+" "+aux.getUnidad_medida()+" "+aux.getCantidad_pedido()+" "+aux.getTipo_item()+" "+aux.getArticulo()+" "+aux.getDetalle_solicitud());
            }  */      
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"DEBE ELEJIR UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    
    private void LlenaTramites(){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();           
            Map[] datos=puerto.getTramites();
            CmbTramite.addItem("- ELIJA UN TIPO DE TRAMITE -");
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                    
                    CmbTramite.addItem(datos[c].get("COD_TRAMITE")+" - "+datos[c].get("TIPO_REPORTE") );
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }
    
    void MostrarReporte(){
        int fila=TblTransaccionEstado.getSelectedRow();
        int cod_trans_nro=Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 0).toString()); 
        int cod_transaccion = Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 9).toString());
        String cod_estado=TblTransaccionEstado.getValueAt(fila, 1).toString();
        int cod_tram=Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 2).toString()); 
        String titulo=TblTransaccionEstado.getValueAt(fila, 4).toString();
        //System.out.println("Capri :D :D :D cod_transaccion -----> "+this.cod_transaccion);
        /*if (cod_rol==2 && cod_tram==3 && "C".equals(cod_estado))
            MostrarReporte(cod_trans_nro, cod_estado, cod_tram, titulo);*/
        if (cod_tram==3 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado)) && (this.cod_rol==2 || this.cod_rol==4))
            reportes.MostrarIngreso(cod_trans_nro, cod_estado, cod_tram);//MostrarIngreso(cod_trans_nro, cod_estado, cod_tram, titulo);
        else if (cod_tram==1 && (this.cod_rol==2||this.cod_rol==4))
            reportes.MostrarSolicitud(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");//MostrarSolicitud(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");
            //System.out.println();
        else if (cod_tram==2 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado)||"JUR".equals(cod_estado))&& (this.cod_rol==2 || this.cod_rol==4))
            reportes.MostrarOrden(cod_trans_nro, cod_estado, cod_tram, titulo);//MostrarOrden(cod_trans_nro, cod_estado, cod_tram, titulo);
        else if (cod_tram==4 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado))&& (this.cod_rol==2|| this.cod_rol==4))
            reportes.MostrarPedido(cod_trans_nro, cod_estado, cod_tram);//MostrarPedido(cod_trans_nro, cod_estado, cod_tram);
            System.out.println();
    }
    void MostrarReporteEnviados(){
        int fila=TblEnviados.getSelectedRow();
        int cod_trans_nro=Integer.parseInt(TblEnviados.getValueAt(fila, 0).toString()); 
        int cod_transaccion = Integer.parseInt(TblEnviados.getValueAt(fila, 9).toString());
        String cod_estado=TblEnviados.getValueAt(fila, 1).toString();
        int cod_tram=Integer.parseInt(TblEnviados.getValueAt(fila, 2).toString()); 
        String titulo=TblEnviados.getValueAt(fila, 4).toString();
        //System.out.println("Capri :D :D :D cod_transaccion -----> "+this.cod_transaccion);
        /*if (cod_rol==2 && cod_tram==3 && "C".equals(cod_estado))
            MostrarReporte(cod_trans_nro, cod_estado, cod_tram, titulo);*/
        if (cod_tram==3 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado)) && (this.cod_rol==2 || this.cod_rol==4))
            reportes.MostrarIngreso(cod_trans_nro, cod_estado, cod_tram);//MostrarIngreso(cod_trans_nro, cod_estado, cod_tram, titulo);
        else if (cod_tram==1 && (this.cod_rol==2||this.cod_rol==4))
            reportes.MostrarSolicitud(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");//MostrarSolicitud(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");
            //System.out.println();
        else if (cod_tram==2 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado)||"JUR".equals(cod_estado))&& (this.cod_rol==2 || this.cod_rol==4))
            reportes.MostrarOrden(cod_trans_nro, cod_estado, cod_tram, titulo);//MostrarOrden(cod_trans_nro, cod_estado, cod_tram, titulo);
        else if (cod_tram==4 && ("C".equals(cod_estado)||"ALM1".equals(cod_estado))&& (this.cod_rol==2|| this.cod_rol==4))
            reportes.MostrarPedido(cod_trans_nro, cod_estado, cod_tram);//MostrarPedido(cod_trans_nro, cod_estado, cod_tram);
            System.out.println();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        PnlTransaccionBandeja = new javax.swing.JScrollPane();
        TblTransaccionBandeja = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        PnlEniviados = new javax.swing.JScrollPane();
        TblEnviados = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        BtnInicio = new javax.swing.JButton();
        BtnAtras = new javax.swing.JButton();
        BtnAdelante = new javax.swing.JButton();
        BtnFinal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        CmbTramite = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        PnlTransaccionEstado = new javax.swing.JScrollPane();
        TblTransaccionEstado = new javax.swing.JTable();
        BtnSalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        BtnActualizar = new javax.swing.JButton();

        setBackground(new java.awt.Color(104, 129, 156));
        setClosable(true);
        setTitle("BANDEJA DE TRANSACCIONES DE ENTRADA Y TRANSACCIONES DE ESTADO");
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

        TblTransaccionBandeja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        jTabbedPane2.addTab("BANDEJA", PnlTransaccionBandeja);

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));
        jPanel3.setLayout(null);

        TblEnviados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblEnviados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblEnviadosMousePressed(evt);
            }
        });
        TblEnviados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TblEnviadosKeyPressed(evt);
            }
        });
        PnlEniviados.setViewportView(TblEnviados);

        jPanel3.add(PnlEniviados);
        PnlEniviados.setBounds(0, 0, 1120, 190);

        jButton2.setText("modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(420, 190, 160, 28);

        jTabbedPane2.addTab("ENVIADOS", jPanel3);

        getContentPane().add(jTabbedPane2);
        jTabbedPane2.setBounds(10, 10, 1120, 250);

        jPanel2.setBackground(new java.awt.Color(210, 223, 236));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_first.png"))); // NOI18N
        BtnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInicioActionPerformed(evt);
            }
        });
        jPanel2.add(BtnInicio);
        BtnInicio.setBounds(0, 50, 30, 28);

        BtnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_previous.png"))); // NOI18N
        BtnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAtrasActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAtras);
        BtnAtras.setBounds(30, 50, 30, 28);

        BtnAdelante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_next.png"))); // NOI18N
        BtnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAdelanteActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAdelante);
        BtnAdelante.setBounds(60, 50, 30, 28);

        BtnFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_last.png"))); // NOI18N
        BtnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFinalActionPerformed(evt);
            }
        });
        jPanel2.add(BtnFinal);
        BtnFinal.setBounds(90, 50, 30, 28);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Tipo de Tramite");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(340, 40, 100, 20);

        CmbTramite.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel2.add(CmbTramite);
        CmbTramite.setBounds(340, 20, 270, 24);

        jButton1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(970, 10, 90, 60);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 310, 1120, 80);

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

        getContentPane().add(PnlTransaccionEstado);
        PnlTransaccionEstado.setBounds(10, 390, 1120, 200);

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
        BtnSalir.setBounds(420, 600, 150, 28);

        jPanel1.setBackground(new java.awt.Color(210, 223, 236));
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
        BtnActualizar.setBounds(10, 10, 120, 28);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 260, 1120, 50);

        setBounds(0, 0, 1163, 670);
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
        /*if (cod_rol==4) cod_tramite=1;
        else if(cod_rol==5 || cod_rol==7) cod_tramite=2;
        else if(cod_rol==2) cod_tramite=3;   */
        
        if (cod_rol==2 || cod_rol==4 || cod_rol==5) cod_tramite=1;     
        else if(cod_rol==7) cod_tramite=2;
        //else if(cod_rol==2) cod_tramite=3;   
        /*
        if (cod_rol==2) 
            cod_tramite=1;
        else if (cod_rol==4)*/
        LlenaTramites();        
        CalculaElementosListar();
    }//GEN-LAST:event_formInternalFrameOpened

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        LlenaBandeja();        
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CalculaElementosListar();
        LlenaEstados();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        LlenaEnviados();
        LlenaBandeja();
        //LlenaEstados();
        
    }//GEN-LAST:event_formComponentShown

    private void TblTransaccionBandejaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            AbreItems();
        }
    }//GEN-LAST:event_TblTransaccionBandejaKeyPressed

    private void TblTransaccionBandejaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaMousePressed
        if (evt.getClickCount() == 2)
        AbreItems();
    }//GEN-LAST:event_TblTransaccionBandejaMousePressed

    private void TblEnviadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblEnviadosMousePressed
        // TODO add your handling code here:
        if(evt.getClickCount()==2)
            MostrarReporteEnviados();
    }//GEN-LAST:event_TblEnviadosMousePressed

    private void TblEnviadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblEnviadosKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            MostrarReporteEnviados();
    }//GEN-LAST:event_TblEnviadosKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
            int fila = TblEnviados.getSelectedRow();
        int cod_trans_nro = Integer.parseInt(TblEnviados.getValueAt(fila, 0).toString());
        String tramite = TblTransaccionBandeja.getValueAt(fila, 4).toString();
        System.err.println("El cod_trans_nro: "+cod_trans_nro);
        //String tramite = "Correccion Tramite";
        int cod_w = Integer.parseInt(TblEnviados.getValueAt(fila, 2).toString());
        System.err.println("El cod_w: "+cod_w);
        String origen=TblEnviados.getValueAt(fila, 1).toString();
        System.err.println("El origen: "+origen);
        String detalle=TblEnviados.getValueAt(fila, 5).toString();
        System.err.println("El detalle: "+detalle);
        String unidad_sol=TblEnviados.getValueAt(fila, 6).toString();
        System.err.println("unidad_sol: "+unidad_sol);
        String unidad_des=TblEnviados.getValueAt(fila, 7).toString();
        System.err.println("unidad_des: "+unidad_des);
        String estadox=TblEnviados.getValueAt(fila, 8).toString();
        System.err.println("El estadox: "+estadox);
        String nro=TblEnviados.getValueAt(fila, 3).toString();
        System.err.println("El nro: "+nro);
        String cuantia=TblEnviados.getValueAt(fila, 10).toString();
        System.err.println("cuantia: "+cuantia);
        String del=TblEnviados.getValueAt(fila, 11).toString();
        System.err.println("del: "+del);
        String hasta=TblEnviados.getValueAt(fila, 12).toString();
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
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAdelante;
    private javax.swing.JButton BtnAtras;
    private javax.swing.JButton BtnFinal;
    private javax.swing.JButton BtnInicio;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JComboBox CmbTramite;
    private javax.swing.JScrollPane PnlEniviados;
    private javax.swing.JScrollPane PnlTransaccionBandeja;
    private javax.swing.JScrollPane PnlTransaccionEstado;
    private javax.swing.JTable TblEnviados;
    private javax.swing.JTable TblTransaccionBandeja;
    private javax.swing.JTable TblTransaccionEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
