/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.XXXIngresoMateriales.tabla;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.XXXItemsBuscaData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class XXXTablaItemsBusca extends AbstractTableModel 
{
    static final public ColumnData m_columns[] = {
        new ColumnData( "cia", 0, JLabel.RIGHT ),
        new ColumnData( "Unid.Med.",120, JLabel.CENTER ),
        new ColumnData( "tipo Item",150, JLabel.LEFT ),
        new ColumnData( "Articulo", 400, JLabel.LEFT ),        
    };

  protected ArrayList lista;
  
    public XXXTablaItemsBusca() {    
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
        XXXItemsBuscaData row = (XXXItemsBuscaData)lista.get(nRow);
        switch (nCol) {
            case 0: return row.cod_item;
            case 1: return row.unidad_medida;
            case 2: return row.tipo_item;
            case 3: return row.articulo;            
        }
        return "";          
    }             
  
    @Override
    public void setValueAt(Object value, int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return;    
        XXXItemsBuscaData row = (XXXItemsBuscaData)lista.get(nRow);
        String svalue = value.toString();

        switch (nCol) {
            case 0:
                row.cod_item = svalue;
                break;      
            case 1:
                row.unidad_medida = svalue;
                break;
            case 2:
                row.tipo_item = svalue;
                break;
            case 3:
                row.articulo = svalue;
                break;                  
        }
    }
  
    public void insert(int row) {
        if (row < 0)
            row = 0;
        if (row > lista.size())
            row = lista.size();    
        lista.add(row,new XXXItemsBuscaData());
    }

    public boolean delete(int row) {
        if (row < 0 || row >= lista.size())
            return false;
        lista.remove(row);
        return true;
    }  
}
