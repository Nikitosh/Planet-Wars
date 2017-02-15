package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;
import com.nikitosh.spbau.model.*;

import java.util.*;

public interface Strategy {
    List<ActionProbability> getActions(GameState state);
    Action getAction(GameState state);
}
