/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.beans;

import com.fuensalida.BaseDatos.GestionAuditorioBD;

/**
 *
 * @author vPalomo
 */
public class ButacaSesion {
    private int idButaca;
    private int idActividad;
    private int idSesion;
    private int idEstado;
    private String motivo;
    private String fechaMotivo;

    /**
     * Crea una butaca con el id que se le pasa y con el estado 1 (libre), el resto vacío
     * @param id 
     */
    public ButacaSesion(int id){
        idButaca=id;
        idEstado=1;
    }
    public ButacaSesion(){}
    
    public String toString(){
        return "Butaca: "+getIdButaca()+" Estado:"+getIdEstado();
    }
    
    public int getIdButaca() {
        return idButaca;
    }
    
    public String getNombreButaca(){
        return GestionAuditorioBD.getNumButaca(this.idButaca);
    }
    public String getFilaButaca(){
        return GestionAuditorioBD.getFilaButaca(idButaca);
    }
    public String getNumAsientoButaca(){
        return GestionAuditorioBD.getNumAsientoButaca(idButaca);
    }

    public void setIdButaca(int idButaca) {
        this.idButaca = idButaca;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFechaMotivo() {
        return fechaMotivo;
    }

    public void setFechaMotivo(String fechaMotivo) {
        this.fechaMotivo = fechaMotivo;
    }
    
    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
    
}
