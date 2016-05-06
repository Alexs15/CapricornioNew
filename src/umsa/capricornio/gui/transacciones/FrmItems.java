/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmTransaccionDetalle.java
 *
 * Created on 30-jun-2011, 9:29:04
 */
package umsa.capricornio.gui.transacciones;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;   
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Ppto;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.tablas.TablaItems;
import umsa.capricornio.gui.transacciones.tablas.TablaDocumentos;
import umsa.capricornio.utilitarios.herramientas.Archivos;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
import umsa.capricornio.utilitarios.herramientas.validacion;
import umsa.capricornio.service.HelloService;
import com.caucho.hessian.client.HessianProxyFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import umsa.capricornio.domain.Transaccion;
import static umsa.capricornio.gui.menu.FrmMenu.cod_usuario;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.JDAdjuntos;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.JD_FechaEntrega;
import umsa.capricornio.gui.transacciones.reporte.RepTransaccion;
import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.PartidaCellEditor;
import umsa.capricornio.gui.transacciones.tablas.PartidaCellRenderer;
import umsa.capricornio.gui.transacciones.tablas.TablaPreventivo;
import umsa.capricornio.gui.transacciones.tablas.TablaVistaPreventivo;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedida;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedidaCellEditor;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author 
 */
public class FrmItems extends javax.swing.JInternalFrame {

    FrmMenu menu;
    FrmTransacciones frm_transaccion;    
    
    int cod_transaccion,cod_rol,gestion,cod_w,tab_habil,cod_almacen,cod_trans_nro,cod_user;
    private Runtime r;
    private File rutaArchivo;
    String tramite,origen,detalle,unidad_des,unidad_sol,nro,cuantia,del,hasta,lectura,nombre_archivo;
    TablaItems items;
    TablaVistaPreventivo tvpreventivo;
    TablaDocumentos documentos;
    private boolean sw_preventivo = true;
    private boolean sw;
    private double Total;
    
    
    /** Creates new form FrmTransaccionDetalle */
    public FrmItems(FrmMenu menu,FrmTransacciones frm_transaccion,int cod_transaccion,int cod_rol,String tramite,int gestion,int cod_w,String origen,String detalle,String unidad_sol,String unidad_des,String nro,String cuantia,String del,String hasta,int cod_almacen,int cod_trans_nro) {
        //System.out.println("entroooooooooo :P:P:P:P:P:P:P");
        this.menu=menu;
        this.frm_transaccion=frm_transaccion;
        this.cod_transaccion=cod_transaccion;
        this.cod_rol=cod_rol;        
        this.tramite=tramite;
        this.gestion=gestion;
        this.cod_w=cod_w;
        this.origen=origen;
        this.detalle=detalle;
        this.unidad_des=unidad_des;
        this.unidad_sol=unidad_sol;
        this.nro=nro;
        this.cuantia=cuantia;
        this.del=del;
        this.hasta=hasta;
        this.cod_almacen=cod_almacen;
        this.cod_trans_nro=cod_trans_nro;
        this.cod_user=cod_user;
        initComponents();
        ConstruyeTablaItems();
        ConstruyeTablaVistaPreventivo();
        
        //this.JB_Buscar.setVisible(false);
        //ConstruyeTablaDocumentos();
    }

    private  List<Partida> getPartidas(int gestion){
        List<Partida> listPartida=null;
        try {
                listPartida = new ArrayList();
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();           
                Map[] datos=puerto.getPartidas(String.valueOf(gestion));
                
                if (datos!=null){
                    for (int c=0;c<datos.length;c++){
                        //this.JC_Partidas.addItem(datos[c].get("PARTIDA")+" - "+datos[c].get("DETALLE") );
                        //System.out.println("--> "+datos[c].get("PARTIDA").toString()); 
//                        System.out.println("--> "+datos[c].get("PARTIDA").toString()+" - "+datos[c].get("DETALLE").toString()); 
                        listPartida.add(new Partida(datos[c].get("PARTIDA").toString()+" - "+datos[c].get("DETALLE").toString()));
                    }
                }
            }
            catch (RemoteException e){
                javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            catch (ServiceException e){ System.out.println(e);}
        return listPartida;
    }
    public void MultiplicaCantidadPrecioUnit(int fila){
       
        DecimalFormat formatter = new DecimalFormat("###.00");
           
            try {
                if (!"".equals(TblItems.getValueAt(fila, 7).toString())){
                    double cantidad=Double.parseDouble(TblItems.getValueAt(fila, 3).toString());
                    double precio_unit=Double.parseDouble(TblItems.getValueAt(fila, 7).toString());
                    
                    System.out.println("la cantidad es: "+cantidad+", precio_unit: "+precio_unit);
                    String numero=(String) formatter.format(cantidad*precio_unit);
                    System.out.println("El numero es: "+numero);
                    TblItems.setValueAt(numero.replace(',','.'), fila, 8);
                }
                else TblItems.setValueAt("", fila, 8);
                TblItems.repaint();
            }
            catch(NumberFormatException e){}
        System.out.println("Hola Que tal....");
        
    }
    private boolean VerificaPartidas(){
        sw = true;
        for (int f=0;f<TblItems.getRowCount();f++){
            String partida = ((Partida)TblItems.getValueAt(f, 5)).getCodigo().trim();
            if(partida.equals("No tiene") && !(TblItems.getValueAt(f, 5)).toString().equals("")){
                sw = false;
            }
//            System.out.println("Partida --> "+((Partida)TblItems.getValueAt(f, 5)).getCodigo());
        }
        return sw;
    }
    private boolean VerificaPrecioU(){
        sw = true;
        for (int f=0;f<TblItems.getRowCount();f++){
            double monto = Double.parseDouble(TblItems.getValueAt(f, 7).toString().trim());
            if(!(monto>0)){
                sw = false;
            }
//            System.out.println("El monto es  --> "+monto);
        }
        return sw;
    }
    private List<UnidadMedida> getUnidadMedida() {
        List<UnidadMedida> listUnidadMedida = null;
        try {
            listUnidadMedida = new ArrayList();
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getUnidadMedida();

            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    //this.JC_Partidas.addItem(datos[c].get("PARTIDA")+" - "+datos[c].get("DETALLE") );
//                    System.out.println("--> " + datos[c].get("PARTIDA").toString());
                        System.out.println("--> "+datos[c].get("COD_UNIDAD_MEDIDA").toString()+" - "+datos[c].get("DETALLE").toString()); 
                    listUnidadMedida.add(new UnidadMedida(datos[c].get("COD_UNIDAD_MEDIDA").toString() + " - " + datos[c].get("DETALLE").toString()));
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        return listUnidadMedida;
    }
    private void ConstruyeTablaItems(){
        
        List<Partida> listPartida = this.getPartidas(2014);
        List<UnidadMedida> listUnidadMedida = this.getUnidadMedida();
        
        items = new TablaItems(this,cod_rol,origen,this.cod_user);
        TblItems.setAutoCreateColumnsFromModel(false);
        TblItems.setModel(items);
//        TblItems.setDefaultRenderer(Partida.class, new PartidaCellRenderer());
//        TblItems.setDefaultEditor(Partida.class, new PartidaCellEditor(listPartida));

        for (int k = 0; k < TablaItems.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaItems.m_columns[k].m_alignment);
//            TableCellRenderer renderer = new PartidaCellRenderer
            TableCellEditor edit1 = new PartidaCellEditor(listPartida);
            TableCellEditor edit2 = new UnidadMedidaCellEditor(listUnidadMedida);
            
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column;
            if(k==5){
                column = new TableColumn(k, TablaItems.m_columns[k].m_width, renderer, edit1);
            }else if(k==4){
                column = new TableColumn(k, TablaItems.m_columns[k].m_width, renderer, edit2);
            }
            else{
                column = new TableColumn(k, TablaItems.m_columns[k].m_width, renderer, null);
            }
             
            //TblItems.setDefaultEditor(Partida.class, new PartidaCellEditor(listPartida));
            //TblItems.setDefaultEditor(Partida.class, new PartidaCellEditor(listPartida));
            TblItems.addColumn(column);
        }
        
        JTableHeader header = TblItems.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlItems.getViewport().add(TblItems);
    }
    private void ConstruyeTablaVistaPreventivo(){
             
               
        tvpreventivo = new TablaVistaPreventivo();
        JTbl_VPreventivo.setAutoCreateColumnsFromModel(false);
        JTbl_VPreventivo.setModel(tvpreventivo);


        for (int k = 0; k < TablaVistaPreventivo.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaVistaPreventivo.m_columns[k].m_alignment);
//            TableCellRenderer renderer = new PartidaCellRenderer
//            TableCellEditor edit =new PartidaCellEditor(listPartida);
            
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaVistaPreventivo.m_columns[k].m_width, renderer, null);
            //JTbl_VPreventivo.setDefaultEditor(Partida.class, new PartidaCellEditor(listPartida));
            //JTbl_VPreventivo.setDefaultEditor(Partida.class, new PartidaCellEditor(listPartida));
            JTbl_VPreventivo.addColumn(column);
        }
        
