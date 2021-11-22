package com.example.pracadyplomowaproba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    //nazwy plikow shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LOGIN = "login";
    public static final String HASLO = "haslo";
    public static final String ADRES = "adres";
    public static String SWIATLO;
    public static String WENTYL;
    public static String GNIAZDO;

    //pobranie
    private String loadLogin;
    private String loadHaslo;
    private String loadAdres;
    private String loadSwiatlo;
    private String loadWentyl;
    private String loadGniazdo;

    //zapis
    EditText InputLogin;
    EditText InputHaslo;
    EditText InputAdresIP;
    String inputSwiatlo;
    String inputWentyl;
    String inputGniazdo;

    public void ConnectedSSH(String username, String password, String hostname, int port) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        if (session.isConnected()) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputLogin = (EditText) findViewById(R.id.InputLogin);
        InputHaslo = (EditText) findViewById(R.id.inputPassword);
        InputAdresIP = (EditText) findViewById(R.id.InputIP);

        Button ConnectedButton = (Button) findViewById(R.id.ConnectedBtn);
        ConnectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    protected Void doInBackground(Integer... params) {
                        try {
                            ConnectedSSH(InputLogin.getText().toString().trim(), InputHaslo.getText().toString().trim(), InputAdresIP.getText().toString().trim(), 22);
                            saveData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        getWebsite();
        loadData();
        updateViews();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN, InputLogin.getText().toString());
        editor.putString(HASLO, InputHaslo.getText().toString());
        editor.putString(ADRES, InputAdresIP.getText().toString());
        editor.putString(GNIAZDO, inputGniazdo);
        editor.putString(WENTYL, inputWentyl);
        editor.putString(SWIATLO, inputSwiatlo);
        editor.apply();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        loadLogin = sharedPreferences.getString(LOGIN, "");
        loadHaslo = sharedPreferences.getString(HASLO, "");
        loadAdres = sharedPreferences.getString(ADRES, "");
        loadSwiatlo = sharedPreferences.getString(SWIATLO, "");
        loadGniazdo = sharedPreferences.getString(GNIAZDO,"");
        loadWentyl = sharedPreferences.getString(WENTYL,"");

    }
    public void updateViews() {
        InputLogin.setText(loadLogin);
        InputHaslo.setText(loadHaslo);
        InputAdresIP.setText(loadAdres);
    }
    public void getWebsite() {
        Runnable task1 =() -> {
            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(loadLogin, loadAdres, 22);
                session.setPassword(loadHaslo);
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);
                session.connect();

                ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
                channelssh.setCommand("gpio -g read 13\n");
                channelssh.connect();
                BufferedReader input = new BufferedReader(new InputStreamReader(channelssh.getInputStream()));
                String gniazdo = input.readLine();
                if(gniazdo == "1")
                {
                    inputGniazdo = "true";
                }
                else if (gniazdo == "0") {
                    inputGniazdo = "false";
                }
                channelssh.disconnect();

                ChannelExec channelssh1 = (ChannelExec) session.openChannel("exec");
                channelssh1.setCommand("gpio -g read 19\n");
                channelssh1.connect();
                BufferedReader input1 = new BufferedReader(new InputStreamReader(channelssh1.getInputStream()));
                String wentyl = input1.readLine();
                if(wentyl == "1")
                {
                    inputWentyl = "true";
                }
                else if (wentyl == "0") {
                    inputWentyl = "false";
                }
                channelssh1.disconnect();

                ChannelExec channelssh2 = (ChannelExec) session.openChannel("exec");
                channelssh2.setCommand("gpio -g read 26\n");
                channelssh2.connect();
                BufferedReader input2 = new BufferedReader(new InputStreamReader(channelssh2.getInputStream()));
                String swiatlo = input2.readLine();
                if(swiatlo == "1")
                {
                    inputSwiatlo = "true";
                }
                else if (swiatlo == "0") {
                    inputSwiatlo = "false";
                }
                channelssh2.disconnect();
                System.out.println(swiatlo);
                System.out.println(wentyl);
                System.out.println(gniazdo);

            } catch (JSchException | IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(task1).start();
    }

}

