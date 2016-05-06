/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class RelacionUsrWorkData
{
  public String m_cod1;
  public String m_detalle1;
  public String m_cod2;
  public String m_detalle2;
  public String m_cod3;
  public String m_detalle3;
  
  
  public RelacionUsrWorkData(String cod1, String detalle1, String cod2,String detalle2,String cod3,String detalle3) {
    m_cod1 =  cod1;
    m_detalle1=detalle1;
    m_cod2=cod2;
    m_detalle2=detalle2;
    m_cod3=cod3;
    m_detalle3=detalle3;
  }
  
   public RelacionUsrWorkData() {
    m_cod1 =  "";
    m_detalle1="";
    m_cod2="";
    m_detalle2="";
    m_cod3="";
    m_detalle3="";
  }
   
}