package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Index extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }




    public void btn_1 (View view){
        Fragment fbilan = new Frag_calcule();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,fbilan);
        ft.commit();
    }

    public void btn_2 (View view){
        Fragment fricap = new Frag_recap();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment,fricap);
        ft.commit();


    }


    public void redirect(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Index.this, MainActivity.class));
    }


}