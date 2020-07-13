package com.uas.restaurantsearch.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.restaurantsearch.R;
import com.uas.restaurantsearch.entity.Restaurants;
import com.uas.restaurantsearch.entity.Utility;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DetailPageFragment extends BaseFragment {

    public static final String TAG = DetailPageFragment.class.getName();

    private Restaurants.Restaurant restaurant;
    private ImageView imageView;
    private TextView nameText, ratingText, cuisinesText, localityText, addressText, avgCost, bogoOffers, userRatingText;
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.restraurant_detail, container, false);
        imageView = mView.findViewById(R.id.image);
        nameText = mView.findViewById(R.id.name);
        ratingText = mView.findViewById(R.id.ratings);
        cuisinesText = mView.findViewById(R.id.cuisines);
        localityText = mView.findViewById(R.id.locality);
        addressText = mView.findViewById(R.id.address);
        avgCost = mView.findViewById(R.id.avg_cost);
        bogoOffers = mView.findViewById(R.id.bogo_txt);
        userRatingText = mView.findViewById(R.id.rating_text);
        webView = mView.findViewById(R.id.web_view);
        return mView;
    }
    private void openBrowser()
    {
        String namarestoran = restaurant.getName();
        String alamat = restaurant.getLocation().getAddress();
        String kota = restaurant.getLocation().getCity();
        String latitude = String.valueOf(restaurant.getLocation().getlatitude());
        String longitude = String.valueOf(restaurant.getLocation().getlongitude());
        String url = "https://irvan.had3ae.team/osm.php?lat="+latitude+"&long="+longitude+"&name="+namarestoran+"&jl="+alamat+"&kota="+kota;
        WebChromeClient wcc = new WebChromeClient();
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setInitialScale(1);
        webView.setWebChromeClient(wcc);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.loadUrl("https://had3ae.team/ppb/osm.php?long="+String.valueOf(restaurant.getLocation().getlongitude())+"&lat="+String.valueOf(restaurant.getLocation().getlatitude()));
        webView.loadUrl(url);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
//        String[] arrayhighlight = restaurant.gethighlights();
//        Log.d(TAG, "onViewCreated: "+ arrayhighlight[5]);
//        Log.d(TAG, "onViewCreated: "+ arrayhighlight.length);

        ArrayList<String> list = new ArrayList<>(restaurant.getHighlights());
        Log.d(TAG, "onViewCreated: "+restaurant.getHighlights());
        Log.d(TAG, "onViewCreated: "+ restaurant.getId());

        for (int counter = 0; counter < list.size(); counter++) {
            Log.d(TAG, "onViewCreated: hightlights "+list.get(counter));
        }

        if(Utility.isValidStr(restaurant.getFeatured_image()))
        {
            int height = getActivity().getResources().getDimensionPixelSize(R.dimen.size_85);
            int width = getActivity().getResources().getDimensionPixelSize(R.dimen.size_170);
            Picasso.get().load(restaurant.getFeatured_image()).placeholder(R.drawable.ic_restaurant_black_24dp).resize(width, height).into(imageView);
        }
//        Log.d(TAG, "onCreateView: "+restaurant.getLocation().getlongitude());

        nameText.setText(restaurant.getName());
        ratingText.setText(restaurant.getUser_rating().getAggregate_rating());
        Drawable drawable = ratingText.getBackground();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.parseColor("#" + restaurant.getUser_rating().getRating_color()));
        ratingText.setBackground(drawable);

        cuisinesText.setText(restaurant.getCuisines());
        localityText.setText(restaurant.getLocation().getLocality_verbose());
        addressText.setText(restaurant.getLocation().getAddress());

        userRatingText.setText(getString(R.string.user_experience, restaurant.getUser_rating().getRating_text()));
        avgCost.setText(getString(R.string.avg_cost, restaurant.getCurrency() + " " + restaurant.getAverage_cost_for_two()));

        if(restaurant.isInclude_bogo_offers())
            bogoOffers.setVisibility(View.VISIBLE);
        else
            bogoOffers.setVisibility(View.GONE);
        openBrowser();
        Log.d(TAG, "price range: "+restaurant.getPrice_range());
    }

    private void init()
    {
        Bundle bundle = getArguments();
        restaurant = (Restaurants.Restaurant) bundle.getSerializable("RESTAURANT");
    }
}
