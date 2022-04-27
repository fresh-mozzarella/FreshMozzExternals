package net.runelite.client.plugins.externals.farmrun.teleports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IcyBasalt {
    POH_PORTAL("PoH Portal");

    private String name;

    @Override
    public String toString() { return name; }
}
