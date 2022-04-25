package net.runelite.client.plugins.externals.farmrun.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PortPhasmatysPatch {
    ECTOPHIAL("Ectophial"),
    FAIRY_RING("Fairy Ring");

    private final String name;

    @Override
    public String toString() { return name; }
}
