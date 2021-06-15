package com.example.project;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Frag_calcule extends Fragment {


    public  EditText immobilisation_incorporelle ;
    public  EditText immobilisation_corporelle;
    public  EditText immobilisation_financiere;
    public  EditText capiteaux_propres;
    public  EditText resultat;
    public  EditText stocks;
    public  EditText creance;
    public  EditText disponibilites;
    public  EditText dettes_financier;
    public  EditText dettes_dexploitation;
    public  EditText dettes_immobilisation;
    public  EditText autre_dettes;

    public TextView resultat_bilan;

    DatabaseReference reff;
    private FirebaseUser user;
    private String userID;


    double res;
    double res_fond_roulement ;
    double res_besoin_roulement ;


    public Frag_calcule() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_calcule, container, false);

        immobilisation_incorporelle = (EditText) view.findViewById(R.id.iicor);
        immobilisation_corporelle = (EditText) view.findViewById(R.id.ic);
        immobilisation_financiere = (EditText) view.findViewById(R.id.ifinancier);

        stocks = (EditText) view.findViewById(R.id.stock);
        creance = (EditText) view.findViewById(R.id.creance);
        disponibilites = (EditText) view.findViewById(R.id.disponibilite);
        capiteaux_propres = (EditText) view.findViewById(R.id.cp);
        resultat = (EditText) view.findViewById(R.id.exerc);
        dettes_financier = (EditText) view.findViewById(R.id.dettesfin);
        dettes_dexploitation = (EditText) view.findViewById(R.id.dettesexp);
        dettes_immobilisation = (EditText) view.findViewById(R.id.dettessurimm);
        autre_dettes = (EditText) view.findViewById(R.id.autresdettes);

        Button btn = (Button) view.findViewById(R.id.btnbilan);

        resultat_bilan = (TextView) view.findViewById(R.id.totalebilan);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = Float.parseFloat(immobilisation_incorporelle.getText().toString()) + Float.parseFloat(immobilisation_corporelle.getText().toString())
                        + Float.parseFloat(immobilisation_financiere.getText().toString()) + Float.parseFloat(stocks.getText().toString())
                        + Float.parseFloat(creance.getText().toString()) + Float.parseFloat(disponibilites.getText().toString());
                resultat_bilan.setText(res + "");

                res_fond_roulement = Float.parseFloat(capiteaux_propres.getText().toString()) - Float.parseFloat(immobilisation_financiere.getText().toString());

                res_besoin_roulement = (Float.parseFloat(stocks.getText().toString()) + Float.parseFloat(creance.getText().toString()) +Float.parseFloat(disponibilites.getText().toString()))
                        - (Float.parseFloat(dettes_financier.getText().toString()) +Float.parseFloat(dettes_dexploitation.getText().toString())
                        +Float.parseFloat(dettes_immobilisation.getText().toString()) +Float.parseFloat(autre_dettes.getText().toString())) ;


                User users = new User (res,res_fond_roulement,res_besoin_roulement);
                user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase db = FirebaseDatabase.getInstance() ;
                DatabaseReference node = db.getReference("Users");
                userID = user.getUid();

                node.child(userID).child("bilan").setValue(users);


            }
         });





        return  view ;
    }



}

