package com.srinumallidi.popularmovies.data;

import com.srinumallidi.popularmovies.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srinu Mallidi.
 */
public class ParseMovieData {

    public static final String TAG = ParseMovieData.class.getSimpleName();

    public static List<Movie> getMovieListFromJSON(String movieJsonStr) throws JSONException {

        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(Constants.TMDB_RESULTS);

        List<Movie> movies = new ArrayList<Movie>();

        for (int i=0; i < moviesArray.length(); i++) {
            JSONObject popularMovies = moviesArray.getJSONObject(i);

            Movie movie = new Movie();

            movie.setOriginalTitle(popularMovies.getString(Constants.TMDB_TITLE));
            movie.setSynopsis(popularMovies.getString(Constants.TMDB_OVERVIEW));
            movie.setReleaseDate(popularMovies.getString(Constants.TMDB_RELEASEDATE));
            movie.setPoster(popularMovies.getString(Constants.TMDB_POSTERPATH));
            movie.setRating(popularMovies.getDouble(Constants.TMDB_VOTEAVERAGE));
            movie.setMovieId(popularMovies.getInt(Constants.TMDB_ID));

            movies.add(movie);
        }
        return movies;
    }


    public static List<MovieTrailer> getMovieTrailerFromJson(String trailerJsonStr) throws JSONException {

        List<MovieTrailer> results = new ArrayList<MovieTrailer>();

        JSONObject root = new JSONObject(trailerJsonStr);
        JSONArray list = root.getJSONArray("results");

        for (int i=0; i < list.length(); i++) {

            JSONObject x = list.getJSONObject(i);

            MovieTrailer trailer = new MovieTrailer();

            trailer.setMovieId(x.getString("id"));
            trailer.setTrailerKey(x.getString("key"));
            trailer.setTrailerName(x.getString("name"));
            trailer.setTrailerSite(x.getString("site"));
            trailer.setTrailerSize(x.getInt("size"));
            trailer.setTrailerType(x.getString("type"));

            results.add(trailer);
        }
        return results;
    }


    public static List<MovieReview> getMovieReviewFromJson(String reviewJsonStr) throws JSONException {

        List<MovieReview> results = new ArrayList<MovieReview>();

        JSONObject root = new JSONObject(reviewJsonStr);
        JSONArray list = root.getJSONArray("results");

        for (int i=0; i < list.length(); i++) {

            JSONObject x = list.getJSONObject(i);

            MovieReview review = new MovieReview();

            review.setReviewId(x.getString("id"));
            review.setReviewAuthor(x.getString("author"));
            review.setReviewContent(x.getString("content"));
            review.setReviewURL(x.getString("url"));

            results.add(review);
        }
        return results;
    }


    public static List<MovieDetails> getMovieDetailsFromJson(String moviesDetailsJSONString) throws JSONException {

        List<MovieDetails> results = new ArrayList<MovieDetails>();

        JSONObject root = new JSONObject(moviesDetailsJSONString);
        JSONArray reviewsArray = root.getJSONArray("reviews");
        JSONArray trailersArray = root.getJSONArray("trailers");

        for (int i=0; i<reviewsArray.length(); i++) {

            JSONObject x = reviewsArray.getJSONObject(i);

            MovieDetails reviews = new MovieDetails();

            reviews.setReviewId(x.getString("review_id"));
            reviews.setReviewAuthor(x.getString("author"));
            reviews.setReviewContent(x.getString("content"));
            reviews.setReviewURL(x.getString("url"));

            results.add(reviews);
        }

        for (int i=0; i<trailersArray.length(); i++) {

            JSONObject x = trailersArray.getJSONObject(i);

            MovieDetails trailers = new MovieDetails();

            trailers.setTrailerKey(x.getString("key"));
            trailers.setTrailerName(x.getString("name"));
            trailers.setTrailerSite(x.getString("site"));
            trailers.setTrailerSize(x.getInt("size"));
            trailers.setTrailerType(x.getString("type"));

            results.add(trailers);
        }
        return results;
    }
}
