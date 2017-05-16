/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entando.entando.tool.migration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author matteo
 */
public class TestOptions implements ITestConnectionParams {

    @Test
    public void testDriverName() {
        Migrate.main(args);

        String src = Options.getSrcUrl();
        assertNotNull(src);
        assertEquals(URL_SRC_POSTGRES,
                src);
    }


    @Test
    public void testDefaultsOptions() {
        Migrate.main(args);

        Integer minIdle = Options.getMinIdle();

        assertEquals(SystemConstants.DEFAULT_MIN_IDLE, minIdle);
    }

    public final static String args[] = {
        Options.ARG_SRC_URL + "=" + URL_SRC_POSTGRES
    };

}
