package com.carpediemsolution.hotelsapp.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.carpediemsolution.hotelsapp.App;
import com.carpediemsolution.hotelsapp.R;
import com.carpediemsolution.hotelsapp.api.WebApi;
import com.carpediemsolution.hotelsapp.model.Hotel;
import com.carpediemsolution.hotelsapp.utils.Preferences;
import com.carpediemsolution.hotelsapp.utils.SortUtils;
import com.carpediemsolution.hotelsapp.widget.DialogScreen;
import com.carpediemsolution.hotelsapp.widget.HotelsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Юлия on 24.07.2017.
 */

public class HotelsActivity extends AppCompatActivity implements DialogScreen.EditDialogListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "HotelsActivity";
    private List<Hotel> hotelList;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private HotelsAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    private SharedPreferences prefs;
    private String sortParams;
    private Observable<List<Hotel>> observable;
    private SortUtils sortUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        ButterKnife.bind(this);

        sortUtils = new SortUtils();
        hotelList = new ArrayList<>();

        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(HotelsActivity.this));
        mAdapter = new HotelsAdapter(HotelsActivity.this);
        mAdapter.setHotels(hotelList);
        mRecyclerView.setAdapter(mAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        mProgressDialog = new ProgressDialog(HotelsActivity.this, R.style.AppCompatAlertDialogStyle);
        else
            mProgressDialog = new ProgressDialog(HotelsActivity.this, R.style.AlertDialog_Holo);

        setProgressDialogParams(mProgressDialog);
        mProgressDialog.show();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorGrey,
                R.color.colorPrimaryDark,
                R.color.colorBlack,
                R.color.colorOneMoreGrey);

        prefs = PreferenceManager.getDefaultSharedPreferences(HotelsActivity.this);
        sortParams = prefs.getString(Preferences.PREFERENCE, "");
        loadHotels(sortParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort) {
            //go to sort settings
            DialogFragment newFragment = DialogScreen.newInstance();
            newFragment.show(getSupportFragmentManager(), "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //initialize observable
    public Observable<List<Hotel>> getObservable() {
        final WebApi webApi = App.getWebApi();
        observable = webApi.getHotels();
        return observable;
    }

    //rx download list of hotels, add messages in recyclerview
    public void loadHotels(final String params) {
        observable = getObservable();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Hotel>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "");
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError ", e.toString());
                        Toast.makeText(HotelsActivity.this,
                                getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onNext(List<Hotel> hotels) {
                        Log.d("onNext ", hotels.toString());
                        //test progressDialog
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        hotels = sortUtils.sortByParams(params, hotels);

                        mAdapter.setHotels(hotels);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    //update chosen sort settings
    @Override
    public void updateResult(String params) {
        Log.d(LOG_TAG, "get data from dialog");
        prefs = PreferenceManager.getDefaultSharedPreferences(HotelsActivity.this);
        sortParams = prefs.getString(Preferences.PREFERENCE, "");
        loadHotels(sortParams);
    }

    //set params to ProgressDialog
    public void setProgressDialogParams(ProgressDialog progressDialog) {
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onRefresh() {
        loadHotels(sortParams);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class MathUtils {
        public double average(int a, int b) {

            return a + b / 2;
        }


    }
}
