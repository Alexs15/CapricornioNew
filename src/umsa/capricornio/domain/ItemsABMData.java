/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class ItemsABMData { 
    
    public String  cod_item;
    public String  partida;
    public String  articulo;
    public String  unidad_medida;
    public String  gestion;
    public String  cod_tipo_item;
        
    public ItemsABMData(String cod_item, String partida,String articulo,String unidad_medida,String gestion,String cod_tipo_item) {
        this.cod_item=cod_item;
        this.partida=partida;
        this.articulo=articulo;       
        this.unidad_medida=unidad_medida;
        this.gestion=gestion;
        this.cod_tipo_item=cod_tipo_item;       
            
    }
    
    public ItemsABMData() {
        this.cod_item="";
        this.partida="";
        this.articulo="";
        this.unidad_medida="";
        this.gestion="";       
        this.cod_tipo_item="";       
               
    }
}