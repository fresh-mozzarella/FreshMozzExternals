package net.runelite.client.plugins.presetmanager;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PresetList {
    public List<EquipmentPreset> presetList;
}
