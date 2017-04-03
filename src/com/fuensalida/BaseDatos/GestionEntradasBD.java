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
    public static SesionBean getDtos(SesionBean sesion){
        SesionBean result=new SesionBean();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                        "select precio1, descPrecio1,precio2, descPrecio2,precio3, descPrecio3 " +
                        "from sesiones " +
                        "where idActividad=? " +
                        "	and idSesion=?");
            
            consulta.setInt(1, sesion.getIdActividad());
            consulta.setInt(2, sesion.getIdSesion());
            ResultSet resultado = consulta.executeQuery();
            resultado.next();
            
            result.setDescPrecio1(resultado.getString("descPrecio1"));
            result.setDescPrecio2(resultado.getString("descPrecio2"));
            result.setDescPrecio3(resultado.getString("descPrecio3"));
            result.setPrecio1(resultado.getInt("precio1"));
            result.setPrecio2(resultado.getInt("precio2"));
            result.setPrecio3(resultado.getInt("precio3"));
            
            
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
    public static int ventaButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, int precioDto){
        int result=0;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`butacassesion` (`idButaca`, `idActividad`, `idSesion`, `idEstado`) VALUES (?, ?, ?, 2)");            
            PreparedStatement update = conexion.prepareStatement(
                "UPDATE `cdc`.`butacassesion` SET `idEstado`=? WHERE  `idButaca`=? AND `idActividad`=? AND `idSesion`=?");
            PreparedStatement insert2 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`tickets` (`idButaca`, `idActividad`, `idSesion`, `importeVenta`, `idUsuario`, `motivo`) VALUES (?, ?, ?, ?, ?, ?)");
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
                insert2.setInt(4, precioDto);
                insert2.setString(5, "0");
                insert2.setString(6, "");

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
    
    public static int reservaButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, String motivo){
        return reservaInvitacionButacas(listaButacas, sesion, motivo, true);
    }
    
    public static int invitacionButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, String motivo){
        return reservaInvitacionButacas(listaButacas, sesion, motivo, false);
    }
    /**
     * 
     * @param listaButacas
     * @param sesion
     * @param motivo
     * @param isReserva Si es true es que se va a hacer una reserva, sino es una invitacion
     * @return 
     */
    public static int reservaInvitacionButacas(ArrayList<ButacaSesion> listaButacas, SesionBean sesion, String motivo, boolean isReserva){
        int estado = isReserva?3:5;
        int result=0;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`butacassesion` (`idButaca`, `idActividad`, `idSesion`, `idEstado`,`motivo`) VALUES (?, ?, ?, ?, ?)");            
            PreparedStatement update = conexion.prepareStatement(
                "UPDATE `cdc`.`butacassesion` SET `idEstado`=?,`motivo`=?  WHERE  `idButaca`=? AND `idActividad`=? AND `idSesion`=?");
            for (ButacaSesion butaca : listaButacas) {
                
                int estadoButaca=existeEntrada(butaca, sesion);
                switch (estadoButaca) {
                    case 0:
                        //Si la butaca está libre (inexistente) insertamos
                        insert1.setInt(1, butaca.getIdButaca());
                        insert1.setInt(2, sesion.getIdActividad());
                        insert1.setInt(3, sesion.getIdSesion());
                        insert1.setInt(4, estado);
                        insert1.setString(5, motivo);
                        insert1.executeUpdate();
                        break;
                    case 1:
                        //Si la butaca esta en estado vendida (3) o estado libre (1) pero la butaca ya existe en la tabla, se hace un update
                        update.setInt(1, estado);
                        update.setString(2, motivo);
                        update.setInt(3, butaca.getIdButaca());
                        update.setInt(4, sesion.getIdActividad());
                        update.setInt(5, sesion.getIdSesion());
                        update.executeUpdate();
                        break;
                    default:
                        return -1;
                }
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
            for (ButacaSesion listaButaca : listaButacas) {
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
                result.add(GestionAuditorioBD.getNumButaca(resultado.getInt(1))+": "+resultado.getString(2));
            }
            System.out.println("Butacas: "+listaButacas.size());
            System.out.println("result: "+result);
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
    
    public static String getMotivo(int idButaca, SesionBean sesion){
        String result="";
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            StringBuilder builder = new StringBuilder();
            
            String stmt = "select idButaca, Motivo from butacassesion where idActividad=? and idSesion=? and idButaca=?";
            System.out.println("Select IN: "+stmt);
            PreparedStatement pstmt=conexion.prepareStatement(stmt);
            pstmt.setInt(1, sesion.getIdActividad());
            pstmt.setInt(2, sesion.getIdSesion());
            pstmt.setInt(3, idButaca);
            
            System.out.println("SQL: "+pstmt.toString());
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            return resultado.getString(2); //Correcto
            
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
     * devuelve el precio al que se vendió una butaca. Si el importe es menor que 0 es un error.
     * @param butaca
     * @param sesion
     * @return 
     */
    public static int getImporteTicket(ButacaSesion butaca, SesionBean sesion) {
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            String stmt = "select importeVenta " +
                "from tickets " +
                "where idButaca=?" +
                "	and idActividad=?" +
                "	and idSesion=?";
            PreparedStatement pstmt=conexion.prepareStatement(stmt);
            pstmt.setInt(2, sesion.getIdActividad());
            pstmt.setInt(3, sesion.getIdSesion());
            pstmt.setInt(1, butaca.getIdButaca());
            
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            return resultado.getInt(1); //Correcto
            
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
        return -1;
    }

    public static int generaTicketAbono(ArrayList<ButacaSesion> butacas, SesionBean sesion, String motivo) {
        int result=0;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                "INSERT INTO `cdc`.`tickets` (`idButaca`, `idActividad`, `idSesion`, `importeVenta`, `idUsuario`, `motivo`) VALUES (?, ?, ?, ?, ?, ?)");
            PreparedStatement update = conexion.prepareStatement(
                "UPDATE `cdc`.`tickets` SET `isAnulada`=true WHERE  `idTicket`=593;");
            for (ButacaSesion butaca : butacas) {
                
                
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
