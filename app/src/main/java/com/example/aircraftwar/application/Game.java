package com.example.aircraftwar.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aircraftwar.GameActivity;
import com.example.aircraftwar.Prop.AbstractProp;
import com.example.aircraftwar.Prop.BombProp;
import com.example.aircraftwar.Prop.FireProp;
import com.example.aircraftwar.R;
import com.example.aircraftwar.aircraft.AbstractAircraft;
import com.example.aircraftwar.aircraft.BossEnemy;
import com.example.aircraftwar.aircraft.HeroAircraft;
import com.example.aircraftwar.basic.AbstractFlyingObject;
import com.example.aircraftwar.strategy.Bullet;
import com.example.aircraftwar.strategy.Strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static Bitmap EASY_BACKGROUND_IMAGE;
    public static Bitmap MIDDLE_BACKGROUND_IMAGE;
    public static Bitmap HARD_BACKGROUND_IMAGE;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap HP_PROP_IMAGE;
    public static Bitmap FIRE_PROP_IMAGE;
    public static Bitmap BOMB_PROP_IMAGE;
    private final static Map<String, Bitmap> maps = new HashMap<>();

    private MediaPlayer bgmPlayer = null;
    private MediaPlayer bgmBossPlayer = null;
    MediaPlayer bulletHitPlayer = null;

    // 游戏难度
    protected String difficulty;
    // 用于背景滚动
    private int backGroundTop = 0;
    // 背景图片
    private Bitmap backGroundImg;
    // 屏幕尺寸
    public static int windowWidth;
    public static int windowHeight;
    // 用于surfaceview的控制
    private SurfaceHolder holder;
    public boolean isDrawing;
    private Canvas canvas;
    private final GameActivity gameActivity;

    // 总分
    private int score = 0;

    // 最大敌机数量
    protected int enemyMaxNumber = 5;
    // 精英敌机产生概率
    protected double eliteProbability = 0.4;

    // 时间戳
    private int tick = 0;
    // 敌机生成周期
    protected int enemyGenerateCircle = 300;
    // 敌机射击周期
    protected int enemyShootCircle = 300;
    // 英雄机射击周期
    protected int heroShootCircle = 75;
    // 是否生成boss
    protected boolean isBoss = true;
    // boss生成的分数阈值
    protected int bossGenerateCircle = 500;
    // boss血量
    protected int bossHp = 500;
    // boss计数
    private int bossCnt = 0;

    // 工厂
    AircraftFactory aircraftFactory = new AircraftFactory(eliteProbability);
    PropFactory propFactory = new PropFactory();

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        if(bgmBossPlayer != null){
            bgmBossPlayer.stop();
            bgmBossPlayer.reset();
            bgmBossPlayer.release();
            bgmBossPlayer = null;
        }
        if(bgmPlayer != null){
            bgmPlayer.stop();
            bgmPlayer.reset();
            bgmPlayer.release();
            bgmPlayer = null;
        }
        new Thread(()->{
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.game_over);
            mediaPlayer.start();
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }).start();

        ItemList.heroBullets.clear();
        ItemList.enemyBullets.clear();
        ItemList.enemyAircraft.clear();
        ItemList.props.clear();
        isDrawing = false;
    }

    public Game(Context context, GameActivity gameActivity){
        super(context);
        this.gameActivity = gameActivity;

        Init();
        InitView();
    }
    // 初始化视图
    private void InitView(){
        // 初始化holder
        holder = getHolder();
        holder.addCallback(this);

        EASY_BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        MIDDLE_BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        HARD_BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg3);
        HERO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite);
        BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
        HP_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_blood);
        FIRE_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bullet);
        BOMB_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bomb);

        // 将背景图片放缩到屏幕大小
        DisplayMetrics dm = getResources().getDisplayMetrics();
        windowWidth = dm.widthPixels;
        windowHeight = dm.heightPixels;

        int width = EASY_BACKGROUND_IMAGE.getWidth();
        int height = EASY_BACKGROUND_IMAGE.getHeight();

        float scaleX = ((float) windowWidth) / width;
        float scaleY = ((float) windowHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);

        EASY_BACKGROUND_IMAGE = Bitmap.createBitmap(EASY_BACKGROUND_IMAGE, 0, 0, width, height, matrix, true);
        MIDDLE_BACKGROUND_IMAGE = Bitmap.createBitmap(MIDDLE_BACKGROUND_IMAGE, 0, 0, width, height, matrix, true);
        HARD_BACKGROUND_IMAGE = Bitmap.createBitmap(HARD_BACKGROUND_IMAGE, 0, 0, width, height, matrix, true);

        maps.put("HERO_IMAGE", HERO_IMAGE);
        maps.put("MOB_ENEMY_IMAGE", MOB_ENEMY_IMAGE);
        maps.put("ELITE_ENEMY_IMAGE", ELITE_ENEMY_IMAGE);
        maps.put("BOSS_ENEMY_IMAGE", BOSS_ENEMY_IMAGE);
        maps.put("HERO_BULLET_IMAGE", HERO_BULLET_IMAGE);
        maps.put("ENEMY_BULLET_IMAGE", ENEMY_BULLET_IMAGE);
        maps.put("HP_PROP_IMAGE", HP_PROP_IMAGE);
        maps.put("FIRE_PROP_IMAGE", FIRE_PROP_IMAGE);
        maps.put("BOMB_PROP_IMAGE", BOMB_PROP_IMAGE);
        maps.put("EASY_BACKGROUND_IMAGE", EASY_BACKGROUND_IMAGE);
        maps.put("MIDDLE_BACKGROUND_IMAGE", MIDDLE_BACKGROUND_IMAGE);
        maps.put("HARD_BACKGROUND_IMAGE", HARD_BACKGROUND_IMAGE);

        // 选择背景图片
        backGroundImg = getImage(difficulty + "_BACKGROUND_IMAGE");
        ItemList.heroAircraft = new HeroAircraft(windowWidth/2, windowHeight/2, 0, 0, 100);
        // 设定英雄机图片
        ItemList.heroAircraft.setImage(getImage("HERO_IMAGE"));
    }
    // 初始化难度
    public abstract void Init();

    public static Bitmap getImage(String name){
        return maps.get(name);
    }

    // 控制英雄机移动
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(x > 0 && x < windowWidth && y > 0 && y < windowHeight){
            ItemList.heroAircraft.setLocation(x, y);
        }
        return true;
    }

    private void drawList(Canvas canvas, List<? extends AbstractFlyingObject> objects){
        for(AbstractFlyingObject abstractObject : objects){
            canvas.drawBitmap(abstractObject.getImage(), abstractObject.getLocationX() - ((float)abstractObject.getWidth() /2),
                    abstractObject.getLocationY() - ((float)abstractObject.getWidth() /2), null);
        }
    }

    private void draw(){
        try {

            canvas = holder.lockCanvas();
            if(canvas != null){
                // 绘制背景图片
                canvas.drawBitmap(backGroundImg, 0, -backGroundTop, null);
                canvas.drawBitmap(backGroundImg, 0, windowHeight - backGroundTop, null);
                backGroundTop += 1;
                if (backGroundTop == windowHeight) {
                    backGroundTop = 0;
                }

                // 绘制子弹
                drawList(canvas, ItemList.heroBullets);
                drawList(canvas, ItemList.enemyBullets);
                // 绘制敌机
                drawList(canvas, ItemList.enemyAircraft);
                // 绘制英雄机
                canvas.drawBitmap(ItemList.heroAircraft.getImage(), ItemList.heroAircraft.getLocationX() - ((float) ItemList.heroAircraft.getWidth() / 2),
                        ItemList.heroAircraft.getLocationY() - ((float) ItemList.heroAircraft.getHeight() / 2), null);
                // 绘制道具
                drawList(canvas, ItemList.props);

                // 显示分数
                int x = 20;
                int y = 50;

                Paint paint=new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(12);
                paint.setTextSize(50);
                canvas.drawText("SCORE:" + score, x, y, paint);
                canvas.drawText("HP:" + ItemList.heroAircraft.getHp(), x, y + 50, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void enemyGenerate(){
        if(ItemList.enemyAircraft.size() < enemyMaxNumber){
            ItemList.enemyAircraft.add(aircraftFactory.createAircraft());
            if(isBoss  && score >= bossGenerateCircle * bossCnt){
                bossCnt++;
                if(bgmBossPlayer == null){
                    bgmBossPlayer = MediaPlayer.create(getContext(), R.raw.bgm_boss);
                    bgmBossPlayer.setLooping(true);
                }
                bgmBossPlayer.start();
                if(bgmPlayer != null){
                    bgmPlayer.stop();
                    bgmPlayer.reset();
                    bgmPlayer.release();
                    bgmPlayer = null;
                }
                ItemList.enemyAircraft.add(aircraftFactory.createBoss(bossHp));
            }
        }
    }

    private void enemyShoot(){
        for(AbstractAircraft enemyAircraft : ItemList.enemyAircraft){
            Strategy shootStrategy = enemyAircraft.context();
            ItemList.enemyBullets.addAll(shootStrategy.shoot(enemyAircraft));
        }
    }

    private void heroShoot(){
        Strategy shootStrategy = ItemList.heroAircraft.context();
        ItemList.heroBullets.addAll(shootStrategy.shoot(ItemList.heroAircraft));
    }

    private void move(){
        for (Bullet bullet : ItemList.heroBullets) {
            bullet.forward();
        }
        for (Bullet bullet : ItemList.enemyBullets) {
            bullet.forward();
        }
        for (AbstractAircraft enemyAircraft : ItemList.enemyAircraft) {
            enemyAircraft.forward();
        }
        for (AbstractProp abstractProp : ItemList.props){
            abstractProp.forward();
        }
    }


    private void crashCheckAction(){
        // 敌机子弹攻击英雄
        for (Bullet bullet : ItemList.enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (ItemList.heroAircraft.notValid()) {
                // 已被其他子弹击毁，不再检测
                // 避免多个子弹重复击毁英雄的判定
                continue;
            }
            if (ItemList.heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                ItemList.heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }


        for(AbstractAircraft enemyAircraft : ItemList.enemyAircraft){
            for(Bullet bullet : ItemList.heroBullets) {
                if (bullet.notValid()) {
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();

                    new Thread(()->{
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bullet_hit);
                        mediaPlayer.start();
                        try {
                            Thread.sleep(500);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }).start();
                }
            }
            // 英雄机 与 敌机 相撞，均损毁
            if (enemyAircraft.crash(ItemList.heroAircraft) || ItemList.heroAircraft.crash(enemyAircraft)) {
                enemyAircraft.vanish();
                ItemList.heroAircraft.decreaseHp(Integer.MAX_VALUE);
            }
        }
    }

    private void postProcessAction(){
        for(AbstractProp prop : ItemList.props) {
            if((prop.crash(ItemList.heroAircraft) || ItemList.heroAircraft.crash(prop))) {
                if (prop instanceof BombProp) {
                    new Thread(() -> {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bomb_explosion);
                        mediaPlayer.start();
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }).start();
                } else if (prop instanceof FireProp) {
                    new Thread(() -> {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bullet);
                        mediaPlayer.start();
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }).start();
                } else {
                    new Thread(() -> {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.get_supply);
                        mediaPlayer.start();
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }).start();
                }
                prop.func();
                prop.vanish();
            }
        }

        for(AbstractAircraft enemyAircraft : ItemList.enemyAircraft){
            if(enemyAircraft.notValid()){
                if(enemyAircraft instanceof BossEnemy){
                    if(bgmBossPlayer != null){
                        bgmBossPlayer.stop();
                        bgmBossPlayer.reset();
                        bgmBossPlayer.release();
                        bgmBossPlayer = null;
                    }
                    if(bgmPlayer == null){
                        bgmPlayer = MediaPlayer.create(getContext(), R.raw.bgm);
                        bgmPlayer.setLooping(true);
                    }
                    bgmPlayer.start();
                }
                List<AbstractProp> abstractProps = propFactory.createProp(
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY(),
                        0,
                        enemyAircraft.getSpeedY(),
                        enemyAircraft.getPropProbability(difficulty),
                        enemyAircraft.isMultiProp());
                ItemList.props.addAll(abstractProps);
                score += enemyAircraft.getScore();
            }
        }
        ItemList.enemyAircraft.removeIf(AbstractFlyingObject::notValid);
        ItemList.enemyBullets.removeIf(AbstractFlyingObject::notValid);
        ItemList.heroBullets.removeIf(AbstractFlyingObject::notValid);
        ItemList.props.removeIf(AbstractFlyingObject::notValid);
    }

    @Override
    public void run(){
        if(bgmPlayer == null){
            bgmPlayer = MediaPlayer.create(getContext(), R.raw.bgm);
            bgmPlayer.setLooping(true);
        }
        bgmPlayer.start();
        while(isDrawing){
            if(tick % enemyGenerateCircle == 0){
                enemyGenerate();
            }
            if(tick % enemyShootCircle == 0){
                enemyShoot();
            }
            if(tick % heroShootCircle == 0){
                heroShoot();
            }
            move();
            crashCheckAction();
            postProcessAction();
            draw();

            if(ItemList.heroAircraft.getHp() <= 0){
                isDrawing = false;
                gameActivity.finish();
            }
            tick += 10;
        }
    }
}