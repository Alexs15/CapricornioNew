/*
 * FrmUsrRol.java
 *
 * Created on 15 de octubre de 2008, 11:13
 */

package umsa.capricornio.gui.relacionadores;

import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.relacionadores.tablas.TablaUsrWorkflow;

/**
 *
 * @author  julian
 */
public class FrmUsrWorkflow extends javax.swing.JInternalFrame {

    TablaUsrWorkflow usr_work;    
    FrmMenu menu;

    int cod_w;
    
    private Runtime r;
    
    int gestion, cod_almacen;
    
    /** Creates new form FrmUsrRol */
    public FrmUsrWorkflow(FrmMenu menu, int gestion, int cod_almacen) {
        this.menu=menu;
        initComponents();
        this.gestion=gestion;
        this.cod_almacen=cod_almacen;
        ConstruyeTablaUsrRol();
    }

    private void ConstruyeTablaUsrRol(){        
        usr_work = new TablaUsrWorkflow();
        TblUsrWorkflow.setAutoCreateColumnsFromModel(false);
        TblUsrWorkflow.setModel(usr_work);
        
        for (int k = 0; k < TablaUsrWorkflow.m_columns.length; k++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaUsrWorkflow.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaUsrWorkflow.m_columns[k].m_width, renderer, null);
            TblUsrWorkflow.addColumn(column);
        }        
        JTableHeader header = TblUsrWorkflow.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlUsrWorkflow.getViewport().add(TblUsrWorkflow);
        //getContentPane().add(PnlTechos, BorderLayout.CENTER);
    }
    
    private void LlenaUsrOficina(){
        CerearTablaUsrOficina();
        String q = "";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();  
            
            System.out.println("Intentando llenar la tabla de worklow");
            Map[] datos=puerto.getUsrWorkflow2(this.cod_almacen);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    usr_work.insert(c);
                    TblUsrWorkflow.tableChanged(new TableModelEvent(usr_work, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblUsrWorkflow.setValueAt(datos[c].get("COD_USUARIO"),c,0);
                    TblUsrWorkflow.setValueAt(datos[c].get("COD_W"),c,1);
                    TblUsrWorkflow.setValueAt(datos[c].get("CODIGO"),c,2);
                    TblUsrWorkflow.setValueAt(datos[c].get("USUARIO"),c,3);
                    TblUsrWorkflow.setValueAt(datos[c].get("DETALLE"),c,4);
                    TblUsrWorkflow.setValueAt(datos[c].get("ESTADO"),c,5);
                }
            }                                                                          
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIACO CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}
    }
   
void LlenaListasUsrWorkflow(){
    LstWorkflow.removeAll();
    LstUsuarios.removeAll();
    DefaultListModel ListaUsr = new DefaultListModel();    
    DefaultListModel ListaUE = new DefaultListModel();    
    try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();    
            Map[] datos= puerto.getUsuariox2(this.gestion, this.cod_almacen);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                    
                    ListaUsr.addElement(datos[c].get("COD_USUARIO")+" - "+datos[c].get("USUARIO"));
                }                
                LstUsuarios.setModel(ListaUsr);
            }  
            datos= puerto.getWorkflowDetalle();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    ListaUE.addElement(datos[c].get("COD_W")+" - "+datos[c].get("DETALLE"));
                }                
                LstWorkflow.setModel(ListaUE);
            }
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIACO CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}    
}

