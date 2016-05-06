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
public class ItemsPartidaData {
    public String cod_item;
    public String partida;
    public String item;
    public String detalle;
    public String unidad_medida;

    public ItemsPartidaData(String cod_item,String partida, String item, String detalle, String unidad_medida) {
        this.cod_item = cod_item;
        this.partida = partida;
        this.item = item;
        this.detalle = detalle;
        this.unidad_medida = unidad_medida;
    }

    public ItemsPartidaData() {
        this.cod_item = "";
        this.partida = "";
        this.item = "";
        this.detalle = "";
        this.unidad_medida = "";
    }
    
}
