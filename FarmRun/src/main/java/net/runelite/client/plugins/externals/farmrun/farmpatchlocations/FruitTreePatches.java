package net.runelite.client.plugins.externals.farmrun.farmpatchlocations;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum FruitTreePatches {
    NONE("None"),
    BRIMHAVEN("Brimhaven", new WorldPoint(2765, 3214, 0)),
    CATHERBY("Catherby", new WorldPoint(2858, 3434, 0)),
    FARMING_GUILD("Farming Guild", new WorldPoint(1249, 3719, 0)),
    GNOME_STRONGHOLD("Gnome Stronghold", new WorldPoint(2473, 3446, 0)),
    TREE_GNOME_VILLAGE("Tree Gnome Village", new WorldPoint(2489, 3182, 0)),
    TAI_BWO_WANNAI("Tai Bwo Wannai", new WorldPoint(2794, 3103, 0)),
    PRIFDDINAS("Prifddinas", new WorldPoint(3292, 6117, 0));

    private final String name;
    private WorldPoint worldPoint;

    FruitTreePatches(String name, WorldPoint worldPoint) {
        this.name = name;
        this.worldPoint = worldPoint;
    }

    FruitTreePatches(String name) {
        this.name = name;
    }
}
