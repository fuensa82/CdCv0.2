/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.beans;

import com.fuensalida.utils.PrecioUtils;

/**
 *
 * @author vPalomo
 */
public class OptionCombo {
    private int value;
    private String texto;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        //String valueTest=""+value;
        //valueTest=valueTest.substring(0, valueTest.length()-2)+","+valueTest.substring(valueTest.length()-2);
        return PrecioUtils.getPrecioEuros(value)+" - "+texto;
    }

    public OptionCombo(int value, String texto) {
        this.value = value;
        this.texto = texto;
    }

    public OptionCombo() {
    }
    
}
