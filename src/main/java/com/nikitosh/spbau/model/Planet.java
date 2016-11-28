package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.*;

@DeepCopyState
public class Planet implements ObjectInstance, State {
    public static final String CLASS_NAME = "Planet";

    private int x;
    private int y;
    private int spaceshipsNumber;
    private String name;

    private static class Vars {
        private static final String X = "x";
        private static final String Y = "y";
        private static final String SPACESHIPS_NUMBER = "spaceshipsNumber";
    };

    private static final List<Object> keys = Arrays.<Object>asList(Vars.X, Vars.Y, Vars.SPACESHIPS_NUMBER);

    public Planet(int x, int y, int spaceshipsNumber, String name) {
        this.x = x;
        this.y = y;
        this.spaceshipsNumber = spaceshipsNumber;
        this.name = name;
    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Planet copyWithName(String newName) {
        return new Planet(x, y, spaceshipsNumber, newName);
    }

    @Override
    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public Object get(Object variableKey) {
        if (variableKey.equals(Vars.X)) {
            return x;
        }
        if (variableKey.equals(Vars.Y)) {
            return y;
        }
        if (variableKey.equals(Vars.SPACESHIPS_NUMBER)) {
            return spaceshipsNumber;
        }
        throw new UnknownKeyException(variableKey);
    }

    @Override
    public Planet copy() {
        return new Planet(x, y, spaceshipsNumber, name);
    }

    public void onTick() {
        spaceshipsNumber++;
    }

    public int getSpaceshipsNumber() {
        return spaceshipsNumber;
    }

    public void increaseSpaceshipsNumber(int increment) {
        spaceshipsNumber += increment;
    }

    public void setSpaceshipsNumber(int spaceshipsNumber) {
        this.spaceshipsNumber = spaceshipsNumber;
    }
}
