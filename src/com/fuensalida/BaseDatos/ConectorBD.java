/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.BaseDatos;

import org.mariadb.jdbc.MariaDbDataSource;
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

public class ConectorBD {

    private static String baseDatos="cdc";
    private static String usuario="CdC";
    private static String contrasenia="CdC0217";

    
    public static Connection getConnection1() throws NamingException, SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUser(ConectorBD.usuario);
        dataSource.setPassword(ConectorBD.contrasenia);
        dataSource.setUrl("jdbc:mariadb://localhost:3306/"+baseDatos);
        //dataSource.setDatabaseName(ConectorBD.baseDatos);
        //dataSource.setServerName("localhost");
        
        //dataSource.setServerName("CDCDELLCONSER");

        Connection conexion = dataSource.getConnection();
        return conexion;
    }


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




    
    public static Connection getConnection() throws NamingException, SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUser(ConectorBD.usuario);
        dataSource.setPassword(ConectorBD.contrasenia);
        dataSource.setUrl("jdbc:mariadb://localhost:3306/"+baseDatos);

        Connection conexion = dataSource.getConnection();
        return conexion;
    }
}
