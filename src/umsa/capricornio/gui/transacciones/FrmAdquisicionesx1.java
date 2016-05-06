/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmAdquisiciones.java
 *
 * Created on 08-09-2011, 09:36:52 AM
 */
package umsa.capricornio.gui.transacciones;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;

/**
 *
 * @author julian
 */
public class FrmAdquisicionesx1 extends javax.swing.JInternalFrame {

    FrmMenu menu;
    FrmTransacciones frm_transaccion;
    int cod_transaccion,gestion,cod_w;    
    String tramite;
    private Runtime r;
        
    DefaultTreeModel modeloTrans = null;
    DefaultTreeModel modeloOrden = null;
    
    /** Creates new form FrmAdquisiciones */
    public FrmAdquisicionesx1(FrmMenu menu,FrmTransacciones frm_transaccion,int cod_transaccion,String tramite,int gestion,int cod_w) {
        this.menu=menu;
        this.frm_transaccion=frm_transaccion;
        this.cod_transaccion=cod_transaccion;
        this.tramite=tramite;
        this.gestion=gestion;
        this.cod_w=cod_w;
        initComponents();    
        GeneraArbolTransaccion();
        GeneraArbolOrdenCompra();
    }

    public void GeneraArbolTransaccion() {
        TreeTransaccion.setModel(cargarArbolTransaccion("Pedido de Materiales"));
    }
        
    public void GeneraArbolOrdenCompra() {
        TreeOrdenCompra.setModel(cargarArbol("Orden(es) de Compra"));
    }
        
    public DefaultTreeModel cargarArbolTransaccion(String titulo) {
        DefaultMutableTreeNode Titulo = null;
        DefaultMutableTreeNode items = null;
        DefaultMutableTreeNode detalle = null;

        Titulo = new DefaultMutableTreeNode(titulo);
        modeloTrans = new DefaultTreeModel(Titulo);
        
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)TreeTransaccion.getCellRenderer();
        render.setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png")));
        render.setOpenIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_open.png")));
        render.setClosedIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book.png")));        
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos=puerto.getItems(cod_transaccion);
            String elemento ="";
            int ci=0,cd=0;
            if (datos!=null){
                for (int c=0;c<datos.length;c++){ 
                    /*elemento=datos[c].get("ARTICULO").toString();
                    if (!"".equals(datos[c].get("TIPO_ITEM"))){
                        items= new DefaultMutableTreeNode(elemento);
                        modeloTrans.insertNodeInto(items, Titulo, ci);
                        ci++;cd=0;
                    }
                    else {
                        detalle = new DefaultMutableTreeNode(elemento);
                        modeloTrans.insertNodeInto(detalle, items, cd);
                        cd++;
                    }  */                  
                    elemento=datos[c].get("ARTICULO").toString();
                    if (!"".equals(datos[c].get("TIPO_ITEM"))){
                        items= new DefaultMutableTreeNode(elemento);
                        modeloTrans.insertNodeInto(items, Titulo, ci);
                        ci++;cd=0;
                    }
                    /*else {
                        detalle = new DefaultMutableTreeNode(elemento);
                        modeloTrans.insertNodeInto(detalle, items, cd);
                        cd++;
                    }*/
                }                                
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        return modeloTrans;
    }

