package net.runelite.client.plugins.externals.ui;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.plugins.externals.api.GameAPI;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class Chatbox {
    private final GameAPI gameAPI;

    @Inject
    public Chatbox(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }

    public ChatState chatState() {
        switch (gameAPI.widget(162, 559).nestedInterface()) {
            case -1:
                return ChatState.CLOSED;
            case 11:
                return ChatState.ITEM_CHAT;
            case 217:
                return ChatState.PLAYER_CHAT;
            case 231:
                return ChatState.NPC_CHAT;
            case 219:
                return ChatState.OPTIONS_CHAT;
            case 193:
                return ChatState.SPECIAL;
            case 229:
                return ChatState.MODEL;
            case 633:
                return ChatState.SPRITE;
            case 233:
                return ChatState.LEVEL_UP;
            case 270:
                return ChatState.MAKE;
            default:
                throw new IllegalStateException("unknown chat child " + gameAPI.widget(162, 562).nestedInterface());
        }
    }

    public void chat() {
        gameAPI.waitUntil(() -> chatState() != ChatState.CLOSED, 100);
        continueChats();
    }

    /**
     * Choose chat option in order of given chat options. Will fail if chat option isn't found.
     */
    public void chat(String... options) {
        gameAPI.waitUntil(() -> chatState() != ChatState.CLOSED, 100);

        for (var option : options) {
            continueChats();
            chooseOption(option);
        }

        continueChats();
    }

    public void chat(int... options) {
        gameAPI.waitUntil(() -> chatState() != ChatState.CLOSED, 100);

        for (var option : options) {
            continueChats();
            chooseOption(option);
        }

        continueChats();
    }

    /**
     * Choose chat option from any given chat options
     */
    public void chats(Collection<String> options) {
        gameAPI.waitUntil(() -> chatState() != ChatState.CLOSED);

        while (chatState() != ChatState.CLOSED) {
            continueChats();
            chooseOptions(options);
        }
    }

    public void continueChats() {
        while (chatState() != ChatState.CLOSED && chatState() != ChatState.OPTIONS_CHAT && chatState() != ChatState.MAKE) {
            continueChat();
            gameAPI.tick(1,2);
        }
    }

    public void continueChat() {
        switch (chatState()) {
            case CLOSED:
                throw new IllegalStateException("there's no chat");
            case OPTIONS_CHAT:
                throw new IllegalStateException("can't continue, this is an options chat");
            case PLAYER_CHAT:
                gameAPI.widget(217, 5).select();
                break;
            case NPC_CHAT:
                gameAPI.widget(231, 5).select();
                break;
            case ITEM_CHAT:
                gameAPI.widget(11, 4).select();
                break;
            case SPECIAL:
                gameAPI.widget(193, 0, 1).select();
                break;
            case MODEL:
                gameAPI.widget(229, 2).select();
                break;
            case SPRITE:
                gameAPI.widget(633, 0, 1).select();
                break;
            case LEVEL_UP:
                gameAPI.widget(233, 3).select();
                break;
            default:
                throw new IllegalStateException("unknown continue chat " + chatState());
        }
    }

    public List<String> getOptions() {
        List<String> options = new ArrayList<>();

        if (chatState() == ChatState.CLOSED || chatState() != ChatState.OPTIONS_CHAT) {
//            throw new IllegalStateException("Not an options chat");
            return options;
        }


        for (var i = 0; i < gameAPI.widget(219, 1).items().size(); i++) {
            if (gameAPI.widget(219, 1, i).text() != null) {
                options.add(gameAPI.widget(219, 1, i).text());
            }
        }

        return options;
    }

    public void chooseOption(String part) {
        if (chatState() == ChatState.CLOSED) {
            throw new IllegalStateException("chat closed before option: " + part);
        }

        if (chatState() != ChatState.OPTIONS_CHAT) {
            throw new IllegalStateException("not an options chat, " + chatState());
        }

        for (var i = 0; i < gameAPI.widget(219, 1).items().size(); i++) {
            if (gameAPI.widget(219, 1, i).text() != null && gameAPI.widget(219, 1, i).text().contains(part)) {
                gameAPI.widget(219, 1, i).select();
                gameAPI.waitChange(this::chatState, 6);
                gameAPI.tick(2,3);
                return;
            }
        }

        throw new IllegalStateException("no option " + part + " found");
    }

    public void chooseOption(int option) {
        if (chatState() == ChatState.CLOSED) {
            throw new IllegalStateException("chat closed before option: " + option);
        }

        if (chatState() != ChatState.OPTIONS_CHAT) {
            throw new IllegalStateException("not an options chat, " + chatState());
        }

        if (gameAPI.widget(219, 1, option).text() != null) {
            log.info("Selecting chat option from integer: {}", gameAPI.widget(219, 1, option).text());
            gameAPI.widget(219, 1, option).select();
            gameAPI.waitChange(this::chatState, 6);
            gameAPI.tick(2,3);
            return;
        }

        throw new IllegalStateException("no option " + option + " found");
    }

    public void chooseOptions(Collection<String> options) {
        if (chatState() == ChatState.CLOSED || chatState() != ChatState.OPTIONS_CHAT) {
            return;
        }

        List<String> checkedOptions = new ArrayList<>();
        for (var i = 0; i < gameAPI.widget(219, 1).items().size(); i++) {
            if (gameAPI.widget(219, 1, i).text() != null) {
                checkedOptions.add(gameAPI.widget(219, 1, i).text());
                //if (options.contains(gameAPI.widget(219, 1, i).text())) {
                final int finalIndex = i;
                if (options.stream().anyMatch(s -> gameAPI.widget(219, 1, finalIndex).text().toLowerCase().contains(s.toLowerCase()))) {
                    gameAPI.widget(219, 1, i).select();
                    gameAPI.waitChange(this::chatState, 6);
                    gameAPI.tick(2,3);
                    return;
                }
            }
        }

        throw new IllegalStateException("no option found: " + options.toString() + " from available options: " + String.join(", ", checkedOptions));
    }

    public String findFromOptions(Collection<String> options) {
        if (chatState() == ChatState.CLOSED || chatState() != ChatState.OPTIONS_CHAT) {
            return "";
        }

        for (var i = 0; i < gameAPI.widget(219, 1).items().size(); i++) {
            if (gameAPI.widget(219, 1, i).text() != null && options.contains(gameAPI.widget(219, 1, i).text())) {
                return gameAPI.widget(219, 1, i).text();
            }
        }
        return "";
    }

    public void selectMenu(String option) { //TODO untested
        gameAPI.waitUntil(() -> gameAPI.screenContainer().nestedInterface() == 187);

        for (var child : gameAPI.widget(187, 3).items()) {
            if (child.text() != null && child.text().contains(option)) {
                child.select();
                return;
            }
        }

        throw new IllegalArgumentException("no option '" + option + "' found");
    }

    public void selectExperienceItemSkill(Skill skill) {
        gameAPI.waitUntil(() -> gameAPI.screenContainer().nestedInterface() == 240);

        switch (skill) {
            case ATTACK:
                gameAPI.widget(240, 0, 0).select();
                break;
            case STRENGTH:
                gameAPI.widget(240, 0, 1).select();
                break;
            case RANGED:
                gameAPI.widget(240, 0, 2).select();
                break;
            case MAGIC:
                gameAPI.widget(240, 0, 3).select();
                break;
            case DEFENCE:
                gameAPI.widget(240, 0, 4).select();
                break;
            case HITPOINTS:
                gameAPI.widget(240, 0, 5).select();
                break;
            case PRAYER:
                gameAPI.widget(240, 0, 6).select();
                break;
            case AGILITY:
                gameAPI.widget(240, 0, 7).select();
                break;
            case HERBLORE:
                gameAPI.widget(240, 0, 8).select();
                break;
            case THIEVING:
                gameAPI.widget(240, 0, 9).select();
                break;
            case CRAFTING:
                gameAPI.widget(240, 0, 10).select();
                break;
            case RUNECRAFT:
                gameAPI.widget(240, 0, 11).select();
                break;
            case SLAYER:
                gameAPI.widget(240, 0, 12).select();
                break;
            case FARMING:
                gameAPI.widget(240, 0, 13).select();
                break;
            case MINING:
                gameAPI.widget(240, 0, 14).select();
                break;
            case SMITHING:
                gameAPI.widget(240, 0, 15).select();
                break;
            case FISHING:
                gameAPI.widget(240, 0, 16).select();
                break;
            case COOKING:
                gameAPI.widget(240, 0, 17).select();
                break;
            case FIREMAKING:
                gameAPI.widget(240, 0, 18).select();
                break;
            case WOODCUTTING:
                gameAPI.widget(240, 0, 19).select();
                break;
            case FLETCHING:
                gameAPI.widget(240, 0, 20).select();
                break;
            case CONSTRUCTION:
                gameAPI.widget(240, 0, 21).select();
                break;
            case HUNTER:
                gameAPI.widget(240, 0, 22).select();
                break;
        }

        gameAPI.waitUntil(() -> gameAPI.screenContainer().nestedInterface() != 240, 10);
    }

    public void make(int index, int quantity) {
        gameAPI.waitUntil(() -> chatState() == ChatState.MAKE);
        gameAPI.widget(270, 14 + index, quantity).select();
    }

    public enum ChatState {
        CLOSED,
        PLAYER_CHAT,
        NPC_CHAT,
        ITEM_CHAT,
        OPTIONS_CHAT,
        SPECIAL,
        MODEL,
        SPRITE,
        LEVEL_UP,
        MAKE
    }
}
