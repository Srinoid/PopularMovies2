package com.srinumallidi.popularmovies;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.srinumallidi.popularmovies.dailogs.MyDailog;
import com.srinumallidi.popularmovies.utils.NetworkConnectionDetector;

/**
 * Created by Srinu Mallidi.
 */

public class DetailActivity extends AppCompatActivity {
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Network Connection detector class
    NetworkConnectionDetector mNetworkConnectionDetector;


    private static final String TAG = DetailActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // creating connection detector class instance
        mNetworkConnectionDetector = new NetworkConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = mNetworkConnectionDetector.isConnectingToInternet();

        // check for Internet status
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            MyDailog mMyDailog = new MyDailog();
            mMyDailog.showAlertDialog(DetailActivity.this, "No Internet Connection",
                    "Seems internet connection is not available .Please enable Wi-Fi or Mobile Data for Popular Movies", false);
        }
        Log.d(TAG, "onCreate() Called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
