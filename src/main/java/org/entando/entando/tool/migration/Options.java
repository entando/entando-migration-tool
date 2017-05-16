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

    public static void loadParams(String[] argv)
    {
        // FIXME use java 8 lambda?
        for (String arg: argv)
        {
            parseParameter(arg);
        }
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
            instance.src = tok[1];
        }

    }


    /**
     * Return source JDBC URL
     * @return
     */
    public static String getSrcUrl()
    {
        Options instance = Options.getInstance();

        return instance.src;
    }

    private static Options _instance;

    private String src;


    public final static String ARG_SEPARATOR = "=";

    public final static String ARG_SRC_URL = "src";
    public final static String ARG_DST_URL = "dst";

}
