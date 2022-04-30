package net.runelite.client.plugins.freshutils.walking;

import net.runelite.client.plugins.freshutils.game.GameAPI;
import net.runelite.client.plugins.freshutils.scene.Position;

import java.util.function.Consumer;

public class Transport {
    public final Position source;
    public final Position target;
    public final Consumer<GameAPI> handler;
    public final int targetRadius;
    public final int sourceRadius;

    public Transport(Position source, Position target, int sourceRadius, int targetRadius, Consumer<GameAPI> handler) {
        this.source = source;
        this.target = target;
        this.targetRadius = targetRadius;
        this.handler = handler;
        this.sourceRadius = sourceRadius;
    }
}
