package Game;

import java.awt.Image;

public class PowerUp extends ItemObject {

    public PowerUp(Image img, int x, int y, int speed) {
        super(img, x, y, speed);
    }


    public void update() {
        y += speed;
        if(TRE.t1.collision(x, y, width, height)){
            show = false;
            TRE.t2.hitTank();
        }
        else if(TRE.t2.collision(x, y, width, height)){
            show = false;
            TRE.t1.hitTank();
        }
    }

}
