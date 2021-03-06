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

/**
 *
 * @author matteo
 */
public interface ITestConnectionParams {

    final static String USERNAME = "agile";
    final static String PASSWORD = "agile";

    final static String URL_SRC_POSTGRES = "127.0.0.1:5432/ent-4.2Port";
    final static String URL_DST_POSTGRES = "127.0.0.1:5432/ent-4.3Port";
    final static String JDBC_DRIVER_POSTGRES = "postgresql";

    final static String URL_SRC_MYSQL = "127.0.0.1:3306/ent4-2Port";
    final static String URL_DST_MYSQL = "127.0.0.1:3306/ent4-3Port";
    final static String JDBC_DRIVER_MYSQL = "mysql";

    final static boolean JBDC_DRIVER_POSTGRES_ENABLE = true;
    final static boolean JBDC_DRIVER_MYSQL_ENABLE = false;

}
