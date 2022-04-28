package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class luotiliActivity extends AppCompatActivity {
    Tietokanta DatabaseUser;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luotili);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        DatabaseUser = new Tietokanta(this);

    }
    public void createAccount(View v) {
        String name = username.getText().toString();
        String word = password.getText().toString();
        Boolean checkuser = DatabaseUser.checkNameOnly(name);

        if (checkuser == false) {
            Boolean insert = DatabaseUser.createUser(name, word);
            if (insert == true){
                Toast.makeText(this, "Käyttäjätilin luonti onnistui!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Käyttäjätilin luonti epäonnistui", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Käyttäjätili on jo olemassa", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onBackPressed(){
        ;
    }
}