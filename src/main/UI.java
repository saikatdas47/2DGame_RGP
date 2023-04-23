package main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    //  BufferedImage keyImage;
    public boolean messageOn = false;
    //public String message = "";
    public boolean gameFinished = false;
    // int messageCounter = 0;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDiologues = "";
    public int commandNum = 0;
    public int titleState = 0;
    BufferedImage heart_full, heart_half, heart_bank, crystal_full, crystal_blank, coin;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;

    int counter = 0;
    public Entity npc;


    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        //create hud object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_bank = heart.image3;
        Entity crystal = new OBJ_Mana_Crystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //playState
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        //pauseState
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            draePouseScreen();
        }
        //dialogue State
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        /// CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        // OPTIOnS STATE
        if (gp.gameState == gp.optionsState) {
            drawOptionsState();
        }
        // Gameover STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
    }


    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";
        // shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //Back to the title screen
        text = "Menu";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawPlayerLife() {
        //  gp.player.life=6;
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int i = 0;
        //Draw blank heart
        while (i < gp.player.maxlife / 2) {
            g2.drawImage(heart_bank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;

        i = 0;

        //draw currrent life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        /// DRAW MAX MANA
        x = (gp.tileSize / 2);
        y = (int) (gp.tileSize / 1.5) + 38;
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        /// DRAW  MANA
        x = (gp.tileSize / 2);
        y = (int) (gp.tileSize / 1.5) + 38;
        i = 0;
        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }


    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;//messege counter++
                messageCounter.set(i, counter);//set the counter to array

                messageY += 50;
                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if (titleState == 0) {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/credit/font.jpg"));

                g2.drawImage(image, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //tile name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
            String text = "Hinomo";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            //Shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y);
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize - 20;

            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);

            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }


            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }
            text = "MULTIPLAYER";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }
            text = "CONTROLS";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }
            text = "CREDIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 4) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 5) {
                g2.drawString(">", x - gp.tileSize + 10, y);
            }
        }


        //controls
        if (titleState == 3) {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/credit/font1.jpg"));

                g2.drawImage(image, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F));
            String text = "CONTROLS";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3 + 100;
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y - 150);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            text = "Right = D";
            x = getXforCenteredText(text) - 200;
            y = gp.tileSize * 3 + 100;
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);


            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            text = "Up = W";
            x = getXforCenteredText(text) - 200;
            y = gp.tileSize * 3;
            g2.drawString(text, x, y + 100 + 60);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            text = "Down = S";
            x = getXforCenteredText(text) - 200;
            y = gp.tileSize * 3;
            g2.drawString(text, x, y + 160 + 60);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            text = "Left = A";
            x = getXforCenteredText(text) - 200;
            y = gp.tileSize * 3;
            g2.drawString(text, x, y + 220 + 60);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            text = "Pouse Game = P";
            x = getXforCenteredText(text) - 200;
            y = gp.tileSize * 3;
            g2.drawString(text, x, y + 280 + 60);

        }
        if (titleState == 2) {
            gp.ui.titleState = 1;
            if (gp.ui.titleState == 1) {
                gp.gameState = gp.playState;
            }
            gp.playMusic(0);
        }

        if (titleState == 4) {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/credit/font2.jpg"));

                g2.drawImage(image, 0, 0, null);
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/credit/Saikat2-removebg-preview.png"));
                int x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
                int y = gp.tileSize + 30;
                g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
            } catch (IOException e) {
                e.printStackTrace();
            }


            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 46F));
            String text = "SAIKAT DAS";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3 + 100;
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);


            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
            text = "ID: 20210104158";
            x = getXforCenteredText(text);
            y = gp.tileSize * 3;
            g2.drawString(text, x + 5, y + 100 + 60);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
            text = "DEPT. CSE";
            x = getXforCenteredText(text);
            y = gp.tileSize * 3;
            g2.drawString(text, x + 5, y + 160 + 60);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
            text = "2ND YEAR,1ST SEMESTER";
            x = getXforCenteredText(text);
            y = gp.tileSize * 3;
            g2.drawString(text, x + 5, y + 220 + 60);
        }
    }


    public void drawDialogueScreen() {
        //window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        //int width=200;
        // int height=570 ;
        int width = (gp.screenWidth) - (gp.tileSize * 12);
        int height = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22F));
        x += gp.tileSize;
        y = +gp.tileSize + 30;
        for (String line : currentDiologues.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        // g2.drawString(currentDiologues,x,y);
    }

    public void drawCharacterScreen() {
        //CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 10;
        final int frameHeight = gp.tileSize * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(26F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;


        // NAMES

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strenght", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

// VVALUES
        int tailx = (frameX + frameWidth) - 30;
        //Reset textY;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxlife);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightlext(value, tailx);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailx - gp.tileSize - 240, textY - 20, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailx - gp.tileSize - 240, textY - 20, null);

    }

    public void drawSubWindow(int x, int y, int height, int width) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawOptionsState() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
// SUB WINDOW
        int frameX = gp.tileSize * 3;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 10;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;
        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        textX = frameX + gp.tileSize;
        // MUSic
        textY += gp.tileSize * 2;
        g2.drawString("Music", textX, textY);
        if (commandNum == 0) {
            g2.drawString("›", textX - 25, textY);
        }

        // SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 1) {
            g2.drawString("›", textX - 25, textY);
        }

        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 2) {
            g2.drawString("›", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }


        // END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3) {
            g2.drawString("›", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 3;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }

        // BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4) {
            g2.drawString("›", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
                gp.keyH.enterPressed = false;

            }
        }

        //Music
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        textY += gp.tileSize;

        gp.config.saveConfig();

    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDiologues = "Quit the game and \nreturn to the title screen?";
        for (String line : currentDiologues.split(" \n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.ui.titleState = 0;
            }
        }
        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 3;
                gp.keyH.enterPressed = false;
            }
        }
    }


    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Charecter Screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pouse", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);


        textX = frameX + gp.tileSize * 7;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("p", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.keyH.enterPressed = false;
                commandNum = 2;
            }
        }

    }


    public void draePouseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);


        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.tileSize * 9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 5;
            frameHeight = gp.tileSize * 6;
            slotRow = playerSlotRow;
            slotCol = playerSlotCol;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 5;
            frameHeight = gp.tileSize * 6;
            slotRow = npcSlotRow;
            slotCol = npcSlotCol;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //DRAW Player iteam
        for (int i = 0; i < entity.inventory.size(); i++) {

            // EQUIP CURSOR

            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield||
                    entity. inventory.get (i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            //display amount
            if (entity==gp.player&&entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;
                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightlext(s, slotX + 44) + 240;
                amountY = slotY + gp.tileSize;
                // SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);

            }


            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }


        // CURSOR
        if (cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //Description Frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight - 40;
            int dFrameWidth = frameWidth - 50;
            int dFrameHeight = gp.tileSize * 6;


            //descriptiom text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for (String Line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(Line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    //trade
    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        drawDialogueScreen();
        //Draw Window
        int x = gp.tileSize * 11;
        int y = gp.tileSize * 4;
        int width = (int) (gp.tileSize * 3.5);
        int height = gp.tileSize * 3;
        drawSubWindow(x, y, width, height);
        // DRAWTEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed == true) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDiologues = "Come Again, he he!";
            }
        }
        y += gp.tileSize;

    }

    public void trade_buy() {
// DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
// DRAW NPC INVENTORY
        drawInventory(npc, true);
        //DRAW HINT WINDOW
        int x = gp.tileSize * 9;
        int y = gp.tileSize * 6;
        int width = gp.tileSize * 2;
        int height = gp.tileSize * 6;
        drawSubWindow(x, y, width, height);
        g2.drawString(" [ESC] Back", x + 24, y + 60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 9;
        y = gp.tileSize * 8;
        width = gp.tileSize * 2;
        height = gp.tileSize * 6;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coin " + gp.player.coin, x + 24, y + 60);

//DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = gp.tileSize;
            height = (int) (gp.tileSize * 2.5);
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightlext(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x + 240, y + 34);
            //Buy an Item
            if (gp.keyH.enterPressed == true) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDiologues = "You need more coin to buy that!";
                    drawDialogueScreen();
                } else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;

                    } else {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDiologues = "You cannot carry any more!";
                    }
                }


