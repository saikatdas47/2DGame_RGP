package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        type = type_pickupOnly;
        this.gp = gp;
        name = "Heart";
        value = 2;
        down1 = setup("/object/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/object/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/object/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/object/heart_blank", gp.tileSize, gp.tileSize);
        price=75;

    }

    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        return true;

    }

}
