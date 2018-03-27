package com.demo.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.retrofit.net.RetrofitUtils;
import com.demo.retrofit.net.json.MovieDetails;
import com.demo.volley.VolleyDemoActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitDemoActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RetrofitDemoActivity";
    private EditText movieIdEditText;
    private TextView adultTextView;
    private TextView originalTitleTextView;
    private TextView overviewTextView;
    private ImageView backdropImageView;
    private ImageView posterImageView;
    Callback<MovieDetails> callback = new Callback<MovieDetails>() {
        @Override
        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
            String url = call.request().url().toString();
            Log.d(LOG_TAG, "Success loading: " + url);
            RetrofitDemoActivity.this.onResponse(response.body());
        }

        @Override
        public void onFailure(Call<MovieDetails> call, Throwable thr) {
            String url = call.request().url().toString();
            Log.e(LOG_TAG, "Error on loading: " + url, thr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);

        movieIdEditText = findViewById(R.id.movie_id);
        adultTextView = findViewById(R.id.adult);
        originalTitleTextView = findViewById(R.id.original_title);
        overviewTextView = findViewById(R.id.overview);

        posterImageView = findViewById(R.id.poster_path);
        backdropImageView = findViewById(R.id.backdrop_path);

    }

    public void onResponse(MovieDetails movieDetails) {
        if (movieDetails != null) {
            Log.d(LOG_TAG, "onResponse: " + movieDetails.getOverview());
            movieIdEditText.setText(String.valueOf(movieDetails.getId()));
            adultTextView.setText(Boolean.toString(movieDetails.getAdult()));
            originalTitleTextView.setText(movieDetails.getOriginalTitle());
            overviewTextView.setText(movieDetails.getOverview());

            RetrofitUtils.loadImage(this, movieDetails.getPosterPath(), posterImageView);
            RetrofitUtils.loadImage(this, movieDetails.getBackdropPath(), backdropImageView);
        }
    }

    /**
     * On button click starts loading movie details from TMDB server
     * eg:
     * https://api.themoviedb.org/3/movie/550?api_key=b93df5426ee003173d21ca16c533cbbe
     *
     * @param view
     */
    public void onClickStart(View view) {
        int movieId = Integer.parseInt((movieIdEditText.getText().toString()));
        loadMovieDetailsForId(movieId);
        closeKeyboard();
    }

    public void loadMovieDetailsForId(int movieId) {
        RetrofitUtils.movieDetailsRequest(movieId, callback);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.retrofit_demo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean displayItemAsSelect = true;
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.volley_demo_mi:
                intent = new Intent(this, VolleyDemoActivity.class);
                break;
            case R.id.retrofit_demo_mi:
                intent = new Intent(this, RetrofitDemoActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
        return true;
    }

}
