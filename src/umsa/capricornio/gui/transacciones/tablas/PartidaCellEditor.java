/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.tablas;

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
public class PartidaCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
 
    private Partida partida;
    private List<Partida> listPartida;
     
    public PartidaCellEditor(List<Partida> listPartida) {
        this.listPartida = listPartida;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.partida;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        
        System.out.println("El column es: "+column);
        if (value instanceof Partida) {
            this.partida = (Partida) value;
        }
         
        JComboBox comboPartida = new JComboBox();
         
        for (Partida aPartida : listPartida) {
            comboPartida.addItem(aPartida.getCodigo());
        }
         
        comboPartida.setSelectedItem(partida);
        comboPartida.addActionListener(this);
         
        if (isSelected) {
            //System.out.println("Brutal!!!");
            //comboPartida.setBackground(table.getSelectionBackground());
            //comboPartida.setFocusable(false);
        } else {
            //comboPartida.setBackground(table.getSelectionForeground());
        }
         
        return comboPartida;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox comboPartida = (JComboBox) event.getSource();
        this.partida = new Partida (comboPartida.getSelectedItem().toString());
    }
 
}
