/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package umsa.capricornio.gui.transacciones.Adquisiciones.tramites.tablas;

/**
 *
 * @author UMSA-JES
 */
public class Item {
    private String cod_item;

    public Item(String cod_item) {
        super();
        this.cod_item = cod_item;
    }

    public String getCod_item() {
        return cod_item;
    }

    public void setCod_item(String cod_item) {
        this.cod_item = cod_item;
    }
}
