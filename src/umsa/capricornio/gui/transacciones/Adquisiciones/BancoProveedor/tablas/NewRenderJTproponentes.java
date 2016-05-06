/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

/**
 *
 * @author UMSA-JES
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class NewRenderJTproponentes extends DefaultTableCellRenderer
{
 public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column)
   {
      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      if ( hasFocus )
      {
         this.setOpaque(true);
         this.setBackground(Color.RED);
         this.setForeground(Color.YELLOW);
      } else {
         // Restaurar los valores por defecto
      }

      return this;
   }
}
