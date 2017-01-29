package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class GameRewardFunction implements RewardFunction {
    public static final double INFINITY = 1e9;

    @Override
    public double reward(State state, Action action, State newState) {
        GameState gameState = (GameState) state;
        if (!gameState.isApplicable((MoveAction) action)) {
            return -INFINITY;
        }
        GameState newGameState = (GameState) newState;
        return getEvaluation(newGameState) - getEvaluation(gameState);
    }

    private double getEvaluation(GameState gameState) {
        return getEvaluation((Agent) gameState.object(GameState.AGENT_NAME))
                - getEvaluation((Agent) gameState.object(GameState.OPPONENT_NAME));
    }

    private double getEvaluation(Agent agent) {
        //return agent.getPlanets().stream().mapToInt(Planet::getSpaceshipsNumber).sum();
        return agent.getPlanets().stream().mapToInt(planet -> planet.getSpaceshipsNumber() + Planet.MAX_CAPACITY).sum();
    }
}
