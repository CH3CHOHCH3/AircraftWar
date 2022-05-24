package com.example.aircraftwar.application;

import android.content.Context;

import com.example.aircraftwar.GameActivity;

public class EasyGame extends Game{
    public EasyGame(Context context, GameActivity gameActivity){
        super(context, gameActivity);
    }
    @Override
    public void Init(){
        this.difficulty = "EASY";

        this.isBoss = false;
        this.enemyMaxNumber = 5;
        this.eliteProbability = 0.2;

        this.enemyGenerateCircle = 400;
        this.enemyShootCircle = 300;
    }

}
