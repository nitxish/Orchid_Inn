package com.example.orchidinn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orchidinn.Admin.AdminBookingDetails;
import com.example.orchidinn.Model.BookingDetailsForAdmin;
import com.example.orchidinn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.MyHolder> {

    Context context;
    FirebaseAuth auth;
    DatabaseReference reference;
    List<BookingDetailsForAdmin> bookingDetails;

    public BookingDetailsAdapter(Context context, List<BookingDetailsForAdmin> bookingDetails) {
        this.context = context;
        this.bookingDetails = bookingDetails;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_design, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsAdapter.MyHolder holder, int position) {

        String bookerName = bookingDetails.get(position).getName().toString();
        String checkIn = bookingDetails.get(position).getCheckIn().toString();
        String checkOut = bookingDetails.get(position).getCheckOut().toString();
        String address = bookingDetails.get(position).getAddress().toString();
        String adult = bookingDetails.get(position).getAdult().toString();
        String child = bookingDetails.get(position).getChild().toString();
        String country = bookingDetails.get(position).getCountry().toString();
        String email = bookingDetails.get(position).getEmail().toString();
        String phoneNo = bookingDetails.get(position).getPhoneNo().toString();
        String roomNo = bookingDetails.get(position).getRoomId().toString();
        String roomType = bookingDetails.get(position).getRoomType().toString();
        String roomCount = bookingDetails.get(position).getRooms().toString();
        String state = bookingDetails.get(position).getState().toString();
        String cancel = bookingDetails.get(position).getCancel().toString();
        String bookedDate = bookingDetails.get(position).getBookedDate().toString();
        String paymentMethod = bookingDetails.get(position).getPaymentMethod().toString();


        if (cancel.equals("yes")){
            holder.cancelTv.setVisibility(View.VISIBLE);
        }
        holder.bookerName.setText(bookerName);
        holder.address.setText("Address : " + address);
        holder.adultCount.setText("Adult : " + adult);
        holder.childCount.setText("Child : " + child);
        holder.country.setText("Country : " + country);
        holder.email.setText("Email : "+email);
        holder.phoneNo.setText("Phone No : " + phoneNo);
        holder.roomNo.setText("Room No : " + roomNo);
        holder.roomType.setText("Room Type : " + roomType);
        holder.roomCount.setText("No of Rooms : " + roomCount);
        holder.state.setText("State : " + state);
        holder.checkIn.setText("Check In : " + checkIn);
        holder.checkOut.setText("Check Out : " + checkOut);
        holder.bookedDate.setText(bookedDate);
        holder.paymentMethod.setText(paymentMethod);

        // Set click listener for toggle button
        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContentVisibility(holder);
            }
        });

    }

    // Method to toggle content visibility
    private void toggleContentVisibility(MyHolder holder) {
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
        return bookingDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private LinearLayout content;
        private ImageView toggleButton;
        TextView bookerName, address, checkIn, checkOut, adultCount, childCount, roomType, roomNo, email, roomCount, state, country, phoneNo, cancelTv;
        TextView paymentMethod, bookedDate;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            content = itemView.findViewById(R.id.content);
            toggleButton = itemView.findViewById(R.id.toggleButton);
            bookerName = itemView.findViewById(R.id.booker_name);
            address = itemView.findViewById(R.id.address_tv);
            checkIn = itemView.findViewById(R.id.check_in_tv);
            checkOut = itemView.findViewById(R.id.check_out_tv);
            adultCount = itemView.findViewById(R.id.adult_count_tv);
            childCount = itemView.findViewById(R.id.child_count_tv);
            roomType = itemView.findViewById(R.id.room_type_tv);
            roomNo = itemView.findViewById(R.id.room_no_tv);
            email = itemView.findViewById(R.id.email_tv);
            roomCount = itemView.findViewById(R.id.room_count_tv);
            state = itemView.findViewById(R.id.state_tv);
            country = itemView.findViewById(R.id.country_tv);
            phoneNo = itemView.findViewById(R.id.phone_no_tv);
            cancelTv = itemView.findViewById(R.id.room_cancel_tv);
            bookedDate = itemView.findViewById(R.id.booked_date_tv);
            paymentMethod = itemView.findViewById(R.id.payment_method_tv);

        }
    }
}
