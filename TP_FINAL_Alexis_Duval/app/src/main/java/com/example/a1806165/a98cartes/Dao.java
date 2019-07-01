package com.example.a1806165.a98cartes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper {

    //----------------------------------------------
    // variables
    //----------------------------------------------
    private static Dao instance;
    private static SQLiteDatabase database;

    //----------------------------------------------
    // instance
    //----------------------------------------------
    public static Dao getInstance(Context context) {

        if (instance == null) {
            instance = new Dao(context.getApplicationContext());
        }

        return instance;
    }

    //----------------------------------------------
    // Constructeur
    //----------------------------------------------
    private Dao(Context context) {
        super(context, "db", null, 1);
        ouvrirBD();
    }

    //----------------------------------------------
    // méthode onCreate
    //----------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS scores(_id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER);");
    }

    //----------------------------------------------
    // méthode onUpgrade
    //----------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE FROM scores");
        onCreate(db);
    }

    //----------------------------------------------
    // méthode pour ouvrir la base de données
    //----------------------------------------------
    public void ouvrirBD() {database = this.getWritableDatabase();}

    //----------------------------------------------
    // méthode pour fermer la base de données
    //----------------------------------------------
    public void fermerBD() {database.close();}

    //----------------------------------------------
    // méthode pour insérer un score dans la base de données
    //----------------------------------------------
    public void insererScore(int score) {
        ContentValues temp = new ContentValues();
        temp.put("score", score);
        database.insert("scores", null, temp);
    }

    //----------------------------------------------
    // méthode pour obtenir le meilleur score
    //----------------------------------------------
    public int obtenirMeilleurScore() {

        int resultat = 0;

        String[] colonne = new String[]{ "score" };
        Cursor c = database.query(
                "scores",
                colonne,
                null,
                null,
                null,
                null,
                "score DESC");
        if (c != null) {
            if (c.moveToFirst()) {
                resultat = c.getInt(0);
            }
            c.close();
        }

        return resultat;
    }
}
