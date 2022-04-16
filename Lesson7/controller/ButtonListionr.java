package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.PlayBord;
import view.PlayCanvas;
import model.EnemyComposite;
import model.Shooter;



public class ButtonListionr implements ActionListener { 
    PlayBord gb;
    public ButtonListionr(PlayBord gb){
        this.gb = gb;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
            PlayCanvas canvas = gb.getCanvas();
            gb.setEnemyui();
            gb.getTimerListener().getEventQueue().clear();
            canvas.getGameElements().clear();
            gb.setShooter(new Shooter(PlayBord.WIDTH/2,PlayBord.HEIGHT-PlayBord.SIZE));
            canvas.getGameElements().add(gb.getShooter());
            canvas.getGameElements().add(gb.getEnemyComposite());   
            gb.getScoreView().setText("0");
            EnemyComposite.droped_to =0;
            gb.getTimer().start();
            gb.setScore(0);
} 


}
