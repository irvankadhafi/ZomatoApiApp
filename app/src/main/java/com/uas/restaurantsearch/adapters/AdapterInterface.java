package com.uas.restaurantsearch.adapters;

import com.uas.restaurantsearch.models.Categories;
import com.uas.restaurantsearch.models.Restaurants;

public interface AdapterInterface {
    void onClick(Restaurants.Restaurant restaurant);
    void onClickSeeAll(Categories.Category category);
}
