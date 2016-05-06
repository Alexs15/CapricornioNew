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
public class Pedido implements Serializable {
    private int  cod_trans_detalle;
    private String  articulo;
    private int fila;  
    private String cod_estado;

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
     * @return the cod_trans_detalle
     */
    public int getCod_trans_detalle() {
        return cod_trans_detalle;
    }

    /**
     * @param cod_trans_detalle the cod_trans_detalle to set
     */
    public void setCod_trans_detalle(int cod_trans_detalle) {
        this.cod_trans_detalle = cod_trans_detalle;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the cod_estado
     */
    public String getCod_estado() {
        return cod_estado;
    }

    /**
     * @param cod_estado the cod_estado to set
     */
    public void setCod_estado(String cod_estado) {
        this.cod_estado = cod_estado;
    }
      
}
