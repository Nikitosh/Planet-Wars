package com.nikitosh.spbau.algorithms;

import com.nikitosh.spbau.model.*;

import java.util.*;

public class BFSPlanner {
    public static final int INFINITY = (int) 1e9;

    private List<Planet> planets;
    private List<MoveAction> edges;
    private Queue<String> queue = new LinkedList<>();
    private HashMap<Pair<String, String>, Integer> distances = new HashMap<>();
    private HashMap<Pair<String, String>, String> firstPlanetInPath = new HashMap<>();

    public BFSPlanner(World world) {
        planets = world.getPlanets();
        edges = world.getEdges();
    }

    public void planAllPaths() {
        for (Planet startPlanet : planets) {
            queue.clear();
            String startName = startPlanet.getName();
            queue.add(startName);
            for (Planet otherPlanets : planets) {
                distances.put(new Pair<>(startName, otherPlanets.getName()), INFINITY);
                firstPlanetInPath.put(new Pair<>(startName, otherPlanets.getName()), startName);
            }
            distances.put(new Pair<>(startName, startName), 0);
            while (!queue.isEmpty()) {
                String name = queue.remove();
                int currentDistance = distances.get(new Pair<>(startName, name));
                for (MoveAction edge : edges) {
                    String nextPlanetName = null;
                    if (edge.getSourceName().equals(name)) {
                        nextPlanetName = edge.getDestinationName();
                    }
                    if (edge.getDestinationName().equals(name)) {
                        nextPlanetName = edge.getSourceName();
                    }
                    if (nextPlanetName != null) {
                        Pair<String, String> key = new Pair<>(startName, nextPlanetName);
                        if (distances.get(key) > currentDistance + 1) {
                            distances.put(key, currentDistance + 1);
                            String firstPlanetName = firstPlanetInPath.get(new Pair<>(startName, name));
                            if (firstPlanetName.equals(startName)) {
                                firstPlanetInPath.put(new Pair<>(startName, nextPlanetName), nextPlanetName);
                            } else {
                                firstPlanetInPath.put(new Pair<>(startName, nextPlanetName), firstPlanetName);
                            }
                            queue.add(nextPlanetName);
                        }
                    }
                }
            }
        }
    }

    public int getDistance(Planet sourcePlanet, Planet destinationPlanet) {
        return distances.get(new Pair<>(sourcePlanet.getName(), destinationPlanet.getName()));
    }

    public String getFirstPlanetInPath(Planet sourcePlanet, Planet destinationPlanet) {
        return firstPlanetInPath.get(new Pair<>(sourcePlanet.getName(), destinationPlanet.getName()));
    }
}
