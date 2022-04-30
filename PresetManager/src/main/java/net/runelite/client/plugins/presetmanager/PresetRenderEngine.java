package net.runelite.client.plugins.presetmanager;

import com.google.common.collect.ImmutableMap;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.SpriteID;
import net.runelite.api.widgets.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PresetRenderEngine {
    static final int ITEMS_PER_ROW = 8;
    private static final int ITEM_VERTICAL_SPACING = 36;
    static final int ITEM_HORIZONTAL_SPACING = 48;
    static final int ITEM_ROW_START = 51;
    static final int LINE_HEIGHT = 2;
    static final int TEXT_HEIGHT = 15;
    static final int LINE_VERTICAL_SPACING = 5;

    private static final int ITEM_HEIGHT = 32;
    private static final int ITEM_WIDTH = 36;

    public static ArrayList<Widget> handleRender(Widget itemContainer,
                                    List<Widget> addedWidgets,
                                    PresetManager swapper,
                                    EquipmentPreset preset,
                                    Client client)
    {
        ArrayList<Widget> presetWidgets = new ArrayList<>();

        if (!addedWidgets.isEmpty())
        {
            for (Widget addedWidget : addedWidgets)
            {
                addedWidget.setHidden(true);
            }
            addedWidgets.clear();
        }

        hideOtherContainerItems(itemContainer);

        int itemsPlaced = 0;

        addedWidgets.add(swapper.createPresetInteractionWidget(itemContainer, preset, itemsPlaced));
        itemsPlaced++;

        List<Integer> itemIds = PresetConverter.PRESET_TO_ITEM_IDS.apply(preset).stream().distinct().collect(Collectors.toList());
        itemIds = itemIds.stream().filter(item -> item >= 0).collect(Collectors.toList());

        client.getLogger().info("Rendering: "+ itemIds.toString());

        for (Widget itemWidget : itemContainer.getDynamicChildren()) {
            if (itemIds.contains(itemWidget.getItemId())) {
                renderPreset(itemWidget, itemsPlaced);
                itemWidget.setHidden(false);
                itemWidget.revalidate();

                presetWidgets.add(itemWidget);

                itemsPlaced++;
            }
        }

        return presetWidgets;
    }

    static void renderPreset(Widget widget, int placementIndex)
    {
        int rowOffSet = placementIndex / ITEMS_PER_ROW;
        int columnOffset = placementIndex % ITEMS_PER_ROW;

        widget.setOriginalWidth(ITEM_WIDTH);
        widget.setOriginalHeight(ITEM_HEIGHT);
        widget.setOriginalX(columnOffset * ITEM_HORIZONTAL_SPACING + ITEM_ROW_START);
        widget.setOriginalY(rowOffSet * ITEM_VERTICAL_SPACING);
        widget.revalidate();
    }

    static void hideOtherContainerItems(Widget widget)
    {
        for (Widget itemWidget : widget.getDynamicChildren())
        {
            if (itemWidget.getSpriteId() == SpriteID.RESIZEABLE_MODE_SIDE_PANEL_BACKGROUND
                    || itemWidget.getText().contains("Tab"))
            {
                itemWidget.setHidden(true);
            } else if (!itemWidget.isHidden() &&
                    itemWidget.getItemId() != -1 &&
                    itemWidget.getItemId() != 6512)
            {
                itemWidget.setHidden(true);
            }
        }
    }

    public static Map<Integer, Integer> presetIcons()
    {
        ImmutableMap.Builder<Integer, Integer> iconListBuilder = ImmutableMap.builder();

        iconListBuilder.put(4, ItemID.GRACEFUL_TOP);
        iconListBuilder.put(5, ItemID.ROBIN_HOOD_HAT);
        iconListBuilder.put(6, ItemID.ABYSSAL_BLUDGEON);
        iconListBuilder.put(7, ItemID.FIRE_CAPE);
        iconListBuilder.put(8, ItemID.ANCIENT_STAFF);
        iconListBuilder.put(9, ItemID.TOXIC_BLOWPIPE_EMPTY);
        iconListBuilder.put(10, ItemID.DRAGONFRUIT_SAPLING);
        iconListBuilder.put(11, ItemID.WATERING_CAN);
        iconListBuilder.put(12, ItemID.SLAYER_HELMET);
        iconListBuilder.put(13, ItemID.ANCESTRAL_HAT);
        iconListBuilder.put(14, ItemID.SNAPDRAGON);
        iconListBuilder.put(15, ItemID.INFERNAL_CAPE);
        iconListBuilder.put(16, ItemID.ONYX);
        iconListBuilder.put(17, ItemID.ZENYTE);
        iconListBuilder.put(18, ItemID.MAGIC_FANG);
        iconListBuilder.put(19, ItemID.DRAGON_AXE);
        iconListBuilder.put(20, ItemID.CLUE_SCROLL);
        iconListBuilder.put(21, ItemID.CAKE_OF_GUIDANCE);
        iconListBuilder.put(22, ItemID.ABYSSAL_WHIP);
        iconListBuilder.put(23, ItemID.SPADE);


        return iconListBuilder.build();
    }
}
