package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Crystal extends Entity {
    GamePanel gp;
    public OBJ_Mana_Crystal (GamePanel gp) {
        super(gp);
        this.gp = gp;
       type= type_pickupOnly;
        name = "Mana Crystal";
        value=1;
        down1=setup("/object/manacrystal_full",gp.tileSize,gp.tileSize);
        image = setup("/object/manacrystal_full",gp.tileSize,gp.tileSize);
        image2=setup("/object/manacrystal_blank",gp.tileSize,gp.tileSize);
        price=75;

    }
    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Mana +" + value);
        entity.mana += value;
        return true;

    }
}
