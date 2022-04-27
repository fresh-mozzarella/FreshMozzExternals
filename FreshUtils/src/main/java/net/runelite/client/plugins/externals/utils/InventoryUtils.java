package net.runelite.client.plugins.externals.utils;

import net.runelite.api.Client;
import net.runelite.api.VarClientInt;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryUtils {

    public static Widget[] getInventoryItems(Client client)
    {
        if (client.getVar(VarClientInt.INVENTORY_TAB) != 3)
        {
            client.runScript(915, 3);
        }

        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);

        if (inventory == null || inventory.isHidden())
        {
            return null;
        }

        return inventory.getDynamicChildren();
    }



    public static Map<Integer, Integer> getInventoryItemsMap(final List<Integer> itemIds, Client client)
    {
        if (itemIds == null)
        {
            return null;
        }

        Widget[] inventoryItems = getInventoryItems(client);

        if (inventoryItems == null)
        {
            return null;
        }

        Map<Integer, Integer> items = new HashMap<>();

        for (Widget inventoryItem : inventoryItems)
        {

            if (itemIds.contains(inventoryItem.getItemId()))
            {
                items.put(inventoryItem.getItemId(), inventoryItem.getIndex());
            }
        }

        if (items.isEmpty())
        {
            return null;
        }

        return items;
    }

    public static boolean hasItem(final Integer itemId, Client client)
    {
        if (itemId == null)
        {
            return false;
        }

        Map<Integer, Integer> items = getInventoryItemsMap(List.of(itemId), client);

        return items != null && !items.isEmpty();
    }

    public static boolean hasItems(final List<Integer> itemIds, Client client)
    {
        if (itemIds == null)
        {
            return false;
        }

        Map<Integer, Integer> items = getInventoryItemsMap(itemIds, client);

        return items != null && !items.isEmpty() && items.size() == itemIds.size();
    }

}
