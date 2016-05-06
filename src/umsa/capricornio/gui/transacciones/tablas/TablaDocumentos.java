/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.gui.transacciones.tablas;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.xml.rpc.ServiceException;
import umsa.capricornio.domain.DocumentosData;
import umsa.capricornio.gui.ConnectADQUI.AdquiWSServiceLocator;
import umsa.capricornio.gui.ConnectADQUI.AdquiWS_PortType;
import umsa.capricornio.gui.transacciones.FrmItems;
import umsa.capricornio.utilitarios.herramientas.ColumnData;

/**
 *
 * @author Leo
 */
public class TablaDocumentos extends AbstractTableModel 
{
  static final public ColumnData m_columns[] = {
    new ColumnData( "cod_docs", 0, JLabel.RIGHT ),
    new ColumnData( "cod_transaccion", 10, JLabel.RIGHT ),
    new ColumnData( "Nombre de Documento", 10, JLabel.RIGHT ),
    new ColumnData( "ubicacion",0, JLabel.CENTER ),
    new ColumnData( "descripcion",100, JLabel.CENTER )      
  };

  protected ArrayList lista;
  
    
  public TablaDocumentos() {    
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
    DocumentosData row = (DocumentosData)lista.get(nRow);
    switch (nCol) {
      case 0: return row.cod_docs;
      case 1: return row.cod_transaccion;
      case 2: return row.terminos_ref;
      case 3: return row.ubicacion;
      case 4: return row.descripcion;    
    }
    return "";          
  }             
  
    @Override
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;    
    DocumentosData row = (DocumentosData)lista.get(nRow);
    String svalue = value.toString();
    
    switch (nCol) {
      case 0:
        row.cod_docs = svalue;
        break;      
      case 1:
        row.cod_transaccion = svalue;
        break;      
      case 2:
        row.terminos_ref = svalue;
        break;       
      case 3:
        row.ubicacion = svalue;
        break;
      case 4:
        row.descripcion = svalue;
        break;    
  
    }
  }
  
  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > lista.size())
      row = lista.size();    
    lista.add(row,new DocumentosData());
  }

  public boolean delete(int row) {
    if (row < 0 || row >= lista.size())
      return false;
    lista.remove(row);
      return true;
  }  
}