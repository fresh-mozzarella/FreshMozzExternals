package net.runelite.client.plugins.externals.farmrun;

import lombok.Getter;

@Getter
public enum FarmingPatches {
    NONE("None"),
    ALLOTMENTS("Allotments"),
    BUSHES("Bushes"),
    FRUIT_TREES("Fruit Trees"),
    HERBS("Herbs"),
    HOPS("Hops"),
    TREES("Trees");

    private final String name;

    FarmingPatches(String name) { this.name = name; }
}