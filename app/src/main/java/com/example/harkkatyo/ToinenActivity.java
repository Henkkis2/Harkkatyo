package com.example.harkkatyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ToinenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Context context = null;
    Elokuva EK;
    Teatterit theat;
    ListView listi;
    ArrayList<Teatterit> listOfTheathers = new ArrayList<>();
    ArrayList<Elokuva> listOfMovies = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toinen);
        context = ToinenActivity.this;
        FileReadSave file = new FileReadSave();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String userName = getIntent().getStringExtra("user");


        ArrayList<String> listofTheat = new ArrayList<>();
        readXML(); // Reads all theathers from XML file, and put those to ArrayList.
        for (int index = 0; index < listOfTheathers.size(); index++){
            theat = listOfTheathers.get(index);
            listofTheat.add(theat.getTeatteriID() + " " + theat.getTeatteriNimi());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listofTheat);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        
        Spinner spinners = findViewById(R.id.teatteriLista);
        spinners.setAdapter(arrayAdapter);
        spinners.setOnItemSelectedListener(this);

        listi = findViewById(R.id.nakyma);
        listi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String movieName = list.get(i);
                file.saveFile(userName, movieName, context);
                Toast.makeText(ToinenActivity.this, "Elokuva: "+ movieName + " on lisätty suosikkeihin.", Toast.LENGTH_LONG).show();
            }
        });

        BottomNavigationView bottomBar = findViewById(R.id.bottom_navigation);
        bottomBar.setSelectedItemId(R.id.lista);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.lista:
                        return true;
                    case R.id.koti:
                        Intent intent = new Intent(ToinenActivity.this, SovellusActivity.class);
                        intent.putExtra("user", userName);
                        startActivity(intent);
                        return true;
                    case R.id.profiili:
                        Intent intent1 = new Intent(ToinenActivity.this, KolmasActivity.class);
                        intent1.putExtra("user", userName);
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });
    }

    public void readXML() {
        try {
            DocumentBuilder builder = null;
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            Theathers(doc);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return;
    }

    public void Theathers(Document doc) {
        NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String ID = element.getElementsByTagName("ID").item(0).getTextContent();
                String place = element.getElementsByTagName("Name").item(0).getTextContent();
                Teatterit theater = new Teatterit(ID, place);
                listOfTheathers.add(theater);
            }
        }
        return;
    }
    @Override
    public void onBackPressed(){
        ;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = adapterView.getItemAtPosition(i).toString();
        String[] cutted = selected.split(" ");
        String ID = cutted[0];
        readProgram(ID);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void readProgram(String ID) {
        list.clear();
        Date date = new Date();
        SimpleDateFormat muoto = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = muoto.format(date);
        Integer number = Integer.valueOf(ID);
        TextView pvm = findViewById(R.id.syotaPV);
        TextView view2 = (TextView) findViewById(R.id.textView2);
        String address = "";
        address = String.valueOf(pvm.getText());

        try {
            DocumentBuilder builder = null;
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            System.out.println(address);
            if (address.isEmpty()) { // Without Date inserted.
                if (number != 1029) {
                    String url = "https://www.finnkino.fi/xml/Schedule/?area=" + ID + "&dt=" + dateString;
                    Document doc = builder.parse(url);
                    doc.getDocumentElement().normalize();
                    parseMovieData(doc);
                    for (int index = 0; index < listOfMovies.size(); index++){
                        EK = listOfMovies.get(index);
                        list.add(EK.getName());
                    }
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    listi.setAdapter(adapter22);
                }
                if (number == 1029) {
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    listi.setAdapter(adapter22);
                }
                view2.setText(dateString);
            } else if (address.isEmpty() == false) { //With date inserted.
                if (number != 1029) {
                    String url = "https://www.finnkino.fi/xml/Schedule/?area=" + ID + "&dt=" + address;
                    Document doc = builder.parse(url);
                    doc.getDocumentElement().normalize();
                    parseMovieData(doc);
                    for (int index = 0; index < listOfMovies.size(); index++){
                        EK = listOfMovies.get(index);
                        list.add(EK.getName());
                    }
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    listi.setAdapter(adapter22);
                }
                if (number == 1029) {
                    ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    listi.setAdapter(adapter22);
                }
                view2.setText(address);
            }
            listOfMovies.clear();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return;
    }
    public void parseMovieData(Document doc) {
        NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("Title").item(0).getTextContent();
                String start = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                String end = element.getElementsByTagName("dttmShowEnd").item(0).getTextContent();
                Date date = null;
                Date date2 = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(start);
                    date2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String startTime = new SimpleDateFormat("HH:mm").format(date);
                String endTime = new SimpleDateFormat("HH:mm").format(date2);
                String dateMovie = new SimpleDateFormat("dd-MM-yyyy").format(date);
                listOfMovies.add(new Elokuva(name, dateMovie, startTime, endTime));
            }
        }
        return;
    }
}
