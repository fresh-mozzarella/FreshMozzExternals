package net.runelite.client.plugins.externals.farmrun.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.externals.farmrun.ui.FarmRunConfig;

import javax.inject.Inject;

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

//    private final FarmRunConfig farmRunConfig;
//    private final Client client;
//    private final EventBus eventBus;
//    private final ItemManager itemManager;
//    private TeleportState teleportState;



//    @Inject
//    TeleportTask()
}
