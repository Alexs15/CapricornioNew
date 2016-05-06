/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JD_Preventivo.java
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
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.ConnectSigma.SigmaCapriWSServiceLocator;
import umsa.capricornio.gui.ConnectSigma.SigmaCapriWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.tablas.TablaPreventivo;

/**
 *
 * @author henrry
 */
public class JD_Preventivo extends javax.swing.JDialog {

    TablaPreventivo datos_ppto;
    
    int gestion;
    int cod_transaccion;
    private Runtime r;
    
    Ppto ppto;
    
    /** Creates new form DiagCertifPpto */
    public JD_Preventivo(FrmMenu menu, int gestion, int cod_transaccion) {
        super(menu, true);
        initComponents();
        this.gestion=gestion;
        this.cod_transaccion=cod_transaccion;
        this.JTA_Roperacion.setEditable(false);
        ConstruyeTablaSolicitudesPpto();
    }
    
    private void ConstruyeTablaSolicitudesPpto(){
        datos_ppto = new TablaPreventivo();
        TblCertifPpto.setAutoCreateColumnsFromModel(false);
        TblCertifPpto.setModel(datos_ppto);

        for (int k = 0; k < TablaPreventivo.m_columns.length; k++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaPreventivo.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaPreventivo.m_columns[k].m_width, renderer, null);
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
        String preventivo = null;
        String da = null;
        String concepto = null;
        
        
        if ("".equals(JT_Preventivo.getText())
                && "".equals(JT_Da.getText())
                && "".equals(JT_Concepto.getText())){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> NO INTRODUJO NINGUN PARAMETRO DE BUSQUEDA,<BR> DEBE INTRODUCIR POR LO MENOS UNO","CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"".equals(JT_Preventivo.getText())){
            try { 
                //int n = new Integer(TxtSolPpto.getText().trim());
//                nro="'"+TxtSolPpto.getText().trim()+"'";
                preventivo = JT_Preventivo.getText().trim();
            }
            catch (NumberFormatException e) {}
        }
        if (!"".equals(JT_Da.getText()))
                da=JT_Da.getText().trim();

        if (!"".equals(JT_Concepto.getText()))
                concepto="'"+JT_Concepto.getText().trim()+"'";
        try{
            System.out.println("Entro Majete!!!! - "+da+" - "+preventivo);
//            PptoWSServiceLocator servicio = new PptoWSServiceLocator();
//            PptoWS_PortType puerto = servicio.getPptoWS();
            SigmaCapriWSServiceLocator servicio = new SigmaCapriWSServiceLocator();
            SigmaCapriWS_PortType puerto = servicio.getSigmaCapriWS();
            CerearTablaSolicitud();
            //Map[] datos=puerto.getSolicitudesPptoConcluidos(nro,hojaruta,Palabra,gestion);
            Map[] datos=puerto.getComprobantesGastoSIGMA("2016", da, preventivo);
//            Map [] datos = null;
            if (datos!=null){
                this.JTA_Roperacion.setText(datos[0].get("RESUMEN_OPERACION").toString().trim());
                this.JT_TotalA.setText(datos[0].get("TOTAL_AUTORIZADO").toString().trim());
                this.JT_Retenciones.setText(datos[0].get("TOTAL_DEDUCCIONES").toString().trim());
                this.JT_Multas.setText(datos[0].get("TOTAL_MULTAS").toString().trim());
                this.JT_LPagable.setText(datos[0].get("LIQUIDO_PAGABLE_CONSOLIDADO").toString().trim());
                for (int c=0;c<datos.length;c++){
                    datos_ppto.insert(c);
                    TblCertifPpto.tableChanged(new TableModelEvent(datos_ppto, c, c, TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT));
                    /*TblCertifPpto.setValueAt(datos[c].get("id_sol"),c,0);
                    TblCertifPpto.setValueAt(datos[c].get("apert"),c,1);
                    TblCertifPpto.setValueAt(datos[c].get("hoja_ruta"),c,2);
                    TblCertifPpto.setValueAt(datos[c].get("detalle"),c,3);
                    TblCertifPpto.setValueAt(datos[c].get("monto_ejec"),c,4);*/
//                    TblCertifPpto.setValueAt(datos[c].get("id_sol"),c,0);
//                    TblCertifPpto.setValueAt(datos[c].get("hoja_ruta"),c,1);
//                    TblCertifPpto.setValueAt(datos[c].get("apert"),c,2);
//                    TblCertifPpto.setValueAt(datos[c].get("detalle"),c,3);
//                    TblCertifPpto.setValueAt(datos[c].get("fuente"),c,4);
                    TblCertifPpto.setValueAt(datos[c].get("PROGRAMA"),c,0);
                    TblCertifPpto.setValueAt(datos[c].get("PROYECTO"),c,1);
                    TblCertifPpto.setValueAt(datos[c].get("ACTIVIDAD"),c,2);
                    TblCertifPpto.setValueAt(datos[c].get("OBJETO_DEL_GASTO"),c,3);
                    TblCertifPpto.setValueAt(datos[c].get("IMPORTE"),c,4);
                    System.out.println(c+" UE - "+datos[c].get("UE"));
                    System.out.println(c+" PROGRAMA - "+datos[c].get("PROGRAMA"));
                    System.out.println(c+" PROYECTO - "+datos[c].get("PROYECTO"));
                    System.out.println(c+" ACTIVIDAD - "+datos[c].get("ACTIVIDAD"));
                    System.out.println(c+" DOC_PREVENTIVO - "+datos[c].get("DOC_PREVENTIVO"));
                    System.out.println(c+" RESUMEN_OPERACION - "+datos[c].get("RESUMEN_OPERACION"));
                    System.out.println(c+" TOTAL_AUTORIZADO - "+datos[c].get("TOTAL_AUTORIZADO"));
                    System.out.println(c+" TOTAL_DEDUCCIONES - "+datos[c].get("TOTAL_DEDUCCIONES"));
                    System.out.println(c+" TOTAL_MULTAS - "+datos[c].get("TOTAL_MULTAS"));
                    System.out.println(c+" LIQUIDO_PAGABLE - "+datos[c].get("LIQUIDO_PAGABLE"));
                    System.out.println(c+" SUMA_MODIF_AUTORIZADO - "+datos[c].get("SUMA_MODIF_AUTORIZADO"));
                    System.out.println(c+" SUMA_MODIF_DEDUCCIONES - "+datos[c].get("SUMA_MODIF_DEDUCCIONES"));
                    System.out.println(c+" LIQUIDO_PAGABLE_CONSOLIDADO - "+datos[c].get("LIQUIDO_PAGABLE_CONSOLIDADO"));
                    System.out.println(c+" MONTO_RETENCIONES_PAGADO - "+datos[c].get("MONTO_RETENCIONES_PAGADO"));
                    System.out.println(c+" PAGOS_NO_ENTREGADOS - "+datos[c].get("PAGOS_NO_ENTREGADOS"));
                    System.out.println(c+" PAGOS_ENTREGADOS - "+datos[c].get("PAGOS_ENTREGADOS"));
                    System.out.println(c+" LIQUIDO_PAGABLE_ENTREGADO - "+datos[c].get("LIQUIDO_PAGABLE_ENTREGADO"));
                    System.out.println(c+" API_ESTADO - "+datos[c].get("API_ESTADO"));
                    System.out.println(c+" FUENTE - "+datos[c].get("FUENTE"));
                    System.out.println(c+" ORGANISMO - "+datos[c].get("ORGANISMO"));
                    System.out.println(c+" OBJETO_DEL_GASTO - "+datos[c].get("OBJETO_DEL_GASTO"));
                    System.out.println(c+" IMPORTE - "+datos[c].get("IMPORTE"));
                    System.out.println(c+" FEC_CRE - "+datos[c].get("FEC_CRE"));
                    System.out.println(c+" PREVENTIVO - "+datos[c].get("PREVENTIVO"));
                    System.out.println(c+" COMPROMISO - "+datos[c].get("COMPROMISO"));
                    System.out.println(c+" DEVENGADO - "+datos[c].get("DEVENGADO"));
                    System.out.println(c+" PAGO - "+datos[c].get("PAGO"));
                    
                    
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
        jLabel1 = new javax.swing.JLabel();
        JT_Da = new javax.swing.JTextField();
        JT_Concepto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        JT_Preventivo = new javax.swing.JTextField();
        PnlCertif = new javax.swing.JScrollPane();
        TblCertifPpto = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BtnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        JT_LPagable = new javax.swing.JTextField();
        JT_TotalA = new javax.swing.JTextField();
        JT_Retenciones = new javax.swing.JTextField();
        JT_Multas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTA_Roperacion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Preventivo");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(230, 30, 60, 14);

        JT_Da.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        JT_Da.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JT_DaKeyPressed(evt);
            }
        });
        jPanel1.add(JT_Da);
        JT_Da.setBounds(40, 10, 100, 20);

        JT_Concepto.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        JT_Concepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JT_ConceptoKeyPressed(evt);
            }
        });
        jPanel1.add(JT_Concepto);
        JT_Concepto.setBounds(380, 10, 280, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setText("Concepto");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(490, 30, 60, 14);

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
        BtnBuscar.setBounds(740, 10, 120, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("DA");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 30, 30, 14);

        JT_Preventivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JT_PreventivoActionPerformed(evt);
            }
        });
        jPanel1.add(JT_Preventivo);
        JT_Preventivo.setBounds(210, 10, 90, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 950, 80);

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
        PnlCertif.setBounds(10, 140, 950, 170);

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/arrow_left.png"))); // NOI18N
        BtnSalir.setMnemonic('S');
        BtnSalir.setText("Cancelar");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(BtnSalir);
        BtnSalir.setBounds(530, 60, 119, 25);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/add.png"))); // NOI18N
        jButton1.setText(" Preventivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(370, 60, 130, 25);

        jLabel4.setText("Total Autorizado:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 10, 110, 14);

        jLabel5.setText("Total Retenciones:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 40, 120, 14);

        jLabel6.setText("Total Multas:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 70, 80, 14);

        jLabel7.setText("Liquido Pagable:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 100, 100, 14);

        JT_LPagable.setEditable(false);
        JT_LPagable.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(JT_LPagable);
        JT_LPagable.setBounds(130, 100, 130, 20);

        JT_TotalA.setEditable(false);
        JT_TotalA.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(JT_TotalA);
        JT_TotalA.setBounds(130, 10, 130, 20);

        JT_Retenciones.setEditable(false);
        JT_Retenciones.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(JT_Retenciones);
        JT_Retenciones.setBounds(130, 40, 130, 20);

        JT_Multas.setEditable(false);
        JT_Multas.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.add(JT_Multas);
        JT_Multas.setBounds(130, 70, 130, 20);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 310, 950, 150);

        JTA_Roperacion.setColumns(20);
        JTA_Roperacion.setRows(5);
        jScrollPane1.setViewportView(JTA_Roperacion);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 90, 950, 50);

        setSize(new java.awt.Dimension(989, 509));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JT_DaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_DaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
}//GEN-LAST:event_JT_DaKeyPressed

    private void JT_ConceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JT_ConceptoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnBuscar.doClick();
        }
}//GEN-LAST:event_JT_ConceptoKeyPressed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        LlenaSolicitudesConcluidas();
//        JT_Concepto.setText("");
//        JT_Da.setText("");
//        JT_Preventivo.setText(""); 
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

    private void JT_PreventivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JT_PreventivoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_JT_PreventivoActionPerformed

    private void guardarPreventivo(int cod_transaccion, String cod_preventivo, String resumen,int da, String total){
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            System.out.println("el cod_transaccion es: "+cod_transaccion+", cod_preventivo: "+cod_preventivo+", resumen: "+resumen+", da: "+da+", total: "+total);
            puerto.addPreventivo("SET-addPreventivo", cod_transaccion, cod_preventivo, resumen,da,total);
            javax.swing.JOptionPane.showMessageDialog(this,"<html> Se adiciono el preventivo correctamente <br> ","SYSTEM CAPRICORNIO",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"Error!!! "+e,"SYSTEM CAPRICORNIO",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println("El cod_transaccion es: "+cod_transaccion+" , preventivo: "+this.JT_Preventivo.getText().toString().trim());
        String preventivo = this.JT_Preventivo.getText().toString().trim();
        System.out.println("El preventivo es: "+this.JT_TotalA.getText().trim().replace(".", ","));
        this.guardarPreventivo(cod_transaccion, preventivo, this.JTA_Roperacion.getText().trim(),Integer.parseInt(this.JT_Da.getText().trim()),this.JT_TotalA.getText().trim());
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JTextArea JTA_Roperacion;
    private javax.swing.JTextField JT_Concepto;
    private javax.swing.JTextField JT_Da;
    private javax.swing.JTextField JT_LPagable;
    private javax.swing.JTextField JT_Multas;
    private javax.swing.JTextField JT_Preventivo;
    private javax.swing.JTextField JT_Retenciones;
    private javax.swing.JTextField JT_TotalA;
    private javax.swing.JScrollPane PnlCertif;
    private javax.swing.JTable TblCertifPpto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
