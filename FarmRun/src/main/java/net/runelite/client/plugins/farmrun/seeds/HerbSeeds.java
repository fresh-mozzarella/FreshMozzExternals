package net.runelite.client.plugins.farmrun.seeds;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HerbSeeds {
    HARRALANDER_SEED("Harralander",5294),
    RANARR_SEED("Ranarr",5295),
    TOADFLAX_SEED("Toadflax",5296),
    IRIT_SEED("Irit",5297),
    AVANTOE_SEED("Avantoe",5298),
    KWUARM_SEED("Kwuarm",5299),
    SNAPDRAGON_SEED("Snapdragon",5300),
    CADANTINE_SEED("Cadantine",5301),
    LANTADYME_SEED("Lantadyme",5302),
    DWARF_WEED_SEED("Dwarf Weed",5303),
    TORSTOL_SEED("Torstol",5304);

    private final String name;
    private final int itemID;

    @Override
    public String toString() { return name; }

}
