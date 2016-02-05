package com.srinumallidi.popularmovies.data;

/**
 *  Created by Srinu Mallidi
 */
public class MovieReview {

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewURL;

    public MovieReview() {}

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
}
