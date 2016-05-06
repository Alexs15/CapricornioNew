/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.ItemsData;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.FrmItems;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaItems extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "cc", 0, JLabel.RIGHT ),
    new ColumnData( "ce", 0, JLabel.RIGHT ),
    new ColumnData( "ctd", 0, JLabel.RIGHT ),
    new ColumnData( "Cantidad",140, JLabel.CENTER ),
    new ColumnData( "Unidad Medida",180, JLabel.CENTER ),
    new ColumnData( "Partida", 260, JLabel.LEFT ),
    new ColumnData( "Articulo", 300, JLabel.LEFT ),
    new ColumnData( "Precio U.", 130, JLabel.CENTER ),
    new ColumnData( "Subtotal", 130, JLabel.CENTER )
    
       
  };

  protected ArrayList lista;
  FrmItems items;
  int cod_rol;
  String cod_estado;
  int cod_user;
  //private List<Partida> listPartida = new ArrayList() ;
  
  public TablaItems(FrmItems items,int cod_rol,String cod_estado,int cod_user) {    
      lista = new ArrayList();
      this.items=items;
      this.cod_rol=cod_rol;
      this.cod_estado=cod_estado;
      this.cod_user=cod_user;
      //this.listPartida.addAll(listPartida);
  }

  public int getRowCount() {    
    return lista==null ? 0 : lista.size(); 
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
      if(nCol==5 && cod_rol==4)
          return true;
      if(nCol==4 && cod_rol==4)
          return true;
      if(nCol == 7 && cod_rol==4)
          return true;
      return false;
//            if (nCol==8)
//                if ((cod_rol==6) && !("".equals(this.getValueAt(nRow, 0))))
//                    return true;
//                else 
//                    return false;
//            else if (nCol==9)
//                if ((cod_rol==3) &&("ALM1".equals(cod_estado))&& !("".equals(this.getValueAt(nRow, 0))))
//                    return true;
//                else 
//                    return false;
//            else if (nCol==11)
//                    if ((cod_rol==6) &&("".equals(this.getValueAt(nRow, 0))))
//                        return true;
//                    else
//                        return false;
////            else if (nCol==6)
////                    if(cod_rol==4)
////                        return true;
////                    else
////                        return false;
//            else 
//                return false;
//      return false;
  }

  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    ItemsData row = (ItemsData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_complemento;
      case 1: return row.cod_estado;
      case 2: return row.cod_trans_detalle;
      case 3: return row.cantidad_pedido;
      case 4: 
//          return row.unidad_medida;
           if(row.cod_complemento.equals("")){
//               System.err.println("LA UNIDAD DE MEDIDA: "+row.unidad_medida.getCodigo());
               return row.unidad_medida;
           }
              
          else 
              return "";   
      case 5: 
          if(row.cod_complemento.equals(""))
              return row.partida;
          else 
              return "";    
      case 6: return row.articulo;
      case 7: return row.precio_unit;
      case 8: return row.sub_total;
      
      
    }
    return "";          
  }             
  
//    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return; 
    
    ItemsData row = (ItemsData)lista.get(nRow);
    String svalue="";
    if(nCol!=5 && nCol!=4){
//        System.err.println("el nCol es -> "+nCol);
        svalue = value.toString();
    }
    
    switch (nCol) {
      case 0:
          System.err.println("set cod_complemento: "+svalue);
        row.cod_complemento = svalue;
        break;      
      case 1:
          System.err.println("set cod_estado: "+svalue);
        row.cod_estado = svalue;
        break;      
      case 2:
          System.err.println("set cod_trans_detalle: "+svalue);
        row.cod_trans_detalle = svalue;
        break;       
      case 3:
          System.err.println("set cantidad_pedido: "+svalue);
        row.cantidad_pedido = svalue;
        break;       
      case 4:
//        row.unidad_medida = svalue;
//        break;
          System.err.println("SET to Cambiar Unidad de Medida");
          
          if (row.cod_complemento.equals("")) {
            row.unidad_medida = (UnidadMedida) value;
//            System.out.println("El codigo es:" + row.unidad_medida.getCodigo());
//            System.out.println("El codigo es:" + row.unidad_medida.getCodigo().split(" - ")[0]);
  //        row.partida.setCodigo(svalue);
            //Partida.setCodigo((Partida) svalue);
            try {
                //String partida = svalue.trim();
                int unidad_medida = Integer.parseInt(row.unidad_medida.getCodigo().split(" - ")[0]);
                int cod_trans_detalle = Integer.parseInt(this.getValueAt(nRow, 2).toString()); 
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                puerto.setUnidadMedida2("SET-setUnidadMedida", cod_trans_detalle, unidad_medida,cod_user);
            } catch (Exception e) {
                System.out.println("Error al actualizar la unidad de medida");
            }
          break;
        }
          
      case 5:
          System.err.println("SET to Cambiar Partida");
          if (row.cod_complemento.equals("")) {
            row.partida = (Partida) value;
//            System.err.println("1) El codigo es:" + row.partida.getCodigo());
//            System.err.println("2) El codigo es:" + row.partida.getCodigo().split(" - ")[0]);
  //        row.partida.setCodigo(svalue);
            //Partida.setCodigo((Partida) svalue);
            try {
                //String partida = svalue.trim();
                String partida = row.partida.getCodigo().split(" - ")[0];
                int cod_trans_detalle = Integer.parseInt(this.getValueAt(nRow, 2).toString()); 
                AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                AdquiWS_PortType puerto = servicio.getAdquiWS();
                puerto.setPartida2("SET-setPartida", cod_trans_detalle, partida,cod_user);
            } catch (Exception e) {
                System.out.println("Error al actualizar la partida");
            }
          break;
        }
      case 6:
        row.articulo = svalue;
        break;
      case 7:
          System.err.println("SET to Cambiar Precio");
          try { 
              if (!("".equals(svalue.trim()))) {
                    double n = new Double(svalue.trim());
                    row.precio_unit = svalue.trim();
                    
                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
//                    Map[] datos=null;
                    if (cod_rol==4){
                        if (!"".equals(svalue))
                            puerto.setDetallePrecioUnit2("SET-upDatedetPrecUnit", Integer.parseInt(this.getValueAt(nRow, 2).toString()),n,cod_user);
                    }
              }
              else
                  row.precio_unit = "0";
              items.MultiplicaCantidadPrecioUnit(nRow);
            }
          catch (RemoteException e){
                javax.swing.JOptionPane.showMessageDialog(null,"<html> error de conexion con el servidor <br> "+e,"SYSTEM CAPRICORN",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
          }
          catch (ServiceException e){ System.out.println(e);} 
          catch (NumberFormatException e) {
              javax.swing.JOptionPane.showMessageDialog(null,"EL PRECIO UNITARIO DEBE TENER MONTOS REALES","SYSTEM CAPRICORN",
                      javax.swing.JOptionPane.ERROR_MESSAGE);
               }       
//        row.precio_unit = svalue;
        break;
      case 8:
          System.err.println("set sub_total: "+svalue);
        row.sub_total = svalue;
        items.SumaPreciosTotales();
        break;    
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new ItemsData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}