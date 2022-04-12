package com.example.lecoin.lib;

public enum AdType {
    Game(0),
    Kitchen(1),
    Animal(2),
    Whatever(3);

    private int mID;

    AdType(int id) { mID = id; }

    public static AdType fromID(int id) {
        AdType[] values = values();
        if (id < 0 || id >= values.length)
            throw new IllegalArgumentException("Unknown ID given !" + id);

        return values[id];
    }

    public int getID() { return mID; }
}
