package com.example.aircraftwar.application;

import com.example.aircraftwar.Prop.AbstractProp;
import com.example.aircraftwar.aircraft.AbstractAircraft;
import com.example.aircraftwar.aircraft.HeroAircraft;
import com.example.aircraftwar.strategy.Bullet;

import java.util.LinkedList;
import java.util.List;

public class ItemList {
    public static List<AbstractProp> props = new LinkedList<>();
    public static List<Bullet> heroBullets = new LinkedList<>();
    public static List<Bullet> enemyBullets = new LinkedList<>();
    public static List<AbstractAircraft> enemyAircraft = new LinkedList<>();
    public static HeroAircraft heroAircraft;

}
