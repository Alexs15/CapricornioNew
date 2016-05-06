/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.utilitarios.herramientas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.Item;
import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedida;

/**
 *
 * @author julian
 */

public class MiRenderer implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    int columna;
    public MiRenderer(int columna){
        this.columna=columna;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {

        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JLabel) renderer).setOpaque(true);

        ((JLabel) renderer).setHorizontalAlignment(columna);
        
        Color foreground, background;
        if (value instanceof Item) {
            Item item = (Item) value;
            DEFAULT_RENDERER.setText(item.getCod_item());
        }
        if (value instanceof Partida) {
            Partida partida = (Partida) value;
            DEFAULT_RENDERER.setText(partida.getCodigo());
        }
        if (value instanceof UnidadMedida) {
            UnidadMedida unidad_medida = (UnidadMedida) value;
            DEFAULT_RENDERER.setText(unidad_medida.getCodigo());
        }
        if (isSelected) {
            foreground = new java.awt.Color(47,69,27);
            background = new java.awt.Color(155,195,222);
            if ("D".equals(table.getValueAt(row, 1)) || "IN".equals(table.getValueAt(row, 1)))
                foreground= Color.RED;                        
        }
        else{
            if (row % 2 == 0) {
                foreground = new java.awt.Color(47,69,27);
                background = Color.white;
            }
            else { foreground = new java.awt.Color(47,69,27);
                   background = new java.awt.Color(223,231,235);
            }
            if ("D".equals(table.getValueAt(row, 1)) || "IN".equals(table.getValueAt(row, 1)))
                foreground= Color.RED;
        }
        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;
  }
}