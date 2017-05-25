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

import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class TestSQLMigrationPagesLastUpdate implements ITestConnectionParams {

    @Test
    public void testMigration() {
        if (JBDC_DRIVER_POSTGRES_ENABLE) {
            testMigrationPostgres();
        }

        if (JBDC_DRIVER_MYSQL_ENABLE) {
            testMigrationMysql();
        }

    }

    private void testMigrationPostgres() {
        try {

            //System.out.println("Options.ARG_JDBC_DRIVER " + Options.getJdbcDriver());
            final DatabaseDriver.SUPPORTED_DBMS driver = DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL;
            //System.out.println("\t >> Driver : " + driver);
            final PooledConnection sourceDatabasePort = PooledConnectionFactory.getConnection(driver, URL_SRC_POSTGRES, USERNAME, PASSWORD);

            final String urlSourceDatabaseEnv = URL_SRC_POSTGRES.substring(0, URL_SRC_POSTGRES.lastIndexOf("Port")).concat("Serv");
            final PooledConnection sourceDatabaseEnv = PooledConnectionFactory.getConnection(driver, urlSourceDatabaseEnv, USERNAME, PASSWORD);

            final PooledConnection destinationDatabase = PooledConnectionFactory.getConnection(driver, URL_DST_POSTGRES, USERNAME, PASSWORD);

            assertTrue(new SQLMigrationPagesLastUpdate(sourceDatabasePort, sourceDatabaseEnv, destinationDatabase).execute());

        } catch (Throwable ex) {
            fail(ex.getMessage());
        }

    }

    private void testMigrationMysql() {
        try {

            //System.out.println("Options.ARG_JDBC_DRIVER " + Options.getJdbcDriver());
            final DatabaseDriver.SUPPORTED_DBMS driver = DatabaseDriver.SUPPORTED_DBMS.MYSQL;
            //System.out.println("\t >> Driver : " + driver);
            final PooledConnection sourceDatabasePort = PooledConnectionFactory.getConnection(driver, URL_SRC_MYSQL, USERNAME, PASSWORD);

            final String urlSourceDatabaseEnv = URL_SRC_MYSQL.substring(0, URL_SRC_MYSQL.lastIndexOf("Port")).concat("Serv");
            final PooledConnection sourceDatabaseEnv = PooledConnectionFactory.getConnection(driver, urlSourceDatabaseEnv, USERNAME, PASSWORD);

            final PooledConnection destinationDatabase = PooledConnectionFactory.getConnection(driver, URL_DST_MYSQL, USERNAME, PASSWORD);

            assertTrue(new SQLMigrationPagesLastUpdate(sourceDatabasePort, sourceDatabaseEnv, destinationDatabase).execute());

        } catch (Throwable ex) {
            Logger.getLogger(TestSQLMigrationPagesLastUpdate.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
