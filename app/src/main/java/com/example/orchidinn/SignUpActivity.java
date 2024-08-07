package com.example.orchidinn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orchidinn.Admin.AddRoomActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout fullNameEdt, emailEdt, passwordEdt, conformPassEdt;
    EditText pass;
    Button signupBtn;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEdt = findViewById(R.id.name_edt);
        emailEdt = findViewById(R.id.email_edt);
        passwordEdt = findViewById(R.id.password_edt);
        pass = findViewById(R.id.pass);
        conformPassEdt = findViewById(R.id.coform_password);
        signIn = findViewById(R.id.sign_in_here);
        signupBtn = findViewById(R.id.sign_up_btn);

        pd = new ProgressDialog(SignUpActivity.this);
        pd.setMessage("Loading...");

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void checkDetails() {

        String fullName = fullNameEdt.getEditText().getText().toString();
        String email = emailEdt.getEditText().getText().toString();
        String password = passwordEdt.getEditText().getText().toString();
        String conformPass = conformPassEdt.getEditText().getText().toString();
        boolean status = false;

        if (fullName.isEmpty()) {
            fullNameEdt.setError("Please enter the your name");
            status = true;
        } else {
            fullNameEdt.setError("");
        }

        if (email.isEmpty()) {
            emailEdt.setError("Please enter the mail id");
            status = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdt.setError("Please enter valid email address");
            status = true;
        } else {
            emailEdt.setError("");
        }

        if (password.isEmpty()) {
            passwordEdt.setError("Please enter the password");
            status = true;

        } else if (password.length() <= 8) {
            passwordEdt.setError("Password must greater than 8 letters");
            status = true;
        } else {
            passwordEdt.setError("");
        }

        if (conformPass.isEmpty()) {
            conformPassEdt.setError("Please retype the password");
            conformPassEdt.setEndIconDrawable(0);
            conformPassEdt.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            status = true;
        } else if (!conformPass.equals(password)) {
            conformPassEdt.setError("Password does not match");
            status = true;
        } else {
            conformPassEdt.setError("");
        }

        if (!status) {
            pd.show();
            registerUser(email, password, fullName);
        }
    }

    private void registerUser(String email, String password, String fullName) {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //if sign in success
                    FirebaseUser user = auth.getCurrentUser();

                    //getting user details from firebase auth
                    String emailId = user.getEmail().toString();
                    String userId = user.getUid();


                    // when user is registered successfully store user info in firebase realtime database
                    //using hashmap for store user details
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("name", fullName);
                    hashMap.put("email", emailId);
                    hashMap.put("password", password);
                    hashMap.put("userId", userId);


                    //put data with in hashmap in database
                    reference.child(userId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this, "Welcome " + fullName, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(SignUpActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    //if sign in not successfully completed
                    pd.dismiss();
                    Toast.makeText(SignUpActivity.this, "Error: Registration is unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                //if user register failed
                Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}