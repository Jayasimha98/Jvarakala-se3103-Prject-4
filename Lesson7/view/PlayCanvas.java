package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.GameElement;
import java.awt.Graphics;



public class PlayCanvas extends JPanel{

    private PlayBord gameBoard;
    private ArrayList<GameElement> gameElements = new ArrayList<>();

    public PlayCanvas(PlayBord gameBoard,int width,int height){
        this.gameBoard=gameBoard;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width,height));
     
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
     

        for (int i = 0; i < gameElements.size(); i++) {
            gameElements.get(i).render(g2);
        }
    }
    
    public ArrayList<GameElement> getGameElements(){
        return gameElements;
    }

    
}
