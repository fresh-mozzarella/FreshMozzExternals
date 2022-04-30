package net.runelite.client.plugins.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Falador {
    RUNES("Runes"),
    TELEPORT_TAB("Teleport Tab"),
    POH_PORTAL("PoH Portal");

    private String name;

    @Override
    public String toString() { return name; }
}
