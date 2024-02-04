package com.example.orchidinn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BookingModel extends AppCompatActivity {

    ImageButton roomIncrement, roomDecrement,personIncerement, personDecrement, adultIncrement, adultDecrement, childIncrement, childDecrement;
    TextView roomCountTv,personCountTv, adultCountTv, childCountTv, warning;
    private int roomCount = 1;
    private int personCount = 1;
    private int adultCount = 1;
    private int childCount = 0;
    private int totalPersonCount;
    private int totalAdultCount;
    private int maxChildCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_model);

        roomIncrement = findViewById(R.id.room_increment);
        roomDecrement = findViewById(R.id.room_decrement);
        personIncerement = findViewById(R.id.person_increment);
        personDecrement = findViewById(R.id.person_decrement);
        adultIncrement = findViewById(R.id.adult_increment);
        adultDecrement = findViewById(R.id.adult_decrement);
        childIncrement = findViewById(R.id.child_increment);
        childDecrement = findViewById(R.id.child_decrement);
        roomCountTv = findViewById(R.id.room_count);
        adultCountTv = findViewById(R.id.adult_count);
        childCountTv = findViewById(R.id.child_count);
        personCountTv = findViewById(R.id.person_count);
        warning = findViewById(R.id.warning);

        totalAdultCount = roomCount * 4;
        totalPersonCount = roomCount * 4 + roomCount;
        maxChildCount = totalAdultCount - adultCount + roomCount;


        roomIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomCount < 5) {
                    roomCount++;
                    roomCountTv.setText("" + roomCount);
                    totalPersonCount = roomCount * 4 + roomCount;
                    if(personCount<roomCount){
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Each room must have atleast one person");
                        //Toast.makeText(BookingModel.this, "Each room must have atleast one person", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        warning.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        roomDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomCount > 1) {
                    roomCount--;
                    roomCountTv.setText("" + roomCount);
                    totalPersonCount = roomCount * 4 + roomCount;
                    if(personCount<roomCount){
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Each room must have atleast one person");
                        //Toast.makeText(BookingModel.this, "Each room must have atleast one person", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        warning.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        personIncerement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personCount < totalPersonCount) {
                    personCount++;
                    personCountTv.setText("" + personCount);

                    if(personCount<roomCount){
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Each room must have atleast one person");
                        //Toast.makeText(BookingModel.this, "Each room must have atleast one person", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        warning.setVisibility(View.INVISIBLE);
                    }
                    //maxChildCount = totalPersonCountCount - adultCount + roomCount;
                }
            }
        });

        personDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personCount > 1) {
                    personCount--;
                    personCountTv.setText("" + personCount);
                    if(personCount<roomCount){
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Each room must have atleast one person");
                        //Toast.makeText(BookingModel.this, "Each room must have atleast one person", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        warning.setVisibility(View.INVISIBLE);
                    }
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
}