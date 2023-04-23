package main;

import entity.Entity;
import org.w3c.dom.css.Rect;

import javax.sound.midi.MidiFileFormat;
import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap,tempCol,tempRow;


    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;


            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }

            }
        }
    }

    public void checkEvent() {
        //check if the playerr charecter is more than one tile from the laast
        //event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent == true) {
            //  if (hit(22, 16, "right") == true) {
            //                //Event Happened
            //                damagePit(22, 16,gp.dialogueState);
            //            }
            //            if (hit(23, 19, "any") == true) {
            //                //Event Happened
            //                damagePit(22, 16,gp.dialogueState);
            //            }
            if (hit(0,23, 12, "up") == true) {
                healingPool(gp.dialogueState);
            }

             else if (hit(0,10, 39, "up") == true) {
                teleport(1,12,13);
            }
           else if (hit(1,12,13, "down") == true) {
                teleport(0,10, 39 );
            }
            else if (hit(1,12,9, "up") == true) {
                speak(gp.npc[1][0]);
            }
        }

    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;


    }

    public void damagePit( int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDiologues = "You fall into a pit!";
        gp.player.life -= 1;
        //eventRect [col] [row] .eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool( int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            gp.ui.currentDiologues = "You drink the water. \nYour life and mana have been recovered.";
            gp.player.life = gp.player.maxlife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
    }
    public void teleport(int map, int col, int row){

        gp.gameState=gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
//        gp.currentMap = map;
//        gp.player.worldX = gp.tileSize * col;
//        gp.player.worldY = gp.tileSize *row;
//        previousEventX = gp.player.worldX;
//        previousEventY = gp.player. worldY;
        canTouchEvent= false;
        gp.playSE(13);
    }
    public  void speak(Entity entity){
        if (gp. keyH.enterPressed == true) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
