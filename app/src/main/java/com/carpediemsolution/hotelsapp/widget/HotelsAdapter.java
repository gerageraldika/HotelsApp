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
    private Hotel hotel;
    private Context context;
    private List<Hotel> hotels;

    public HotelsAdapter(Context context) {
        this.context = context;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTextView;
        private TextView adressTextView;
        private TextView starsTextView;
        private TextView distanceTextView;
        private TextView suitesTextView;

        private TaskHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_item_text_view);
            adressTextView = (TextView) itemView.findViewById(R.id.adress_item_text_view);
            starsTextView = (TextView) itemView.findViewById(R.id.stars_item_text_view);
            distanceTextView = (TextView) itemView.findViewById(R.id.distance_item_text_view);
            suitesTextView = (TextView) itemView.findViewById(R.id.suites_item_text_view);

            itemView.setOnClickListener(this);
        }

        //set OnClickListener on TaskHolder to open hotel's details
        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            hotel = hotels.get(position);
            Intent intent = new Intent(context, HotelDetailsActivity.class);
            intent.putExtra("id", String.valueOf(hotel.getmId()));
            context.startActivity(intent);
        }
    }

    //if list size = 0, return empty view
    @Override
    public int getItemViewType(int position) {
        if (hotels.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return hotels.size() > 0 ? hotels.size() : 1;
    }

    public void setHotels(List<Hotel> hotelList) {
        hotels = hotelList;
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
            hotel = hotels.get(position);

            vh.nameTextView.setText(hotel.getmName());
            vh.adressTextView.setText(hotel.getmAdress());
            vh.starsTextView.setText(String.valueOf(hotel.getmStars()) + " " + context.getString(R.string.stars));
            vh.distanceTextView.setText(context.getString(R.string.distance) + " " + String.valueOf(hotel.getmDistance()));
            vh.suitesTextView.setText(context.getString(R.string.suites_available) + " " + String.valueOf(hotel.getmSuitesAvailable()));
        }
    }
}

