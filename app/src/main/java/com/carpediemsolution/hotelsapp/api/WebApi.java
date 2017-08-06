package com.carpediemsolution.hotelsapp.api;

import com.carpediemsolution.hotelsapp.model.Hotel;
import com.carpediemsolution.hotelsapp.model.HotelLatLng;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

import rx.Observable;


/**
 * Created by Юлия on 24.07.2017.
 */

public interface WebApi {

    //get list of hotels
    @GET("0777.json")
    Observable<List<Hotel>> getHotels();

    //  @GET("{page}.json")
    //  Observable<List<Hotel>> getHotels(@Path("page") String page);

    //get hotel details by id
    @GET("{id}.json")
    Observable<Hotel> getHotelDetails(@Path("id") String id);

    //get hotel coordinates by hotel id
    @GET("{id}.json")
    Observable<HotelLatLng> getHotelCoordinates(@Path("id") String id);
}
