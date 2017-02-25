package com.nikitosh.spbau.model;

import burlap.mdp.core.action.*;

import java.util.stream.*;

public class MoveAction implements Action {
    private static final int P = 239017;

    private String sourceName;
    private String destinationName;
    private int spaceshipsNumber;

    public static final MoveAction WAITING_ACTION = new MoveAction("", "", 0);

    public MoveAction(String sourceName, String destinationName, int spaceshipsNumber) {
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.spaceshipsNumber = spaceshipsNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MoveAction)) {
            return false;
        }
        MoveAction action = (MoveAction) obj;
        return sourceName.equals(action.sourceName)
                && destinationName.equals(action.destinationName)
                && spaceshipsNumber == action.spaceshipsNumber;
    }

    @Override
    public int hashCode() {
        return spaceshipsNumber + sourceName.hashCode() * P + destinationName.hashCode() * P * P;
    }

    @Override
    public String toString() {
        return actionName();
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

    public boolean isApplicable(GameState state, Agent currentAgent) {
        if (equals(WAITING_ACTION)) {
            return true;
        }
        Planet source = state.getPlanet(sourceName);
        return source.getSpaceshipsNumber() >= spaceshipsNumber && currentAgent.getPlanets().contains(source);
    }

    public boolean isAttacking(GameState state, Agent currentAgent) {
        return !equals(WAITING_ACTION)
                && !currentAgent.getPlanets().stream().map(Planet::getName).collect(Collectors.toList())
                .contains(destinationName)
                && state.getPlanet(destinationName).getSpaceshipsNumber() < spaceshipsNumber;
    }

    public boolean isWaiting() {
        return equals(WAITING_ACTION);
    }
}
