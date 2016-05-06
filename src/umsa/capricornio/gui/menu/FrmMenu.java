/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmMenu.java
 *
 * Created on 01-jun-2011, 11:54:40
 */
package umsa.capricornio.gui.menu;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.Roles;
import umsa.capricornio.gui.Almacen.FrmReportesAlmacen;
import umsa.capricornio.gui.Almacen.FrmReportesItems;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.Juridica.FrmReportesJuridica;
import umsa.capricornio.gui.Maestros.*;
import umsa.capricornio.gui.menu.JIF_DatosInstitucion;
import umsa.capricornio.gui.relacionadores.FrmUsrFirma;
import umsa.capricornio.gui.relacionadores.FrmUsrRol;
import umsa.capricornio.gui.relacionadores.FrmUsrUnidad;
import umsa.capricornio.gui.relacionadores.FrmUsrWorkflow;
import umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.FrmBancoProveedores;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmAdquisiciones;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmReporteModalidad;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmReporteUnidad;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmResumenReporte;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmtareasAdicionales;
import umsa.capricornio.gui.transacciones.FrmTransacciones;

/**
 *
 * @author Henrry
 */
public class FrmMenu extends javax.swing.JFrame {

    JInternalFrame FrmInterno;
    int cod_rol,gestion,cod_almacen;
    String[][] roles;
    String ver;
    public static int cod_usuario;
    public static String usuario;
    /** Creates new form FrmMenu */
    public FrmMenu(int cod_usuario,int gestion,int cod_almacen,String ver) {
        this.cod_usuario=cod_usuario;
        this.gestion=gestion;
        this.cod_almacen=cod_almacen;
        this.ver=ver;
        initComponents();
    }
    public void AbrirFrameInterno(JInternalFrame FrameInterno) {
            /*MenArchivosMaestros.setEnabled(false);
            MenProcesos.setEnabled(false);
            MenRelacionadores.setEnabled(false);
            MenReportes.setEnabled(false);
            MenOtrosProcesos.setEnabled(false);*/
            System.out.println("wujuuuuu :D!!!");
            MenAdministracion.setEnabled(false);
            MenProcesos.setEnabled(false);
            MenRelacion.setEnabled(false);
            MenReportes.setEnabled(false);
            ToolBotones.setVisible(false);
            FrmInterno= FrameInterno;
            PnlAdquisiciones.add(FrameInterno);
            FrameInterno.setVisible(true);
            //ImageIcon icono=  new ImageIcon(getClass().getResource("/org/umsa/gui/images/leo.gif"));
            //FrameInterno.setFrameIcon(icono);                        
    }
    
    public void CerrarFrameInterno(JInternalFrame FrameInterno){
        PnlAdquisiciones.getDesktopManager().closeFrame(FrameInterno);
        FrmInterno=null;
        /*MenArchivosMaestros.setEnabled(true);
        MenProcesos.setEnabled(true);
        MenRelacionadores.setEnabled(true);
        MenReportes.setEnabled(true);
        MenOtrosProcesos.setEnabled(true);*/
        ToolBotones.setVisible(true);
        MenAdministracion.setEnabled(true);
        MenProcesos.setEnabled(true);
        MenRelacion.setEnabled(true);
        MenReportes.setEnabled(true);
    }

    public void AbrirOtroFrame(JInternalFrame FrameInternoActual,JInternalFrame FrameInternoNuevo) {
        FrmInterno= FrameInternoNuevo;
        PnlAdquisiciones.add(FrameInternoNuevo);
        PnlAdquisiciones.setSelectedFrame(FrameInternoNuevo);
        FrameInternoActual.setVisible(false);
        FrameInternoNuevo.setVisible(true);
                
    }

    public void CerrarOtroFrame(JInternalFrame FrameInternoActual,JInternalFrame FrameInternoAnterior) {
        PnlAdquisiciones.getDesktopManager().closeFrame(FrameInternoActual);
        FrameInternoAnterior.setVisible(true);
    }

