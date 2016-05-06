/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class AlmacenData { 
    
    public String  cod_almacen;
    public String  almacen;
    public String  facultad;
    public String  cod_fac;
        
    public AlmacenData(String cod_almacen, String almacen,String facultad,String cod_fac) {
        this.cod_almacen=cod_almacen;
        this.almacen=almacen;
        this.facultad=facultad;
        this.cod_fac=cod_fac;       
    }
    
    public AlmacenData() {
        this.cod_almacen="";
        this.almacen="";
        this.facultad="";
        this.cod_fac="";        
    }
}