/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.Maestros.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.UsuariosData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaUsuario extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "cu", 0, JLabel.CENTER ),
    new ColumnData( "usuario", 250, JLabel.LEFT ),
    new ColumnData( "nombre_resumen", 200, JLabel.LEFT )    
  };

  protected ArrayList lista;
  
  public TablaUsuario() {    
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
    UsuariosData row = (UsuariosData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_usuario;
      case 1: return row.usuario;
      case 2: return row.nombre_resumen;
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    UsuariosData row = (UsuariosData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_usuario = svalue;
        break;      
      case 1:
        row.usuario = svalue;
        break;      
      case 2:
        row.nombre_resumen = svalue;
        break;            
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new UsuariosData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