    public DefaultTreeModel cargarArbol(String titulo) {
        DefaultMutableTreeNode Titulo = null;
        DefaultMutableTreeNode items = null;
        DefaultMutableTreeNode detalle = null;

        Titulo = new DefaultMutableTreeNode(titulo);
        modeloOrden = new DefaultTreeModel(Titulo);
        
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)TreeOrdenCompra.getCellRenderer();
        render.setLeafIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png")));
        render.setOpenIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book_open.png")));
        render.setClosedIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/book.png")));                
        return modeloOrden;
    }   
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TreeTransaccion = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        TreeOrdenCompra = new javax.swing.JTree();
        BtnSalir = new javax.swing.JButton();
        BtnNuevaOrden = new javax.swing.JButton();
        BtnEliminaOrden = new javax.swing.JButton();
        BtnAgregar = new javax.swing.JButton();
        BtnDevolver = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

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

        TreeTransaccion.setFont(new java.awt.Font("Arial", 0, 11));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("colors");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sports");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("food");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("pizza");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ravioli");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("bananas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        TreeTransaccion.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(TreeTransaccion);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 50, 250, 210);

        TreeOrdenCompra.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jScrollPane2.setViewportView(TreeOrdenCompra);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(330, 50, 250, 210);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 11));
        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSalir);
        BtnSalir.setBounds(310, 280, 70, 23);

        BtnNuevaOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/folder_add.png"))); // NOI18N
        BtnNuevaOrden.setToolTipText("Crea Orden de Compra");
        BtnNuevaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevaOrdenActionPerformed(evt);
            }
        });
        getContentPane().add(BtnNuevaOrden);
        BtnNuevaOrden.setBounds(350, 20, 30, 25);

        BtnEliminaOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/folder_delete.png"))); // NOI18N
        BtnEliminaOrden.setToolTipText("Elimina Orden de Compra");
        BtnEliminaOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminaOrdenActionPerformed(evt);
            }
        });
        getContentPane().add(BtnEliminaOrden);
        BtnEliminaOrden.setBounds(380, 20, 30, 25);

        BtnAgregar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnAgregar.setText(">");
        BtnAgregar.setToolTipText("Agrega El item elejido del pedido");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAgregar);
        BtnAgregar.setBounds(270, 80, 50, 23);

        BtnDevolver.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnDevolver.setText("<");
        BtnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDevolverActionPerformed(evt);
            }
        });
        getContentPane().add(BtnDevolver);
        BtnDevolver.setBounds(270, 120, 50, 23);

        jButton3.setText("Generar Ordenes de Compra");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(120, 280, 130, 23);

        setBounds(0, 0, 606, 345);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        menu.CerrarOtroFrame(this,frm_transaccion);
            System.gc();
            r = Runtime.getRuntime();
            long mem1 = r.freeMemory();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
       // AgregaElementosArbol();
        //LlenaItemsArbol();
    }//GEN-LAST:event_formInternalFrameOpened

    public void imprimirNodos(TreeNode nodo) {
        System.out.println( nodo.getChildCount()+" "+nodo.toString());

        if (nodo.getChildCount() >= 0) {
            for (Enumeration e=nodo.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                imprimirNodos(n);
            }
        }
    }
    
    private void BtnEliminaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminaOrdenActionPerformed
        try{
            DefaultMutableTreeNode parentNode = null;
            TreePath parentPath = TreeOrdenCompra.getSelectionPath();
            if (parentPath != null)        
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());              
           
            System.out.println(parentPath.getPathCount()+" "+parentPath.getPathComponent(2)+" "+parentPath.getLastPathComponent()+" "+modeloOrden.getIndexOfChild(parentPath.getPathComponent(2), parentPath.getLastPathComponent()));
            System.out.println(parentPath.getPathCount()+" "+parentPath.getPathComponent(1)+" "+parentPath.getLastPathComponent()+" "+modeloOrden.getIndexOfChild(parentPath.getPathComponent(1), parentPath.getLastPathComponent()));
            System.out.println(parentPath.getPathCount()+" "+parentPath.getPathComponent(0)+" "+ parentPath.getLastPathComponent()+" "+modeloOrden.getIndexOfChild(parentPath.getPathComponent(0), parentPath.getLastPathComponent()));

            TreeNode raiz = (TreeNode)TreeOrdenCompra.getModel().getRoot();
            imprimirNodos( raiz );

            
            
            if (parentPath.getPathCount()==2){
                //if (parentPath.getPathCount()==3)
                    modeloOrden.removeNodeFromParent(parentNode);
                //else
                //   javax.swing.JOptionPane.showMessageDialog(this,"Existe Items En la ORDEN DE COMPRA","Proyectos Presupuestarios",
                //   javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            else
                javax.swing.JOptionPane.showMessageDialog(this,"Solo se pueden eliminar las ordenes de compra","Proyectos Presupuestarios",
                javax.swing.JOptionPane.ERROR_MESSAGE);            

        }
        catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe Elejir el ITEM adecuado antes de eliminar el orden de compra","Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnEliminaOrdenActionPerformed

    private void BtnNuevaOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevaOrdenActionPerformed
        TreeOrdenCompra.setSelectionRow(0);        
            DefaultMutableTreeNode parentNode = null;                        
            TreePath parentPath = TreeOrdenCompra.getSelectionPath();            
            if (parentPath == null) {                
            } else {
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
            }
            String tit ="Orden de Compra - "+parentNode.getChildCount();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(tit);        
            modeloOrden.insertNodeInto(child, parentNode, parentNode.getChildCount());                
        TreeTransaccion.repaint();
    }//GEN-LAST:event_BtnNuevaOrdenActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        TreePath nodo_trans = TreeTransaccion.getSelectionPath();
        //System.out.println(nodo_trans.getPathComponent(1));
        try {
            DefaultMutableTreeNode parentNode = null;
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodo_trans.getPathComponent(1));        
            TreePath parentPath = TreeOrdenCompra.getSelectionPath();
            
            if (parentPath.getPathCount()==2) {
                if (parentPath != null) 
                    parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
                modeloOrden.insertNodeInto(child, parentNode, parentNode.getChildCount());
                
                DefaultMutableTreeNode parentNode1 = null;                                
                parentNode1 = (DefaultMutableTreeNode) (nodo_trans.getLastPathComponent());
                modeloTrans.removeNodeFromParent(parentNode1);
            }
        }
        catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe Elejir el ITEM adecuado antes de separar la orden de compra","Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (NullPointerException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe Elejir a que orden de compra adicionara el item","Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        TreeTransaccion.repaint();
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void BtnDevolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDevolverActionPerformed
        try{
            TreePath nodo_trans = TreeOrdenCompra.getSelectionPath();
            DefaultMutableTreeNode parentNode1 = null;
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodo_trans.getPathComponent(2));     
            //TreeTransaccion.setSelectionInterval(0, 0);
            TreePath parentPath1 = TreeTransaccion.getSelectionPath();
            
            if (parentPath1.getPathCount()==1) {
                if (parentPath1 != null) 
                    parentNode1 = (DefaultMutableTreeNode) (parentPath1.getLastPathComponent());
                modeloTrans.insertNodeInto(child, parentNode1, parentNode1.getChildCount());
            } 
            else {
                javax.swing.JOptionPane.showMessageDialog(this,"Debe Marcar PEDIDOR DE MATERIALES si desea devolver el item","Proyectos Presupuestarios",
                javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            DefaultMutableTreeNode parentNode = null;
            TreePath parentPath = TreeOrdenCompra.getSelectionPath();
            if (parentPath != null)        
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());              
            if (parentPath.getPathCount()==3)
                modeloOrden.removeNodeFromParent(parentNode);
            else 
                javax.swing.JOptionPane.showMessageDialog(this,"Solo se puede mover los items de la orden de compra","Proyectos Presupuestarios",
                javax.swing.JOptionPane.ERROR_MESSAGE);

        }
        catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Debe Elejir el ITEM adecuado antes de eliminar el orden de compra","Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        } 
        catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"Debe Elejir PEDIDO antes de devolver el item","Proyectos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnDevolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnDevolver;
    private javax.swing.JButton BtnEliminaOrden;
    private javax.swing.JButton BtnNuevaOrden;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JTree TreeOrdenCompra;
    private javax.swing.JTree TreeTransaccion;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
