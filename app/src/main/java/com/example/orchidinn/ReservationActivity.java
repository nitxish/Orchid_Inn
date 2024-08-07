package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orchidinn.Model.ReservationDetails;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button checkAvailability;
    TextView checkIn, checkOut, roomCountTv, adultCountTv, childCountTv, warning, searchBar;
    ImageButton roomIncrement, roomDecrement, adultIncrement, adultDecrement, childIncrement, childDecrement, back_btn;
    private int totalAdultCount;

    FirebaseAuth auth;
    DatabaseReference reference;
    private int maxAdultCount;
    private int maxChildCount;
    private int roomCount = 1;
    private int adultCount = 1;
    private int childCount = 0;
    private String address;
    private String name;
    private String check_In = "";
    private String check_Out = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        toolbar = findViewById(R.id.toolbar);
        checkIn = findViewById(R.id.checkin_date);
        checkOut = findViewById(R.id.checkout_date);
        roomIncrement = findViewById(R.id.room_increment);
        roomDecrement = findViewById(R.id.room_decrement);
        adultIncrement = findViewById(R.id.adult_increment);
        adultDecrement = findViewById(R.id.adult_decrement);
        childIncrement = findViewById(R.id.child_increment);
        childDecrement = findViewById(R.id.child_decrement);
        roomCountTv = findViewById(R.id.room_count);
        adultCountTv = findViewById(R.id.adult_count);
        warning = findViewById(R.id.warning);
        childCountTv = findViewById(R.id.child_count);
        checkAvailability = findViewById(R.id.check_availability);
        back_btn = findViewById(R.id.back_btn);
        searchBar = findViewById(R.id.search_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        totalAdultCount = roomCount * 4;
        maxAdultCount = totalAdultCount - childCount;
        maxChildCount = totalAdultCount - adultCount + roomCount;

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");

        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");


        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ReservationActivity.this, ShowAllHotelActivity.class);
                startActivity(intent1);
            }
        });
        reference.orderByChild("name").equalTo(hotelName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    /*HotelDetails details = ds.getValue(HotelDetails.class);
                    searchBar.setText(details.getName());*/
                    name = ds.child("name").getValue().toString();
                    address = ds.child("address").getValue().toString();
                }
                searchBar.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReservationActivity.this, "2", Toast.LENGTH_SHORT).show();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
            }
        });

        roomIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomCount < 5) {
                    roomCount++;
                    roomCountTv.setText("" + roomCount);
                    totalAdultCount = roomCount * 4;
                    maxChildCount = totalAdultCount - adultCount + roomCount;
                    maxAdultCount = totalAdultCount - childCount + roomCount;
                    checkCondition();
                }
            }
        });

        roomDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomCount > 1) {
                    roomCount--;
                    roomCountTv.setText(String.format("%s", roomCount));
                    totalAdultCount = roomCount * 4;
                    maxChildCount = totalAdultCount - adultCount + roomCount;
                    maxAdultCount = totalAdultCount - childCount + roomCount;
                    checkCondition();
                }
            }
        });

        adultIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount < totalAdultCount) {
                    if (adultCount < maxAdultCount) {
                        adultCount++;
                        adultCountTv.setText(String.format("%s", adultCount));
                        maxChildCount = totalAdultCount - adultCount + roomCount;
                        checkCondition();
                    }
                }
            }
        });

        adultDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount > 1) {
                    adultCount--;
                    adultCountTv.setText(String.format("%s", adultCount));
                    maxChildCount = totalAdultCount - adultCount + roomCount;
                    checkCondition();
                }
            }
        });

        childIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount < maxChildCount) {
                    childCount++;
                    childCountTv.setText(String.format("%s", childCount));
                    maxAdultCount = totalAdultCount - childCount + roomCount;
                }
            }
        });

        childDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount > 0) {
                    childCount--;
                    childCountTv.setText(String.format("%s", childCount));
                    maxAdultCount = totalAdultCount - childCount + roomCount;
                }
            }
        });

        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_In.isEmpty() || check_Out.isEmpty()) {
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Enter CheckIn Date and CheckOut Date");
                } else {
                    warning.setVisibility(View.INVISIBLE);
                    warning.setText("");
                    ReservationDetails reservationDetails = new ReservationDetails(check_In, check_Out, roomCount, adultCount, childCount);
                    Intent intent = new Intent(ReservationActivity.this, RoomSelectActivity.class);
                    intent.putExtra("hotelName", name);
                    intent.putExtra("reservationDetails", reservationDetails);
                    startActivity(intent);
                }
            }
        });

    }

    private void checkCondition() {

        String dateIn = checkIn.getText().toString();
        String dateOut = checkOut.getText().toString();

        if (adultCount < roomCount) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("Each room must have atLeast one adult");
            checkAvailability.setEnabled(false);
        } else if (adultCount > maxAdultCount) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("There is more no of persons in single room");
            checkAvailability.setEnabled(false);
        } else if (childCount > maxChildCount) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("There is more no of persons in single room");
            checkAvailability.setEnabled(false);
        } else {
            warning.setVisibility(View.INVISIBLE);
            checkAvailability.setEnabled(true);
        }
    }


    private void showDateRangePicker() {
        // Set up the date range picker
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        // Makes only dates from today forward selectable.
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());


        MaterialDatePicker<Pair<Long, Long>> datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(androidx.core.util.Pair.create(today, today))
                .setTitleText("Select In and Out Date")
                .setCalendarConstraints(constraintsBuilder.build())
                //.setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTheme(R.style.CustomDatePickerTheme)
                .build();


        // Set up the listener for date range selection
        datePicker.addOnPositiveButtonClickListener(selection -> {
            //long startDate = selection.first;
            //long endDate = selection.second;

            check_In = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
            check_Out = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));

            checkIn.setText(check_In);
            checkOut.setText(check_Out);
        });

        // Show the date range picker
        datePicker.show(getSupportFragmentManager(), datePicker.toString());


    }
}