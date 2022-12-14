package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    FirebaseFirestore reference;
    private boolean isRemeberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email_txt = (EditText) findViewById(R.id.Email);
        EditText Password_txt = (EditText) findViewById(R.id.password);
        TextView forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        TextView register = (TextView) findViewById(R.id.register);
        Button login_btn = (Button) findViewById(R.id.Login);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        CheckBox remeberMe = (CheckBox) findViewById(R.id.rememberMe);
        shared_Refrence s = new shared_Refrence();
        isRemeberMe = s.isRemember(getApplicationContext());
        remeberMe.setChecked(isRemeberMe);
        /**********************************************Login************************************************/
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_txt.getText().toString();
                String password = Password_txt.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    //to check email and pass
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Login succesful", Toast.LENGTH_SHORT).show();
                                    if (isRemeberMe) {
                                        s.setRememberMeStatues(getApplicationContext(), isRemeberMe);
                                        s.setUserdata(email, password);
                                    }
                                    //to get information of user and go to home page
                                    reference = FirebaseFirestore.getInstance();
                                    reference.collection("Users")
                                            .whereEqualTo("email", email)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    User user;
                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                        user = new User(documentSnapshot.getString("fristName"), documentSnapshot.getString("lastName"), documentSnapshot.getString("email"), documentSnapshot.getString("gender"), documentSnapshot.getString("birthday"), documentSnapshot.getString("job"),documentSnapshot.getString("User UID"));
                                                        Intent i = new Intent(Login.this, CategoriesActivity.class);
                                                        i.putExtra("Name", user.getFristName() + " " + user.getLastName());
                                                        i.putExtra("userid",user.getEmail());

                                                        startActivity(i);
                                                    }
                                                }
                                            });

                                }

                            });
                }
            }
        });
        if (isRemeberMe) {
            email_txt.setText(s.getEmail());
            Password_txt.setText(s.getPassword());
            login_btn.callOnClick();
        }
        remeberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemeberMe = b;
            }
        });
        /**********************************************forgetPassword************************************************/
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_txt.getText().toString();
                progressDialog.setTitle("Sending Mail");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
    });


    }
}

