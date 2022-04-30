package net.runelite.client.plugins.farmrun.farmpatchlocations;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum TreePatches {
    NONE("None"),
    FALADOR("Falador", new WorldPoint(3002, 3374, 0)),
    FARMING_GUILD("Farming Guild", new WorldPoint(1249, 3719, 0)),
    GNOME_STRONGHOLD("Gnome Stronghold", new WorldPoint(2437, 3418, 0)),
    LUMBRIDGE("Lumbridge", new WorldPoint(3195, 3230, 0)),
    TAVERLEY("Taverley", new WorldPoint(2936, 3450, 0)),
    VARROCK("Varrock", new WorldPoint(3227, 3457, 0));

    private final String name;
    private WorldPoint worldPoint;

    TreePatches(String name, WorldPoint worldPoint) {
        this.name = name;
        this.worldPoint = worldPoint;
    }

    TreePatches(String name) {
        this.name = name;
    }
}
