package com.example.orchidinn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orchidinn.CustomerDetailsForBookingActivity;
import com.example.orchidinn.Model.HotelDetails;
import com.example.orchidinn.R;
import com.example.orchidinn.ReservationActivity;

import java.util.List;

public class ShowAllHotelAdapter extends RecyclerView.Adapter<ShowAllHotelAdapter.MyHolder> {
    Context context;
    List<HotelDetails> hotelDetails;

    public ShowAllHotelAdapter(Context context, List<HotelDetails> hotelDetails) {
        this.context = context;
        this.hotelDetails = hotelDetails;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_all_hotel_item, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllHotelAdapter.MyHolder holder, int position) {
        String name = hotelDetails.get(position).getName();
        String address = hotelDetails.get(position).getAddress();
        String image = hotelDetails.get(position).getImage();
        String rating = hotelDetails.get(position).getRating();

        holder.hotelName.setText(name);
        holder.hotelAddress.setText(address);
        holder.hotelRatingBar.setRating(Float.parseFloat(rating));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReservationActivity.class);
                intent.putExtra("hotelName",name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.logo)
                .into(holder.hotelImage);

    }

    @Override
    public int getItemCount() {
        return hotelDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView hotelName, hotelAddress;
        RatingBar hotelRatingBar;
        ImageView hotelImage;

        //RecyclerView recyclerViewItem;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelAddress = itemView.findViewById(R.id.hotel_address);
            hotelRatingBar = itemView.findViewById(R.id.hotel_rating);
            hotelImage = itemView.findViewById(R.id.hotel_image);
            //recyclerViewItem = itemView.findViewById(R.id.recycle_view_item);

        }
    }
}
