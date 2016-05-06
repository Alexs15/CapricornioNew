/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class ProvBuscData { 
    
    public String  documento;
    public String  tipo_id;
    public String  descripcion;
    public String  clase_beneficiario;
    public String  nombre;
    public String  nombre_comercial;
    public String  adh_nombre;
    public String  adh_documento;
    public String  dir_lugar;
    public String  dir_direccion;
    public String  dir_telefono;
    public String  dir_email;
    public String  estado_adjudicado;
    
    
    public ProvBuscData(String documento, String tipo_id,String descripcion,String clase_beneficiario,String nombre,String nombre_comercial,String adh_nombre,String adh_documento,String dir_lugar, String dir_direccion,String dir_telefono,String dir_email,String estado_adjudicado) {
        this.documento=documento;
        this.tipo_id=tipo_id;
        this.descripcion=descripcion;
        this.clase_beneficiario=clase_beneficiario;
        this.nombre=nombre;
        this.nombre_comercial=nombre_comercial;
        this.adh_nombre=adh_nombre;
        this.adh_documento=adh_documento;
        this.dir_lugar=dir_lugar;
        this.dir_direccion=dir_direccion;
        this.dir_telefono=dir_telefono;
        this.dir_email=dir_email;
        this.estado_adjudicado=estado_adjudicado;
    }
    
    public ProvBuscData() {
        this.documento="";
        this.tipo_id="";
        this.descripcion="";
        this.clase_beneficiario="";
        this.nombre="";
        this.nombre_comercial="";
        this.adh_nombre="";
        this.adh_documento="";
        this.dir_lugar="";
        this.dir_direccion="";
        this.dir_telefono="";
        this.dir_email="";
        this.estado_adjudicado="";
         }
}