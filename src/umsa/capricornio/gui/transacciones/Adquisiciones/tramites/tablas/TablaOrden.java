/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.ItemsData;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.DiagOrdenesDetalle;
//import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedida;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaOrden extends AbstractTableModel {

    static final public ColumnData m_columns[] = {
        new ColumnData("cc", 0, JLabel.RIGHT),
        new ColumnData("ce", 0, JLabel.RIGHT),
        new ColumnData("ctd", 0, JLabel.RIGHT),
        new ColumnData("Cant.", 60, JLabel.CENTER),
        new ColumnData("Unid.Med.", 200, JLabel.CENTER),
        new ColumnData("Item", 120, JLabel.LEFT),
        new ColumnData("Partida", 300, JLabel.LEFT),
        new ColumnData("Articulo", 300, JLabel.LEFT),
        new ColumnData("Articulo Actualizado ADQ", 300, JLabel.LEFT),
        new ColumnData("Articulo Actualizado Ing. Alm.", 300, JLabel.LEFT),
        new ColumnData("DBC/CONTRATO", 300, JLabel.LEFT),
        new ColumnData("Precio unit", 100, JLabel.RIGHT),
        new ColumnData("Sub Total", 100, JLabel.RIGHT),
        new ColumnData("Cod Item", 0, JLabel.RIGHT)
    };

    protected ArrayList lista;
    DiagOrdenesDetalle orden;
    int cod_rol;
    String cod_estado;
    int cod_user;

    public TablaOrden(DiagOrdenesDetalle orden, int cod_rol, String cod_estado,int cod_user) {
        lista = new ArrayList();
        this.orden = orden;
        this.cod_rol = cod_rol;
        this.cod_estado = cod_estado;
        this.cod_user = cod_user;
    }

    public int getRowCount() {
        return lista == null ? 0 : lista.size();
    }

    public int getColumnCount() {
        return m_columns.length;
    }

    @Override
    public String getColumnName(int column) {
        String str = m_columns[column].m_title;
        return str;
    }

    @Override
    public boolean isCellEditable(int nRow, int nCol) {

        if (nCol == 8) {
            if ((cod_rol == 5) && !("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        } else if (nCol == 9) {
            if ((cod_rol == 2) && ("ALM1".equals(cod_estado)) && !("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        } else if (nCol == 11) {
            if ((cod_rol == 5) && ("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        } else if (nCol == 3) {
            if ((cod_rol == 5) && ("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        } else if (nCol == 6) {
            if ((cod_rol == 5) && ("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        }
         else if (nCol == 4) {
            if ((cod_rol == 5) && ("".equals(this.getValueAt(nRow, 0)))) {
                return true;
            } else {
                return false;
            }
        }
//        else if (nCol == 4) {
//            if ((cod_rol == 5) && ("".equals(this.getValueAt(nRow, 0)))) {
//                return true;
//            } else {
//                return false;
//            }
//        }
        
        //            else if (nCol==5)
        //                    if ((cod_rol==5) &&("".equals(this.getValueAt(nRow, 0))))
        //                        return true;
        //                    else
        //                        return false;
        else {
            return false;
        }
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount()) {
            return "";
        }
        ItemsData row = (ItemsData) lista.get(nRow);
        switch (nCol) {
            case 0:
                return row.cod_complemento;
            case 1:
                return row.cod_estado;
            case 2:
                return row.cod_trans_detalle;
            case 3:
                return row.cantidad_pedido;
            case 4:
//                return row.unidad_medida;
                 if (row.cod_complemento.equals("")) {
//                     System.err.println("Unidad de medida: "+row.unidad_medida.getCodigo());
                    return row.unidad_medida;
                } else {
                    return "";
                }

//      case 5: return row.tipo_item;
            case 5:
                if (row.cod_complemento.equals("")) {
                    return row.item;
                } else {
                    return "";
                }
            case 6:
                if (row.cod_complemento.equals("")) {
                    return row.partida;
                } else {
                    return "";
                }

            case 7:
                return row.articulo;
            case 8:
                return row.articulo_act;
            case 9:
                return row.articulo_alm;
            case 10:
                return row.dbc;
            case 11:
                return row.precio_unit;
            case 12:
                return row.sub_total;
            case 13:
                return row.cod_item;
        }
        return "";
    }

//    @Override
    public void setValueAt(Object value, int nRow, int nCol) {
        
        if (nRow < 0 || nRow >= getRowCount()) {
            return;
        }
        ItemsData row = (ItemsData) lista.get(nRow);
        String svalue = "";
        if (nCol != 4 && nCol != 6 ) {
//            System.err.println("imprimimos --> "+value.toString());
            svalue = value.toString();
        }
//        if (nCol != 4) {
//            System.err.println("imprimimos --> "+value.toString());
//            svalue = value.toString();
//        }

        switch (nCol) {
            case 0:
                row.cod_complemento = svalue;
                break;
            case 1:
                row.cod_estado = svalue;
                break;
            case 2:
                row.cod_trans_detalle = svalue;
                break;
            case 3:
                //row.cantidad_pedido = svalue;
                try {
                    if (!("".equals(svalue.trim()))) {
                        int n = new Integer(svalue.trim());
                        row.cantidad_pedido = svalue.trim();

                        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                        AdquiWS_PortType puerto = servicio.getAdquiWS();
                        Map[] datos = null;
                        if (cod_rol == 5) {
                            if (!"".equals(svalue)) {
                                datos = puerto.updateCantidadItem2("SET-updateCantidadItem", Integer.parseInt(this.getValueAt(nRow, 2).toString()), n,cod_user);
                            }
                        }
                    } else {
                        row.cantidad_pedido = "";
                    }

                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(orden, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 4:
//                row.unidad_medida = svalue;
                System.err.println("**/*/wujuuu");
                if (row.cod_complemento.equals("")) {
                    row.unidad_medida = (UnidadMedida) value;
                    System.err.println("El codigo es:" + row.unidad_medida.getCodigo().split(" - ")[0]);
//        row.partida.setCodigo(svalue);
                    //Partida.setCodigo((Partida) svalue);
                    try {
                        //String partida = svalue.trim();
                        int unidad_medida = Integer.parseInt(row.unidad_medida.getCodigo().split(" - ")[0]);
                        
                        System.out.println("----> Las unidades de medida son - " + unidad_medida);
                        int cod_trans_detalle = Integer.parseInt(this.getValueAt(nRow, 2).toString());
                        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                        AdquiWS_PortType puerto = servicio.getAdquiWS();
                        puerto.setUnidadMedida2("SET-setUnidadMedida", cod_trans_detalle, unidad_medida,cod_user);
                    } catch (Exception e) {
                        System.out.println("Error al actualizar la unidad de medida");
                    }
                    break;
                }
//                break;
            case 5:
                row.item = svalue;
//        row.tipo_item = new Item(svalue);
                break;
            case 6:
//        row.partida = new Partida(svalue);
//          System.out.println(((Partida) value).getCodigo());
                if (row.cod_complemento.equals("")) {
                    row.partida = (Partida) value;
                    System.out.println("El codigo es:" + row.partida.getCodigo().split(" - ")[0]);
//        row.partida.setCodigo(svalue);
                    //Partida.setCodigo((Partida) svalue);
                    try {
                        //String partida = svalue.trim();
                        String partida = row.partida.getCodigo().split(" - ")[0];
                        System.out.println("----> Las partidas son - " + partida);
                        int cod_trans_detalle = Integer.parseInt(this.getValueAt(nRow, 2).toString());
                        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                        AdquiWS_PortType puerto = servicio.getAdquiWS();
                        puerto.setPartida2("SET-setPartida", cod_trans_detalle, partida,cod_user);
                    } catch (Exception e) {
                        System.out.println("Error al actualizar la partida");
                    }
                    break;
                }

            case 7:
                row.articulo = svalue;
                break;
            case 8:
                row.articulo_act = svalue.trim();
                try {
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    Map[] datos = null;
                    if (cod_rol == 5) {
                        String detalle = null;
                        if (!"".equals(svalue.trim())) {
                            detalle = "'" + svalue.trim() + "'";
                            datos = puerto.setNuevoComplemento("SET-upDateNuevoComplemento", Integer.parseInt(this.getValueAt(nRow, 0).toString()), detalle);
                        }
                    }
                } catch (RemoteException e) {
                    javax.swing.JOptionPane.showMessageDialog(orden, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                } catch (ServiceException e) {
                    System.out.println(e);
                }
                break;
            case 9:
                row.articulo_alm = svalue.trim();
                try {
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    Map[] datos = null;
                    if (cod_rol == 2) {
                        String detalle = null;
                        if (!"".equals(svalue.trim())) {
                            detalle = "'" + svalue.trim() + "'";
                            datos = puerto.setNuevoComplementoIngAlm("SET-upDateNuevoComplementoIngAlm", Integer.parseInt(this.getValueAt(nRow, 0).toString()), detalle);
                        }
                    }
                } catch (RemoteException e) {
                    javax.swing.JOptionPane.showMessageDialog(orden, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                } catch (ServiceException e) {
                    System.out.println(e);
                }
                break;
            case 10:
                row.dbc = svalue;
                break;
            case 11:
//          System.out.println("Entro yeihhh :D");
                try {
                    if (!("".equals(svalue.trim()))) {
                        double n = new Double(svalue.trim());
                        row.precio_unit = svalue.trim();

                        AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                        AdquiWS_PortType puerto = servicio.getAdquiWS();
                        Map[] datos = null;
                        if (cod_rol == 5) {
                            if (!"".equals(svalue)) {
                                datos = puerto.setDetallePrecioUnit2("SET-upDatedetPrecUnit", Integer.parseInt(this.getValueAt(nRow, 2).toString()), n,cod_user);
                            }
                        }
                    } else {
                        row.precio_unit = "";
                    }
                    orden.MultiplicaCantidadPrecioUnit(nRow);
                } catch (RemoteException e) {
                    javax.swing.JOptionPane.showMessageDialog(orden, "<html> error de conexion con el servidor <br> " + e, "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                } catch (ServiceException e) {
                    System.out.println(e);
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(orden, "EL PRECIO UNITARIO DEBE TENER MONTOS REALES", "SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 12:
                row.sub_total = svalue;
                orden.SumaPreciosTotales();
                break;
            case 13:
                row.cod_item = svalue;
                break;
        }
    }

    public void insert(int row) {
        if (row < 0) {
            row = 0;
        }
        if (row > lista.size()) {
            row = lista.size();
        }
        lista.add(row, new ItemsData());
    }

    public boolean delete(int row) {
        if (row < 0 || row >= lista.size()) {
            return false;
        }
        lista.remove(row);
        return true;
    }
}
