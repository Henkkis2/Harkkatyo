package com.example.harkkatyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class KolmasActivity extends AppCompatActivity {
    Context context = null;
    TextView text;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolmas);
        context = KolmasActivity.this;
        text = findViewById(R.id.userNameShow2); //Textview for username
        name = getIntent().getStringExtra("user");
        ListView listview = findViewById(R.id.favoriteListView); // ListView for showing favorite movies.
        object1(); // Picks up username from last activity and show it on TextView.

        FileReadSave frs = new FileReadSave();
        ArrayList<String> list = frs.openFile(name, context); // Reads saved favoritemovies from user specific.txt file.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        BottomNavigationView BottomBar = findViewById(R.id.bottom_navigation);
        BottomBar.setSelectedItemId(R.id.profiili);
        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.profiili:
                        return true;
                    case R.id.koti:
                        Intent intent = new Intent(KolmasActivity.this, SovellusActivity.class);
                        intent.putExtra("user", name);
                        startActivity(intent);
                        return true;
                    case R.id.lista:
                        Intent intent1 = new Intent(KolmasActivity.this, ToinenActivity.class);
                        intent1.putExtra("user", name);
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });
    }
    public void object1() {
        text.setText("Käyttäjä: " + name);
    }

    public void logOut (View v) {
        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(exit);
        finish();
    }

    @Override
    public void onBackPressed(){
        ;
    }
}