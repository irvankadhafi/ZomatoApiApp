package com.uas.restaurantsearch;

import com.uas.restaurantsearch.models.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
@SmallTest
public class ExampleUnitTest {

    private Location mLocation;

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        mLocation = new Location();
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void setCity (){
        String city = "Bandung";
        mLocation.setCity(city);
        assertEquals(city, "Bandung");
    }
}