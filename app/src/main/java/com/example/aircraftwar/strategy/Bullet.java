package com.example.aircraftwar.strategy;

import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.basic.AbstractFlyingObject;

public class Bullet extends AbstractFlyingObject {
    private int power = 10;
    public Bullet(int locationX, int locationY, int speedX, int speedY, int power){
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward(){
        super.forward();

        // 判定 x 轴出界
        if(locationX <= 0 || locationX >= Game.windowWidth){
            vanish();
            // 判定 y 轴出界
            if(speedY > 0 && locationY >= Game.windowHeight ){
                // 向下飞行出界
                vanish();
            }else if (locationY <= 0){
                // 向上飞行出界
                vanish();
            }
        }
    }

    public int getPower(){
        return power;
    }
}
