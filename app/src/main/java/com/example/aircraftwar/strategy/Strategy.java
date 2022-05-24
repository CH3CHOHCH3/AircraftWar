package com.example.aircraftwar.strategy;

import com.example.aircraftwar.aircraft.AbstractAircraft;

import java.util.List;

public interface Strategy {
    public List<Bullet> shoot(AbstractAircraft aircraft);
}
