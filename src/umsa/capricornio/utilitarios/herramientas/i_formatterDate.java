/*
 * i_formatterDate.java
 *
 * Created on 16 de diciembre de 2005, 12:24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package umsa.capricornio.utilitarios.herramientas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 *
 * @author ianlui
 */
public final class i_formatterDate {

    /**
     * Convierte un Date en su equivalente a String en formato Postgres
     * "yyyy-MM-dd"
     *
     * @param dtDate Fecha a convertir.
     * @return String
     */
    public static String i_toStringPostgres(Date dtDate) {
        SimpleDateFormat sdtForPostgres = new SimpleDateFormat("yyyy-MM-dd");
        return sdtForPostgres.format(dtDate);
    }
    
    /**
     * Convierte un Date en su equivalente a String en formato Standard
     * "dd-MM-yyyy"
     *
     * @param dtDate Fecha a convertir.
     * @return String
     */
    public static String i_toStringStandard(Date dtDate) {
        SimpleDateFormat sdtForStandard = new SimpleDateFormat("dd-MM-yyyy");
        return sdtForStandard.format(dtDate);
    }
    
    /**
     * Compara dos Date para ver cual es mayor o menor
     *
     * @param dtDate1 Fecha 1
     * @param dtDate2 Fecha 2
     * @return int. 0: si las fechas son iguales. -1: si la fecha uno
     * es menor que la fecha 2. y 1 si la fecha uno es mayor a la fecha
     * dos 
     */
    public static int i_compareDate(Date dtDate1, Date dtDate2) {
        SimpleDateFormat sdtForPostgres = new SimpleDateFormat("yyyy-MM-dd");
        String strDate1[] = sdtForPostgres.format(dtDate1).split("-");
        String strDate2[] = sdtForPostgres.format(dtDate2).split("-");
        // comparar a�os
        if(Integer.parseInt(strDate1[0]) < Integer.parseInt(strDate2[0])) {
            return -1;
        } else {
            if(Integer.parseInt(strDate1[0]) < Integer.parseInt(strDate2[0])) {
                return 1;
            }
        }
        // comparar meses
        if(Integer.parseInt(strDate1[1]) < Integer.parseInt(strDate2[1])) {
            return -1;
        } else {
            if(Integer.parseInt(strDate1[1]) < Integer.parseInt(strDate2[1])) {
                return 1;
            }
        }
        // comparar dias
        if(Integer.parseInt(strDate1[2]) < Integer.parseInt(strDate2[2])) {
            return -1;
        } else {
            if(Integer.parseInt(strDate1[2]) < Integer.parseInt(strDate2[2])) {
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * Convertir una fecha String Standard a Postgres
     *
     * @param String fecha en formato Standard "dd-MM-yyyy"
     * @return String fecha en formato Postgres "yyyy-MM-dd"
     */
    public static String i_converterStandardToPostgres(String strDateStandard) {
        String strDate[] = strDateStandard.split("-");
        return strDate[2]+"-"+strDate[1]+"-"+strDate[0];
    }
    
    /**
     * Convertir una fecha String Postgres a Standard
     *
     * @param String fecha en formato Postgres "yyyy-MM-dd"
     * @return String fecha en formato Standard "dd-MM-yyyy"
     */
    public static String i_converterPostgresToStandard(String strDatePostgres) {
        String strDate[] = strDatePostgres.split("-");
        return strDate[2]+"-"+strDate[1]+"-"+strDate[0];
    }
    
    /**
     * Convierte String(dd-mm-yyyy) a Date
     */
    public static Date i_conveterStandardToDate(String strDateStandard) {
        Date dtDate = new Date();
        //El string se llamar�a miString, y viene en formato dd-MM-yyyy
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dtDate = df.parse(strDateStandard);
        }
        catch(ParseException e){System.out.println(e);}
        return dtDate;
    }

    public static Date i_conveterPostgresToDate(String strDateStandard) {
        Date dtDate = new Date();
        //El string se llamar�a miString, y viene en formato yyyy-MM-dd
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dtDate = df.parse(strDateStandard);
        }
        catch(ParseException e){System.out.println(e);}
        return dtDate;
    }
    
    /**
     * Devuelve el a�o de una fecha
     *
     * @param dtDate fecha
     * @return String a�o en formato "yyyy"
     */
    public static String getStrYear(Date dtDate) {
        SimpleDateFormat sdtForStandard = new SimpleDateFormat("dd-MM-yyyy");
        String strDatePostgres = sdtForStandard.format(dtDate);
        String strDate[] = strDatePostgres.split("-");
        
        return strDate[2];
    }
    
    public static String getHora(Date hora){
        SimpleDateFormat formahora = new SimpleDateFormat("HH:mm");
        return formahora.format(hora);
    } 
    
    public static String getFechaHora(Date hoy){
        SimpleDateFormat formahora = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formahora.format(hoy);
    }
    
    public static String getFecha(Date hoy){
        SimpleDateFormat formahora = new SimpleDateFormat("dd-MM-yyyy");
        return formahora.format(hoy);
    }
}
