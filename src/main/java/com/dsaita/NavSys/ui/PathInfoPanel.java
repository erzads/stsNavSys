package com.dsaita.NavSys.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsaita.NavSys.pathing.PathInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class PathInfoPanel {

    private static final Texture SCREEN = new Texture("images/highlight_menu.png");
    public static final float SCREEN_WIDTH = 300.0f;
    public static final float SCREEN_HEIGHT = 500.0f;
    private static final float SCREEN_X = 20 * Settings.xScale;
    private static final float SCREEN_Y = Settings.HEIGHT - 50 - SCREEN_HEIGHT;
    public static final float CONTENT_X = 25 + SCREEN_X;
    public static final float CONTENT_Y = Settings.HEIGHT - 70;
    public static final float TEXT_LINE_OFFSET = 40 * Settings.scale;

    public static final int MAX_RESULTS = 15;
    private final SpriteBatch sb;

    public PathInfoPanel(SpriteBatch sb) {
        this.sb = sb;
    }

    public void draw(List<PathInfo> pathInfoList) {
        sb.setColor(Color.WHITE);
        sb.draw(SCREEN, SCREEN_X, SCREEN_Y, SCREEN_WIDTH, SCREEN_HEIGHT);

        String panelTitle = "NavSys - " + pathInfoList.size() + " found (max: " + MAX_RESULTS + ")";
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.blockInfoFont, panelTitle, CONTENT_X, CONTENT_Y, Settings.GOLD_COLOR);

        List<PathInfo> filteredPaths = pathInfoList.stream()
                .sorted().distinct()
                .limit(MAX_RESULTS)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredPaths.size(); i++) {
            PathInfo pathInfo = filteredPaths.get(i);
            String label = String.format("%dElite %dRest %d$ %dX %d?",
                    pathInfo.getEliteNodeCount(),
                    pathInfo.getRestSiteNodeCount(),
                    pathInfo.getShopNodeCount(),
                    pathInfo.getMonsterNodeCount(),
                    pathInfo.getEventNodeCount());
            float y = CONTENT_Y - (TEXT_LINE_OFFSET * (i + 1));
            FontHelper.renderFontLeft(sb, FontHelper.blockInfoFont, label, CONTENT_X, y, Settings.CREAM_COLOR);
        }
    }
}
