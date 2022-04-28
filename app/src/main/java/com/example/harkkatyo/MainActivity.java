package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createAccount(View v) {
        Intent createaccount = new Intent(this, luotiliActivity.class);
        startActivity(createaccount);
    }

    public void logIn(View v) {
        Intent login = new Intent(this,kirjauduSisaanActivity.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed(){
        ;
    }

}