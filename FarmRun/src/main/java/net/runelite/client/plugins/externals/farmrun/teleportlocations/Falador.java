package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Falador {
    RUNES("Runes"),
    TELEPORT_TABLET("Teleport Tablet"),
    POH_PORTAL("PoH Portal"),
    EXPLORERS_RING("Explorer's Ring");

    private final String name;

    @Override
    public String toString() { return name; }
}
