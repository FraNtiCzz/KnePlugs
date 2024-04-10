package com.example.Alching;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;

import com.example.Alching.AlchPlugin;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class AutoAlchOverlay extends Overlay {
    private final PanelComponent panelComponent = new PanelComponent();

    private final Client client;

    private final AlchPlugin plugin;

    @Inject
    private AutoAlchOverlay (Client client, AlchPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.BOTTOM_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setDragTargetable(true);
    }

    public Dimension render(Graphics2D graphics) {
        this.panelComponent.getChildren().clear();
        this.panelComponent.setPreferredSize(new Dimension(250, 0));
        this.panelComponent.getChildren().add(TitleComponent.builder().text("Auto Alch").build());
        LineComponent timeout = buildLine("Started: ", String.valueOf(this.plugin.started));
        LineComponent state = buildLine("State: ", String.valueOf(this.plugin.alchState()));
        this.panelComponent.getChildren().add(timeout);
        panelComponent.getChildren().add(state);
        return this.panelComponent.render(graphics);
    }

    private LineComponent buildLine(String left, String right) {
        return LineComponent.builder()
                .left(left)
                .right(right)
                .leftColor(Color.WHITE)
                .rightColor(Color.YELLOW)
                .build();
    }

    private void renderTile(Graphics2D graphics, LocalPoint dest, Color color, double borderWidth, Color fillColor) {
        if (dest != null) {
            Polygon poly = Perspective.getCanvasTilePoly(this.client, dest);
            if (poly != null)
                OverlayUtil.renderPolygon(graphics, poly, color, fillColor, new BasicStroke((float)borderWidth));
        }
    }
}
