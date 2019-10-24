package Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;
import java.awt.Rectangle;


public abstract class GameObject implements Observer {

    protected int x, y, speed, height, width;
    protected Image img;
    Rectangle obj;


    public GameObject (Image img, int x, int y, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.height = img.getHeight(null);
        this.width = img.getWidth(null);
    }



    public Image getImg() {
        return this.img;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return width;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeight() {
        return height;
    }

    public boolean collision(int x, int y, int w, int h) {
        obj = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle newObj = new Rectangle(x, y, w, h);
        if ((this.obj.intersects(newObj))) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Observable obj, Object arg) {
    }

    public void draw(ImageObserver obs, Graphics2D g) {
    }

}
