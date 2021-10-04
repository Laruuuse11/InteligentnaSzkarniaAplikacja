package com.example.pracadyplomowaproba;

import android.content.Intent;
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

    public void ConnectedSSH (String username, String password, String hostname, int port) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();
        if(session.isConnected()) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("WysyłkaIP1",hostname);
            intent.putExtra("WysyłkaLogin1",username);
            intent.putExtra("WysylkaPass1",password);
            session.disconnect();
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText InputLogin = (EditText) findViewById(R.id.InputLogin);
        EditText InputHaslo = (EditText) findViewById(R.id.inputPassword);
        EditText InputAdresIP = (EditText) findViewById(R.id.InputIP);

        String hostnamePobrane = getIntent().getStringExtra("WysyłkaIP2");
        String passwordPobrane = getIntent().getStringExtra("WysylkaPass2");
        String usernamePobrane = getIntent().getStringExtra("WysyłkaLogin2");

        InputLogin.setText(usernamePobrane);
        InputHaslo.setText(passwordPobrane);
        InputAdresIP.setText(hostnamePobrane);

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
                        return null;
                    }
                }.execute(1);
            }
        });
    }
}

