package com.example.aircraftwar.aircraft;

import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.strategy.ScatterShoot;
import com.example.aircraftwar.strategy.Strategy;

public class MobEnemy extends AbstractAircraft{
    // 分数
    private int score = 10;


    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Game.windowHeight ) {
            vanish();
        }
    }

    @Override
    public int getShootNum(){
        return 0;
    }

    @Override
    public int getPower(){
        return 0;
    }

    @Override
    public int getScore(){
        return this.score;
    }

    @Override
    public Strategy context(){
        return new ScatterShoot(1);
    }

    @Override
    public int getDirection(){
        return 0;
    }

    @Override
    public double[] getPropProbability(String difficulty){
        double[] probability = {0,0,0};
        return probability;
    }

    @Override
    public boolean isMultiProp(){
        return false;
    }
}
