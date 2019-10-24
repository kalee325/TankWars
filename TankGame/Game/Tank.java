package Game;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{


    //public int x;
    //public int y;
    private int vx;
    private int vy;
    private int angle;

    private final double R = 1.3;
    private final int ROTATIONSPEED = 4;



    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;

    private TRE tank;
    private int speed = 5;
    private int health = 200;
    private int lives = 3;
    private boolean end = false, die = false;
    private int spawnPointX, spawnPointY, startAngle;
    private Tank p1 = TRE.getTank(1), p2 = TRE.getTank(2);

    private PowerUp item;

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(img, x, y, angle);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;

        spawnPointX = x;
        spawnPointY = y;
        startAngle = angle;

    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }




    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }


    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }




    private void checkBorder() {
        if (x < 0) {
            x = 0;
        }
        if (x >= TRE.SCREEN_WIDTH) {
            x = TRE.SCREEN_WIDTH;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= TRE.SCREEN_HEIGHT) {
            y = TRE.SCREEN_HEIGHT;
        }

    }



    public int getAngle() {
        return angle;
    }

    public int getTankCenterX() {
        return x + img.getWidth(null) / 2;
    }

    public int getTankCenterY() {
        return y + img.getHeight(null) / 2;
    }

    public void ShootBullet(Tank a) {

        tank = TRE.getTankGame();

        tank.getBullet().add(new TankBullet(tank.getBulletImage(), speed * 2, this, 1));

    }

    public void paintHealth(Graphics g, int x, int y){
        int newHealth = 200;
        g.setColor(Color.green);
        g.fillRect(x, y, health, 30);

        if(health < 150 && health >= 100) {
            g.setColor(Color.yellow);
            g.fillRect(x, y, health, 30);
        }
        else if(health < 100) {
            g.setColor(Color.red);
            g.fillRect(x, y, health, 30);
        }

        if(health <= 0){
            lives -= 1;
            health = newHealth;
            this.die = true;
        }
    }

    public void paintLives(Graphics g, int x, int y){
        String heart = "\u2665";
        g.setColor(Color.red);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));

        int life;

        for(life = 0; life < lives-1; life++){
            g.drawString(heart, x + life*50, y);
        }

        if(lives == 0) {
            this.end = true;
            g.setColor(Color.ORANGE);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 70));
            g.drawString("Game Over!", 320, 90);
        }
    }

    public int getLives(){
        return lives;
    }

    public void hitTank() {
        this.health -= 50;
    }

    public void addHealth(){
        if(health < 200) {
            this.health += 50;
        }
    }

    public boolean endGame() {
        return end;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }



    void drawImage(Graphics g) {
        if(health > 0 && lives > 0) {
            die = false;
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);

        } else {
            die = false;
            x = spawnPointX;
            y = spawnPointY;
            angle = startAngle;
        }
    }


}
