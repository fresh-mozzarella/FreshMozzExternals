package net.runelite.client.plugins.externals.farmrun;

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
import net.runelite.client.plugins.externals.CalculationUtils;
import net.runelite.client.plugins.externals.FreshUtils;
import net.runelite.client.plugins.externals.MouseUtils;
import net.runelite.client.plugins.externals.farmrun.patchteleportmethods.FaladorPatch;
import net.runelite.client.plugins.externals.api.ContainerUtils;
import net.runelite.client.plugins.externals.api.LegacyInventoryAssistant;
import net.runelite.client.plugins.externals.ui.LegacyMenuEntry;
import net.runelite.client.plugins.externals.ui.MenuUtils;
import org.pf4j.Extension;

import javax.inject.Inject;

import static net.runelite.client.plugins.externals.farmrun.patchteleportmethods.FaladorPatch.EXPLORERS_RINGS;

@Extension
@PluginDescriptor(
        name = "Farm Run",
        description = "Automates the extremely boring task of doing farm runs.",
        tags = {"farm", "run", "krieger"}
)

@Slf4j
@PluginDependency(FreshUtils.class)
public class FarmRun extends Plugin
{
    @Inject
    private FarmRunConfig config;

    @Inject
    private Client client;

    @Inject
    private FreshUtils utils;

    @Inject
    private ContainerUtils containerUtils;

    @Inject
    private MouseUtils mouse;

    @Inject
    private CalculationUtils calc;

    @Inject
    private MenuUtils menu;

    private LegacyInventoryAssistant legacyInventoryAssistant;

    private LegacyMenuEntry entry;

    private long sleepDelay() {
        sleepLength = calc.randomDelay(config.sleepWeightedDistribution(), config.sleepMin(), config.sleepMax(), config.sleepDeviation(), config.sleepTarget());
        return sleepLength;
    }

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
                    if (((EXPLORERS_RINGS.stream().anyMatch(item-> containerUtils.hasItem(item, client))))
                            && (client.getLocalPlayer().getWorldLocation().getRegionID() != FaladorPatch.EXPLORERS_RING.getRegionID())) {
                            int item = ContainerUtils.getInventoryItemsMap(EXPLORERS_RINGS, client).keySet().stream().findFirst().orElse(-1);
                            int id = legacyInventoryAssistant.itemOptionToId(item, "Teleport");
                        WidgetItem explorersring = containerUtils.getWidgetItem(item);
                            entry = new LegacyMenuEntry("Teleport", "", id, MenuAction.CC_OP, 0, WidgetInfo.INVENTORY.getId(), false);
                            menu.setEntry(entry);
                            utils.doActionMsTime(entry, new Point(0,0), sleepDelay());
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


