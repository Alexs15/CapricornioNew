/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiagBuscaProveedor.java
 *
 * Created on 04-10-2011, 11:50:34 AM
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Proveedor;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.ConnecSigma.SigmaCapriWSServiceLocator;
import umsa.capricornio.gui.ConnecSigma.SigmaCapriWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.TablaProveedoresBusca;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;

/**
 *
 * @author julian
 */
public class DiagBuscaProveedor extends javax.swing.JDialog {

    TablaProveedoresBusca pbusca;
    Proveedor proveedor;
    private Runtime r;
    
    /** Creates new form DiagBuscaProveedor */
    public DiagBuscaProveedor(FrmMenu menu) {
        super(menu, true);
        initComponents();
        ConstruyeTablaBusqueda();
    }

    private void ConstruyeTablaBusqueda(){
        pbusca = new TablaProveedoresBusca();
        TblProveedores.setAutoCreateColumnsFromModel(false);
        TblProveedores.setModel(pbusca);

        for (int k = 0; k < TablaProveedoresBusca.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaProveedoresBusca.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaProveedoresBusca.m_columns[k].m_width, renderer, null);
            TblProveedores.addColumn(column);
        }
        JTableHeader header = TblProveedores.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlProveedores.getViewport().add(TblProveedores);
    }
    
    private void BuscaProveedores(){
        String nit = null,nombre_comercial= null,nombre=null;        
        if (!"".equals(TxtNombre_Comercial.getText())) 
        {
             nombre_comercial="%"+TxtNombre_Comercial.getText().toUpperCase()+"%";
        }
        else
        {   
             if (!"".equals(TxtNit.getText()))
             {
               nit=TxtNit.getText();
             }
             else
             {
               if (!"".equals(TxtNombre.getText()))
               {    
                  nombre="%"+TxtNombre.getText().toUpperCase()+"%";
               }
               else
               {
                   javax.swing.JOptionPane.showMessageDialog(this,"USTED DEBE INTRODUCIR POR LO MENOS UN VALOR EN NIT O NOMBRE O NOMBRE COMERCIAL","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                   return;
               }  
             }  
        }  
        
        try{
            SigmaCapriWSServiceLocator servicio = new SigmaCapriWSServiceLocator();
            SigmaCapriWS_PortType puerto = servicio.getSigmaCapriWS();
            //ERROR Map[] datos=puerto.getProveedoresBusqueda(partida, casa, serv,nit);
            System.out.println("nit: "+nit+" - nombre: "+nombre+" - nombre_comercial: "+nombre_comercial);
            if (!((nit != null)&&(!nit.isEmpty()))) { 
               nit="%";}            
            if (!((nombre != null)&&(!nombre.isEmpty()))) { 
               nombre="%";}
            if (!((nombre_comercial != null)&&(!nombre_comercial.isEmpty()))) { 
               nombre_comercial="%";}
            
            System.out.println("nit: "+nit+" - nombre: "+nombre+" - nombre_comercial: "+nombre_comercial);
            Map[] datos=puerto.getBeneficiariosCapriSigmaII(nit,nombre,nombre_comercial);
            System.out.println("eooooo el tamaño es: "+datos.length);
            CerearTablaProveedor();
            
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    pbusca.insert(c);
                    TblProveedores.tableChanged(new TableModelEvent(pbusca, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblProveedores.setValueAt(datos[c].get("DOCUMENTO"),c,0);
                    TblProveedores.setValueAt(datos[c].get("TIPO_ID"),c,1);                    
                    TblProveedores.setValueAt(datos[c].get("CLASE_BENEFICIARIO"),c,2);
                    TblProveedores.setValueAt(datos[c].get("NOMBRE"),c,3);
                    TblProveedores.setValueAt(datos[c].get("NOMBRE_COMERCIAL"),c,4);
                    TblProveedores.setValueAt(datos[c].get("ADH_NOMBRE"),c,5);
                    TblProveedores.setValueAt(datos[c].get("ADH_DOCUMENTO"),c,6);
                    TblProveedores.setValueAt(datos[c].get("DIR_LUGAR"),c,7);
                    TblProveedores.setValueAt(datos[c].get("DIR_DIRECCION"),c,8);
                    TblProveedores.setValueAt(datos[c].get("DIR_TELEFONO"),c,9);
                    TblProveedores.setValueAt(datos[c].get("DIR_EMAIL"),c,10);
                }
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
             if (pbusca.delete(i)) {
                TblProveedores.tableChanged(new TableModelEvent(
                pbusca, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
       
    void AbreItems(){
        int fila = TblProveedores.getSelectedRow();
        System.out.println("Wolassss --> "+TblProveedores.getValueAt(fila, 0));
        javax.swing.JOptionPane.showMessageDialog(this,"selecciono la fila con codigo -->"+TblProveedores.getValueAt(fila, 0)+" ","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        proveedor.setCod_proveedor(TblProveedores.getValueAt(fila, 0).toString());
        proveedor.setTipo_cod(TblProveedores.getValueAt(fila, 1).toString());
        proveedor.setClase(TblProveedores.getValueAt(fila, 2).toString());
        proveedor.setNombre(TblProveedores.getValueAt(fila, 3).toString());
        proveedor.setCasa_comercial(TblProveedores.getValueAt(fila, 4).toString());
        proveedor.setAdh_nombre(TblProveedores.getValueAt(fila, 5).toString());
        
        proveedor.setDireccion(TblProveedores.getValueAt(fila, 8).toString());
        proveedor.setTelefono(TblProveedores.getValueAt(fila, 9).toString());
        proveedor.setEstado("X");
        //8
        //9
        BtnCancelar.doClick();
        
        //this.setVisible(false);
        /*
        if ("HA".equals(TblProveedores.getValueAt(fila, 1))) {
            proveedor.setCod_proveedor(Integer.parseInt(TblProveedores.getValueAt(fila, 0).toString()));
            proveedor.setCasa_comercial(TblProveedores.getValueAt(fila, 3).toString());
            BtnCancelar.doClick();
        }
        else
            javax.swing.JOptionPane.showMessageDialog(this,"NO SE PUEDE ELEJIR UNA EMPRESA QUE ESTE SANSIONADA","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        */
    }
        
    public void AbreDialogo(){
        setVisible(true);
    }
    
    public Proveedor ProveedorElejido(){
        return proveedor;
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
        TxtNombre = new javax.swing.JTextField();
        TxtNombre_Comercial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtNit = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        PnlProveedores = new javax.swing.JScrollPane();
        TblProveedores = new javax.swing.JTable();
        BtnCancelar = new javax.swing.JButton();
        BtnDetalle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BUSCA PROVEEDORES");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(185, 203, 221));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Nombre");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(230, 40, 60, 15);

        TxtNombre.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNombreKeyPressed(evt);
            }
        });
        jPanel1.add(TxtNombre);
        TxtNombre.setBounds(120, 20, 280, 21);

        TxtNombre_Comercial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtNombre_Comercial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNombre_ComercialKeyPressed(evt);
            }
        });
        jPanel1.add(TxtNombre_Comercial);
        TxtNombre_Comercial.setBounds(420, 20, 260, 21);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Nombre Comercial");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(490, 40, 150, 15);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("NIT");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 40, 40, 15);

        TxtNit.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TxtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNitKeyPressed(evt);
            }
        });
        jPanel1.add(TxtNit);
        TxtNit.setBounds(10, 20, 90, 21);

        BtnBuscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(0, 102, 0));
        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnBuscar.setText("Buscar");
        BtnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        BtnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnBuscar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        BtnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnBuscar);
        BtnBuscar.setBounds(700, 10, 80, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 800, 70);

        TblProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblProveedoresMousePressed(evt);
            }
        });
        PnlProveedores.setViewportView(TblProveedores);

        getContentPane().add(PnlProveedores);
        PnlProveedores.setBounds(10, 90, 800, 190);

        BtnCancelar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnCancelar.setForeground(new java.awt.Color(0, 51, 153));
        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_previous.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar);
        BtnCancelar.setBounds(210, 290, 120, 25);

        BtnDetalle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnDetalle.setForeground(new java.awt.Color(0, 51, 153));
        BtnDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/information.png"))); // NOI18N
        BtnDetalle.setText("Detalle");
        BtnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDetalleActionPerformed(evt);
            }
        });
        getContentPane().add(BtnDetalle);
        BtnDetalle.setBounds(410, 290, 120, 25);

        setSize(new java.awt.Dimension(837, 388));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        BuscaProveedores();
        TxtNombre_Comercial.setText("");
        TxtNit.setText("");
        TxtNombre.setText("");
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void TblProveedoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblProveedoresMousePressed
        if (evt.getClickCount() == 2)
            AbreItems();
    }//GEN-LAST:event_TblProveedoresMousePressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        proveedor = new Proveedor();
    }//GEN-LAST:event_formWindowOpened

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        dispose();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void TxtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtNombreKeyPressed

    private void TxtNombre_ComercialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombre_ComercialKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtNombre_ComercialKeyPressed

    private void TxtNitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNitKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
    }//GEN-LAST:event_TxtNitKeyPressed

    private void BtnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDetalleActionPerformed
        // TODO add your handling code here:
        int fila = TblProveedores.getSelectedRow();
        if (fila<0)
            return;    
        String adh_nombre=String.valueOf(TblProveedores.getValueAt(fila,5));
        String adh_documento=String.valueOf(TblProveedores.getValueAt(fila,6));
        String dir_lugar=String.valueOf(TblProveedores.getValueAt(fila,7));;
        String dir_direccion=String.valueOf(TblProveedores.getValueAt(fila,8));;
        String dir_telefono=String.valueOf(TblProveedores.getValueAt(fila,9));;
        String dir_email=String.valueOf(TblProveedores.getValueAt(fila,10));;
        String nl = System.getProperty("line.separator");
        javax.swing.JOptionPane.showMessageDialog(this,
        "APODERADO :"+adh_nombre +nl+"DOCUMENTO : "+ adh_documento +nl+"PAIS-CIUDAD: "+dir_lugar+nl+"DIRECCION:"+dir_direccion+nl+"TELEFONO :"+dir_telefono+nl+"EMAIL :"+dir_email ,
          "SYSTEM CAPRICORN", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        
    }//GEN-LAST:event_BtnDetalleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnDetalle;
    private javax.swing.JScrollPane PnlProveedores;
    private javax.swing.JTable TblProveedores;
    private javax.swing.JTextField TxtNit;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtNombre_Comercial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
