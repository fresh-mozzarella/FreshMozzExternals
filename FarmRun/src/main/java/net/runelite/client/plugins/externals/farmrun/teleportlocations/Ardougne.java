package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Ardougne {
    RUNES("Runes"),
    TELEPORT_TABLET("Teleport Tablet"),
    POH_PORTAL("PoH Portal"),
    ARDOUGNE_CLOAK("Ardougne_Cloak");

    private final String name;

    @Override
    public String toString() { return name; }
}
