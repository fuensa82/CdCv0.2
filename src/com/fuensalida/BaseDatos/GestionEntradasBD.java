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
            //System.out.println(result);
            
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
    
    public static int getRecaudacionSesion(SesionBean sesion){
        int result=0;
        Connection conexion = null;
        try {
            
            conexion=ConectorBD.getConnection();
            //System.out.println("Sesion: "+sesion);
            PreparedStatement consulta = conexion.prepareStatement(
                    "select sum(importeVenta) from tickets " +
                    "where idActividad=? and idSesion=? and isAnulada=false");
            consulta.setString(1, ""+sesion.getIdActividad());
            consulta.setString(2, ""+sesion.getIdSesion());
            //System.out.println("SQL: "+consulta.toString());
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            result=resultado.getInt(1);
            conexion.close();
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
    /**
     * Devuelve 1 si todo ha ido bien, -1 si no se ha podido dar el alta porque la butaca está ocupada.
     * @param listaButacas
     * @param sesion
     * @param idDto
     * @param dto
     * @return 
     */
    public static int ventaButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, int idDto, int dto){
        int result=0;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`butacassesion` (`idButaca`, `idActividad`, `idSesion`, `idEstado`) VALUES (?, ?, ?, 2)");            
            PreparedStatement update = conexion.prepareStatement(
                "UPDATE `cdc`.`butacassesion` SET `idEstado`=? WHERE  `idButaca`=? AND `idActividad`=? AND `idSesion`=?");
            PreparedStatement insert2 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`tickets` (`idButaca`, `idActividad`, `idSesion`, `importeVenta`, `idUsuario`, `motivo`,`idDto`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (ButacaSesion butaca : listaButacas) {
                
                int estadoButaca=existeEntrada(butaca, sesion);
                switch (estadoButaca) {
                    case 0:
                        //Si la butaca está libre (inexistente) insertamos
                        insert1.setString(1, ""+butaca.getIdButaca());
                        insert1.setString(2, ""+sesion.getIdActividad());
                        insert1.setString(3, ""+sesion.getIdSesion());
                        insert1.executeUpdate();
                        break;
                    case 3:
                    case 1:
                        //Si la butaca esta en estado vendida (3) o estado libre (1) pero la butaca ya existe en la tabla, se hace un update
                        update.setString(1, "2"); //Estado de vendido
                        update.setString(2, ""+butaca.getIdButaca());
                        update.setString(3, ""+sesion.getIdActividad());
                        update.setString(4, ""+sesion.getIdSesion());
                        update.executeUpdate();
                        break;
                    default:
                        return -1;
                }
                insert2.setString(1, ""+butaca.getIdButaca());
                insert2.setString(2, ""+sesion.getIdActividad());
                insert2.setString(3, ""+sesion.getIdSesion());
                int importe=(int)(sesion.getPrecio()*((100.00-(Long.parseLong(""+dto)))/100.00));

                insert2.setInt(4, importe);
                insert2.setString(5, "0");
                insert2.setString(6, "");
                insert2.setString(7, ""+idDto);

                insert2.executeUpdate();
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
    /**
     * Devuelve el estado de una butaca si existe dicha butaca, sino devuelve 0.
     * @param butaca Butaca a buscar
     * @param sesion Sesion a la que pertenece la butaca a consultar
     * @return 
     */
    public static int existeEntrada(ButacaSesion butaca, SesionBean sesion){
        int result=0;
        Connection conexion = null;
        try {
            
            conexion=ConectorBD.getConnection();
            //System.out.println("Sesion: "+sesion);
            PreparedStatement consulta = conexion.prepareStatement(
                    "select count(idEstado),idEstado from butacassesion " +
                    "where idButaca=? " +
                    "and idActividad=? " +
                    "and idSesion=?");
            consulta.setString(1, ""+butaca.getIdButaca());
            consulta.setString(2, ""+sesion.getIdActividad());
            consulta.setString(3, ""+sesion.getIdSesion());
            //System.out.println("SQL: "+consulta.toString());
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            result=resultado.getInt(1);
            if(result>0){
                result=resultado.getInt(2);
            }
            conexion.close();
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
    
    public static ArrayList<String> getMotivos(ArrayList<ButacaSesion> listaButacas, SesionBean sesion){
        ArrayList<String> result=new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            StringBuilder builder = new StringBuilder();
            for( int i = 0 ; i < listaButacas.size(); i++ ) {
                builder.append("?,");
            }
            String stmt = "select idButaca, Motivo from butacassesion where idActividad=? and idSesion=? and idButaca IN (" 
                           + builder.deleteCharAt( builder.length() -1 ).toString()+" ) order by idButaca";
            System.out.println("Select IN: "+stmt);
            PreparedStatement pstmt=conexion.prepareStatement(stmt);
            int index = 3;
            pstmt.setInt(1, sesion.getIdActividad());
            pstmt.setInt(2, sesion.getIdSesion());
            for(ButacaSesion butaca : listaButacas) {
                pstmt.setInt(index++, butaca.getIdButaca());
            }
            System.out.println("SQL: "+pstmt.toString());
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()){
                result.add(resultado.getString(2));
            }
            
            return result; //Correcto
            
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
