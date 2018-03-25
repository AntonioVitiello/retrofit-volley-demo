package com.demo.volley.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.demo.retrofit.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Vitiello Antonio on 25/03/2018.
 */

public class VolleyUtils {
    private static final String LOG_TAG = "VolleyUtils";
    private static final String SERVICE_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGES_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String API_KEY = "b93df5426ee003173d21ca16c533cbbe";

    private static VolleyUtils mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private VolleyUtils(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyUtils(context);
        }
        return mInstance;
    }

    public static Uri buildImageUrl(String encodedPath) {
        return Uri.parse(IMAGES_BASE_URL)
                .buildUpon()
                .appendEncodedPath(encodedPath)
                .build();
    }

    public static void loadImage(Context context, String imagePath, ImageView view) {
        final Uri imageUrl = buildImageUrl(imagePath);
        Log.d(LOG_TAG, "Loading Image: " + imageUrl);
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_android_black_24dp)
                .into(view, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Log.e(LOG_TAG, "Error while loading image: " + imageUrl.toString());
                    }
                });
    }

    public static String buildMovieDetailsUrl(int movieId) {
        // movie/{id}
        return Uri.parse(SERVICE_BASE_URL)
                .buildUpon()
                .appendEncodedPath("movie")
                .appendEncodedPath(String.valueOf(movieId))
                .appendQueryParameter("api_key", API_KEY)
                .build()
                .toString();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Using ApplicationContext and not just Context
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
