package net.runelite.client.plugins.farmrun.farmpatchlocations;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum BushPatches {
    NONE("None"),
    ARDOUGNE("Ardougne", new WorldPoint(2616, 3227, 0)),
    CHAMPIONS_GUILD("Champions Guild", new WorldPoint(3182, 3360, 0)),
    ETCETERIA("Etceteria", new WorldPoint(2592, 3865, 0)),
    FARMING_GUILD("Farming Guild", new WorldPoint(1249, 3719, 0)),
    RIMMINGTON("Rimmington", new WorldPoint(2942, 3223, 0));

    private final String name;
    private WorldPoint worldPoint;

    BushPatches(String name, WorldPoint worldPoint) {
        this.name = name;
        this.worldPoint = worldPoint;
    }

    BushPatches(String name) {
        this.name = name;
    }
}
