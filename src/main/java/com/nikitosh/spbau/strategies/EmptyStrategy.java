package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

public class EmptyStrategy implements Strategy {
    @Override
    public List<ActionProbability> getActions(GameState state) {
        String name = ((Agent) state.object(GameState.OPPONENT_NAME)).getPlanets().stream()
                .collect(Collectors.toList()).get(0).name();
        return Collections.singletonList(new ActionProbability(new MoveAction(name, name, 0), 1.));
    }

    @Override
    public Action getAction(GameState state) {
        return getActions(state).get(0).getAction();
    }
}
