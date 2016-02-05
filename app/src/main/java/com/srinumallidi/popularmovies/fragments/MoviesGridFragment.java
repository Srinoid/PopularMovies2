package com.srinumallidi.popularmovies.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.srinumallidi.popularmovies.R;
import com.srinumallidi.popularmovies.adapters.MovieGridAdapter;
import com.srinumallidi.popularmovies.data.Movie;
import com.srinumallidi.popularmovies.interfaces.OnMovieItemSelected;
import com.srinumallidi.popularmovies.interfaces.OnTaskCompleted;
import com.srinumallidi.popularmovies.tasks.FetchMovies;
import com.srinumallidi.popularmovies.utils.Constants;
import com.srinumallidi.popularmovies.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Srinu Mallidi.
 */
public class MoviesGridFragment extends Fragment implements OnTaskCompleted<List<Movie>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MoviesGridFragment.class.getSimpleName();

    private static final String STATE_SORT = TAG + ".SORT";
    private static final String STATE_POSITION = TAG + ".POSITION";
    private static final String STATE_DATA = TAG + ".DATA";

    private String mSortOrder;

    private String mApiKey;
    private String mVoteCount;
    private Context mContext;
    private View mView;
    private ArrayList<Movie> mMovieList;
    private MovieGridAdapter mMovieGridAdapter;

    private int mCurrentPage = 1;
    private boolean mPreferencesHaveChanged;
    private DBHelper mDBHelper;

    static int index;

    /*
    Inject views using ButterKnife
    */
    @InjectView(R.id.gridView) GridView mGridView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.imageLoadingError) View mLoadingErrorView;

    public MoviesGridFragment() {
        Log.d(TAG, "########## Initializing MainActivityFragment()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        this.mContext = getActivity();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        ButterKnife.inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           Log.d(TAG, "onCreateView()");

            mView = inflater.inflate(R.layout.fragment_main, container, false);
            mGridView = (GridView) mView.findViewById(R.id.gridView);
            mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
            mLoadingErrorView = mView.findViewById(R.id.imageLoadingError);

            mGridView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mLoadingErrorView.setVisibility(View.GONE);

            // Handle savedInstanceState and loading of favorites here
            if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_DATA)) {
                Log.d(TAG, "savedInstanceState is null and does not contain key: " + STATE_DATA);
                mMovieList = new ArrayList<>();

                // Load favorites from the get-go if the preferences have been saved as such
                if (isfavorite()) {
                    Log.d(TAG, "Loading favorites...");

                    mDBHelper = new DBHelper(mContext);
                    List<Movie> movieList = mDBHelper.getAllMovies();
                    Log.d(TAG, "MovieList array size: " + movieList.size());

                    mMovieList = new ArrayList<Movie>(movieList);

                    mMovieGridAdapter = new MovieGridAdapter(getActivity(), R.layout.movies, mMovieList);
                    mGridView.setAdapter(mMovieGridAdapter);
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Movie movie = mMovieGridAdapter.getItem(position);
                            ((OnMovieItemSelected) getActivity()).onItemSelected(movie);
                        }
                    });

                    mProgressBar.setVisibility(View.GONE);
                    mGridView.setVisibility(View.VISIBLE);
                } else {
                    fetchMovies();
                    mProgressBar.setVisibility(View.GONE);
                    mGridView.setVisibility(View.VISIBLE);
                }
            } else {
                Log.d(TAG, "savedInstanceState is not null and contains key: " + STATE_DATA);
                mMovieList = savedInstanceState.getParcelableArrayList(STATE_DATA);

                mProgressBar.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
            }

            mMovieGridAdapter = new MovieGridAdapter(getActivity(), R.layout.movies, mMovieList);
            mGridView.setAdapter(mMovieGridAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovieGridAdapter.getItem(position);
                    ((OnMovieItemSelected) getActivity()).onItemSelected(movie);
                }
            });
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    String sortOrder = getSortOrder();

                    if (sortOrder.equals("favorites")) {
                        return;
                    }

                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        mCurrentPage++;
                        fetchMovies();
                    }
                }
            });

            PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
        return mView;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        mGridView.setSelection(index);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        index = mGridView.getFirstVisiblePosition();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        mMovieGridAdapter.clear();
        super.onDestroy();
    }

    /*
        Methods implemented by the OnTaskCompleted interface
        This method is executed AFTER the AsyncTask has done its work, unless it's being
        called by fetchMovies() to load movies from the database
        */
    @Override
    public void onTaskCompleted(List<Movie> result) {
        Log.d(TAG, "onTaskCompleted()");

        // Add items to the adapter
        for (Movie results : result) {
            mMovieGridAdapter.add(results);
        }
    }

    @Override
    public void onError() {
        mGridView.setVisibility(View.GONE);
        mLoadingErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState()");

        outState.putString(STATE_SORT, getSortOrder());
        outState.putInt(STATE_POSITION, mCurrentPage);
        outState.putParcelableArrayList(STATE_DATA, mMovieList);
    }

    private String getSortOrder() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSortOrder = sharedPreferences.getString("sortOrder", "");

        return mSortOrder;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "########## Preferences have changed.");
        mPreferencesHaveChanged = true;
        fetchMovies();
    }


    /*
    Check if we're trying to load up favorites
    */
    public boolean isfavorite() {
        String result = getSortOrder();

        if (result.equals("favorites")) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Method to kick off the SearchMovies AsyncTask or to build JSONArray from database
    */
    private void fetchMovies() {
        Log.d(TAG, "fetchMovies()");

        if (mPreferencesHaveChanged) {
            Log.d(TAG, "Preferences have changed. Clearing adapter and reloading movies.");
            mMovieGridAdapter.clear();
            mCurrentPage = 1;
            mPreferencesHaveChanged = false;
        }

        if (getSortOrder() != null) {

            String sortOrder = getSortOrder();
            Log.d(TAG, "sortOrder == " + sortOrder);



            if (isfavorite()) {
                Log.d(TAG, "Preferences set to \"favorites\". Loading favorites from database...");

                mMovieGridAdapter.clear();
                mDBHelper = new DBHelper(mContext);
                List<Movie> movieList = mDBHelper.getAllMovies();


                //Create a JSONArray from movieList and feed it to onTaskCompleted for parsing.
                onTaskCompleted(movieList);
            }
            else {
                Log.d(TAG, "Preferences set to " + sortOrder + ". Fetching movies via AsyncTask...");
                mApiKey = Constants.TMDB_API_KEY;
                mVoteCount = 1000 + "";

                new FetchMovies(this, getSortOrder(), mCurrentPage).execute(mMovieGridAdapter);
            }
        }
        else {
            Log.d(TAG, "getSortOrder() returns null!");
        }
    }

}
