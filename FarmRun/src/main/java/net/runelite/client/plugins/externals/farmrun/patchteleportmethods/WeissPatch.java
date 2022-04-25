package net.runelite.client.plugins.externals.farmrun.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeissPatch {
    ICY_BASALT("Icy Basalt");

    private final String name;

    @Override
    public String toString() { return name; }
}
