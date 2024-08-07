package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.orchidinn.Model.ReservationDetails;
import com.example.orchidinn.Model.RoomDetails;
import com.example.orchidinn.Model.VerifyEmail;
import com.example.orchidinn.fragments.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.time.LocalDate;
import java.util.List;

public class CustomerDetailsForBookingActivity extends AppCompatActivity {

    ImageButton back_btn;
    private ReservationDetails reservationDetails;
    private RoomDetails roomDetails1, roomDetails2, roomDetails3, roomDetails4, roomDetails5;
    List<RoomDetails> roomDetailsList;

    FirebaseAuth auth;
    FirebaseUser user;
    AlertDialog alertDialog;
    DatabaseReference reference, userReference, bookReference, userBookingReference;
    ImageView hotelImg;
    TextView hotelNameTv, hotelAddressTv, checkInDateTv, checkOutDateTv, roomCountTv, guestCountTv;
    TextView roomType;
    TextView billRoomType, billCount, billDay, billPrice;
    EditText cusName, cusMail, cusAddress, cusState, cusCountry, cusPhone;
    Button bookBtn;
    private RadioButton payAtHotelRadioButton;
    private RadioButton payOnlineRadioButton;
    private String name_hotel, hotelAddress, hotelImage;
    private int totalAmount = 0;
    private String hotelName;
    private String userName;
    private String userMail;
    private VerifyEmail verifyEmail;
    private String formattedCurrentDateTime;
    private long daysBetween = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_for_booking);

        back_btn = findViewById(R.id.back_btn);
        hotelImg = findViewById(R.id.hotel_img);
        hotelNameTv = findViewById(R.id.hotel_name);
        hotelAddressTv = findViewById(R.id.hotel_address);
        checkInDateTv = findViewById(R.id.check_in_date);
        checkOutDateTv = findViewById(R.id.check_out_date);
        guestCountTv = findViewById(R.id.guest_count);
        roomCountTv = findViewById(R.id.room_count);
        roomType = findViewById(R.id.selected_room);
        cusName = findViewById(R.id.full_name);
        cusMail = findViewById(R.id.customer_mail);
        cusAddress = findViewById(R.id.customer_address);
        cusState = findViewById(R.id.state);
        cusCountry = findViewById(R.id.country);
        cusPhone = findViewById(R.id.customer_ph_no);
        bookBtn = findViewById(R.id.payment_gateway);
        billRoomType = findViewById(R.id.room_type);
        billPrice = findViewById(R.id.amount);
        payOnlineRadioButton = findViewById(R.id.pay_online);
        payAtHotelRadioButton = findViewById(R.id.pay_at_hotel);

        roomDetailsList = new ArrayList<>();
        pd = new ProgressDialog(CustomerDetailsForBookingActivity.this);
        pd.setMessage("Booking room..");

        Intent intent = getIntent();
        hotelName = intent.getStringExtra("hotelName");
        reservationDetails = intent.getParcelableExtra("reservationDetails");
        String selectedRoomSize = intent.getStringExtra("size");

        /*roomDetails1 = intent.getParcelableExtra("roomDetails1");
        roomDetails2 = intent.getParcelableExtra("roomDetails2");
        roomDetails3 = intent.getParcelableExtra("roomDetails3");
        roomDetails4 = intent.getParcelableExtra("roomDetails4");
        roomDetails5 = intent.getParcelableExtra("roomDetails5");*/

        // Retrieve room details dynamically based on array size
        //Toast.makeText(this, "" + selectedRoomSize, Toast.LENGTH_SHORT).show();

        int size = Integer.parseInt(selectedRoomSize);

        for (int i = 1; i <= size; i++) {
            RoomDetails roomDetails = intent.getParcelableExtra("roomDetails" + i);
            if (roomDetails != null) {
                roomDetailsList.add(roomDetails);
            }
        }



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Hotels");
        userReference = FirebaseDatabase.getInstance().getReference("Users");
        bookReference = FirebaseDatabase.getInstance().getReference("BookedDetails");
        userBookingReference = FirebaseDatabase.getInstance().getReference("UserBookedDetails");


        Query query = userReference.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    userName = "" + ds.child("name").getValue();
                    userMail = "" + ds.child("email").getValue();

                    cusName.setText(userName);
                    cusMail.setText(userMail);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.orderByChild("name").equalTo(hotelName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    name_hotel = ds.child("name").getValue().toString();
                    hotelAddress = ds.child("address").getValue().toString();
                    hotelImage = ds.child("image").getValue().toString();

                    /*phNo = ds.child("phone").getValue().toString();
                    description = ds.child("description").getValue().toString();*/


                }
                Glide.with(CustomerDetailsForBookingActivity.this)
                        .load(hotelImage).placeholder(R.drawable.logo2).into(hotelImg);

                hotelNameTv.setText(name_hotel);
                hotelAddressTv.setText(hotelAddress);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        checkInDateTv.setText(reservationDetails.getCheckIn());
        checkOutDateTv.setText(reservationDetails.getCheckOut());


        int adultCou = reservationDetails.getAdultCount();
        int childCou = reservationDetails.getChildCount();
        int guestCou = adultCou + childCou;
        String roomNames = "";

        guestCountTv.setText(guestCou + "");
        roomCountTv.setText("" + reservationDetails.getRoomCount());
        for (RoomDetails roomDetails : roomDetailsList) {
            roomNames = roomNames + ", " + roomDetails.getName();
        }
        roomType.setText(roomNames);
        //billRoomType.setText("");

        String checkIn = reservationDetails.getCheckIn();
        String checkOut = reservationDetails.getCheckOut();

        // Define the date format
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            // Parse the input strings into LocalDate objects
            LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
            LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
            // Calculate the difference between check-in and check-out dates
            daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }

        String billPriceDetails = "";
        for (RoomDetails roomDetails : roomDetailsList) {
            int price = (int) (daysBetween * Double.parseDouble(roomDetails.getPrice()));
            totalAmount += price;
            billPriceDetails = billPriceDetails + roomDetails.getName() + " ( 1 Room × " + daysBetween + " Days ) = ₹" + price + "\n";
        }

        billRoomType.setText("" + billPriceDetails);
        billPrice.setText("Total amount: ₹" + totalAmount);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = cusName.getText().toString();
                String address = cusAddress.getText().toString();
                String state = cusState.getText().toString();
                String country = cusCountry.getText().toString();
                String mail = cusMail.getText().toString();
                String phoneNo = cusPhone.getText().toString();
                boolean status = false;

                /*if (fullName.isEmpty()) {
                    cusName.setError("Enter your Name");
                    status  = true;
                } else {
                    cusName.setError("");
                }*/

                if (address.isEmpty()) {
                    cusAddress.setError("Enter your Address");
                    status  = true;
                } else {
                    //cusAddress.setError("");
                }

                if (state.isEmpty()) {
                    cusState.setError("Enter your State");
                    status  = true;
                } else {
                    //cusState.setError("");
                }

                if (country.isEmpty()) {
                    cusCountry.setError("Enter your Country");
                    status  = true;
                } else {
                    //cusCountry.setError("");
                }

                if (phoneNo.isEmpty()) {
                    cusPhone.setError("Enter your Phone No");
                    status  = true;
                } else {
                    //cusPhone.setError("");
                }

                if (!status) {

                    String senderEmail = "orchidinn2101@gmail.com";
                    String passwordSenderEmail = "eccnjpahiycawndh";

                    verifyEmail = new VerifyEmail();
                    verifyEmail.setSenderEmail(senderEmail);
                    verifyEmail.setReceiverEmail(userMail);
                    verifyEmail.setPasswordSenderEmail(passwordSenderEmail);
                    verifyEmail.setReceiverName(userName);
                    verifyEmail.sendOtpToEmail("otp", "", "");

                    if (payOnlineRadioButton.isChecked()) {
                        emailOtp("payOnline");
                    } else if (payAtHotelRadioButton.isChecked()) {
                        emailOtp("payOffline");
                    }

                }

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void emailOtp(String paymentMethod) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(CustomerDetailsForBookingActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.booking_conformation, null);

        EditText mailInput = mView.findViewById(R.id.otp_edt); // Get the EditText view from the inflated layout
        TextView price = mView.findViewById(R.id.price_tv); // Get the EditText view from the inflated layout
        TextView email = mView.findViewById(R.id.email_id_tv); // Get the EditText view from the inflated layout
        Button btnCancel = mView.findViewById(R.id.cancel_btn); // Get the Cancel button from the inflated layout
        Button btnPay = mView.findViewById(R.id.pay_btn); // Get the Reset button from the inflated layout

        if (paymentMethod.equals("payOnline")) {
            price.setText("(Total price: ₹" + totalAmount + ")");
        } else {
            price.setText("(Pay at Hotel)");
        }

        email.setText("OTP is sent to this Email       " + user.getEmail());

        alert.setView(mView);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


        // Set OnClickListener for the Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        // Set OnClickListener for the Reset button
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpEdt = mailInput.getText().toString();

                int otp = verifyEmail.getEmailOtpNumber();

                if (TextUtils.isEmpty(otpEdt)) {
                    mailInput.setError("Enter the Otp");
                } else if (Integer.parseInt(otpEdt) == otp) {
                    Date currentDate = new Date();
                    // Define the date format for parsing
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    // Format the current date
                    formattedCurrentDateTime = dateFormat.format(currentDate);
                    pd.show();
                    bookRoom(paymentMethod);

                } else {
                    mailInput.setError("Enter Valid Otp");
                }

            }
        });
    }

    private void bookRoom(String paymentMethod) {

        String fullName = cusName.getText().toString();
        String address = cusAddress.getText().toString();
        String state = cusState.getText().toString();
        String country = cusCountry.getText().toString();
        String mail = cusMail.getText().toString();
        String phoneNo = cusPhone.getText().toString();


        for (RoomDetails roomDetails : roomDetailsList) {
            int price = (int) (daysBetween * Double.parseDouble(roomDetails.getPrice()));

            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("name", fullName);
            hashMap.put("email", mail);
            hashMap.put("address", address);
            hashMap.put("state", state);
            hashMap.put("country", country);
            hashMap.put("phoneNo", phoneNo);
            hashMap.put("hotelName", name_hotel);
            hashMap.put("checkIn", reservationDetails.getCheckIn());
            hashMap.put("checkOut", reservationDetails.getCheckOut());
            hashMap.put("adult", "" + reservationDetails.getAdultCount());
            hashMap.put("child", "" + reservationDetails.getChildCount());
            hashMap.put("rooms", "" + reservationDetails.getRoomCount());
            hashMap.put("roomType", "" + roomDetails.getName());
            hashMap.put("roomId", "" + roomDetails.getId());
            hashMap.put("userId", user.getUid());
            hashMap.put("price", String.valueOf(price));
            hashMap.put("paymentMethod", paymentMethod);
            hashMap.put("cancel", "no");
            hashMap.put("bookedDate", "" + formattedCurrentDateTime);
            hashMap.put("totalAmount", "" + totalAmount);

            //put data with in hashmap in database
            bookReference.child(name_hotel).child(roomDetails.getId()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    userBookingReference.child(user.getUid()).child(roomDetails.getId()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            HashMap<String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put("availability", "unavailable");
                            reference.child(hotelName).child("Rooms").child(roomDetails.getId()).updateChildren(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CustomerDetailsForBookingActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        String subject = "Room Booked Successfully";
        Toast.makeText(CustomerDetailsForBookingActivity.this, "Room booked successfully " + fullName, Toast.LENGTH_SHORT).show();
        String content = "Dear " + fullName + ",\n\n" + "Thank you for booking your stay with " + hotelName + ".We are really looking forward to hosting you" +
                "\n\nYour booking details are as follows :\n" + "Check In       " + reservationDetails.getCheckIn() + "\nCheck Out      " + reservationDetails.getCheckOut() +
                "\nGuests         "
                + reservationDetails.getAdultCount() + " adult, " + reservationDetails.getChildCount()
                + " child" + "\nTotal Amount   " + "₹ " + totalAmount + "\n\n" + "If you have any questions please don't hesitate to contact us." +
                "\nWe hope you enjoy your stay with us!!"
                + "\n\nThank you\nTeam Orchid Inn";

        verifyEmail.sendOtpToEmail("book", subject, content);
        alertDialog.dismiss();
        Intent intent = new Intent(CustomerDetailsForBookingActivity.this, MainActivity.class);
        pd.dismiss();
        startActivity(intent);
    }
}