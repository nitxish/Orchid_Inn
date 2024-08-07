package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.orchidinn.Adapter.HomeAdapter;
import com.example.orchidinn.Adapter.StayAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    HomeAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;

    FirebaseAuth auth;
    String[] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new HomeAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


        auth = FirebaseAuth.getInstance();
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

        //checkLoginStatus();

        // Disable swiping between fragments
        viewPager2.setUserInputEnabled(false);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.bot_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.bot_stay:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.bot_acc:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bot_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bot_stay).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bot_acc).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });



    }



   /* private void checkLoginStatus() {

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){

            if(!checkStoragePermission()){
                requestPermission();
            }

            String userId = user.getUid();
            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID",userId);
            editor.apply();
        }else{
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }

    }*/

    private void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this, storagePermission, 10);

    }
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
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