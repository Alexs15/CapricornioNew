/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class UnidadEjecutoraData { 
    
    public String  cod_apert;
    public String  detalle;
    public String  apertura;
    public String  almacen;
    public String  da;
        
    public UnidadEjecutoraData(String cod_apert, String detalle,String apertura,String almacen) {
        this.cod_apert=cod_apert;
        this.detalle=detalle;
        this.apertura=apertura;
        this.almacen=almacen;
        this.almacen=da;
    }
    
    public UnidadEjecutoraData() {
        this.cod_apert="";
        this.detalle="";
        this.apertura="";
        this.almacen="";
        this.da="";
    }
}