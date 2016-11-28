package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;

public class MoveAction implements Action {
    private static final String ACTION_NAME = "Move";

    private Planet source;
    private Planet destination;
    private int spaceshipsNumber;

    public MoveAction(Planet source, Planet destination, int spaceshipsNumber) {
        this.source = source;
        this.destination = destination;
        this.spaceshipsNumber = spaceshipsNumber;
    }

    @Override
    public String actionName() {
        return ACTION_NAME;
    }

    @Override
    public Action copy() {
        return new MoveAction(source, destination, spaceshipsNumber);
    }

    public Planet getSource() {
        return source;
    }

    public Planet getDestination() {
        return destination;
    }

    public int getSpaceshipsNumber() {
        return spaceshipsNumber;
    }
}
