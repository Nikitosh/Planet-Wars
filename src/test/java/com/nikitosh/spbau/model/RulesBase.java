package com.nikitosh.spbau.model;

import com.nikitosh.spbau.*;
import org.junit.*;

public class RulesBase {
    private static final int PLANET_CAPACITY = 20;

    private int oldPlanetCapacity;

    @Before
    public void setNewPlanetCapacity() {
        oldPlanetCapacity = Settings.getInstance().getPlanetCapacity();
        Settings.getInstance().setPlanetCapacity(PLANET_CAPACITY);
    }

    @After
    public void setOldPlanetCapacity() {
        Settings.getInstance().setPlanetCapacity(oldPlanetCapacity);
    }
}
