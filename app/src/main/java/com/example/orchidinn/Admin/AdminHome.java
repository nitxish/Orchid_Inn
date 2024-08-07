package com.example.orchidinn.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orchidinn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminHome extends AppCompatActivity {
    LinearLayout bookingDetails, addRooms, maintenance;
    FirebaseAuth auth;
    DatabaseReference reference;
    TextView hotelNameTv;
    Button logout;
    private String hotelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bookingDetails = findViewById(R.id.layout_booking_details);
        addRooms = findViewById(R.id.layout_edit_rooms);
        maintenance = findViewById(R.id.layout_maintenance);
        hotelNameTv = findViewById(R.id.hotel_name_tv);
        logout = findViewById(R.id.admin_logout);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Admin");

        getAdminDetails();

        bookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminBookingDetails.class);
                startActivity(intent);
            }
        });

        addRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AddRoomActivity.class);
                intent.putExtra("hotelName",hotelName);
                startActivity(intent);
            }
        });

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminMaintenance.class);
                intent.putExtra("hotelName",hotelName);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(AdminHome.this,AdminSignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getAdminDetails() {
        FirebaseUser user = auth.getCurrentUser();

        Query query = reference.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    hotelName = ds.child("hotelName").getValue(String.class);
                    hotelNameTv.setText(hotelName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}