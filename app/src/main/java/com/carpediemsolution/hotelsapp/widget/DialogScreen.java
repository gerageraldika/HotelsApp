package com.carpediemsolution.hotelsapp.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.carpediemsolution.hotelsapp.R;
import com.carpediemsolution.hotelsapp.utils.Preferences;

/**
 * Created by Юлия on 25.07.2017.
 */

public class DialogScreen extends DialogFragment {

    private static final String LOG_TAG = "DialogScreen";

    public static DialogScreen newInstance() {
        return new DialogScreen();
    }

    private int getItemChoised() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences
                (getActivity());

        String sortPrm = prefs.getString(Preferences.PREFERENCE, "");
        if (sortPrm.equals(Preferences.DISTANCE)) return 1;
        else if (sortPrm.equals(Preferences.SUITES_AVAILABLE)) return 2;
        else return 0;
    }

    //create AlertDialog
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] sortParams = {Preferences.DELAULT, Preferences.DISTANCE, Preferences.SUITES_AVAILABLE};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyTheme_Dark_Dialog);
        builder.setTitle(getString(R.string.sort_by)); // заголовок для диалога


        builder.setSingleChoiceItems(sortParams, getItemChoised(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences
                        (getActivity());

                prefs.edit().putString(Preferences.PREFERENCE, sortParams[item]).apply();

                EditDialogListener activity = (EditDialogListener) getActivity();
                activity.updateResult(sortParams[item]);
                Log.d(LOG_TAG, "---" + sortParams[item]);
            }
        });
        return builder.create();
    }

    public interface EditDialogListener {
        void updateResult(String inputText);
    }
}



