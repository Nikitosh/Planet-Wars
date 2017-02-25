package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

public class RandomStrategy implements Strategy {
    private Random random = new Random();
    private List<Action> actions;

    public RandomStrategy(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public List<ActionProbability> getActions(GameState state) {
        Agent opponent = (Agent) state.object(GameState.OPPONENT_NAME);
        List<Action> applicableActions = actions.stream()
                .filter(action -> ((MoveAction) action).isApplicable(state, opponent)).collect(Collectors.toList());
        return applicableActions.stream().map(action -> new ActionProbability(action, 1. / applicableActions.size()))
                .collect(Collectors.toList());
    }

    @Override
    public Action getAction(GameState state) {
        List<ActionProbability> possibleActions = getActions(state);
        return possibleActions.get(random.nextInt(possibleActions.size())).getAction();
    }
}
