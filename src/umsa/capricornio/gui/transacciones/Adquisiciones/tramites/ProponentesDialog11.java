/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Proveedor;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.DiagBuscaProveedor;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.TablaProponentesBusca;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.NewRenderJTproponentes;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;
/**
 *
 * @author UMSA-JES
 */
public class ProponentesDialog11 extends javax.swing.JDialog {
    
    private int nroFilas=0;
    public Proveedor proveedor=null;
    public Proveedor proveedor_adj=null;
    private TablaProponentesBusca tblProv;
    FrmMenu menu; 
    private int cod_transaccion;
    private int cod_trans_nro;
    DiagBuscaProveedor prov;
    private String tprov;
    private String tbche;
    public JFrame parent;
    public int cod_w;

    /**
     * Creates new form ProponentesDialog
     */
    public ProponentesDialog11(DiagOrdenesGenera c,java.awt.Frame parent, boolean modal, int cod_transaccion, int cod_trans_nro,int cod_w) {
        //this.menu=parent;
        super(c, false);
        initComponents();
        this.parent=(JFrame) parent;
        this.setLocationRelativeTo(null);
        this.ConstruyeTablaProp();
        this.cod_trans_nro=cod_trans_nro;
        this.cod_transaccion=cod_transaccion;
        this.cod_w=cod_w;
        System.out.println("======================= importante --> cod_transaccion: "+this.cod_transaccion+" cod_trans_nro: "+this.cod_trans_nro);
        LlenaProponentes();
        setOcultarColumnasJTable(new int[]{1,2});
    }
    private void setOcultarColumnasJTable(int columna[])
    {
        System.out.println("preciso meu irmao");
        for(int i=0;i<columna.length;i++)
        {
             TablaProponentes.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             TablaProponentes.getColumnModel().getColumn(columna[i]).setMinWidth(0);
             TablaProponentes.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             TablaProponentes.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
        }
    }
    void CerearTabla(){
        int f = TablaProponentes.getRowCount();
        for (int i=f;i>=0;i--){
             if (tblProv.delete(i)) {
                TablaProponentes.tableChanged(new TableModelEvent(
                tblProv, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT)); 
             }
        }
    }
    private void LlenaProponentes(){
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getProponentes(this.cod_transaccion, this.cod_trans_nro);
            //CerearTablaItems
            //NuevoRender();
            if (datos!=null){
                System.out.println("Entro llena proponentes...");
                for (int c=this.nroFilas;c<datos.length;c++){
                    
                    tblProv.insert(c);
                    TablaProponentes.tableChanged(new TableModelEvent(tblProv, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TablaProponentes.setValueAt(datos[c].get("COD_PROVEEDOR"),c,0);
                    TablaProponentes.setValueAt(datos[c].get("TIPO"),c,1);                    
                    TablaProponentes.setValueAt(datos[c].get("CLASE"),c,2);                    
                    TablaProponentes.setValueAt(datos[c].get("NOMBRE"),c,3);
                    TablaProponentes.setValueAt(datos[c].get("NOMBRE_COMERCIAL"),c,4);
                    TablaProponentes.setValueAt(datos[c].get("TELEFONO"),c,6);
                    TablaProponentes.setValueAt(datos[c].get("DIRECCION"),c,5);
                    TablaProponentes.setValueAt(datos[c].get("ESTADO"),c,7);
                    ///
                }
            }
            
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }
    private void ConstruyeTablaProp(){
        tblProv = new TablaProponentesBusca();
        this.TablaProponentes.setAutoCreateColumnsFromModel(false);
        this.TablaProponentes.setModel(tblProv);
        
        for (int k = 0; k < TablaProponentesBusca.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaProponentesBusca.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaProponentesBusca.m_columns[k].m_width, renderer, null);
            TablaProponentes.addColumn(column);
        }
        
        
        JTableHeader header = TablaProponentes.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PanelProponentes.getViewport().add(TablaProponentes);
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
        PanelProponentes = new javax.swing.JScrollPane();
        TablaProponentes = new javax.swing.JTable();
        BtnBuscaProveedor = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(185, 203, 221));

        PanelProponentes.setViewportView(TablaProponentes);

        BtnBuscaProveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnBuscaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnBuscaProveedor.setText("Buscar");
        BtnBuscaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscaProveedorActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/accept.png"))); // NOI18N
        jButton1.setText("Generar Invitacion");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/cancel.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Listado de Invitados");

        jLabel2.setForeground(new java.awt.Color(51, 0, 204));
        jLabel2.setText("Ingrese Nombre");

        jLabel3.setForeground(new java.awt.Color(51, 0, 204));
        jLabel3.setText("Casa Comercial");

        jLabel4.setForeground(new java.awt.Color(51, 0, 204));
        jLabel4.setText("Direccion");

        jLabel5.setForeground(new java.awt.Color(51, 0, 204));
        jLabel5.setText("Telefono");

        jLabel6.setForeground(new java.awt.Color(51, 0, 204));
        jLabel6.setText("NIT");

        jButton4.setForeground(new java.awt.Color(0, 0, 204));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        jButton4.setText("REGISTRAR E INVITAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(PanelProponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(306, 306, 306)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(315, 315, 315)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jButton1)
                                        .addGap(38, 38, 38)
                                        .addComponent(BtnBuscaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelProponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnBuscaProveedor)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnBuscaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscaProveedorActionPerformed
        prov = new DiagBuscaProveedor(menu);
        prov.AbreDialogo();
        //JRadioButton male = new JRadioButton("male");
        //JRadioButton female = new JRadioButton("Female");
        //ButtonGroup bG = new ButtonGroup();
        //bG.add(male);
        //bG.add(female);
        
        if (prov.ProveedorElejido().getCod_proveedor()!=null){
            
            proveedor= prov.ProveedorElejido();
            
            //TxtCasa.setText(proveedor.getCasa_comercial());
            //TablaProponentes.setValueAt(proveedor.getCasa_comercial(), 0, 0);
            try {
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
//                System.out.println("la transaccion tiene como -->  cod_transaccion: "+this.cod_transaccion+" cod_trans_nro: "+this.cod_trans_nro);
//                System.out.println("1 -- "+String.valueOf(proveedor.getCod_proveedor()));
//                System.out.println("2 -- "+this.cod_transaccion);
//                System.out.println("3 -- "+this.cod_trans_nro);
//                System.out.println("4 -- "+this.proveedor.getTipo_cod());
//                System.out.println("5 -- ");
//                System.out.println("6 -- ");
//                System.out.println("7 -- ");
                System.out.println("**************** cod_proveedor: "+proveedor.getCod_proveedor());
                System.out.println("**************** cod_transaccion: "+this.cod_transaccion);
                System.out.println("**************** cod_trans_nro: "+this.cod_trans_nro);
               
                System.out.println("**************** tipo_cod: "+this.proveedor.getTipo_cod());
                System.out.println("**************** clase: "+this.proveedor.getClase());
                System.out.println("**************** nombre: "+this.proveedor.getNombre());
                System.out.println("**************** casa_comercial: "+this.proveedor.getCasa_comercial());
                System.out.println("**************** direccion: "+this.proveedor.getDireccion());
                System.out.println("**************** telefono: "+this.proveedor.getTelefono());
                System.out.println("**************** adh_nombre: "+this.proveedor.getAdh_nombre());
                
                
                
                Map[] datos=puerto.addProponente11(String.valueOf(proveedor.getCod_proveedor()),this.cod_transaccion ,this.cod_trans_nro,this.proveedor.getTipo_cod(),this.proveedor.getClase(),this.proveedor.getNombre().replace("'", ""),this.proveedor.getCasa_comercial().replace("'", ""),this.proveedor.getDireccion(),this.proveedor.getTelefono(),this.proveedor.getAdh_nombre(),2);
                //Map[] datos=puerto.getProponentes(20);
                //System.out.println("Tamaño "+datos.length);
                //puerto.setGestion("SET-insDataGes", 2016);
                System.out.println("EXITO");
               
                tblProv.insert(this.nroFilas);
                TablaProponentes.tableChanged(new TableModelEvent(tblProv, this.nroFilas, this.nroFilas, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                TablaProponentes.setValueAt(proveedor.getCod_proveedor(),this.nroFilas,0);
                TablaProponentes.setValueAt(proveedor.getTipo_cod(),this.nroFilas,1);
                TablaProponentes.setValueAt(proveedor.getClase(),this.nroFilas,2);
                TablaProponentes.setValueAt(proveedor.getNombre(),this.nroFilas,3);
                TablaProponentes.setValueAt(proveedor.getCasa_comercial(),this.nroFilas,4);
                TablaProponentes.setValueAt(proveedor.getDireccion(),this.nroFilas,5);
                TablaProponentes.setValueAt(proveedor.getTelefono(),this.nroFilas,6);
                TablaProponentes.setValueAt(proveedor.getEstado(),this.nroFilas,7);
                //TablaProponentes.setValueAt(male,0,3);
                System.out.println("Changos :D");
                this.nroFilas++;
            } catch (Exception e) {
                System.out.println("ERROR --> "+e);
            }
            
        }
            

    }//GEN-LAST:event_BtnBuscaProveedorActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    public Proveedor ProveedorElejido(){
        return proveedor;
    }
    public Proveedor ProveedorAdj(){
        return proveedor_adj;
    }
    public void NuevoRender(){
        System.out.println("Tratando de cambiar de Render");
        TablaProponentes.setDefaultRenderer(Object.class, new NewRenderJTproponentes());
    }
    private void eliminaprop_tabla(int fila)
    {
        String vt1[],vt2[];
        String n1="",n2="";
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos =puerto.getResAdjObra(cod_trans_nro);
            if(datos!=null)
            {
                this.tprov=datos[0].get("NUM_INF_HP").toString();
                this.tbche=datos[0].get("CONVOCATORIA").toString();
                vt1=tprov.split(",");
                vt2=tbche.split(",");
                int f=fila+1;
                for(int i=1;i<=vt1.length-1;i++)
                {
                    if(i!=f)
                    {
                        //n1=n1+","+vt1[i];
                        System.out.println("esto si sale: "+vt1[i]);
                    }
                    else
                    {
                        System.out.println("esto no sale: "+vt1[i]);
                        f=f+1;
                    }
                }
                for(int i=1;i<=vt2.length-1;i++)
                {
                    if(i!=f)
                    {
                        //n2=n2+","+vt2[i];
                        System.out.println("esto si sale en chaeck: "+vt2[i]);
                    }
                }
                puerto.updatetabla1(n1,n2,this.cod_trans_nro);
            }
        } catch (Exception e) {
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        int fila = TablaProponentes.getSelectedRow();
        
        if (fila<0)
            return;    
        String documento=String.valueOf(TablaProponentes.getValueAt(fila,0));
        
        int dialogResult = javax.swing.JOptionPane.showConfirmDialog (this, "¿Esta seguro(a) de eliminar el proponente con \n CODIGO: "+documento+"","ADJUDICAR ORDEN DE COMPRA",javax.swing.JOptionPane.YES_NO_OPTION);
        if(dialogResult == javax.swing.JOptionPane.YES_OPTION){
            //System.out.println("codigo del elegido dentro: "+this.ProveedorElejido().getNombre());
            System.out.println("Okey vaaa :D");
            try {
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                eliminaprop_tabla(fila);
                puerto.delProponente(documento, this.cod_transaccion, this.cod_trans_nro);
                System.out.println("Hay Caramba :D :D :D :D :D");
                this.setVisible(false);
            } catch (Exception e) {
            }
            this.setVisible(false);
            
        }else
            System.out.println("entonces sea el codigo trans_nro --> ");
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                //Map[] datos=puerto.addProponente11(String.valueOf(proveedor.getCod_proveedor()),this.cod_transaccion ,this.cod_trans_nro,this.proveedor.getTipo_cod(),this.proveedor.getClase(),this.proveedor.getNombre().replace("'", ""),this.proveedor.getCasa_comercial().replace("'", ""),this.proveedor.getDireccion(),this.proveedor.getTelefono(),this.proveedor.getAdh_nombre(),2);
                Map[] datos=puerto.addProponente11(jTextField5.getText(), cod_transaccion, cod_trans_nro, "C", "NATURAL", jTextField1.getText(), jTextField2.getText(), jTextField3.getText(), jTextField4.getText(), jTextField1.getText(), 2);
                CerearTabla();
                LlenaProponentes();
                //Map[] datos=puerto.getProponentes(20);
                //System.out.println("Tamaño "+datos.length);
                //puerto.setGestion("SET-insDataGes", 2016);
                System.out.println("EXITO");
               
                /*tblProv.insert(this.nroFilas);
                TablaProponentes.tableChanged(new TableModelEvent(tblProv, this.nroFilas, this.nroFilas, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                TablaProponentes.setValueAt(proveedor.getCod_proveedor(),this.nroFilas,0);
                TablaProponentes.setValueAt(proveedor.getTipo_cod(),this.nroFilas,1);
                TablaProponentes.setValueAt(proveedor.getClase(),this.nroFilas,2);
                TablaProponentes.setValueAt(proveedor.getNombre(),this.nroFilas,3);
                TablaProponentes.setValueAt(proveedor.getCasa_comercial(),this.nroFilas,4);
                TablaProponentes.setValueAt(proveedor.getDireccion(),this.nroFilas,5);
                TablaProponentes.setValueAt(proveedor.getTelefono(),this.nroFilas,6);
                TablaProponentes.setValueAt(proveedor.getEstado(),this.nroFilas,7);
                //TablaProponentes.setValueAt(male,0,3);
                System.out.println("Changos :D");
                this.nroFilas++;*/
            } catch (Exception e) {
                System.out.println("ERROR --> "+e);
            }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        int fila = TablaProponentes.getSelectedRow();
        if (fila<0)
        return;

        //TablaProponentes.getr
        String documento=String.valueOf(TablaProponentes.getValueAt(fila,0));
        String tipo=String.valueOf(TablaProponentes.getValueAt(fila,1));
        String clase=String.valueOf(TablaProponentes.getValueAt(fila,2));
        String nombre=String.valueOf(TablaProponentes.getValueAt(fila,3));;
        String nombre_comercial=String.valueOf(TablaProponentes.getValueAt(fila,4));
        String direccion=String.valueOf(TablaProponentes.getValueAt(fila,5));
        String telefono=String.valueOf(TablaProponentes.getValueAt(fila,6));
        System.out.println("documento: "+documento+" tipo: "+tipo+" clase: "+clase+" nombre: "+nombre+" nombre_c: "+nombre_comercial+" direccion: "+direccion+" telefono: "+telefono);
        invitacion x=new invitacion(this,this.parent,false,nombre,nombre_comercial,cod_transaccion,cod_trans_nro,cod_w);
        x.setLocationRelativeTo(null);
        x.setVisible(true);
        // proveedor.setCod_proveedor(TablaProponentes.getValueAt(fila,0).toString());
        //this.proveedor = this.ProveedorElejido();

        /*this.proveedor_adj= new Proveedor();
        proveedor_adj.setCod_proveedor(documento);
        proveedor_adj.setTipo_cod(tipo);
        proveedor_adj.setClase(clase);
        proveedor_adj.setNombre(nombre);
        proveedor_adj.setNombre(nombre_comercial);
        proveedor_adj.setDireccion(direccion);
        proveedor_adj.setTelefono(telefono);

        System.out.println("codigo del elegido: "+this.ProveedorAdj().getCod_proveedor());*/
        //System.out.println("Okey: "+this.ProveedorElejido().getCod_proveedor());
        //String dir_email=String.valueOf(TablaProponentes.getValueAt(fila,10));;
        /*String nl = System.getProperty("line.separator");
        javax.swing.JOptionPane.showMessageDialog(this,
            "DOCUMENTO: "+documento +nl+"TIPO DE DOCUMENTO: "+ tipo +nl+"CLASE: "+clase+nl+"NOMBRE: "+nombre+nl+"NOMBRE COMERCIAL: "+nombre_comercial ,
            "¿DESEA ADJUDICAR LA ORDEN AL SIGUIENTE PROVEEDOR?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);*/

        /*int dialogResult = javax.swing.JOptionPane.showConfirmDialog (this, "¿Esta seguro(a) de adjudicar la orden de compra a\n NOMBRE: "+nombre+"\n NOMBRE COMERCIAL: "+nombre_comercial+"\n DOCUMENTO: "+documento+"?","ADJUDICAR ORDEN DE COMPRA",javax.swing.JOptionPane.YES_NO_OPTION);
        if(dialogResult == javax.swing.JOptionPane.YES_OPTION){

            System.out.println("codigo del elegido dentro: "+this.ProveedorAdj().getNombre());
            System.out.println("Okey vaaa :D");
            this.setVisible(false);

        }*/
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProponentesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProponentesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProponentesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProponentesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProponentesDialog dialog = new ProponentesDialog(new javax.swing.JFrame(), true,0,0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscaProveedor;
    private javax.swing.JScrollPane PanelProponentes;
    private javax.swing.JTable TablaProponentes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
