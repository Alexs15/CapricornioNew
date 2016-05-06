/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiagSubeDBC.java
 *
 * Created on 09-11-2011, 10:22:19 AM
 */
package umsa.capricornio.gui.transacciones;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.menu.FrmMenu;
import umsa.capricornio.utilitarios.herramientas.Archivos;
import umsa.capricornio.utilitarios.herramientas.HyperlinkLabel;

/**
 *
 * @author julian
 */
public class DiagSubeContrato extends javax.swing.JDialog {

    private File rutaArchivo;
    private String lectura,nombre_archivo,archivo_subido;
    
    FrmMenu menu;
    int cod_trans_detalle;
    private Runtime r;
    
    String ip="200.7.166.211";
    int puerto=3007;
    
    /** Creates new form DiagSubeDBC */
    public DiagSubeContrato(FrmMenu menu,int cod_trans_detalle,String archivo_subido) {
        super(menu, true);
        initComponents();
        this.menu=menu;
        this.cod_trans_detalle=cod_trans_detalle;
        this.archivo_subido=archivo_subido;
        
        HyperlinkLabel hlVinculo = new HyperlinkLabel(archivo_subido,"http://"+ip+":8080/capri-web/docs/"+archivo_subido);
        hlVinculo.setHorizontalAlignment(JLabel.CENTER);
        PnlArchivo.add(hlVinculo);
        PnlArchivo.getViewport().add(hlVinculo);
    }

    boolean subirDatos(){
        boolean sw=true;
        try{
            String nombre_firma="TrustSSL.frm";
            File dir = new File("c:/temp");
            if (!dir.exists())
                dir.mkdir();
            String ruta_firma=dir+"/"+nombre_firma;
            try {
                // Se abre el fichero original para lectura            
                InputStream fileInput = this.getClass().getResourceAsStream("/umsa/capricornio/gui/firma/"+nombre_firma);                        
                BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
                // Se abre el fichero donde se hará la copia
                FileOutputStream fileOutput = new FileOutputStream (ruta_firma);
                BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
                // Bucle para leer de un fichero y escribir en el otro.
                byte [] array = new byte[1000];
                int leidos = bufferedInput.read(array);
                while (leidos > 0){
                    bufferedOutput.write(array,0,leidos);
                    leidos=bufferedInput.read(array);
                }
                // Cierre de los ficheros
                bufferedInput.close();
                bufferedOutput.close();
            }
            catch (Exception e){}      

            File file = new File(ruta_firma);       
            System.setProperty("javax.net.ssl.trustStore", file.getPath());            
            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket s = ssf.createSocket(ip, puerto);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            /*=================================================
             * DATOS ESPECIFICOS DE LA FIRMA DIGITAL
             ==================================================*/
            /*
            SSLSession session = ((SSLSocket) s).getSession();
            Certificate[] cchain = session.getPeerCertificates();
            System.out.println("The Certificates used by peer");
            for (int i = 0; i < cchain.length; i++) {
              System.out.println(((X509Certificate) cchain[i]).getSubjectDN());
            }
            System.out.println("Peer host is " + session.getPeerHost());
            System.out.println("Cipher is " + session.getCipherSuite());
            System.out.println("Protocol is " + session.getProtocol());
            System.out.println("ID is " + new BigInteger(session.getId()));
            System.out.println("Session created in " + session.getCreationTime());
            System.out.println("Session accessed in " + session.getLastAccessedTime());
            */
            String resp = in.readLine();
            System.out.println(resp);
            
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(nombre_archivo);

            String arch_guar = in.readLine();
            System.out.println("el archivo transferido es ::"+arch_guar);
            nombre_archivo=arch_guar;
            DataInputStream input;
            input = new DataInputStream (s.getInputStream() );
            System.out.println("Server message: " +input.readUTF() );

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(rutaArchivo.getAbsolutePath()));
            BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());    
            byte[] byteArray = new byte[8192];
            int n=0;
            while ((n = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,n);
            }  
            //System.out.println("Server message: " +input.readUTF() ); 
            bos.close();
            bis.close(); 

