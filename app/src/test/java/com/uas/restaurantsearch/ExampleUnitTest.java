package com.uas.restaurantsearch;

import com.uas.restaurantsearch.models.Categories;
import com.uas.restaurantsearch.models.Location;
import com.uas.restaurantsearch.models.Restaurants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    private Location mLocation;
    private Restaurants.Restaurant mRestaurant;

    @Before
    public void setUpLocation() {
        mLocation = new Location();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSetCity (){
        String city = "Bandung";
        mLocation.setCity(city);
        assertThat(mLocation.getCity(), is(equalTo(city)));
    }

    @Test
    public void testGetCity() {
        String city = "Bandung";
        mLocation.setCity(city);
        assertThat(city, is(equalTo(mLocation.getCity())));
    }

    @Test
    public void testSetNameRestaurant (){
        mRestaurant = new Restaurants.Restaurant();
        String name = "The Bistro";
        mRestaurant.setName(name);
        assertThat(mRestaurant.getName(), is(equalTo(name)));
    }

    @Test
    public void testGetNameRestaurant (){
        mRestaurant = new Restaurants.Restaurant();
        String name = "The Bistro";
        mRestaurant.setName(name);
        assertThat(name, is(equalTo(mRestaurant.getName())));
    }


}