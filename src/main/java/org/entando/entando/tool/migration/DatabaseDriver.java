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
 * @author Federico Locci <f.locci@entando.com>
 */
public class DatabaseDriver {

    public enum SUPPORTED_DBMS {
        MYSQL(DRV_MYSQL),
        POSTGRESQL(DRV_POSTGRES);

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
