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
public class Partida {
    public String partida;
    public String total;

    public Partida(String partida, String total) {
        this.partida = partida;
        this.total = total;
    }
    public Partida() {
        this.partida = "";
        this.total = "";
    }
    
}
