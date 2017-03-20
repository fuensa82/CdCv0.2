/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.BaseDatos;

import com.fuensalida.NewJFrame;
import com.fuensalida.beans.ButacaSesion;
import com.fuensalida.beans.DescuentosBean;
import com.fuensalida.beans.SesionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author vPalomo
 */
public class GestionEntradasBD {
    public static ArrayList<DescuentosBean> getDtos(){
        ArrayList<DescuentosBean> result=new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement("select idDto, dto, descripcion from descuentos");
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                DescuentosBean d=new DescuentosBean();
                d.setIdDto(resultado.getInt(1));
                d.setDto(resultado.getInt(2));
                d.setDescripcion(resultado.getString(3));
                result.add(d);
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
    
    public static int recaudacionSesion(SesionBean sesion){
        int result=0;
        Connection conexion = null;
        try {
            
            conexion=ConectorBD.getConnection();
            System.out.println("Sesion: "+sesion);
            PreparedStatement consulta = conexion.prepareStatement(
                    "select sum(importeVenta) as recaudacion from tickets " +
                    "where idActividad=? and idSesion=? and isAnulada=false");
            consulta.setString(1, ""+sesion.getIdActividad());
            consulta.setString(2, ""+sesion.getIdSesion());
            //insert.setString(4, ""+butaca.getIdEstado());
            System.out.println("SQL: "+consulta.toString());
            ResultSet resultado = consulta.executeQuery();
            
            conexion.close();
            
            result=resultado.getInt(1);
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
        return result;
    }
    
    public static int ventaButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, int idDto, int dto){
        int result=0;
        Connection conexion = null;
        try {
            
            for (ButacaSesion butaca : listaButacas) {
                conexion=ConectorBD.getConnection();
                System.out.println("Butaca: "+butaca);
                System.out.println("Sesion: "+sesion);
                PreparedStatement insert = conexion.prepareStatement(
                        "INSERT INTO `cdc`.`butacassesion` (`idButaca`, `idActividad`, `idSesion`, `idEstado`) VALUES (?, ?, ?, 2)");
                insert.setString(1, ""+butaca.getIdButaca());
                insert.setString(2, ""+sesion.getIdActividad());
                insert.setString(3, ""+sesion.getIdSesion());
                //insert.setString(4, ""+butaca.getIdEstado());
                System.out.println("SQL: "+insert.toString());
                insert.executeUpdate();
                conexion.close();
                
                conexion=ConectorBD.getConnection();
                System.out.println("Sesion: "+sesion);
                
                insert = conexion.prepareStatement(
                        "INSERT INTO `cdc`.`tickets` (`idButaca`, `idActividad`, `idSesion`, `importeVenta`, `idUsuario`, `motivo`,`idDto`) VALUES (?, ?, ?, ?, ?, ?, ?)");
                insert.setString(1, ""+butaca.getIdButaca());
                insert.setString(2, ""+sesion.getIdActividad());
                insert.setString(3, ""+sesion.getIdSesion());
                int importe=(int)(sesion.getPrecio()*((100.00-(Long.parseLong(""+dto)))/100.00));
                System.out.println("Importe BD: "+importe);
                insert.setInt(4, importe);
                insert.setString(5, "0");
                insert.setString(6, "");
                insert.setString(7, ""+idDto);
                System.out.println("SQL: "+insert.toString());
                insert.executeUpdate();
                conexion.close();
            }
            return 1; //Correcto
            
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
