package com.example.aircraftwar.aircraft;

import android.graphics.BitmapFactory;
import android.view.View;

import com.example.aircraftwar.R;
import com.example.aircraftwar.strategy.ScatterShoot;
import com.example.aircraftwar.strategy.Strategy;

public class HeroAircraft extends AbstractAircraft{
    /** 攻击方式 */
    private int shootNum = 1;     //子弹一次发射数量
    private int power = 20;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：-1，向下发射：1)

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */

    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public void changeShootNum(int shootNum){
        if(this.shootNum != 1 || shootNum != -1){
            this.shootNum += shootNum;
        }
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public int getShootNum(){
        return this.shootNum;
    }

    @Override
    public int getScore(){
        return 0;
    }
    @Override
    public double[] getPropProbability(String difficulty){
        double[] probability = {0,0,0};
        return probability;
    }


    @Override
    public int getDirection(){
        return this.direction;
    }

    @Override
    public Strategy context(){
        return new ScatterShoot(1);
    }

    @Override
    public boolean isMultiProp(){
        return false;
    }
}
