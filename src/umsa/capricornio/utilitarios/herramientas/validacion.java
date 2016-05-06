/*
 * validacion.java
 *
 * Created on 8 de noviembre de 2005, 9:42
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package umsa.capricornio.utilitarios.herramientas;

import java.text.SimpleDateFormat;
import java.util.Date;


public class validacion {
    private static String digitosValidos = "1234567890";
    private static String digCarValidosMoneda = "1234567890.";
    private static String letrasValidas = "abcdeéfghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ,.-";
    private static String letDigValidos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890,.-:;/(*) \r\n";
    
    /*
     * Validacion numerica
     * @param strNumero numero a validar
     */
    public static boolean valNumero(String strNumero, int longMin, int longMax) {
        if(strNumero != null) {
            int longitud = strNumero.length();

            if( longMin<=longitud && longitud<=longMax) {
                for( int i = 0; i < longitud; i++ ) {
                    if(digitosValidos.indexOf(strNumero.charAt(i)) == -1 )
                        return false;
                }
                return true;
            } else return false;
        }
        return false;
    }
    
    /*
     * Validacion numerica de mondeda
     * @param strCantidad numero a validar
     */
    public static boolean valMoneda(String strCantidad) {
        int longitud = strCantidad.length();
        int punto = 0;
        int contDecimales = 0;

        for( int i = 0; i < longitud; i++ ) {
            if(digCarValidosMoneda.indexOf(strCantidad.charAt(i)) == -1 )
                return false;            
            if(strCantidad.charAt(i)=='.') punto++;
            if(punto>1) return false;
        }
        return true;
    }

    /*
     * Validar alfabetica
     * @param strFrase a validar
     */
    public static boolean valAfabetico(String strFrase, int longMin, int longMax) {
        int longitud = strFrase.length();

        if( longMin<=longitud && longitud<=longMax) {
            for( int i = 0; i < longitud; i++ ) {
                if( letrasValidas.indexOf(strFrase.charAt(i)) == -1 ) {
                    return false;
                }
            }
            return true;
        } else return false;
    }
    
    /*
     * Validacion alfanumerico
     * @param strFrase a validar
     */
    public static boolean valAfaNumerico(String strFrase, int longMin, int longMax) {
        int longitud = strFrase.length();

        if( longMin<=longitud && longitud<=longMax) {
            for( int i = 0; i < longitud; i++ ) {
                if( letDigValidos.indexOf(strFrase.charAt(i)) == -1 ) {
                    return false;
                }
            }
            return true;
        } else return false;
    }
    
    public static boolean valDate(String strDate, String strMask) {
        boolean bolRes = true;
        //String strDate = "02-13-1999"; //Fecha incorrecta
        //String strMask = "dd-MM-yyyy"; //mascara que utiliza
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strMask);
            sdf.setLenient(false);
            Date dt2 = sdf.parse(strDate);
            //System.out.println("Fecha ok = " + dt2 + "(" + strDate + ")");
        } catch (java.text.ParseException e) {
            //System.out.println(e.getMessage());
            bolRes = false;
        } catch (IllegalArgumentException e) {
            //System.out.println("Fecha incorrecta");
            bolRes = false;
        }
        return bolRes;
    }
    
    /*public static boolean valTime(String fecha,String mask){
        boolean bolRes = true;
        try{ SimpleDateFormat sdf = new SimpleDateFormat(mask);
             sdf.setLenient(false);
             Date dt2 = sdf.parse(fecha);
        }
        catch (java.text.ParseException e) { bolRes=false;}
        catch (IllegalArgumentException  e) { bolRes = false;}
        return bolRes;
    }*/
}
