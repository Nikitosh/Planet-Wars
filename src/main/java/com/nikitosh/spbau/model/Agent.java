package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Agent implements ObjectInstance, State {
    public static final String CLASS_NAME = "AGENT_CLASS";

    private List<Planet> planets;
    private String name;

    private static class Vars {
        private static final String PLANETS = "PLANETS";
    };

    private static final List<Object> KEYS = Arrays.<Object>asList(Vars.PLANETS);

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
        return new Agent(planets.stream().map(Planet::copy).collect(Collectors.toList()), newName);
    }

    @Override
    public List<Object> variableKeys() {
        return KEYS;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Agent)) {
            return false;
        }
        Agent agent = (Agent) obj;
        return CollectionUtils.isEqualCollection(planets, agent.planets);
    }

    @Override
    public int hashCode() {
        return planets.hashCode();
    }
}
