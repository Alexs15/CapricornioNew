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
public class Items implements Serializable {
      private int cod_item;
      private String unidad_medida;
      private String tipo_item;
      private String articulo;

    /**
     * @return the unidad_medida
     */
    public String getUnidad_medida() {
        return unidad_medida;
    }

    /**
     * @param unidad_medida the unidad_medida to set
     */
    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    /**
     * @return the tipo_item
     */
    public String getTipo_item() {
        return tipo_item;
    }

    /**
     * @param tipo_item the tipo_item to set
     */
    public void setTipo_item(String tipo_item) {
        this.tipo_item = tipo_item;
    }

    /**
     * @return the articulo
     */
    public String getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    /**
     * @return the cod_item
     */
    public int getCod_item() {
        return cod_item;
    }

    /**
     * @param cod_item the cod_item to set
     */
    public void setCod_item(int cod_item) {
        this.cod_item = cod_item;
    }
      
}
