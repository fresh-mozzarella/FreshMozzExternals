package net.runelite.client.plugins.freshutils.api;

import net.runelite.api.Skill;
import net.runelite.client.plugins.freshutils.game.GameAPI;
import net.runelite.client.plugins.freshutils.ui.Prayer;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class Prayers {
    private final GameAPI game;
    private final Map<Prayer, Long> prayerTimes = new HashMap<>();
    private final Map<Prayer, Boolean> prayerStatuses = new HashMap<>();

    @Inject
    public Prayers(GameAPI game) {
        this.game = game;
    }

    public boolean active(Prayer prayer) {
        if (System.currentTimeMillis() - prayerTimes.getOrDefault(prayer, 0L) < 1800) {
            return prayerStatuses.getOrDefault(prayer, false);
        }

        return game.varb(prayer.varb) == 1;
    }

    public void setEnabled(Prayer prayer, boolean enabled) {
        if (game.modifiedLevel(Skill.PRAYER) != 0) {

            if (enabled == active(prayer)) {
                return;
            }

            prayerTimes.put(prayer, System.currentTimeMillis());
            prayerStatuses.put(prayer, enabled);

            game.widget(541, prayer.widget).interact(0);
        }
    }
}
