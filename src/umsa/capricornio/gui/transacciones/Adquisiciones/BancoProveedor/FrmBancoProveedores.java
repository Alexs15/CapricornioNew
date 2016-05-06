/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmBancoProveedores.java
 *
 * Created on 03-10-2011, 10:08:48 AM
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Proveedores;
import umsa.capricornio.domain.Transaccion;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.reportes.RepProveedores;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.TablaProveedores;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.TablaProveedores1;
import umsa.capricornio.gui.transacciones.reporte.RepTransaccion;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

/**
 *
 * @author julian
 */
public class FrmBancoProveedores extends javax.swing.JInternalFrame {

    FrmMenu menu;
    
    int cod_usuario,cod_rol,cod_almacen;
    private Runtime r;
    int sw=0;
    
    TablaProveedores proveedor;
    TablaProveedores1 proveedor1;
    /** Creates new form FrmBancoProveedores */
    public FrmBancoProveedores(FrmMenu menu,int cod_usuario,int cod_rol,int cod_almacen) {
        this.menu=menu;
        this.cod_usuario=cod_usuario;
        this.cod_rol=cod_rol;        
        this.cod_almacen=cod_almacen;
        initComponents();
        ConstruyeTablaProveedores();
    }

    private void ConstruyeTablaProveedores(){
        proveedor = new TablaProveedores();
        TblProveedores.setAutoCreateColumnsFromModel(false);
        TblProveedores.setModel(proveedor);

        for (int k = 0; k < TablaProveedores.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaProveedores.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaProveedores.m_columns[k].m_width, renderer, null);
            TblProveedores.addColumn(column);
        }
        JTableHeader header = TblProveedores.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlProveedores.getViewport().add(TblProveedores);
    }
    private void ConstruyeTablaProveedores1(){
        proveedor1 = new TablaProveedores1();
        TblProveedores1.setAutoCreateColumnsFromModel(false);
        TblProveedores1.setModel(proveedor1);

        for (int k = 0; k < TablaProveedores1.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaProveedores1.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaProveedores1.m_columns[k].m_width, renderer, null);
            TblProveedores1.addColumn(column);
        }
        JTableHeader header = TblProveedores1.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlProveedores1.getViewport().add(TblProveedores1);
    }
    
        
    void CerrarFrame(){
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
      
    private void LlenaBandeja(){ 
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getProveedores();
            System.out.println("jjjj "+datos.length);
            CerearTablaProveedor();
            System.out.println("jjjj "+datos[0].get("cod"));
            System.out.println("jjjj "+datos[0].get("COD"));
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    proveedor.insert(c);
                    System.out.println("uuuuu");
                    TblProveedores.tableChanged(new TableModelEvent(proveedor, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    System.out.println("el cod es--> "+datos[c].get("nombre"));
                    TblProveedores.setValueAt(datos[c].get("COD"),c,0);
                    TblProveedores.setValueAt(datos[c].get("TIPO"),c,1);                    
                    TblProveedores.setValueAt(datos[c].get("CLASE"),c,2);
                    TblProveedores.setValueAt(datos[c].get("NOMBRE"),c,3);
                    TblProveedores.setValueAt(datos[c].get("NC"),c,4);
                    TblProveedores.setValueAt(datos[c].get("TEL"),c,5);
                    TblProveedores.setValueAt(datos[c].get("DIR"),c,6);
                    //TblProveedores.setValueAt(datos[c].get("SERVICIO"),c,7);
                    //TblProveedores.setValueAt(datos[c].get("DIRECCION"),c,7);
                    //TblProveedores.setValueAt(datos[c].get("TELEFONO"),c,8);
                    //TblProveedores.setValueAt(datos[c].get("FAX"),c,9);
                    //TblProveedores.setValueAt(datos[c].get("NIT"),c,10);
                    //TblProveedores.setValueAt(datos[c].get("ESTADO"),c,11);
                }
            }
            else
            {
                System.out.println("sin datos :p");
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
        
    void CerearTablaProveedor(){
        int f = TblProveedores.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (proveedor.delete(i)) {
                TblProveedores.tableChanged(new TableModelEvent(
                proveedor, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
    void CerearTablaProveedor1(){
        int f = TblProveedores1.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (proveedor1.delete(i)) {
                System.out.println("ew");
                TblProveedores1.tableChanged(new TableModelEvent(
                proveedor1, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
       
    void RecuperaDatosProveedor(){
        int fila = TblProveedores.getSelectedRow();
        TxtCasaComercial.setText(TblProveedores.getValueAt(fila, 6).toString());
        TxtDireccion.setText(TblProveedores.getValueAt(fila, 8).toString());
        TxtFax.setText(TblProveedores.getValueAt(fila, 10).toString());
        TxtGranActividad.setText(TblProveedores.getValueAt(fila, 3).toString());
        TxtNit.setText(TblProveedores.getValueAt(fila, 11).toString());
        TxtPartida.setText(TblProveedores.getValueAt(fila, 5).toString());
        TxtRazonNit.setText(TblProveedores.getValueAt(fila, 4).toString());
        TxtServicio.setText(TblProveedores.getValueAt(fila, 7).toString());
        TxtTelefono.setText(TblProveedores.getValueAt(fila, 9).toString());
        TxtWeb.setText(TblProveedores.getValueAt(fila, 2).toString());
        
        if("IN".equals(TblProveedores.getValueAt(fila, 1).toString()))
            RadInhabilitado.setSelected(true);
        else
            RadHabilitado.setSelected(true);
    }
    
    void MostrarProveedores(){
        List list=new ArrayList();
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getProveedores();            
            if (datos!=null){                 
                Map map = new HashMap();
                for (int f=0;f<datos.length;f++){
                    Proveedores prov = new Proveedores();
                    prov.setWeb(datos[f].get("WEB").toString());
                    prov.setGran_actividad_nit(datos[f].get("GRAN_ACTIVIDAD_NIT").toString());
                    prov.setRazon_social_nit(datos[f].get("RAZON_SOCIAL_NIT").toString());
                    prov.setPartida(datos[f].get("PARTIDA").toString());
                    prov.setCasa_comercial(datos[f].get("CASA_COMERCIAL").toString());
                    prov.setServicio(datos[f].get("SERVICIO").toString());
                    prov.setDireccion(datos[f].get("DIRECCION").toString());
                    prov.setTelefono(datos[f].get("TELEFONO").toString());
                    prov.setFax(datos[f].get("FAX").toString());
                    prov.setNit(datos[f].get("NIT").toString());
                    prov.setEstado(datos[f].get("ESTADO").toString());                                        
                    list.add(prov);
                } 
                RepProveedores rep = new RepProveedores();
                rep.Reporte(list);
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
        catch (ServiceException e){ System.out.println(e);} 
        catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"DEBE ELEJIR UNA FILA PARA PODER IMPRIMIR EL REPORTE","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnGrpHabilitado = new javax.swing.ButtonGroup();
        PnlProveedores = new javax.swing.JScrollPane();
        TblProveedores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        Btneliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        TxtCasaComercial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TxtServicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtDireccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtPartida = new javax.swing.JTextField();
        TxtTelefono = new javax.swing.JTextField();
        TxtFax = new javax.swing.JTextField();
        TxtWeb = new javax.swing.JTextField();
        TxtNit = new javax.swing.JTextField();
        TxtGranActividad = new javax.swing.JTextField();
        TxtRazonNit = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        RadHabilitado = new javax.swing.JRadioButton();
        RadInhabilitado = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        PnlProveedores1 = new javax.swing.JScrollPane();
        TblProveedores1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(39, 67, 121));
        setTitle("BANCO DE PROVEEDORES");
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
        getContentPane().setLayout(null);

        TblProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblProveedoresMousePressed(evt);
            }
        });
        PnlProveedores.setViewportView(TblProveedores);

        getContentPane().add(PnlProveedores);
        PnlProveedores.setBounds(10, 70, 930, 220);

        jPanel1.setBackground(new java.awt.Color(185, 203, 221));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Administracion de Banco de Proveedores"));
        jPanel1.setLayout(null);

        BtnNuevo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(BtnNuevo);
        BtnNuevo.setBounds(350, 240, 30, 25);

        BtnGuardar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/database_add.png"))); // NOI18N
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnGuardar);
        BtnGuardar.setBounds(380, 240, 30, 25);

        BtnModificar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnModificar);
        BtnModificar.setBounds(410, 240, 30, 25);

        Btneliminar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        Btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtneliminarActionPerformed(evt);
            }
        });
        jPanel1.add(Btneliminar);
        Btneliminar.setBounds(440, 240, 30, 25);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Casa Comercial :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 30, 100, 20);

        TxtCasaComercial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtCasaComercial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtCasaComercialKeyPressed(evt);
            }
        });
        jPanel1.add(TxtCasaComercial);
        TxtCasaComercial.setBounds(120, 30, 760, 21);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Servicio :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(50, 60, 60, 20);

        TxtServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtServicioKeyPressed(evt);
            }
        });
        jPanel1.add(TxtServicio);
        TxtServicio.setBounds(120, 60, 760, 21);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Direccion :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(50, 90, 60, 20);

        TxtDireccion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtDireccionKeyPressed(evt);
            }
        });
        jPanel1.add(TxtDireccion);
        TxtDireccion.setBounds(120, 90, 760, 21);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Partida(s) :");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(40, 120, 70, 20);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Telefono :");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(250, 120, 55, 20);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("FAX :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(410, 120, 40, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("WEB :");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(570, 120, 40, 20);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("NIT :");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(80, 150, 30, 20);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Gran Actividad NIT :");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(250, 150, 120, 20);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Razon Social NIT :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(530, 150, 100, 20);

        TxtPartida.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtPartida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtPartidaKeyPressed(evt);
            }
        });
        jPanel1.add(TxtPartida);
        TxtPartida.setBounds(120, 120, 100, 21);

        TxtTelefono.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtTelefonoKeyPressed(evt);
            }
        });
        jPanel1.add(TxtTelefono);
        TxtTelefono.setBounds(310, 120, 90, 21);

        TxtFax.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtFax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtFaxKeyPressed(evt);
            }
        });
        jPanel1.add(TxtFax);
        TxtFax.setBounds(460, 120, 100, 21);

        TxtWeb.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtWeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtWebKeyPressed(evt);
            }
        });
        jPanel1.add(TxtWeb);
        TxtWeb.setBounds(640, 120, 240, 21);

        TxtNit.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNitKeyPressed(evt);
            }
        });
        jPanel1.add(TxtNit);
        TxtNit.setBounds(120, 150, 100, 21);

        TxtGranActividad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtGranActividad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtGranActividadKeyPressed(evt);
            }
        });
        jPanel1.add(TxtGranActividad);
        TxtGranActividad.setBounds(380, 150, 120, 21);

        TxtRazonNit.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.add(TxtRazonNit);
        TxtRazonNit.setBounds(640, 150, 240, 21);

        jPanel2.setBackground(new java.awt.Color(185, 203, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        RadHabilitado.setBackground(new java.awt.Color(185, 203, 221));
        BtnGrpHabilitado.add(RadHabilitado);
        RadHabilitado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        RadHabilitado.setForeground(new java.awt.Color(0, 102, 0));
        RadHabilitado.setSelected(true);
        RadHabilitado.setText("EMPRESA HABILITADA");
        jPanel2.add(RadHabilitado);
        RadHabilitado.setBounds(100, 10, 180, 20);

        RadInhabilitado.setBackground(new java.awt.Color(185, 203, 221));
        BtnGrpHabilitado.add(RadInhabilitado);
        RadInhabilitado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        RadInhabilitado.setForeground(new java.awt.Color(204, 0, 0));
        RadInhabilitado.setText("EMPRESA INHABILITADA");
        jPanel2.add(RadInhabilitado);
        RadInhabilitado.setBounds(320, 10, 180, 20);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(160, 190, 620, 40);

        jButton1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jButton1.setText("VER SANSIONES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(10, 190, 130, 30);

        jButton2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jButton2.setText("Reporte");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(210, 240, 90, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 20, 930, 20);

        jPanel3.setBackground(new java.awt.Color(185, 203, 221));
        jPanel3.setLayout(null);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_first.png"))); // NOI18N
        jPanel3.add(jButton3);
        jButton3.setBounds(20, 0, 49, 25);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_previous.png"))); // NOI18N
        jPanel3.add(jButton4);
        jButton4.setBounds(70, 0, 50, 25);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_next.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);
        jButton5.setBounds(120, 0, 50, 25);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_last.png"))); // NOI18N
        jPanel3.add(jButton6);
        jButton6.setBounds(170, 0, 50, 25);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        jPanel3.add(BtnSalir);
        BtnSalir.setBounds(230, 0, 90, 25);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 290, 930, 30);

        jPanel4.setBackground(new java.awt.Color(185, 203, 221));
        jPanel4.setLayout(null);

        jLabel11.setText("NOMBRE:");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(30, 4, 50, 20);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField1);
        jTextField1.setBounds(80, 0, 180, 30);

        jLabel12.setText("NOMBRE COMERCIAL:");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(270, 0, 110, 30);
        jPanel4.add(jTextField2);
        jTextField2.setBounds(380, 0, 190, 30);

        jLabel13.setText("ITEM:");
        jPanel4.add(jLabel13);
        jLabel13.setBounds(590, 4, 30, 20);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField3);
        jTextField3.setBounds(620, 0, 170, 30);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/find.png"))); // NOI18N
        jButton7.setText("BUSCAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(810, 0, 60, 30);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 40, 930, 30);

        TblProveedores1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PnlProveedores1.setViewportView(TblProveedores1);

        getContentPane().add(PnlProveedores1);
        PnlProveedores1.setBounds(10, 360, 930, 130);

        jPanel5.setBackground(new java.awt.Color(185, 203, 221));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Partidas que se les adjudicaron"));
        getContentPane().add(jPanel5);
        jPanel5.setBounds(10, 340, 930, 20);
        jPanel5.getAccessibleContext().setAccessibleName("");

        setBounds(0, 0, 969, 529);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();            
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CerrarFrame();
    }//GEN-LAST:event_formInternalFrameClosed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        String obs_almacen=null,obs_adq=null,casa_comercial=null, direccion=null, telefono=null, nit=null,serv=null,partida=null,fax=null,web=null,gran_act=null,razon_nit=null;
        if(!"".equals(TxtCasaComercial.getText().trim()))
            casa_comercial="'"+TxtCasaComercial.getText().trim().toUpperCase()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la casa comercial","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCasaComercial.requestFocus();            
            return;
        }
        if(!"".equals(TxtServicio.getText().trim()))
            serv="'"+TxtServicio.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la direccion","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtServicio.requestFocus();            
            return;
        }
        if(!"".equals(TxtDireccion.getText().trim()))
            direccion="'"+TxtDireccion.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la direccion","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtDireccion.requestFocus();            
            return;
        }
        if(!"".equals(TxtPartida.getText().trim()))
            partida="'"+TxtPartida.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el telefono","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtPartida.requestFocus();            
            return;
        }
        if(!"".equals(TxtTelefono.getText().trim()))
            telefono="'"+TxtTelefono.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el telefono","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtTelefono.requestFocus();            
            return;
        }
        if(!"".equals(TxtFax.getText().trim()))
            fax="'"+TxtFax.getText().trim()+"'";        
        
        if(!"".equals(TxtWeb.getText().trim()))
            web="'"+TxtWeb.getText().trim()+"'";        
        
        if(!"".equals(TxtNit.getText().trim()))
            nit="'"+TxtNit.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtNit.requestFocus();
            return;
        }
        if(!"".equals(TxtGranActividad.getText().trim()))
            gran_act="'"+TxtGranActividad.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtGranActividad.requestFocus();
            return;
        }
        if(!"".equals(TxtRazonNit.getText().trim()))
            razon_nit="'"+TxtRazonNit.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtRazonNit.requestFocus();
            return;
        }
       
        boolean existe_prov=false;
        for (int f=0; f<TblProveedores.getRowCount();f++){
            if (TxtNit.getText().trim().equals(TblProveedores.getValueAt(f, 11))
                    && TxtPartida.getText().trim().equals(TblProveedores.getValueAt(f, 5))){
                existe_prov=true;
                break;
            }
        }
        if (existe_prov) {
            javax.swing.JOptionPane.showMessageDialog(this,"El proveedor con este NIT ya existe","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCasaComercial.requestFocus();
            return;
        }
            
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setProveedorCrea("SET-upDateNewProveedor", casa_comercial, serv, direccion, partida, telefono, fax, web,nit,gran_act,razon_nit);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ALMACENADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            TxtCasaComercial.setText("");
            TxtDireccion.setText("");
            TxtFax.setText("");
            TxtGranActividad.setText("");
            TxtNit.setText("");
            TxtPartida.setText("");
            TxtRazonNit.setText("");
            TxtServicio.setText("");
            TxtTelefono.setText("");
            TxtWeb.setText("");
            RadHabilitado.setSelected(true);
            RadHabilitado.setEnabled(false);
            RadInhabilitado.setEnabled(false);
            BtnNuevo.setEnabled(true);
            BtnGuardar.setEnabled(false);
            BtnModificar.setEnabled(false);
            Btneliminar.setEnabled(false);
            LlenaBandeja();
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        String obs_almacen=null,obs_adq=null,casa_comercial=null, direccion=null, telefono=null, nit=null,serv=null,partida=null,fax=null,web=null,gran_act=null,razon_nit=null,estado=null;
        if(!"".equals(TxtCasaComercial.getText().trim()))
            casa_comercial="'"+TxtCasaComercial.getText().trim().toUpperCase()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la casa comercial","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCasaComercial.requestFocus();            
            return;
        }
        if(!"".equals(TxtServicio.getText().trim()))
            serv="'"+TxtServicio.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la direccion","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtServicio.requestFocus();            
            return;
        }
        if(!"".equals(TxtDireccion.getText().trim()))
            direccion="'"+TxtDireccion.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir la direccion","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtDireccion.requestFocus();            
            return;
        }
        if(!"".equals(TxtPartida.getText().trim()))
            partida="'"+TxtPartida.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el telefono","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtPartida.requestFocus();            
            return;
        }
        if(!"".equals(TxtTelefono.getText().trim()))
            telefono="'"+TxtTelefono.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el telefono","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtTelefono.requestFocus();            
            return;
        }
        if(!"".equals(TxtFax.getText().trim()))
            fax="'"+TxtFax.getText().trim()+"'";        
        
        if(!"".equals(TxtWeb.getText().trim()))
            web="'"+TxtWeb.getText().trim()+"'";        
        
        if(!"".equals(TxtNit.getText().trim()))
            nit="'"+TxtNit.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtNit.requestFocus();
            return;
        }
        if(!"".equals(TxtGranActividad.getText().trim()))
            gran_act="'"+TxtGranActividad.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtGranActividad.requestFocus();
            return;
        }
        if(!"".equals(TxtRazonNit.getText().trim()))
            razon_nit="'"+TxtRazonNit.getText().trim()+"'";
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtRazonNit.requestFocus();
            return;
        }
        
        int fila=TblProveedores.getSelectedRow();
        int cod_proveedor=Integer.parseInt(TblProveedores.getValueAt(fila, 0).toString());
        
        if (RadHabilitado.isSelected()) estado="HA";
        if (RadInhabilitado.isSelected()) estado="IN";
        
        if ("IN".equals(estado)) {
            DiagSansion ordenes= new DiagSansion(menu,cod_proveedor,TblProveedores.getValueAt(fila, 6).toString());
            ordenes.AbreDialogo(); 
            
            if (!ordenes.SansionGenerada()){
                javax.swing.JOptionPane.showMessageDialog(this,"NO SE GENERO LA SANSION","SYSTEM CAPRICORN",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        
        boolean existe_prov=false;
        for (int f=0; f<TblProveedores.getRowCount();f++){
            if (fila!=f) {
                if (TxtNit.getText().trim().equals(TblProveedores.getValueAt(f, 11))
                        && TxtPartida.getText().trim().equals(TblProveedores.getValueAt(f, 5))){
                    existe_prov=true;
                    break;
                }
            }
        }
        if (existe_prov) {
            javax.swing.JOptionPane.showMessageDialog(this,"El proveedor con este NIT ya existe","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCasaComercial.requestFocus();
            return;
        }
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setProveedorActualiza("SET-upDateNewProveedor", cod_proveedor,casa_comercial, serv, direccion, partida, telefono, fax, web,nit,gran_act,razon_nit,estado);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS MODIFICADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            TxtCasaComercial.setText("");
            TxtDireccion.setText("");
            TxtFax.setText("");
            TxtGranActividad.setText("");
            TxtNit.setText("");
            TxtPartida.setText("");
            TxtRazonNit.setText("");
            TxtServicio.setText("");
            TxtTelefono.setText("");
            TxtWeb.setText("");
            RadHabilitado.setSelected(true);
            RadHabilitado.setEnabled(false);
            RadInhabilitado.setEnabled(false);
            BtnNuevo.setEnabled(true);
            BtnGuardar.setEnabled(false);
            BtnModificar.setEnabled(false);
            Btneliminar.setEnabled(false);
            TblProveedores.setEnabled(true);
            
            TxtCasaComercial.setEnabled(false);
            TxtDireccion.setEnabled(false);
            TxtFax.setEnabled(false);
            TxtGranActividad.setEnabled(false);
            TxtNit.setEnabled(false);
            TxtPartida.setEnabled(false);
            TxtRazonNit.setEnabled(false);
            TxtServicio.setEnabled(false);
            TxtTelefono.setEnabled(false);
            TxtWeb.setEnabled(false);
        
            LlenaBandeja();
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void BtneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtneliminarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea Eliminar este proveedor?",
                "ZODIAC CAPRICORN SYSTEM",javax.swing.JOptionPane.YES_NO_OPTION );
        if (res == javax.swing.JOptionPane.NO_OPTION) return;
        
        int fila=TblProveedores.getSelectedRow();
        int cod_proveedor=Integer.parseInt(TblProveedores.getValueAt(fila, 0).toString());
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setProveedorElimina("SET-upDateNewProveedor", cod_proveedor);
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ELIMINADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            TxtCasaComercial.setText("");
            TxtDireccion.setText("");
            TxtFax.setText("");
            TxtGranActividad.setText("");
            TxtNit.setText("");
            TxtPartida.setText("");
            TxtRazonNit.setText("");
            TxtServicio.setText("");
            TxtTelefono.setText("");
            TxtWeb.setText("");
            RadHabilitado.setSelected(true);
            
            BtnNuevo.setEnabled(true);
            BtnGuardar.setEnabled(false);
            BtnModificar.setEnabled(false);
            Btneliminar.setEnabled(false);
            RadHabilitado.setEnabled(false);
            RadInhabilitado.setEnabled(false);
            TblProveedores.setEnabled(true);
            
            TxtCasaComercial.setEnabled(false);
            TxtDireccion.setEnabled(false);
            TxtFax.setEnabled(false);
            TxtGranActividad.setEnabled(false);
            TxtNit.setEnabled(false);
            TxtPartida.setEnabled(false);
            TxtRazonNit.setEnabled(false);
            TxtServicio.setEnabled(false);
            TxtTelefono.setEnabled(false);
            TxtWeb.setEnabled(false);
            
            LlenaBandeja();
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
    }//GEN-LAST:event_BtneliminarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        LlenaBandeja();
        RadHabilitado.setEnabled(false);
        RadInhabilitado.setEnabled(false);
        BtnNuevo.setEnabled(true);
        BtnGuardar.setEnabled(false);
        BtnModificar.setEnabled(false);
        Btneliminar.setEnabled(false);
        
        TxtCasaComercial.setEnabled(false);
        TxtDireccion.setEnabled(false);
        TxtFax.setEnabled(false);
        TxtGranActividad.setEnabled(false);
        TxtNit.setEnabled(false);
        TxtPartida.setEnabled(false);
        TxtRazonNit.setEnabled(false);
        TxtServicio.setEnabled(false);
        TxtTelefono.setEnabled(false);
        TxtWeb.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void TblProveedoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblProveedoresMousePressed
        if (evt.getClickCount() == 2) {
            //RecuperaDatosProveedor();
            /**BtnNuevo.setEnabled(true);
            BtnGuardar.setEnabled(false);
            BtnModificar.setEnabled(true);
            Btneliminar.setEnabled(true);
            RadHabilitado.setEnabled(true);
            RadInhabilitado.setEnabled(true);
            TblProveedores.setEnabled(false);
            TxtCasaComercial.setEnabled(true);
            TxtDireccion.setEnabled(true);
            TxtFax.setEnabled(true);
            TxtGranActividad.setEnabled(true);
            TxtNit.setEnabled(true);
            TxtPartida.setEnabled(true);
            TxtRazonNit.setEnabled(true);
            TxtServicio.setEnabled(true);
            TxtTelefono.setEnabled(true);
            TxtWeb.setEnabled(true);*/
            
            
            //TblProveedores1.removeAll();
            if(sw==0)
            {
                ConstruyeTablaProveedores1();
                sw=1;
            }
            int fila=TblProveedores.getSelectedRow();
            CerearTablaProveedor1();
            //int cod_proveedor=Integer.parseInt(TblProveedores1.getValueAt(fila, 0).toString());
            try{
                System.out.println("uno");
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                System.out.println("dos");
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                System.out.println("3");
                Map[] datos=puerto.getDetProveedores(TblProveedores.getValueAt(fila, 0).toString());
                System.out.println("4.->"+datos.length);
                CerearTablaProveedor1();
                if (datos!=null){
                    System.out.println("4.->"+datos.length);
                for (int c=0;c<datos.length;c++){
                    System.out.println("PARTIDA "+datos[c].get("PARTIDA"));
                    System.out.println("PARTIDA "+datos[c].get("CANTIDAD"));
                    System.out.println("PARTIDA "+datos[c].get("UNIDAD"));
                    System.out.println("PARTIDA "+datos[c].get("DETALLE"));
                    proveedor1.insert(c);
                    System.out.println("aaaaaa");
                    TblProveedores1.tableChanged(new TableModelEvent(proveedor1, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    //System.out.println("el cod es--> "+datos[c].get("nombre"));
                    TblProveedores1.setValueAt(datos[c].get("PARTIDA"),c,0);
                    TblProveedores1.setValueAt(datos[c].get("CANTIDAD"),c,1);                    
                    TblProveedores1.setValueAt(datos[c].get("UNIDAD"),c,2);
                    TblProveedores1.setValueAt(datos[c].get("DETALLE"),c,3);
                    TblProveedores1.setValueAt(datos[c].get("GESTION"),c,4);
                    }
                }
                else
                {
                    System.out.println("esta vacio rayos :P");
                }
                
            }catch(Exception e)
            {
                System.out.println("error "+e.getMessage());
            }
            
        }
    }//GEN-LAST:event_TblProveedoresMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int fila=TblProveedores.getSelectedRow();
        int cod_proveedor=Integer.parseInt(TblProveedores.getValueAt(fila, 0).toString());
        
        DiagSansion ordenes= new DiagSansion(menu,cod_proveedor,TblProveedores.getValueAt(fila, 6).toString());
        ordenes.AbreDialogo(); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        BtnNuevo.setEnabled(false);
        BtnGuardar.setEnabled(true);
        BtnModificar.setEnabled(false);
        Btneliminar.setEnabled(false);
        RadHabilitado.setSelected(true);
        TblProveedores.setEnabled(true);
        
        TxtCasaComercial.setText("");
        TxtDireccion.setText("");
        TxtFax.setText("");
        TxtGranActividad.setText("");
        TxtNit.setText("");
        TxtPartida.setText("");
        TxtRazonNit.setText("");
        TxtServicio.setText("");
        TxtTelefono.setText("");
        TxtWeb.setText("");
        
        TxtCasaComercial.setEnabled(true);
        TxtDireccion.setEnabled(true);
        TxtFax.setEnabled(true);
        TxtGranActividad.setEnabled(true);
        TxtNit.setEnabled(true);
        TxtPartida.setEnabled(true);
        TxtRazonNit.setEnabled(true);
        TxtServicio.setEnabled(true);
        TxtTelefono.setEnabled(true);
        TxtWeb.setEnabled(true);
        TxtCasaComercial.requestFocus();
        
        RadHabilitado.setEnabled(false);
        RadInhabilitado.setEnabled(false);
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void TxtCasaComercialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCasaComercialKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtServicio.requestFocus();
        }        
    }//GEN-LAST:event_TxtCasaComercialKeyPressed

    private void TxtServicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtServicioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtDireccion.requestFocus();
        }
    }//GEN-LAST:event_TxtServicioKeyPressed

    private void TxtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDireccionKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtPartida.requestFocus();
        }
    }//GEN-LAST:event_TxtDireccionKeyPressed

    private void TxtPartidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPartidaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtTelefono.requestFocus();
        }
    }//GEN-LAST:event_TxtPartidaKeyPressed

    private void TxtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtTelefonoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtFax.requestFocus();
        }
    }//GEN-LAST:event_TxtTelefonoKeyPressed

    private void TxtFaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFaxKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtWeb.requestFocus();
        }
    }//GEN-LAST:event_TxtFaxKeyPressed

    private void TxtWebKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtWebKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtNit.requestFocus();
        }
    }//GEN-LAST:event_TxtWebKeyPressed

    private void TxtNitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNitKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtGranActividad.requestFocus();
        }
    }//GEN-LAST:event_TxtNitKeyPressed

    private void TxtGranActividadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtGranActividadKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtRazonNit.requestFocus();
        }
    }//GEN-LAST:event_TxtGranActividadKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MostrarProveedores();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String nombre=this.jTextField1.getText();
        String n_comercial=this.jTextField2.getText();
        String item=this.jTextField3.getText();
        if(nombre=="" && n_comercial=="" && item=="")
            JOptionPane.showMessageDialog( null, "debe ingresar un valor" );
        else
        {
            if(nombre=="")
                nombre="%";
            else
                nombre=nombre.toUpperCase();
            if(n_comercial=="")
                n_comercial="%";
            else
                n_comercial=n_comercial.toUpperCase();
            if(item=="")
                item="%";
            else
                item=item.toUpperCase();
            try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getProveedoresbusca(nombre,n_comercial,item);
            //System.out.println("jjjj "+datos.length);
            CerearTablaProveedor();
            //System.out.println("jjjj "+datos[0].get("cod"));
            //System.out.println("jjjj "+datos[0].get("COD"));
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    proveedor.insert(c);
                    System.out.println("uuuuu");
                    TblProveedores.tableChanged(new TableModelEvent(proveedor, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    System.out.println("el cod es--> "+datos[c].get("nombre"));
                    TblProveedores.setValueAt(datos[c].get("COD"),c,0);
                    TblProveedores.setValueAt(datos[c].get("TIPO"),c,1);                    
                    TblProveedores.setValueAt(datos[c].get("CLASE"),c,2);
                    TblProveedores.setValueAt(datos[c].get("NOMBRE"),c,3);
                    TblProveedores.setValueAt(datos[c].get("NC"),c,4);
                    TblProveedores.setValueAt(datos[c].get("TEL"),c,5);
                    TblProveedores.setValueAt(datos[c].get("DIR"),c,6);
                    //TblProveedores.setValueAt(datos[c].get("SERVICIO"),c,7);
                    //TblProveedores.setValueAt(datos[c].get("DIRECCION"),c,7);
                    //TblProveedores.setValueAt(datos[c].get("TELEFONO"),c,8);
                    //TblProveedores.setValueAt(datos[c].get("FAX"),c,9);
                    //TblProveedores.setValueAt(datos[c].get("NIT"),c,10);
                    //TblProveedores.setValueAt(datos[c].get("ESTADO"),c,11);
                }
            }
            else
            {
                System.out.println("sin datos :p");
            }
            }
            catch (Exception e){
                javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            } 
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BtnGrpHabilitado;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JButton Btneliminar;
    private javax.swing.JScrollPane PnlProveedores;
    private javax.swing.JScrollPane PnlProveedores1;
    private javax.swing.JRadioButton RadHabilitado;
    private javax.swing.JRadioButton RadInhabilitado;
    private javax.swing.JTable TblProveedores;
    private javax.swing.JTable TblProveedores1;
    private javax.swing.JTextField TxtCasaComercial;
    private javax.swing.JTextField TxtDireccion;
    private javax.swing.JTextField TxtFax;
    private javax.swing.JTextField TxtGranActividad;
    private javax.swing.JTextField TxtNit;
    private javax.swing.JTextField TxtPartida;
    private javax.swing.JTextField TxtRazonNit;
    private javax.swing.JTextField TxtServicio;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JTextField TxtWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
