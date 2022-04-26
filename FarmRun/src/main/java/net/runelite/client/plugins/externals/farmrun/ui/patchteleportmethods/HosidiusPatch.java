package net.runelite.client.plugins.externals.farmrun.ui.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HosidiusPatch {
    XERICS_TALISMAN("Xeric's Talisman"),
    TELEPORT_TO_HOUSE("Teleport to house");

    private final String name;

    @Override
    public String toString() { return name; }
}
