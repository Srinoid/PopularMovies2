package com.srinumallidi.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by Srinu Mallidi.
 */
public class URLHelper {

    public static final String TAG = URLHelper.class.getSimpleName();

    /**
     *
     * Function to build Movie Search URL
     *
     **/

    public static String buildMovieSearchURL(String searchOrder, int page) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY)
                .appendQueryParameter("vote_count.gte", "10")
                .appendQueryParameter("page", page + "")
                .appendQueryParameter("sort_by", searchOrder);

        String URL = builder.build().toString();
        return URL;
    }

    /**
     *
     * Function to build Movie Image URL
     *
     **/

    public static String buildImageURL(String image, String imageSize) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath(imageSize)
                .appendPath(image.startsWith("/") ? image.substring(1): image);

        String URL = builder.build().toString();

        return URL;
    }

    /**
     *
     * Function to build Movie Trailers URL
     *
     **/

    public static String buildTrailersURL(long movieID) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendPath("videos")
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        // print out full URL
        String URL = builder.build().toString();
        Log.d(TAG, "URL[" + URL + ")");

        return URL;
    }

    /**
     *
     * Function to build Movie Details URL
     * Sample URL : https://api.themoviedb.org/3/movie/87101?api_key=<Your TMDB key>
     *
     **/

    public static String buildMoviewDetailsURL(long movieID) {
        // build up URL
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        // print out full URL
        String URL = builder.build().toString();
        //Log.d(TAG, "URL[" + URL + ")");

        return( URL );
    }

    /**
     *
     * Function to build Movie Review URL
     * Sample URL : https://api.themoviedb.org/3/movie/76341/reviews?api_key=<Your TMDB key>
     *
     **/

    public static String buildMovieReviewsURL(long movieID) {
        // build up URL
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendPath("reviews")
                .appendQueryParameter("api_key", Constants.TMDB_API_KEY);

        // print out full URL
        String URL = builder.build().toString();
        Log.d(TAG, "URL[" + URL + ")");

        return( URL );
    }
}
