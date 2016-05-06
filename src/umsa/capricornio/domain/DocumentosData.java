/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.domain;

/**
 *
 * @author Leo
 */
public class DocumentosData { 
    
    
    public String  cod_docs;
    public String  cod_transaccion;
    public String  terminos_ref;
    public String  ubicacion;
    public String descripcion;
    
    public DocumentosData(String cod_docs, String cod_transaccion,String terminos_ref,String ubicacion,String descripcion) {
        this.cod_docs=cod_docs;
        this.cod_transaccion=cod_transaccion;
        this.terminos_ref=terminos_ref;
        this.ubicacion=ubicacion;
        this.descripcion=descripcion;
        
    }
    
    public DocumentosData() {
        cod_docs="";
        cod_transaccion="";
        terminos_ref="";
        ubicacion="";
        descripcion="";
    }
}
