package com.nikitosh.spbau.model;

import burlap.mdp.core.oo.state.*;
import burlap.mdp.core.state.State;
import burlap.statehashing.HashableState;

import java.util.*;
import java.util.stream.Collectors;

public class GameState implements OOState, HashableState {
    private static final int P = 239017;
    public static final int PLAYERS_NUMBER = 2;
    public static final String AGENT_NAME = "AGENT";
    public static final String OPPONENT_NAME = "OPPONENT";
    public static final String NEUTRAL_NAME = "NEUTRAL";

    private Agent agent;
    private Agent opponent;
    private Agent neutral;

    public GameState(Agent neutral, Agent agent, Agent opponent) {
        this.neutral = neutral;
        this.agent = agent;
        this.opponent = opponent;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GameState)) {
            return false;
        }
        GameState state = (GameState) obj;
        return neutral.equals(state.neutral) && agent.equals(state.agent) && opponent.equals(state.opponent);
    }

    @Override
    public int hashCode() {
        return agent.hashCode() * P * P + opponent.hashCode() * P + neutral.hashCode();
    }

    @Override
    public String toString() {
        return neutral.toString() + "\n" + agent.toString() + "\n" + opponent.toString();
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
        return new GameState(neutral, agent, opponent);
    }

    @Override
    public int numObjects() {
        return PLAYERS_NUMBER + 1;
    }

    @Override
    public ObjectInstance object(String objectName) {
        return objects().stream().filter((object) -> object.name().equals(objectName)).findAny().orElse(null);
    }

    @Override
    public List<ObjectInstance> objects() {
        return Arrays.asList(neutral, agent, opponent);
    }

    @Override
    public List<ObjectInstance> objectsOfClass(String objectName) {
        return objects().stream().filter((object) -> object.className().equals(objectName))
                .collect(Collectors.toList());
    }

    @Override
    public State s() {
        return this;
    }

    public Agent touchNeutral() {
        neutral = neutral.copy();
        return neutral;
    }

    public Agent touchAgent() {
        agent = agent.copy();
        return agent;
    }

    public Agent touchOpponent() {
        opponent = opponent.copy();
        return opponent;
    }

    private List<Planet> getAllPlanets() {
        return objects().stream().flatMap(object -> ((Agent) object).getPlanets().stream())
                .collect(Collectors.toList());
    }

    public Planet getPlanet(String name) {
        return getAllPlanets().stream().filter(planet -> planet.name().equals(name)).findAny().orElse(null);
    }

    public Agent getNeutral() {
        return neutral;
    }

    public Agent getAgent() {
        return agent;
    }

    public Agent getOpponent() {
        return opponent;
    }

    public boolean isLosing() {
        return agent.getPlanets().isEmpty();
    }

    public boolean isWinning() {
        return opponent.getPlanets().isEmpty();
    }
}
