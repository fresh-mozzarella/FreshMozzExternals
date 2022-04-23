package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PortPhasmatys {
    ECTOPHIAL("Ectophial"),
    FAIRY_RING("Fairy Ring");

    private final String name;

    @Override
    public String toString() { return name; }
}
