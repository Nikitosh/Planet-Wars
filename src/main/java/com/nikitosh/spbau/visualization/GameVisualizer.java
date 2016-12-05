package com.nikitosh.spbau.visualization;

import burlap.mdp.core.action.Action;
import burlap.visualizer.*;
import com.nikitosh.spbau.model.Agent;

import java.util.List;

public class GameVisualizer {
    private List<Action> edges;

    public GameVisualizer(List<Action> edges) {
        this.edges = edges;
    }

    public StateRenderLayer getStateRenderLayer() {
        StateRenderLayer renderLayer = new StateRenderLayer();
        renderLayer.addStatePainter(new GameStatePainter(edges));

        OOStatePainter ooStatePainter = new OOStatePainter();
        ooStatePainter.addObjectClassPainter(Agent.CLASS_NAME, new GameAgentPainter());
        renderLayer.addStatePainter(ooStatePainter);

        return renderLayer;
    }

    public Visualizer getVisualizer() {
        return new Visualizer(getStateRenderLayer());
    }
}
