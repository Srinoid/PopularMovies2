package com.srinumallidi.popularmovies.interfaces;

/**
 * Created by Srinu Mallidi.
 */
public interface OnTaskCompleted<E> {
    // Called from task when completed
    //void onTaskCompleted(String event);
    public void onTaskCompleted(E event);

    // Called from task when an error is encountered
    public void onError();
}
