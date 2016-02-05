package com.srinumallidi.popularmovies.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srinu Mallidi.
 */
public class MovieDetails {
    private long mMovieId;
    private long mDuration;
    private List<MovieTrailer> mTrailers = new ArrayList<MovieTrailer>();
    private List<MovieReview> mReviews = new ArrayList<MovieReview>();

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewURL;

    private String mTrailerKey;
    private String mTrailerName;
    private String mTrailerSite;
    private int mTrailerSize;
    private String mTrailerType;

    public long getMovieId() {
        return mMovieId;
    }

    public void setMovieId(long movieId) {
        mMovieId = movieId;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public List<MovieTrailer> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<MovieTrailer> trailers) {
        mTrailers = trailers;
    }

    public List<MovieReview> getReviews() {
        return mReviews;
    }

    public void setReviews(List<MovieReview> reviews) {
        mReviews = reviews;
    }

    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId(String reviewId) {
        mReviewId = reviewId;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        mReviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewURL() {
        return mReviewURL;
    }

    public void setReviewURL(String reviewURL) {
        mReviewURL = reviewURL;
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
