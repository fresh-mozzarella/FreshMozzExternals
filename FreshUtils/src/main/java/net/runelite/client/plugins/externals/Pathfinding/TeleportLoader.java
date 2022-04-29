package net.runelite.client.plugins.externals.Pathfinding;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.externals.Spells;
import net.runelite.client.plugins.externals.TeleportTab;
import net.runelite.client.plugins.externals.api.GameAPI;
import net.runelite.client.plugins.externals.api.InventoryItem;
import net.runelite.client.plugins.externals.api.Position;
import net.runelite.client.plugins.externals.ui.Bank;
import net.runelite.client.plugins.externals.ui.Chatbox;
import net.runelite.client.plugins.externals.ui.StandardSpellbook;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TeleportLoader {
    public static final int[] RING_OF_DUELING = {2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};
    public static final int[] GAMES_NECKLACE = {3853, 3863, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
    public static final int[] COMBAT_BRACELET = {11118, 11972, 11974, 11120, 11122, 11124};
    public static final int[] RING_OF_WEALTH = {11980, 11982, 11984, 11986, 11988};
    public static final int[] AMULET_OF_GLORY = {1712, 1706, 1708, 1710, 11976, 11978};
    public static final int[] NECKLACE_OF_PASSAGE = {21146, 21149, 21151, 21153, 21155};
    public static final int[] BURNING_AMULET = {21166, 21171, 21173, 21175, 21167, 21169};
    public static final int XERICS_TALISMAN = 13393;
    public static final int[] SLAYER_RING = {11866, 11867, 11868, 11869, 11870, 11871, 11872, 11873, 21268};
    public static final int[] DIGSITE_PENDANT = {11190, 11191, 11192, 11193, 11194};
    public static final int DRAKANS_MEDALLION = 22400;
    public static final int[] SKILLS_NECKLACE = {11105, 11111, 11109, 11107, 11970, 11968};
    private final GameAPI gameAPI;
    private final Bank bank;
    private final Chatbox chatbox;
    protected StandardSpellbook standardSpellbook;

    public TeleportLoader(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        this.bank = new Bank(gameAPI);
        this.chatbox = new Chatbox(gameAPI);
        this.standardSpellbook = new StandardSpellbook(gameAPI);
    }

    public List<Teleport> buildTeleports() {
        var teleports = new ArrayList<Teleport>();
        var playerPosition = gameAPI.localPlayer().position();

        if (gameAPI.membersWorld()) {
            if (ringOfDueling() != null) {
                teleports.add(new Teleport(new Position(3315, 3235, 0), 6, () -> jewelryAction(ringOfDueling(), "Duel Arena")));
                teleports.add(new Teleport(new Position(2440, 3090, 0), 5, () -> jewelryAction(ringOfDueling(), "Castle Wars")));
                teleports.add(new Teleport(new Position(3151, 3635, 0), 5, () -> jewelryAction(ringOfDueling(), "Ferox Enclave")));
            }

            if (gameAPIsNecklace() != null) {
                teleports.add(new Teleport(new Position(2898, 3553, 0), 5, () -> jewelryAction(gameAPIsNecklace(), "Burthorpe")));
                teleports.add(new Teleport(new Position(2520, 3571, 0), 6, () -> jewelryAction(gameAPIsNecklace(), "Barbarian Outpost")));
                teleports.add(new Teleport(new Position(2964, 4382, 2), 5, () -> jewelryAction(gameAPIsNecklace(), "Corporeal Beast")));
                teleports.add(new Teleport(new Position(3244, 9501, 2), 5, () -> jewelryAction(gameAPIsNecklace(), "Tears of Guthix")));
                teleports.add(new Teleport(new Position(1624, 3938, 0), 5, () -> jewelryAction(gameAPIsNecklace(), "Wintertodt Camp")));
            }

            if (combatBracelet() != null) {
                teleports.add(new Teleport(new Position(2882, 3548, 0), 5, () -> jewelryAction(combatBracelet(), "Warriors' Guild")));
                teleports.add(new Teleport(new Position(3191, 3367, 0), 5, () -> jewelryAction(combatBracelet(), "Champions' Guild")));
                teleports.add(new Teleport(new Position(3052, 3488, 0), 5, () -> jewelryAction(combatBracelet(), "Monastery")));
                teleports.add(new Teleport(new Position(2655, 3441, 0), 5, () -> jewelryAction(combatBracelet(), "Ranging Guild")));
            }

            if (skillsNecklace() != null) {
                teleports.add(new Teleport(new Position(2611, 3390, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Fishing Guild")));
                teleports.add(new Teleport(new Position(3050, 9763, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Mining Guild")));
                teleports.add(new Teleport(new Position(2933, 3295, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Crafting Guild")));
                teleports.add(new Teleport(new Position(3143, 3440, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Cooking Guild")));
                teleports.add(new Teleport(new Position(1662, 3505, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Woodcutting Guild")));
                teleports.add(new Teleport(new Position(1249, 3718, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Farming Guild")));
            }

            if (xericsTalisman() != null) {
                teleports.add(new Teleport(new Position(1576, 3530, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Xeric's Lookout")));
                teleports.add(new Teleport(new Position(1752, 3566, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Xeric's Glade")));
                teleports.add(new Teleport(new Position(1504, 3817, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Xeric's Inferno")));
                teleports.add(new Teleport(new Position(1640, 3674, 0), 6, () -> jeweleryContainerAction(skillsNecklace(), "Xeric's Heart")));
//                teleports.add(new Teleport(new Position(1662, 3505, 0), 6, () -> jewelryContainerAction(skillsNecklace(), "Xeric's Honour")));
            }

            if (ringOfWealth() != null) {
                teleports.add(new Teleport(new Position(3163, 3478, 0), 5, () -> jewelryAction(ringOfWealth(), "Grand Exchange")));
                teleports.add(new Teleport(new Position(2996, 3375, 0), 5, () -> jewelryAction(ringOfWealth(), "Falador")));
//            teleports.add(new Teleport(new Position, 2, () -> jewelryAction(ringOfWealth(), "Miscellania")));
                teleports.add(new Teleport(new Position(2829, 10167, 0), 5, () -> jewelryAction(ringOfWealth(), "Dondakan")));
            }

            if (amuletOfGlory() != null) {
                teleports.add(new Teleport(new Position(3087, 3496, 0), 5, () -> jewelryAction(amuletOfGlory(), "Edgeville")));
                teleports.add(new Teleport(new Position(2918, 3176, 0), 5, () -> jewelryAction(amuletOfGlory(), "Karamja")));
                teleports.add(new Teleport(new Position(3105, 3251, 0), 5, () -> jewelryAction(amuletOfGlory(), "Draynor Village")));
                teleports.add(new Teleport(new Position(3293, 3163, 0), 5, () -> jewelryAction(amuletOfGlory(), "Al Kharid")));
            }

            if (necklaceOfPassage() != null) {
                teleports.add(new Teleport(new Position(3114, 3179, 0), 5, () -> jewelryAction(necklaceOfPassage(), "Wizards' Tower")));
                teleports.add(new Teleport(new Position(2430, 3348, 0), 5, () -> jewelryAction(necklaceOfPassage(), "The Outpost")));
                teleports.add(new Teleport(new Position(3405, 3157, 0), 5, () -> jewelryAction(necklaceOfPassage(), "Eagles' Eyrie")));
            }

            if (burningAmulet() != null) {
                teleports.add(new Teleport(new Position(3235, 3636, 0), 5, () -> jewelryAction(burningAmulet(), "Chaos Temple", "Okay")));
                teleports.add(new Teleport(new Position(3038, 3651, 0), 5, () -> jewelryAction(burningAmulet(), "Bandit Camp", "Okay")));
                teleports.add(new Teleport(new Position(3028, 3842, 0), 5, () -> jewelryAction(burningAmulet(), "Lava Maze", "Okay")));
            }

            if (slayerRing() != null) {
                teleports.add(new Teleport(new Position(2432, 3423, 0), 5, () -> jewelryAction(slayerRing(), "Stronghold Slayer Cave")));
                teleports.add(new Teleport(new Position(3422, 3537, 0), 5, () -> jewelryAction(slayerRing(), "Slayer Tower")));
                teleports.add(new Teleport(new Position(2802, 10000, 0), 5, () -> jewelryAction(slayerRing(), "Fremennik Slayer Dungeon")));
                teleports.add(new Teleport(new Position(3185, 4601, 0), 5, () -> jewelryAction(slayerRing(), "Tarn's Lair")));
                teleports.add(new Teleport(new Position(2028, 4636, 0), 5, () -> jewelryAction(slayerRing(), "Dark Beasts")));
            }

            if (digsitePendant() != null) {
                teleports.add(new Teleport(new Position(3341, 3445, 0), 5, () -> jewelryAction(digsitePendant(), "Digsite")));
                teleports.add(new Teleport(new Position(3764,3869,1), 5, () -> jewelryAction(digsitePendant(), "Fossil Island")));
                teleports.add(new Teleport(new Position(3549, 10456, 0), 5, () -> jewelryAction(digsitePendant(), "Lithkren")));
            }


            if (gameAPI.getWildernessLevelFrom(gameAPI.client().getLocalPlayer().getWorldLocation()) <= 20) {
                for (TeleportTab teleportTab : TeleportTab.values()) {
                    if (teleportTab.canUse(gameAPI) && teleportTab.getLocation().distanceTo(playerPosition) > 20) {
                        teleports.add(new Teleport(teleportTab.getLocation(), 5, () -> inventoryAction(teleportTab.getTabletName(), "Break")));
                    }
                }
            }
        }

        for (TeleportSpell teleportSpell : TeleportSpell.values()) {
            if (!teleportSpell.canUse(gameAPI)) continue;
            if (teleportSpell.getLocation().distanceTo(playerPosition) > 50) {
                teleports.add(new Teleport(teleportSpell.getLocation(), 5, () -> cast(teleportSpell.getSpellName())));
            }
        }

        return teleports;
    }

    //jewelry
    private void jewelryAction(InventoryItem item, String... target) {
        while (chatbox.chatState() == Chatbox.ChatState.CLOSED) {
            item.interact("Rub");
            gameAPI.waitUntil(() -> chatbox.chatState() != Chatbox.ChatState.CLOSED, 10);
        }
        if (chatbox.chatState() != Chatbox.ChatState.CLOSED) {
            chatbox.chat(target);
        }
    }

    private void jeweleryContainerAction(InventoryItem item, String target) {
        item.interact("Rub");
        gameAPI.tick();

        if (gameAPI.screenContainer().nestedInterface() == 187) {
            for (int i = 0; i < gameAPI.widget(187, 3).items().size(); i++) {
                String option = gameAPI.widget(187, 3, i).text();
                if (option != null) {
                    if (option.split(": ")[1].contains(target)) {
                        gameAPI.widget(187, 3, i).select();
                    }
                }
            }
        }
    }

    private InventoryItem ringOfDueling() {
        return gameAPI.inventory().withId(RING_OF_DUELING).first();
    }

    private InventoryItem gameAPIsNecklace() {
        return gameAPI.inventory().withId(GAMES_NECKLACE).first();
    }

    private InventoryItem combatBracelet() {
        return gameAPI.inventory().withId(COMBAT_BRACELET).first();
    }

    private InventoryItem skillsNecklace() {
        return gameAPI.inventory().withId(SKILLS_NECKLACE).first();
    }

    private InventoryItem ringOfWealth() {
        return gameAPI.inventory().withId(RING_OF_WEALTH).first();
    }

    private InventoryItem amuletOfGlory() {
        return gameAPI.inventory().withId(AMULET_OF_GLORY).first();
    }

    private InventoryItem necklaceOfPassage() {
        return gameAPI.inventory().withId(NECKLACE_OF_PASSAGE).first();
    }

    private InventoryItem burningAmulet() {
        return gameAPI.inventory().withId(BURNING_AMULET).first();
    }

    private InventoryItem xericsTalisman() {
        return gameAPI.inventory().withId(XERICS_TALISMAN).first();
    }

    private InventoryItem slayerRing() {
        return gameAPI.inventory().withId(SLAYER_RING).first();
    }

    private InventoryItem digsitePendant() {
        return gameAPI.inventory().withId(DIGSITE_PENDANT).first();
    }

    private InventoryItem drakansMedallion() {
        return gameAPI.inventory().withId(DRAKANS_MEDALLION).first();
    }

    //Magic
    private void cast(String spellName) {
        if (bank.isOpen()) {
            bank.close();
            gameAPI.tick();
        }
        log.info("Casting teleport - {}", spellName);
        standardSpellbook.castSpell(Spells.getWidget(spellName));
    }

    //Inventory items - tablets, scrolls, equipment(?)
    private void inventoryAction(String itemName, String action) {
        gameAPI.inventory().withName(itemName).withAction(action).first().interact(action);
    }

}
