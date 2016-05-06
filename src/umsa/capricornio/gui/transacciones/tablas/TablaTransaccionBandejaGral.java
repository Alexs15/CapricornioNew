/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

//import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.TransaccionBandejaData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaTransaccionBandejaGral extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "ct", 0, JLabel.LEFT ),
    new ColumnData( "ce", 0, JLabel.LEFT ),
    new ColumnData( "ctn", 0, JLabel.CENTER ),    
    new ColumnData( "cw", 0, JLabel.CENTER ),
    new ColumnData( "Nro.",60, JLabel.CENTER ),
    new ColumnData( "Tramite",150, JLabel.CENTER ),
    new ColumnData( "Detalle",300, JLabel.LEFT ),
    new ColumnData( "Unidad Solicitante", 250, JLabel.LEFT ),
    new ColumnData( "Unidad Destino", 250, JLabel.LEFT ),
    new ColumnData( "Estado", 100, JLabel.CENTER ),    
    new ColumnData( "cuantia", 0, JLabel.CENTER ),    
    new ColumnData( "del", 0, JLabel.CENTER ),   
    new ColumnData( "hasta", 0, JLabel.CENTER )    
  };

  //protected Vector m_vector;  
  protected ArrayList lista;

  public TablaTransaccionBandejaGral() {    
    //m_vector = new Vector();
      lista = new ArrayList();
  }

  public int getRowCount() {
    //return m_vector==null ? 0 : m_vector.size(); 
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
      return false;
  }

  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    //Vector m_vector=new Vector();
    //TransaccionBandejaData row = (TransaccionBandejaData)m_vector.elementAt(nRow);
    TransaccionBandejaData row = (TransaccionBandejaData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_transaccion;
      case 1: return row.cod_estado;
      case 2: return row.cod_trans_nro;
      case 3: return row.cod_w;
      case 4: return row.nro_tramite;
      case 5: return row.tipo_tramite;
      case 6: return row.detalle;
      case 7: return row.unidad_sol;
      case 8: return row.unidad_des;
      case 9: return row.estado;
      case 10: return row.cuantia;
      case 11: return row.del;
      case 12: return row.hasta;
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    //TransaccionBandejaData row = (TransaccionBandejaData)m_vector.elementAt(nRow);
    TransaccionBandejaData row = (TransaccionBandejaData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_transaccion = svalue;
        break;
      case 1:
        row.cod_estado = svalue;
        break;
      case 2:
        row.cod_trans_nro = svalue;
        break;
      case 3:
        row.cod_w= svalue;
        break;
      case 4:
        row.nro_tramite = svalue;
        break;
      case 5:
        row.tipo_tramite = svalue;
        break;       
      case 6:
        row.detalle = svalue;
        break;       
      case 7:
        row.unidad_sol = svalue;
        break;
      case 8:
        row.unidad_des = svalue;
        break;
      case 9:
        row.estado = svalue;
        break;      
      case 10:
        row.cuantia = svalue;
        break;      
      case 11:
        row.del = svalue;
        break;      
      case 12:
        row.hasta = svalue;
        break;      
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();
    //m_vector.insertElementAt(new TransaccionBandejaData(), row);
    lista.add(row,new TransaccionBandejaData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
