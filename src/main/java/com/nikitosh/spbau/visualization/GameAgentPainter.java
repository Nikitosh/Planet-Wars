package com.nikitosh.spbau.visualization;

import burlap.mdp.core.oo.state.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.*;

import java.awt.*;
import java.util.*;

public class GameAgentPainter implements ObjectPainter {
    private static final Map<String, Color> COLORS = new HashMap<String, Color>() {{
        put(GameState.NEUTRAL_NAME, Color.YELLOW);
        put(GameState.AGENT_NAME, Color.GREEN);
        put(GameState.OPPONENT_NAME, Color.RED);
    }};

    @Override
    public void paintObject(Graphics2D graphics2D, OOState ooState, ObjectInstance objectInstance,
                            float cWidth, float cHeight) {
        Agent agent = (Agent) objectInstance;
        Color color = COLORS.get(agent.name());
        graphics2D.setColor(color);

        agent.getPlanets().forEach(planet -> {
            int radius = 3 * planet.getSpaceshipsNumber();
            graphics2D.fillOval(planet.getX() - radius / 2, planet.getY() - radius / 2, radius, radius);
            VisualizationUtilities.drawCenteredString(graphics2D, String.valueOf(planet.getSpaceshipsNumber()), planet.getX(), planet.getY(), Color.BLACK);
        });
    }
}
