package com.example.aircraftwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aircraftwar.application.EasyGame;
import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.application.HardGame;
import com.example.aircraftwar.application.MiddleGame;


public class GameActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if("EASY".equals(intent.getStringExtra("difficulty"))){
            game = new EasyGame(this, this);
        }else if("MIDDLE".equals(intent.getStringExtra("difficulty"))){
            game = new MiddleGame(this, this);
        }else{
            game = new HardGame(this, this);
        }
        super.onCreate(savedInstanceState);
        // 不显示标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(game);
    }

}