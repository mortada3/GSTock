package com.example.gstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.gstock.SharedHelper.sha256;

public class FamilleComposants extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout laynaviguer, layrechercher;
    EditText _txtFamille, _txtnom, _txtRechercheFamille;
    ImageButton _btnRecherche;
    Button _btnprems, _btnPrevious, _btnNext, _btnDern;
    Button _btnSauvegarder, _btnSaveComposant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        laynaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layrechercher = (LinearLayout) findViewById(R.id.layRecherche);

        _txtRechercheFamille = (EditText) findViewById(R.id.txtRechercheFamille);
        _txtFamille = (EditText) findViewById(R.id.txtFamille);
        _txtnom = (EditText) findViewById(R.id.txtnom);

        _btnprems = (Button) findViewById(R.id.btnprems);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);
        _btnDern = (Button) findViewById(R.id.btnDern);

        _btnSauvegarder = (Button) findViewById(R.id.btnSauvegarder);
        _btnSaveComposant = (Button) findViewById(R.id.btnSaveComposant);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);


        db = openOrCreateDatabase("dbexam", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS FAMILLECOMPOSANT(id integer primary key autoincrement, nom varchar);");

        laynaviguer.setVisibility(View.INVISIBLE);
        _btnSauvegarder.setVisibility(View.INVISIBLE);


        _btnRecherche.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cur = db.rawQuery("select * from famillecomposant where nom like ?", new String[]{ "%" + _txtRechercheFamille.getText().toString() + "%"});
                try {
                    cur.moveToFirst();
                    _txtnom.setText(cur.getString(1));
                    if (cur.getCount() == 1){
                        laynaviguer.setVisibility(View.INVISIBLE);
                    }else {
                        laynaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aucun resultat !", Toast.LENGTH_SHORT).show();
                    _txtnom.setText("");
                    laynaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnSauvegarder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                db.execSQL("insert into famillecomposant values (?,?)", new String []{sha256("1"),  "pile" });
                laynaviguer.setVisibility(View.VISIBLE);
                _btnSauvegarder.setVisibility(View.VISIBLE);
            }
        });

        _btnSaveComposant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Composants.class);
                startActivity(i);
            }
        });
    }
}