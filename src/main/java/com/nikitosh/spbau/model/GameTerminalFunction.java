package com.nikitosh.spbau.model;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

public class GameTerminalFunction implements TerminalFunction {
    @Override
    public boolean isTerminal(State state) {
        GameState gameState = (GameState) state;
        return gameState.isWinning() || gameState.isLosing();
    }
}
