package com.example.a1806165.a98cartes;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class PartieTerminee {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private static JeuActivity activite_s;
    private static Vector<LinearLayout> zoneDeCartesDisponibles_s;
    private static Vector<LinearLayout> zoneDeJeu_s;
    private static int[] cartesDisponibles_int;
    private static int[] pilesDeCartes_int;
    private static boolean[] lesCartesSontBloquees;
    private static Dao dao;

    //----------------------------------------------
    // méthode pour vérifier si la partie est terminée
    //----------------------------------------------
    public static void verifier(JeuActivity activite) {

        activite_s = activite;
        zoneDeCartesDisponibles_s = activite_s.zoneDeCartesDisponibles();
        zoneDeJeu_s = activite_s.zoneDeJeu();

        cartesDisponibles_int = obtenirLesValeursDesCartesDisponibles();
        pilesDeCartes_int = obtenirLesValeursDesPilesDeCartes();

        lesCartesSontBloquees = new boolean[Const.nbDeCartesAJouer];

        for (int i = 0; i < Const.nbDeCartesAJouer; i++) {
            lesCartesSontBloquees[i] = verifierSiCarteEstBloquee(i);
        }

        boolean partieTerminee
                = lesCartesSontBloquees[0]
                && lesCartesSontBloquees[1]
                && lesCartesSontBloquees[2]
                && lesCartesSontBloquees[3]
                && lesCartesSontBloquees[4]
                && lesCartesSontBloquees[5]
                && lesCartesSontBloquees[6]
                && lesCartesSontBloquees[7];

        if (partieTerminee) {
            arreterLeJeu();
        }
    }

    //----------------------------------------------
    // méthode pour arrêter le jeu
    //----------------------------------------------
    private static void arreterLeJeu() {

        for (int i = 0; i < Const.nbDeCartesAJouer; i++) {

            Outils.chrono().stop();

            LinearLayout zone = zoneDeCartesDisponibles_s.get(i);
            zone.setOnDragListener(null);
            TextView tv_c = (TextView) zone.getChildAt(0);
            if (tv_c != null) {
                tv_c.setOnTouchListener(null);
            }
        }

        Toast.makeText(activite_s, Const.textePartieTerminee, Toast.LENGTH_LONG).show();
        dao = Dao.getInstance(activite_s);
        dao.ouvrirBD();
        dao.insererScore(Algo.score());
    }

    //----------------------------------------------
    // méthode pour obtenir les valeurs des cartes disponibles
    //----------------------------------------------
    public static int[] obtenirLesValeursDesCartesDisponibles() {
        int[] valeurs = new int[Const.nbDeCartesAJouer];
        for (int i = 0; i < valeurs.length; i++) {
            LinearLayout zone = zoneDeCartesDisponibles_s.get(i);
            TextView tv_c = (TextView) zone.getChildAt(0);
            if (tv_c != null) {
                String texte = (String) tv_c.getText();
                valeurs[i] = Integer.parseInt(texte);
            } else {
                valeurs[i] = -1;
            }
        }

        return valeurs;
    }

    //----------------------------------------------
    // méthode pour obtenir les valeurs des piles de cartes
    //----------------------------------------------
    public static int[] obtenirLesValeursDesPilesDeCartes() {
        int[] valeurs = new int[Const.nbDePilesDeCartes];
        for (int i = 0; i < valeurs.length; i++) {
            LinearLayout zone = zoneDeJeu_s.get(i);
            TextView tv_zc = (TextView) zone.getChildAt(0);
            String texte = (String) tv_zc.getText();
            valeurs[i] = Integer.parseInt(texte);
        }

        return valeurs;
    }

    //----------------------------------------------
    // méthode pour vérifier si une carte est bloquee
    //----------------------------------------------
    public static boolean verifierSiCarteEstBloquee(int i) {

        boolean resultat;

        boolean carteExiste = cartesDisponibles_int[i] != -1;

        if (!carteExiste) {
            resultat = true;
        } else {
            boolean cartePlusBasseQueZ1 = cartesDisponibles_int[i] < pilesDeCartes_int[0];
            boolean cartePlusBasseQueZ2 = cartesDisponibles_int[i] < pilesDeCartes_int[1];
            boolean cartePlusHauteQueZ3 = cartesDisponibles_int[i] > pilesDeCartes_int[2];
            boolean cartePlusHauteQueZ4 = cartesDisponibles_int[i] > pilesDeCartes_int[3];
            boolean pas10deMoinsQueZ1 = cartesDisponibles_int[i] != pilesDeCartes_int[0] - 10;
            boolean pas10deMoinsQueZ2 = cartesDisponibles_int[i] != pilesDeCartes_int[1] - 10;
            boolean pas10dePlusQueZ3 = cartesDisponibles_int[i] != pilesDeCartes_int[2] + 10;
            boolean pas10dePlusQueZ4 = cartesDisponibles_int[i] != pilesDeCartes_int[3] + 10;

            resultat = cartePlusBasseQueZ1
                    && cartePlusBasseQueZ2
                    && cartePlusHauteQueZ3
                    && cartePlusHauteQueZ4
                    && pas10deMoinsQueZ1
                    && pas10deMoinsQueZ2
                    && pas10dePlusQueZ3
                    && pas10dePlusQueZ4;
        }

        return resultat;
    }
}
