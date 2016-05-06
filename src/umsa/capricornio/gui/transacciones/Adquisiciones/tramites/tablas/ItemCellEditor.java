/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author UMSA-JES
 */
public class ItemCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
 
    private Item item;
    private List<Item> listItem;
     
    public ItemCellEditor(List<Item> listItem) {
        this.listItem = listItem;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.item;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof Item) {
            this.item = (Item) value;
        }
         
        JComboBox comboItem = new JComboBox();
         
        for (Item aItem : listItem) {
            comboItem.addItem(aItem.getCod_item());
        }
         
        comboItem.setSelectedItem(item);
        comboItem.addActionListener(this);
         
        if (isSelected) {
            //System.out.println("Brutal!!!");
            //comboPartida.setBackground(table.getSelectionBackground());
            //comboPartida.setFocusable(false);
        } else {
            //comboPartida.setBackground(table.getSelectionForeground());
        }
         
        return comboItem;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox comboItem = (JComboBox) event.getSource();
        this.item = new Item (comboItem.getSelectedItem().toString());
    }
 
}
