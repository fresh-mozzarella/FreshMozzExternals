package net.runelite.client.plugins.externals.farmrun;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;

@Extension
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

    @Provides
    FarmRunConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(FarmRunConfig.class);
    }

    @Override
    protected void startUp() {
        log.info("Plugin Started");

        if (config.isFaladorEnabled()) {
            log.info("The value of 'config.isFaladorEnabled()' is ${config.isFaladorEnabled()}");
        }
    }
    @Override
    protected void shutDown() {
        log.info("Plugin Stopped");
    }

    @Subscribe
    private void onGameTick(GameTick gameTick){
        log.info("GameTick");
    }
}


