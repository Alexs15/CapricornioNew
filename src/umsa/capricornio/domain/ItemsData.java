/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

//import umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas.Item;
import umsa.capricornio.gui.transacciones.tablas.Partida;
import umsa.capricornio.gui.transacciones.tablas.UnidadMedida;

/**
 *
 * @author julian
 */
public class ItemsData { 
    
    
    public String  cod_complemento;
    public String  cod_estado;
    public String  cod_trans_detalle;
    public String  cantidad_pedido;
    //public String  unidad_medida;
    public UnidadMedida  unidad_medida;
    public String  item;
    //public String item;
//    public Item  tipo_item;
    public String  articulo;        
    public String  articulo_act;
    public String  articulo_alm;
    public String  dbc;
    public String  precio_unit;
    public String  sub_total;
    //public String partida;
    public Partida partida;
    public String cod_item;

    public ItemsData(String cod_complemento, String cod_estado,String cod_trans_detalle,String cantidad_pedido,UnidadMedida unidad_medida,String item,Partida partida,String articulo,String articulo_act,String articulo_alm,String dbc,String precio_unit,String sub_total,String cod_item) {
        this.cod_complemento=cod_complemento;
        this.cod_estado=cod_estado;
        this.cod_trans_detalle=cod_trans_detalle;
        this.cantidad_pedido=cantidad_pedido;
        this.unidad_medida=unidad_medida;
        this.item=item;
        this.partida = partida;
        this.articulo=articulo;
        this.articulo_act=articulo_act;
        this.articulo_alm=articulo_alm;
        this.dbc=dbc;
        this.precio_unit=precio_unit;
        this.sub_total=sub_total;
        this.cod_item = cod_item;
    }
    
    public ItemsData() {
        cod_complemento="";
        cod_estado="";
        cod_trans_detalle="";
        cantidad_pedido="";
        //unidad_medida="";
        unidad_medida=null;
        item = "";
//        tipo_item=null;
        //partida="";
        partida=null;
        articulo="";
        articulo_act="";
        articulo_alm="";
        dbc="";
        precio_unit="";
        sub_total="";
        cod_item="";
    }
}