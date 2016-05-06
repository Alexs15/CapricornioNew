/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import com.caucho.hessian.client.HessianProxyFactory;
import java.awt.event.KeyEvent;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Pedido;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.tablas.TablaOrdenes;
import umsa.capricornio.gui.transacciones.tablas.TablaPedidos;
import umsa.capricornio.gui.transacciones.tablas.TablaTransaccionBandejaGral;
import umsa.capricornio.utilitarios.herramientas.MiRenderer; 
import javax.swing.JFileChooser;
import java.net.URLConnection;
import java.net.MalformedURLException;
import umsa.capricornio.gui.transacciones.tablas.TablaDocumentos;
import java.net.URL;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import umsa.capricornio.service.HelloService;
import umsa.capricornio.utilitarios.herramientas.Archivos;
import umsa.capricornio.gui.transacciones.reporte.GetResoluciones;

/**
 *
 * @author Henrry
 */
public class DiagOrdenesGenera extends javax.swing.JDialog {

    TablaPedidos pedidos;
    TablaOrdenes ordenes;
    TablaTransaccionBandejaGral bandeja;
    JInternalFrame ft;
    FrmMenu menu;
    int cod_transaccion,gestion,cod_w,cod_almacen,cod_usuario,cod_trans_nro,cod_rol,cod_res_ini,num_desiertos;
    String lectura,nombre_archivo,origen, detalle,cuantia;
    private Runtime r;
    private int nro_orden;
    private File rutaArchivo;
    TablaDocumentos documentos;
    
    
    public JPopupMenu popup;
    
