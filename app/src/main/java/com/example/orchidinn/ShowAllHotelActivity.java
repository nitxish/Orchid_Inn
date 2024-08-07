package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.orchidinn.Adapter.ShowAllHotelAdapter;
import com.example.orchidinn.Model.HotelDetails;
import com.example.orchidinn.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAllHotelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton backBtn;
    ShowAllHotelAdapter allHotelAdapter;
    Toolbar toolbar;
    EditText searchBar;
    FirebaseAuth auth;
    DatabaseReference reference;
    private List<Users> userDetails;
    private List<HotelDetails> hotelDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_hotel);

        recyclerView = findViewById(R.id.show_all_hotel_recycle_view);
        toolbar = findViewById(R.id.search_toolbar);
        searchBar = findViewById(R.id.search_bar);
        backBtn = findViewById(R.id.back_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        userDetails = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");

        showAllHotels();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchHotels(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showAllHotels() {
        hotelDetails = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotelDetails.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    HotelDetails details = ds.getValue(HotelDetails.class);
                    hotelDetails.add(details);
                }

                allHotelAdapter = new ShowAllHotelAdapter(getApplicationContext(), hotelDetails);
                recyclerView.setAdapter(allHotelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchHotels(String searchKey) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotelDetails.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HotelDetails details = ds.getValue(HotelDetails.class);
                    if (details.getName().toLowerCase().contains(searchKey)) {
                        hotelDetails.add(details);
                    }
                }

                allHotelAdapter = new ShowAllHotelAdapter(getApplicationContext(), hotelDetails);
                recyclerView.setAdapter(allHotelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}