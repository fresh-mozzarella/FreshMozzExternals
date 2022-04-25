package net.runelite.client.plugins.externals.farmrun.teleports;

import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.externals.farmrun.TeleportTabs;

import static net.runelite.client.plugins.externals.farmrun.TeleportTabs.ARDOUGNE_TELEPORT_TAB;

@Getter
public enum Ardougne {
    RUNES("Runes", WidgetInfo.SPELL_ARDOUGNE_TELEPORT),
    TELEPORT_TAB("Teleport Tab", ARDOUGNE_TELEPORT_TAB),
    POH_PORTAL("PoH Portal", ARDOUGNE_PORTAL);

    private String name;
    private  teleportMethod;

    Ardougne(String string, TeleportTabs ardougneTeleport) {
        this.name = string;
        this.teleportMethod = teleportMethod;
    }

    Ardougne(String string) {
        this.name = string;
        this.teleportMethod = null;
    }

    @Override
    public String toString() { return name; }
}
