package net.runelite.client.plugins.externals.api;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemComposition;
import net.runelite.api.MenuAction;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.plugins.externals.Useable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.runelite.api.MenuAction.WIDGET_TARGET_ON_GAME_OBJECT;

@Slf4j
public class InventoryItem implements Interactable, Useable {

    private final GameAPI gameAPI;
    private final WidgetItem widgetItem;
    private final ItemComposition definition;

    public InventoryItem(GameAPI gameAPI, WidgetItem widgetItem, ItemComposition definition) {
        this.gameAPI = gameAPI;
        this.widgetItem = widgetItem;
        this.definition = definition;
    }

    public GameAPI gameAPI() {
        return gameAPI;
    }

    public int id() {
        return widgetItem.getId();
    }

    public String name() {
        return definition.getName();
    }

    public int quantity() {
        return widgetItem.getQuantity();
    }

    public int slot() {
        return widgetItem.getIndex();
    }

    public ItemComposition definition() {
        return definition;
    }

    @Override
    public List<String> actions() {
        return Arrays.stream(definition.getInventoryActions()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void interact(String action) {
        List<String> actions = actions();

        for (int i = 0; i < actions.size(); i++) {
            if (action.equalsIgnoreCase(actions.get(i))) {
                interact(i);
                return;
            }
        }

        throw new IllegalArgumentException("no action \"" + action + "\" on item " + id());
    }

    private int getMenuId(int action) {
        return gameAPI.inventoryAssistant.itemOptionToId(id(), actions().get(action));
    }

    private int getMenuAction(int action) {
        return gameAPI.inventoryAssistant.idToMenuAction(getMenuId(action)).getId();
    }

    public void interact(int action) {
        gameAPI.interactionManager().interact(
                getMenuId(action),
                getMenuAction(action),
                slot(),
                WidgetInfo.INVENTORY.getId()
        );
    }

    @Override
    public void useOn(InventoryItem item) {
        gameAPI.interactionManager().submit(() -> {
            gameAPI.clientThread.invoke(() -> {


                gameAPI.client.setSelectedSpellWidget(WidgetInfo.INVENTORY.getId());
                gameAPI.client.setSelectedSpellChildIndex(item.slot());
                gameAPI.client.setSelectedSpellItemId(item.id());
                gameAPI.client.setSpellSelected(true);
                gameAPI.client.invokeMenuAction("", "", 0,
                        MenuAction.WIDGET_TARGET_ON_WIDGET.getId(), slot(), WidgetInfo.INVENTORY.getId());
            });
        });
    }

    @Override
    public void useOn(iObject object) {
        gameAPI.interactionManager().submit(() -> {
            gameAPI.clientThread.invoke(() -> {

                gameAPI.client.setSelectedSpellWidget(WidgetInfo.INVENTORY.getId());
                gameAPI.client.setSelectedSpellChildIndex(slot());
                gameAPI.client.setSelectedSpellItemId(id());
                gameAPI.client.setSpellSelected(true);

                gameAPI.client.invokeMenuAction("", "", object.id(),
                        WIDGET_TARGET_ON_GAME_OBJECT.getId(), object.menuPoint().getX(), object.menuPoint().getY());
            });
        });
    }

    @Override
    public void useOn(iNPC npc) {
        gameAPI.interactionManager().submit(() -> {
            gameAPI.clientThread.invoke(() -> {
                //gameAPI.client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
                //gameAPI.client.setSelectedItemSlot(slot());
                //gameAPI.client.setSelectedItemID(id());

                gameAPI.client.setSelectedSpellWidget(WidgetInfo.INVENTORY.getId());
                gameAPI.client.setSelectedSpellChildIndex(slot());
                gameAPI.client.setSelectedSpellItemId(id());
                gameAPI.client.setSpellSelected(true);

                gameAPI.client.invokeMenuAction("", "", npc.index(),
                        MenuAction.ITEM_USE_ON_NPC.getId(), 0, 0);
            });
        });
    }
//
//    @Override
//    public void useOn(iPlayer player) {
//        gameAPI.mouseClicked();
//        gameAPI.connection().playerUseItem(player.index(), id, slot, (containingWidget.group << 16) + containingWidget.file, gameAPI.ctrlRun);
//    }
//
//    @Override
//    public void useOn(iNPC npc) {
//        gameAPI.mouseClicked();
//        gameAPI.connection().npcUseItem(npc.index(), id, slot, (containingWidget.group << 16) + containingWidget.file, gameAPI.ctrlRun);
//    }

    public String toString() {
        return name() + " (" + id() + ")" + (quantity() == 1 ? "" : " x" + quantity());
    }
}
