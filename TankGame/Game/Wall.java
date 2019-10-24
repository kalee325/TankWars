package Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Rectangle;


public class Wall extends GameObject{
    private int width, height, coolDown;
    private Tank p1 = TRE.getTank(1), p2 = TRE.getTank(2);
    boolean breakable;

    private final Rectangle wallRect;


    public Wall(Image img, int x, int y, boolean breakableWall) {
        super(img, x, y, 0);

        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

        breakable = breakableWall;
        coolDown = 0;


        wallRect = new Rectangle(x, y, width, height);
    }

    public boolean isBreakable() {
        return breakable;
    }

    public boolean isRespawning() {
        return coolDown == 0;
    }

    public void breakWall() {
        coolDown = 700;
    }

    public Rectangle getWallRectangle() {
        return wallRect;
    }

    public void draw(ImageObserver obs, Graphics2D g) {
        if (coolDown == 0) {
            g.drawImage(img, x, y, null);
            update();
        } else {
            coolDown -= 1;
        }
    }

    public void update() {
        if (p1.collision(this.x, this.y, this.width, this.height)) {
            if (p1.x > (x)) {
                p1.x += 3;
            } else if (p1.x < (this.x)) {
                p1.x -= 3;
            }
            if (p1.y > (this.y)) {
                p1.y += 3;
            } else if (p1.y < this.y) {
                p1.y -= 3;
            }
        }

        if (p2.collision(this.x, this.y, this.width, this.height)) {
            if (p2.x > (x)) {
                p2.x += 3;
            } else if (p2.x < (this.x)) {
                p2.x -= 3;
            }
            if (p2.y > (this.y)) {
                p2.y += 3;
            } else if (p1.y < this.y) {
                p2.y -= 3;
            }
        }
    }

}
