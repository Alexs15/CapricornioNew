/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablas_nuevas;

import umsa.capricornio.gui.transacciones.tablas.*;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.RestriccionesFechasData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author UMSA-JES
 */
//esto es nuevo para ver en git
//COMMIT DE HENRRY
public class TablaFechaRestricciones extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "Nro RI", 1, JLabel.CENTER ),
    new ColumnData( "Nro SC", 1, JLabel.CENTER ),
    new ColumnData( "Nro OC", 5, JLabel.CENTER ),
    new ColumnData( "Proceso", 40, JLabel.LEFT ),  
    new ColumnData( "F. Conclusión", 20, JLabel.CENTER ),
    new ColumnData( "Dias Restantes", 5, JLabel.CENTER )
//    new ColumnData( "Partida", 15, JLabel.CENTER ),
//    new ColumnData( "Importe", 15, JLabel.CENTER ),
//    new ColumnData( "Monto", 10, JLabel.RIGHT )   
  };

  protected ArrayList lista;

  public TablaFechaRestricciones() {    
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
    RestriccionesFechasData row = (RestriccionesFechasData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.nro_resolucion_inicio;
      case 1: return row.nro_solicitud_compra;
      case 2: return row.fecha_inicio;
      case 3: return row.fecha_final;
      case 4: return row.dias_restantes;
      case 5: return row.m_actividad;      
//      case 3: return row.m_partida;
//      case 4: return row.m_importe;      
//      case 5: return row.m_monto;      
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    RestriccionesFechasData row = (RestriccionesFechasData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
          row.nro_resolucion_inicio = svalue;
        break;
      case 1:
          row.nro_solicitud_compra = svalue;
        break;
      case 2:        
          row.fecha_inicio = svalue;
        break;
      case 3:        
          row.fecha_final = svalue;
        break;   
      case 4:        
          row.dias_restantes = svalue;
        break;   
      case 5:        
          row.m_actividad = svalue;
        break;      
//      case 3:
//          row.m_partida = svalue;
//        break;
//      case 4:
//          row.m_importe = svalue;
//        break;      
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
    lista.add(row,new RestriccionesFechasData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}