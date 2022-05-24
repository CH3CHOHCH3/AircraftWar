package com.example.aircraftwar.aircraft;

import com.example.aircraftwar.application.Game;
import com.example.aircraftwar.strategy.DontShoot;
import com.example.aircraftwar.strategy.ScatterShoot;
import com.example.aircraftwar.strategy.Strategy;

import java.util.HashMap;
import java.util.Map;

public class BossEnemy extends AbstractAircraft{
    // 分数
    private int score = 50;
    /** 攻击方式 */
    // 子弹一次发射数量
    private int shootNum = 4;
    // 子弹伤害
    private int power = 5;
    // 子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = 1;

    private boolean multiProp = true;

    // 生成加血道具、火力道具、炸弹道具的概率
    private final double[] easyPropProbability = {1, 1, 1};
    private final double[] middlePropProbability = {0.9, 0.8, 0.7};
    private final double[] hardPropProbability = {0.8, 0.7, 0.6};
    // 把难度映射到概率
    private final Map<String, double[]> probabilitys = new HashMap<String, double[]>();

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        probabilitys.put("EASY", easyPropProbability);
        probabilitys.put("MIDDLE", middlePropProbability);
        probabilitys.put("HARD", hardPropProbability);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Game.windowHeight) {
            vanish();
        }
    }

    @Override
    public int getPower(){
        return this.power;
    }

    @Override
    public int getShootNum(){
        return this.shootNum;
    }

    @Override
    public int getScore(){
        return this.score;
    }

    @Override
    public Strategy context(){
        double flag = Math.random();
        if(flag <= 0.4){
            return new ScatterShoot(3);
        }
        else if(flag <= 0.8){
            return new ScatterShoot(4);
        }
        else {
            return new DontShoot();
        }
    }

    @Override
    public int getDirection(){
        return direction;
    }

    @Override
    public double[] getPropProbability(String difficulty){
        return probabilitys.get(difficulty);
    }

    @Override
    public boolean isMultiProp(){
        return this.multiProp;
    }
}
