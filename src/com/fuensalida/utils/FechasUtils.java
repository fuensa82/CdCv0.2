/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.utils;

import java.util.Calendar;

public class FechasUtils {

    /**
     * Dada una fecha en formato MySQL (aaaa-mm-dd) genera la fecha estandar española (dd-mm-aaaa)
     * @param fecha
     * @return 
     */
    public static String fecha(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase("")) || (fecha.equals("0000-00-00"))) {
            return "";
        }
        return fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4);
    }

    /**
     * Prepara una fecha para poder ser utilizada en MySQL
     * @param fecha Fecha en formado dd-mm-aaaa
     * @return Devuelve la fecha para poder ser usada en MySQL, es decir, en formado aaaa-mm-dd
     */
    public static String fechaParaMysql(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return null;
        }
        return fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
    }

    /**
     * Combierte una fecha en formado AAAA-MM-DD a entero para poder ser ordenada o comparada 
     * @param fecha Fecha en formado AAAA-MM-DD
     * @return 
     */
    public static int numeroFechaAAAAMMDD(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return -1;
        }
        String aux = fecha.substring(0, 4) + fecha.substring(5, 7) + fecha.substring(8, 10);
        int num = Integer.parseInt(aux);
        return num;
    }

    /**
     * Combierte una fecha en formado DD-MM-AAAA a entero para poder ser ordenada o comparada 
     * @param fecha fecha en formado DD-MM-AAAA
     * @return 
     */
    public static int numeroFechaDDMMAAAA(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return -1;
        }

        String aux = fecha.substring(6, 10) + fecha.substring(3, 5) + fecha.substring(0, 2);
        int num = Integer.parseInt(aux);
        return num;
    }

    /**
     * Devuelve la fecha de hoy en formado dd-mm-aaaa
     * @return 
     */
    public static String fechaActualString() {
        Calendar c = Calendar.getInstance();
        String anio = Integer.toString(c.get(1));

        int m = c.get(2) + 1;
        String mes;
        if (m < 10) {
            mes = "0" + m;
        } else {
            mes = "" + m;
        }

        int d = c.get(5);
        String dia;
        if (d < 10) {
            dia = "0" + d;
        } else {
            dia = "" + d;
        }
        return dia + "-" + mes + "-" + anio;
    }
}