/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSSoapBindingStub;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.reporte.GetResAdj;
import umsa.capricornio.gui.transacciones.tablas.TablaPedidos1;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;

/**
 *
 * @author alex
 */
public class FrmtareasAdicionales extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmReporteUnidad
     */
    TablaPedidos1 pedidos;
    FrmMenu menu;
    int cc=1,tamanio=100;
    int almacenados=0;
    int cod_user, cod_rol,estado,btr;
    int gestion;
    private Runtime r;
    GetResAdj genera_res_15 = new GetResAdj();
    public JTextField jt;
    public JCheckBox jb;
    public JLabel jl;
    public String mes;
    public FrmtareasAdicionales(FrmMenu menu,int cod_user, int cod_rol,int gestion,String mes) {
        this.menu=menu;
        //this.btr=bt;
        this.cod_user=cod_user;
        this.cod_rol=cod_rol;
        this.gestion=gestion;
        //this.mes=mes;
        initComponents();
        ConstruyeTablaPedidos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnGrpReporte = new javax.swing.ButtonGroup();
        BtnReporte = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        BtnReporte2 = new javax.swing.JButton();
        PnlPedido = new javax.swing.JScrollPane();
        TblPedido = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setTitle("MODULO DE TAREAS");

        BtnReporte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnReporte.setForeground(new java.awt.Color(0, 0, 255));
        BtnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/clipboard_text.png"))); // NOI18N
        BtnReporte.setText("AGRGAR");
        BtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReporteActionPerformed(evt);
            }
        });

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 -ENERO", "2 -FEBRERO", "3 -MARZO", "4 -ABRIL", "5 -MAYO", "6 -JUNIO", "7 -JULIO", "8 -AGOSTO", "9 -SEPTIEMBRE", "10 -OCTUBRE", "11 -NOVIEMBRE", "12 -DICIEMBRE" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        BtnReporte2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnReporte2.setForeground(new java.awt.Color(255, 0, 0));
        BtnReporte2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/clipboard_text.png"))); // NOI18N
        BtnReporte2.setText("ELIMINAR");
        BtnReporte2.setToolTipText("");
        BtnReporte2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReporte2ActionPerformed(evt);
            }
        });

        PnlPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TAREAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        TblPedido.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TblPedido.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PnlPedido.setViewportView(TblPedido);

        jLabel1.setText("TAREA NUEVA");

        jButton1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/address_16.png"))); // NOI18N
        jButton1.setText("REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 -ENERO", "2 -FEBRERO", "3 -MARZO", "4 -ABRIL", "5 -MAYO", "6 -JUNIO", "7 -JULIO", "8 -AGOSTO", "9 -SEPTIEMBRE", "10 -OCTUBRE", "11 -NOVIEMBRE", "12 -DICIEMBRE" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 -ENERO", "2 -FEBRERO", "3 -MARZO", "4 -ABRIL", "5 -MAYO", "6 -JUNIO", "7 -JULIO", "8 -AGOSTO", "9 -SEPTIEMBRE", "10 -OCTUBRE", "11 -NOVIEMBRE", "12 -DICIEMBRE" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel2.setText("SELECCIONAR DE QUE MES A QUE MES DESEA VER EL REPORTE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(PnlPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnReporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnReporte2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)))
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(140, 140, 140))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnlPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSalir)
                    .addComponent(BtnReporte)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnReporte2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void llena_tareas()
    {
        CerearTablaPedidos();
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos;
            
            //datos=puerto.existeReIni(cod_transaccion);
            //this.cod_res_ini = Integer.parseInt(datos[0].get("COD_RES_INI").toString());
            //System.out.println("cod_res_ini:"+datos[0].get("COD_RES_INI").toString());
            System.out.println(",,,,,,,,,,,,,,,,,,,, Cod_ trans: "+jComboBox1.getSelectedItem());
            mes=jComboBox1.getSelectedItem().toString();
            datos=puerto.mostrartareas(cod_user, mes, gestion);
            String elemento ="";
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    elemento=datos[c].get("TAREA").toString();
//                    if (!"".equals(datos[c].get("TIPO_ITEM"))){                        
                        pedidos.insert(c);
                        TblPedido.tableChanged(new TableModelEvent(pedidos, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                        TblPedido.setValueAt(datos[c].get("MES"),c,0);
                        TblPedido.setValueAt(datos[c].get("ID"),c,1);
                        TblPedido.setValueAt(elemento,c,2);
//                    }
                }
            }
            else{System.out.println("vacio");}
        }
        catch (ServiceException e){ System.out.println(e);} catch (RemoteException ex) {
            Logger.getLogger(FrmtareasAdicionales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ConstruyeTablaPedidos(){
        pedidos = new TablaPedidos1(this,cod_rol,cod_user);
        TblPedido.setAutoCreateColumnsFromModel(false);
        TblPedido.setModel(pedidos);

        for (int k = 0; k < TablaPedidos1.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaPedidos1.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaPedidos1.m_columns[k].m_width, renderer, null);
            TblPedido.addColumn(column);
        }
        JTableHeader header = TblPedido.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlPedido.getViewport().add(TblPedido);
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
    
    public void nueva_tarea()
    {
        String nt=jTextField1.getText();
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.insertartareas(cod_user, mes, nt, gestion);
            jTextField1.setText("");
            llena_tareas();
        }catch(Exception e)
        {
            System.out.println("tarea no creada por "+e);
        }
    }
    
    private void BtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReporteActionPerformed
        nueva_tarea();
    }//GEN-LAST:event_BtnReporteActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        llena_tareas();
        //elimina_componentes();
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void BtnReporte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReporte2ActionPerformed
        // TODO add your handling code here:boolean df=jTextField1.getFocusTraversalKeysEnabled();
        int fila=TblPedido.getSelectedRow();
        int cod_tarea=Integer.parseInt(TblPedido.getValueAt(fila, 1).toString());
        System.out.println("aqui esta el convertido "+cod_tarea);
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            puerto.eliminatarea(cod_tarea);
            //jTextField1.setText("");
            llena_tareas();
        }catch(Exception e)
        {
            System.out.println("tarea no creada por "+e);
        }
    }//GEN-LAST:event_BtnReporte2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int i=jComboBox4.getSelectedIndex()+1;
        int j=jComboBox5.getSelectedIndex()+1;
        GetResAdj repo = new GetResAdj();
        repo.ReporteTareas(i,j);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    void CerrarFrame(){
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BtnGrpReporte;
    private javax.swing.JButton BtnReporte;
    private javax.swing.JButton BtnReporte2;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JScrollPane PnlPedido;
    private javax.swing.JTable TblPedido;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
