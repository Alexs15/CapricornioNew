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
import umsa.capricornio.gui.relacionadores.tablas.TablaUsrRol;

/**
 *
 * @author  henrry
 */
public class FrmUsrRol extends javax.swing.JInternalFrame {

    TablaUsrRol relacion;

    FrmMenu menu;
    
    private Runtime r;
    
    private int gestion,cod_almacen;
    
    /** Creates new form FrmUsrRol */
    public FrmUsrRol(FrmMenu menu, int gestion, int cod_almacen) {
        this.menu=menu;
        initComponents();
        this.gestion=gestion;
        this.cod_almacen=cod_almacen;
        ConstruyeTablaUsrRol();
    }

    private void ConstruyeTablaUsrRol(){        
        relacion = new TablaUsrRol();        
        TblUsrRol.setAutoCreateColumnsFromModel(false);
        TblUsrRol.setModel(relacion); 
        
        for (int k = 0; k < TablaUsrRol.m_columns.length; k++) {               
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaUsrRol.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaUsrRol.m_columns[k].m_width, renderer, null);
            TblUsrRol.addColumn(column);                           
        }        
        JTableHeader header = TblUsrRol.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlUsrRol.getViewport().add(TblUsrRol);        
        //getContentPane().add(PnlTechos, BorderLayout.CENTER);
    }
    
    private void LlenaUsrRol(){
        CerearTablaUsrRol();
        String q = "";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getUsrRol2(this.cod_almacen);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    relacion.insert(c);                    
                    TblUsrRol.tableChanged(new TableModelEvent(relacion, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));                    
                    TblUsrRol.setValueAt(datos[c].get("COD_USUARIO"),c,0);
                    TblUsrRol.setValueAt(datos[c].get("COD_ROL"),c,1);
                    TblUsrRol.setValueAt(datos[c].get("USUARIO"),c,2);        
                    TblUsrRol.setValueAt(datos[c].get("ROL"),c,3);        
                }
            }                                                                          
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}
    }
   
    void LlenaListasUsrRol(){
        LstRoles.removeAll();
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
                datos= puerto.getRoles();
                if (datos!=null){
                    for (int c=0;c<datos.length;c++){
                        ListaUE.addElement(datos[c].get("COD_ROL")+" - "+datos[c].get("ROL"));
                    }                
                    LstRoles.setModel(ListaUE);
                }
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}    
    }

    void CerearTablaUsrRol(){
        int f = TblUsrRol.getRowCount();
        for (int i=f;i>=0;i--){
             if (relacion.delete(i)) {
                TblUsrRol.tableChanged(new TableModelEvent(
                relacion, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT)); 
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

        PnlUsrRol = new javax.swing.JScrollPane();
        TblUsrRol = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        LstUsuarios = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        LstRoles = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setTitle("USUARIO - ROLES");
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

        TblUsrRol.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblUsrRol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblUsrRolMousePressed(evt);
            }
        });
        PnlUsrRol.setViewportView(TblUsrRol);

        getContentPane().add(PnlUsrRol);
        PnlUsrRol.setBounds(10, 10, 670, 190);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));
        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        LstUsuarios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(LstUsuarios);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 200, 330, 160);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Roles"));
        jScrollPane3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        LstRoles.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(LstRoles);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(350, 200, 330, 160);

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
        BtnNuevo.setBounds(100, 20, 30, 25);

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnGuardar);
        BtnGuardar.setBounds(130, 20, 30, 25);

        BtnModificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.setToolTipText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnModificar);
        BtnModificar.setBounds(160, 20, 30, 25);

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnEliminar.setToolTipText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnEliminar);
        BtnEliminar.setBounds(190, 20, 30, 25);

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
        jButton5.setBounds(270, 20, 80, 25);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(120, 360, 460, 60);

        setBounds(0, 0, 699, 457);
    }// </editor-fold>//GEN-END:initComponents

private void TblUsrRolMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblUsrRolMousePressed
    if (evt.getClickCount() == 2) {
        BtnGuardar.setEnabled(false);
        BtnEliminar.setEnabled(true);
        TblUsrRol.setEnabled(false);
        int fila=TblUsrRol.getSelectedRow();
        LstUsuarios.setSelectedValue(TblUsrRol.getValueAt(fila, 0)+" - "+TblUsrRol.getValueAt(fila, 2), true);
        LstRoles.setSelectedValue(TblUsrRol.getValueAt(fila, 1)+" - "+TblUsrRol.getValueAt(fila, 3), true);
    }
}//GEN-LAST:event_TblUsrRolMousePressed

