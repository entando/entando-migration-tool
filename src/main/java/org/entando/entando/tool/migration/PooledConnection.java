/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.entando.entando.tool.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Entando
 */
public class PooledConnection {

    public static Connection getDataSource(
            SUPPORTED_DBMS dbms,
            String username,
            String password,
            String url) throws Throwable
    {
        BasicDataSource ds = new BasicDataSource();
        Connection connection = null;

        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(url))
        {
            return connection;
        }

        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(dbms.getDriverName());

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

        connection = ds.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT code from PAGES");

        System.out.println("The Connection Object is of Class: "+connection.getClass());
        try (ResultSet resultSet = pstmt.executeQuery();)
        {
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1));
            }
        }
        catch (Exception e)
        {
            connection.rollback();
            e.printStackTrace();
        }

        return connection;
    }



    public enum SUPPORTED_DBMS {
        MYSQL(DRV_MYSQL),
        POSTGRES(DRV_POSTGRES);

        private String name;

        SUPPORTED_DBMS(String name) {
            this.name = name;
        }

        public String getDriverName() {
            return name;
        }

    };

    private final static String DRV_POSTGRES = "org.postgresql.Driver";
    private final static String DRV_MYSQL = "com.mysql.jdbc.Driver";
}
