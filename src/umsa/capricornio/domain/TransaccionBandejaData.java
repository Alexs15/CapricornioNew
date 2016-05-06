/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class TransaccionBandejaData { 
    
    public String  cod_transaccion;
    public String  cod_w;    
    public String  cod_trans_nro;    
    public String  cod_estado;
    public String  nro_tramite;
    public String  tipo_tramite;
    public String  detalle;
    public String  unidad_sol;
    public String  unidad_des;
    public String  estado;        
    public String  cuantia;        
    public String  del;        
    public String  hasta;        

    public TransaccionBandejaData(String cod_transaccion,String cod_trans_nro,String cod_w,String cod_estado, String nro_tramite,String tipo_tramite,String detalle,String unidad_sol,String unidad_des,String estado,String cuantia,String del,String hasta) {
        this.cod_transaccion=cod_transaccion;
        this.cod_estado=cod_estado;
        this.cod_trans_nro=cod_trans_nro;
        this.cod_w=cod_w;
        this.nro_tramite=nro_tramite;
        this.tipo_tramite=tipo_tramite;
        this.detalle=detalle;
        this.unidad_sol=unidad_sol;
        this.unidad_des=unidad_des;
        this.estado=estado;
        this.cuantia=cuantia;
        this.del=del;
        this.hasta=hasta;
    }
    
    public TransaccionBandejaData() {
        cod_transaccion="";
        cod_estado="";
        cod_trans_nro="";
        cod_w="";
        nro_tramite="";
        tipo_tramite="";
        detalle="";
        unidad_sol="";
        unidad_des="";
        estado="";
        cuantia="";
        del="";
        hasta="";
    }
}