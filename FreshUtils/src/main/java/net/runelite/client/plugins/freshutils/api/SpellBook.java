package net.runelite.client.plugins.freshutils.api;

import net.runelite.client.plugins.freshutils.game.GameAPI;

import java.util.Arrays;

public class SpellBook {

    private static final int SPELLBOOK_VARBIT = 4070;

    public enum Type {
        STANDARD(0),
        ANCIENT(1),
        LUNAR(2),
        ARCEUUS(3);

        private int varbit;

        Type(int varbit) {
            this.varbit = varbit;
        }

        public boolean isInUse(GameAPI game) {
            return game.varb(SPELLBOOK_VARBIT) == varbit;
        }
    }

    public static Type getCurrentSpellBook(GameAPI game) {
        return Arrays.stream(Type.values()).filter(t -> t.isInUse(game)).findAny().orElse(null);
    }

}