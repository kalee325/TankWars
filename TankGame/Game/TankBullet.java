package Game;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.geom.AffineTransform;



public class TankBullet extends Bullet{

    private Tank p2 = TRE.getTank(2), p1 = TRE.getTank(1);
    private Image bullet;
    private int angle;
    private TRE obj;
    private PowerUp item;

    public TankBullet(Image img, int speed, Tank tank, int dmg) {
        super(img, tank.getTankCenterX(), tank.getTankCenterY(), speed);
        bullet = img;
        xSize = img.getWidth(null);
        ySize = img.getHeight(null);
        currentTank = tank;
        angle = currentTank.getAngle();
        visible = true;
    }

    public void update() {


        x += Math.round(speed * Math.cos(Math.toRadians(angle * -1)));
        y -= Math.round(speed * Math.sin(Math.toRadians(angle * -1)));

        if (p1.collision(x, y, xSize, ySize) && visible && currentTank != p1){
            visible = false;
            p1.hitTank();
        }

        else if (p2.collision(x, y, xSize, ySize) && visible && currentTank != p2){
            visible = false;
            p2.hitTank();
        }
        else {

            obj = TRE.getTankGame();
            for (int i = 0; i < obj.getWall().size() - 1; i++) {
                Wall wall = obj.getWall().get(i);
                if (wall.getWallRectangle().intersects(x, y, width, height) && wall.isRespawning()) {
                    if(wall.isBreakable()){
                        wall.breakWall();
                        wall.update();
                    }

                    visible = false;
                }
            }
        }
    }

    public void draw(ImageObserver obs, Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), 0, 0);
        g.drawImage(bullet, rotation, obs);

        update();
    }
}
