package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.invoke.ConstantCallSite;

public class Registeruser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private Button registerusr;
    private TextView banner;
    private EditText editfullname, editage, editeemail, editepassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);

        mAuth = FirebaseAuth.getInstance();

        editfullname = (EditText) findViewById(R.id.fullname);
        editage = (EditText) findViewById(R.id.age);
        editeemail = (EditText) findViewById(R.id.email);
        editepassword = (EditText) findViewById(R.id.password);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerusr = (Button) findViewById(R.id.registeruser);
        registerusr.setOnClickListener(this);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registeruser:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = editeemail.getText().toString().trim();
        String password = editepassword.getText().toString().trim();
        String fullname = editfullname.getText().toString().trim();
        String age = editage.getText().toString().trim();

        if (fullname.isEmpty()) {
            editfullname.setError("Full name is required !");
            editfullname.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            editage.setError("Full name is required !");
            editage.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editeemail.setError("Full name is required !");
            editeemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editepassword.setError("Full name is required !");
            editepassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editeemail.setError("Please provide valid Email !");
            editeemail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editepassword.setError("Min password length should be 6 characters !");
            editepassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful()){
                            User user = new User(fullname, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registeruser.this,"User has been registeres successfully!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to onther layout
                                        startActivity(new Intent(Registeruser.this, Index.class));
                                    }else{
                                        Toast.makeText(Registeruser.this,"Failed to register!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Registeruser.this,"Failed to register! try again", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }


}


