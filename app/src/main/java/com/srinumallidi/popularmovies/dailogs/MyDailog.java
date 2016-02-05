package com.srinumallidi.popularmovies.dailogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.srinumallidi.popularmovies.DetailActivity;
import com.srinumallidi.popularmovies.R;

/**
 * Created by Srinu Mallidi.
 */
public class MyDailog {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog mAlertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        mAlertDialog.setTitle(title);

        // Setting Dialog Message
        mAlertDialog.setMessage(message);

        // Setting alert dialog icon
        mAlertDialog.setIcon(R.drawable.fail);
        mAlertDialog.setCanceledOnTouchOutside(false);

        // Setting OK Button
        mAlertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        mAlertDialog.show();
    }
}
