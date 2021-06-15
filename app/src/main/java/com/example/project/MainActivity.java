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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView regi ;
    private TextView regis_user;
    private EditText editemail, editpassword;
    private FirebaseAuth mAuth;


    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regi = (TextView) findViewById(R.id.register);
        regi.setOnClickListener(this);

        regis_user =(TextView) findViewById(R.id.registeruser);
        regis_user.setOnClickListener(this);

        editemail = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register: startActivity(new Intent(this, Registeruser.class));
            break;
            case  R.id.registeruser:
                userLogin();
                break;
        }
    }


    private void userLogin(){
        String email = editemail.getText().toString().trim();
        String password =editpassword.getText().toString().trim();

        if (email.isEmpty()) {
            editemail.setError("Full name is required !");
            editemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editpassword.setError("Full name is required !");
            editpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Please provide valid Email !");
            editemail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editpassword.setError("Min password length should be 6 characters !");
            editpassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //redirect To ClaculBilan
                    startActivity(new Intent(MainActivity.this, Index.class));

                }else{
                    Toast.makeText(MainActivity.this, "Failed to login!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}