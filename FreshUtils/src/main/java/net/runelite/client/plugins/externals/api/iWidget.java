package net.runelite.client.plugins.externals.api;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.plugins.externals.Useable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class iWidget implements Interactable, Useable {

    private final GameAPI gameAPI;
    private final Widget widget;
    Map<Integer, iWidget> children = new HashMap<>();

    public iWidget(GameAPI gameAPI, Widget widget) {
        this.gameAPI = gameAPI;
        this.widget = widget;
    }

    public GameAPI gameAPI() {
        return gameAPI;
    }

    public Client client() {
        return gameAPI.client();
    }

    public int id() {
        return widget.getId();
    }

    public int itemId() {
        return widget.getItemId();
    }

    public int index() {
//        return game.getFromClientThread(() -> widget.getIndex());
        return widget.getIndex();
    }

    public int x() {
        return widget.getOriginalX();
    }

    public int y() {
        return widget.getOriginalY();
    }

    public String text() {
        return widget.getText();
    }

    public String name() {
        if (widget.getName().contains(">")) {
            String result = StringUtils.substringBetween(widget.getName()
                    , ">"
                    , "<");
            return result;
        }
        return widget.getName();
    }

    public int quantity() {
        return widget.getItemQuantity();
    }

    public boolean hidden() {
        if (widget == null) {
            return true;
        }
        return gameAPI.getFromClientThread(widget::isHidden);
    }

    public List<WidgetItem> getWidgetItems() {
        ArrayList<WidgetItem> items = new ArrayList<>();

        for (WidgetItem slot : widget.getWidgetItems()) {
            if (slot != null) {
                items.add(slot);
            }
        }
        return items;
    }

    public List<iWidget> items() {
        ArrayList<iWidget> items = new ArrayList<>();

        for (Widget slot : widget.getDynamicChildren()) {
            if (slot != null) {
                items.add(new iWidget(gameAPI(), slot));
            }
        }
        return items;
    }

    public int nestedInterface() {
        Widget[] nested = gameAPI.getFromClientThread(widget::getNestedChildren);

        if (nested.length == 0) {
            return -1;
        }

        return nested[0].getId() >> 16;
    }

    @Override
    public List<String> actions() {
        return Arrays.stream(widget.getActions())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void interact(String action) {
        String[] actions = widget.getActions();
        for (int i = 0; i < actions.length; i++) {
            if (action.equalsIgnoreCase(actions[i])) {
                interact(i);
                return;
            }
        }

        throw new IllegalArgumentException("no action " + action + " on widget " + widget.getParentId() + "." + widget.getId());
    }

    public void interact(int action) {
        gameAPI().clientThread.invoke(() ->
                client().invokeMenuAction("", "",
                        action + 1,
                        MenuAction.CC_OP.getId(),
                        index(),
                        id()
                )
        );
    }

    public void select() {
        gameAPI().clientThread.invoke(() ->
                client().invokeMenuAction("", "",
                        0,
                        MenuAction.WIDGET_CONTINUE.getId(),
                        index(),
                        id()
                )
        );
    }

    public Widget child(int child) { //TODO untested
        if (widget.getDynamicChildren().length == 0) {
            return null;
        }

        var c = widget.getDynamicChildren()[child];

        if (c == null) {
            this.children.put(child, this);
        }

        return c;
    }

    @Override
    public void useOn(InventoryItem item) {
        gameAPI.interactionManager().submit(() -> gameAPI.clientThread.invoke(() -> {
            gameAPI.client.setSelectedSpellWidget(id());
            gameAPI.client.setSelectedSpellChildIndex(-1);
            gameAPI.client.invokeMenuAction("", "", item.id(),
                    MenuAction.WIDGET_USE_ON_ITEM.getId(), item.slot(), WidgetInfo.INVENTORY.getId());
        }));
    }

    @Override
    public void useOn(iNPC npc) {
        gameAPI.interactionManager().submit(() -> gameAPI.clientThread.invoke(() -> {
            gameAPI.client.setSelectedSpellWidget(id());
            gameAPI.client.setSelectedSpellChildIndex(-1);
            gameAPI.client.invokeMenuAction("", "", npc.index(),
                    MenuAction.WIDGET_TARGET_ON_NPC.getId(), 0, 0);
        }));
    }

    @Override
    public void useOn(iObject object) {
        gameAPI.interactionManager().submit(() -> gameAPI.clientThread.invoke(() -> {
            gameAPI.client.setSelectedSpellWidget(id());
            gameAPI.client.setSelectedSpellChildIndex(-1);
            gameAPI.client.invokeMenuAction("", "", object.id(),
                    MenuAction.WIDGET_TARGET_ON_GAME_OBJECT.getId(), object.menuPoint().getX(), object.menuPoint().getY());
        }));
    }
}
