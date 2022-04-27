package net.runelite.client.plugins.externals.farmrun.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.externals.farmrun.Location;
import net.runelite.client.plugins.externals.farmrun.Runes;
import net.runelite.client.plugins.externals.farmrun.FarmRunConfig;
import net.runelite.client.plugins.externals.farmrun.teleports.Glory;
import net.runelite.client.plugins.externals.farmrun.teleports.PoH;
import net.runelite.client.plugins.externals.farmrun.teleports.SkillsNecklace;
import net.runelite.client.plugins.externals.farmrun.teleports.XericsTalisman;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.runelite.client.plugins.externals.InventoryUtils.hasItems;

@Slf4j
public class TeleportTask {

    enum TeleportState
    {
        NONE,
        OPEN_BANK,
        OPENING_BANK,
        QUANTITY,
        SETTINGS,
        ITEM_OPTIONS,
        DEPOSIT_ALL,
        CLOSE_BANK,
        FETCH_POH,
        FETCH_TELEPORT,
        SELECT_MINIGAME,
        MINIGAME_TELEPORT,
        MINIGAME_TELEPORT_WAIT,
        TELEPORT_POH,
        TELEPORT_POH_WAIT,
        TELEPORT_MENU,
        TELEPORT_MENU_CLICK,
        TELEPORT_DIALOG,
        TELEPORT_WAIT,
        EQUIPMENT,
        TELEPORT_EQUIPMENT,
        TELEPORT_EQUIPMENT_CLICK,
        TELEPORTING,
        HANDLE_POH,
        CLICK_POH_TELEPORT,
        POH_WAIT_TELEPORT_MENU,
        POH_TELEPORT_MENU,
    }

    private static final int[] AMOUNT_VARBITS =
            {
                    Varbits.RUNE_POUCH_AMOUNT1, Varbits.RUNE_POUCH_AMOUNT2, Varbits.RUNE_POUCH_AMOUNT3
            };
    private static final int[] RUNE_VARBITS =
            {
                    Varbits.RUNE_POUCH_RUNE1, Varbits.RUNE_POUCH_RUNE2, Varbits.RUNE_POUCH_RUNE3
            };

    private final FarmRunConfig farmRunConfig;
    private final Client client;
    private final EventBus eventBus;
    private final ItemManager itemManager;
    private TeleportState teleportState;



    @Inject
    TeleportTask(FarmRunConfig farmRunConfig, FarmRunConfig farmRunConfig1, Client client, ItemManager itemManager),



    private void pohLogic()
    {
        Set<Integer> items = new HashSet<>();

        if (farmRunConfig.pohTeleport() == PoH.TELEPORT_TAB)
        {
            items.add(ItemID.TELEPORT_TO_HOUSE);
        }
        else if (farmRunConfig.pohTeleport() == PoH.RUNES)
        {
            for (Runes rune : getRunesForTeleport(Location.POH))
            {
                items.add(runeOrRunepouch(rune));
            }
        }
        if (client.getItemContainer(InventoryID.BANK) != null)
        {
            Collection<WidgetItem> bankInventoryItems = getBankInventoryItems(client);
            if (bankInventoryItems != null && bankInventoryItems.stream().noneMatch((item) -> item.getId() == 6512))
            {
                teleportState = TeleportState.DEPOSIT_ALL;
                disposables.add(chinManagerPlugin.getTaskExecutor().prepareTask(new ClickTask(chinManagerPlugin)).ignoreElements().subscribe());

                return;
            }

            for (int item : items)
            {
                if (!hasAnyBankInventoryItem(item, client))
                {
                    if (hasAnyBankItem(item, client))
                    {
                        teleportState = TeleportState.FETCH_POH;
                        disposables.add(chinManagerPlugin.getTaskExecutor().prepareTask(new ClickTask(chinManagerPlugin)).ignoreElements().subscribe());

                        return;
                    }
                }
            }

            teleportState = TeleportState.CLOSE_BANK;
            disposables.add(chinManagerPlugin.getTaskExecutor().prepareTask(new ClickTask(chinManagerPlugin)).ignoreElements().subscribe());
        }
        else if (hasItems(List.copyOf(items), client))
        {
            List<Integer> equipmentItems = getEquipment();

            if (farmRunConfig.pohTeleport() == PoH.RUNES && client.getVar(VarClientInt.INVENTORY_TAB) != 6)
            {
                client.runScript(MagicNumberScripts.ACTIVE_TAB.getId(), 6);
            }
            else if (farmRunConfig.pohTeleport() == PoH.CONSTRUCTION_CAPE && equipmentItems.stream().anyMatch(CONSTRUCT_CAPE::contains) && client.getVar(VarClientInt.INVENTORY_TAB) != 4)
            {
                client.runScript(MagicNumberScripts.ACTIVE_TAB.getId(), 4);
            }

            teleportState = TeleportState.TELEPORT_POH;
            disposables.add(chinManagerPlugin.getTaskExecutor().prepareTask(new ClickTask(chinManagerPlugin)).ignoreElements().subscribe());
        }
        else if (ChinManagerPlugin.isAtBank(client))
        {
            teleportState = TeleportState.OPEN_BANK;
        }
    }

    private boolean needSkillsNecklace(Location location)
    {
        switch (location)
        {
            case FISHING_GUILD:
            case MINING_GUILD:
            case CRAFTING_GUILD:
            case COOKS_GUILD:
            case WOODCUTTING_GUILD:
            case FARMING_GUILD:
                return farmRunConfig.skillsNecklaceTeleport() != SkillsNecklace.POH;

            default:
                return false;
        }
    }

    private boolean needAmuletOfGlory(Location location)
    {
        switch (location)
        {
            case EDGEVILLE:
            case KARAMJA:
            case DRAYNOR_VILLAGE:
            case AL_KHARID:
                return farmRunConfig.amuletOfGloryTeleport() != Glory.POH;

            default:
                return false;
        }
    }

    private boolean needXericsTalisman(Location location)
    {
        switch (location)
        {
            case XERICS_LOOKOUT:
            case XERICS_GLADE:
            case XERICS_INFERNO:
            case XERICS_HEART:
            case XERICS_HONOUR:
                return farmRunConfig.xericsTalismanTeleport() != XericsTalisman.POH;

            default:
                return false;
        }
    }
}
