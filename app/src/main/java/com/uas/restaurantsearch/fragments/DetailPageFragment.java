package com.uas.restaurantsearch.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.uas.restaurantsearch.HttpHandler;
import com.uas.restaurantsearch.MainActivity;
import com.uas.restaurantsearch.R;
import com.uas.restaurantsearch.entity.Constant;
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
    Context context;
    private Restaurants.Restaurant restaurant;
    private ImageView imageView;
    private TextView nameText, ratingText, cuisinesText, localityText, addressText, avgCost, bogoOffers, userRatingText;
    private WebView webView;
    private GetColors getColors;
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
        listView.setNestedScrollingEnabled(true);
        getColors = new GetColors();
        return mView;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetColors extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetch Reviews Data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // JSON data url
            String reviewsapi = "https://api.zomato.com/v1/reviews.json/"+restaurant.getId()+"/user?count=0&apikey="+Constant.API_KEY;
            String jsonurl = "https://api.zomato.com/v1/reviews.json/18701282/user?count=0&apikey=ed217d7f9628996e820e7aa509b09d3f";
            String jsonString = httpHandler.makeServiceCall(reviewsapi);
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    JSONArray colors = jsonObject.getJSONArray("userReviews");

                    for (int i = 0; i < colors.length(); i++) {
                        JSONObject c = colors.getJSONObject(i);
                        JSONObject review = c.getJSONObject("review");


                        String name = review.getString("userName");
                        String rating = review.getString("rating");
                        String reviewText = review.getString("reviewText");
                        String ratingColor = review.getString("ratingColor");


                        HashMap<String, String> colorx = new HashMap<>();

                        colorx.put("name", name);
                        colorx.put("rating", rating);
                        colorx.put("review", reviewText);
                        colorx.put("ratingcolor", ratingColor);
                        Log.d(TAG, "datanyaaaa: "+colorx);


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
                    new String[]{"name","rating","review"},
                    new int[]{R.id.text_headline,R.id.text_subhead,R.id.text_review});

            listView.setAdapter(adapter);

            if (reviewJsonList.isEmpty()){
                listView.setVisibility(View.GONE);
                Toast toast = Toast.makeText(getActivity(),
                        "Reviws not loaded",
                        Toast.LENGTH_SHORT);
                toast.show();
            }else {
                listView.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getActivity(),
                        "Reviws loaded",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
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

        ArrayList<String> list = new ArrayList<>(restaurant.getHighlights());
//        Log.d(TAG, "onViewCreated: "+restaurant.getHighlights());
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

        if(restaurant.getIs_delivering_now().equals("1"))
            bogoOffers.setVisibility(View.VISIBLE);
        else
            bogoOffers.setVisibility(View.GONE);

        openBrowser();
        Log.d(TAG, "price range: "+restaurant.getPrice_range());
        Log.d(TAG, "Apakah delivery sekarang: "+restaurant.getIs_delivering_now());
        new GetColors().execute();
    }

    private void init()
    {
        Bundle bundle = getArguments();
        restaurant = (Restaurants.Restaurant) bundle.getSerializable("RESTAURANT");
    }
}
