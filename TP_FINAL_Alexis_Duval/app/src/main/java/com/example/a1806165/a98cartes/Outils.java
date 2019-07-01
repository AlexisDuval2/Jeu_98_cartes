package com.example.a1806165.a98cartes;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class Outils {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private static JeuActivity activite_s;
    private static Chronometer chrono_s;

    //----------------------------------------------
    // méthode pour initialiser Outils
    //----------------------------------------------
    public static void initialiser(JeuActivity activite) {
        activite_s = activite;
        Algo.setNbDeCartesRestantes(Const.nbDeCartes);
        Algo.setTemps1(SystemClock.elapsedRealtime());
        Algo.setScore(0);
    }

    //----------------------------------------------
    // méthode pour obtenir les LinearLayout de la zone de jeu
    //----------------------------------------------
    public static Vector<LinearLayout> preparerZoneDeJeu() {

        Vector<LinearLayout> zoneDeJeu = new Vector<>();
        LinearLayout[] zones = new LinearLayout[Const.nbDePilesDeCartes];

        zones[0] = activite_s.findViewById(R.id.zoneCarte1);
        zones[1] = activite_s.findViewById(R.id.zoneCarte2);
        zones[2] = activite_s.findViewById(R.id.zoneCarte3);
        zones[3] = activite_s.findViewById(R.id.zoneCarte4);

        for (LinearLayout zone : zones) {
            zoneDeJeu.add(zone);
        }

        return zoneDeJeu;
    }

    //----------------------------------------------
    // méthode pour obtenir les LinearLayout de la zone de cartes disponibles
    //----------------------------------------------
    public static Vector<LinearLayout> preparerZoneDeCartesDisponibles() {

        Vector<LinearLayout> zoneDeCartesDisponibles = new Vector<>();
        LinearLayout[] zones = new LinearLayout[Const.nbDeCartesAJouer];

        zones[0] = activite_s.findViewById(R.id.zCarteAJouer1);
        zones[1] = activite_s.findViewById(R.id.zCarteAJouer2);
        zones[2] = activite_s.findViewById(R.id.zCarteAJouer3);
        zones[3] = activite_s.findViewById(R.id.zCarteAJouer4);
        zones[4] = activite_s.findViewById(R.id.zCarteAJouer5);
        zones[5] = activite_s.findViewById(R.id.zCarteAJouer6);
        zones[6] = activite_s.findViewById(R.id.zCarteAJouer7);
        zones[7] = activite_s.findViewById(R.id.zCarteAJouer8);

        for (LinearLayout zone : zones) {
            zoneDeCartesDisponibles.add(zone);
        }

        return zoneDeCartesDisponibles;
    }

    //----------------------------------------------
    // méthode pour ajouter les écouteurs aux zones de cartes et aux cartes
    //----------------------------------------------
    public static void ajouterLesEcouteurs() {

        Vector<LinearLayout> zoneDeJeu = activite_s.zoneDeJeu();
        Vector<LinearLayout> zoneDeCartesDisponibles = activite_s.zoneDeCartesDisponibles();
        JeuActivity.Ecouteur ec = activite_s.ec();

        for (int i = 0; i < Const.nbDePilesDeCartes; i++) {
            LinearLayout zone = zoneDeJeu.get(i);
            zone.setOnDragListener(ec);
            zone.getChildAt(0).setOnTouchListener(ec);
        }

        for (int i = 0; i < Const.nbDeCartesAJouer; i++) {
            LinearLayout zone = zoneDeCartesDisponibles.get(i);
            zone.setOnDragListener(ec);
            zone.getChildAt(0).setOnTouchListener(ec);
        }
    }

    //----------------------------------------------
    // méthode pour remmettre les images de fond après un drag and drop
    //----------------------------------------------
    public static void afficherImagesDeFond(Vector<LinearLayout> zoneDeJeu) {

        Drawable[] images = new Drawable[Const.nbDePilesDeCartes];
        images[0] = ContextCompat.getDrawable(activite_s, R.drawable.triangle_hg);
        images[1] = ContextCompat.getDrawable(activite_s, R.drawable.triangle_hd);
        images[2] = ContextCompat.getDrawable(activite_s, R.drawable.triangle_bg);
        images[3] = ContextCompat.getDrawable(activite_s, R.drawable.triangle_bd);

        for (int i = 0; i < Const.nbDePilesDeCartes; i++) {
            zoneDeJeu.get(i).setBackground(images[i]);
        }
    }

    //----------------------------------------------
    // méthode pour mettre les zones disponibles en vert
    //----------------------------------------------
    public static void afficherZonesDisponibles(Vector<LinearLayout> zoneDeJeu) {
        for (LinearLayout zone : zoneDeJeu) {
            zone.setBackgroundColor(Const.vert);
        }
    }

    //----------------------------------------------
    // méthode pour afficher la zone qui est touchée
    //----------------------------------------------
    public static void afficherZoneTouchee(Vector<LinearLayout> zoneDeJeu, LinearLayout destination, View v) {
        if (zoneDeJeu.contains(destination)) {
            v.setBackgroundColor(Const.orange);
        }
    }

    //----------------------------------------------
    // méthode pour remettre la couleur bleu dans la zone de cartes disponibles
    //----------------------------------------------
    public static void remettreCouleurInitiale(Vector<LinearLayout> zoneDeCartesDisponibles) {
        for (LinearLayout zone : zoneDeCartesDisponibles) {
            zone.setBackgroundColor(Const.bleu);
        }
    }

    //----------------------------------------------
    // méthode pour afficher le nb de cartes restantes
    //----------------------------------------------
    public static void afficherNbDeCartes() {
        String texte = String.valueOf(Algo.nbDeCartesRestantes());
        TextView nbCartes = activite_s.findViewById(R.id.nbCartes);
        nbCartes.setText(texte);
    }

    //----------------------------------------------
    // méthode pour afficher le nb de cartes restantes
    //----------------------------------------------
    public static void afficherTemps() {
        chrono_s = activite_s.findViewById(R.id.temps);
        chrono_s.setBase(SystemClock.elapsedRealtime());
        chrono_s.start();
    }

    //----------------------------------------------
    // méthode pour afficher le score
    //----------------------------------------------
    public static void afficherScore() {
        String texte = String.valueOf(Algo.score());
        TextView score = activite_s.findViewById(R.id.score);
        score.setText(texte);
    }

    //----------------------------------------------
    // méthode pour accéder au chronomètre
    //----------------------------------------------
    public static Chronometer chrono() {return chrono_s;}
}
