package com.example.pracadyplomowaproba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WilgotnoscGlebyActivity extends AppCompatActivity {

    private String loadAdres;
    RecyclerView recyclerView;
    String wynikPobieraniaWilgotnosciCzerwony;
    TextView czujnikCzerwony;
    String s1[], s2[];
    int images [] = {R.drawable.pobrane, R.drawable.pobrane1, R.drawable.pobrane2, R.drawable.pobrane3, R.drawable.pobrane4, R.drawable.pobrane5, R.drawable.pobrane6, R.drawable.pobrane7, R.drawable.pobrane8, R.drawable.pobrane9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilgotnosc_gleby);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        loadAdres = sharedPreferences.getString(MainActivity.ADRES, "");

        s1=getResources().getStringArray(R.array.plants);
        s2=getResources().getStringArray(R.array.opisPodlewania);

        recyclerView = findViewById(R.id.recyclerView);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        czujnikCzerwony = findViewById(R.id.CzerwonyCzujnikData);
        TextView date = findViewById(R.id.DateTextView);

        getWebsite();

        @SuppressLint("SimpleDateFormat")

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        date.setText(dateFormat.format(cal.getTime()));

        Button CofnijBtn = findViewById(R.id.Cofnij);
        CofnijBtn.setOnClickListener(view -> {
            Intent intent = new Intent(WilgotnoscGlebyActivity.this,MainActivity2.class);
            startActivity(intent);
        });
     }

    public void getWebsite() {
        @SuppressLint("SetTextI18n") Runnable task1 =() -> {
            try {
                for(int i=1; i>0;i++) {
                    Document doc = Jsoup.connect("http://"+loadAdres+"/Temps/Api.php").get();
                    Elements h3 = doc.select("h3");
                    for (Element element : h3) {
                            wynikPobieraniaWilgotnosciCzerwony = element.text();
                    }
                    runOnUiThread(()-> czujnikCzerwony.setText(wynikPobieraniaWilgotnosciCzerwony + " %"));
                    Thread.sleep(1000);
                }

            } catch (IOException | InterruptedException e) {
                czujnikCzerwony.setText("Nie udało się pobrać temperatury");
            }
        };
        new Thread(task1).start();
    }
}