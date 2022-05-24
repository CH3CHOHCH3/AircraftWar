package com.example.aircraftwar.application;

import com.example.aircraftwar.Prop.AbstractProp;
import com.example.aircraftwar.Prop.BombProp;
import com.example.aircraftwar.Prop.FireProp;
import com.example.aircraftwar.Prop.HpProp;

import java.util.LinkedList;
import java.util.List;

public class PropFactory {
    public PropFactory(){

    }

    public List<AbstractProp> createProp(int locationX, int locationY, int speedX, int speedY, double[] probability, boolean multiProp){
        List<AbstractProp> res = new LinkedList<>();

        double flag = Math.random();
        if(multiProp){
            if(flag <= probability[0]){
                AbstractProp abstractProp = new HpProp(Math.max(Math.min((int)(locationX + (Math.random()*2-1)*50), Game.windowWidth-20),20),
                        (int)(locationY + Math.random()*50), speedX, speedY + 10);
                abstractProp.setImage(Game.getImage("HP_PROP_IMAGE"));
                res.add(abstractProp);
            }
            if(flag <= probability[1]){
                AbstractProp abstractProp = new FireProp(Math.max(Math.min((int)(locationX + (Math.random()*2-1)*50), Game.windowWidth-20),20),
                        (int)(locationY + Math.random()*50), speedX, speedY + 10);
                abstractProp.setImage(Game.getImage("FIRE_PROP_IMAGE"));
                res.add(abstractProp);
            }
            if(flag <= probability[2]){
                AbstractProp abstractProp = new BombProp(Math.max(Math.min((int)(locationX + (Math.random()*2-1)*50), Game.windowWidth-20),20),
                        (int)(locationY + Math.random()*50), speedX, speedY + 10);
                abstractProp.setImage(Game.getImage("BOMB_PROP_IMAGE"));
                res.add(abstractProp);
            }
        }
        else{
            if(flag <= probability[0]){
                AbstractProp abstractProp = new HpProp(locationX, locationY, speedX, speedY);
                abstractProp.setImage(Game.getImage("HP_PROP_IMAGE"));
                res.add(abstractProp);
            }
            else if(flag <= probability[1]){
                AbstractProp abstractProp = new FireProp(locationX, locationY, speedX, speedY);
                abstractProp.setImage(Game.getImage("FIRE_PROP_IMAGE"));
                res.add(abstractProp);
            }
            else if(flag <= probability[2]){
                AbstractProp abstractProp = new BombProp(locationX, locationY, speedX, speedY);
                abstractProp.setImage(Game.getImage("BOMB_PROP_IMAGE"));
                res.add(abstractProp);
            }
        }
        return res;
    }
}
