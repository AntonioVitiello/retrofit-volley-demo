package com.demo.retrofit.net;

import com.demo.retrofit.net.json.MovieDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Vitiello Antonio on 25/03/2018.
 */

public interface MovieServices {

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
