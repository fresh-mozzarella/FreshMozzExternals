package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FarmingGuild {
    SKILLS_NECKLACE("Skills Necklace"),
    POH_JEWELRY_BOX("POH Jewelry Box");

    private final String name;

    @Override
    public String toString() { return name; }
}
