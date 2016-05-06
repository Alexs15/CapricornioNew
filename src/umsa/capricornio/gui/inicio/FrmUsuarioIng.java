/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmUsuario.java
 *
 * Created on 01-jun-2011, 11:46:15
 */
package umsa.capricornio.gui.inicio;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.utilitarios.herramientas.i_formatterDate;

//import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import javax.swing.UIManager;
/**
 *
 * @author julian
 */
public class FrmUsuarioIng extends javax.swing.JDialog {
    private int cod_usuario;
    private int ne;

    private static final String PREFERRED_LOOK_AND_FEEL = "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel";    
    /** Creates new form FrmUsuario */
    public FrmUsuarioIng(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    boolean ValidaFechaServCliente(){
        System.out.println("Comprobando fecha");
        boolean resp = false;        
        Date hoy = new Date();
        String fec=i_formatterDate.i_toStringPostgres(hoy);
        String fechaServ="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getFechaServ();
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    fechaServ=datos[c].get("HOY").toString();
                    if (!fechaServ.equals(fec)){
                        javax.swing.JOptionPane.showMessageDialog(this,"La fecha del computador no es correcta","CAPRICORNIO",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                        resp=false;
                    }
                    else {resp=true;}
                }
            }
            else
                System.out.println("Fecha Vacia :D");
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"cAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        return resp;
    }

    private void BuscaUsuario(){
        System.out.println("Buscando Usuario...");
        int gestion=Integer.parseInt(TxtGestion.getText());
        if ("".equals(TxtUsuario.getText())){
            javax.swing.JOptionPane.showMessageDialog(this,"No introdujo usuario","CAPRICORNIO",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("".equals(TxtPassw.getText().trim())){
            javax.swing.JOptionPane.showMessageDialog(this,"No introdujo password","CAPRICORNIO",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            
            Map[] datos=puerto.getGestion(Integer.parseInt(TxtGestion.getText()));
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                    
                    gestion=Integer.parseInt(datos[c].get("GESTION").toString());
                }
            }
            else { javax.swing.JOptionPane.showMessageDialog(this,"LA GESTION NO ESTA HABILITADA","CAPRICORNIO",
                   javax.swing.JOptionPane.ERROR_MESSAGE);
                   return;
            }
            
            int cod_almacen=0;
            datos=puerto.getUsuario2(TxtUsuario.getText(),TxtPassw.getText());
            if (datos!=null){
                for (int c=0;c<datos.length;c++){
                    cod_usuario=Integer.parseInt(datos[c].get("COD_USUARIO").toString());
                    cod_almacen=Integer.parseInt(datos[c].get("COD_ALMACEN").toString());
                }
                dispose();
                System.out.println("cod_usuario: "+cod_usuario+", gestion: "+gestion+",  ");
                new FrmMenu(cod_usuario,gestion,cod_almacen,LblVersion.getText()).setVisible(true);
            }
            else {javax.swing.JOptionPane.showMessageDialog(this,"USUARIO NO HABILITADO","CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                  TxtPassw.setText("");
                  TxtUsuario.setText("");                  
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
        catch (NumberFormatException e){
            javax.swing.JOptionPane.showMessageDialog(this,"Los datos de la gestion deben ser numericos\n"+e,"CAJA PAGADORA",
                        javax.swing.JOptionPane.ERROR_MESSAGE);}
    }        
        
    /*private static void installLnF() {
        try {
                String lnfClassname = PREFERRED_LOOK_AND_FEEL;
                if (lnfClassname == null)
                        lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
                UIManager.setLookAndFeel(lnfClassname);
                SyntheticaLookAndFeel.setWindowsDecorated(false);
                SyntheticaLookAndFeel.setUseSystemFileIcons(true);
        } catch (Exception e) {
                System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
                                + " on this platform:" + e.getMessage());
        }
    }*/
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JPasswordField();
        TxtPassw = new javax.swing.JPasswordField();
        BtnAceptar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        TxtGestion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LblVersion = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ZODIAC CAPRICORN SYSTEM");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("USUARIO :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(120, 30, 70, 20);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("PASSWORD :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(120, 60, 70, 20);

        TxtUsuario.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        TxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyPressed(evt);
            }
        });
        getContentPane().add(TxtUsuario);
        TxtUsuario.setBounds(200, 30, 150, 20);

        TxtPassw.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        TxtPassw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtPasswKeyPressed(evt);
            }
        });
        getContentPane().add(TxtPassw);
        TxtPassw.setBounds(200, 60, 150, 20);

        BtnAceptar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnAceptar.setForeground(new java.awt.Color(0, 51, 153));
        BtnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/complete_status.gif"))); // NOI18N
        BtnAceptar.setText("Aceptar");
        BtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAceptar);
        BtnAceptar.setBounds(50, 130, 130, 25);

        BtnCancelar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        BtnCancelar.setForeground(new java.awt.Color(255, 0, 51));
        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/cancel.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar);
        BtnCancelar.setBounds(220, 130, 130, 25);

        TxtGestion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        TxtGestion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtGestionKeyPressed(evt);
            }
        });
        getContentPane().add(TxtGestion);
        TxtGestion.setBounds(200, 90, 80, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("GESTION :");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(120, 90, 70, 20);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/passw.gif"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(70, 50, 40, 40);

        LblVersion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        LblVersion.setForeground(new java.awt.Color(255, 255, 102));
        LblVersion.setText("Beta 7.0.0");
        getContentPane().add(LblVersion);
        LblVersion.setBounds(10, 10, 60, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/inicio.jpg"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(-40, 0, 434, 180);

        setSize(new java.awt.Dimension(405, 218));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAceptarActionPerformed
        if (!ValidaFechaServCliente()) return;
            ne++;
            BuscaUsuario();
            if (ne==4){
              javax.swing.JOptionPane.showMessageDialog(this,"SOLO TIENE TRES OPCIONES PARA INGREAR AL SISTEMA","CAPRICORNIO",
                javax.swing.JOptionPane.WARNING_MESSAGE);
              BtnCancelar.doClick();
        }
        else TxtUsuario.requestFocus();
    }//GEN-LAST:event_BtnAceptarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ne=0;
        Date hoy = new Date();
        TxtGestion.setText(i_formatterDate.getStrYear(hoy));
    }//GEN-LAST:event_formWindowOpened

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void TxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtPassw.requestFocus();
        }
    }//GEN-LAST:event_TxtUsuarioKeyPressed

    private void TxtPasswKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPasswKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            TxtGestion.requestFocus();
        }
    }//GEN-LAST:event_TxtPasswKeyPressed

    private void TxtGestionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtGestionKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER )  {
            BtnAceptar.doClick();
        }
    }//GEN-LAST:event_TxtGestionKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //installLnF();
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                FrmUsuarioIng dialog = new FrmUsuarioIng(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAceptar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JLabel LblVersion;
    private javax.swing.JTextField TxtGestion;
    private javax.swing.JPasswordField TxtPassw;
    private javax.swing.JPasswordField TxtUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
