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
public class Ppto implements Serializable {
      private String certif_ppto;
      private String hoja_ruta;
      private double monto;
      private String fuente;

    /**
     * @return the certif_ppto
     */
    public String getCertif_ppto() {
        return certif_ppto;
    }

    /**
     * @param certif_ppto the certif_ppto to set
     */
    public void setCertif_ppto(String certif_ppto) {
        this.certif_ppto = certif_ppto;
    }

    /**
     * @return the hoja_ruta
     */
    public String getHoja_ruta() {
        return hoja_ruta;
    }

    /**
     * @param hoja_ruta the hoja_ruta to set
     */
    public void setHoja_ruta(String hoja_ruta) {
        this.hoja_ruta = hoja_ruta;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * @return the fuente
     */
    public String getFuente() {
        return fuente;
    }

    /**
     * @param fuente the fuente to set
     */
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
      
}
