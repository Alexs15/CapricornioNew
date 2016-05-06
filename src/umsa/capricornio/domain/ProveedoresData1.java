/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.domain;

/**
 *
 * @author alex
 */
public class ProveedoresData1 {
    public String  partida;
    public String  cantidad;
    public String  unidad;
    public String  detalle;
    public String  gestion;

    public ProveedoresData1(String partida, String cantidad, String unidad, String detalle, String gestion) {
        this.partida = partida;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.detalle = detalle;
        this.gestion = gestion;
    }

    public ProveedoresData1() {
        this.partida = "";
        this.cantidad = "";
        this.unidad = "";
        this.detalle = "";
        this.gestion = "";
    }
    
}
