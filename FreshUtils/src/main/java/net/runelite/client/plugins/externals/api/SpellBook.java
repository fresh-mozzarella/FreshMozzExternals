package net.runelite.client.plugins.externals.api;



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

        public boolean isInUse(GameAPI gameAPI) {
            return gameAPI.varb(SPELLBOOK_VARBIT) == varbit;
        }
    }

    public static Type getCurrentSpellBook(GameAPI gameAPI) {
        return Arrays.stream(Type.values()).filter(t -> t.isInUse(gameAPI)).findAny().orElse(null);
    }

}