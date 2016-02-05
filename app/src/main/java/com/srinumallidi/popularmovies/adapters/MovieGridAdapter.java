package com.srinumallidi.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srinumallidi.popularmovies.R;
import com.srinumallidi.popularmovies.data.Movie;
import com.srinumallidi.popularmovies.utils.URLHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Srinu Mallidi.
 */


public class MovieGridAdapter extends ArrayAdapter<Movie> {

    public static final String TAG = MovieGridAdapter.class.getSimpleName();

    Context mContext;
    private int mLayoutResourceId;
    private List<Movie> mMovies = new ArrayList<>();

    String mImageSize;

    public MovieGridAdapter(Context context, int layoutResourceId, List<Movie> movies) {

        super(context, layoutResourceId, movies);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = mMovies.get(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(URLHelper.buildImageURL(movie.getPoster(), mImageSize = "w185"))
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.movie_poster_not_available)
                .fit()
                .into(imageView);

        return convertView;
    }

    public List<Movie> getData() {
        return mMovies;
    }
}