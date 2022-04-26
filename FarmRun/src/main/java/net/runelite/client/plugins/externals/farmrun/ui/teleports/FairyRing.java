package net.runelite.client.plugins.externals.farmrun.ui.teleports;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FairyRing {
    ARDOUGNE_CAPE("Ardougne Cape"),
    POH("PoH Fairy Ring");

    private String name;

    @Override
    public String toString() { return name; }
}
