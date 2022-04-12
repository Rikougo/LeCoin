package com.example.lecoin.lib;

import androidx.annotation.NonNull;

public enum AdType {
    Game("Jeu"),
    Car("Voiture"),
    Animal("Animal"),
    House("Maison");

    public final String label;

    AdType(String name) {
        this.label = name;
    }

    public String Name() { return this.label; }

    @NonNull
    @Override
    public String toString()  {
        return this.label;
    }
}
