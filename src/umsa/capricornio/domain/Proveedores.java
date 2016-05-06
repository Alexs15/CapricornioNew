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
public class Proveedores implements Serializable{
private String web;
    private String gran_actividad_nit;    
    private String razon_social_nit;    
    private String partida;    
    private String casa_comercial;    
    private String servicio;    
    private String direccion;    
    private String telefono;    
    private String fax;    
    private String nit;    
    private String estado;  
    
    public String getCasa_comercial() {
        return casa_comercial;
    }

    public void setCasa_comercial(String casa_comercial) {
        this.casa_comercial = casa_comercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGran_actividad_nit() {
        return gran_actividad_nit;
    }

    public void setGran_actividad_nit(String gran_actividad_nit) {
        this.gran_actividad_nit = gran_actividad_nit;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getRazon_social_nit() {
        return razon_social_nit;
    }

    public void setRazon_social_nit(String razon_social_nit) {
        this.razon_social_nit = razon_social_nit;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
      
}
