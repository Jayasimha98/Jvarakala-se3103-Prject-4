package view;

import java.awt.Container;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import controller.KeyController;
import controller.TimerListener;
import model.EnemyComposite;
import model.Shooter;

import controller.ButtonListionr;
import controller.ExitButtonListionr;

public class PlayBord {

    public static final int WIDTH=600;
    public static final int HEIGHT=500;

    public static final int FPS =20;
    public static final int DELAY = 1000/FPS; 

    private JFrame window;

    private PlayCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;
    private int score = 0;
    private JLabel scoreView = new JLabel("0");

    public static final int SIZE=20;

    //  shooter

    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLETS = 3;

//bullet
    
    public static final int MOVE_WIDTH=5;

    // boomb

    public static final int ELEMENT_SIZE =5;
    public static final int UNIT_MOVE_BOMB =5;


    
    public PlayBord(JFrame window){
            this.window=window;
    }
    
    public void init(){

        Container cp = window.getContentPane();
        setCanvas(cp);
       
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        startButton.setFocusable(false);
        quitButton.setFocusable(false);
        startButton.setBackground(Color.GREEN);
        quitButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        quitButton.setForeground(Color.WHITE);

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH,southPanel);

        cp.add(BorderLayout.NORTH, setNorthPannel(cp));

       
        String startGame= "click <start> to Play";
        canvas.getGameElements().add(new TextDraw(startGame,100,100,Color.yellow,30));
        shooter = new Shooter(PlayBord.WIDTH/2,PlayBord.HEIGHT-SIZE);
        
        timerListener = new TimerListener(this);
        timer = new Timer(DELAY,timerListener);
        
        ButtonListionr buttonlist = new ButtonListionr(this);
        startButton.addActionListener(buttonlist);
        quitButton.addActionListener(new ExitButtonListionr());
    
        
    }
    private JPanel setNorthPannel(Container cp) {
        JPanel northPannel = new JPanel();
        JLabel scoreLable = new JLabel("Score");
        northPannel.add(scoreLable);
        northPannel.add(scoreView);
        scoreLable.setFocusable(false);
        scoreView.setFocusable(false);
        return northPannel;
    }

    private void setCanvas(Container cp) {
        canvas = new PlayCanvas(this,WIDTH,HEIGHT);
        cp.add(BorderLayout.CENTER,canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);
    }

    public void setEnemyui(){
        enemyComposite = new EnemyComposite(this);

    }
public void setScore(int score) {
    this.score = score;
    scoreView.setText(""+score);
}
public int getScore() {
    return score;
}
public JLabel getScoreView() {
    return scoreView;
}

    public void setEnemyComposite(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }

    public PlayCanvas getCanvas(){
        return canvas;
    }

    public Timer getTimer() {
        return timer;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }
public void setShooter(Shooter shooter) {
    this.shooter = shooter;
}
    public Shooter getShooter(){
        return shooter;
    }

    public EnemyComposite getEnemyComposite(){
        return enemyComposite;
    }
}
