package com.pinetree408.keme.prevent;

/**
 * Created by user on 2016-12-28.
 */

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PreventTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PreventTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PreventTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testPrevent()
    {
        assertTrue( true );
    }

}
