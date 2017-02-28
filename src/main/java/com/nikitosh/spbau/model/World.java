package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import com.nikitosh.spbau.*;

import java.io.*;
import java.util.*;

public final class World {
    private List<Set<Planet>> planets = new ArrayList<>();
    private List<Planet> allPlanets = new ArrayList<>();
    private List<Action> agentActions = new ArrayList<>();
    private List<Action> allActions = new ArrayList<>();
    private List<MoveAction> edges = new ArrayList<>();

    private World() {
        for (int i = 0; i < GameState.PLAYERS_NUMBER + 1; i++) {
            planets.add(new HashSet<>());
        }
    }

    public static World readFromFile(File file) throws FileNotFoundException {
        World world = new World();

        Scanner scanner = new Scanner(file);
        int planetNumber = scanner.nextInt();
        for (int i = 0; i < planetNumber; i++) {
            String name = scanner.next();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int spaceshipsNumber = Settings.getInstance().getPlanetCapacity();
            int owner = scanner.nextInt();
            Planet planet = new Planet(x, y, spaceshipsNumber, name);
            world.planets.get(owner).add(planet);
            world.allPlanets.add(planet);
        }
        world.agentActions.add(MoveAction.WAITING_ACTION);
        world.allActions.add(MoveAction.WAITING_ACTION);

        int connectionsNumber = scanner.nextInt();
        for (int i = 0; i < connectionsNumber; i++) {
            String name1 = scanner.next();
            String name2 = scanner.next();
            world.edges.add(new MoveAction(name1, name2, 0));
            for (int j = 1; j < Settings.getInstance().getBucketsNumber(); j++) {
                world.agentActions.add(new MoveAction(name1, name2, j * Settings.getInstance().getBucketSize()));
                world.agentActions.add(new MoveAction(name2, name1, j * Settings.getInstance().getBucketSize()));
            }
            for (int j = 1; j <= Settings.getInstance().getPlanetCapacity(); j++) {
                world.allActions.add(new MoveAction(name1, name2, j * Settings.getInstance().getBucketSize()));
                world.allActions.add(new MoveAction(name2, name1, j * Settings.getInstance().getBucketSize()));
            }
        }
        return world;
    }

    public State getState() {
        return new GameState(new Agent(planets.get(0), GameState.NEUTRAL_NAME),
                new Agent(planets.get(1), GameState.AGENT_NAME),
                new Agent(planets.get(2), GameState.OPPONENT_NAME));
    }

    public List<Planet> getPlanets() {
        return allPlanets;
    }

    public List<Action> getAgentActions() {
        return agentActions;
    }

    public List<Action> getAllActions() {
        return allActions;
    }

    public List<MoveAction> getEdges() {
        return edges;
    }
}
