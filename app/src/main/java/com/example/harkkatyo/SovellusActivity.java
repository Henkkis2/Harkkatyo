package com.example.harkkatyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SovellusActivity extends AppCompatActivity {
    TextView text;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sovellus);
        text = findViewById(R.id.userNameShow);
        object1();

        BottomNavigationView BottomBar = findViewById(R.id.bottom_navigation);
        BottomBar.setSelectedItemId(R.id.koti);
        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.koti:
                        return true;
                    case R.id.lista:
                        Intent intent = new Intent(SovellusActivity.this, ToinenActivity.class);
                        intent.putExtra("user", name);
                        startActivity(intent);
                        return true;
                    case R.id.profiili:
                        Intent intent1 = new Intent(SovellusActivity.this, KolmasActivity.class);
                        intent1.putExtra("user", name);
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed(){
        ;
    }

    public void object1() {
        name = getIntent().getStringExtra("user");
        text.setText("Käyttäjä: " + name);
    }

}