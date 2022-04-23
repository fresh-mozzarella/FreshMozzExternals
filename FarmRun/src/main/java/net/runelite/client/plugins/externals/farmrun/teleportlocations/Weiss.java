package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Weiss {
    POH_PORTAL("PoH_Portal");

    private final String name;

    @Override
    public String toString() { return name; }
}
