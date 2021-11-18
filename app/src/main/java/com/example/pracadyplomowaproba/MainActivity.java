package com.example.pracadyplomowaproba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LOGIN = "login";
    public static final String HASLO = "haslo";
    public static final String ADRES = "adres";
    private String loadLogin;
    private String loadHaslo;
    private String loadAdres;
    EditText InputLogin;
    EditText InputHaslo;
    EditText InputAdresIP;

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
        editor.apply();
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        loadLogin = sharedPreferences.getString(LOGIN, "");
        loadHaslo = sharedPreferences.getString(HASLO, "");
        loadAdres = sharedPreferences.getString(ADRES, "");
    }
    public void updateViews()
    {
        InputLogin.setText(loadLogin);
        InputHaslo.setText(loadHaslo);
        InputAdresIP.setText(loadAdres);
    }
}

