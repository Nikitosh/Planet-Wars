package com.nikitosh.spbau.model;

import org.junit.*;
import org.junit.rules.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

//CheckStyle:OFF: MagicNumber

public class WorldTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testReadFromFile() throws IOException {
        File temporaryFile = temporaryFolder.newFile("map.txt");
        PrintWriter printWriter = new PrintWriter(temporaryFile);

        List<Planet> planets = Arrays.asList(
                new Planet(100, 100, 5, "Planet1"),
                new Planet(200, 100, 6, "Planet2"),
                new Planet(300, 100, 7, "Planet3"),
                new Planet(400, 100, 8, "Planet4")
                );
        List<Integer> owners = Arrays.asList(1, 0, 1, 2);

        printWriter.println(planets.size());
        for (int i = 0; i < planets.size(); i++) {
            printPlanet(printWriter, planets.get(i), owners.get(i));
        }

        List<MoveAction> edges = Arrays.asList(
                new MoveAction("Planet1", "Planet3", 0),
                new MoveAction("Planet1", "Planet2", 0),
                new MoveAction("Planet1", "Planet4", 0)
        );

        printWriter.println(edges.size());

        edges.forEach(edge -> printEdge(printWriter, edge.getSourceName(), edge.getDestinationName()));
        printWriter.flush();

        World world = World.readFromFile(temporaryFile);
        GameState state = (GameState) world.getState();

        GameState expectedState = new GameState(
                new Agent(Arrays.asList(new Planet(200, 100, 6, "Planet2")), GameState.NEUTRAL_NAME),
                new Agent(Arrays.asList(
                        new Planet(100, 100, 5, "Planet1"),
                        new Planet(300, 100, 7, "Planet3")),
                        GameState.AGENT_NAME),
                new Agent(Arrays.asList(new Planet(400, 100, 8, "Planet4")), GameState.OPPONENT_NAME)
                );
        assertEquals(expectedState, state);
    }

    private void printPlanet(PrintWriter printWriter, Planet planet, int owner) {
        printWriter.println(planet.getName() + " " + planet.getX() + " " + planet.getY() + " "
                + planet.getSpaceshipsNumber() + " " + owner);
    }

    private void printEdge(PrintWriter printWriter, String sourcePlanetName, String destinationPlanetName) {
        printWriter.println(sourcePlanetName + " " + destinationPlanetName);
    }
}

//CheckStyle:ON: MagicNumber
