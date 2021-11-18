package com.example.pracadyplomowaproba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class MainActivity2 extends AppCompatActivity {
    String   wynikPobieraniaTemp;
    TextView PobranaTemperatura;
    String   wynikPobieraniaWilg;
    TextView PobranaWilgotnosc;

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

        getWebsite();
        PobranaWilgotnosc = findViewById(R.id.WilgotnoscImport);
        PobranaTemperatura = findViewById(R.id.TemperaturaImport);
        TextView date = findViewById(R.id.DateTextView);
        @SuppressLint("SimpleDateFormat")

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        date.setText(dateFormat.format(cal.getTime()));

        Button PrzejscieDoWilgotnosciGleby = findViewById(R.id.WilgotnoscGlebyBtn);
        Button ButtonSwiatloOn = findViewById(R.id.SwiatloOnBtn);
        Button ButtonSwiatloOff = findViewById(R.id.SwiatloOffBtn);
        Button ButtonWyjscie = findViewById(R.id.Wyjscie);
        Button ButtonWentylatorOn = findViewById(R.id.WentylatoryOnBtn);
        Button ButtonWentylatorOff = findViewById(R.id.WentylatoryOffBtn);
        Button ButtonGniazdaOn = findViewById(R.id.GniazdoOnBtn);
        Button ButtonGniazdaOff = findViewById(R.id.GniazdoOffBtn);

        ButtonGniazdaOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                       GpioGniazdaOff(loadLogin, loadHaslo, loadAdres, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonGniazdaOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioGniazdaOn(loadLogin, loadHaslo, loadAdres, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonWentylatorOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioWentylatorOff(loadLogin, loadHaslo, loadAdres, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonWentylatorOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioWentylatorOn(loadLogin, loadHaslo, loadAdres, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonSwiatloOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioSwiatloOn(loadLogin, loadHaslo, loadAdres, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        ButtonSwiatloOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioSwiatloOff(loadLogin, loadHaslo, loadAdres, 22);
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
                for(int i=1; i>0;i++) {
                    Document doc = Jsoup.connect("http://"+ loadAdres +"/Temps/Api.php").get();
                    Elements h2 = doc.select("h2");
                    Elements h3 = doc.select("h3");
                    for (Element element : h2) {
                        wynikPobieraniaTemp = element.text();
                    }
                    for (Element element : h3) {
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
