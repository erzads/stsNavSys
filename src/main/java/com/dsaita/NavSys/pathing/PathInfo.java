package com.dsaita.NavSys.pathing;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;

import java.util.List;
import java.util.Objects;

public class PathInfo {

    private final List<MapRoomNode> nodes;
    private int monsterNodeCount;
    private int eventNodeCount;
    private int restSiteNodeCount;
    private int eliteNodeCount;
    private int shopNodeCount;

    public PathInfo(List<MapRoomNode> nodes) {
        this.nodes = nodes;
        for (MapRoomNode node : nodes) {
            if (node.getRoom() instanceof MonsterRoomElite) {
                eliteNodeCount++;
            } else if (node.getRoom() instanceof MonsterRoom) {
                monsterNodeCount++;
            } else if (node.getRoom() instanceof EventRoom) {
                eventNodeCount++;
            } else if (node.getRoom() instanceof RestRoom) {
                restSiteNodeCount++;
            } else if (node.getRoom() instanceof ShopRoom) {
                shopNodeCount++;
            }
        }
    }

    public int getMonsterNodeCount() {
        return monsterNodeCount;
    }

    public int getEventNodeCount() {
        return eventNodeCount;
    }

    public int getRestSiteNodeCount() {
        return restSiteNodeCount;
    }

    public int getEliteNodeCount() {
        return eliteNodeCount;
    }

    public int getShopNodeCount() {
        return shopNodeCount;
    }

    public List<MapRoomNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathInfo pathInfo = (PathInfo) o;
        return monsterNodeCount == pathInfo.monsterNodeCount && eventNodeCount == pathInfo.eventNodeCount && restSiteNodeCount == pathInfo.restSiteNodeCount && eliteNodeCount == pathInfo.eliteNodeCount && shopNodeCount == pathInfo.shopNodeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(monsterNodeCount, eventNodeCount, restSiteNodeCount, eliteNodeCount, shopNodeCount);
    }
}
