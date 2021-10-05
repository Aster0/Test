package me.astero.mlb_redo.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.URL;

import me.astero.mlb_redo.MainActivity;
import me.astero.mlb_redo.R;
import me.astero.mlb_redo.Util;
import me.astero.mlb_redo.database.Database;

public class LoginScreen extends AppCompatActivity {


    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);



        email = findViewById(R.id.email);

        password = findViewById(R.id.password);







    }



    public void onLogin(View view)
    {

        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
        startActivity(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {


                    String results = Database.post("user/login",
                            "\"email\":\"" +  email.getText().toString()
                                    + "\", \"password\":\"" + password.getText().toString() + "\"");


                    System.out.println(results);

                    if(results.contains("invalid"))
                    {
                        Util.showSnackbar(view, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }, "Invalid Credentials");
                    }
                    else
                    {
                        Util.showSnackbar(view, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }, "Login Successfully");


                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}