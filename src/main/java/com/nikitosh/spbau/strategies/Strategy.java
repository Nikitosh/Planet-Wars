package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.Action;
import com.nikitosh.spbau.model.GameState;

public interface Strategy {
    Action getAction(GameState state);
}
