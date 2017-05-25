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

import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author matteo
 */
public class Options {

    private static Options _instance;
    private final static Properties propertyParameters = new Properties();

    private String _src;
    private String _username;
    private String _password;
    private String _dst;
    private String _jdbcDriver;
    private Integer _minIdle;
    private Integer _maxIdle;
    private Integer _maxPreparedStatement;

    public final static String ARG_SEPARATOR = "=";

    public final static String ARG_SRC_URL = "--src";
    public final static String ARG_USERNAME = "--user";
    public final static String ARG_PASSWORD = "--password";
    public final static String ARG_DST_URL = "--dst";
    public final static String ARG_JDBC_DRIVER = "--jdbc";
    public final static String ARG_MIN_IDLE = "--min-idle";
    public final static String ARG_MAX_IDLE = "--max-idle";
    public final static String ARG_MAX_PREPARED_SATEMENT = "--max-prepared-statement";

    private Options() {
    }

    public static Options getInstance() {
        if (null == _instance) {
            _instance = new Options();
            processDefaults();
        }
        return _instance;
    }

    public static void loadParams(String[] argv) {

        if (null == argv || argv.length == 0) {
            usage();
        }

        processDefaults();
        /*Usare in class edi propetries cosi argv nn viene parsato ogni volta. Migliora le performance!*/
        Arrays.asList(argv).forEach(consumer -> {
            final String[] values = consumer.split(ARG_SEPARATOR);
            propertyParameters.setProperty(values[0], values[1]);
        });

        parseParameter();

        String stringCheckMandatoryParameter = checkMandatoryParameter();
        if (!stringCheckMandatoryParameter.equals("")) {
            System.err.println(stringCheckMandatoryParameter);
            usage();
            System.exit(1);
        }

    }

    private static void parseParameter() {

        final Options instance = Options.getInstance();
        instance._src = ifIsNullValue(propertyParameters.getProperty(ARG_SRC_URL), instance._src);
        instance._username = ifIsNullValue(propertyParameters.getProperty(ARG_USERNAME), instance._username);
        instance._password = ifIsNullValue(propertyParameters.getProperty(ARG_PASSWORD), instance._password);
        instance._dst = ifIsNullValue(propertyParameters.getProperty(ARG_DST_URL), instance._dst);
        instance._jdbcDriver = ifIsNullValue(propertyParameters.getProperty(ARG_JDBC_DRIVER), instance._jdbcDriver);
        instance._minIdle = Integer.parseInt(ifIsNullValue(propertyParameters.getProperty(ARG_MIN_IDLE), Integer.toString(instance._minIdle)));
        instance._maxIdle = Integer.parseInt(ifIsNullValue(propertyParameters.getProperty(ARG_MAX_IDLE), Integer.toString(instance._maxIdle)));;
        instance._maxPreparedStatement = Integer.parseInt(ifIsNullValue(propertyParameters.getProperty(ARG_MAX_PREPARED_SATEMENT), Integer.toString(instance._maxPreparedStatement)));

    }

    private static String ifIsNullValue(final String value, final String defaultValue) {
        return null == value ? defaultValue : value;
    }

    private static String checkMandatoryParameter() {
        final String message = new StringBuffer().append("missing ").append("@").append(" parameter").toString();
        if (null == Options.getInstance()._src) {
            return message.replace("@", ARG_SRC_URL);

        } else if (null == Options.getInstance()._username) {
            return message.replace("@", ARG_USERNAME);

        } else if (null == Options.getInstance()._password) {
            return message.replace("@", ARG_PASSWORD);

        } else if (null == Options.getInstance()._dst) {
            return message.replace("@", ARG_DST_URL);

        } else if (null == Options.getInstance()._jdbcDriver) {
            return message.replace("@", ARG_JDBC_DRIVER);

        }
        return "";
    }

    /**
     * Assign defaults values for unspecified parameters
     */
    private static void processDefaults() {
        final Options instance = Options.getInstance();

        if (null == instance._minIdle) {
            instance._minIdle = SystemConstants.DEFAULT_MIN_IDLE;
        }
        if (null == instance._maxIdle) {
            instance._maxIdle = SystemConstants.DEFAULT_MAX_IDLE;
        }
        if (null == instance._maxPreparedStatement) {
            instance._maxPreparedStatement = SystemConstants.DEFAULT_MAX_OPEN_PREPARED_STATEMENTS;
        }
    }

    private static void usage() {
        System.out.println(""
                + "----------------------------------------------------------------------------"
                + "\nusage : java -jar entando-db-migration-tool.jar  "
                + "\n --src=<connection string source database> "
                + "\n --user=<username connection database> "
                + "\n --password=<password user> "
                + "\n --dst=<connection string destination database> \n"
                + "example : java -jar migrate.jar --src=127.0.0.1:5432/ent-4.2Port --user=agile --password=agile --dst=127.0.0.1:5432/ent4-3Port --jdbc=postgresql \n "
                + "-----------------------------------------------------------------------------\n");
    }

    /**
     * Return source JDBC URL
     *
     * @return
     */
    public static String getSrcUrl() {
        final Options instance = Options.getInstance();

        return instance._src;
    }

    /**
     * Return source Username DB
     *
     * @return
     */
    public static String getUsername() {
        final Options instance = Options.getInstance();
        return instance._username;
    }

    /**
     * Return source Password DB
     *
     * @return
     */
    public static String getPassword() {
        final Options instance = Options.getInstance();
        return instance._password;
    }

    /**
     * Return source JDBC URL
     *
     * @return
     */
    public static String getDstUrl() {
        final Options instance = Options.getInstance();
        return instance._dst;
    }

    /**
     * Return source JDBC TYPE
     *
     * @return
     */
    public static String getJdbcDriver() {
        final Options instance = Options.getInstance();
        return instance._jdbcDriver;
    }

    /**
     * Return the MIN number of idle connections
     *
     * @return
     */
    public static Integer getMinIdle() {
        final Options instance = Options.getInstance();

        return instance._minIdle;
    }

    /**
     * Return the MAX number of idle connections
     *
     * @return
     */
    public static Integer getMaxIdle() {
        final Options instance = Options.getInstance();
        return instance._maxIdle;
    }

    /**
     * Return the Max number of PrepraredStatement
     *
     * @return
     */
    public static Integer getMaxPreparedStatement() {
        final Options instance = Options.getInstance();
        return instance._maxPreparedStatement;
    }

}
