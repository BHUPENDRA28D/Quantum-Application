package com.example.quantumclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    SpinKitView spinKitViewPB_login;
    int counter =0;
    Button callSignUp,forgetPaswordBtn;
    AppCompatButton login_btn;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout log_email,log_password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private  static final String TAG =  "Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        spinKitViewPB_login = findViewById(R.id.PB_login);
        callSignUp =findViewById(R.id.idSignup_btn);
        login_btn =findViewById(R.id.loginBtn_ofLoginPage);
        image = findViewById(R.id.log_img);
        forgetPaswordBtn =findViewById(R.id.frogetPas);

        logoText= findViewById(R.id.log_title);
        sloganText= findViewById(R.id.log_slogan);

        log_email =findViewById(R.id.login_emailId);
        log_password =findViewById(R.id.log_password);
        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //SignUp Button
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    // Forget password btn
        forgetPaswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Not working yet.App under development", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Boolean validateUsername(){
        String val = log_email.getEditText().getText().toString();

        if(val.isEmpty()){
            log_email.setError("Field cannot be empty");
            return false;
        }
     else {
            log_email.setError(null);
            return true;}
    }

    private Boolean validatePassword(){
        String val = log_password.getEditText().getText().toString();

        if(val.isEmpty()){
            log_password.setError("Field cannot be empty");
            return false;
        } else {
            log_password.setError(null);
            return true;}
    }

    public void loginUser(View view) {
        // Validate email and password
        if (!validateUsername() | !validatePassword()) {
            return;
        }else {
            spinKitViewPB_login.setVisibility(View.VISIBLE);

            perforLogin();
////            isUser();
        }
        // fetching data from password to compare data from firebase and data filled by user.

    }

    private void perforLogin() {
        String email = log_email.getEditText().getText().toString();
        String password = log_password.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    spinKitViewPB_login.setVisibility(View.GONE );
                    Toast.makeText(Login.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Home_Activity.class));
                    finish();
                }else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                         log_email.setError("User not Exists or no longer valid.Please register again");
                         log_email.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        log_email.setError("Invalid Credentials. Cheack and re-enter again");
                        log_email.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

//    private void isUser(){
//      final   String userEnteredUsername = username.getEditText().toString().trim();
//      final   String userEnteredPassword = password.getEditText().toString().trim();
//
//       FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("users");
//
//        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                     username.setError(null);
//                     username.setErrorEnabled(false);
//
//                     String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
//
//                     if (passwordFromDB.equals(userEnteredPassword)){
//                         password.setError(null);
//                         password.setErrorEnabled(false);
//
//                         Toast.makeText(getApplicationContext(),"Login Succesfull",Toast.LENGTH_LONG).show();
//
//                         Intent intent = new Intent(getApplicationContext(),UserProfile.class);
//                         startActivity(intent);
//                         finish();
//                     }
//                        else {
//                            password.setError("Wrong Password");
//                            password.requestFocus();
//                     }
//
//                }
//                else {
//                    username.setError("User not exists");
//                    username.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

// Cheack if User is already logged in . In such case,Straigthaway take the User to major page.
    @Override
    protected void onStart() {
        super.onStart();
        UserProfile u = new UserProfile();

        if (mAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Home_Activity.class));
            finish();
        }
        else {
            Toast.makeText(this, "Loggin First", Toast.LENGTH_SHORT).show();
        }
    }

}