void LlenaListaEstadosW(){
    String v1=LstWorkflow.getSelectedValue().toString();
    
    String [] campos = v1.split(" - ");    
    cod_w= Integer.parseInt(campos[0]);
    
    LstEstado.removeAll();
    DefaultListModel ListaEstado = new DefaultListModel();
    try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();    
            Map[] datos= puerto.getEstadosWorkflow(cod_w);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    ListaEstado.addElement(datos[c].get("ORIGEN")+" - "+datos[c].get("ESTADO"));
                }
                LstEstado.setModel(ListaEstado);
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIACO CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
}

    void CerearTablaUsrOficina(){
        int f = TblUsrWorkflow.getRowCount();
        for (int i=f;i>=0;i--){
             if (usr_work.delete(i)) {
                TblUsrWorkflow.tableChanged(new TableModelEvent(
                usr_work, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
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

        PnlUsrWorkflow = new javax.swing.JScrollPane();
        TblUsrWorkflow = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        LstUsuarios = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        LstEstado = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        LstWorkflow = new javax.swing.JList();

        setTitle("USUARIO - WORKFLOW");
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

        TblUsrWorkflow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblUsrWorkflowMousePressed(evt);
            }
        });
        PnlUsrWorkflow.setViewportView(TblUsrWorkflow);

        getContentPane().add(PnlUsrWorkflow);
        PnlUsrWorkflow.setBounds(10, 10, 750, 190);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));
        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        LstUsuarios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(LstUsuarios);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 200, 270, 160);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado"));
        jScrollPane3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        LstEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(LstEstado);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(540, 200, 220, 160);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Relacion Usuario - Rol"));
        jPanel1.setLayout(null);

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnNuevo.setToolTipText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(BtnNuevo);
        BtnNuevo.setBounds(140, 20, 30, 25);

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnGuardar);
        BtnGuardar.setBounds(170, 20, 30, 25);

        BtnModificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.setToolTipText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnModificar);
        BtnModificar.setBounds(200, 20, 30, 25);

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnEliminar.setToolTipText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnEliminar);
        BtnEliminar.setBounds(230, 20, 30, 25);

        jButton5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/arrow_left.png"))); // NOI18N
        jButton5.setMnemonic('S');
        jButton5.setText("Salir");
        jButton5.setToolTipText("Salir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(260, 20, 80, 25);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(160, 360, 460, 60);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Workflow"));
        jScrollPane4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        LstWorkflow.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LstWorkflow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LstWorkflowMousePressed(evt);
            }
        });
        LstWorkflow.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LstWorkflowValueChanged(evt);
            }
        });
        LstWorkflow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                LstWorkflowKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(LstWorkflow);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(280, 200, 260, 160);

        setBounds(0, 0, 780, 454);
    }// </editor-fold>//GEN-END:initComponents

    private void TblUsrWorkflowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblUsrWorkflowMousePressed
        if (evt.getClickCount() == 2) {
            BtnGuardar.setEnabled(false);
            BtnEliminar.setEnabled(true);
            TblUsrWorkflow.setEnabled(false);
            int fila=TblUsrWorkflow.getSelectedRow();
            LstUsuarios.setSelectedValue(TblUsrWorkflow.getValueAt(fila, 0)+" - "+TblUsrWorkflow.getValueAt(fila, 3), true);
            LstWorkflow.setSelectedValue(TblUsrWorkflow.getValueAt(fila, 1)+" - "+TblUsrWorkflow.getValueAt(fila, 4), true);
            LstEstado.setSelectedValue(TblUsrWorkflow.getValueAt(fila, 2)+" - "+TblUsrWorkflow.getValueAt(fila, 5), true);
        }
}//GEN-LAST:event_TblUsrWorkflowMousePressed

private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
    BtnEliminar.setEnabled(false);
    BtnModificar.setEnabled(false);
    BtnGuardar.setEnabled(true);
    TblUsrWorkflow.setEnabled(true);
}//GEN-LAST:event_BtnNuevoActionPerformed

private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
    String v1="", v2="",v3="";
    try {
        v1=LstUsuarios.getSelectedValue().toString();
        v2=LstWorkflow.getSelectedValue().toString();
        v3=LstEstado.getSelectedValue().toString();
    }
    catch(NullPointerException e){
        javax.swing.JOptionPane.showMessageDialog(this,"Debe Elegir usuarios , flujo de trabajo y el estado","ZODIACO CAPRICORN SYSTEM",
        javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String [] campos = v1.split(" - ");
    int cod_usr=Integer.parseInt(campos[0]);        
    
    campos = v2.split(" - ");    
    cod_w= Integer.parseInt(campos[0]);
           
    campos = v3.split(" - ");    
    String    cod_estado= campos[0];        
    
    for (int n=0 ; n<TblUsrWorkflow.getRowCount();n++){
        if ( TblUsrWorkflow.getValueAt(n, 0).equals(Integer.toString(cod_usr))
                && TblUsrWorkflow.getValueAt(n, 1).equals(Integer.toString(cod_w))
                && TblUsrWorkflow.getValueAt(n, 2).equals(cod_estado)){
            javax.swing.JOptionPane.showMessageDialog(this,"La relacion que eligio, ya existe","ZODIACO CAPRICORN SYSTEM",
            javax.swing.JOptionPane.ERROR_MESSAGE);  
            return;
        }            
    }
    
    int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea guardar los datos?",
                "Traspasos Presupuestarios",javax.swing.JOptionPane.YES_NO_OPTION );
    if (res == javax.swing.JOptionPane.NO_OPTION) return;            
    try {                  
        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
        AdquiWS_PortType puerto = servicio.getAdquiWS();    
        Map[] datos = puerto.setUsrWorkflow(cod_usr,cod_w,cod_estado);        
        javax.swing.JOptionPane.showMessageDialog(this,"Relacion almacenada","ZODIACO CAPRICORN SYSTEM",
        javax.swing.JOptionPane.INFORMATION_MESSAGE);                    
        LlenaUsrOficina();              
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+ex,"ZODIACO CAPRICORN SYSTEM",
                            javax.swing.JOptionPane.ERROR_MESSAGE);    } 
        catch (ServiceException ex) {}
}//GEN-LAST:event_BtnGuardarActionPerformed

