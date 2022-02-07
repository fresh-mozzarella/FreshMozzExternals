package net.runelite.client.plugins.externals.presetmanager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import net.runelite.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.runelite.api.ItemID.*;

public class PresetConverter {
    private static final int INVENTORY_SIZE = 28;
    private static final Gson gson = new Gson();

    private static final ImmutableMap<Integer, Integer> WORN_ITEMS = ImmutableMap.<Integer, Integer>builder().
            put(BOOTS_OF_LIGHTNESS_89, BOOTS_OF_LIGHTNESS).
            put(PENANCE_GLOVES_10554, PENANCE_GLOVES).

            put(GRACEFUL_HOOD_11851, GRACEFUL_HOOD).
            put(GRACEFUL_CAPE_11853, GRACEFUL_CAPE).
            put(GRACEFUL_TOP_11855, GRACEFUL_TOP).
            put(GRACEFUL_LEGS_11857, GRACEFUL_LEGS).
            put(GRACEFUL_GLOVES_11859, GRACEFUL_GLOVES).
            put(GRACEFUL_BOOTS_11861, GRACEFUL_BOOTS).
            put(GRACEFUL_HOOD_13580, GRACEFUL_HOOD_13579).
            put(GRACEFUL_CAPE_13582, GRACEFUL_CAPE_13581).
            put(GRACEFUL_TOP_13584, GRACEFUL_TOP_13583).
            put(GRACEFUL_LEGS_13586, GRACEFUL_LEGS_13585).
            put(GRACEFUL_GLOVES_13588, GRACEFUL_GLOVES_13587).
            put(GRACEFUL_BOOTS_13590, GRACEFUL_BOOTS_13589).
            put(GRACEFUL_HOOD_13592, GRACEFUL_HOOD_13591).
            put(GRACEFUL_CAPE_13594, GRACEFUL_CAPE_13593).
            put(GRACEFUL_TOP_13596, GRACEFUL_TOP_13595).
            put(GRACEFUL_LEGS_13598, GRACEFUL_LEGS_13597).
            put(GRACEFUL_GLOVES_13600, GRACEFUL_GLOVES_13599).
            put(GRACEFUL_BOOTS_13602, GRACEFUL_BOOTS_13601).
            put(GRACEFUL_HOOD_13604, GRACEFUL_HOOD_13603).
            put(GRACEFUL_CAPE_13606, GRACEFUL_CAPE_13605).
            put(GRACEFUL_TOP_13608, GRACEFUL_TOP_13607).
            put(GRACEFUL_LEGS_13610, GRACEFUL_LEGS_13609).
            put(GRACEFUL_GLOVES_13612, GRACEFUL_GLOVES_13611).
            put(GRACEFUL_BOOTS_13614, GRACEFUL_BOOTS_13613).
            put(GRACEFUL_HOOD_13616, GRACEFUL_HOOD_13615).
            put(GRACEFUL_CAPE_13618, GRACEFUL_CAPE_13617).
            put(GRACEFUL_TOP_13620, GRACEFUL_TOP_13619).
            put(GRACEFUL_LEGS_13622, GRACEFUL_LEGS_13621).
            put(GRACEFUL_GLOVES_13624, GRACEFUL_GLOVES_13623).
            put(GRACEFUL_BOOTS_13626, GRACEFUL_BOOTS_13625).
            put(GRACEFUL_HOOD_13628, GRACEFUL_HOOD_13627).
            put(GRACEFUL_CAPE_13630, GRACEFUL_CAPE_13629).
            put(GRACEFUL_TOP_13632, GRACEFUL_TOP_13631).
            put(GRACEFUL_LEGS_13634, GRACEFUL_LEGS_13633).
            put(GRACEFUL_GLOVES_13636, GRACEFUL_GLOVES_13635).
            put(GRACEFUL_BOOTS_13638, GRACEFUL_BOOTS_13637).
            put(GRACEFUL_HOOD_13668, GRACEFUL_HOOD_13667).
            put(GRACEFUL_CAPE_13670, GRACEFUL_CAPE_13669).
            put(GRACEFUL_TOP_13672, GRACEFUL_TOP_13671).
            put(GRACEFUL_LEGS_13674, GRACEFUL_LEGS_13673).
            put(GRACEFUL_GLOVES_13676, GRACEFUL_GLOVES_13675).
            put(GRACEFUL_BOOTS_13678, GRACEFUL_BOOTS_13677).
            put(GRACEFUL_HOOD_21063, GRACEFUL_HOOD_21061).
            put(GRACEFUL_CAPE_21066, GRACEFUL_CAPE_21064).
            put(GRACEFUL_TOP_21069, GRACEFUL_TOP_21067).
            put(GRACEFUL_LEGS_21072, GRACEFUL_LEGS_21070).
            put(GRACEFUL_GLOVES_21075, GRACEFUL_GLOVES_21073).
            put(GRACEFUL_BOOTS_21078, GRACEFUL_BOOTS_21076).
            put(GRACEFUL_HOOD_24745, GRACEFUL_HOOD_24743).
            put(GRACEFUL_CAPE_24748, GRACEFUL_CAPE_24746).
            put(GRACEFUL_TOP_24751, GRACEFUL_TOP_24749).
            put(GRACEFUL_LEGS_24754, GRACEFUL_LEGS_24752).
            put(GRACEFUL_GLOVES_24757, GRACEFUL_GLOVES_24755).
            put(GRACEFUL_BOOTS_24760, GRACEFUL_BOOTS_24758).
            put(GRACEFUL_HOOD_25071, GRACEFUL_HOOD_25069).
            put(GRACEFUL_CAPE_25074, GRACEFUL_CAPE_25072).
            put(GRACEFUL_TOP_25077, GRACEFUL_TOP_25075).
            put(GRACEFUL_LEGS_25080, GRACEFUL_LEGS_25078).
            put(GRACEFUL_GLOVES_25083, GRACEFUL_GLOVES_25081).
            put(GRACEFUL_BOOTS_25086, GRACEFUL_BOOTS_25084).

