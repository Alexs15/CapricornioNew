/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiagSansion.java
 *
 * Created on 03-10-2011, 04:38:18 PM
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas.TablaSansiones;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;

/**
 *
 * @author julian
 */
public class DiagSansion extends javax.swing.JDialog {

    int cod_proveedor;
    private Runtime r;
    
    String proveedor;
    TablaSansiones sansion;
    private boolean sw;
    /** Creates new form DiagSansion */
    public DiagSansion(FrmMenu menu,int cod_proveedor,String proveedor) {
        super(menu, true);
        initComponents();
        this.cod_proveedor=cod_proveedor;
        this.proveedor=proveedor;
        ConstruyeTablaSansion();
    }

    private void ConstruyeTablaSansion(){
        sansion = new TablaSansiones();
        TblSansion.setAutoCreateColumnsFromModel(false);
        TblSansion.setModel(sansion);

        for (int k = 0; k < TablaSansiones.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(TablaSansiones.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, TablaSansiones.m_columns[k].m_width, renderer, null);
            TblSansion.addColumn(column);
        }
        JTableHeader header = TblSansion.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlSansion.getViewport().add(TblSansion);
    }
        
    private void LlenaSansiones(){        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getSansiones(cod_proveedor);
            CerearTablaSansion();
            
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    sansion.insert(c);
                    TblSansion.tableChanged(new TableModelEvent(sansion, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    TblSansion.setValueAt(datos[c].get("COD_PROV_SANSION"),c,0);
                    TblSansion.setValueAt(datos[c].get("FEC_INI"),c,1);                    
                    TblSansion.setValueAt(datos[c].get("FEC_FIN"),c,2);
                    TblSansion.setValueAt(datos[c].get("OBS"),c,3);                    
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}        
    }
        
    void CerearTablaSansion(){
        int f = TblSansion.getRowCount();
        for (int i=f-1;i>=0;i--){
             if (sansion.delete(i)) {
                TblSansion.tableChanged(new TableModelEvent(
                sansion, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    } 
            
    public void AbreDialogo(){
        setVisible(true);
    }
    
    public boolean SansionGenerada(){
        return sw;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlSansion = new javax.swing.JScrollPane();
        TblSansion = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        CalFecIni = new net.sf.nachocalendar.components.DateField();
        CalFecFin = new net.sf.nachocalendar.components.DateField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        BtnAceptar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtObs = new javax.swing.JTextPane();
        LblRazonSocial = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        PnlSansion.setViewportView(TblSansion);

        getContentPane().add(PnlSansion);
        PnlSansion.setBounds(20, 50, 452, 140);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        CalFecIni.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFecIni.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel2.add(CalFecIni);
        CalFecIni.setBounds(70, 10, 100, 18);

        CalFecFin.setDateFormat(DateFormat.getDateInstance(DateFormat.MEDIUM));
        CalFecFin.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel2.add(CalFecFin);
        CalFecFin.setBounds(270, 10, 100, 18);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Fecha de inicio INHABILITACION");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(30, 30, 178, 15);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("Fecha final de INHABILITACION");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(240, 30, 180, 15);

        BtnAceptar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnAceptar.setText("Aceptar");
        BtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAceptarActionPerformed(evt);
            }
        });
        jPanel2.add(BtnAceptar);
        BtnAceptar.setBounds(80, 140, 130, 30);

        BtnCancelar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCancelar);
        BtnCancelar.setBounds(250, 140, 130, 30);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Observacion"));
        jScrollPane2.setViewportView(TxtObs);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(10, 50, 430, 80);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(20, 200, 450, 180);

        LblRazonSocial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(LblRazonSocial);
        LblRazonSocial.setBounds(10, 10, 470, 20);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-503)/2, (screenSize.height-429)/2, 503, 429);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        dispose();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAceptarActionPerformed
        String obs=null;        
        if(!"".equals(TxtObs.getText().trim()))
            obs=TxtObs.getText().trim();
        else {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe introducir el NIT","SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtObs.requestFocus();
            return;
        }
        SimpleDateFormat form =new SimpleDateFormat("dd/MM/yyyy");
        String fec_ini=form.format(CalFecIni.getValue());
        String fec_fin=form.format(CalFecFin.getValue());
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.setProveedorSansion("SET-upDatePrevSansion",cod_proveedor,fec_ini,fec_fin,obs );
            javax.swing.JOptionPane.showMessageDialog(this,"DATOS ALMACENADOS","SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);  
            sw=true;
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}  
        BtnCancelar.doClick();
    }//GEN-LAST:event_BtnAceptarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        LblRazonSocial.setText(proveedor);
        LlenaSansiones();        
        sw=false;
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAceptar;
    private javax.swing.JButton BtnCancelar;
    private net.sf.nachocalendar.components.DateField CalFecFin;
    private net.sf.nachocalendar.components.DateField CalFecIni;
    private javax.swing.JLabel LblRazonSocial;
    private javax.swing.JScrollPane PnlSansion;
    private javax.swing.JTable TblSansion;
    private javax.swing.JTextPane TxtObs;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
