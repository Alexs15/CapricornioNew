/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.relacionadores.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.RelacionadoresData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author Usuario
 */
public class TablaUsrFirma extends AbstractTableModel{

    static final public ColumnData m_columns[] = {
    new ColumnData( "",0 , JLabel.CENTER ),   
    new ColumnData( "",0 , JLabel.CENTER ),   
    new ColumnData( "Usuario", 450, JLabel.CENTER ),
    new ColumnData( "FirmaUsuario", 450, JLabel.CENTER )
  };
    
  protected ArrayList lista;

  public TablaUsrFirma() {      
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
    RelacionadoresData row = (RelacionadoresData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.m_cod1;
      case 1: return row.m_cod2;
      case 2: return row.m_detalle1;
      case 3: return row.m_detalle2;      
    }
    return "";          
  } 
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    RelacionadoresData row = (RelacionadoresData)lista.get(nRow);
    String svalue = value.toString();

    switch (nCol) {
      case 0:
        row.m_cod1 = svalue; 
        break;
      case 1:
        row.m_cod2= svalue; 
        break;
      case 2:
        row.m_detalle1 = svalue; 
        break;
      case 3:
        row.m_detalle2 = svalue; 
        break;      
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new RelacionadoresData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }
    
}
