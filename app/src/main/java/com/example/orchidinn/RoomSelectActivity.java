package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orchidinn.Adapter.RoomSelectAdapter;
import com.example.orchidinn.Model.ReservationDetails;
import com.example.orchidinn.Model.RoomDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomSelectActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ImageButton back_btn;
    ImageView hotelPic;
    Button bookBtn;
    TextView hotelNameTv,hotelAddressTv,hotelPhoneTv,hotelDescriptionTv, hotelmail, noAvailableRoom;
    DatabaseReference reference;
    private List<RoomDetails> roomDetails;
    RecyclerView recyclerView;
    RoomSelectAdapter roomSelectAdapter;
    private String name,address,phNo,description, mail;
    private ReservationDetails reservationDetails;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_select);

        recyclerView = findViewById(R.id.recycle_view);
        back_btn = findViewById(R.id.back_btn);
        hotelNameTv = findViewById(R.id.hotel_name);
        hotelAddressTv = findViewById(R.id.hotel_address);
        hotelPhoneTv = findViewById(R.id.hotel_ph_no);
        hotelDescriptionTv = findViewById(R.id.hotel_description);
        hotelmail = findViewById(R.id.hotel_email);
        bookBtn = findViewById(R.id.book_btn);
        noAvailableRoom = findViewById(R.id.no_room);
        hotelPic = findViewById(R.id.hotel_pic);

        roomDetails = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");


        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");
        reservationDetails = intent.getParcelableExtra("reservationDetails");

        String c = reservationDetails.getCheckIn();
        //Toast.makeText(this, "" + c, Toast.LENGTH_SHORT).show();
        reference.orderByChild("name").equalTo(hotelName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    /*HotelDetails details = ds.getValue(HotelDetails.class);
                    searchBar.setText(details.getName());*/
                    name = ds.child("name").getValue().toString();
                    address = ds.child("address").getValue().toString();
                    phNo = ds.child("phone").getValue().toString();
                    description = ds.child("description").getValue().toString();
                    image = ds.child("image").getValue().toString();
                    mail = ds.child("email").getValue().toString();
                }
                hotelNameTv.setText(name);
                hotelAddressTv.setText(address);
                hotelPhoneTv.setText(phNo);
                hotelDescriptionTv.setText(description);
                hotelmail.setText(mail);

                Glide.with(RoomSelectActivity.this)
                        .load(image)
                        .placeholder(R.drawable.logo)
                        .into(hotelPic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(this, "" + hotelName, Toast.LENGTH_SHORT).show();
        reference.child(hotelName).child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomDetails.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    RoomDetails details = ds.getValue(RoomDetails.class);

                    if(!details.getAvailability().equals("unavailable") && !details.getMaintenance().equals("yes")) {
                        roomDetails.add(details);
                    }
                }

                if (roomDetails.size()==0){
                    bookBtn.setVisibility(View.INVISIBLE);
                    noAvailableRoom.setVisibility(View.VISIBLE);
                }
                roomSelectAdapter = new RoomSelectAdapter(getApplicationContext(), roomDetails, reservationDetails, hotelName, bookBtn);
                recyclerView.setAdapter(roomSelectAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}