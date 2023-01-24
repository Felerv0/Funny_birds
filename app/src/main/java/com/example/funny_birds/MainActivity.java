package com.example.funny_birds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.funny_birds.view.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}