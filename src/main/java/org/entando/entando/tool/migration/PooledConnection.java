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
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Federico Locci <f.locci@entando.com>
 */
public class PooledConnection {

	private final BasicDataSource datasource;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SQLMigrationPagesLastUpdate.class);

	protected PooledConnection(
			final DatabaseDriver.SUPPORTED_DBMS driver,
			final String url,
			final String username,
			final String password) {

		//jdbc:postgresql://127.0.0.1:5432/ent-4.2Port
		final String jdbcString = new StringBuilder("jdbc:").append(driver).append("://").append(url).toString();
		//System.out.println(">>jdbc: " + jdbcString.replace(jdbcString.split(":")[1], jdbcString.split(":")[1].toLowerCase()));
		//System.out.println(">>DriverName: " + driver.getDriverName());
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(jdbcString.replace(jdbcString.split(":")[1], jdbcString.split(":")[1].toLowerCase()));
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		basicDataSource.setDriverClassName(driver.getDriverName());
		Options.getInstance();
		basicDataSource.setMinIdle(Options.getMinIdle());
		basicDataSource.setMaxIdle(Options.getMaxIdle());
		basicDataSource.setMaxOpenPreparedStatements(Options.getMaxPreparedStatement());
		this.datasource = basicDataSource;
		//System.out.println("Connection done.");
	}

	public Connection getConnection() throws SQLException {
		if (null != datasource) {
			return datasource.getConnection();
		}

		return null;
	}

	public String getDriverName() {
		return this.datasource.getDriverClassName();
	}

	public void close() {
		try {
			if (null != datasource) {
				datasource.close();
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
	}

}
