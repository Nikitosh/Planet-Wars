package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class GameRewardFunction implements RewardFunction {
    @Override
    public double reward(State state, Action action, State newState) {
        GameState gameState = (GameState) state;
        GameState newGameState = (GameState) newState;
        return getEvaluation(newGameState) - getEvaluation(gameState);
    }

    private double getEvaluation(GameState gameState) {
        return getEvaluation((Agent) gameState.object(GameState.AGENT_NAME))
                - getEvaluation((Agent) gameState.object(GameState.OPPONENT_NAME));
    }

    private double getEvaluation(Agent agent) {
        return agent.getPlanets().stream().mapToInt(Planet::getSpaceshipsNumber).sum();
    }
}
