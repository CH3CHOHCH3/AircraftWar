package com.example.aircraftwar.application;

import android.content.Context;

import com.example.aircraftwar.GameActivity;

public class MiddleGame extends Game{
    public MiddleGame(Context context, GameActivity gameActivity){
        super(context, gameActivity);
    }

    @Override
    public void Init(){
        this.difficulty = "MIDDLE";

        this.isBoss = true;
        this.bossHp = 500;
        this.enemyMaxNumber = 6;
        this.eliteProbability = 0.3;

        this.enemyGenerateCircle = 350;
        this.enemyShootCircle = 250;
    }
}
