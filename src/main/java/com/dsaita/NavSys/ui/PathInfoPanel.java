package com.dsaita.NavSys.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsaita.NavSys.pathing.PathInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.List;

public class PathInfoPanel {

    private static final Texture SCREEN = new Texture("images/highlight_menu.png");
    public static final float SCREEN_WIDTH = 360.0f * Settings.scale;
    public static final float SCREEN_HEIGHT = 500.0f * Settings.scale;
    private static final float SCREEN_X = 10.0f * Settings.xScale;
    private static final float SCREEN_Y = 550.0f * Settings.yScale;
    private static final float CONTENT_X = 25.0f * Settings.scale;
    private static final float TITLE_Y = 360.0f * Settings.yScale;
    private static final float TEXT_LINE_OFFSET = 40.0f * Settings.scale;
    private final SpriteBatch sb;

    public PathInfoPanel(SpriteBatch sb) {
        this.sb = sb;
    }

    public void draw(List<PathInfo> pathInfoList) {
        sb.setColor(Color.WHITE);
        sb.draw(SCREEN, SCREEN_X, SCREEN_Y, SCREEN_WIDTH, SCREEN_HEIGHT);

        FontHelper.renderFontLeftTopAligned(sb, FontHelper.blockInfoFont, "NavSys - Saita", SCREEN_X + CONTENT_X, SCREEN_Y + TITLE_Y, Settings.GOLD_COLOR);

        for (int i = 0; i < pathInfoList.size(); i++) {
            PathInfo pathInfo = pathInfoList.get(i);
            String label = String.format("%dElt %dRst %d$ %dX %d?",
                    pathInfo.getEliteNodeCount(),
                    pathInfo.getRestSiteNodeCount(),
                    pathInfo.getShopNodeCount(),
                    pathInfo.getMonsterNodeCount(),
                    pathInfo.getEventNodeCount());
            FontHelper.renderFontLeft(sb, FontHelper.blockInfoFont, label, SCREEN_X + CONTENT_X, SCREEN_Y + TITLE_Y - (TEXT_LINE_OFFSET * (i + 1)), Settings.CREAM_COLOR);
        }

    }
}
