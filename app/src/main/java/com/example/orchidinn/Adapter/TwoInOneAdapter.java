package com.example.orchidinn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orchidinn.MainActivity;
import com.example.orchidinn.Model.BookingDetailsForAdmin;
import com.example.orchidinn.Model.VerifyEmail;
import com.example.orchidinn.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class TwoInOneAdapter extends RecyclerView.Adapter<TwoInOneAdapter.MyHolder> {


    Context context;
    List<BookingDetailsForAdmin> bookingDetails;
    int cancelBtnDisable;

    FirebaseAuth auth;
    DatabaseReference reference, userBookedReference, adminBookedReference;

    public TwoInOneAdapter(Context context, List<BookingDetailsForAdmin> bookingDetails, int cancelBtnDisable) {
        this.context = context;
        this.bookingDetails = bookingDetails;
        this.cancelBtnDisable = cancelBtnDisable;

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");
        userBookedReference = FirebaseDatabase.getInstance().getReference("UserBookedDetails");
        adminBookedReference = FirebaseDatabase.getInstance().getReference("BookedDetails");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_booked_rooms_design, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwoInOneAdapter.MyHolder holder, int position) {

        if (cancelBtnDisable == 0){
            holder.cancelRoomBtn.setVisibility(View.GONE);
        }

        String bookerName = bookingDetails.get(position).getName().toString();
        String checkIn = bookingDetails.get(position).getCheckIn().toString();
        String checkOut = bookingDetails.get(position).getCheckOut().toString();
        String price = bookingDetails.get(position).getPrice().toString();
        String adult = bookingDetails.get(position).getAdult().toString();
        String child = bookingDetails.get(position).getChild().toString();
        String hotelName = bookingDetails.get(position).getHotelName().toString();
        String email = bookingDetails.get(position).getEmail().toString();
        String phoneNo = bookingDetails.get(position).getPhoneNo().toString();
        String roomNo = bookingDetails.get(position).getRoomId().toString();
        String roomType = bookingDetails.get(position).getRoomType().toString();
        String roomCount = bookingDetails.get(position).getRooms().toString();
        String state = bookingDetails.get(position).getState().toString();

        holder.userNameTv.setText(bookerName);
        holder.hotelNameTv.setText(hotelName);
        holder.adultCountTv.setText("Adult : " + adult);
        holder.childCountTv.setText("Child : " + child);
        holder.roomTypeTv.setText("Room Type : " + roomType);
        holder.roomCountTv.setText("No of Rooms : " + roomCount);
        holder.checkInTv.setText("Check In : " + checkIn);
        holder.checkOutTv.setText("Check Out : " + checkOut);
        holder.priceTv.setText("₹ " + price);


        FirebaseUser user = auth.getCurrentUser();

        //for cancel room
        holder.cancelRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hashmap for update for admin and user
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("cancel", "yes");

                userBookedReference.child(user.getUid()).child(roomNo).updateChildren(hashMap); // update value in user database
                adminBookedReference.child(hotelName).child(roomNo).updateChildren(hashMap);// update value in admin database

                //hashmap for change available
                HashMap<String, Object> hashMap1 = new HashMap<>();
                hashMap1.put("availability", "available");

                reference.child(hotelName).child("Rooms").child(roomNo).updateChildren(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Room Cancelled Successfully", Toast.LENGTH_SHORT).show();

                        String subject = "Reservation Cancellation Conformation";
                        String content = "Dear " + bookerName + ",\n\n" + "We hope you this email find you well.\n\n"+ "We regret to to inform you that your reservation for "+ roomType+ " on "+ checkIn+ " at "+ hotelName+ " has been cancelled.We understand that plans can change unexpectedly."+ "\nWe apologize for any inconvenience we have caused and hope the opportunity to welcome you as our guest in the future."+"\n\nWarm regards\nTeam Orchid Inn";
                        String senderEmail = "orchidinn2101@gmail.com";
                        String passwordSenderEmail = "eccnjpahiycawndh";

                        VerifyEmail verifyEmail = new VerifyEmail();
                        verifyEmail.setSenderEmail(senderEmail);
                        verifyEmail.setReceiverEmail(user.getEmail());
                        verifyEmail.setPasswordSenderEmail(passwordSenderEmail);
                        verifyEmail.setReceiverName(bookerName);
                        verifyEmail.sendOtpToEmail("cancel",subject,content);

                        /*Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);*/

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView hotelNameTv, userNameTv, checkInTv, checkOutTv, adultCountTv, childCountTv, roomCountTv, roomTypeTv, priceTv;
        Button cancelRoomBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            hotelNameTv = itemView.findViewById(R.id.hotel_name);
            userNameTv = itemView.findViewById(R.id.name_tv);
            checkInTv = itemView.findViewById(R.id.check_in_tv);
            checkOutTv = itemView.findViewById(R.id.check_out_tv);
            adultCountTv = itemView.findViewById(R.id.adult_count_tv);
            childCountTv = itemView.findViewById(R.id.child_count_tv);
            roomTypeTv = itemView.findViewById(R.id.room_type_tv);
            roomCountTv = itemView.findViewById(R.id.room_count_tv);
            priceTv = itemView.findViewById(R.id.price_tv);
            cancelRoomBtn = itemView.findViewById(R.id.cancel_room_btn);


        }
    }
}
