/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class SansionesData { 
    
    public String  cod_prov_sansion;
    public String  fec_ini;
    public String  fec_fin;
    public String  obs;
        
    public SansionesData(String cod_prov_sansion, String fec_ini,String fec_fin,String obs) {
        this.cod_prov_sansion=cod_prov_sansion;
        this.fec_ini=fec_ini;
        this.fec_fin=fec_fin;
        this.obs=obs;       
    }
    
    public SansionesData() {
        this.cod_prov_sansion="";
        this.fec_ini="";
        this.fec_fin="";
        this.obs="";        
    }
}