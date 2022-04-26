package net.runelite.client.plugins.externals.farmrun.ui.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SkillsNecklace {
    SKILLS_NECKLACE("Skills Necklace"),
    POH_JEWELRY_BOX("PoH Jewelry Box");

    private String name;

    @Override
    public String toString() { return name; }
}
