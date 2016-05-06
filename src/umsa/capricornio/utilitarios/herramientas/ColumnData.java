/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.utilitarios.herramientas;

/**
 *
 * @author julian
 */
public class ColumnData
{
  public String  m_title;
  public int     m_width;
  public int     m_alignment;

  public ColumnData(String title, int width, int alignment) {
    m_title = title;
    m_width = width;
    m_alignment = alignment;
  }
}