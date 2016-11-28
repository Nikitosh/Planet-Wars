package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.*;
import burlap.mdp.core.state.*;

import java.util.*;
import java.util.stream.Collectors;

public class GameState implements OOState {
    private Agent agent;
    private Agent opponent;
    private Agent neutral;

    public GameState(Agent agent, Agent opponent, Agent neutral) {
        this.agent = agent;
        this.opponent = opponent;
        this.neutral = neutral;
    }

    @Override
    public List<Object> variableKeys() {
        return OOStateUtilities.flatStateKeys(this);
    }

    @Override
    public Object get(Object variableKey) {
        return OOStateUtilities.get(this, variableKey);
    }

    @Override
    public State copy() {
        return new GameState(agent, opponent, neutral);
    }

    @Override
    public int numObjects() {
        return 3;
    }

    @Override
    public ObjectInstance object(String objectName) {
        return objects().stream().filter((object) -> object.name().equals(objectName)).findAny().orElse(null);
    }

    @Override
    public List<ObjectInstance> objects() {
        return Arrays.asList(agent, opponent, neutral);
    }

    @Override
    public List<ObjectInstance> objectsOfClass(String objectName) {
        return objects().stream().filter((object) -> object.className().equals(objectName)).collect(Collectors.toList());
    }
}
