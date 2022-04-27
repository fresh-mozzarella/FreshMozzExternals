package net.runelite.client.plugins.externals.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.ItemID;

import java.util.List;

@AllArgsConstructor
@Getter
public enum SkillsNecklace {
    SKILLS_NECKLACE("Skills Necklace"),
    POH_JEWELRY_BOX("PoH Jewelry Box");

    private String name;

    @Override
    public String toString() { return name; }

    public static final List<Integer> SKILLS_NECKLACES = List.of(
            ItemID.SKILLS_NECKLACE1,
            ItemID.SKILLS_NECKLACE2,
            ItemID.SKILLS_NECKLACE3,
            ItemID.SKILLS_NECKLACE4,
            ItemID.SKILLS_NECKLACE5,
            ItemID.SKILLS_NECKLACE6
    );
}
