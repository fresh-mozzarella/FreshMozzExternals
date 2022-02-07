package net.runelite.client.plugins.externals.presetmanager;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class PresetList {
    public List<EquipmentPreset> presetList;
}
