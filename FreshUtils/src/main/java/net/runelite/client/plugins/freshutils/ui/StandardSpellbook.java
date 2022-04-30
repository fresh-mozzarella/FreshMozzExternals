package net.runelite.client.plugins.freshutils.ui;

import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.freshutils.api.Spells;
import net.runelite.client.plugins.freshutils.game.GameAPI;

/**
 * @author kylestev
 */
public class StandardSpellbook {
    private final GameAPI game;

    public StandardSpellbook(GameAPI game) {
        this.game = game;
    }

    public void castSpell(Spells spell) {
        System.out.println("At castSpell 1");
        System.out.println("Spell info: " + spell.getInfo());
        castSpell(spell.getInfo());
    }

    public void testing(Spells spell) {
        System.out.println("Test method ran");
    }

    public void castSpell(WidgetInfo spellInfo) {
        System.out.println("Casting spell: " + spellInfo.toString());
        game.widget(spellInfo).interact("Cast");
    }

    public void lumbridgeHomeTeleport() {
        // TODO: check response to update timer in Profile
        if (game.localPlayer().position().regionID() == 12850) {
            return;
        }

        castSpell(Spells.LUMBRIDGE_HOME_TELEPORT);
        game.waitUntil(() -> game.localPlayer().position().regionID() == 12850, 30);
        game.tick(3);
    }
}
