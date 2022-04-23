package net.runelite.client.plugins.externals.farmrun.teleportlocations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrollStronghold {
    POH_PORTAL("PoH Portal"),
    RUNES("Runes");

    private final String name;

    @Override
    public String toString() { return name; }
}
