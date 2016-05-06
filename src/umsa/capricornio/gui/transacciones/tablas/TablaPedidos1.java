/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.PedidosTblData;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.FrmtareasAdicionales;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaPedidos1 extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "MES", 0, JLabel.RIGHT ),
    new ColumnData( "ID", 0, JLabel.RIGHT ),
    new ColumnData( "TAREA REALIZADA", 400, JLabel.LEFT )    
  };
  
  protected ArrayList lista;
  FrmtareasAdicionales orden;
  int cod_rol;
  int cod_user;
  //protected ArrayList lista;
  
  public TablaPedidos1(FrmtareasAdicionales orden, int cod_rol, int cod_user) {    
      lista = new ArrayList();
      this.orden = orden;
      this.cod_rol = cod_rol;
      this.cod_user = cod_user;
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
      if(nCol==2)
          return true;
      else
        return false;
  }

  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    PedidosTblData row = (PedidosTblData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_trans_detalle;
      case 1: return row.cod_estado;
      case 2: return row.articulo;      
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    PedidosTblData row = (PedidosTblData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_trans_detalle = svalue;
        break;      
      case 1:
        row.cod_estado = svalue;
        break;            
      case 2:
        //row.articulo = svalue;
      try {
                if (!("".equals(svalue.trim()))) {
                    //double n = new Double(svalue.trim());
                    String n=svalue.toString();
                    row.articulo = svalue.trim();

                    AdquiWSServiceLocator servicio = new AdquiWSServiceLocator();
                    AdquiWS_PortType puerto = servicio.getAdquiWS();
                    Map[] datos = null;
                    if (cod_rol == 5) {
                        if (!"".equals(svalue)) {
                            System.out.println("muestra el id "+this.getValueAt(nRow, 1));
                            int cod=Integer.parseInt(this.getValueAt(nRow, 1).toString());
                            //datos = puerto.setDetallePrecioUnit2("SET-upDatedetPrecUnit", Integer.parseInt(this.getValueAt(nRow, 2).toString()), n,cod_user);
                            datos=puerto.modificar_tarea(cod,n);
                            System.out.println("si funciona "+n);
                        }
                    }
                } else {
                    row.articulo = "";
                }
                //orden.MultiplicaCantidadPrecioUnit(nRow);
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
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new PedidosTblData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
