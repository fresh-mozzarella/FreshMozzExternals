package net.runelite.client.plugins.farmrun;

import net.runelite.client.config.*;
import net.runelite.client.plugins.farmrun.seeds.AllotmentSeeds;
import net.runelite.client.plugins.farmrun.seeds.HerbSeeds;
import net.runelite.client.plugins.farmrun.patchteleportmethods.ArdougnePatch;
import net.runelite.client.plugins.farmrun.patchteleportmethods.CatherbyPatch;
import net.runelite.client.plugins.farmrun.patchteleportmethods.FaladorPatch;
import net.runelite.client.plugins.farmrun.patchteleportmethods.PortPhasmatysPatch;


@ConfigGroup("FarmRun")
public interface FarmRunConfig extends Config {
    @ConfigSection(
            keyName = "delayConfig",
            name = "Delay Configuration",
            description = "Sleep is reaction time (1000ms = 1 second). Tick delays are timeouts after certain actions. Set tick delays to 0 to for tick perfect performance (1 tick =~600ms)",
            closedByDefault = true,
            position = 0
    )
    String delayConfig = "delayConfig";

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMin",
            name = "Sleep Min",
            description = "",
            position = 1,
            section = "delayConfig"
    )
    default int sleepMin() {
        return 60;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMax",
            name = "Sleep Max",
            description = "",
            position = 2,
            section = "delayConfig"
    )
    default int sleepMax() {
        return 350;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepTarget",
            name = "Sleep Target",
            description = "",
            position = 3,
            section = "delayConfig"
    )
    default int sleepTarget() {
        return 100;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepDeviation",
            name = "Sleep Deviation",
            description = "",
            position = 4,
            section = "delayConfig"
    )
    default int sleepDeviation() {
        return 10;
    }

    @ConfigItem(
            keyName = "sleepWeightedDistribution",
            name = "Sleep Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 5,
            section = "delayConfig"
    )
    default boolean sleepWeightedDistribution() {
        return false;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMin",
            name = "Game Tick Min",
            description = "",
            position = 6,
            section = "delayConfig"
    )
    default int tickDelayMin() {
        return 1;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMax",
            name = "Game Tick Max",
            description = "",
            position = 7,
            section = "delayConfig"
    )
    default int tickDelayMax() {
        return 3;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayTarget",
            name = "Game Tick Target",
            description = "",
            position = 8,
            section = "delayConfig"
    )
    default int tickDelayTarget() {
        return 2;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayDeviation",
            name = "Game Tick Deviation",
            description = "",
            position = 9,
            section = "delayConfig"
    )
    default int tickDelayDeviation() {
        return 1;
    }

    @ConfigItem(
            keyName = "tickDelayWeightedDistribution",
            name = "Game Tick Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 10,
            section = "delayConfig"
    )
    default boolean tickDelayWeightedDistribution() {
        return false;
    }

    @ConfigSection(
            keyName = "faladorPatchSettings",
            name = "Falador",
            description = "",
            position = 15
    )
    String faladorPatchSettings = "faladorPatchSettings";

    @ConfigItem(
            keyName = "faladorEnable",
            name = "Enable",
            description = "Enable the Falador patch",
            position = 16,
            section = "faladorPatchSettings"
    )
    default boolean isFaladorEnabled() {
        return false;
    }

    @ConfigItem(
            keyName = "faladorTeleportMethod",
            name = "Teleport",
            description = "Falador patch teleport method.",
            position = 17,
            section = "faladorPatchSettings"
    )
    default FaladorPatch faladorTeleportMethod() { return FaladorPatch.FALADOR_TELEPORT; }

    @ConfigItem(
            keyName = "faladorHerbChoice",
            name = "Herb",
            description = "Which herb seed to plant.",
            position = 18,
            section = "faladorPatchSettings"
    )
    default HerbSeeds faladorHerbSeed() { return HerbSeeds.HARRALANDER_SEED; }

    @ConfigItem(
            keyName = "faladorAllotmentChoice",
            name = "Allotment",
            description = "Which allotment seed to plant.",
            position = 19,
            section = "faladorPatchSettings"
    )
    default AllotmentSeeds faladorAllotmentSeed() { return AllotmentSeeds.WATERMELON_SEED; }

    @ConfigItem(
            keyName = "faladorCompostEnable",
            name = "Fill compost bin",
            description = "",
            position = 20,
            section = "faladorPatchSettings"
    )
    default boolean isFaladorCompostEnabled() { return false; }

    @ConfigSection(
            keyName = "portPhasmatysPatchSettings",
            name = "Port Phasmatys",
            description = "",
            position = 21
    )
    String portPhasmatysPatchSettings = "portPhasmatysPatchSettings";

    @ConfigItem(
            keyName = "portPhasmatysEnable",
            name = "Enable",
            description = "Enable the Port Phasmatys patch.",
            position = 22,
            section = "portPhasmatysPatchSettings"
    )
    default boolean isPortPhasmatysEnabled() {
        return false;
    }

    @ConfigItem(
            keyName = "portPhasmatysMethod",
            name = "Teleport",
            description = "Port Phasmatys patch teleport method.",
            position = 23,
            section = "portPhasmatysPatchSettings"
    )
    default PortPhasmatysPatch portPhasmatysTeleportMethod() { return PortPhasmatysPatch.ECTOPHIAL; }

    @ConfigItem(
            keyName = "portPhasmatysHerbChoice",
            name = "Herb",
            description = "Which herb seed to plant.",
            position = 24,
            section = "portPhasmatysPatchSettings"
    )
    default HerbSeeds portPhasmatysHerbSeed() { return HerbSeeds.HARRALANDER_SEED; }

    @ConfigItem(
            keyName = "portPhasmatysAllotmentChoice",
            name = "Allotment",
            description = "Which allotment seed to plant.",
            position = 25,
            section = "portPhasmatysPatchSettings"
    )
    default AllotmentSeeds portPhasmatysAllotmentSeed() { return AllotmentSeeds.WATERMELON_SEED; }

    @ConfigItem(
            keyName = "portPhasmatysCompostEnable",
            name = "Fill compost bin",
            description = "",
            position = 26,
            section = "portPhasmatysPatchSettings"
    )
    default boolean isPortPhasmatysCompostEnabled() { return false; }

    @ConfigSection(
            keyName = "catherbyPatchSettings",
            name = "Catherby",
            description = "",
            position = 27
    )
    String catherbyPatchSettings = "catherbyPatchSettings";

    @ConfigItem(
            keyName = "catherbyPatchEnable",
            name = "Enable",
            description = "Enable the Catherby patch.",
            position = 28,
            section = "catherbyPatchSettings"
    )
    default boolean isCatherbyEnabled() {
        return false;
    }

    @ConfigItem(
            keyName = "catherbyPatchMethod",
            name = "Teleport",
            description = "Catherby patch teleport method.",
            position = 29,
            section = "catherbyPatchSettings"
    )
    default CatherbyPatch catherbyTeleportMethod() { return CatherbyPatch.CAMELOT_TELEPORT; }

    @ConfigItem(
            keyName = "catherbyHerbChoice",
            name = "Herb",
            description = "Which herb seed to plant.",
            position = 30,
            section = "catherbyPatchSettings"
    )
    default HerbSeeds catherbyHerbSeed() { return HerbSeeds.HARRALANDER_SEED; }

    @ConfigItem(
            keyName = "catherbyAllotmentChoice",
            name = "Allotment",
            description = "Which allotment seed to plant.",
            position = 31,
            section = "catherbyPatchSettings"
    )
    default AllotmentSeeds catherbyAllotmentSeed() { return AllotmentSeeds.WATERMELON_SEED; }

    @ConfigItem(
            keyName = "catherbyCompostEnable",
            name = "Fill compost bin",
            description = "",
            position = 32,
            section = "catherbyPatchSettings"
    )
    default boolean isCatherbyCompostEnabled() { return false; }

    @ConfigSection(
            keyName = "ardougnePatchSettings",
            name = "Ardougne",
            description = "",
            position = 33
    )
    String ardougnePatchSettings = "ardougnePatchSettings";

    @ConfigItem(
            keyName = "ardougnePatchEnable",
            name = "Enable",
            description = "Enable the Ardougne patch.",
            position = 28,
            section = "ardougnePatchSettings"
    )
    default boolean isArdougneEnabled() {
        return false;
    }

    @ConfigItem(
            keyName = "ardougnePatchMethod",
            name = "Teleport",
            description = "Ardougne patch teleport method.",
            position = 29,
            section = "ardougnePatchSettings"
    )
    default ArdougnePatch ardougneTeleportMethod() { return ArdougnePatch.ARDOUGNE_TELEPORT; }

    @ConfigItem(
            keyName = "ardougneHerbChoice",
            name = "Herb",
            description = "Which herb seed to plant.",
            position = 30,
            section = "ardougnePatchSettings"
    )
    default HerbSeeds ardougneHerbSeed() { return HerbSeeds.HARRALANDER_SEED; }

    @ConfigItem(
            keyName = "ardougneAllotmentChoice",
            name = "Allotment",
            description = "Which allotment seed to plant.",
            position = 31,
            section = "ardougnePatchSettings"
    )
    default AllotmentSeeds ardougneAllotmentSeed() { return AllotmentSeeds.WATERMELON_SEED; }

    @ConfigItem(
            keyName = "ardougneCompostEnable",
            name = "Fill compost bin",
            description = "",
            position = 32,
            section = "ardougnePatchSettings"
    )
    default boolean isArdougneCompostEnabled() { return false; }

    @ConfigItem(
            keyName = "startButton",
            name = "Start/Stop",
            description = "Start the plugin.",
            position = 300
    )
    default Button startButton() { return new Button(); }
}



