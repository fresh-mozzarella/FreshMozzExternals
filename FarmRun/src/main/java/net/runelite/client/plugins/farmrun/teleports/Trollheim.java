package net.runelite.client.plugins.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Trollheim {
    RUNES("Runes"),
    POH_PORTAL("PoH Portal");

    private String name;

    @Override
    public String toString() { return name; }
}
