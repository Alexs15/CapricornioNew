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
public class Roles implements Serializable{
    private int cod_rol;
    private String rol;

    /**
     * @return the cod_rol
     */
    public int getCod_rol() {
        return cod_rol;
    }

    /**
     * @param cod_rol the cod_rol to set
     */
    public void setCod_rol(int cod_rol) {
        this.cod_rol = cod_rol;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
