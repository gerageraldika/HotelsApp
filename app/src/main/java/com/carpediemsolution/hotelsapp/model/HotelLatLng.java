package com.carpediemsolution.hotelsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Юлия on 25.07.2017.
 */

public class HotelLatLng {

    @SerializedName("lat")
    private double mLat;

    @SerializedName("lon")
    private double mLon;

    public double getmLat() {
        return mLat;
    }

    public double getmLon() {
        return mLon;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public void setmLon(double mLon) {
        this.mLon = mLon;
    }
}
