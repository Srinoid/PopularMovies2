package com.srinumallidi.popularmovies.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.srinumallidi.popularmovies.data.Movie;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Srinu Mallidi.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "favorites.db";
    public static final String TABLE_NAME = "movies";
    public static final String COL_ORIGINAL_TITLE = "original_title";
    public static final String COL_SYNOPSIS = "overview";
    public static final String COL_RELEASE_DATE = "release_date";
    public static final String COL_RATING = "vote_average";
    public static final String COL_POSTER = "poster_path";
    public static final String COL_MOVIE_ID = "id";
    public static final String COL_TRAILER_KEY = "trailer_key";

    private static final String[] COLUMNS = {
            COL_ORIGINAL_TITLE,
            COL_SYNOPSIS,
            COL_RELEASE_DATE,
            COL_RATING,
            COL_POSTER,
            COL_MOVIE_ID,
            COL_TRAILER_KEY };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d(TAG, "Inside DBHelper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + " " + TABLE_NAME + " (" +
                        COL_MOVIE_ID + " INTEGER PRIMARY KEY," +
                        COL_ORIGINAL_TITLE + " TEXT," +
                        COL_SYNOPSIS + " TEXT," +
                        COL_RELEASE_DATE + " TEXT," +
                        COL_RATING + " DOUBLE," +
                        COL_POSTER + " TEXT," +
                        COL_TRAILER_KEY + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading the database...");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        Log.d(TAG, "Adding: " + movie.getOriginalTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("original_title", movie.getOriginalTitle());
        contentValues.put("overview", movie.getSynopsis());
        contentValues.put("release_date", movie.getReleaseDate());
        contentValues.put("vote_average", movie.getRating());
        contentValues.put("poster_path", movie.getPoster());
        contentValues.put("id", movie.getMovieId());
        contentValues.put("trailer_key", movie.getTrailerKey());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Movie getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(cursor.getString(0)));
        movie.setOriginalTitle(cursor.getString(1));
        movie.setSynopsis(cursor.getString(2));
        movie.setReleaseDate(cursor.getString(3));
        movie.setRating(Double.parseDouble(cursor.getString(4)));
        movie.setPoster(cursor.getString(5));

        return movie;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new LinkedList<Movie>();

        String q = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);

        Movie movie = null;

        if (cursor.moveToFirst()) {
            do {
                movie = new Movie();
                movie.setMovieId(Integer.parseInt(cursor.getString(0)));
                movie.setOriginalTitle(cursor.getString(1));
                movie.setSynopsis(cursor.getString(2));
                movie.setReleaseDate(cursor.getString(3));
                movie.setRating(Double.parseDouble(cursor.getString(4)));
                movie.setPoster(cursor.getString(5));

                movies.add(movie);
            }
            while (cursor.moveToNext());
        }

        return movies;
    }

    public void deleteMovie(Movie movie) {

        /*
        Get reference to writable database
        */
        SQLiteDatabase db = this.getWritableDatabase();

        /*
        Delete
        */
        db.delete(TABLE_NAME,
                COL_MOVIE_ID + " = ?",
                new String[] {String.valueOf(movie.getMovieId())}
        );

        /*
        Close
        */
        db.close();

        Log.d(TAG, "deleteMovie([" + movie.toString() + "])");
    }

    public boolean isMovieInDatabase(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        Log.d(TAG, "####### " + query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        else {
            cursor.close();
            return true;
        }
    }
}
