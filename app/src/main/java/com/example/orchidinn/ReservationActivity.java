package com.example.orchidinn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView checkIn, checkOut, roomCountTv, adultCountTv, childCountTv,warning;
    ImageButton roomIncrement, roomDecrement, adultIncrement, adultDecrement, childIncrement, childDecrement;
    private int totalAdultCount;
    private int maxChildCount;
    private int roomCount = 1;
    private int adultCount = 1;
    private int childCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

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

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        totalAdultCount = roomCount * 4;
        maxChildCount = totalAdultCount - adultCount + roomCount;

        checkIn.setOnClickListener(new View.OnClickListener() {
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
                    checkCondition();
                }
            }
        });

        roomDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomCount > 1) {
                    roomCount--;
                    roomCountTv.setText("" + roomCount);
                    totalAdultCount = roomCount * 4;
                    checkCondition();
                }
            }
        });

        adultIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount < totalAdultCount) {
                    adultCount++;
                    adultCountTv.setText("" + adultCount);
                    maxChildCount = totalAdultCount - adultCount + roomCount;
                    checkCondition();
                }
            }
        });

        adultDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount > 1) {
                    adultCount--;
                    adultCountTv.setText("" + adultCount);
                    maxChildCount = totalAdultCount - adultCount + 1;
                    checkCondition();
                }
            }
        });

        childIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount < maxChildCount) {
                    childCount++;
                    childCountTv.setText("" + childCount);
                }
            }
        });

        childDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount > 0) {
                    childCount--;
                    childCountTv.setText("" + childCount);
                }
            }
        });


    }

    private void checkCondition() {
        if(adultCount<roomCount){
            warning.setVisibility(View.VISIBLE);
            warning.setText("Each room must have atleast one person");
            //Toast.makeText(BookingModel.this, "Each room must have atleast one person", Toast.LENGTH_SHORT).show();
        }
        else{
            warning.setVisibility(View.INVISIBLE);
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

            String date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
            String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));

            checkIn.setText(date1);
            checkOut.setText(date2);
        });

        // Show the date range picker
        datePicker.show(getSupportFragmentManager(), datePicker.toString());


    }
}