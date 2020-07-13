package com.uas.restaurantsearch.adapters;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uas.restaurantsearch.R;
import com.uas.restaurantsearch.models.Categories;
import com.uas.restaurantsearch.models.Restaurants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ChildHolder>{

    private List<Categories.Category> categories;
    private Context context;
    private HashMap<Long, List<Restaurants>> restaurantHashMap;

    private AdapterInterface mInterface;

    public HomePageAdapter(List<Categories.Category> categories, AdapterInterface clickInterface, HashMap<Long, List<Restaurants>> restaurantHashMap)
    {
        this.categories = categories;
        this.mInterface = clickInterface;
        this.restaurantHashMap = restaurantHashMap;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.parent_recycler, viewGroup,false);
        return new ChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChildHolder childHolder, int i) {

        Categories.Category category = categories.get(i);
        Log.d("fatal", "category id" + category.getId());
        childHolder.list.setHasFixedSize(true);
        childHolder.list.setLayoutManager(getLayoutManager());
        childHolder.header.setText(category.getName());
        childHolder.adapter = getAdapter(category.getId());
        childHolder.list.setAdapter(childHolder.adapter);
        childHolder.seeAll.setTag(category);
        childHolder.seeAll.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder
    {
        TextView header;
        RecyclerView list;
        RecyclerView.Adapter adapter;
        TextView seeAll;

        ChildHolder(View view)
        {
            super(view);
            header = view.findViewById(R.id.header);
            list = view.findViewById(R.id.restaurant_list);
            seeAll = view.findViewById(R.id.see_all);
        }
    }

    private RecyclerView.Adapter getAdapter(long categoryId)
    {
        if(restaurantHashMap.get(categoryId) == null)
            restaurantHashMap.put(categoryId, new ArrayList<Restaurants>());

        return new RestaurantsAdapter(restaurantHashMap.get(categoryId), mInterface);
    }

    private LinearLayoutManager getLayoutManager()
    {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Categories.Category category = (Categories.Category) view.getTag();
            mInterface.onClickSeeAll(category);

        }
    };
}
