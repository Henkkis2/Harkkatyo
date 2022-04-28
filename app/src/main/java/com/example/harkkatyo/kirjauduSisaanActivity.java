package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class kirjauduSisaanActivity extends AppCompatActivity {
    Tietokanta DatabaseUser;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirjaudu_sisaan);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        DatabaseUser = new Tietokanta(this);

    }

    public void openApp(View v) { // If username and password match, then user is allowed to enter app.
        String name = username.getText().toString();
        String word = password.getText().toString();
        Boolean check = DatabaseUser.checkBoth(name, word);
        if (check == true) {
            Intent app = new Intent(this, SovellusActivity.class);
            app.putExtra("user", name);
            startActivity(app);
        } else {
            Toast.makeText(kirjauduSisaanActivity.this, "Kirjautuminen epäonnistui", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View v) { // Takaisin nappi tässä aktiviteetissa.
        finish();
    }

    @Override
    public void onBackPressed(){
        ;
    }
}