//                else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
//                    subState = 0;
//                    gp.gameState = gp.dialogueState;
//                    currentDiologues = "You cannot carry any more!";
//                } else {
//                    gp.player.coin -= npc.inventory.get(itemIndex).price;
//                    gp.player.inventory.add(npc.inventory.get(itemIndex));
//                }
            }

        }

    }

    public void trade_sell() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;
        //DRAW HINT WINDOW
        x = gp.tileSize * 2;
        y = gp.tileSize * 6;
        width = gp.tileSize * 2;
        height = gp.tileSize * 6;
        drawSubWindow(x, y, width, height);
        g2.drawString(" [ESC] Back", x + 15, y + 60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 2;
        y = gp.tileSize * 8;
        width = gp.tileSize * 2;
        height = gp.tileSize * 6;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your coin " + gp.player.coin, x + 15, y + 60);

//DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 11);
            y = (int) (gp.tileSize * 5.5);
            width = gp.tileSize;
            height = (int) (gp.tileSize * 2.5);
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlignToRightlext(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);
            //Buy an Item
            if (gp.keyH.enterPressed == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDiologues = "You cannot sell an equipped item!";
                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    } else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }

        }

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void credit() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/credit/saikat2.jpg"));
        int x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        int y = gp.tileSize;
        g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    public int getXforAlignToRightlext(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length - 240;
        return x;
    }


}
