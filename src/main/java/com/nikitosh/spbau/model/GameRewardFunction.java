package com.nikitosh.spbau.model;

import burlap.mdp.core.action.*;
import burlap.mdp.core.state.*;
import burlap.mdp.singleagent.model.*;

public class GameRewardFunction implements RewardFunction {
    private static final double INFINITY = 1e4;

    @Override
    public double reward(State state, Action action, State newState) {
        //if (((GameState) newState).isLosing()) {
        //    return -INFINITY;
        //}
        if (((GameState) newState).isWinning()) {
            return INFINITY;
        }
        return getEvaluation((GameState) newState);
    }

    private double getEvaluation(GameState gameState) {
        return getEvaluation((Agent) gameState.object(GameState.AGENT_NAME))
                - getEvaluation((Agent) gameState.object(GameState.OPPONENT_NAME));
    }

    private double getEvaluation(Agent agent) {
        return agent.getPlanets().stream().mapToInt(Planet::getSpaceshipsNumber).sum();
    }
}
