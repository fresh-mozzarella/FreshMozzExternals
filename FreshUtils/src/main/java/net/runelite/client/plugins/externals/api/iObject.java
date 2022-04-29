package net.runelite.client.plugins.externals.api;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.MenuAction;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.api.coords.LocalPoint;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class iObject implements Locatable, Interactable {

    private final GameAPI gameAPI;
    private final TileObject tileObject;
    private final ObjectComposition definition;

    public iObject(GameAPI gameAPI, TileObject tileObject, ObjectComposition definition) {
        this.gameAPI = gameAPI;
        this.tileObject = tileObject;
        this.definition = definition;
    }


    public GameAPI gameAPI() {
        return gameAPI;
    }

    public Client client() {
        return gameAPI.client;
    }

    @Override
    public Position position() {
        return new Position(tileObject.getWorldLocation());
    }

    public LocalPoint localPoint() {
        return tileObject.getLocalLocation();
    }

    public int id() {
        return tileObject.getId();
    }

    public String name() {
        return definition().getName();
    }

    public int orientation() {
        if (tileObject instanceof WallObject) {
            int orientation = ((WallObject) tileObject).getOrientationA();
            if (orientation == 1) return 0;
            if (orientation == 2) return 1;
            if (orientation == 4) return 2;
            if (orientation == 8) return 3;
            throw new AssertionError();
        }
        return -1;
    }

    public List<String> actions() {
        return Arrays.stream(definition().getActions())
                .collect(Collectors.toList());
    }

    public ObjectComposition definition() {
        int[] imposter = definition.getImpostorIds();
        if (imposter != null && imposter.length > 0 && definition.getImpostor() != null) {
            return definition.getImpostor();
        }
        return definition;
    }

    public Point menuPoint() {
        if (tileObject instanceof GameObject) {
            GameObject temp = (GameObject) tileObject;
            return temp.getSceneMinLocation();
        }
        return new Point(localPoint().getSceneX(), localPoint().getSceneY());
    }

    @Override
    public void interact(String action) {
        for (int i = 0; i < actions().size(); i++) {
            log.info("Action: {}", actions().get(i));
            if (action.equalsIgnoreCase(actions().get(i))) {
                interact(i);
                return;
            }
        }
        throw new IllegalArgumentException("no action \"" + action + "\" on object " + id());
    }

    private int getActionId(int action) {
        switch (action) {
            case 0:
                return MenuAction.GAME_OBJECT_FIRST_OPTION.getId();
            case 1:
                return MenuAction.GAME_OBJECT_SECOND_OPTION.getId();
            case 2:
                return MenuAction.GAME_OBJECT_THIRD_OPTION.getId();
            case 3:
                return MenuAction.GAME_OBJECT_FOURTH_OPTION.getId();
            case 4:
                return MenuAction.GAME_OBJECT_FIFTH_OPTION.getId();
            default:
                throw new IllegalArgumentException("action = " + action);
        }
    }

    public void interact(int action) {
        gameAPI.interactionManager().interact(id(),
                getActionId(action),
                menuPoint().getX(),
                menuPoint().getY()
        );
    }
}
