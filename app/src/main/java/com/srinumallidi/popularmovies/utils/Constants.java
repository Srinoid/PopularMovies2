package com.srinumallidi.popularmovies.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.srinumallidi.popularmovies.BuildConfig;

/**
 * Created by Srinu Mallidi
 */
public class Constants {

    /*
       The names of the JSON objects that need to be extracted
    */

    static final public String TMDB_RESULTS = "results";
    static final public String TMDB_TITLE = "title";
    static final public String TMDB_OVERVIEW = "overview";
    static final public String TMDB_RELEASEDATE = "release_date";
    static final public String TMDB_POSTERPATH = "poster_path";
    static final public String TMDB_VOTEAVERAGE = "vote_average";
    static final public String TMDB_ID = "id";

    // TMDB Query URL Components
    static final public String TMDB_DISCOVER_MOVIES_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    static final public String TMDB_SORT_PARAM = "sort_by";
    static final public String TMDB_API_KEY_PARAM = "api_key";
    static final public String TMDB_API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;


}
