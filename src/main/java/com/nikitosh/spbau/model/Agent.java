package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.*;
import burlap.mdp.core.state.*;

import java.util.*;
import java.util.stream.*;

public class Agent implements ObjectInstance, State {
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

    @Override
    public String className() {
        return CLASS_NAME;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Agent copyWithName(String newName) {
        return new Agent(planets.stream().map((planet) -> (Planet) planet.copy()).collect(Collectors.toList()), newName);
    }

    @Override
    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public Object get(Object variableKey) {
        if (variableKey.equals(Vars.PLANETS)) {
            return planets;
        }
        throw new UnknownKeyException(variableKey);
    }

    @Override
    public Agent copy() {
        return copyWithName(name);
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void removePlanet(Planet planet) {
        if (!planets.remove(planet)) {
            throw new RuntimeException("Could not remove planet");
        }
    }

    public void addPlanet(Planet planet) {
        if (planets.contains(planet)) {
            throw new RuntimeException("Planet is already in list");
        }
        planets.add(planet);
    }
}
