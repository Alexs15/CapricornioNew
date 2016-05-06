/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiagOrdenesDetalle.java
 *
 * Created on 20-09-2011, 04:23:37 PM
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Proveedor;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import static umsa.capricornio.gui.menu.FrmMenu.cod_usuario;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.DiagBuscaProveedor;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.Item;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.ItemCellEditor;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.TablaOrden;
import umsa.capricornio.gui.transacciones.FrmTransacciones;
import umsa.capricornio.gui.transacciones.JD_CambiaPartida;
import umsa.capricornio.gui.transacciones.JD_DefItem;
import umsa.capricornio.gui.transacciones.JD_Opciones;
import umsa.capricornio.gui.transacciones.JD_abmDetalle;
import umsa.capricornio.gui.transacciones.JD_addDetalle;
import umsa.capricornio.gui.transacciones.JD_updateItem;
import umsa.capricornio.gui.transacciones.reporte.GeneraRepo;
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;
import umsa.capricornio.gui.transacciones.reporte.GetResoluciones;
import umsa.capricornio.gui.transacciones.reporte.Reportes;
import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.PartidaCellEditor;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedida;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedidaCellEditor;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author Henrry
 */
public class DiagOrdenesDetalle extends javax.swing.JDialog {

    FrmMenu menu;
    JInternalFrame ft;
    private Reportes reportes;
    private double Total;
    int cod_almacen, cod_trans_nro, cod_rol, gestion, cod_w, tab_habil,cod_user;
    private Runtime r;
    String tramite, origen, detalle, unidad_sol, unidad_des, nro, cuantia, del, hasta;
    TablaOrden orden;
    private boolean sw,sw_modificacion_concluido=false;
    private int cod_transaccion;
    private String des,dias;
    private Proveedor proveedor;
    GetResoluciones genera_reportes;
    

    /**
     * Creates new form DiagOrdenesDetalle
     */
    
    public DiagOrdenesDetalle(JInternalFrame frmt, FrmMenu menu, int cod_almacen, int cod_trans_nro, int cod_rol, String tramite, int gestion, int cod_w, String origen, String detalle, String unidad_sol, String unidad_des, String nro, String cuantia, String del, String hasta,int cod_user,boolean sw_modificacion_concluido) {
        super(menu, false);
        initComponents();
        ft = frmt;
        //this.BtnRetornar.setVisible(false);
        this.menu = menu;
        this.cod_almacen = cod_almacen;
        this.cod_trans_nro = cod_trans_nro;
        this.cod_rol = cod_rol;
        this.tramite = tramite;
        this.gestion = gestion;
        this.cod_w = cod_w;
        this.origen = origen;
        this.detalle = detalle;
        this.unidad_des = unidad_des;
        this.unidad_sol = unidad_sol;
        this.nro = nro;
        this.cuantia = cuantia;
        this.del = del;
        this.hasta = hasta;
        this.cod_user = cod_user;
        this.sw_modificacion_concluido=sw_modificacion_concluido;
        genera_reportes = new GetResoluciones(this.cod_almacen);
        //this.des=dest;
        if(this.sw_modificacion_concluido==true)
        {
         this.BtnAnular.setVisible(true);   
        }
        else
        {
         
        this.BtnAnular.setVisible(false);   
        }
        
        bloquea();
        System.out.println("Yuhuuuuu :P");
        getDatosAlmacen(cod_trans_nro);
        ConstruyeTablaItems();
        if(cuantia.equals("COMPRA MENOR"))
            verificadias();
        else
        {
            jLabel14.setVisible(false);
            jLabel9.setVisible(false);
        }
        if(this.sw_modificacion_concluido){
            this.SetEstadoModificado(cod_transaccion);
        }

    }
    private void SetEstadoModificado(int cod_transaccion){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.setModificaEstado(cod_transaccion, "M");
            
        } catch (Exception e) {
            System.err.println("Error terrible :( al modificar el estado de la transaccion");
        }
    }
    /*private void addProv(){
        
     }*/
    private void verificadias()
    {
        jLabel14.setVisible(true);
        jLabel9.setVisible(true);
        if(tab_habil==0)
            {
                int t=15;
                try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getDias(this.cod_trans_nro);
            t=15;
            if(!datos[0].get("DIAS").toString().equals(""))
            { 
                System.out.println("ttttt ya tiene dias");
                dias=datos[0].get("DIAS").toString();
                jLabel14.setText(dias);
            }
            else
            {
                System.out.println("aqui era");
                /*boolean sw12=true;
                do{
                String resp=JOptionPane.showInputDialog("escribe el tiempo de entrega en caso de ser mayor a 15 dias\nsi es menor deje en blanco y click en aceptar");
                if(resp==null)
                {
                    /*this.setVisible(false);
                    ft.setVisible(true);*/
                    /*BtnSalir1.doClick();
                    System.err.println("jajaja si detecto el cancel");
                    //return;
                    
                }
                if(resp.equals(""))
                {
                    System.out.println("s 15");
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
                int res1 = javax.swing.JOptionPane.showConfirmDialog(this, "Esta seguro de que el tiempo de duracion se "+t+" dias",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
                if (res1 != javax.swing.JOptionPane.YES_OPTION) {
                    return;
                }
                Map[] datos1=puerto.setDias(this.cod_trans_nro,t);*/
            }
        }
        catch(Exception e)
        {}
              try{
              if(this.cod_w==6 && cuantia.equals("COMPRA MENOR") && t<=15)
              {
                  int cod_alternativo=cod_w;
              }
              }catch(Exception e)
              {}
        }
    }
    private void getDatosAlmacen(int cod_trans_nro) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getDatosAlmacen(cod_trans_nro);
            if (datos != null) {
                for (int f = 0; f < datos.length; f++) {
                    //Transaccion trans = new Transaccion();
//                    System.out.println(" -->" + datos[f].get("FACTURA").toString());
//                    System.out.println(" -->" + datos[f].get("FECHA_FACT").toString());
//                    System.out.println(" -->" + datos[f].get("FECHA_ING").toString());
//                    System.out.println(" -->" + datos[f].get("MEMO").toString());
//                    System.out.println(" -->" + datos[f].get("OBS").toString());
//                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                    
                    //String fec_fact="'"+form.format(CalFechaFact.getValue())+"'";
                    this.TxtFactura.setText(datos[f].get("FACTURA").toString());
                    this.TxtObsAlmacen.setText(datos[f].get("OBS").toString());
                    this.TxtMemo.setText(datos[f].get("MEMO").toString());
//                    System.err.println("Try to Print");
//                    System.err.println("1) Esta fecha es -->>> "+datos[f].get("FECHA_FACT").toString());
//                    System.err.println("2) Esta fecha es -->>> "+form.format(datos[f].get("FECHA_FACT").toString()));
//                    Date hoy = new Date(datos[f].get("FECHA_FACT").toString());
//                    System.err.println("el dia de hoy ---> "+hoy);
                   CalFechaFact.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date(datos[f].get("FECHA_FACT").toString())));
                   CalFechaIng.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date(datos[f].get("FECHA_ING").toString())));
                   CalFechaNotificacion.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date(datos[f].get("FECHA_NOTI").toString())));
                    
                    
                    
