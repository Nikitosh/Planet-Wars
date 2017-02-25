package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.model.*;

import java.util.*;

public class EmptyStrategy implements Strategy {
    @Override
    public List<ActionProbability> getActions(GameState state) {
        return Collections.singletonList(new ActionProbability(MoveAction.WAITING_ACTION, 1));
    }

    @Override
    public Action getAction(GameState state) {
        return getActions(state).get(0).getAction();
    }
}
