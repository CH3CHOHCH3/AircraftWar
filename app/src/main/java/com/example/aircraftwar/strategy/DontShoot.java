package com.example.aircraftwar.strategy;

import com.example.aircraftwar.aircraft.AbstractAircraft;

import java.util.LinkedList;
import java.util.List;

public class DontShoot implements Strategy{
    @Override
    public List<Bullet> shoot(AbstractAircraft aircraft) {
        List<Bullet> res = new LinkedList<>();
        return res;
    }
}
