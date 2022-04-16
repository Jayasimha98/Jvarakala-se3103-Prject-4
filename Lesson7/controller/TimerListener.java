package controller;

import java.awt.event.ActionListener;
import java.util.LinkedList;

import model.Bullet;
import model.Shooter;
import view.PlayBord;

import java.awt.event.ActionEvent;

public class TimerListener implements ActionListener {


    public enum EventType {
        KEY_RIGHT,KEY_LEFT,KEY_SPACE
    }

    private LinkedList<EventType> eventQueue;

    private PlayBord gameBoard;
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;

    public TimerListener(PlayBord gameBoard){
        this.gameBoard=gameBoard;
        eventQueue=new LinkedList<>();
      
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ++frameCounter;
        update();
        processEventQueue();
        processCollision();
        gameBoard.getCanvas().repaint();
    }   

    private void processEventQueue(){
              while(!eventQueue.isEmpty()){
                  var e=eventQueue.getFirst();
                  eventQueue.removeFirst();
                  Shooter shooter = gameBoard.getShooter();
                  if(shooter == null) return;

                  switch(e) {
                    case KEY_LEFT:
                      shooter.moveLeft();
                      break;
                    case KEY_RIGHT:
                      shooter.moveRight();
                      break;
                    case KEY_SPACE:
                      if (shooter.canFireMoreBullet())
                        shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                      break;
                     
                  }
              }

              if(frameCounter == BOMB_DROP_FREQ) {
                resetBoombs();
              }
    }

    void resetBoombs(){
        gameBoard.getEnemyComposite().dropBombs();
        frameCounter = 0;
    }

    private void processCollision(){
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();

        shooter.removeBulletsOutOfBound();
        enemyComposite.checkShoterHit(shooter);
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
    }

    private void update(){
        for(var e: gameBoard.getCanvas().getGameElements()){
             e.animate();
        }
        
    }

    public LinkedList<EventType> getEventQueue(){
        return eventQueue;
    }
}
