package net.runelite.client.plugins.externals.farmrun.ui.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum XericsTalisman {
    XERICS_TALISMAN("Xeric's Talisman");

    private String name;

    @Override
    public String toString() { return name; }
}
