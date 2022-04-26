package net.runelite.client.plugins.externals.farmrun.ui.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrollStrongholdPatch {
    STONY_BASALT("Stony Basalt"),
    TROLLHEIM_TELEPORT("Trollheim Teleport");

    private final String name;

    @Override
    public String toString() { return name; }
}
