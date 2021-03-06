package com.uas.restaurantsearch.adapters;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.restaurantsearch.R;
import com.uas.restaurantsearch.models.Restaurants;
import com.uas.restaurantsearch.models.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantHolder>{

    List<Restaurants> restaurants;
    AdapterInterface mInterface;

    public RestaurantsAdapter(List<Restaurants> restaurants, AdapterInterface mInterface)
    {
        this.restaurants = restaurants;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_recycler, viewGroup, false);
            return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int i) {
        Restaurants.Restaurant restaurant = restaurants.get(i).getRestaurant();
        holder.textView.setText(restaurant.getName());
        holder.address.setText(restaurant.getLocation().getLocality_verbose());
        if(Utility.isValidStr(restaurant.getThumb()))
            Picasso.get().load(restaurant.getThumb()).placeholder(R.drawable.ic_restaurant_black_24dp).into(holder.imageView);
        if(Utility.isValidStr(restaurant.getCuisines()))
            holder.cuisines.setText(restaurant.getCuisines());
        holder.ratings.setText(restaurant.getUser_rating().getAggregate_rating());
        Drawable drawable = holder.ratings.getBackground();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.parseColor("#" + restaurant.getUser_rating().getRating_color()));
        holder.ratings.setBackground(drawable);

        if(restaurant.getIs_delivering_now().equals("1"))
            holder.onlinedeliv.setVisibility(View.VISIBLE);
        else
            holder.onlinedeliv.setVisibility(View.GONE);

        holder.mView.setTag(restaurant);
        holder.mView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class RestaurantHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        TextView address;
        TextView cuisines, ratings, onlinedeliv;
        View mView;

        public RestaurantHolder(View view)
        {
            super(view);
            imageView = view.findViewById(R.id.image);
            textView = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            cuisines = view.findViewById(R.id.cuisines);
            ratings = view.findViewById(R.id.ratings);
            onlinedeliv = view.findViewById(R.id.onlinedeliv);
            mView = view;
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Restaurants.Restaurant restaurant = (Restaurants.Restaurant) view.getTag();
            mInterface.onClick(restaurant);
        }
    };

}
