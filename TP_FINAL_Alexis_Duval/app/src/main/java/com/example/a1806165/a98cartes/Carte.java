package com.example.a1806165.a98cartes;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

public class Carte {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private JeuActivity activite;
    private int valeur;
    private TextView textView;

    //----------------------------------------------
    // Constructeur
    //----------------------------------------------
    public Carte(JeuActivity activite, int valeur) {

        this.activite = activite;
        this.valeur = valeur;
        this.textView = new TextView(activite);

        modifierStyle();
    }

    //----------------------------------------------
    // méthode pour modifier le style du TextView
    //----------------------------------------------
    private void modifierStyle() {
        textView.setBackground(ContextCompat.getDrawable(activite, R.drawable.carte));
        textView.setWidth(Const.largeurCarte);
        textView.setHeight(Const.hauteurCarte);
        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        textView.setPadding(Const.margeGaucheCarte, 0, 0, 0);
        textView.setText(String.valueOf(valeur));
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(Const.taillePolice);
        textView.setVisibility(View.VISIBLE);
    }

    //----------------------------------------------
    // méthode d'accès
    //----------------------------------------------
    public TextView getTextView() {return textView;}
}
