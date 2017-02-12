package com.nikitosh.spbau;

import com.nikitosh.spbau.model.*;

import java.util.*;
import java.util.stream.*;

public final class TestUtilities {
    private TestUtilities() {}

    public static Agent generateAgent(String name, List<Integer> spaceshipsNumberList) {
        return new Agent(spaceshipsNumberList.stream()
                .map(spaceshipsNumber -> new Planet(0, 0, spaceshipsNumber, UUID.randomUUID().toString()))
                .collect(Collectors.toList()), name);
    }
}
