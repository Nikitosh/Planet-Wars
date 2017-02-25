package com.nikitosh.spbau;

//CheckStyle:OFF: MagicNumber

public final class Settings {
    private static Settings instance;
    private int planetCapacity = 4;

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public int getPlanetCapacity() {
        return planetCapacity;
    }

    public void setPlanetCapacity(int planetCapacity) {
        this.planetCapacity = planetCapacity;
    }
}

//CheckStyle:ON: MagicNumber
