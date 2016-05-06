/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.Maestros.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.UnidadEjecutoraData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaUnidadEjecutora extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "ca", 0, JLabel.CENTER ),
    new ColumnData( "actividad", 450, JLabel.LEFT ),
    new ColumnData( "apertura", 130, JLabel.CENTER ),
    new ColumnData( "almacen", 200, JLabel.LEFT ),
    new ColumnData( "da", 200, JLabel.LEFT )
  };

  protected ArrayList lista;
  
  public TablaUnidadEjecutora() {    
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
    UnidadEjecutoraData row = (UnidadEjecutoraData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_apert;
      case 1: return row.detalle;
      case 2: return row.apertura;
      case 3: return row.almacen;
      case 4: return row.da;    
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    UnidadEjecutoraData row = (UnidadEjecutoraData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_apert = svalue;
        break;      
      case 1:
        row.detalle = svalue;
        break;      
      case 2:
        row.apertura = svalue;
        break;      
      case 3:
        row.almacen = svalue;
        break;
      case 4:
        row.da = svalue;
        break;    
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new UnidadEjecutoraData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
