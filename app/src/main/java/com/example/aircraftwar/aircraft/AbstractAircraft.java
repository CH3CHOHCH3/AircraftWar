package com.example.aircraftwar.aircraft;

import com.example.aircraftwar.basic.AbstractFlyingObject;
import com.example.aircraftwar.strategy.Strategy;

abstract public class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
        if(hp >= maxHp){
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }

    public abstract int getPower();

    public abstract int getScore();

    public abstract int getShootNum();

    public abstract int getDirection();

    public abstract Strategy context();

    public abstract double[] getPropProbability(String difficulty);

    public abstract boolean isMultiProp();
}
