/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.XXXIngresoMateriales.tabla;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.XXXItemsIngAlmData;
import umsa.capricornio.gui.transacciones.XXXIngresoMateriales.XXXFrmIngresoMateriales;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class XXXTablaItemsIngAlm extends AbstractTableModel 
{
    static final public ColumnData m_columns[] = {
        new ColumnData( "cc", 0, JLabel.RIGHT ),
        new ColumnData( "ctd", 0, JLabel.RIGHT ),
        new ColumnData( "Cantidad",60, JLabel.CENTER ),
        new ColumnData( "Unid.Med.",120, JLabel.CENTER ),
        new ColumnData( "tipo Item",150, JLabel.LEFT ),
        new ColumnData( "Articulo", 400, JLabel.LEFT ),
        new ColumnData( "Precio unit", 100, JLabel.RIGHT ),    
        new ColumnData( "Sub Total", 100, JLabel.RIGHT )    
    };

  protected ArrayList lista;
  XXXFrmIngresoMateriales items;
  int cod_rol;
  String cod_estado;
  
    public XXXTablaItemsIngAlm(XXXFrmIngresoMateriales items) {    
        lista = new ArrayList();
        this.items=items;        
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
        if (nCol==6)
            if ((cod_rol==5) && !("".equals(this.getValueAt(nRow, 0))))
                return true;
            else 
                return false;
        else if (nCol==7)
            if ((cod_rol==2) &&("ALM1".equals(cod_estado))&& !("".equals(this.getValueAt(nRow, 0))))
                return true;
            else 
                return false;
        else if (nCol==8)
                if ((cod_rol==5) &&("".equals(this.getValueAt(nRow, 0))))
                    return true;
                else
                    return false;
        else 
            return false;
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return "";
        XXXItemsIngAlmData row = (XXXItemsIngAlmData)lista.get(nRow);
        switch (nCol) {
            case 0: return row.cod_complemento;
            case 1: return row.cod_trans_detalle;
            case 2: return row.cantidad_pedido;
            case 3: return row.unidad_medida;
            case 4: return row.tipo_item;
            case 5: return row.articulo;
            case 6: return row.precio_unit;
            case 7: return row.sub_total;
        }
        return "";          
    }             
  
    @Override
    public void setValueAt(Object value, int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return;    
        XXXItemsIngAlmData row = (XXXItemsIngAlmData)lista.get(nRow);
        String svalue = value.toString();

        switch (nCol) {
            case 0:
                row.cod_complemento = svalue;
                break;      
            case 1:
                row.cod_trans_detalle = svalue;
                break;      
            case 2:
                row.cantidad_pedido = svalue;
                break;       
            case 3:
                row.unidad_medida = svalue;
                break;
            case 4:
                row.tipo_item = svalue;
                break;
            case 5:
                row.articulo = svalue;
                break;      
            case 6:
                row.precio_unit= svalue;
                try {
                    if (!("".equals(svalue.trim())))
                        row.precio_unit = svalue.trim();                        
                    else 
                        row.precio_unit = "";
                    items.MultiplicaCantidadPrecioUnit(nRow);
                }              
              catch (NumberFormatException e) {
                  javax.swing.JOptionPane.showMessageDialog(items,"EL PRECIO UNITARIO DEBE TENER MONTOS REALES","SYSTEM CAPRICORN",
                          javax.swing.JOptionPane.ERROR_MESSAGE);
               } 
                break;
            case 7:
                row.sub_total= svalue;
                //boveda.SumaTrans();
                break;  
        }
    }
  
    public void insert(int row) {
        if (row < 0)
            row = 0;
        if (row > lista.size())
            row = lista.size();    
        lista.add(row,new XXXItemsIngAlmData());
    }

    public boolean delete(int row) {
        if (row < 0 || row >= lista.size())
            return false;
        lista.remove(row);
        return true;
    }  
}
