package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.Mon_GreenSlime;
import object.*;
import tile_inreactive.IT_DryTree;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapnum = 0;
        int i = 0;
        gp.obj[mapnum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 25;
        gp.obj[mapnum][i].worldY = gp.tileSize * 23;
        i++;
        gp.obj[mapnum][i] = new OBJ_Heart(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 21;
        gp.obj[mapnum][i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[mapnum][i] = new OBJ_Mana_Crystal(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 26;
        gp.obj[mapnum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapnum][i] = new OBJ_Axe(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 33;
        gp.obj[mapnum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapnum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 30;
        gp.obj[mapnum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapnum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 22;
        gp.obj[mapnum][i].worldY = gp.tileSize * 27;
        i++;
        gp.obj[mapnum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 23;
        gp.obj[mapnum][i].worldY = gp.tileSize * 27;
        i++;
        gp.obj[mapnum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 24;
        gp.obj[mapnum][i].worldY = gp.tileSize * 27;
        i++;
        gp.obj[mapnum][i] = new OBJ_Door(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 14;
        gp.obj[mapnum][i].worldY = gp.tileSize * 28;
        i++;
        gp.obj[mapnum][i] = new OBJ_Door(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 12;
        gp.obj[mapnum][i].worldY = gp.tileSize * 12;
        i++;
        gp.obj[mapnum][i] = new OBJ_Chest(gp,new OBJ_Key(gp));
        gp.obj[mapnum][i].worldX = gp.tileSize * 30;
        gp.obj[mapnum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapnum][i] = new OBJ_Lantern(gp);
        gp.obj[mapnum][i].worldX = gp.tileSize * 18;
        gp.obj[mapnum][i].worldY = gp.tileSize * 20;
    }

    public void setNpc() {
        int mapnum = 0;
        int i = 0;
        gp.npc[mapnum][i] = new NPC_OldMan(gp);
        gp.npc[mapnum][i].worldX = gp.tileSize * 21;
        gp.npc[mapnum][i].worldY = gp.tileSize * 21;
        i++;
        mapnum = 1;
        i=0;
        gp.npc[mapnum][i] = new NPC_Merchant(gp);
        gp.npc[mapnum][i].worldX = gp.tileSize * 12;
        gp.npc[mapnum][i].worldY = gp.tileSize * 7;


    }

    public void setMonster() {
        int mapnum = 0;

      int i = 0;
        gp.monster[mapnum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapnum][i].worldX = gp.tileSize * 23;
        gp.monster[mapnum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapnum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapnum][i].worldX = gp.tileSize * 23;
        gp.monster[mapnum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapnum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapnum][i].worldX = gp.tileSize * 23;
        gp.monster[mapnum][i].worldY = gp.tileSize * 39;
        i++;
//        gp.monster[mapnum][i] = new Mon_GreenSlime(gp);
//        gp.monster[mapnum][i].worldX = gp.tileSize * 50;
//        gp.monster[mapnum][i].worldY = gp.tileSize * 30;
        i++;
//


        mapnum=1;
        gp.monster[mapnum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapnum][i].worldX = gp.tileSize * 38;
        gp.monster[mapnum][i].worldY = gp.tileSize * 42;
        i++;
    }

    public void setInteractiveTile() {
        int mapnum = 0;

        int i = 0;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 30, 30);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 30, 31);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 29, 31);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 28, 31);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 27, 31);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 26, 31);
        i++;
        gp.iTile[mapnum][i] = new IT_DryTree(gp, 25, 31);
        i++;
    }

}
