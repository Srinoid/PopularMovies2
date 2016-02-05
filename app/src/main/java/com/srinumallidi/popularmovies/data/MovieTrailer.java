package com.srinumallidi.popularmovies.data;

/**
 *  Created by Srinu Mallidi
 */
public class MovieTrailer {

    private String mMovieId;
    private String mTrailerKey;
    private String mTrailerName;
    private String mTrailerSite;
    private int mTrailerSize;
    private String mTrailerType;

    public MovieTrailer() {}

    public MovieTrailer(String name) {
        this.mTrailerName = name;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
    }

    public String getTrailerName() {
        return mTrailerName;
    }

    public void setTrailerName(String trailerName) {
        mTrailerName = trailerName;
    }

    public String getTrailerSite() {
        return mTrailerSite;
    }

    public void setTrailerSite(String trailerSite) {
        mTrailerSite = trailerSite;
    }

    public int getTrailerSize() {
        return mTrailerSize;
    }

    public void setTrailerSize(int trailerSize) {
        mTrailerSize = trailerSize;
    }

    public String getTrailerType() {
        return mTrailerType;
    }

    public void setTrailerType(String trailerType) {
        mTrailerType = trailerType;
    }
}
