/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.BaseDatos;

import com.fuensalida.beans.SesionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;


public class GestionFuncionesBD {
 /**
  *  select a.idActividad, Descripcion, Compania, idSesion, Fecha, Hora, Precio
 *   from actividades as a, sesiones as b
 *   where a.idActividad=b.idActividad 
 *	and fecha>DATE("2017-03-04")
 * 
  * @param fecha
  * @return 
  */
    public static ArrayList<SesionBean> getSesiones(String ano, String mes){
        ArrayList<SesionBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            SesionBean sesion;
            PreparedStatement consulta = conexion.prepareStatement(
                    "select a.idActividad, Descripcion, Compania, idSesion, Fecha, Hora, Precio " +
                    "from actividades as a, sesiones as b " +
                        "where a.idActividad=b.idActividad " +
                            "and YEAR(Fecha)=? and MONTH(Fecha)=?");
            consulta.setString(1, ""+ano);
            consulta.setString(2, ""+mes);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                sesion=new SesionBean();
                sesion.setIdActividad(resultado.getInt(1));
                sesion.setDescripcion(resultado.getString(2));
                
                sesion.setCompania(resultado.getString(3));
                sesion.setIdSesion(resultado.getInt(4));
                sesion.setFecha(com.fuensalida.utils.FechasUtils.fecha(resultado.getString(5)));
                sesion.setHora(resultado.getString(6));
                sesion.setPrecio(resultado.getInt(7));
                
                result.add(sesion);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //System.out.println("Saliendo de la base de datos");
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    /**
     * Devuelve el año mayor o menor de las sesiones dadas de alta
     * @param max Si true devuelve el mayor año de las sesiones, si false devuelve el menor año de las sesiones
     * @return 
     */
    public static String getMaxMinAnoSesiones(boolean max){
        Connection conexion = null;
        String auxResult="";
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta;
            if(max){
                consulta = conexion.prepareStatement("select MAX(Fecha) from sesiones");
            }else{
                consulta = conexion.prepareStatement("select MIN(Fecha) from sesiones");
            }
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            auxResult=resultado.getString(1);
            //System.out.println("Año: "+auxResult);
            auxResult=auxResult.substring(0, auxResult.indexOf("-"));
            //result=Integer.parseInt(auxResult);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //System.out.println("Saliendo de la base de datos");
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return auxResult;
    }
    
    /**
     * Carga la sesion con idActividad e idSesion iguales a los pasados por parámetros
     * @param idActividad
     * @param idSesion
     * @return Devuelve el Bean de sesion cargado con todos los datos de la sesion
     */
    public static SesionBean getSesion(String idActividad, String idSesion){
        SesionBean result=null;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select a.idActividad, Descripcion, Compania, idSesion, Fecha, Hora, Precio " +
                    "from actividades as a, sesiones as b " +
                        "where a.idActividad=b.idActividad " +
                            "and a.idActividad=? and b.idSesion=?");
            consulta.setString(1, idActividad);
            consulta.setString(2, ""+idSesion);
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            result=new SesionBean();
            result.setIdActividad(resultado.getInt(1));
            result.setDescripcion(resultado.getString(2));

            result.setCompania(resultado.getString(3));
            result.setIdSesion(resultado.getInt(4));
            result.setFecha(com.fuensalida.utils.FechasUtils.fecha(resultado.getString(5)));
            result.setHora(resultado.getString(6));
            result.setPrecio(resultado.getInt(7));
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //System.out.println("Saliendo de la base de datos");
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public static SesionBean getSesionDefault(){
        SesionBean result=null;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select a.idActividad, Descripcion, Compania, idSesion, Fecha, Hora, Precio " +
                    "from actividades as a, sesiones as b " +
                        "where a.idActividad=b.idActividad " +
                            "Limit 1");
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            result=new SesionBean();
            result.setIdActividad(resultado.getInt(1));
            result.setDescripcion(resultado.getString(2));

            result.setCompania(resultado.getString(3));
            result.setIdSesion(resultado.getInt(4));
            result.setFecha(com.fuensalida.utils.FechasUtils.fecha(resultado.getString(5)));
            result.setHora(resultado.getString(6));
            result.setPrecio(resultado.getInt(7));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                //System.out.println("Saliendo de la base de datos");
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionAuditorioBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