    private void TieneVariosRoles(){        
        String rol="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getUsuarioRol(cod_usuario);
            if (datos!=null){
                if (datos.length>1) {
                    roles =new String[datos.length][2];
                    for (int c=0;c<datos.length;c++){
                        roles[c][0]=datos[c].get("COD_ROL").toString();
                        roles[c][1]=datos[c].get("ROL").toString();
                        usuario = datos[c].get("USUARIO").toString();
                    }
                    DiagRoles Roles= new DiagRoles(this,roles);
                    boolean sw=Roles.AbreRoles();
                    Roles rol_elegido= new Roles();
                    rol_elegido=Roles.RolElegido();
                    cod_rol=rol_elegido.getCod_rol();
                    rol=rol_elegido.getRol();
                }
                else {
                    for (int c=0;c<datos.length;c++){
                        cod_rol = Integer.parseInt(datos[c].get("COD_ROL").toString());
                        usuario = datos[c].get("USUARIO").toString();
                        rol = datos[c].get("ROL").toString();
                    }
                }
                LblRol.setText("USUARIO:["+usuario+"]     ROL:["+rol+"]");
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"Traspasos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}                
    }
    private void FechasNHActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmFechasNH(this));
    }
    private void deshabilita(){
        ItemGestion.setEnabled(false);
        ItemAlmacen.setEnabled(false);
        ItemUsuario.setEnabled(false);
        ItemUnidadEjec.setEnabled(false);
        ItemItems.setEnabled(false);
        
        ItemUsrRol.setEnabled(false);
        ItemUsrUnidad.setEnabled(false);
        ItemUsrWorkflow.setEnabled(false);
        
        ItemDespacho.setEnabled(false);
        BtnDespacho.setEnabled(false);
        ItemProvedores.setEnabled(false);
        BtnProveedores.setEnabled(false);
        ItemOrdenes.setEnabled(false);
        BtnOrdenesCompra.setEnabled(false);
        ItemBandeja.setEnabled(false);
        System.out.println("el cod usuario "+cod_usuario);
        if(cod_usuario!=4 && cod_usuario!=12 && cod_usuario!=14)
        {
            ItmDocsEnviados.setEnabled(false);
            ItmDocsEnviados.setVisible(false);
        }
        else
        {
            ItmRepAlmacen.setVisible(false);
        }
        BtnBandeja.setEnabled(false);
    }

    private void habilita(){
        String enlace="";        
        String rol="";
        try{
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos= puerto.getEnlaces(cod_rol);
            if (datos!=null){
                for (int c=0;c<datos.length;c++){                    
                    enlace=datos[c].get("ENLACE").toString();

                    //MAESTROS                    
                    if(enlace.equals("GESTION"))
                        ItemGestion.setEnabled(true);
                    if(enlace.equals("ALMACEN"))
                        ItemAlmacen.setEnabled(true);
                    if(enlace.equals("USUARIOS"))
                        ItemUsuario.setEnabled(true);
                    if(enlace.equals("UNIDAD EJECUTORA"))
                        ItemUnidadEjec.setEnabled(true);
                    if(enlace.equals("ITEMS"))
                        ItemItems.setEnabled(true);                    

                    //RELACIONADORES
                    if(enlace.equals("USUARIO ROL"))
                        ItemUsrRol.setEnabled(true);
                    if(enlace.equals("USUARIO UNIDAD EJECUTORA"))
                        ItemUsrUnidad.setEnabled(true);
                    if(enlace.equals("USUARIO WORKFLOW"))
                        ItemUsrWorkflow.setEnabled(true);
                    //PROCESOS
                    if(enlace.equals("DESPACHO")) { BtnDespacho.setEnabled(true);
                        ItemDespacho.setEnabled(true);
                    }
                    if(enlace.equals("PROVEEDORES")) {BtnProveedores.setEnabled(true);
                        ItemProvedores.setEnabled(true);
                    }
                    if(enlace.equals("ORDEN COMPRA")) {BtnOrdenesCompra.setEnabled(true);
                        ItemOrdenes.setEnabled(true);
                    }
                    if(enlace.equals("BANDEJA")) {ItemBandeja.setEnabled(true);
                        BtnBandeja.setEnabled(true);
                    }                    

                    //REPORTES                    
                }
            }
            else {javax.swing.JOptionPane.showMessageDialog(this,"Usuario sin asignacion de roles","Traspasos Presupuestarios",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                  dispose();
                  System.exit(0);
            }
        }
        catch (RemoteException e){
            javax.swing.JOptionPane.showMessageDialog(this,"<html> error de conexion con el servidor <br> "+e,"Traspasos Presupuestarios",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch (ServiceException e){ System.out.println(e);}
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ToolBotones = new javax.swing.JToolBar();
        BtnDespacho = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        BtnProveedores = new javax.swing.JButton();
        BtnOrdenesCompra = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        BtnBandeja = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        BtnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        PnlAdquisiciones = new javax.swing.JDesktopPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LblRol = new javax.swing.JLabel();
        LblGestion = new javax.swing.JLabel();
        MenMenu = new javax.swing.JMenuBar();
        MenAdministracion = new javax.swing.JMenu();
        ItemGestion = new javax.swing.JMenuItem();
        ItemUsuario = new javax.swing.JMenuItem();
        ItemAdministradores = new javax.swing.JMenuItem();
        ItemAlmacen = new javax.swing.JMenuItem();
        ItemUnidadEjec = new javax.swing.JMenuItem();
        ItemItems = new javax.swing.JMenuItem();
        FechasNH = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        ItemCambioGestion = new javax.swing.JMenuItem();
        ItemRol = new javax.swing.JMenuItem();
        ItemPassword = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        MenRelacion = new javax.swing.JMenu();
        ItemUsrRol = new javax.swing.JMenuItem();
        ItemUsrUnidad = new javax.swing.JMenuItem();
        ItemUsrWorkflow = new javax.swing.JMenuItem();
        ItemUsrFirma = new javax.swing.JMenuItem();
        MenProcesos = new javax.swing.JMenu();
        ItemDespacho = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        ItemProvedores = new javax.swing.JMenuItem();
        ItemOrdenes = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        ItemBandeja = new javax.swing.JMenuItem();
        MenReportes = new javax.swing.JMenu();
        ItmRepAlmacen = new javax.swing.JMenuItem();
        ItmDocsEnviados = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        ItmRepAlmacen1 = new javax.swing.JMenuItem();
        ItmRepAlmacen2 = new javax.swing.JMenuItem();
        ItmRepAlmacen3 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("mio de mi");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        ToolBotones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ToolBotones.setRollover(true);

        BtnDespacho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/basket_put.png"))); // NOI18N
        BtnDespacho.setToolTipText("Despacho de Almacenes");
        BtnDespacho.setFocusable(false);
        BtnDespacho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnDespacho.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnDespacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDespachoActionPerformed(evt);
            }
        });
        ToolBotones.add(BtnDespacho);
        ToolBotones.add(jSeparator1);

        BtnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/lorry.png"))); // NOI18N
        BtnProveedores.setFocusable(false);
        BtnProveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnProveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProveedoresActionPerformed(evt);
            }
        });
        ToolBotones.add(BtnProveedores);

        BtnOrdenesCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/paste_plain.png"))); // NOI18N
        BtnOrdenesCompra.setToolTipText("Pedido de Materiales");
        BtnOrdenesCompra.setFocusable(false);
        BtnOrdenesCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnOrdenesCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnOrdenesCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOrdenesCompraActionPerformed(evt);
            }
        });
        ToolBotones.add(BtnOrdenesCompra);
        ToolBotones.add(jSeparator2);

        BtnBandeja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/accept.png"))); // NOI18N
        BtnBandeja.setToolTipText("Bandeja de Transacciones");
        BtnBandeja.setFocusable(false);
        BtnBandeja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnBandeja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnBandeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBandejaActionPerformed(evt);
            }
        });
        ToolBotones.add(BtnBandeja);
        ToolBotones.add(jSeparator3);

        BtnSalir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        BtnSalir.setForeground(new java.awt.Color(255, 0, 0));
        BtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disconnect.png"))); // NOI18N
        BtnSalir.setText("SALIR");
        BtnSalir.setFocusable(false);
        BtnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });
        ToolBotones.add(BtnSalir);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/application.png"))); // NOI18N
        jButton1.setToolTipText("Notificaciones");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        ToolBotones.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/application_add.png"))); // NOI18N
        jButton2.setToolTipText("Notificaciones");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        ToolBotones.add(jButton2);

        getContentPane().add(ToolBotones);
        ToolBotones.setBounds(0, 0, 490, 30);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/logoUMSA.jpg"))); // NOI18N
        PnlAdquisiciones.add(jLabel3);
        jLabel3.setBounds(0, 0, 410, 25);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/capricornio_1.jpg"))); // NOI18N
        PnlAdquisiciones.add(jLabel1);
        jLabel1.setBounds(290, 270, 592, 130);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/new_fondo2.jpg"))); // NOI18N
        PnlAdquisiciones.add(jLabel2);
        jLabel2.setBounds(10, -30, 1180, 740);

        getContentPane().add(PnlAdquisiciones);
        PnlAdquisiciones.setBounds(0, 30, 1190, 710);

        LblRol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LblRol.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(LblRol);
        LblRol.setBounds(574, 4, 430, 20);

        LblGestion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LblGestion.setForeground(new java.awt.Color(255, 0, 0));
        LblGestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblGestion.setText("2011");
        getContentPane().add(LblGestion);
        LblGestion.setBounds(500, 4, 70, 20);

        MenAdministracion.setForeground(new java.awt.Color(21, 65, 108));
        MenAdministracion.setText("Administracion");
        MenAdministracion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        ItemGestion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemGestion.setForeground(new java.awt.Color(0, 51, 204));
        ItemGestion.setText("gestion");
        ItemGestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemGestionActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemGestion);

        ItemUsuario.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemUsuario.setForeground(new java.awt.Color(0, 51, 204));
        ItemUsuario.setText("Usuarios");
        ItemUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUsuarioActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemUsuario);

        ItemAdministradores.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemAdministradores.setForeground(new java.awt.Color(0, 51, 204));
        ItemAdministradores.setText("Administradores");
        ItemAdministradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemAdministradoresActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemAdministradores);

        ItemAlmacen.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemAlmacen.setForeground(new java.awt.Color(0, 51, 204));
        ItemAlmacen.setText("Almacenes");
        ItemAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemAlmacenActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemAlmacen);

        ItemUnidadEjec.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemUnidadEjec.setForeground(new java.awt.Color(0, 51, 204));
        ItemUnidadEjec.setText("Unidad Ejecutora");
        ItemUnidadEjec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUnidadEjecActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemUnidadEjec);

        ItemItems.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemItems.setForeground(new java.awt.Color(0, 51, 204));
        ItemItems.setText("Items");
        ItemItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemItemsActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemItems);

        FechasNH.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        FechasNH.setForeground(new java.awt.Color(0, 51, 204));
        FechasNH.setText("Fechas No Habiles");
        FechasNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechasNHActionPerformed(evt);
            }
        });
        MenAdministracion.add(FechasNH);
        MenAdministracion.add(jSeparator4);

        ItemCambioGestion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemCambioGestion.setForeground(new java.awt.Color(0, 51, 204));
        ItemCambioGestion.setText("Cambio de Gestion");
        ItemCambioGestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemCambioGestionActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemCambioGestion);

        ItemRol.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemRol.setForeground(new java.awt.Color(0, 51, 204));
        ItemRol.setText("Cambio de Rol");
        ItemRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemRolActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemRol);

        ItemPassword.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemPassword.setForeground(new java.awt.Color(0, 51, 204));
        ItemPassword.setText("Cambio de Password");
        ItemPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemPasswordActionPerformed(evt);
            }
        });
        MenAdministracion.add(ItemPassword);
        MenAdministracion.add(jSeparator7);

        jMenuItem1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(0, 51, 204));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/briefcase_16.png"))); // NOI18N
        jMenuItem1.setText("DATOS INSTITUCIÓN");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenAdministracion.add(jMenuItem1);

        MenMenu.add(MenAdministracion);

        MenRelacion.setForeground(new java.awt.Color(21, 65, 108));
        MenRelacion.setText("Relacionador");
        MenRelacion.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        ItemUsrRol.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemUsrRol.setForeground(new java.awt.Color(0, 51, 204));
        ItemUsrRol.setText("Usuario - Rol");
        ItemUsrRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUsrRolActionPerformed(evt);
            }
        });
        MenRelacion.add(ItemUsrRol);

        ItemUsrUnidad.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemUsrUnidad.setForeground(new java.awt.Color(0, 51, 204));
        ItemUsrUnidad.setText("Usuario - Unidad");
        ItemUsrUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUsrUnidadActionPerformed(evt);
            }
        });
        MenRelacion.add(ItemUsrUnidad);

        ItemUsrWorkflow.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemUsrWorkflow.setForeground(new java.awt.Color(0, 51, 204));
        ItemUsrWorkflow.setText("Usuario - Flujo de Trabajo");
        ItemUsrWorkflow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUsrWorkflowActionPerformed(evt);
            }
        });
        MenRelacion.add(ItemUsrWorkflow);

        ItemUsrFirma.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ItemUsrFirma.setForeground(new java.awt.Color(0, 51, 204));
        ItemUsrFirma.setText("Usuario - Firma");
        ItemUsrFirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemUsrFirmaActionPerformed(evt);
            }
        });
        MenRelacion.add(ItemUsrFirma);

        MenMenu.add(MenRelacion);

        MenProcesos.setForeground(new java.awt.Color(21, 65, 108));
        MenProcesos.setText("Procesos");
        MenProcesos.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        ItemDespacho.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemDespacho.setForeground(new java.awt.Color(0, 51, 204));
        ItemDespacho.setText("Despacho de Almacen");
        ItemDespacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemDespachoActionPerformed(evt);
            }
        });
        MenProcesos.add(ItemDespacho);
        MenProcesos.add(jSeparator5);

        ItemProvedores.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemProvedores.setForeground(new java.awt.Color(0, 51, 204));
        ItemProvedores.setText("Proveedores");
        ItemProvedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemProvedoresActionPerformed(evt);
            }
        });
        MenProcesos.add(ItemProvedores);

        ItemOrdenes.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemOrdenes.setForeground(new java.awt.Color(0, 51, 204));
        ItemOrdenes.setText("Ordenes de Compra");
        ItemOrdenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemOrdenesActionPerformed(evt);
            }
        });
        MenProcesos.add(ItemOrdenes);
        MenProcesos.add(jSeparator6);

        ItemBandeja.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItemBandeja.setForeground(new java.awt.Color(0, 51, 204));
        ItemBandeja.setText("Bandeja de Transacciones");
        ItemBandeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemBandejaActionPerformed(evt);
            }
        });
        MenProcesos.add(ItemBandeja);

        MenMenu.add(MenProcesos);

        MenReportes.setForeground(new java.awt.Color(21, 65, 108));
        MenReportes.setText("Reportes");
        MenReportes.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        ItmRepAlmacen.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItmRepAlmacen.setForeground(new java.awt.Color(0, 51, 204));
        ItmRepAlmacen.setText("Reportes Generales");
        ItmRepAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItmRepAlmacenActionPerformed(evt);
            }
        });
        MenReportes.add(ItmRepAlmacen);

        ItmDocsEnviados.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItmDocsEnviados.setForeground(new java.awt.Color(0, 51, 204));
        ItmDocsEnviados.setText("Reportes por Tipo de solicitud");
        ItmDocsEnviados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItmDocsEnviadosActionPerformed(evt);
            }
        });
        MenReportes.add(ItmDocsEnviados);

        jMenuItem2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jMenuItem2.setForeground(new java.awt.Color(0, 51, 204));
        jMenuItem2.setText("Reporte Boletas de Garantia");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenReportes.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jMenuItem3.setForeground(new java.awt.Color(0, 51, 204));
        jMenuItem3.setText("Reporte de Items");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MenReportes.add(jMenuItem3);

        ItmRepAlmacen1.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItmRepAlmacen1.setForeground(new java.awt.Color(0, 51, 204));
        ItmRepAlmacen1.setText("Reportes Por Unidad");
        ItmRepAlmacen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItmRepAlmacen1ActionPerformed(evt);
            }
        });
        MenReportes.add(ItmRepAlmacen1);

        ItmRepAlmacen2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItmRepAlmacen2.setForeground(new java.awt.Color(0, 51, 204));
        ItmRepAlmacen2.setText("Reportes simplificados");
        ItmRepAlmacen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItmRepAlmacen2ActionPerformed(evt);
            }
        });
        MenReportes.add(ItmRepAlmacen2);

        ItmRepAlmacen3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        ItmRepAlmacen3.setForeground(new java.awt.Color(0, 51, 204));
        ItmRepAlmacen3.setText("Resumenes de Ordenes");
        ItmRepAlmacen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItmRepAlmacen3ActionPerformed(evt);
            }
        });
        MenReportes.add(ItmRepAlmacen3);

        MenMenu.add(MenReportes);

        jMenu7.setForeground(new java.awt.Color(21, 65, 108));
        jMenu7.setText("A cerca de");
        jMenu7.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        MenMenu.add(jMenu7);

        jMenu8.setForeground(new java.awt.Color(255, 0, 0));
        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/bookmark_document.png"))); // NOI18N
        jMenu8.setText("Generacion de Contratos");
        jMenu8.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jMenuItem4.setForeground(new java.awt.Color(0, 0, 255));
        jMenuItem4.setText("BECAS");
        jMenu8.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jMenuItem5.setForeground(new java.awt.Color(0, 0, 255));
        jMenuItem5.setText("PLAZO FIJO");
        jMenu8.add(jMenuItem5);

        MenMenu.add(jMenu8);

        setJMenuBar(MenMenu);

        setSize(new java.awt.Dimension(1210, 818));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setTitle("ZODIAC CAPRICORN SYSTEM -- Sistema de Adquisiciones -- "+ver );
        LblGestion.setText(Integer.toString(gestion));
        deshabilita();
        TieneVariosRoles();
        habilita();
        MensajesAlerta();
        
    }//GEN-LAST:event_formWindowOpened
    private void MensajesAlerta(){
        try {
            if(cod_rol==5){
                jButton1.setVisible(true);
//                javax.swing.JOptionPane.showMessageDialog(this," :D","SYSTEM CAPRICORN",
//                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                JD_RES_FECH restriccion_fechas = new JD_RES_FECH(this,false);
                restriccion_fechas.setVisible(true);
            }
            else
                jButton1.setVisible(false);
        } catch (Exception e) {
        }
    }
    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        dispose();
        File f=new File("temp");
        if(f.exists())
        {
            System.out.println(f.getAbsolutePath());
            File[] files=f.listFiles();
            System.out.println(files.length);
            for(int x=0;x<files.length;x++)
            {
                files[x].delete();
            }
            f.delete();
        }
        System.gc();
        Runtime r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
        System.exit(0);
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void BtnBandejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBandejaActionPerformed
        AbrirFrameInterno(new FrmTransacciones(this, cod_usuario, cod_rol, gestion,cod_almacen));
    }//GEN-LAST:event_BtnBandejaActionPerformed

    private void BtnOrdenesCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOrdenesCompraActionPerformed
        AbrirFrameInterno(new FrmAdquisiciones(this, cod_usuario, cod_rol, gestion,cod_almacen));
    }//GEN-LAST:event_BtnOrdenesCompraActionPerformed

    private void BtnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProveedoresActionPerformed
        AbrirFrameInterno(new FrmBancoProveedores(this, cod_usuario, cod_rol, cod_almacen));
    }//GEN-LAST:event_BtnProveedoresActionPerformed

    private void BtnDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDespachoActionPerformed
        
}//GEN-LAST:event_BtnDespachoActionPerformed

    private void ItmRepAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItmRepAlmacenActionPerformed
        AbrirFrameInterno(new FrmReportesAlmacen(this));
    }//GEN-LAST:event_ItmRepAlmacenActionPerformed

    private void ItmDocsEnviadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItmDocsEnviadosActionPerformed
        AbrirFrameInterno(new FrmReporteModalidad(this));
    }//GEN-LAST:event_ItmDocsEnviadosActionPerformed

    private void ItemAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemAlmacenActionPerformed
        AbrirFrameInterno(new FrmAlmacenes(this,gestion));
    }//GEN-LAST:event_ItemAlmacenActionPerformed

    private void ItemUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUsuarioActionPerformed
        AbrirFrameInterno(new FrmUsuarios(this,this.gestion,this.cod_almacen,"C2015"));
    }//GEN-LAST:event_ItemUsuarioActionPerformed

    private void ItemUnidadEjecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUnidadEjecActionPerformed
        AbrirFrameInterno(new FrmUnidadesEjecutoras(this,gestion));
    }//GEN-LAST:event_ItemUnidadEjecActionPerformed

    private void ItemItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemItemsActionPerformed
        AbrirFrameInterno(new FrmItems(this,gestion));
    }//GEN-LAST:event_ItemItemsActionPerformed

    private void ItemGestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemGestionActionPerformed
        AbrirFrameInterno(new FrmGestion(this));
    }//GEN-LAST:event_ItemGestionActionPerformed

    private void ItemUsrRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUsrRolActionPerformed
        System.out.println("A ver pues");
        AbrirFrameInterno(new FrmUsrRol(this,this.gestion,this.cod_almacen));
    }//GEN-LAST:event_ItemUsrRolActionPerformed

    private void ItemUsrUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUsrUnidadActionPerformed
        AbrirFrameInterno(new FrmUsrUnidad(this,this.gestion, this.cod_almacen));
    }//GEN-LAST:event_ItemUsrUnidadActionPerformed

    private void ItemUsrWorkflowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUsrWorkflowActionPerformed
        AbrirFrameInterno(new FrmUsrWorkflow(this, this.gestion, this.cod_almacen));
    }//GEN-LAST:event_ItemUsrWorkflowActionPerformed

    private void ItemRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemRolActionPerformed
        deshabilita();
        TieneVariosRoles();
        habilita(); 
    }//GEN-LAST:event_ItemRolActionPerformed

    private void ItemPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemPasswordActionPerformed
        AbrirFrameInterno(new FrmCambiarPassw(this, cod_usuario)); 
    }//GEN-LAST:event_ItemPasswordActionPerformed

    private void ItemCambioGestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemCambioGestionActionPerformed
        DiagGestion nueva_gestion= new DiagGestion(this,gestion);        
        nueva_gestion.AbreGestion();
        gestion=nueva_gestion.GestionActual();
        LblGestion.setText(Integer.toString(gestion));
    }//GEN-LAST:event_ItemCambioGestionActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new JIF_DatosInstitucion(this, cod_almacen, gestion));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void ItemDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemDespachoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemDespachoActionPerformed

    private void ItemBandejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemBandejaActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmTransacciones(this, cod_usuario, cod_rol, gestion,cod_almacen));
    }//GEN-LAST:event_ItemBandejaActionPerformed

    private void ItemOrdenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemOrdenesActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmAdquisiciones(this, cod_usuario, cod_rol, gestion,cod_almacen));
    }//GEN-LAST:event_ItemOrdenesActionPerformed

    private void ItemProvedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemProvedoresActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmBancoProveedores(this, cod_usuario, cod_rol, cod_almacen));
    }//GEN-LAST:event_ItemProvedoresActionPerformed

    private void ItemUsrFirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemUsrFirmaActionPerformed
        // TODO add your handling code here:
        System.out.println("a ver cuantas veces entra!!!");
        AbrirFrameInterno(new FrmUsrFirma(this,this.gestion,this.cod_almacen));
    }//GEN-LAST:event_ItemUsrFirmaActionPerformed

    private void ItemAdministradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemAdministradoresActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmAdministradores(this,this.gestion));
    }//GEN-LAST:event_ItemAdministradoresActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmReportesJuridica(this));
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JD_RES_FECH jd=new JD_RES_FECH(new javax.swing.JFrame(), true);
        jd.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmReportesItems(this));
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void ItmRepAlmacen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItmRepAlmacen1ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmReporteUnidad(this,cod_usuario,cod_rol,1));
        
    }//GEN-LAST:event_ItmRepAlmacen1ActionPerformed

    private void ItmRepAlmacen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItmRepAlmacen2ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmReporteUnidad(this,cod_usuario,cod_rol,2));
    }//GEN-LAST:event_ItmRepAlmacen2ActionPerformed

    private void ItmRepAlmacen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItmRepAlmacen3ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmResumenReporte(this,cod_usuario,cod_rol));
    }//GEN-LAST:event_ItmRepAlmacen3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        AbrirFrameInterno(new FrmtareasAdicionales(this,cod_usuario,cod_rol,this.gestion,"d"));
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBandeja;
    private javax.swing.JButton BtnDespacho;
    private javax.swing.JButton BtnOrdenesCompra;
    private javax.swing.JButton BtnProveedores;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JMenuItem FechasNH;
    private javax.swing.JMenuItem ItemAdministradores;
    private javax.swing.JMenuItem ItemAlmacen;
    private javax.swing.JMenuItem ItemBandeja;
    private javax.swing.JMenuItem ItemCambioGestion;
    private javax.swing.JMenuItem ItemDespacho;
    private javax.swing.JMenuItem ItemGestion;
    private javax.swing.JMenuItem ItemItems;
    private javax.swing.JMenuItem ItemOrdenes;
    private javax.swing.JMenuItem ItemPassword;
    private javax.swing.JMenuItem ItemProvedores;
    private javax.swing.JMenuItem ItemRol;
    private javax.swing.JMenuItem ItemUnidadEjec;
    private javax.swing.JMenuItem ItemUsrFirma;
    private javax.swing.JMenuItem ItemUsrRol;
    private javax.swing.JMenuItem ItemUsrUnidad;
    private javax.swing.JMenuItem ItemUsrWorkflow;
    private javax.swing.JMenuItem ItemUsuario;
    private javax.swing.JMenuItem ItmDocsEnviados;
    private javax.swing.JMenuItem ItmRepAlmacen;
    private javax.swing.JMenuItem ItmRepAlmacen1;
    private javax.swing.JMenuItem ItmRepAlmacen2;
    private javax.swing.JMenuItem ItmRepAlmacen3;
    private javax.swing.JLabel LblGestion;
    private javax.swing.JLabel LblRol;
    private javax.swing.JMenu MenAdministracion;
    private javax.swing.JMenuBar MenMenu;
    private javax.swing.JMenu MenProcesos;
    private javax.swing.JMenu MenRelacion;
    private javax.swing.JMenu MenReportes;
    private javax.swing.JDesktopPane PnlAdquisiciones;
    private javax.swing.JToolBar ToolBotones;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    // End of variables declaration//GEN-END:variables
}
