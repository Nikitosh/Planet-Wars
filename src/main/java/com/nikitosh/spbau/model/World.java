package com.nikitosh.spbau.model;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;

import java.io.*;
import java.util.*;

public final class World {
    private List<List<Planet>> planets = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    private World() {
        for (int i = 0; i < GameState.PLAYERS_NUMBER + 1; i++) {
            planets.add(new ArrayList<>());
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
            int spaceshipsNumber = scanner.nextInt();
            int owner = scanner.nextInt();
            Planet planet = new Planet(x, y, spaceshipsNumber, name);
            world.planets.get(owner).add(planet);
        }

        int connectionsNumber = scanner.nextInt();
        for (int i = 0; i < connectionsNumber; i++) {
            String name1 = scanner.next();
            String name2 = scanner.next();
            for (int j = 0; j <= Planet.MAX_CAPACITY; j++) {
                world.actions.add(new MoveAction(name1, name2, j));
                world.actions.add(new MoveAction(name2, name1, j));
            }
        }
        return world;
    }

    public State getState() {
        return new GameState(new Agent(planets.get(0), GameState.NEUTRAL_NAME),
                new Agent(planets.get(1), GameState.AGENT_NAME),
                new Agent(planets.get(2), GameState.OPPONENT_NAME));
    }

    public List<Action> getActions() {
        return actions;
    }
}
