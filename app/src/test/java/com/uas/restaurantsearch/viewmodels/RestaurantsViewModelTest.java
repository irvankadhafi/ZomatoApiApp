package com.uas.restaurantsearch.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.uas.restaurantsearch.models.Restaurants;
import com.uas.restaurantsearch.viewmodelsfactory.RestaurantsDataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RestaurantsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    public RestaurantsViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel= new RestaurantsViewModel();
    }

    @Test
    public void getRestaurantListFromApi() {
//        viewModel.getRestaurantListFromApi();
    }
}