            in.close();
        }
        catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"ERROR AL TRASLADAR DOCUMENTO AL SERVIDOR \n"+e+"\nNOTIFIQUE EL ERROR AL AREA DE SISTEMAS","SYSTEM CAPRICORN",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
            nombre_archivo="";
            sw=false;
        }
        catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this,"NO ELIGIO NINGUN ARCHIVO PARA SUBIR \n"+e,"SYSTEM CAPRICORN",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
            nombre_archivo="";
            sw=false;
        }
        return sw;
    }
        
    
    public void AbreDialogo(){
        setVisible(true);
    }
    
    public String NombreArchvo() {
        return nombre_archivo;
    }
    
    void cerrarDiag(){
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        dispose();
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
        jButton1 = new javax.swing.JButton();
        TxtRuta = new javax.swing.JTextField();
        BtnAceptar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        PnlArchivo = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("PROCESO DBC");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        jButton1.setFont(new java.awt.Font("Arial", 1, 11));
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(10, 20, 80, 23);

        TxtRuta.setEditable(false);
        jPanel1.add(TxtRuta);
        TxtRuta.setBounds(90, 20, 340, 20);

        BtnAceptar.setFont(new java.awt.Font("Arial", 1, 11));
        BtnAceptar.setText("Aceptar");
        BtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAceptarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnAceptar);
        BtnAceptar.setBounds(70, 50, 120, 23);

        BtnCancelar.setFont(new java.awt.Font("Arial", 1, 11));
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(BtnCancelar);
        BtnCancelar.setBounds(230, 50, 120, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 50, 440, 80);

        PnlArchivo.setBorder(null);
        getContentPane().add(PnlArchivo);
        PnlArchivo.setBounds(180, 20, 270, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel1.setText("Archivo Subido Actualmente :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 20, 162, 20);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-479)/2, (screenSize.height-181)/2, 479, 181);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String[] csv = new String[] { "csv", "txt","jpg","doc","xls","pdf"};
        JFileChooser FileImportaVeneficiarios = new JFileChooser();
        FileImportaVeneficiarios.setAcceptAllFileFilterUsed(false);
        //FileImportaVeneficiarios.addChoosableFileFilter(new SimpleFileFilter(csv,"Datos a IMPORTAR (*.csv, *.txt)"));
        int option = FileImportaVeneficiarios.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            rutaArchivo = FileImportaVeneficiarios.getSelectedFile();
            if (FileImportaVeneficiarios.getSelectedFile() != null){
                lectura=null;
                Archivos archivo= Archivos.getInstance();
                try {
                    lectura = archivo.leerArchivo(rutaArchivo.getAbsolutePath()).trim();
                    TxtRuta.setText(rutaArchivo.getAbsolutePath());
                    nombre_archivo=rutaArchivo.getName();
                    //MigraDatosTabla("\\|"); // ESTE METODO TAMBIEN FUNCIONA, ambos son validos
                } catch(FileNotFoundException e) {
                    javax.swing.JOptionPane.showMessageDialog(this,"No se encontro el archivo \n"+e,"CAJA PAGADORA",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                } catch(IOException e) {
                    javax.swing.JOptionPane.showMessageDialog(this,"Error de Flujo Entrada Salida \n"+e,"CAJA PAGADORA",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
            //MigraDatosTabla("|");
        } else {
            System.out.println("Cancelado.");
        }
}//GEN-LAST:event_jButton1ActionPerformed

    private void BtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAceptarActionPerformed
        if (!"".equals(TxtRuta.getText())) {
            try{
                if (subirDatos()) {
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    Map[] datos=null;            
                        String detalle=null;
                        if (!"".equals(TxtRuta.getText())){
                            detalle = TxtRuta.getText();
                            datos=puerto.setDetalleContrato("SET-upDatedetDBC",cod_trans_detalle,nombre_archivo);
                            javax.swing.JOptionPane.showMessageDialog(this,"ARCHIVO ALMACENADO CORRECTAMENTE","SYSTEM CAPRICORN",
                                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        }
                }
                cerrarDiag();
            }        
            catch (RemoteException e){
                javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            catch (ServiceException e){ System.out.println(e);}              
        }
        else {
               javax.swing.JOptionPane.showMessageDialog(menu,"No eligio ningun archivo para subir","SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAceptarActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        if ("".equals(TxtRuta.getText()) || "".equals(archivo_subido))
            nombre_archivo="";
        cerrarDiag();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(!"".equals(archivo_subido));
            nombre_archivo=archivo_subido;        
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAceptar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JScrollPane PnlArchivo;
    private javax.swing.JTextField TxtRuta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

/*class SimpleFileFilter extends FileFilter {

  String[] extensions;

  String description;

  public SimpleFileFilter(String ext) {
    this(new String[] { ext }, null);
  }

  public SimpleFileFilter(String[] exts, String descr) {
    // Clone and lowercase the extensions
    extensions = new String[exts.length];
    for (int i = exts.length - 1; i >= 0; i--) {
      extensions[i] = exts[i].toLowerCase();
    }
    // Make sure we have a valid (if simplistic) description
    description = (descr == null ? exts[0] + " files" : descr);
  }

  public boolean accept(File f) {
    // We always allow directories, regardless of their extension
    if (f.isDirectory()) {
      return true;
    }

    // Ok, it's a regular file, so check the extension
    String name = f.getName().toLowerCase();
    for (int i = extensions.length - 1; i >= 0; i--) {
      if (name.endsWith(extensions[i])) {
        return true;
      }
    }
    return false;
  }

  public String getDescription() {
    return description;
  }
}*/