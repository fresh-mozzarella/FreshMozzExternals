package net.runelite.client.plugins.externals.farmrun.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.ItemID;

import java.util.List;

@AllArgsConstructor
@Getter
public enum FaladorPatch {
    FALADOR_TELEPORT("Falador Teleport", 11828),
    EXPLORERS_RING("Explorer's Ring", 12083);

    private final String name;
    private int regionID;

    FaladorPatch(String name) { this.name = name; }

    FaladorPatch(String name, Integer regionID ) {
        this.name = name;
        this.regionID = regionID;
    }

    @Override
    public String toString() { return name; }

    public static final List<Integer> EXPLORERS_RINGS = List.of(
            ItemID.EXPLORERS_RING_3,
            ItemID.EXPLORERS_RING_4
    );
}
