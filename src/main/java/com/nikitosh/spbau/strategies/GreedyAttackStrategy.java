package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

public class GreedyAttackStrategy implements Strategy {
    private Random random = new Random();
    private List<Action> actions;

    public GreedyAttackStrategy(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public List<ActionProbability> getActions(GameState state) {
        Agent opponent = (Agent) state.object(GameState.OPPONENT_NAME);
        List<Action> applicableActions = actions.stream().filter(action -> {
            MoveAction moveAction = (MoveAction) action;
            return moveAction.isApplicable(state, opponent)
                    && (moveAction.isAttacking(state, opponent) || moveAction.isWaiting());
        }).collect(Collectors.toList());
        return applicableActions.stream().map(action -> new ActionProbability(action, 1. / applicableActions.size()))
                .collect(Collectors.toList());
    }

    @Override
    public Action getAction(GameState state) {
        List<ActionProbability> applicableActions = getActions(state);
        return applicableActions.get(random.nextInt(applicableActions.size())).getAction();
    }
}
