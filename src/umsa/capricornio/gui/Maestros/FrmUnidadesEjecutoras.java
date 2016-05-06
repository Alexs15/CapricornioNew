/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.Maestros;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.Maestros.tablas.TablaUnidadEjecutora;
import umsa.capricornio.gui.menu.FrmMenu;
import java.io.IOException;
import java.io.File;
import java.net.URLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
        


/**
 *
 * @author julian
 */
public class FrmUnidadesEjecutoras extends javax.swing.JInternalFrame {

    FrmMenu menu;
    private Runtime r;
    int gestion;
    
    TablaUnidadEjecutora unidad;
    /**
     * Creates new form FrmUnidadesEjecutora
     */
    public FrmUnidadesEjecutoras(FrmMenu menu, int gestion) {
        this.menu=menu;
        this.gestion=gestion;
        initComponents();
        ConstruyeTablaUnidades();
    }

    private void ConstruyeTablaUnidades(){        
        unidad = new TablaUnidadEjecutora();        
        TblUnidadEjecutora.setAutoCreateColumnsFromModel(false);
        TblUnidadEjecutora.setModel(unidad); 
        
        for (int k = 0; k < TablaUnidadEjecutora.m_columns.length; k++) {               
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaUnidadEjecutora.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaUnidadEjecutora.m_columns[k].m_width, renderer, null);
            TblUnidadEjecutora.addColumn(column);
        }        
        JTableHeader header = TblUnidadEjecutora.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlUnidadEjecutora.getViewport().add(TblUnidadEjecutora);        
        //getContentPane().add(PnlTechos, BorderLayout.CENTER);
    }
        
    private void LlenaAlmacen(){           
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //adicionar almacen
            Map[] datos= puerto.getAlmacen(gestion);
            CmbAlmacen.addItem("-- Elija un Almacen --");
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                                        
                    CmbAlmacen.addItem(datos[c].get("COD_ALMACEN")+" - "+datos[c].get("ALMACEN"));                    
                }                
            }                    
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
    
    private void LlenaDA(){           
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            //adicionar almacen
            Map[] datos= puerto.getDa(gestion);
            CmbDa.addItem("-- Elija una Dirección Administrativa --");
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                                        
                    CmbDa.addItem(datos[c].get("COD_FACULTY")+" - "+datos[c].get("DA"));                    
                }                
            }                    
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORNIO SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
    
    
    private void LlenaUnidad(){
        CerearTablaUnidad();    
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getUnidadEjecutora(gestion);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    unidad.insert(c);                    
                    TblUnidadEjecutora.tableChanged(new TableModelEvent(unidad, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT)); 
                    TblUnidadEjecutora.setValueAt(datos[c].get("COD_APERT"),c,0);
                    TblUnidadEjecutora.setValueAt(datos[c].get("DETALLE"),c,1);
                    TblUnidadEjecutora.setValueAt(datos[c].get("APERTURA"),c,2);
                    TblUnidadEjecutora.setValueAt(datos[c].get("ALMACEN"),c,3);
                    TblUnidadEjecutora.setValueAt(datos[c].get("DA"),c,4);
                }                
            }                    
        }
        catch (RemoteException e){ 
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);           
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
    
    void CerearTablaUnidad(){
        int f = TblUnidadEjecutora.getRowCount();
        for (int i=f;i>=0;i--){
             if (unidad.delete(i)) {
                TblUnidadEjecutora.tableChanged(new TableModelEvent(
                unidad, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT)); 
             }
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

        PnlUnidadEjecutora = new javax.swing.JScrollPane();
        TblUnidadEjecutora = new javax.swing.JTable();
        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        TxtCodApert = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        CmbAlmacen = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TxtUnidadEjecutora = new javax.swing.JTextField();
        CmbDa = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();

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

        TblUnidadEjecutora.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblUnidadEjecutora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblUnidadEjecutoraMouseClicked(evt);
            }
        });
        PnlUnidadEjecutora.setViewportView(TblUnidadEjecutora);

        getContentPane().add(PnlUnidadEjecutora);
        PnlUnidadEjecutora.setBounds(10, 10, 690, 160);

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnNuevo.setToolTipText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(BtnNuevo);
        BtnNuevo.setBounds(220, 340, 30, 25);

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnGuardar);
        BtnGuardar.setBounds(250, 340, 30, 25);

        BtnModificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.setToolTipText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnModificar);
        BtnModificar.setBounds(280, 340, 30, 25);

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnEliminar.setToolTipText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnEliminar);
        BtnEliminar.setBounds(310, 340, 30, 25);

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
        jButton5.setBounds(390, 340, 80, 25);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Administracin de Unidades Ejecutoras"));
        jPanel1.setLayout(null);

        TxtCodApert.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(TxtCodApert);
        TxtCodApert.setBounds(110, 60, 130, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Dirección Administrativa");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 90, 120, 20);

        CmbAlmacen.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(CmbAlmacen);
        CmbAlmacen.setBounds(360, 60, 250, 20);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Almacen :");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(270, 60, 80, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Unidad Ejecutora :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 30, 90, 20);

        TxtUnidadEjecutora.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(TxtUnidadEjecutora);
        TxtUnidadEjecutora.setBounds(110, 30, 500, 20);

        CmbDa.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanel1.add(CmbDa);
        CmbDa.setBounds(140, 90, 440, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Codigo Apertura :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 60, 90, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(40, 180, 630, 140);

        setBounds(0, 0, 722, 433);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        BtnEliminar.setEnabled(false);
        BtnModificar.setEnabled(false);
        BtnGuardar.setEnabled(true);
        TblUnidadEjecutora.setEnabled(true);
        TxtUnidadEjecutora.setText("");
        TxtCodApert.setText("");
        CmbAlmacen.setSelectedIndex(0);
        
        /*
         * CmbRubro.setEnabled(true); CmbFF.setEnabled(true);
         * CmbOF.setEnabled(true);
         *
         * TxtMonto.setText(""); CmbRubro.setSelectedIndex(0);
    CmbRubro.requestFocus();
         */
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        if ("".equals(TxtUnidadEjecutora.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir la unidad ejecutora", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtUnidadEjecutora.requestFocus();
            return;
        }

        if ("".equals(TxtCodApert.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir el codigo de apertura programatica", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCodApert.requestFocus();
            return;
        }

        if ("-- Elija un Almacen --".equals(CmbAlmacen.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar un almacen", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCodApert.requestFocus();
            return;
        }
            
          
        for (int n=0 ; n<TblUnidadEjecutora.getRowCount();n++){
            if (TxtCodApert.getText().trim().equals(TblUnidadEjecutora.getValueAt(n, 2))){
                javax.swing.JOptionPane.showMessageDialog(this,"El codigo ya existe , debe introducir otro codigo ","ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);  
                TxtCodApert.requestFocus();
                return;
            }
            if (TxtUnidadEjecutora.getText().trim().equals(TblUnidadEjecutora.getValueAt(n, 1))){
                javax.swing.JOptionPane.showMessageDialog(this,"Ya existe esta unidad ejecutora con este mismo nombre, debe introducir otro nombre","ZODIAC CAPRICORN SYSTEM",
                        javax.swing.JOptionPane.ERROR_MESSAGE);  
                TxtUnidadEjecutora.requestFocus();
                return;
            }

        }
        
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea guardar los datos?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        String []dat_alm=CmbAlmacen.getSelectedItem().toString().split(" - ");
        String []dat_da=CmbDa.getSelectedItem().toString().split(" - ");
        int cod_almacen=Integer.parseInt(dat_alm[0]);
        String cod_da=dat_da[0];
        Date hoy = new Date();
        SimpleDateFormat sdtForPostgres = new SimpleDateFormat("ddMMyyHHmmssSSSS");
        String cod_apert=sdtForPostgres.format(hoy);
        try {
             AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setUnidadEjecutora("SET-insDataUE", cod_apert,TxtUnidadEjecutora.getText().trim(),TxtCodApert.getText().trim(),cod_almacen,cod_da,gestion);
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario Registrado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUnidad();
            TxtUnidadEjecutora.setText("");
            TxtCodApert.setText("");
            CmbAlmacen.setSelectedIndex(0);            
            TxtUnidadEjecutora.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        if ("".equals(TxtUnidadEjecutora.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir la unidad ejecutora", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtUnidadEjecutora.requestFocus();
            return;
        }

        if ("".equals(TxtCodApert.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir el codigo de apertura programatica", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtCodApert.requestFocus();
            return;
        }

        if ("-- Elija un Almacen --".equals(CmbAlmacen.getSelectedItem())){
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar un almacen", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            CmbAlmacen.requestFocus();
            return;
        }                    
            
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea modificar los datos?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        String []dat_alm=CmbAlmacen.getSelectedItem().toString().split(" - ");
        int cod_almacen=Integer.parseInt(dat_alm[0]);
        String []dat_da=CmbDa.getSelectedItem().toString().split(" - ");
        String cod_da=dat_da[0];        
        int f = TblUnidadEjecutora.getSelectedRow();
        String cod_apert=TblUnidadEjecutora.getValueAt(f, 0).toString();
                
        for (int n=0 ; n<TblUnidadEjecutora.getRowCount();n++){
            if (f!=n){
                if (TxtCodApert.getText().trim().equals(TblUnidadEjecutora.getValueAt(n, 2)) ){
                    javax.swing.JOptionPane.showMessageDialog(this,"El codigo  ya existe , debe introducir otro codigo ","ZODIAC CAPRICORN SYSTEM",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    TxtCodApert.requestFocus();
                    return;
                }
                if (TxtUnidadEjecutora.getText().trim().equals(TblUnidadEjecutora.getValueAt(n, 1))){
                    javax.swing.JOptionPane.showMessageDialog(this,"Ya existe esta unidad ejecutora con este mismo nombre, debe introducir otro nombre","ZODIAC CAPRICORN SYSTEM",
                            javax.swing.JOptionPane.ERROR_MESSAGE);  
                    TxtUnidadEjecutora.requestFocus();
                    return;
                }
            }
        }
        
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setUnidadEjecutoraModif("SET-updDataUE", cod_apert, TxtUnidadEjecutora.getText().trim(), TxtCodApert.getText().trim(),cod_almacen,cod_da,gestion);
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario modificado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUnidad();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblUnidadEjecutora.setEnabled(true);
            TblUnidadEjecutora.setEnabled(true);
            TxtUnidadEjecutora.setText("");
            TxtCodApert.setText("");
            CmbAlmacen.setSelectedIndex(0);            
            TxtUnidadEjecutora.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea Eliminar esta Actividad?",
                "ZODIAC CAPRICORN SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        
        int f = TblUnidadEjecutora.getSelectedRow();
        String cod_apert=TblUnidadEjecutora.getValueAt(f, 0).toString();
        
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = datos = puerto.setUnidadEjecutoraDel("SET-delDataUE", cod_apert);
            javax.swing.JOptionPane.showMessageDialog(this, "Actividad Eliminada", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUnidad();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblUnidadEjecutora.setEnabled(true);
            TxtUnidadEjecutora.setText("");
            TxtCodApert.setText("");
            CmbAlmacen.setSelectedIndex(0);
            TxtUnidadEjecutora.requestFocus();
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

    private void TblUnidadEjecutoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblUnidadEjecutoraMouseClicked
        if (evt.getClickCount() == 2) {
            BtnEliminar.setEnabled(true);
            BtnModificar.setEnabled(true);
            BtnGuardar.setEnabled(false);
            TblUnidadEjecutora.setEnabled(false);
            int fila=TblUnidadEjecutora.getSelectedRow();        
            TxtUnidadEjecutora.setText(TblUnidadEjecutora.getValueAt(fila,1).toString());
            TxtCodApert.setText(TblUnidadEjecutora.getValueAt(fila,2).toString());             
            String almacen=TblUnidadEjecutora.getValueAt(fila,3).toString();
            String da=TblUnidadEjecutora.getValueAt(fila,4).toString();
            String []dat_alm=null;
            String []dat_da=null;
            for (int n=0 ; n<CmbAlmacen.getItemCount();n++) {
                if (n!=0) {
                    dat_alm=CmbAlmacen.getItemAt(n).toString().split(" - ");
                    if (almacen.equals(dat_alm[1])) 
                        CmbAlmacen.setSelectedIndex(n);
                }
            }
            for (int n=0 ; n<CmbDa.getItemCount();n++) {
                if (n!=0) {
                    dat_da=CmbDa.getItemAt(n).toString().split(" - ");
                    if (da.equals(dat_da[1])) 
                        CmbDa.setSelectedIndex(n);
                }
            }
            
        }
    }//GEN-LAST:event_TblUnidadEjecutoraMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        LlenaAlmacen();
        LlenaDA();
        LlenaUnidad();
        BtnGuardar.setEnabled(true);
        BtnModificar.setEnabled(false);
        BtnEliminar.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JComboBox CmbAlmacen;
    private javax.swing.JComboBox CmbDa;
    private javax.swing.JScrollPane PnlUnidadEjecutora;
    private javax.swing.JTable TblUnidadEjecutora;
    private javax.swing.JTextField TxtCodApert;
    private javax.swing.JTextField TxtUnidadEjecutora;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
