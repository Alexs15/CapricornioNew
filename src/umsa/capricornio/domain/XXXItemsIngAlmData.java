/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class XXXItemsIngAlmData { 
    
    
    public String  cod_complemento;
    public String  cod_trans_detalle;
    public String  cantidad_pedido;
    public String  unidad_medida;
    public String  tipo_item;
    public String  articulo;        
    public String  precio_unit;
    public String  sub_total;

    public XXXItemsIngAlmData(String cod_complemento, String cod_trans_detalle,String cantidad_pedido,String unidad_medida,String tipo_item,String articulo,String precio_unit,String sub_total) {
        this.cod_complemento=cod_complemento;        
        this.cod_trans_detalle=cod_trans_detalle;        
        this.cantidad_pedido=cantidad_pedido;
        this.unidad_medida=unidad_medida;
        this.tipo_item=tipo_item;
        this.articulo=articulo;
        this.precio_unit=precio_unit;
        this.sub_total=sub_total;
    }
    
    public XXXItemsIngAlmData() {
        cod_complemento="";
        cod_trans_detalle="";
        cantidad_pedido="";
        unidad_medida="";
        tipo_item="";
        articulo="";
        precio_unit="";
        sub_total="";
    }
}