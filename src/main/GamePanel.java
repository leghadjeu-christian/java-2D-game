package main;

import entity.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    
    final int originalTileSize = 16; // 16 x 16 tile
    
    final int scale = 3;
    public final int  tileSize = originalTileSize * scale; // 48 x 48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth  = tileSize * maxScreenCol;  //768 pixels
    final int screenHeight  = tileSize * maxScreenRow; //576 pixels
    
    int  FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    
    int playerX = 100;
    int playerY = 100;
    int playerspeed= 4;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }
    
    public void startGameThreaad() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;  //0.0166 seconds
        double  delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime) ;
             
            lastTime = currentTime;

            if (delta >= 1) {

                update();
                repaint();
                delta--;
                drawCount++;
            }
            

            if (timer >= 1000000000) {
                System.out.println("FPS" +  " " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            // Update information such as the updated information
            
            
            
            
        }
        
    }
    
    public void update() {
       player.update();
       
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        
       
        g2.dispose();
        
    }
    
}
