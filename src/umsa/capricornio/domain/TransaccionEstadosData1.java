/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class TransaccionEstadosData1 { 
    
    public String  cod_transaccion;
    public String cod_trans_nro;
    public String  cod_estado;
    public String  cod_tramite;
    public String  nro_tramite;
    public String  tipo_tramite;
    public String  detalle;
    public String  unidad_sol;
    public String  unidad_des;
    public String  estado;
    public String  cuantia;        
    public String  del;        
    public String  hasta;

    public TransaccionEstadosData1(
            String cod_transaccion, String cod_trans_nro,String cod_estado,String cod_tramite
            ,String nro_tramite,String tipo_tramite,String detalle,String unidad_sol
            ,String unidad_des,String estado,String cuantia,String del,String hasta) {
        this.cod_transaccion=cod_transaccion;
        this.cod_trans_nro = cod_trans_nro;
        this.cod_estado=cod_estado;
        this.cod_tramite=cod_tramite;
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
    
    public TransaccionEstadosData1() {
        cod_transaccion="";
        cod_trans_nro = "";
        cod_estado="";
        cod_tramite="";
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