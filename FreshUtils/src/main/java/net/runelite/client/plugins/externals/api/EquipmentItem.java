package net.runelite.client.plugins.externals.api;

import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.MenuAction;

import java.util.Arrays;
import java.util.List;

public class EquipmentItem implements Interactable {
    private final GameAPI gameAPI;
    private final Item item;
    private final ItemComposition definition;
    private final EquipmentSlot equipmentSlot;

    public EquipmentItem(GameAPI gameAPI, Item item, ItemComposition definition, EquipmentSlot equipmentSlot) {
        this.gameAPI = gameAPI;
        this.item = item;
        this.definition = definition;
        this.equipmentSlot = equipmentSlot;
    }

    public GameAPI gameAPI() {
        return gameAPI;
    }

    public int id() {
        return item.getId();
    }

    public String name() {
        return definition.getName();
    }

    public int quantity() {
        return item.getQuantity();
    }

    public int slot() {
        return equipmentSlot.index;
    }

    public ItemComposition definition() {
        return definition;
    }

    @Override
    public List<String> actions() {
        var actions = new String[10];
        actions[0] = "Remove";
        actions[9] = "Examine";
        var itemAttributes = definition().getParams();
        if (itemAttributes != null) {
            for (int i = 0; i < 8; i++) {
                if (itemAttributes.get(451 + i) != null) {
                    actions[i + 1] = definition.getStringValue(451 + i);
                }
            }
        }
        return Arrays.asList(actions);
    }

    @Override
    public void interact(String action) {
        List<String> actions = actions();

        for (int i = 0; i < actions.size(); i++) {
            if (action.equalsIgnoreCase(actions.get(i))) {
                interact(i + 1);
                return;
            }
        }

        throw new IllegalArgumentException("no action \"" + action + "\" on item " + id());
    }

    public void interact(int action) {
        gameAPI.interactionManager().interact(
                action,
                MenuAction.CC_OP.getId(),
                -1,
                equipmentSlot.widgetInfo.getId()
        );
    }

    public String toString() {
        return name() + " (" + id() + ")" + (quantity() == 1 ? "" : " x" + quantity());
    }
}
