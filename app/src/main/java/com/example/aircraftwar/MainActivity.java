package com.example.aircraftwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view){
        Intent intent = intent = new Intent(this, GameActivity.class);;

        if(view.getId() == R.id.easyButton){
            intent.putExtra("difficulty", "EASY");
        }else if(view.getId() == R.id.middleButton){
            intent.putExtra("difficulty", "MIDDLE");
        }else {
            intent.putExtra("difficulty", "HARD");
        }
        startActivity(intent);
    }
}