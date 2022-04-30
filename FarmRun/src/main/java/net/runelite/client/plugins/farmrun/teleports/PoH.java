package net.runelite.client.plugins.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PoH {
    RUNES("Runes"),
    TELEPORT_TAB("Teleport Tab");

    private String name;

    @Override
    public String toString() { return name; }
}
