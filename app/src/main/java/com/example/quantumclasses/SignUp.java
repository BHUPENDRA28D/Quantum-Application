package com.example.quantumclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    // Variables
    TextInputLayout regName,regUsername,regEmail,regPhone,regPassword;
    AppCompatButton regBtn,regLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.reg_name);
        regUsername =findViewById(R.id.reg_Username);
        regEmail = findViewById(R.id.reg_email);
        regPhone = findViewById(R.id.reg_phone);
        regPassword = findViewById(R.id.reg_password);
        regLoginBtn = findViewById(R.id.reg_loginBtn);
        regBtn = findViewById(R.id.reg_signup);


        rootNode = FirebaseDatabase.getInstance();
        reference =rootNode.getReference("users");

        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Allready have account Button
        regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

    }


    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;}
    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;}
    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid Email Address");
            return false;
            
        } else {
            regEmail.setError(null);
            return true;}
    }

    private Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();
        String PhonePatter = "^[7-9][0-9]{9}$";

        if(val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(PhonePatter)) {
            regPhone.setError("Invalid Email Address");
            return false;

        } else {
            regPhone.setError(null);
            return true;}
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVAl ="^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVAl)) {
            regPassword.setError("Password is to Weak");
            return false;

        } else {
            regPassword.setError(null);
            return true;}
    }




    // Saving / Inserting data in Firebase.
    public void registerUser(View view) {
//        rootNode = FirebaseDatabase.getInstance();
//        reference =rootNode.getReference("users");

        if (!validateName() |!validateUsername() | !validateEmail()  | !validatePassword() ){
            return;
        }

        PerforAuth();

        insetInRTDB();

    }

    private void PerforAuth() {
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "SingUp Successfull", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void insetInRTDB() {
        // Get all the valuse
        String name = regName.getEditText().getText().toString();
        String username =regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        UserHelper userHelper = new UserHelper(name,username,email,phone,password);

        reference.child(phone).setValue(userHelper);


    }
}