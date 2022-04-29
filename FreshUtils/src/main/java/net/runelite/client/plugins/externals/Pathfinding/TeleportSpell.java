package net.runelite.client.plugins.externals.Pathfinding;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.plugins.externals.api.GameAPI;
import net.runelite.client.plugins.externals.api.ItemQuantity;
import net.runelite.client.plugins.externals.api.Position;
import net.runelite.client.plugins.externals.api.SpellBook;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public enum TeleportSpell {

    VARROCK_TELEPORT(SpellBook.Type.STANDARD, 25, new Position(3212, 3424, 0), false, "Varrock Teleport", new RuneRequirement(1, RuneElement.LAW), new RuneRequirement(3, RuneElement.AIR), new RuneRequirement(1, RuneElement.FIRE)),
    LUMBRIDGE_TELEPORT(SpellBook.Type.STANDARD, 31, new Position(3225, 3219, 0), false, "Lumbridge Teleport", new RuneRequirement(1, RuneElement.LAW), new RuneRequirement(3, RuneElement.AIR), new RuneRequirement(1, RuneElement.EARTH)),
    FALADOR_TELEPORT(SpellBook.Type.STANDARD, 37, new Position(2966, 3379, 0), false, "Falador Teleport", new RuneRequirement(1, RuneElement.LAW), new RuneRequirement(3, RuneElement.AIR), new RuneRequirement(1, RuneElement.WATER)),
    CAMELOT_TELEPORT(SpellBook.Type.STANDARD, 45, new Position(2757, 3479, 0), true, "Camelot Teleport", new RuneRequirement(1, RuneElement.LAW), new RuneRequirement(5, RuneElement.AIR)),
    ARDOUGNE_TELEPORT(SpellBook.Type.STANDARD, 51, new Position(2661, 3300, 0), true, "Ardougne Teleport", new RuneRequirement(2, RuneElement.LAW), new RuneRequirement(2, RuneElement.WATER)),
    ;

    private SpellBook.Type spellBookType;
    private int requiredLevel;
    private Position location;
    private boolean members;
    private String spellName;
    private RuneRequirement[] recipe;

    TeleportSpell(SpellBook.Type spellBookType, int level, Position location, boolean members, String spellName, RuneRequirement... recipe) {
        this.spellBookType = spellBookType;
        this.requiredLevel = level;
        this.location = location;
        this.members = members;
        this.spellName = spellName;
        this.recipe = recipe;
    }

    public RuneRequirement[] getRecipe() {
        return recipe;
    }

    public String getSpellName() {
        return spellName;
    }

    public Position getLocation() {
        return location;
    }

    public List<ItemQuantity> recipe(GameAPI gameAPI) {
        List<ItemQuantity> items = new ArrayList<>();

        for (RuneRequirement pair : recipe) {
            int amountRequiredForSpell = pair.getFirst();
            RuneElement runeElement = pair.getSecond();
            int amount = amountRequiredForSpell - runeElement.getCount(gameAPI);
            if (amount > 0) {
                items.add(new ItemQuantity(pair.getSecond().getRuneId(), amount));
            }
        }
        return items;
    }

    public boolean hasRequirements(GameAPI gameAPI) {
        if (SpellBook.getCurrentSpellBook(gameAPI) != spellBookType) {
            return false;
        }
        if (this.members && !gameAPI.membersWorld()) {
            return false;
        }
        if (requiredLevel > gameAPI.modifiedLevel(Skill.MAGIC)) {
            return false;
        }
        if (this == ARDOUGNE_TELEPORT && gameAPI.varp(165) < 30) { //TODO may cause issues
            return false;
        }

        return true;
    }

    public boolean canUse(GameAPI gameAPI) {
        if (!hasRequirements(gameAPI)) {
            return false;
        }

        for (RuneRequirement pair : recipe) {
            int amountRequiredForSpell = pair.getFirst();
            RuneElement runeElement = pair.getSecond();
            if (runeElement.getCount(gameAPI) < amountRequiredForSpell) {
                return false;
            }
        }
        return true;
    }

}