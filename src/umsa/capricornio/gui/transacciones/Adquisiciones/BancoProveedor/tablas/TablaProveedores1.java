/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.utilitarios.herramientas.ColumnData;
import umsa.capricornio.domain.ProveedoresData1;

/**
 *
 * @author alex
 */
public class TablaProveedores1 extends AbstractTableModel
{
    static final public ColumnData m_columns[] = {
    new ColumnData("PARTIDA", 20, JLabel.LEFT),
    new ColumnData("CANTIDAD", 7, JLabel.LEFT),
    new ColumnData("UNIDAD", 20, JLabel.LEFT),
    new ColumnData("DETALLE", 250, JLabel.LEFT),
    new ColumnData("GESTION", 30, JLabel.LEFT)
    };
    protected ArrayList lista; 
    
    public TablaProveedores1() {    
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
    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
      return "";
    ProveedoresData1 row = (ProveedoresData1)lista.get(nRow);
    switch (nCol) {
      /*case 0: return row.cod_proveedor;
      case 1: return row.cod_estado;      
      case 2: return row.web;
      case 3: return row.gran_act;
      case 4: return row.razon_nit;
      case 5: return row.partida;
      case 6: return row.casa_comercial;
      case 7: return row.servicio;
      case 8: return row.direccion;
      case 9: return row.telefono;
      case 10: return row.fax;
      case 11: return row.nit;
      case 12: return row.estado;*/
      case 0: return row.partida;
      case 1: return row.cantidad;
      case 2: return row.unidad;
      case 3: return row.detalle;
      case 4: return row.gestion;
    }
    return "";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    ProveedoresData1 row = (ProveedoresData1)lista.get(nRow);
    System.out.println("aqui---"+value+" asdnoasdn++"+nRow+" asdnoasdn++"+nCol);
    String svalue = value.toString();
    switch (nCol) {
      case 0:
        row.partida = svalue;
        break;      
      case 1:
        row.cantidad = svalue;
        break;       
      case 2:
        row.unidad = svalue;
        break;       
      case 3:
        row.detalle = svalue;
        break;       
      case 4:
        row.gestion = svalue;
        break;       
    }
  }
    public void insert(int row) {
    System.out.println("jjjjk");    
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    System.out.println("jjjjk");
    lista.add(row,new ProveedoresData1());
    
  }
    public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
