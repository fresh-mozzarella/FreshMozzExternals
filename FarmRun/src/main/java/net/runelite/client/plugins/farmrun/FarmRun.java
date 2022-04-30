package net.runelite.client.plugins.farmrun;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.Point;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.farmrun.patchteleportmethods.FaladorPatch;
import net.runelite.client.plugins.freshutils.CalculationUtils;
import net.runelite.client.plugins.freshutils.ContainerUtils;
import net.runelite.client.plugins.freshutils.FreshUtils;
import net.runelite.client.plugins.freshutils.InventoryUtils;
import net.runelite.client.plugins.freshutils.LegacyMenuEntry;
import org.pf4j.Extension;

import javax.inject.Inject;

@Extension
@PluginDependency(FreshUtils.class)
@PluginDescriptor(
        name = "Farm Run",
        description = "Automates the extremely boring task of doing farm runs.",
        tags = {"farm", "run", "krieger"}
)

@Slf4j
public class FarmRun extends Plugin
{
    @Inject
    private FarmRunConfig config;

    @Inject
    private Client client;

    @Inject
    private InventoryUtils inventory;

    @Inject
    private FreshUtils utils;

    @Inject
    private CalculationUtils calc;

    LegacyMenuEntry targetMenu;

    boolean startFarmRun;
    long sleepLength;

    @Provides
    FarmRunConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(FarmRunConfig.class);
    }

    @Override
    protected void startUp() {
        log.info("Plugin Started");
    }
    @Override
    protected void shutDown() {
        log.info("Plugin Stopped");
    }

    private long sleepDelay() {
        sleepLength = calc.randomDelay(config.sleepWeightedDistribution(), config.sleepMin(), config.sleepMax(), config.sleepDeviation(), config.sleepTarget());
        return calc.randomDelay(config.sleepWeightedDistribution(), config.sleepMin(), config.sleepMax(), config.sleepDeviation(), config.sleepTarget());
    }
    @Subscribe
    private void onConfigButtonPressed (ConfigButtonClicked configButtonClicked)
    {
        if (!configButtonClicked.getGroup().equalsIgnoreCase("FarmRun"))
        {
            return;
        }
        log.info("button {} pressed!", configButtonClicked.getKey());
        if (configButtonClicked.getKey().equals("startButton")) {
            if (!startFarmRun) {
                startFarmRun = true;
                if (config.isFaladorEnabled() == true) {
                    if (((FaladorPatch.EXPLORERS_RINGS.stream().anyMatch(item-> ContainerUtils.hasItem(item, client))))
                            && (client.getLocalPlayer().getWorldLocation().getRegionID() != FaladorPatch.EXPLORERS_RING.getRegionID())) {
                        int item = ContainerUtils.getInventoryItemsMap(FaladorPatch.EXPLORERS_RINGS, client).keySet().stream().findFirst().orElse(-1);
                        WidgetItem explorersring = inventory.getWidgetItem(FaladorPatch.EXPLORERS_RINGS);
                            targetMenu = new LegacyMenuEntry("Teleport", "", item, MenuAction.CC_OP, 0, WidgetInfo.INVENTORY.getId(), false);
                            utils.doActionMsTime(targetMenu, new Point(0,0), sleepDelay());
                        }
                    }







                    }
                }

            }



    @Subscribe
    private void onGameTick(GameTick gameTick){
        log.info("GameTick");
    }
}


