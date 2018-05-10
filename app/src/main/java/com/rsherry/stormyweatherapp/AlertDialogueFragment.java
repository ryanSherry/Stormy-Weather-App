package com.rsherry.stormyweatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.rsherry.stormyweatherapp.R;

public class AlertDialogueFragment extends DialogFragment {
    //Supply keys for bundle
    public static final String TITLE_ID = "title";
    public static final String MESSAGE_ID = "message";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get supplied title and message
        Bundle messages = getArguments();

        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.error_button_ok_text,null);

        if(messages != null) {

            builder.setTitle(messages.getString(TITLE_ID, "Sorry"))
                    .setMessage(messages.getString(MESSAGE_ID, "There was an error."));
        } else {
            builder.setTitle("Sorry")
                    .setMessage("There was an error.");
        }

        return builder.create();
    }
}
