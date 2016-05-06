/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.Preventivo;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author UMSA-JES
 */
public class TablaPreventivo extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "Programa", 5, JLabel.CENTER ),
    new ColumnData( "Proyecto", 5, JLabel.CENTER ),
    new ColumnData( "Actividad", 5, JLabel.CENTER ),
    new ColumnData( "Partida", 15, JLabel.CENTER ),
    new ColumnData( "Importe", 15, JLabel.CENTER ),
//    new ColumnData( "Monto", 10, JLabel.RIGHT )   
  };

  protected ArrayList lista;

  public TablaPreventivo() {    
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
    Preventivo row = (Preventivo)lista.get(nRow);
    switch (nCol) {
      case 0: return row.m_programa;
      case 1: return row.m_proyecto;
      case 2: return row.m_actividad;      
      case 3: return row.m_partida;
      case 4: return row.m_importe;      
//      case 5: return row.m_monto;      
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    Preventivo row = (Preventivo)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
          row.m_programa = svalue;
        break;  
      case 1:        
          row.m_proyecto = svalue;
        break;      
      case 2:        
          row.m_actividad = svalue;
        break;      
      case 3:
          row.m_partida = svalue;
        break;
      case 4:
          row.m_importe = svalue;
        break;      
//      case 5:
//          row.m_monto = svalue;
//        break;      
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();
    lista.add(row,new Preventivo());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
