package com.dsaita.NavSys;

import basemod.BaseMod;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsaita.NavSys.pathing.AbstractDungeonWrapper;
import com.dsaita.NavSys.pathing.PathGenerator;
import com.dsaita.NavSys.pathing.PathInfo;
import com.dsaita.NavSys.ui.PathInfoPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;

import java.util.List;

import static java.util.stream.Collectors.toList;

@SpireInitializer
public class NavSysMod implements RenderSubscriber {

    private List<PathInfo> pathInfoList;

    private MapRoomNode lastCurrentNode;

    public NavSysMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new NavSysMod();
    }


    private boolean generatePathsIfNeeded() {
        MapRoomNode currMapNode = AbstractDungeon.getCurrMapNode();
        if (pathInfoList == null || !currMapNode.equals(lastCurrentNode)) {
            lastCurrentNode = currMapNode;
            List<List<MapRoomNode>> possiblePaths = new PathGenerator(new AbstractDungeonWrapper()).generateAllPossiblePaths();
            pathInfoList = possiblePaths.stream().map(PathInfo::new).distinct().collect(toList());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void receiveRender(SpriteBatch sb) {
        if (CardCrawlGame.isInARun() && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.MAP) {
            generatePathsIfNeeded();
            new PathInfoPanel(sb).draw(pathInfoList);
        }
    }
}