        JTableHeader header = JTbl_VPreventivo.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlPreventivo.getViewport().add(JTbl_VPreventivo);
    }
    
    /*private void ConstruyeTablaDocumentos(){
        documentos = new TablaDocumentos();
        TblDocumentos.setAutoCreateColumnsFromModel(false);
        TblDocumentos.setModel(documentos);

        for (int k = 0; k < TablaDocumentos.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaDocumentos.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaDocumentos.m_columns[k].m_width, renderer, null);
            TblDocumentos.addColumn(column);
        }
        JTableHeader header = TblDocumentos.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlDocumentos.getViewport().add(TblDocumentos);
       
    }*/
    
    
    private void LlenaCabecera(){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("************************ El cod_transaccion es: "+cod_transaccion);
            Map[] datos=puerto.getTransaccion(cod_transaccion);            
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    LblNro.setText(datos[c].get("NRO_GESTION").toString());
                    LblUnidadSol.setText(datos[c].get("UNIDAD_SOL").toString());
                    LblUnidadDes.setText(datos[c].get("UNIDAD_DES").toString());
                    LblDetalle.setText(datos[c].get("USUARIO_SOL").toString());
                    System.out.println("************************ La observacion de almacen es: "+datos[c].get("OBS_ALMACEN").toString());
                    TxtObsAlmacen.setText(datos[c].get("OBS_ALMACEN").toString());
//                    TxtHojaRuta.setText(datos[c].get("HOJA_RUTA").toString());
//                    TxtPreventivo.setText(datos[c].get("CBTE").toString());
                    TxtObsPpto.setText(datos[c].get("OBS_PPTO").toString());
                    System.out.println("la hoja de ruta es: "+datos[c].get("HOJA_RUTA").toString());
                    this.JT_HRUTA.setText(datos[c].get("HOJA_RUTA").toString());
//                    TxtCeritPpto.setText(datos[c].get("CERTIF_PPTO").toString());
//                    TxtMontoPpto.setText(datos[c].get("MONTO_PPTO").toString());
//                    TxtFondo.setText(datos[c].get("FONDO").toString());
                    /*TxtCasa.setText(datos[c].get("CASA_COMERCIAL").toString());
                    TxtDireccion.setText(datos[c].get("DIRECCION").toString());
                    TxtTelefono.setText(datos[c].get("TELEFONO").toString());
                    TxtNit.setText(datos[c].get("NIT").toString());
                    TxtObsAdq.setText(datos[c].get("OBS_ADQ").toString());
                    TxtFactura.setText(datos[c].get("FACTURA").toString());
                    if (!(datos[c].get("FECHA_FACT")==null || "".equals(datos[c].get("FECHA_FACT").toString()))){
                        Date hoy = i_formatterDate.i_conveterPostgresToDate(datos[c].get("FECHA_FACT").toString());
                        CalFechaFact.setValue(hoy);
                    }                    
                    TxtMemo.setText(datos[c].get("MEMO").toString());*/
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }

    private void LlenaVistaPreventivo(){
        double total = 0;
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getPreventivos(cod_transaccion);
//            Map[] datos = null;
            this.CerearTablaVPreventivo();
            System.out.println("Wujuuu!!! - 1");
            if (datos!=null){
                this.sw_preventivo = true;
                for (int c=0;c<datos.length;c++){
                    tvpreventivo.insert(c);
                    JTbl_VPreventivo.tableChanged(new TableModelEvent(tvpreventivo, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
//                    System.out.println("cod_preventivo: "+datos[c].get("COD_PREVENTIVO"));
//                    System.out.println("total: "+datos[c].get("TOTAL"));
//                    System.out.println("resumen: "+datos[c].get("RESUMEN"));
                    JTbl_VPreventivo.setValueAt(datos[c].get("COD_PREVENTIVO").toString(),c,0);
                    JTbl_VPreventivo.setValueAt(datos[c].get("TOTAL").toString(),c,1);
                    JTbl_VPreventivo.setValueAt(datos[c].get("RESUMEN").toString(),c,2);
//                    JTbl_VPreventivo.setValueAt(datos[c].get("TOTAL"),c,1);
//                    JTbl_VPreventivo.setValueAt(datos[c].get("RESUMEN"),c,2);
                    total=total+Double.parseDouble(datos[c].get("TOTAL").toString().trim().replace(",", "."));
//                    System.out.println("total --> "+total);
                }
                this.JL_TotalM.setText(String.valueOf(total));
            }else{
                System.out.println("Wujuuu!!! - 2");
                tvpreventivo.insert(0);
                JTbl_VPreventivo.tableChanged(new TableModelEvent(tvpreventivo, 0, 0, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                JTbl_VPreventivo.setValueAt("Vacio",0,0);
                this.JL_TotalM.setText("0");
                this.sw_preventivo = false;
                
            }
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }
     public String Redondear(Double numero, int pow)
    {
           Double num_deci = Math.pow(10, pow);
           return String.valueOf(Math.rint(numero*num_deci)/num_deci);
    }
    private void LlenaItems(){        
        try{
            double sum = 0, subtotal = 0;
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("EL COD_TRANSACCION ES ========>>> "+cod_transaccion);
            Map[] datos=puerto.getItems2(cod_transaccion,1);
//            Map[] datos=puerto.getItems(cod_transaccion);
            CerearTablaItems();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    items.insert(c);
                    TblItems.tableChanged(new TableModelEvent(items, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblItems.setValueAt(datos[c].get("COD_COMPLEMENTO"),c,0);
                    TblItems.setValueAt(datos[c].get("ESTADO"),c,1);                    
                    TblItems.setValueAt(datos[c].get("COD_TRANS_DETALLE"),c,2);                    
                    TblItems.setValueAt(datos[c].get("CANTIDAD_PEDIDO"),c,3);
                    
                    
                    if(datos[c].get("UNIDAD_MEDIDA").toString().equals("")){
                        TblItems.setValueAt(new UnidadMedida("No tiene"),c,4);
                    }else{
//                        TblItems.setValueAt(datos[c].get("UNIDAD_MEDIDA").toString(),c,4);
                        TblItems.setValueAt(new UnidadMedida(datos[c].get("UNIDAD_MEDIDA").toString()),c,4);
                    }
                    
                    //TblItems.setValueAt(datos[c].get("TIPO_ITEM"),c,5);
                    
//                    TblItems.setValueAt(datos[c].get("PARTIDA"),c,5);
//                    System.out.println("Es nulooo"+datos[c].get("PARTIDA"));
                    if(datos[c].get("PARTIDA").toString().equals("")){
                        TblItems.setValueAt(new Partida("No tiene"),c,5);
                    }else{
                        TblItems.setValueAt(new Partida(datos[c].get("PARTIDA").toString()),c,5);
                    }
                    
                    //TblItems.setValueAt(new ,c,5);
//                    subtotal = Double.parseDouble(Redondear(Double.parseDouble(datos[c].get("CANTIDAD_PEDIDO").toString())*Double.parseDouble(datos[c].get("PRECIO_UNIT").toString()),2));
//                    sum = sum+subtotal;
                    TblItems.setValueAt(datos[c].get("ARTICULO"),c,6);
                    TblItems.setValueAt(datos[c].get("PRECIO_UNIT"),c,7);
//                    TblItems.setValueAt(subtotal,c,8);
                    //TblItems.setValueAt(datos[c].get("ARTICULO"),c,6);
                    //TblItems.setValueAt(datos[c].get("ARTICULO_ACT"),c,8);                    
                    //TblItems.setValueAt(datos[c].get("ARTICULO_ALM"),c,9);                    
                    //TblItems.setValueAt(datos[c].get("CONTRATO"),c,10);                    
                    //TblItems.setValueAt(datos[c].get("PRECIO_UNIT"),c,11);
                    //TblItems.setValueAt(datos[c].get("COD_ITEM"),c,13);
                }
//                this.TxtTotal.setText(String.valueOf(sum));
            }
            
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
    public void SumaPreciosTotales(){
        String sum="";
        double sumT=0.0;
        for (int f=0; f<TblItems.getRowCount();f++){
            sum =(String) TblItems.getValueAt(f, 8);
            if (!"".equals(sum))
                sumT+=Double.parseDouble(sum);
        }
        /*if(!verifica_monto(sumT)){
            javax.swing.JOptionPane.showMessageDialog(this,"El monto no es el correcto de acuerdo a la partida","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }*/
        //System.out.println("----------------------  La suma: "+sumT+" es y la cuantia: "+cuantia+"   -------------------------");
        //System.out.println("----------------------  Del: "+this.del+" - hasta: "+this.hasta+"   -------------------------");
        DecimalFormat formatter = new DecimalFormat("###,##0.00");
        TxtTotal.setText(formatter.format(sumT));
        this.Total=sumT;
    }
    /*private void LlenaDocumentos(){        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getDocumentos(cod_transaccion);
            CerearTablaDocumentos();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    documentos.insert(c);
                    TblDocumentos.tableChanged(new TableModelEvent(documentos, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblDocumentos.setValueAt(datos[c].get("COD_DOCS"),c,0);
                    TblDocumentos.setValueAt(datos[c].get("COD_TRANSACCION"),c,1);                    
                    TblDocumentos.setValueAt(datos[c].get("TERMINOS_REF"),c,2);                    
                    TblDocumentos.setValueAt(datos[c].get("UBICACION"),c,3);
                    TblDocumentos.setValueAt(datos[c].get("DESCRIPCION"),c,4);
                            
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }*/
    
    
    void CerearTablaItems(){
        int f = TblItems.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (items.delete(i)) {
                TblItems.tableChanged(new TableModelEvent(
                items, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }
    void CerearTablaVPreventivo(){
        int f = JTbl_VPreventivo.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (tvpreventivo.delete(i)) {
                JTbl_VPreventivo.tableChanged(new TableModelEvent(
                tvpreventivo, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
    
   /*void CerearTablaDocumentos(){
        int f = TblDocumentos.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (documentos.delete(i)) {
                TblDocumentos.tableChanged(new TableModelEvent(
                documentos, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } */
    
    void ActualizaTransaccionAlmacen(String obs_almacen){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setActualizaTransaccionAlm(cod_transaccion, obs_almacen);
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }

    void ActualizaTransaccionIngresoAlmacen(String factura,String fecha_fact,String fecha_ing, String fecha_noti, String memo,String obs){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setActualizaTransaccionIngresoAlm(cod_transaccion, factura,fecha_fact,fecha_ing,fecha_noti,memo,obs);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ALMACENADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }
    
    void ActualizaTransaccionPpto(String obs_ppto,String hoja_ruta,String cbte,String certif_ppto,String monto_ppto,String fondo){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setActualizaTransaccionPpto("SET-upDateActTransPpto", cod_transaccion, obs_ppto, hoja_ruta, cbte,certif_ppto,Double.parseDouble(monto_ppto),fondo);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ALMACENADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }
    
    /*void ActualizaTransaccionAdq(String obs_almacen,String obs_adq,String casa_comercial,String direccion,String telefono,String nit){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setActualizaTransaccionAdq("SET-upDateActTransAdq", cod_transaccion, obs_adq, casa_comercial, direccion, telefono, nit);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ALMACENADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }*/
    
    public static String getExtension(String filename) {
            int index = filename.lastIndexOf('.');
            if (index == -1) {
                  return "";
            } else {
                  return filename.substring(index + 1);
            }
    }
    
   
      
    
    
    boolean AdjuntarArchivo(String ruta_archivo,String nombre_archivo) {
     boolean sw=false;   
            HessianProxyFactory proxy = new HessianProxyFactory();
    try {
   //call proxy for Upload
      HelloService x = (HelloService) proxy.create(HelloService.class, "http://200.7.160.182/HessianServerI/HelloServlet");
      InputStream in;
  try {
        in = new FileInputStream(ruta_archivo);
        x.upload("/opt/tomcat/webapps/prueba/"+nombre_archivo, in);
        sw=true;
      } catch (FileNotFoundException ex) {
          sw=false;
        //Logger.getLogger(HessianFrame.class.getName()).log(Level.SEVERE, null, ex);
      }
    } catch (MalformedURLException ex) {
        sw=false;
        //Logger.getLogger(HessianFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
    return sw;
    }
    
    
    private int VerificaTipoTramite(int cod_tramite){
        int cantidad_tram=0;
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            for (int f=0;f<TblItems.getRowCount();f++)
                if (!"".equals(TblItems.getValueAt(f, 1).toString())){
                    Map[] datos=puerto.getTieneTipoTramite(cod_tramite,Integer.parseInt(TblItems.getValueAt(f, 2).toString()));
                    if (datos!=null){
                        cantidad_tram = Integer.parseInt(datos[0].get("CANTIDAD").toString());
                        break;
                    }
                }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
        return cantidad_tram;
    }
        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LblUnidadSol = new javax.swing.JLabel();
        LblUnidadDes = new javax.swing.JLabel();
        LblDetalle = new javax.swing.JLabel();
        LblTituloNro = new javax.swing.JLabel();
        LblNro = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        LblCuantia = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LblDe = new javax.swing.JLabel();
        LblA = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        PnlItems = new javax.swing.JScrollPane();
        TblItems = new javax.swing.JTable();
        BtnAvanzar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();
        BtnRetornar = new javax.swing.JButton();
        TabTransaccion = new javax.swing.JTabbedPane();
        PnlAlmacen = new javax.swing.JPanel();
        ScrObs = new javax.swing.JScrollPane();
        TxtObsAlmacen = new javax.swing.JTextPane();
        LblFactura = new javax.swing.JLabel();
        LblFecFact = new javax.swing.JLabel();
        LblMemo = new javax.swing.JLabel();
        TxtFactura = new javax.swing.JTextField();
        CalFechaFact = new net.sf.nachocalendar.components.DateField();
        TxtMemo = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        CalFechaIng = new net.sf.nachocalendar.components.DateField();
        LblFechaIng = new javax.swing.JLabel();
        PnlPpto = new javax.swing.JPanel();
        TxtObsPpto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        JT_HRUTA = new javax.swing.JTextField();
        BtnCertifPpto = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        PnlPreventivo = new javax.swing.JScrollPane();
        JTbl_VPreventivo = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        JL_TotalM = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        BtnCertifPpto1 = new javax.swing.JButton();
        TxtTotal = new javax.swing.JTextField();
        BtnAnular = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        BtnGuardar = new javax.swing.JButton();

        setBackground(new java.awt.Color(61, 82, 122));
        setTitle("ZODIAC CAPRICORN SYSTEM");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
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
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(209, 224, 240));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Unidad Solicitante :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 50, 140, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Bs.");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(580, 90, 30, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Detalle :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 30, 140, 20);

        LblUnidadSol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblUnidadSol.setForeground(new java.awt.Color(44, 59, 89));
        LblUnidadSol.setText("jLabel4");
        jPanel1.add(LblUnidadSol);
        LblUnidadSol.setBounds(180, 50, 750, 20);

        LblUnidadDes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblUnidadDes.setForeground(new java.awt.Color(44, 59, 89));
        LblUnidadDes.setText("jLabel5");
        jPanel1.add(LblUnidadDes);
        LblUnidadDes.setBounds(180, 70, 750, 20);

        LblDetalle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblDetalle.setForeground(new java.awt.Color(44, 59, 89));
        LblDetalle.setText("jLabel6");
        jPanel1.add(LblDetalle);
        LblDetalle.setBounds(180, 30, 750, 20);

        LblTituloNro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LblTituloNro.setForeground(new java.awt.Color(204, 0, 51));
        LblTituloNro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LblTituloNro.setText("titulo:");
        jPanel1.add(LblTituloNro);
        LblTituloNro.setBounds(630, 10, 370, 20);

        LblNro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LblNro.setForeground(new java.awt.Color(204, 0, 51));
        LblNro.setText("jLabel8");
        jPanel1.add(LblNro);
        LblNro.setBounds(1010, 10, 90, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Unidad Destino :");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(30, 70, 140, 20);

        LblCuantia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblCuantia.setForeground(new java.awt.Color(44, 59, 89));
        LblCuantia.setText("jLabel8");
        jPanel1.add(LblCuantia);
        LblCuantia.setBounds(180, 90, 160, 20);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tipo Cuantia :");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 90, 140, 20);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 51));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("De :");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(330, 90, 30, 20);

        LblDe.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblDe.setForeground(new java.awt.Color(44, 59, 89));
        LblDe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblDe.setText("100.100,10");
        jPanel1.add(LblDe);
        LblDe.setBounds(370, 90, 80, 20);

        LblA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblA.setForeground(new java.awt.Color(44, 59, 89));
        LblA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblA.setText("jLabel14");
        jPanel1.add(LblA);
        LblA.setBounds(500, 90, 80, 20);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("A :");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(460, 90, 30, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 1130, 120);

        TblItems.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TblItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblItemsMousePressed(evt);
            }
        });
        PnlItems.setViewportView(TblItems);

        getContentPane().add(PnlItems);
        PnlItems.setBounds(10, 140, 1130, 190);

        BtnAvanzar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnAvanzar.setForeground(new java.awt.Color(0, 51, 153));
        BtnAvanzar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnAvanzar.setText("Guardar y Avanzar");
        BtnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAvanzarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAvanzar);
        BtnAvanzar.setBounds(770, 530, 160, 25);

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
        BtnSalir.setBounds(450, 530, 160, 25);

        BtnRetornar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnRetornar.setForeground(new java.awt.Color(0, 102, 0));
        BtnRetornar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/arrow_left.png"))); // NOI18N
        BtnRetornar.setText("Retornar Tramite");
        BtnRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRetornarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRetornar);
        BtnRetornar.setBounds(130, 530, 160, 25);

        TabTransaccion.setBackground(new java.awt.Color(209, 224, 240));

        PnlAlmacen.setBackground(new java.awt.Color(209, 224, 240));
        PnlAlmacen.setEnabled(false);
        PnlAlmacen.setLayout(null);

        ScrObs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        TxtObsAlmacen.setEditable(false);
        TxtObsAlmacen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ScrObs.setViewportView(TxtObsAlmacen);

        PnlAlmacen.add(ScrObs);
        ScrObs.setBounds(70, 50, 630, 60);

        LblFactura.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFactura.setText("Factura :");
        PnlAlmacen.add(LblFactura);
        LblFactura.setBounds(80, 20, 60, 20);

        LblFecFact.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFecFact.setText("Fecha Factura :");
        PnlAlmacen.add(LblFecFact);
        LblFecFact.setBounds(300, 20, 90, 20);

        LblMemo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblMemo.setText("Memo :)");
        PnlAlmacen.add(LblMemo);
        LblMemo.setBounds(780, 20, 50, 20);

        TxtFactura.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFacturaKeyPressed(evt);
            }
        });
        PnlAlmacen.add(TxtFactura);
        TxtFactura.setBounds(140, 20, 100, 21);

        CalFechaFact.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFechaFact.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CalFechaFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CalFechaFactKeyPressed(evt);
            }
        });
        PnlAlmacen.add(CalFechaFact);
        CalFechaFact.setBounds(390, 20, 100, 20);

        TxtMemo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtMemo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtMemoKeyPressed(evt);
            }
        });
        PnlAlmacen.add(TxtMemo);
        TxtMemo.setBounds(830, 20, 90, 21);

        jButton3.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 102, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/attach_2.png"))); // NOI18N
        jButton3.setText("Archivos Adjuntos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        PnlAlmacen.add(jButton3);
        jButton3.setBounds(800, 100, 170, 25);

        CalFechaIng.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFechaIng.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CalFechaIng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CalFechaIngKeyPressed(evt);
            }
        });
        PnlAlmacen.add(CalFechaIng);
        CalFechaIng.setBounds(640, 20, 100, 20);

        LblFechaIng.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFechaIng.setText("Fecha Ingreso:");
        PnlAlmacen.add(LblFechaIng);
        LblFechaIng.setBounds(550, 20, 90, 20);

        TabTransaccion.addTab("Almacen", PnlAlmacen);

        PnlPpto.setBackground(new java.awt.Color(209, 224, 240));
        PnlPpto.setLayout(null);

        TxtObsPpto.setEditable(false);
        TxtObsPpto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtObsPpto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtObsPptoKeyPressed(evt);
            }
        });
        PnlPpto.add(TxtObsPpto);
        TxtObsPpto.setBounds(30, 40, 440, 40);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Observacion");
        PnlPpto.add(jLabel16);
        jLabel16.setBounds(220, 10, 90, 20);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        PnlPpto.add(jSeparator1);
        jSeparator1.setBounds(520, 0, 20, 170);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setText("HOJA DE RUTA:");
        PnlPpto.add(jLabel6);
        jLabel6.setBounds(150, 90, 90, 20);
        PnlPpto.add(JT_HRUTA);
        JT_HRUTA.setBounds(270, 90, 100, 20);

        BtnCertifPpto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/note.png"))); // NOI18N
        BtnCertifPpto.setText("NOTA");
        BtnCertifPpto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCertifPpto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCertifPptoActionPerformed(evt);
            }
        });
        PnlPpto.add(BtnCertifPpto);
        BtnCertifPpto.setBounds(720, 10, 90, 25);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/cancel.png"))); // NOI18N
        jButton4.setText("Preventivo - Nota");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        PnlPpto.add(jButton4);
        jButton4.setBounds(620, 40, 190, 25);

        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/attach_2.png"))); // NOI18N
        jButton1.setText("Archivos Adjuntos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PnlPpto.add(jButton1);
        jButton1.setBounds(620, 70, 190, 25);

        PnlPreventivo.setViewportView(JTbl_VPreventivo);

        PnlPpto.add(PnlPreventivo);
        PnlPreventivo.setBounds(830, 10, 250, 110);

        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("TOTAL PREVENTIVOS:");
        PnlPpto.add(jLabel4);
        jLabel4.setBounds(820, 130, 140, 14);

        JL_TotalM.setForeground(new java.awt.Color(255, 0, 0));
        JL_TotalM.setText("0");
        PnlPpto.add(JL_TotalM);
        JL_TotalM.setBounds(960, 130, 150, 14);

        jButton5.setText("TOTALES POR PARTIDAS");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        PnlPpto.add(jButton5);
        jButton5.setBounds(620, 100, 190, 23);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        PnlPpto.add(jButton13);
        jButton13.setBounds(370, 89, 30, 25);

        BtnCertifPpto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnCertifPpto1.setText("SIGMA");
        BtnCertifPpto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCertifPpto1ActionPerformed(evt);
            }
        });
        PnlPpto.add(BtnCertifPpto1);
        BtnCertifPpto1.setBounds(620, 10, 100, 25);

        TabTransaccion.addTab("Presupuesto", PnlPpto);

        getContentPane().add(TabTransaccion);
        TabTransaccion.setBounds(10, 350, 1130, 180);

        TxtTotal.setEditable(false);
        TxtTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtTotal.setForeground(new java.awt.Color(255, 0, 0));
        TxtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(TxtTotal);
        TxtTotal.setBounds(990, 330, 150, 21);

        BtnAnular.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnAnular.setForeground(new java.awt.Color(255, 0, 0));
        BtnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnAnular.setText("Anular");
        BtnAnular.setEnabled(false);
        BtnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnularActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAnular);
        BtnAnular.setBounds(290, 530, 160, 25);

        jButton2.setText("Mostrar Solicitud de Compra");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(470, 330, 230, 23);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TOTAL: ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(930, 330, 60, 17);

        BtnGuardar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnGuardar.setForeground(new java.awt.Color(0, 51, 153));
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk.png"))); // NOI18N
        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnGuardar);
        BtnGuardar.setBounds(610, 530, 160, 25);

        setBounds(0, 0, 1168, 612);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
            menu.CerrarOtroFrame(this,frm_transaccion);
            System.gc();
            r = Runtime.getRuntime();
            long mem1 = r.freeMemory();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
        System.out.println("-------------------- Entro a bandejaxx");
        LblTituloNro.setText(tramite +" Nro.:");
        Date hoy = new Date();
        CalFechaFact.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(hoy));
        
        LlenaCabecera();
        LblNro.setText(nro+" - "+gestion);
        LblDetalle.setText(detalle);
        LblUnidadDes.setText(unidad_des);
        LblUnidadSol.setText(unidad_sol);
        LblCuantia.setText(cuantia);
        
        DecimalFormat formatter = new DecimalFormat("###,##0");        
        LblDe.setText(formatter.format(Double.parseDouble(del)));        
        LblA.setText(formatter.format(Double.parseDouble(hasta))); 
        
        LlenaItems();
        LlenaVistaPreventivo();
        //LlenaDocumentos();
        
        /*PnlAlmacen.setVisible(false);
        PnlPpto.setVisible(false);
        PnlAdquisiciones.setVisible(false);
        if (cod_rol==2)
            PnlAlmacen.setVisible(true);
        else if (cod_rol==4)
            PnlPpto.setVisible(true);
        else if (cod_rol==5 )
            PnlAdquisiciones.setVisible(true);
          */
        
        LblFactura.setVisible(false);
        LblFecFact.setVisible(false);
        LblMemo.setVisible(false);
        TxtFactura.setVisible(false);
        CalFechaFact.setVisible(false);
        TxtMemo.setVisible(false);
        ScrObs.setVisible(false);
        if (cod_rol==2){
            if ("ALM1".equals(origen)){
                LblFactura.setVisible(true);
                LblFecFact.setVisible(true);
                LblMemo.setVisible(true);
                TxtFactura.setVisible(true);
                CalFechaFact.setVisible(true);
                TxtMemo.setVisible(true);
            }
            else {
                ScrObs.setVisible(true);
                TxtObsAlmacen.setEditable(true);     
            }
            tab_habil=0;            
        }
        else if (cod_rol==4){
            TxtObsPpto.setEditable(true);
//            TxtHojaRuta.setEditable(true);
//            TxtPreventivo.setEditable(true);
//            TxtFondo.setEditable(true);
//            TxtMontoPpto.setEditable(true);
            tab_habil=1;
            TabTransaccion.setEnabledAt(TabTransaccion.indexOfComponent(PnlAlmacen),false);
        }   
        else if (cod_rol==7){
                TabTransaccion.setVisible(false);
                BtnGuardar.setEnabled(false);
                
        }
        TabTransaccion.setSelectedIndex(tab_habil);
        
    }//GEN-LAST:event_formInternalFrameOpened

    private boolean verificaAdjPreventivo(){
        boolean sw_verAdj = false;
        try {
            
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getAdjunto(cod_transaccion, 14);
            if (datos!=null){
                System.out.println("Tiene preventivo");
                sw_verAdj = true;
            }
            else{
                System.out.println("No tiene preventivo");
                sw_verAdj = false;
            }
        } catch (Exception e) {
            System.out.println("Error en el servidor");
        }
        return sw_verAdj;
    }
    private boolean verifica_avanzar() {
        if (cod_rol != 2) {
            
            if (this.JT_HRUTA.getText() == null || this.JT_HRUTA.getText().trim().length() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe adicionar la hoja de ruta", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        } else {
            return true;
        }
    }
    
    private void BtnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAvanzarActionPerformed
        System.out.println("------> "+cod_rol+" sw: "+sw);
        if (cod_rol!=7)
            BtnGuardar.doClick();
        else 
            sw=true;
        
        if (sw && sw_preventivo && verifica_avanzar()) {
            int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea avanzar esta TRANSACCION?",
                    "MENSAJE CAPRICORNIO",javax.swing.JOptionPane.YES_NO_OPTION );
            if (res != javax.swing.JOptionPane.YES_OPTION) return;
            try{
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                Map[] datos=null;               
                String destino="";
                System.out.println("llego 1 ----------------- con cod_rol: "+cod_rol+" y cod_w: "+cod_w);
                if (cod_rol==7 && cod_w==5) {
                    System.out.println("llego 2 ----entro con cod_rol: "+cod_rol+" y cod_w: "+cod_w);
                    int nuevo_cod_trans_nro=0;                    
                    if (VerificaTipoTramite(3)==0){
                        System.out.println("entro al verificando 3: "+VerificaTipoTramite(3));
                        datos = puerto.setCreaNroTramite("SET-upDateGeneraTramite",cod_transaccion,cod_almacen, 3, gestion,cod_trans_nro,cod_usuario);
                        if (datos!=null){
                            System.out.println("SET-upDateGeneraTramite devolvio datos");
                            nuevo_cod_trans_nro = Integer.parseInt(datos[0].get("COD_TRANS_NRO").toString());
                            System.out.println(nuevo_cod_trans_nro);
                            for (int f=0;f<TblItems.getRowCount();f++)
                                if (!"".equals(TblItems.getValueAt(f, 1).toString()))
                                    datos=puerto.setTransaccionDetalleNro("SET-upDateTransDetNro", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), nuevo_cod_trans_nro);
                        }
                        else
                            System.out.println("SET-upDateGeneraTramite devolvio nulo");
                    }                    
                }
                else
                    System.out.println("Nooo entro con cod_rol: "+cod_rol+" y cod_w: "+cod_w);
                
                //System.out.println(Integer.parseInt(TblItems.getValueAt(f, 2).toString()),cod_w,TblItems.getValueAt(f, 1).toString());
                for (int f=0;f<TblItems.getRowCount();f++){
                    System.out.println("Entro1");
                    if (!"".equals(TblItems.getValueAt(f, 1).toString())&& !"D".equals(TblItems.getValueAt(f, 1).toString())){
                        System.out.println("Entro2");
                        //System.out.println(Integer.parseInt(TblItems.getValueAt(f, 2).toString()+", "+cod_w+", "+TblItems.getValueAt(f, 1).toString()));
                        destino=puerto.setTransaccionesDestino("SET-upDateDestino",Integer.parseInt(TblItems.getValueAt(f, 2).toString()),cod_w,TblItems.getValueAt(f, 1).toString());  
                    }
                }
                    
                            
                
                BtnSalir.doClick();
            }
            catch (RemoteException e){
                javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            catch (ServiceException e){System.out.println(e);}
            catch (IllegalArgumentException e){
                javax.swing.JOptionPane.showMessageDialog(this,"Debe elegir una fila de la bandeja de salida para enviar el memorandum","SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_BtnAvanzarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
     String obs_almacen=null,obs_ppto=null,obs_adq=null,hoja_ruta=null, preventivo=null,certif_ppto=null, monto_ppto=null, fondo=null, nit=null,factura=null,memo=null;
     if(!"".equals(TxtObsAlmacen.getText().trim()))
         obs_almacen="'"+TxtObsAlmacen.getText().trim()+"'";
     if(!"".equals(TxtObsPpto.getText().trim()))
         obs_ppto="'"+TxtObsPpto.getText().trim()+"'";
     
     if(!"".equals(TxtMemo.getText().trim())) 
         memo="'"+TxtMemo.getText().trim()+"'";
     
     SimpleDateFormat form =new SimpleDateFormat("dd/MM/yyyy");
     String fec_fact="'"+form.format(CalFechaFact.getValue())+"'";
     String fec_ing="'"+form.format(CalFechaIng.getValue())+"'";
     String fec_noti="";
        sw=true;     
     switch (tab_habil) {
         case 0:             
             if ("ALM1".equals(origen)){
                 if(!"".equals(TxtFactura.getText().trim()))     
                    factura="'"+TxtFactura.getText().trim()+"'";
                 else {
                     javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el numero de factura","SYSTEM CAPRICORN",
                     javax.swing.JOptionPane.ERROR_MESSAGE);
                     TxtFactura.requestFocus();
                     sw=false;
                     return;
                 }
                 String obs = this.TxtObsAlmacen.getText().trim();
                 System.out.println("La observacion seria : "+obs);
                 ActualizaTransaccionIngresoAlmacen(factura, fec_fact,fec_ing, fec_noti, memo,obs);
             }
             else
                 ActualizaTransaccionAlmacen(obs_almacen);
             break;
         case 1:
             if(!sw_preventivo){
                  javax.swing.JOptionPane.showMessageDialog(this,"Debe adicionar por lo menos un preventivo","SYSTEM CAPRICORN",
                     javax.swing.JOptionPane.ERROR_MESSAGE);
                  return;
             }
//             if(this.JT_HRUTA.getText().length()==0){
//                  javax.swing.JOptionPane.showMessageDialog(this,"Debe adicionar la hoja de ruta","SYSTEM CAPRICORN",
//                     javax.swing.JOptionPane.ERROR_MESSAGE);
//                  sw = false;
//                  return;
//             }
             if(!this.VerificaPartidas()){
                 javax.swing.JOptionPane.showMessageDialog(this,"Todos los items deben tener partida","SYSTEM CAPRICORN",
                     javax.swing.JOptionPane.ERROR_MESSAGE);
                  sw = false;
                  return;
             }
             if(!this.VerificaPrecioU()){
                 javax.swing.JOptionPane.showMessageDialog(this,"Todos los items deben tener precio mayor a 0","SYSTEM CAPRICORN",
                     javax.swing.JOptionPane.ERROR_MESSAGE);
                 sw = false;
                  return;
             }
             
             if(!this.verificaAdjPreventivo()){
                 javax.swing.JOptionPane.showMessageDialog(this,"Debe subir el preventivo a sistema","SYSTEM CAPRICORN",
                     javax.swing.JOptionPane.ERROR_MESSAGE);
                  return;
             }
             
             
//             javax.swing.JOptionPane.showMessageDialog(this,"SE GUAR","SYSTEM CAPRICORN",
//                     javax.swing.JOptionPane.INFORMATION_MESSAGE);
                 
//             if(!"".equals(TxtHojaRuta.getText().trim()))
//                 hoja_ruta="'"+TxtHojaRuta.getText().trim()+"'";         
//             else {
//                 javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la hoja de ruta","SYSTEM CAPRICORN",
//                 javax.swing.JOptionPane.ERROR_MESSAGE);
//                 TxtHojaRuta.requestFocus();
//                 sw=false;
//                 return;
//             }
//             if(!"".equals(TxtPreventivo.getText().trim()))
//                 preventivo="'"+TxtPreventivo.getText().trim()+"'"; 
//             else {
//                 javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el preventivo","SYSTEM CAPRICORN",
//                 javax.swing.JOptionPane.ERROR_MESSAGE);
//                 TxtPreventivo.requestFocus();
//                 sw=false;
//                 return;
//             }
//             if(!"".equals(TxtCeritPpto.getText().trim()))
//                 certif_ppto="'"+TxtCeritPpto.getText().trim()+"'"; 
//             if(!"".equals(TxtMontoPpto.getText().trim()) && validacion.valMoneda(TxtMontoPpto.getText().trim()))
//                 monto_ppto=TxtMontoPpto.getText().trim(); 
//             else {
//                 javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el monto presupuestado","SYSTEM CAPRICORN",
//                 javax.swing.JOptionPane.ERROR_MESSAGE);
//                 TxtMontoPpto.requestFocus();
//                 sw=false;
//                 return;
//             }
//             
//             if (!(Double.parseDouble(TxtMontoPpto.getText())>=Double.parseDouble(del) && Double.parseDouble(TxtMontoPpto.getText())<=Double.parseDouble(hasta))){
//                 javax.swing.JOptionPane.showMessageDialog(this,"EL MONTO PRESUPUESTADO DEBE ESTAR EN EL RANGO DE LA CUANTIA \nENTRE "+del+" Y "+hasta+" Bs.","SYSTEM CAPRICORN",
//                 javax.swing.JOptionPane.ERROR_MESSAGE);
//                 TxtMontoPpto.requestFocus();
//                 sw=false;
//                 return;
//             }
//                 
//             if(!"".equals(TxtFondo.getText().trim()))
//                 fondo="'"+TxtFondo.getText().trim()+"'"; 
//             ActualizaTransaccionPpto(obs_ppto, hoja_ruta, preventivo,certif_ppto,monto_ppto,fondo);
//             GuardarDatos(this.JT_HRUTA.getText());
//             javax.swing.JOptionPane.showMessageDialog(this,"Cambios Guardados!!!","SYSTEM CAPRICORN",
//                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
             break;          
     }     
    }//GEN-LAST:event_BtnGuardarActionPerformed
    
    private void GuardarDatos(String hoja_ruta){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addHojaRuta("SET-addHojaRuta", cod_transaccion, hoja_ruta);
        } catch (Exception e) {
        }
    }
    
    private void TxtObsPptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtObsPptoKeyPressed
    if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
        BtnGuardar.requestFocus();
    }
    }//GEN-LAST:event_TxtObsPptoKeyPressed

    private void BtnRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRetornarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea rertornar esta TRANSACCION?",
                "MENSAJE CAPRICORNIO",javax.swing.JOptionPane.YES_NO_OPTION );
        if (res != javax.swing.JOptionPane.YES_OPTION) return; 
        
        String obs_almacen=null;
        int ts = TabTransaccion.getSelectedIndex();
        switch (ts){
            case 0:
                if("".equals(TxtObsAlmacen.getText().trim())){
                    javax.swing.JOptionPane.showMessageDialog(this,"DEBE INTRODUCIR UNA OBSERVACION PARA RETORAR EL TRAMITE","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    TxtObsAlmacen.requestFocus();
                    return;
                }            
                else
                    obs_almacen="'"+TxtObsAlmacen.getText().trim()+"'";                 
                break;
            case 1:
                if("".equals(TxtObsPpto.getText().trim())){
                    javax.swing.JOptionPane.showMessageDialog(this,"DEBE INTRODUCIR UNA OBSERVACION PARA RETORAR EL TRAMITE","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    TxtObsPpto.requestFocus();
                    return;
                }            
                else
                    obs_almacen="'"+TxtObsPpto.getText().trim()+"'";
        }
                         
        ActualizaTransaccionAlmacen(obs_almacen);
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=null;            
            for (int f=0;f<TblItems.getRowCount();f++)
                if (!"".equals(TblItems.getValueAt(f, 1).toString())) {
                    if (cod_rol==4)//TIENE ROL DE PRESUPUESTOS
                        datos=puerto.setTransaccionesPptoUnidad("SET-upDateTransPptoUnidad",Integer.parseInt(TblItems.getValueAt(f, 2).toString()),"B");
                    else
                        datos=puerto.setTransaccionesOrigen("SET-upDateOrig",Integer.parseInt(TblItems.getValueAt(f, 2).toString()),cod_w,TblItems.getValueAt(f, 1).toString());
                }
            BtnSalir.doClick();
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe elegir una fila de la bandeja de salida para enviar el memorandum","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnRetornarActionPerformed

    private void BtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnularActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea ANULAR esta TRANSACCION?",
                "MENSAJE CAPRICORNIO",javax.swing.JOptionPane.YES_NO_OPTION );
        if (res != javax.swing.JOptionPane.YES_OPTION) return;
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=null;            
            datos=puerto.setAnulaTransaccionAdq("SET-upDateAnulTransAdq",cod_transaccion);  
            javax.swing.JOptionPane.showMessageDialog(this,"LA TRANSACCION FUE ANULADA","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            BtnSalir.doClick();
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe elegir una fila de la bandeja de salida para enviar el memorandum","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAnularActionPerformed

    private void TblItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblItemsMousePressed
        if (evt.getClickCount() == 2) {
            int fila = TblItems.getSelectedRow();
            int col = TblItems.getSelectedColumn();
            if (col==9 && (cod_rol==7 || cod_rol==5) && (cod_w==3 || cod_w==4 || cod_w==5) && "".equals(TblItems.getValueAt(fila, 0))) {
                int cod_trans_detalle=Integer.parseInt(TblItems.getValueAt(fila, 2).toString());
                DiagSubeContrato dbc = new DiagSubeContrato(menu,cod_trans_detalle,TblItems.getValueAt(fila, 9).toString());
                dbc.AbreDialogo(); 
                /*if (dbc.ProveedorElejido().getCod_proveedor()!=0)
                    proveedor= prov.ProveedorElejido();

                TxtCasa.setText(proveedor.getCasa_comercial());*/
                if (!"".equals(dbc.NombreArchvo()))
                    TblItems.setValueAt(dbc.NombreArchvo(), fila, col);
            }
//            if(col==5 && "".equals(TblItems.getValueAt(fila, 0))){
//                String cod_trans_detalle =TblItems.getValueAt(fila, 2).toString();
////                String cod_item =TblItems.getValueAt(fila, 13).toString();
////                System.out.println("Hola mamá, el cod_trans_detalle es -> "+cod_trans_detalle+" y el cod_item es: "+cod_item);
//////                JD_CambiaItem JDci = new JD_CambiaItem(this.menu,true,cod_trans_detalle,cod_item);
//////                JDci.setVisible(true);
//////                JD_CambiaPartida JDcp = new JD_CambiaPartida();
////                
////                JD_CambiaPartida JDcp= new JD_CambiaPartida(this.menu,true,cod_trans_detalle,cod_item,this.gestion);
////                JDcp.setVisible(true);
////                this.LlenaItems();
//                System.out.println("Guuuaauu!!!");
//            }
                
        }
    }//GEN-LAST:event_TblItemsMousePressed

    private void TxtMemoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtMemoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnGuardar.requestFocus();
        }
}//GEN-LAST:event_TxtMemoKeyPressed

    private void CalFechaFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CalFechaFactKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtMemo.requestFocus();
        }
}//GEN-LAST:event_CalFechaFactKeyPressed

    private void TxtFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFacturaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            CalFechaFact.requestFocus();
        }
}//GEN-LAST:event_TxtFacturaKeyPressed

    private void BtnCertifPptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCertifPptoActionPerformed
 
        System.out.println("Hola Clipetes !!! :D");
        JD_NotaPpto JDN = new JD_NotaPpto(menu,true,cod_transaccion);
        JDN.setVisible(true);
        this.LlenaVistaPreventivo();
    }//GEN-LAST:event_BtnCertifPptoActionPerformed
    void MostrarReporte(){
        //int fila=TblTransaccionEstado.getSelectedRow();
        //int cod_trans_nro=Integer.parseInt(TblTransaccionEstado.getValueAt(fila, 0).toString()); 
        //String cod_estado=TblTransaccionEstado.getValueAt(fila, 1).toString();
        //String cod_estado="ALM1";        
        //String titulo=TblTransaccionEstado.getValueAt(fila, 4).toString();
        //if ((cod_rol==2 && cod_tramite==3)|| (cod_rol==5 && cod_tramite==2))
            //MostrarReporte(cod_trans_nro, "ALM1", 2, ":D");
        }
        void MostrarReporte(int cod_trans_nro,String cod_estado,int cod_tramite,String titulo){
        
        int a;
        List list=new ArrayList();
        try{
            String lug="";
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos5=puerto.getAnpe(cod_transaccion);
            lug=datos5[0].get("LUGAR_ENTREGA").toString();
            String cuantia=datos5[0].get("COD_CUANTIA").toString();
            a=Integer.parseInt(cuantia);
            System.out.println("cod_trans_nro: "+cod_trans_nro+" cod_estado: "+cod_estado+" cod_tramite: "+cod_tramite+" titulo: "+titulo);
            //String nom_usuario = puerto.getNombreUsuario(String.valueOf(cod_transaccion));
            //System.out.println("WWAADSADASDA------> "+nom_usuario);
            if(lug.equals("") || lug==null)
            {
                Map[] datos=puerto.getReporteSolicitudx(cod_trans_nro,cod_estado,cod_tramite);
                if (datos!=null){
                    System.out.println("Tiene Datos :D :D :D");
                    Map map = new HashMap();
                    int i=0;
                    String cod_trans_detalle,aux = null;
                    for (int f=0;f<datos.length;f++){
                        Transaccion trans = new Transaccion();
                        System.out.println("Tiene Datos :D :D :D -->"+datos[f].get("NRO_GESTION").toString());
                        trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                        //System.out.println(datos[f].get("NRO_GESTION").toString());
                        trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                        trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                        trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                        trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                        trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                        trans.setDetalle(datos[f].get("DETALLE").toString());
                        trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                        trans.setUser_maker(datos[f].get("USER_MAKER").toString());
                        System.out.println("Sipiii "+datos[f].get("USER_MAKER").toString());
                        //trans.setCantidad_pedido(datos[f].get("CANTIDAD_PEDIDO").toString());
                        trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                        //trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                        //System.out.println("Articulo :D :D :D --> "+datos[f].get("ARTICULO").toString());
                        trans.setArticulo(datos[f].get("ARTICULO").toString());
                        trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());                    
                        trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));

                        //System.out.println("------------------ "+datos[f].get("COD_TRANS_DETALLE").toString()+"---------------------");
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
                    
                }        
                else
                    System.out.println("Vaciooooo :P");
            }
            else
            {
                if(a==1)
                {
                    Map[] datos=puerto.getReporteSolicitudx2(cod_transaccion,cod_estado,1);
                        //Map[] datos=puerto.getReporteSolicitud(110,"PPTO",1); 
                        //Map[] datos=puerto.getReporteSolicitud2(cod_transaccion);
                        //System.out.println("es_____---> "+datos.length);
                        if(datos==null)
                            System.out.println("Es NULL");
                        if (datos!=null){                 
                            Map map = new HashMap();
                            int i=0;
                            String cod_trans_detalle,aux = null;
                            for (int f=0;f<datos.length;f++){
                                Transaccion trans = new Transaccion();
                                trans.setCod_transaccion(cod_transaccion);
                                trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                                trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                                trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                                trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                                trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                                trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                                trans.setDetalle(datos[f].get("DETALLE").toString());
                                trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                                trans.setUser_maker(datos[f].get("USER_MAKER").toString());
                                if(datos[f].get("TERMINOS_REF").toString().equals(""))
                                {

                                }
                                else
                                {
                                    trans.setProforma("X");
                                }


                                trans.setEmpresa(datos[f].get("DESCRIPCION").toString());
                                trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                                trans.setPreventivo(datos[f].get("CBTE").toString());
                                trans.setPartida(datos[f].get("PARTIDA").toString());
                                trans.setSubtotal(Double.parseDouble(datos[f].get("SUB_TOTAL").toString()));
                                System.out.println("aqui esta la fecha de prof "+datos[f].get("FECHA_DOC").toString());
                                trans.setFec_proforma(datos[f].get("FECHA_DOC").toString());
                                //trans.setCantidad_pedido(datos[f].get("CANTIDAD_PEDIDO").toString());
                                trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                                //trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                                //System.out.println("Articulo :D :D :D --> "+datos[f].get("ARTICULO").toString());
                                trans.setArticulo(datos[f].get("ARTICULO").toString());
                                trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());                    
                                trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));
                                trans.setLugar_entrega(datos[f].get("LUGAR_ENTREGA").toString());
                                cod_trans_detalle=datos[f].get("COD_TRANS_DETALLE").toString();
                                if(!cod_trans_detalle.equals(aux)){
                                    i++;
                                    System.out.println("El indice es wujuuu: "+i);
                                    trans.setIndice(String.valueOf(i));
                                }
                                aux=cod_trans_detalle;
                                trans.setCod_trans_detalle(cod_trans_detalle);
                                //System.out.println("i: "+f+" cod_trans_detalle: "+datos[f].get("COD_TRANS_DETALLE").toString());
                                list.add(trans);
                            }                                                                                            
                        }
                }
                else
                {
                    System.out.println("el tramite es anpe");
                        Map[] datos=puerto.getReporteSolicitudx3(cod_transaccion,cod_estado,1);
                        System.out.println("el tramite es anpe");
                        //Map[] datos=puerto.getReporteSolicitud(110,"PPTO",1); 
                        //Map[] datos=puerto.getReporteSolicitud2(cod_transaccion);
                        //System.out.println("es_____---> "+datos.length);
                        if(datos==null)
                            System.out.println("Es NULL");
                        if (datos!=null){                 
                            Map map = new HashMap();
                            int i=0;
                            String cod_trans_detalle,aux = null;
                            for (int f=0;f<datos.length;f++){
                                Transaccion trans = new Transaccion();
                                trans.setCod_transaccion(cod_transaccion);
                                trans.setNro_gestion(datos[f].get("NRO_GESTION").toString());
                                trans.setFecha_creacion(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_CREACION").toString()));
                                trans.setFecha_envio(i_formatterDate.i_conveterStandardToDate(datos[f].get("FECHA_ENVIO").toString()));
                                trans.setUnidad_sol(datos[f].get("UNIDAD_SOL").toString());
                                trans.setUnidad_des(datos[f].get("UNIDAD_DES").toString());
                                trans.setUsuario_sol(datos[f].get("USUARIO_SOL").toString());
                                trans.setDetalle(datos[f].get("DETALLE").toString());
                                trans.setUnidad_medida(datos[f].get("UNIDAD_MEDIDA").toString());
                                trans.setUser_maker(datos[f].get("USER_MAKER").toString());
                                trans.setMetodo(datos[f].get("METODO").toString());
                                trans.setResponsable(datos[f].get("RESPONSABLE").toString());
                                trans.setCargo(datos[f].get("CARGO").toString());
                                trans.setCorreo(datos[f].get("CORREO").toString());
                                if(datos[f].get("INSPECCION").toString().equals("checked"))
                                {
                                    trans.setInspeccion("X");
                                }
                                else
                                {
                                    trans.setInspeccion("");
                                }
                                if(datos[f].get("CONSULTA").toString().equals("checked"))
                                {
                                    trans.setConsultas("X");
                                }
                                else
                                {
                                    trans.setConsultas("");
                                }
                                if(datos[f].get("REUNION").toString().equals("checked"))
                                {
                                    trans.setReunion("X");
                                }
                                else
                                {
                                    trans.setReunion("");
                                }
                                System.out.println("aqui sale los dias de la propuesta "+datos[f].get("DIAS1").toString());
                                trans.setDias1(datos[f].get("DIAS1").toString());
                                trans.setDias2(datos[f].get("DIAS2").toString());
                                if(datos[f].get("TERMINOS_REF").toString().equals(""))
                                {

                                }
                                else
                                {
                                    trans.setProforma("X");
                                }


                                trans.setEmpresa(datos[f].get("DESCRIPCION").toString());
                                trans.setPrecio_unit(Double.parseDouble(datos[f].get("PRECIO_UNIT").toString()));
                                trans.setPreventivo(datos[f].get("CBTE").toString());
                                trans.setPartida(datos[f].get("PARTIDA").toString());
                                trans.setSubtotal(Double.parseDouble(datos[f].get("SUB_TOTAL").toString()));
                                System.out.println("aqui esta la fecha de prof "+datos[f].get("FECHA_DOC").toString());
                                trans.setFec_proforma(datos[f].get("FECHA_DOC").toString());
                                //trans.setCantidad_pedido(datos[f].get("CANTIDAD_PEDIDO").toString());
                                trans.setCantidad_pedido(Integer.parseInt(datos[f].get("CANTIDAD_PEDIDO").toString()));
                                //trans.setTipo_item(datos[f].get("TIPO_ITEM").toString());
                                //System.out.println("Articulo :D :D :D --> "+datos[f].get("ARTICULO").toString());
                                trans.setArticulo(datos[f].get("ARTICULO").toString());
                                trans.setDetalle_solicitud(datos[f].get("DETALLE_SOLICITUD").toString());                    
                                trans.setNro_transaccion(Integer.parseInt(datos[f].get("NRO_TRANSACCION").toString()));
                                trans.setLugar_entrega(datos[f].get("LUGAR_ENTREGA").toString());
                                cod_trans_detalle=datos[f].get("COD_TRANS_DETALLE").toString();
                                if(!cod_trans_detalle.equals(aux)){
                                    i++;
                                    System.out.println("El indice es wujuuu: "+i);
                                    trans.setIndice(String.valueOf(i));
                                }
                                aux=cod_trans_detalle;
                                trans.setCod_trans_detalle(cod_trans_detalle);
                                //System.out.println("i: "+f+" cod_trans_detalle: "+datos[f].get("COD_TRANS_DETALLE").toString());
                                list.add(trans);
                            }                                                                                            
                        }
                }
            }
            RepTransaccion rep = new RepTransaccion();
            System.out.println("titulo: "+titulo);
            System.out.println("cod_tramite: "+cod_tramite);
            System.out.println("cod_trans_nro: "+cod_trans_nro);

            rep.Reporte(list,cuantia+"#"+lug+"#"+cod_transaccion,cod_tramite,cod_trans_nro,cod_almacen);
            
               
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println("error 1:"+e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"LA ORDEN NO FUE APROBADA O \n NO ELIJIO UNA FILA PARA PODER IMPRIMIR EL REPORTE --> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        System.out.println("cod_trans_nro: "+cod_trans_nro);
        MostrarReporte(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");
        //MostrarReporte(145, "PPTO", 1, "SOLICITUD DE COMPRAS");
    }//GEN-LAST:event_jButton2ActionPerformed
 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(this.cod_transaccion!=0){
            JDAdjuntos JDA = new JDAdjuntos(menu,true,cod_transaccion,cod_rol);
            JDA.AbreDialogo();
        }else{
            JOptionPane.showMessageDialog(null, "Usted no a seleccionado ninguna Solicitud", "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void CalFechaIngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CalFechaIngKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalFechaIngKeyPressed

    private void eliminaPreventivo(int cod_transaccion, String cod_preventivo){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("El cod_transaccion es: "+cod_transaccion+", cod_preventivo: "+cod_preventivo);
            puerto.delPreventivo("SET-delPreventivo", cod_transaccion, cod_preventivo);
            JOptionPane.showMessageDialog(null, "Se elimino el preventivo exitosamente", "Error",JOptionPane.OK_OPTION);
            this.LlenaVistaPreventivo();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el preventivo: "+e, "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int fila = JTbl_VPreventivo.getSelectedRow();
        int col = JTbl_VPreventivo.getSelectedColumn();
//        System.out.println("cod_w="+cod_w+" , cod_rol = "+cod_rol+", fila = "+fila+" ,columna = "+col);
//        System.out.println("A ver: "+JTbl_VPreventivo.getValueAt(fila, 0));
        try {
            this.eliminaPreventivo(this.cod_transaccion,JTbl_VPreventivo.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un preventivo", "Error",JOptionPane.ERROR_MESSAGE);
        }
       
            
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JD_TPARTIDA JDTP = new JD_TPARTIDA(this.menu,true,this.cod_trans_nro);
        JDTP.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        GuardarDatos(this.JT_HRUTA.getText());
        javax.swing.JOptionPane.showMessageDialog(this, "Se guardo la Hoja de Ruta", "SYSTEM CAPRICORN",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void BtnCertifPpto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCertifPpto1ActionPerformed
        // TODO add your handling code here:
        JD_Preventivo certif_ppto = new JD_Preventivo(menu,gestion,cod_transaccion);
        certif_ppto.AbreDialogo();
        Ppto ppto= certif_ppto.Certif_Ppto();
        this.LlenaVistaPreventivo();
    }//GEN-LAST:event_BtnCertifPpto1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnular;
    private javax.swing.JButton BtnAvanzar;
    private javax.swing.JButton BtnCertifPpto;
    private javax.swing.JButton BtnCertifPpto1;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnRetornar;
    private javax.swing.JButton BtnSalir;
    private net.sf.nachocalendar.components.DateField CalFechaFact;
    private net.sf.nachocalendar.components.DateField CalFechaIng;
    private javax.swing.JLabel JL_TotalM;
    private javax.swing.JTextField JT_HRUTA;
    private javax.swing.JTable JTbl_VPreventivo;
    private javax.swing.JLabel LblA;
    private javax.swing.JLabel LblCuantia;
    private javax.swing.JLabel LblDe;
    private javax.swing.JLabel LblDetalle;
    private javax.swing.JLabel LblFactura;
    private javax.swing.JLabel LblFecFact;
    private javax.swing.JLabel LblFechaIng;
    private javax.swing.JLabel LblMemo;
    private javax.swing.JLabel LblNro;
    private javax.swing.JLabel LblTituloNro;
    private javax.swing.JLabel LblUnidadDes;
    private javax.swing.JLabel LblUnidadSol;
    private javax.swing.JPanel PnlAlmacen;
    private javax.swing.JScrollPane PnlItems;
    private javax.swing.JPanel PnlPpto;
    private javax.swing.JScrollPane PnlPreventivo;
    private javax.swing.JScrollPane ScrObs;
    private javax.swing.JTabbedPane TabTransaccion;
    private javax.swing.JTable TblItems;
    private javax.swing.JTextField TxtFactura;
    private javax.swing.JTextField TxtMemo;
    private javax.swing.JTextPane TxtObsAlmacen;
    private javax.swing.JTextField TxtObsPpto;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
