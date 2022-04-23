package net.runelite.client.plugins.externals.farmrun.seeds;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AllotmentSeeds {
    WATERMELON_SEED("Watermelon",5321),
    SNAPE_GRASS_SEED("Snape_Grass",22879);

    private final String name;
    private final int itemID;

    @Override
    public String toString() { return name; }
}
