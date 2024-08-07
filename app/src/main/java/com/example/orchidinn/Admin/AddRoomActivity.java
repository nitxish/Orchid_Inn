package com.example.orchidinn.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.orchidinn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddRoomActivity extends AppCompatActivity {
    ImageButton backBtn;
    Button addRoom;
    EditText roomNameEdt, priceEdt, idEdt, roomDescriptionEdt;
    FirebaseAuth auth;
    DatabaseReference reference, imageReference;
    ProgressDialog pd;
    private String img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        roomNameEdt = findViewById(R.id.room_name_edt);
        priceEdt = findViewById(R.id.price_edt);
        idEdt = findViewById(R.id.id_edt);
        roomDescriptionEdt = findViewById(R.id.description_edt);
        addRoom = findViewById(R.id.add_room_btn);
        backBtn = findViewById(R.id.back_btn);

        //for loading screen
        pd = new ProgressDialog(AddRoomActivity.this);
        pd.setMessage("Loading...");

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");
        imageReference = FirebaseDatabase.getInstance().getReference("RoomImage");

        Intent intent = getIntent();
        String hotelName = intent.getStringExtra("hotelName");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom(hotelName);

            }
        });
    }

    private void addRoom(String hotelName) {
        String roomName = roomNameEdt.getText().toString();
        String price = priceEdt.getText().toString();
        String id = idEdt.getText().toString();
        String description = roomDescriptionEdt.getText().toString();
        boolean status = false;
        img1 = "";
        img2 = "";

        if(roomName.isEmpty()){
            roomNameEdt.setError("Please enter room name");
            status = true;
        } else {
            //roomNameEdt.setError("");
        }

        if (price.isEmpty()) {
            priceEdt.setError("Please enter room price");
            status = true;
        } else {
            //priceEdt.setError("");
        }

        if (id.isEmpty()) {
            idEdt.setError("Please enter room id");
            status = true;
        } else {
            //idEdt.setError("");
        }

        if (description.isEmpty()) {
            roomDescriptionEdt.setError("Please enter room description");
            status = true;
        } else {
            //roomDescriptionEdt.setError("");
        }

        if (!status) {
            //loading symbol
            pd.show();

            if(roomName.toLowerCase().equals("junior suite")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://www.tajhotels.com/content/dam/luxury/hotels/taj-coromandel/images/gallery/04%20-%20Junior%20Suite.jpg/jcr:content/renditions/cq5dam.web.756.756.jpeg";
                //img2 = "https://www.tajhotels.com/content/dam/luxury/hotels/taj-coromandel/images/gallery/bath.jpg/jcr:content/renditions/cq5dam.web.756.756.jpeg";
            } else if(roomName.toLowerCase().equals("standard kingbed")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://amorgoshotel.com/wp-content/uploads/2014/12/Amorgos-Standard-Room2-e1464286437370.jpg";
                //img2 = "https://www.mhousebelgrade.com/wp-content/uploads/2019/04/Standard-double-3.jpg";
            } else if(roomName.toLowerCase().equals("standard twinbed")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://en.hoteltheflag.jp/overview/rooms/img/stt.jpg";
                //img2 = "https://en.hoteltheflag.jp/overview/rooms/img/st/room01.jpg";
            } else if(roomName.toLowerCase().equals("deluxe kingbed")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://www.shutterstock.com/image-photo/hotel-room-interior-600nw-227150821.jpg";
                //im/g2 = "https://image-tc.galaxy.tf/wijpeg-d5omab2koaymp3rw49cof1w0j/deluxe-room-desk-side.jpg";
            } else if(roomName.toLowerCase().equals("deluxe twinbed")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://sp-ao.shortpixel.ai/client/q_glossy,ret_img,w_700,h_400/https://www.londonhousehotels.com/wp-content/uploads/2017/11/8.-3-standard-twin-3-700x400.jpg";
                //img2 = "https://www.londonhousehotels.com/wp-content/uploads/2017/11/9.-1-twin-garden-view-MAIN.jpg";
            } else if(roomName.toLowerCase().equals("luxury suite")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://www.tajhotels.com/content/dam/luxury/hotels/taj-coromandel/images/gallery/Executive%20Suite%20Living%20Area.jpg/jcr:content/renditions/cq5dam.web.756.756.jpeg";
                //img2 = "https://www.tajhotels.com/content/dam/luxury/hotels/taj-coromandel/images/gallery/Suite-Living-Area.jpg/jcr:content/renditions/cq5dam.web.756.756.jpeg";
            } else if(roomName.toLowerCase().equals("presidential suite")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img1 = "https://www.theleela.com/prod/content/assets/styles/aio_banner_image_webp/public/aio-banner/dekstop/presidential-suite-leela-palace-hotel-delhi.jpg.webp?VersionId=Gua9eqo7kmKjjy.NfM63Dhv3iP46PZm8&itok=0Tb2mV6d";
                //img2 = "https://www.theleela.com/prod/content/assets/styles/tl_1920_735/public/aio-banner/dekstop/presidential-suite-leela-chennai-hotel.jpg?VersionId=SDWOR9h1wljkOb661j2dExQiWWCp4k6E&itok=05p9-ZBN";
            } else if(roomName.toLowerCase().equals("accessible room")){
                getRoomImage(roomName, price, id, description, hotelName);
                //img2 = "https://www.parkregisbirmingham.co.uk/wp-content/uploads/2023/02/Accessible-Bathroom-2520x1400.jpg";
            }

        }


    }

    private void getRoomImage(String roomName, String price, String id, String description, String hotelName) {

        Query query = imageReference.orderByChild("name").equalTo(roomName.toLowerCase());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    img1 = ds.child("img1").getValue().toString();
                    img2 = ds.child("img2").getValue().toString();

                }

                HashMap<Object, String> hashMap = new HashMap<>();
                hashMap.put("name",roomName);
                hashMap.put("price",price);
                hashMap.put("id",id);
                hashMap.put("description",description);
                hashMap.put("availability", "available");
                hashMap.put("maintenance", "no");
                hashMap.put("image1", img1);
                hashMap.put("image2", img2);


                reference.child(hotelName).child("Rooms").child(id).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        roomNameEdt.setText("");
                        priceEdt.setText("");
                        idEdt.setText("");
                        roomDescriptionEdt.setText("");
                        Toast.makeText(AddRoomActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddRoomActivity.this, "Could not able to add room", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}