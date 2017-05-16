/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.entando.entando.tool.migration;

import java.sql.Connection;
import java.sql.SQLException;
import junit.framework.Assert;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Entando
 */
public class TestPooledConnection implements ITestConnectionParams {

    @Test
    public void TestDriverName() {

        PooledConnection.SUPPORTED_DBMS dbms = PooledConnection.SUPPORTED_DBMS.POSTGRES;

        Assert.assertEquals(DRV_POSTGRES,
                dbms.getDriverName());

        dbms = PooledConnection.SUPPORTED_DBMS.MYSQL;

        Assert.assertEquals(DRV_MYSQL,
                dbms.getDriverName());
    }


    @Test
    public void TestConnectionPool() throws Throwable {
        try {
            Connection connection = PooledConnection.getDataSource(PooledConnection.SUPPORTED_DBMS.POSTGRES,
                    USERNAME,
                    PASSWORD,
                    URL_SRC_POSTGRES);

            assertNotNull(connection);

        } catch (Throwable t) {
            throw t;
        }
    }

    @Test
    public void TestInvalidConnectionPool() throws Throwable {
        boolean exceptionInvoked = false;

        try {
            Connection connection = PooledConnection.getDataSource(PooledConnection.SUPPORTED_DBMS.POSTGRES,
                    "meh",
                    "blah",
                    URL_SRC_POSTGRES);

            assertNotNull(connection);

        } catch (SQLException t) {
            exceptionInvoked = true;
        } catch (Throwable t) {
            throw t;
        }

        assertTrue(exceptionInvoked);
    }


    public final static String DRV_POSTGRES = "org.postgresql.Driver";
    public final static String DRV_MYSQL = "com.mysql.jdbc.Driver";


}
