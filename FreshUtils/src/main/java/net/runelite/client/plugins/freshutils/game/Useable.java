package net.runelite.client.plugins.freshutils.game;

public interface Useable {
    void useOn(iObject object);

//    void useOn(GroundItem groundItem); TODO

    void useOn(iNPC npc);
//
//    void useOn(iPlayer player);

    void useOn(InventoryItem item);
}
