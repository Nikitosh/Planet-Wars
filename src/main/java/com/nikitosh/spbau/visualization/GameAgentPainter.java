package com.nikitosh.spbau.visualization;

import burlap.mdp.core.oo.state.*;
import burlap.visualizer.ObjectPainter;
import com.nikitosh.spbau.model.*;

import java.awt.*;

public class GameAgentPainter implements ObjectPainter {
    @Override
    public void paintObject(Graphics2D graphics2D, OOState ooState, ObjectInstance objectInstance,
                            float cWidth, float cHeight) {
        Agent agent = (Agent) objectInstance;
        Color color = Color.BLACK;
        if (agent.name().equals(GameState.AGENT_NAME)) {
            color = Color.GREEN;
        }
        if (agent.name().equals(GameState.OPPONENT_NAME)) {
            color = Color.RED;
        }
        if (agent.name().equals(GameState.NEUTRAL_NAME)) {
            color = Color.YELLOW;
        }
        graphics2D.setColor(color);

        agent.getPlanets().forEach(planet -> {
            int radius = 3 * planet.getSpaceshipsNumber();
            graphics2D.fillOval(planet.getX() - radius / 2, planet.getY() - radius / 2, radius, radius);
            drawCenteredString(graphics2D, String.valueOf(planet.getSpaceshipsNumber()), planet.getX(), planet.getY());
        });
    }

    private void drawCenteredString(Graphics2D graphics2D, String text, float x, float y) {
        Color oldColor = graphics2D.getColor();
        graphics2D.setColor(Color.BLACK);
        FontMetrics metrics = graphics2D.getFontMetrics();
        graphics2D.drawString(text, x - metrics.stringWidth(text) / 2,
                y - metrics.getHeight() / 2 + metrics.getAscent());
        graphics2D.setColor(oldColor);
    }
}