private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
///sin codigo
}//GEN-LAST:event_BtnModificarActionPerformed

private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
    String v1="", v2="",v3="";
    try {        
        v1=LstUsuarios.getSelectedValue().toString();
        v2=LstWorkflow.getSelectedValue().toString();
        v3=LstEstado.getSelectedValue().toString();
    }
    catch(NullPointerException e){ 
        javax.swing.JOptionPane.showMessageDialog(this,"Debe Elegir Ambos componentes","ZODIACO CAPRICORN SYSTEM",
        javax.swing.JOptionPane.ERROR_MESSAGE);                              
        return;
    }
    
    String [] campos = v1.split(" - ");
    int cod_usr=Integer.parseInt(campos[0]);        
    
    campos = v2.split(" - ");    
    cod_w= Integer.parseInt(campos[0]);
           
    campos = v3.split(" - ");    
    String    cod_estado= campos[0];

    int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea Eliminar esta relacion?",
                "ZODIACO CAPRICORN SYSTEM",javax.swing.JOptionPane.YES_NO_OPTION );
    if (res == javax.swing.JOptionPane.NO_OPTION) return;
        try {   AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();    
                Map[] datos = puerto.setUsrWorkflowDel(cod_usr,cod_w,cod_estado);                                    
                javax.swing.JOptionPane.showMessageDialog(this,"Relacion eliminada","ZODIACO CAPRICORN SYSTEM",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);                                     
                LlenaUsrOficina();
                BtnEliminar.setEnabled(false);
                BtnModificar.setEnabled(false);
                BtnGuardar.setEnabled(true);    
                TblUsrWorkflow.setEnabled(true);
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+ex,"ZODIACO CAPRICORN SYSTEM",
                            javax.swing.JOptionPane.ERROR_MESSAGE);    } 
        catch (ServiceException ex) {}
}//GEN-LAST:event_BtnEliminarActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
menu.CerrarFrameInterno(this);
    System.gc();
    r = Runtime.getRuntime();
    long mem1 = r.freeMemory();
}//GEN-LAST:event_jButton5ActionPerformed

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    BtnEliminar.setEnabled(false);
    BtnModificar.setEnabled(false);
    BtnGuardar.setEnabled(true); 
    LlenaUsrOficina();
    LlenaListasUsrWorkflow();
}//GEN-LAST:event_formInternalFrameOpened

private void LstWorkflowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LstWorkflowKeyReleased
    /*if(evt.getKeyCode() == KeyEvent.VK_UP
            || evt.getKeyCode() == KeyEvent.VK_DOWN)  {
        LlenaListaEstadosW();
    }*/
}//GEN-LAST:event_LstWorkflowKeyReleased

private void LstWorkflowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LstWorkflowMousePressed
    //LlenaListaEstadosW();
}//GEN-LAST:event_LstWorkflowMousePressed

private void LstWorkflowValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LstWorkflowValueChanged
    LlenaListaEstadosW();
}//GEN-LAST:event_LstWorkflowValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JList LstEstado;
    private javax.swing.JList LstUsuarios;
    private javax.swing.JList LstWorkflow;
    private javax.swing.JScrollPane PnlUsrWorkflow;
    private javax.swing.JTable TblUsrWorkflow;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

}
