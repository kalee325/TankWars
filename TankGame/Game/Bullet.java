package Game;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;

public class Bullet extends GameObject{

    int xSize;
    int ySize;
    Tank currentTank;
    Boolean visible;

    public Bullet(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
        visible = true;
        this.speed = speed;

    }


    public boolean isVisible() {
        return visible;
    }

    public void draw(ImageObserver obs, Graphics2D g) {
        if(visible){
            g.drawImage(img, x, y,  obs);
        }
    }

}
