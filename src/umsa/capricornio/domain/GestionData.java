/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class GestionData { 
    
    public String  cod_gestion;
    public String  gestion;    
        
    public GestionData(String cod_gestion, String gestion) {
        this.cod_gestion=cod_gestion;
        this.gestion=gestion;        
    }
    
    public GestionData() {
        this.cod_gestion="";
        this.gestion="";        
    }
}