package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orchidinn.Adapter.RoomSelectAdapter;
import com.example.orchidinn.Model.RoomDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");


        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("name", "Gateway Manor");
        hashMap.put("email", "gatewaymanor@gmail.com");
        hashMap.put("address", "28, Bandra Kurla Complex Vicinity, Mumbai, Maharashtra-400 055");
        hashMap.put("image", "https://assets.hyatt.com/content/dam/hyatt/hyattdam/images/2022/04/12/1329/MUMGH-P0765-Inner-Courtyard-Hotel-Exterior-Evening.jpg/MUMGH-P0765-Inner-Courtyard-Hotel-Exterior-Evening.16x9.jpg");
        hashMap.put("description", "Strike out to explore the bustling city of Mumbai, shopping in the Lower Parel district or Chor Bazaar. Set out to see iconic sites, like the Gateway of India and Haji Ali Shrine. And marvel at Indian artifacts at the Chhatrapati Shivaji Maharaj Vastu Sangrahalaya.");
        hashMap.put("phone", "9970000123");
        hashMap.put("rating", "4.5");


        //put data with in hashmap in database
        reference.child("Gateway Manor").setValue(hashMap);
    }
}