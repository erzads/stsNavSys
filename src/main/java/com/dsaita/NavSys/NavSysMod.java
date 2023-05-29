package com.dsaita.NavSys;

import basemod.BaseMod;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsaita.NavSys.pathing.AbstractDungeonWrapper;
import com.dsaita.NavSys.pathing.PathGenerator;
import com.dsaita.NavSys.pathing.PathInfo;
import com.dsaita.NavSys.ui.PathInfoPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.map.MapRoomNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dsaita.NavSys.ui.PathInfoPanel.*;
import static java.util.stream.Collectors.toList;

@SpireInitializer
public class NavSysMod implements RenderSubscriber {

    private List<PathInfo> pathInfoList;

    private MapRoomNode lastCurrentNode;

    private final List<Hitbox> hitboxes = new ArrayList<>();

    private Optional<Integer> hoveredIndex = Optional.empty();

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
            pathInfoList = possiblePaths.stream().map(PathInfo::new).collect(toList());
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
            if (hitboxes.isEmpty()) {
                setUpHitBoxes(sb);
            }

            for (int i = 0; i < hitboxes.size(); i++) {
                Hitbox hitbox = hitboxes.get(i);
                hitbox.update();
                if (hitbox.hovered) {
                    hoveredIndex = Optional.of(i);
                    break;
                }
                hoveredIndex = Optional.empty();
            }

            if (hoveredIndex.isPresent()) {
                this.highlightMap(hoveredIndex.get(), sb);
            } else {
                this.clearHighlight(sb);
            }
        }
    }

    private void setUpHitBoxes(SpriteBatch sb) {
        for (int i = 0; i < MAX_RESULTS; i++) {
            float y = CONTENT_Y - (5 + TEXT_LINE_OFFSET * (i + 1));
            Hitbox hitbox = new Hitbox(CONTENT_X, y, 200, 20);
            hitbox.render(sb);
            hitboxes.add(hitbox);
        }
    }

    private void highlightMap(Integer highlightedIndex, SpriteBatch sb) {
        if (pathInfoList.size() > highlightedIndex) {
            PathInfo pathInfo = pathInfoList.get(highlightedIndex);
            List<MapRoomNode> nodes = pathInfo.getNodes();
            for (MapRoomNode node : nodes) {
                if (!node.taken) {
                    node.color = Color.BLUE;
                    node.render(sb);
                }
            }
        }
    }

    private void clearHighlight(SpriteBatch sb) {
        for (PathInfo pathInfo : pathInfoList) {
            List<MapRoomNode> nodes = pathInfo.getNodes();
            for (MapRoomNode node : nodes) {
                if (node.color.equals(Color.BLUE)) {
                    node.color = Color.BLACK;
                    node.render(sb);
                }
            }
        }

    }
}
