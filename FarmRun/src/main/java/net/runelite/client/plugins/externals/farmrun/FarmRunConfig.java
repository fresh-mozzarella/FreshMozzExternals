package net.runelite.client.plugins.externals.farmrun;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.ConfigTitle;
import net.runelite.client.config.Range;
import net.runelite.client.plugins.externals.farmrun.seeds.AllotmentSeeds;
import net.runelite.client.plugins.externals.farmrun.seeds.HerbSeeds;
import net.runelite.client.plugins.externals.farmrun.teleportlocations.Falador;


@ConfigGroup("FarmRun")
public interface FarmRunConfig extends Config {
    @ConfigSection(
            keyName = "delayConfig",
            name = "Delay Configuration",
            description = "Sleep is reaction time (1000ms = 1 second). Tick delays are timeouts after certain actions. Set tick delays to 0 to for tick perfect performance (1 tick =~600ms)",
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
            position = 11
    )
    String faladorPatchSettings = "faladorPatchSettings";

    @ConfigTitle(
            keyName = "faladorGeneral",
            name = "General",
            description = "",
            position = 12,
            section = "faladorPatchSettings"
    )
    String faladorGeneral = "faladorGeneral";

    @ConfigItem(
            keyName = "faladorEnable",
            name = "Enable",
            description = "Enable the Falador Patch",
            position = 13,
            section = "faladorPatchSettings"
    )
    default boolean isFaladorEnabled() {
        return false;
    }

    @ConfigItem(
            keyName = "faladorMethod",
            name = "Method",
            description = "Falador Patch Teleport Method",
            position = 14,
            section = "faladorPatchSettings"
    )
    default Falador faladorTeleportMethod() { return Falador.RUNES; }

    @ConfigTitle(
            keyName = "faladorPatches",
            name = "Patches",
            description = "",
            position = 15,
            section = "faladorPatchSettings"
    )
    String faladorPatches = "faladorPatches";

    @ConfigTitle(
            keyName = "faladorHerb",
            name = "Herb",
            description = "",
            position = 16,
            section = "faladorPatchSettings"
    )
    String faladorHerb = "faladorHerb";

    @ConfigItem(
            keyName = "faladorHerbChoice",
            name = "Herb",
            description = "Which herb seed to plant.",
            position = 17,
            section = "faladorPatchSettings"
    )
    default HerbSeeds faladorHerbSeed() { return HerbSeeds.HARRALANDER_SEED; }

    @ConfigTitle(
            keyName = "faladorAllotment",
            name = "Allotment",
            description = "",
            position = 18,
            section = "faladorPatchSettings"
    )
    String faladorAllotment = "faladorAllotment";

    @ConfigItem(
            keyName = "faladorAllotmentChoice",
            name = "Allotment",
            description = "Which allotment seed to plant.",
            position = 19,
            section = "faladorPatchSettings"
    )
    default AllotmentSeeds faladorAllotmentSeed() { return AllotmentSeeds.WATERMELON_SEED; }

    @ConfigTitle(
            keyName = "faladorCompost",
            name = "Compost",
            description = "",
            position = 20,
            section = "faladorPatchSettings"
    )
    String faladorCompost = "faladorCompost";

    @ConfigItem(
            keyName = "faladorCompostEnable",
            name = "Compost",
            description = "",
            position = 21,
            section = "faladorPatchSettings"
    )
    default boolean isCompostEnabled() { return false; }

    @ConfigSection(
            keyName = "portPhasmatysPatchSettings",
            name = "Port Phasmatys",
            description = "",
            position = 15
    )
    String portPhasmatysPatchSettings = "portPhasmatysPatchSettings";

    @ConfigTitle(
            keyName = "portPhasmatysGeneral",
            name = "General",
            description = "",
            position = 16,
            section = "portPhasmatysPatchSettings"
    )
    String portPhasmatysGeneral = "portPhasmatysGeneral";

    @ConfigTitle(
            keyName = "portPhasmatysPatches",
            name = "Patches",
            description = "",
            position = 17,
            section = "portPhasmatysPatchSettings"
    )
    String portPhasmatysPatches = "portPhasmatysPatches";
}

