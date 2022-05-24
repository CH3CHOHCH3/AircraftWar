package com.example.aircraftwar.application;

import com.example.aircraftwar.aircraft.AbstractAircraft;
import com.example.aircraftwar.aircraft.BossEnemy;
import com.example.aircraftwar.aircraft.EliteEnemy;
import com.example.aircraftwar.aircraft.MobEnemy;

public class AircraftFactory {
    private double p;

    public AircraftFactory(double p){
        this.p = p;
    }

    public AbstractAircraft createAircraft(){
        AbstractAircraft abstractAircraft = null;
        if (Math.random() <= p){
            abstractAircraft = new EliteEnemy(
                    (int) (Math.random() * (Game.windowWidth - Game.getImage("ELITE_ENEMY_IMAGE").getWidth()))*1,
                    (int) (Math.random() * Game.windowHeight * 0.2)*1,
                    0,
                    10,
                    60
            );
            abstractAircraft.setImage(Game.getImage("ELITE_ENEMY_IMAGE"));
        }
        else {
            abstractAircraft = new MobEnemy(
                    (int) (Math.random() * (Game.windowWidth - Game.getImage("MOB_ENEMY_IMAGE").getWidth()))*1,
                    (int) (Math.random() * Game.windowHeight * 0.2)*1,
                    0,
                    10,
                    30
            );
            abstractAircraft.setImage(Game.getImage("MOB_ENEMY_IMAGE"));
        }
        return abstractAircraft;
    }

    public AbstractAircraft createBoss(int hp){
        AbstractAircraft abstractAircraft = null;
        abstractAircraft = new BossEnemy(
                (int) (Math.random() * (Game.windowWidth - Game.getImage("BOSS_ENEMY_IMAGE").getWidth()))*1,
                (int) (Math.random() * Game.windowHeight * 0.2)*1,
                5,
                0,
                hp
        );
        abstractAircraft.setImage(Game.getImage("BOSS_ENEMY_IMAGE"));
        return abstractAircraft;
    }
}
