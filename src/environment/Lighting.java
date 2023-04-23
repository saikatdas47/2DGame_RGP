package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;


    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    public void setLightSource() {
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0, 0.98f));
        } else {
            int centerX = gp.player.screenX + (gp.tileSize) / 2;
            int centerY = gp.player.screenY + (gp.tileSize) / 2;


            Color color[] = new Color[5];
            float fraction[] = new float[5];
            color[0] = new Color(0, 0, 0, 0f);
            color[1] = new Color(0, 0, 0, 0.25f);
            color[2] = new Color(0, 0, 0, 0.5f);
            color[3] = new Color(0, 0, 0, 0.75f);
            color[4] = new Color(0, 0, 0, 0.98f);
            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.7f;
            fraction[4] = 1f;

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();

    }

    public void update() {
        if (gp.player.lightUpdated == true) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
