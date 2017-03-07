/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.BaseDatos;

import com.fuensalida.NewJFrame;
import com.fuensalida.beans.EstadoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author vPalomo
 */
public class GestionEstadosBD {
    static final int LIBRE=1;
    static final int VENDIDA=2;
    static final int RESERVADA=3;
    static final int NO_VENDER=4;
    static final int INVITACION=5;
    static final int ABONO=6;
    static final int OTROS=99;
    static final boolean conBD=false; //Haremos que no busque en la base de datos, es más pesada la carga. En un futuro se puede solucionar con un Singleton
    
    private static EstadoBean getEstadoSinBD(String estado){
        if("1".equalsIgnoreCase(estado)){
            return new EstadoBean(1, "Libre", "00FF00");
        }else if("2".equalsIgnoreCase(estado)){
            return new EstadoBean(2, "Vendida", "FF0000");
        }else if("3".equalsIgnoreCase(estado)){
            return new EstadoBean(3, "Reservada", "FF9900");
        }else if("4".equalsIgnoreCase(estado)){
            return new EstadoBean(4, "No vender", "000000");
        }else if("5".equalsIgnoreCase(estado)){
            return new EstadoBean(5, "Invitación", "3B83BD");
        }else if("6".equalsIgnoreCase(estado)){
            return new EstadoBean(6, "Abono", "EA899A");
        }else{
            return new EstadoBean();
        }
    }
    public static EstadoBean getEstado(int estado){
        if(conBD){
            return getEstadoConBD(""+estado);
        }else{
            return getEstadoSinBD(""+estado);
        }
    }
    
    private static EstadoBean getEstadoConBD(String estado){
        EstadoBean result = null;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement("select idEstado, Descripcion, Color from estados where idEstado=?");
            consulta.setString(1, estado);
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                result=new EstadoBean();
                result.setIdEstado(Integer.parseInt(resultado.getString(1)));
                result.setDescripcion(resultado.getString(2));
                result.setColor(resultado.getString(3));
            }
            System.out.println(result);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
