/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class PedidosTblData { 
    
    public String  cod_trans_detalle;
    public String  cod_estado;
    public String  articulo;
    
    public PedidosTblData(String cod_trans_detalle, String cod_estado, String articulo) {
        this.cod_trans_detalle=cod_trans_detalle;
        this.cod_estado=cod_estado;
        this.articulo=articulo;        
    }
    
    public PedidosTblData() {
        cod_trans_detalle="";
        cod_estado="";
        articulo="";        
    }
}