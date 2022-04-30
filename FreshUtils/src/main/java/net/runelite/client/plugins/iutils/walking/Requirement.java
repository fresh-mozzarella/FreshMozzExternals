package net.runelite.client.plugins.iutils.walking;

import net.runelite.client.plugins.iutils.game.GameAPI;

public interface Requirement {

    GameAPI game();

    boolean satisfies();
}
