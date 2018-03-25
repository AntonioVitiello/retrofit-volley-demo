package com.demo.retrofit.net;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.demo.retrofit.R;
import com.demo.retrofit.net.json.MovieDetails;
import com.squareup.picasso.Picasso;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vitiello Antonio on 25/03/2018.
 */

public class RetrofitUtils {
    private static final String LOG_TAG = "RetrofitUtils";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGES_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String API_KEY = "b93df5426ee003173d21ca16c533cbbe";
    private static Retrofit retrofitClient;

    public static Retrofit getClient() {
        if (retrofitClient == null) {
            retrofitClient = getClient(BASE_URL);
        }
        return retrofitClient;
    }

    private static Retrofit getClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void movieDetailsRequest(int movieId, Callback<MovieDetails> movieDetailsCallback) {
        MovieServices movieServices = getClient().create(MovieServices.class);
        movieServices.getMovieDetails(movieId, API_KEY).enqueue(movieDetailsCallback);
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

}
