package net.runelite.client.plugins.freshutils.scene;

import net.runelite.api.Client;
import net.runelite.client.plugins.freshutils.game.GameAPI;

public interface Locatable {
    /**
     * The {@link GameAPI} instance this object belongs to.
     */
    GameAPI game();

    Client client();

    /**
     * The position in the world. In an instance, this is the actual
     * world position, not the position in the chunk template.
     */
    Position position();

    /**
     * If not in an instance, this is equal to {@link Locatable#position()}. In an instance, this returns
     * the position in the chunk template.
     */
    default Position templatePosition() {
        var tile = game().tile(position());

        if (tile == null) {
            return position();
        }

        return tile.templatePosition();
    }
}
