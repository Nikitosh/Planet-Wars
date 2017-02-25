package com.nikitosh.spbau;

import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

public final class TestUtilities {
    private TestUtilities() {}

    public static Agent generateAgent(String name, List<Integer> spaceshipsNumberList, List<String> nameList) {
        Set<Planet> planets = new HashSet<>();
        for (int i = 0; i < spaceshipsNumberList.size(); i++) {
            planets.add(new Planet(0, 0, spaceshipsNumberList.get(i), nameList.get(i)));
        }
        return new Agent(planets, name);
    }

    public static Agent generateAgent(String name, List<Integer> spaceshipsNumberList) {
        return new Agent(spaceshipsNumberList.stream().map(spaceshipsNumber ->
                new Planet(0, 0, spaceshipsNumber, UUID.randomUUID().toString())).collect(Collectors.toSet()),
                name);
    }
}
