/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.Maestros;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.Maestros.tablas.TablaUsuario;
import umsa.capricornio.gui.menu.FrmMenu;

/**
 *
 * @author Usuario
 */
public class FrmAdministradores extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmAdministradores
     */
    TablaUsuario usuario;

    Runtime r;
    FrmMenu menu;
//    int cod_usuario;
    int gestion;
    int cod_almacen;
    String cod_apert;

    public FrmAdministradores(FrmMenu menu, int gestion) {
        this.menu = menu;
        initComponents();
        this.gestion = gestion;
//        this.cod_usuario=cod_usuario;
//        this.cod_almacen=cod_almacen;
//        this.cod_apert=cod_apert;
        System.out.println("gestion: " + gestion);
        ConstruyeTablaUsuarios();
        LlenaComboApertura1();

    }

    public void LlenaComboApertura() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getAlmacenGestion(gestion);
            this.JC_Aperturas.addItem("- ELIJA UN ALMACEN -");
            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    this.JC_Aperturas.addItem(datos[c].get("COD_FAC") + " - " + datos[c].get("ALMACEN"));
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }
    public void LlenaComboApertura1() {
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.getUnidadEjecutora1(gestion);
            this.JC_Aperturas.addItem("- ELIJA UNA FACULTAD -");
            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    this.JC_Aperturas.addItem(datos[c].get("COD_ALMACEN") + " - " + datos[c].get("COD_FACULTY") + " - " + datos[c].get("DA"));
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private void ConstruyeTablaUsuarios() {
        usuario = new TablaUsuario();
        TblUsuarios.setAutoCreateColumnsFromModel(false);
        TblUsuarios.setModel(usuario);

        for (int k = 0; k < TablaUsuario.m_columns.length; k++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(TablaUsuario.m_columns[k].m_alignment);
            TableColumn column = new TableColumn(k, TablaUsuario.m_columns[k].m_width, renderer, null);
            TblUsuarios.addColumn(column);
        }
        JTableHeader header = TblUsuarios.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);

        PnlUsuarios.getViewport().add(TblUsuarios);
        //getContentPane().add(PnlTechos, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        TxtAlias = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtPass1 = new javax.swing.JPasswordField();
        TxtPass2 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        TxtResumen = new javax.swing.JTextField();
        JC_Aperturas = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        PnlUsuarios = new javax.swing.JScrollPane();
        TblUsuarios = new javax.swing.JTable();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Administracion de Usuarios"));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre Usuario:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 20, 90, 20);

        TxtUsuario.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtUsuarioKeyPressed(evt);
            }
        });
        jPanel1.add(TxtUsuario);
        TxtUsuario.setBounds(110, 20, 330, 20);

        TxtAlias.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        TxtAlias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtAliasKeyPressed(evt);
            }
        });
        jPanel1.add(TxtAlias);
        TxtAlias.setBounds(220, 80, 150, 20);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("vuelva a introducir password:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(50, 140, 150, 20);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Introduzca password:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(90, 110, 110, 20);

        TxtPass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtPass1KeyPressed(evt);
            }
        });
        jPanel1.add(TxtPass1);
        TxtPass1.setBounds(220, 110, 150, 20);

        TxtPass2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtPass2KeyPressed(evt);
            }
        });
        jPanel1.add(TxtPass2);
        TxtPass2.setBounds(220, 140, 150, 20);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nombre Resumen:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 50, 90, 20);

        TxtResumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtResumenKeyPressed(evt);
            }
        });
        jPanel1.add(TxtResumen);
        TxtResumen.setBounds(110, 50, 220, 20);

        JC_Aperturas.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        JC_Aperturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JC_AperturasActionPerformed(evt);
            }
        });
        jPanel1.add(JC_Aperturas);
        JC_Aperturas.setBounds(420, 80, 400, 19);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Alias:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(120, 80, 80, 20);

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

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page.png"))); // NOI18N
        BtnNuevo.setToolTipText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/disk_multiple.png"))); // NOI18N
        BtnGuardar.setToolTipText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnModificar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/page_edit.png"))); // NOI18N
        BtnModificar.setToolTipText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/umsa/capricornio/gui/images/eliminar.gif"))); // NOI18N
        BtnEliminar.setToolTipText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        PnlUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuarios"));

        TblUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TblUsuariosMousePressed(evt);
            }
        });
        PnlUsuarios.setViewportView(TblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(BtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PnlUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnlUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnModificar)
                    .addComponent(BtnEliminar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TxtResumen.requestFocus();
        }
    }//GEN-LAST:event_TxtUsuarioKeyPressed

    private void TxtAliasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtAliasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TxtPass1.requestFocus();
        }
    }//GEN-LAST:event_TxtAliasKeyPressed

    private void TxtPass1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TxtPass2.requestFocus();
        }
    }//GEN-LAST:event_TxtPass1KeyPressed

    private void TxtPass2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPass2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (BtnModificar.isEnabled() == true) {
                BtnModificar.doClick();
            } else {
                BtnGuardar.doClick();
            }
        }
    }//GEN-LAST:event_TxtPass2KeyPressed

    private void TxtResumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtResumenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TxtAlias.requestFocus();
        }
    }//GEN-LAST:event_TxtResumenKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        menu.CerrarFrameInterno(this);
        System.gc();
        r = Runtime.getRuntime();
        long mem1 = r.freeMemory();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        BtnEliminar.setEnabled(false);
        BtnModificar.setEnabled(false);
        BtnGuardar.setEnabled(true);
        TblUsuarios.setEnabled(true);
        TxtUsuario.setText("");
        TxtAlias.setText("");
        TxtPass1.setText("");
        TxtPass2.setText("");
        TxtUsuario.requestFocus();
        /*CmbRubro.setEnabled(true);
         CmbFF.setEnabled(true);
         CmbOF.setEnabled(true);

         TxtMonto.setText("");
         CmbRubro.setSelectedIndex(0);
         CmbRubro.requestFocus();*/
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed

        if (!(TxtPass1.getText().equals(TxtPass2.getText()))) {
            javax.swing.JOptionPane.showMessageDialog(this, "Los passwords son diferentes", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtPass1.setText("");
            TxtPass2.setText("");
            TxtPass1.requestFocus();
            return;
        }

        if ((TxtPass1.getText().length() < 6 && TxtPass1.getText().length() > 20) || (TxtPass2.getText().length() < 6 && TxtPass2.getText().length() > 20)) {
            javax.swing.JOptionPane.showMessageDialog(this, "El password debe ser por lo menos de 6 carcateres", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtPass1.setText("");
            TxtPass2.setText("");
            TxtPass1.requestFocus();
            return;
        }

        if ("".equals(TxtUsuario.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir nombre completo del susuario", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtUsuario.requestFocus();
            return;
        }

        if ("".equals(TxtResumen.getText().trim())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe introducir resumen del nombre del usuario", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            TxtResumen.requestFocus();
            return;
        }

        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea guardar los datos?",
                "ZODIAC LIBRA SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            int cod_usuario = puerto.secCodUsuario();
            
            System.out.println("cod_usuario: "+cod_usuario+", cod_almacen: "+this.cod_almacen);
            
            puerto.setUsuariox("SET-insDataUsr", cod_usuario, TxtAlias.getText().trim(), TxtPass1.getText(), TxtUsuario.getText().trim(), TxtResumen.getText().trim());
            //            puerto.addUserApert("SET-addUserApert",cod_usuario,this.cod_apert,this.gestion);
            puerto.addAdmAlm("SET-addAdmAlm", cod_usuario, this.cod_almacen,gestion);
            //Adicionando rol de administrador al usuario :D
            puerto.setUsrRol("SET-insDataUsr", cod_usuario, 1);
            //            Map[] datos = datos=puerto.setUsuario("SET-insDataUsr",TxtAlias.getText().trim(),TxtPass1.getText(),TxtUsuario.getText().trim(),TxtResumen.getText().trim());
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario Registrado", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUsuarios();
            TxtUsuario.setText("");
            TxtPass1.setText("");
            TxtPass2.setText("");
            TxtAlias.setText("");
            TxtUsuario.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    void CerearTablaUsuarios() {
        int f = TblUsuarios.getRowCount();
        for (int i = f; i >= 0; i--) {
            if (usuario.delete(i)) {
                TblUsuarios.tableChanged(new TableModelEvent(
                        usuario, i, i, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        }
    }

    private void LlenaUsuarios() {
        CerearTablaUsuarios();
        System.out.println("La gestion es: " + this.gestion + ", y el cod_almacen es :" + this.cod_almacen);
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
//            Map[] datos= puerto.getUsuario();
            Map[] datos = puerto.getUsuariosTipo(1,gestion);
            if (datos != null) {
                for (int c = 0; c < datos.length; c++) {
                    usuario.insert(c);
                    TblUsuarios.tableChanged(new TableModelEvent(usuario, c, c, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                    TblUsuarios.setValueAt(datos[c].get("COD_USUARIO"), c, 0);
                    TblUsuarios.setValueAt(datos[c].get("USUARIO"), c, 1);
                    TblUsuarios.setValueAt(datos[c].get("NOMBRE_RESUMEN"), c, 2);
                }
            }
        } catch (RemoteException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + e, "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }
    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea Modificar los datos?",
                "ZODIAC LIBRA SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        int f = TblUsuarios.getSelectedRow();
        int id_usr = Integer.parseInt(TblUsuarios.getValueAt(f, 0).toString());
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = puerto.setUsuarioModif("SET-updDataUsr", id_usr, TxtUsuario.getText().trim(), TxtResumen.getText().trim());
            javax.swing.JOptionPane.showMessageDialog(this, "Datos modificado", "ZODIAC CAPRICORN SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUsuarios();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblUsuarios.setEnabled(true);
            TxtUsuario.setText("");
            TxtPass1.setText("");
            TxtPass2.setText("");
            TxtAlias.setText("");
            TxtUsuario.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        int res = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea Eliminar los datos?",
                "ZODIAC LIBRA SYSTEM", javax.swing.JOptionPane.YES_NO_OPTION);
        int f = TblUsuarios.getSelectedRow();
        int id_usr = Integer.parseInt(TblUsuarios.getValueAt(f, 0).toString());
        if (res == javax.swing.JOptionPane.NO_OPTION) {
            return;
        }
        try {
            AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
            AdquiWS_PortType puerto = servicio.getAdquiWS();
            Map[] datos = datos = puerto.setUsuarioDel("SET-delDataUsr", id_usr);
            javax.swing.JOptionPane.showMessageDialog(this, "Datos Eliminados", "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            LlenaUsuarios();
            BtnEliminar.setEnabled(false);
            BtnModificar.setEnabled(false);
            BtnGuardar.setEnabled(true);
            TblUsuarios.setEnabled(true);
            TxtUsuario.setText("");
            TxtPass1.setText("");
            TxtPass2.setText("");
            TxtAlias.setText("");
            TxtUsuario.requestFocus();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "<html> error de conexion con el servidor <br> " + ex, "ZODIAC LIBRA SYSTEM",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void TblUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblUsuariosMousePressed
        if (evt.getClickCount() == 2) {
            BtnEliminar.setEnabled(true);
            BtnModificar.setEnabled(true);
            BtnGuardar.setEnabled(false);
            TblUsuarios.setEnabled(false);
            int fila = TblUsuarios.getSelectedRow();
            TxtUsuario.setText(TblUsuarios.getValueAt(fila, 1).toString());
            TxtResumen.setText(TblUsuarios.getValueAt(fila, 2).toString());
        }
    }//GEN-LAST:event_TblUsuariosMousePressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        LlenaUsuarios();
        BtnGuardar.setEnabled(true);
        BtnModificar.setEnabled(false);
        BtnEliminar.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void JC_AperturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JC_AperturasActionPerformed
        // TODO add your handling code here:
        try {
            System.out.println("Waooo Llocallo");
//        System.out.println("Sacatelas Babuchas --> "+this.JC_Aperturas.getSelectedItem());
            int cod_almacen = Integer.parseInt(this.JC_Aperturas.getSelectedItem().toString().split(" - ")[0]);
            this.cod_almacen = cod_almacen;
            System.out.println("El cod almacen es: " + this.cod_almacen);
//        this.JTA_Descripcion.setText(getDescripcion(partida));
            //this.JC_Partidas.getSelectedItem();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_JC_AperturasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JComboBox JC_Aperturas;
    private javax.swing.JScrollPane PnlUsuarios;
    private javax.swing.JTable TblUsuarios;
    private javax.swing.JTextField TxtAlias;
    private javax.swing.JPasswordField TxtPass1;
    private javax.swing.JPasswordField TxtPass2;
    private javax.swing.JTextField TxtResumen;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}