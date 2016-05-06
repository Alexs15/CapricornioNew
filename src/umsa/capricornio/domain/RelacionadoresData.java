/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class RelacionadoresData
{
  public String m_cod1;
  public String m_detalle1;
  public String m_cod2;
  public String m_detalle2;  
  
  
  public RelacionadoresData(String cod1, String detalle1, String cod2,String detalle2) {
    m_cod1 =  cod1;
    m_detalle1=detalle1;
    m_cod2=cod2;
    m_detalle2=detalle2;
  }
  
   public RelacionadoresData() {
    m_cod1 =  "";
    m_detalle1="";
    m_cod2="";
    m_detalle2="";
  }
   
}