    /**
     * Creates new form DiagOrdenesGenera
     */
    public DiagOrdenesGenera(JInternalFrame fft,FrmMenu menu,int gestion,int cod_almacen,int cod_usuario) {
        super(menu, false);
        initComponents();
        this.ft=fft;
        this.menu=menu;        
        this.gestion=gestion;        
        this.cod_almacen=cod_almacen;
        this.cod_usuario=cod_usuario;
        
        
        ConstruyeTablaTransacciones();
        ConstruyeTablaOrdenes();
        ConstruyeTablaPedidos(); 
        //ConstruyeTablaDocumentos();
        try {
            Inicializa();
        } catch (Exception e) {
        }

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
            TblTransaccionBandeja.addColumn(column);
        }
        JTableHeader header = TblTransaccionBandeja.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlTransaccionBandeja.getViewport().add(TblTransaccionBandeja);
    }
        
    private void ConstruyeTablaPedidos(){
        pedidos = new TablaPedidos();
        TblPedido.setAutoCreateColumnsFromModel(false);
        TblPedido.setModel(pedidos);

        for (int k = 0; k < TablaPedidos.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaPedidos.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaPedidos.m_columns[k].m_width, renderer, null);
            TblPedido.addColumn(column);
        }
        JTableHeader header = TblPedido.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlPedido.getViewport().add(TblPedido);
    }
    
    private void ConstruyeTablaOrdenes(){
        ordenes = new TablaOrdenes();
        TblOrdenes.setAutoCreateColumnsFromModel(false);
        TblOrdenes.setModel(ordenes);

        for (int k = 0; k < TablaOrdenes.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaOrdenes.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaOrdenes.m_columns[k].m_width, renderer, null);
            TblOrdenes.addColumn(column);
        }
        JTableHeader header = TblOrdenes.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlOrdenes.getViewport().add(TblOrdenes);
    }
    
    /*private void ConstruyeTablaDocumentos(){
        cod_rol=5;
        origen="ADQ";
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
    public void getCod_res_ini(){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.existeReIni(cod_transaccion);
            if(datos!=null)
                this.cod_res_ini = Integer.parseInt(datos[0].get("COD_RESOLUCION").toString());
            else
                this.cod_res_ini=0;
            
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }
    void AbreItems(){
        this.num_desiertos=0;
        int fila = TblTransaccionBandeja.getSelectedRow();
        cod_transaccion=Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 0).toString());
        cod_trans_nro=Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 2).toString());
        cod_w=Integer.parseInt(TblTransaccionBandeja.getValueAt(fila, 3).toString());
        detalle = TblTransaccionBandeja.getValueAt(fila, 6).toString();
        cuantia = TblTransaccionBandeja.getValueAt(fila, 10).toString().toString();
        //System.out.println("Entro Yeihh -->"+this.cod_transaccion+" - "+this.cod_trans_nro+" - "+this.cod_w);
        
        CerearTablaPedidos();
        CerearTablaOrdenes();
        this.getCod_res_ini();
        //this.cod_res_ini = 0;
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos;
            
            //datos=puerto.existeReIni(cod_transaccion);
            //this.cod_res_ini = Integer.parseInt(datos[0].get("COD_RES_INI").toString());
            //System.out.println("cod_res_ini:"+datos[0].get("COD_RES_INI").toString());
            System.out.println(",,,,,,,,,,,,,,,,,,,, Cod_ trans: "+cod_transaccion);
            datos=puerto.getItems2(cod_transaccion);
            String elemento ="";
            if (datos!=null){
                int n=0;
                for (int c=0;c<datos.length;c++){
                    elemento=datos[c].get("ARTICULO").toString();
//                    if (!"".equals(datos[c].get("TIPO_ITEM"))){                        
                        pedidos.insert(n);
                        TblPedido.tableChanged(new TableModelEvent(pedidos, n, n, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                        TblPedido.setValueAt(datos[c].get("COD_TRANS_DETALLE"),n,0);
                        TblPedido.setValueAt(datos[c].get("ESTADO"),n,1);
                        if(datos[c].get("ESTADO").toString().equals("D"))
                            this.num_desiertos++;
                        TblPedido.setValueAt(elemento,n,2);
                        n++;
//                    }
                }
                System.out.println("El numero de desiertos es --> "+this.num_desiertos);
                BtnRetorno.setEnabled(true);
                JB_adj.setEnabled(true);
                JB_RI.setEnabled(true);
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
                 
        nro_orden=1;
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

    void CerearTablaPedidos(){
        int f = TblPedido.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (pedidos.delete(i)) {
                TblPedido.tableChanged(new TableModelEvent(
                pedidos, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
    
    void CerearTablaOrdenes(){
        int f = TblOrdenes.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (ordenes.delete(i)) {
                TblOrdenes.tableChanged(new TableModelEvent(
                ordenes, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }
    
    public void AbreDialogo(){
        setVisible(true);
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
    
       public static String getExtension(String filename) {
            int index = filename.lastIndexOf('.');
            if (index == -1) {
                  return "";
            } else {
                  return filename.substring(index + 1);
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
        TxtSolicitud = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TxtHojaRuta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtPreventivo = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        PnlTransaccionBandeja = new javax.swing.JScrollPane();
        TblTransaccionBandeja = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BtnRetorno = new javax.swing.JButton();
        JB_RI = new javax.swing.JButton();
        JB_adj = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTA_ObsAdqui = new javax.swing.JTextArea();
        JB_RI1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        PnlPedido = new javax.swing.JScrollPane();
        TblPedido = new javax.swing.JTable();
        BtnDesierto = new javax.swing.JButton();
        BtnAsignar = new javax.swing.JButton();
        BtnDevolver = new javax.swing.JButton();
        BtnCreaOrden = new javax.swing.JButton();
        BtnEliminaOrden = new javax.swing.JButton();
        PnlOrdenes = new javax.swing.JScrollPane();
        TblOrdenes = new javax.swing.JTable();
        BtnGeneraOrdenes = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        BtnAsignarTodo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GENERACION DE ORDENES DE COMPRA");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(205, 215, 224));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Solicitud de compra :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 20, 120, 20);

        TxtSolicitud.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtSolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtSolicitudKeyPressed(evt);
            }
        });
        jPanel1.add(TxtSolicitud);
        TxtSolicitud.setBounds(150, 20, 90, 19);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Hoja Ruta :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(260, 20, 70, 20);

        TxtHojaRuta.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtHojaRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtHojaRutaActionPerformed(evt);
            }
        });
        TxtHojaRuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtHojaRutaKeyPressed(evt);
            }
        });
        jPanel1.add(TxtHojaRuta);
        TxtHojaRuta.setBounds(340, 20, 90, 19);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Preventivo :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(450, 20, 70, 20);

        TxtPreventivo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtPreventivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtPreventivoKeyPressed(evt);
            }
        });
        jPanel1.add(TxtPreventivo);
        TxtPreventivo.setBounds(530, 20, 90, 19);

        BtnBuscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(0, 51, 102));
        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnBuscar.setText("Buscar");
        BtnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnBuscar);
        BtnBuscar.setBounds(730, 20, 100, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 960, 60);

        TblTransaccionBandeja.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
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

        getContentPane().add(PnlTransaccionBandeja);
        PnlTransaccionBandeja.setBounds(10, 70, 960, 160);

        jPanel2.setBackground(new java.awt.Color(185, 203, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnRetorno.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnRetorno.setForeground(new java.awt.Color(0, 102, 102));
        BtnRetorno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnRetorno.setText("Devolver a Presupuesto");
        BtnRetorno.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnRetorno.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRetornoActionPerformed(evt);
            }
        });
        jPanel2.add(BtnRetorno);
        BtnRetorno.setBounds(150, 80, 190, 26);

        JB_RI.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JB_RI.setForeground(new java.awt.Color(0, 102, 102));
        JB_RI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/bookmark_document.png"))); // NOI18N
        JB_RI.setText("Genera Resolucion de Inicio");
        JB_RI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        JB_RI.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        JB_RI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_RIActionPerformed(evt);
            }
        });
        jPanel2.add(JB_RI);
        JB_RI.setBounds(710, 10, 240, 26);

        JB_adj.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JB_adj.setForeground(new java.awt.Color(0, 102, 102));
        JB_adj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/attach_2.png"))); // NOI18N
        JB_adj.setText("Archivos Adjuntos");
        JB_adj.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        JB_adj.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        JB_adj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_adjActionPerformed(evt);
            }
        });
        jPanel2.add(JB_adj);
        JB_adj.setBounds(710, 90, 240, 26);

        JTA_ObsAdqui.setColumns(20);
        JTA_ObsAdqui.setRows(5);
        JTA_ObsAdqui.setBorder(javax.swing.BorderFactory.createTitledBorder("Observacion"));
        jScrollPane2.setViewportView(JTA_ObsAdqui);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(30, 10, 410, 60);

        JB_RI1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JB_RI1.setForeground(new java.awt.Color(0, 102, 102));
        JB_RI1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/photo_album.png"))); // NOI18N
        JB_RI1.setText("Generar Invitaciones");
        JB_RI1.setToolTipText("");
        JB_RI1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        JB_RI1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        JB_RI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB_RI1ActionPerformed(evt);
            }
        });
        jPanel2.add(JB_RI1);
        JB_RI1.setBounds(710, 40, 240, 26);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(690, 0, 10, 130);
        jPanel2.add(jSeparator2);
        jSeparator2.setBounds(690, 70, 269, 10);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 490, 960, 130);

        jPanel3.setBackground(new java.awt.Color(185, 203, 221));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        PnlPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        TblPedido.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TblPedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PnlPedido.setViewportView(TblPedido);

        jPanel3.add(PnlPedido);
        PnlPedido.setBounds(20, 20, 380, 180);

        BtnDesierto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnDesierto.setForeground(new java.awt.Color(204, 0, 0));
        BtnDesierto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnDesierto.setText("Declarar Desierto");
        BtnDesierto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDesiertoActionPerformed(evt);
            }
        });
        jPanel3.add(BtnDesierto);
        BtnDesierto.setBounds(50, 210, 160, 26);

        BtnAsignar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnAsignar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_next.png"))); // NOI18N
        BtnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsignarActionPerformed(evt);
            }
        });
        jPanel3.add(BtnAsignar);
        BtnAsignar.setBounds(450, 50, 50, 26);

        BtnDevolver.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnDevolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_previous.png"))); // NOI18N
        BtnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDevolverActionPerformed(evt);
            }
        });
        jPanel3.add(BtnDevolver);
        BtnDevolver.setBounds(450, 80, 50, 26);

        BtnCreaOrden.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnCreaOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/folder_add.png"))); // NOI18N
        BtnCreaOrden.setToolTipText("Crea Orden de Compra");
        BtnCreaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCreaOrdenActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCreaOrden);
        BtnCreaOrden.setBounds(410, 10, 40, 30);

        BtnEliminaOrden.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminaOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/folder_delete.png"))); // NOI18N
        BtnEliminaOrden.setToolTipText("Elimina Orden de Compra");
        BtnEliminaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminaOrdenActionPerformed(evt);
            }
        });
        jPanel3.add(BtnEliminaOrden);
        BtnEliminaOrden.setBounds(500, 10, 40, 30);

        PnlOrdenes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordenes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        TblOrdenes.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TblOrdenes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PnlOrdenes.setViewportView(TblOrdenes);

        jPanel3.add(PnlOrdenes);
        PnlOrdenes.setBounds(550, 20, 380, 180);

        BtnGeneraOrdenes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnGeneraOrdenes.setForeground(new java.awt.Color(0, 51, 51));
        BtnGeneraOrdenes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/accept.png"))); // NOI18N
        BtnGeneraOrdenes.setText("Generar Orden(es)");
        BtnGeneraOrdenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGeneraOrdenesActionPerformed(evt);
            }
        });
        jPanel3.add(BtnGeneraOrdenes);
        BtnGeneraOrdenes.setBounds(660, 210, 170, 26);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/trash_16.png"))); // NOI18N
        jButton3.setText("Limpiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);
        jButton3.setBounds(420, 220, 110, 26);

        BtnAsignarTodo.setBackground(new java.awt.Color(51, 51, 51));
        BtnAsignarTodo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnAsignarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/control_fastforward.png"))); // NOI18N
        BtnAsignarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsignarTodoActionPerformed(evt);
            }
        });
        jPanel3.add(BtnAsignarTodo);
        BtnAsignarTodo.setBounds(450, 110, 50, 26);

        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setText("informe de la comision");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(230, 210, 170, 25);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 230, 960, 260);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/resultset_first.png"))); // NOI18N
        BtnSalir.setText("SALIR");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir);
        BtnSalir.setBounds(450, 620, 110, 26);

        setSize(new java.awt.Dimension(995, 688));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializa() throws RemoteException{
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //Map[] datos = puerto.getTransaccionesBuscaAdquisicion(cod_almacen, cod_usuario, 1, gestion, solicitud, hoja_ruta, preventivo);
            System.out.println(":D --> cod_almacen: "+cod_almacen+" cod_usuario: "+cod_usuario+" cod_almacen: "+cod_almacen+" gestion: "+gestion);
            Map[] datos =puerto.getTransaccionesBuscaAdquisicionTODO2(cod_almacen, cod_usuario, 1, gestion);
            CerearTablaBandeja();
            CerearTablaPedidos();
            CerearTablaOrdenes();
            //CerearTablaDocumentos();
            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    bandeja.insert(c);
                    System.out.println("esta entrando aqui");
                    TblTransaccionBandeja.tableChanged(new TableModelEvent(bandeja, c, c, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANSACCION"), c, 0);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_ESTADO"), c, 1);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANS_NRO"), c, 2);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_W"), c, 3);
                    TblTransaccionBandeja.setValueAt(datos[c].get("NRO_TRAMITE"), c, 4);
                    TblTransaccionBandeja.setValueAt(datos[c].get("TIPO_TRAMITE"), c, 5);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DETALLE"), c, 6);
                    //System.out.println("El detalle seria --> "+datos[c].get("DETALLE"));
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_SOL"), c, 7);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_DES"), c, 8);
                    TblTransaccionBandeja.setValueAt(datos[c].get("ESTADO"), c, 9);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_CUANTIA"), c, 10);
                    
                }
                BtnRetorno.setEnabled(false);
                JB_adj.setEnabled(false);
                JB_RI.setEnabled(false);
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
       
    }
    private void TxtSolicitudKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSolicitudKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtSolicitudKeyPressed

    private void TxtHojaRutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtHojaRutaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtHojaRutaKeyPressed

    private void TxtPreventivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPreventivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtPreventivoKeyPressed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        boolean sw=false;
        String solicitud = null, hoja_ruta = null, preventivo = null;
        if (!"".equals(TxtSolicitud.getText())) {
            try {
                int n = new Integer(TxtSolicitud.getText());
                solicitud = "'" + TxtSolicitud.getText() + "'";
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "La solicitud debe ser un numero entero \n" + e, "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            sw=true;
        }
        if (!"".equals(TxtHojaRuta.getText())) {
            hoja_ruta = "'" + TxtHojaRuta.getText() + "'";
            sw=true;
        }

        if (!"".equals(TxtPreventivo.getText())) {
            preventivo = "'" + TxtPreventivo.getText() + "'";
            sw=true;
        }

        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos;
            if(sw)
                datos = puerto.getTransaccionesBuscaAdquisicion(cod_almacen, cod_usuario, 1, gestion, solicitud, hoja_ruta, preventivo);
            else
                datos = puerto.getTransaccionesBuscaAdquisicionTODO(cod_almacen, cod_usuario, 1, gestion);
            
            CerearTablaBandeja();
            CerearTablaPedidos();
            CerearTablaOrdenes();
            //CerearTablaDocumentos();
            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    bandeja.insert(c);
                    TblTransaccionBandeja.tableChanged(new TableModelEvent(bandeja, c, c, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANSACCION"), c, 0);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_ESTADO"), c, 1);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_TRANS_NRO"), c, 2);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_W"), c, 3);
                    TblTransaccionBandeja.setValueAt(datos[c].get("NRO_TRAMITE"), c, 4);
                    TblTransaccionBandeja.setValueAt(datos[c].get("TIPO_TRAMITE"), c, 5);
                    TblTransaccionBandeja.setValueAt(datos[c].get("DETALLE"), c, 6);
                    //System.out.println("El detalle seria --> "+datos[c].get("DETALLE"));
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_SOL"), c, 7);
                    TblTransaccionBandeja.setValueAt(datos[c].get("UNIDAD_DES"), c, 8);
                    TblTransaccionBandeja.setValueAt(datos[c].get("ESTADO"), c, 9);
                    TblTransaccionBandeja.setValueAt(datos[c].get("COD_CUANTIA"), c, 10);
                    
                }
                BtnRetorno.setEnabled(false);
                JB_adj.setEnabled(false);
                JB_RI.setEnabled(false);
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        TxtHojaRuta.setText("");
        TxtPreventivo.setText("");
        TxtSolicitud.setText("");
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void TblTransaccionBandejaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaMousePressed
        if (evt.getClickCount() == 2) {
            AbreItems();
            //LlenaDocumentos();
        }
    }//GEN-LAST:event_TblTransaccionBandejaMousePressed

    private void TblTransaccionBandejaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblTransaccionBandejaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AbreItems();
            //LlenaDocumentos();
        }
    }//GEN-LAST:event_TblTransaccionBandejaKeyPressed

    private void BtnRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRetornoActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea rertornar esta TRANSACCION?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        String obs_adqui = null;
        if (!"".equals(JTA_ObsAdqui.getText().trim())) {
            obs_adqui = "'" + JTA_ObsAdqui.getText().trim() + "'";
        }
        /*
         * boolean sw=false; for (int f=0;f<TblPedido.getRowCount();f++)
         * if("D".equals(TblPedido.getValueAt(f, 1).toString())){ sw=true;
         * break; } if (!sw) {
         * javax.swing.JOptionPane.showMessageDialog(this,"Ninguno de los items
         * quedó DESIERTA\nNO se devolvera el tramite a PRESUPUESTOS","SYSTEM
         * CAPRICORN", javax.swing.JOptionPane.ERROR_MESSAGE); return;
        }
         */
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = null;
            puerto.setActualizaTransaccionObsAdqui(cod_transaccion, obs_adqui);

            for (int f = 0; f < TblPedido.getRowCount(); f++) {
                if (!"D".equals(TblPedido.getValueAt(f, 1).toString())) {
                    datos = puerto.setTransaccionesOrigen("SET-upDateOrig", Integer.parseInt(TblPedido.getValueAt(f, 0).toString()), cod_w, TblPedido.getValueAt(f, 1).toString());
                }
            }

            javax.swing.JOptionPane.showMessageDialog(this, "LA TRANSACCION SE RETORNO A LA UNIDAD ANTERIOR", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            BtnSalir.doClick();
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe elegir una fila de la bandeja de salida para enviar el memorandum", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnRetornoActionPerformed

    private void BtnDesiertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDesiertoActionPerformed
        //JDInformeComision x = new JDInformeComision(menu, false, this.cod_w, this.cod_trans_nro, this.proveedor.getCasa_comercial(), this.detalle, this.cod_transaccion,this.gestion);
        JD_reporte_desierta x=new JD_reporte_desierta(menu, false, cod_w, cod_trans_nro, detalle, cod_transaccion, gestion);
        //JD_reporte desierta x=new JD_reporte_desierta(menu, false, this.cod_w, this.cod_trans_nro, this.detalle, this.cod_transaccion,this.gestion);
        //this.setModal(false);
        //x.setVisible(true);
        int n = TblPedido.getSelectedRow();
        if ("D".equals(TblPedido.getValueAt(n, 1))) {
            javax.swing.JOptionPane.showMessageDialog(this, "Items ya fue declarado DESIERTO", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            this.setModal(false);
            x.setVisible(true);
            return;
        }
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea declarar DESIERTA el item?",
                "SYSTEM CAPRICORN", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        try {
            int cod_trans_detalle = Integer.parseInt(TblPedido.getValueAt(n, 0).toString());
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //JD_reporte_desierta x=new JD_reporte_desierta(menu, false, cod_w, cod_trans_nro, detalle, cod_transaccion, gestion);
            //JD_reporte desierta x=new JD_reporte_desierta(menu, false, this.cod_w, this.cod_trans_nro, this.detalle, this.cod_transaccion,this.gestion);
            this.setModal(false);
            x.setVisible(true);
            Map[] datos = puerto.setItemRegDesierto("SET-upDateItRDes", cod_trans_detalle);
            javax.swing.JOptionPane.showMessageDialog(this, "ITEM FUE DECLARADA DESIERTA", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            AbreItems();
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe elejir un item para declararlo DESIERTO", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnDesiertoActionPerformed

    private void BtnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsignarActionPerformed
        
        System.out.println("pedidos tiene elementos --> "+TblPedido.getRowCount());
        
        Pedido pedido = new Pedido();

        int n = TblPedido.getSelectedRow();

        if ("D".equals(TblPedido.getValueAt(n, 1))) {
            javax.swing.JOptionPane.showMessageDialog(this, "Items declarado DESIERTO no puede pertenecer a una ORDEN DE COMPRA", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            pedido.setCod_trans_detalle(Integer.parseInt(TblPedido.getValueAt(n, 0).toString()));
            pedido.setCod_estado(TblPedido.getValueAt(n, 1).toString());
            pedido.setArticulo(TblPedido.getValueAt(n, 2).toString());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No elijio ningun articulo del PEDIDO", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean sw = false;
        for (int f = TblOrdenes.getRowCount() - 1; f >= 0; f--) {
            if (!"".equals(TblOrdenes.getValueAt(f, 2))) {
                sw = true;
                break;
            }
        }
        if (sw) {
            int nf = TblOrdenes.getSelectedRow();
            if (nf == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe Elejir una Orden de Compra a asignar", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            nf++;
            ordenes.insert(nf);
            TblOrdenes.tableChanged(new TableModelEvent(ordenes, nf, nf, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            TblOrdenes.setValueAt(pedido.getCod_trans_detalle(), nf, 0);
            TblOrdenes.setValueAt(pedido.getCod_estado(), nf, 1);
            TblOrdenes.setValueAt(pedido.getArticulo(), nf, 3);

            if (pedidos.delete(n)) {
                TblPedido.tableChanged(new TableModelEvent(
                        pedidos, n, n, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe Tener Por lo menos una Orden de compra Creada", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAsignarActionPerformed

    private void BtnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDevolverActionPerformed
        Pedido orden = new Pedido();
        int n = TblOrdenes.getSelectedRow();
        if ("".equals(TblOrdenes.getValueAt(n, 2))) {
            orden.setCod_trans_detalle(Integer.parseInt(TblOrdenes.getValueAt(n, 0).toString()));
            orden.setCod_estado(TblOrdenes.getValueAt(n, 1).toString());
            orden.setArticulo(TblOrdenes.getValueAt(n, 3).toString());

            int nf = TblPedido.getRowCount();
            pedidos.insert(nf);
            TblPedido.tableChanged(new TableModelEvent(pedidos, nf, nf, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            TblPedido.setValueAt(orden.getCod_trans_detalle(), nf, 0);
            TblPedido.setValueAt(orden.getCod_estado(), nf, 1);
            TblPedido.setValueAt(orden.getArticulo(), nf, 2);

            if (ordenes.delete(n)) {
                TblOrdenes.tableChanged(new TableModelEvent(
                        ordenes, n, n, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Solo puede devolver los items de las ordenes", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnDevolverActionPerformed

    private void BtnCreaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCreaOrdenActionPerformed
        int n = TblOrdenes.getRowCount();
        ordenes.insert(n);
        TblOrdenes.tableChanged(new TableModelEvent(ordenes, n, n, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        TblOrdenes.setValueAt("Orden - " + nro_orden, n, 2);
        nro_orden++;
    }//GEN-LAST:event_BtnCreaOrdenActionPerformed

    private void BtnEliminaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminaOrdenActionPerformed
        int i = TblOrdenes.getSelectedRow();
        if (!"".equals(TblOrdenes.getValueAt(i, 2))) {
            boolean sw = true;
            for (int f = i + 1; f < TblOrdenes.getRowCount(); f++) {
                if ("".equals(TblOrdenes.getValueAt(f, 2))) {
                    sw = false;
                    break;
                }
            }
            if (sw) {
                if (ordenes.delete(i)) {
                    TblOrdenes.tableChanged(new TableModelEvent(ordenes, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No puede eliminar una ORDEN si contiene items asignados", "SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Solo Se pueden Eliminar las Ordenes que no contengan los items", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnEliminaOrdenActionPerformed

    private void BtnGeneraOrdenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGeneraOrdenesActionPerformed
        
        this.getCod_res_ini();
        System.out.println("tamanio --> "+TblPedido.getRowCount());
        int numFilas=TblPedido.getRowCount()-this.num_desiertos;
        if(this.cod_transaccion==0){
           javax.swing.JOptionPane.showMessageDialog(this, "Usted no selecciono ninguna transaccion", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return; 
        }
        if(numFilas!=0){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe procesar todos los items, antes de generar las ordenes de compra", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("La cuantia es --> "+Integer.valueOf(this.cuantia));
        if(this.cod_res_ini==0 && Integer.valueOf(this.cuantia)!=1){
            javax.swing.JOptionPane.showMessageDialog(this, "Todavia no se ha generado la resolución de Inicio", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;   
        }
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea avanzar esta TRANSACCION?",
                "MENSAJE CAPRICORNIO", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = null;
            int cod_trans_nro_nuevo = 0;
            for (int f = 0; f < TblOrdenes.getRowCount(); f++) {
                if (!"".equals(TblOrdenes.getValueAt(f, 2).toString())) {
                    datos = puerto.setCreaNroTramite("SET-upDateGeneraTramite", cod_transaccion, cod_almacen, 2, gestion, cod_trans_nro,cod_usuario);
                    if (datos != null) {
                        cod_trans_nro_nuevo = Integer.parseInt(datos[0].get("COD_TRANS_NRO").toString());
                        System.out.println(cod_trans_nro);
                    }
                } else {
                    datos = puerto.setTransaccionDetalleNro("SET-upDateTransDetNro", Integer.parseInt(TblOrdenes.getValueAt(f, 0).toString()), cod_trans_nro_nuevo);
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Ordenes de Compra Generados", "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            BtnSalir.doClick();
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_BtnGeneraOrdenesActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        dispose();
        ft.setVisible(true);
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //BtnRetorno.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void JB_RIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_RIActionPerformed
        // TODO add your handling code here:
        this.setModal(false);
        //System.out.println("Lo veo todo");
        //System.out.println("Wolasss");
        //GetResoluciones x = new  GetResoluciones();
        //x.Reporte(":D");
        //System.out.println("Wolass2");
        int valor_cuantia=0;
        try {
            valor_cuantia = Integer.parseInt(this.cuantia);
        } catch (Exception e) {
            valor_cuantia =0;
        }
       
        if(this.cod_transaccion!=0 && (valor_cuantia>1 && valor_cuantia<5)){
            switch(this.cod_w) {
                case 1: 
                    System.out.println("Solicitud de compra");
                    break;
                case 3: 
                    System.out.println("Consultorias");
                    break;
                case 4: 
                    System.out.println("Obras");
                    break;
            }
                    
            System.out.println("Entro Yeihh -->"+this.cod_transaccion+" - "+this.cod_trans_nro+" - "+this.cod_w);
            /*
            try {
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();

                //System.out.println("cod_trans_nro: "+cod_trans_nro+"cod_prov:xxx detalle:"+detalle);
                puerto.generaResIni(cod_transaccion, detalle);
            } catch (Exception e) {
            }
            */
            ResolucionInicio x = new ResolucionInicio(this.detalle, this.cod_transaccion, this.cod_w, this.cod_almacen,this.menu,this.cod_trans_nro,this.gestion);
            x.setVisible(true);
            
            System.out.println("Se cerro ups :P");
        }else{
            //System.out.println("Usted no a seleccionado ninguna Solicitud");
            JOptionPane.showMessageDialog(null, "Usted no a seleccionado ninguna Solicitud\n O se trata de una Solicitud de Compra Menor", "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_JB_RIActionPerformed

    private void TxtHojaRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtHojaRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtHojaRutaActionPerformed

    private void JB_adjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_adjActionPerformed
        // TODO add your handling code here:
        if(this.cod_transaccion!=0){
            JDAdjuntos JDA = new JDAdjuntos(menu,true,cod_transaccion,5);
            JDA.AbreDialogo();
        }else{
            JOptionPane.showMessageDialog(null, "Usted no a seleccionado ninguna Solicitud", "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_JB_adjActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.CerearTablaPedidos();
        this.CerearTablaOrdenes();
        this.cod_transaccion=0;
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void BtnAsignarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsignarTodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAsignarTodoActionPerformed

    private void JB_RI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JB_RI1ActionPerformed
        // TODO add your handling code here:
        System.out.println("wujuuuuuu");
        ProponentesDialog11 x = new ProponentesDialog11(this,this.menu, false, this.cod_transaccion, this.cod_trans_nro,cod_w);
        x.setLocationRelativeTo(null);
        x.setVisible(true);
    }//GEN-LAST:event_JB_RI1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JDInformeComision x = new JDInformeComision(menu, false, this.cod_w, this.cod_trans_nro, null, this.detalle, this.cod_transaccion,this.gestion);
        this.setModal(false);
        x.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAsignar;
    private javax.swing.JButton BtnAsignarTodo;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCreaOrden;
    private javax.swing.JButton BtnDesierto;
    private javax.swing.JButton BtnDevolver;
    private javax.swing.JButton BtnEliminaOrden;
    private javax.swing.JButton BtnGeneraOrdenes;
    private javax.swing.JButton BtnRetorno;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JButton JB_RI;
    private javax.swing.JButton JB_RI1;
    private javax.swing.JButton JB_adj;
    private javax.swing.JTextArea JTA_ObsAdqui;
    private javax.swing.JScrollPane PnlOrdenes;
    private javax.swing.JScrollPane PnlPedido;
    private javax.swing.JScrollPane PnlTransaccionBandeja;
    private javax.swing.JTable TblOrdenes;
    private javax.swing.JTable TblPedido;
    private javax.swing.JTable TblTransaccionBandeja;
    private javax.swing.JTextField TxtHojaRuta;
    private javax.swing.JTextField TxtPreventivo;
    private javax.swing.JTextField TxtSolicitud;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
