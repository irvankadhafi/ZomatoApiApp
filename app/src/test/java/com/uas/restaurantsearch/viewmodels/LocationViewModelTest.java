package com.uas.restaurantsearch.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.uas.restaurantsearch.networks.APIRepo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class LocationViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    APIRepo apiRepo;
    private LocationViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel= new LocationViewModel();
    }

    @Test
    public void getLocationSuggestionsFromApi() {
        apiRepo.getLocationSuggestions("Bandung");
//        viewModel.getLocationSuggestionsFromApi("Bandung");
    }
}