            put(MAX_CAPE_13342, MAX_CAPE).

            put(SPOTTED_CAPE_10073, SPOTTED_CAPE).
            put(SPOTTIER_CAPE_10074, SPOTTIER_CAPE).

            put(AGILITY_CAPET_13341, AGILITY_CAPET).
            put(AGILITY_CAPE_13340, AGILITY_CAPE).
            build();

    public static final BiFunction<PresetList, Integer, String> GET_NAME_FROM_ID =
            (list, id) -> list.getPresetList()
                                .stream()
                                .filter(preset -> preset.configId == id)
                                .findFirst()
                                .get()
                                .configName;

    private static final Function<Item, ItemConfig> ITEM_TO_CONFIG =
            (item) -> ItemConfig.builder()
                    .itemCount(item.getQuantity())
                    .itemId(WORN_ITEMS.getOrDefault(item.getId(), item.getId()))
                    .itemCount(item.getQuantity())
                    .build();

    public static Function<EquipmentPreset, List<Integer>> PRESET_TO_ITEM_IDS = (preset) -> {
        ArrayList<Integer> itemIds = new ArrayList<>();
        preset.inventorySetup.forEach(item -> {
            if (item.itemId > 0) {
                itemIds.add(item.itemId);
            }
        });
        preset.playerEquipment.forEach(equipment -> {
            if (equipment.itemId > 0) {
                itemIds.add(equipment.itemId);
            }
        });
        return itemIds;
    };

    public static Function<List<ItemConfig>, List<Integer>> CONFIGS_TO_ITEM_IDS = (preset) -> {
        ArrayList<Integer> itemIds = new ArrayList<>();
        preset.forEach(item -> {
            if (item.itemId > 0) {
                itemIds.add(item.itemId);
            }
        });
        return itemIds;
    };

    static Optional<EquipmentPreset> getEquipmentFromId(int id, PresetList presetList) {
        return presetList.getPresetList().stream().filter(preset -> preset.configId == id).findFirst();
    }

    static PresetList getPresetFromConfig(PresetManagerConfig config) {
        PresetList presets = gson.fromJson(config.configBlob(), PresetList.class);

        if (presets == null) {
            presets = new PresetList(ImmutableList.of());
        } else if (presets.presetList == null) {
            presets = new PresetList(ImmutableList.of());
        }

        return presets;
    }

    static String updateConfigWithSnapshot(PresetList presets, Client client, int configId, String name) {
        ImmutableList.Builder<EquipmentPreset> newPresets = ImmutableList.builder();
        newPresets.addAll(presets.getPresetList().stream().filter(p -> p.configId != configId).collect(Collectors.toList()));
        newPresets.add(newSnapshot(client, configId, name));
        return gson.toJson(new PresetList(newPresets.build()));
    }

    static EquipmentPreset newSnapshot(Client client, int configId, String name) {
        return EquipmentPreset.builder()
                .configId(configId)
                .configIcon(ItemID.CAKE_OF_GUIDANCE)
                .configName(name)
                .playerEquipment(getItems(InventoryID.EQUIPMENT, client))
                .inventorySetup(getItems(InventoryID.INVENTORY, client))
                .build();
    }

    static void addInventoryItems(ImmutableList.Builder<ItemConfig> itemBuilder, Item[] items) {
        for (int slot = 0; slot < INVENTORY_SIZE; slot++) {
            if (slot < items.length) {
                if (items[slot].getId() > 0) {
                    itemBuilder.add(ITEM_TO_CONFIG.apply(items[slot]));
                }
            }
        }
    }

    static void addEquipmentItems(ImmutableList.Builder<ItemConfig> itemBuilder, Item[] items) {
        for (EquipmentInventorySlot slot : EquipmentInventorySlot.values()) {
            if (slot.getSlotIdx() < items.length && slot.getSlotIdx() >= 0) {
                if (items[slot.getSlotIdx()].getId() > 0) {
                    itemBuilder.add(ITEM_TO_CONFIG.apply(items[slot.getSlotIdx()]));
                }
            }
        }
    }

    static List<ItemConfig> getItems(InventoryID inventoryID, Client client) {
        ImmutableList.Builder<ItemConfig> itemBuilder = ImmutableList.builder();
        ItemContainer container = client.getItemContainer(inventoryID);

        if (container == null) {
            return itemBuilder.build();
        }

        Item[] items = container.getItems();

        if (inventoryID == InventoryID.INVENTORY) {
            addInventoryItems(itemBuilder, items);
        } else if (inventoryID == InventoryID.EQUIPMENT) {
            addEquipmentItems(itemBuilder, items);
        }

        return itemBuilder.build();
    }
}
