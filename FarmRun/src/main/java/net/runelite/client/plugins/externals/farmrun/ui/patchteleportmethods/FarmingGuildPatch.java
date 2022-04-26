package net.runelite.client.plugins.externals.farmrun.ui.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FarmingGuildPatch {
    SKILLS_NECKLACE("Skills Necklace"),
    FAIRY_RING("Fairy Ring");

    private final String name;

    @Override
    public String toString() { return name; }
}
