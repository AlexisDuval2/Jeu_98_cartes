package com.example.a1806165.a98cartes;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class Cartes {

    //----------------------------------------------
    // vecteur de cartes
    //----------------------------------------------
    private static JeuActivity activite_s;
    private static Vector<Carte> liste_s;

    //----------------------------------------------
    // méthode pour mettre 97 cartes dans le vecteur
    //----------------------------------------------
    public static void initialiser(JeuActivity activite) {

        activite_s = activite;
        liste_s = new Vector<>();
        for (int i = 1; i <= Const.nbDeCartes; i++) {
            liste_s.add(new Carte(activite_s, i));
        }
    }

    //----------------------------------------------
    // méthode pour dessiner les 8 cartes initiales
    //----------------------------------------------
    public static void dessinerLesCartesInitiales(Vector<LinearLayout> zoneDeCartesDisponibles) {

        for (int i = 0; i < Const.nbDeCartesAJouer; i++) {
            TextView carteTemp = genererUneCarte().getTextView();
            zoneDeCartesDisponibles.get(i).addView(carteTemp);
        }
    }

    //----------------------------------------------
    // méthode pour générer une nouvelle carte
    //----------------------------------------------
    public static Carte genererUneCarte() {

        Carte carte = null;

        int nbTotalDeCartes = liste_s.size();
        if (nbTotalDeCartes > 0) {
            int index = genererNbAleatoire(0, liste_s.size() - 1);
            carte = liste_s.remove(index);
        }

        return carte;
    }

    //----------------------------------------------
    // méthode pour générer un nombre aléatoire
    //----------------------------------------------
    private static int genererNbAleatoire(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
