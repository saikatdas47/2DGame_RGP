package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Key";
        down1 = setup("/object/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "] \nIt open a door.";
        price = 75;
        stackable=true;

    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Door");
        if (objIndex != 999) {
            gp.ui.currentDiologues = "You use the " + name + " and open the door";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;

        } else {
            gp.ui.currentDiologues = "What are you doing?";
            return false;
        }


    }

}
