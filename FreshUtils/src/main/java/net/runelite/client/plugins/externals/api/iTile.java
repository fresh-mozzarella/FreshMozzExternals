package net.runelite.client.plugins.externals.api;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.plugins.externals.api.ObjectCategory;

import java.util.Arrays;
import java.util.Objects;

import static net.runelite.api.Constants.CHUNK_SIZE;

@Slf4j
public class iTile implements Locatable {
    final GameAPI gameAPI;
    final Tile tile;
    iObject regularObject;
    iObject wall;
    iObject wallDecoration;
    iObject floorDecoration;

    iTile(GameAPI gameAPI, Tile tile) {
        this.gameAPI = gameAPI;
        this.tile = tile;
    }

    //    @Override
    public GameAPI gameAPI() {
        return gameAPI;
    }

    @Override
    public Client client() {
        return gameAPI.client;
    }

    @Override
    public Position position() {
        return new Position(tile.getSceneLocation().getX(), tile.getSceneLocation().getY(), tile.getPlane());
    }

    public Position templatePosition() {
        if (client().isInInstancedRegion()) {
            LocalPoint localPoint = client().getLocalPlayer().getLocalLocation();
            int[][][] instanceTemplateChunks = client().getInstanceTemplateChunks();
            int z = client().getPlane();
            int chunkData = instanceTemplateChunks[z][localPoint.getSceneX() / CHUNK_SIZE][localPoint.getSceneY() / CHUNK_SIZE];

            int rotation = chunkData >> 1 & 0x3; //TODO
            int chunkX = (chunkData >> 14 & 0x3FF) * CHUNK_SIZE + (localPoint.getSceneX() % CHUNK_SIZE);
            int chunkY = (chunkData >> 3 & 0x7FF) * CHUNK_SIZE + (localPoint.getSceneY() % CHUNK_SIZE);
            int chunkZ = (chunkData >> 24 & 0x3);

            return new Position(chunkX, chunkY, chunkZ);
        }
        return gameAPI.localPlayer().position();
    }

    public void walkTo() {
        gameAPI.interactionManager().submit(() -> gameAPI.walkUtils.sceneWalk(position(), 0, 0));
    }

    public iObject object(ObjectCategory category) {
        switch (category) {
            case REGULAR:
                if (tile == null) {
                    return null;
                }

                var objects = tile.getGameObjects();

                if (objects == null) {
                    return null;
                }

                GameObject go = Arrays.stream(objects).filter(Objects::nonNull).findFirst().orElse(null);
                return (go == null) ? null : new iObject(gameAPI, go,
                        gameAPI.getFromClientThread(() -> client().getObjectDefinition(go.getId()))
                );
            case WALL:
                WallObject wo = tile.getWallObject();
                return (wo == null) ? null : new iObject(gameAPI, wo, gameAPI.getFromClientThread(() -> client().getObjectDefinition(wo.getId())));
            case WALL_DECORATION:
                DecorativeObject dec = tile.getDecorativeObject();
                return (dec == null) ? null : new iObject(gameAPI, dec, gameAPI.getFromClientThread(() -> client().getObjectDefinition(dec.getId())));
            case FLOOR_DECORATION:
                GroundObject ground = tile.getGroundObject();
                return (ground == null) ? null : new iObject(gameAPI, ground, gameAPI.getFromClientThread(() -> client().getObjectDefinition(ground.getId())));
            default:
                return null;
        }
    }
}
