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
    public static ArrayList<SesionBean> getSesiones(String fecha){
        ArrayList<SesionBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            SesionBean sesion;
            PreparedStatement consulta = conexion.prepareStatement("select a.idActividad, Descripcion, Compania, idSesion, Fecha, Hora, Precio " +
                    "from actividades as a, sesiones as b " +
                        "where a.idActividad=b.idActividad " +
                            "and fecha>DATE(?)");
            consulta.setString(1, ""+fecha);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                sesion=new SesionBean();
                sesion.setIdActividad(resultado.getInt(1));
                sesion.setDescripcion(resultado.getString(2));
                
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
}
