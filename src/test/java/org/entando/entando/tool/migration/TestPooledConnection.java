/*
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package org.entando.entando.tool.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class TestPooledConnection implements ITestConnectionParams {

    @Test
    public void testConnectionPool() throws SQLException {

        if (JBDC_DRIVER_POSTGRES_ENABLE) {
            testConnectionPoolPostegres();
        }

        if (JBDC_DRIVER_MYSQL_ENABLE) {
            testConnectionPoolMysql();
        }

    }

    private void testConnectionPoolPostegres() throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL, URL_SRC_POSTGRES, USERNAME, PASSWORD);

            connection1 = pooledConnection.getConnection();
            assertNotNull(connection1);
            connection2 = pooledConnection.getConnection();
            assertNotSame(connection1, connection2);
            connection1.close();
            connection2.close();

        } catch (SQLException ex) {
            fail(ex.getMessage());
        } finally {
            if (null != connection1) {
                connection1.close();
            }
            if (null != connection2) {
                connection2.close();
            }
        }

    }

    private void testConnectionPoolMysql() throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.MYSQL, URL_SRC_MYSQL, USERNAME, PASSWORD);

            connection1 = pooledConnection.getConnection();
            assertNotNull(connection1);
            connection2 = pooledConnection.getConnection();
            assertNotSame(connection1, connection2);

            connection1.close();
            connection2.close();

        } catch (SQLException ex) {
            Assert.fail(ex.getMessage());
        } finally {
            if (null != connection1) {
                connection1.close();
            }
            if (null != connection2) {
                connection2.close();
            }
        }

    }

    @Test
    public void testInvalidConnectionPool() {

        if (JBDC_DRIVER_POSTGRES_ENABLE) {
            testInvalidConnectionPoolPostgres();
        }

        if (JBDC_DRIVER_MYSQL_ENABLE) {
            testInvalidConnectionPoolMysql();
        }

    }

    private void testInvalidConnectionPoolPostgres() {

        boolean exceptionInvoked = false;

        try {
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL, URL_SRC_POSTGRES, "admin", "admin");

            assertNotNull(pooledConnection.getConnection());

        } catch (SQLException t) {
            exceptionInvoked = true;
        } catch (Throwable t) {
            throw t;
        }

        assertTrue(exceptionInvoked);
    }

    private void testInvalidConnectionPoolMysql() {

        boolean exceptionInvoked = false;

        try {
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.MYSQL, URL_SRC_MYSQL, "admin", "admin");

            assertNotNull(pooledConnection.getConnection());

        } catch (SQLException t) {
            exceptionInvoked = true;
        }

        assertTrue(exceptionInvoked);
    }

    @Test
    public void testQuery() {
        try {

            if (JBDC_DRIVER_POSTGRES_ENABLE) {
                testQueryPostgres();
            }

            if (JBDC_DRIVER_MYSQL_ENABLE) {
                testQueryMysql();
            }

        } catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

    private void testQueryPostgres() throws SQLException {
        Connection connection = null;
        try {
            List<String> pages = new ArrayList<>();

            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL, URL_SRC_POSTGRES, USERNAME, PASSWORD);

            connection = pooledConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT code from PAGES");
            System.out.println("The Connection Object is of Class: " + connection.getClass());
            ResultSet resultSet = pstmt.executeQuery();
            {
                while (resultSet.next()) {
                    String pageCode = resultSet.getString(1);

                    pages.add(pageCode);
                    System.out.println("\t >> page id " + pageCode);
                }
            }
            connection.close();
            assertNotNull(pages);
            assertFalse(pages.isEmpty());
            assertTrue(pages.size() > 1);
        } catch (SQLException ex) {
            fail(ex.getMessage());

        } finally {
            if (null != connection) {
                connection.close();
            }

        }

    }

    private void testQueryMysql() throws SQLException {
        Connection connection = null;
        try {

            List<String> pages = new ArrayList<>();

            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(
                    DatabaseDriver.SUPPORTED_DBMS.MYSQL, URL_SRC_MYSQL, USERNAME, PASSWORD);

            connection = pooledConnection.getConnection();

            PreparedStatement pstmt = connection.prepareStatement("SELECT code from pages");
            connection.setSchema(URL_SRC_MYSQL.substring(URL_SRC_MYSQL.lastIndexOf("/") + 1));
            System.out.println("The Connection Object is of Class: " + connection.getClass());
            ResultSet resultSet = pstmt.executeQuery();
            {
                {
                    while (resultSet.next()) {
                        String pageCode = resultSet.getString(1);
                        pages.add(pageCode);
                        System.out.println("\t >> page id " + pageCode);
                    }
                }
            }
            assertNotNull(pages);
            assertFalse(pages.isEmpty());
            assertTrue(pages.size() > 1);
        } catch (SQLException ex) {
            fail(ex.getMessage());
        } finally {
            if (null != connection) {
                connection.close();
            }

        }

    }

}
