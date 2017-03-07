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
public class SesionBean {
    private int idActividad;
    private String descripcion;
    private String compania;
    private int idSesion;
    private String fecha;
    private String hora;
    private int precio;

    public SesionBean() {
    }

    public String toString(){
        return descripcion+" "+fecha+" "+hora;
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
}
