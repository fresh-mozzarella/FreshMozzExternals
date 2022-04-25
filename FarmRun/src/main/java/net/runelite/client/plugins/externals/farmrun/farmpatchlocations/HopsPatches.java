package net.runelite.client.plugins.externals.farmrun.farmpatchlocations;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum HopsPatches {
    NONE("None"),
    LUMBRIDGE("Lumbridge", new WorldPoint(3231, 3312, 0)),
    SEERS_VILLAGE("Seers' Village", new WorldPoint(2670, 3522, 0)),
    YANILLE("Yanille", new WorldPoint(2578, 3102, 0));

    private final String name;
    private WorldPoint worldPoint;

    HopsPatches(String name, WorldPoint worldPoint) {
        this.name = name;
        this.worldPoint = worldPoint;
    }

    HopsPatches(String name) {
        this.name = name;
    }
}
