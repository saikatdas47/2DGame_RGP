package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    GamePanel gp;
    public OBJ_Door(GamePanel gp) {
        super(gp);
        type=type_obstacle;

this.gp=gp;
        name = "Door";
        down1=setup("/object/door",gp.tileSize,gp.tileSize);

        collision = true;
        solidArea.x= 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interect () {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDiologues = "You need a key to open this";
    }
}
