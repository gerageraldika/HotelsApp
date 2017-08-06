package com.carpediemsolution.hotelsapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Юлия on 24.07.2017.
 */

public class Hotel {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("address")
    private String mAdress;

    @SerializedName("stars")
    private double mStars;

    @SerializedName("distance")
    private double mDistance;

    @SerializedName("suites_availability")
    private String mSuitesAvailability;

    @SerializedName("image")
    private String mImageNumber;

    public Hotel() {

    }

    public int getmSuitesAvailable() {
        return mSuitesAvailability.split(":").length;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmAdress() {
        return mAdress;
    }

    public double getmStars() {
        return mStars;
    }

    public double getmDistance() {
        return mDistance;
    }

    public String getmSuitesAvailability() {
        return mSuitesAvailability;
    }

    public String getmImageNumber() {
        return mImageNumber;
    }


    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
    }

    public void setmStars(double mStars) {
        this.mStars = mStars;
    }

    public void setmDistance(double mDistance) {
        this.mDistance = mDistance;
    }

    public void setmSuitesAvailability(String mSuitesAvailability) {
        this.mSuitesAvailability = mSuitesAvailability;
    }

    public void setmImageNumber(String mImageNumber) {
        this.mImageNumber = mImageNumber;
    }

}
