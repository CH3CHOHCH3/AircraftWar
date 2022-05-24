package com.example.aircraftwar.Prop;

import com.example.aircraftwar.aircraft.AbstractAircraft;
import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.application.ItemList;
import com.example.aircraftwar.strategy.Bullet;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(){
        for (AbstractAircraft enemyAircraft : ItemList.enemyAircraft) {
            enemyAircraft.decreaseHp(200);
        }
        for (Bullet enemyBullet : ItemList.enemyBullets) {
            enemyBullet.vanish();
        }
    }
}
