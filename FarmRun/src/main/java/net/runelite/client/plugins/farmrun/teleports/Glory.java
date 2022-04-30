package net.runelite.client.plugins.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Glory {
    AMULET_OF_GLORY("Amulet of Glory"),
    POH_MOUNTED("Mounted in PoH"),
    POH_JEWELRY_BOX("PoH Jewelry Box");

    private String name;

    @Override
    public String toString() { return name; }
}
