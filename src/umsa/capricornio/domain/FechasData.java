/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class FechasData { 
    
    public String  COD;
    public String  FECHAS;    
        
    public FechasData(String cod_gestion, String gestion) {
        this.COD=cod_gestion;
        this.FECHAS=gestion;        
    }
    
    public FechasData() {
        this.COD="";
        this.FECHAS="";        
    }
}