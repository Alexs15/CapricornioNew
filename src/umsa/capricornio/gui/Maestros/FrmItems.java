/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.Maestros;

import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.Maestros.tablas.TablaItemsABM;
import umsa.capricornio.gui.menu.FrmMenu;

/**
 *
 * @author julian
 */
public class FrmItems extends javax.swing.JInternalFrame {

    FrmMenu menu; 
    TablaItemsABM items;
    private Runtime r;
    int gestion;
    /**
     * Creates new form FrmItems
     */
    public FrmItems(FrmMenu menu,int gestion) {
        this.menu=menu;
        this.gestion=gestion;
        initComponents();
        ConstruyeTablaItems();
    }

    private void ConstruyeTablaItems(){        
        items = new TablaItemsABM();        
        TblItems.setAutoCreateColumnsFromModel(false);
        TblItems.setModel(items); 
        
        for (int k = 0; k < TablaItemsABM.m_columns.length; k++) {               
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaItemsABM.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaItemsABM.m_columns[k].m_width, renderer, null);
            TblItems.addColumn(column);
        }        
        JTableHeader header = TblItems.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlItems.getViewport().add(TblItems);        
        //getContentPane().add(PnlTechos, BorderLayout.CENTER);
    }
       
    private void LlenaItems(){
        CerearTablaItems();
        String linea="";
        int j=0;
          try{
            int partida=0;
            linea=CmbPartida.getSelectedItem().toString();
            String [] campos = linea.split(" - ");
            while(j<campos.length){  
               partida= Integer.parseInt(campos[0]);
               j++;  
            }
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getItemsABM(gestion,partida);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    items.insert(c);                    
                    TblItems.tableChanged(new TableModelEvent(items, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT)); 
                    TblItems.setValueAt(datos[c].get("COD_ITEM"),c,0);
                    TblItems.setValueAt(datos[c].get("PARTIDA"),c,1);
                    TblItems.setValueAt(datos[c].get("ARTICULO"),c,2);
                    TblItems.setValueAt(datos[c].get("UNIDAD_MEDIDA"),c,3);
                    TblItems.setValueAt(datos[c].get("GESTION"),c,4);
                    TblItems.setValueAt(datos[c].get("TIPO_ITEM"),c,5);
                }                
            }                  
          }  
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}  
        catch(NullPointerException e){}
    }

    void CerearTablaItems(){
        int f = TblItems.getRowCount();
        for (int i=f;i>=0;i--){
             if (items.delete(i)) {
                TblItems.tableChanged(new TableModelEvent(
                items, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT)); 
             }
        }
    }
    
    
    private void LlenaPartida(int gestion){           
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getPartida(gestion);
            CmbPartida.removeAllItems();
            CmbPartida.addItem("-- Elija una Partida --");
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                                        
                    CmbPartida.addItem(datos[c].get("PARTIDA")+" - "+ datos[c].get("DETALLE"));                    
                }                
            }                    
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
        
    private void LlenaTipoArticulo(){           
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getTipoItem();
            CmbTipoArticulo.addItem("-- Elija un Tipo --");
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                                        
                    CmbTipoArticulo.addItem(datos[c].get("COD_TIPO_ITEM")+" - "+datos[c].get("DETALLE"));                    
                }                
            }                    
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        PnlItems = new javax.swing.JScrollPane();
        TblItems = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        CmbTipoArticulo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        TxtUnidadMedida = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtArticulo = new javax.swing.JTextField();
        CmbPartida = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

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

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnNuevo.setToolTipText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(BtnNuevo);
        BtnNuevo.setBounds(320, 320, 30, 25);

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnGuardar);
        BtnGuardar.setBounds(350, 320, 30, 25);

        BtnModificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.setToolTipText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnModificar);
        BtnModificar.setBounds(380, 320, 30, 25);

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnEliminar.setToolTipText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnEliminar);
        BtnEliminar.setBounds(410, 320, 30, 25);

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
        getContentPane().add(jButton5);
        jButton5.setBounds(490, 320, 80, 25);

        TblItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblItemsMouseClicked(evt);
            }
        });
        PnlItems.setViewportView(TblItems);

        getContentPane().add(PnlItems);
        PnlItems.setBounds(10, 10, 860, 170);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Administracion de Articulos"));
        jPanel1.setLayout(null);

        CmbTipoArticulo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(CmbTipoArticulo);
        CmbTipoArticulo.setBounds(430, 80, 160, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("Tipo Articulo :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(330, 80, 70, 20);

        TxtUnidadMedida.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(TxtUnidadMedida);
        TxtUnidadMedida.setBounds(110, 80, 100, 20);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Unidad Medida :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 80, 90, 20);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Articulo :");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(40, 50, 70, 20);
        jPanel1.add(TxtArticulo);
        TxtArticulo.setBounds(110, 50, 580, 20);

        CmbPartida.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        CmbPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbPartidaActionPerformed(evt);
            }
        });
        jPanel1.add(CmbPartida);
        CmbPartida.setBounds(110, 20, 440, 20);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Partida");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(30, 20, 70, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(80, 190, 710, 120);

        setBounds(0, 0, 894, 418);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        BtnEliminar.setEnabled(false);
        BtnModificar.setEnabled(false);
        BtnGuardar.setEnabled(true);
        TblItems.setEnabled(true);
        CmbPartida.setEnabled(true);
        CmbPartida.setSelectedIndex(0);
        CmbTipoArticulo.setSelectedIndex(0);
        TxtArticulo.setText("");
        TxtUnidadMedida.setText("");
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        if ("".equals(TxtArticulo.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir el articulo", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtArticulo.requestFocus();
            return;
        }

        if ("".equals(TxtUnidadMedida.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir la unidad de medida", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtUnidadMedida.requestFocus();
            return;
        }

        
        if ("-- Elija una Partida --".equals(CmbPartida.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar una partida", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            CmbPartida.requestFocus();
            return;
        }
        
        if ("-- Elija un Tipo --".equals(CmbTipoArticulo.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar el tipo de articulo", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            CmbTipoArticulo.requestFocus();
            return;
        }
                
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea guardar los datos?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        String []dat_itm=CmbTipoArticulo.getSelectedItem().toString().split(" - ");
        int cod_tipo_item=Integer.parseInt(dat_itm[0]);
        String []dat_partida=CmbPartida.getSelectedItem().toString().split(" - ");
        String estado="V";
        int cod_partida=Integer.parseInt(dat_partida[0]);
        try {
             AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setItems("SET-insDataItm",cod_partida,TxtArticulo.getText().trim(),TxtUnidadMedida.getText().trim(),gestion,estado,cod_tipo_item);
            javax.swing.JOptionPane.showMessageDialog(this, "Item registrado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaItems();            
            CmbTipoArticulo.setSelectedIndex(0);
            TxtArticulo.setText("");
            TxtUnidadMedida.setText("");
            CmbPartida.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        if ("".equals(TxtArticulo.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir el articulo", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtArticulo.requestFocus();
            return;
        }

        if ("".equals(TxtUnidadMedida.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir la unidad de medida", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtUnidadMedida.requestFocus();
            return;
        }

        
        if ("-- Elija una Partida --".equals(CmbPartida.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar una partida", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            CmbPartida.requestFocus();
            return;
        }
        
        if ("-- Elija un Tipo --".equals(CmbTipoArticulo.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar el tipo de articulo", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            CmbTipoArticulo.requestFocus();
            return;
        }
        
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea Modificar esta Actividad?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        String []dat_itm=CmbTipoArticulo.getSelectedItem().toString().split(" - ");
        int cod_tipo_item=Integer.parseInt(dat_itm[0]);
        
        int f= TblItems.getSelectedRow();
        int cod_item=Integer.parseInt(TblItems.getValueAt(f, 0).toString());
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setItemsModif("SET-updDataItm", cod_item,TxtArticulo.getText().trim(), TxtUnidadMedida.getText().trim(),cod_tipo_item,gestion);
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario modificado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaItems();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblItems.setEnabled(true);
            CmbPartida.setEnabled(true);            
            CmbTipoArticulo.setSelectedIndex(0);
            TxtArticulo.setText("");
            TxtUnidadMedida.setText("");
            CmbPartida.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea Eliminar Artículo?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        int f= TblItems.getSelectedRow();
        int cod_item=Integer.parseInt(TblItems.getValueAt(f, 0).toString());
        
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = datos = puerto.setItemsDel("SET-delDataItm", cod_item);
            javax.swing.JOptionPane.showMessageDialog(this, "Articulo Eliminado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaItems();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblItems.setEnabled(true);
            CmbPartida.setEnabled(true);
            CmbTipoArticulo.setSelectedIndex(0);
            TxtArticulo.setText("");
            TxtUnidadMedida.setText("");
            CmbPartida.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void CmbPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbPartidaActionPerformed
        String linea=CmbPartida.getSelectedItem().toString();
        if (linea.equals("-- Elija una Partida --")) 
          { System.out.println("Hola");}
        else
          {LlenaItems(); } 
          
        //LlenaItems();
        
    }//GEN-LAST:event_CmbPartidaActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        LlenaPartida(gestion);
        LlenaTipoArticulo();
        BtnGuardar.setEnabled(true);
        BtnModificar.setEnabled(false);
        BtnEliminar.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void TblItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblItemsMouseClicked
        if (evt.getClickCount() == 2) { 
            CmbPartida.setEnabled(false);
            BtnEliminar.setEnabled(true);
            BtnModificar.setEnabled(true);
            BtnGuardar.setEnabled(false);
            TblItems.setEnabled(false);            
            int fila=TblItems.getSelectedRow();        
            String tipo=TblItems.getValueAt(fila,5).toString();
            String []dat_tip=null;
            for (int n=0 ; n<CmbTipoArticulo.getItemCount();n++) {
                if (n!=0) {
                    dat_tip=CmbTipoArticulo.getItemAt(n).toString().split(" - ");
                    if (tipo.equals(dat_tip[1])) 
                        CmbTipoArticulo.setSelectedIndex(n);
                }
            }
            CmbPartida.setSelectedItem(TblItems.getValueAt(fila,1).toString());
            TxtArticulo.setText(TblItems.getValueAt(fila,2).toString());
            TxtUnidadMedida.setText(TblItems.getValueAt(fila,3).toString());  
        }
    }//GEN-LAST:event_TblItemsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JComboBox CmbPartida;
    private javax.swing.JComboBox CmbTipoArticulo;
    private javax.swing.JScrollPane PnlItems;
    private javax.swing.JTable TblItems;
    private javax.swing.JTextField TxtArticulo;
    private javax.swing.JTextField TxtUnidadMedida;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
