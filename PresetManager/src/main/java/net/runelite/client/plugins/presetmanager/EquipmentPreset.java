package net.runelite.client.plugins.presetmanager;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@EqualsAndHashCode
public class EquipmentPreset
{
    public int configIcon;
    public int configId;
    public String configName;
    public List<ItemConfig> playerEquipment;
    public List<ItemConfig> inventorySetup;
}
