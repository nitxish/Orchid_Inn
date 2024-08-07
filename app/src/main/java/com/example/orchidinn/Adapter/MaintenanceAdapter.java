package com.example.orchidinn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orchidinn.Model.RoomDetails;
import com.example.orchidinn.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter <MaintenanceAdapter.MyHolder>{
    Context context;
    List<RoomDetails> roomDetails;
    String hotelName;

    FirebaseAuth auth;
    DatabaseReference reference;

    public MaintenanceAdapter(Context context, List<RoomDetails> roomDetails, String hotelName) {
        this.context = context;
        this.roomDetails = roomDetails;
        this.hotelName = hotelName;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_maintenance_admin,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceAdapter.MyHolder holder, int position) {

        String roomNo = roomDetails.get(position).getId().toString();
        String roomType = roomDetails.get(position).getName().toString();
        String maintenance = roomDetails.get(position).getMaintenance().toString();

        holder.roomNo.setText(roomNo);
        holder.roomType.setText(roomType);
        if (maintenance.equals("yes")){
            holder.maintenanceOnOff.setChecked(true);
        } else {
            holder.maintenanceOnOff.setChecked(false);
        }
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Hotels");
        holder.maintenanceOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch is ON
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("maintenance", "yes");
                    reference.child(hotelName).child("Rooms").child(roomDetails.get(position).getId()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Maintenance On", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("maintenance", "no");
                    reference.child(hotelName).child("Rooms").child(roomDetails.get(position).getId()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Maintenance Off", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView roomNo, roomType;
        Switch maintenanceOnOff;
         public MyHolder(@NonNull View itemView) {
            super(itemView);

            roomNo = itemView.findViewById(R.id.room_no);
            roomType = itemView.findViewById(R.id.room_type);
            maintenanceOnOff = itemView.findViewById(R.id.maintenance_switch);



        }
    }
}
