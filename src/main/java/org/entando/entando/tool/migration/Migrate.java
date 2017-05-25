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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class Migrate {

    private final List<ISQLMigration> migrate;

    Migrate() {
        this.migrate = new ArrayList();
    }

    public void addMigration(final ISQLMigration sqlMigration) {
        migrate.add(sqlMigration);
    }

    public void run() {
        migrate.forEach(action -> {
            action.execute();
        });
    }

    public static void main(String[] args) {
        System.out.println("Starting migration");
        Options.loadParams(args);
        Migrate migrate = new Migrate();

        final DatabaseDriver.SUPPORTED_DBMS driver = Options.getJdbcDriver().equals("postgresql")
                ? DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL : DatabaseDriver.SUPPORTED_DBMS.MYSQL;

        final PooledConnection sourceDatabasePort = PooledConnectionFactory.getConnection(driver, Options.getSrcUrl(), Options.getUsername(), Options.getPassword());

        final String urlSourceDatabaseEnv = Options.getSrcUrl().substring(0, Options.getSrcUrl().lastIndexOf("Port")).concat("Serv");
        final PooledConnection sourceDatabaseEnv = PooledConnectionFactory.getConnection(driver, urlSourceDatabaseEnv, Options.getUsername(), Options.getPassword());

        final PooledConnection destinationDatabase = PooledConnectionFactory.getConnection(driver, Options.getDstUrl(), Options.getUsername(), Options.getPassword());

        migrate.addMigration(new SQLMigrationPagesLastUpdate(sourceDatabasePort, sourceDatabaseEnv, destinationDatabase));
        migrate.run();

        System.out.println("done.");
        System.exit(0);

    }
}
