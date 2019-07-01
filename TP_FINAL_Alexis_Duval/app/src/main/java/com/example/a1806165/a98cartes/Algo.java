package com.example.a1806165.a98cartes;

import android.os.SystemClock;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class Algo {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private static JeuActivity activite_s;
    private static Vector<LinearLayout> zoneDeJeu_s;
    private static JeuActivity.Ecouteur ec_s;

    private static Carte carteTemporaire_s;
    private static int idTemporaire_s;

    private static View carte_s;
    private static LinearLayout parent_s;
    private static LinearLayout destination_s;

    private static int nbDeCartesRestantes_s = Const.nbDeCartes;
    private static int nb_choix_s;
    private static int nb_destination_s;
    private static int score_s;
    private static long temps1_s;
    private static long temps2_s;

    //----------------------------------------------
    // méthode pour initialiser l'algorithme
    //----------------------------------------------
    public static void initialiser(JeuActivity activite, View element, DragEvent evenement) {

        activite_s = activite;
        zoneDeJeu_s = activite.zoneDeJeu();
        ec_s = activite.ec();

        carte_s = (View) evenement.getLocalState();
        parent_s = (LinearLayout) carte_s.getParent();
        destination_s = (LinearLayout) element;
    }

    //----------------------------------------------
    // méthode pour exécuter l'ensemble de l'algorithme du jeu
    //----------------------------------------------
    public static void executer() {

        if (zoneDeJeu_s.contains(destination_s)) {

            if (cartePeutBouger()) {

                carte_s.setVisibility(View.VISIBLE);
                parent_s.removeAllViews();
                destination_s.removeAllViews();
                destination_s.addView(carte_s);

                nbDeCartesRestantes_s--;
                temps2_s = SystemClock.elapsedRealtime();
                calculerScore();

                Carte c = Cartes.genererUneCarte();

                if (c != null) {
                    if (carteTemporaire_s == null) {
                        conserverCarte(c);
                    } else {
                        ajouterDeuxCartes(c);
                    }
                }
            }
        }

        PartieTerminee.verifier(activite_s);
    }

    //----------------------------------------------
    // méthode pour conserver la carte temporaire
    //----------------------------------------------
    private static void conserverCarte(Carte c) {
        idTemporaire_s = parent_s.getId();
        carteTemporaire_s = c;
    }

    //----------------------------------------------
    // méthode pour ajouter deux cartes
    //----------------------------------------------
    private static void ajouterDeuxCartes(Carte nouvelleCarte) {

        LinearLayout zoneCarte1 = activite_s.findViewById(idTemporaire_s);
        TextView ancienneCarte = carteTemporaire_s.getTextView();

        zoneCarte1.addView(ancienneCarte);
        ancienneCarte.setOnTouchListener(ec_s);

        parent_s.addView(nouvelleCarte.getTextView());
        nouvelleCarte.getTextView().setOnTouchListener(ec_s);

        carteTemporaire_s = null;
    }

    //----------------------------------------------
    // méthode pour vérifier si la carte peut bouger
    //----------------------------------------------
    private static boolean cartePeutBouger() {

        TextView t_choix = (TextView) carte_s;
        String texte_choix = (String) t_choix.getText();
        nb_choix_s = Integer.parseInt(texte_choix);

        TextView t_destination = (TextView) destination_s.getChildAt(0);
        String texte_destination = (String) t_destination.getText();
        nb_destination_s = Integer.parseInt(texte_destination);

        boolean sectionQuiMonte = destination_s == zoneDeJeu_s.get(0) || destination_s == zoneDeJeu_s.get(1);
        boolean carteChoisiePlusHauteQueDestination = nb_choix_s > nb_destination_s;
        boolean carteChoisieEst10DeMoins = nb_choix_s == nb_destination_s - 10;
        boolean sectionQuiDescend = destination_s == zoneDeJeu_s.get(2) || destination_s == zoneDeJeu_s.get(3);
        boolean carteChoisiePlusBasseQueDestination = nb_choix_s < nb_destination_s;
        boolean carteChoisieEst10DePlus = nb_choix_s == nb_destination_s + 10;

        return sectionQuiMonte && (carteChoisiePlusHauteQueDestination || carteChoisieEst10DeMoins)
                || sectionQuiDescend && (carteChoisiePlusBasseQueDestination || carteChoisieEst10DePlus);
    }

    //----------------------------------------------
    // méthode pour calculer le score
    //----------------------------------------------
    public static int calculerScore() {

        int nbdeCartesJouees = Const.nbDeCartes - nbDeCartesRestantes_s;

        double pointsPrProximite = Math.floor(1.0 / Math.abs(nb_choix_s - nb_destination_s) * 1500.0);
        double pointsPrVitesse = Math.floor(1.0 / (temps2_s - temps1_s) * 150000.0);
        temps1_s = temps2_s;
        double pointsPrNbDeCartesRestantes = Math.floor(nbdeCartesJouees * 1.25) * 5.0;

        score_s += (int)(pointsPrProximite + pointsPrVitesse + pointsPrNbDeCartesRestantes);

        return score_s;
    }

    //----------------------------------------------
    // méthodes d'accès
    //----------------------------------------------
    public static int nbDeCartesRestantes() {return nbDeCartesRestantes_s;}
    public static int score() {return score_s;}

    //----------------------------------------------
    // méthode de mutation
    //----------------------------------------------
    public static void setTemps1(long tempsInitial) {temps1_s = tempsInitial;}
    public static void setNbDeCartesRestantes(int nb) {nbDeCartesRestantes_s = nb;}
    public static void setScore(int score) {score_s = score;}
}
