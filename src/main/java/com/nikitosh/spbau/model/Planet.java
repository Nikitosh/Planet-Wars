package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;
import com.nikitosh.spbau.*;

import java.util.*;

@DeepCopyState
public class Planet implements ObjectInstance, State {
    private static final int P = 239017;
    public static final String CLASS_NAME = "PLANET_CLASS";

    private static class Vars {
        private static final String X = "X";
        private static final String Y = "Y";
        private static final String SPACESHIPS_NUMBER = "SPACESHIPS_NUMBER";
    };
    private static final List<Object> KEYS = Arrays.<Object>asList(Vars.X, Vars.Y, Vars.SPACESHIPS_NUMBER);

    private int x;
    private int y;
    private int spaceshipsNumber;
    private String name;

    public Planet(int x, int y, int spaceshipsNumber, String name) {
        this.x = x;
        this.y = y;
        this.spaceshipsNumber = spaceshipsNumber;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Planet)) {
            return false;
        }
        Planet planet = (Planet) obj;
        int bucketSize = Settings.getInstance().getBucketSize();
        return name.equals(planet.name) && spaceshipsNumber / bucketSize == planet.spaceshipsNumber / bucketSize;
    }

    @Override
    public int hashCode() {
        return (spaceshipsNumber / Settings.getInstance().getBucketSize()) * P + name.hashCode();
    }

    @Override
    public String toString() {
        return name + " - " + spaceshipsNumber;
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
        return KEYS;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void increaseSpaceshipsNumber(int increment) {
        spaceshipsNumber += increment;
    }

    public void setSpaceshipsNumber(int spaceshipsNumber) {
        this.spaceshipsNumber = spaceshipsNumber;
    }

    public void normalize() {
        spaceshipsNumber = Math.min(spaceshipsNumber, Settings.getInstance().getPlanetCapacity());
    }
}
