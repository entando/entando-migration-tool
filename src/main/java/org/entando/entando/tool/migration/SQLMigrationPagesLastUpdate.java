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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class SQLMigrationPagesLastUpdate implements ISQLMigration {

    final static String PAGES = "SELECT code from pages";
    final static String UPDATE_PAGE_LAST_UPDATED_TO_METADATA_TABLE = "UPDATE @table SET updatedat = ? WHERE code = ? ";

    final static String PGSQL_ROW_ACTIVITYSTREAM_LAST_UPDATE = "SELECT max(updatedate) lastupdate, namespace, actionname, parameters "
            + "FROM actionlogrecords "
            + "WHERE parameters like '%pageCode=@page'||chr(10)||'%' or parameters like '%pageCode=@page' "
            + "GROUP BY namespace, actionname, parameters";

    final static String MYSQL_ROW_ACTIVITYSTREAM_LAST_UPDATE = "SELECT max(updatedate) lastupdate, namespace, actionname, parameters "
            + "FROM actionlogrecords "
            + "WHERE parameters like concat('%pageCode=@page',char(10),'%')  or parameters like '%pageCode=@page' "
            + "GROUP BY namespace, actionname, parameters";

    private final PooledConnection sourceDatabasePort;
    private final PooledConnection sourceDatabaseEnv;
    private final PooledConnection destinationDatabase;

    SQLMigrationPagesLastUpdate(final PooledConnection sourceDatabasePort, final PooledConnection sourceDatabaseEnv, final PooledConnection destinationDatabase) {
        this.sourceDatabasePort = sourceDatabasePort;
        this.sourceDatabaseEnv = sourceDatabaseEnv;
        this.destinationDatabase = destinationDatabase;

    }

    @Override
    public boolean execute() {
        try {
            System.out.println("execute " + this.getClass().getName());
            return updateDataIntoMetatadaTable("pages_metadata_online");
        } catch (Throwable ex) {
            Logger.getLogger(SQLMigrationPagesLastUpdate.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private boolean updateDataIntoMetatadaTable(final String table) {

        final HashMap<String, Timestamp> pageLastUpdate;
        try {
            pageLastUpdate = fetchActivityStreamLastRowUpdatedData();
            pageLastUpdate.forEach((key, value) -> {
                setUpdateValue(table, key, value, destinationDatabase);
            });
            destinationDatabase.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLMigrationPagesLastUpdate.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private void setUpdateValue(final String table, final String key, final Timestamp value, final PooledConnection pooledConnection) {
        try {
            final Connection connection = pooledConnection.getConnection();
            connection.setAutoCommit(false);
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PAGE_LAST_UPDATED_TO_METADATA_TABLE.replace("@table", table));
            preparedStatement.setTimestamp(1, value);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
            connection.commit();
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLMigrationPagesLastUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return @throws SQLException
     */
    private HashMap<String, Timestamp> fetchActivityStreamLastRowUpdatedData() throws SQLException {

        final HashMap<String, Timestamp> pageLastUpdated = new HashMap<>();
        final List<String> pages = this.fetchPagesData();
        final String query = getQuerFetchActivityStreamLastRowUpdatedData();
        pages.forEach(page -> {
            this.setPageLastUpdate(pageLastUpdated, page, sourceDatabaseEnv, query);
        });
        sourceDatabaseEnv.close();
        return pageLastUpdated;
    }

    private String getQuerFetchActivityStreamLastRowUpdatedData() {

        if (sourceDatabaseEnv.getDriverName().equals(DatabaseDriver.SUPPORTED_DBMS.POSTGRESQL.getDriverName())) {
            return PGSQL_ROW_ACTIVITYSTREAM_LAST_UPDATE;
        } else if (sourceDatabaseEnv.getDriverName().equals(DatabaseDriver.SUPPORTED_DBMS.MYSQL.getDriverName())) {
            return MYSQL_ROW_ACTIVITYSTREAM_LAST_UPDATE;
        }
        return PGSQL_ROW_ACTIVITYSTREAM_LAST_UPDATE;

    }

    private boolean setPageLastUpdate(final HashMap<String, Timestamp> pageLastUpdated, final String page, final PooledConnection pooledConnection, final String query) {
        try {
            final Connection connection = pooledConnection.getConnection();
            final ResultSet resultset = connection.prepareStatement(query.replaceAll("@page", page)).executeQuery();
            while (resultset.next()) {
                pageLastUpdated.put(page, resultset.getTimestamp("lastupdate"));
            }
            resultset.close();
            connection.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLMigrationPagesLastUpdate.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private List<String> fetchPagesData() throws SQLException {
        final List<String> rows;

        final Connection connection = sourceDatabasePort.getConnection();
        rows = new ArrayList<>();
        final ResultSet resultSet = connection.prepareStatement(PAGES).executeQuery();
        while (resultSet.next()) {
            rows.add(resultSet.getString("code"));
        }
        resultSet.close();
        connection.close();
        sourceDatabasePort.close();
        return rows;
    }

}