//                    this.CalFechaIng.setValue(form.format(datos[f].get("FECHA_ING").toString()));
//                    this.CalFechaNotificacion.setValue(form.format(datos[f].get("FECHA_NOTI").toString()));

                    //this.TxtObsAdq
                }
                //this.TxtObsAlmacen.setText("Hola Mundo");

            }
        } catch (Exception e) {
        }
    }

    private void bloquea() {
        if (this.cuantia.equals("COMPRA MENOR")) {
//            System.out.println("Waooooo :D");
            this.jButton5.setEnabled(false);
            this.jButton6.setEnabled(false);
            this.jButton10.setEnabled(false);
            
//            this.jButton12.setEnabled(false);
        }
//        System.out.println("El cod_rol es: "+cod_rol);
        if (cod_rol == 7) {
            this.jButton11.setEnabled(true);
            BtnGarantia.setEnabled(true);
        } else {
            this.jButton11.setEnabled(false);
            BtnGarantia.setEnabled(false);
        }
//        System.out.println("El cod_rol es: "+cod_rol);
        if (cod_rol == 7) {
            this.jButton11.setEnabled(true);
            BtnGarantia.setEnabled(true);
        } else {
            this.jButton11.setEnabled(false);
            BtnGarantia.setEnabled(false);
        }
        
        if(sw_modificacion_concluido){
            this.BtnAvanzar.setEnabled(false);
        }
    }

    private List<Item> getItemClasificador(int gestion, int partida) {
        List<Item> listItem = null;
        try {
            listItem = new ArrayList();
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getItemClasificador(gestion, partida);

            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    //this.JC_Partidas.addItem(datos[c].get("PARTIDA")+" - "+datos[c].get("DETALLE") );
                    System.out.println("--> " + datos[c].get("ARTICULO").toString());
                    listItem.add(new Item(datos[c].get("ARTICULO").toString()));
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        return listItem;
    }

    private List<Partida> getPartidas(int gestion) {
        List<Partida> listPartida = null;
        try {
            listPartida = new ArrayList();
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getPartidas(String.valueOf(gestion));

            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    //this.JC_Partidas.addItem(datos[c].get("PARTIDA")+" - "+datos[c].get("DETALLE") );
//                    System.out.println("--> " + datos[c].get("PARTIDA").toString());
//                        System.out.println("--> "+datos[c].get("PARTIDA").toString()+" - "+datos[c].get("DETALLE").toString()); 
                    listPartida.add(new Partida(datos[c].get("PARTIDA").toString() + " - " + datos[c].get("DETALLE").toString()));
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        return listPartida;
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

    private void ConstruyeTablaItems() {

        List<Partida> listPartida = this.getPartidas(2014);
        List<UnidadMedida> listUnidadMedida = this.getUnidadMedida();
        orden = new TablaOrden(this, cod_rol, origen,this.cod_user);
        TblItems.setAutoCreateColumnsFromModel(false);
        TblItems.setModel(orden);

//        List<Item> listItem = new ArrayList();
//        listItem.add(new Item("1"));
//        listItem.add(new Item("2"));
//        listItem.add(new Item("3"));
        //List<Item> listItem = this.getItemClasificador(2015, 34200);
//          List<Item> listItem = null;
        for (int k = 0; k < TablaOrden.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaOrden.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
             renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
//            TableCellEditor edit =new ItemCellEditor(listItem);
            TableCellEditor edit1 = new PartidaCellEditor(listPartida);
            TableCellEditor edit2 = new UnidadMedidaCellEditor(listUnidadMedida);
            TableColumn column;
            
//            if (k == 4) {
//                column = new TableColumn(k, TablaOrden.m_columns[k].m_width, renderer, edit2);
//            } else {
//                column = new TableColumn(k, TablaOrden.m_columns[k].m_width, renderer, null);
//            }
            
            if (k == 4) {
                column = new TableColumn(k, TablaOrden.m_columns[k].m_width, renderer, edit2);
            }else if(k==6){
                column = new TableColumn(k, TablaOrden.m_columns[k].m_width, renderer, edit1);
            } 
            else {
                column = new TableColumn(k, TablaOrden.m_columns[k].m_width, renderer, null);
            }
            TblItems.addColumn(column);
        }
        JTableHeader header = TblItems.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlItems.getViewport().add(TblItems);
    }

    private void GetAdjudicado() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();

            //Map[] datos = puerto.getTransaccionNro(cod_trans_nro);
            System.out.println("Recuperando datos -- cod_trans_nro: " + cod_trans_nro);
            Map[] datos = puerto.getProponenteADJ(cod_trans_nro);
            if (datos != null) {
                /*System.out.println("Devolvio datos yeihhh --> "+datos[0].get("COD_PROVEEDOR").toString());
                 TxtCasa.setText(datos[0].get("NOMBRE_COMERCIAL").toString());*/

                proveedor.setCod_proveedor(datos[0].get("COD_PROVEEDOR").toString());
                proveedor.setTipo_cod(datos[0].get("TIPO").toString());
                proveedor.setClase(datos[0].get("CLASE").toString());
                proveedor.setNombre(datos[0].get("NOMBRE").toString());
                proveedor.setCasa_comercial(datos[0].get("NOMBRE_COMERCIAL").toString());
                proveedor.setDireccion(datos[0].get("DIRECCION").toString());
                proveedor.setTelefono(datos[0].get("TELEFONO").toString());
                TxtCasa.setText(proveedor.getCasa_comercial());
            }
        } catch (Exception e) {

        }
    }

    private void LlenaDetalles() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();

            //Map[] datos = puerto.getTransaccionNro(cod_trans_nro);
            System.out.println("Recuperando datos -- cod_trans_nro: " + cod_trans_nro);
            Map[] datos = puerto.getProponenteADJ(cod_trans_nro);
            if (datos != null) {
                /*System.out.println("Devolvio datos yeihhh --> "+datos[0].get("COD_PROVEEDOR").toString());
                 TxtCasa.setText(datos[0].get("NOMBRE_COMERCIAL").toString());*/
                proveedor.setCod_proveedor(datos[0].get("COD_PROVEEDOR").toString());
                proveedor.setTipo_cod(datos[0].get("TIPO").toString());
                proveedor.setClase(datos[0].get("CLASE").toString());
                proveedor.setNombre(datos[0].get("NOMBRE").toString());
                proveedor.setCasa_comercial(datos[0].get("NOMBRE_COMERCIAL").toString());
                proveedor.setDireccion(datos[0].get("DIRECCION").toString());
                proveedor.setTelefono(datos[0].get("TELEFONO").toString());
                TxtCasa.setText(proveedor.getCasa_comercial());
            }
            datos = puerto.getTransaccionNro(cod_trans_nro);
            if (datos != null) {
                System.out.println("Nuuuuu");
                this.cod_transaccion = Integer.parseInt(datos[0].get("COD_TRANSACCION").toString());
                if (this.cod_rol == 5) {
                    this.TxtObsAdq.setText(datos[0].get("OBS").toString());
                }
            }

            /*
             if (datos!=null){
             System.out.println("con datos en llena Detalles wau wau "+cod_trans_nro);
             for (int c=0;c<datos.length;c++){
             TxtCasa.setText(datos[c].get("CASA_COMERCIAL").toString());
             TxtObsAdq.setText(datos[c].get("OBS").toString());
             TxtFactura.setText(datos[c].get("FACTURA").toString());
             if (!("".equals(datos[c].get("FECHA_FACT")) || datos[c].get("FECHA_FACT")==null))
             CalFechaFact.setValue(datos[c].get("FECHA_FACT"));
             TxtMemo.setText(datos[c].get("MEMO").toString());
             //TxtResolucion.setText(datos[c].get("RESOLUCION_ADM").toString());
             //TxtCuce.setText(datos[c].get("CUCE").toString());
             cod_transaccion=Integer.parseInt(datos[c].get("COD_TRANSACCION").toString());
             //TxtInfComision.setText(datos[c].get("INF_COMISION").toString());
             // TxtNroPropuesta.setText(datos[c].get("NRO_PROPUESTA").toString());
                    
             if (!("".equals(datos[c].get("COD_PROVEEDOR").toString()) || datos[c].get("COD_PROVEEDOR")==null))
             proveedor.setCod_proveedor(datos[c].get("COD_PROVEEDOR").toString());
             proveedor.setCasa_comercial(datos[c].get("CASA_COMERCIAL").toString());
             }
             }
             */
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    public void LlenaItems() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getItemsPorOrdenDeCompra2(cod_trans_nro);
            CerearTablaItems();
            if (datos != null) {
                System.out.println("Entro y el cod_trans_nro: " + cod_trans_nro);
                for (int c = 0; c < datos.length; c++) {
                    orden.insert(c);
                    TblItems.tableChanged(new TableModelEvent(orden, c, c, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                    TblItems.setValueAt(datos[c].get("COD_COMPLEMENTO"), c, 0);
                    TblItems.setValueAt(datos[c].get("ESTADO"), c, 1);
                    TblItems.setValueAt(datos[c].get("COD_TRANS_DETALLE"), c, 2);

                    /*Wooooooo*/
                    TblItems.setValueAt(datos[c].get("CANTIDAD_PEDIDO"), c, 3);
                    
                    //TblItems.setValueAt(datos[c].get("UNIDAD_MEDIDA"), c, 4);
                    if (datos[c].get("UNIDAD_MEDIDA").toString().equals("")) {
                        TblItems.setValueAt(new UnidadMedida("No tiene"), c, 4);
                    } else {
                        TblItems.setValueAt(new UnidadMedida(datos[c].get("UNIDAD_MEDIDA").toString()), c, 4);
                    }

//                    System.out.println("----------------->> El cod_item : "+datos[c].get("COD_ITEM").toString());
                    if (datos[c].get("COD_ITEM").toString().equals("") || datos[c].get("COD_ITEM").toString().equals("0")) {
                        TblItems.setValueAt("Sin Definir", c, 5);
                    } else {
                        TblItems.setValueAt(datos[c].get("COD_ITEM").toString(), c, 5);
                    }
//                    TblItems.setValueAt("Hola :P",c,5);

                    //TblItems.setValueAt(datos[c].get("TIPO_ITEM"),c,5);
//                    TblItems.setValueAt(datos[c].get("PARTIDA"),c,6);
                    if (datos[c].get("PARTIDA").toString().equals("")) {
                        TblItems.setValueAt(new Partida("No tiene"), c, 6);
                    } else {
                        TblItems.setValueAt(new Partida(datos[c].get("PARTIDA").toString()), c, 6);
                    }
                    if(datos[c].get("ARTICULO").toString().equals(""))
                    {
                        TblItems.setValueAt(datos[c].get("ARTICULO_ALM"), c, 7);
                        TblItems.setValueAt(datos[c].get("ARTICULO"), c, 9);
                    }
                    else
                    {
                        TblItems.setValueAt(datos[c].get("ARTICULO"), c, 7);
                        TblItems.setValueAt(datos[c].get("ARTICULO_ALM"), c, 9);
                    }
                    
                    TblItems.setValueAt(datos[c].get("ARTICULO_ACT"), c, 8);
                    System.out.println("******************** uuuuuuuu: " + datos[c].get("ARTICULO"));
                    
                    
                    if (cod_rol == 7) {
                        TblItems.setValueAt(datos[c].get("CONTRATO"), c, 10);
                    } else {
                        TblItems.setValueAt(datos[c].get("DBC"), c, 10);
                    }
                    System.out.println("El precio unitario es: " + datos[c].get("PRECIO_UNIT"));
                    TblItems.setValueAt(datos[c].get("PRECIO_UNIT"), c, 11);
                    //TblItems.setValueAt(datos[c].get("COD_ITEM"),c,13);
                }
            } else {
                System.out.println("Vacio");
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    int cantidad_tram;

    private void VerificaTipoTramite(int cod_tramite) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();

            for (int f = 0; f < TblItems.getRowCount(); f++) {
                if (!"".equals(TblItems.getValueAt(f, 1).toString())) {
                    Map[] datos = puerto.getTieneTipoTramite(cod_tramite, Integer.parseInt(TblItems.getValueAt(f, 2).toString()));
                    if (datos != null) {
                        cantidad_tram = Integer.parseInt(datos[0].get("CANTIDAD").toString());
                        break;
                    }

                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    void CerearTablaItems() {
        int f = TblItems.getRowCount();
        for (int i = f - 1; i >= 0; i--) {
            if (orden.delete(i)) {
                TblItems.tableChanged(new TableModelEvent(
                        orden, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        }
    }

    void ActualizaTransaccionAlmacen(String obs_almacen) {
        System.out.println("Tratando de Guardar obs_alm para cod_trans_nro: " + cod_trans_nro + " el Dato obs_almacen: " + obs_almacen);
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setActualizaTransaccionAlmDos(cod_trans_nro, obs_almacen);
            javax.swing.JOptionPane.showMessageDialog(this, "DATOS ALMACENADOS", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    void ActualizaTransaccionIngresoAlmacen(String factura, String fecha_fact, String fecha_ing, String fecha_noti, String memo, String obs) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setActualizaTransaccionIngresoAlm(cod_trans_nro, factura, fecha_fact, fecha_ing, fecha_noti, memo, obs);
            javax.swing.JOptionPane.showMessageDialog(this, "DATOS ALMACENADOS", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    void ActualizaTransaccionAdq(String obs_adq, String cod_proveedor) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("cod_trans_nro: " + cod_trans_nro);
            System.out.println("cod_proveedor: " + cod_proveedor);
            Map[] datos = puerto.setActualizaTransaccionAdq("SET-upDateActTransAdq", cod_trans_nro, obs_adq, cod_proveedor, "", "", "", "");
            javax.swing.JOptionPane.showMessageDialog(this, "DATOS ALMACENADOS", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    void AddObservacion() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("================>   Este es el cod_trans_nro: " + this.cod_trans_nro);
            puerto.addObservacion(cod_trans_nro, this.TxtObsAdq.getText().toString());
        } catch (Exception e) {
        }
    }

    void ActualizaTransaccionAdq(String cod_proveedor) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("cod_trans_nro: " + cod_trans_nro);
            System.out.println("cod_proveedor: " + cod_proveedor);
            //Map[] datos=puerto.setActualizaTransaccionAdq("SET-upDateActTransAdq", cod_trans_nro, obs_adq,cod_proveedor,"","","","");
            puerto.downProveedor(cod_trans_nro);
            Map[] datos = puerto.addProveedor(cod_trans_nro, cod_proveedor);
            puerto.upProveedor(cod_trans_nro, cod_proveedor);
            javax.swing.JOptionPane.showMessageDialog(this, "DATOS ALMACENADOS", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    public void MultiplicaCantidadPrecioUnit(int fila) {
        //int fila = TblItems.getSelectedRow();
        DecimalFormat formatter = new DecimalFormat("###.00");
        //if (fila >= 0){            
        try {
            if (!"".equals(TblItems.getValueAt(fila, 11).toString())) {
                double cantidad = Double.parseDouble(TblItems.getValueAt(fila, 3).toString());
                double precio_unit = Double.parseDouble(TblItems.getValueAt(fila, 11).toString());
                String numero = (String) formatter.format(cantidad * precio_unit);
                TblItems.setValueAt(numero.replace(',', '.'), fila, 12);
            } else {
                TblItems.setValueAt("", fila, 12);
            }
            TblItems.repaint();
        } catch (NumberFormatException e) {
        }
        //}
    }

    public void SumaPreciosTotales() {
        String sum = "";
        double sumT = 0.0;
        for (int f = 0; f < TblItems.getRowCount(); f++) {
            sum = (String) TblItems.getValueAt(f, 12);
            if (!"".equals(sum)) {
                sumT += Double.parseDouble(sum);
            }
        }
        /*if(!verifica_monto(sumT)){
         javax.swing.JOptionPane.showMessageDialog(this,"El monto no es el correcto de acuerdo a la partida","SYSTEM CAPRICORN",
         javax.swing.JOptionPane.ERROR_MESSAGE);
         }*/
        //System.out.println("----------------------  La suma: "+sumT+" es y la cuantia: "+cuantia+"   -------------------------");
        //System.out.println("----------------------  Del: "+this.del+" - hasta: "+this.hasta+"   -------------------------");
        DecimalFormat formatter = new DecimalFormat("###,##0.00");
        TxtTotal.setText(formatter.format(sumT));
        this.Total = sumT;
    }

    private boolean verifica_monto(double monto) {
        return (monto >= Double.parseDouble(this.del)) && (monto <= Double.parseDouble(this.hasta));
        //return true;
    }

    public void AbreDialogo() {
//        this.sw_modificacion_concluido = sw;
        ft.setVisible(false);
        setVisible(true);
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
        LblUnidadSol = new javax.swing.JLabel();
        LblUnidadDes = new javax.swing.JLabel();
        LblDetalle = new javax.swing.JLabel();
        LblTituloNro = new javax.swing.JLabel();
        LblNro = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        LblCuantia = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        LblA = new javax.swing.JLabel();
        LblDe = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        PnlItems = new javax.swing.JScrollPane();
        TblItems = new javax.swing.JTable();
        TabTransaccion = new javax.swing.JTabbedPane();
        PnlAdquisiciones = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        TxtCasa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtObsAdq = new javax.swing.JTextField();
        BtnBuscaProveedor = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        JT_HRUTA = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        PnlAlmacen = new javax.swing.JPanel();
        ScrObs = new javax.swing.JScrollPane();
        TxtObsAlmacen = new javax.swing.JTextPane();
        LblFactura = new javax.swing.JLabel();
        LblFecFact = new javax.swing.JLabel();
        LblFechaIng = new javax.swing.JLabel();
        TxtFactura = new javax.swing.JTextField();
        CalFechaFact = new net.sf.nachocalendar.components.DateField();
        TxtMemo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        LblMemo = new javax.swing.JLabel();
        CalFechaIng = new net.sf.nachocalendar.components.DateField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        LblFechaIng1 = new javax.swing.JLabel();
        CalFechaNotificacion = new net.sf.nachocalendar.components.DateField();
        TxtTotal = new javax.swing.JTextField();
        BtnGarantia = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnAvanzar = new javax.swing.JButton();
        BtnRetornar = new javax.swing.JButton();
        BtnAnular = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        BtnSalir1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ZODIAC CAPRICORN SYSTEM");
        setBackground(new java.awt.Color(255, 0, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
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
        jLabel2.setText("Tipo Cuantia :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(700, 90, 90, 20);

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
        LblTituloNro.setText("titulo");
        jPanel1.add(LblTituloNro);
        LblTituloNro.setBounds(480, 10, 370, 20);

        LblNro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LblNro.setForeground(new java.awt.Color(204, 0, 51));
        LblNro.setText("jLabel8");
        jPanel1.add(LblNro);
        LblNro.setBounds(860, 10, 90, 20);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Unidad Destino :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(30, 70, 140, 20);

        LblCuantia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblCuantia.setForeground(new java.awt.Color(44, 59, 89));
        LblCuantia.setText("jLabel8");
        jPanel1.add(LblCuantia);
        LblCuantia.setBounds(800, 90, 180, 20);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Bs.");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(570, 90, 30, 20);

        LblA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblA.setForeground(new java.awt.Color(44, 59, 89));
        LblA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblA.setText("jLabel14");
        jPanel1.add(LblA);
        LblA.setBounds(490, 90, 80, 20);

        LblDe.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblDe.setForeground(new java.awt.Color(44, 59, 89));
        LblDe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblDe.setText("100.100,10");
        jPanel1.add(LblDe);
        LblDe.setBounds(360, 90, 80, 20);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 51));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("De :");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(320, 90, 30, 20);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("A :");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(450, 90, 30, 20);

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/alarm.png"))); // NOI18N
        jButton14.setText("Fechas Limite");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14);
        jButton14.setBounds(920, 30, 210, 28);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 51));
        jLabel9.setText("Tiempo: ");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(980, 90, 50, 20);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(44, 59, 89));
        jLabel14.setText("no tiene");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(1040, 90, 60, 20);

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/add.png"))); // NOI18N
        jButton16.setToolTipText("Adicionar un Item");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16);
        jButton16.setBounds(10, 90, 40, 28);

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/cancel.png"))); // NOI18N
        jButton17.setToolTipText("Eliminar un Item");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton17);
        jButton17.setBounds(50, 90, 40, 28);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 1140, 120);

        TblItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblItemsMousePressed(evt);
            }
        });
        PnlItems.setViewportView(TblItems);

        getContentPane().add(PnlItems);
        PnlItems.setBounds(10, 140, 1140, 210);

        TabTransaccion.setBackground(new java.awt.Color(209, 224, 240));

        PnlAdquisiciones.setBackground(new java.awt.Color(209, 224, 240));
        PnlAdquisiciones.setMaximumSize(new java.awt.Dimension(32770, 32770));
        PnlAdquisiciones.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("PROVEEDOR :");
        PnlAdquisiciones.add(jLabel7);
        jLabel7.setBounds(30, 10, 80, 20);

        TxtCasa.setEditable(false);
        TxtCasa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtCasa.setForeground(new java.awt.Color(255, 0, 0));
        PnlAdquisiciones.add(TxtCasa);
        TxtCasa.setBounds(120, 10, 500, 27);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Observacion :");
        PnlAdquisiciones.add(jLabel11);
        jLabel11.setBounds(20, 40, 90, 20);

        TxtObsAdq.setEditable(false);
        TxtObsAdq.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtObsAdq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtObsAdqKeyPressed(evt);
            }
        });
        PnlAdquisiciones.add(TxtObsAdq);
        TxtObsAdq.setBounds(120, 40, 500, 50);

        BtnBuscaProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnBuscaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnBuscaProveedor.setText("Proponentes");
        BtnBuscaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscaProveedorActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(BtnBuscaProveedor);
        BtnBuscaProveedor.setBounds(240, 100, 130, 28);

        jButton4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/report.png"))); // NOI18N
        jButton4.setText("Solicitud de Compra");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton4);
        jButton4.setBounds(900, 30, 210, 28);

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/report.png"))); // NOI18N
        jButton3.setText("O. Compra - Resolucion");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton3);
        jButton3.setBounds(900, 60, 210, 28);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("- DOCUMENTOS");
        PnlAdquisiciones.add(jLabel4);
        jLabel4.setBounds(840, 10, 120, 17);

        jButton5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/bookmark_document.png"))); // NOI18N
        jButton5.setText("Resolución de Adjudicación");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton5);
        jButton5.setBounds(670, 60, 210, 28);

        jButton6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/report.png"))); // NOI18N
        jButton6.setText("Resolución de Inicio");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton6);
        jButton6.setBounds(670, 30, 210, 23);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        PnlAdquisiciones.add(jSeparator1);
        jSeparator1.setBounds(650, 0, 20, 140);

        jButton2.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 102, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/attach_2.png"))); // NOI18N
        jButton2.setText("Archivos Adjuntos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton2);
        jButton2.setBounds(900, 100, 210, 28);

        jButton7.setText("Editar Datos Adjudicado");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton7);
        jButton7.setBounds(380, 100, 150, 28);

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/alarm.png"))); // NOI18N
        jButton10.setText("Fechas Limite");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton10);
        jButton10.setBounds(670, 100, 210, 28);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setText("HOJA DE RUTA:");
        PnlAdquisiciones.add(jLabel8);
        jLabel8.setBounds(10, 100, 90, 20);
        PnlAdquisiciones.add(JT_HRUTA);
        JT_HRUTA.setBounds(100, 100, 100, 28);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton13);
        jButton13.setBounds(200, 97, 30, 28);

        jButton15.setText("informe de la comision");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        PnlAdquisiciones.add(jButton15);
        jButton15.setBounds(540, 100, 110, 28);

        TabTransaccion.addTab("Adquisiciones", PnlAdquisiciones);

        PnlAlmacen.setBackground(new java.awt.Color(209, 224, 240));
        PnlAlmacen.setLayout(null);

        ScrObs.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        TxtObsAlmacen.setEditable(false);
        TxtObsAlmacen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ScrObs.setViewportView(TxtObsAlmacen);

        PnlAlmacen.add(ScrObs);
        ScrObs.setBounds(70, 60, 650, 60);

        LblFactura.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFactura.setText("Factura :");
        PnlAlmacen.add(LblFactura);
        LblFactura.setBounds(80, 20, 60, 20);

        LblFecFact.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFecFact.setText("Fecha Factura :");
        PnlAlmacen.add(LblFecFact);
        LblFecFact.setBounds(260, 20, 90, 20);

        LblFechaIng.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFechaIng.setText("Fecha Notificacion:");
        PnlAlmacen.add(LblFechaIng);
        LblFechaIng.setBounds(700, 20, 110, 20);

        TxtFactura.setEditable(false);
        TxtFactura.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFacturaKeyPressed(evt);
            }
        });
        PnlAlmacen.add(TxtFactura);
        TxtFactura.setBounds(140, 20, 100, 27);

        CalFechaFact.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFechaFact.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CalFechaFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CalFechaFactKeyPressed(evt);
            }
        });
        PnlAlmacen.add(CalFechaFact);
        CalFechaFact.setBounds(350, 20, 100, 20);

        TxtMemo.setEditable(false);
        TxtMemo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtMemo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtMemoKeyPressed(evt);
            }
        });
        PnlAlmacen.add(TxtMemo);
        TxtMemo.setBounds(820, 50, 100, 27);

        jButton1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/attach_2.png"))); // NOI18N
        jButton1.setText("Archivos Adjuntos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PnlAlmacen.add(jButton1);
        jButton1.setBounds(950, 10, 170, 28);

        LblMemo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblMemo.setText("Memo :");
        PnlAlmacen.add(LblMemo);
        LblMemo.setBounds(770, 50, 50, 20);

        CalFechaIng.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFechaIng.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CalFechaIng.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CalFechaIngStateChanged(evt);
            }
        });
        CalFechaIng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CalFechaIngKeyPressed(evt);
            }
        });
        PnlAlmacen.add(CalFechaIng);
        CalFechaIng.setBounds(560, 20, 100, 20);

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton8.setText("Vista Previa");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        PnlAlmacen.add(jButton8);
        jButton8.setBounds(950, 40, 170, 28);

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 0, 0));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/cancel.png"))); // NOI18N
        jButton9.setText("ANULAR TRAMITE");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        PnlAlmacen.add(jButton9);
        jButton9.setBounds(950, 80, 170, 28);

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton12.setText("Fechas de Entrega");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        PnlAlmacen.add(jButton12);
        jButton12.setBounds(757, 80, 160, 28);

        LblFechaIng1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblFechaIng1.setText("Fecha Ingreso:");
        PnlAlmacen.add(LblFechaIng1);
        LblFechaIng1.setBounds(470, 20, 90, 20);

        CalFechaNotificacion.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFechaNotificacion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CalFechaNotificacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CalFechaNotificacionStateChanged(evt);
            }
        });
        CalFechaNotificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CalFechaNotificacionKeyPressed(evt);
            }
        });
        PnlAlmacen.add(CalFechaNotificacion);
        CalFechaNotificacion.setBounds(820, 20, 100, 20);

        TabTransaccion.addTab("Almacen", PnlAlmacen);

        getContentPane().add(TabTransaccion);
        TabTransaccion.setBounds(10, 380, 1140, 160);

        TxtTotal.setEditable(false);
        TxtTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtTotal.setForeground(new java.awt.Color(0, 0, 204));
        TxtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        getContentPane().add(TxtTotal);
        TxtTotal.setBounds(1030, 350, 120, 27);

        BtnGarantia.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        BtnGarantia.setForeground(new java.awt.Color(255, 0, 51));
        BtnGarantia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/calendar_16.png"))); // NOI18N
        BtnGarantia.setText("B.G.");
        BtnGarantia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGarantiaActionPerformed(evt);
            }
        });
        getContentPane().add(BtnGarantia);
        BtnGarantia.setBounds(860, 350, 80, 28);

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
        BtnGuardar.setBounds(430, 350, 120, 28);

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
        BtnAvanzar.setBounds(270, 350, 160, 28);

        BtnRetornar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnRetornar.setForeground(new java.awt.Color(0, 102, 0));
        BtnRetornar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/arrow_left.png"))); // NOI18N
        BtnRetornar.setText("Retornar Tramite");
        BtnRetornar.setEnabled(false);
        BtnRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRetornarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRetornar);
        BtnRetornar.setBounds(120, 350, 150, 28);

        BtnAnular.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnAnular.setForeground(new java.awt.Color(255, 0, 0));
        BtnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnAnular.setText("Anular");
        BtnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnularActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAnular);
        BtnAnular.setBounds(10, 350, 110, 28);

        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("TOTAL:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(970, 350, 60, 20);

        jButton11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/printer.png"))); // NOI18N
        jButton11.setText("Generacion de contrato");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton11);
        jButton11.setBounds(670, 350, 181, 28);

        BtnSalir1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir1.setForeground(new java.awt.Color(255, 0, 51));
        BtnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnSalir1.setText("Salir");
        BtnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalir1ActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir1);
        BtnSalir1.setBounds(550, 350, 120, 28);

        setSize(new java.awt.Dimension(1181, 585));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtObsAdqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtObsAdqKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnGuardar.requestFocus();
        }
}//GEN-LAST:event_TxtObsAdqKeyPressed

    private void TxtFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFacturaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CalFechaFact.requestFocus();
        }
}//GEN-LAST:event_TxtFacturaKeyPressed

    private void CalFechaFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CalFechaFactKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TxtMemo.requestFocus();
        }
}//GEN-LAST:event_CalFechaFactKeyPressed

    private void TxtMemoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtMemoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnGuardar.requestFocus();
        }
}//GEN-LAST:event_TxtMemoKeyPressed

    private void BtnGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGarantiaActionPerformed
        
        JD_BoletaGarantia JDBG = new JD_BoletaGarantia(menu,true,this.cod_trans_nro);
        JDBG.setVisible(true);
       
}//GEN-LAST:event_BtnGarantiaActionPerformed

    private void setCodW(int cod_transaccion, int cod_w) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.setCodW("SET-setCodW", cod_transaccion, cod_w);
        } catch (Exception e) {
            System.out.println("ERROR !!!!");
        }
    }
    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        String obs_almacen = null, obs_adq = null, factura = null, memo = null;
        System.out.println("cod_w: " + cod_w + " cuantia: " + cuantia);
        if (this.cod_w == 7 && !cuantia.equals("COMPRA MENOR")) {
            setCodW(this.cod_transaccion, 7);
            this.cod_w = 7;
        }
        if (!"".equals(TxtObsAlmacen.getText().trim())) {
            obs_almacen = "'" + TxtObsAlmacen.getText().trim() + "'";
        }
        if (!"".equals(TxtObsAdq.getText().trim())) {
            obs_adq = "'" + TxtObsAdq.getText().trim() + "'";
        }

        if (!"".equals(TxtMemo.getText().trim())) {
            memo = TxtMemo.getText().trim();
        }

        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        String fec_fact = "'" + form.format(CalFechaFact.getValue()) + "'";
        String fec_ing = "'" + form.format(CalFechaIng.getValue()) + "'";
        
        
        String fec_noti = "'" + form.format(CalFechaNotificacion.getValue()) + "'";

        System.out.println(" ------------------- ************** ------------ La fecha es: " + CalFechaFact.getValue());

        sw = true;
        System.out.println("---- TAB_HABIL --> " + tab_habil);
        switch (tab_habil) {
            case 0:
                for (int f = 0; f < TblItems.getRowCount(); f++) {
                    try {
                        if (TblItems.getValueAt(f, 3).toString().length() > 0
                                && Double.parseDouble(TblItems.getValueAt(f, 11).toString()) <= 0) {
                            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir un precio unitario real", "SYSTEM CAPRICORN",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                            TblItems.editCellAt(f, 10);
                            sw = false;
                            return;
                        }
                    } catch (NumberFormatException e) {
                        javax.swing.JOptionPane.showMessageDialog(this, "El precio unitario no debe tener espacios vacios", "SYSTEM CAPRICORN",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        TblItems.editCellAt(f, 10);
                        sw = false;
                        return;
                    }
                }
                if (proveedor.getCod_proveedor() == null) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Debe elejir un proveedor", "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    BtnBuscaProveedor.requestFocus();
                    return;
                }
                if (!this.VerificaPartidas()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Todos los items deben tener partida", "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!this.VerificaITEM()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Todos los items deben tener su codigo", "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                System.out.println("Intentando actualizar proveedor :D  " + proveedor.getCod_proveedor());
                this.AddObservacion();
                //ActualizaTransaccionAdq(obs_adq,proveedor.getCod_proveedor());
                break;
            case 1:
                System.err.println("origen "+origen);
                if ("ALM1".equals(origen) || "C".equals(origen)) {
                    if (!"".equals(TxtFactura.getText().trim())) {
                        factura = "'" + TxtFactura.getText().trim() + "'";
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir el numer de factura", "SYSTEM CAPRICORN",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        TxtFactura.requestFocus();
                        sw = false;
                        return;
                    }
                    String obs = this.TxtObsAlmacen.getText().trim();
                    System.out.println("La observacion seria : " + obs);
                    System.out.println("El memo al intentar guardarse es: " + memo);
                    System.out.println("factura asdasdasd "+factura);
                    ActualizaTransaccionIngresoAlmacen(factura, fec_fact, fec_ing, fec_noti, memo, obs);
                    //ActualizaTransaccionAlmacen("vvv");
                }
                break;
        }
}//GEN-LAST:event_BtnGuardarActionPerformed

    private boolean VerificaPartidas() {
//        boolean sw_vf = true;
        for (int f = 0; f < TblItems.getRowCount(); f++) {
            System.out.println("length --> " + (TblItems.getValueAt(f, 6)).toString().length());
            if ((TblItems.getValueAt(f, 6)).toString().length() != 0) {
                System.out.println(((Partida) TblItems.getValueAt(f, 6)).getCodigo().trim());
                String partida = ((Partida) TblItems.getValueAt(f, 6)).getCodigo().trim();
                if (partida.equals("No tiene") && !(TblItems.getValueAt(f, 6)).toString().equals("")) {
                    this.sw = false;
                }
//            System.out.println("Partida --> "+((Partida)TblItems.getValueAt(f, 5)).getCodigo());
            }

        }
        return sw;
    }

    private boolean VerificaITEM() {
//        boolean sw_vf = true;
        for (int f = 0; f < TblItems.getRowCount(); f++) {
            System.out.println("length --> " + (TblItems.getValueAt(f, 5)).toString().length());
            if ((TblItems.getValueAt(f, 5)).toString().length() != 0) {
//                System.out.println(( TblItems.getValueAt(f, 5)).toString().trim());
                String cod_item = (TblItems.getValueAt(f, 5)).toString().trim();
                if (cod_item.equals("Sin Definir") && !(TblItems.getValueAt(f, 5)).toString().equals("")) {
                    sw = false;
                }
//            System.out.println("Partida --> "+((Partida)TblItems.getValueAt(f, 5)).getCodigo());
            }

        }
        return sw;
    }

    private boolean verifica_avanzar() {
        if (cod_rol != 2) {
            if (this.proveedor.getCod_proveedor() == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe elegir un Proveedor", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (this.JT_HRUTA.getText() == null || this.JT_HRUTA.getText().trim().length() == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe adicionar la hoja de ruta", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            /*
             if(cod_rol==2){
             javax.swing.JOptionPane.showMessageDialog(this,"El rol que se le asigno, no le permite avanzar la transacción","SYSTEM CAPRICORN",
             javax.swing.JOptionPane.ERROR_MESSAGE);
             return false;
             }
             */
            System.out.println("Monto Total: " + Total);
            if (!this.verifica_monto(Total)) {
                javax.swing.JOptionPane.showMessageDialog(this, "El monto total no es acorde al tipo de cuantia: " + this.cuantia, "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    private void BtnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAvanzarActionPerformed
        
        if (cod_rol != 7) {
            BtnGuardar.doClick();
        } else {
            sw = true;
        }
        System.out.println("El cod_rol" + cod_rol);
        System.out.println("si aparece la cuantia " + cuantia);
        //System.out.println("El cod"+this.proveedor.getCod_proveedor());
        /*if(this.proveedor.getCod_proveedor()==null && cod_rol!=2 && this.verifica_monto(Double.parseDouble(this.TxtTotal.getText())))
         {
         sw=false;
           
            
         }
         System.out.println("EL SW: "+sw);*/
        try{
            System.out.println("el cod_t "+cod_transaccion+" el CODTN "+cod_trans_nro);
        AdquiWSServiceLocator servicio1 = new AdquiWSServiceLocator();
        AdquiWS_PortType puerto1 = servicio1.getAdquiWS();
        Map[] ddd=puerto1.getCod_Trans(cod_transaccion);
        int cod_TN=Integer.parseInt(ddd[0].get("COD_TRANS_NRO").toString());
        System.out.println(cod_TN);
        Map[] dat=puerto1.dias_restantes(cod_TN);
        System.err.println("zxfadsfsdfasdf "+dat[0].get("NUM").toString());
        if(cod_rol==7 && Integer.parseInt(dat[0].get("NUM").toString())!=11 && cuantia.equals("ANPE"))
        {
            JOptionPane.showMessageDialog(null, "DEBE REALIZAR EL CONTROL DE FECHAS DE LOS PROCESOS ANPE");
            return;
        }
        else
        {
            System.out.println("entro al else de juridicas");
        }
        if(Integer.parseInt(dat[0].get("NUM").toString())!=10 && tab_habil==0 && cuantia.equals("ANPE"))
        {
            JOptionPane.showMessageDialog(null, "DEBE REALIZAR EL CONTROL DE FECHAS DE LOS PROCESOS ANPE");
            return;
        }
        else
        {
            System.out.println("entro al else");
        }
        }catch(Exception e)
        {System.out.println("entro al catch "+e.getMessage());}
        if (verifica_avanzar() && sw) {
            int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea avanzar esta TRANSACCION?",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
            if (res != javax.swing.JOptionPane.YES_OPTION) {
                return;
            }
            
            
            try {
                System.out.println(":P intentando");
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                Map[] datos = null;
                if (cod_rol == 5 || cod_rol == 7) {
                    int nuevo_cod_trans_nro = 0;
                    if (cod_w == 1 || cod_w == 2 || cod_w == 5 || cod_w == 6 || cod_w == 7) {
                        VerificaTipoTramite(3);
                        System.out.println("************ la cantidad de tramites de tipo 3 es: " + cantidad_tram);
                        if (cantidad_tram == 0) {
                            System.out.println(cod_usuario);
                            System.out.println("Oka 1 cod_transaccion: " + cod_transaccion + " cod_almacen: " + cod_almacen + " viene el 3 gestion: " + gestion + " cod_trans_nro: " + cod_trans_nro + " cod_usuario: " + cod_usuario);
                            datos = puerto.setCreaNroTramiteDos("SET-upDateGeneraTramite", cod_transaccion, cod_almacen, 3, gestion, cod_trans_nro, cod_usuario);
                            System.out.println("El numero que devolvio es: " + datos[0].get("COD_TRANS_NRO").toString());
                            if(cod_w==6 && cod_rol==5)
                            {
                                nuevo_cod_trans_nro = Integer.parseInt(datos[0].get("COD_TRANS_NRO").toString());
                                System.out.println("dasffsdf "+nuevo_cod_trans_nro);
                                Map[] datos1 = puerto.setCreaNroTramiteDos("SET-upDateGeneraTramite", cod_transaccion, cod_almacen, 4, gestion, nuevo_cod_trans_nro, cod_usuario);
                                puerto.updatenro(Integer.parseInt(datos1[0].get("COD_TRANS_NRO").toString()));
                                nuevo_cod_trans_nro = Integer.parseInt(datos1[0].get("COD_TRANS_NRO").toString());
                                System.out.println("dasffsdf "+nuevo_cod_trans_nro);
                            }
                            if (datos != null) {
                                if(cod_w!=6)
                                {
                                    nuevo_cod_trans_nro = Integer.parseInt(datos[0].get("COD_TRANS_NRO").toString());
                                    System.out.println(nuevo_cod_trans_nro);
                                }
                                for (int f = 0; f < TblItems.getRowCount(); f++) {
                                    if (!"".equals(TblItems.getValueAt(f, 1).toString())) {
                                        System.out.println("Oka 1/2");
                                        //
                                        datos = puerto.setTransaccionDetalleNro("SET-upDateTransDetNro", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), nuevo_cod_trans_nro);
                                    }
                                }

                            }
                        }
                    }
                }
                if (cod_rol == 2 && "ALM1".equals(origen)) {
                    int nuevo_cod_trans_nro = 0;
                    VerificaTipoTramite(4);
                    if (cantidad_tram == 0) {
                        System.out.println("************** ULALA **************");
                        System.out.println("cod_transaccion: " + cod_transaccion + " cod_almacen: " + cod_almacen + " gestion: " + gestion + " cod_trans_nro: " + cod_trans_nro + " cod_usuario: " + cod_usuario);

                        //puerto.setNumIngreso("setTransaccionesDestino", cod_trans_nro);
                        datos = puerto.setCreaNroTramiteTres("SET-upDateGeneraTramite", cod_transaccion, cod_almacen, 4, gestion, cod_trans_nro, cod_usuario);
                        //datos=puerto.setCreaNroTramiteDos("SET-upDateGeneraTramite",cod_transaccion,cod_almacen, 4, gestion,cod_trans_nro,cod_usuario);
                        System.out.println("Oka 2");
                        if (datos != null) {
                            System.out.println("Oka 3");
                            nuevo_cod_trans_nro = Integer.parseInt(datos[0].get("COD_TRANS_NRO").toString());
                            System.out.println(nuevo_cod_trans_nro);
                            for (int f = 0; f < TblItems.getRowCount(); f++) {
                                if (!"".equals(TblItems.getValueAt(f, 1).toString())) {
                                    System.out.println("Oka 3");
                                    datos = puerto.setTransaccionDetalleNro("SET-upDateTransDetNro", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), nuevo_cod_trans_nro);
                                }
                            }
                        }
                    }
                }
                String destino = "";
                //System.out.println("");
                int cod_aux = cod_w;
                for (int f = 0; f < TblItems.getRowCount(); f++) {
                    if (!"".equals(TblItems.getValueAt(f, 1).toString()) && !"D".equals(TblItems.getValueAt(f, 1).toString())) {
                        System.out.println("cod_trans_detalle: " + Integer.parseInt(TblItems.getValueAt(f, 2).toString()) + ", cod_w:" + cod_w + ", origen" + TblItems.getValueAt(f, 1).toString());
                        System.out.println("Destino Waoooo");
                        System.out.println("sdfsfsdfsdfsdfdsf" + cuantia + " " + cod_w);
                        if (cuantia.equals("COMPRA MENOR") && cod_w == 7) {
                            cod_aux = cod_w;
                            cod_w = 1;
                        }
                        Map[] d=puerto.getDias(cod_trans_nro);
                        
                        //System.err.println("xx entro!!!  "+d[0].get("DIAS").toString().trim());
                        if (cod_rol == 5) {
                            
                        
                        switch(cod_w){
                            case 1:
                                if(cuantia.equals("COMPRA MENOR")){
                                    if(Integer.parseInt(d[0].get("DIAS").toString().trim()) <= 15){
                                        destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                    }
                                    else{
                                        System.err.println("entro!!!  "+d[0].get("DIAS").toString().trim());
                                        destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, "XJUR");
                                    }
                                }
                                if(cuantia.equals("ANPE")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, "XJUR");
                                }
                                break;
                            case 3:
                                if(cuantia.equals("COMPRA MENOR")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                }
                                if(cuantia.equals("ANPE")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                }
                                break;
                            case 4:
                                if(cuantia.equals("COMPRA MENOR")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                }
                                if(cuantia.equals("ANPE")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                }
                                break;
//                           
                            case 6:
                                if(cuantia.equals("COMPRA MENOR")){
                                    if(Integer.parseInt(d[0].get("DIAS").toString().trim()) <=15){
                                        System.err.println("entro!!!  "+d[0].get("DIAS").toString().trim());
                                        destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, "ALM1");
                                    }
                                    else{
                                        destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                    }
                                }
                                if(cuantia.equals("ANPE")){
                                    destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                                }
                                break;
                        }
                        
                        }
                        else
                        {
                            destino = puerto.setTransaccionesDestino("SET-upDateDestino", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                        }
                        
                                                
                    }
                }
                //cod_w = cod_aux;
                //BtnGarantia.doClick();
                Object seleccion = JOptionPane.showInputDialog(
                null,
                "Seleccione a Donde va el Tramite",
                "Selector de opciones",
                JOptionPane.QUESTION_MESSAGE,
                null,  // null para icono defecto
                new Object[] { "Juridicas", "Almacenes", "Concluido" }, 
                "Alamacenes");
                if(seleccion==null)
                {
                    javax.swing.JOptionPane.showMessageDialog(this, "<html> al cancelar el sistema colocara automaticamente la direccion segun el tipo de tramite <br> ", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String est="";
                    if(seleccion.equals("Juridicas"))
                    {
                        est="JUR";
                    }
                    if(seleccion.equals("Almacenes"))
                    {
                        est="ALM1";
                    }
                    if(seleccion.equals("Concluido"))
                    {
                        est="C";
                    }
                    Map[] d=puerto.cambioEnvio(cod_trans_nro);
                    for(int i=0;i<d.length;i++)
                    {
                        int x=Integer.parseInt(d[i].get("COD_TRANS_DETALLE").toString());
                        
                        puerto.cambioEnvio1(x, est);
                    }
                    
                }
                System.out.println("El usuario ha elegido "+seleccion);
                BtnSalir1.doClick();
                
            } catch (RemoteException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException e) {
                System.out.println(e);
            } catch (IllegalArgumentException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe elegir una fila de la bandeja de salida para enviar el memorandum", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_BtnAvanzarActionPerformed

    private void BtnRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRetornarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea rertornar esta TRANSACCION?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = null;
            for (int f = 0; f < TblItems.getRowCount(); f++) {
                if (!"".equals(TblItems.getValueAt(f, 1).toString())) {
                    datos = puerto.setTransaccionesOrigen("SET-upDateOrig", Integer.parseInt(TblItems.getValueAt(f, 2).toString()), cod_w, TblItems.getValueAt(f, 1).toString());
                }
            }

            javax.swing.JOptionPane.showMessageDialog(this, "LA TRANSACCION SE RETORNO A LA UNIDAD ANTERIOR", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            BtnGarantia.doClick();
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe elegir una fila de la bandeja de salida para enviar el memorandum", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_BtnRetornarActionPerformed

    private void BtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnularActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea ANULAR esta TRANSACCION?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getCod_trans_nro("GET-getCod_trans_nro", cod_transaccion);
            for(int i=0;i<datos.length;i++)
            {
                int nro=Integer.parseInt(datos[i].get("COD_TRANS_NRO").toString());
                System.out.println("esto es el cod_trans_nro "+nro);
                datos = puerto.setAnulaTransaccionAdq("SET-upDateAnulTransAdq", nro);
                System.out.println("esto es el cod_trans_nro "+nro);
            }
            javax.swing.JOptionPane.showMessageDialog(this, "LA TRANSACCION FUE ANULADA", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            BtnGarantia.doClick();
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe elegir una fila de la bandeja de salida para enviar el memorandum", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_BtnAnularActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        LblTituloNro.setText(tramite + " Nro.:");
        Date hoy = new Date();
        LblNro.setText(nro + " - " + gestion);
//        CalFechaFact.setValue(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(hoy));

        proveedor = new Proveedor();
        System.out.println(" El cod rol es: " + this.cod_rol);
        //if(this.cod_rol==5)
        LlenaDetalles();
        LlenaItems();
        GetDatos();
        reportes = new Reportes(cod_almacen);
        
        //VerificaTipoTramite(); 

        LblDetalle.setText(detalle);
        LblUnidadDes.setText(unidad_des);
        LblUnidadSol.setText(unidad_sol);
        LblCuantia.setText(cuantia);

        DecimalFormat formatter = new DecimalFormat("###,##0");
        LblDe.setText(formatter.format(Double.parseDouble(del)));
        LblA.setText(formatter.format(Double.parseDouble(hasta)));

        /*
         TxtFactura.setEnabled(false);
         CalFechaFact.setEnabled(false);
         TxtMemo.setEnabled(false);
         ScrObs.setEnabled(false);
         if (cod_rol==2){
         if ("ALM1".equals(origen)){               
         TxtFactura.setEnabled(true);
         CalFechaFact.setEnabled(true);
         TxtMemo.setEnabled(true);
         }
         tab_habil=1;            
         }
         else if (cod_rol==5){
         tab_habil=1;
         } */
        CalFechaFact.setEnabled(false);
        jButton14.setVisible(false);
        if (cod_rol == 2) {
            System.out.println("Entro a la Restricción");
            TxtFactura.setEditable(true);
            CalFechaFact.setEnabled(true);
            TxtMemo.setEditable(true);
            TxtObsAlmacen.setEditable(true);
            BtnBuscaProveedor.setEnabled(false);
            BtnRetornar.setEnabled(true);
            tab_habil = 1;
            TabTransaccion.setEnabledAt(TabTransaccion.indexOfComponent(PnlAdquisiciones), false);
        } else if (cod_rol == 5) {
            TxtObsAdq.setEditable(true);
            tab_habil = 0;
            TabTransaccion.setEnabledAt(TabTransaccion.indexOfComponent(PnlAlmacen), false);
        } else if (cod_rol == 7) {
            if(cuantia.equals("ANPE"))
                jButton14.setVisible(true);
            TabTransaccion.setVisible(false);
            BtnGuardar.setEnabled(false);
            this.setSize(this.getWidth(), this.getHeight() - 160);

        }
        TabTransaccion.setSelectedIndex(tab_habil);
    }//GEN-LAST:event_formWindowOpened

    private void BtnBuscaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscaProveedorActionPerformed
        /*DiagBuscaProveedor prov = new DiagBuscaProveedor(menu);
        
         prov.AbreDialogo(); 
         if (prov.ProveedorElejido().getCod_proveedor()!=0)
         proveedor= prov.ProveedorElejido();
                
         TxtCasa.setText(proveedor.getCasa_comercial());*/
        System.out.println("El codigo de la transaccion es --> " + this.cod_transaccion);
        ProponentesDialog x = new ProponentesDialog(this.menu, true, this.cod_transaccion, this.cod_trans_nro);
        x.setVisible(true);
        //if (x.ProveedorElejido().getCod_proveedor()!=null)
        //if(proveedor==null){
        //
        //}
        System.out.println("Que pedoooo cachorros :D");
        proveedor = x.ProveedorAdj();
        if (proveedor == null) {
            System.out.println("Entro");
            proveedor = new Proveedor();
            this.GetAdjudicado();
        } else {

            System.out.println("OOOEEOO " + proveedor.getCasa_comercial());
            ActualizaTransaccionAdq(proveedor.getCod_proveedor());
            this.GetAdjudicado();
            //System.out.println("OOOEEOO "+x.proveedor.getNombre());
            TxtCasa.setText(proveedor.getCasa_comercial());
        }


    }//GEN-LAST:event_BtnBuscaProveedorActionPerformed

    private void TblItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblItemsMousePressed
        System.out.println("Nesecitamos abrir el adjuntos...");
        if (evt.getClickCount() == 2) {
            int fila = TblItems.getSelectedRow();
            int col = TblItems.getSelectedColumn();
            System.out.println("cod_w=" + cod_w + " , cod_rol = " + cod_rol + ", fila = " + fila + " ,columna = " + col);
            System.out.println("A ver: " + TblItems.getValueAt(fila, 0));
            if (col == 10 && (cod_rol == 7 || cod_rol == 5) && (cod_w == 3 || cod_w == 4 || cod_w == 5 || cod_w == 6 || cod_w == 7) && "".equals(TblItems.getValueAt(fila, 0))) {
                int cod_trans_detalle = Integer.parseInt(TblItems.getValueAt(fila, 2).toString());

                /*
                 DiagSubeDBC dbc = new DiagSubeDBC(this,cod_trans_detalle,TblItems.getValueAt(fila, 9).toString());
                 dbc.AbreDialogo(); 
                 */
                System.out.println("el cod_transaccion es: " + cod_transaccion);
                if (this.cod_transaccion != 0) {
                    System.out.println("Hey por aca .....");
                    JDAdjuntos JDA = new JDAdjuntos(menu, true, cod_transaccion, cod_rol);
                    JDA.AbreDialogo();
                } else {
                    JOptionPane.showMessageDialog(null, "Usted no a seleccionado ninguna Solicitud", "Error", JOptionPane.ERROR_MESSAGE);
                }
                /*if (dbc.ProveedorElejido().getCod_proveedor()!=0)
                 proveedor= prov.ProveedorElejido();

                 TxtCasa.setText(proveedor.getCasa_comercial());*/

                /*if (!"".equals(dbc.NombreArchvo()))
                 TblItems.setValueAt(dbc.NombreArchvo(), fila, col);*/
            }
//            if(col==6 && "".equals(TblItems.getValueAt(fila, 0))){
//                String cod_trans_detalle =TblItems.getValueAt(fila, 2).toString();
//                String cod_item =TblItems.getValueAt(fila, 13).toString();
//                System.out.println("Hola mamá, el cod_trans_detalle es -> "+cod_trans_detalle+" y el cod_item es: "+cod_item);
////                JD_CambiaItem JDci = new JD_CambiaItem(this.menu,true,cod_trans_detalle,cod_item);
////                JDci.setVisible(true);
////                JD_CambiaPartida JDcp = new JD_CambiaPartida();
//                
//                //JD_CambiaPartida JDcp= new JD_CambiaPartida(this.menu,true,cod_trans_detalle,cod_item,this.gestion);
//                //JDcp.setVisible(true);
//                //this.LlenaItems();
//            }
            if (col == 7 && "".equals(TblItems.getValueAt(fila, 0))) {
//                String cod_trans_detalle = TblItems.getValueAt(fila, 2).toString();
//                String cod_item = TblItems.getValueAt(fila, 13).toString();
//                System.out.println("Hola mamá, el cod_trans_detalle es -> " + cod_trans_detalle + " y el cod_item es: " + cod_item);
//                
//                JD_addDetalle JDad = new JD_addDetalle(this.menu, true, cod_trans_detalle, this.cod_rol);
//                JDad.setVisible(true);
//                this.LlenaItems();
                
                String cod_trans_detalle = TblItems.getValueAt(fila, 2).toString();
//                String cod_item = TblItems.getValueAt(fila, 13).toString();
//                System.out.println("Hola mamá, el cod_trans_detalle es -> " + cod_trans_detalle + " y el cod_item es: " + cod_item);
                
//                JD_updateItem JDup = new JD_updateItem(this.menu, true, cod_trans_detalle, this.cod_rol);
//                JDup.setVisible(true);
                
                JD_Opciones JDo = new JD_Opciones (this.menu,true,this,cod_trans_detalle,this.cod_rol);
                JDo.setVisible(true);
//                this.LlenaItems();
            }
            if (col == 7 && !("".equals(TblItems.getValueAt(fila, 0)))) {
                String cod_trans_detalle = TblItems.getValueAt(fila, 2).toString();
                String cod_item = TblItems.getValueAt(fila, 13).toString();
                String cod_complemento = TblItems.getValueAt(fila, 0).toString();
                System.out.println("Hola mamá, el cod_trans_detalle es -> " + cod_trans_detalle + " y el cod_item es: " + cod_item);
//                JD_CambiaItem JDci = new JD_CambiaItem(this.menu,true,cod_trans_detalle,cod_item);
//                JDci.setVisible(true);
//                JD_CambiaPartida JDcp = new JD_CambiaPartida();

                JD_abmDetalle JDabm = new JD_abmDetalle(this.menu, true, cod_trans_detalle, cod_complemento, this.cod_rol);
                JDabm.setVisible(true);
                this.LlenaItems();
                System.out.println("El cod_complemento: " + TblItems.getValueAt(fila, 0).toString());
            }
            if (col == 5) {
//                int partida = Integer.parseInt((Partida)TblItems.getValueAt(fila, 6).toString());
                String partida = ((Partida) TblItems.getValueAt(fila, 6)).getCodigo().split(" - ")[0];

                JD_DefItem JDi = new JD_DefItem(this.menu, true, this.gestion, Integer.parseInt(partida));
                JDi.setVisible(true);
                String cod_trans_detalle = TblItems.getValueAt(fila, 2).toString();

                int cod_item = JDi.getCod_item();
                System.out.println("Wolasss :P!!!! " + cod_item);
                if (cod_item != 0) {
                    this.guardarClaseItem(Integer.parseInt(cod_trans_detalle), cod_item);
                    TblItems.setValueAt(cod_item, fila, 5);
                }

            }
        }
    }//GEN-LAST:event_TblItemsMousePressed

    private void guardarClaseItem(int cod_trans_detalle, int cod_item) {
        if (cod_item != 0) {
            try {
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                System.out.println("El cod_trans_detalle es : " + cod_trans_detalle + ", cod_item: " + cod_item);
                puerto.updateCodItem2("SET-updateCodItem", cod_trans_detalle, cod_item,cod_user);
                System.out.println("Guardado con exito!!! ");
            } catch (Exception e) {
                System.out.println("Error --> " + e);
            }
        }

    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        System.out.println("Generando Solicitud de Compras...." + cod_transaccion);
        this.setModal(false);
        //SolicitudCompra(cod_transaccion,"PPTO", 1, "SOLICITUD DE COMPRAS");
        reportes.MostrarSolicitud(cod_transaccion, "PPTO", 1, "SOLICITUD DE COMPRAS");
    }//GEN-LAST:event_jButton4ActionPerformed


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        /*if (proveedor==null){
         javax.swing.JOptionPane.showMessageDialog(this,"Debe adjudicar la orden a un proveedor","SYSTEM CAPRICORN",
         javax.swing.JOptionPane.ERROR_MESSAGE);
         BtnBuscaProveedor.requestFocus();
         return;
         }
         else{
         this.setModal(false);
         this.OrdenCompra(cod_trans_nro, "ADQ", 2, "ORDEN DE COMPRA Y/U ORDEN DE SERVICIO");
           
         }*/
        this.setModal(false);
        System.out.println(this.cuantia+" sdf "+this.cod_trans_nro);
        int dias=0;
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getDias(cod_trans_nro);
            System.out.println(datos[0].get("DIAS").toString());
            dias=Integer.parseInt(datos[0].get("DIAS").toString());
        }
        catch(Exception e)
        {}
        if(!this.cuantia.equals("ANPE") && dias>15)
        {
            //reportes.MostrarOrden(cod_trans_nro, "ADQ", 2, "ORDEN DE COMPRA Y/U ORDEN DE SERVICIO"); 
            System.out.println("si entra aqui "+TxtCasa.getText()+" "+TblItems);
            ResNotificacion jfnew=new ResNotificacion(menu,false,TxtCasa.getText(),cod_transaccion,cod_trans_nro,gestion,cod_w);
            //this.jPanel1.add(jfnew);
            jfnew.setLocationRelativeTo(null);
            jfnew.setVisible(true);
        }
        else
        {
            reportes.MostrarOrden(cod_trans_nro, "ADQ", 2, "ORDEN DE COMPRA Y/U ORDEN DE SERVICIO");
        }
        //this.OrdenCompra(cod_trans_nro, "ADQ", 2, "ORDEN DE COMPRA Y/U ORDEN DE SERVICIO");
        

    }//GEN-LAST:event_jButton3ActionPerformed

    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.setModal(false);
        this.genera_reportes.Reporte(cod_transaccion, this.cod_w, this.detalle);
//        RRTR
//        try {
//            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
//            AdquiWS_PortType puerto = servicio.getAdquiWS();
//            //System.out.println("Para cod_transaccion: "+cod_transaccion);
//            System.out.println("El cod_transaccion --> "+cod_transaccion+" y el cod_w: "+cod_w);
//            Map[] datos = puerto.getResIni(cod_transaccion);
//            String dir_daf = puerto.getDatoGeneral("DIR_DAF");
//            //System.out.println("Dir Daf es: "+dir_daf);
//            if(datos!=null){
//                
//                String envia=datos[0].get("ENVIA").toString();
//                String dns=datos[0].get("DETALLE_NOTA_SOLICITUD").toString();
//                String dnp=datos[0].get("DETALLE_NOTA_PRESUPUESTO").toString();
//                String destino=datos[0].get("DESTINO").toString();
//                int cod_res_ini=Integer.parseInt(datos[0].get("COD_RES_INI").toString());
//                String num_resolucion=datos[0].get("NUM_RESOLUCION").toString();
//                String monto_ppto=datos[0].get("MONTO_PPTO").toString();
//                
////                datos=puerto.getDatosGenerales();
////                String dir_daf = datos[0].get("DIR_DAF").toString();
////                System.out.println("Dir Daf es: "+dir_daf);
//                
//                this.setModal(false);
//                this.genera_reportes.Reporte(cod_transaccion,this.cod_w,num_resolucion,this.detalle,monto_ppto,envia,dns,dnp,destino,dir_daf);
//                //this.setVisible(false);
//                
//            }
//        } catch (Exception e) {
//        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        System.out.println("La cuantia es: " + this.cuantia);
        //System.out.println("Cod Proveedor: "+proveedor.getCod_proveedor());
        if (proveedor.getCod_proveedor() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe adjudicar la orden a un proveedor", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            BtnBuscaProveedor.requestFocus();
            return;
        } else {
            if (cod_w == 4) {
                System.out.println("entra al if de obras");
                JDResAdjObra x = new JDResAdjObra(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
                this.setModal(false);
                x.setVisible(true);
            } else {
                this.BtnGuardar.doClick();
                System.out.println(":P :P :P cod_w: " + cod_w + "cod_trans_nro: " + this.cod_trans_nro + "  nombre_comercial: " + this.proveedor.getCasa_comercial() + " detalle: " + this.detalle);
                //JDResAdj x = new JDResAdj(menu,true,this.cod_trans_nro);
                JDResAdj2 x = new JDResAdj2(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
                this.setModal(false);
                x.setVisible(true);
            }

        }
        /*JDResAdj2 x = new JDResAdj2(menu,true,this.cod_w,this.cod_trans_nro,this.proveedor.getCasa_comercial(),this.detalle,this.cod_transaccion);
         this.setModal(false);
         x.setVisible(true);*/

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (this.cod_transaccion != 0) {
            JDAdjuntos JDA = new JDAdjuntos(menu, true, cod_transaccion, cod_rol);
            JDA.AbreDialogo();
        } else {
            JOptionPane.showMessageDialog(null, "Usted no a seleccionado ninguna Solicitud", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.jButton1.doClick();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void CalFechaIngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CalFechaIngKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalFechaIngKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (proveedor.getCod_proveedor() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe adjudicar la orden a un proveedor", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            BtnBuscaProveedor.requestFocus();
        } else {
            JDEditAdj JDEdita_adjunto = new JDEditAdj(this.menu, true, cod_trans_nro, proveedor);
            JDEdita_adjunto.setVisible(true);
            System.out.println("------------Testing.........");
            this.GetAdjudicado();
        }

    }//GEN-LAST:event_jButton7ActionPerformed


    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //this.MostrarIngreso(cod_trans_nro, "ALM1", 3);
        reportes.MostrarIngreso(cod_trans_nro, "ALM1", 3);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Esta seguro de Anular el tramite?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("Antes de anular... el nro es: " + Integer.parseInt(nro));
            puerto.XAnulaTramite("SET-anulaTramite", Integer.parseInt(nro));
//            int cod_trans_detalle;
//            Map[] datos = puerto.getTransDetalle(Integer.parseInt(nro));
//            if (datos!=null){  
//                for (int f=0;f<datos.length;f++){
//                  
////                    cod_trans_detalle = Integer.parseInt(datos[f].get("COD_TRANS_DETALLE").toString());
////                    sql="update adquisiciones.transaccion_detalle set estado ='NUL' where cod_trans_detalle="+cod_trans_detalle;
////                    Consulta(sql);
//                  System.out.println("El cod_trans_detalle es: "+datos[f].get("COD_TRANS_DETALLE").toString());
//                }
//            }
            System.out.println("Despues de anular...");
            javax.swing.JOptionPane.showMessageDialog(this, "EL Tramite fue anulado correctamente", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getCod_Trans(cod_transaccion);
            int cod_T_N=Integer.parseInt(d[0].get("COD_TRANS_NRO").toString());
            Map[] datos=puerto.dias_restantes(cod_T_N);
            int dias=Integer.parseInt(datos[0].get("DIAS_RESTANTES").toString());
            System.out.println("estos son los dias "+ dias);
            if(dias>=0)
            {
                Map[] datos1 =puerto.getResIni(cod_transaccion);
                if(datos1==null)
                {
                    JOptionPane.showMessageDialog(this, "USTED DEBE GENERAR LA RESOLUCION DE INICIO PRIMERO");
                }
                else
                {
                    JD_FECH_ANPE JDF = new JD_FECH_ANPE(menu, false, this.cod_trans_nro, this.cod_transaccion);
                    JDF.setVisible(true);
                }
            }
            else
            {
                
                JOptionPane.showMessageDialog(null, "DEBIDO A QUE EL PROCESO: \n"+datos[0].get("PROCESO").toString()+" HA EXPIRTADO EL TIEMPO ESTABLECIDO: "+datos[0].get("FECHA")+"\nDEBE PRESENTAR UNA PRORROGA PARA REANUDARLO" );
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "USTED NO HA INSERTADO LAS FECHAS DEL SICOES");
        }
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
//        JD_GeneraContrato gc = new JD_GeneraContrato(menu, false, this.cod_trans_nro);
//        gc.setVisible(true);
        JD_GeneraContrato2 gc2 = new JD_GeneraContrato2(menu, false, this.cod_transaccion,cuantia,cod_w,cod_trans_nro);
        gc2.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        JD_FechaEntrega fe = new JD_FechaEntrega(menu, false, cod_trans_nro);
        fe.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        GuardarDatos(this.JT_HRUTA.getText());
        javax.swing.JOptionPane.showMessageDialog(this, "Se guardo la Hoja de Ruta", "SYSTEM CAPRICORN",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void BtnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalir1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ft.setVisible(true);
    }//GEN-LAST:event_BtnSalir1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] d=puerto.getCod_Trans(cod_transaccion);
            int cod_T_N=Integer.parseInt(d[0].get("COD_TRANS_NRO").toString());
            Map[] datos=puerto.dias_restantes(cod_T_N);
            int dias=Integer.parseInt(datos[0].get("DIAS_RESTANTES").toString());
            System.out.println("estos son los dias "+ dias);
            if(dias>=0)
            {
                Map[] datos1 =puerto.getResIni(cod_transaccion);
                if(datos1==null)
                {
                    JOptionPane.showMessageDialog(this, "USTED DEBE GENERAR LA RESOLUCION DE INICIO PRIMERO");
                }
                else
                {
                    if(cod_rol==7)
                    {
                        JD_FECH_ANPE JDF = new JD_FECH_ANPE(menu, false, this.cod_trans_nro, this.cod_transaccion,cod_rol);
                        JDF.setVisible(true);
                    }
                }
            }
            else
            {      
                JOptionPane.showMessageDialog(null, "DEBIDO A QUE EL PROCESO: \n"+datos[0].get("PROCESO").toString()+" HA EXPIRTADO EL TIEMPO ESTABLECIDO: "+datos[0].get("FECHA")+"\nDEBE PRESENTAR UNA PRORROGA PARA REANUDARLO" );
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "USTED DEBE GENERAR LA RESOLUCION DE INICIO PRIMERO");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void CalFechaNotificacionStateChanged(javax.swing.event.ChangeEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void CalFechaNotificacionKeyPressed(java.awt.event.KeyEvent evt) {                                                
        // TODO add your handling code here:
    }
    
    private void CalFechaIngStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CalFechaIngStateChanged
        // TODO add your handling code here:
//        DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
//        String s =  df1.format(CalFechaFact.getValue());
//        String s1 =  df1.format(CalFechaIng.getValue());
//        int comp=compara_fechas(s, s1);
//        if(comp!=0)
//        {
//            JOptionPane.showMessageDialog(null, "Debe introducir una fecha que no sea menor a la anterior");
//            CalFechaIng.setValue(CalFechaFact.getValue());
//        }
    }//GEN-LAST:event_CalFechaIngStateChanged

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        
        JDInformeComision x = new JDInformeComision(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
        this.setModal(false);
        x.setVisible(true);
        // TODO add your handling code here:
        /*if (proveedor.getCod_proveedor() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe adjudicar la orden a un proveedor", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            BtnBuscaProveedor.requestFocus();
            return;
        } else {*/
            /*if (cod_w == 4) {
                System.out.println("entra al if de obras");
                
                JDResAdjObra x = new JDResAdjObra(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
                this.setModal(false);
                x.setVisible(true);
            } else {
                this.BtnGuardar.doClick();
                System.out.println(":P :P :P cod_w: " + cod_w + "cod_trans_nro: " + this.cod_trans_nro + "  nombre_comercial: " + this.proveedor.getCasa_comercial() + " detalle: " + this.detalle);
                //JDResAdj x = new JDResAdj(menu,true,this.cod_trans_nro);
                JDResAdj2 x = new JDResAdj2(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
                this.setModal(false);
                x.setVisible(true);
            }
*/
        //}
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Esta Seguro de Adicionar un nuevo item?",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
            if (res != javax.swing.JOptionPane.YES_OPTION) {
                return;
            }
        addItem();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Esta Seguro de eliminar el item seleccionado?",
                    "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
            if (res != javax.swing.JOptionPane.YES_OPTION) {
                return;
            }
        delItem();
    }//GEN-LAST:event_jButton17ActionPerformed
    
    private void delItem(){
        try {
            int fila = TblItems.getSelectedRow();
            int cod_trans_detalle = Integer.parseInt(TblItems.getValueAt(fila, 2).toString());
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.delItemPro("SET-delItemPro", cod_trans_detalle);
            this.LlenaItems();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Debe seleccionar un item", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addItem(){
        try {
            int aux_cod_trans_nro;
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            String cod_trans_detalle = puerto.secTransaccionDetalle();
            puerto.addItem("SET-addItem",Integer.parseInt(cod_trans_detalle) ,cod_transaccion, gestion);
                                    
            Map[] ArrayCod_trans_nro = puerto.getCod_trans_nro("GET-getCod_trans_nro",cod_transaccion);
            
            if (ArrayCod_trans_nro != null){
                System.out.println("entro!!!");
                for (int c=0;c<ArrayCod_trans_nro.length;c++){
                   aux_cod_trans_nro = Integer.parseInt(ArrayCod_trans_nro[c].get("COD_TRANS_NRO").toString());
                   System.out.println("El aux_cod_trans_nro es: "+aux_cod_trans_nro);
                   puerto.setTransaccionDetalleNro("SET-upDateTransDetNro", Integer.parseInt(cod_trans_detalle), aux_cod_trans_nro);
                }
            }
            this.LlenaItems();
            
            
        } catch (Exception e) {
            System.err.println("Error terrible!!!: "+e);
        }
    }
    public int compara_fechas(String f1,String f2)
    {
        int res=-1;
        try {
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
    
    private void GuardarDatos(String hoja_ruta) {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.addHojaRuta("SET-addHojaRuta", cod_transaccion, hoja_ruta);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar la hoja de ruta", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void GetDatos() {
        try {
            System.out.println("El cod_transaccion es: " + cod_transaccion);
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("La hoja de ruta es: " + puerto.getHojaRuta(cod_transaccion));
            this.JT_HRUTA.setText(puerto.getHojaRuta(cod_transaccion));
        } catch (Exception e) {
//            System.out.println("Terrible Error!!!");
            javax.swing.JOptionPane.showMessageDialog(this, "Error al leer la hoja de ruta", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnular;
    private javax.swing.JButton BtnAvanzar;
    private javax.swing.JButton BtnBuscaProveedor;
    private javax.swing.JButton BtnGarantia;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnRetornar;
    private javax.swing.JButton BtnSalir1;
    private net.sf.nachocalendar.components.DateField CalFechaFact;
    private net.sf.nachocalendar.components.DateField CalFechaIng;
    private net.sf.nachocalendar.components.DateField CalFechaNotificacion;
    private javax.swing.JTextField JT_HRUTA;
    private javax.swing.JLabel LblA;
    private javax.swing.JLabel LblCuantia;
    private javax.swing.JLabel LblDe;
    private javax.swing.JLabel LblDetalle;
    private javax.swing.JLabel LblFactura;
    private javax.swing.JLabel LblFecFact;
    private javax.swing.JLabel LblFechaIng;
    private javax.swing.JLabel LblFechaIng1;
    private javax.swing.JLabel LblMemo;
    private javax.swing.JLabel LblNro;
    private javax.swing.JLabel LblTituloNro;
    private javax.swing.JLabel LblUnidadDes;
    private javax.swing.JLabel LblUnidadSol;
    private javax.swing.JPanel PnlAdquisiciones;
    private javax.swing.JPanel PnlAlmacen;
    private javax.swing.JScrollPane PnlItems;
    private javax.swing.JScrollPane ScrObs;
    private javax.swing.JTabbedPane TabTransaccion;
    private javax.swing.JTable TblItems;
    private javax.swing.JTextField TxtCasa;
    private javax.swing.JTextField TxtFactura;
    private javax.swing.JTextField TxtMemo;
    private javax.swing.JTextField TxtObsAdq;
    private javax.swing.JTextPane TxtObsAlmacen;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
