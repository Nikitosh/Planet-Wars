package com.nikitosh.spbau.strategies;

import burlap.mdp.core.action.*;

public class ActionProbability {
    private Action action;
    private double probability;

    public ActionProbability(Action action, double probability) {
        this.action = action;
        this.probability = probability;
    }

    public Action getAction() {
        return action;
    }

    public double getProbability() {
        return probability;
    }
}
