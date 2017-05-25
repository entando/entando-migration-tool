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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class TestDatabaseDriver {

    private final static String DRV_POSTGRES = "org.postgresql.Driver";
    private final static String DRV_MYSQL = "com.mysql.jdbc.Driver";

    @Test
    public void testDriverName() {

        DatabaseDriver.SUPPORTED_DBMS dbms = DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL;

        assertEquals(DRV_POSTGRES,
                dbms.getDriverName());

        dbms = DatabaseDriver.SUPPORTED_DBMS.MYSQL;

        assertEquals(DRV_MYSQL,
                dbms.getDriverName());
    }

}
