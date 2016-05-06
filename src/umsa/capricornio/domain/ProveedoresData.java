/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author julian
 */
public class ProveedoresData { 
    
    public String  cod;
    public String  tipo;
    public String  clase;
    public String  nombre;
    public String  nc;
    public String  tel;
    public String  dir;
    /*public String  casa_comercial;
    public String  servicio;
    public String  direccion;
    public String  telefono;
    public String  nit;
    public String  estado;*/
    
    
    public ProveedoresData(String cod_proveedor, String cod_estado,String fax,String web,String gran_act,String razon_nit,String partida,String casa_comercial) {
        this.cod=cod_proveedor;
        this.tipo=cod_estado;
        this.clase=fax;
        this.nombre=web;
        this.nc=gran_act;
        this.tel=razon_nit;
        this.dir=casa_comercial;
        /*this.servicio=servicio;
        this.direccion=direccion;
        this.telefono=telefono;
        this.nit=nit;
        this.estado=estado;*/
    }
    
    public ProveedoresData() {
        this.cod="";
        this.tipo="";
        this.clase="";
        this.nombre="";
        this.nc="";
        this.tel="";
        this.dir="";
        /*this.casa_comercial="";
        this.servicio="";
        this.direccion="";
        this.telefono="";
        this.nit="";
        this.estado="";*/
    }
}