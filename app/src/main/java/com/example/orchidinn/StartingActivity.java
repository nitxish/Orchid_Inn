package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orchidinn.Admin.AdminHome;
import com.example.orchidinn.Admin.AdminSignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartingActivity extends AppCompatActivity {
    Button admin, user;
    FirebaseAuth auth;
    String[] storagePermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        admin = findViewById(R.id.admin_login);
        user = findViewById(R.id.user_login);

        auth = FirebaseAuth.getInstance();
        storagePermission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        checkLoginStatus();

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this, AdminSignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void checkLoginStatus() {
        FirebaseUser user = auth.getCurrentUser();


        if (user != null) {

            if (!checkStoragePermission()) {
                requestPermission();
            }

            String userId = user.getUid();
            String email = user.getEmail();

            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("CURRENT_ID", userId);
            editor.putString("EMAIL",email);
            editor.apply();

            // Retrieve values
            //String username = sp.getString("EMAIL", "");

            //Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();

            if (email.contains("admin")){
                Intent intent = new Intent(StartingActivity.this, AdminHome.class);
                startActivity(intent);
                finish();

            } else if(!email.contains("admin")){
                Intent intent = new Intent(StartingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(StartingActivity.this, storagePermission, 10);
    }
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(StartingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 10){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Please allow storage permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

