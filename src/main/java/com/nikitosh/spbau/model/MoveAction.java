package com.nikitosh.spbau.model;

import burlap.mdp.core.action.*;

import java.util.stream.*;

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

    public boolean isApplicable(GameState state, Agent currentAgent) {
        Planet source = state.getPlanet(sourceName);
        return source.getSpaceshipsNumber() >= spaceshipsNumber && currentAgent.getPlanets().indexOf(source) != -1;
    }

    public boolean isAttacking(GameState state, Agent currentAgent) {
        return !currentAgent.getPlanets().stream().map(Planet::getName).collect(Collectors.toList())
                .contains(destinationName)
                && state.getPlanet(destinationName).getSpaceshipsNumber() < spaceshipsNumber;
    }

    public boolean isWaiting() {
        return spaceshipsNumber == 0;
    }
}
