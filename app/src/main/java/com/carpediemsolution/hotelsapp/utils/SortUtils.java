package com.carpediemsolution.hotelsapp.utils;

import android.util.Log;

import com.carpediemsolution.hotelsapp.model.Hotel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Юлия on 25.07.2017.
 */

public class SortUtils {
    //class for sorting hotels by params
    private static final String LOG_TAG = "SortUtils";

    public List<Hotel> sortByParams(String sortParams, List<Hotel> hotels) {
        if (sortParams.equals(Preferences.DISTANCE)) {
            sortByDistance(hotels);
            Log.d(LOG_TAG, " sortByDistance(hotels) " + hotels);
        } else if (sortParams.equals(Preferences.SUITES_AVAILABLE)) {
            sortBySuitesAvailable(hotels);
            Log.d(LOG_TAG, " sortBySuitesAvailable(hotels) " + hotels);
        } else
            Log.d(LOG_TAG, " no sorted hotels " + hotels);
        return hotels;
    }

    private List sortByDistance(List<Hotel> list) {

        Collections.sort(list, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                return Double.compare(o1.getmDistance(), o2.getmDistance());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        Log.d(LOG_TAG, " sortByDistance " + list);
        return list;
    }

    private List sortBySuitesAvailable(List<Hotel> list) {

        Collections.sort(list, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                return Integer.compare(o1.getmSuitesAvailable(), o2.getmSuitesAvailable());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        Log.d(LOG_TAG, " sortBySuitesAvailable " + list);
        return list;
    }
}
