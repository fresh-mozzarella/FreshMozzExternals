package net.runelite.client.plugins.externals.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InteractionManager {

    private final GameAPI gameAPI;

    public InteractionManager(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }

    public void submit(Runnable runnable) {
//        log.info("interacting");
        gameAPI.sleepDelay();
        runnable.run();
    }

    public void interact(int identifier, int opcode, int param0, int param1) {
        log.info("interacting");
        gameAPI.sleepDelay();
        gameAPI.clientThread.invoke(() -> gameAPI.client().invokeMenuAction("", "", identifier, opcode, param0, param1));
    }

}
