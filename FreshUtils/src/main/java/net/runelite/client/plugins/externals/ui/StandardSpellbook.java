package net.runelite.client.plugins.externals.ui;

import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.externals.Spells;
import net.runelite.client.plugins.externals.api.GameAPI;


/**
 * @author kylestev
 */
public class StandardSpellbook {
    private final GameAPI gameAPI;

    public StandardSpellbook(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
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
        gameAPI.widget(spellInfo).interact("Cast");
    }

    public void lumbridgeHomeTeleport() {
        if (gameAPI.localPlayer().position().regionID() == 12850) {
            return;
        }

        castSpell(Spells.LUMBRIDGE_HOME_TELEPORT);
        gameAPI.waitUntil(() -> gameAPI.localPlayer().position().regionID() == 12850, 30);
        gameAPI.tick(3);
    }
}
