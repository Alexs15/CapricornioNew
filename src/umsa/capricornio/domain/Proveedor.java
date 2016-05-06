/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package umsa.capricornio.domain;

import java.io.Serializable;

/**
 *
 * @author julian
 */
public class Proveedor implements Serializable {
    private String cod_proveedor;
    private String casa_comercial;
    private String tipo_cod;
    private String clase;
    private String nombre;
    private String telefono;
    private String direccion;
    private String estado;
    private String adh_nombre;

    public String getAdh_nombre() {
        return adh_nombre;
    }

    public void setAdh_nombre(String adh_nombre) {
        this.adh_nombre = adh_nombre;
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCod_proveedor() {
        return cod_proveedor;
    }

    public void setCod_proveedor(String cod_proveedor) {
        this.cod_proveedor = cod_proveedor;
    }
    public String getTipo_cod() {
        return tipo_cod;
    }

    public void setTipo_cod(String tipo_cod) {
        this.tipo_cod = tipo_cod;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   

    /**
     * @return the casa_comercial
     */
    public String getCasa_comercial() {
        return casa_comercial;
    }

    /**
     * @param casa_comercial the casa_comercial to set
     */
    public void setCasa_comercial(String casa_comercial) {
        this.casa_comercial = casa_comercial;
    }
}
