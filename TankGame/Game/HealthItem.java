package Game;

import java.awt.Image;

public class HealthItem extends ItemObject {
    public HealthItem(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
    }

    public void update(){
        y += speed;
        if (TRE.t1.collision(x, y, width, height)) {
            TRE.t1.addHealth();
            show = false;
        } else if (TRE.t2.collision(x, y, width, height)) {
            TRE.t2.addHealth();
            show = false;
        }
    }
}
