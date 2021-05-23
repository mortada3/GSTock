package com.example.gstock;

import androidx.appcompat.app.AppCompatActivity;

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

public class Composants extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    EditText _txtName, _txtDate, _txtQuantite, _txtSearch;
    ImageButton _btnSearch;
    Button _btnPrec, _btnSave, _btnSuiv;
    LinearLayout laySearch, layNavig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composants);

        layNavig = (LinearLayout) findViewById(R.id.layNavig);
        laySearch = (LinearLayout) findViewById(R.id.laySearch);

        _txtName = (EditText) findViewById(R.id.txtName);
        _txtDate = (EditText) findViewById(R.id.txtDate);
        _txtQuantite = (EditText) findViewById(R.id.txtQuantite);
        _txtSearch = (EditText) findViewById(R.id.txtSearch);


        _btnPrec = (Button) findViewById(R.id.btnPrec);
        _btnSave = (Button) findViewById(R.id.btnSave);
        _btnSuiv = (Button) findViewById(R.id.btnSuiv);



        _btnSearch = (ImageButton) findViewById(R.id.btnSearch);

        db = openOrCreateDatabase("dbexam", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS COMPOSANT(id integer primary key autoincrement, nom varchar, dateAcquisition date, quantite integer);");


        _btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = db.rawQuery("select * from composant where nom like ?", new String[]{ "%" + _txtSearch.getText().toString() + "%"});
                try {
                    cur.moveToFirst();
                    _txtName.setText(cur.getString(1));
                    if (cur.getCount() == 1){
                        layNavig.setVisibility(View.INVISIBLE);
                    }else {
                        layNavig.setVisibility(View.VISIBLE);
                        _btnPrec.setEnabled(false);
                        _btnSuiv.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "aucun resultat !", Toast.LENGTH_SHORT).show();
                    _txtName.setText("");
                    layNavig.setVisibility(View.INVISIBLE);
                }
            }
        });


        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("insert into composant values (?,?,?,?)", new String []{sha256("1"),  "Arduino uno R2", "15/05/2021", "20" });
                layNavig.setVisibility(View.VISIBLE);
                _btnSave.setVisibility(View.VISIBLE);
            }
        });

    }
}