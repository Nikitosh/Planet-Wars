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

    public String className() {
        return CLASS_NAME;
    }

    public String name() {
        return name;
    }

    public ObjectInstance copyWithName(String newName) {
        return new Planet(x, y, spaceshipsNumber, newName);
    }

    public List<Object> variableKeys() {
        return keys;
    }

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

    public State copy() {
        return new Planet(x, y, spaceshipsNumber, name);
    }

    public void onTick() {
        spaceshipsNumber++;
    }
}
