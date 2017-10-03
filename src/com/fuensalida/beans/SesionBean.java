/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.beans;

import com.fuensalida.BaseDatos.GestionEntradasBD;

/**
 *
 * @author vPalomo
 */
public class SesionBean {
    private int idActividad;
    private String descripcion;
    private String compania;
    private int idSesion;
    private String fecha;
    private String hora;
    private int precio;
    private int precio1;
    private int precio2;
    private int precio3;
    private String descPrecio1;
    private String descPrecio2;
    private String descPrecio3;

    public int getPrecio1() {
        return precio1;
    }

    public void setPrecio1(int precio1) {
        this.precio1 = precio1;
    }

    public int getPrecio2() {
        return precio2;
    }

    public void setPrecio2(int precio2) {
        this.precio2 = precio2;
    }

    public int getPrecio3() {
        return precio3;
    }

    public void setPrecio3(int precio3) {
        this.precio3 = precio3;
    }

    public String getDescPrecio1() {
        return descPrecio1;
    }

    public void setDescPrecio1(String descPrecio1) {
        this.descPrecio1 = descPrecio1;
    }

    public String getDescPrecio2() {
        return descPrecio2;
    }

    public void setDescPrecio2(String descPrecio2) {
        this.descPrecio2 = descPrecio2;
    }

    public String getDescPrecio3() {
        return descPrecio3;
    }

    public void setDescPrecio3(String descPrecio3) {
        this.descPrecio3 = descPrecio3;
    }
    
    public SesionBean() {
    }

    public String toString(){
        return descripcion+" "+fecha+" "+hora+" idActi:"+idActividad+" idSes:"+idSesion+" Precio:"+precio;
    }
    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public SesionBean(int idActividad, String descripcion, String compania, int idSesion, String fecha, String hora, int precio) {
        this.idActividad = idActividad;
        this.descripcion = descripcion;
        this.compania = compania;
        this.idSesion = idSesion;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
    }

    public void cargaPrecios() {
        SesionBean sAux=GestionEntradasBD.getDtos(this);
        precio1=sAux.getPrecio1();
        precio2=sAux.getPrecio2();
        precio3=sAux.getPrecio3();
        descPrecio1=sAux.getDescPrecio1();
        descPrecio2=sAux.getDescPrecio2();
        descPrecio3=sAux.getDescPrecio3();
        //System.out.println(precio1+"\n"+precio2+"\n"+precio3+"\n"+descPrecio1+"\n"+descPrecio2+"\n"+descPrecio3);
    }

    public String getHoraCorta() {
        if(getHora().length()<7) return getHora();
        return getHora().substring(0, hora.lastIndexOf(":"));
    }
    
    
}
