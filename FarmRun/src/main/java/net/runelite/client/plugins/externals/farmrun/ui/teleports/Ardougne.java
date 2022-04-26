package net.runelite.client.plugins.externals.farmrun.ui.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Ardougne {
    RUNES("Runes"),
    TELEPORT_TAB("Teleport Tab"),
    POH_PORTAL("PoH Portal");

    private String name;

    @Override
    public String toString() { return name; }
}
