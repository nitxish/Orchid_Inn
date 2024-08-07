package com.example.orchidinn.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orchidinn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminSignInActivity extends AppCompatActivity {

    TextInputLayout emailEdt, passwordEdt;
    Button signInBtn;
    TextView forgotPassword;
    FirebaseAuth auth;

    ProgressDialog pd;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        emailEdt = findViewById(R.id.admin_email_edt);
        passwordEdt = findViewById(R.id.password_edt);
        signInBtn = findViewById(R.id.sign_in_btn);
        forgotPassword = findViewById(R.id.forgot_password);
        auth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(AdminSignInActivity.this);
        pd.setMessage("Loading...");


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void checkDetails() {

        String email = this.emailEdt.getEditText().getText().toString();
        String password = passwordEdt.getEditText().getText().toString();
        boolean status = false;

        if (email.isEmpty()) {
            emailEdt.setError("Please enter the mail id");
            status = true;
        } else if (!email.contains("admin")) {
            emailEdt.setError("Please enter valid email address");
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
        } else {
            passwordEdt.setError("");
        }

        if (!status) {
            pd.show();
            loginAdmin(email, password);
        }

    }

    private void loginAdmin(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(AdminSignInActivity.this, AdminHome.class);
                    pd.dismiss();
                    startActivity(intent);
                    finish();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AdminSignInActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPassword() {
        //forgotPass.setVisibility(View.INVISIBLE);

        final AlertDialog.Builder alert = new AlertDialog.Builder(AdminSignInActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.reset_password, null);

        final EditText mailInput = mView.findViewById(R.id.reset_email_edt); // Get the EditText view from the inflated layout
        Button btnCancel = mView.findViewById(R.id.cancel_btn); // Get the Cancel button from the inflated layout
        Button btnReset = mView.findViewById(R.id.reset_btn); // Get the Reset button from the inflated layout

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
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetEmail = mailInput.getText().toString();


                if (TextUtils.isEmpty(resetEmail)) {
                    mailInput.setError("Enter the Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(resetEmail).matches()) {
                    mailInput.setError("Please enter a valid email address");
                } else {
                    sendPasswordResetEmail(resetEmail);
                }
            }
        });
    }

    private void sendPasswordResetEmail(String resetEmail) {
        auth.sendPasswordResetEmail(resetEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminSignInActivity.this, "Reset Password link has been sent to the registered Email", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminSignInActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}