private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
    BtnEliminar.setEnabled(false);
    BtnModificar.setEnabled(false);
    BtnGuardar.setEnabled(true);
    TblUsrRol.setEnabled(true);
}//GEN-LAST:event_BtnNuevoActionPerformed

private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
    String v1="";
    String v2="";
    try {
        v1=LstRoles.getSelectedValue().toString();
        v2=LstUsuarios.getSelectedValue().toString();        
    }
    catch(NullPointerException e){ 
        javax.swing.JOptionPane.showMessageDialog(this,"Debe Elegir Ambos componentes","ZODIAC CAPRICORN SYSTEM",
        javax.swing.JOptionPane.ERROR_MESSAGE);                              
        return;
    }
        
    String [] campos = v1.split(" - ");  
    int cod_rol=Integer.parseInt(campos[0]);    
            
    campos = v2.split(" - ");  
    int cod_usr= Integer.parseInt(campos[0]);                        
        
    
    for (int n=0 ; n<TblUsrRol.getRowCount();n++){
        if ( TblUsrRol.getValueAt(n, 0).equals(Integer.toString(cod_usr)) && TblUsrRol.getValueAt(n, 1).equals(Integer.toString(cod_rol))){
            javax.swing.JOptionPane.showMessageDialog(this,"La relacion que eligio, ya existe","ZODIAC CAPRICORN SYSTEM",
            javax.swing.JOptionPane.ERROR_MESSAGE);  
            return;
        }            
    }
    
    int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea guardar los datos?",
                "ZODIAC CAPRICORN SYSTEM",javax.swing.JOptionPane.YES_NO_OPTION );
    if (res == javax.swing.JOptionPane.NO_OPTION) return;            
    try {                  
        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
        AdquiWS_PortType puerto = servicio.getAdquiWS();
        Map[] datos = puerto.setUsrRol("SET-insDataUsr",cod_usr,cod_rol);
            javax.swing.JOptionPane.showMessageDialog(this,"Relacion ROL - Usuario(s) almacenada","ZODIAC CAPRICORN SYSTEM",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);                    
            LlenaUsrRol();                         
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+ex,"ZODIAC CAPRICORN SYSTEM",
                            javax.swing.JOptionPane.ERROR_MESSAGE);    } 
        catch (ServiceException ex) {}
}//GEN-LAST:event_BtnGuardarActionPerformed

private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
///sin codigo
}//GEN-LAST:event_BtnModificarActionPerformed

private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
    String v1="";
    String v2="";
    try {
        v1=LstRoles.getSelectedValue().toString();
        v2=LstUsuarios.getSelectedValue().toString();        
    }
    catch(NullPointerException e){ 
        javax.swing.JOptionPane.showMessageDialog(this,"Debe Elegir Ambos componentes","ZODIAC CAPRICORN SYSTEM",
        javax.swing.JOptionPane.ERROR_MESSAGE);                              
        return;
    }
    
    String [] campos = v1.split(" - ");  
    int cod_rol=Integer.parseInt(campos[0]);    
            
    campos = v2.split(" - ");  
    int cod_usr= Integer.parseInt(campos[0]);   
    
    int res = javax.swing.JOptionPane.showConfirmDialog( this,"¿Desea Eliminar esta relacion?",
                "ZODIAC CAPRICORN SYSTEM",javax.swing.JOptionPane.YES_NO_OPTION );
    if (res == javax.swing.JOptionPane.NO_OPTION) return;
        try {   AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                Map[] datos = puerto.setUsrRolDel("SET-delDataUsr",cod_usr,cod_rol);                
                javax.swing.JOptionPane.showMessageDialog(this,"Relacion Eliminada","ZODIAC CAPRICORN SYSTEM",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);                                     
                LlenaUsrRol();
                BtnEliminar.setEnabled(false);
                BtnModificar.setEnabled(false);
                BtnGuardar.setEnabled(true);
                TblUsrRol.setEnabled(true);
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+ex,"ZODIAC CAPRICORN SYSTEM",
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
    LlenaUsrRol();
    LlenaListasUsrRol();
}//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JList LstRoles;
    private javax.swing.JList LstUsuarios;
    private javax.swing.JScrollPane PnlUsrRol;
    private javax.swing.JTable TblUsrRol;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

}
