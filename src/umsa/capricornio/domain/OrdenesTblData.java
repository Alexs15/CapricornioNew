/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class OrdenesTblData { 
    
    public String  cod_trans_detalle;
    public String  cod_estado;
    public String  articulo;
    public String nro_orden;
    
    public OrdenesTblData(String cod_trans_detalle, String cod_estado,String nro_orden,String articulo) {
        this.cod_trans_detalle=cod_trans_detalle;
        this.cod_estado=cod_estado;
        this.nro_orden=nro_orden;
        this.articulo=articulo;        
    }
    
    public OrdenesTblData() {
        cod_trans_detalle="";
        cod_estado="";
        nro_orden="";
        articulo="";        
    }
}