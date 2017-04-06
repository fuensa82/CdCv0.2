/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.BaseDatos;

import com.fuensalida.NewJFrame;
import com.fuensalida.beans.SesionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author vPalomo
 */
public class GestionInformesBD {
    /**
     * Genera un HashMap con los datos de numero de entradas vendidas(netas y brutas) y devueltas.
     * En el HashMap (keys):
     *      'ventas netas' Numero total de entradas que se han vendido menos las devoluciones
     *      'ventas brutas' Numero total de entradas impresas
     *      'devoluciones' Numero de entradas que se han devuelto.
     * 
     * @param sesion
     * @return 
     */
    public static HashMap getCifrasSesion(SesionBean sesion) {
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            String stmt = "select total1-total2 as 'ventas netas', total1 as 'ventas brutas', total2 as devoluciones " +
                            "from (" +
                            "	select count(*) as total1 " +
                            "		from tickets " +
                            "		where idActividad=? " +
                            "			and idSesion=? " +
                            "			and importeVenta>=0)as a , " +
                            "	(select count(*) as total2 " +
                            "		from tickets " +
                            "		where idActividad=? " +
                            "			and idSesion=? " +
                            "			and importeVenta<0) as b";
            PreparedStatement pstmt=conexion.prepareStatement(stmt);
            pstmt.setInt(1, sesion.getIdActividad());
            pstmt.setInt(2, sesion.getIdSesion());
            pstmt.setInt(3, sesion.getIdActividad());
            pstmt.setInt(4, sesion.getIdSesion());
            
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            HashMap<String, String> result = new HashMap();
            result.put("ventas netas", resultado.getString("ventas netas"));
            result.put("ventas brutas", resultado.getString("ventas brutas"));
            result.put("devoluciones", resultado.getString("devoluciones"));
            return result;
            
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
        return null;
    }
    
    /**
     * Datos de recaudacion de la sesion agrupado en tipos
     * Genera un ArrayList donde cada uno de los item son HashMap con las siguientes keys
     *      "N ENtradas"
     *      "motivo"
     *      "Importe Entrada"
     *      "Total"
     * 
     * @param sesion
     * @return 
     */
    public static ArrayList<HashMap> getImportesSesion(SesionBean sesion) {
        ArrayList<HashMap> result=new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            String stmt = "select count(*) as 'N Entradas', motivo, importeVenta as 'Importe Entrada', sum(importeVenta) as Total " +
                            "from tickets " +
                            "where idActividad=? " +
                            "	and idSesion=? " +
                            "	and importeVenta<0 " +
                            "group by motivo, importeVenta";
            PreparedStatement pstmt=conexion.prepareStatement(stmt);
            pstmt.setInt(1, sesion.getIdActividad());
            pstmt.setInt(2, sesion.getIdSesion());
            
            ResultSet resultado = pstmt.executeQuery();
            while(resultado.next()){
                HashMap<String, String> item = new HashMap();
                item.put("N Entradas", resultado.getString("N Entradas"));
                item.put("motivo", resultado.getString("motivo"));
                item.put("Importe Entrada", resultado.getString("Importe Entrada"));
                item.put("Total", resultado.getString("Total"));
                result.add(item);
            }
            return result;
            
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
        return null;
    }
}
