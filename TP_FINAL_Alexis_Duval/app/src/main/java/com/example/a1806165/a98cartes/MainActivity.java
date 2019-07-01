package com.example.a1806165.a98cartes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private Button boutonNouvellePartie;
    private TextView score;
    private Dao dao;

    //----------------------------------------------
    // méthode onCreate
    //----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonNouvellePartie = findViewById(R.id.boutonNouvellePartie);

        dao = Dao.getInstance(this);
        dao.ouvrirBD();
        int meilleurScore = dao.obtenirMeilleurScore();
        score = findViewById(R.id.meilleurScore);
        score.setText(String.valueOf(meilleurScore));

        Ecouteur ec = new Ecouteur();
        boutonNouvellePartie.setOnClickListener(ec);
    }

    //----------------------------------------------
    // méthode onStop
    //----------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();
        dao.fermerBD();
    }

    //----------------------------------------------
    // classe Ecouteur
    //----------------------------------------------
    private class Ecouteur implements AdapterView.OnClickListener {

        //----------------------------------------------
        // méthode onClick
        //----------------------------------------------
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, JeuActivity.class);
            startActivity(i);
        }
    }
}
