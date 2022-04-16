package model;

import java.awt.Graphics2D;

import view.PlayBord;

import java.awt.Color;

public class Bullet extends GameElement {
   
  

    public Bullet(int x,int y){
        super(x,y,Color.red,true,PlayBord.MOVE_WIDTH,PlayBord.MOVE_WIDTH+3);
    }


    @Override
    public void render(Graphics2D g2) {
         g2.setColor(color);
         if(filled){
            g2.fillRect(x, y, width, height);
         }
         else
          g2.drawRect(x, y, width, height);
        
    }
    @Override
    public void animate() {
        super.y -= PlayBord.UNIT_MOVE;
        
    }
    
    
}
