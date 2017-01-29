package com.nikitosh.spbau.visualization;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public final class VisualizationUtilities {
    private VisualizationUtilities() {}

    public static void drawCenteredString(Graphics2D graphics2D, String text, float x, float y, Color color) {
        Color oldColor = graphics2D.getColor();
        graphics2D.setColor(color);
        FontMetrics metrics = graphics2D.getFontMetrics();
        graphics2D.drawString(text, x - metrics.stringWidth(text) / 2,
                y - metrics.getHeight() / 2 + metrics.getAscent());
        graphics2D.setColor(oldColor);
    }

    public static void drawRotatedImage(Graphics2D graphics2D, BufferedImage image, int x, int y,
                                        int width, int height, double angle) {
        AffineTransform tx = AffineTransform.getRotateInstance(angle, image.getWidth() / 2, image.getWidth() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        graphics2D.drawImage(op.filter(image, null), x, y, width, height, null);
    }
}
