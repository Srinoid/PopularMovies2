package com.srinumallidi.popularmovies.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.srinumallidi.popularmovies.R;
import com.srinumallidi.popularmovies.data.Movie;
import com.srinumallidi.popularmovies.data.MovieDetails;
import com.srinumallidi.popularmovies.data.ParseMovieData;
import com.srinumallidi.popularmovies.interfaces.OnTaskCompleted;
import com.srinumallidi.popularmovies.tasks.FetchMovieDetails;
import com.srinumallidi.popularmovies.utils.Constants;
import com.srinumallidi.popularmovies.utils.DBHelper;
import com.srinumallidi.popularmovies.utils.URLHelper;

import org.json.JSONException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements OnTaskCompleted<String> {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private static final String CURRENT_MOVIE = TAG + ".CURRENT.MOVIE";

    private Activity mActivity;
    private View mView,mView1;
    private Intent mIntent;
    private boolean mIsLargeDevice;

    private String IMAGE_SIZE;

    private String mApiKey;
    private String mMovieId;
    private List<MovieDetails> mMovieDetails;

    private Movie mMovie;

    URLHelper mURLHelper = new URLHelper();
    DBHelper mDBHelper;

    @InjectView(R.id.posterImageView)
    ImageView mPosterImageView;
    @InjectView(R.id.titleTextView)
    TextView mTitleTextView;
    @InjectView(R.id.releaseDateTextView)
    TextView mReleaseDateTextView;
    @InjectView(R.id.ratingTextView)
    TextView mRatingTextView;
    @InjectView(R.id.addToFavoritesImageView)
    ImageView mAddToFavoritesImageView;
    @InjectView(R.id.removeFromFavoritesImageView)
    ImageView mRemoveFromFavoritesImageView;
    @InjectView(R.id.synopsisDetailsTextView)
    TextView mSynopsisDetailsTextView;
    @InjectView(R.id.scrollView)
    ScrollView mScrollView;
    @InjectView(R.id.placeholderLayout)
    FrameLayout mPlaceholderFrameLayout;

    ViewGroup mTrailersContainer;
    ViewGroup mReviewsContainer;

    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        this.mActivity = getActivity();
        this.mIntent = mActivity.getIntent();
        setHasOptionsMenu(true);
        setRetainInstance(true);

        ButterKnife.inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreateView()");

        mDBHelper = new DBHelper(this.mActivity);
        mView = inflater.inflate(R.layout.fragment_movie_details, container, false);


        Bundle bundle = getArguments();

        if (bundle != null) {
            Log.d(TAG, "Bundle is not null!");
            mMovie = (Movie) bundle.getParcelable("movie");
            mIsLargeDevice = true;
        }
        else {
            mMovie = getActivity().getIntent().getParcelableExtra("movie");
            mIsLargeDevice = false;
        }

        if (mMovie == null) {
            Log.d(TAG, "Large screen device detected.");

            mScrollView = (ScrollView) mView.findViewById(R.id.scrollView);
            mTitleTextView = (TextView) mView.findViewById(R.id.titleTextView);

            mScrollView.setVisibility(View.GONE);
            mTitleTextView.setVisibility(View.GONE);

            return mView;
        }
        else {
            mPlaceholderFrameLayout = (FrameLayout) mView.findViewById(R.id.placeholderLayout);
            mScrollView = (ScrollView) mView.findViewById(R.id.scrollView);
            mPosterImageView = (ImageView) mView.findViewById(R.id.posterImageView);
            mTitleTextView = (TextView) mView.findViewById(R.id.titleTextView);
            mReleaseDateTextView = (TextView) mView.findViewById(R.id.releaseDateTextView);
            mRatingTextView = (TextView) mView.findViewById(R.id.ratingTextView);
            mAddToFavoritesImageView = (ImageView) mView.findViewById(R.id.addToFavoritesImageView);
            mRemoveFromFavoritesImageView = (ImageView) mView.findViewById(R.id.removeFromFavoritesImageView);
            mSynopsisDetailsTextView = (TextView) mView.findViewById(R.id.synopsisDetailsTextView);

            /*
            Use the SearchDetails AsyncTask to get movie trailer info
            The results are parsed in the onTaskCompleted method below
            */
            mMovieId = mMovie.getMovieId() + "";
            mApiKey = Constants.TMDB_API_KEY;

            FetchMovieDetails searchDetails = new FetchMovieDetails(this);
            searchDetails.execute(mMovieId, mApiKey);

            /*
            Populate various views using the Parcel items
            */
            if (mMovie != null) {

                /*
                Toggle the favorites image views based on what's in the database.
                */
                if (isMovieFavorited(mMovie.getMovieId())) {
                    mAddToFavoritesImageView.setVisibility(View.GONE);
                    mRemoveFromFavoritesImageView.setVisibility(View.VISIBLE);
                } else {
                    mRemoveFromFavoritesImageView.setVisibility(View.GONE);
                    mAddToFavoritesImageView.setVisibility(View.VISIBLE);
                }

                /*
                Add the movie to the favorites database
                */
                mAddToFavoritesImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        Add the movie to the database.
                        */
                        mDBHelper.addMovie(new Movie(
                                mMovie.getMovieId(),
                                mMovie.getOriginalTitle(),
                                mMovie.getSynopsis(),
                                mMovie.getReleaseDate(),
                                mMovie.getRating(),
                                mMovie.getPoster(),
                                mMovie.getTrailerKey()));

                        /*
                        Toggle the favorites image views
                        */
                        mAddToFavoritesImageView.setVisibility(View.GONE);
                        mRemoveFromFavoritesImageView.setVisibility(View.VISIBLE);

                        Toast.makeText(mActivity, "Added " + mMovie.getOriginalTitle() + " to favorites", Toast.LENGTH_LONG).show();
                    }
                });

                /*
                Remove the movie from the favorites database
                */
                mRemoveFromFavoritesImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        Remove the movie from the database
                        */
                        mDBHelper.deleteMovie(mMovie);

                        /*
                        Toggle the favorites image views
                        */
                        mRemoveFromFavoritesImageView.setVisibility(View.GONE);
                        mAddToFavoritesImageView.setVisibility(View.VISIBLE);

                        Toast.makeText(mActivity, "Removed " + mMovie.getOriginalTitle() + " from favorites", Toast.LENGTH_LONG).show();
                    }
                });
            }
            try {
                updateUI();
            }
            catch (Exception e) {
                Log.d(TAG, e + "");
            }
        }
        return mView;
    }

    private void updateUI() {
        Log.d(TAG, "updateUI()");

        Log.d(TAG, "mIsLargeDevice == " + mIsLargeDevice);

        if (mIsLargeDevice) {
            IMAGE_SIZE = "w300";
        }
        else {
            IMAGE_SIZE = "w342";
        }

        mPlaceholderFrameLayout.setVisibility(View.GONE);

        mTitleTextView.setText(mMovie.getOriginalTitle());
        mSynopsisDetailsTextView.setText(mMovie.getSynopsis());
        Picasso.with(getActivity())
                .load(mURLHelper.buildImageURL(mMovie.getPoster(), IMAGE_SIZE))
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.movie_poster_not_available)
                .into(mPosterImageView);
        mReleaseDateTextView.setText("Release date:\n" + mMovie.getReleaseDate());
        mRatingTextView.setText("Rating:\n" + mMovie.getRating() + "/10");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(CURRENT_MOVIE, mMovie);
    }

    @Override
    public void onTaskCompleted(String result) {

        Log.d(TAG, "onTaskCompleted()");

        String trailerJSONString = result;

        /*
        Parse the JSON that was returned by the SearchDetails AsyncTask and
        add TextViews dynamically
        */
        if (result != null) {

            try {
                mReviewsContainer = (ViewGroup) getActivity().findViewById(R.id.reviewsContainer);
                mTrailersContainer = (ViewGroup) getActivity().findViewById(R.id.trailersContainer);
            } catch (Exception e) {
                Log.d(TAG, e + "");
            }

            ParseMovieData parseMovieData = new ParseMovieData();

            try {
                mMovieDetails = parseMovieData.getMovieDetailsFromJson(trailerJSONString);

                /*
                Dynamically create the textViews required to display the Trailers
                */
                int prevId = 0;

                for (int i=0; i<mMovieDetails.size(); i++) {

                    int curId = prevId + 1;
                    final int finalI = i;

                    if ( mMovieDetails.get(i).getTrailerName() != null ) {
                        Button textView = new Button(mActivity);
                        textView.setText(mMovieDetails.get(i).getTrailerName());
                        textView.setPadding(50, 0, 50, 0);
                        textView.setTextSize(15);
                        textView.setTextColor(getResources().getColor(R.color.colorWhite));
                        textView.setBackgroundResource(R.drawable.play_movies);
                        textView.setId(curId);
                        textView.setTag(mMovieDetails.get(i).getTrailerKey());
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "onClick()");
                                playTrailer(mMovieDetails.get(finalI).getTrailerKey());
                            }
                        });

                        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );


                        params.addRule(RelativeLayout.BELOW, prevId);
                        params.rightMargin=10 ;//what is your desired y coordinate
                        params.leftMargin= 10; //what is your desired x coordinate

                        textView.setLayoutParams(params);

                        prevId = curId;
                        mTrailersContainer.addView(textView, params);

                    }
                }

                /*
                Dynamically create the textViews required to display the reviews
                */
                for (int i=0; i<mMovieDetails.size(); i++) {

                    int curId = prevId + 1;

                    if (mMovieDetails.get(i).getReviewContent() != null) {
                        TextView textView = new TextView(mActivity);
                        textView.setText(mMovieDetails.get(i).getReviewAuthor() + ":\n" + mMovieDetails.get(i).getReviewContent());
                        textView.setPadding(0, 10, 0, 10);
                        textView.setTextSize(15);
                        textView.setId(curId);

                        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );

                        params.addRule(RelativeLayout.BELOW, prevId);
                        textView.setLayoutParams(params);

                        prevId = curId;
                        mReviewsContainer.addView(textView, params);
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError() {
    }

    public boolean isMovieFavorited(int id) {
        if (mDBHelper.isMovieInDatabase(id)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void playTrailer(String trailer) {
        String url = "http://www.youtube.com/watch?v=" + trailer;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void setIsLargeDevice(boolean tablet) {
        mIsLargeDevice = true;
    }
}
