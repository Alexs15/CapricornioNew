/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.BancoProveedor.tablas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import umsa.capricornio.domain.ProvBuscData;
import umsa.capricornio.domain.ProveedoresData;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author UMSA-JES
 */
public class TablaProponentesBusca extends AbstractTableModel{

   static final public ColumnData m_columns[] = {
    new ColumnData( "Documento", 290, JLabel.RIGHT ),
    new ColumnData( "Tipo", 15, JLabel.RIGHT ),    
    new ColumnData( "Clase_persona", 60, JLabel.CENTER ),
    new ColumnData( "Nombre",290, JLabel.LEFT ),
    new ColumnData( "Nombre Comercial",290, JLabel.LEFT ),
    new ColumnData( "Direccion",0, JLabel.LEFT ),
    new ColumnData( "Telefono",0, JLabel.LEFT ),
    new ColumnData( "Adjudicado",290, JLabel.LEFT )
    /*new ColumnData( "adh nombre",0, JLabel.CENTER ),
    new ColumnData( "adh documento",0, JLabel.CENTER ),
    new ColumnData( "dir lugar",0, JLabel.CENTER ),
    new ColumnData( "dir direccion",0, JLabel.CENTER ),
    new ColumnData( "dir telefono",0, JLabel.CENTER ),
    new ColumnData( "dir email",0, JLabel.CENTER )*/
  };

  protected ArrayList lista;  
  
  public TablaProponentesBusca() {    
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
      //System.out.println("Oky Doky");
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
    ProvBuscData row = (ProvBuscData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.documento;
      case 1: return row.tipo_id;      
      case 2: return row.descripcion;
      case 3: return row.clase_beneficiario;
      case 4: return row.nombre;
      case 5: return row.nombre_comercial;
      case 6: return row.dir_direccion;
      case 7: return row.dir_telefono;
      case 8: return row.estado_adjudicado;
      /*case 6: return row.adh_nombre;
      case 7: return row.adh_documento;
      case 8: return row.dir_lugar;
      case 9: return row.dir_direccion;
      case 10: return row.dir_telefono;
      case 11: return row.dir_email;*/    
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    ProvBuscData row = (ProvBuscData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.documento = svalue;
        break;      
      case 1:
        row.tipo_id = svalue;
        break;       
      case 2:
        row.descripcion = svalue;
        break;       
      case 3:
        row.clase_beneficiario = svalue;
        break;       
      case 4:
        row.nombre = svalue;
        break;
      case 5:
        row.nombre_comercial = svalue;
        break;
      case 6:
        row.dir_direccion = svalue;
        break;
      case 7:
        row.dir_telefono = svalue;
        break;
      case 8:
        row.estado_adjudicado = svalue;
        break;  
      /*case 6:
        row.adh_nombre = svalue;
        break;            
      case 7:
          row.adh_documento = svalue;          
          break;
      case 8:
          row.dir_lugar = svalue;          
          break;
      case 9:
          row.dir_direccion = svalue;          
          break;
      case 10:
          row.dir_telefono = svalue;          
          break;
      case 11:
          row.dir_email = svalue;          
          break;
      */
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new ProvBuscData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }
   
}
