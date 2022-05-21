package com.example.pracadyplomowaproba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


@SuppressWarnings("ALL")
public class MainActivity2 extends AppCompatActivity {
    String   wynikPobieraniaTemp;
    TextView PobranaTemperatura;
    String   wynikPobieraniaWilg;
    TextView PobranaWilgotnosc;
    String   stanSwiatla;
    String   stanWentyl;
    String   stanGniazdo;
    Switch   swiatloSW;
    Switch   wentylSW;
    Switch   gniazdoSW;

    private String loadLogin;
    private String loadHaslo;
    private String loadAdres;

    public void GpioSwiatloOff(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 26 out\ngpio -g write 26 0");
        channelssh.connect();
        channelssh.disconnect();
    }
    public void GpioSwiatloOn(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 26 out\ngpio -g write 26 1");
        channelssh.connect();
        channelssh.disconnect();
    }
    public void GpioWentylatorOff(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 19 out\ngpio -g write 19 0");
        channelssh.connect();
        channelssh.disconnect();
    }
    public void GpioWentylatorOn(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 19 out\ngpio -g write 19 1");
        channelssh.connect();
        channelssh.disconnect();
    }
    public void GpioGniazdaOff(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 13 out\ngpio -g write 13 0");
        channelssh.connect();
        channelssh.disconnect();
    }
    public void GpioGniazdaOn(String username, String password, String hostname, int port) throws Exception {

        JSch jsch = new JSch();
        Session session = jsch.getSession(username,hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g mode 13 out\ngpio -g write 13 1");
        channelssh.connect();
        channelssh.disconnect();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        loadLogin = sharedPreferences.getString(MainActivity.LOGIN, "");
        loadHaslo = sharedPreferences.getString(MainActivity.HASLO, "");
        loadAdres = sharedPreferences.getString(MainActivity.ADRES, "");
        swiatloSW = findViewById(R.id.switchSwiatlo);
        gniazdoSW = findViewById(R.id.switchGniazdo);
        wentylSW = findViewById(R.id.switchWentyl);

        getWebsite();

        PobranaWilgotnosc = findViewById(R.id.WilgotnoscImport);
        PobranaTemperatura = findViewById(R.id.TemperaturaImport);
        TextView date = findViewById(R.id.DateTextView);
        @SuppressLint("SimpleDateFormat")

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        date.setText(dateFormat.format(cal.getTime()));

        Button PrzejscieDoWilgotnosciGleby = findViewById(R.id.WilgotnoscGlebyBtn);
        Button ButtonWyjscie = findViewById(R.id.Wyjscie);

        gniazdoSW.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    if(gniazdoSW.isChecked()) {
                        GpioGniazdaOn(loadLogin, loadHaslo, loadAdres, 22);
                    }
                    else
                    {
                        GpioGniazdaOff(loadLogin, loadHaslo, loadAdres, 22);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        wentylSW.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    if(wentylSW.isChecked()) {
                        GpioWentylatorOn(loadLogin, loadHaslo, loadAdres, 22);
                    }
                    else
                    {
                        GpioWentylatorOff(loadLogin, loadHaslo, loadAdres, 22);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));


        swiatloSW.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    if(swiatloSW.isChecked()) {
                        GpioSwiatloOn(loadLogin, loadHaslo, loadAdres, 22);
                    }
                    else
                    {
                        GpioSwiatloOff(loadLogin, loadHaslo, loadAdres, 22);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonWyjscie.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            startActivity(intent);
        });

        PrzejscieDoWilgotnosciGleby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, WilgotnoscGlebyActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void getWebsite() {
        Runnable task1 =() -> {
            try {
                Document doc = Jsoup.connect("http://"+ loadAdres +"/Temps/Api.php").get();
                Elements h4 = doc.select("h4");
                Elements h5 = doc.select("h5");
                Elements h6 = doc.select("h6");

                for (Element element : h4) {
                    stanSwiatla = element.text();
                }
                for (Element element : h5) {
                    stanWentyl = element.text();
                }
                for (Element element : h6) {
                    stanGniazdo = element.text();
                }
                if(stanWentyl.equals("1"))
                {
                    runOnUiThread(()-> wentylSW.setChecked(true));
                }
                else if(stanWentyl.equals("0"))
                {
                    runOnUiThread(()-> wentylSW.setChecked(false));
                }
                if(stanSwiatla.equals("1"))
                {
                    runOnUiThread(()-> swiatloSW.setChecked(true));
                }
                else if(stanSwiatla.equals("0"))
                {
                    runOnUiThread(()-> swiatloSW.setChecked(false));
                }
                if(stanGniazdo.equals("1"))
                {
                    runOnUiThread(()-> gniazdoSW.setChecked(true));
                }
                else if(stanGniazdo.equals("0"))
                {
                    runOnUiThread(()-> gniazdoSW.setChecked(false));
                }

                for(int i=1; i>0;i++) {
                    Document doc2 = Jsoup.connect("http://"+ loadAdres +"/Temps/Api.php").get();
                    Elements h1 = doc2.select("h1");
                    Elements h2 = doc2.select("h2");

                    for (Element element : h1) {
                        wynikPobieraniaTemp = element.text();
                    }
                    for (Element element : h2) {
                        wynikPobieraniaWilg = element.text();
                    }
                    runOnUiThread(() -> PobranaTemperatura.setText(wynikPobieraniaTemp + " °C"));
                    runOnUiThread(()-> PobranaWilgotnosc.setText(wynikPobieraniaWilg + " %"));

                    Thread.sleep(1000);
                }

            } catch (IOException | InterruptedException e) {
                PobranaTemperatura.setText("Nie udało się pobrać temperatury");
                PobranaWilgotnosc.setText("Nie udało się pobrać wilgotności");
            }
        };
       new Thread(task1).start();
    }
}

