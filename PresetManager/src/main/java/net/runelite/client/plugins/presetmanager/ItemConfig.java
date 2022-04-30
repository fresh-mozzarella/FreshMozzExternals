package net.runelite.client.plugins.presetmanager;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class ItemConfig
{
    public int itemId;
    public int itemCount;
    public boolean stackable;
}
