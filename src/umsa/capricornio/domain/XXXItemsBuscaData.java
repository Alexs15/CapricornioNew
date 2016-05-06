/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class XXXItemsBuscaData { 
    
    
    public String cod_item;
    public String unidad_medida;
    public String tipo_item;
    public String articulo;

    public XXXItemsBuscaData(String cod_item, String unidad_medida,String tipo_item,String articulo) {
        this.cod_item=cod_item;        
        this.unidad_medida=unidad_medida;        
        this.tipo_item=tipo_item;
        this.articulo=articulo;        
    }
    
    public XXXItemsBuscaData() {
        cod_item="";
        unidad_medida="";
        tipo_item="";
        articulo="";        
    }
}