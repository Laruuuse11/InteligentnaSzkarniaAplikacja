package com.example.pracadyplomowaproba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    RecyclerView recyclerView;
    String wynikPobieraniaWilgotnosciCzerwony;
    TextView czujnikCzerwony;
    String s1[], s2[];
    int images [] = {R.drawable.pobrane, R.drawable.pobrane1, R.drawable.pobrane2, R.drawable.pobrane3, R.drawable.pobrane4, R.drawable.pobrane5, R.drawable.pobrane6, R.drawable.pobrane7, R.drawable.pobrane8, R.drawable.pobrane9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilgotnosc_gleby);

        s1=getResources().getStringArray(R.array.plants);
        s2=getResources().getStringArray(R.array.opisPodlewania);

        recyclerView = findViewById(R.id.recyclerView);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        czujnikCzerwony = findViewById(R.id.CzerwonyCzujnikData);
        TextView date = findViewById(R.id.DateTextView);

        getWebsite();

        String hostname = getIntent().getStringExtra("WysyłkaIP2");
        String password = getIntent().getStringExtra("WysylkaPass2");
        String username = getIntent().getStringExtra("WysyłkaLogin2");

        if(hostname == null)
        {
            hostname = "192.168.1.40";
            password = "Konie505";
            username = "pi";
        }

        @SuppressLint("SimpleDateFormat")

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        date.setText(dateFormat.format(cal.getTime()));

        Button CofnijBtn = findViewById(R.id.Cofnij);
        String finalHostname = hostname;
        String finalUsername = username;
        String finalPassword = password;
        CofnijBtn.setOnClickListener(view -> {
            Intent intent = new Intent(WilgotnoscGlebyActivity.this,MainActivity2.class);
            intent.putExtra("WysyłkaIP2", finalHostname);
            intent.putExtra("WysyłkaLogin2", finalUsername);
            intent.putExtra("WysylkaPass2", finalPassword);
            startActivity(intent);
        });
     }

    public void getWebsite() {
        @SuppressLint("SetTextI18n") Runnable task1 =() -> {
            try {
                for(int i=1; i>0;i++) {
                    Document doc = Jsoup.connect("http://192.168.1.40/Temps/Api.php").get();
                    Elements h4 = doc.select("h4");
                    for (Element element : h4) {
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