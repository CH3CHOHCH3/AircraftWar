package com.example.aircraftwar.Prop;

import com.example.aircraftwar.application.ItemList;

public class HpProp extends AbstractProp{
    public HpProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void func(){
        ItemList.heroAircraft.decreaseHp(-30);
    }
}
