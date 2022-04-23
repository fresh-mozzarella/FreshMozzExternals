package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Hosidius {
    XERICS_TALISMAN("Xeric's Talisman"),
    POH_PORTAL("PoH Portal");

    private final String name;

    @Override
    public String toString() { return name; }
}
