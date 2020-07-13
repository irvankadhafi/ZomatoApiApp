package com.uas.restaurantsearch.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.restaurantsearch.HttpHandler;
import com.uas.restaurantsearch.MainActivity;
import com.uas.restaurantsearch.R;
import com.uas.restaurantsearch.entity.Restaurants;
import com.uas.restaurantsearch.entity.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DetailPageFragment extends BaseFragment {


    public static final String TAG = DetailPageFragment.class.getName();
    private ProgressDialog progressDialog;
    private ListView listView;
    ArrayList<HashMap<String, String>> reviewJsonList;

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
        reviewJsonList = new ArrayList<>();
        listView = mView.findViewById(R.id.listview);
        new GetColors().execute();
        return mView;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetColors extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // JSON data url
            String jsonurl = "https://api.zomato.com/v1/reviews.json/18389079/user?count=0&apikey=ed217d7f9628996e820e7aa509b09d3f";
            String jsonString = httpHandler.makeServiceCall(jsonurl);
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    JSONArray colors = jsonObject.getJSONArray("userReviews");

                    for (int i = 0; i < colors.length(); i++) {
                        JSONObject c = colors.getJSONObject(i);
//                        String id = c.getString("id");
//                        String color = c.getString("color");
//                        String type = c.getString("type");

                        JSONObject review = c.getJSONObject("review");
                        String name = review.getString("userName");
                        String rating = review.getString("rating");
                        String reviewText = review.getString("reviewText");


                        HashMap<String, String> colorx = new HashMap<>();

                        colorx.put("name", name);
                        colorx.put("rating", rating);
                        colorx.put("review", reviewText);
                        Log.d(TAG, "doInBackground: "+reviewText);

                        reviewJsonList.add(colorx);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e(TAG, "Could not get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing()) progressDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), reviewJsonList, R.layout.listreview_item,
                    new String[]{"review","rating","name"},
                    new int[]{R.id.colorName,R.id.colorType,R.id.colorHex});

            listView.setAdapter((android.widget.ListAdapter) adapter);
        }

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
