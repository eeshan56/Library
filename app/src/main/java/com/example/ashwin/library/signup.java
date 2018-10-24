package com.example.ashwin.library;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    // DatabaseReference dref;
    EditText editTextUid, editTextName, editTextPassword, editTextEmail;
    int u = 0;
    boolean isadmin = false;

    private DatabaseReference ref;
    Button buttonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextName = (EditText) findViewById(R.id.input_name);

        editTextUid = (EditText) findViewById(R.id.input_uid);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        buttonSignup = (Button) findViewById(R.id.btn_signup);
        buttonSignup.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();


        ref = FirebaseDatabase.getInstance().getReference("student");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        } ;
    }

    private void registerUser(){
        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String uid = editTextUid.getText().toString().trim();

        u = Integer.parseInt(uid);
        final String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(uid)){
            Toast.makeText(this,"Please enter uid",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog


        Log.d("asd", email);
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(signup.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            ref = FirebaseDatabase.getInstance().getReference("student/" + currentFirebaseUser.getUid().toString());
                            data d = new data();
                            d.setName(editTextName.getText().toString());
                            d.setUid(u);
                            d.setIsadmin(isadmin);
                            d.setEmail(editTextEmail.getText().toString());
                            Log.d("sads", "asd");

                            ref.child("").setValue(d);

                            Intent k = new Intent(signup.this,action.class);
                            startActivity(k);
                            //finish();
                        }else{
                            //display some message here


                            Toast.makeText(signup.this, "Registration Error" + " " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    public void goto_login(View view){
        Intent l = new Intent(signup.this,MainActivity.class);
        startActivity(l);
    }

}
