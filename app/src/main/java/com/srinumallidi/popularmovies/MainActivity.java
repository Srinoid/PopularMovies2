package com.srinumallidi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.srinumallidi.popularmovies.dailogs.MyDailog;
import com.srinumallidi.popularmovies.data.Movie;
import com.srinumallidi.popularmovies.fragments.MovieDetailsFragment;
import com.srinumallidi.popularmovies.interfaces.OnMovieItemSelected;
import com.srinumallidi.popularmovies.utils.NetworkConnectionDetector;

/**
 * Created by Srinu Mallidi.
 */

public class MainActivity extends AppCompatActivity implements OnMovieItemSelected {
    public static final String TAG = MainActivity.class.getSimpleName();


    private static final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG";
    private static final String STATE_LARGE_DEVICE = TAG + ".LARGE.DEVICE";
    private boolean mIsLargeDevice = false;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Network Connection detector class
    NetworkConnectionDetector mNetworkConnectionDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //also displays wide logo


        if (savedInstanceState != null) {
            mIsLargeDevice = savedInstanceState.getBoolean(STATE_LARGE_DEVICE);
        }
        // creating connection detector class instance
        mNetworkConnectionDetector = new NetworkConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = mNetworkConnectionDetector.isConnectingToInternet();

        // check for Internet status
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            MyDailog mMyDailog = new MyDailog();
            mMyDailog.showAlertDialog(MainActivity.this, "No Internet Connection",
                    "Seems internet connection is not available .Please enable Wi-Fi or Mobile Data for Popular Movies", false);
        }
        /*
        Check if this is a large device. != null means that the fragment_detail-large/activity-main
        fragment_detail has been loaded- we are using a large screen device such as a tablet.
        */
        if (findViewById(R.id.layout_detail_fragment) != null) {
            this.mIsLargeDevice = true;

            MovieDetailsFragment movieDetailsFragment =
                    (MovieDetailsFragment) getSupportFragmentManager()
                            .findFragmentByTag(DETAIL_FRAGMENT_TAG);

            if (movieDetailsFragment == null) {
                movieDetailsFragment = new MovieDetailsFragment();
                movieDetailsFragment.setIsLargeDevice(true);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.layout_detail_fragment, movieDetailsFragment, DETAIL_FRAGMENT_TAG)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_detail_fragment, movieDetailsFragment, DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            this.startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(Movie movie) {

        if (mIsLargeDevice) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", movie);

            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            movieDetailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_detail_fragment, movieDetailsFragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        }
        else {
            Log.d(TAG, "This must be a phone.");
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_LARGE_DEVICE, mIsLargeDevice);

    }

}
