package net.runelite.client.plugins.externals.Pathfinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RuneRequirement {
    public int quantity;
    public RuneElement type;

    public int getFirst() {
        return this.quantity;
    }

    public RuneElement getSecond() {
        return this.type;
    }
}
