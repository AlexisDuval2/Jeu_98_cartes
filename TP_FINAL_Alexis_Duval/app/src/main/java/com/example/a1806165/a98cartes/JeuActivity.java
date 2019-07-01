package com.example.a1806165.a98cartes;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import java.util.Vector;

public class JeuActivity extends AppCompatActivity {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private Vector<LinearLayout> zoneDeJeu;
    private Vector<LinearLayout> zoneDeCartesDisponibles;
    private Dao dao;
    private Ecouteur ec;

    //----------------------------------------------
    // méthode onCreate
    //----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        Cartes.initialiser(JeuActivity.this);
        Outils.initialiser(JeuActivity.this);
        Outils.afficherNbDeCartes();
        Outils.afficherTemps();
        Outils.afficherScore();

        zoneDeJeu = Outils.preparerZoneDeJeu();
        zoneDeCartesDisponibles = Outils.preparerZoneDeCartesDisponibles();
        Cartes.dessinerLesCartesInitiales(zoneDeCartesDisponibles);

        dao = Dao.getInstance(this);
        dao.ouvrirBD();

        ec = new Ecouteur();
        Outils.ajouterLesEcouteurs();
    }

    //----------------------------------------------
    // méthode onStop
    //----------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();
        dao.fermerBD();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(JeuActivity.this, MainActivity.class);
        startActivity(i);
    }

    //----------------------------------------------
    // classe Ecouteur
    //----------------------------------------------
    public class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        //----------------------------------------------
        // méthode onTouch
        //----------------------------------------------
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            LinearLayout parent = (LinearLayout) v.getParent();

            if (zoneDeCartesDisponibles.contains(parent)) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(null, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
            }

            return true;
        }

        //----------------------------------------------
        // méthode onDrag
        //----------------------------------------------
        @Override
        public boolean onDrag(View element, DragEvent evenement) {

            View carte = (View) evenement.getLocalState();
            LinearLayout parent = (LinearLayout) carte.getParent();
            LinearLayout destination = (LinearLayout) element;
            int action = evenement.getAction();

            if (action == DragEvent.ACTION_DRAG_STARTED) {

                carte.setVisibility(View.INVISIBLE);
                Outils.afficherZonesDisponibles(zoneDeJeu);

            } else if (action == DragEvent.ACTION_DRAG_ENTERED) {

                carte.setVisibility(View.INVISIBLE);
                Outils.afficherZoneTouchee(zoneDeJeu, destination, element);

            } else if (action == DragEvent.ACTION_DROP) {

                Algo.initialiser(JeuActivity.this, element, evenement);
                Algo.executer();

            } else if (action == DragEvent.ACTION_DRAG_EXITED) {

                carte.setVisibility(View.INVISIBLE);
                Outils.afficherZonesDisponibles(zoneDeJeu);

            } else if (action == DragEvent.ACTION_DRAG_ENDED) {

                carte.setVisibility(View.VISIBLE);
                Outils.afficherImagesDeFond(zoneDeJeu);
                Outils.remettreCouleurInitiale(zoneDeCartesDisponibles);
                Outils.afficherNbDeCartes();
                Outils.afficherScore();
            }

            return true;
        }
    }

    //----------------------------------------------
    // méthodes d'accès
    //----------------------------------------------
    public Vector<LinearLayout> zoneDeJeu() {return this.zoneDeJeu;}
    public Vector<LinearLayout> zoneDeCartesDisponibles() {return this.zoneDeCartesDisponibles;}
    public Ecouteur ec() {return this.ec;}
}
