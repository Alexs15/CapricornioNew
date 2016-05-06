/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;


import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author UMSA-JES
 */
public class UnidadMedidaCellRenderer extends DefaultTableCellRenderer {
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof UnidadMedida) {
            UnidadMedida unidad_medida = (UnidadMedida) value;
            setText(unidad_medida.getCodigo());
        }
         
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getSelectionForeground());
        }
         
        return this;
    }
     
}
