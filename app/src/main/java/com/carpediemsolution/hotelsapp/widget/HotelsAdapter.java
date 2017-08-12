package com.carpediemsolution.hotelsapp.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carpediemsolution.hotelsapp.R;
import com.carpediemsolution.hotelsapp.activity.HotelDetailsActivity;
import com.carpediemsolution.hotelsapp.model.Hotel;

import java.util.List;


/**
 * Created by Юлия on 24.07.2017.
 */

public class HotelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private Hotel mHotel;
    private Context mContext;
    private List<Hotel> mHotels;

    public HotelsAdapter(Context context) {
        this.mContext = context;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mAdressTextView;
        private TextView mStarsTextView;
        private TextView mDistanceTextView;
        private TextView mSuitesTextView;

        private TaskHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.name_item_text_view);
            mAdressTextView = (TextView) itemView.findViewById(R.id.adress_item_text_view);
            mStarsTextView = (TextView) itemView.findViewById(R.id.stars_item_text_view);
            mDistanceTextView = (TextView) itemView.findViewById(R.id.distance_item_text_view);
            mSuitesTextView = (TextView) itemView.findViewById(R.id.suites_item_text_view);

            itemView.setOnClickListener(this);
        }

        //set OnClickListener on TaskHolder to open hotel's details
        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            mHotel = mHotels.get(position);
            Intent intent = new Intent(mContext, HotelDetailsActivity.class);
            intent.putExtra("id", String.valueOf(mHotel.getmId()));
            mContext.startActivity(intent);
        }
    }

    //if list size = 0, return empty view
    @Override
    public int getItemViewType(int position) {
        if (mHotels.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return mHotels.size() > 0 ? mHotels.size() : 1;
    }

    public void setHotels(List<Hotel> hotels) {
        mHotels = hotels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            return new EmptyViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new TaskHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, int position) {
        if (vho instanceof TaskHolder) {
            TaskHolder vh = (TaskHolder) vho;
            mHotel = mHotels.get(position);

            vh.mNameTextView.setText(mHotel.getmName());
            vh.mAdressTextView.setText(mHotel.getmAdress());
            vh.mStarsTextView.setText(String.valueOf(mHotel.getmStars()) + " " + mContext.getString(R.string.stars));
            vh.mDistanceTextView.setText(mContext.getString(R.string.distance) + " " + String.valueOf(mHotel.getmDistance()));
            vh.mSuitesTextView.setText(mContext.getString(R.string.suites_available) + " " + String.valueOf(mHotel.getmSuitesAvailable()));
        }
    }
}

