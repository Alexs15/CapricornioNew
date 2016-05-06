/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.SansionesData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaSansiones extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "cps", 0, JLabel.RIGHT ),
    new ColumnData( "Fec ini", 80, JLabel.CENTER ),    
    new ColumnData( "fec fin", 80, JLabel.CENTER ),
    new ColumnData( "Observacion", 350, JLabel.LEFT )    
  };

  protected ArrayList lista;  
  
  public TablaSansiones() {    
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
    SansionesData row = (SansionesData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_prov_sansion;
      case 1: return row.fec_ini;      
      case 2: return row.fec_fin;
      case 3: return row.obs;      
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    SansionesData row = (SansionesData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_prov_sansion = svalue;
        break;      
      case 1:
        row.fec_ini = svalue;
        break;       
      case 2:
        row.fec_fin = svalue;
        break;       
      case 3:
        row.obs = svalue;
        break;      
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new SansionesData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
