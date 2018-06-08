package com.os.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.os.builditbigger.backend.jokerApi.JokerApi;
import com.os.jokeactivity.JokeActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements JokerApiAsyncTask.OnJokerAsyncTaskCompleted{
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button tellAJokeButton = findViewById(R.id.tellAJokeButton);
        tellAJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new JokerApiAsyncTask(MainActivity.this).execute();
            }
        });
    }

    @Override
    public void onTaskCompleted(String result) {
        progressBar.setVisibility(View.INVISIBLE);

        if (result == null) {
            Toast.makeText(MainActivity.this, "Error getting your joke!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_EXTRA, result);
        startActivity(intent);
    }
}
