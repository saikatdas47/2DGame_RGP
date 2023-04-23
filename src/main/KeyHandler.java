package main;

import multiplayer.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    //Debug
    boolean checkDrawTime = false;
    GamePanel gp;
    //Game g=new Game("192.168.42.1.50158",813);

    boolean music = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.titleState == 0) {

            //tile state
            if (gp.gameState == gp.titleState) {
                titleState(code);
            }

            //playState
            else if (gp.gameState == gp.playState) {
                playState(code);
            }
            //pause state
            else if (gp.gameState == gp.pauseState) {
                pauseState(code);
            }
            //dialoge state
            else if (gp.gameState == gp.dialogueState) {
                dialogueState(code);
            }
            //chrecterState
            else if (gp.gameState == gp.characterState) {
                characterState(code);
            }
            //Option
            else if (gp.gameState == gp.optionsState) {
                optionsState(code);
            }
            //GameOver
            else if (gp.gameState == gp.gameOverState) {
                gameOverState(code);
            }
            //Marcent
            else if (gp.gameState == gp.tradeState) {
                tradeState(code);

            }


        }

        if (code == KeyEvent.VK_BACK_SPACE) {
            if (gp.ui.titleState == 4) {
                gp.ui.titleState = 0;
            }
            if (gp.ui.titleState == 3) {
                gp.ui.titleState = 0;
            }
        }
        //musix
        if (code == KeyEvent.VK_M) {
            if (gp.ui.titleState == 1) {
                if (music == true) {
                    gp.stopMusic();
                    music = false;
                } else if (music == false) {
                    gp.playMusic(0);
                    music = true;
                }
            }
        }
    }


    public void titleState(int code) {
        //title state
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum == -1) {
                    gp.ui.commandNum = 5;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum == 6) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleState = 1;
                    if (gp.ui.titleState == 1) {
                        gp.gameState = gp.playState;
                    }
                    gp.playMusic(0);
                    music = true;
                }
                if (gp.ui.commandNum == 1) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleState = 1;
                        if (gp.ui.titleState == 1) {
                            gp.gameState = gp.playState;
                        }
                        // gp.playMusic(0);
                    }
                }
                if (gp.ui.commandNum == 2) {
                    gp.ui.titleState = 2;
                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleState = 3;
                }
                if (gp.ui.commandNum == 4) {
                    gp.ui.titleState = 4;
                }
                if (gp.ui.commandNum == 5) {
                    System.exit(0);
                }
            }
        }
    }


    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;

        }

        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }

        //Debug
        if (code == KeyEvent.VK_T) {
            if (checkDrawTime == false) {
                checkDrawTime = true;
            } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
    }

    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A)
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        if (code == KeyEvent.VK_D)
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.se.checkVolume();
                    gp.playSE(9);
                }
            }


    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if (code == KeyEvent.VK_ENTER) {

            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);

            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.ui.titleState = 0;
                gp.restart();
                gp.stopMusic();
            }
        }

    }

    public void pauseState(int code) {

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void tradeState (int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if(gp.ui.subState == 1){
            npcInventory (code);
            if (code == KeyEvent. VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2){
            playerInventory (code);
            if (code == KeyEvent. VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }
    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);

    }


    public void playerInventory(int code){
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }


    public void npcInventory(int code){
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
