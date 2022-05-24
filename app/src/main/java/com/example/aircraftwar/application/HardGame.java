package com.example.aircraftwar.application;

import android.content.Context;

import com.example.aircraftwar.GameActivity;

public class HardGame extends Game{
    public HardGame(Context context, GameActivity gameActivity){
        super(context, gameActivity);
    }

    @Override
    public void Init(){
        this.difficulty = "HARD";

        this.isBoss = true;
        this.bossHp = 500;
        this.enemyMaxNumber = 7;
        this.eliteProbability = 0.4;

        this.enemyGenerateCircle = 350;
        this.enemyShootCircle = 225;
    }
}
