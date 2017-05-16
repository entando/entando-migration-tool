/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entando.entando.tool.migration;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author matteo
 */
public class Options {


    private Options() {
        // do nothing
    }

    public static Options getInstance()
    {
        if (null == _instance) {
            _instance = new Options();
        }
        return _instance;
    }

    public static void loadParams(String[] argv) throws Throwable
    {
        // FIXME use java 8 lambda?
        for (String arg: argv)
        {
            parseParameter(arg);
        }
        // process default
        processDefaults();

        // TODO make sure that needed parameters exists
    }

    // FIXME add support for single item command line aruments (those with no '=')
    private static void parseParameter(String opt)
    {
        Options instance = Options.getInstance();
        String tok[];

        if  (StringUtils.isBlank(opt)) {
            return;
        }

        tok = opt.split(ARG_SEPARATOR);

        // process SRC
        if (opt.startsWith(ARG_SRC_URL)) {
            instance._src = tok[1];
        }

    }

    /**
     * Assign defaults values for unspecified parameters
     */
    private static void processDefaults()
    {
        Options instance = Options.getInstance();

        if (null == instance._minIdle) {
            instance._minIdle = SystemConstants.DEFAULT_MIN_IDLE;
        }
    }


    /**
     * Return source JDBC URL
     * @return
     */
    public static String getSrcUrl()
    {
        Options instance = Options.getInstance();

        return instance._src;
    }

    /**
     * Return the MIN number of idle connections
     * @return
     */
    public static Integer getMinIdle()
    {
        Options instance = Options.getInstance();

        return instance._minIdle;
    }

    private static Options _instance;

    private String _src;
    private Integer _minIdle;


    public final static String ARG_SEPARATOR = "=";

    public final static String ARG_SRC_URL = "src";
    public final static String ARG_DST_URL = "dst";

}
