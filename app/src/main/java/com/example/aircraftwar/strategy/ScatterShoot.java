package com.example.aircraftwar.strategy;

import com.example.aircraftwar.aircraft.AbstractAircraft;
import com.example.aircraftwar.aircraft.HeroAircraft;
import com.example.aircraftwar.application.Game;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements Strategy {
    private int deltaX;

    public ScatterShoot(int deltaX){
        this.deltaX = deltaX;
    }

    @Override
    public List<Bullet> shoot(AbstractAircraft aircraft) {
        List<Bullet> res = new LinkedList<>();
        int direction = aircraft.getDirection();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction * 2;
        int speedY = aircraft.getSpeedY() + direction * 15;
        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            Bullet bullet = new Bullet(
                    x + (i * 2 - shootNum + 1) * 10, y,
                    (i * 2 - shootNum + 1) * deltaX,
                    speedY, power);
            if(aircraft instanceof HeroAircraft){
                bullet.setImage(Game.getImage("HERO_BULLET_IMAGE"));
            }
            else{
                bullet.setImage(Game.getImage("ENEMY_BULLET_IMAGE"));
            }
            res.add(bullet);
        }
        return res;
    }
}
