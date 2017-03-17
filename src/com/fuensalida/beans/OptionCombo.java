/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.beans;

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
        return value+"% - "+texto;
    }

    public OptionCombo(int value, String texto) {
        this.value = value;
        this.texto = texto;
    }

    public OptionCombo() {
    }
    
}
