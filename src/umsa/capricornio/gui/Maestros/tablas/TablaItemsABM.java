/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.Maestros.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.ItemsABMData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaItemsABM extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "Cod_item", 0, JLabel.CENTER ),
    new ColumnData( "Partida", 20, JLabel.CENTER ),
    new ColumnData( "Articulo",130, JLabel.LEFT ),
    new ColumnData( "Unidad_Medida", 20, JLabel.CENTER ),
    new ColumnData( "Gestion", 20, JLabel.LEFT ),
    new ColumnData( "Cod_Tipo_Item", 20, JLabel.LEFT )      
    
  };

  protected ArrayList lista;
  
  public TablaItemsABM() {    
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
    ItemsABMData row = (ItemsABMData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_item;
      case 1: return row.partida;
      case 2: return row.articulo;
      case 3: return row.unidad_medida;    
      case 4: return row.gestion;
      case 5: return row.cod_tipo_item;    
            
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    ItemsABMData row = (ItemsABMData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_item = svalue;
        break;      
      case 1:
        row.partida = svalue;
        break;      
      case 2:
        row.articulo = svalue;
        break;      
      case 3:
        row.unidad_medida = svalue;
        break;            
      case 4:
        row.gestion = svalue;
        break;
      case 5:
        row.cod_tipo_item = svalue;
        break;    
                  
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new ItemsABMData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
