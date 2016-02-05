package com.srinumallidi.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Srinu Mallidi.
 */

public class Movie implements Parcelable{
    /*
         MovieDetails  variables
    */
    private String mOriginalTitle;
    private String mPoster;
    private String mSynopsis;
    private String mBackdropPath;
    private Double mRating;
    private Double mPopularity;
    private String mReleaseDate;

    /*
     MovieTrailer  variables
    */
    private int mMovieId;
    private String mTrailerKey;
    private String mTrailerName;
    private String mTrailerSite;
    private int mTrailerSize;
    private String mTrailerType;

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public long getPopularity() {
        return Math.round(mPopularity);
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    /*
    MovieTrailer getters and setters
    */
    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
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

    public String getTrailerName() {
        return mTrailerName;
    }

    public void setTrailerName(String trailerName) {
        mTrailerName = trailerName;
    }

    public String getTrailerType() {
        return mTrailerType;
    }

    public void setTrailerType(String trailerType) {
        mTrailerType = trailerType;
    }

    public Movie() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    Storing Movie data to Parcel object
    */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getMovieId());
        dest.writeString(this.getOriginalTitle());
        dest.writeString(this.getSynopsis());
        dest.writeString(this.getReleaseDate());
        dest.writeDouble(this.getRating());
        dest.writeString(this.getPoster());
        dest.writeString(this.getTrailerKey());
    }

    /*
    A constructor that initializes the Movie object
    */
    public Movie(Integer id,
                 String originalTitle,
                 String synopsis,
                 String releaseDate,
                 Double rating,
                 String poster,
                 String trailerKey) {

        this.mMovieId = id;
        this.mOriginalTitle = originalTitle;
        this.mSynopsis = synopsis;
        this.mReleaseDate = releaseDate;
        this.mRating = rating;
        this.mPoster = poster;
        this.mTrailerKey = trailerKey;
    }

    /*
    Retrieving Parcel data from the Movie object
    This constructor is invoked by the method createFromParcel(Parcel source)
    of the object CREATOR
    */
    private Movie(Parcel in) {
        this.mMovieId = in.readInt();
        this.mOriginalTitle = in.readString();
        this.mSynopsis = in.readString();
        this.mReleaseDate = in.readString();
        this.mRating = in.readDouble();
        this.mPoster = in.readString();
        this.mTrailerKey = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}