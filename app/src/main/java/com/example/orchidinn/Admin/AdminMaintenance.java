package com.example.orchidinn.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.orchidinn.Adapter.MaintenanceAdapter;
import com.example.orchidinn.Adapter.RoomSelectAdapter;
import com.example.orchidinn.Model.RoomDetails;
import com.example.orchidinn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminMaintenance extends AppCompatActivity {
    ImageButton backBtn;
    RecyclerView recyclerView;
    MaintenanceAdapter maintenanceAdapter;
    FirebaseAuth auth;
    DatabaseReference reference;

    List<RoomDetails> roomDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintenance);

        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recycle_view);
        roomDetails = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");

        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reference.child(hotelName).child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomDetails.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    RoomDetails details = ds.getValue(RoomDetails.class);
                    roomDetails.add(details);

                }

                maintenanceAdapter = new MaintenanceAdapter(getApplicationContext(), roomDetails, hotelName);
                recyclerView.setAdapter(maintenanceAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}