package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.Action;
import com.nikitosh.spbau.model.*;

import java.util.stream.Collectors;

public class EmptyStrategy implements Strategy {
    @Override
    public Action getAction(GameState state) {
        String name = state.objects().stream().flatMap(agent -> ((Agent) agent).getPlanets().stream())
                .collect(Collectors.toList()).get(0).name();
        return new MoveAction(name, name, 0);
    }
}