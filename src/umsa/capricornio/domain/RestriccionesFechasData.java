/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author UMSA-JES
 */
public class RestriccionesFechasData {
    public String fecha_inicio;
    public String fecha_final;
    public String nro_solicitud_compra;
    public String nro_resolucion_inicio;
    public String estado;
    public String dias_restantes;
    public String m_actividad;

    public RestriccionesFechasData() {
        this.fecha_inicio = "";
        this.fecha_final = "";
        this.nro_solicitud_compra = "";
        this.nro_resolucion_inicio = "";
        this.estado = "";
        this.dias_restantes = "";
        this.m_actividad="";
    }

    public RestriccionesFechasData(String fecha_inicio, String fecha_final, String nro_solicitud_compra, String nro_resolucion_inicio, String estado, String dias_restantes,String m_actividad) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.nro_solicitud_compra = nro_solicitud_compra;
        this.nro_resolucion_inicio = nro_resolucion_inicio;
        this.estado = estado;
        this.dias_restantes = dias_restantes;
        this.m_actividad=m_actividad;
    }
    
}
