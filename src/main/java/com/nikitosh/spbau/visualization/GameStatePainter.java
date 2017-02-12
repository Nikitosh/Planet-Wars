package com.nikitosh.spbau.visualization;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.visualizer.StatePainter;
import com.nikitosh.spbau.model.*;

import java.awt.*;
import java.util.List;

public class GameStatePainter implements StatePainter {
    private static final float OPAQUE_LINE_LENGTH = 10;
    private static final float TRANSPARENT_LINE_LENGTH = 40;
    private static final float WIDTH = 3;

    private List<Action> edges;

    public GameStatePainter(List<Action> edges) {
        this.edges = edges;
    }

    @Override
    public void paint(Graphics2D graphics2D, State state, float cWidth, float cHeight) {
        GameState gameState = (GameState) state;
        edges.forEach(action -> {
            MoveAction moveAction = (MoveAction) action;
            Planet source = gameState.getPlanet(moveAction.getSourceName());
            Planet destination = gameState.getPlanet(moveAction.getDestinationName());
            drawDashedLine(graphics2D, source.getX(), source.getY(), destination.getX(), destination.getY());
        });
    }

    private void drawDashedLine(Graphics2D graphics2D, int x1, int y1, int x2, int y2) {
        float[] dashPattern = {OPAQUE_LINE_LENGTH, TRANSPARENT_LINE_LENGTH};
        Stroke stroke = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                dashPattern, 0));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawLine(x1, y1, x2, y2);
        graphics2D.setStroke(stroke);
    }
}
