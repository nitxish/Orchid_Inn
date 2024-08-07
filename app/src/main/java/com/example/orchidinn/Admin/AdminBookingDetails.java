package com.example.orchidinn.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.orchidinn.Adapter.BookingDetailsAdapter;
import com.example.orchidinn.Adapter.MaintenanceAdapter;
import com.example.orchidinn.Adapter.ShowAllHotelAdapter;
import com.example.orchidinn.Model.BookingDetailsForAdmin;
import com.example.orchidinn.Model.HotelDetails;
import com.example.orchidinn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminBookingDetails extends AppCompatActivity {
    ImageButton backBtn;
    RecyclerView recyclerView;
    BookingDetailsAdapter bookingDetailsAdapter;
    FirebaseAuth auth;
    List<BookingDetailsForAdmin> bookingDetails;
    DatabaseReference reference, bookingReference;
    private String hotelName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_details);

        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.booking_recyclerview);

        bookingDetails = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        bookingReference = FirebaseDatabase.getInstance().getReference("BookedDetails");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getAdminDetails();

    }

    private void getAdminDetails() {
        FirebaseUser user = auth.getCurrentUser();

        Query query = reference.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    hotelName = ds.child("hotelName").getValue().toString();
                    getBookingDetails(hotelName);
                    //Toast.makeText(AdminBookingDetails.this, ""+hotelName, Toast.LENGTH_SHORT).show();
                }
                //name = hotelName;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();


        //getBookingDetails(hotelName);
    }

    private void getBookingDetails(String hotelName) {
        bookingReference.child(hotelName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingDetails.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    BookingDetailsForAdmin bookingDetailsForAdmin = ds.getValue(BookingDetailsForAdmin.class);
                    bookingDetails.add(bookingDetailsForAdmin);
                }
                bookingDetailsAdapter = new BookingDetailsAdapter(getApplicationContext(), bookingDetails);
                recyclerView.setAdapter(bookingDetailsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}