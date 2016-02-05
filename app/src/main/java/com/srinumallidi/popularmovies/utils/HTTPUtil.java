package com.srinumallidi.popularmovies.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Srinu Mallidi.
 */
public class HTTPUtil {
    private static final String TAG = HTTPUtil.class.getSimpleName();

    public static String GET(String URI) throws EOFException, IOException {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        //String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL( URI );

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod( "GET" );
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if( inputStream == null ) {
                // Nothing to do.
                throw new EOFException( "Failed to obtain input stream!" );
            }
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

            String line;
            while( (line = reader.readLine()) != null ) {
                buffer.append( line + "\n" );
            }

            if( buffer.length() == 0 ) {
                throw new EOFException( "No data returned!" );
            }

            String data = buffer.toString();
            Log.d(TAG, "Data[" + data + "]");
            return( data );
        }
        catch(Throwable e) {
            //Log.e(TAG, "Error", e );
            e.printStackTrace();
            throw e;
        }
        finally {
            if( urlConnection != null) {
                urlConnection.disconnect();
            }
            if( reader != null ) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
    }
}