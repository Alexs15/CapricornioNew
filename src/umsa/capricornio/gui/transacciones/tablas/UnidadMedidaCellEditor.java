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
public class UnidadMedidaCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
 
    private UnidadMedida unidad_medida;
    private List<UnidadMedida> listUnidadMedida;
     
    public UnidadMedidaCellEditor(List<UnidadMedida> listUnidadMedida) {
        this.listUnidadMedida = listUnidadMedida;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.unidad_medida;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        
        System.out.println("El column es: "+column);
        if (value instanceof UnidadMedida) {
            this.unidad_medida = (UnidadMedida) value;
        }
         
        JComboBox comboUnidadMedida = new JComboBox();
         
        for (UnidadMedida aUnidadMedida : listUnidadMedida) {
            comboUnidadMedida.addItem(aUnidadMedida.getCodigo());
        }
         
        comboUnidadMedida.setSelectedItem(unidad_medida);
        comboUnidadMedida.addActionListener(this);
         
        if (isSelected) {
            
        } else {
            
        }
         
        return comboUnidadMedida;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox comboUnidadMedida = (JComboBox) event.getSource();
        this.unidad_medida = new UnidadMedida (comboUnidadMedida.getSelectedItem().toString());
    }
 
}
