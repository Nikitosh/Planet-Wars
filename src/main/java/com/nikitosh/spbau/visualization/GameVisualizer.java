package com.nikitosh.spbau.visualization;

import burlap.mdp.core.action.*;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.*;

import java.util.*;

public class GameVisualizer {
    private List<Action> edges;

    public GameVisualizer(List<Action> edges) {
        this.edges = edges;
    }

    private StateRenderLayer getStateRenderLayer() {
        StateRenderLayer renderLayer = new StateRenderLayer();
        renderLayer.addStatePainter(new GameStatePainter(edges));

        OOStatePainter ooStatePainter = new OOStatePainter();
        ooStatePainter.addObjectClassPainter(Agent.CLASS_NAME, new GameAgentPainter());
        renderLayer.addStatePainter(ooStatePainter);

        return renderLayer;
    }

    public Visualizer getVisualizer() {
        Visualizer visualizer = new Visualizer(getStateRenderLayer());
        visualizer.setStateActionRenderLayer(new GameStateActionRenderLayer(), true);
        return visualizer;
    }
}
