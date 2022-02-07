/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package net.runelite.client.plugins.externals.presetmanager;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;

@ConfigGroup("presetManager")
public interface PresetManagerConfig extends Config
{
	Gson gson = new Gson();

	@ConfigSection(
			keyName = "config",
			name = "Config",
			description = "",
			position = 0
	)
	String config = "Config";

	@ConfigItem(
			keyName = "randLow",
			name = "Minimum MS Delay",
			description = "Dont set this too high.",
			section = config,
			position = 3
	)
	default int randLow()
	{
		return 70;
	}

	@ConfigItem(
			keyName = "randLower",
			name = "Maximum MS Delay",
			description = "Dont set this too high.",
			section = config,
			position = 4
	)
	default int randHigh()
	{
		return 80;
	}

	@ConfigSection(
			position = 1,
			keyName = "mainConfig",
			name = "Main Config",
			description = ""
	)
	String mainConfig = "Main Config";

	@ConfigItem(
			keyName = "configBlob",
			name = "JSON blob of config",
			description = "",
			position = 55
	)
	default String configBlob()
	{
		return gson.toJson(
				PresetList.builder()
						.presetList(ImmutableList.of(
								EquipmentPreset.builder()
										.configId(1)
										.configName("Preset 1")
										.inventorySetup(ImmutableList.of())
										.playerEquipment(ImmutableList.of())
										.build(),
								EquipmentPreset.builder()
										.configId(2)
										.configName("Preset 2")
										.inventorySetup(ImmutableList.of())
										.playerEquipment(ImmutableList.of())
										.build(),
								EquipmentPreset.builder()
										.configId(3)
										.configName("Preset 3")
										.inventorySetup(ImmutableList.of())
										.playerEquipment(ImmutableList.of())
										.build(),
								EquipmentPreset.builder()
										.configId(4)
										.configName("Preset 4")
										.inventorySetup(ImmutableList.of())
										.playerEquipment(ImmutableList.of())
										.build(),
								EquipmentPreset.builder()
										.configId(5)
										.configName("Preset 5")
										.inventorySetup(ImmutableList.of())
										.playerEquipment(ImmutableList.of())
										.build()
						))
						.build()
		);
	}

	@ConfigItem(
			keyName = "configBlob",
			name = "JSON blob of config",
			description = "",
			hidden = true
	)
	void configBlob(String data);

	@ConfigItem(
			keyName = "name",
			name = "Preset name",
			description = "Enter a name for a preset that shows up as a menu option",
			position = 5
	)
	default String name()
	{
		return "name";
	}

	@ConfigItem(
			keyName = "presetNum",
			name = "Preset #",
			description = "Insert information for the preset (1-5) you want to update",
			position = 6
	)
	default int presetNum() {
		return 1;
	}

	@ConfigItem(
			keyName = "snapshot",
			name = "Snapshot Key",
			description = "Hotkey to add current equipment to the configuration blob",
			position = 22,
			section = mainConfig
	)
	default Keybind snapshot()
	{
		return Keybind.NOT_SET;
	}
}
