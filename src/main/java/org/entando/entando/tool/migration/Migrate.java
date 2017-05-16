/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.entando.entando.tool.migration;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;



/**
 *
 * @author Entando
 */
public class Migrate {


    public static void main(String[] argv)
    {
        // parse command line options
        Options.loadParams(argv);

    }


    /*
    private static BasicDataSource getDataSource()
    {

        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:postgresql://127.0.0.1:5432/ent-4.2Port");
            ds.setUsername("agile");
            ds.setPassword("agile");
            ds.setDriverClassName("org.postgresql.Driver");

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);

            dataSource = ds;

            // test

            try
            {
                Connection connection = ds.getConnection();
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return dataSource;
    }
    */

    private static BasicDataSource dataSource;

}
