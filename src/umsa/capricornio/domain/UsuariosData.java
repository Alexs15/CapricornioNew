/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class UsuariosData { 
    
    public String  cod_usuario;
    public String  usuario;
    public String  nombre_resumen;    
        
    public UsuariosData(String cod_usuario, String usuario,String nombre_resumen) {
        this.cod_usuario=cod_usuario;
        this.usuario=usuario;
        this.nombre_resumen=nombre_resumen;        
    }
    
    public UsuariosData() {
        this.cod_usuario="";
        this.nombre_resumen="";
        this.usuario="";
    }
}