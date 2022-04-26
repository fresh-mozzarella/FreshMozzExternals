package net.runelite.client.plugins.externals.farmrun.ui.patchteleportmethods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArdougnePatch {
    ARDOUGNE_TELEPORT("Ardougne teleport"),
    ARDOUGNE_CLOAK("Ardougne Cloak");

    private final String name;

    @Override
    public String toString() { return name; }
}
