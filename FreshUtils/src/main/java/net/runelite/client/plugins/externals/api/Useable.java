package net.runelite.client.plugins.externals;

import net.runelite.client.plugins.externals.api.InventoryItem;
import net.runelite.client.plugins.externals.api.iNPC;
import net.runelite.client.plugins.externals.api.iObject;

public interface Useable {
    void useOn(iObject object);


    void useOn(iNPC npc);


    void useOn(InventoryItem item);
}
