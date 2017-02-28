package com.nikitosh.spbau;

//CheckStyle:OFF: MagicNumber

public final class Settings {
    private static Settings instance;
    private int bucketSize = 7;
    private int bucketsNumber = 3;
    private int planetCapacity = bucketSize * bucketsNumber - 1;

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

    public int getBucketSize() {
        return bucketSize;
    }

    public int getBucketsNumber() {
        return bucketsNumber;
    }
}

//CheckStyle:ON: MagicNumber
