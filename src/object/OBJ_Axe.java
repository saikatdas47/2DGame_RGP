package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe (GamePanel gp) {
        super(gp);
        name = "Woodcutter's Axe";
        type=type_axe;
         down1 = setup ("/object/axe", gp.tileSize,gp.tileSize);
        attackValue=2;
        attackArea.width = 30;
        attackArea.height = 30;
        description= "[" + name + "] \nAn Axe for cutting\ntrees and\nself defence";
price=75;
    }
}
