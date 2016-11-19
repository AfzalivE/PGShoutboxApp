package com.afzaln.pgshoutbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by afzal on 2016-11-19.
 */

public class ExampleUnitTest {
    @Before
    public void setup() {
    }

    @After
    public void destroy() {
    }

    /**
     * Sample test case
     */
    @Test
    public void getWeather_cityName() {
        assertThat(true, is(true));
    }
}
