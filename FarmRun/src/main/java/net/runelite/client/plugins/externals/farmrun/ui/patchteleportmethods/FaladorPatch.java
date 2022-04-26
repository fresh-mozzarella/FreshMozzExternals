package net.runelite.client.plugins.externals.farmrun.ui.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FaladorPatch {
    FALADOR_TELEPORT("Falador Teleport"),
    EXPLORERS_RING("Explorer's Ring");

    private final String name;

    @Override
    public String toString() { return name; }
}
