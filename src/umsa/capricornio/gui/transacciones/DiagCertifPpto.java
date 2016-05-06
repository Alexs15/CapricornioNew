/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiagCertifPpto.java
 *
 * Created on 21-11-2011, 06:10:27 PM
 */
package umsa.capricornio.gui.transacciones;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectPPTO.PptoWSServiceLocator;
import umsa.capricornio.gui.ConnectPPTO.PptoWS_PortType;
import umsa.capricornio.domain.Ppto;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.tablas.TablaSolPptoConcluidos;

/**
 *
 * @author julian
 */
public class DiagCertifPpto extends javax.swing.JDialog {

    TablaSolPptoConcluidos datos_ppto;
    
    int gestion;    
    private Runtime r;
    
    Ppto ppto;
    
    /** Creates new form DiagCertifPpto */
    public DiagCertifPpto(FrmMenu menu, int gestion) {
        super(menu, true);
        initComponents();
        this.gestion=gestion;
        ConstruyeTablaSolicitudesPpto();
    }
    
    private void ConstruyeTablaSolicitudesPpto(){
        datos_ppto = new TablaSolPptoConcluidos();
        TblCertifPpto.setAutoCreateColumnsFromModel(false);
        TblCertifPpto.setModel(datos_ppto);

        for (int k = 0; k < TablaSolPptoConcluidos.m_columns.length; k++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaSolPptoConcluidos.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaSolPptoConcluidos.m_columns[k].m_width, renderer, null);
            TblCertifPpto.addColumn(column);
        }
        JTableHeader header = TblCertifPpto.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlCertif.getViewport().add(TblCertifPpto);
    }
        
    void CerearTablaSolicitud(){
        int f = TblCertifPpto.getRowCount();
        for (int i=f-1  ;i>=0;i--){
             if (datos_ppto.delete(i)) {
                TblCertifPpto.tableChanged(new TableModelEvent(
                datos_ppto, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
             }
        }
    }
    private void LlenaSolicitudesConcluidas(){
        String hojaruta=null;
        String nro = null;
        String Palabra = null;
        if ("".equals(TxtSolPpto.getText())
                && "".equals(TxtHojaRuta.getText())
                && "".equals(TxtDetalle.getText())){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> NO INTRODUJO NINGUN PARAMETRO DE BUSQUEDA,<BR> DEBE INTRODUCIR POR LO MENOS UNO","CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"".equals(TxtSolPpto.getText())){
            try { int n = new Integer(TxtSolPpto.getText().trim());
                  nro="'"+TxtSolPpto.getText().trim()+"'";
            }
            catch (NumberFormatException e) {}
        }
        if (!"".equals(TxtHojaRuta.getText()))
                hojaruta="'"+TxtHojaRuta.getText().trim()+"'";

        if (!"".equals(TxtDetalle.getText()))
                Palabra="'"+TxtDetalle.getText().trim()+"'";
        try{
            PptoWSServiceLocator servicio = new PptoWSServiceLocator();
            PptoWS_PortType puerto = servicio.getPptoWS();
            CerearTablaSolicitud();
            Map[] datos=puerto.getSolicitudesPptoConcluidos(nro,hojaruta,Palabra,gestion);
            if (datos!=null){                
                for (int c=0;c<datos.length;c++){
                    datos_ppto.insert(c);
                    TblCertifPpto.tableChanged(new TableModelEvent(datos_ppto, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    /*TblCertifPpto.setValueAt(datos[c].get("id_sol"),c,0);
                    TblCertifPpto.setValueAt(datos[c].get("apert"),c,1);
                    TblCertifPpto.setValueAt(datos[c].get("hoja_ruta"),c,2);
                    TblCertifPpto.setValueAt(datos[c].get("detalle"),c,3);
                    TblCertifPpto.setValueAt(datos[c].get("monto_ejec"),c,4);*/
                    TblCertifPpto.setValueAt(datos[c].get("id_sol"),c,0);
                    TblCertifPpto.setValueAt(datos[c].get("hoja_ruta"),c,1);
                    TblCertifPpto.setValueAt(datos[c].get("apert"),c,2);
                    TblCertifPpto.setValueAt(datos[c].get("detalle"),c,3);
                    TblCertifPpto.setValueAt(datos[c].get("fuente"),c,4);
                    TblCertifPpto.setValueAt(datos[c].get("monto_ejec"),c,5);                    
                }
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }
        
    public void AbreDialogo(){
        setVisible(true);
    }
     
    public Ppto Certif_Ppto() {
        return ppto;
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
        TxtSolPpto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        TxtHojaRuta = new javax.swing.JTextField();
        TxtDetalle = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        PnlCertif = new javax.swing.JScrollPane();
        TblCertifPpto = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(217, 228, 216));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        TxtSolPpto.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TxtSolPpto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtSolPptoKeyPressed(evt);
            }
        });
        jPanel1.add(TxtSolPpto);
        TxtSolPpto.setBounds(60, 10, 80, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Sol. Ppto");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 30, 70, 14);

        TxtHojaRuta.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TxtHojaRuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtHojaRutaKeyPressed(evt);
            }
        });
        jPanel1.add(TxtHojaRuta);
        TxtHojaRuta.setBounds(210, 10, 100, 20);

        TxtDetalle.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TxtDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtDetalleKeyPressed(evt);
            }
        });
        jPanel1.add(TxtDetalle);
        TxtDetalle.setBounds(380, 10, 200, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setText("Detalle de transaccion");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(380, 30, 120, 14);

        BtnBuscar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(0, 102, 51));
        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/magnifier.png"))); // NOI18N
        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnBuscar);
        BtnBuscar.setBounds(650, 10, 120, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("Hoja Ruta");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(210, 30, 70, 14);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 830, 50);

        TblCertifPpto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblCertifPpto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblCertifPptoMouseClicked(evt);
            }
        });
        TblCertifPpto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TblCertifPptoKeyPressed(evt);
            }
        });
        PnlCertif.setViewportView(TblCertifPpto);

        getContentPane().add(PnlCertif);
        PnlCertif.setBounds(10, 60, 830, 110);

        jPanel2.setBackground(new java.awt.Color(217, 228, 216));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(0, 102, 51));
        BtnSalir.setMnemonic('S');
        BtnSalir.setText("Cancelar");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(BtnSalir);
        BtnSalir.setBounds(350, 10, 120, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 170, 830, 50);

        setSize(new java.awt.Dimension(867, 263));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtSolPptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSolPptoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
}//GEN-LAST:event_TxtSolPptoKeyPressed

    private void TxtHojaRutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtHojaRutaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
}//GEN-LAST:event_TxtHojaRutaKeyPressed

    private void TxtDetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDetalleKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
}//GEN-LAST:event_TxtDetalleKeyPressed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        LlenaSolicitudesConcluidas();
        TxtDetalle.setText("");
        TxtHojaRuta.setText("");
        TxtSolPpto.setText(""); 
}//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        dispose();
}//GEN-LAST:event_BtnSalirActionPerformed

    private void TblCertifPptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TblCertifPptoKeyPressed
         
    }//GEN-LAST:event_TblCertifPptoKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ppto =new Ppto();
    }//GEN-LAST:event_formWindowOpened

    private void TblCertifPptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblCertifPptoMouseClicked
        if (evt.getClickCount() == 2){
            int fila = TblCertifPpto.getSelectedRow();
            ppto.setCertif_ppto(TblCertifPpto.getValueAt(fila, 0).toString());
            ppto.setHoja_ruta(TblCertifPpto.getValueAt(fila, 1).toString());   
            ppto.setMonto(Double.parseDouble(TblCertifPpto.getValueAt(fila, 5).toString()));
            ppto.setFuente(TblCertifPpto.getValueAt(fila, 4).toString());
            BtnSalir.doClick();
         }
    }//GEN-LAST:event_TblCertifPptoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JScrollPane PnlCertif;
    private javax.swing.JTable TblCertifPpto;
    private javax.swing.JTextField TxtDetalle;
    private javax.swing.JTextField TxtHojaRuta;
    private javax.swing.JTextField TxtSolPpto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
