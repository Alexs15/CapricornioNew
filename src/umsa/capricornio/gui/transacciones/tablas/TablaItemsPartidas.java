/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.ItemsPartidaData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author UMSA-JES
 */
public class TablaItemsPartidas extends AbstractTableModel{

    static final public ColumnData m_columns[] = {
        new ColumnData( "Cod_Item", 100, JLabel.CENTER ),
        new ColumnData( "Partida", 100, JLabel.CENTER ),
        new ColumnData( "Item",100, JLabel.CENTER ),
        new ColumnData( "Detalle", 100, JLabel.CENTER ),
        new ColumnData( "Unidad de Medida",100, JLabel.CENTER )
    };
    protected ArrayList lista;
    
    public TablaItemsPartidas(){
         lista = new ArrayList();
    }
    @Override
    public int getRowCount() {
        return lista==null ? 0 : lista.size(); 
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return m_columns.length; 
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getColumnName(int column) {
        String str = m_columns[column].m_title;
        return str;
    }
    @Override
    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
      return "";
    //Vector m_vector=new Vector();
    //TransaccionBandejaData row = (TransaccionBandejaData)m_vector.elementAt(nRow);
    ItemsPartidaData row = (ItemsPartidaData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_item;
      case 1: return row.partida;
      case 2: return row.item;
      case 3: return row.detalle;
      case 4: return row.unidad_medida;
      
    }
    return "";          
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void setValueAt(Object value, int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return;
        //TransaccionBandejaData row = (TransaccionBandejaData)m_vector.elementAt(nRow);
        ItemsPartidaData row = (ItemsPartidaData)lista.get(nRow);
        String svalue = value.toString();
        switch (nCol) {
            case 0:
                row.cod_item = svalue;
                break;
            case 1:
                row.partida = svalue;
                break;
            case 2:
                row.item = svalue;
                break;
            case 3:
                row.detalle = svalue;
                break;
            case 4:
                row.unidad_medida = svalue;
                break;
        }
    }
    public void insert(int row) {
        if (row < 0)
            row = 0;
        if (row > lista.size())
            row = lista.size();
        //m_vector.insertElementAt(new TransaccionBandejaData(), row);
        lista.add(row,new ItemsPartidaData());
  }

  public boolean delete(int row) {
      if (row < 0 || row >= lista.size())
        return false;
      lista.remove(row);
        return true;
  }  
    
}
