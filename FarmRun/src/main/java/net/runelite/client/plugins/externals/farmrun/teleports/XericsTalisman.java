package net.runelite.client.plugins.externals.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum XericsTalisman {
    XERICS_TALISMAN("Xeric's Talisman"),
    POH("PoH (Mounted xeric's talisman)");

    private String name;

    @Override
    public String toString() { return name; }
}
