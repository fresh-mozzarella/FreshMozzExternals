package net.runelite.client.plugins.presetmanager;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetType;
import net.runelite.client.util.ColorUtil;

import java.awt.*;

public class WidgetUtils
{
    public static void dispatchError(String error, Client client)
    {
        String str = ColorUtil.wrapWithColorTag("Custom Swapper", Color.MAGENTA)
                + " has encountered an "
                + ColorUtil.wrapWithColorTag("error", Color.RED)
                + ": "
                + error;

        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", str, null);
    }

    public static Widget createGraphic(Widget container, String name, int spriteId, int width, int height, int x, int y)
    {
        Widget widget = container.createChild(-1, WidgetType.GRAPHIC);
        widget.setOriginalWidth(width);
        widget.setOriginalHeight(height);
        widget.setOriginalX(x);
        widget.setOriginalY(y);

        widget.setSpriteId(spriteId);
        widget.setOnOpListener(ScriptID.NULL);
        widget.setHasListener(true);
        widget.setName(name);
        widget.revalidate();

        return widget;
    }

    public static Widget createGraphic(Widget container, int spriteId, int width, int height, int x, int y)
    {
        Widget widget = container.createChild(-1, WidgetType.GRAPHIC);
        widget.setOriginalWidth(width);
        widget.setOriginalHeight(height);
        widget.setOriginalX(x);
        widget.setOriginalY(y);

        widget.setSpriteId(spriteId);

        widget.revalidate();

        return widget;
    }

    public static Widget createText(Widget container, String text, int color, int width, int height, int x, int y)
    {
        Widget widget = container.createChild(-1, WidgetType.TEXT);
        widget.setOriginalWidth(width);
        widget.setOriginalHeight(height);
        widget.setOriginalX(x);
        widget.setOriginalY(y);

        widget.setText(text);
        widget.setFontId(FontID.PLAIN_11);
        widget.setTextColor(color);
        widget.setTextShadowed(true);

        widget.revalidate();

        return widget;
    }
}
