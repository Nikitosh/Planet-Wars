package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.*;
import burlap.mdp.core.state.*;

import java.util.*;
import java.util.stream.*;

public class    Agent implements ObjectInstance, State {
    public static final String CLASS_NAME = "Agent";

    private List<Planet> planets;
    private String name;

    private static class Vars {
        private static final String PLANETS = "planets";
    };

    private static final List<Object> keys = Arrays.<Object>asList(Vars.PLANETS);

    public Agent(List<Planet> planets, String name) {
        this.planets = planets;
        this.name = name;
    }

    public String className() {
        return CLASS_NAME;
    }

    public String name() {
        return name;
    }

    public ObjectInstance copyWithName(String newName) {
        return new Agent(planets.stream().map((planet) -> (Planet) planet.copy()).collect(Collectors.toList()), newName);
    }

    public List<Object> variableKeys() {
        return keys;
    }

    public Object get(Object variableKey) {
        if (variableKey.equals(Vars.PLANETS)) {
            return planets;
        }
        throw new UnknownKeyException(variableKey);
    }

    public State copy() {
        return copyWithName(name);
    }

}
