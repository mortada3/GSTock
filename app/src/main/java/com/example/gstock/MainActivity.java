package com.example.gstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.gstock.SharedHelper.sha256;

public class MainActivity extends AppCompatActivity {
    EditText _txtLogin, _txtPassword;
    Button _btnConnection;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txtLogin = (EditText) findViewById(R.id.txtLogin);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);
        _btnConnection = (Button) findViewById(R.id.btnConnection);

        //Création de la base de données
        db = openOrCreateDatabase("dbexam", MODE_PRIVATE, null);
        //creation de la table "admin"
        db.execSQL("CREATE TABLE IF NOT EXISTS ADMIN (login varchar primary key, password varchar);");
        SQLiteStatement s = db.compileStatement("select count(*) from admin;");
        long c = s.simpleQueryForLong();
        if (c==0){
            db.execSQL("insert into admin (login, password) values (?,?)", new String []{"toto", sha256("12345")});
        }
        _btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strlogin = _txtLogin.getText().toString();
                String strpassword = _txtPassword.getText().toString();


                Cursor cur = db.rawQuery("select password from admin where login=?", new String[] {strlogin});
                try {
                    cur.moveToFirst();
                    String p = cur.getString(0);
                    if (p.equals(sha256(strpassword))){
                        Toast.makeText(getApplicationContext(), "Bienvenue" + strlogin, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), FamilleComposants.class);
                        startActivity(i);
                    }else{
                        _txtLogin.setText("");
                        _txtPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Echec de connexion",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Administrateur inexistant",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}