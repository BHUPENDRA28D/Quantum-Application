package com.example.quantumclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    LinearLayout profile;

    TextView prfFullName,prfUsername;
    TextInputLayout prfEmail, prfPhone,prfPassword;
    AppCompatButton update,logout;
    String fullName, username,email,phone,password;
    FirebaseAuth authProfile;
//    FirebaseDatabase dbProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profile = findViewById(R.id.profileView);
        prfFullName =findViewById(R.id.prf_fullname);
        prfUsername =findViewById(R.id.prf_username);
        prfEmail =findViewById(R.id.prf_email);
        prfPhone =findViewById(R.id.prf_phone);
        prfPassword =findViewById(R.id.prf_password);
        update =findViewById(R.id.prf_updateBtn);
        logout =findViewById(R.id.prf_Logout);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =authProfile.getCurrentUser();
//        dbProfile = FirebaseDatabase.getInstance();




        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's detail is not avalabile", Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }


        // Update Button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserProfile.this, "Not Functional yet,Development on Progress", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String useID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phone");
        reference.child(useID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper = snapshot.getValue(UserHelper.class);
                if (userHelper != null){
                    fullName =firebaseUser.getDisplayName();
                    username =userHelper.username;
                    email = firebaseUser.getEmail();
                    phone = userHelper.getPhone();
                    password =userHelper.password;


                    prfFullName.setText(fullName);
                    prfUsername.setText(username);
                    prfEmail.getEditText().setText(email);
                    prfPassword.getEditText().setText(email);
                    prfPhone.getEditText().setText(phone);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something Went Wrong Enable to fecth data.", Toast.LENGTH_SHORT).show();

            }
        });


    }

//    //Function for both cardView
//    private  void cardFunOne(View view){
//        Toast.makeText(this, "Not Functional yet. App under Development", Toast.LENGTH_LONG).show();
//    }
//    private  void cardFunTwo(View view){
//        Toast.makeText(this, "Not Functional yet. App under Development", Toast.LENGTH_LONG).show();
//    }

}