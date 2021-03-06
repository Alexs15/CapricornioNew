/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmIngresoMateriales.java
 *
 * Created on 28-07-2011, 02:44:14 PM
 */
package umsa.capricornio.gui.transacciones.XXXIngresoMateriales;

import java.text.DecimalFormat;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import umsa.capricornio.domain.Items;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.gui.transacciones.FrmTransacciones;
import umsa.capricornio.gui.transacciones.XXXIngresoMateriales.tabla.XXXTablaItemsIngAlm;
import umsa.capricornio.utilitarios.herramientas.MiRenderer;

/**
 *
 * @author julian
 */
public class XXXFrmIngresoMateriales extends javax.swing.JInternalFrame {

    XXXTablaItemsIngAlm items;
    FrmMenu menu;
    private Runtime r;
    int cod_almacen;
    private int cod_item;
    private String tipo_item;
    FrmTransacciones frm_transaccion;
    /** Creates new form FrmIngresoMateriales */
    public XXXFrmIngresoMateriales(FrmMenu menu,FrmTransacciones frm_transaccion,int cod_almacen) {
        this.menu=menu;
        this.frm_transaccion=frm_transaccion;
        this.cod_almacen=cod_almacen;
        initComponents();
        ConstruyeTablaItems();
    }

    private void ConstruyeTablaItems(){
        items = new XXXTablaItemsIngAlm(this);
        TblItems.setAutoCreateColumnsFromModel(false);
        TblItems.setModel(items);

        for (int k = 0; k < XXXTablaItemsIngAlm.m_columns.length; k++) {
            TableCellRenderer renderer = new MiRenderer(XXXTablaItemsIngAlm.m_columns[k].m_alignment);
            /*DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(DatosTablaObligacionBandeja.m_columns[k].m_alignment);*/
            TableColumn column = new TableColumn(k, XXXTablaItemsIngAlm.m_columns[k].m_width, renderer, null);
            TblItems.addColumn(column);
        }
        JTableHeader header = TblItems.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        PnlItems.getViewport().add(TblItems);
    }
    
    
    public void MultiplicaCantidadPrecioUnit(int fila){
        DecimalFormat formatter = new DecimalFormat("###.00");
        try {
            if (!"".equals(TblItems.getValueAt(fila, 6).toString())){
                double cantidad=Double.parseDouble(TblItems.getValueAt(fila, 2).toString());
                double precio_unit=Double.parseDouble(TblItems.getValueAt(fila, 6).toString());                    
                String numero=(String) formatter.format(cantidad*precio_unit);
                TblItems.setValueAt(numero.replace(',','.'), fila, 7);
            }
            else TblItems.setValueAt("", fila, 7);
            TblItems.repaint();
        }
        catch(NumberFormatException e){}
    }
    
    void CerrarFrame(){
        menu.CerrarOtroFrame(this,frm_transaccion);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlItems = new javax.swing.JScrollPane();
        TblItems = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtArticulo = new javax.swing.JTextField();
        TxtUnidadMedida = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtCantidad = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        TxtPrecioUnit = new javax.swing.JTextField();
        BtnAgregar = new javax.swing.JButton();
        BtnAgregardetalles = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        TxtProveedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtOrdenCompra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtFactura = new javax.swing.JTextField();
        TxtNit = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtPreventivo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TxtMemo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        TxtHojaRuta = new javax.swing.JTextField();
        CaFeclOrdCompra = new net.sf.nachocalendar.components.DateField();
        CalFecFactura = new net.sf.nachocalendar.components.DateField();
        CalFecMemo = new net.sf.nachocalendar.components.DateField();

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

        PnlItems.setViewportView(TblItems);

        getContentPane().add(PnlItems);
        PnlItems.setBounds(10, 10, 820, 160);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Item");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(50, 30, 50, 14);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Unidad Medida");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(530, 30, 80, 14);

        TxtArticulo.setEditable(false);
        TxtArticulo.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.add(TxtArticulo);
        TxtArticulo.setBounds(50, 10, 470, 20);

