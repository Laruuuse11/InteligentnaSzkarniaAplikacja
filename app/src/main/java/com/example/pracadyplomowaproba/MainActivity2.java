package com.example.pracadyplomowaproba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

        getWebsite();
        PobranaWilgotnosc = findViewById(R.id.WilgotnoscImport);
        PobranaTemperatura = findViewById(R.id.TemperaturaImport);
        TextView date = findViewById(R.id.DateTextView);
        @SuppressLint("SimpleDateFormat")

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        date.setText(dateFormat.format(cal.getTime()));

        String hostname = getIntent().getStringExtra("WysyłkaIP1");
        String password = getIntent().getStringExtra("WysylkaPass1");
        String username = getIntent().getStringExtra("WysyłkaLogin1");

        if(hostname == null)
        {
            hostname = getIntent().getStringExtra("WysyłkaIP2");
            password = getIntent().getStringExtra("WysylkaPass2");
            username = getIntent().getStringExtra("WysyłkaLogin2");
        }

        Button PrzejscieDoWilgotnosciGleby = findViewById(R.id.WilgotnoscGlebyBtn);
        Button ButtonSwiatloOn = findViewById(R.id.SwiatloOnBtn);
        Button ButtonSwiatloOff = findViewById(R.id.SwiatloOffBtn);
        Button ButtonWyjscie = findViewById(R.id.Wyjscie);
        Button ButtonWentylatorOn = findViewById(R.id.WentylatoryOnBtn);
        Button ButtonWentylatorOff = findViewById(R.id.WentylatoryOffBtn);
        Button ButtonGniazdaOn = findViewById(R.id.GniazdoOnBtn);
        Button ButtonGniazdaOff = findViewById(R.id.GniazdoOffBtn);

        String finalUsername2 = username;
        String finalPassword2 = password;
        String finalHostname2 = hostname;
        ButtonGniazdaOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                       GpioGniazdaOff(finalUsername2, finalPassword2, finalHostname2, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        String finalUsername1 = username;
        String finalPassword1 = password;
        String finalHostname1 = hostname;
        ButtonGniazdaOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioGniazdaOn(finalUsername1, finalPassword1, finalHostname1, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        String finalUsername = username;
        String finalPassword = password;
        String finalHostname = hostname;
        ButtonWentylatorOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioWentylatorOff(finalUsername, finalPassword, finalHostname, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        String finalUsername3 = username;
        String finalPassword3 = password;
        String finalHostname3 = hostname;
        ButtonWentylatorOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioWentylatorOn(finalUsername3, finalPassword3, finalHostname3, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        String finalUsername4 = username;
        String finalPassword4 = password;
        String finalHostname4 = hostname;
        ButtonSwiatloOn.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioSwiatloOn(finalUsername4, finalPassword4, finalHostname4, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));

        String finalUsername5 = username;
        String finalPassword5 = password;
        String finalHostname5 = hostname;
        ButtonSwiatloOff.setOnClickListener(v -> new AsyncTask<Integer, Void, Void>() {
            protected Void doInBackground(Integer... params) {
                try {
                    GpioSwiatloOff(finalUsername5, finalPassword5, finalHostname5, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1));


        String finalHostname6 = hostname;
        String finalUsername6 = username;
        String finalPassword6 = password;
        ButtonWyjscie.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            intent.putExtra("WysyłkaIP2", finalHostname6);
            intent.putExtra("WysyłkaLogin2", finalUsername6);
            intent.putExtra("WysylkaPass2", finalPassword6);
            startActivity(intent);
        });

        String finalHostname7 = hostname;
        String finalUsername7 = username;
        String finalPassword7 = password;
        PrzejscieDoWilgotnosciGleby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, WilgotnoscGlebyActivity.class);
                intent.putExtra("WysyłkaIP2", finalHostname7);
                intent.putExtra("WysyłkaLogin2", finalUsername7);
                intent.putExtra("WysylkaPass2", finalPassword7);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void getWebsite() {
       Runnable task1 =() -> {
            try {
                for(int i=1; i>0;i++) {
                    Document doc = Jsoup.connect("http://192.168.1.40/Temps/Api.php").get();
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
