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
import java.sql.SQLException;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class TestPooledConnectionFactory implements ITestConnectionParams {

    @Test
    public void testPooledConnectionFactory() throws SQLException {

        if (JBDC_DRIVER_POSTGRES_ENABLE) {
            testPooledConnectionFactoryPostgres();
        }

        if (JBDC_DRIVER_MYSQL_ENABLE) {
            testPooledConnectionFactoryMysql();
        }

    }

    private void testPooledConnectionFactoryPostgres() throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            System.out.println("testPooledConnectionFactoryPostgres");
            DatabaseDriver.SUPPORTED_DBMS driver = DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL;
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(driver, URL_SRC_POSTGRES, USERNAME, PASSWORD);
            assertNotNull(pooledConnection.getConnection());
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

    private void testPooledConnectionFactoryMysql() throws SQLException {
        Connection connection1 = null;
        Connection connection2 = null;
        try {
            System.out.println("testPooledConnectionFactoryMysql");
            DatabaseDriver.SUPPORTED_DBMS driver = DatabaseDriver.SUPPORTED_DBMS.MYSQL;
            PooledConnection pooledConnection = PooledConnectionFactory.getConnection(driver, URL_SRC_MYSQL, USERNAME, PASSWORD);
            assertNotNull(pooledConnection.getConnection());
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

}
