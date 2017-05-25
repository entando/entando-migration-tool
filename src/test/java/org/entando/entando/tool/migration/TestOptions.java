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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author matteo
 */
public class TestOptions implements ITestConnectionParams {

    @Before
    public void setup() throws Throwable {
        Options.loadParams(args);
    }

    @Test
    public void testDriverName() {
        String src = Options.getSrcUrl();
        assertNotNull(src);
        switch (Options.getJdbcDriver()) {
            case JDBC_DRIVER_POSTGRES: {
                assertEquals(URL_SRC_POSTGRES,
                        src);
                break;
            }
            case JDBC_DRIVER_MYSQL: {
                assertEquals(URL_SRC_MYSQL,
                        src);
                break;
            }
        }

    }

    @Test
    public void testUsernameParameter() {
        final String username = Options.getUsername();
        assertNotNull(username);
        assertEquals(USERNAME, username);
    }

    @Test
    public void testPasswordParameter() {
        final String password = Options.getPassword();
        assertNotNull(password);
        assertEquals(PASSWORD, password);
    }

    @Test
    public void testDriverNameDst() {
        String dst = Options.getDstUrl();
        assertNotNull(dst);
        switch (Options.getJdbcDriver()) {
            case JDBC_DRIVER_POSTGRES: {
                assertEquals(URL_DST_POSTGRES, dst);
                break;
            }
            case JDBC_DRIVER_MYSQL: {
                assertEquals(URL_DST_MYSQL, dst);
                break;
            }
        }

    }

    @Test
    public void testJdbcDriver() {

        System.out.println(">>> Options.getJdbcDriver " + Options.getJdbcDriver());

        switch (Options.getJdbcDriver()) {
            case JDBC_DRIVER_POSTGRES: {
                testJdbcDriverPostgres();
                break;
            }
            case JDBC_DRIVER_MYSQL: {
                testJdbcDriverMysql();
                break;
            }
        }
    }

    private void testJdbcDriverPostgres() {
        String jdbcDriverPg = Options.getJdbcDriver();
        assertNotNull(jdbcDriverPg);
        assertEquals(JDBC_DRIVER_POSTGRES, jdbcDriverPg);
    }

    private void testJdbcDriverMysql() {
        String jdbcDriver = Options.getJdbcDriver();
        assertNotNull(jdbcDriver);
        assertEquals(JDBC_DRIVER_MYSQL, jdbcDriver);
    }

    @Test
    public void testDefaultsOptions() {
        Integer minIdle = Options.getMinIdle();
        assertEquals(SystemConstants.DEFAULT_MIN_IDLE, minIdle);
        Integer maxIdle = Options.getMaxIdle();
        assertEquals(SystemConstants.DEFAULT_MAX_IDLE, maxIdle);
        Integer maxPreparedStatement = Options.getMaxPreparedStatement();
        assertEquals(SystemConstants.DEFAULT_MAX_OPEN_PREPARED_STATEMENTS, maxPreparedStatement);

    }

    private final static String args[] = {
        Options.ARG_SRC_URL + "=" + URL_SRC_POSTGRES,
        Options.ARG_USERNAME + "=" + USERNAME,
        Options.ARG_PASSWORD + "=" + PASSWORD,
        Options.ARG_DST_URL + "=" + URL_DST_POSTGRES,
        Options.ARG_JDBC_DRIVER + "=" + JDBC_DRIVER_POSTGRES,
        Options.ARG_MIN_IDLE + "=" + SystemConstants.DEFAULT_MIN_IDLE

    /*
		Options.ARG_SRC_URL + "=" + URL_SRC_MYSQL,
		Options.ARG_USERNAME + "=" + USERNAME,
		Options.ARG_PASSWORD + "=" + PASSWORD,
		Options.ARG_DST_URL + "=" + URL_DST_MYSQL,
		Options.ARG_JDBC_DRIVER + "=" + JDBC_DRIVER_MYSQL,
		Options.ARG_MIN_IDLE + "=" + SystemConstants.DEFAULT_MIN_IDLE
     */
    };

}
