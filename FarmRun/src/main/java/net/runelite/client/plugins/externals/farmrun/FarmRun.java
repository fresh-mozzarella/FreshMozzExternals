package net.runelite.client.plugins.externals.farmrun;

import net.runelite.api.ItemID;
import net.runelite.client.plugins.PluginDependency;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.externals.farmrun.patchteleportmethods.FaladorPatch;
import net.runelite.client.plugins.externals.utils.InventoryUtils;
import org.pf4j.Extension;

import javax.inject.Inject;

import static net.runelite.client.plugins.externals.farmrun.patchteleportmethods.FaladorPatch.EXPLORERS_RINGS;

@Extension
@PluginDependency(InventoryUtils.class)
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




    boolean startFarmRun;

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
                    if (((EXPLORERS_RINGS.stream())) && (client.getLocalPlayer().getWorldLocation().getRegionID() != FaladorPatch.EXPLORERS_RING.getRegionID())) {

                    }
                }
//

            }
        }
    }





    @Subscribe
    private void onGameTick(GameTick gameTick){
        log.info("GameTick");
    }
}


