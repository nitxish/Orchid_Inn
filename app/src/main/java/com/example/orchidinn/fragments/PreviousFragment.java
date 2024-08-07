package com.example.orchidinn.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orchidinn.Adapter.TwoInOneAdapter;
import com.example.orchidinn.Model.BookingDetailsForAdmin;
import com.example.orchidinn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PreviousFragment extends Fragment {


    RecyclerView recyclerView;
    TwoInOneAdapter adapter;
    FirebaseAuth auth;
    List<BookingDetailsForAdmin> bookingDetails;
    DatabaseReference reference, bookingReference;
    private int historyCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous, container, false);

        recyclerView = view.findViewById(R.id.previous_rv);

        bookingDetails = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("UserBookedDetails");
        // bookingReference = FirebaseDatabase.getInstance().getReference("BookedDetails");

        getBookedRoomHistory();

        return view;

    }

    private void getBookedRoomHistory() {

        FirebaseUser user = auth.getCurrentUser();
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingDetails.clear();
                historyCount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String checkOut = "" + ds.child("checkOut").getValue();
                    String cancel = "" + ds.child("cancel").getValue();

                    Date currentDate = new Date();

                    // Define the date format for parsing
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    // Format the current date
                    String formattedCurrentDate = dateFormat.format(currentDate);

                    try {
                        // Parse the input date string into a Date object
                        Date inputDate = dateFormat.parse(checkOut);
                        // Input date is in the past and not equal to today date
                        if (inputDate.before(currentDate) && !formattedCurrentDate.equals(checkOut)) {
                            if (cancel.equals("no")) {
                                BookingDetailsForAdmin bookingDetailsForAdmin = ds.getValue(BookingDetailsForAdmin.class);
                                bookingDetails.add(bookingDetailsForAdmin);
                                historyCount++;
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (historyCount == 0) {
                    Toast.makeText(getActivity(), "You have no past stays", Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("yes");
                }
                int cancelBtnDisable = 0;
                adapter = new TwoInOneAdapter(getActivity(), bookingDetails, cancelBtnDisable);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}