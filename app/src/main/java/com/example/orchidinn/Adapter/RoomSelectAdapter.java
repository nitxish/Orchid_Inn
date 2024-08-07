package com.example.orchidinn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orchidinn.CustomerDetailsForBookingActivity;
import com.example.orchidinn.Model.ReservationDetails;
import com.example.orchidinn.Model.RoomDetails;
import com.example.orchidinn.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class RoomSelectAdapter extends RecyclerView.Adapter<RoomSelectAdapter.ViewHolder> {

    Context context;
    List<RoomDetails> roomDetails;
    ReservationDetails reservationDetails;
    String hotelName;
    Button bookBtn;
    ArrayList<String> selectedRooms = new ArrayList<String>();

    public RoomSelectAdapter(Context context, List<RoomDetails> roomDetails, ReservationDetails reservationDetails, String hotelName, Button bookBtn) {
        this.context = context;
        this.roomDetails = roomDetails;
        this.reservationDetails = reservationDetails;
        this.hotelName = hotelName;
        this.bookBtn = bookBtn;
    }

    @NonNull
    @Override
    public RoomSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomSelectAdapter.ViewHolder holder, int position) {

        String name = roomDetails.get(position).getName();
        String id = roomDetails.get(position).getId();
        String price = roomDetails.get(position).getPrice();
        String description = roomDetails.get(position).getDescription();
        String image1 = roomDetails.get(position).getImage1();
        String image2 = roomDetails.get(position).getImage2();

        holder.roomName.setText(name);
        holder.roomRate.setText(price);
        holder.roomDescription.setText(description);

        Glide.with(context)
                .load(image1)
                .placeholder(R.drawable.logo)
                .into(holder.roomImage1);

        Glide.with(context)
                .load(image2)
                .placeholder(R.drawable.logo)
                .into(holder.roomImage2);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRooms.size() == reservationDetails.getRoomCount()) {
                    Intent intent = new Intent(context, CustomerDetailsForBookingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("reservationDetails", reservationDetails);
                    intent.putExtra("hotelName", hotelName);
                    intent.putExtra("size", "" + selectedRooms.size());

                    for (int i = 0; i < selectedRooms.size(); i++) {
                        int roomIndex = Integer.parseInt(selectedRooms.get(i));
                        if (roomIndex < roomDetails.size()) {
                            intent.putExtra("roomDetails" + (i + 1), roomDetails.get(roomIndex));
                        }
                    }

                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "You need to select " + reservationDetails.getRoomCount() + " rooms", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRooms.size() < reservationDetails.getRoomCount()) {
                    if (holder.checkBox.isChecked()) {
                        selectedRooms.add(String.valueOf(position));
                    } else if (!holder.checkBox.isChecked()) {
                        selectedRooms.remove(String.valueOf(position));
                    }
                } else if (selectedRooms.size() == reservationDetails.getRoomCount()) {
                    if (holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(false);
                        Toast.makeText(context, "You can select only " + reservationDetails.getRoomCount() + "rooms", Toast.LENGTH_SHORT).show();
                    } else if (!holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(false);
                        selectedRooms.remove(String.valueOf(position));
                        Toast.makeText(context, "You can select only " + reservationDetails.getRoomCount() + "rooms", Toast.LENGTH_SHORT).show();
                    }
                }
                //Toast.makeText(context, "5" + selectedRooms.size(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for toggle button
        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContentVisibility(holder);
            }
        });
    }

    // Method to toggle content visibility
    private void toggleContentVisibility(ViewHolder holder) {
        if (holder.content.getVisibility() == View.VISIBLE) {
            holder.content.setVisibility(View.GONE);
            holder.toggleButton.setImageResource(R.drawable.icon_expand_more); // Change toggle button icon to expand
        } else {
            holder.content.setVisibility(View.VISIBLE);
            holder.toggleButton.setImageResource(R.drawable.icon_expand_less); // Change toggle button icon to collapse
        }
    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView roomName, roomRate, roomDescription;

        private LinearLayout content;
        private ImageView toggleButton, roomImage1, roomImage2;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.room_name);
            roomRate = itemView.findViewById(R.id.room_rate);
            roomDescription = itemView.findViewById(R.id.room_description);
            checkBox = itemView.findViewById(R.id.add_room_checkBox);
            roomImage1 = itemView.findViewById(R.id.room_img_1);
            roomImage2 = itemView.findViewById(R.id.room_img_2);

            //for extendable Text view
            content = itemView.findViewById(R.id.content);
            toggleButton = itemView.findViewById(R.id.toggleButton);

        }
    }
}

