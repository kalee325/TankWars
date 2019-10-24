/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.awt.Dimension;


/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel  {


    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final TRE trex = new TRE();

    private BufferedImage world = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage world2 = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

    private  BufferedImage p1View = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage p2View = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D buffer;
    private JFrame jf;
    public static Tank t1, t2;
    public Dimension window;
    private int colddown = 1;


    private Image background, unbreakableWall, breakableWall, normalBullet, powerUp, health;


    public InputStream map;

    private ArrayList<Wall> wall = new ArrayList<>();
    private ArrayList<TankBullet> bullet = new ArrayList<>();
    private ArrayList<ItemObject> items = new ArrayList<>();



    public static void main(String[] args) {
        Thread x;
        //TRE trex = new TRE();
        trex.init();
        try {

            while (true) {
                trex.t1.update();
                trex.t2.update();
                trex.repaint();
                //System.out.println(trex.t1);
                Thread.sleep(1000 / 144);

                if(t1.endGame() || t2.endGame()){
                    if(t2.endGame()){
                        System.out.println("Player 1 wins!");
                    }
                    else if(t1.endGame()){
                        System.out.println("Player 2 wins!");
                    }
                    Thread.sleep(4000);
                    System.exit(0);
                }
            }
        } catch (InterruptedException ignored) { }

    }


    private void init() {
        this.jf = new JFrame("Tank Game");
        //this.world = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null;

        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = ImageIO.read(new File("Resource/Tank1.gif"));
            t2img = ImageIO.read(new File("Resource/Tank2.gif"));

            background = ImageIO.read(new File("Resource/Background.bmp"));
            unbreakableWall = ImageIO.read(new File("Resource/Wall1.gif"));
            breakableWall = ImageIO.read(new File("Resource/Wall2.gif"));
            normalBullet = ImageIO.read(new File("Resource/Shell1.gif"));
            powerUp = ImageIO.read(new File("Resource/Pickup.gif"));
            health = ImageIO.read(new File("Resource/Heart.png"));
            map = new FileInputStream("Resource/map.txt");


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        t1 = new Tank(50, 50, 0, 0, 0, t1img);
        t2 = new Tank(920, 675, 0, 0, 180, t2img);


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);


        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);



        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


        trex.window = jf.getContentPane().getSize();
        System.out.println(trex.window.width + " x "+ trex.window.height);


        LoadMap();

    }


    public ArrayList<TankBullet> getBullet() {
        return bullet;
    }
    public Image getBulletImage() {
        return normalBullet;
    }

    public ArrayList<Wall> getWall() {
        return wall;
    }

    public static TRE getTankGame() {
        return trex;
    }

    public static Tank getTank(int j) {
        if (j == 1) {
            return t1;
        }
        return t2;
    }

    public void drawDetail() {
        drawBackGround();
        if (!wall.isEmpty()) {
            for (int i = 0; i <= wall.size() - 1; i++) {
                wall.get(i).draw(this, buffer);
            }
        }

        if (!bullet.isEmpty()) {
            for (int i = 0; i <= bullet.size() - 1; i++) {
                bullet.get(i).draw(this, buffer);
                if (!bullet.get(i).isVisible()) {
                    bullet.remove(i--);
                }
            }
        }

        if(colddown % 500 == 0) {

            items.add(new PowerUp(powerUp, (int)(Math.random()* 920 - 70), (int)(Math.random()* 680 - 80), 0));
            items.add(new HealthItem(health, (int)(Math.random()* 920 - 70), (int)(Math.random()* 680 - 80), 0));

        }


        if (!items.isEmpty()) {
            for (int i = 0; i <= items.size() - 1; i++) {
                if (!items.get(i).isVisible()) {
                    items.remove(i--);
                } else {
                    items.get(i).update();
                    items.get(i).draw(this, buffer);
                }
            }
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        world2 = (BufferedImage) createImage(window.width, window.height);
        buffer = world2.createGraphics();


        p1View = world.getSubimage(tankViewX(t1), tankViewY(t1), SCREEN_WIDTH/2, SCREEN_HEIGHT);
        p2View = world.getSubimage(tankViewX(t2), tankViewY(t2), SCREEN_WIDTH/2, SCREEN_HEIGHT);


        buffer.drawImage(p1View,0, 0, SCREEN_WIDTH/2-1, SCREEN_HEIGHT,this);
        buffer.drawImage(p2View,window.width/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT,this);


        Image minimap = world.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        buffer.drawImage(minimap, window.width/2-100, window.height/2-100, 200, 200, this);

        colddown++;
    }


    //Get Tank X and Y for lock screen
    public int tankViewX(Tank tank){
        int x = tank.getX() - SCREEN_WIDTH/4;

        if(x < 0){
            x = 0;
        }
        if(x > (SCREEN_WIDTH/2)){
            x = SCREEN_WIDTH/2;
        }

        return x;
    }
    public int tankViewY(Tank tank){
        int y = tank.getY() - SCREEN_HEIGHT/2;

        if(y < 0){
            y = 0;
        }else {
            y = 0;
        }

        return y;
    }


    public void drawBackGround() {
        int TileWidth = background.getWidth(this);
        int TileHeight = background.getHeight(this);

        int NumberX = 6; // 6 times 256 = 1536, the actual map size is 1536 x 1536
        int NumberY = 6;

        for (int i = 0; i < NumberY; i++) {
            for (int j = 0; j < NumberX; j++) {
                buffer.drawImage(background, j * TileWidth, i * TileHeight,
                        TileWidth, TileHeight, this);
            }
        }
    }

    public void LoadMap() {
        BufferedReader line = new BufferedReader(new InputStreamReader(map));
        String number;
        int position = 0;
        try {
            number = line.readLine();
            while (number != null) {
                for (int i = 0; i < number.length(); i++) {
                    if (number.charAt(i) == '1') {
                        wall.add(new Wall(unbreakableWall, ((i) % 48) * 32, ((position) % 49) * 32, false));
                    } else if (number.charAt(i) == '2') {
                        wall.add(new Wall(breakableWall, ((i) % 48) * 32, ((position) % 49) * 32, true));
                    }
                }
                position++;
                number = line.readLine();
            }
        } catch (Exception e) {
            System.err.println("LoadMap" + e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);


        drawDetail();

        if(t1.getLives() > 0) {
            this.t1.paintHealth(buffer, 5, 725);
        }
        if(t2.getLives() > 0) {
            this.t2.paintHealth(buffer, 820, 5);
        }

        this.t1.paintLives(buffer, 40, 720);
        this.t2.paintLives(buffer, 890, 90);


        g2.drawImage(world2,0,0,null);

    }


}
