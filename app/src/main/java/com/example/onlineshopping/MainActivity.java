package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    //register
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText frist_name=(EditText)findViewById(R.id.fristName);
        EditText last_name=(EditText)findViewById(R.id.lastName);
        EditText email=(EditText)findViewById(R.id.Email);
        EditText password=(EditText)findViewById(R.id.password);
        RadioGroup GenderGroup=(RadioGroup)findViewById(R.id.GenderGroup);
        Button Register=(Button)findViewById(R.id.Login);
        progressDialog=new ProgressDialog(this);
        /*****************************************select birthday***********************************/
        EditText date=(EditText)findViewById(R.id.date);
        DatePickerDialog.OnDateSetListener setListener;
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
         date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DatePickerDialog datePickerDialog=new DatePickerDialog(
                         MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                           month=month+1;
                           String birthday=day+"/"+month+"/"+year;
                           date.setText(birthday);
                     }
                 },year,month,day);
                 datePickerDialog.show();
             }
         });
         /**********************************************Register************************************************/
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fristName=frist_name.getText().toString();
                String Email=email.getText().toString();
                String lastName=last_name.getText().toString();
                String Password=password.getText().toString();
                if(fristName.isEmpty()||lastName.isEmpty()||Email.isEmpty()||Password.isEmpty()||date.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
                }
                else {
                        //to get value of Gender
                        int idGender = GenderGroup.getCheckedRadioButtonId();
                        RadioButton Gender = (RadioButton) findViewById(idGender);
                        //to save new user in firebase
                      saveData(fristName,lastName,Email,Password,Gender.getText().toString(),date.getText().toString(),"Customer");
                }
            }
        });
        TextView loginTxt=(TextView) findViewById(R.id.toGologin);
         loginTxt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent login=new Intent(MainActivity.this,Login.class);
                 startActivity(login);
                 finish();
             }
         });
    }
    public void saveData(String fristName,String lastName,String Email,String Password,String Gender,String Birthday,String Job){
        FirebaseAuth firebaseAuth;
        FirebaseFirestore firebaseFirestore;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                           firebaseFirestore.collection("Users")
                                   .document(FirebaseAuth.getInstance().getUid())
                                   .set(new User(fristName,lastName,Email,Password,Gender,Birthday,Job));
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,Login.class);
                           startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
 }