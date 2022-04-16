package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import view.PlayBord;
import view.TextDraw;

public class EnemyComposite extends GameElement  {
    private boolean movingToRight = true;
    public static final int NROWS =2;
    public static final int NCOLS=10;
    public static final int ENEMY_SIZE=20;
    public static  int UNIT_MOVE=5;
    public static float frequency = 0.2f;
    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
   
    private Random random = new Random();
    public static Boolean bottontouch = false;

    public static int droped_to = 0;
    PlayBord gb;

    public enum Event {
        EnemyFinish,EnemyTouchDown,SooterHit,IncreaseScore
    }

    public EnemyComposite( PlayBord gb) {
        this.gb = gb;
       rows = new ArrayList<>();
       bombs = new ArrayList<>();
       droped_to =0;
      // enemyStratagy = new NormalSpeed(rows);
       bottontouch =false;
       for(int r=0;r <NROWS;r++){
           var oneRow = new ArrayList<GameElement>();
           rows.add(oneRow);
           for (int c=0;c<NCOLS;c++){
               oneRow.add(new Enemy( 
                    c * ENEMY_SIZE *2 , r *ENEMY_SIZE *2, ENEMY_SIZE,Color.yellow,true
               ));

               
           }
       }
    }


    @Override
    public void render(Graphics2D g2) {
        renderEnemeys(g2);
        renderBombs(g2);
    }

    private void renderEnemeys(Graphics2D g2){
        for(var r:rows){
            for(var e:r){
                e.render(g2);
            }
        }
    }
    private void renderBombs(Graphics2D g2){
        for(var b :bombs){
            b.render(g2);
        }
    }


    @Override
    public void animate() {

        int dx = 5;
        if(movingToRight){
            if(rightEnd() >= PlayBord.WIDTH) {
                verticaldropDown();
                dx = -dx;
                movingToRight = false;
            }
        }else {
            dx=-dx;
            if(leftEnd() <= 0){
                verticaldropDown();
                dx = -dx;
                movingToRight = true;
            }
        }

        moveEnamys(dx);

        
        animateBombs();
              
    }

    private void moveEnamys(int dx){
        for (var row:rows) {
            for( var e : row){
                e.x +=dx;
            }
        }

    }

    private void animateBombs(){
        for(var b :bombs){
            b.animate();
        }
    }
    
        private void verticaldropDown(){
        
            for (var row:rows) {
                for( var e : row){
                    e.y +=20;
                    EnemyComposite.droped_to = e.y;
                }
            }
    
            if(EnemyComposite.droped_to == PlayBord.HEIGHT-20){
                EnemyComposite.bottontouch = true;
            }
        }
        
        private int rightEnd(){
            int xEnd = -100;
            for(var row:rows){
                if(row.size() == 0) continue;
                int x= row.get(row.size() -1).x + EnemyComposite.ENEMY_SIZE;
                if(x > xEnd) xEnd = x;
            }
            return xEnd;
        }
    
        private int leftEnd(){
            int xEnd = 9000;
            for(var row:rows){
                if(row.size() == 0) continue;
                int x= row.get(0).x;
                if(x < xEnd) xEnd = x;
            }
            return xEnd;
        }
    
    
    public void dropBombs(){
        for(var row: rows){
            for( var e:row){
                if(random.nextFloat() < frequency){
                    bombs.add(new Bomb(e.x,e.y));
                }
            }
        }

    }

    public void removeBombsOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for(var b : bombs){
            if(b.y >= PlayBord.HEIGHT){
                remove.add(b);
            }
            
        }
        bombs.removeAll(remove);
    }

    public void checkShoterHit(Shooter shooter){
        var remove = new ArrayList<GameElement>();
        for(var b : bombs){
            int index = shooter.hitIndex(b);
            if(index == -2){
            CallGameOver();
            }else{
                if(index>=0){
                    remove.add(b);
                    shooter.remove(index);
                }
            }
            
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter){
        var removeBullets = new ArrayList<GameElement>();

        for(var row:rows){
            var removeEnemies = new ArrayList<GameElement>();
            for(var enemy: row){
                for(var bullet:shooter.getWeapons()) {
                    if(enemy.collideWith(bullet)){
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                        gb.setScore(gb.getScore()+10);
                    }
                }
            }
            row.removeAll(removeEnemies);

        }

        boolean hasEnemys = false;
        for(var row:rows){
            for(var enemy: row){
                hasEnemys =true;
            }
        }


        if(!hasEnemys){
            callYouOwn();
        }
        shooter.getWeapons().removeAll(removeBullets);

        if(bottontouch){
          
            CallGameOver();
        }
       

        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for(var b:bombs){
            for( var bullet:shooter.getWeapons()){
                if(b.collideWith(bullet)){
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }

        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);
    }




   public void callYouOwn(){
    gb.getTimer().stop();
    gb.getCanvas().getGameElements().clear();
    gb.getCanvas().getGameElements().add(new TextDraw("Game Over", 180, 250, Color.green, 30));
    gb.getCanvas().getGameElements().add(new TextDraw("You Win", 210 ,300, Color.yellow, 25));
    gb.getCanvas().getGameElements().add(new TextDraw("Your score : \""+gb.getScore()+"\"", 160, 350, Color.WHITE, 25));
    gb.getCanvas().repaint();
   }

  
    public void CallGameOver() {
//        EnemyFinish,EnemyTouchDown,SooterHit,IncreaseScore

gb.getTimer().stop();
gb.getCanvas().getGameElements().clear();
System.out.println("in listiner");
gb.getCanvas().getGameElements().add(new TextDraw("Game Over", 230, 250, Color.RED, 30));
gb.getCanvas().getGameElements().add(new TextDraw("You Lost", 230, 300, Color.RED, 25));
gb.getCanvas().getGameElements().add(new TextDraw("Your score is :  "+gb.getScore(), 180, 350, Color.GREEN, 25));
gb.getCanvas().repaint();

        
    }
}
