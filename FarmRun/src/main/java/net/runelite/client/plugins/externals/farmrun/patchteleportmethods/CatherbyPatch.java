package net.runelite.client.plugins.externals.farmrun.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CatherbyPatch {
    CAMELOT_TELEPORT("Camelot Teleport"),
    CATHERBY_TELEPORT("Catherby Teleport");

    private final String name;

    @Override
    public String toString() { return name; }
}
