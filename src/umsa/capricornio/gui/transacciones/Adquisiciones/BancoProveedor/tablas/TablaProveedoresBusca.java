/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.ProvBuscData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */

public class TablaProveedoresBusca extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "documento", 60, JLabel.RIGHT ),
    new ColumnData( "tipo", 15, JLabel.RIGHT ),    
    new ColumnData( "clase_persona", 60, JLabel.CENTER ),
    new ColumnData( "nombre",290, JLabel.LEFT ),
    new ColumnData( "nombre_comercial",290, JLabel.LEFT ),
    new ColumnData( "adh nombre",0, JLabel.CENTER ),
    new ColumnData( "adh documento",0, JLabel.CENTER ),
    new ColumnData( "dir lugar",0, JLabel.CENTER ),
    new ColumnData( "dir direccion",0, JLabel.CENTER ),
    new ColumnData( "dir telefono",0, JLabel.CENTER ),
    new ColumnData( "dir email",0, JLabel.CENTER )
  };

  protected ArrayList lista;  
  
  public TablaProveedoresBusca() {    
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
    ProvBuscData row = (ProvBuscData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.documento;
      case 1: return row.tipo_id;      
      case 2: return row.descripcion;
      case 3: return row.clase_beneficiario;
      case 4: return row.nombre;
      case 5: return row.nombre_comercial;
      case 6: return row.adh_nombre;
      case 7: return row.adh_documento;
      case 8: return row.dir_lugar;
      case 9: return row.dir_direccion;
      case 10: return row.dir_telefono;
      case 11: return row.dir_email;    
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    ProvBuscData row = (ProvBuscData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.documento = svalue;
        break;      
      case 1:
        row.tipo_id = svalue;
        break;       
      case 2:
        row.descripcion = svalue;
        break;       
      case 3:
        row.clase_beneficiario = svalue;
        break;       
      case 4:
        row.nombre = svalue;
        break;
      case 5:
        row.nombre_comercial = svalue;
        break;            
      case 6:
        row.adh_nombre = svalue;
        break;            
      case 7:
          row.adh_documento = svalue;          
          break;
      case 8:
          row.dir_lugar = svalue;          
          break;
      case 9:
          row.dir_direccion = svalue;          
          break;
      case 10:
          row.dir_telefono = svalue;          
          break;
      case 11:
          row.dir_email = svalue;          
          break;    
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new ProvBuscData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
