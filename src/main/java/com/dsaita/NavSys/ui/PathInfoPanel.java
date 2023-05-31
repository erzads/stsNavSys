package com.dsaita.NavSys.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsaita.NavSys.pathing.PathInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathInfoPanel {
    private static final float SCREEN_X = 20 * Settings.xScale;
    public static final float CONTENT_X = SCREEN_X + 5;
    public static final float CONTENT_Y = Settings.HEIGHT - Settings.yScale * 200;
    public static final float TEXT_LINE_OFFSET = 30;

    public static final float ICON_WIDTH = 25;
    public static final float ICON_HEIGHT = 25;
    public static final int MAX_RESULTS = 15;

    public static List<PathInfo> draw(SpriteBatch sb, List<PathInfo> pathInfoList) {
        sb.setColor(Color.WHITE);
        String panelTitle = "NavSys - " + pathInfoList.size() + " found (max: " + MAX_RESULTS + ")";
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.blockInfoFont, panelTitle, SCREEN_X, CONTENT_Y, Settings.GOLD_COLOR);

        List<PathInfo> filteredPaths = pathInfoList.stream()
                .sorted().distinct()
                .limit(MAX_RESULTS)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredPaths.size(); i++) {
            float y = CONTENT_Y - (TEXT_LINE_OFFSET * (i + 1));
            drawIcons(sb, CONTENT_X, y);
            renderText(sb, filteredPaths.get(i), CONTENT_X, y);
        }
        return filteredPaths;
    }

    private static void drawIcons(SpriteBatch sb, float baseX, float baseY) {
        float spaceBetweenIcons = 40;
        List<Texture> iconsTextures = Arrays.asList(
                ImageMaster.MAP_NODE_ELITE,
                ImageMaster.MAP_NODE_REST,
                ImageMaster.MAP_NODE_MERCHANT,
                ImageMaster.MAP_NODE_ENEMY,
                ImageMaster.MAP_NODE_EVENT
        );

        for (int i = 0; i < iconsTextures.size(); i++) {
            float x = baseX + 5;
            if (i != 0) {
                x = baseX + 5 + i * spaceBetweenIcons;
            }
            sb.draw(iconsTextures.get(i), x, baseY - 12.5f, ICON_WIDTH, ICON_HEIGHT);
        }
    }

    private static void renderText(SpriteBatch sb, PathInfo pathInfo, float baseX, float baseY) {
        float spaceBetweenText = 40;
        List<Integer> nodesCounts = Stream.of(pathInfo)
                .map(p -> Arrays.asList(p.getEliteNodeCount(), p.getRestSiteNodeCount(), p.getShopNodeCount(), p.getMonsterNodeCount(), p.getEventNodeCount()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        for (int i = 0; i < nodesCounts.size(); i++) {
            Integer nodeCount = nodesCounts.get(i);
            float x = baseX;
            if (i != 0) {
                x = baseX + (i * spaceBetweenText);
            }
            FontHelper.renderFontLeft(sb, FontHelper.blockInfoFont, String.valueOf(nodeCount), x, baseY, Settings.CREAM_COLOR);
        }
    }
}
