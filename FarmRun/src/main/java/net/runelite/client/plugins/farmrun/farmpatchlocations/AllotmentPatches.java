package net.runelite.client.plugins.farmrun.farmpatchlocations;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
public enum AllotmentPatches {
    NONE("None"),
    ARDOUGNE("Ardougne", new WorldPoint(2668, 3375, 0)),
    CATHERBY("Catherby", new WorldPoint(2811, 3465, 0)),
    FALADOR("Falador", new WorldPoint(3056, 3310, 0)),
    FARMING_GUILD("Farming Guild", new WorldPoint(1249, 3719, 0)),
    KOUREND("Kourend", new WorldPoint(1736, 3553, 0)),
    MORYTANIA("Morytania", new WorldPoint(3600, 3523, 0)),
    PRIFDDINAS("Prifddinas", new WorldPoint(3290, 6100, 0));

    private final String name;
    private WorldPoint worldPoint;

    AllotmentPatches(String name, WorldPoint worldPoint) {
        this.name = name;
        this.worldPoint = worldPoint;
    }

    AllotmentPatches(String name) {
        this.name = name;
    }
}
