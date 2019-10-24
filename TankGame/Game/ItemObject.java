package Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class ItemObject extends GameObject {
    boolean show = false;

    public ItemObject(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
        show = true;

    }

    public boolean isVisible(){
        return show;
    }

    public void update() {
    }

    public void draw(ImageObserver obs, Graphics2D g) {
        if(show)
            g.drawImage(img, x, y, obs);
    }
}
