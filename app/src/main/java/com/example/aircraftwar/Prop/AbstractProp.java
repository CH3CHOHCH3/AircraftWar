package com.example.aircraftwar.Prop;

import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject{
    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if(locationX <= 0 || locationX >= Game.windowWidth){
            vanish();
        }

        // 判定 y 轴出界
        if(speedY > 0 && locationY >= Game.windowHeight){
            // 向下飞行出界
            vanish();
        }else if(locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    /**
     * 道具功能
     */
    public abstract void func();
}
