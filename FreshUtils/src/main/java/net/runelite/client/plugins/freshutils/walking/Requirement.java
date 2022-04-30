package net.runelite.client.plugins.freshutils.walking;

import net.runelite.client.plugins.freshutils.game.GameAPI;

public interface Requirement {

    GameAPI game();

    boolean satisfies();
}
