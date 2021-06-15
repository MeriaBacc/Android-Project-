package com.example.project;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_recap extends Fragment {

    DatabaseReference reff;

    public  TextView tv1 ;
    public  TextView tv2;
    public  TextView tv3;

    private FirebaseUser user;
    private String userID;

    public Frag_recap() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_frag_recap, container, false);

        tv1 = (TextView) view.findViewById(R.id.t1);
        tv2 = (TextView) view.findViewById(R.id.t2);
        tv3 = (TextView) view.findViewById(R.id.t3);

        Button btn = (Button) view.findViewById(R.id.voir);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("bilan");

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String totale = dataSnapshot.child("totalebilan").getValue().toString();
                        String fond = dataSnapshot.child("fond_roulement").getValue().toString();
                        String besoin = dataSnapshot.child("besoin_roulement").getValue().toString();

                        tv1.setText("TOTALE BILAN : " + totale +"");

                        tv2.setText("FOND DE ROULEMENT :" + fond +"\n SI FONDS DE ROULEMENT EST SUPERIEUR A BESOIN EN FONDS DE ROULEMENT " +
                                "ALORS LE TRESORERIE NETTE EST SUPERIEUR A 0. " +
                                "\n SI FONDS DE ROULEMENT EST INFERIEUR A BESOIN EN FONDS DE ROULEMENT " +
                                "ALORS LE TRESORERIE NETTE EST SUPERIEUR A 0.");


                        tv3.setText("BESOIN EN FONDS DE ROULEMENT :" + besoin +"\n S'IL EST NEGATIF, C'EST QUE LA SOCIETE A PLUS DE DETTES COURT TERME QUE DE CREANCE ET DES STOCKS."
                        +"\n S'IL EST POSITVE, C'EST QUE LA SOCIETE DE CREANCE ET DES STOCKS QUE DE DETTES FOURNISSEURS.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });










        return view;
    }





}