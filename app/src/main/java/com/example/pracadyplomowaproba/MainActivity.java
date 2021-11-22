package com.example.pracadyplomowaproba;

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
import com.jcraft.jsch.Session;

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
    public static Boolean SWIATLO = false;
    public static Boolean WENTYL = false;
    public static Boolean GNIAZDO = false;

    //pobranie
    private String loadLogin;
    private String loadHaslo;
    private String loadAdres;
    private Boolean loadSwiatlo;
    private Boolean loadWentyl;
    private Boolean loadGniazdo;

    //zapis
    EditText InputLogin;
    EditText InputHaslo;
    EditText InputAdresIP;
    Boolean inputSwiatlo = false;
    Boolean inputWentyl = false;
    Boolean inputGniazdo = false;

    public void ConnectedSSH(String username, String password, String hostname, int port) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");
        channelssh.setCommand("gpio -g read 13\n");
        channelssh.connect();
        BufferedReader input = new BufferedReader(new InputStreamReader(channelssh.getInputStream()));
        String gniazdo = input.readLine().trim();
        while (gniazdo == "1") {
            inputGniazdo = true; }
        channelssh.disconnect();

        ChannelExec channelssh1 = (ChannelExec) session.openChannel("exec");
        channelssh1.setCommand("gpio -g read 19\n");
        channelssh1.connect();
        BufferedReader input1 = new BufferedReader(new InputStreamReader(channelssh1.getInputStream()));
        String wentyl = input1.readLine().trim();
        while (wentyl == "1") {
            inputWentyl = true; }
        channelssh1.disconnect();

        ChannelExec channelssh2 = (ChannelExec) session.openChannel("exec");
        channelssh2.setCommand("gpio -g read 26\n");
        channelssh2.connect();
        BufferedReader input2 = new BufferedReader(new InputStreamReader(channelssh2.getInputStream()));
        String swiatlo = input2.readLine().trim();
        while (swiatlo == "1") {
            inputGniazdo = true; }
        channelssh2.disconnect();

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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        saveData();

                        return null;
                    }
                }.execute(1);
            }
        });

        loadData();
        updateViews();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN, InputLogin.getText().toString());
        editor.putString(HASLO, InputHaslo.getText().toString());
        editor.putString(ADRES, InputAdresIP.getText().toString());
        editor.putBoolean(String.valueOf(GNIAZDO), inputGniazdo);
        editor.putBoolean(String.valueOf(WENTYL), inputWentyl);
        editor.putBoolean(String.valueOf(SWIATLO), inputSwiatlo);
        editor.apply();
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        loadLogin = sharedPreferences.getString(LOGIN, "");
        loadHaslo = sharedPreferences.getString(HASLO, "");
        loadAdres = sharedPreferences.getString(ADRES, "");
        loadSwiatlo = sharedPreferences.getBoolean(String.valueOf(SWIATLO), false);
        loadGniazdo = sharedPreferences.getBoolean(String.valueOf(GNIAZDO),false);
        loadWentyl = sharedPreferences.getBoolean(String.valueOf(WENTYL),false);

    }
    public void updateViews()
    {
        InputLogin.setText(loadLogin);
        InputHaslo.setText(loadHaslo);
        InputAdresIP.setText(loadAdres);
    }
}