        TxtUnidadMedida.setEditable(false);
        TxtUnidadMedida.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.add(TxtUnidadMedida);
        TxtUnidadMedida.setBounds(530, 10, 140, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Cantidad");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(680, 30, 50, 14);

        TxtCantidad.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.add(TxtCantidad);
        TxtCantidad.setBounds(680, 10, 60, 20);

        BtnBuscar.setFont(new java.awt.Font("Arial", 0, 11));
        BtnBuscar.setText("B");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnBuscar);
        BtnBuscar.setBounds(10, 10, 40, 23);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel4.setText("Precio.Unit");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(750, 30, 60, 14);

        TxtPrecioUnit.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel1.add(TxtPrecioUnit);
        TxtPrecioUnit.setBounds(750, 10, 60, 20);

        BtnAgregar.setText("Agregar");
        jPanel1.add(BtnAgregar);
        BtnAgregar.setBounds(350, 50, 120, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 220, 820, 80);

        BtnAgregardetalles.setFont(new java.awt.Font("Arial", 0, 11));
        BtnAgregardetalles.setText("Agregar Item");
        BtnAgregardetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregardetallesActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAgregardetalles);
        BtnAgregardetalles.setBounds(700, 170, 130, 23);

        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir);
        BtnSalir.setBounds(370, 470, 73, 23);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Proveedor :");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 10, 90, 20);

        TxtProveedor.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtProveedor);
        TxtProveedor.setBounds(120, 10, 680, 20);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Orden de Compra :");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 40, 100, 20);

        TxtOrdenCompra.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtOrdenCompra);
        TxtOrdenCompra.setBounds(120, 40, 100, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Fecha Ord.Comp :");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(230, 40, 100, 20);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Factura :");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 70, 90, 20);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Fecha Factura :");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(230, 70, 100, 20);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("NIT :");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(450, 70, 60, 20);

        TxtFactura.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtFactura);
        TxtFactura.setBounds(120, 70, 100, 20);

        TxtNit.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtNit);
        TxtNit.setBounds(520, 70, 120, 20);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Preventivo :");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(10, 100, 100, 20);

        TxtPreventivo.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtPreventivo);
        TxtPreventivo.setBounds(120, 100, 100, 20);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Memo  :");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(220, 100, 110, 20);

        TxtMemo.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtMemo);
        TxtMemo.setBounds(340, 100, 80, 20);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Fecha :");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(450, 100, 60, 20);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 11));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Hoja Ruta :");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(650, 100, 70, 20);

        TxtHojaRuta.setFont(new java.awt.Font("Arial", 0, 11));
        jPanel2.add(TxtHojaRuta);
        TxtHojaRuta.setBounds(730, 100, 70, 20);
        jPanel2.add(CaFeclOrdCompra);
        CaFeclOrdCompra.setBounds(340, 40, 80, 20);
        jPanel2.add(CalFecFactura);
        CalFecFactura.setBounds(340, 70, 80, 20);
        jPanel2.add(CalFecMemo);
        CalFecMemo.setBounds(520, 100, 80, 20);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 320, 820, 140);

        setBounds(0, 0, 855, 531);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        CerrarFrame();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        XXXDiagBuscaItems articulo= new XXXDiagBuscaItems(menu,cod_almacen);
        articulo.AbreDialogo();
        if (articulo.AceptaDatos()==true){
            Items item = articulo.ItemSeleccionado();
            cod_item =item.getCod_item();
            TxtArticulo.setText(item.getArticulo());
            TxtUnidadMedida.setText(item.getUnidad_medida());
            tipo_item=item.getTipo_item();
        }
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnAgregardetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregardetallesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAgregardetallesActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
                // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnAgregardetalles;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnSalir;
    private net.sf.nachocalendar.components.DateField CaFeclOrdCompra;
    private net.sf.nachocalendar.components.DateField CalFecFactura;
    private net.sf.nachocalendar.components.DateField CalFecMemo;
    private javax.swing.JScrollPane PnlItems;
    private javax.swing.JTable TblItems;
    private javax.swing.JTextField TxtArticulo;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtFactura;
    private javax.swing.JTextField TxtHojaRuta;
    private javax.swing.JTextField TxtMemo;
    private javax.swing.JTextField TxtNit;
    private javax.swing.JTextField TxtOrdenCompra;
    private javax.swing.JTextField TxtPrecioUnit;
    private javax.swing.JTextField TxtPreventivo;
    private javax.swing.JTextField TxtProveedor;
    private javax.swing.JTextField TxtUnidadMedida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
