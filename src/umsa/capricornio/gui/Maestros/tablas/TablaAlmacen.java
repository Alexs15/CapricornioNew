/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.Maestros.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.AlmacenData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaAlmacen extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "ca", 0, JLabel.CENTER ),
    new ColumnData( "almacen", 200, JLabel.LEFT ),
    new ColumnData( "facultad", 200, JLabel.LEFT ),
    new ColumnData( "cod_fac", 100, JLabel.CENTER )    
  };

  protected ArrayList lista;
  
  public TablaAlmacen() {    
      lista = new ArrayList();      
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
        return false;
  }

  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    AlmacenData row = (AlmacenData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_almacen;
      case 1: return row.almacen;
      case 2: return row.facultad;
      case 3: return row.cod_fac;      
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    AlmacenData row = (AlmacenData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_almacen = svalue;
        break;      
      case 1:
        row.almacen = svalue;
        break;      
      case 2:
        row.facultad = svalue;
        break;      
      case 3:
        row.cod_fac = svalue;
        break;            
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new AlmacenData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
