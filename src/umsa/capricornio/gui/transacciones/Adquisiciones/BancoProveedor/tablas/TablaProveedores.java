/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.ProveedoresData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author julian
 */
public class TablaProveedores extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    /*new ColumnData( "cp", 0, JLabel.RIGHT ),
    new ColumnData( "ce", 0, JLabel.RIGHT ),    
    new ColumnData( "web", 0, JLabel.RIGHT ),
    new ColumnData( "gran_act", 0, JLabel.RIGHT ),
    new ColumnData( "razon_nit", 0, JLabel.RIGHT ),
    new ColumnData( "partida", 50, JLabel.CENTER ),
    new ColumnData( "Casa Comercial",280, JLabel.LEFT ),
    new ColumnData( "Servicio",150, JLabel.LEFT ),
    new ColumnData( "Direccion",150, JLabel.LEFT ),
    new ColumnData( "Telefono", 80, JLabel.CENTER ),   
    new ColumnData( "FAX", 80, JLabel.CENTER ),   
    new ColumnData( "NIT", 80, JLabel.CENTER ),       
    new ColumnData( "estado", 80, JLabel.CENTER )*/
    new ColumnData("NIT o CI", 50, JLabel.CENTER),
    new ColumnData("TIPO", 3, JLabel.LEFT),
    new ColumnData("CLASE", 30, JLabel.LEFT),
    new ColumnData("NOMBRE", 200, JLabel.LEFT),
    new ColumnData("NOMBRE COMERCIAL", 280, JLabel.LEFT),
    new ColumnData("TELEFONO", 20, JLabel.LEFT),
    new ColumnData("DIRECCION", 150, JLabel.LEFT)
    //new ColumnData("ESTADO", 3, JLabel.LEFT)
    
  };

  protected ArrayList lista;  
  
  public TablaProveedores() {    
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
    ProveedoresData row = (ProveedoresData)lista.get(nRow);
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
      case 0: return row.cod;
      case 1: return row.tipo;
      case 2: return row.clase;
      case 3: return row.nombre;
      case 4: return row.nc;
      case 5: return row.tel;
      case 6: return row.dir;
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    ProveedoresData row = (ProveedoresData)lista.get(nRow);
    System.out.println("aqui+++"+value+" asdnoasdn++"+nRow+" asdnoasdn++"+nCol);
    String svalue = value.toString();
    switch (nCol) {
      case 0:
        row.cod = svalue;
        break;      
      case 1:
        row.tipo = svalue;
        break;       
      case 2:
        row.clase = svalue;
        break;       
      case 3:
        row.nombre = svalue;
        break;       
      case 4:
        row.nc = svalue;
        break;       
      case 5:
        row.tel = svalue;
        break;       
      case 6:
        row.dir = svalue;
        break;       
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new ProveedoresData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}
