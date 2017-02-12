package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;

public class MoveAction implements Action {
    private String sourceName;
    private String destinationName;
    private int spaceshipsNumber;

    public MoveAction(String sourceName, String destinationName, int spaceshipsNumber) {
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.spaceshipsNumber = spaceshipsNumber;
    }

    @Override
    public String actionName() {
        return sourceName + " to " + destinationName + ": " + spaceshipsNumber;
    }

    @Override
    public Action copy() {
        return new MoveAction(sourceName, destinationName, spaceshipsNumber);
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public int getSpaceshipsNumber() {
        return spaceshipsNumber;
    }

    public void increaseSpaceshipsNumber(int increment) {
        spaceshipsNumber += increment;
    }

    @Override
    public String toString() {
        return actionName();
    }
}
