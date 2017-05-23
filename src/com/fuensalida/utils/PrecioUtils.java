/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.utils;

/**
 *
 * @author vPalomo
 */
public class PrecioUtils {
    public static String getPrecioEuros(String texto){
        if(texto.length()==1){
            texto="00"+texto;
        }else if(texto.length()==2){
            texto="0"+texto;
        }
        return texto.substring(0, texto.length()-2)+","+texto.substring(texto.length()-2)+" €";
    }
    public static String getPrecioEuros(int precio){
        //System.out.println("Precio: "+getPrecioEuros(""+precio));
        return getPrecioEuros(""+precio);
    }
    
    
}
