package com.example.aircraftwar.Prop;

import com.example.aircraftwar.application.ItemList;

public class FireProp extends AbstractProp{
    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(){
        ItemList.heroAircraft.changeShootNum(1);
        new Thread(()->{
            try {
                Thread.sleep(5000);
                ItemList.heroAircraft.changeShootNum(-1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
