package com.example.orchidinn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView checkIn, checkOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        toolbar = findViewById(R.id.toolbar);
        checkIn = findViewById(R.id.checkin_date);
        checkOut = findViewById(R.id.checkout_date);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        // Calculate thisMonth and today only once
        long thisMonthMillis = MaterialDatePicker.thisMonthInUtcMilliseconds();
        long todayMillis = MaterialDatePicker.todayInUtcMilliseconds();



        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
            }
        });


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