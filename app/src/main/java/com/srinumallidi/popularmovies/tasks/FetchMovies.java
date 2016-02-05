package com.srinumallidi.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.srinumallidi.popularmovies.data.Movie;
import com.srinumallidi.popularmovies.data.ParseMovieData;
import com.srinumallidi.popularmovies.interfaces.OnTaskCompleted;
import com.srinumallidi.popularmovies.utils.HTTPUtil;
import com.srinumallidi.popularmovies.utils.URLHelper;

import java.util.List;


/**
 * Created by Srinu Mallidi.
 */
public class FetchMovies extends AsyncTask<ArrayAdapter<Movie>, Void, List<Movie>> {

    public static final String TAG = FetchMovies.class.getSimpleName();

    private ArrayAdapter mArrayAdapter;
    private String mSearchOrder;
    private int mPage;
    private OnTaskCompleted<List<Movie>> delegate;

    public FetchMovies(OnTaskCompleted<List<Movie>> delegate, String searchOrder, int page) {
        this.delegate = delegate;
        this.mSearchOrder = searchOrder;
        this.mPage = page;
    }

    /*
    doInbackground is executed first via the .execute() method in the main activity.
    */
    @Override
    protected List<Movie> doInBackground(ArrayAdapter... params) {

        if (params.length == 0) {
            return null;
        }

        mArrayAdapter = params[0];

        try {
            String json = HTTPUtil.GET(URLHelper.buildMovieSearchURL(mSearchOrder, mPage));
            List<Movie> movies = ParseMovieData.getMovieListFromJSON(json);
            return movies;
        }
        catch (Exception e) {
            Log.e(TAG, "Failed to find movies");
        }
        return null;
    }

    /*
    onPostExecute is called by doInBackground(). This method delegates the
    task back to the main activity via the OnTaskCompleted interface
    */
    @Override
    protected void onPostExecute(List<Movie> result) {
        super.onPostExecute(result);

        if (result == null) {
            this.delegate.onError();
            return;
        }

        /*
         Let the activity know that we are done
        */
        this.delegate.onTaskCompleted(result);
    }
}
