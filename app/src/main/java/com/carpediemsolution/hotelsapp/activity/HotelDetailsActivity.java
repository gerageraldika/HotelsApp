package com.carpediemsolution.hotelsapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediemsolution.hotelsapp.App;
import com.carpediemsolution.hotelsapp.R;
import com.carpediemsolution.hotelsapp.api.WebApi;
import com.carpediemsolution.hotelsapp.model.Hotel;
import com.carpediemsolution.hotelsapp.model.HotelLatLng;
import com.carpediemsolution.hotelsapp.utils.ImageTransform;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Юлия on 24.07.2017.
 */

public class HotelDetailsActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String LOG_TAG = "HotelDetailsActivity";
    private static final String imagePath = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/";

    @BindView(R.id.hotel_name)
    TextView mNameTextView;
    @BindView(R.id.hotel_adress)
    TextView mAdressTextView;
    @BindView(R.id.hotel_stars)
    TextView mStarsTextView;
    @BindView(R.id.hotel_distance)
    TextView mDistTextView;
    @BindView(R.id.hotel_suites)
    TextView mSuitesTextView;
    @BindView(R.id.hotel_image)
    ImageView mHotelImageView;

    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoteldetails);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String id = "";
        if (bundle != null)
            id = bundle.getString("id");

        final WebApi webApi = App.getWebApi();

        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);

        //rx download hotel coordinates
        Observable<HotelLatLng> hotelCoordinatesbservable = webApi.getHotelCoordinates(id);
        hotelCoordinatesbservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotelLatLng>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError ", e.toString());
                        Toast.makeText(HotelDetailsActivity.this,
                                getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(HotelLatLng messages) {
                        Log.d("onNext ", messages.toString());
                        lat = messages.getmLat();
                        lon = messages.getmLon();
                        mapFragment.getMapAsync(HotelDetailsActivity.this);
                    }
                });
//rx download hotel details
        Observable<Hotel> observable = webApi.getHotelDetails(id);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Hotel>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("onError ", e.toString());
                        Toast.makeText(HotelDetailsActivity.this,
                                getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Hotel messages) {
                        Log.d("onNext ", messages.toString());
                        mNameTextView.setText(messages.getmName());
                        mAdressTextView.setText(messages.getmAdress());
                        mStarsTextView.setText(String.valueOf(messages.getmStars()) + " " + getString(R.string.stars));
                        mDistTextView.setText(getString(R.string.distance) + " " + String.valueOf(messages.getmDistance()));
                        mSuitesTextView.setText(getString(R.string.suites_available) + " " + String.valueOf(messages.getmSuitesAvailable()));

                        if (messages.getmImageNumber().isEmpty()) {
                            mHotelImageView.setImageDrawable(getResources().getDrawable(R.drawable.net_foto));
                        } else {
                            String s = imagePath + messages.getmImageNumber();
                            loadHotelImage(mHotelImageView, s);
                            //if image not found
                        }
                    }
                });
    }

    //load image by path from json file
    public static void loadHotelImage(@NonNull ImageView imageView, @NonNull String imagePath) {
        Picasso.with(imageView.getContext())
                .load(imagePath)
                .transform(new ImageTransform())
                .error(R.drawable.net_foto)
                .noFade() //отменить эффект затухания
                .into(imageView);
        Log.d(LOG_TAG, imagePath);
    }

    //load google map, insert hotel coordinates from json file
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coordinates = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title(""));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(coordinates.latitude, coordinates.longitude), 15));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //
    }
}
