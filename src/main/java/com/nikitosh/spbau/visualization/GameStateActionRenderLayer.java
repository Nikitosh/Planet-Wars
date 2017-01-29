package com.nikitosh.spbau.visualization;

import burlap.mdp.core.action.*;
import burlap.mdp.core.state.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.*;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class GameStateActionRenderLayer extends StateActionRenderLayer {
    private BufferedImage spaceshipImage;

    public GameStateActionRenderLayer() {
        try {
            spaceshipImage = ImageIO.read(new File("src/test/resources/spaceship.png"));
        } catch (IOException exception) {
            System.err.println("Could not load image of spaceship");
        }
    }

    @Override
    public void renderStateAction(Graphics2D graphics2D, State state, Action action, float width, float height) {
        GameState gameState = (GameState) state;
        MoveAction moveAction = (MoveAction) action;
        Planet planet1 = gameState.getPlanet(moveAction.getSourceName());
        Planet planet2 = gameState.getPlanet(moveAction.getDestinationName());
        if (planet1 != planet2) {
            int spaceshipX = (planet1.getX() + planet2.getX()) / 2;
            int spaceshipY = (planet1.getY() + planet2.getY()) / 2;
            double angle = Math.atan2(planet2.getY() - planet1.getY(), planet2.getX() - planet1.getX());
            VisualizationUtilities.drawRotatedImage(graphics2D, spaceshipImage,
                    spaceshipX - 15, spaceshipY - 15, 30, 30, angle);
            VisualizationUtilities.drawCenteredString(graphics2D, String.valueOf(moveAction.getSpaceshipsNumber()),
                    spaceshipX, spaceshipY - 20, Color.BLACK);
        }
    }
}

